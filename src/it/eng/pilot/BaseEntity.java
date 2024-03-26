package it.eng.pilot;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe base per gli oggetti Entity. Ogni oggetto Entity, per essere tale ,
 * deve necessariamente estendere questa classe. Tale classe infatti offre tutte
 * le funzionalità di definizione della where condition, ordinamento, e
 * impostazione di campi a null nelle update. I parametri definiti nella where
 * condition e passati a Null VENGONO AUTOMATICAMENTE ESCLUSI dalla formazione
 * della where condition.
 * 
 * @author Antonio Corinaldi
 * 
 */
public abstract class BaseEntity extends BaseDaoEntity {

	private static final long serialVersionUID = 6198001931561158088L;
	private PList<Where> wc = pl();
	private PList<String> fieldsToExcludeInUpdate = pl();
	private PList<String> fieldsToUpdate = pl();
	private PList<String> orderBy = plstr();
	private transient Connection connection;
	private String codUtenteCostruttore;
	private String codApplCostruttore;
	private PList<String> container = plstr();

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione Column
	 * stato=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setStato(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.stato()) {
					setAttValue(value, att);
				}
			}
		}
		return this;
	}

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione @Column
	 * fascicolo=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setFascicolo(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.fascicolo()) {
					setAttValue(value, att);
				}
			}
		}
		return this;
	}

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione Column
	 * codice=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setCodice(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.codice()) {
					setAttValue(value, att);
				}
			}
		}
		return this;
	}

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione Column
	 * descrizione=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setDescrizione(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.descrizione()) {
					setAttValue(value, att);
				}
			}
		}
		return this;
	}

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione Column
	 * tipo=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setTipo(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.tipo()) {
					setAttValue(value, att);
				}
			}
		}
		return this;
	}

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione Column
	 * sede=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setSede(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.sede()) {
					setAttValue(value, att);
				}
			}
		}
		return this;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo stato=true
	 * dell'annotazione Column
	 * 
	 * @param <K>
	 * @return K
	 */
	public <K> K getStato() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.stato()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo
	 * 
	 * fascicolo=true dell'annotazione Column
	 * 
	 * @param <K>
	 * @return K
	 */
	public <K> K getFascicolo() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.fascicolo()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo codice=true
	 * dell'annotazione Column
	 * 
	 * @param <K>
	 * @return K
	 */
	public <K> K getCodice() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.codice()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo
	 * descrizione=true dell'annotazione Column
	 * 
	 * @param <K>
	 * @return K
	 */
	public <K> K getDescrizione() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.descrizione()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo
	 * 
	 * tipo=true dell'annotazione Column
	 * 
	 * @param <K>
	 * @return K
	 * 
	 */
	public <K> K getTipo() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.tipo()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo sede=true
	 * dell'annotazione Column
	 * 
	 * @param <K>
	 * @return K
	 * 
	 */
	public <K> K getSede() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.sede()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo @stato1=true
	 * dell'annotazione @Column
	 * 
	 * @param <K>
	 * @return K
	 */
	public <K> K getStato1() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.stato1()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo @ente=true
	 * dell'annotazione @Column
	 * 
	 * @param <K>
	 * @return K
	 */

	public <K> K getEnte() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.ente()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo @iscritto=true
	 * dell'annotazione @Column
	 * 
	 * @param <K>
	 * @return K
	 */
	public <K> K getIscritto() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.iscritto()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo @cassa=true
	 * dell'annotazione @Column
	 * 
	 * @param <K>
	 * @return K
	 */
	public <K> K getCassa() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.cassa()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo @progr=true
	 * dell'annotazione @Column
	 * 
	 * @param <K>
	 * @return K
	 */
	public <K> K getProgressivo() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.progr()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo
	 * 
	 * startDate=true dell'annotazione Column
	 * 
	 * @param <K>
	 * @return K
	 */
	public <K> K getDataInizio() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.startDate()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	/**
	 * Ritorna il valore della variabile istanza che ha attributo
	 * 
	 * endDate=true dell'annotazione Column
	 * 
	 * @param <K>
	 * @return K
	 */
	public <K> K getDataFine() {
		K ret = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.endDate()) {
					ret = getAttValue(att);
				}
			}
		}
		return ret;
	}

	private <K> K getAttValue(Field att) {
		K ret = null;
		try {
			ret = invokeGetter(att);
		} catch (Exception e) {
			logError("Errore durante il recupero del valore dell'attributo ", att.getName());
		}
		return ret;
	}

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione @Column
	 * stato1=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setStato1(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.stato1()) {
					setAttValue(value, att);

				}
			}
		}
		return this;
	}

	private <K> void setAttValue(K value, Field att) {
		try {
			invokeSetter(value, att);
		} catch (Exception e) {
			logError("Errore durante l'impostazione del valore ", value, "per l'attributo", att.getName(), e);
		}
	}

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione @Column
	 * ente=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setEnte(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.ente()) {
					setAttValue(value, att);
				}
			}
		}
		return this;
	}

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione @Column
	 * iscritto=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setIscritto(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.iscritto()) {
					setAttValue(value, att);
				}
			}
		}
		return this;
	}

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione @Column
	 * cassa=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setCassa(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.cassa()) {
					setAttValue(value, att);
				}
			}
		}
		return this;
	}

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione @Column
	 * progr=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setProgr(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.progr()) {
					setAttValue(value, att);
				}
			}
		}
		return this;
	}

	/**
	 * Imposta il valore delle variabili istanza dell'entity che hanno attributi
	 * dell'annotazione @Column ente=true,iscritto=true,cassa=true ossia che
	 * individuano la terna [Ente-Iscritto-Cassa]
	 * 
	 * @param <K>
	 * @param idEnte
	 * @param idIscritto
	 * @param idCassa
	 * @return BaseEntity
	 * @throws Exception
	 */
	public <K> BaseEntity setTernaEnteIscrittoCassa(K idEnte, K idIscritto, K idCassa) throws Exception {
		setEnte(idEnte);
		setIscritto(idIscritto);
		setCassa(idCassa);
		return this;
	}

	/**
	 * Metodo alias di setTernaEnteIscrittoCassa
	 * 
	 * @param <K>
	 * @param idEnte
	 * @param idIscritto
	 * @param idCassa
	 * @return BaseEntity
	 * @throws Exception
	 */
	public <K> BaseEntity setTerna(K idEnte, K idIscritto, K idCassa) throws Exception {
		return setTernaEnteIscrittoCassa(idEnte, idIscritto, idCassa);
	}

	/**
	 * Imposta where condition sulla terna ente-iscritto-cassa con la relazione
	 * di equality
	 * 
	 * @param <K>
	 * @param idEnte
	 * @param idIscritto
	 * @param idCassa
	 * @return BaseEntity
	 */
	public <K> BaseEntity terna(K idEnte, K idIscritto, K idCassa) {
		return eq(getFieldEnte(), idEnte).eq(getFieldIscritto(), idIscritto).eq(getFieldCassa(), idCassa);
	}

	/**
	 * Imposta where condition sulla quaterna ente-iscritto-cassa-progressivo
	 * con la relazione di equality
	 * 
	 * @param <K>
	 * @param idEnte
	 * @param idIscritto
	 * @param idCassa
	 * @param idProgr
	 * @return BaseEntity
	 * @throws Exception
	 */
	public <K> BaseEntity quaterna(K idEnte, K idIscritto, K idCassa, K idProgr) throws Exception {
		return terna(idEnte, idIscritto, idCassa).eq(getFieldProgr(), idProgr);
	}

	/**
	 * Imposta il valore delle variabili istanza dell'entity che hanno attributi
	 * dell'annotazione @Column ente=true,iscritto=true,cassa=true,progr=true
	 * ossia che individuano la quaterna [Ente-Iscritto-Cassa-Progressivo]
	 * 
	 * @param <K>
	 * @param idEnte
	 * @param idIscritto
	 * @param idCassa
	 * @param idProgr
	 * @return BaseEntity
	 * @throws Exception
	 */
	public <K> BaseEntity setQuaternaEnteIscrittoCassaProgr(K idEnte, K idIscritto, K idCassa, K idProgr) throws Exception {
		setTernaEnteIscrittoCassa(idEnte, idIscritto, idCassa);
		setProgr(idProgr);
		return this;
	}

	/**
	 * Metodo alias di setQuaternaEnteIscrittoCassaProgr
	 * 
	 * @param <K>
	 * @param idEnte
	 * @param idIscritto
	 * @param idCassa
	 * @param idProgr
	 * @return BaseEntity
	 * @throws Exception
	 */
	public <K> BaseEntity setQuaterna(K idEnte, K idIscritto, K idCassa, K idProgr) throws Exception {
		return setQuaternaEnteIscrittoCassaProgr(idEnte, idIscritto, idCassa, idProgr);
	}

	/**
	 * Invoca il metodo setter che termina con "CODUTENTE" passando il valore
	 * codUtente
	 * 
	 * @param codUtente
	 * @return BaseEntity
	 * @throws Exception
	 */
	public BaseEntity setCodUtente(String codUtente) throws Exception {
		for (Method method : getMetodiEnt()) {
			if (tutte(method.getName().toUpperCase().startsWith(SET), method.getName().toUpperCase().endsWith((METHOD_COD_UTENTE.toUpperCase())))) {
				method.invoke(this, codUtente);
				break;
			}
		}
		return this;
	}

	/**
	 * Invoca il metodo getter che termina con "CODUTENTE"
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getCodUtente() throws Exception {
		String codUtente = null;
		for (Method method : getMetodiEnt()) {
			if (tutte(method.getName().toUpperCase().startsWith(GET), method.getName().toUpperCase().endsWith((METHOD_COD_UTENTE.toUpperCase())))) {
				codUtente = (String) method.invoke(this);
				break;
			}
		}
		return codUtente;
	}

	/**
	 * Invoca il metodo getter che termina con "CODAPP"
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getCodApp() throws Exception {
		String codApp = null;
		for (Method method : getMetodiEnt()) {
			if (tutte(method.getName().toUpperCase().startsWith(GET), method.getName().toUpperCase().endsWith((METHOD_COD_APPL.toUpperCase())))) {
				codApp = (String) method.invoke(this);
				break;
			}
		}
		return codApp;
	}

	/**
	 * Invoca il metodo setter che termina con "CODAPP" passando il valore
	 * codApp
	 * 
	 * @param codApp
	 * @return BaseEntity
	 * @throws Exception
	 */
	public BaseEntity setCodApp(String codApp) throws Exception {
		for (Method method : getMetodiEnt()) {
			if (tutte(method.getName().toUpperCase().startsWith(SET), method.getName().toUpperCase().endsWith((METHOD_COD_APPL.toUpperCase())))) {
				method.invoke(this, codApp);
				break;
			}
		}
		return this;
	}

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione @Column
	 * startDate=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setStartDate(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.startDate()) {
					setAttValue(value, att);
				}
			}
		}
		return this;
	}

	/**
	 * Imposta il valore della variabile istanza dell'entity a value. La
	 * variabile istanza è quella che ha l'attributo dell'annotazione @Column
	 * endDate=true
	 * 
	 * @param <K>
	 * @param value
	 * @return BaseEntity
	 */
	public <K> BaseEntity setEndDate(K value) {
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.endDate()) {
					setAttValue(value, att);
				}
			}
		}
		return this;
	}

	/**
	 * Metodo alias di setEndDate
	 * 
	 * @param <K>
	 * @param endDate
	 * @return BaseEntity
	 */
	public <K> BaseEntity setDataFine(K endDate) {
		return setEndDate(endDate);
	}

	/**
	 * Metodo alias di setStartDate
	 * 
	 * @param <K>
	 * @param startDate
	 * @return BaseEntity
	 */
	public <K> BaseEntity setDataInizio(K startDate) {
		return setStartDate(startDate);
	}

	/**
	 * @param conn
	 */
	public BaseEntity(Connection conn) {
		setConnection(conn);

	}

	/**
	 * Costruttore che oltre a Connection accetta come parametro anche una
	 * PList<String> destinata a contenere le query sql eseguite e la relativa
	 * tempistica di esecuzione
	 * 
	 * @param conn
	 * @param container
	 */
	public BaseEntity(Connection conn, PList<String> container) {
		this(conn);
		setContainer(container);

	}

	/**
	 * Oltre a connection, questo costruttore prevede anche codUtente e codAppl
	 * che verranno applicati automaticamente (quindi senza passarli nell'entity
	 * stessa) ai relativi campi della entity *COD_UTENTE e *COD_APPL nelle
	 * query di update/delete/insert. Se si impostano i campi nell'Entity,
	 * questi avranno la precedenza (override) sui valori passati al costruttore
	 * 
	 * @param conn
	 * @param codUtente
	 * @param codAppl
	 */
	public BaseEntity(Connection conn, String codUtente, String codAppl) {
		this(conn);
		setCodUtenteCostruttore(codUtente);
		setCodApplCostruttore(codAppl);
	}

	/**
	 * Oltre a connection , questo costruttore prevede anche codUtente e codAppl
	 * che verranno applicati automaticamente (quindi senza passarli nell'entity
	 * stessa) ai relativi campi della entity *COD_UTENTE e *COD_APPL nelle
	 * query di update/delete/insert. Se si impostano i campi nell'Entity,
	 * questi avranno la precedenza (override) sui valori passati al
	 * costruttore. Accetta inoltre come parametro anche una PList<String>
	 * destinata a contenere le query sql eseguite e la relativa tempistica di
	 * esecuzione
	 * 
	 * @param conn
	 * @param codUtente
	 * @param codAppl
	 * @param container
	 */
	public BaseEntity(Connection conn, String codUtente, String codAppl, PList<String> container) {
		this(conn, container);
		setCodUtenteCostruttore(codUtente);
		setCodApplCostruttore(codAppl);
	}

	/**
	 * Oltre a connection questo costruttore prevede anche codUtente che verrà
	 * applicato automaticamente (quindi senza passarlo nell'entity stessa) al
	 * relativo campo della entity *COD_UTENTE nelle query di
	 * update/delete/insert. Se si imposta il campo nell'Entity, questo avrà la
	 * precedenza (override) sul valore passato al costruttore
	 * 
	 * @param conn
	 * @param codUtente
	 */
	public BaseEntity(Connection conn, String codUtente) {
		this(conn);
		setCodUtenteCostruttore(codUtente);
	}

	/**
	 * Oltre a connection questo costruttore prevede anche codUtente che verrà
	 * applicato automaticamente (quindi senza passarlo nell'entity stessa) al
	 * relativo campo della entity *COD_UTENTE nelle query di
	 * update/delete/insert. Se si imposta il campo nell'Entity, questo avrà la
	 * precedenza (override) sul valore passato al costruttore Accetta inoltre
	 * come parametro anche una PList<String> destinata a contenere le query sql
	 * eseguite e la relativa tempistica di esecuzione
	 * 
	 * @param conn
	 * @param codUtente
	 * @param container
	 */
	public BaseEntity(Connection conn, String codUtente, PList<String> container) {
		this(conn, container);
		setCodUtenteCostruttore(codUtente);
	}

	public BaseEntity() {
	}

	public PList<Where> getWhereCondition() {
		return wc;
	}

	public BaseEntity setWhereCondition(PList<Where> wc) {
		this.wc = wc;
		return this;
	}

	public PList<String> getFieldsToExcludeInUpdate() {
		return this.fieldsToExcludeInUpdate;
	}

	public BaseEntity setFieldsToExcludeInUpdate(PList<String> fieldsToExclude) {
		this.fieldsToExcludeInUpdate.addAll(fieldsToExclude);
		return this;
	}

	public BaseEntity setFieldsToExcludeInUpdate(String... fieldsToExclude) {
		this.fieldsToExcludeInUpdate.addAll(arrayToList(fieldsToExclude));
		return this;
	}

	public BaseEntity exclude(String... fieldsToExclude) {
		return setFieldsToExcludeInUpdate(fieldsToExclude);
	}

	public PList<String> getOrderBy() {
		return this.orderBy;
	}

	/**
	 * Esegue un ordinamento ascendente per i campi indicati nel parametro
	 * orderBy
	 * 
	 * @param orderBy
	 * @return BaseEntity
	 */
	public BaseEntity orderByASC(String... orderBy) {
		if (null != orderBy) {
			PList<String> ordine = arrayToList(orderBy);
			ordine.add(ASC);
			this.orderBy = ordine;
		}
		return this;
	}

	/**
	 * Esegue un ordinamento discendente per i campi indicati nel parametro
	 * orderBy
	 * 
	 * @param orderBy
	 * @return BaseEntity
	 */
	public BaseEntity orderByDESC(String... orderBy) {
		if (null != orderBy) {
			PList<String> ordine = arrayToList(orderBy);
			ordine.add(DESC);
			this.orderBy = ordine;
		}
		return this;
	}

	/**
	 * Realizza la condizione di = nella clausola where. Termina automaticamente
	 * con AND per concatenare la prossima condizione. Prefix è il prefisso da
	 * usare prima della colonna, suffix è il suffisso al nome di colonna,
	 * esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")"
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity eqPref(String campo, String prefix, String suffix, Object... value) {
		addWhereCondition(Operatore.EQUAL, OperatoreBooleano.AND, campo, prefix, suffix, value);
		return this;
	}

	/**
	 * Realizza la condizione di = nella clausola where. Termina automaticamente
	 * con AND per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity eq(String campo, Object... value) {
		addWhereCondition(Operatore.EQUAL, OperatoreBooleano.AND, campo, value);
		return this;
	}

	/**
	 * Specifica le variabili istanza dell'entity che devono essere recuperate
	 * dalla tabella in select distinct
	 * 
	 * @param values
	 * @return BaseEntity
	 */
	public BaseEntity distinct(String... values) {
		setDistinctFields(arrayToList(values));
		return this;
	}

	/**
	 * Specifica le variabili istanza dell'entity che devono essere recuperate
	 * dalla tabella in select
	 * 
	 * @param values
	 * @return BaseEntity
	 */
	public BaseEntity fields(String... values) {
		setFields(arrayToList(values));
		return this;
	}

	/**
	 * Metodo alias di fields
	 * 
	 * @param values
	 * @return BaseEntity
	 */
	public BaseEntity cols(String... values) {
		return fields(values);
	}

	/**
	 * Specifica le tabelle che andranno in join sulla select. I valori values
	 * sono i nomi delle classi entity corrispondenti alle tabelle di join
	 * 
	 * @param joinName
	 * @return BaseEntity
	 */
	public BaseEntity join(String joinName) {
		setJoinName(joinName);
		return this;
	}

	/**
	 * Mette una parentesi aperta ( nella where condition
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity par() {
		Where w = new Where();
		w.setPar(true);
		getWhereCondition().add(w);
		return this;
	}

	/**
	 * Mette una parentesi chiusa ) nella where condition
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity closePar() {
		Where w = new Where();
		w.setClosePar(true);
		getWhereCondition().add(w);
		return this;
	}

	/**
	 * Realizza la condizione di LIKE nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * Value deve essere del tipo %valore%, ossia i % devono essere indicati nel
	 * value
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity likePref(String campo, String prefix, String suffix, Object... value) {
		addWhereCondition(Operatore.LIKE, OperatoreBooleano.AND, campo, prefix, suffix, value);
		return this;
	}

	/**
	 * Realizza la condizione di LIKE nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Value
	 * deve essere del tipo %valore%, ossia i % devono essere indicati nel value
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity like(String campo, Object... value) {
		addWhereCondition(Operatore.LIKE, OperatoreBooleano.AND, campo, value);
		return this;
	}

	/**
	 * Realizza la condizione di NOT LIKE nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * Value deve essere del tipo %valore%, ossia i % devono essere indicati nel
	 * value
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity notLikePref(String campo, String prefix, String suffix, Object... value) {
		addWhereCondition(Operatore.NOTLIKE, OperatoreBooleano.AND, campo, prefix, suffix, value);
		return this;
	}

	/**
	 * Realizza la condizione di NOT LIKE nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Value
	 * deve essere del tipo %valore%, ossia i % devono essere indicati nel value
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity notLike(String campo, Object... value) {
		addWhereConditionForNot(Operatore.NOTLIKE, OperatoreBooleano.AND, campo, value);
		return this;
	}

	/**
	 * Realizza la condizione di <> nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity neqPref(String campo, String prefix, String suffix, Object... value) {
		addWhereConditionForNot(Operatore.NOT_EQUAL, OperatoreBooleano.AND, campo, prefix, suffix, value);
		return this;
	}

	/**
	 * Realizza la condizione di <> nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity neq(String campo, Object... value) {
		addWhereConditionForNot(Operatore.NOT_EQUAL, OperatoreBooleano.AND, campo, value);
		return this;
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity between(String campo, String prefix, String suffix, Object value, Object value1) {
		if (notNull(value, value1)) {
			getWhereCondition().add(new Where(campo, Operatore.BETWEEN, value, value1, OperatoreBooleano.AND));
		} else {
			if (Null(value)) {
				lte(campo, prefix, suffix, value1);
			} else {
				if (Null(value1)) {
					gte(campo, prefix, suffix, value);
				}
			}
		}
		return this;
	}

	/**
	 * Realizza la condizione che un valore dato sia compreso nell'intervallo
	 * [campo1,campo2]
	 * 
	 * @param value
	 * @param campo1
	 * @param campo2
	 * @return BaseEntity
	 */
	public BaseEntity betweenFields(Object value, String campo1, String campo2) {
		return lte(campo1, value).gte(campo2, value);
	}

	/**
	 * Realizza la condizione che la data odierna sia compresa nell'intervallo
	 * [campo1,campo2] dove campo1 e campo2 sono campi data dell'entity
	 * 
	 * @param campo1
	 * @param campo2
	 * @return BaseEntity
	 */
	public BaseEntity betweenSysdate(String campo1, String campo2) {
		return betweenFields(now(), campo1, campo2);
	}

	/**
	 * Alias di betweenSysdate
	 * 
	 * @param campo1
	 * @param campo2
	 * @return BaseEntity
	 */
	public BaseEntity current(String campo1, String campo2) {
		return betweenFields(now(), campo1, campo2);
	}

	/**
	 * Realizza la condizione che la data odierna sia compresa nell'intervallo
	 * [campo1,campo2] dove campo1 e campo2 sono campi data dell'entity che
	 * hanno definito gli attributi dell'annotazione Column startDate=true e
	 * endDate=true
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity betweenSysdate() {
		return betweenSysdate(getFieldDataInizio(), getFieldDataFine());
	}

	/**
	 * Alias di betweenSysdate
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity current() {
		return betweenSysdate();
	}

	/**
	 * 
	 * Realizza la condizione che la data odierna sia compresa nell'intervallo
	 * [campo1,campo2] dove campo1 e campo2 sono campi data dell'entity che
	 * hanno definito gli attributi dell'annotazione Column startDate=true e
	 * endDate=true. Concatena automaticamente la clausola or per la prossima
	 * condizione
	 * 
	 * @return BaseEntity
	 */

	public BaseEntity betweenOrSysdate() {
		return betweenOrSysdate(getFieldDataInizio(), getFieldDataFine());
	}

	/**
	 * Alias di betweenOrSysdate
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity currentOr() {
		return betweenOrSysdate();
	}

	/**
	 * Realizza la condizione che la data odierna sia compresa nell'intervallo
	 * [campo1,campo2] dove campo1 e campo2 sono campi data dell'entity. Termina
	 * automaticamente con la clausola OR per concatenare la prossima condizione
	 * 
	 * @param campo1
	 * @param campo2
	 * @return BaseEntity
	 */
	public BaseEntity betweenOrSysdate(String campo1, String campo2) {
		return betweenFieldsOR(now(), campo1, campo2);
	}

	/**
	 * Alias di betweenOrSysdate
	 * 
	 * @param campo1
	 * @param campo2
	 * @return BaseEntity
	 */
	public BaseEntity currentOr(String campo1, String campo2) {
		return betweenOrSysdate(campo1, campo2);
	}

	/**
	 * Realizza la condizione che un valore dato sia compreso nell'intervallo
	 * [campo1,campo2].Termina con la clausola OR per concatenare la prossima
	 * condizione
	 * 
	 * @param value
	 * @param campo1
	 * @param campo2
	 * @return BaseEntity
	 */
	public BaseEntity betweenFieldsOR(Object value, String campo1, String campo2) {
		return lte(campo1, value).gte_OR(campo2, value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity between(String campo, Object value, Object value1) {
		if (notNull(value, value1)) {
			getWhereCondition().add(new Where(campo, Operatore.BETWEEN, value, value1, OperatoreBooleano.AND));
		} else {
			if (Null(value)) {
				lte(campo, value1);
			} else {
				if (Null(value1)) {
					gte(campo, value);
				}
			}
		}
		return this;
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= d
	 * 
	 * @param d
	 * @return BaseEntity
	 */
	public BaseEntity modifiedSince(Date d) {
		String dataAgg = getFieldDataAggiornamento();
		if (Null(dataAgg))
			return this;
		return gte(dataAgg, d);
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia compresa
	 * nell'intervallo [start,end]
	 * 
	 * @param start
	 * @param end
	 * @return BaseEntity
	 */
	public BaseEntity modifiedBetween(Date start, Date end) {
		String dataAgg = getFieldDataAggiornamento();
		if (Null(dataAgg)) {
			log("Mancanza della colonna DATA_AGGIORNAMENTO per l'entity", getEntityDetail());
			return this;
		}
		if (Null(start, end))
			return this;
		if (Null(end))
			return gte(dataAgg, start);
		if (Null(start))
			return lte(dataAgg, end);
		return between(dataAgg, start, end);
	}

	/**
	 * Imposta la condizione che il campo *COD_UTENTE sia user, ossia i record
	 * modificati dall'utente user
	 * 
	 * @param user
	 * @return BaseEntity
	 */
	public BaseEntity modifiedBy(String user) {
		String codUtente = getFieldCodUtente();
		if (Null(codUtente))
			return this;
		return eq(codUtente, user);
	}

	/**
	 * Imposta la condizione che il campo *COD_APP sia app, ossia i record
	 * modificati dall'applicativo app
	 * 
	 * @param app
	 * @return BaseEntity
	 */
	public BaseEntity modifiedByApp(String app) {
		String codApp = getFieldCodApp();
		if (Null(codApp))
			return this;
		return eq(codApp, app);
	}

	/**
	 * Ritorna true le se l'entity contiene le colonne *COD_UTENTE,*COD_APPL e
	 * *DATAAGGIORN
	 * 
	 * @return boolean
	 */
	public boolean hasImpactColums() {
		return notNull(getFieldCodApp(), getFieldCodUtente(), getFieldDataAggiornamento());
	}

	/**
	 * Metodo alias di modifiedByApp
	 * 
	 * @param app
	 * @return BaseEntity
	 */
	public BaseEntity byApp(String app) {
		String codApp = getFieldCodApp();
		if (Null(codApp)) {
			log("Mancanza della colonna COD_APPL per l'entity", getEntityDetail());
			return this;
		}
		return eq(codApp, app);
	}

	/**
	 * Metodo alias di modifiedByUser
	 * 
	 * @param user
	 * @return BaseEntity
	 */
	public BaseEntity byUser(String user) {
		String codUtente = getFieldCodUtente();
		if (Null(codUtente)) {
			log("Mancanza della colonna COD_UTENTE per l'entity", getEntityDetail());
			return this;
		}
		return eq(codUtente, user);
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna dell'anno scorso, ossia ritorna i record modificati nell'ultimo
	 * anno
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity modifiedSinceAYear() {
		return modifiedSince(addYears(now(), -1));
	}

	/**
	 * Metodo alias di modifiedSinceAYear
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity lastYear() {
		return modifiedSinceAYear();
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna di i anni fa , ossia ritorna i record modificati negli ultimi i
	 * anni
	 * 
	 * @param i
	 * @return BaseEntity
	 */
	public BaseEntity modifiedSinceNumOfYears(Integer i) {
		return modifiedSince(addYears(now(), -i));
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna del mese scorso, ossia ritorna i record modificati nell'ultimo
	 * mese
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity modifiedSinceAMonth() {
		return modifiedSince(addMonths(now(), -1));
	}

	/**
	 * Metodo alias di modifiedSinceAMonth
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity lastMonth() {
		return modifiedSinceAMonth();
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna di i mesi scorsi, ossia ritorna i record modificati negli ultimi
	 * i mesi
	 * 
	 * @param i
	 * @return BaseEntity
	 */
	public BaseEntity modifiedSinceNumOfMonths(Integer i) {
		return modifiedSince(addMonths(now(), -i));
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna - 7 giorni, ossia ritorna i record modificati nella ultima
	 * settimana
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity modifiedSinceAWeek() {
		return modifiedSince(addDays(now(), -7));
	}

	/**
	 * Metodo alias di modifiedSinceAWeek
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity lastWeek() {
		return modifiedSinceAWeek();
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna - i giorni, ossia ritorna i record modificati negli ultimi i
	 * giorni
	 * 
	 * @param i
	 * @return BaseEntity
	 */
	public BaseEntity modifiedSinceNumOfDays(Integer i) {
		return modifiedSince(addDays(now(), -i));
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna - 1 giorno, ossia ritorna i record modificati nell'ultimo giorno
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity modifiedSinceADay() {
		return modifiedSince(addDays(now(), -1));
	}

	/**
	 * Metodo alias di modifiedSinceADay
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity lastDay() {
		return modifiedSinceADay();
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna - 1 ora, ossia ritorna i record modificati nella ultima ora
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity modifiedSinceAHour() {
		return modifiedSince(addHours(now(), -1));
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna - i minuti, ossia ritorna i record modificati negli ultimi i
	 * minuti
	 * 
	 * @param i
	 * @return BaseEntity
	 */
	public BaseEntity modifiedSinceMinutes(Integer i) {
		return modifiedSince(addMinutes(now(), -i));
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna - 10 minuti, ossia ritorna i record modificati negli ultimi 10
	 * minuti
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity last10Minutes() {
		return modifiedSinceMinutes(10);
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna - 5 minuti, ossia ritorna i record modificati negli ultimi 5
	 * minuti
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity last5Minutes() {
		return modifiedSinceMinutes(5);
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna - 15 minuti, ossia ritorna i record modificati negli ultimi 15
	 * minuti
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity last15Minutes() {
		return modifiedSinceMinutes(15);
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna - 30 minuti, ossia ritorna i record modificati negli ultimi 30
	 * minuti
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity last30Minutes() {
		return modifiedSinceMinutes(30);
	}

	/**
	 * Metodo alias di modifiedSinceAHour
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity lastHour() {
		return modifiedSinceAHour();
	}

	/**
	 * Imposta la condizione che il campo data aggiornamento sia >= della data
	 * odierna - i ore, ossia ritorna i record modificati nelle ultime i ore
	 * 
	 * @param i
	 * @return BaseEntity
	 */
	public BaseEntity modifiedSinceNumOfHours(Integer i) {
		return modifiedSince(addHours(now(), -i));
	}

	/**
	 * Realizza la condizione di >= nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione.Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity gte(String campo, String prefix, String suffix, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.GTE, value, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Realizza la condizione di >= nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity gte(String campo, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, Operatore.GTE, value, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Realizza la condizione di > nella clausola where. Termina automaticamente
	 * con AND per concatenare la prossima condizione. Prefix è il prefisso da
	 * usare prima della colonna, suffix è il suffisso al nome di colonna,
	 * esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity gt(String campo, String prefix, String suffix, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.GT, value, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Realizza la condizione di > nella clausola where. Termina automaticamente
	 * con AND per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity gt(String campo, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, Operatore.GT, value, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Realizza la condizione di <= nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity lte(String campo, String prefix, String suffix, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.LTE, value, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Realizza la condizione di <= nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity lte(String campo, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, Operatore.LTE, value, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Realizza la condizione di < nella clausola where. Termina automaticamente
	 * con AND per concatenare la prossima condizione. Prefix è il prefisso da
	 * usare prima della colonna, suffix è il suffisso al nome di colonna,
	 * esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity lt(String campo, String prefix, String suffix, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.LT, value, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Realizza la condizione di < nella clausola where. Termina automaticamente
	 * con AND per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity lt(String campo, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, Operatore.LT, value, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Realizza la condizione di IS NULL nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione.Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @return BaseEntity
	 */
	public BaseEntity isNull(String campo, String prefix, String suffix) {
		getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.ISNULL, OperatoreBooleano.AND));
		return this;
	}

	/**
	 * Realizza la condizione di IS NULL nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @return BaseEntity
	 */
	public BaseEntity isNull(String campo) {
		getWhereCondition().add(new Where(campo, Operatore.ISNULL, OperatoreBooleano.AND));
		return this;
	}

	/**
	 * Realizza la condizione di IS NOT NULL nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @return BaseEntity
	 */
	public BaseEntity isNotNull(String campo, String prefix, String suffix) {
		getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.ISNOTNULL, OperatoreBooleano.AND));
		return this;
	}

	/**
	 * Realizza la condizione di IS NOT NULL nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @return BaseEntity
	 */
	public BaseEntity isNotNull(String campo) {
		getWhereCondition().add(new Where(campo, Operatore.ISNOTNULL, OperatoreBooleano.AND));
		return this;
	}

	/**
	 * Realizza la condizione di IN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param <T>
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param valori
	 * @return BaseEntity
	 */
	public <T> BaseEntity in(String campo, String prefix, String suffix, PList<T> valori) {
		if (notNull(valori)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.IN, valori, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Realizza la condizione di IN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione.
	 * 
	 * @param <T>
	 * @param campo
	 * @param valori
	 * @return BaseEntity
	 */
	public <T> BaseEntity in(String campo, PList<T> valori) {
		if (notNull(valori)) {
			getWhereCondition().add(new Where(campo, Operatore.IN, valori, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Realizza la condizione di IN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione.
	 * 
	 * @param <T>
	 * @param campo
	 * @param valori
	 * @return BaseEntity
	 */
	public <T> BaseEntity in(String campo, T... valori) {
		return in(campo, arrayToList(valori));
	}

	/**
	 * Realizza la condizione di NOT IN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param <T>
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param valori
	 * @return BaseEntity
	 */
	public <T> BaseEntity notIn(String campo, String prefix, String suffix, PList<T> valori) {
		if (notNull(valori)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.NOTIN, valori, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Realizza la condizione di NOT IN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione.
	 * 
	 * @param <T>
	 * @param campo
	 * @param valori
	 * @return BaseEntity
	 */
	public <T> BaseEntity notIn(String campo, PList<T> valori) {
		if (notNull(valori)) {
			getWhereCondition().add(new Where(campo, Operatore.NOTIN, valori, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Realizza la condizione di NOT IN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione.
	 * 
	 * @param <T>
	 * @param campo
	 * @param valori
	 * @return BaseEntity
	 */
	public <T> BaseEntity notIn(String campo, T... valori) {
		return notIn(campo, arrayToList(valori));
	}

	/**
	 * Realizza la condizione di = nella clausola where. Termina automaticamente
	 * con OR per concatenare la prossima condizione. Prefix è il prefisso da
	 * usare prima della colonna, suffix è il suffisso al nome di colonna,
	 * esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")"
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity eqPref_OR(String campo, String prefix, String suffix, Object... value) {
		addWhereCondition(Operatore.EQUAL, OperatoreBooleano.OR, campo, prefix, suffix, value);
		return this;
	}

	/**
	 * Realizza la condizione di = nella clausola where. Termina automaticamente
	 * con OR per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity eq_OR(String campo, Object... value) {
		addWhereCondition(Operatore.EQUAL, OperatoreBooleano.OR, campo, value);
		return this;
	}

	/**
	 * Realizza la condizione di LIKE nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * Value deve essere del tipo %valore%, ossia i % devono essere indicati nel
	 * value
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity likePref_OR(String campo, String prefix, String suffix, Object... value) {
		addWhereCondition(Operatore.LIKE, OperatoreBooleano.OR, campo, prefix, suffix, value);
		return this;
	}

	/**
	 * Realizza la condizione di LIKE nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione. Value deve
	 * essere del tipo %valore%, ossia i % devono essere indicati nel value
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity like_OR(String campo, Object... value) {
		addWhereCondition(Operatore.LIKE, OperatoreBooleano.OR, campo, value);
		return this;
	}

	private void addWhereCondition(Operatore op, OperatoreBooleano opb, String campo, Object... value) {
		if (null != value) {
			int num = value.length;
			if (num == 1) {
				getWhereCondition().add(new Where(campo, op, value[0], opb));
			} else if (num > 1) {
				par();
				for (Object val : value) {
					getWhereCondition().add(new Where(campo, op, val, opb));
				}
				closePar();
			}
		}
	}

	private void addWhereCondition(Operatore op, OperatoreBooleano opb, String campo, String prefix, String suffix, Object... value) {
		if (null != value) {
			int num = value.length;
			if (num == 1) {
				getWhereCondition().add(new Where(campo, prefix, suffix, op, value[0], opb));
			} else if (num > 1) {
				par();
				for (Object val : value) {
					getWhereCondition().add(new Where(campo, prefix, suffix, op, val, opb));
				}
				closePar();
			}
		}
	}

	private void addWhereConditionForNot(Operatore op, OperatoreBooleano opb, String campo, Object... value) {
		if (null != value) {
			int num = value.length;
			if (num == 1) {
				getWhereCondition().add(new Where(campo, op, value[0], opb));
			} else if (num > 1) {
				int i = 0;
				par();
				for (Object val : value) {
					i++;
					if (i == num) {
						getWhereCondition().add(new Where(campo, op, val, opb));
					} else
						getWhereCondition().add(new Where(campo, op, val, OperatoreBooleano.AND));
				}
				closePar();
			}
		}
	}

	private void addWhereConditionForNot(Operatore op, OperatoreBooleano opb, String campo, String prefix, String suffix, Object... value) {
		if (null != value) {
			int num = value.length;
			if (num == 1) {
				getWhereCondition().add(new Where(campo, prefix, suffix, op, value[0], opb));
			} else if (num > 1) {
				int i = 0;
				par();
				for (Object val : value) {
					i++;
					if (i == num) {
						getWhereCondition().add(new Where(campo, prefix, suffix, op, val, opb));
					} else
						getWhereCondition().add(new Where(campo, prefix, suffix, op, val, OperatoreBooleano.AND));
				}
				closePar();
			}
		}
	}

	/**
	 * Realizza la condizione di NOT LIKE nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * Value deve essere del tipo %valore%, ossia i % devono essere indicati nel
	 * value
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity notLikePref_OR(String campo, String prefix, String suffix, Object... value) {
		addWhereCondition(Operatore.NOTLIKE, OperatoreBooleano.OR, campo, prefix, suffix, value);
		return this;
	}

	/**
	 * Realizza la condizione di NOT LIKE nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione. Value deve
	 * essere del tipo %valore%, ossia i % devono essere indicati nel value
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity notLike_OR(String campo, Object... value) {
		addWhereConditionForNot(Operatore.NOTLIKE, OperatoreBooleano.OR, campo, value);
		return this;
	}

	/**
	 * Realizza la condizione di <> nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity neqPref_OR(String campo, String prefix, String suffix, Object... value) {
		addWhereConditionForNot(Operatore.NOT_EQUAL, OperatoreBooleano.OR, campo, prefix, suffix, value);
		return this;
	}

	/**
	 * Realizza la condizione di <> nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity neq_OR(String campo, Object... value) {
		addWhereConditionForNot(Operatore.NOT_EQUAL, OperatoreBooleano.OR, campo, value);
		return this;
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity between_OR(String campo, String prefix, String suffix, Object value, Object value1) {
		if (notNull(value, value1)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.BETWEEN, value, value1, OperatoreBooleano.OR));
		} else {
			if (Null(value)) {
				lte_OR(campo, prefix, suffix, value1);
			} else {
				if (Null(value1)) {
					gte_OR(campo, prefix, suffix, value);
				}
			}
		}
		return this;
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity between_OR(String campo, Object value, Object value1) {
		if (notNull(value, value1)) {
			getWhereCondition().add(new Where(campo, Operatore.BETWEEN, value, value1, OperatoreBooleano.OR));
		} else {
			if (Null(value)) {
				lte_OR(campo, value1);
			} else {
				if (Null(value1)) {
					gte_OR(campo, value);
				}
			}
		}
		return this;
	}

	/**
	 * Realizza la condizione di >= nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione.Prefix è il
	 * prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity gte_OR(String campo, String prefix, String suffix, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.GTE, value, OperatoreBooleano.OR));
		}
		return this;
	}

	/**
	 * Realizza la condizione di >= nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity gte_OR(String campo, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, Operatore.GTE, value, OperatoreBooleano.OR));
		}
		return this;
	}

	/**
	 * Realizza la condizione di > nella clausola where. Termina automaticamente
	 * con OR per concatenare la prossima condizione. Prefix è il prefisso da
	 * usare prima della colonna, suffix è il suffisso al nome di colonna,
	 * esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity gt_OR(String campo, String prefix, String suffix, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.GT, value, OperatoreBooleano.OR));
		}
		return this;
	}

	/**
	 * Realizza la condizione di > nella clausola where. Termina automaticamente
	 * con OR per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity gt_OR(String campo, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, Operatore.GT, value, OperatoreBooleano.OR));
		}
		return this;
	}

	/**
	 * Realizza la condizione di <= nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity lte_OR(String campo, String prefix, String suffix, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.LTE, value, OperatoreBooleano.OR));
		}
		return this;
	}

	/**
	 * Realizza la condizione di <= nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity lte_OR(String campo, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, Operatore.LTE, value, OperatoreBooleano.OR));
		}
		return this;
	}

	/**
	 * Realizza la condizione di < nella clausola where. Termina automaticamente
	 * con OR per concatenare la prossima condizione. Prefix è il prefisso da
	 * usare prima della colonna, suffix è il suffisso al nome di colonna,
	 * esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity lt_OR(String campo, String prefix, String suffix, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.LT, value, OperatoreBooleano.OR));
		}
		return this;
	}

	/**
	 * Realizza la condizione di < nella clausola where. Termina automaticamente
	 * con OR per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity lt_OR(String campo, Object value) {
		if (notNull(value)) {
			getWhereCondition().add(new Where(campo, Operatore.LT, value, OperatoreBooleano.OR));
		}
		return this;
	}

	/**
	 * Realizza la condizione di IS NULL nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione.Prefix è il
	 * prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @return BaseEntity
	 */
	public BaseEntity isNull_OR(String campo, String prefix, String suffix) {
		getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.ISNULL, OperatoreBooleano.OR));
		return this;
	}

	/**
	 * Realizza la condizione di IS NULL nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @return BaseEntity
	 */
	public BaseEntity isNull_OR(String campo) {
		getWhereCondition().add(new Where(campo, Operatore.ISNULL, OperatoreBooleano.OR));
		return this;
	}

	/**
	 * Realizza la condizione di IS NOT NULL nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @return BaseEntity
	 */
	public BaseEntity isNotNull_OR(String campo, String prefix, String suffix) {
		getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.ISNOTNULL, OperatoreBooleano.OR));
		return this;
	}

	/**
	 * Realizza la condizione di IS NOT NULL nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione.
	 * 
	 * @param campo
	 * @return BaseEntity
	 */
	public BaseEntity isNotNull_OR(String campo) {
		getWhereCondition().add(new Where(campo, Operatore.ISNOTNULL, OperatoreBooleano.OR));
		return this;
	}

	/**
	 * Realizza la condizione di IN nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param <T>
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param valori
	 * @return BaseEntity
	 */
	public <T> BaseEntity in_OR(String campo, String prefix, String suffix, PList<T> valori) {
		if (notNull(valori)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.IN, valori, OperatoreBooleano.OR));
		}
		return this;
	}

	/**
	 * Realizza la condizione di IN nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione.
	 * 
	 * @param <T>
	 * @param campo
	 * @param valori
	 * @return BaseEntity
	 */
	public <T> BaseEntity in_OR(String campo, PList<T> valori) {
		if (notNull(valori)) {
			getWhereCondition().add(new Where(campo, Operatore.IN, valori, OperatoreBooleano.OR));
		}
		return this;
	}

	/**
	 * Realizza la condizione di IN nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione.
	 * 
	 * @param <T>
	 * @param campo
	 * @param valori
	 * @return BaseEntity
	 */
	public <T> BaseEntity in_OR(String campo, T... valori) {
		return in_OR(campo, arrayToList(valori));
	}

	/**
	 * Realizza la condizione di NOT IN nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione. Prefix è
	 * il prefisso da usare prima della colonna, suffix è il suffisso al nome di
	 * colonna, esempio SUM( nome colonna ), prefix è "SUM(" e suffix è ")".
	 * 
	 * @param <T>
	 * @param campo
	 * @param prefix
	 * @param suffix
	 * @param valori
	 * @return BaseEntity
	 */
	public <T> BaseEntity notIn_OR(String campo, String prefix, String suffix, PList<T> valori) {
		if (notNull(valori)) {
			getWhereCondition().add(new Where(campo, prefix, suffix, Operatore.NOTIN, valori, OperatoreBooleano.OR));
		}
		return this;
	}

	/**
	 * Realizza la condizione di NOT IN nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione.
	 * 
	 * @param <T>
	 * @param campo
	 * @param valori
	 * @return BaseEntity
	 */
	public <T> BaseEntity notIn_OR(String campo, PList<T> valori) {
		if (notNull(valori)) {
			getWhereCondition().add(new Where(campo, Operatore.NOTIN, valori, OperatoreBooleano.OR));
		}
		return this;
	}

	/**
	 * Realizza la condizione di NOT IN nella clausola where. Termina
	 * automaticamente con OR per concatenare la prossima condizione.
	 * 
	 * @param <T>
	 * @param campo
	 * @param valori
	 * @return BaseEntity
	 */
	public <T> BaseEntity notIn_OR(String campo, T... valori) {
		return notIn_OR(campo, arrayToList(valori));
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Svuota la where condition
	 */
	public void cleanWhereCondition() {
		setWhereCondition(new PArrayList<Where>());
	}

	/**
	 * Svuota la order by
	 */
	public BaseEntity cleanOrderBy() {
		this.orderBy = plstr();
		return this;
	}

	/**
	 * Svuota la lista dei campi da escludere in una update
	 */
	public BaseEntity cleanFieldsToExclude() {
		setFieldsToExcludeInUpdate(plstr());
		return this;
	}

	/**
	 * Svuota la where condition. la order by e i campi da impostare a null in
	 * una update
	 */
	public BaseEntity cleanAll() {
		cleanFieldsToExclude();
		cleanWhereCondition();
		cleanOrderBy();
		return this;
	}

	/**
	 * Metodo che imposta la where condition sulla chiave primaria
	 * 
	 */
	protected void setWhereConditionForPk() {
		setSearchByPk(true);
		// cleanWhereCondition();
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.pk()) {
					try {
						eq(att.getName(), invokeGetter(att));
					} catch (Exception e) {
					}
				}
			}
		}
	}

	/**
	 * Imposta la where condition sul flagStato
	 */
	protected boolean setWhereConditionForFlagStato() {
		boolean flagStatoImpostato = false;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.deleteLogic()) {
					try {
						eq(att.getName(), invokeGetter(att));
						flagStatoImpostato = true;
						break;
					} catch (Exception e) {
					}
				}
			}
		}
		return flagStatoImpostato;
	}

	public String getCodUtenteCostruttore() {
		return codUtenteCostruttore;
	}

	public void setCodUtenteCostruttore(String codUtenteCostruttore) {
		this.codUtenteCostruttore = codUtenteCostruttore;

	}

	public String getCodApplCostruttore() {
		return codApplCostruttore;
	}

	public void setCodApplCostruttore(String codApplCostruttore) {
		this.codApplCostruttore = codApplCostruttore;
	}

	/**
	 * Imposta automaticamente la where condition con la relazione di
	 * equivalenza = e impostando automaticamente il valore al valore della
	 * variabile istanza dell'Entity. I valori NULL vengono automaticamente
	 * esclusi
	 * 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected void setWhereConditionByInstanceVars() {
		if (isSearchByPk())
			return;
		PList<String> nomiMetodi = plstr();
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				nomiMetodi.add(str(GET, capFirstLetter(att.getName())));
			}
		}
		for (Method method : getMetodiEnt()) {
			if (is(nomiMetodi, method.getName())) {
				if (is(method.getReturnType().getSimpleName(), "byte[]"))
					continue;
				String attName = decapFirstLetter(substring(method.getName(), GET, false, false, null, false, false));
				Object o = null;
				try {
					o = method.invoke(this);
				} catch (Exception e) {
				}
				if (notNull(o)) {
					eq(attName, o);
				}
			}
		}
	}

	/**
	 * Aggiunge alla where condition la condizione FLAG_STATO='A'
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity attivo() {
		String attName = getFlagStatoAttributeName();
		if (notNull(attName)) {
			eq(attName, Pilot.ATTIVO);
		}
		return this;
	}

	/**
	 * Aggiunge alla where condition la condizione FLAG_STATO='C'
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity disattivo() {
		String attName = getFlagStatoAttributeName();
		if (notNull(attName)) {
			eq(attName, Pilot.DISATTIVO);
		}
		return this;
	}

	protected PList<String> getContainer() {
		return container;
	}

	public void setContainer(PList<String> container) {
		this.container = container;
	}

	/**
	 * Legge la tipologica chiave-valore utilizzando un meccanismo di cache
	 * implementato tramite il parametro Map<K,T>.Key è il valore chiave della
	 * tipologica. Restituisce il valore della tipologica corrispondente alla
	 * chiave passata come parametro e utilizzando un meccanismo di cache per
	 * evitare accessi multipli ridondanti alla base dati
	 * 
	 * @param <K>
	 * @param <T>
	 * @param key
	 * @param mappa
	 * @return T
	 * @throws Exception
	 */
	public <K, T> T getTipologica(K key, Map<K, T> mappa) throws Exception {
		try {
			if (Null(key))
				return null;
			if (null == mappa.get(key)) {
				String keyField = "";
				String valueField = "";
				for (Field field : getAttributi()) {
					if (field.isAnnotationPresent(TipologicaKey.class)) {
						keyField = field.getName();
					}
					if (field.isAnnotationPresent(TipologicaValue.class)) {
						valueField = field.getName();
					}
				}
				if (NullOR(keyField, valueField))
					throw new Exception(str("Occorre definire le variabili istanza @TipologicaKey e @TipologicaValue della tipologica per l'entity ", getClass().getSimpleName()));
				mappa.put(key, (T) get(eq(keyField, key).selectOne(), valueField));
			}
		} finally {
			closeAlls(null, null);
		}
		return (T) mappa.get(key);
	}

	private String getFieldDataInizio() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.startDate()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldDataFine() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.endDate()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldStato() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.stato()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldCitta() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.citta()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldIndirizzo() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.indirizzo()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldTitolo() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.titolo()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldPiva() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.piva()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldNazione() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.nazione()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldNome() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.nome()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldTotale() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.totale()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldValore() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.valore()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldUsername() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.username()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldEmail() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.email()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldCognome() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.cognome()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldEta() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.eta()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldCf() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.cf()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldImporto() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.importo()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldTipo() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.tipo()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldSede() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.sede()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldCodice() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.codice()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldFascicolo() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.fascicolo()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldDescrizione() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.descrizione()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldIscritto() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.iscritto()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldEnte() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.ente()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldCassa() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.cassa()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	private String getFieldProgr() {
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

	private String getFieldStato1() {
		String campo = null;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.stato1()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	/**
	 * Crea la condizione colonnaStato=value dove colonnaStato è la variabile
	 * istanza dell'entity che ha l'attributo stato=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity stato(Object value) {
		return eq(getFieldStato(), value);
	}

	/**
	 * Crea la condizione colonnaTipo=value dove colonnaTipo è la variabile
	 * istanza dell'entity che ha l'attributo tipo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity tipo(Object value) {
		return eq(getFieldTipo(), value);
	}

	/**
	 * Crea la condizione colonnaTipo IN (value) dove colonnaTipo è la variabile
	 * istanza dell'entity che ha l'attributo tipo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity tipoIn(T... value) {
		return in(getFieldTipo(), value);
	}

	/**
	 * Crea la condizione colonnaTipo IN (value) dove colonnaTipo è la variabile
	 * istanza dell'entity che ha l'attributo tipo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity tipoIn(PList<T> value) {
		return in(getFieldTipo(), value);
	}

	/**
	 * Crea la condizione colonnaTipo NOT IN (value) dove colonnaTipo è la
	 * variabile istanza dell'entity che ha l'attributo tipo=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity tipoNotIn(T... value) {
		return notIn(getFieldTipo(), value);
	}

	/**
	 * Crea la condizione colonnaTipo NOT IN (value) dove colonnaTipo è la
	 * variabile istanza dell'entity che ha l'attributo tipo=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity tipoNotIn(PList<T> value) {
		return notIn(getFieldTipo(), value);
	}

	/**
	 * Crea la condizione colonnaSede=value dove colonnaSede è la variabile
	 * istanza dell'entity che ha l'attributo sede=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity sede(Object value) {
		return eq(getFieldSede(), value);
	}

	/**
	 * Crea la condizione colonnaSede IN (value) dove colonnaSede è la variabile
	 * istanza dell'entity che ha l'attributo sede=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity sedeIn(T... value) {
		return in(getFieldSede(), value);
	}

	/**
	 * Crea la condizione colonnaSede IN (value) dove colonnaSede è la variabile
	 * istanza dell'entity che ha l'attributo sede=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity sedeIn(PList<T> value) {
		return in(getFieldSede(), value);
	}

	/**
	 * Crea la condizione colonnaSede NOT IN (value) dove colonnaSede è la
	 * variabile istanza dell'entity che ha l'attributo sede=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity sedeNotIn(T... value) {
		return notIn(getFieldSede(), value);
	}

	/**
	 * Crea la condizione colonnaSede NOT IN (value) dove colonnaSede è la
	 * variabile istanza dell'entity che ha l'attributo sede=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity sedeNotIn(PList<T> value) {
		return notIn(getFieldSede(), value);
	}

	/**
	 * Crea la condizione colonnaCodice=value dove colonnaCodice è la variabile
	 * istanza dell'entity che ha l'attributo codice=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity codice(Object value) {
		return eq(getFieldCodice(), value);
	}

	/**
	 * Crea la condizione colonnaCodice IN (value) dove colonnaCodice è la
	 * variabile istanza dell'entity che ha l'attributo codice=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity codiceIn(T... value) {
		return in(getFieldCodice(), value);
	}

	/**
	 * Crea la condizione colonnaCodice IN (value) dove colonnaCodice è la
	 * variabile istanza dell'entity che ha l'attributo codice=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity codiceIn(PList<T> value) {
		return in(getFieldCodice(), value);
	}

	/**
	 * Crea la condizione colonnaCodice NOT IN (value) dove colonnaCodice è la
	 * variabile istanza dell'entity che ha l'attributo codice=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity codiceNotIn(T... value) {
		return notIn(getFieldCodice(), value);
	}

	/**
	 * Crea la condizione colonnaCodice NOT IN (value) dove colonnaCodice è la
	 * variabile istanza dell'entity che ha l'attributo codice=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity codiceNotIn(PList<T> value) {
		return notIn(getFieldCodice(), value);
	}

	/**
	 * Crea la condizione colonnaFascicolo=value dove colonnaFascicolo è la
	 * variabile istanza dell'entity che ha l'attributo fascicolo=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity fascicolo(Object value) {
		return eq(getFieldFascicolo(), value);
	}

	/**
	 * Crea la condizione colonnaFascicolo IN (value) dove colonnaFascicolo è la
	 * variabile istanza dell'entity che ha l'attributo fascicolo=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity fascicoloIn(T... value) {
		return in(getFieldFascicolo(), value);
	}

	/**
	 * Crea la condizione colonnaFascicolo IN (value) dove colonnaFascicolo è la
	 * variabile istanza dell'entity che ha l'attributo fascicolo=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity fascicoloIn(PList<T> value) {
		return in(getFieldFascicolo(), value);
	}

	/**
	 * Crea la condizione colonnaFascicolo NOT IN (value) dove colonnaFascicolo
	 * è la variabile istanza dell'entity che ha l'attributo fascicolo=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity fascicoloNotIn(T... value) {
		return notIn(getFieldFascicolo(), value);
	}

	/**
	 * Crea la condizione colonnaFascicolo NOT IN (value) dove colonnaFascicolo
	 * è la variabile istanza dell'entity che ha l'attributo fascicolo=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity fascicoloNotIn(PList<T> value) {
		return notIn(getFieldFascicolo(), value);
	}

	/**
	 * Crea la condizione colonnaDescrizione=value dove colonnaDescrizione è la
	 * variabile istanza dell'entity che ha l'attributo descrizione=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity descrizione(Object value) {
		return eq(getFieldDescrizione(), value);
	}

	/**
	 * Crea la condizione colonnaDescrizione IN (value) dove colonnaDescrizione
	 * è la variabile istanza dell'entity che ha l'attributo descrizione=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity descrizioneIn(T... value) {
		return in(getFieldDescrizione(), value);
	}

	/**
	 * Crea la condizione colonnaDescrizione IN (value) dove colonnaDescrizione
	 * è la variabile istanza dell'entity che ha l'attributo descrizione=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity descrizioneIn(PList<T> value) {
		return in(getFieldDescrizione(), value);
	}

	/**
	 * Crea la condizione colonnaDescrizione NOT IN (value) dove
	 * colonnaDescrizione è la variabile istanza dell'entity che ha l'attributo
	 * descrizione=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity descrizioneNotIn(T... value) {
		return notIn(getFieldDescrizione(), value);
	}

	/**
	 * Crea la condizione colonnaDescrizione NOT IN (value) dove
	 * colonnaDescrizione è la variabile istanza dell'entity che ha l'attributo
	 * descrizione=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity descrizioneNotIn(PList<T> value) {
		return notIn(getFieldDescrizione(), value);
	}

	/**
	 * Crea la condizione colonnaIscritto IN (value) dove colonnaIscritto è la
	 * variabile istanza dell'entity che ha l'attributo iscritto=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity iscrittoIn(T... value) {
		return in(getFieldIscritto(), value);
	}

	/**
	 * Crea la condizione colonnaIscritto IN (value) dove colonnaIscritto è la
	 * variabile istanza dell'entity che ha l'attributo iscritto=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity iscrittoIn(PList<T> value) {
		return in(getFieldIscritto(), value);
	}

	/**
	 * Crea la condizione colonnaIscritto NOT IN (value) dove colonnaIscritto è
	 * la variabile istanza dell'entity che ha l'attributo iscritto=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity iscrittoNotIn(T... value) {
		return notIn(getFieldIscritto(), value);
	}

	/**
	 * Crea la condizione colonnaIscritto NOT IN (value) dove colonnaIscritto è
	 * la variabile istanza dell'entity che ha l'attributo iscritto=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity iscrittoNotIn(PList<T> value) {
		return notIn(getFieldIscritto(), value);
	}

	/**
	 * Crea la condizione colonnaIscritto=value dove colonnaIscritto è la
	 * variabile istanza dell'entity che ha l'attributo iscritto=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity iscritto(Object value) {
		return eq(getFieldIscritto(), value);
	}

	/**
	 * Crea la condizione colonnaEnte=value dove colonnaEnte è la variabile
	 * istanza dell'entity che ha l'attributo ente=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity ente(Object value) {
		return eq(getFieldEnte(), value);
	}

	/**
	 * Crea la condizione colonnaEnte IN (value) dove colonnaEnte è la variabile
	 * istanza dell'entity che ha l'attributo ente=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity enteIn(T... value) {
		return in(getFieldEnte(), value);
	}

	/**
	 * Crea la condizione colonnaEnte IN (value) dove colonnaEnte è la variabile
	 * istanza dell'entity che ha l'attributo ente=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity enteIn(PList<T> value) {
		return in(getFieldEnte(), value);
	}

	/**
	 * Crea la condizione colonnaEnte NOT IN (value) dove colonnaEnte è la
	 * variabile istanza dell'entity che ha l'attributo ente=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity enteNotIn(T... value) {
		return notIn(getFieldEnte(), value);
	}

	/**
	 * Crea la condizione colonnaEnte NOT IN (value) dove colonnaEnte è la
	 * variabile istanza dell'entity che ha l'attributo ente=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity enteNotIn(PList<T> value) {
		return notIn(getFieldEnte(), value);
	}

	/**
	 * Crea la condizione colonnaCassa=value dove colonnaStato è la variabile
	 * istanza dell'entity che ha l'attributo cassa=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity cassa(Object value) {
		return eq(getFieldCassa(), value);
	}

	/**
	 * Crea la condizione colonnaCassa IN (value) dove colonnaCassa è la
	 * variabile istanza dell'entity che ha l'attributo cassa=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cassaIn(T... value) {
		return in(getFieldCassa(), value);
	}

	/**
	 * Crea la condizione colonnaCassa IN (value) dove colonnaCassa è la
	 * variabile istanza dell'entity che ha l'attributo cassa=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cassaIn(PList<T> value) {
		return in(getFieldCassa(), value);
	}

	/**
	 * Crea la condizione colonnaCassa NOT IN (value) dove colonnaCassa è la
	 * variabile istanza dell'entity che ha l'attributo cassa=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cassaNotIn(T... value) {
		return notIn(getFieldCassa(), value);
	}

	/**
	 * Crea la condizione colonnaCassa NOT IN (value) dove colonnaCassa è la
	 * variabile istanza dell'entity che ha l'attributo cassa=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cassaNotIn(PList<T> value) {
		return notIn(getFieldCassa(), value);
	}

	/**
	 * Crea la condizione colonnaId=value dove colonnaId è la variabile istanza
	 * dell'entity che ha l'attributo id=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity id(Object value) {
		return eq(getFieldId(), value);
	}

	/**
	 * Crea la condizione colonnaId IN (value) dove colonnaId è la variabile
	 * istanza dell'entity che ha l'attributo id=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity idIn(T... value) {
		return in(getFieldId(), value);
	}

	/**
	 * Crea la condizione colonnaId IN (value) dove colonnaId è la variabile
	 * istanza dell'entity che ha l'attributo id=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity idIn(PList<T> value) {
		return in(getFieldId(), value);
	}

	/**
	 * Crea la condizione colonnaId NOT IN (value) dove colonnaId è la variabile
	 * istanza dell'entity che ha l'attributo id=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity idNotIn(T... value) {
		return notIn(getFieldId(), value);
	}

	/**
	 * Crea la condizione colonnaId NOT IN (value) dove colonnaId è la variabile
	 * istanza dell'entity che ha l'attributo id=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity idNotIn(PList<T> value) {
		return notIn(getFieldId(), value);
	}

	/**
	 * Crea la condizione colonnaProgr=value dove colonnaProgr è la variabile
	 * istanza dell'entity che ha l'attributo progr=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity progr(Object value) {
		return eq(getFieldProgr(), value);
	}

	/**
	 * Crea la condizione colonnaProgr IN (value) dove colonnaProgr è la
	 * variabile istanza dell'entity che ha l'attributo progr=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity progrIn(T... value) {
		return in(getFieldProgr(), value);
	}

	/**
	 * Crea la condizione colonnaProgr IN (value) dove colonnaProgr è la
	 * variabile istanza dell'entity che ha l'attributo progr=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity progrIn(PList<T> value) {
		return in(getFieldProgr(), value);
	}

	/**
	 * Crea la condizione colonnaProgr NOT IN (value) dove colonnaProgr è la
	 * variabile istanza dell'entity che ha l'attributo progr=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity progrNotIn(T... value) {
		return notIn(getFieldProgr(), value);
	}

	/**
	 * Crea la condizione colonnaProgr NOT IN (value) dove colonnaProgr è la
	 * variabile istanza dell'entity che ha l'attributo progr=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity progrNotIn(PList<T> value) {
		return notIn(getFieldProgr(), value);
	}

	/**
	 * Crea la condizione colonnaStato1 IN (value) dove colonnaStato1 è la
	 * variabile istanza dell'entity che ha l'attributo stato1=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity stato1(Object value) {
		return eq(getFieldStato1(), value);
	}

	/**
	 * Crea la condizione colonnaStato IN (value) dove colonnaStato è la
	 * variabile istanza dell'entity che ha l'attributo stato=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity statoIn(T... value) {
		return in(getFieldStato(), value);
	}

	/**
	 * Crea la condizione colonnaStato IN (value) dove colonnaStato è la
	 * variabile istanza dell'entity che ha l'attributo stato=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity statoIn(PList<T> value) {
		return in(getFieldStato(), value);
	}

	/**
	 * Crea la condizione colonnaStato NOT IN (value) dove colonnaStato è la
	 * variabile istanza dell'entity che ha l'attributo stato=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity statoNotIn(T... value) {
		return notIn(getFieldStato(), value);
	}

	/**
	 * Crea la condizione colonnaStato NOT IN (value) dove colonnaStato è la
	 * variabile istanza dell'entity che ha l'attributo stato=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity statoNotIn(PList<T> value) {
		return notIn(getFieldStato(), value);
	}

	/**
	 * Crea la condizione colonnaStato1 IN (value) dove colonnaStato è la
	 * variabile istanza dell'entity che ha l'attributo stato1=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity stato1In(T... value) {
		return in(getFieldStato1(), value);
	}

	/**
	 * Crea la condizione colonnaStato1 IN (value) dove colonnaStato è la
	 * variabile istanza dell'entity che ha l'attributo stato1=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity stato1In(PList<T> value) {
		return in(getFieldStato1(), value);
	}

	/**
	 * Crea la condizione colonnaStato1 NOT IN (value) dove colonnaStato è la
	 * variabile istanza dell'entity che ha l'attributo stato1=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity stato1NotIn(T... value) {
		return notIn(getFieldStato1(), value);
	}

	/**
	 * Crea la condizione colonnaStato1 NOT IN (value) dove colonnaStato è la
	 * variabile istanza dell'entity che ha l'attributo stato1=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity stato1NotIn(PList<T> value) {
		return notIn(getFieldStato1(), value);
	}

	/**
	 * Crea la condizione colonnaStato IN (value) dove colonnaStato è la
	 * variabile istanza dell'entity che ha l'attributo stato=true
	 * nell'annotazione @Column. Termina con la clausola OR
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity statoInOr(T... value) {
		return in_OR(getFieldStato(), value);
	}

	/**
	 * Crea la condizione colonnaStato1 IN (value) dove colonnaStato1 è la
	 * variabile istanza dell'entity che ha l'attributo stato1=true
	 * nell'annotazione @Column. Termina con la clausola OR
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity stato1InOr(T... value) {
		return in_OR(getFieldStato1(), value);
	}

	/**
	 * Crea la condizione colonnaStato=value dove colonnaStato è la variabile
	 * istanza dell'entity che ha l'attributo stato=true nell'annotazione
	 * 
	 * Column. Concatena OR per la prossima condizione di filtro
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity statoOr(Object value) {
		return eq_OR(getFieldStato(), value);
	}

	/**
	 * Crea la condizione colonnaStato1=value dove colonnaStato1 è la variabile
	 * istanza dell'entity che ha l'attributo stato1=true nell'annotazione *
	 * 
	 * Column. Concatena OR per la prossima condizione di filtro
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity stato1Or(Object value) {
		return eq_OR(getFieldStato1(), value);
	}

	/**
	 * Imposta gli attributi chieve primaria dell'entity, secondo l'ordine in
	 * cui sono definiti nell'entity. I valori assegnati value vengono assegnati
	 * rispettando l'ordine di definizione dei valori. Il primo argomento viene
	 * assegnato al primo attributo con pk=true, il secondo argomento viene
	 * assegnato al secondo attributo trovato con pk=true...... l'n-esimo
	 * argomento viene assegnato all'n-esimo attributo trovato con valore
	 * pk=true
	 * 
	 * @param value
	 *            K
	 * @return K
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public <K extends BaseEntity> K setPrimary(Object... value) throws Exception {
		int i = 0;
		for (Field att : getAttributi()) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.pk()) {
					if (i < value.length) {
						invokeSetter(value[i], att);
						i++;
					}
				}
			}
		}
		return (K) this;
	}

	/**
	 * Imposta la chiave primaria dell'entity a partire dall'entity ent passata
	 * come parametro. Il principio regolatore è l'uguaglianza dell'alias della
	 * colonna tra le due entity per le sole colonne chiave primaria.
	 * 
	 * Column.alias= Column.alias e entrambe devono essere chiave primaria
	 * pk=true
	 * 
	 * @param <T>
	 * @param ent
	 * @return BaseEntity
	 * @throws Exception
	 */
	public <K extends BaseEntity, T extends BaseEntity> BaseEntity setPrimaryEnt(T ent) throws Exception {
		if (notNull(ent)) {
			Map<String, Object> mappaPk = new HashMap<String, Object>();
			Method[] metodiEntPar = ent.getClass().getDeclaredMethods();
			Field[] attributiEntPar = ent.getClass().getDeclaredFields();
			for (Field att : attributiEntPar) {
				Column annCol = att.getAnnotation(Column.class);
				if (notNull(annCol)) {
					if (notNull(annCol.alias()) && annCol.pk()) {
						String nomeMetodo = str(GET, cap(att.getName()));
						Method m = findMethod(metodiEntPar, nomeMetodo);
						mappaPk.put(annCol.alias(), m.invoke(ent));
					}
				}
			}
			if (notNull(mappaPk)) {
				for (Map.Entry<String, Object> entry : mappaPk.entrySet()) {
					for (Field att : getAttributi()) {
						Column annCol = att.getAnnotation(Column.class);
						if (notNull(annCol)) {
							if (notNull(annCol.alias()) && annCol.pk() && is(entry.getKey(), annCol.alias())) {
								String nomeMetodo = str(SET, cap(att.getName()));
								Method m = findMethod(getMetodiEnt(), nomeMetodo);
								m.invoke(this, entry.getValue());
							}

						}
					}
				}
			}
		}
		return this;
	}

	/**
	 * Aggiunge la condizione ROWNUM<=limite
	 * 
	 * @param limite
	 * @return BaseEntity
	 */
	public BaseEntity limit(Integer limite) {
		if (notNull(limite)) {
			getWhereCondition().add(new Where(Operatore.LIMIT, limite, OperatoreBooleano.AND));
		}
		return this;
	}

	/**
	 * Crea la condizione colonnaDataFine=value dove colonnaDataFine è la
	 * variabile istanza dell'entity che ha l'attributo endDate=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity dataFine(Object value) {
		return eq(getFieldDataFine(), value);
	}

	/**
	 * Crea la condizione colonnaDataInizio=value dove colonnaDataInizio è la
	 * variabile istanza dell'entity che ha l'attributo startDate=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity dataInizio(Object value) {
		return eq(getFieldDataInizio(), value);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * cassa=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByCassa() {
		return orderByASC(getFieldCassa());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * cassa=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByCassaDESC() {
		return orderByDESC(getFieldCassa());
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * tipo=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByTipo() {
		return orderByASC(getFieldTipo());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * tipo=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByTipoDESC() {
		return orderByDESC(getFieldTipo());
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * iscritto=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByIscritto() {
		return orderByASC(getFieldIscritto());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * iscritto=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByIscrittoDESC() {
		return orderByDESC(getFieldIscritto());
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * id=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderById() {
		return orderByASC(getFieldId());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * id=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByIdDESC() {
		return orderByDESC(getFieldId());
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * fascicolo=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByFascicolo() {
		return orderByASC(getFieldFascicolo());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * fascicolo=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByFascicoloDESC() {
		return orderByDESC(getFieldFascicolo());
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * descrizione=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByDescrizione() {
		return orderByASC(getFieldDescrizione());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * descrizione=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByDescrizioneDESC() {
		return orderByDESC(getFieldDescrizione());
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * stato=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByStato() {
		return orderByASC(getFieldStato());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * stato=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByStatoDESC() {
		return orderByDESC(getFieldStato());
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * stato1=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByStato1() {
		return orderByASC(getFieldStato1());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * stato1=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByStato1DESC() {
		return orderByDESC(getFieldStato1());
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * ente=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByEnte() {
		return orderByASC(getFieldEnte());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * ente=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByEnteDESC() {
		return orderByDESC(getFieldEnte());
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * progr=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByProgr() {
		return orderByASC(getFieldProgr());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * progr=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByProgrDESC() {
		return orderByDESC(getFieldProgr());
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * sede=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderBySede() {
		return orderByASC(getFieldSede());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * sede=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderBySedeDESC() {
		return orderByDESC(getFieldSede());
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da cassa=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenCassa(Object value, Object value1) {
		return between(getFieldCassa(), value, value1);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da tipo=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenTipo(Object value, Object value1) {
		return between(getFieldTipo(), value, value1);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da iscritto=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenIscritto(Object value, Object value1) {
		return between(getFieldIscritto(), value, value1);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da descrizione=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenDescrizione(Object value, Object value1) {
		return between(getFieldDescrizione(), value, value1);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da fascicolo=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenFascicolo(Object value, Object value1) {
		return between(getFieldFascicolo(), value, value1);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da ente=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenEnte(Object value, Object value1) {
		return between(getFieldEnte(), value, value1);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da sede=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenSede(Object value, Object value1) {
		return between(getFieldSede(), value, value1);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da stato=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenStato(Object value, Object value1) {
		return between(getFieldStato(), value, value1);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da stato1=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenStato1(Object value, Object value1) {
		return between(getFieldStato1(), value, value1);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da id=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenId(Object value, Object value1) {
		return between(getFieldId(), value, value1);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da progr=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenProgr(Object value, Object value1) {
		return between(getFieldProgr(), value, value1);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da dataFine=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenDataFine(Object value, Object value1) {
		return between(getFieldDataFine(), value, value1);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da dataInizio=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenDataInizio(Object value, Object value1) {
		return between(getFieldDataInizio(), value, value1);
	}

	/**
	 * Crea la condizione colonnaNome=value dove colonnaNome è la variabile
	 * istanza dell'entity che ha l'attributo nome=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity nome(Object value) {
		return eq(getFieldNome(), value);
	}

	/**
	 * Crea la condizione colonnaNome IN (value) dove colonnaNome è la variabile
	 * istanza dell'entity che ha l'attributo nome=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity nomeIn(PList<T> value) {
		return in(getFieldNome(), value);
	}

	/**
	 * Crea la condizione colonnaNome IN (value) dove colonnaNome è la variabile
	 * istanza dell'entity che ha l'attributo nome=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity nome(T... value) {
		return in(getFieldNome(), value);
	}

	/**
	 * Crea la condizione colonnaNome NOT IN (value) dove colonnaNome è la
	 * variabile istanza dell'entity che ha l'attributo nome=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity nomeNotIn(PList<T> value) {
		return notIn(getFieldNome(), value);
	}

	/**
	 * Crea la condizione colonnaNome NOT IN (value) dove colonnaNome è la
	 * variabile istanza dell'entity che ha l'attributo nome=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity nomeNotIn(T... value) {
		return notIn(getFieldNome(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da nome=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenNome(Object value, Object value1) {
		return between(getFieldNome(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * nome=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByNome() {
		return orderByASC(getFieldNome());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * nome=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByNomeDESC() {
		return orderByDESC(getFieldNome());
	}

	/**
	 * Crea la condizione Cognome=value dove Cognome è la variabile istanza
	 * dell'entity che ha l'attributo Cognome=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity cognome(Object value) {
		return eq(getFieldCognome(), value);
	}

	/**
	 * Crea la condizione Cognome IN (value) dove Cognome è la variabile istanza
	 * dell'entity che ha l'attributo Cognome=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cognomeIn(PList<T> value) {
		return in(getFieldCognome(), value);
	}

	/**
	 * Crea la condizione Cognome IN (value) dove Cognome è la variabile istanza
	 * dell'entity che ha l'attributo Cognome=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cognome(T... value) {
		return in(getFieldCognome(), value);
	}

	/**
	 * Crea la condizione Cognome NOT IN (value) dove Cognome è la variabile
	 * istanza dell'entity che ha l'attributo Cognome=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cognomeNotIn(PList<T> value) {
		return notIn(getFieldCognome(), value);
	}

	/**
	 * Crea la condizione Cognome NOT IN (value) dove Cognome è la variabile
	 * istanza dell'entity che ha l'attributo Cognome=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cognomeNotIn(T... value) {
		return notIn(getFieldCognome(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da Cognome=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenCognome(Object value, Object value1) {
		return between(getFieldCognome(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * Cognome=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByCognome() {
		return orderByASC(getFieldCognome());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * Cognome=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByCognomeDESC() {
		return orderByDESC(getFieldCognome());
	}

	/**
	 * Crea la condizione eta=value dove eta è la variabile istanza dell'entity
	 * che ha l'attributo eta=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity eta(Object value) {
		return eq(getFieldEta(), value);
	}

	/**
	 * Crea la condizione eta IN (value) dove eta è la variabile istanza
	 * dell'entity che ha l'attributo eta=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity etaIn(PList<T> value) {
		return in(getFieldEta(), value);
	}

	/**
	 * Crea la condizione eta IN (value) dove eta è la variabile istanza
	 * dell'entity che ha l'attributo eta=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity eta(T... value) {
		return in(getFieldEta(), value);
	}

	/**
	 * Crea la condizione eta NOT IN (value) dove eta è la variabile istanza
	 * dell'entity che ha l'attributo eta=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity etaNotIn(PList<T> value) {
		return notIn(getFieldEta(), value);
	}

	/**
	 * Crea la condizione eta NOT IN (value) dove eta è la variabile istanza
	 * dell'entity che ha l'attributo eta=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity etaNotIn(T... value) {
		return notIn(getFieldEta(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da eta=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenEta(Object value, Object value1) {
		return between(getFieldEta(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * eta=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByEta() {
		return orderByASC(getFieldEta());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * eta=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByEtaDESC() {
		return orderByDESC(getFieldEta());
	}

	/**
	 * Crea la condizione cf=value dove cf è la variabile istanza dell'entity
	 * che ha l'attributo cf=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity cf(Object value) {
		return eq(getFieldCf(), value);
	}

	/**
	 * Crea la condizione cf IN (value) dove cf è la variabile istanza
	 * dell'entity che ha l'attributo cf=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cfIn(PList<T> value) {
		return in(getFieldCf(), value);
	}

	/**
	 * Crea la condizione cf IN (value) dove cf è la variabile istanza
	 * dell'entity che ha l'attributo cf=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cf(T... value) {
		return in(getFieldCf(), value);
	}

	/**
	 * Crea la condizione cf NOT IN (value) dove cf è la variabile istanza
	 * dell'entity che ha l'attributo cf=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cfNotIn(PList<T> value) {
		return notIn(getFieldCf(), value);
	}

	/**
	 * Crea la condizione cf NOT IN (value) dove cf è la variabile istanza
	 * dell'entity che ha l'attributo cf=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cfNotIn(T... value) {
		return notIn(getFieldCf(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da cf=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenCf(Object value, Object value1) {
		return between(getFieldCf(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * cf=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByCf() {
		return orderByASC(getFieldCf());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * cf=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByCfDESC() {
		return orderByDESC(getFieldCf());
	}

	/**
	 * Crea la condizione importo=value dove importo è la variabile istanza
	 * dell'entity che ha l'attributo importo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity importo(Object value) {
		return eq(getFieldImporto(), value);
	}

	/**
	 * Crea la condizione importo IN (value) dove importo è la variabile istanza
	 * dell'entity che ha l'attributo importo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity importoIn(PList<T> value) {
		return in(getFieldImporto(), value);
	}

	/**
	 * Crea la condizione importo IN (value) dove importo è la variabile istanza
	 * dell'entity che ha l'attributo importo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity importo(T... value) {
		return in(getFieldImporto(), value);
	}

	/**
	 * Crea la condizione importo NOT IN (value) dove importo è la variabile
	 * istanza dell'entity che ha l'attributo importo=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity importoNotIn(PList<T> value) {
		return notIn(getFieldImporto(), value);
	}

	/**
	 * Crea la condizione importo NOT IN (value) dove importo è la variabile
	 * istanza dell'entity che ha l'attributo importo=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity importoNotIn(T... value) {
		return notIn(getFieldImporto(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da importo=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenImporto(Object value, Object value1) {
		return between(getFieldImporto(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * importo=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByImporto() {
		return orderByASC(getFieldImporto());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * importo=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByImportoDESC() {
		return orderByDESC(getFieldImporto());
	}

	/**
	 * Crea la condizione importo like (value) dove importo è la variabile
	 * istanza dell'entity che ha l'attributo importo=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity importoLike(String value) {
		return like(getFieldImporto(), value);
	}

	/**
	 * Crea la condizione descrizione like (value) dove descrizione è la
	 * variabile istanza dell'entity che ha l'attributo descrizione=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity descrizioneLike(Object value) {
		return like(getFieldDescrizione(), value);
	}

	/**
	 * Crea la condizione descrizione not like (value) dove descrizione è la
	 * variabile istanza dell'entity che ha l'attributo descrizione=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity descrizioneNotLike(Object value) {
		return notLike(getFieldDescrizione(), value);
	}

	/**
	 * Crea la condizione iscritto like (value) dove iscritto è la variabile
	 * istanza dell'entity che ha l'attributo iscritto=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity iscrittoLike(Object value) {
		return like(getFieldIscritto(), value);
	}

	/**
	 * Crea la condizione iscritto not like (value) dove iscritto è la variabile
	 * istanza dell'entity che ha l'attributo iscritto=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity iscrittoNotLike(Object value) {
		return notLike(getFieldIscritto(), value);
	}

	/**
	 * Crea la condizione ente like (value) dove ente è la variabile istanza
	 * dell'entity che ha l'attributo ente=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity enteLike(Object value) {
		return like(getFieldEnte(), value);
	}

	/**
	 * Crea la condizione ente not like (value) dove ente è la variabile istanza
	 * dell'entity che ha l'attributo ente=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity enteNotLike(Object value) {
		return notLike(getFieldEnte(), value);
	}

	/**
	 * Crea la condizione fascicolo like (value) dove fascicolo è la variabile
	 * istanza dell'entity che ha l'attributo fascicolo=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity fascicoloLike(Object value) {
		return like(getFieldFascicolo(), value);
	}

	/**
	 * Crea la condizione fascicolo not like (value) dove fascicolo è la
	 * variabile istanza dell'entity che ha l'attributo fascicolo=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity fascicoloNotLike(Object value) {
		return notLike(getFieldFascicolo(), value);
	}

	/**
	 * Crea la condizione nome like (value) dove nome è la variabile istanza
	 * dell'entity che ha l'attributo nome=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity nomeLike(Object value) {
		return like(getFieldNome(), value);
	}

	/**
	 * Crea la condizione nome not like (value) dove nome è la variabile istanza
	 * dell'entity che ha l'attributo nome=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity nomeNotLike(Object value) {
		return notLike(getFieldNome(), value);
	}

	/**
	 * Crea la condizione cognome like (value) dove cognome è la variabile
	 * istanza dell'entity che ha l'attributo cognome=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cognomeLike(Object value) {
		return like(getFieldCognome(), value);
	}

	/**
	 * Crea la condizione cognome not like (value) dove cognome è la variabile
	 * istanza dell'entity che ha l'attributo cognome=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cognomeNotLike(Object value) {
		return notLike(getFieldCognome(), value);
	}

	/**
	 * Crea la condizione cf like (value) dove cf è la variabile istanza
	 * dell'entity che ha l'attributo cf=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cfLike(Object value) {
		return like(getFieldCf(), value);
	}

	/**
	 * Crea la condizione cf not like (value) dove cf è la variabile istanza
	 * dell'entity che ha l'attributo cf=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cfNotLike(Object value) {
		return notLike(getFieldCf(), value);
	}

	/**
	 * Crea la condizione tipo like (value) dove tipo è la variabile istanza
	 * dell'entity che ha l'attributo tipo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity tipoLike(Object value) {
		return like(getFieldTipo(), value);
	}

	/**
	 * Crea la condizione tipo not like (value) dove tipo è la variabile istanza
	 * dell'entity che ha l'attributo tipo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity tipoNotLike(Object value) {
		return notLike(getFieldTipo(), value);
	}

	/**
	 * Crea la condizione codice like (value) dove codice è la variabile istanza
	 * dell'entity che ha l'attributo codice=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity codiceLike(Object value) {
		return like(getFieldCodice(), value);
	}

	/**
	 * Crea la condizione codice not like (value) dove codice è la variabile
	 * istanza dell'entity che ha l'attributo codice=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity codiceNotLike(Object value) {
		return notLike(getFieldCodice(), value);
	}

	/**
	 * Crea la condizione cassa like (value) dove cassa è la variabile istanza
	 * dell'entity che ha l'attributo cassa=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cassaLike(Object value) {
		return like(getFieldCassa(), value);
	}

	/**
	 * Crea la condizione cassa not like (value) dove cassa è la variabile
	 * istanza dell'entity che ha l'attributo cassa=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cassaNotLike(Object value) {
		return notLike(getFieldCassa(), value);
	}

	/**
	 * Crea la condizione stato like (value) dove stato è la variabile istanza
	 * dell'entity che ha l'attributo stato=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity statoLike(Object value) {
		return like(getFieldStato(), value);
	}

	/**
	 * Crea la condizione stato not like (value) dove stato è la variabile
	 * istanza dell'entity che ha l'attributo stato=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity statoNotLike(Object value) {
		return notLike(getFieldStato(), value);
	}

	/**
	 * Crea la condizione stato1 like (value) dove stato1 è la variabile istanza
	 * dell'entity che ha l'attributo stato1=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity stato1Like(Object value) {
		return like(getFieldStato1(), value);
	}

	/**
	 * Crea la condizione stato1 not like (value) dove stato1 è la variabile
	 * istanza dell'entity che ha l'attributo stato1=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity stato1NotLike(Object value) {
		return notLike(getFieldStato1(), value);
	}

	/**
	 * Crea la condizione citta=value dove citta è la variabile istanza
	 * dell'entity che ha l'attributo citta=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity citta(Object value) {
		return eq(getFieldCitta(), value);
	}

	/**
	 * Crea la condizione citta IN (value) dove citta è la variabile istanza
	 * dell'entity che ha l'attributo citta=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cittaIn(PList<T> value) {
		return in(getFieldCitta(), value);
	}

	/**
	 * Crea la condizione citta IN (value) dove citta è la variabile istanza
	 * dell'entity che ha l'attributo citta=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity citta(T... value) {
		return in(getFieldCitta(), value);
	}

	/**
	 * Crea la condizione citta NOT IN (value) dove citta è la variabile istanza
	 * dell'entity che ha l'attributo citta=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cittaNotIn(PList<T> value) {
		return notIn(getFieldCitta(), value);
	}

	/**
	 * Crea la condizione citta NOT IN (value) dove citta è la variabile istanza
	 * dell'entity che ha l'attributo citta=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cittaNotIn(T... value) {
		return notIn(getFieldCitta(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da citta=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenCitta(Object value, Object value1) {
		return between(getFieldCitta(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * citta=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByCitta() {
		return orderByASC(getFieldCitta());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * citta=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByCittaDESC() {
		return orderByDESC(getFieldCitta());
	}

	/**
	 * Crea la condizione citta like (value) dove citta è la variabile istanza
	 * dell'entity che ha l'attributo citta=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cittaLike(Object value) {
		return like(getFieldCitta(), value);
	}

	/**
	 * Crea la condizione citta not like (value) dove citta è la variabile
	 * istanza dell'entity che ha l'attributo citta=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity cittaNotLike(Object value) {
		return notLike(getFieldCitta(), value);
	}

	/**
	 * Crea la condizione indirizzo=value dove indirizzo è la variabile istanza
	 * dell'entity che ha l'attributo indirizzo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity indirizzo(Object value) {
		return eq(getFieldIndirizzo(), value);
	}

	/**
	 * Crea la condizione indirizzo IN (value) dove indirizzo è la variabile
	 * istanza dell'entity che ha l'attributo indirizzo=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity indirizzoIn(PList<T> value) {
		return in(getFieldIndirizzo(), value);
	}

	/**
	 * Crea la condizione indirizzo IN (value) dove indirizzo è la variabile
	 * istanza dell'entity che ha l'attributo indirizzo=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity indirizzo(T... value) {
		return in(getFieldIndirizzo(), value);
	}

	/**
	 * Crea la condizione indirizzo NOT IN (value) dove indirizzo è la variabile
	 * istanza dell'entity che ha l'attributo indirizzo=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity indirizzoNotIn(PList<T> value) {
		return notIn(getFieldIndirizzo(), value);
	}

	/**
	 * Crea la condizione indirizzo NOT IN (value) dove indirizzo è la variabile
	 * istanza dell'entity che ha l'attributo indirizzo=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity indirizzoNotIn(T... value) {
		return notIn(getFieldIndirizzo(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da indirizzo=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenIndirizzo(Object value, Object value1) {
		return between(getFieldIndirizzo(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * indirizzo=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByIndirizzo() {
		return orderByASC(getFieldIndirizzo());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * indirizzo=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByIndirizzoDESC() {
		return orderByDESC(getFieldIndirizzo());
	}

	/**
	 * Crea la condizione indirizzo like (value) dove indirizzo è la variabile
	 * istanza dell'entity che ha l'attributo indirizzo=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity indirizzoLike(Object value) {
		return like(getFieldIndirizzo(), value);
	}

	/**
	 * Crea la condizione indirizzo not like (value) dove indirizzo è la
	 * variabile istanza dell'entity che ha l'attributo indirizzo=true
	 * nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity indirizzoNotLike(Object value) {
		return notLike(getFieldIndirizzo(), value);
	}

	/**
	 * Crea la condizione titolo=value dove titolo è la variabile istanza
	 * dell'entity che ha l'attributo titolo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity titolo(Object value) {
		return eq(getFieldTitolo(), value);
	}

	/**
	 * Crea la condizione titolo IN (value) dove titolo è la variabile istanza
	 * dell'entity che ha l'attributo titolo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity titoloIn(PList<T> value) {
		return in(getFieldTitolo(), value);
	}

	/**
	 * Crea la condizione titolo IN (value) dove titolo è la variabile istanza
	 * dell'entity che ha l'attributo titolo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity titolo(T... value) {
		return in(getFieldTitolo(), value);
	}

	/**
	 * Crea la condizione titolo NOT IN (value) dove titolo è la variabile
	 * istanza dell'entity che ha l'attributo titolo=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity titoloNotIn(PList<T> value) {
		return notIn(getFieldTitolo(), value);
	}

	/**
	 * Crea la condizione titolo NOT IN (value) dove titolo è la variabile
	 * istanza dell'entity che ha l'attributo titolo=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity titoloNotIn(T... value) {
		return notIn(getFieldTitolo(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da titolo=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenTitolo(Object value, Object value1) {
		return between(getFieldTitolo(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * titolo=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByTitolo() {
		return orderByASC(getFieldTitolo());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * titolo=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByTitoloDESC() {
		return orderByDESC(getFieldTitolo());
	}

	/**
	 * Crea la condizione titolo like (value) dove titolo è la variabile istanza
	 * dell'entity che ha l'attributo titolo=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity titoloLike(Object value) {
		return like(getFieldTitolo(), value);
	}

	/**
	 * Crea la condizione titolo not like (value) dove titolo è la variabile
	 * istanza dell'entity che ha l'attributo titolo=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity titoloNotLike(Object value) {
		return notLike(getFieldTitolo(), value);
	}

	/**
	 * Crea la condizione piva=value dove piva è la variabile istanza
	 * dell'entity che ha l'attributo piva=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity piva(Object value) {
		return eq(getFieldPiva(), value);
	}

	/**
	 * Crea la condizione piva IN (value) dove piva è la variabile istanza
	 * dell'entity che ha l'attributo piva=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity pivaIn(PList<T> value) {
		return in(getFieldPiva(), value);
	}

	/**
	 * Crea la condizione piva IN (value) dove piva è la variabile istanza
	 * dell'entity che ha l'attributo piva=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity piva(T... value) {
		return in(getFieldPiva(), value);
	}

	/**
	 * Crea la condizione piva NOT IN (value) dove piva è la variabile istanza
	 * dell'entity che ha l'attributo piva=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity pivaNotIn(PList<T> value) {
		return notIn(getFieldPiva(), value);
	}

	/**
	 * Crea la condizione piva NOT IN (value) dove piva è la variabile istanza
	 * dell'entity che ha l'attributo piva=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity pivaNotIn(T... value) {
		return notIn(getFieldPiva(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da piva=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenPiva(Object value, Object value1) {
		return between(getFieldPiva(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * piva=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByPiva() {
		return orderByASC(getFieldPiva());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * piva=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByPivaDESC() {
		return orderByDESC(getFieldPiva());
	}

	/**
	 * Crea la condizione piva like (value) dove piva è la variabile istanza
	 * dell'entity che ha l'attributo piva=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity pivaLike(Object value) {
		return like(getFieldPiva(), value);
	}

	/**
	 * Crea la condizione piva not like (value) dove piva è la variabile istanza
	 * dell'entity che ha l'attributo piva=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity pivaNotLike(Object value) {
		return notLike(getFieldPiva(), value);
	}

	/**
	 * Crea la condizione nazione=value dove nazione è la variabile istanza
	 * dell'entity che ha l'attributo nazione=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity nazione(Object value) {
		return eq(getFieldNazione(), value);
	}

	/**
	 * Crea la condizione nazione IN (value) dove nazione è la variabile istanza
	 * dell'entity che ha l'attributo nazione=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity nazioneIn(PList<T> value) {
		return in(getFieldNazione(), value);
	}

	/**
	 * Crea la condizione nazione IN (value) dove nazione è la variabile istanza
	 * dell'entity che ha l'attributo nazione=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity nazione(T... value) {
		return in(getFieldNazione(), value);
	}

	/**
	 * Crea la condizione nazione NOT IN (value) dove nazione è la variabile
	 * istanza dell'entity che ha l'attributo nazione=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity nazioneNotIn(PList<T> value) {
		return notIn(getFieldNazione(), value);
	}

	/**
	 * Crea la condizione nazione NOT IN (value) dove nazione è la variabile
	 * istanza dell'entity che ha l'attributo nazione=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity nazioneNotIn(T... value) {
		return notIn(getFieldNazione(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da nazione=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenNazione(Object value, Object value1) {
		return between(getFieldNazione(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * nazione=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByNazione() {
		return orderByASC(getFieldNazione());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * nazione=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByNazioneDESC() {
		return orderByDESC(getFieldNazione());
	}

	/**
	 * Crea la condizione nazione like (value) dove nazione è la variabile
	 * istanza dell'entity che ha l'attributo nazione=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity nazioneLike(Object value) {
		return like(getFieldNazione(), value);
	}

	/**
	 * Crea la condizione nazione not like (value) dove nazione è la variabile
	 * istanza dell'entity che ha l'attributo nazione=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity nazioneNotLike(Object value) {
		return notLike(getFieldNazione(), value);
	}

	/**
	 * Crea la condizione totale=value dove totale è la variabile istanza
	 * dell'entity che ha l'attributo totale=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity totale(Object value) {
		return eq(getFieldTotale(), value);
	}

	/**
	 * Crea la condizione totale IN (value) dove totale è la variabile istanza
	 * dell'entity che ha l'attributo totale=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity totaleIn(PList<T> value) {
		return in(getFieldTotale(), value);
	}

	/**
	 * Crea la condizione totale IN (value) dove totale è la variabile istanza
	 * dell'entity che ha l'attributo totale=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity totale(T... value) {
		return in(getFieldTotale(), value);
	}

	/**
	 * Crea la condizione totale NOT IN (value) dove totale è la variabile
	 * istanza dell'entity che ha l'attributo totale=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity totaleNotIn(PList<T> value) {
		return notIn(getFieldTotale(), value);
	}

	/**
	 * Crea la condizione totale NOT IN (value) dove totale è la variabile
	 * istanza dell'entity che ha l'attributo totale=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity totaleNotIn(T... value) {
		return notIn(getFieldTotale(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da totale=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenTotale(Object value, Object value1) {
		return between(getFieldTotale(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * totale=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByTotale() {
		return orderByASC(getFieldTotale());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * totale=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByTotaleDESC() {
		return orderByDESC(getFieldTotale());
	}

	/**
	 * Crea la condizione totale like (value) dove totale è la variabile istanza
	 * dell'entity che ha l'attributo totale=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity totaleLike(Object value) {
		return like(getFieldTotale(), value);
	}

	/**
	 * Crea la condizione totale not like (value) dove totale è la variabile
	 * istanza dell'entity che ha l'attributo totale=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity totaleNotLike(Object value) {
		return notLike(getFieldTotale(), value);
	}

	/**
	 * Crea la condizione valore=value dove valore è la variabile istanza
	 * dell'entity che ha l'attributo valore=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity valore(Object value) {
		return eq(getFieldValore(), value);
	}

	/**
	 * Crea la condizione valore IN (value) dove valore è la variabile istanza
	 * dell'entity che ha l'attributo valore=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity valoreIn(PList<T> value) {
		return in(getFieldValore(), value);
	}

	/**
	 * Crea la condizione valore IN (value) dove valore è la variabile istanza
	 * dell'entity che ha l'attributo valore=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity valore(T... value) {
		return in(getFieldValore(), value);
	}

	/**
	 * Crea la condizione valore NOT IN (value) dove valore è la variabile
	 * istanza dell'entity che ha l'attributo valore=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity valoreNotIn(PList<T> value) {
		return notIn(getFieldValore(), value);
	}

	/**
	 * Crea la condizione valore NOT IN (value) dove valore è la variabile
	 * istanza dell'entity che ha l'attributo valore=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity valoreNotIn(T... value) {
		return notIn(getFieldValore(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da valore=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenValore(Object value, Object value1) {
		return between(getFieldValore(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * valore=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByValore() {
		return orderByASC(getFieldValore());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * valore=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByValoreDESC() {
		return orderByDESC(getFieldValore());
	}

	/**
	 * Crea la condizione valore like (value) dove valore è la variabile istanza
	 * dell'entity che ha l'attributo valore=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity valoreLike(Object value) {
		return like(getFieldValore(), value);
	}

	/**
	 * Crea la condizione valore not like (value) dove valore è la variabile
	 * istanza dell'entity che ha l'attributo valore=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity valoreNotLike(Object value) {
		return notLike(getFieldValore(), value);
	}

	/**
	 * Crea la condizione username=value dove username è la variabile istanza
	 * dell'entity che ha l'attributo username=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity username(Object value) {
		return eq(getFieldUsername(), value);
	}

	/**
	 * Crea la condizione username IN (value) dove username è la variabile
	 * istanza dell'entity che ha l'attributo username=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity usernameIn(PList<T> value) {
		return in(getFieldUsername(), value);
	}

	/**
	 * Crea la condizione username IN (value) dove username è la variabile
	 * istanza dell'entity che ha l'attributo username=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity username(T... value) {
		return in(getFieldUsername(), value);
	}

	/**
	 * Crea la condizione username NOT IN (value) dove username è la variabile
	 * istanza dell'entity che ha l'attributo username=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity usernameNotIn(PList<T> value) {
		return notIn(getFieldUsername(), value);
	}

	/**
	 * Crea la condizione username NOT IN (value) dove username è la variabile
	 * istanza dell'entity che ha l'attributo username=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity usernameNotIn(T... value) {
		return notIn(getFieldUsername(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da username=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenUsername(Object value, Object value1) {
		return between(getFieldUsername(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * username=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByUsername() {
		return orderByASC(getFieldUsername());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * username=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByUsernameDESC() {
		return orderByDESC(getFieldUsername());
	}

	/**
	 * Crea la condizione username like (value) dove username è la variabile
	 * istanza dell'entity che ha l'attributo username=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity usernameLike(Object value) {
		return like(getFieldUsername(), value);
	}

	/**
	 * Crea la condizione username not like (value) dove username è la variabile
	 * istanza dell'entity che ha l'attributo username=true nell'annotazione
	 * Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity usernameNotLike(Object value) {
		return notLike(getFieldUsername(), value);
	}

	/**
	 * Crea la condizione email=value dove email è la variabile istanza
	 * dell'entity che ha l'attributo email=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public BaseEntity email(Object value) {
		return eq(getFieldEmail(), value);
	}

	/**
	 * Crea la condizione email IN (value) dove email è la variabile istanza
	 * dell'entity che ha l'attributo email=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity emailIn(PList<T> value) {
		return in(getFieldEmail(), value);
	}

	/**
	 * Crea la condizione email IN (value) dove email è la variabile istanza
	 * dell'entity che ha l'attributo email=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity email(T... value) {
		return in(getFieldEmail(), value);
	}

	/**
	 * Crea la condizione email NOT IN (value) dove email è la variabile istanza
	 * dell'entity che ha l'attributo email=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity emailNotIn(PList<T> value) {
		return notIn(getFieldEmail(), value);
	}

	/**
	 * Crea la condizione email NOT IN (value) dove email è la variabile istanza
	 * dell'entity che ha l'attributo email=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity emailNotIn(T... value) {
		return notIn(getFieldEmail(), value);
	}

	/**
	 * Realizza la condizione di BETWEEN nella clausola where. Termina
	 * automaticamente con AND per concatenare la prossima condizione. Esegue la
	 * BETWEEN sul campo individuato da email=true nell'annotazione Column
	 * 
	 * @param value
	 * @param value1
	 * @return BaseEntity
	 */
	public BaseEntity betweenEmail(Object value, Object value1) {
		return between(getFieldEmail(), value, value1);
	}

	/**
	 * Esegue un ordinamento ascendente per la variabile identificata dal
	 * email=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByEmail() {
		return orderByASC(getFieldEmail());
	}

	/**
	 * Esegue un ordinamento discendente per la variabile identificata dal
	 * email=true nell'annotazione Column
	 * 
	 * @return BaseEntity
	 */
	public BaseEntity orderByEmailDESC() {
		return orderByDESC(getFieldEmail());
	}

	/**
	 * Crea la condizione email like (value) dove email è la variabile istanza
	 * dell'entity che ha l'attributo email=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity emailLike(Object value) {
		return like(getFieldEmail(), value);
	}

	/**
	 * Crea la condizione email not like (value) dove email è la variabile
	 * istanza dell'entity che ha l'attributo email=true nell'annotazione Column
	 * 
	 * @param value
	 * @return BaseEntity
	 */
	public <T> BaseEntity emailNotLike(Object value) {
		return notLike(getFieldEmail(), value);
	}

	public PList<String> getFieldsToUpdate() {
		return this.fieldsToUpdate;
	}

	public BaseEntity setFieldsToUpdate(PList<String> fieldsToExclude) {
		this.fieldsToUpdate.addAll(fieldsToExclude);
		return this;
	}

	public BaseEntity setFieldsToUpdate(String... fieldsToExclude) {
		this.fieldsToUpdate.addAll(arrayToList(fieldsToExclude));
		return this;
	}

	public BaseEntity setOnly(String... fieldsToUpdate) {
		return setFieldsToUpdate(fieldsToUpdate);
	}

}
