package it.eng.pilot;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;

/**
 * Classe base astratta per gli oggetti Entity. Tale classe offre tutte le
 * funzionalità di esecuzione delle query di select/delete/insert/update/upsert.
 * 
 * I parametri passati a Null NON VENGONO APPLICATI nella formazione della where
 * condition.
 * 
 * Nelle select la condizione FLAG_STATO=A (deleteLogic=true) viene
 * automaticamente impostata nella formazione della where condition.
 * 
 * Nelle insert/delete/update,le colonne FLAG_STATO (deleteLogic=true),
 * DATA_AGGIORNAMENTO, COD_UTENTE, COD_APP vengono automaticamente valorizzate
 * con A, sysdate, codUtente e codApp specificati dagli attributi relativi in
 * DaoCOnfig.
 * 
 * @author Antonio Corinaldi
 * 
 */
@SuppressWarnings("unchecked")
public abstract class BaseDaoEntity extends PilotSupport implements Entity {

	public static final String PIPE = "|";
	public static final String KEY_ = "_?^";
	private static final String UP_SET = " SET ";
	private static final String WHERE = " WHERE ";
	protected final String DESC = "DESC";
	protected final String ASC = "ASC";
	private static final String COMMA = DaoHelper.COMMA;
	public static final String SET = "set";
	private static final String MSET = "SET";
	private static final String MGET = "GET";
	protected final String GET = "get";
	private static final String OPEN = DaoHelper.OPEN_QUADRA;
	private static final String SPACE = DaoHelper.SPACE;
	private static final String DOUBLE_SPACE = DaoHelper.DOUBLE_SPACE;
	private static final String CLOSE = DaoHelper.CLOSE_QUADRA;
	private static final String LEFT_ARROW = DaoHelper.LEFT_ARROW;
	private static final String RIGHT_ARROW = DaoHelper.RIGHT_ARROW;
	public static final String QUERY_PREFIX = "Query in esecuzione..";
	private static final long serialVersionUID = -7873548059367056469L;
	protected static final String METHOD_FLAG_STATO = "FLAGSTATO";
	protected static final String METHOD_COD_UTENTE = "CODUTENTE";
	protected static final String METHOD_COD_APPL = "CODAPPL";
	protected static final String METHOD_DATA_AGGIORN = "DATAAGGIORN";
	private boolean searchByPk = false;
	private boolean skipFlagStato = false;
	private boolean skipInstanceVars = false;
	private boolean byPassUpdateDeleteLimitation = false;
	private String queryEseguita;
	private String execTime;
	private Integer queryTimeout = 60;
	private Integer updateDeleteLimit = null;
	private Long recordImpattati = 0l;
	protected transient Logger log = Logger.getLogger(getClass().getName());
	private boolean resumed = false;
	protected transient Field[] attributi = getClass().getDeclaredFields();
	protected transient Method[] metodiEnt = getClass().getDeclaredMethods();
	protected boolean logWhileRunning = true;// se true logga le query durante
	// l'esecuzione altrimenti no
	public static final String ERROR_MARK = OPEN + "ERRORE!" + CLOSE;
	public static final String RECORD = "record";
	private boolean mock;
	private PMap<String, PList<BaseEntity>> db = pmapl();
	protected PList<String> distinctFields = plstr();
	private PList<String> distinctCol = plstr();
	private String joinCondition;
	private String joinName;
	private boolean fromReal;
	protected PList<String> fields = plstr();
	private PList<String> cols = plstr();
	private Map<String, PList<? extends BaseEntity>> tableCache = new HashMap<String, PList<? extends BaseEntity>>();
	private boolean db2;
	private boolean dsMode;
	private boolean multi;

	protected Field[] getAttributi() {
		if (null == attributi) {
			attributi = getClass().getDeclaredFields();
		}
		return attributi;
	}

	protected Method[] getMetodiEnt() {
		if (null == metodiEnt) {
			metodiEnt = getClass().getDeclaredMethods();
		}
		return metodiEnt;
	}

	public void setTableCache(Map<String, PList<? extends BaseEntity>> tableCache) {
		this.tableCache = tableCache;
	}

	public Map<String, PList<? extends BaseEntity>> getTableCache() {
		return this.tableCache;
	}

	protected PList<String> getDistinctFields() {
		return distinctFields;
	}

	protected void setDistinctFields(PList<String> distinctFields) {
		this.distinctFields = distinctFields;
	}

	protected Method findMethod(Method[] metodi, String nome) {
		Method met = null;
		for (Method m : metodi) {
			if (is(m.getName(), nome)) {
				met = m;
				break;
			}
		}
		return met;
	}

	protected <K> void invokeSetter(K id, Field att) throws Exception {
		findMethod(metodiEnt, str(SET, cap(att.getName()))).invoke(this, id);
	}

	protected <K> void invokeSetter(K id, String campo, K ent) throws Exception {
		findMethod(metodiEnt, str(SET, cap(campo))).invoke(ent, id);
	}

	protected <K, X> void invokeSetter(K id, Field att, X ent) throws Exception {
		findMethod(metodiEnt, str(SET, cap(att.getName()))).invoke(ent, id);
	}

	protected <K> K invokeGetter(Field att) throws Exception {
		return (K) getClass().getMethod(str(GET, cap(att.getName()))).invoke(this);
	}

	protected <K> K invokeGetter(K ent, Field att) throws Exception {
		return (K) getClass().getMethod(str(GET, cap(att.getName()))).invoke(ent);
	}

	private void setQueryEseguita(String sql) {
		this.queryEseguita = sql;
	}

	/**
	 * Ritorna la query eseguita da questa Entity
	 * 
	 * @return String
	 */
	public String getQueryEseguita() {
		return queryEseguita;
	}

	public abstract Connection getConnection();

	public abstract Logger getLogger();

	protected abstract PList<String> getContainer();

	protected abstract void setContainer(PList<String> container);

	protected PList<String> providedAttributes = plstr();

	protected abstract <K> K getDataInizio();

	protected abstract <K> K getDataFine();

	protected abstract <K> BaseEntity setDataFine(K endDate);

	protected abstract <K> BaseEntity setDataInizio(K endDate);

	/**
	 * Se valorizzato mediante la chiamata al costruttore a 4 parametri di
	 * BaseEntity, viene richiamato automaticamente nella operazione di update
	 * per impostare automaticamente la colonna ...COD_APPL al valore impostato.
	 * 
	 * 
	 * @return String
	 */
	public abstract String getCodApplCostruttore();

	/**
	 * Se valorizzato mediante la chiamata al costruttore a 4 parametri di
	 * BaseEntity, viene richiamato automaticamente nella operazione di update
	 * per impostare la colonna ...COD_UTENTE al valore impostato
	 * 
	 * @return String
	 */
	public abstract String getCodUtenteCostruttore();

	/**
	 * Ritorna la PK sotto forma di stringa separando ogni elemento con "-".
	 * Considera tutte gli attributi con @Column pk=true
	 * 
	 * @return String
	 */
	public String getPk() {
		PList<Object> valori = pl();
		for (Field f : safe(getFieldsPk())) {
			try {
				String nomeMetodo = str(GET, cap(f.getName()));
				Method metodo = getClass().getMethod(nomeMetodo);
				valori.add(metodo.invoke(this));
			} catch (Exception e) {
				logError("Errore in getPk()", e);
			}
		}
		return valori.concatena(Pilot.DASH);
	}

