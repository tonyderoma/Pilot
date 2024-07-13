package it.eng.pilot;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe di esecuzione delle query di selezione scritte manualmente eseguite
 * tramite l'ausilio di DaoHelper.Gli oggetti di mapping ORM devono avere le
 * variabili istanza uguali agli alias usati nelle query. I filtri applicati
 * alle query (parametri della where condition) devono essere indicati nella
 * query nella forma :nomeParametro e i parametri passati a NULL non vengono
 * considerati nella formazione della where condition.
 * 
 * @author Antonio Corinaldi
 */
@SuppressWarnings("unchecked")
public abstract class DaoHelperBaseResult extends PilotSupport implements Serializable {

	private static final String KEY_ = BaseDaoEntity.KEY_;
	private static final String CLOSE_PAR = ")";
	private static final String NUMBER = "NUMBER";
	private static final String INTEGER = "INTEGER";
	private static final String INTEGER_TYPE = "Integer";
	private static final String SHORT = "Short";
	private static final String FLOAT = "Float";
	private static final String DOUBLE = "Double";
	private static final String TIMESTAMP = "Timestamp";
	private static final String LONG = "LONG";
	private static final String LONG_TYPE = "Long";
	private static final String DATE = "Date";
	private static final String BYTE = "byte[]";
	private static final String STRING = "String";
	public static final String BIG_DECIMAL = "BigDecimal";
	public static final String DECIMAL = "DECIMAL";
	private static final String COMMA = DaoHelper.COMMA;
	private static final String CALL = "call";
	private static final String SET_CONNECTION = "setConnection";
	private static final String SET = BaseDaoEntity.SET;
	private static final String SPACE = DaoHelper.SPACE;
	private static final String QUERY_PREFIX = BaseDaoEntity.QUERY_PREFIX;
	private static final String OPEN = DaoHelper.OPEN_QUADRA;
	private static final String CLOSE = DaoHelper.CLOSE_QUADRA;
	private static final String LEFT_ARROW = DaoHelper.LEFT_ARROW;
	private static final long serialVersionUID = -7984036154418911980L;
	private static String QUERY_FILE = DaoHelper.QUERY_FILE;
	protected boolean logWhileRunning = true;// se true logga le query durante
	// l'esecuzione altrimenti no
	private static final String ERROR_MARK = BaseDaoEntity.ERROR_MARK;
	public static final String RECORD = BaseDaoEntity.RECORD;
	private boolean mock;
	private boolean db2;
	private boolean dsMode;

	public abstract Connection getConnection();

	public abstract String getQuery();

	public abstract void setQuery(String query);

	protected abstract PList<String> getContainer();

	protected abstract void setContainer(PList<String> container);

	private String queryEseguita;
	private String execTime;
	public Integer queryTimeout = 60;
	private String queryName;

	private void logNoQuery() {
		log("Occorre definire una query di select da eseguire. La query attuale è vuota, non esiste alcuna key ", getQueryName(), " nel file ", QUERY_FILE);
	}

	private class Pagination {

		private Integer numeroPagina;
		private Integer quantiPerPagina;

		private Pagination(Integer numeroPagina, Integer quantiPerPagina) {
			super();
			this.numeroPagina = numeroPagina;
			this.quantiPerPagina = quantiPerPagina;
		}

		public Integer getNumeroPagina() {
			return numeroPagina;
		}

		public void setNumeroPagina(Integer numeroPagina) {
			this.numeroPagina = numeroPagina;
		}

		public Integer getQuantiPerPagina() {
			return quantiPerPagina;
		}

		public void setQuantiPerPagina(Integer quantiPerPagina) {
			this.quantiPerPagina = quantiPerPagina;
		}

		private Integer getPrimoIndice() {
			return (getNumeroPagina() * getQuantiPerPagina()) + 1;
		}

		private Integer getUltimoUndice() {
			return ((getNumeroPagina() - 1) * getQuantiPerPagina()) + 1;
		}
	}

	private <K> void invokeMethod(Method method, K item, ResultSet rs, String col) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		Class retType = method.getParameterTypes()[0];