	private PList<Field> getFieldsPk() {
		PList<Field> campiPk = pl();
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.pk()) {
					campiPk.add(att);
				}
			}
		}
		return campiPk;
	}

	private String getFieldProgressivo() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.progr()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	protected String getFieldId() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.id()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private Field getFieldSequence() {
		Field campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.pk() && notNull(annCol.seq())) {
					campo = att;
				}
			}
		}
		return campo;
	}

	private String getSequence(String campo) {
		String seqName = null;
		for (Field att : getAttributi()) {
			if (is(att.getName(), campo)) {
				Column annCol = att.getAnnotation(Column.class);
				if (notNull(annCol)) {
					seqName = annCol.seq();
				}
				break;
			}
		}
		return seqName;
	}

	private PList<String> buildColumns(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		PList<String> alias = plstr();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			alias.add(rsmd.getColumnName(i));
		}
		return alias;
	}

	protected void closeAlls(PreparedStatement stmt, ResultSet rs) {
		try {
			if (notNull(stmt))
				stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			if (notNull(rs))
				rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			if (isDsMode() && !multi) {
				if (notNull(getConnection())) {
					getConnection().close();
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean checkNullable() throws Exception {
		boolean esito = true;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.notNull()) {
					if (Null(invokeGetter(att))) {
						logError("Il campo", att.getName(), "non può contenere un valore NULL. Operazione non eseguita.");
						esito = false;
					}
				}

			}
		}
		return esito;
	}

	private boolean checkNullable(PList<String> campiDaAggiornare) throws Exception {
		boolean esito = true;
		for (Field att : getAttributi()) {
			if (!is(campiDaAggiornare, att.getName()))
				continue;
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.notNull()) {
					if (Null(invokeGetter(att))) {
						logError("Il campo", att.getName(), "non può contenere un valore NULL. Operazione non eseguita.");
						esito = false;
					}
				}

			}
		}
		return esito;
	}

	/**
	 * Esegue l'inserimento dell'oggetto entity. Se si utilizza il costruttore a
	 * 4 parametri per l'Entity da inserire, l'inserimento dei valori per le
	 * colonne FLAG_STATO ad A, COD_UTENTE per il codice utente passato COD_APPL
	 * per il codice appl passato e DATA_AGGIORN a data di sistema attuale,
	 * AVVENGONO AUTOMATICAMENTE SENZA LA NECESSITA' DI SETTARE MANUALMENTE
	 * QUESTI VALORI NELLE VARIABILI ISTANZA DELL'ENTITY. QUALORA VENGANO
	 * SETTATI MANUALMENTE NELLE VARIABILI ISTANZA DELL'ENTITY, QUESTI VALORI
	 * COSI' SETTATI HANNO LA PRECEDENZA OSSIA RICOPRONO QUELLI AUTOMATICI DI
	 * CUI SOPRA
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	private boolean _insert() throws Exception {
		setQuaternaFlagStatoACodUtenteCodApplDataAggiorn();
		setNextProgrForInsert();
		if (!isMock()) {
			logEseguo("INSERT");
			setNextValForSequence();
		}

		if (isMock()) {
			if (!isFromReal()) {
				setQuaternaFlagStatoACodUtenteCodApplDataAggiorn();
				setNextProgrForInsert();
				setSearchByPk(true);
				skipFlagStato = true;
				if (notNull(getFieldSequence())) {
					Long seq = zeroIfNull((Long) safe((select().narrow(getFieldSequence().getName()))).max()) + 1;
					invokeSetter(seq, getFieldSequence());
					logEseguo("INSERT");
				}
				setSearchByPk(false);
				skipFlagStato = false;
				if (!checkNullable())
					return false;
			}
			getDb().aggiungiMappaListaEnt(getClass().getName(), this);
			return true;
		}
		Map<String, String> mappa = new HashMap<String, String>();
		Table tableAnn = getClass().getAnnotation(Table.class);
		String tableName = tableAnn.name();
		String comma = " , ";
		String sql = str("INSERT INTO ", tableName, " ( ");
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				aggiungi(mappa, annCol.name(), att.getName());
			}
		}

		for (Map.Entry<String, String> entry : mappa.entrySet()) {
			sql = str(sql, entry.getKey(), comma);

		}
		sql = str(substring(sql, null, false, false, comma, false, true), " ) VALUES( ");

		for (Map.Entry<String, String> entry : mappa.entrySet()) {
			sql = str(sql, getValore(get(this, entry.getValue())), comma);
		}
		sql = str(substring(sql, null, false, false, comma, false, true), " ) ");
		PreparedStatement stmt = null;
		int num = 0;
		Date start = null;
		Date end = null;
		Integer numRecord = 0;
		String error = null;
		try {
			stmt = getConnection().prepareStatement(sql);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			num = stmt.executeUpdate();
			end = now();
			numRecord = 1;
		} catch (Exception e) {
			end = now();
			error = str(getPk(), RIGHT_ARROW, e.getMessage());
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(sql, start, end, numRecord, error);
			else
				logQuery(sql, start, end, numRecord);
			closeAlls(stmt, null);
		}
		return num > 0;
	}

	private String getAnnotazioneCampo(Map<String, String> mappa, String campo) {
		String colonna = null;
		for (Map.Entry<String, String> entry : mappa.entrySet()) {
			if (is(entry.getValue(), campo)) {
				colonna = entry.getKey();
				break;
			}
		}
		return colonna;
	}

	private boolean presentInWhere(String colonna) {
		boolean ret = false;
		PList<Where> wc = getWhereCondition();
		for (Where w : safe(wc)) {
			if (is(w.getCampo(), colonna)) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	private String setWhereCondition(Map<String, String> mappa, String sql) throws Exception {
		PList<Where> wc = getWhereCondition();
		if (!checkWhereForSqlInjecton(wc)) {
			String err = "Possibile SQL Injection rilevata, QUERY NON ESEGUITA!";
			throw new Exception(err);
		}
		if (notNull(wc)) {
			sql = str(sql, WHERE);
			for (Where w : safe(wc)) {
				if (w.isPar()) {
					sql = str(sql, "(");
					continue;
				}
				if (w.isClosePar()) {
					sql = str(sql, ")");
					sql = replaceLast(sql, str(OperatoreBooleano.AND.op, ")"), str(")", OperatoreBooleano.AND.op));
					sql = replaceLast(sql, str(OperatoreBooleano.OR.op, ")"), str(")", OperatoreBooleano.OR.op));
					continue;
				}
				String colonna = getColonnaWhereCondition(mappa, w);
				if (tutte(Null(colonna), !is(w.getOp(), Operatore.LIMIT)))
					continue;
				String operatoreBooleano = "";
				try {
					operatoreBooleano = w.getBop().getOp();
				} catch (Exception e) {
				}
				if (is(w.getOp(), Operatore.LIMIT)) {
					sql = str(sql, colonna, w.getOp().getOp(), w.getV1(), operatoreBooleano);
					continue;
				}

				if (is(w.getOp(), Operatore.IN, Operatore.NOTIN)) {
					if (notNull(w.getVals())) {
						String valoriIn = concatenaListaForInClause(w.getVals(), COMMA);
						sql = str(sql, colonna, w.getOp().getOp(), valoriIn, w.getOp().getSuffixOp(), operatoreBooleano);
					} else {
						sql = str(sql, colonna, Operatore.EQUAL.op, colonna, operatoreBooleano);
					}
					continue;
				}

				if (almenoUna(w.getOp() == Operatore.ISNULL, w.getOp() == Operatore.ISNOTNULL)) {
					sql = str(sql, colonna, w.getOp().getOp(), operatoreBooleano);
					continue;
				}
				if (Null(w.getV1())) {
					w.setAnnnullatov1(true);
					w.setV1(colonna);
					if (almenoUna(w.getOp() == Operatore.GT, w.getOp() == Operatore.GTE, w.getOp() == Operatore.LT, w.getOp() == Operatore.LTE, w.getOp() == Operatore.NOT_EQUAL)) {
						w.setOp(Operatore.EQUAL);
					}
				}
				if (w.getOp() == Operatore.BETWEEN) {
					if (Null(w.getV2())) {
						w.setAnnnullatov2(true);
						w.setV2(colonna);
					}

					if (w.isAnnnullatov1()) {
						sql = str(sql, colonna, w.getOp().getOp(), w.getV1().toString(), " AND ");
					} else {
						sql = str(sql, colonna, w.getOp().getOp(), getValore(w.getV1()), " AND ");
					}

					if (w.isAnnnullatov2()) {
						sql = str(sql, w.getV2().toString(), operatoreBooleano);

					} else {
						sql = str(sql, getValore(w.getV2()), operatoreBooleano);
					}

				} else {
					if (w.isAnnnullatov1()) {
						sql = str(sql, colonna, w.getOp().getOp(), w.getV1().toString(), w.getOp().getSuffixOp(), operatoreBooleano);
					} else {
						sql = str(sql, colonna, w.getOp().getOp(), getValore(w.getV1()), w.getOp().getSuffixOp(), operatoreBooleano);
					}
				}
			}
		}
		if (sql.endsWith(OperatoreBooleano.AND.op)) {
			sql = substring(sql, null, false, false, OperatoreBooleano.AND.op, false, true);
		}

		if (sql.endsWith(OperatoreBooleano.OR.op)) {
			sql = substring(sql, null, false, false, OperatoreBooleano.OR.op, false, true);
		}
		return sql;
	}

	private boolean checkWhereForSqlInjecton(PList<Where> wc) {
		boolean test = true;
		for (Where w : safe(wc)) {
			if (w.getV1() instanceof String) {
				String t = (String) w.getV1();
				if (isPossibleSqlInjection(t)) {
					test = false;
					break;
				}
			}
			if (w.getV2() instanceof String) {
				String t = (String) w.getV2();
				if (isPossibleSqlInjection(t)) {
					test = false;
					break;
				}
			}
			for (Object v : safe(w.getVals())) {
				if (v instanceof String) {
					String t = (String) w.getV2();
					if (isPossibleSqlInjection(t)) {
						test = false;
						break;
					}

				}
				break;
			}
		}
		return test;

	}

	private boolean isPossibleSqlInjection(String input) {
		if (notNull(input)) {
			for (String keyword : DaoHelper.sqlKeywords) {
				if (input.toUpperCase().contains(keyword)) {
					log("ATTENZIONE! PARAMETRO ", input, " POSSIBILE SQL INJECTION!");
					return true; // Potenziale SQL injection rilevata
				}
			}
		}
		return false; // La stringa di input non sembra essere una SQL injection
	}

	/**
	 * Esegue la cancellazione dell'oggetto entity. La query non viene eseguita
	 * per ragioni di sicurezza qualora non venga imposta la where condition
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	private <K extends BaseEntity> boolean _delete() throws Exception {

		Map<String, String> mappa = new HashMap<String, String>();
		Table tableAnn = getClass().getAnnotation(Table.class);
		String tableName = tableAnn.name();
		String sql = str("DELETE FROM ", tableName, SPACE);
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				aggiungi(mappa, annCol.name(), att.getName());
			}
		}
		setWhereConditionByInstanceVars();
		sql = setWhereCondition(mappa, sql);
		if (isMock()) {
			getDb().rimuoviMappaListaEnt(getClass().getName(), mockSelect());
			return true;
		}
		PreparedStatement stmt = null;
		int num = 0;
		Date start = null;
		Date end = null;
		String error = null;
		try {
			if (isLike(sql, WHERE)) {
				stmt = getConnection().prepareStatement(sql);
				stmt.setQueryTimeout(getQueryTimeout());
				start = now();
				num = stmt.executeUpdate();
				end = now();
			} else {
				logNoWhere();
			}
		} catch (Exception e) {
			end = now();
			error = str(getPk(), RIGHT_ARROW, e.getMessage());
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(sql, start, end, recordImpattati.intValue(), error);
			else
				logQuery(sql, start, end, recordImpattati.intValue());

			closeAlls(stmt, null);
		}
		return num > 0;
	}

	private void logNoSet() {
		log("MANCANZA DELLA CLAUSOLA SET!QUERY DI UPDATE NON ESEGUITA SULLA ENTITY ", getEntityDetail());
	}

	/**
	 * Esegue l'aggiornamento dell'oggetto entity. La query non viene eseguita
	 * per ragioni di sicurezza qualora non venga impostata la where condition
	 * 
	 * Se l'Entity presenta la colonna ...DATA_AGGIORN, questa viene
	 * automaticamente impostata alla data odierna Tutti i valori che sono
	 * impostati nell'Entity attraverso i metodi set, saranno impostati con
	 * SET='...'. I valori impostati a NULL saranno impostati con SET='' Le
	 * variabili istanza non impostate non verranno considerate
	 * nell'aggiornamento delle rispettive colonne della tabella
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public <K extends BaseEntity> boolean update() throws Exception {
		if (isOverLimit()) {
			if (notNull(getWhereCondition())) {
				logOverLimit("Update");
			}
			return false;
		}
		if (Null(getProvidedAttributes())) {
			logNoSet();
			return false;
		}

		Map<String, String> mappa = new HashMap<String, String>();
		setTernaCodUtenteCodApplDataAggiorn();
		if (notNull(getFieldsToUpdate())) {
			getFieldsToUpdate().add(getFieldCodUtente());
			getFieldsToUpdate().add(getFieldCodApp());
			getFieldsToUpdate().add(getFieldDataAggiornamento());
		}
		Table tableAnn = getClass().getAnnotation(Table.class);
		String comma = " , ";
		String tableName = tableAnn.name();
		String sql = str("UPDATE ", tableName, SPACE);
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				aggiungi(mappa, annCol.name(), att.getName());
			}
		}
		if (notNull(mappa)) {
			sql = str(sql, UP_SET);
			for (Map.Entry<String, String> entry : mappa.entrySet()) {
				String campo = entry.getValue();

				if (notNull(this.getFieldsToExcludeInUpdate()) && is(this.getFieldsToExcludeInUpdate(), campo))
					continue;

				if (notNull(this.getFieldsToUpdate()) && isNot(this.getFieldsToUpdate(), campo))
					continue;
				String colonna = getAnnotazioneCampo(mappa, campo);
				Object val = get(this, campo);

				Boolean provided = isProvided(campo);
				if (provided) {
					if (notNull(val)) {
						String valore = getValore(val);
						sql = str(sql, colonna, " = ", valore, comma);
					} else {
						sql = str(sql, colonna, " = '' ", comma);
					}
				}
			}
			sql = str(substring(sql, null, false, false, comma, false, true));
			sql = setWhereCondition(mappa, sql);
		}
		String primaParte = substring(sql, null, false, false, "=", true, false);
		if (!isLike(primaParte, UP_SET)) {
			logNoSet();
			return false;
		}
		if (isMock()) {
			PList<String> campiDaAggiornare = getProvidedAttributes();
			if (!checkNullable(campiDaAggiornare))
				return false;
			PList<K> elenco = mockSelect();
			for (Iterator iterator = campiDaAggiornare.iterator(); iterator.hasNext();) {
				String campo = (String) iterator.next();
				if (notNull(getFieldsToExcludeInUpdate()) && is(getFieldsToExcludeInUpdate(), campo)) {
					iterator.remove();
					continue;
				}
				if (notNull(getFieldsToUpdate()) && isNot(getFieldsToUpdate(), campo))
					iterator.remove();
				continue;
			}

			for (Field att : getAttributi()) {
				Column annCol = att.getAnnotation(Column.class);
				if (notNull(annCol)) {
					if (is(campiDaAggiornare, att.getName())) {
						for (K k : safe(elenco)) {
							invokeSetter(invokeGetter(att), att, k);
						}

					}
				}
			}
			return true;

		}
		PreparedStatement stmt = null;
		int num = 0;
		Date start = null;
		Date end = null;
		String error = null;
		try {
			if (isLike(sql, WHERE)) {
				stmt = getConnection().prepareStatement(sql);
				stmt.setQueryTimeout(getQueryTimeout());
				start = now();
				num = stmt.executeUpdate();
				end = now();
			} else {
				logNoWhere();
			}
		} catch (Exception e) {
			end = now();
			error = str(getPk(), RIGHT_ARROW, e.getMessage());
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(sql, start, end, recordImpattati.intValue(), error);
			else
				logQuery(sql, start, end, recordImpattati.intValue());
			closeAlls(stmt, null);
		}
		setByPassUpdateDeleteLimitation(false);
		return num > 0;
	}

	private void logNoWhere() {
		log("MANCANZA DELLA WHERE CONDITION! QUERY NON ESEGUITA PER MOTIVI DI SICUREZZA sulla entity ", getEntityDetail());
	}

	/**
	 * Metodo che ritorna il prossimo valore da usare come progressivo per la
	 * creazione di una nuova chiave primaria. Se campoProgr non è chiave
	 * primaria torna NULL. Prende il massimo valore del progressivo presente e
	 * aggiunge 1 in modo che la n-pla nuova da inserire come chiave primaria
	 * abbia appunto il valore successivo al massimo presente n-1 ple
	 * 
	 * @param campoProgr
	 * @return Long
	 * @throws Exception
	 */
	public Long getNextProgr(String campoProgr) throws Exception {
		Long next = null;
		if (!isAttributoPk(campoProgr)) {
			logFieldNoPk(campoProgr);
			return next;
		}
		skipFlagStato = true;
		next = zeroIfNull((Long) select().narrow(campoProgr).max()) + 1;
		skipFlagStato = false;
		return next;
	}

	private Long _getNextProgr(String campoProgr) throws Exception {
		Long next = null;
		if (!isAttributoPk(campoProgr)) {
			logFieldNoPk(campoProgr);
			return next;
		}
		skipFlagStato = true;
		next = zeroIfNull((Long) _selectByPkForNextProgr().narrow(campoProgr).max()) + 1;
		skipFlagStato = false;
		return next;
	}

	private void logFieldNoPk(String campo) {
		log("Il campo indicato ", campo, " NON E' parte di chiave primaria.");
	}

	private Long getNextProgrForInsert() throws Exception {
		return _getNextProgr(getFieldProgressivo());
	}

	private void setNextProgrForInsert() throws Exception {
		String varProgr = getFieldProgressivo();
		if (notNull(varProgr)) {
			String nomeMetodo = str(GET, cap(varProgr));
			String nomeMetodoSet = str(SET, cap(varProgr));
			Method metodo = getClass().getMethod(nomeMetodo);
			Object actualValue = metodo.invoke(this);
			if (Null(actualValue)) {
				Long nextProgr = getNextProgrForInsert();
				metodo = getClass().getMethod(nomeMetodoSet, Long.class);
				if (notNull(metodo)) {
					metodo.invoke(this, nextProgr);
				} else {
					metodo = getClass().getMethod(nomeMetodoSet, String.class);
					if (notNull(metodo)) {
						metodo.invoke(this, String.valueOf(nextProgr));
					}
				}
			}
		}
	}

	private void setNextValForSequence() throws Exception {
		Long nextVal = getNextSequenceVal();
		if (notNull(nextVal))
			invokeSetter(nextVal, getFieldSequence());
	}

	private Long getNextSequenceVal() throws Exception {
		Long nextVal = null;
		Date start = null;
		Date end = null;
		String campoSequence = null;
		Field fieldSeq = getFieldSequence();
		if (notNull(fieldSeq)) {
			campoSequence = fieldSeq.getName();
		}
		String seqQuery = null;
		String seqName = null;
		String error = null;
		if (notNull(campoSequence)) {
			seqName = getSequence(campoSequence);
			seqQuery = str("SELECT ", seqName, ".NEXTVAL FROM DUAL");
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				stmt = getConnection().prepareStatement(seqQuery);
				stmt.setQueryTimeout(getQueryTimeout());
				start = now();
				rs = stmt.executeQuery();
				end = now();
				if (notNull(rs)) {
					rs.next();
					nextVal = rs.getLong(1);
				}
			} catch (Exception e) {
				end = now();
				error = str(seqName, RIGHT_ARROW, e.getMessage());
				throw (e);
			} finally {
				if (notNull(error))
					logQuery(seqQuery, start, end, 0, error);
				else
					logQuery(seqQuery, start, end, 1);
				closeAlls(stmt, rs);
			}
		}
		return nextVal;
	}

	/**
	 * Individua il prossimo progressivo della variabile istanza dell'entity che
	 * ha progr=true come attributo di @Column
	 * 
	 * @return Long
	 * @throws Exception
	 */
	public Long getNextProgr() throws Exception {
		return getNextProgr(getFieldProgressivo());
	}

	/**
	 * Dato un entity, torna il numero di occorrenze ritornate dalla where
	 * condition applicata.Se l'attributo FLAG_STATO di cancellazione logica non
	 * è istanziato, lo imposta automaticamente a A (ATTIVO) in modo tale da
	 * recuperare solo i record non cancellati
	 * 
	 * @return Long
	 * @throws Exception
	 */
	public <K> Long selectCount() throws Exception {

		Map<String, String> mappa = new HashMap<String, String>();
		Table tableAnn = getClass().getAnnotation(Table.class);
		String tableName = tableAnn.name();
		String sql = str("SELECT COUNT(*) FROM ", tableName, SPACE);
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				aggiungi(mappa, annCol.name(), att.getName());
			}
		}
		if (!skipInstanceVars) {
			setWhereConditionByInstanceVars();
		}
		impostaCondizioneFlagStato();
		sql = setWhereCondition(mappa, sql);
		if (isMock()) {
			return new Integer(mockSelect().size()).longValue();
		}
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Long quanti = null;
		Date start = null;
		Date end = null;
		try {
			stmt = getConnection().prepareStatement(sql);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				rs.next();
				quanti = rs.getLong(1);
			}
		} finally {
			logQuery(sql, start, end, quanti.intValue());
			closeAlls(stmt, rs);
		}
		// setSearchByPk(false);
		return quanti;
	}

	/**
	 * Torna true se la where condition applicata torna un result set con almeno
	 * un record
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isRecordPresente() throws Exception {
		skipFlagStato = true;
		boolean esito = maggioreZero(selectCount());
		skipFlagStato = false;
		return esito;
	}

	/**
	 * Torna true se la where condition applicata torna un result set vuoto
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isRecordAssente() throws Exception {
		return !isRecordPresente();
	}

	private String orderBy(Entity ent, Map<String, String> mappa, String sql) {
		if (null != ent.getOrderBy()) {
			if (notNull(ent.getOrderBy())) {
				sql = str(sql, " ORDER BY ");
			}
			PList<String> ob = ent.getOrderBy();
			String ordine = getLastElement(ob);
			if (notNull(ordine)) {
				String comma = " , ";
				for (String campo : safe(ob)) {
					String colonna = getAnnotazioneCampo(mappa, campo);
					if (notNull(colonna)) {
						sql = str(sql, colonna, comma);
					}
				}
				sql = str(substring(sql, null, false, false, comma, false, true));
				if (is(ordine, ASC, DESC)) {
					sql = str(sql, DOUBLE_SPACE, ordine);
				}
			}
		}
		return sql;
	}

	private String getColonnaWhereCondition(Map<String, String> mappa, Where w) {
		String annCampo = getAnnotazioneCampo(mappa, w.getCampo());
		if (NullOR(w.getCampo(), annCampo))
			return "";
		return str(w.getPrefixColonna(), SPACE, annCampo, w.getSuffixColonna(), SPACE);
	}

	/**
	 * Ritorna il nome della variabile istanza relativa alla colonna di
	 * cancellazione logica;
	 * 
	 * @return String
	 */
	protected String getFlagStatoAttributeName() {
		String nomeAttributo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.deleteLogic()) {
					nomeAttributo = att.getName();
					break;
				}
			}
		}
		return nomeAttributo;
	}

	/**
	 * Imposta la condizione FLAG_STATO=A solo se non è impostato nella where
	 * condition
	 * 
	 * @throws Exception
	 */
	private void impostaCondizioneFlagStato() throws Exception {
		if (!skipFlagStato) {
			String nomeAttributo = getFlagStatoAttributeName();
			if (tutte(hasDeleteLogic(), notNull(nomeAttributo), !presentInWhere(nomeAttributo))) {
				setFlagStato(Pilot.ATTIVO);
				setWhereConditionForFlagStato();
			}
		}
	}

	private String getTables() throws Exception {
		setJoinCondition(null);
		String tabelle = "";
		Table table = getClass().getAnnotation(Table.class);
		if (notNull(table)) {
			tabelle = table.name();
		}
		Join join = getClass().getAnnotation(Join.class);
		if (notNull(join)) {
			if (is(join.name(), getJoinName())) {
				if (tutte(notNull(join.table(), join.condition()))) {
					tabelle = str(tabelle, COMMA, join.table());
					setJoinCondition(join.condition());
				}
			}
		}
		return tabelle;

	}

	private String getCampi() {
		setDistinctCol(plstr());
		setCols(plstr());
		String s = " * ";
		if (notNull(getDistinctFields())) {
			s = " DISTINCT ";
			for (Field f : getAttributi()) {
				Column annCol = f.getAnnotation(Column.class);
				if (tutte(notNull(annCol), is(getDistinctFields(), f.getName()))) {
					getDistinctCol().add(annCol.name());
					s = str(s, annCol.name(), ",");
				}

			}
			s = cutLast(s);
		}
		if (notNull(getFields())) {
			s = "";
			for (Field f : getAttributi()) {
				Column annCol = f.getAnnotation(Column.class);
				if (tutte(notNull(annCol), is(getFields(), f.getName()))) {
					getCols().add(annCol.name());
					s = str(s, annCol.name(), ",");
				}

			}
			s = cutLast(s);
		}
		return s;
	}

	/**
	 * Esegue la select e salva il resultSet in una struttura interna cache in
	 * memoria che è una mappa con key=Tabella.className+WhereCondition e
	 * value=PList<T> con T oggetti entity tornati dalla select. Alla prossima
	 * esecuzione della stessa query con la stessa where condition, non si
	 * accederà più al db ma si estrarrà direttamente il result set dalla cache
	 * 
	 * @param <T>
	 * @return PList<T>
	 * @throws Exception
	 */
	public <T extends BaseEntity> PList<T> selectCache() throws Exception {
		Map<String, String> mappa = new HashMap<String, String>();
		String key = null;
		try {
			for (Field att : getAttributi()) {
				Column annCol = att.getAnnotation(Column.class);
				if (notNull(annCol)) {
					aggiungi(mappa, annCol.name(), att.getName());
				}
			}
			setWhereConditionByInstanceVars();
			impostaCondizioneFlagStato();
			key = str(getClass().getAnnotation(Table.class).name(), PIPE, setWhereCondition(mappa, ""));
			if (null == getTableCache().get(key)) {
				skipInstanceVars = true;
				getTableCache().put(key, select());
				skipInstanceVars = false;
			}
		} finally {
			closeAlls(null, null);
		}
		return (PList<T>) getTableCache().get(key);
	}

	/**
	 * Dato un oggetto entity che riporta una where condition (non
	 * obbligatoria), ritorna una lista di oggetti entity dello stesso tipo che
	 * soddisfano quella where condition applicata. Se l'attributo FLAG_STATO di
	 * cancellazione logica non è istanziato, lo imposta automaticamente a A
	 * (ATTIVO) in modo tale da recuperare solo i record non cancellati
	 * logicamente.
	 * 
	 * @param <T>
	 * @return PList<T>
	 * @throws Exception
	 */
	public <T extends BaseEntity> PList<T> select() throws Exception {
		setDistinctCol(plstr());
		setCols(plstr());
		Class<?> cls = Class.forName(getClass().getName());
		if (!Entity.class.isAssignableFrom(cls)) {
			throw new IllegalArgumentException();
		}
		PList<T> res = pl();
		Map<String, String> mappa = new HashMap<String, String>();
		Map<String, Method> mappaMetodi = new HashMap<String, Method>();
		Map<String, String> mappaTipiRitorno = new HashMap<String, String>();
		String sql = str("SELECT ", getCampi(), " FROM ", getTables(), SPACE);
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				aggiungi(mappa, annCol.name(), att.getName());
				aggiungi(mappaMetodi, annCol.name(), findMethod(getMetodiEnt(), str(SET, cap(att.getName()))));
				aggiungi(mappaTipiRitorno, annCol.name(), att.getType().getSimpleName());
			}
		}
		if (!skipInstanceVars)
			setWhereConditionByInstanceVars();
		impostaCondizioneFlagStato();
		sql = setWhereCondition(mappa, sql);
		if (notNull(getJoinCondition())) {
			sql = str(sql, SPACE, OperatoreBooleano.AND.op, getJoinCondition());
		}
		if (isMock()) {
			return mockSelect();
		}
		sql = orderBy(this, mappa, sql);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Date start = null;
		Date end = null;
		String error = null;
		try {
			stmt = getConnection().prepareStatement(sql);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			rs = stmt.executeQuery();
			end = now();
			PList<String> cols = buildColumns(rs);
			if (notNull(rs)) {
				ResultSetMetaData rsmd = rs.getMetaData();
				while (rs.next()) {
					T e = getInstance(cls);
					for (String col : safe(cols)) {
						if (tutte(notNull(getDistinctCol()), isNot(getDistinctCol(), col)))
							continue;
						if (tutte(notNull(getCols()), isNot(getCols(), col)))
							continue;
						String tipoRitorno = mappaTipiRitorno.get(col);
						Method m = mappaMetodi.get(col);
						if (notNull(m)) {
							boolean invoked = false;
							for (int i = 1; i <= rsmd.getColumnCount(); i++) {
								if (tutte(is(rsmd.getColumnName(i), col))) {
									if (tutte(is(rsmd.getColumnTypeName(i), "NUMBER"))) {
										if (is(tipoRitorno, "Long")) {
											m.invoke(e, rs.getLong(col));
										}
										if (is(tipoRitorno, "BigDecimal")) {
											m.invoke(e, rs.getBigDecimal(col));
										}
										if (is(tipoRitorno, "Float")) {
											m.invoke(e, rs.getFloat(col));
										}
										invoked = true;
									}
									if (is(rsmd.getColumnTypeName(i), "BLOB", "CLOB")) {
										m.invoke(e, rs.getBytes(col));
										invoked = true;
									}
								}
							}
							if (!invoked) {
								m.invoke(e, rs.getObject(col));
							}
						}
					}
					e.cleanProvidedAttributes();
					res.add(e);
				}
			}
		} catch (Exception e) {
			end = now();
			error = str(getPk(), RIGHT_ARROW, e.getMessage());
			throw (e);
		} finally {
			cleanWhereCondition();
			setSearchByPk(false);
			if (notNull(error))
				logQuery(sql, start, end, res.size(), error);
			else
				logQuery(sql, start, end, res.size());
			closeAlls(stmt, rs);
		}
		return res;
	}

	/**
	 * Esegue la select e stampa il result set tornato nel path
	 * (path+nomeFile.estensione) indicato. Se il path non esiste viene
	 * automaticamente creato. Ritorna il nome del file di impatto creato
	 * 
	 * @param <T>
	 * @param path
	 * @throws Exception
	 * @return String
	 */
	public <T extends BaseEntity> String selectForWrite(String path) throws Exception {
		setDistinctCol(plstr());
		setCols(plstr());
		Class<?> cls = Class.forName(getClass().getName());
		if (!Entity.class.isAssignableFrom(cls)) {
			throw new IllegalArgumentException();
		}
		String nuovoNome = "";
		int cont = 0;
		p.createFolder(path);
		File file = new File(path);
		FileWriter fr = null;
		Map<String, String> mappa = new HashMap<String, String>();
		Map<String, Method> mappaMetodi = new HashMap<String, Method>();
		Map<String, String> mappaTipiRitorno = new HashMap<String, String>();
		String sql = str("SELECT ", getCampi(), " FROM ", getTables(), SPACE);
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				aggiungi(mappa, annCol.name(), att.getName());
				aggiungi(mappaMetodi, annCol.name(), findMethod(getMetodiEnt(), str(SET, cap(att.getName()))));
				aggiungi(mappaTipiRitorno, annCol.name(), att.getType().getSimpleName());
			}
		}
		if (!skipInstanceVars)
			setWhereConditionByInstanceVars();
		impostaCondizioneFlagStato();
		sql = setWhereCondition(mappa, sql);
		if (notNull(getJoinCondition())) {
			sql = str(sql, SPACE, OperatoreBooleano.AND.op, getJoinCondition());
		}
		fr = new FileWriter(file);
		if (isMock()) {
			PList<T> out = mockSelect();
			for (T ent : safe(out)) {
				fr.write(toStrEntity(ent));
			}
		} else {
			sql = orderBy(this, mappa, sql);
			PreparedStatement stmt = null;
			ResultSet rs = null;
			Date start = null;
			Date end = null;
			String error = null;
			try {
				stmt = getConnection().prepareStatement(sql);
				stmt.setQueryTimeout(getQueryTimeout());
				start = now();
				rs = stmt.executeQuery();
				end = now();
				PList<String> cols = buildColumns(rs);
				if (notNull(rs)) {
					ResultSetMetaData rsmd = rs.getMetaData();
					while (rs.next()) {
						cont++;
						T e = getInstance(cls);
						for (String col : safe(cols)) {
							if (tutte(notNull(getDistinctCol()), isNot(getDistinctCol(), col)))
								continue;
							if (tutte(notNull(getCols()), isNot(getCols(), col)))
								continue;
							String tipoRitorno = mappaTipiRitorno.get(col);
							Method m = mappaMetodi.get(col);
							if (notNull(m)) {
								boolean invoked = false;
								for (int i = 1; i <= rsmd.getColumnCount(); i++) {
									if (tutte(is(rsmd.getColumnName(i), col))) {
										if (tutte(is(rsmd.getColumnTypeName(i), "NUMBER"))) {
											if (is(tipoRitorno, "Long")) {
												m.invoke(e, rs.getLong(col));
											}
											if (is(tipoRitorno, "BigDecimal")) {
												m.invoke(e, rs.getBigDecimal(col));
											}
											if (is(tipoRitorno, "Float")) {
												m.invoke(e, rs.getFloat(col));
											}
											invoked = true;
										}
										if (is(rsmd.getColumnTypeName(i), "BLOB", "CLOB")) {
											m.invoke(e, rs.getBytes(col));
											invoked = true;
										}
									}
								}
								if (!invoked) {
									m.invoke(e, rs.getObject(col));
								}
							}
						}
						e.cleanProvidedAttributes();
						fr.write(toStrEntity(e));
					}
				}
			} catch (Exception e) {
				end = now();
				error = str(getPk(), RIGHT_ARROW, e.getMessage());
				throw (e);
			} finally {
				fr.close();
				String ante = substring(path, null, false, false, ".", false, true);
				String ext = substring(path, ".", true, true, null, false, false);
				String suffix = str("_", cont, "_records_", getUniqueNamehhmmss());
				nuovoNome = str(ante, suffix, ext);
				p.renameFile(path, nuovoNome);
				cleanWhereCondition();
				setSearchByPk(false);
				if (notNull(error))
					logQuery(sql, start, end, cont, error);
				else
					logQuery(sql, start, end, cont);
				closeAlls(stmt, rs);
			}
		}
		return nuovoNome;
	}

	/**
	 * Esegue la select e ritorna come lista di stringhe il result set tornato.
	 * Torna le entity risultato della select sotto forma di lista di stringhe
	 * dove ogni stringa è la rappresentazione stringa della entity
	 * 
	 * @param <T>
	 * @return PList<String>
	 * @throws Exception
	 */
	public <T extends BaseEntity> PList<String> selectForWrite() throws Exception {
		setDistinctCol(plstr());
		setCols(plstr());
		Class<?> cls = Class.forName(getClass().getName());
		if (!Entity.class.isAssignableFrom(cls)) {
			throw new IllegalArgumentException();
		}
		int cont = 0;
		PList<String> contenuto = plstr();
		Map<String, String> mappa = new HashMap<String, String>();
		Map<String, Method> mappaMetodi = new HashMap<String, Method>();
		Map<String, String> mappaTipiRitorno = new HashMap<String, String>();
		String sql = str("SELECT ", getCampi(), " FROM ", getTables(), SPACE);
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				aggiungi(mappa, annCol.name(), att.getName());
				aggiungi(mappaMetodi, annCol.name(), findMethod(getMetodiEnt(), str(SET, cap(att.getName()))));
				aggiungi(mappaTipiRitorno, annCol.name(), att.getType().getSimpleName());
			}
		}
		if (!skipInstanceVars)
			setWhereConditionByInstanceVars();
		impostaCondizioneFlagStato();
		sql = setWhereCondition(mappa, sql);
		if (notNull(getJoinCondition())) {
			sql = str(sql, SPACE, OperatoreBooleano.AND.op, getJoinCondition());
		}
		if (isMock()) {
			PList<T> out = mockSelect();
			for (T ent : safe(out)) {
				contenuto.add(toStrEntity(ent));
			}
		} else {
			sql = orderBy(this, mappa, sql);
			PreparedStatement stmt = null;
			ResultSet rs = null;
			Date start = null;
			Date end = null;
			String error = null;
			try {
				stmt = getConnection().prepareStatement(sql);
				stmt.setQueryTimeout(getQueryTimeout());
				start = now();
				rs = stmt.executeQuery();
				end = now();
				PList<String> cols = buildColumns(rs);
				if (notNull(rs)) {
					ResultSetMetaData rsmd = rs.getMetaData();
					while (rs.next()) {
						cont++;
						T e = getInstance(cls);
						for (String col : safe(cols)) {
							if (tutte(notNull(getDistinctCol()), isNot(getDistinctCol(), col)))
								continue;
							if (tutte(notNull(getCols()), isNot(getCols(), col)))
								continue;
							String tipoRitorno = mappaTipiRitorno.get(col);
							Method m = mappaMetodi.get(col);
							if (notNull(m)) {
								boolean invoked = false;
								for (int i = 1; i <= rsmd.getColumnCount(); i++) {
									if (tutte(is(rsmd.getColumnName(i), col))) {
										if (tutte(is(rsmd.getColumnTypeName(i), "NUMBER"))) {
											if (is(tipoRitorno, "Long")) {
												m.invoke(e, rs.getLong(col));
											}
											if (is(tipoRitorno, "BigDecimal")) {
												m.invoke(e, rs.getBigDecimal(col));
											}
											if (is(tipoRitorno, "Float")) {
												m.invoke(e, rs.getFloat(col));
											}
											invoked = true;
										}
										if (is(rsmd.getColumnTypeName(i), "BLOB", "CLOB")) {
											m.invoke(e, rs.getBytes(col));
											invoked = true;
										}
									}
								}
								if (!invoked) {
									m.invoke(e, rs.getObject(col));
								}
							}
						}
						e.cleanProvidedAttributes();
						contenuto.add(toStrEntity(e));
					}
				}
			} catch (Exception e) {
				end = now();
				error = str(getPk(), RIGHT_ARROW, e.getMessage());
				throw (e);
			} finally {
				cleanWhereCondition();
				setSearchByPk(false);
				if (notNull(error))
					logQuery(sql, start, end, cont, error);
				else
					logQuery(sql, start, end, cont);
				closeAlls(stmt, rs);
			}
		}
		return contenuto;
	}

	private <T extends BaseEntity> PList<T> mockSelect() throws Exception {

		if (notNull(getJoinName())) {
			// in caso di presenza di join, non eseguo la query di filtro in
			// memoria ma ritorno una
			// mocklist di oggetti entity
			log("Bypassing JOIN ", getJoinName(), " and returning mock values of 10 elements");
			return (PList<T>) mockList(getClass(), 10);
		}

		PList<T> elenco = safe((PList<T>) getDb().get(getClass().getName()));
		PList<T> elencoOR = pl();
		boolean orPresent = false;
		for (T entity : safe(elenco)) {
			elencoOR.add(entity);
		}

		OperatoreBooleano next = OperatoreBooleano.AND;
		for (Where w : getWhereCondition()) {
			String campo = w.getCampo();
			if (is(next, OperatoreBooleano.AND)) {
				elaboraFiltri(elenco, w, campo);

			} else if (is(next, OperatoreBooleano.OR)) {
				orPresent = true;
				elaboraFiltri(elencoOR, w, campo);

			}
			next = w.getBop();
		}
		PList<T> output = pl();
		if (orPresent)
			output = safe(elenco).find().aggiungiList(safe(elencoOR).find());
		else
			output = safe(elenco).find();
		if (notNull(getFields())) {
			output = output.mask(toArray(getFields(), String.class));
		}
		if (notNull(getDistinctFields())) {
			PList<T> outputDistinct = pl();
			PList<String> dist = output.distinctMulti(toArray(getDistinctFields(), String.class));
			for (String item : safe(dist)) {
				T elem = getInstance(Class.forName(getClass().getName()));
				PList<String> l = toListString(item, PIPE);
				for (String elemento : safe(l)) {
					PList<String> ll = toListString(elemento, "=");
					String nomeCampo = ll.getFirstElement();
					String valoreCampo = ll.getLastElement();
					Class c = getAttClassByCampo(nomeCampo);
					Object val = valoreCampo;
					if (c.isAssignableFrom(Long.class)) {
						val = getLong(valoreCampo);
					}
					if (c.isAssignableFrom(Integer.class)) {
						val = getInteger(valoreCampo);
					}
					if (c.isAssignableFrom(Date.class)) {
						val = toDate(valoreCampo);
					}
					if (c.isAssignableFrom(BigDecimal.class)) {
						val = getBigDecimal(valoreCampo);
					}
					if (c.isAssignableFrom(Double.class)) {
						val = getDouble(valoreCampo);
					}
					if (c.isAssignableFrom(Boolean.class)) {
						val = new Boolean(valoreCampo);
					}
					if (c.isAssignableFrom(Short.class)) {
						val = getShort(valoreCampo);
					}
					if (c.isAssignableFrom(Float.class)) {
						val = getFloat(valoreCampo);
					}
					invokeSetter(val, nomeCampo, elem);
				}
				outputDistinct.add(elem);
			}
			return outputDistinct;

		}
		return safe(output);
	}

	private <T> void elaboraFiltri(PList<T> elenco, Where w, String campo) {
		elenco = safe(elenco);
		if (is(w.getOp(), Operatore.EQUAL)) {
			if (notNull(w.getV1())) {
				elenco.eq(campo, w.getV1());
			}
		}
		if (is(w.getOp(), Operatore.LIKE)) {
			if (notNull(w.getV1())) {
				elenco.likeValues(campo, replace((String) w.getV1(), "%", ""));
			}
		}

		if (is(w.getOp(), Operatore.NOTLIKE)) {
			if (notNull(w.getV1())) {
				elenco.notLikeValues(campo, replace((String) w.getV1(), "%", ""));
			}
		}

		if (is(w.getOp(), Operatore.IN)) {
			if (notNull(w.getVals())) {
				elenco.in(campo, w.getVals());
			}
		}
		if (is(w.getOp(), Operatore.NOTIN)) {
			if (notNull(w.getVals())) {
				elenco.notIn(campo, w.getVals());
			}
		}
		if (is(w.getOp(), Operatore.ISNULL)) {
			elenco.isNull(campo);
		}
		if (is(w.getOp(), Operatore.ISNOTNULL)) {
			elenco.isNotNull(campo);
		}
		if (is(w.getOp(), Operatore.NOT_EQUAL)) {
			if (notNull(w.getV1())) {
				elenco.neq(campo, w.getV1());
			}
		}
		if (is(w.getOp(), Operatore.GT)) {
			if (notNull(w.getV1())) {
				elenco.gt(campo, w.getV1());
			}
		}
		if (is(w.getOp(), Operatore.GTE)) {
			if (notNull(w.getV1())) {
				elenco.gte(campo, w.getV1());
			}
		}
		if (is(w.getOp(), Operatore.LT)) {
			if (notNull(w.getV1())) {
				elenco.lt(campo, w.getV1());
			}
		}
		if (is(w.getOp(), Operatore.LTE)) {
			if (notNull(w.getV1())) {
				elenco.lte(campo, w.getV1());
			}
		}
		if (is(w.getOp(), Operatore.BETWEEN)) {
			if (notNull(w.getV1(), w.getV2())) {
				elenco.between(campo, w.getV1(), w.getV2());
			}
			if (tutte(Null(w.getV1()), notNull(w.getV2()))) {
				elenco.lte(w.getV2());
			}
			if (tutte(Null(w.getV2()), notNull(w.getV1()))) {
				elenco.gte(w.getV1());
			}
		}
	}

	private void logQuery(String sql, Date start, Date end, Integer i) {
		sql = encode(sql);
		sql = str(QUERY_PREFIX, OPEN, "%d ", RECORD, CLOSE, " %s ", LEFT_ARROW, sql, " %s");
		setExecTime(sql, start, end, i);

	}

	private String encode(String sql) {
		return replace(sql, "%", KEY_);
	}

	private void logQuery(String sql, Date start, Date end, Integer i, String error) {
		sql = encode(sql);
		sql = str(QUERY_PREFIX, ERROR_MARK, SPACE, OPEN, "%d ", RECORD, CLOSE, " %s ", OPEN, "%s", CLOSE, SPACE, LEFT_ARROW, sql, " %s");
		setExecTimeError(sql, start, end, i, error);

	}

	/**
	 * Dato un oggetto entity che riporta una where condition (non
	 * obbligatoria), ritorna il primo elemento della lista di oggetti entity
	 * che soddisfano la where condition applicata
	 * 
	 * 
	 * @param <T>
	 * @return T
	 * @throws Exception
	 */
	public <T extends BaseEntity> T selectOne() throws Exception {
		return (T) getFirstElement(select());
	}

	/**
	 * Stessa logica applicativa di selectOne ma sfruttando il meccanismo di
	 * cache
	 * 
	 * @param <T>
	 * @return T
	 * @throws Exception
	 */
	public <T extends BaseEntity> T selectOneCache() throws Exception {
		return (T) getFirstElement(selectCache());
	}

	private String getValore(Object val) {
		String valore = "''";
		if (notNull(val)) {
			if (almenoUna((val instanceof Date), (val instanceof Timestamp), (val instanceof java.sql.Date))) {
				Date d = null;
				if (val instanceof Timestamp) {
					d = new Date(((Timestamp) val).getTime());
				}
				if (val instanceof java.sql.Date) {
					d = new Date(((java.sql.Date) val).getTime());
				}
				if (val instanceof java.util.Date) {
					d = (Date) val;
				}
				valore = to_date(d);
			} else {
				valore = inClause(val);
			}
		}
		return valore;
	}

	private String to_dateOracle(Date d) {
		return str(" TO_DATE('", dateToStringhhmmss(d), "','DD/MM/YYYY HH24:mi:ss') ");
	}

	private String to_dateDb2(Date d) {
		return str(" DATE(TO_DATE('", dateToString(d), "','DD/MM/YYYY') ) ");
	}

	private String to_date(Date d) {
		return isDb2() ? to_dateDb2(d) : to_dateOracle(d);
	}

	private <K> String concatenaListaForInClause(List<K> l, String car) {
		car = emptyIfNull(car);
		StringBuffer sb = new StringBuffer();
		for (K k : safe(l)) {
			sb.append(getValore(k)).append(car);
		}
		if (sb.length() > 1) {
			sb.setLength(sb.length() - car.length());
		}
		return sb.toString();
	}

	private String inClause(Object value) {
		String v = null;
		if (value instanceof List) {
			v = concatenaListaForInClause((List) value, COMMA);
		} else if (value instanceof Number) {
			if (value instanceof BigDecimal) {
				v = getBigDecimal((BigDecimal) value).toString();
			} else {
				v = value.toString();
			}
		} else {
			String valoreStringa = String.valueOf(value);
			v = apiceString(valoreStringa);
		}
		return Matcher.quoteReplacement(v);
	}

	/**
	 * Esegue l'update dell'entity solo se la where condition applicata torna
	 * uno e un solo record. Tipicamente usato per eseguire update su chiavi
	 * primarie ma non solo e prevenire aggiornamenti multipli non desiderati
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateOne() throws Exception {
		boolean ret = false;
		skipFlagStato = true;
		Long quanti = selectCount();
		recordImpattati = quanti;
		if (one(quanti)) {
			setByPassUpdateDeleteLimitation(true);
			ret = update();
			setByPassUpdateDeleteLimitation(false);
		} else {
			logMoreThanOneRecord("UPDATE", quanti);
		}
		skipFlagStato = false;
		return ret;
	}

	/**
	 * Metodo che esegue l'update dell'Entity per pk. Se non sono settati tutti
	 * i valori delle variabili istanza della pk, ritorna false e non esegue
	 * l'update.
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateByPk() throws Exception {
		setSearchByPk(true);
		boolean ret = false;
		if (!isAllPkSet()) {
			logNoFullPk("Update");
			return false;
		}
		excludePk();
		setWhereConditionForPk();
		ret = updateOne();
		svuotaFieldToExclude();
		svuotaFieldToUpdate();
		cleanWhereCondition();
		return ret;
	}

	private void excludePk() {
		setFieldsToExcludeInUpdate(getAttributiPk());
	}

	/**
	 * Esegue la delete dell'entity solo se la where condition applicata torna
	 * uno e un solo record. Tipicamente usato per eseguire cancellazioni di
	 * record su chiavi primarie ma non solo e prevenire cancellazioni multiple
	 * non desiderate
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteOne() throws Exception {
		boolean ret = false;
		skipFlagStato = true;
		Long quanti = selectCount();
		recordImpattati = quanti;
		if (one(quanti)) {
			setByPassUpdateDeleteLimitation(true);
			ret = delete();
			setByPassUpdateDeleteLimitation(false);
		} else {
			logMoreThanOneRecord("DELETE", quanti);
		}
		skipFlagStato = false;
		return ret;
	}

	private void logMoreThanOneRecord(String op, Long quanti) {
		log("QUERY DI ", op, " NON ESEGUITA sulla entity ", getEntityDetail(), " PERCHE' IMPATTA ", quanti, " RECORD INVECE DI UNO SOLO COME SI PREVEDEVA");
	}

	/**
	 * Metodo che esegue la delete intelligente logica/fisica in base alla
	 * presenza o meno della colonna di cancellazione logica FLAG_STATO. La
	 * cancellazione dell'Entity avviene per pk.Se non sono settati tutti i
	 * valori delle variabili istanza della pk ritorna false e non esegue la
	 * delete.
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteByPk() throws Exception {
		setSearchByPk(true);
		boolean ret = false;
		if (!isAllPkSet()) {
			logNoFullPk("Delete");
			return false;
		}
		setWhereConditionForPk();
		ret = deleteOne();
		cleanWhereCondition();
		return ret;
	}

	/**
	 * Ritorna true se l'unico record individuato dalla chiave primaria è
	 * cancellato logicamente, ossia ha la colonna FLAG_STATO a C
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isDeleted() throws Exception {
		boolean ret = false;
		if (!isAllPkSet()) {
			logNoFullPk("Verifica record se cancellato logicamente");
			return false;
		}
		setFlagStato(Pilot.DISATTIVO);
		setWhereConditionForPk();
		if (setWhereConditionForFlagStato()) {
			ret = notNull(selectOne());
		}
		setFlagStato(Pilot.ATTIVO);
		cleanWhereCondition();
		return ret;
	}

	private void soloQuaterna() {
		setFieldsToExcludeInUpdate(getAllOtherFieldsButThese(getAttributo(METHOD_FLAG_STATO), getAttributo(METHOD_COD_APPL), getAttributo(METHOD_COD_UTENTE), getAttributo(METHOD_DATA_AGGIORN)));
	}

	/**
	 * Esegue la riattivazione logica di un record individuato univocamente
	 * attraverso la sua pk, impostando la colonna FLAG_STATO a A
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean resumeByPk() throws Exception {
		setSearchByPk(true);
		if (!hasDeleteLogic()) {
			logNoResume();
			return false;
		}
		boolean ret = false;
		if (!isAllPkSet()) {
			logNoFullPk("Delete logic");
			return false;
		}
		logResume();
		setFlagStato(Pilot.ATTIVO);
		soloQuaterna();
		setWhereConditionForPk();
		ret = updateOne();
		cleanWhereCondition();
		svuotaFieldToExclude();
		svuotaFieldToUpdate();
		return ret;
	}

	/**
	 * Esegue la cancellazione logica dei record individuati dalla where
	 * condition applicata, impostando la colonna FLAG_STATO a C
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	private boolean deleteLogic() throws Exception {
		boolean ret = false;
		setWhereConditionByInstanceVars();
		setFlagStato(Pilot.DISATTIVO);
		soloQuaterna();
		ret = update();
		cleanWhereCondition();
		svuotaFieldToExclude();
		svuotaFieldToUpdate();
		return ret;
	}

	/**
	 * Esegue la riattivazione logica dei record individuati dalla where
	 * condition applicata, impostando la colonna FLAG_STATO a A
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean resume() throws Exception {
		if (!hasDeleteLogic()) {
			logNoResume();
			return false;
		}
		logResume();
		boolean ret = false;
		setWhereConditionByInstanceVars();
		setFlagStato(Pilot.ATTIVO);
		soloQuaterna();
		ret = update();
		cleanWhereCondition();
		svuotaFieldToExclude();
		svuotaFieldToUpdate();
		return ret;
	}

	private void logResume() {
		log("Eseguo la resume sull'Entity ", getEntityDetail(), " impostando il FLAG_STATO ad A");
	}

	private void logNoResume() {
		log("La resume dell'Entity ", getEntityDetail(), " non è stata eseguita in quanto manca la colonna identificativa della cancellazione logica FLAG_STATO");
	}

	private boolean _resume() throws Exception {
		if (!hasDeleteLogic()) {
			logNoResume();
			return false;
		}
		boolean ret = false;
		logResume();
		setFlagStato(Pilot.ATTIVO);
		soloQuaterna();
		setWhereConditionForPk();
		ret = update();
		cleanWhereCondition();
		svuotaFieldToExclude();
		svuotaFieldToUpdate();
		return ret;
	}

	private void svuotaFieldToExclude() {
		setFieldsToExcludeInUpdate(plstr());
	}

	private void svuotaFieldToUpdate() {
		setFieldsToUpdate(plstr());
	}

	private void setFlagStato(String value) throws IllegalAccessException, InvocationTargetException {
		if (!hasDeleteLogic())
			return;
		for (Method method : getMetodiEnt()) {
			if (tutte(method.getName().toUpperCase().startsWith(MSET), method.getName().toUpperCase().endsWith((METHOD_FLAG_STATO.toUpperCase())))) {
				method.invoke(this, value);
				break;
			}
		}
	}

	private String getAttributo(String nomeEndsWith) {
		String nomeAttributo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (att.getName().toUpperCase().endsWith(nomeEndsWith.toUpperCase())) {
					nomeAttributo = att.getName();
					break;
				}
			}
		}
		return nomeAttributo;
	}

	private Class getAttClassByCampo(String nomeCampo) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (is(att.getName(), nomeCampo)) {
					return att.getType();
				}
			}
		}
		return null;
	}

	private PList<String> getAttributiPk() {
		PList<String> nomiAttributo = plstr();
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.pk()) {
					nomiAttributo.add(att.getName());
				}
			}
		}
		return nomiAttributo;
	}

	private boolean isAttributoPk(String nomeAttr) {
		return is(getAttributiPk(), nomeAttr);

	}

	private PList<String> getAllOtherFieldsButThese(String... campiDaAggiornare) {
		PList<String> campiDaEscludere = plstr();
		PList<String> campiDaAgg = arrayToList(campiDaAggiornare);
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (!is(campiDaAgg, att.getName())) {
					campiDaEscludere.add(att.getName());
				}
			}
		}
		return campiDaEscludere;
	}

	private void setFlagStatoCondizionale(String value) throws IllegalAccessException, InvocationTargetException {
		if (!hasDeleteLogic())
			return;
		if (!isFlagStatoImpostatoManualmente()) {
			for (Method method : getMetodiEnt()) {
				if (tutte(method.getName().toUpperCase().startsWith(MSET), method.getName().toUpperCase().endsWith((METHOD_FLAG_STATO.toUpperCase())))) {
					method.invoke(this, value);
					break;
				}
			}
		}
	}

	private void setTernaCodUtenteCodApplDataAggiorn() throws IllegalAccessException, InvocationTargetException {
		setCodUtente();
		setCodAppl();
		setDataAggiorn();
	}

	private void setQuaternaFlagStatoACodUtenteCodApplDataAggiorn() throws IllegalAccessException, InvocationTargetException {
		setFlagStatoCondizionale(Pilot.ATTIVO);
		setTernaCodUtenteCodApplDataAggiorn();
	}

	private void setCodUtente() throws IllegalAccessException, InvocationTargetException {
		if (tutte(notNull(getCodUtenteCostruttore()), !isCodUtenteImpostatoManualmente())) {
			for (Method method : getMetodiEnt()) {
				if (tutte(method.getName().toUpperCase().startsWith(MSET), method.getName().toUpperCase().endsWith((METHOD_COD_UTENTE.toUpperCase())))) {
					method.invoke(this, getCodUtenteCostruttore());
					break;
				}
			}
		}
	}

	private boolean isCodUtenteImpostatoManualmente() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		boolean esito = false;
		for (Method method : getMetodiEnt()) {
			if (tutte(method.getName().toUpperCase().startsWith(MGET), method.getName().toUpperCase().endsWith((METHOD_COD_UTENTE.toUpperCase())))) {
				String attName = decapFirstLetter(substring(method.getName(), GET, false, false, null, false, false));
				esito = tutte(notNull(method.invoke(this)), isProvided(attName));
				break;
			}
		}
		return esito;
	}

	private boolean isCodApplImpostatoManualmente() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		boolean esito = false;
		for (Method method : getMetodiEnt()) {
			if (tutte(method.getName().toUpperCase().startsWith(MGET), method.getName().toUpperCase().endsWith((METHOD_COD_APPL.toUpperCase())))) {
				String attName = decapFirstLetter(substring(method.getName(), GET, false, false, null, false, false));
				esito = tutte(notNull(method.invoke(this)), isProvided(attName));
				break;
			}
		}
		return esito;
	}

	private boolean isDataAggiornImpostatoManualmente() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		boolean esito = false;
		for (Method method : getMetodiEnt()) {
			if (tutte(method.getName().toUpperCase().startsWith(MGET), method.getName().toUpperCase().endsWith((METHOD_DATA_AGGIORN.toUpperCase())))) {
				String attName = decapFirstLetter(substring(method.getName(), GET, false, false, null, false, false));
				esito = tutte(notNull(method.invoke(this)), isProvided(attName));
				break;
			}
		}
		return esito;
	}

	private boolean isFlagStatoImpostatoManualmente() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		boolean esito = false;
		for (Method method : getMetodiEnt()) {
			if (tutte(method.getName().toUpperCase().startsWith(MGET), method.getName().toUpperCase().endsWith((METHOD_FLAG_STATO.toUpperCase())))) {
				String attName = decapFirstLetter(substring(method.getName(), GET, false, false, null, false, false));
				esito = tutte(notNull(method.invoke(this)), isProvided(attName));
				break;
			}
		}
		return esito;
	}

	private void setCodAppl() throws IllegalAccessException, InvocationTargetException {
		if (tutte(notNull(getCodApplCostruttore()), !isCodApplImpostatoManualmente())) {
			for (Method method : getMetodiEnt()) {
				if (tutte(method.getName().toUpperCase().startsWith(MSET), method.getName().toUpperCase().endsWith((METHOD_COD_APPL.toUpperCase())))) {
					method.invoke(this, getCodApplCostruttore());
					break;
				}
			}
		}
	}

	private void setDataAggiorn() throws IllegalAccessException, InvocationTargetException {
		if (!isDataAggiornImpostatoManualmente()) {
			for (Method method : getMetodiEnt()) {
				if (tutte(method.getName().toUpperCase().startsWith(MSET), method.getName().toUpperCase().endsWith((METHOD_DATA_AGGIORN.toUpperCase())))) {
					method.invoke(this, now());
					break;
				}
			}
		}
	}

	protected abstract void setWhereConditionForPk();

	protected abstract boolean setWhereConditionForFlagStato();

	protected abstract void cleanWhereCondition();

	protected abstract void setWhereConditionByInstanceVars();

	/**
	 * Se il numero di occorrenze della where condition dell'entity è uguale a
	 * 0, esegue l'inserimento dell'entity stessa, altrimenti se il numero di
	 * occorrenze della where condition è 1 e il record è logicamente cancellato
	 * ossia ha FLAG_STATO='C', allora esegue l'update INTEGRALE dell'entity
	 * altrimenti non fa nulla. Quindi, il risultato è o l'inserimento di tutti
	 * i campi dell'entity o il loro totale aggiornamento considerando anche
	 * quelli NULL che così vengono impostati a NULL e non ignorati
	 * 
	 * @return Boolean
	 * @throws Exception
	 */
	private Boolean upsert() throws Exception {
		setNextProgrForInsert();
		if (!isAllPkSet()) {
			logNoFullPk("Upsert");
			return false;

		}
		setWhereConditionForPk();
		Boolean b = null;
		skipFlagStato = true;
		Long quanti = selectCount();
		skipFlagStato = false;
		if (zero(quanti)) {
			log("Record non trovato,procedo all'inserimento del record ", getPk(), " per la entity", getEntityDetail());
			b = _insert();
		} else if (one(quanti)) {
			if (isDeleted()) {
				log("Record trovato ma cancellato logicamente (FLAG_STATO=C), procedo all'aggiornamento del record ", getPk(), " per la entity ", getEntityDetail());
				b = updateByPk();
				resumed = true;
			} else {
				log("Record trovato ma attivo (FLAG_STATO=A), procedo con il tentativo di inserimento che porterà a INTEGRITY CONSTRAINT VIOLATED per chiave primaria duplicata ", getPk(),
						" per la entity ", getEntityDetail());
				b = _insert();// eseguo ugualmente la insert in modo da far
				// venire fuori l'errore di chiave duplicata
			}
		} else {
			log("Trovati ", quanti, " record, non eseguo nè inserimento nè aggiornamento per la entity ", getEntityDetail());
		}
		cleanWhereCondition();
		return b;
	}

	private Boolean upsertStrong() throws Exception {
		setNextProgrForInsert();
		if (!isAllPkSet()) {
			logNoFullPk("Upsert");
			return false;
		}
		setWhereConditionForPk();
		Boolean b = null;
		skipFlagStato = true;
		Long quanti = selectCount();
		skipFlagStato = false;
		if (zero(quanti)) {
			log("Record non trovato,procedo all'inserimento del record ", getPk(), " per la entity", getEntityDetail());
			b = _insert();
		} else if (one(quanti)) {
			log("Record trovato, procedo all'aggiornamento del record ", getPk(), " per la entity ", getEntityDetail());
			b = updateByPk();
			resumed = true;
		} else {
			log("Trovati ", quanti, " record, non eseguo nè inserimento nè aggiornamento per la entity ", getEntityDetail());
		}
		cleanWhereCondition();
		return b;
	}

	/**
	 * Data una classe c a cui convertire l'oggetto entity e una lista di
	 * entities da convertire, torna la lista di oggetti di tipo c risultato
	 * della conversione per attributi selettivi dall'entity al dto di
	 * riferimento
	 * 
	 * @param <K>
	 * @param <T>
	 * @param c
	 * @param entities
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K, T extends BaseEntity> PList<K> convert(Class<K> c, PList<T> entities) throws Exception {
		PList<K> output = pl();
		for (T t : safe(entities)) {
			output.add((K) t.convert(c));
		}
		return output;
	}

	/**
	 * Converte l'oggetto entity nella corrispondente classe dto di tipo c i cui
	 * attributi istanza hanno il riferimento tramite annotazione alla colonna
	 * di riferimento dell'entity corrispondente
	 * 
	 * @param <K>
	 * @param c
	 * @return K
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <K> K convert(Class<K> c) throws InstantiationException, IllegalAccessException {
		K obj = (K) c.newInstance();
		Method[] metodi = c.getDeclaredMethods();
		Map<String, String> mappaEnt = getMap(getClass());
		Map<String, String> mappaObj = getMap(c);
		Set<String> annotazioni = mappaEnt.keySet();
		Set<String> annotazioniOggetto = mappaObj.keySet();
		for (String annotazione : safe(annotazioni)) {
			if (is(annotazioniOggetto, annotazione)) {
				String nomeMetodoOggetto = str(SET, mappaObj.get(annotazione));
				String nomeMetodoEnt = str(GET, mappaEnt.get(annotazione));
				Method m = findMethod(metodi, nomeMetodoOggetto);
				Method mEnt = findMethod(getMetodiEnt(), nomeMetodoEnt);
				if (notNull(m)) {
					try {
						m.invoke(obj, mEnt.invoke(this));
					} catch (Exception e) {
						log("Errore durante l'invocazione del metodo ", nomeMetodoOggetto, " dal corrispondente metodo dell'entity ", nomeMetodoEnt, e);
					}
				}
			}
		}
		return obj;
	}

	private Map<String, String> getMap(Class c) {
		Map<String, String> mappa = new HashMap<String, String>();
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				aggiungi(mappa, annCol.name(), att.getName());
			}
		}
		return mappa;
	}

	/**
	 * Pone in maiuscolo la prima lettera
	 * 
	 * @param s
	 * @return String
	 */
	protected String cap(String s) {
		return capFirstLetter(s);
	}

	/**
	 * Metodo che ritorna true se tutte le variabili istanza dell'entity,
	 * corrispondenti alle colonne chiave primaria, sono settate a valori non
	 * nulli
	 * 
	 * @return boolean
	 */
	private boolean isAllPkSet() {
		setSearchByPk(true);
		boolean esito = true;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.pk()) {
					try {
						esito = tutte(esito, notNull(invokeGetter(att)));
					} catch (Exception e) {
					}
				}
			}
		}
		return esito;
	}

	/**
	 * Metodo che ritorna true se almeno una delle variabili istanza pk è di
	 * tipo autoincrementale
	 * 
	 * @return boolean
	 */

	private boolean hasAutoInc() {
		boolean esito = false;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (tutte(annCol.pk(), annCol.autoInc())) {
					esito = true;
					break;
				}
			}
		}
		return esito;
	}

	/**
	 * Ritorna true se l'Entity presenta la colonna di cancellazione logica (in
	 * inps è FLAG_STATO)
	 * 
	 * @return boolean
	 */
	private boolean hasDeleteLogic() {
		boolean esito = false;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.deleteLogic()) {
					esito = true;
					break;
				}
			}
		}
		return esito;
	}

	/**
	 * Esegue una select su chiave primaria e ritorna l'unica entity trovata. Se
	 * non tutti i valori della chiave primaria sono impostati, ritorna null.
	 * 
	 * @param <T>
	 * @return T
	 * @throws Exception
	 */
	public <T extends BaseEntity> T selectByPk() throws Exception {
		setSearchByPk(true);
		if (!isAllPkSet()) {
			logNoFullPk("Select");
			return null;
		}
		setWhereConditionForPk();
		skipFlagStato = true;
		T elem = selectOne();
		cleanWhereCondition();
		skipFlagStato = false;
		return elem;
	}

	private void logNoFullPk(String op) {
		log("Non tutte le chiavi primarie dell'Entity ", getEntityDetail(), " sono impostate. ", op, " non eseguita.");
	}

	private <T extends BaseEntity> PList<T> _selectByPkForNextProgr() throws Exception {
		setSearchByPk(true);
		setWhereConditionForPk();
		skipFlagStato = true;
		PList<T> elem = select();
		cleanWhereCondition();
		skipFlagStato = false;
		return elem;
	}

	/**
	 * Esegue in maniera intelligente l'insert o l'upsert. Se l'Entity presenta
	 * la colonna di cancellazione logica FLAG_STATO e NON ha alcuna chiave
	 * primaria autoincrementale, allora esegue l'upsert. Se invece NON presenta
	 * la colonna FLAG_STATO o ha una chiave primaria autoincrementale, allora
	 * esegue una normale INSERT
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean insert() throws Exception {
		boolean esito = false;
		resumed = false;
		boolean goUpsert = tutte(hasDeleteLogic(), !hasAutoInc());
		if (goUpsert) {
			logEseguo("UPSERT");
			esito = upsert();
		} else {
			esito = _insert();
		}
		return esito;
	}

	/**
	 * Esegue in maniera intelligente l'insert o l'upsert basandosi sulla sola
	 * condizione di presenza della stessa chiave primaria a prescrindere dalla
	 * cancellazione logica del record. Se tento di inserire un record che ha la
	 * stessa chiave primaria di uno già esistente a prescindere dalla colonna
	 * di cancellazione logica, allora anzichè l'insert esegue una update per
	 * chiave primaria di tutte le altre colonne. Se invece il record che tento
	 * di inserire non va in conflitto di chiave primaria allora esegue una
	 * classica insert
	 * 
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean insertStrong() throws Exception {
		boolean esito = false;
		resumed = false;
		boolean goUpsert = !hasAutoInc();
		if (goUpsert) {
			logEseguo("UPSERT");
			esito = upsertStrong();
		} else {
			logEseguo("INSERT");
			esito = _insert();
		}
		return esito;
	}

	private void logEseguo(String op) {
		log("Eseguo ", op, " sull'istanza per la entity ", getEntityDetail());
	}

	/**
	 * Esegue in maniera intelligente la delete logica se l'istanza presenta la
	 * colonna FLAG_STATO, altrimenti esegue una delete FISICA dei record
	 * individuati dalla condizione di where
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	public boolean delete() throws Exception {
		boolean esito = false;
		if (isOverLimit()) {
			if (notNull(getWhereCondition())) {
				logOverLimit("Delete");
			}
			return false;
		}
		if (hasDeleteLogic()) {
			log("Eseguo la delete LOGICA impostando FLAG_STATO=C sull'istanza per la entity ", getEntityDetail());
			esito = deleteLogic();
		} else {
			log("Eseguo la delete FISICA dell'istanza in quanto manca la colonna di cancellazione logica FLAG_STATO per la entity ", getEntityDetail());
			esito = _delete();
		}
		setByPassUpdateDeleteLimitation(false);
		return esito;
	}

	private void logOverLimit(String op) {
		log("La query di ", op, " impatta più di ", updateDeleteLimit, " record. ", op, " NON ESEGUITA per la entity ", getEntityDetail(),
				". Eliminare l'attributo updateDeleteLimit da @DaoConfig oppure invocare byPassUpdateDeleteLimitation sull'entity specifica prima di invocare ", op);
	}

	protected boolean isSearchByPk() {
		return searchByPk;
	}

	protected void setSearchByPk(boolean searchByPk) {
		this.searchByPk = searchByPk;
	}

	/**
	 * Verifica se il numero di record impattati dalla where condition è
	 * superiore al valore impostato nella proprietà UPDATE_DELETE_LIMIT nel
	 * file Pilot.properties. Il controllo non viene eseguito se la proprietà
	 * byPassUpdateDeleteLimitation è impostata a true;
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	private boolean isOverLimit() throws Exception {
		if (isByPassUpdateDeleteLimitation())
			return false;
		if (Null(getWhereCondition())) {
			logNoWhere();
			return true;
		}
		skipInstanceVars = true;
		skipFlagStato = true;
		boolean esito = isOverLimit(selectCount());
		skipInstanceVars = false;
		skipFlagStato = true;
		return esito;
	}

	private boolean isOverLimit(Long quanti) {
		boolean overLimit = false;

		if (notNull(updateDeleteLimit)) {
			overLimit = quanti > updateDeleteLimit.intValue();
		}
		recordImpattati = quanti;
		return overLimit;
	}

	private boolean isByPassUpdateDeleteLimitation() {
		return byPassUpdateDeleteLimitation;
	}

	/**
	 * Se impostato a true, disattiva temporaneamente l'eventuale limitazione
	 * imposta su update/delete attraverso la proprietà UPDATE_DELETE_LIMIT
	 * 
	 * @param byPassUpdateDeleteLimitation
	 * @return BaseDaoEntity
	 */
	public BaseDaoEntity setByPassUpdateDeleteLimitation(boolean byPassUpdateDeleteLimitation) {
		this.byPassUpdateDeleteLimitation = byPassUpdateDeleteLimitation;
		return this;
	}

	/**
	 * Disattiva temporaneamente l'eventuale limitazione imposta su
	 * update/delete attraverso la proprietà UPDATE_DELETE_LIMIT
	 * 
	 * @return BaseDaoEntity
	 */
	public BaseDaoEntity byPassUpdateDeleteLimitation() {
		return setByPassUpdateDeleteLimitation(true);
	}

	private PList<String> getProvidedAttributes() {
		return providedAttributes;
	}

	/**
	 * Imposta la lista degli attributi di cui si sono forniti i valori
	 * 
	 * @param providedAttributes
	 */
	protected void setProvidedAttributes(PList<String> providedAttributes) {
		this.providedAttributes = providedAttributes;
	}

	/**
	 * Aggiunge il campo name alla lista di colonne impostate
	 * 
	 * @param name
	 */
	protected void addProvidedAttribute(String name) {
		getProvidedAttributes().add(name);
	}

	/**
	 * Svuota la lista delle colonne impostate
	 */
	protected void cleanProvidedAttributes() {
		setProvidedAttributes(plstr());
	}

	/**
	 * Torna true se il campo nome è nell'elenco dei valori impostati
	 * 
	 * @param nome
	 * @return booleans
	 */
	private boolean isProvided(String nome) {
		return is(getProvidedAttributes(), nome);
	}

	/**
	 * Ritorna una stringa che rappresenta il tempo di esecuzione della query di
	 * questa entity
	 * 
	 * @return String
	 */
	public String getExecTime() {
		return execTime;
	}

	private String decode(String sql) {
		return replace(sql, KEY_, "%");
	}

	private void setExecTime(String sql, Date start, Date end, Integer i) {
		this.execTime = str(DaoHelper.OPEN_QUADRA, elapsedTime(start, end), DaoHelper.CLOSE_QUADRA);
		sql = String.format(sql, i, getExecTime(), getExecTime());
		sql = decode(sql);
		setQueryEseguita(sql);
		if (isLogWhileRunning())
			log(sql);
		addQuery();
	}

	private void setExecTimeError(String sql, Date start, Date end, Integer i, String error) {
		this.execTime = str(DaoHelper.OPEN_QUADRA, elapsedTime(start, end), DaoHelper.CLOSE_QUADRA);
		sql = String.format(sql, i, getExecTime(), error, getExecTime());
		sql = decode(sql);
		setQueryEseguita(sql);
		if (isLogWhileRunning())
			log(sql);
		addQuery();
	}

	private void addQuery() {
		if (null != getContainer()) {
			getContainer().add(truncQuery(getQueryEseguita()));
		}
	}

	private String truncQuery(String sql) {
		return substring(sql, QUERY_PREFIX, false, false, null, false, false);
	}

	public void addQuery(String testo) {
		if (null != getContainer()) {
			getContainer().add(testo);
		}
	}

	/**
	 * Imposta il nuovo valore della variabile queryTimeout in secondi
	 * 
	 * @param queryTimeout
	 */
	public void setQueryTimeout(Integer queryTimeout) {
		this.queryTimeout = queryTimeout;
	}

	/**
	 * Ritorna il valore della variabile queryTimeout impostata di default a 60
	 * sec.
	 * 
	 * @return Integer
	 */
	public Integer getQueryTimeout() {
		return queryTimeout;
	}

	public abstract <K extends BaseEntity> K setPrimary(Object... value) throws Exception;

	public abstract <K extends BaseEntity, T extends BaseEntity> BaseEntity setPrimaryEnt(T ent) throws Exception;

	/**
	 * Esegue una select per chiave primaria impostata secondo la metodologia
	 * setPrimary passando gli argomenti nello stesso ordine in cui si trovano
	 * definite le variabili istanza chiavi primarie (pk=true) dell'entity
	 * 
	 * @param value
	 * @return T
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public <T extends BaseEntity> T selectByPk(Object... value) throws Exception {
		return (T) setPrimary(value).selectByPk();
	}

	/**
	 * Esegue una select per chiave primaria impostata secondo la metodologia
	 * setPrimary a partire da una entity ent passata come parametro e basandosi
	 * sul principio di uguaglianza degli alias delle colonne chiave primaria
	 * tra le due entity.
	 * 
	 * @param <T>
	 * @param ent
	 * @return K
	 * @throws Exception
	 */
	public <K extends BaseEntity, T extends BaseEntity> K selectByPk(T ent) throws Exception {
		if (Null(ent))
			return null;
		return (K) setPrimaryEnt(ent).selectByPk();
	}

	/**
	 * Esegue una update per chiave primaria impostata secondo la metodologia
	 * setPrimary passando gli argomenti nello stesso ordine in cui si trovano
	 * definite le variabili istanza chiavi primarie (pk=true) dell'entity
	 * 
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateByPk(Object... value) throws Exception {
		return setPrimary(value).updateByPk();
	}

	/**
	 * Esegue una update per chiave primaria secondo la metodologia setPrimary a
	 * partire da una entity ent passata come parametro e basandosi sul
	 * principio di uguaglianza degli alias delle colonne chiave primaria tra le
	 * due entity.
	 * 
	 * @param <T>
	 * @param ent
	 * @return boolean
	 * @throws Exception
	 */
	public <T extends BaseEntity> boolean updateByPk(T ent) throws Exception {
		return setPrimaryEnt(ent).updateByPk();
	}

	/**
	 * Esegue una updateByPk su una lista di entity applicando a ogni entity la
	 * logica della updateByPk per entity
	 * 
	 * @param <T>
	 * @param ent
	 * @throws Exception
	 */
	public <T extends BaseEntity> void updateByPk(PList<T> ent) throws Exception {
		try {
			if (isDsMode()) {
				multi = true;
			}
			for (T t : safe(ent)) {
				updateByPk(t);
			}
		} finally {
			if (isDsMode()) {
				if (notNull(getConnection())) {
					getConnection().close();
				}
				multi = false;
			}
		}
	}

	/**
	 * Esegue una update per chiave primaria secondo la metodologia setPrimary a
	 * partire da una entity ent passata come parametro e basandosi sul
	 * principio di uguaglianza degli alias delle colonne chiave primaria tra le
	 * due entity.
	 * 
	 * @param <T>
	 * @param ent
	 * @return boolean
	 * @throws Exception
	 */
	public <T extends BaseEntity> boolean update(T ent) throws Exception {
		excludePk();
		setPrimaryEnt(ent);
		setWhereConditionForPk();
		return update();
	}

	/**
	 * Esegue una update su una lista di entity applicando a ogni entity la
	 * logica della update per entity
	 * 
	 * @param <T>
	 * @param ent
	 * @throws Exception
	 */
	public <T extends BaseEntity> void update(PList<T> ent) throws Exception {
		try {
			if (isDsMode()) {
				multi = true;
			}
			for (T t : safe(ent)) {
				update(t);
			}
		} finally {
			if (isDsMode()) {
				if (notNull(getConnection())) {
					getConnection().close();
				}
				multi = false;
			}
		}
	}

	/**
	 * Esegue una delete per chiave primaria impostata secondo la metodologia
	 * setPrimary passando gli argomenti nello stesso ordine in cui si trovano
	 * definite le variabili istanza chiavi primarie (pk=true) dell'entity
	 * 
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteByPk(Object... value) throws Exception {
		return setPrimary(value).deleteByPk();
	}

	/**
	 * Esegue una resume ossia una riattivazione logica di un record individuato
	 * univocamente attraverso la sua pk, impostando la colonna FLAG_STATO a A
	 * per chiave primaria impostata secondo la metodologia setPrimary passando
	 * gli argomenti nello stesso ordine in cui si trovano definite le variabili
	 * istanza chiavi primarie (pk=true) dell'entity
	 * 
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	public boolean resumeByPk(Object... value) throws Exception {
		return setPrimary(value).resumeByPk();
	}

	/**
	 * Esegue una resume ossia una riattivazione logica di un record individuato
	 * univocamente attraverso la sua pk, impostando la colonna FLAG_STATO a A
	 * per chiave primaria impostata secondo la metodologia setPrimary a partire
	 * da una entity ent passata come parametro e basandosi sul principio di
	 * uguaglianza degli alias delle colonne chiave primaria tra le due entity.
	 * 
	 * @param <T>
	 * @param ent
	 * @return boolean
	 * @throws Exception
	 */
	public <T extends BaseEntity> boolean resumeByPk(T ent) throws Exception {
		if (Null(ent))
			return false;
		return setPrimary(ent).resumeByPk();
	}

	/**
	 * Esegue una resume ossia una riattivazione logica di un insieme di record
	 * individuato impostando la colonna FLAG_STATO a A per chiave primaria
	 * impostata secondo la metodologia setPrimary a partire da una entity ent
	 * passata come parametro e basandosi sul principio di uguaglianza degli
	 * alias delle colonne chiave primaria tra le due entity.
	 * 
	 * @param <T>
	 * @param ent
	 * @return boolean
	 * @throws Exception
	 */
	public <T extends BaseEntity> boolean resume(T ent) throws Exception {
		if (Null(ent))
			return false;
		setPrimaryEnt(ent);
		return _resume();
	}

	/**
	 * Esegue una resume su una lista di entity applicando a ogni entity la
	 * logica della resume per entity
	 * 
	 * @param <T>
	 * @param ent
	 * @throws Exception
	 */
	public <T extends BaseEntity> void resume(PList<T> ent) throws Exception {
		try {
			if (isDsMode()) {
				multi = true;
			}
			for (T t : safe(ent)) {
				resume(t);
			}
		} finally {
			if (isDsMode()) {
				if (notNull(getConnection())) {
					getConnection().close();
				}
				multi = false;
			}
		}
	}

	/**
	 * Esegue una delete secondo la metodologia setPrimary a partire da una
	 * entity ent passata come parametro e basandosi sul principio di
	 * uguaglianza degli alias delle colonne chiave primaria tra le due entity.
	 * 
	 * @param <T>
	 * @param ent
	 * @return boolean
	 * @throws Exception
	 */
	public <T extends BaseEntity> boolean deleteByPk(T ent) throws Exception {
		return setPrimaryEnt(ent).deleteByPk();
	}

	/**
	 * Esegue una deleteByPk su una lista di entity applicando a ogni entity la
	 * logica della deleteByPk per entity
	 * 
	 * @param <T>
	 * @param ent
	 * @throws Exception
	 */
	public <T extends BaseEntity> void deleteByPk(PList<T> ent) throws Exception {
		try {
			if (isDsMode()) {
				multi = true;
			}
			for (T t : safe(ent)) {
				deleteByPk(t);
			}
		} finally {
			if (isDsMode()) {
				if (notNull(getConnection())) {
					getConnection().close();
				}
				multi = false;
			}
		}
	}

	/**
	 * Esegue una delete secondo la metodologia setPrimary a partire da una
	 * entity ent passata come parametro e basandosi sul principio di
	 * uguaglianza degli alias delle colonne chiave primaria tra le due entity.
	 * *
	 * 
	 * @param <T>
	 * @param ent
	 * @return boolean
	 * @throws Exception
	 */
	public <T extends BaseEntity> boolean delete(T ent) throws Exception {
		excludePk();
		setPrimaryEnt(ent);
		setWhereConditionForPk();
		return delete();
	}

	/**
	 * Esegue una delete su una lista di entity applicando a ogni entity la
	 * logica della delete per entity
	 * 
	 * @param <T>
	 * @param ent
	 * @throws Exception
	 */
	public <T extends BaseEntity> void delete(PList<T> ent) throws Exception {
		try {
			if (isDsMode()) {
				multi = true;
			}
			for (T t : safe(ent)) {
				delete(t);
			}
		} finally {
			if (isDsMode()) {
				if (notNull(getConnection())) {
					getConnection().close();
				}
				multi = false;
			}
		}
	}

	/**
	 * Esegue una select impostando automaticamente le colonne pk a partire
	 * dalle colonne pk della entity ent passata come parametro sulla base della
	 * condizione di uguaglianza degli alias delle colonne tra le due entity
	 * 
	 * @param <T>
	 * @param ent
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K extends BaseEntity, T extends BaseEntity> PList<K> select(T ent) throws Exception {
		if (Null(ent))
			return pl();
		setPrimaryEnt(ent);
		return new PArrayList(safe(select()));
	}

	/**
	 * Stessa logica applicativa di select(T ent) ma sfruttando il meccanismo di
	 * cache
	 * 
	 * @param <K>
	 * @param <T>
	 * @param ent
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K extends BaseEntity, T extends BaseEntity> PList<K> selectCache(T ent) throws Exception {
		if (Null(ent))
			return pl();
		setPrimaryEnt(ent);
		return new PArrayList(safe(selectCache()));
	}

	private <T extends BaseEntity> T getInstance(Class c) throws Exception {
		T t = null;
		if (notNull(c)) {
			Constructor<T> ctor = null;
			if (null != getContainer()) {
				ctor = c.getConstructor(Connection.class, String.class, String.class, PList.class);
				t = (T) ctor.newInstance(new Object[] { getConnection(), getCodUtenteCostruttore(), getCodApplCostruttore(), getContainer() });
			} else {
				ctor = c.getConstructor(Connection.class, String.class, String.class);
				t = (T) ctor.newInstance(new Object[] { getConnection(), getCodUtenteCostruttore(), getCodApplCostruttore() });
			}

		}
		return t;
	}

	/**
	 * Data una lista di oggetti entity, esegue per ognuno di essi una select
	 * per chiave primaria impostata secondo la metodologia setPrimary a partire
	 * da una entity ent passata come parametro e basandosi sul principio di
	 * uguaglianza degli alias delle colonne chiave primaria tra le due entity.
	 * Ritorna la lista di oggetti tornati da ogni select così effettuata
	 * 
	 * @param <K>
	 * @param <T>
	 * @param listEnt
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K extends BaseEntity, T extends BaseEntity> PList<K> selectByPk(PList<T> listEnt) throws Exception {
		PList<K> lista = pl();
		try {
			if (isDsMode()) {
				multi = true;
			}
			for (T t : safe(listEnt)) {
				lista.add((K) selectByPk(t));
				cleanWhereCondition();
			}
		} finally {
			if (isDsMode()) {
				if (notNull(getConnection())) {
					getConnection().close();
				}
				multi = false;
			}
		}
		return lista;
	}

	/**
	 * Data una lista di oggetti entity esegue per ognuno di essi una select
	 * impostando automaticamente le colonne pk a partire dalle colonne pk della
	 * entity ent passata come parametro sulla base della condizione di
	 * uguaglianza degli alias delle colonne tra le due entity. Ritorna la lista
	 * di oggetti tornati da ogni select così effettuata
	 * 
	 * @param <K>
	 * @param <T>
	 * @param listEnt
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K extends BaseEntity, T extends BaseEntity> PList<K> select(PList<T> listEnt) throws Exception {
		PList<K> lista = pl();
		try {
			if (isDsMode()) {
				multi = true;
			}
			for (T t : safe(listEnt)) {
				lista.aggiungiList((PList<K>) select(t));
				cleanWhereCondition();
			}
		} finally {
			if (isDsMode()) {
				if (notNull(getConnection())) {
					getConnection().close();
				}
				multi = false;
			}
		}
		return lista;
	}

	/**
	 * Stessa logica applicativa di select(PList<T> listEnt) ma sfruttando il
	 * meccanismo di cache
	 * 
	 * @param <K>
	 * @param <T>
	 * @param listEnt
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K extends BaseEntity, T extends BaseEntity> PList<K> selectCache(PList<T> listEnt) throws Exception {
		PList<K> lista = pl();
		try {
			if (isDsMode()) {
				multi = true;
			}
			for (T t : safe(listEnt)) {
				lista.aggiungiList((PList<K>) selectCache(t));
				cleanWhereCondition();
			}
		} finally {
			if (isDsMode()) {
				if (notNull(getConnection())) {
					getConnection().close();
				}
				multi = false;
			}
		}
		return lista;
	}

	public Integer getUpdateDeleteLimit() {
		return updateDeleteLimit;
	}

	public void setUpdateDeleteLimit(Integer updateDeleteLimit) {
		this.updateDeleteLimit = updateDeleteLimit;
	}

	/**
	 * Se true, indica che l'operazione di inserimento ha causato un
	 * aggiornamento di un record pre-esistente che era stato cancellato
	 * logicamente e che aveva la stessa chiave primaria e che quindi è stato
	 * riportato ad ATTIVO ma con i nuovi valori del record che si è tentato di
	 * inserire
	 * 
	 * @return boolean
	 */
	public boolean isResumed() {
		return resumed;
	}

	protected String getEntityDetail() {
		return str(getClass().getSimpleName(), DOUBLE_SPACE, getPk());
	}

	public boolean isLogWhileRunning() {
		return logWhileRunning;
	}

	public void setLogWhileRunning(Boolean logWhileRunning) {
		this.logWhileRunning = logWhileRunning;
	}

	public boolean isMock() {
		return mock;
	}

	public void setMock(boolean mock) {
		this.mock = mock;
	}

	public PMap<String, PList<BaseEntity>> getDb() {
		return db;
	}

	public void setDb(PMap<String, PList<BaseEntity>> db) {
		this.db = db;
	}

	/**
	 * Imposta i valori delle variabili istanza dell'Entity identificate
	 * dall'annotazione Column,copiandoli direttamente dall'oggetto Entity
	 * passato come parametro k ad esclusione di codUtente e codApp che
	 * altrimenti andrebbero a ricoprire codUtenteCostruttore e
	 * codAppCostruttore impostate tramite giveMe. La colonna FLAGSTATO se
	 * esistente viene sempre impostata ad A
	 * 
	 * @param <X>
	 * @param k
	 * @throws Exception
	 */
	public <X extends BaseDaoEntity, T> BaseDaoEntity copyFrom(X k) throws Exception {
		for (Field att : getAttributi()) {
			if (is(att.getName(), getAttributo(METHOD_COD_APPL), getAttributo(METHOD_COD_UTENTE)))
				continue;
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				invokeSetter((T) invokeGetter(k, att), att);
			}
		}
		setFlagStato(ATTIVO);
		swapDate();
		return this;
	}

	private void swapDate() throws Exception {
		if (!p.isOrderedDate((Date) getDataInizio(), (Date) getDataFine())) {
			Date start = getDataInizio();
			setDataInizio(getDataFine());
			setDataFine(start);
		}
	}

	private PList<String> getDistinctCol() {
		return distinctCol;
	}

	private void setDistinctCol(PList<String> distinctCol) {
		this.distinctCol = distinctCol;
	}

	protected PList<String> getFields() {
		return fields;
	}

	protected void setFields(PList<String> fields) {
		this.fields = fields;
	}

	private PList<String> getCols() {
		return cols;
	}

	private void setCols(PList<String> cols) {
		this.cols = cols;
	}

	private String getJoinCondition() {
		return joinCondition;
	}

	private void setJoinCondition(String joinCondition) {
		this.joinCondition = joinCondition;
	}

	private String getJoinName() {
		return joinName;
	}

	protected void setJoinName(String joinName) {
		this.joinName = joinName;
	}

	public boolean isFromReal() {
		return fromReal;
	}

	public void setFromReal(boolean fromReal) {
		skipFlagStato = fromReal;
		this.fromReal = fromReal;
	}

	public boolean isDb2() {
		return db2;
	}

	public void setDb2(boolean db2) {
		this.db2 = db2;
	}

	public boolean isDsMode() {
		return dsMode;
	}

	public void setDsMode(boolean dsMode) {
		this.dsMode = dsMode;
	}

	protected String getFieldDataAggiornamento() {
		String campo = null;
		for (Field att : getAttributi()) {
			if (att.getName().toUpperCase().endsWith(METHOD_DATA_AGGIORN)) {
				campo = att.getName();
				break;
			}
		}
		return campo;
	}

	protected String getFieldCodUtente() {
		String campo = null;
		for (Field att : getAttributi()) {
			if (att.getName().toUpperCase().endsWith(METHOD_COD_UTENTE)) {
				campo = att.getName();
				break;
			}
		}
		return campo;
	}

	protected String getFieldCodApp() {
		String campo = null;
		for (Field att : getAttributi()) {
			if (att.getName().toUpperCase().endsWith(METHOD_COD_APPL)) {
				campo = att.getName();
				break;
			}
		}
		return campo;
	}

}