		if (is(retType.getSimpleName(), STRING)) {
			method.invoke(item, rs.getString(col));
		} else if (is(retType.getSimpleName(), DATE)) {
			method.invoke(item, rs.getDate(col));
		} else if (is(retType.getSimpleName(), BIG_DECIMAL)) {
			method.invoke(item, rs.getBigDecimal(col));
		} else if (is(retType.getSimpleName(), LONG_TYPE)) {
			method.invoke(item, rs.getLong(col));
		} else if (is(retType.getSimpleName(), INTEGER_TYPE)) {
			method.invoke(item, rs.getInt(col));
		} else if (is(retType.getSimpleName(), SHORT)) {
			method.invoke(item, rs.getShort(col));
		} else if (is(retType.getSimpleName(), FLOAT)) {
			method.invoke(item, rs.getFloat(col));
		} else if (is(retType.getSimpleName(), DOUBLE)) {
			method.invoke(item, rs.getDouble(col));
		} else if (is(retType.getSimpleName(), TIMESTAMP)) {
			method.invoke(item, rs.getTimestamp(col));
		} else if (is(retType.getSimpleName(), BYTE)) {
			method.invoke(item, rs.getBlob(col));
		}

	}

	private String addPaginationClause(String sql, Pagination paging) {
		String prefix = "SELECT * FROM(  SELECT  a.*,rownum r  FROM  (";
		String suffix = str(") a  WHERE rownum < ", String.valueOf(paging.getPrimoIndice()), ") WHERE r >= ", String.valueOf(paging.getUltimoUndice()));
		return str(prefix, sql, suffix);
	}

	class Attributo {
		private Class tipo;
		private String nome;

		public Class getTipo() {
			return tipo;
		}

		public void setTipo(Class tipo) {
			this.tipo = tipo;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

	}

	/**
	 * Metodo che esegue una stored procedure con out parameters. I parametri di
	 * input (IN PARAMETERS) vanno passati nello stesso ordine con cui sono
	 * dichiarati i parametri di ingresso nella forma classica dei punti
	 * interrogativi ?. I parametri passati come NULL vengono trattati come tali
	 * e non esclusi. I tipi ritornati dalla SP (OUT PARAMETERS) vengono
	 * definiti attraverso la dichiarazione di variabili istanza annotate con @OutParam
	 * dichiarate nello stesso ordine di definizione degli out parameters della
	 * stored procedure. Tali variabili istanza al termine dell'invocazione
	 * conterranno i valori restituiti dalla SP e i cui tipi Java sono dello
	 * stesso tipo che prevede la SP di restituire in uscita come out parameter.
	 * Esempio di definizione della invocazione di SP con parametri di IN e OUT
	 * in cui il secondo ? è un out parameter: feriale={call
	 * DBK_ENEK_UTILITY.DBP_ENEK_GetFeriale( ? ,? )}. Se il tipo Out Parameter è
	 * un Date, occorre definire nella classe una variabile del tipo:
	 * 
	 * OutParam private Date data;
	 * 
	 * La stored procedure ha dei parametri di output in cui va a scrivere il
	 * risultato e tali parametri di output devono essere messi, nella
	 * definizione della SP, dopo i parametri di input. Nella funzione SQL
	 * invece, i tipi di ritorno vengono definiti prima dei parametri di input e
	 * la query di invocazione assume la seguente forma:
	 * 
	 * feriale={ ?,?,? = call DBK_ENEK_UTILITY.DBP_ENEK_GetFeriale( ? )}
	 * 
	 * In essa, i tipi di ritorno della funzione vengono messi prima della call
	 * e indicati attraverso i ? separati da virgole. I parametri di ingresso
	 * della funzione invece vengono messi dopo l'indicazione del nome della
	 * funzione tra parentesi tonde sempre attraverso i placeholder ?
	 * 
	 * 
	 * @param <K>
	 * @param params
	 * @param c
	 * @return K
	 * @throws Exception
	 */

	public <K> K executeStoredProcedureOutParameters(Class<K> c, Object... params) throws Exception {
		if (!checkQuerySP())
			return null;
		K item = (K) Class.forName(c.getName()).newInstance();
		if (isMock())
			return mock(c);
		CallableStatement stmt = null;
		Date start = null;
		Date end = null;
		String error = null;
		try {
			Field[] attributi = c.getDeclaredFields();
			PList<Class> outParams = pl();
			PList<Attributo> atts = pl();
			for (Field att : attributi) {
				OutParam annCol = att.getAnnotation(OutParam.class);
				if (notNull(annCol)) {
					Attributo attr = new Attributo();
					attr.setNome(att.getName());
					attr.setTipo(att.getType());
					atts.add(attr);
					outParams.add(att.getType());
				}
			}
			boolean functionMode = checkFunctionMode();
			stmt = getConnection().prepareCall(callToLowerCase(getQuery()));
			int i = 0;
			if (functionMode) {
				i = countFunctionParameters();
			}
			i = setInParameters(stmt, i, params);
			if (functionMode) {
				i = 0;
			}
			setOutParameters(stmt, outParams, i);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			stmt.execute();
			end = now();
			int k = 1;
			if (!functionMode) {
				k = params.length + 1;
			}
			Method[] methods = c.getDeclaredMethods();
			for (Method m : methods) {
				for (Attributo att : safe(atts)) {
					if (is(m.getName(), str(SET, att.getNome()))) {
						// log("INDICE OUT PARAM=", k);
						checkOutParam(stmt, m, item, att.getTipo(), k);
						k++;
						break;
					}
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(getQuery(), start, end, error);
			else
				logQuery(getQuery(), start, end);
			closeAlls(stmt, null);
		}
		return item;
	}

	private boolean checkFunctionMode() {
		boolean functionMode = false;
		String sql = getQuery().toLowerCase();
		sql = substring(sql, null, false, false, CALL, false, false);
		functionMode = isLike(sql, "=");
		return functionMode;
	}

	private Integer countFunctionParameters() {
		String sql = getQuery().toLowerCase();
		sql = substring(sql, null, false, false, CALL, false, false);
		return howManyChars(sql, '?');
	}

	private void setOutParameters(CallableStatement stmt, PList<Class> outParams, int i) throws SQLException {
		for (Class c : safe(outParams)) {
			i++;
			if (almenoUna(Date.class.isAssignableFrom(c), Timestamp.class.isAssignableFrom(c), java.sql.Date.class.isAssignableFrom(c))) {
				stmt.registerOutParameter(i, Types.DATE);
			}
			if (Number.class.isAssignableFrom(c)) {
				stmt.registerOutParameter(i, Types.NUMERIC);
			}
			if (String.class.isAssignableFrom(c)) {
				stmt.registerOutParameter(i, Types.VARCHAR);
			}
		}
	}

	private int setInParameters(CallableStatement stmt, Integer i, Object... params) throws SQLException {

		for (Object par : params) {
			i++;
			if (notNull(par)) {
				if (Date.class.isAssignableFrom(par.getClass())) {
					stmt.setDate(i, getSQLDate((Date) par));
				} else if (Timestamp.class.isAssignableFrom(par.getClass())) {
					stmt.setTimestamp(i, (Timestamp) par);
				}
				if (Number.class.isAssignableFrom(par.getClass())) {
					if (par instanceof BigDecimal) {
						stmt.setBigDecimal(i, (BigDecimal) par);
					}

					if (par instanceof Long) {
						stmt.setLong(i, (Long) par);
					}
					if (par instanceof Integer) {
						stmt.setInt(i, (Integer) par);
					}
					if (par instanceof Double) {
						stmt.setDouble(i, (Double) par);
					}
					if (par instanceof Float) {
						stmt.setFloat(i, (Float) par);
					}
					if (par instanceof Short) {
						stmt.setShort(i, (Short) par);
					}
				}
				if (par instanceof String) {
					stmt.setString(i, (String) par);
				}
			} else {
				stmt.setObject(i, par);
			}
		}
		return i;
	}

	private <K> void checkOutParam(CallableStatement stmt, Method m, K item, Class c, Integer k) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (almenoUna(Date.class.isAssignableFrom(c), Timestamp.class.isAssignableFrom(c), java.sql.Date.class.isAssignableFrom(c))) {
			m.invoke(item, stmt.getDate(k));
		}
		if (Number.class.isAssignableFrom(c)) {
			if (BigDecimal.class.isAssignableFrom(c)) {
				m.invoke(item, stmt.getBigDecimal(k));
			}

			if (Long.class.isAssignableFrom(c)) {
				m.invoke(item, stmt.getLong(k));
			}
			if (Integer.class.isAssignableFrom(c)) {
				m.invoke(item, stmt.getInt(k));
			}
			if (Double.class.isAssignableFrom(c)) {
				m.invoke(item, stmt.getDouble(k));
			}
			if (Float.class.isAssignableFrom(c)) {
				m.invoke(item, stmt.getFloat(k));
			}
			if (Short.class.isAssignableFrom(c)) {
				m.invoke(item, stmt.getShort(k));
			}
		}

		if (String.class.isAssignableFrom(c)) {
			m.invoke(item, stmt.getString(k));
		}

	}

	/**
	 * Metodo che esegue una stored procedure con out parameters. I parametri di
	 * input (IN PARAMETERS) vanno passati in un oggetto dto le cui variabili
	 * istanza devono essere dichiarate nello stesso ordine con cui sono
	 * dichiarati i parametri di ingresso nella forma classica dei punti
	 * interrogativi ?. I parametri passati come NULL vengono trattati come tali
	 * e non esclusi. I tipi ritornati dalla SP (OUT PARAMETERS) vengono
	 * definiti attraverso la dichiarazione di variabili istanza annotate con @OutParam
	 * dichiarate nello stesso ordine di definizione degli out parameters della
	 * stored procedure. Tali variabili istanza al termine dell'invocazione
	 * conterranno i valori restituiti dalla SP e i cui tipi Java sono dello
	 * stesso tipo che prevede la SP di restituire in uscita come out parameter.
	 * Esempio di definizione della invocazione di SP con parametri di IN e OUT:
	 * feriale={call DBK_ENEK_UTILITY.DBP_ENEK_GetFeriale( ? )}. Se il tipo Out
	 * Parameter è un Date, occorre definire nella classe una variabile del
	 * tipo:
	 * 
	 * OutParam private Date data;
	 * 
	 * La stored procedure ha dei parametri di output in cui va a scrivere il
	 * risultato e tali parametri di output devono essere messi, nella
	 * definizione della SP, dopo i parametri di input. Nella funzione SQL
	 * invece, i tipi di ritorno vengono definiti prima dei parametri di input e
	 * la query di invocazione assume la seguente forma:
	 * 
	 * feriale={ ?,?,? = call DBK_ENEK_UTILITY.DBP_ENEK_GetFeriale( ? )}
	 * 
	 * In essa, i tipi di ritorno della funzione vengono messi prima della call
	 * e indicati attraverso i ? separati da virgole. I parametri di ingresso
	 * della funzione invece vengono messi dopo l'indicazione del nome della
	 * funzione tra parentesi tonde sempre attraverso i placeholder ?
	 * 
	 * @param <K>
	 * @param c
	 * @param dto
	 *            La stored procedure ha dei parametri di output in cui va a
	 *            scrivere il risultato e tali parametri di output devono essere
	 *            messi, nella definizione della SP, dopo i parametri di input.
	 *            Nella funzione SQL invece, i tipi di ritorno vengono definiti
	 *            prima dei parametri di input e la query di invocazione assume
	 *            la seguente forma:
	 * 
	 *            feriale={ ?,?,? = call DBK_ENEK_UTILITY.DBP_ENEK_GetFeriale( ?
	 *            )}
	 * 
	 *            In essa, i tipi di ritorno della funzione vengono messi prima
	 *            della call e indicati attraverso i ? separati da virgole. I
	 *            parametri di ingresso della funzione invece vengono messi dopo
	 *            l'indicazione del nome della funzione tra parentesi tonde
	 *            sempre attraverso i placeholder ?
	 * @return K
	 * @throws Exception
	 */
	public <K> K executeStoredProcedureOutParametersDTO(Class<K> c, Object dto) throws Exception {
		if (!checkQuerySP())
			return null;
		if (isMock())
			return mock(c);
		Field[] attributi = dto.getClass().getDeclaredFields();
		PList<Object> params = pl();
		for (Field att : attributi) {
			params.add(get(dto, att.getName()));
		}
		return executeStoredProcedureOutParameters(c, toArray(params, Object.class));
	}

	/**
	 * Metodo che esegue una stored procedure senza out parameters. I parametri
	 * di input (IN PARAMETERS) vanno passati secondo l'ordine in cui sono stati
	 * indicati nella query nella forma classica dei punti interrogativi ?. I
	 * parametri passati come NULL vengono trattati come tali e non esclusi.
	 * Esempio di definizione di query con soli parametri di ingresso:
	 * nuova={CALL DBK_PAAP_SISTCONTRIBUTIVE.aggiorna_apsic_da_entrate(?, ?, ?,
	 * ?, ? )}
	 * 
	 * @param params
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean executeStoredProcedure(Object... params) throws Exception {
		if (!checkQuerySP())
			return null;
		if (isMock())
			return true;
		boolean esito = false;
		CallableStatement stmt = null;
		Date start = null;
		Date end = null;
		String error = null;
		try {
			stmt = getConnection().prepareCall(callToLowerCase(getQuery()));
			setInParameters(stmt, 0, params);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			esito = stmt.execute();
			end = now();
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(getQuery(), start, end, error);
			else
				logQuery(getQuery(), start, end);
			closeAlls(stmt, null);
		}
		return esito;

	}

	/**
	 * Metodo che esegue una stored procedure senza out parameters. I parametri
	 * di input (IN PARAMETERS) vanno passati in un oggetto dto le cui variabili
	 * istanza devono essere dichiarate nello stesso ordine in cui compaiono i
	 * parametri di ingresso rappresentati dai ? . I parametri passati come NULL
	 * vengono trattati come tali e non esclusi. Esempio di definizione di query
	 * con soli parametri di ingresso: nuova={CALL
	 * DBK_PAAP_SISTCONTRIBUTIVE.aggiorna_apsic_da_entrate(?, ?, ?, ?, ? )}
	 * 
	 * @param dto
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean executeStoredProcedureDTO(Object dto) throws Exception {
		if (!checkQuerySP())
			return null;
		if (isMock())
			return true;
		Field[] attributi = dto.getClass().getDeclaredFields();
		PList<Object> params = pl();
		for (Field att : attributi) {
			params.add(get(dto, att.getName()));
		}
		return executeStoredProcedure(toArray(params, Object.class));
	}

	/**
	 * Esegue la query di selezione con i parametri passati nella where
	 * condition e mappa il risultato nella classe c
	 * 
	 * @param <K>
	 * @param c
	 * @param params
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K> PList<K> select(Class<K> c, Object... params) throws Exception {
		return selectBean(c, params);
	}

	private String getCountSql(String sql) {
		String from = substring(sql, "FROM", true, false, null, false, false);
		sql = str("SELECT COUNT(*) ", from);
		return sql;
	}

	private boolean checkQuery() {
		boolean ret = true;
		String sql = getQuery();
		if (Null(sql)) {
			logNoQuery();
			ret = false;
		} else {
			sql = sql.toLowerCase();
			if (!sql.trim().startsWith("select")) {
				log("Occorre definire una query di select da eseguire. La query attuale non mostra la clausola SELECT");
				ret = false;
			}
		}
		return ret;
	}

	private boolean checkQuerySP() {
		boolean ret = true;
		String sql = getQuery();
		if (Null(sql)) {
			logNoQuery();
			ret = false;
		} else {
			sql = sql.toLowerCase();
			sql = sql.trim();
			boolean condizione = tutte(sql.startsWith("{"), sql.endsWith("}"), isLike(sql, CALL));
			if (!condizione) {
				log("Occorre definire una query di invocazione di stored procedure da eseguire.");
				ret = false;
			}
		}
		return ret;
	}

	/**
	 * Torna true se la where condition applicata torna un result set con almeno
	 * un record
	 * 
	 * @param params
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isRecordPresente(Object... params) throws Exception {
		return selectCount(params) > 0;
	}

	/**
	 * Torna true se la where condition applicata torna un result set vuoto
	 * 
	 * @param params
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isRecordAssente(Object... params) throws Exception {
		return !isRecordPresente();
	}

	/**
	 * Torna true se la where condition applicata torna un result set con almeno
	 * un record
	 * 
	 * @param dto
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isRecordPresenteDTO(Object dto) throws Exception {
		return selectCountDTO(dto) > 0;
	}

	/**
	 * Torna true se la where condition applicata torna un result set vuoto
	 * 
	 * @param dto
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isRecordAssenteDTO(Object dto) throws Exception {
		return !isRecordPresenteDTO(dto);
	}

	/**
	 * Ritorna il numero di record individuati dalla query impostata nel bean
	 * 
	 * @param params
	 * @return Long
	 * @throws Exception
	 */
	public Long selectCount(Object... params) throws Exception {
		if (!checkQuery())
			return null;
		if (isMock())
			return generateInt().longValue();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Long quanti = 0l;
		Date start = null;
		Date end = null;
		String query = null;
		String error = null;
		try {
			query = getCountSql(buildSql(getQuery(), params));
			stmt = getConnection().prepareStatement(query);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				while (rs.next()) {
					quanti = rs.getLong(1);
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(query, start, end, quanti.intValue(), error);
			else
				logQuery(query, start, end, quanti.intValue());
			closeAlls(stmt, rs);
		}
		return quanti;
	}

	/**
	 * Ritorna il numero di record individuati dalla query impostata nel bean. I
	 * parametri di filtro della where condition vengono passati nell'oggetto o.
	 * Tale oggetto deve avere le variabili istanza uguali nel nome ai parametri
	 * usati nella where condition
	 * 
	 * @param o
	 * @return Long
	 * @throws Exception
	 */
	public Long selectCountDTO(Object o) throws Exception {
		if (!checkQuery())
			return null;
		if (isMock())
			return generateInt().longValue();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Long quanti = 0l;
		Date start = null;
		Date end = null;
		String query = null;
		String error = null;
		try {
			query = getCountSql(buildSqlDTO(getQuery(), o));
			stmt = getConnection().prepareStatement(query);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				while (rs.next()) {
					quanti = rs.getLong(1);
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(query, start, end, quanti.intValue(), error);
			else
				logQuery(query, start, end, quanti.intValue());
			closeAlls(stmt, rs);
		}
		return quanti;
	}

	/**
	 * Ritorna una lista di oggetti di tipo K definito dalla Classe c primo
	 * parametro del metodo.Non esegue quindi un mapping ORM all'interno di un
	 * bean di mapping del result set ma torna direttamente una lista di oggetti
	 * della classe specificata (tipicamente String, Date, BigDecimal o altri
	 * tipi java)
	 * 
	 * @param <K>
	 * @param c
	 * @param params
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K> PList<K> selectNoBean(Class<K> c, Object... params) throws Exception {
		return _selectNoBean(c, params);
	}

	/**
	 * Ritorna il primo elemento della lista risultato
	 * 
	 * @param <K>
	 * @param c
	 * @param params
	 * @return K
	 * @throws Exception
	 */
	public <K> K selectOne(Class<K> c, Object... params) throws Exception {
		return (K) getFirstElement(select(c, params));
	}

	/**
	 * Ritorna il primo elemento della lista risultato. I parametri di filtro
	 * della where condition vengono passati nell'oggetto o. Tale oggetto deve
	 * avere le variabili istanza uguali nel nome ai parametri usati nella where
	 * condition
	 * 
	 * @param c
	 * @param <K>
	 * @param o
	 * @return K
	 * @throws Exception
	 */
	public <K> K selectOneDTO(Class<K> c, Object o) throws Exception {
		return (K) getFirstElement(selectDTO(c, o));
	}

	/**
	 * Ritorna il primo elemento della lista risultato di oggetti di tipo K
	 * definito dalla Classe c primo parametro del metodo.
	 * 
	 * @param <K>
	 * @param c
	 * @param params
	 * @return K
	 * @throws Exception
	 */
	public <K> K selectOneNoBean(Class<K> c, Object... params) throws Exception {
		return getFirstElement(selectNoBean(c, params));
	}

	/**
	 * Ritorna il primo elemento della lista risultato di oggetti di tipo K
	 * definito dalla Classe c primo parametro del metodo. I parametri di filtro
	 * della where condition vengono passati nell'oggetto o. Tale oggetto deve
	 * avere le variabili istanza uguali nel nome ai parametri usati nella where
	 * condition
	 * 
	 * @param <K>
	 * @param c
	 * @param o
	 * @return K
	 * @throws Exception
	 */
	public <K> K selectOneNoBeanDTO(Class<K> c, Object o) throws Exception {
		return getFirstElement(selectNoBeanDTO(c, o));
	}

	private <K> PList<K> _selectNoBean(Class<K> c, Object... params) throws Exception {
		if (!checkQuery())
			return null;
		if (isMock())
			return (PList<K>) mockList(c, generateInt());
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Date start = null;
		Date end = null;
		String query = null;
		String error = null;
		PList<K> item = pl();
		try {
			query = buildSql(getQuery(), params);
			stmt = getConnection().prepareStatement(query);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				ResultSetMetaData rsmd = rs.getMetaData();
				while (rs.next()) {
					if (tutte(is(rsmd.getColumnTypeName(1), NUMBER), zero(rsmd.getScale(1)))) {
						item.add(c.cast(rs.getLong(1)));
					} else {
						item.add(c.cast(rs.getObject(1)));
					}
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(query, start, end, item.size(), error);
			else
				logQuery(query, start, end, item.size());
			closeAlls(stmt, rs);
		}
		return item;
	}

	/**
	 * Metodo di selezione tramite dto le cui variabili istanza hanno lo stesso
	 * nome dei parametri impostati nella query nella forma :=nomeParametro. I
	 * parametri passati a null vengono automaticamente esclusi nella formazione
	 * della where condition
	 * 
	 * @param <K>
	 * @param c
	 * @param o
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K> PList<K> selectDTO(Class<K> c, Object o) throws Exception {
		if (!checkQuery())
			return null;
		if (isMock())
			return (PList<K>) mockList(c, generateInt());
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PList<K> elenco = pl();
		Date start = null;
		Date end = null;
		String query = null;
		String error = null;
		try {
			query = buildSqlDTO(getQuery(), o);
			stmt = getConnection().prepareStatement(query);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				PList<String> alias = buildAlias(rs);
				Method[] methods = c.getDeclaredMethods();
				while (rs.next()) {
					elenco.add((K) buildBean(c, rs, alias, methods));
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(query, start, end, elenco.size(), error);
			else
				logQuery(query, start, end, elenco.size());
			closeAlls(stmt, rs);
		}
		return elenco;
	}

	/**
	 * Ritorna una lista di oggetti di tipo K definito dalla Classe c primo
	 * parametro del metodo. I parametri di filtro della where condition vengono
	 * passati nell'oggetto o. Tale oggetto deve avere le variabili istanza
	 * uguali nel nome ai parametri usati nella where condition
	 * 
	 * @param <K>
	 * @param c
	 * @param o
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K> PList<K> selectNoBeanDTO(Class<K> c, Object o) throws Exception {
		if (!checkQuery())
			return null;
		if (isMock())
			return (PList<K>) mockList(c, generateInt());
		PList<K> item = pl();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Date start = null;
		Date end = null;
		String query = null;
		String error = null;
		try {
			query = buildSqlDTO(getQuery(), o);
			stmt = getConnection().prepareStatement(query);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				ResultSetMetaData rsmd = rs.getMetaData();
				while (rs.next()) {
					if (tutte(is(rsmd.getColumnTypeName(1), NUMBER, INTEGER, LONG), zero(rsmd.getScale(1)))) {
						item.add(c.cast(rs.getLong(1)));
					} else {
						item.add(c.cast(rs.getObject(1)));
					}
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(query, start, end, item.size(), error);
			else
				logQuery(query, start, end, item.size());
			closeAlls(stmt, rs);
		}
		return item;
	}

	private String buildSqlDTO(String sql, Object o) throws Exception {
		Field[] attributi = o.getClass().getDeclaredFields();
		Map<String, Object> mappa = new HashMap<String, Object>();
		for (Field att : attributi) {
			aggiungi(mappa, att.getName(), get(o, att.getName()));
		}
		String param = null;
		String valoreStringa = null;
		PList<ColonnaWhereCondition> colonneWhereCondition = getColonneWhereCondition(sql);
		PList<String> parametri = getParametri(sql);
		for (Map.Entry<String, Object> entry : mappa.entrySet()) {
			valoreStringa = null;
			param = str(":", entry.getKey(), SPACE);
			if (isNot(parametri, param)) {
				continue;
			}
			Object val = entry.getValue();
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
					valoreStringa = to_date(d);
				} else {
					valoreStringa = inClause(val);
				}
			} else {

				// sostituisco con la condizione colonna=colonna in modo da
				// annullare la where condition qualora il
				// valore del parametro è NULL (vedi query per filtri di
				// ricerca, metto sempre la query completa nel file di query con
				// tutte
				// le condizioni e poi a runtime, quando un parametro ha valore
				// NULL lo vado a sostituire con qualcosa del tipo
				// MUPRA_SEQ_PRATICA_PK=MUPRA_SEQ_PRATICA_PK in modo da rendere
				// nulla o vana quella condizione identificata dal parametro
				// NULL
				sql = replace(sql, param, parametri, colonneWhereCondition);

			}
			if (notNull(valoreStringa)) {
				sql = replace(sql, param, valoreStringa);
			}
		}
		sql = replace(sql, "^", "");
		return sql;
	}

	private Integer generateInt() {
		return new Random().nextInt(20);
	}

	/**
	 * c è la classe del bean K. Params sono i parametri passati alla query
	 * secondo l'ordine in cui appaiono all'interno dello statement
	 * 
	 * @param <K>
	 * @param c
	 * @param params
	 * @return PList<K>
	 * @throws Exception
	 */
	protected <K> PList<K> selectBean(Class<K> c, Object... params) throws Exception {
		if (!checkQuery())
			return null;
		if (isMock())
			return (PList<K>) mockList(c, generateInt());

		PreparedStatement stmt = null;
		ResultSet rs = null;
		PList<K> elenco = pl();
		Date start = null;
		Date end = null;
		String query = null;
		String error = null;
		try {
			query = buildSql(getQuery(), params);
			stmt = getConnection().prepareStatement(query);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				PList<String> alias = buildAlias(rs);
				Method[] methods = c.getDeclaredMethods();
				while (rs.next()) {
					elenco.add((K) buildBean(c, rs, alias, methods));
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(query, start, end, elenco.size(), error);
			else
				logQuery(query, start, end, elenco.size());
			closeAlls(stmt, rs);

		}
		return elenco;
	}

	private void logQuery(String sql, Date start, Date end, Integer i) {
		sql = encode(sql);
		sql = str(QUERY_PREFIX, OPEN, "%d ", RECORD, CLOSE, " %s ", LEFT_ARROW, sql, " %s");
		setExecTime(sql, start, end, i);
	}

	private String encode(String sql) {
		return replace(sql, "%", KEY_);
	}

	private void logQuery(String sql, Date start, Date end) {
		sql = encode(sql);
		sql = str(QUERY_PREFIX, " %s ", LEFT_ARROW, sql, " %s");
		setExecTime(sql, start, end);
	}

	private void logQuery(String sql, Date start, Date end, String error) {
		sql = encode(sql);
		sql = str(QUERY_PREFIX, ERROR_MARK, SPACE, " %s ", OPEN, "%s", CLOSE, SPACE, LEFT_ARROW, sql, " %s");
		setExecTimeError(sql, start, end, error);
	}

	private void logQuery(String sql, Date start, Date end, Integer i, String error) {
		sql = encode(sql);
		sql = str(QUERY_PREFIX, ERROR_MARK, SPACE, OPEN, "%d ", RECORD, CLOSE, " %s ", OPEN, "%s", CLOSE, SPACE, LEFT_ARROW, sql, " %s");
		setExecTimeError(sql, start, end, i, error);
	}

	private PList<String> buildAlias(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		PList<String> alias = plstr();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			alias.add(rsmd.getColumnLabel(i));
		}
		return alias;
	}

	private void closeAlls(PreparedStatement stmt, ResultSet rs) {
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
			if (isDsMode()) {
				if (notNull(getConnection())) {
					log("-----------CHIUDO LA CONNESSIONE!!!!!!!!-----------");
					getConnection().close();

				}
			}
		} catch (Exception e) {
			log("ERROREEEEEEEEEEEEEEEEEEEEEEEEEEEEE!!!", e);
			throw new RuntimeException(e);
		}
	}

	private <K> K buildBean(Class<K> c, ResultSet rs, PList<String> alias, Method[] methods) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException,
			SQLException {
		K item = (K) Class.forName(c.getName()).newInstance();
		ResultSetMetaData rsmd = rs.getMetaData();
		for (String col : safe(alias)) {
			for (Method method : methods) {
				if (is(method.getName(), SET_CONNECTION)) {
					method.invoke(item, this.getConnection());
				}

				if (is(method.getName(), str(SET, col))) {
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						if (tutte(is(rsmd.getColumnLabel(i), col))) {
							invokeMethod(method, item, rs, col);
						}
					}
					break;
				}
			}
		}
		return item;
	}

	private String callToLowerCase(String sql) {
		sql = despaceString(sql);
		String p1 = substring(sql, "= ", false, false, null, false, false);
		String p2 = substring(p1, null, false, false, SPACE, false, false);
		sql = replace(sql, p2, p2.toLowerCase());
		return sql;
	}

	private String buildSql(String sql, Object... params) throws Exception {
		if (!checkParams(params)) {
			String err = "Check dei parametri fallito. Possibile tentativo di SQL INJECTION, QUERY NON ESEGUITA!";
			log(err);
			throw new Exception(err);
		}
		Map<String, Object> mappaParametri = getSQLParamMap(sql, params);
		String param = null;
		PList<String> parametri = getParametri(sql);
		PList<ColonnaWhereCondition> colonneWhereCondition = getColonneWhereCondition(sql);
		for (Map.Entry<String, Object> entry : mappaParametri.entrySet()) {
			param = str(":", entry.getKey(), SPACE);
			if (notNull(entry.getValue())) {
				sql = sql.replaceAll(param, getValore(entry.getValue()));
			} else {
				// sostituisco con la condizione colonna=colonna in modo da
				// annullare la where condition qualora il
				// valore del parametro è NULL (vedi query per filtri di
				// ricerca, metto sempre la query completa nel file di query con
				// tutte
				// le condizioni e poi a runtime, quando un parametro ha valore
				// NULL lo vado a sostituire con qualcosa del tipo
				// MUPRA_SEQ_PRATICA_PK=MUPRA_SEQ_PRATICA_PK in modo da rendere
				// nulla o vana quella condizione identificata dal parametro
				// NULL
				sql = replace(sql, param, parametri, colonneWhereCondition);

			}
		}
		sql = replace(sql, "^", "");
		return sql;
	}

	private boolean checkParams(Object[] params) {
		boolean check = true;
		for (Object par : params) {
			if (par instanceof String) {
				if (isPossibleSqlInjection((String) par)) {
					log("Parametro ", par, " possibile SQL INJECTION.");
					check = false;
					break;
				}
			}

		}
		return check;
	}

	private boolean isPossibleSqlInjection(String input) {
		if (notNull(input)) {
			for (String keyword : DaoHelper.sqlKeywords) {
				if (input.toUpperCase().contains(keyword)) {
					return true; // Potenziale SQL injection rilevata
				}
			}
		}
		return false; // La stringa di input non sembra essere una SQL injection
	}

	private Map<String, Object> getSQLParamMap(String sql, Object... valori) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Pattern pattern = Pattern.compile(":(.*?)( |$)");
		Matcher matcher = pattern.matcher(sql);
		int i = 0;
		try {
			String paramName = null;
			while (matcher.find()) {
				paramName = matcher.group(1);
				if (!parameters.containsKey(paramName)) {
					parameters.put(paramName, valori[i]);
					i++;
				}
			}
		} catch (Exception e) {
		}
		return parameters;
	}

	private PList<String> getParametri(String sql) {
		Pattern pattern = Pattern.compile(":(.*?)( |$)");
		Matcher matcher = pattern.matcher(sql);
		PList<String> p = plstr();
		try {
			while (matcher.find()) {
				p.add(str(":", matcher.group(1), SPACE));
			}
		} catch (Exception e) {
		}
		return p;
	}

	private PList<String> getOperatoriSQL() {
		String operators = "not in (,in (,like,not between,between,<>,>=,<=,>,<,=";
		return toListString(operators, COMMA);
	}

	private ColonnaWhereCondition getColumn(String s, Integer posizione) {
		ColonnaWhereCondition c = null;
		s = s.trim();
		Integer index = null;
		String operatoreLogico = null;
		boolean trovato = false;
		for (String op : safe(getOperatoriSQL())) {
			if (s.toUpperCase().endsWith(op.toUpperCase())) {
				trovato = true;
				index = s.toUpperCase().lastIndexOf(op.toUpperCase());
				if (index != -1) {
					s = substring(s, null, false, false, op, false, true);
					operatoreLogico = op;
					break;
				}
			}
		}
		boolean custom = false;
		if (tutte(trovato, notNull(s))) {
			s = s.trim();
			if (isLike(s, "^")) {
				custom = true;
				s = substring(s, "^", false, false, "^", false, true);
			} else {
				s = substring(s, " WHERE ", false, true, null, false, false);
				s = substring(s, " AND ", false, true, null, false, false);
				s = substring(s, " OR ", false, true, null, false, false);
				s = replace(s, "AND", "");
				s = replace(s, "OR", "");
			}
			s = s.trim();
			if (notNull(s)) {
				c = new ColonnaWhereCondition();
				c.setPosizione(posizione);
				if (!custom)
					s = substring(s, SPACE, false, true, null, false, false);
				c.setNome(s);
				c.setOperatoreLogico(operatoreLogico);
				if (isLike(operatoreLogico.toUpperCase(), "between".toUpperCase())) {
					c.setBetweenClause(true);
				}
				if (isLike(operatoreLogico.toUpperCase(), "in (".toUpperCase())) {
					c.setInClause(true);
				}
			}
		}
		return c;
	}

	private PList<ColonnaWhereCondition> getColonneWhereCondition(String sql) {
		sql = despaceString(sql);
		String[] elems = sql.split(":(.*?)( |$)");
		PList<ColonnaWhereCondition> colonne = pl();
		int i = 1;
		int limite = elems.length;
		for (int j = 0; j < limite; j++) {
			String s = elems[j];
			s = s.trim();
			ColonnaWhereCondition c = getColumn(s, i);
			if (notNull(c)) {
				colonne.add(c);
				if (c.isBetweenClause()) {
					i++;
					colonne.add(c);
				}
				i++;
			}
		}
		return colonne;
	}

	private class ColonnaWhereCondition {
		private String nome;
		private boolean inClause;
		private boolean betweenClause;
		private Integer posizione;
		private boolean diversoDa;
		private String operatoreLogico;

		public String getOperatoreLogico() {
			return operatoreLogico;
		}

		public void setOperatoreLogico(String operatoreLogico) {
			this.operatoreLogico = operatoreLogico;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public boolean isInClause() {
			return inClause;
		}

		public void setInClause(boolean inClause) {
			this.inClause = inClause;
		}

		public boolean isBetweenClause() {
			return betweenClause;
		}

		public void setBetweenClause(boolean betweenClause) {
			this.betweenClause = betweenClause;
		}

		public Integer getPosizione() {
			return posizione;
		}

		public void setPosizione(Integer posizione) {
			this.posizione = posizione;
		}

		public boolean isDiversoDa() {
			return diversoDa;
		}

		public void setDiversoDa(boolean diversoDa) {
			this.diversoDa = diversoDa;
		}
	}

	private String cutOperatorAndEqual(String sql) {
		for (String op : safe(getOperatoriSQL())) {
			if (sql.toUpperCase().trim().endsWith(op.toUpperCase().trim())) {
				sql = substring(sql, null, false, false, op, false, true);
				break;
			}
		}
		sql = str(sql, " = ");
		return sql;
	}

	private String replace(String sql, String nullParam, PList<String> param, PList<ColonnaWhereCondition> colonne) {
		PList<Integer> indiciParametroNull = getIndexesOfElement(param, nullParam);
		int i = 0;
		for (String p_ : safe(param)) {
			if (is(p_, nullParam)) {
				ColonnaWhereCondition c = findColonna(colonne, indiciParametroNull.get(i));
				if (!c.isBetweenClause()) {
					String inizio = substring(sql, null, false, false, p_, false, false);
					inizio = cutOperatorAndEqual(inizio);
					String fine = substring(sql, p_, true, false, null, true, false);
					if (c.isInClause()) {
						String fine_start = substring(fine, p_, true, false, CLOSE_PAR, false, false);
						String fine_end = substring(fine, CLOSE_PAR, false, false, null, true, false);
						fine = fine_start + fine_end;
					}
					sql = str(inizio, fine);
					sql = sql.replaceFirst(p_, str(c.getNome(), SPACE));
				} else {
					sql = sql.replaceFirst(p_, str(c.getNome(), SPACE));
				}
				i++;
			}
		}
		return sql;
	}

	private PList<Integer> getIndexesOfElement(PList<String> param, String nullParam) {
		PList<Integer> indici = pl();
		int i = 0;
		for (String p_ : safe(param)) {
			i++;
			if (is(p_.trim(), nullParam.trim())) {
				indici.add(i);
			}
		}
		return indici;
	}

	private ColonnaWhereCondition findColonna(PList<ColonnaWhereCondition> colonne, Integer indiceNull) {
		ColonnaWhereCondition colonna = null;
		for (ColonnaWhereCondition c : safe(colonne)) {
			if (is(c.getPosizione(), indiceNull)) {
				colonna = c;
				break;
			} else {
				if (c.isBetweenClause() && is(c.getPosizione(), indiceNull, new Integer(indiceNull - 1))) {
					colonna = c;
					break;
				}
			}
		}
		return colonna;
	}

	private String inClause(Object value) {
		String v = null;
		if (value instanceof List) {
			v = concatenaListaForInClause((List) value, COMMA);
		} else if (value instanceof Number) {
			if (value instanceof BigDecimal) {
				v = getBigDecimal((BigDecimal) value).toString();
			} else {
				v = str(value.toString(), SPACE);
			}
		} else {
			String valoreStringa = String.valueOf(value);
			v = apiceString(valoreStringa);
		}
		return Matcher.quoteReplacement(v);
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

	/**
	 * Imposta il nuovo valore della variabile queryTimeout in secondi
	 * 
	 * @param queryTimeout
	 */
	public void setQueryTimeout(Integer queryTimeout) {
		this.queryTimeout = queryTimeout;
	}

	protected String getQueryProp(String nomeQuery) throws IOException {
		return p.readPropertyFromClassPath(getClass(), QUERY_FILE, nomeQuery);
	}

	protected String getQueryProp(String nomeFile, String nomeQuery) throws IOException {
		return p.readPropertyFromClassPath(getClass(), nomeFile, nomeQuery);
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

	/**
	 * Ritorna una stringa che rappresenta il tempo di esecuzione della query di
	 * questa entity
	 * 
	 * @return String
	 */
	public String getExecTime() {
		return execTime;
	}

	private void setExecTime(String sql, Date start, Date end, Integer i) {
		this.execTime = str(OPEN, elapsedTime(start, end), CLOSE);
		sql = String.format(sql, i, getExecTime(), getExecTime());
		sql = decode(sql);
		setQueryEseguita(sql);
		if (isLogWhileRunning())
			log(sql);
		addQuery();
	}

	private void setExecTime(String sql, Date start, Date end) {
		this.execTime = str(OPEN, elapsedTime(start, end), CLOSE);
		sql = String.format(sql, getExecTime(), getExecTime());
		sql = decode(sql);
		setQueryEseguita(sql);
		if (isLogWhileRunning())
			log(sql);
		addQuery();
	}

	private String decode(String sql) {
		return replace(sql, KEY_, "%");
	}

	private void setExecTimeError(String sql, Date start, Date end, String error) {
		this.execTime = str(OPEN, elapsedTime(start, end), CLOSE);
		sql = String.format(sql, getExecTime(), error, getExecTime());
		sql = decode(sql);
		setQueryEseguita(sql);
		if (isLogWhileRunning())
			log(sql);
		addQuery();
	}

	private void setExecTimeError(String sql, Date start, Date end, Integer i, String error) {
		this.execTime = str(OPEN, elapsedTime(start, end), CLOSE);
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

	/**
	 * Ritorna il valore della variabile queryTimeout impostata di default a 60
	 * sec.
	 * 
	 * @return Integer
	 */
	public Integer getQueryTimeout() {
		return queryTimeout;
	}

	private String truncQuery(String sql) {
		return substring(sql, QUERY_PREFIX, false, false, null, false, false);
	}

	protected String getQueryName() {
		return queryName;
	}

	protected void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public boolean isLogWhileRunning() {
		return logWhileRunning;
	}

	public void setLogWhileRunning(boolean logWhileRunning) {
		this.logWhileRunning = logWhileRunning;
	}

	public boolean isMock() {
		return mock;
	}

	public void setMock(boolean mock) {
		this.mock = mock;
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

	// ----------------------------PREPARED
	// STATEMENT--------------------------------

	/**
	 * Esegue la query di selezione in modalità preparedStatement.Il parametro
	 * tipi è l'array di tipi java.sql.Types e valori sono i corrispondenti
	 * valori passati al posto dei placeholder '?'
	 * 
	 * 
	 * @param c
	 * @param tipi
	 * @param valori
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K> PList<K> selectPs(Class<K> c, int[] tipi, Object... valori) throws Exception {
		return selectBeanPs(c, tipi, valori);
	}

	/**
	 * Modalità PreparedStatement con la query con i placeholder '?'. Esegue le
	 * select con paginazione. Numero pagina indica quale numero di pagina si
	 * desidera caricare. Quanti per pagina indica quanto grande deve essere la
	 * pagina, ossia quanti record si devono mostrare per pagina. Tipi è l'array
	 * di tipi java.sql.Types.Valori è l'elenco ordinato dei valori da
	 * sostituire ai placeholder '?'.
	 * 
	 * @param <K>
	 * @param c
	 * @param numeroPagina
	 * @param quantiPerPagina
	 * @param tipi
	 *            sql
	 * @param valori
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K> PList<K> selectPaginatedBeanPs(Class<K> c, Integer numeroPagina, Integer quantiPerPagina, int[] tipi, Object... valori) throws Exception {
		if (isMock())
			return (PList<K>) mockList(c, generateInt());
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PList<K> elenco = pl();
		String query = getQuery();
		Date start = null;
		Date end = null;
		String error = null;
		try {
			String query_ = addPaginationClause(query, new Pagination(numeroPagina, quantiPerPagina));
			stmt = getConnection().prepareStatement(query_);
			stmt.setQueryTimeout(getQueryTimeout());
			ps(query_, stmt, tipi, valori);
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				PList<String> alias = buildAlias(rs);
				Method[] methods = c.getDeclaredMethods();
				while (rs.next()) {
					elenco.add((K) buildBean(c, rs, alias, methods));
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(query, start, end, elenco.size(), error, valori);
			else
				logQuery(query, start, end, elenco.size(), valori);
			closeAlls(stmt, rs);
		}
		return elenco;
	}

	private void logQuery(String sql, Date start, Date end, Integer i, String error, Object... valori) {
		sql = encode(sql);
		sql = str(QUERY_PREFIX, ERROR_MARK, SPACE, OPEN, "%d ", RECORD, CLOSE, " %s ", OPEN, "%s", CLOSE, SPACE, LEFT_ARROW, sql, " %s", getBoundedValues(valori));
		setExecTimeError(sql, start, end, i, error);
	}

	private void logQuery(String sql, Date start, Date end, Integer i, Object... valori) {
		sql = encode(sql);
		sql = str(QUERY_PREFIX, OPEN, "%d ", RECORD, CLOSE, " %s ", LEFT_ARROW, sql, " %s", getBoundedValues(valori));
		setExecTime(sql, start, end, i);
	}

	/**
	 * Modalità PreparedStatement con la query con i placeholder '?'. Tipi è
	 * l'array di tipi java.sql.Types.Valori è l'elenco ordinato dei valori da
	 * sostituire ai placeholder '?'. Ritorna il primo elemento della lista
	 * risultato
	 * 
	 * @param <K>
	 * @param c
	 * @param tipi
	 * @param valori
	 * @return K
	 * @throws Exception
	 */
	public <K> K selectOnePs(Class<K> c, int[] tipi, Object... valori) throws Exception {
		return (K) getFirstElement(selectPs(c, tipi, valori));
	}

	/**
	 * Modalità PreparedStatement con la query con i placeholder '?'. Tipi è
	 * l'array di tipi java.sql.Types.Valori è l'elenco ordinato dei valori da
	 * sostituire ai placeholder '?'.Ritorna il primo elemento della lista
	 * risultato di oggetti di tipo K definito dalla Classe c primo parametro
	 * del metodo.
	 * 
	 * @param <K>
	 * @param tipi
	 *            sql
	 * @param valori
	 * @return K
	 * @throws Exception
	 */
	public <K> K selectOneNoBeanPs(Class<K> c, int[] tipi, Object... valori) throws Exception {
		return getFirstElement(selectNoBeanPs(c, tipi, valori));
	}

	/**
	 * Modalità PreparedStatement con la query con i placeholder '?'. Ritorna
	 * una lista di oggetti di tipo K definito dalla Classe c primo parametro
	 * del metodo.Non esegue quindi un mapping ORM all'interno di un bean di
	 * mapping del result set ma torna direttamente una lista di oggetti della
	 * classe specificata (tipicamente String, Date, BigDecimal o altri tipi
	 * java).Tipi è l'array di tipi java.sql.Types.Valori è l'elenco ordinato
	 * dei valori da sostituire ai placeholder '?'.
	 * 
	 * @param <K>
	 * @param tipi
	 *            sql
	 * @param valori
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K> PList<K> selectNoBeanPs(Class<K> c, int[] tipi, Object... valori) throws Exception {
		return _selectNoBeanPs(c, tipi, valori);
	}

	/**
	 * Modalità preparedstatement con la query sql ocn i placeholder '?'.Tipi è
	 * l'array di tipi java.sql.Types.Valori è l'elenco ordinato dei valori da
	 * sostituire ai placeholder '?'. Ritorna il numero di record individuati
	 * dalla query impostata nel bean
	 * 
	 * @param tipi
	 *            sql
	 * @param valori
	 * @return Long
	 * @throws Exception
	 */
	public Long selectCountPs(int tipi[], Object... valori) throws Exception {
		if (isMock())
			return generateInt().longValue();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Long quanti = 0l;
		Date start = null;
		Date end = null;
		String query = null;
		String error = null;
		try {
			query = getCountSql(getQuery());
			stmt = getConnection().prepareStatement(query);
			stmt.setQueryTimeout(getQueryTimeout());
			ps(query, stmt, tipi, valori);
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				while (rs.next()) {
					quanti = rs.getLong(1);
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(query, start, end, quanti.intValue(), error, valori);
			else
				logQuery(query, start, end, quanti.intValue(), valori);
			closeAlls(stmt, rs);
		}
		return quanti;
	}

	protected <K> PList<K> selectBeanPs(Class<K> c, int tipi[], Object... valori) throws Exception {
		if (isMock())
			return (PList<K>) mockList(c, generateInt());
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PList<K> elenco = pl();
		Date start = null;
		Date end = null;
		String error = null;
		try {
			stmt = getConnection().prepareStatement(getQuery());
			stmt.setQueryTimeout(getQueryTimeout());
			ps(getQuery(), stmt, tipi, valori);
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				PList<String> alias = buildAlias(rs);
				Method[] methods = c.getDeclaredMethods();
				while (rs.next()) {
					elenco.add((K) buildBean(c, rs, alias, methods));
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(getQuery(), start, end, elenco.size(), error, valori);
			else
				logQuery(getQuery(), start, end, elenco.size(), valori);
			closeAlls(stmt, rs);

		}
		return elenco;
	}

	/**
	 * Esegue le select con paginazione. Numero pagina indica quale numero di
	 * pagina si desidera caricare. Quanti per pagina indica quanto grande deve
	 * essere la pagina, ossia quanti record si devono mostrare per pagina.
	 * 
	 * @param <K>
	 * @param c
	 * @param numeroPagina
	 * @param quantiPerPagina
	 * @param params
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K> PList<K> selectPaginatedBean(Class<K> c, Integer numeroPagina, Integer quantiPerPagina, Object... params) throws Exception {
		if (!checkQuery())
			return null;
		if (isMock())
			return (PList<K>) mockList(c, generateInt());
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PList<K> elenco = pl();
		String sql = getQuery();
		String query = buildSql(sql, params);
		Date start = null;
		Date end = null;
		String error = null;
		// BigDecimal recordTotali = bd(selectCount(params));
		// Long resto = recordTotali.longValue() % quantiPerPagina;
		// Long quantePagine = dividi(recordTotali,
		// getBigDecimal(quantiPerPagina)).longValue();
		// if (resto > 0)
		// quantePagine++;
		// StringBuffer pagine = new StringBuffer();
		// for (int k = 1; k <= quantePagine; k++) {
		// pagine.append(k).append(DASH);
		// }
		// if (pagine.length() > 1) {
		// pagine.setLength(pagine.length() - 3);
		// }
		try {
			String query_ = addPaginationClause(query, new Pagination(numeroPagina, quantiPerPagina));
			stmt = getConnection().prepareStatement(query_);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				PList<String> alias = buildAlias(rs);
				Method[] methods = c.getDeclaredMethods();
				while (rs.next()) {
					elenco.add((K) buildBean(c, rs, alias, methods));
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(query, start, end, elenco.size(), error);
			else
				logQuery(query, start, end, elenco.size());
			closeAlls(stmt, rs);
		}
		return elenco;
	}

	/**
	 * Esegue le select con paginazione. Numero pagina indica quale numero di
	 * pagina si desidera caricare. Quanti per pagina indica quanto grande deve
	 * essere la pagina, ossia quanti record si devono mostrare per pagina. I
	 * parametri di filtro della where condition vengono passati nell'oggetto o.
	 * Tale oggetto deve avere le variabili istanza uguali nel nome ai parametri
	 * usati nella where condition
	 * 
	 * @param <K>
	 * @param numeroPagina
	 * @param c
	 * @param quantiPerPagina
	 * @param o
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K> PList<K> selectPaginatedBeanDTO(Class<K> c, Integer numeroPagina, Integer quantiPerPagina, Object o) throws Exception {
		if (!checkQuery())
			return null;
		if (isMock())
			return (PList<K>) mockList(c, generateInt());
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PList<K> elenco = pl();
		String sql = getQuery();
		String query = buildSqlDTO(sql, o);
		Date start = null;
		Date end = null;
		String error = null;
		try {
			String query_ = addPaginationClause(query, new Pagination(numeroPagina, quantiPerPagina));
			stmt = getConnection().prepareStatement(query_);
			stmt.setQueryTimeout(getQueryTimeout());
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				PList<String> alias = buildAlias(rs);
				Method[] methods = c.getDeclaredMethods();
				while (rs.next()) {
					elenco.add((K) buildBean(c, rs, alias, methods));
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(query, start, end, elenco.size(), error);
			else
				logQuery(query, start, end, elenco.size());
			closeAlls(stmt, rs);

		}
		return elenco;
	}

	private String getBoundedValues(Object... valori) {
		if (null == valori || valori.length == 0)
			return "[No Bounded Values]";
		return str("[", strSepDash(valori), "]");

	}

	private <K> PList<K> _selectNoBeanPs(Class<K> c, int[] tipi, Object... valori) throws Exception {
		if (isMock())
			return (PList<K>) mockList(c, generateInt());
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Date start = null;
		Date end = null;
		String error = null;
		PList<K> item = pl();
		try {
			stmt = getConnection().prepareStatement(getQuery());
			stmt.setQueryTimeout(getQueryTimeout());
			ps(getQuery(), stmt, tipi, valori);
			start = now();
			rs = stmt.executeQuery();
			end = now();
			if (notNull(rs)) {
				ResultSetMetaData rsmd = rs.getMetaData();
				while (rs.next()) {
					if (tutte(is(rsmd.getColumnTypeName(1), NUMBER), zero(rsmd.getScale(1)))) {
						item.add(c.cast(rs.getLong(1)));
					} else {
						item.add(c.cast(rs.getObject(1)));
					}
				}
			}
		} catch (Exception e) {
			end = now();
			error = e.getMessage();
			throw (e);
		} finally {
			if (notNull(error))
				logQuery(getQuery(), start, end, item.size(), error, valori);
			else
				logQuery(getQuery(), start, end, item.size(), valori);
			closeAlls(stmt, rs);
		}
		return item;
	}

}
