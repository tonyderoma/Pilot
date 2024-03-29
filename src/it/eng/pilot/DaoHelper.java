package it.eng.pilot;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe Helper per implementare un DAO che utilizza le Entity Pilot. Occorre
 * definire in testa alla classe custom che estende DaoHelper, l'annotazione @DaoConfig
 * con specificato il pkg contenitore delle entities, l'elenco di entities con
 * nome classe senza .class, il codice utente e il codice applicativo da usare
 * nelle query. Serve anche per eseguire query di selezione e invocazione di
 * stored procedures scritte nel file queries.properties e contenuto nel pkg
 * indicato dal valore dell'attributo pkgPropertiesFile. Nelle select tramite
 * query (metodi select*) se il parametro queryName inizia per SELECT e ha al
 * suo interno più di uno spazio e contiene anche la parola chiave FROM , allora
 * viene considerato come query diretta scritta manualmente e non come chiave di
 * file di properties.
 * 
 * In tutte le query, i parametri passati a Null NON VENGONO APPLICATI nella
 * formazione della where condition.
 * 
 * 
 * Le impostazioni di configurazione indicate in @DaoConfig possono essere
 * ridefinite all'interno del file di properties indicato dall'attributo
 * propertyFile in questo modo: autocommit=true/false (oppure per true uno tra i
 * valori 1,S,SI,Y,true) purgeDB=true/false (oppure per true uno tra i valori
 * 1,S,SI,Y,true) inMemory=true/false (oppure per true uno tra i valori
 * 1,S,SI,Y,true) fullReport=true/false (oppure per true uno tra i valori
 * 1,S,SI,Y,true) logWhileRunning=true/false (oppure per true uno tra i valori
 * 1,S,SI,Y,true) timeout=8 (indica 8 secondi) updateDeleteLimit=300 (indica un
 * valore di 300 righe al massimo impattate da update/delete)
 * 
 * @author Antonio Corinaldi
 * 
 */
@SuppressWarnings("unchecked")
public class DaoHelper extends PilotSupport {

	private static final String _RECORDS = " records";
	private static final String TABELLE_PRESENTI_IN_MEMORY = "Tabelle presenti";
	private static final String RECORD_TOTALI_IN_MEMORY = "Record totali.......";
	private static final String TITLE_IN_MEMORY = "IN MEMORY DATABASE!!!!!!";
	private static final String DB_FILE = "_DB.log";
	private static final String DB_FILE_EXPORT = "_DBExport.log";
	public static String QUERY_FILE = "queries.properties";
	private static final String ON = " ON ";
	private static final String JOIN = " JOIN ";
	private static final String QUERIES = "queries";
	private static final String COMMIT = "-------COMMIT";
	private static final String START_COMMIT = "COMMIT";
	private static final String ROLLBACK = "-------ROLLBACK";
	private static final String START_ROLLBACK = "ROLLBACK";
	private Integer numCommitRollback = 0;
	private static final String SLASH = "/";
	public static final String DAYS = "days";
	public static final String HOURS = "hours";
	public static final String MINUTES = "minutes";
	public static final String SECONDS = "seconds";
	public static final String MILLISECONDS = "milliseconds";
	private static final String TXT = ".txt";
	private static final String STORED_PROCEDURES = "STORED PROCEDURES";
	private static final String TOTALE_GENERALE = "TOTALE GENERALE";
	private static final String TITOLO_TOTALE = "RECORD TOTALI COINVOLTI PER TIPO QUERY";
	private static final String INTEGER = "Integer";
	private static final String STRING = "String";
	private static final String LONG = "Long";
	private static final String LF = "\n";
	private static final String TAB = "\t";
	private static final String DOT = ".";
	private static final String NUM_RECORD = " record/";
	private static final String NUM_QUERIES = " queries ok/";
	private static final String NUM_ERRORS = " errors/";
	protected static final String EQ = "=";
	protected static final String Q_MARK = "?";
	public static final String SPACE = " ";
	private static final String PIPE = BaseDaoEntity.PIPE;
	public static final String DOUBLE_SPACE = "  ";
	private static final String THREE_SPACE = "   ";
	private static final String CLOSE_GRAFFA = "}";
	private static final String OPEN_GRAFFA = "{";
	public static final String COMMA = ",";
	public static final String CLOSE_QUADRA = "]";
	public static final String OPEN_QUADRA = "[";
	private static final String CALL = "CALL";
	private static final String QUERIES_PROPERTIES = SLASH + QUERY_FILE;
	private static final String SET = " SET ";
	private static final String WHERE = " WHERE ";
	private static final String FROM = " FROM ";
	// private static final String UNION = " UNION ";
	public static final String LEFT_ARROW = "<---";
	public static final String RIGHT_ARROW = "--->";
	private static final String SEP = "_";
	private static final int l = 150;
	private static final String TOTAL_RECORD = TITOLO_TOTALE;
	private static final String SP_CALLED = "STORED PROCEDURES CALLED";
	private static final String QUERY_BY_TABLE = "QUERY BY TABLE";
	private static final String QUERY_ESEGUITE_IN_ERRORE = "QUERY ESEGUITE IN ERRORE";
	private static final String START_SELECT = "SELECT ";
	private static final String START_UPDATE = "UPDATE ";
	private static final String START_INSERT = "INSERT ";
	private static final String START_DELETE = "DELETE ";
	private static final String DELETE = LEFT_ARROW + START_DELETE;
	private static final String INSERT = LEFT_ARROW + START_INSERT;
	private static final String POST_QUERY_IN = "POST-QUERY IN ";
	private static final String UPDATE = LEFT_ARROW + START_UPDATE;
	private static final String SELECT = LEFT_ARROW + START_SELECT;
	private static final int TIMEOUT_DEFAULT = 60;
	private Connection conn;
	private String codUtente;
	private String codAppl;
	private PList<String> container = plstr();
	private String pkgName;
	private Class[] elencoClassi;
	private PList<String> entities = plstr();
	private Map<String, Map<?, ?>> mp = new HashMap<String, Map<?, ?>>();
	private Map<String, PList<? extends BaseEntity>> tableCache = new HashMap<String, PList<? extends BaseEntity>>();
	private Long totalRecSelect = 0l;
	private Long totalRecUpdate = 0l;
	private Long totalRecInsert = 0l;
	private Long totalRecDelete = 0l;
	protected static DaoHelper INSTANCE;
	private Map<String, Integer> errorsQueryType = new HashMap<String, Integer>();
	private static final String ERROR_MARK = BaseDaoEntity.ERROR_MARK;
	public static final String RECORD = BaseDaoEntity.RECORD;
	private PList<String> totalRecords = plstr();
	private Map<String, String> queryFromProp = new HashMap<String, String>();
	private Map<String, DaoHelperBeanSelect> mapBeanSelect = new HashMap<String, DaoHelperBeanSelect>();
	private Map<String, EntityDetail> mapEnts = new HashMap<String, EntityDetail>();
	private PMap<String, PList<Entity>> db = pmapl();
	private final static String UPDATE_DELETE_LIMIT = "updateDeleteLimit";
	private final static String TIMEOUT = "timeout";
	private final static String IMPACT = "impact";
	private final static String OUT_PATH = "outPath";
	private final static String IMPACT_ALIAS = "impactAlias";
	private final static String LOG_WHILE_RUNNING = "logWhileRunning";
	private final static String AUTOCOMMIT = "autocommit";
	private final static String IN_MEMORY = "inMemory";
	private final static String DB2 = "db2";
	private final static String PURGE_DB = "purgeDb";
	private Integer updateDeleteLimit;
	private boolean logWhileRunning;
	private boolean autocommit;
	private boolean inMemory;
	private boolean purgeDb;
	private Integer timeout;
	private final static String FLUSH_THRESHOLD = "flushThreshold";
	private Integer flushThreshold;
	private DaoOutput daoOutput = new DaoOutput();
	private Date start = now();
	private boolean impact;
	private String outPath;
	private PList<String> impactAlias = plstr();
	private boolean disableContainer;
	private boolean dsMode;
	private DataSource ds;
	private boolean fromBatchCall;
	protected Logger logger = LoggerFactory.getLogger(getClass().getName());
	private boolean db2;
	public static String[] sqlKeywords = { "SELECT", "INSERT", "UPDATE", "DELETE", "DROP", "ALTER", "TRUNCATE", ";", "--", "/*", "*/", " OR ", " AND " };
	protected static final String QS_START = Pilot.QS_START;

	private void loadProperties() throws Exception {
		if (notNull(getClass().getAnnotation(DaoConfig.class))) {
			if (notNull(getClass().getAnnotation(DaoConfig.class).propertyFile())) {
				setUpdateDeleteLimit(getKeyInt(UPDATE_DELETE_LIMIT, getClass().getAnnotation(DaoConfig.class).updateDeleteLimit()));
				setLogWhileRunning(getKeyBool(LOG_WHILE_RUNNING, getClass().getAnnotation(DaoConfig.class).logWhileRunning()));
				if (!isDsMode()) {
					setAutocommit(getKeyBool(AUTOCOMMIT, getClass().getAnnotation(DaoConfig.class).autocommit()));
				}
				setDb2(getKeyBool(DB2, getClass().getAnnotation(DaoConfig.class).db2()));
				setInMemory(getKeyBool(IN_MEMORY, getClass().getAnnotation(DaoConfig.class).inMemory()));
				setPurgeDb(getKeyBool(PURGE_DB, getClass().getAnnotation(DaoConfig.class).purgeDB()));
				setTimeout(getKeyInt(TIMEOUT, getClass().getAnnotation(DaoConfig.class).timeout()));
				setFlushThreshold(getKeyInt(FLUSH_THRESHOLD, getClass().getAnnotation(DaoConfig.class).flushThreshold()));
				setImpact(getKeyBool(IMPACT, getClass().getAnnotation(DaoConfig.class).impact()));
				setOutPath(getKey(OUT_PATH, getClass().getAnnotation(DaoConfig.class).outPath()));
				setImpactAlias(valIfNull(getKeyList(IMPACT_ALIAS), arrayToList(getClass().getAnnotation(DaoConfig.class).impactAlias())));

			} else {
				setUpdateDeleteLimit(getClass().getAnnotation(DaoConfig.class).updateDeleteLimit());
				setLogWhileRunning(getClass().getAnnotation(DaoConfig.class).logWhileRunning());
				if (!isDsMode()) {
					setAutocommit(getClass().getAnnotation(DaoConfig.class).autocommit());
				}
				setInMemory(getClass().getAnnotation(DaoConfig.class).inMemory());
				setPurgeDb(getClass().getAnnotation(DaoConfig.class).purgeDB());
				setTimeout(getClass().getAnnotation(DaoConfig.class).timeout());
				setFlushThreshold(getClass().getAnnotation(DaoConfig.class).flushThreshold());
				setImpact(getClass().getAnnotation(DaoConfig.class).impact());
				setOutPath(getClass().getAnnotation(DaoConfig.class).outPath());
				setImpactAlias(arrayToList(getClass().getAnnotation(DaoConfig.class).impactAlias()));
				setDb2(getClass().getAnnotation(DaoConfig.class).db2());
			}
		} else {
			throw new Exception("Specificare l'annotazione @DaoConfig per poter operare con il dao");
		}
		logProps();
	}

	private void logProps() {
		log(UPDATE_DELETE_LIMIT, EQ, getUpdateDeleteLimit());
		log(LOG_WHILE_RUNNING, EQ, isLogWhileRunning());
		log(AUTOCOMMIT, EQ, isAutocommit());
		log(IN_MEMORY, EQ, isInMemory());
		log(PURGE_DB, EQ, isPurgeDb());
		log(TIMEOUT, EQ, getTimeout());
		log(FLUSH_THRESHOLD, EQ, getFlushThreshold());
		log(IMPACT, EQ, isImpact());
		log(OUT_PATH, EQ, getOutPath());
		log(IMPACT_ALIAS, EQ, getImpactAlias());
		log(DB2, EQ, isDb2());
	}

	/**
	 * Ritorna il valore di tipo stringa corrispondente alla key all'interno del
	 * file di properties specificato dal valore dell'attributo propertiesFile
	 * dell'annotazione @DaoConfig.
	 * 
	 * @param key
	 * @return String
	 */
	public String getKey(String key) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		String ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKey(propFile, key);
		}
		return ret;
	}

	/**
	 * Ritorna il valore di tipo stringa corrispondente alla key all'interno del
	 * file di properties specificato dal valore dell'attributo propertiesFile
	 * dell'annotazione @DaoConfig. Se non trova corrispondenza ritorna il
	 * valore defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return String
	 */
	public String getKey(String key, String defaultValue) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		String ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKey(key, defaultValue);
		}
		return ret;
	}

	/**
	 * Ritorna il valore Integer della key all'interno del file di properties
	 * specificato dall'attributo propertyFile di @DaoConfig. Se la chiave non è
	 * mappata ritorna il defaultValue
	 * 
	 * @param key
	 * @return Integer
	 */
	public Integer getKeyInt(String key, Integer defaultValue) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		Integer ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyInt(key, defaultValue);
		}
		return ret;
	}

	/**
	 * Ritorna il valore Integer della key all'interno del file di properties
	 * specificato dall'attributo propertyFile di @DaoConfig.
	 * 
	 * @param key
	 * @return Integer
	 */
	public Integer getKeyInt(String key) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		Integer ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyInt(key);
		}
		return ret;
	}

	/**
	 * Ritorna il valore Long della key all'interno del file di properties
	 * specificato dall'attributo propertyFile di @DaoConfig. Se la chiave non è
	 * mappata ritorna il defaultValue.
	 * 
	 * @param key
	 * @return Long
	 */
	public Long getKeyLong(String key, Long defaultValue) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		Long ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyLong(key, defaultValue);
		}
		return ret;
	}

	private void missingPropFile(String propFile) {
		logError("File di properties", propFile, "mancante!");
	}

	/**
	 * Ritorna il valore Long della key all'interno del file di properties
	 * specificato dall'attributo propertyFile di @DaoConfig.
	 * 
	 * @param key
	 * @return Long
	 */
	public Long getKeyLong(String key) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		Long ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyLong(key);
		}
		return ret;
	}

	/**
	 * Ritorna true se il valore corrispondente alla chiave key è uno tra i
	 * valori 1,"true","S","SI","Y", false altrimenti
	 * 
	 * @param key
	 * @return boolean
	 */
	public Boolean getKeyBool(String key) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		Boolean ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyBool(key);
		}
		return ret;
	}

	/**
	 * Ritorna true se il valore corrispondente alla chiave key è uno tra i
	 * valori 1,"true","S","SI","Y", false altrimenti. Se il valore è null
	 * ritorna il defaultValue booleano
	 * 
	 * @param key
	 * @return boolean
	 */
	public Boolean getKeyBool(String key, boolean defaultValue) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		Boolean ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyBool(key, defaultValue);
		}
		return ret;
	}

	/**
	 * Ritorna una PList<String> contenente i valori corrispondenti alla chiave
	 * key quando questi sono separati da ","
	 * 
	 * @param key
	 * @return PList<String>
	 */
	public PList<String> getKeyList(String key) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		PList<String> ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyList(key);
		}
		return ret;
	}

	/**
	 * Ritorna una PList<String> contenente i valori corrispondenti alla chiave
	 * key quando questi sono separati dal carattere separator
	 * 
	 * @param key
	 * @return PList<String>
	 */
	public PList<String> getKeyList(String key, String separator) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		PList<String> ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyList(key, separator);
		}
		return ret;
	}

	/**
	 * Ritorna una PList<Integer> contenente i valori Integer corrispondenti
	 * alla chiave key quando questi sono separati da ","
	 * 
	 * @param key
	 * @return PList<Integer>
	 */
	public PList<Integer> getKeyListInt(String key) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		PList<Integer> ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyListInt(key);
		}
		return ret;
	}

	/**
	 * Ritorna una PList<Integer> contenente i valori Integer corrispondenti
	 * alla chiave key quando questi sono separati dal carattere separator
	 * 
	 * @param key
	 * @param separator
	 * @return PList<Integer>
	 */
	public PList<Integer> getKeyListInt(String key, String separator) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		PList<Integer> ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyListInt(key, separator);
		}
		return ret;
	}

	/**
	 * Ritorna una PList<Long> contenente i valori Integer corrispondenti alla
	 * chiave key quando questi sono separati da ","
	 * 
	 * @param key
	 * @return PList<Long>
	 */
	public PList<Long> getKeyListLong(String key) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		PList<Long> ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyListLong(key);
		}
		return ret;
	}

	/**
	 * Ritorna una PList<Long> contenente i valori Integer corrispondenti alla
	 * chiave key quando questi sono separati dal carattere separator
	 * 
	 * @param key
	 * @param separator
	 * @return PList<Long>
	 */
	public PList<Long> getKeyListLong(String key, String separator) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		PList<Long> ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyListLong(key, separator);
		}
		return ret;
	}

	/**
	 * Ritorna una PList<Double> contenente i valori Integer corrispondenti alla
	 * chiave key quando questi sono separati da ","
	 * 
	 * @param key
	 * @return PList<Double>
	 */
	public PList<Double> getKeyListDouble(String key) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		PList<Double> ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyListDouble(key);
		}
		return ret;
	}

	/**
	 * Ritorna una PList<Double> contenente i valori Integer corrispondenti alla
	 * chiave key quando questi sono separati dal carattere separator
	 * 
	 * @param key
	 * @param separator
	 * @return PList<Double>
	 */
	public PList<Double> getKeyListDouble(String key, String separator) {
		String propFile = getClass().getAnnotation(DaoConfig.class).propertyFile();
		PList<Double> ret = null;
		if (Null(propFile)) {
			missingPropFile(propFile);
		} else {
			setPropertyFile(propFile);
			ret = p.getKeyListDouble(key, separator);
		}
		return ret;
	}

	private String db() {
		return str(getClass().getSimpleName(), DB_FILE);
	}

	private String dbExport() {
		return str(getClass().getSimpleName(), DB_FILE_EXPORT);
	}

	private void loadDb() {
		if (isInMemory()) {
			if (isPurgeDb()) {
				purgeDb();
			}
			if (fileExists(db())) {
				try {
					Object o = deserializeFromFile(db());
					if (notNull(o)) {
						setDb((PMap<String, PList<Entity>>) o);
						// getDb().setLog(getLog());
					}
				} catch (Exception e) {
					logError("Errore durante la deserializzazione dal file ", db());
				}
			}
		}
	}

	/**
	 * Rimuove il file del db in memoria (modalità inMemory=true) ripartendo
	 * così da una situazione ex-novo
	 */
	public void purgeDb() {
		if (isInMemory()) {
			removeFile(db());
		}
	}

	/**
	 * Metodo alias di purgeDb
	 */
	public void dropDb() {
		purgeDb();
	}

	/**
	 * Crea la connessione tramite il dataSource e la imposta al dao
	 * 
	 * @param nomeDataSource
	 * @throws Exception
	 */
	public void createConnection(String nomeDataSource) throws Exception {
		setConn(getDs().getConnection());
	}

	private Connection getConnection(String userName, String pwd, String url) throws SQLException {
		if (!isInMemory()) {
			Properties connectionProps = new Properties();
			connectionProps.put("user", userName);
			connectionProps.put("password", pwd);
			return DriverManager.getConnection(url, connectionProps);
		}
		return null;

	}

	/**
	 * Costruttore del dao con passaggio url/userName/password
	 * 
	 * @param userName
	 * @param pwd
	 * @param url
	 * @throws Exception
	 */
	protected DaoHelper(String userName, String pwd, String url) throws Exception {
		loadProperties();
		setConn(getConnection(userName, pwd, url));
		setElencoClassi(getClasses());
		loadDb();
	}

	/**
	 * Costruttore del dao con passaggio della connessione
	 * 
	 * @param conn
	 * @throws Exception
	 */
	protected DaoHelper(Connection conn) throws Exception {
		loadProperties();
		setConn(conn);
		setElencoClassi(getClasses());
	}

	private void setAutocommit() throws SQLException {
		if (notNull(conn))
			conn.setAutoCommit(isAutocommit());
	}

	/**
	 * Costruttore per ambienti web in cui passo direttamente il DataSource. La
	 * connessione verrà prelevata dal DataSource e automaticamente chiusa al
	 * termine di ogni query restituendola al DataSource
	 * 
	 * @param ds
	 * @throws Exception
	 */
	protected DaoHelper(DataSource ds) throws Exception {
		setDs(ds);
		loadProperties();
		setElencoClassi(getClasses());
		loadDb();
	}

	/**
	 * Costruttore per ambienti web in cui passo direttamente il DataSource. La
	 * connessione verrà prelevata dal DataSource e automaticamente chiusa al
	 * termine di ogni query restituendola al DataSource. Se fromBatchCall è
	 * true, preservo il report delle query, che quindi conterrà tutte le query
	 * eseguite e le relative statistiche e al termine della chiamata verrà
	 * distrutta l'istanza del dao
	 * 
	 * 
	 * @param ds
	 * @param fromBatchCall
	 * @throws Exception
	 */
	protected DaoHelper(DataSource ds, boolean fromBatchCall) throws Exception {
		setFromBatchCall(fromBatchCall);
		setDs(ds);
		loadProperties();
		setElencoClassi(getClasses());
		loadDb();
	}

	private void setDs(DataSource ds) throws NamingException {
		this.ds = ds;
		setEjbMode();
	}

	private void setEjbMode() {
		setDsMode(true);
		setDisableContainer(!isFromBatchCall());
	}

	protected DaoHelper() throws Exception {
		loadProperties();
	}

	/**
	 * Ritorna la query scritta nel file di properties queries.properties e
	 * corrispondente alla chiave queryName
	 * 
	 * @param queryName
	 * @return String
	 * @throws IOException
	 * @throws SQLException
	 */
	private String getQueryByNameNoQs(String queryName) throws IOException, SQLException {
		DaoConfig daoConf = getClass().getAnnotation(DaoConfig.class);
		String fileProp = str(daoConf.pkgPropertiesFile(), QUERIES_PROPERTIES);
		DaoHelperBeanSelect bs = new DaoHelperBeanSelect();
		return bs.getQueryProp(fileProp, queryName);
	}

	/**
	 * Ritorna la query scritta nel file di properties queries.properties e
	 * corrispondente alla chiave queryName. Se il nome query termina con una
	 * parte di querystring, questa parte verrà usata per sostituire i
	 * placeholder scritti nel testo della query con i corrispondenti valori
	 * presi dalla query string. Esempio: provaPeriodi?{cassa}=and
	 * reprc_id_cassa between to_date('01/98/29898','dd/mm/yyyy') and
	 * to_date('01/98/29898','dd/mm/yyyy')&{tipo}=OR REPRC_ID_ISCRITTO IN (
	 * :idiscritto ) la query provaPeriodi presenta dei placeholder {cassa} e
	 * {tipo} al suo interno. Questi placeholder verranno sostituiti con i
	 * valori indicati nella parte di querystring ossia dopo il '?'
	 * 
	 * @param queryName
	 * @return String
	 * @throws IOException
	 * @throws SQLException
	 */
	public String getQueryByName(String queryName) throws IOException, SQLException {
		if (!isLike(queryName, QS_START))
			return getQueryByNameNoQs(queryName);
		DaoConfig daoConf = getClass().getAnnotation(DaoConfig.class);
		String fileProp = str(daoConf.pkgPropertiesFile(), QUERIES_PROPERTIES);
		DaoHelperBeanSelect bs = new DaoHelperBeanSelect();
		String before = substring(queryName, null, false, false, QS_START, false, false);
		String query = bs.getQueryProp(fileProp, before);
		PList<KeyValue<String, String>> qs = getQueryString(queryName);
		if (notNull(qs)) {
			query = substring(query, null, false, false, QS_START, false, false);
		}
		for (KeyValue<String, String> kv : safe(qs)) {
			query = replace(query, kv.getKey(), (String) kv.getValue());
		}
		return query;
	}

	private Connection getConnection() throws SQLException {
		if (isDsMode()) {
			// log("APRO LA CONNESSIONE !!!!!!!!!!!!!!!!");
			setConn(getDs().getConnection());
		}
		return getConn();
	}

	private DaoHelperBeanSelect getBeanSelect(String queryName) throws Exception {
		flushContainer();
		if (Null(mapBeanSelect.get(queryName))) {
			DaoConfig daoConf = getClass().getAnnotation(DaoConfig.class);
			if (Null(queryName)) {
				log("Specificare la chiave nomeQuery presente nel file ", QUERY_FILE);
				return null;
			}
			if (null != daoConf && Null(daoConf.pkgPropertiesFile())) {
				log("Specificare il pkg contenente il file ", QUERY_FILE);
				// return null;
			}
			DaoHelperBeanSelect bs = null;
			if (!isDisableContainer()) {
				bs = new DaoHelperBeanSelect(getConnection(), getContainer());
			} else {
				bs = new DaoHelperBeanSelect(getConnection());
			}

			bs.setDb2(isDb2());
			bs.setDsMode(isDsMode());
			bs.setLogWhileRunning(isLogWhileRunning());
			if (checkManualQuery(queryName)) {
				bs.setQuery(queryName);
			} else {
				if (Null(queryFromProp.get(queryName))) {
					bs.setQueryName(queryName);
					bs.setQuery(getQueryByName(queryName));
					queryFromProp.put(queryName, bs.getQuery());
				} else {
					bs.setQueryName(queryName);
					bs.setQuery(queryFromProp.get(queryName));
				}
				if (Null(bs.getQuery())) {
					logError("Nessuna query corrispondente alla chiave", queryName, "nel file", QUERIES_PROPERTIES);
				}
			}
			Integer timeout = getTimeout();
			if (timeout != TIMEOUT_DEFAULT) {
				bs.setQueryTimeout(timeout);
			}
			bs.setMock(isInMemory());
			mapBeanSelect.put(queryName, bs);
		}
		if (!isDisableContainer()) {
			mapBeanSelect.get(queryName).setContainer(getContainer());
		}
		if (mapBeanSelect.get(queryName).getConnection().isClosed()) {
			mapBeanSelect.get(queryName).setConnection(getConnection());
		}
		return mapBeanSelect.get(queryName);
	}

	private boolean checkManualQuery(String queryName) {
		boolean manual = false;
		if (notNull(queryName)) {
			queryName = queryName.trim();
			manual = queryName.toUpperCase().startsWith(START_SELECT) && countChar(queryName, ' ') > 1 && isLike(queryName, FROM);
		}
		return manual;
	}

	/**
	 * Ritorna una lista di oggetti di tipo T individuato dal parametro Class<T>
	 * c. Parametri è un oggetto che specifica variabili istanza aventi nome
	 * uguale ai parametri indicati nella query :param. QueryName è il nome
	 * della chiave nel file queries.properties a cui corrisponde la query sql
	 * da eseguire
	 * 
	 * @param <T>
	 * @param queryName
	 * @param parametri
	 * @param c
	 * @return PList<T>
	 * @throws Exception
	 */
	public <T> PList<T> selectDTO(String queryName, Object parametri, Class<T> c) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.selectDTO(c, parametri) : null;
	}

	/**
	 * Torna true se la where condition applicata torna un result set vuoto.
	 * Parametri è l'elenco ordinato dei parametri così come appaiono nella
	 * query queryName definita all'interno di queries.properties
	 * 
	 * @param queryName
	 * @param parametri
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean isRecordAssente(String queryName, Object... parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.isRecordAssente(parametri) : null;
	}

	/**
	 * Torna true se la where condition applicata torna un result set non vuoto.
	 * Parametri è l'elenco ordinato dei parametri così come appaiono nella
	 * query queryName definita all'interno di queries.properties
	 * 
	 * @param queryName
	 * @param parametri
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean isRecordPresente(String queryName, Object... parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.isRecordPresente(parametri) : null;
	}

	/**
	 * Torna true se la where condition applicata torna un result set non vuoto.
	 * Parametri è un oggetto che specifica variabili istanza aventi nome uguale
	 * ai parametri indicati nella query queryName.
	 * 
	 * @param queryName
	 * @param parametri
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean isRecordPresenteDTO(String queryName, Object parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.isRecordPresenteDTO(parametri) : null;
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
	 * @param queryName
	 * @param parametri
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean executeStoredProcedure(String queryName, Object... parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.executeStoredProcedure(parametri) : null;
	}

	/**
	 * Metodo che esegue una stored procedure senza out parameters. I parametri
	 * di input (IN PARAMETERS) vanno passati in un oggetto dto Parametri le cui
	 * variabili istanza devono essere dichiarate nello stesso ordine in cui
	 * compaiono i parametri di ingresso rappresentati dai ? . I parametri
	 * passati come NULL vengono trattati come tali e non esclusi. Esempio di
	 * definizione di query con soli parametri di ingresso: nuova={CALL
	 * DBK_PAAP_SISTCONTRIBUTIVE.aggiorna_apsic_da_entrate(?, ?, ?, ?, ? )}
	 * 
	 * @param queryName
	 * @param parametri
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean executeStoredProcedureDTO(String queryName, Object parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.executeStoredProcedureDTO(parametri) : null;
	}

	/***
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
	 * @param <T>
	 * @param queryName
	 * @param parametri
	 * @param c
	 * @return T
	 * @throws Exception
	 */
	public <T> T executeStoredProcedureOutParameters(String queryName, Class<T> c, Object... parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? (T) bs.executeStoredProcedureOutParameters(c, parametri) : null;
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
	 * @param <T>
	 * @param queryName
	 * @param c
	 * @param parametri
	 * @return T
	 * @throws Exception
	 */
	public <T> T executeStoredProcedureOutParametersDTO(String queryName, Class<T> c, Object parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? (T) bs.executeStoredProcedureOutParametersDTO(c, parametri) : null;
	}

	/**
	 * Torna true se la where condition applicata torna un result set vuoto
	 * Parametri è un oggetto che specifica variabili istanza aventi nome uguale
	 * ai parametri indicati nella query queryName.
	 * 
	 * @param queryName
	 * @param parametri
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean isRecordAssenteDTO(String queryName, Object parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.isRecordAssenteDTO(parametri) : null;
	}

	/**
	 * Ritorna una lista di oggetti di tipo T individuato dal parametro Class<T>
	 * c. Parametri l'elenco ordinato dei parametri indicati nella query
	 * queryName. QueryName è il nome della chiave nel file queries.properties a
	 * cui corrisponde la query sql da eseguire
	 * 
	 * @param <T>
	 * @param queryName
	 * @param c
	 * @param parametri
	 * @return PList<T>
	 * @throws Exception
	 */
	public <T> PList<T> select(String queryName, Class<T> c, Object... parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.select(c, parametri) : null;
	}

	/**
	 * Ritorna un oggetto di tipo T individuato dal parametro Class<T> c.
	 * Parametri l'elenco ordinato dei parametri indicati nella query queryName.
	 * QueryName è il nome della chiave nel file queries.properties a cui
	 * corrisponde la query sql da eseguire
	 * 
	 * @param <T>
	 * @param queryName
	 * @param c
	 * @param parametri
	 * @return T
	 * @throws Exception
	 */
	public <T> T selectOne(String queryName, Class<T> c, Object... parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.selectOne(c, parametri) : null;
	}

	/**
	 * Ritorna un oggetto di tipo T individuato dal parametro Class<T> c.
	 * Parametri è un oggetto che specifica variabili istanza aventi nome uguale
	 * ai parametri indicati nella query queryName. QueryName è il nome della
	 * chiave nel file queries.properties a cui corrisponde la query sql da
	 * eseguire
	 * 
	 * @param <T>
	 * @param queryName
	 * @param parametri
	 * @param c
	 * @return T
	 * @throws Exception
	 */
	public <T> T selectOneDTO(String queryName, Object parametri, Class<T> c) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.selectOneDTO(c, parametri) : null;
	}

	/**
	 * Ritorna una lista di oggetti di tipo T definito dalla Classe c primo
	 * parametro del metodo.Non esegue quindi un mapping ORM all'interno di un
	 * bean di mapping del result set ma torna direttamente una lista di oggetti
	 * della classe specificata (tipicamente String, Date, BigDecimal o altri
	 * tipi java). Parametri è un oggetto che specifica variabili istanza aventi
	 * nome uguale ai parametri indicati nella query queryName. QueryName è il
	 * nome della chiave nel file queries.properties a cui corrisponde la query
	 * sql da eseguire
	 * 
	 * @param <T>
	 * @param queryName
	 * @param c
	 * @param parametri
	 * @return PList<T>
	 * @throws Exception
	 */
	public <T> PList<T> selectNoBean(String queryName, Class<T> c, Object... parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.selectNoBean(c, parametri) : null;
	}

	/**
	 * Ritorna una lista di oggetti di tipo T definito dalla Classe c primo
	 * parametro del metodo. I parametri di filtro della where condition vengono
	 * passati nell'oggetto Parametri. Tale oggetto deve avere le variabili
	 * istanza uguali nel nome ai parametri usati nella where condition.
	 * QueryName è il nome della chiave nel file queries.properties a cui
	 * corrisponde la query sql da eseguire
	 * 
	 * @param <T>
	 * @param queryName
	 * @param c
	 * @param parametri
	 * @return PList<T>
	 * @throws Exception
	 */
	public <T> PList<T> selectNoBeanDTO(String queryName, Class<T> c, Object parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.selectNoBeanDTO(c, parametri) : null;
	}

	/***
	 * Ritorna il primo elemento della lista risultato di oggetti di tipo T
	 * definito dalla Classe c primo parametro del metodo. I parametri di filtro
	 * della where condition vengono passati nell'oggetto parametri. Tale
	 * oggetto deve avere le variabili istanza uguali nel nome ai parametri
	 * usati nella where condition. QueryName è il nome della chiave nel file
	 * queries.properties a cui corrisponde la query sql da eseguire
	 * 
	 * @param <T>
	 * @param queryName
	 * @param c
	 * @param parametri
	 * @return T
	 * @throws Exception
	 */
	public <T> T selectOneNoBeanDTO(String queryName, Class<T> c, Object parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.selectOneNoBeanDTO(c, parametri) : null;
	}

	/***
	 * Ritorna il primo elemento della lista risultato di oggetti di tipo K
	 * definito dalla Classe c primo parametro del metodo. Parametri è l'elenco
	 * ordinato dei parametri nella query individuata da queryName nel file
	 * queries.properties
	 * 
	 * @param <T>
	 * @param queryName
	 * @param c
	 * @param parametri
	 * @return T
	 * @throws Exception
	 */
	public <T> T selectOneNoBean(String queryName, Class<T> c, Object... parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.selectOneNoBean(c, parametri) : null;
	}

	/**
	 * Esegue le select con paginazione. Numero pagina indica quale numero di
	 * pagina si desidera caricare. Quanti per pagina indica quanto grande deve
	 * essere la pagina, ossia quanti record si devono mostrare per pagina.
	 * Parametri è l'elenco ordinato dei parametri così come compaiono nella
	 * query queryName. QueryName è il nome della chiave nel file
	 * queries.properties a cui corrisponde la query sql da eseguire
	 * 
	 * @param <T>
	 * @param queryName
	 * @param numeroPagina
	 * @param quantiPerPagina
	 * @param c
	 * @param parametri
	 * @return PList<T>
	 * @throws Exception
	 */
	public <T> PList<T> selectPaginatedBean(String queryName, Integer numeroPagina, Integer quantiPerPagina, Class<T> c, Object... parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.selectPaginatedBean(c, numeroPagina, quantiPerPagina, parametri) : null;
	}

	/**
	 * Esegue le select con paginazione. Numero pagina indica quale numero di
	 * pagina si desidera caricare. Quanti per pagina indica quanto grande deve
	 * essere la pagina, ossia quanti record si devono mostrare per pagina. I
	 * parametri di filtro della where condition vengono passati nell'oggetto
	 * parametri. Tale oggetto deve avere le variabili istanza uguali nel nome
	 * ai parametri usati nella where condition. QueryName è il nome della
	 * chiave nel file queries.properties a cui corrisponde la query sql da
	 * eseguire
	 * 
	 * @param <T>
	 * @param queryName
	 * @param numeroPagina
	 * @param quantiPerPagina
	 * @param c
	 * @param parametri
	 * @return PList<T>
	 * @throws Exception
	 */
	public <T> PList<T> selectPaginatedBeanDTO(String queryName, Integer numeroPagina, Integer quantiPerPagina, Class<T> c, Object parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.selectPaginatedBeanDTO(c, numeroPagina, quantiPerPagina, parametri) : null;
	}

	/**
	 * Ritorna la cardinalità del result set della query eseguita. Parametri è
	 * un oggetto che specifica variabili istanza aventi nome uguale ai
	 * parametri indicati nella query queryName. QueryName è il nome della
	 * chiave nel file queries.properties a cui corrisponde la query sql da
	 * eseguire
	 * 
	 * @param queryName
	 * @param parametri
	 * @return Long
	 * @throws Exception
	 */
	public Long selectCountDTO(String queryName, Object parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.selectCountDTO(parametri) : null;
	}

	/**
	 * Ritorna la cardinalità del result set della query eseguita. Parametri è
	 * l'elenco ordinato dei parametri indicati nella query queryName. QueryName
	 * è il nome della chiave nel file queries.properties a cui corrisponde la
	 * query sql da eseguire
	 * 
	 * @param queryName
	 * @param parametri
	 * @return Long
	 * @throws Exception
	 */
	public Long selectCount(String queryName, Object... parametri) throws Exception {
		DaoHelperBeanSelect bs = getBeanSelect(queryName);
		return notNull(bs) ? bs.selectCount(parametri) : null;
	}

	private Class<?>[] getClasses() throws ClassNotFoundException, IOException {
		DaoConfig daoConf = getClass().getAnnotation(DaoConfig.class);
		if (Null(daoConf)) {
			log("Specificare l'annotazione @DaoConfig e indicare i valori per gli attibuti pkg,entities,codUtente,codAppl");
			return null;
		}
		if (Null(daoConf.codUtente())) {
			log("Specificare il codice utente per operare con le Entities");
			return null;
		} else {
			setCodUtente(daoConf.codUtente());
		}
		if (Null(daoConf.codAppl())) {
			log("Specificare il codice applicativo per operare con le Entities");
			return null;
		} else {
			setCodAppl(daoConf.codAppl());
		}
		setPkgName(daoConf.pkg());
		setEntities(arrayToList(daoConf.entities()));
		// if (Null(getPkgName())) {
		// log("Specificare il pkgName di riferimento delle entities");
		// return null;
		// }
		// if (Null(getEntities())) {
		// log("Specificare l'elenco delle entities");
		// return null;
		// }
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (String entName : getEntities()) {
			classes.add(Class.forName(str(getPkgName(), DOT, entName)));
		}
		setElencoClassi(classes.toArray(new Class[classes.size()]));
		return getElencoClassi();
	}

	private <T extends BaseEntity> EntityDetail findEntity(String alias) throws Exception {
		if (Null(alias)) {
			log("Specificare un alias per individuare la Entity su cui operare");
			return null;
		}
		EntityDetail ed = null;
		if (Null(mapEnts.get(alias))) {
			Class<T> classe = null;
			Class<T>[] classi = getElencoClassi();
			if (null != classi)
				for (Class<T> c : classi) {
					Annotation aliasAnnotation = c.getAnnotation(Table.class);
					if (notNull(aliasAnnotation)) {
						String aliasTable = ((Table) aliasAnnotation).alias();
						if (notNull(aliasTable))
							if (is(aliasTable, alias)) {
								classe = c;
								break;
							}
					}
				}
			if (Null(classe)) {
				log("Nessuna classe Entity trovata con alias ", alias, " nel pkg di riferimento ", getPkgName());
			} else {
				ed = new EntityDetail();
				ed.setC(classe);
				ed.setMet(classe.getMethod("setQueryTimeout", Integer.class));
				ed.setMetDb(classe.getMethod("setDb", PMap.class));
				ed.setMetMock(classe.getMethod("setMock", boolean.class));
				ed.setMetExtLog(classe.getMethod("setExternalLogger", Logger.class));
				ed.setMetLogWhileRunning(classe.getMethod("setLogWhileRunning", Boolean.class));
				ed.setMetUpdateDeleteLimit(classe.getMethod("setUpdateDeleteLimit", Integer.class));
				ed.setMetSetTableCache(classe.getMethod("setTableCache", Map.class));
				ed.setMetDb2(classe.getMethod("setDb2", boolean.class));
				ed.setMetDsMode(classe.getMethod("setDsMode", boolean.class));
				mapEnts.put(alias, ed);
			}
		}
		return mapEnts.get(alias);
	}

	/**
	 * Stampa a log la table cache che contiene tutti i result set cachati dalla
	 * classe del dao
	 */
	public void printTableCache() {
		log(getTitle("TABLE CACHE", l, SEP));
		Set<String> tabelleInCache = new HashSet<String>();
		for (String k : tableCache.keySet()) {
			tabelleInCache.add(substring(k, null, false, false, PIPE, false, false));
		}
		for (String s : safe(tabelleInCache)) {
			log(tabn(3), s, LF);
		}
		log(tableCache);
	}

	/**
	 * Rimuove dalla table cache i result set delle entity indicate in alias. Se
	 * l'elenco degli alias è null (ossia non passo alcun parametro), allora
	 * cancella totalmente la table cache del dao
	 * 
	 * @param alias
	 */
	public void clearTableCache(String... alias) {
		if (alias == null || alias.length == 0) {
			tableCache = new HashMap<String, PList<? extends BaseEntity>>();
		} else {
			for (String al : alias) {
				_clearTableCache(al);
			}
		}
	}

	private void _clearTableCache(String alias) {
		EntityDetail ed = null;
		try {
			ed = findEntity(alias);
		} catch (Exception e) {
		}
		if (notNull(ed)) {
			String keyLike = str(((Table) ed.getC().getAnnotation(Table.class)).name(), PIPE);
			PList<String> keysToDelete = plstr();
			for (String s : tableCache.keySet()) {
				if (isLike(s, keyLike)) {
					keysToDelete.add(s);
				}
			}
			tableCache.keySet().removeAll(keysToDelete);
		} else {
			logError("Nessuna entity corrispondente all'alias ", alias);
		}
	}

	/**
	 * Metodo che dato un alias per una Entity, ritorna la Entity con
	 * quell'alias, automaticamente istanziata con la connessione, il codice
	 * utente e il codice applicativo da usare nelle query che la riguardano.
	 * 
	 * @param <T>
	 * @param alias
	 * @return T
	 * @throws Exception
	 */
	public <T extends BaseEntity> T giveMe(String alias) throws Exception {
		flushContainer();
		T t = null;
		EntityDetail ed = findEntity(alias);
		if (notNull(ed)) {
			Integer timeout = getTimeout();
			Integer updateDeleteLimit = getUpdateDeleteLimit();
			Class<T> c = ed.getC();
			Constructor<T> ctor = null;
			if (null != getContainer() && !isDisableContainer()) {
				ctor = c.getConstructor(Connection.class, String.class, String.class, PList.class);
				t = (T) ctor.newInstance(new Object[] { getConnection(), getCodUtente(), getCodAppl(), getContainer() });
			} else {
				ctor = c.getConstructor(Connection.class, String.class, String.class);
				t = (T) ctor.newInstance(new Object[] { getConnection(), getCodUtente(), getCodAppl() });
			}
			ed.getMetLogWhileRunning().invoke(t, isLogWhileRunning());

			ed.getMetSetTableCache().invoke(t, tableCache);
			ed.getMetDb2().invoke(t, isDb2());
			ed.getMetDsMode().invoke(t, isDsMode());
			if (timeout != TIMEOUT_DEFAULT) {
				ed.getMet().invoke(t, timeout);
			}
			ed.getMetMock().invoke(t, getInMemory());
			if (isInMemory()) {
				ed.getMetDb().invoke(t, getDb());
			}
			if (notNull(updateDeleteLimit) && updateDeleteLimit > 0) {
				ed.getMetUpdateDeleteLimit().invoke(t, updateDeleteLimit);
			}
			return t;
		}
		log("Entity con alias ", alias, " inesistente nel pkg di riferimento ", getPkgName());
		return null;
	}

	/**
	 * Dato un alias di una entity e una istanza di una classe che estende
	 * BaseEntity, istanzia le variabili necessarie qualora mancanti codApp,
	 * codUtente, e poi container e connection e imposta il log. Tutte le altre
	 * variabili istanza valorizzate vengono preservate. Utile quando si istanza
	 * una entity da fuori il dao e la si riempie con i dati applicativi
	 * necessari che si vogliono passare al dao per eseguire operazioni di
	 * inserimento/aggiornamento. Se ent secondo parametro è null, ritorna
	 * semplicemente uno nuova istanza inizializzata con connessione container
	 * codUtente e codApp su cui andare poi a valorizzare tutte le altre
	 * variabili istanza applicative per poter poi procedere con operazioni di
	 * insert/update
	 * 
	 * 
	 * @param <T>
	 * @param alias
	 * @return T
	 * @throws Exception
	 */
	public <T extends BaseEntity> T giveMe(String alias, T ent) throws Exception {
		if (Null(ent))
			return giveMe(alias);
		flushContainer();
		EntityDetail ed = findEntity(alias);
		if (notNull(ed)) {
			Integer timeout = getTimeout();
			Integer updateDeleteLimit = getUpdateDeleteLimit();
			impostaParametriEntity(ent);
			ed.getMetLogWhileRunning().invoke(ent, isLogWhileRunning());

			ed.getMetSetTableCache().invoke(ent, tableCache);
			ed.getMetDb2().invoke(ent, isDb2());
			ed.getMetDsMode().invoke(ent, isDsMode());
			if (timeout != TIMEOUT_DEFAULT) {
				ed.getMet().invoke(ent, timeout);
			}
			ed.getMetMock().invoke(ent, getInMemory());
			if (isInMemory()) {
				ed.getMetDb().invoke(ent, getDb());
			}
			if (notNull(updateDeleteLimit) && updateDeleteLimit > 0) {
				ed.getMetUpdateDeleteLimit().invoke(ent, updateDeleteLimit);
			}
			return ent;
		}
		log("Entity con alias ", alias, " inesistente nel pkg di riferimento ", getPkgName());
		return null;
	}

	/**
	 * Esegue quanto indicato in giveMe con l'aggiunta che valorizza anche tutte
	 * le variabili istanza dell'Entity identificata da alias. La valorizzazione
	 * avviene in modalità mock generando dati casuali secondo il metodo
	 * copyFrom della classe Pilot a cui si rimanda per la documentazione
	 * 
	 * @param <T>
	 * @param alias
	 * @return T
	 * @throws Exception
	 */
	public <T extends BaseEntity> T giveMeMock(String alias) throws Exception {
		return (T) giveMe(alias).copyFrom((T) mock(findEntity(alias).getC()));
	}

	protected Connection getConn() {
		return conn;
	}

	private void setConn(Connection conn) throws SQLException {
		this.conn = conn;
		setAutocommit();
	}

	/**
	 * Chiude la connessione
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		if (notNull(conn)) {
			conn.close();
		}
	}

	/**
	 * Esegue il commit delle query eseguite
	 * 
	 * @throws Exception
	 */
	public void commit() throws Exception {
		if (notNull(getConn())) {
			getConn().commit();
			if (isLogWhileRunning()) {
				log(COMMIT);
			}
			addQueryCommitRollback(COMMIT);
		}
	}

	private void addQueryCommitRollback(String tipo) throws Exception {
		setNumCommitRollback(getNumTotQuery() - getNumCommitRollback());
		addQuery(tipo, OPEN_GRAFFA, getNumCommitRollback(), QUERIES, CLOSE_GRAFFA);
		setNumCommitRollback(getNumTotQuery());
	}

	private int getNumTotQuery() throws Exception {
		return getContainer().notLike(COMMIT, ROLLBACK, POST_QUERY_IN).find().size();
	}

	/**
	 * Eseguo il rollback delle query eseguite
	 * 
	 * @throws Exception
	 */
	public void rollback() throws Exception {
		if (notNull(getConn())) {
			getConn().rollback();
			if (isLogWhileRunning()) {
				log(ROLLBACK);
			}
			addQueryCommitRollback(ROLLBACK);
		}
	}

	public String getCodUtente() {
		return codUtente;
	}

	public void setCodUtente(String codUtente) {
		this.codUtente = codUtente;
	}

	public String getCodAppl() {
		return codAppl;
	}

	private void setCodAppl(String codAppl) {
		this.codAppl = codAppl;
	}

	/**
	 * Ritorna tutte le query effettuate da questa istanza del dao
	 * 
	 * @return PList<String>
	 */
	public PList<String> getContainer() {
		return container;
	}

	private <T extends BaseEntity> Class<T>[] getElencoClassi() {
		return elencoClassi;
	}

	private void setElencoClassi(Class[] elencoClassi) {
		this.elencoClassi = elencoClassi;
	}

	private PList<String> getEntities() {
		return entities;
	}

	private void setEntities(PList<String> entities) {
		this.entities = entities;
	}

	private String getPkgName() {
		return pkgName;
	}

	private void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	private <K, T> Map<K, T> getAliasMap(String alias, String c, String v) {
		if (Null(mp.get(alias))) {
			Map m = null;
			if (tutte(is(c, LONG), is(v, LONG))) {
				m = new HashMap<Long, Long>();
			} else if (tutte(is(c, LONG), is(v, STRING))) {
				m = new HashMap<Long, String>();
			} else if (tutte(is(c, STRING), is(v, LONG))) {
				m = new HashMap<String, Long>();
			} else if (tutte(is(c, STRING), is(v, STRING))) {
				m = new HashMap<String, String>();
			} else if (tutte(is(c, INTEGER), is(v, INTEGER))) {
				m = new HashMap<Integer, Integer>();
			} else if (tutte(is(c, INTEGER), is(v, STRING))) {
				m = new HashMap<Integer, String>();
			} else if (tutte(is(c, STRING), is(v, INTEGER))) {
				m = new HashMap<String, Integer>();
			}
			mp.put(alias, m);
		}
		return (Map<K, T>) mp.get(alias);
	}

	/**
	 * Legge da una tipologica chiave-valore utilizzando un meccanismo di cache
	 * automatico.Alias è l'alias dell'entity sorgente.Key è il valore chiave
	 * della tipologica.Restituisce il valore della tipologica corrispondente
	 * alla chiave passata come parametro e utilizzando un meccanismo di cache
	 * automatico per evitare accessi multipli ridondanti alla base dati
	 * 
	 * Il meccanismo di cache è limitato alla sola istanza della classe dao che
	 * estende DaoHelper. Se creo una nuova istanza avrò un'altra cache.
	 * 
	 * @param <T>
	 * @param <K>
	 * @param alias
	 * @param key
	 *            T
	 * @return T
	 * @throws Exception
	 */
	public <T, K> T getTipologica(String alias, K key) throws Exception {
		String keyClass = null;
		String valueClass = null;
		T t = null;
		BaseEntity ent = giveMe(alias);
		for (Field field : ent.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(TipologicaKey.class)) {
				keyClass = field.getType().getSimpleName();
			}
			if (field.isAnnotationPresent(TipologicaValue.class)) {
				valueClass = field.getType().getSimpleName();
			}
		}

		if (notNull(keyClass, valueClass)) {
			t = (T) ent.getTipologica(key, getAliasMap(alias, keyClass, valueClass));
		} else {
			log("Mancanza delle colonne identificate dalle annotazioni @TipologicaKey e @TipologicaValue nella entity ", ent.getClass().getSimpleName());
		}
		return t;
	}

	private List<String> getPkAlias(Class c) {
		List<String> alias = new ArrayList<String>();
		Field[] attributi = c.getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (tutte(annCol.pk())) {
					alias.add(annCol.alias());
				}
			}
		}
		return alias;
	}

	private <K extends BaseEntity> boolean goPk(K parent, K child) {
		boolean goPk = false;
		List<String> parentAlias = safe(getPkAlias(parent.getClass()));
		List<String> childAlias = safe(getPkAlias(child.getClass()));
		List<String> commonAlias = safe(intersection(parentAlias, childAlias));
		if (tutte(notNull(commonAlias), parentAlias.size() > childAlias.size()))
			goPk = false;
		if (tutte(notNull(commonAlias), parentAlias.size() <= childAlias.size()))
			goPk = true;
		return goPk;
	}

	/**
	 * Ritorna un oggetto EntityWired dell'entity con alias passato come
	 * parametro
	 * 
	 * @param alias
	 * @return EntityWired
	 */
	protected EntityWired getEw(String alias) {
		return new EntityWired(alias);
	}

	/**
	 * Ritorna un oggetto EntityWired dell'entity con alias passato come
	 * parametro e con stato l'array di stati passato come parametro
	 * 
	 * @param alias
	 * @param stato
	 * @return EntityWired
	 */
	protected EntityWired getEw(String alias, Object... stato) {
		return new EntityWired(alias, stato);
	}

	/**
	 * Esegue una select a cascata seguendo il cablaggio delle chiavi primarie
	 * (uguaglianza degli alias della annotazione @Column sulla colonne chiavi
	 * primarie pk=true). entWired è l'elenco degli oggetti EntityWired passati
	 * dall'ultimo (tipologia di oggetti che saranno tornati dal metodo) al
	 * primo.
	 * 
	 * start è l'input iniziale. Esempio: selezionare tutti i periodi di un
	 * determinato fascicolo, passando attraverso le relazioni di chiave
	 * primaria delle tabelle intermedie.
	 * 
	 * 
	 * 
	 * @param <K>
	 * @param start
	 * @param entW
	 * @return PList<?>
	 * @throws Exception
	 */
	protected <K extends BaseEntity> PList<?> selectCascade(PList<K> start, EntityWired... entW) throws Exception {
		for (int i = 0; i < entW.length - 1; i++) {
			EntityWired parent = entW[i];
			EntityWired child = entW[i + 1];
			BaseEntity parentEntity = giveMe(parent.getEntAlias());
			BaseEntity childEntity = giveMe(child.getEntAlias());
			parent.setGoPk(goPk(parentEntity, childEntity));
		}
		List<EntityWired> listEntWired = inverti(arrayToList(entW));
		for (EntityWired ew : safe(listEntWired)) {
			BaseEntity be = giveMe(ew.getEntAlias());
			Method mSelect = be.getClass().getMethod(ew.isGoPk() ? "selectByPk" : "select", PList.class);
			Method mStato = ew.getClass().getDeclaredMethod("getStato");
			Method mStato1 = ew.getClass().getDeclaredMethod("getStato1");
			Method mBetweenSysdate = ew.getClass().getDeclaredMethod("isBetweenSysdate");
			start = (PList<K>) mSelect.invoke(be, start);
			Object[] stato = (Object[]) mStato.invoke(ew);
			Object[] stato1 = (Object[]) mStato1.invoke(ew);
			Boolean betweenSysdate = (Boolean) mBetweenSysdate.invoke(ew);
			if (notNull(stato)) {
				start = start.stato(stato);
			}
			if (notNull(stato1)) {
				start = start.stato1(stato1);
			}
			if (betweenSysdate) {
				start = start.betweenNow();
			}
		}
		return start;
	}

	/**
	 * Esegue la logica del metodo selectCascade passando però come input un
	 * solo elemento e non una lista di elementi BaseEntity
	 * 
	 * @param <K>
	 * @param start
	 * @param entW
	 * @return PList<?>
	 * @throws Exception
	 */
	protected <K extends BaseEntity> PList<?> selectCascade(K start, EntityWired... entW) throws Exception {
		PList<K> inizio = pl();
		inizio.add(start);
		return selectCascade(inizio, entW);
	}

	private class DaoOutput {
		private Set<String> spCalled = new HashSet<String>();
		private PList<String> errorQuery = plstr();
		private PList<EntDetail> entDetailList = pl();
		private Integer errorSelect = 0;
		private Integer errorUpdate = 0;
		private Integer errorInsert = 0;
		private Integer errorDelete = 0;
		private Integer errorSP = 0;
		private Total total = new Total();

		private PList<String> getReportContent() throws Exception {
			PList<String> fileContent = plstr();
			if (isInMemory()) {
				fileContent.add(getTitle(TITLE_IN_MEMORY, l, SEP));
			}

			fileContent.add(getTitle(SP_CALLED, l, SEP));
			fileContent.addAll(getSpCalled());
			fileContent.add(LF);
			fileContent.add(getTitle(str(QUERY_ESEGUITE_IN_ERRORE, getErrorQueryDetails()), l, SEP));
			fileContent.aggiungiList(getErrorQuery());
			fileContent.add(LF);
			fileContent.add(getTitle(QUERY_BY_TABLE, l, SEP));
			fileContent.aggiungiList(getGroupBy());
			fileContent.add(LF);
			fileContent.add(getTitle(TOTAL_RECORD, l, SEP));
			fileContent.aggiungiList(getTotalReport());
			return fileContent;
		}

		private PList<String> getTotalReport() {
			totalRecords = plstr();
			Integer numSelect = getTotal().getNumSelect();
			Integer numUpdate = getTotal().getNumUpdate();
			Integer numInsert = getTotal().getNumInsert();
			Integer numDelete = getTotal().getNumDelete();
			Integer numSP = getTotal().getNumSP();

			Integer errorSelect = getTotal().getErrorSelect();
			Integer errorUpdate = getTotal().getErrorUpdate();
			Integer errorInsert = getTotal().getErrorInsert();
			Integer errorDelete = getTotal().getErrorDelete();
			Integer errorSP = getTotal().getErrorSP();

			Time timeSelect = getTotal().getTimeSelect();
			Time timeUpdate = getTotal().getTimeUpdate();
			Time timeInsert = getTotal().getTimeInsert();
			Time timeDelete = getTotal().getTimeDelete();
			Time timeSP = getTotal().getTimeSP();

			Integer numCommit = getTotal().getNumCommit();
			Integer numRollback = getTotal().getNumRollback();
			Integer totaleQuery = getTotal().getNumQueryTotale();
			Integer totalErrors = getTotal().getNumErroriTotale();

			Time totalQueryTime = getTotal().getTotalQueryTime();

			totalRecords.add(str(START_SELECT.trim(), RIGHT_ARROW, OPEN_GRAFFA, numSelect, NUM_QUERIES, errorSelect, NUM_ERRORS, totalRecSelect, NUM_RECORD, timeSelect.getTotalTime(), CLOSE_GRAFFA));
			totalRecords.add(str(START_UPDATE.trim(), RIGHT_ARROW, OPEN_GRAFFA, numUpdate, NUM_QUERIES, errorUpdate, NUM_ERRORS, totalRecUpdate, NUM_RECORD, timeUpdate.getTotalTime(), CLOSE_GRAFFA));
			totalRecords.add(str(START_INSERT.trim(), RIGHT_ARROW, OPEN_GRAFFA, numInsert, NUM_QUERIES, errorInsert, NUM_ERRORS, totalRecInsert, NUM_RECORD, timeInsert.getTotalTime(), CLOSE_GRAFFA));
			totalRecords.add(str(START_DELETE.trim(), RIGHT_ARROW, OPEN_GRAFFA, numDelete, NUM_QUERIES, errorDelete, NUM_ERRORS, totalRecDelete, NUM_RECORD, timeDelete.getTotalTime(), CLOSE_GRAFFA));
			totalRecords.add(str(STORED_PROCEDURES, RIGHT_ARROW, OPEN_GRAFFA, numSP, NUM_QUERIES, errorSP, NUM_ERRORS, timeSP.getTotalTime(), CLOSE_GRAFFA));
			totalRecords.add(str(START_COMMIT, RIGHT_ARROW, OPEN_GRAFFA, numCommit, CLOSE_GRAFFA));
			totalRecords.add(str(START_ROLLBACK, RIGHT_ARROW, OPEN_GRAFFA, numRollback, CLOSE_GRAFFA));
			Long total = sommaLong(totalRecDelete, totalRecInsert, totalRecSelect, totalRecUpdate);
			totalRecords.add(str(TOTALE_GENERALE, RIGHT_ARROW, OPEN_GRAFFA, totaleQuery, NUM_QUERIES, totalErrors, NUM_ERRORS, total, NUM_RECORD, totalQueryTime.getTotalTime(), CLOSE_GRAFFA));
			return totalRecords;
		}

		private EntDetail sumAllDetail(String ent, PList<EntDetail> lista) {
			EntDetail somma = new EntDetail();
			somma.setNome(ent);
			for (EntDetail ed : safe(lista)) {
				somma.getSelectDetail().addQuery(ed.getSelectDetail().getNumQuery());
				somma.getSelectDetail().addRecord(ed.getSelectDetail().getNumRecord());
				somma.getSelectDetail().addError(ed.getSelectDetail().getNumErrors());
				somma.getSelectDetail().addTime(ed.getSelectDetail().getTime());

				somma.getUpdateDetail().addQuery(ed.getUpdateDetail().getNumQuery());
				somma.getUpdateDetail().addRecord(ed.getUpdateDetail().getNumRecord());
				somma.getUpdateDetail().addError(ed.getUpdateDetail().getNumErrors());
				somma.getUpdateDetail().addTime(ed.getUpdateDetail().getTime());

				somma.getInsertDetail().addQuery(ed.getInsertDetail().getNumQuery());
				somma.getInsertDetail().addRecord(ed.getInsertDetail().getNumRecord());
				somma.getInsertDetail().addError(ed.getInsertDetail().getNumErrors());
				somma.getInsertDetail().addTime(ed.getInsertDetail().getTime());

				somma.getDeleteDetail().addQuery(ed.getDeleteDetail().getNumQuery());
				somma.getDeleteDetail().addRecord(ed.getDeleteDetail().getNumRecord());
				somma.getDeleteDetail().addError(ed.getDeleteDetail().getNumErrors());
				somma.getDeleteDetail().addTime(ed.getDeleteDetail().getTime());

			}
			return somma;
		}

		private PList<String> getGroupBy() throws Exception {
			PList<EntDetail> listaDettaglio = pl();
			PMap<String, PList<EntDetail>> mappa = getEntDetailList().groupBy("nome");
			for (Map.Entry<String, PList<EntDetail>> entry : mappa.entrySet()) {
				listaDettaglio.add(sumAllDetail(entry.getKey(), entry.getValue()));
			}
			PList<String> el = plstr();
			for (EntDetail ent : safe(listaDettaglio)) {
				el.add(str(ent.getNome(), RIGHT_ARROW, OPEN_QUADRA, LF, TAB, TAB, START_SELECT, ent.getSelectDetail().getInfo(), LF, TAB, TAB, START_UPDATE, ent.getUpdateDetail().getInfo(), LF, TAB,
						TAB, START_INSERT, ent.getInsertDetail().getInfo(), LF, TAB, TAB, START_DELETE, ent.getDeleteDetail().getInfo(), LF, CLOSE_QUADRA, SPACE));
			}
			return el;
		}

		private String getErrorQueryDetails() throws Exception {
			return str(THREE_SPACE, OPEN_QUADRA, getErrorSelect(), SPACE, START_SELECT, DOUBLE_SPACE, getErrorUpdate(), SPACE, START_UPDATE, DOUBLE_SPACE, getErrorInsert(), SPACE, START_INSERT,
					DOUBLE_SPACE, getErrorDelete(), SPACE, START_DELETE, DOUBLE_SPACE, getErrorSP(), SPACE, SP_CALLED, CLOSE_QUADRA);
		}

		private void addTotal(Total t) {
			getTotal().addNumSelect(t.getNumSelect());
			getTotal().addNumUpdate(t.getNumUpdate());
			getTotal().addNumInsert(t.getNumInsert());
			getTotal().addNumDelete(t.getNumDelete());
			getTotal().addNumSP(t.getNumSP());

			getTotal().addErrorSelect(t.getErrorSelect());
			getTotal().addErrorUpdate(t.getErrorUpdate());
			getTotal().addErrorInsert(t.getErrorInsert());
			getTotal().addErrorDelete(t.getErrorDelete());
			getTotal().addErrorSP(t.getErrorSP());

			getTotal().addNumRecordSelect(t.getNumRecordSelect());
			getTotal().addNumRecordUpdate(t.getNumRecordUpdate());
			getTotal().addNumRecordInsert(t.getNumRecordInsert());
			getTotal().addNumRecordDelete(t.getNumRecordDelete());

			getTotal().addTimeSelect(t.getTimeSelect());
			getTotal().addTimeUpdate(t.getTimeUpdate());
			getTotal().addTimeInsert(t.getTimeInsert());
			getTotal().addTimeDelete(t.getTimeDelete());
			getTotal().addTimeSP(t.getTimeSP());

			getTotal().addCommit(t.getNumCommit());
			getTotal().addRollback(t.getNumRollback());
		}

		private Total getTotal() {
			return total;
		}

		private void addEntityDetail(PList<EntDetail> lista) {
			getEntDetailList().addAll(lista);
		}

		private void addErrorSelect(Integer errors) {
			setErrorSelect(getErrorSelect() + errors);
		}

		private void addErrorUpdate(Integer errors) {
			setErrorUpdate(getErrorUpdate() + errors);
		}

		private void addErrorInsert(Integer errors) {
			setErrorInsert(getErrorInsert() + errors);
		}

		private void addErrorDelete(Integer errors) {
			setErrorDelete(getErrorDelete() + errors);
		}

		private void addErrorSP(Integer errors) {
			setErrorSP(getErrorSP() + errors);
		}

		private Integer getErrorSelect() {
			return errorSelect;
		}

		private void setErrorSelect(Integer errorSelect) {
			this.errorSelect = errorSelect;
		}

		private Integer getErrorUpdate() {
			return errorUpdate;
		}

		private void setErrorUpdate(Integer errorUpdate) {
			this.errorUpdate = errorUpdate;
		}

		private Integer getErrorInsert() {
			return errorInsert;
		}

		private void setErrorInsert(Integer errorInsert) {
			this.errorInsert = errorInsert;
		}

		private Integer getErrorDelete() {
			return errorDelete;
		}

		private void setErrorDelete(Integer errorDelete) {
			this.errorDelete = errorDelete;
		}

		private Integer getErrorSP() {
			return errorSP;
		}

		private void setErrorSP(Integer errorSP) {
			this.errorSP = errorSP;
		}

		private void addSP(Set<String> sp) {
			getSpCalled().addAll(sp);
		}

		private void addError(PList<String> error) {
			getErrorQuery().addAll(error);
		}

		private Set<String> getSpCalled() {
			return spCalled;
		}

		private PList<String> getErrorQuery() {
			return errorQuery;
		}

		private PList<EntDetail> getEntDetailList() {
			return entDetailList;
		}

	}

	private class EntDetail {
		private String nome;
		private QueryDetail selectDetail = new QueryDetail();
		private QueryDetail updateDetail = new QueryDetail();
		private QueryDetail deleteDetail = new QueryDetail();
		private QueryDetail insertDetail = new QueryDetail();

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		private QueryDetail getSelectDetail() {
			return selectDetail;
		}

		private void setSelectDetail(QueryDetail selectDetail) {
			this.selectDetail = selectDetail;
		}

		private QueryDetail getUpdateDetail() {
			return updateDetail;
		}

		private void setUpdateDetail(QueryDetail updateDetail) {
			this.updateDetail = updateDetail;
		}

		private QueryDetail getDeleteDetail() {
			return deleteDetail;
		}

		private void setDeleteDetail(QueryDetail deleteDetail) {
			this.deleteDetail = deleteDetail;
		}

		private QueryDetail getInsertDetail() {
			return insertDetail;
		}

		private void setInsertDetail(QueryDetail insertDetail) {
			this.insertDetail = insertDetail;
		}

	}

	/**
	 * Stampa tutte le query eseguite tramite l'istanza della classe DaoHelper
	 * 
	 * @throws Exception
	 */
	public void printQueries() throws Exception {
		log(LF, LF);
		if (isInMemory()) {
			log(getTitle(TITLE_IN_MEMORY, l, SEP));
		}
		printList(SP_CALLED, l, setToList(getDaoOutput().getSpCalled()));
		printList(str(QUERY_ESEGUITE_IN_ERRORE, getDaoOutput().getErrorQueryDetails()), l, getDaoOutput().getErrorQuery());
		printList(QUERY_BY_TABLE, l, getDaoOutput().getGroupBy());
		printTotal();
	}

	private void printTotal() throws Exception {
		log(LF, LF);
		log(TITOLO_TOTALE, LF);
		for (String s : safe(getDaoOutput().getTotalReport())) {
			log(TAB, s, LF);
		}
	}

	/**
	 * Metodo alias di printQueries
	 * 
	 * @throws Exception
	 */
	public void printReport() throws Exception {
		printQueries();
	}

	private boolean isSP(String s) {
		s = emptyIfNull(s);
		String inizio = substring(s, null, false, true, CALL, true, false);
		if (isLike(inizio, Q_MARK)) {
			s = substring(s, Q_MARK, false, true, CALL, true, false);
		}
		s = substring(s, OPEN_GRAFFA, false, true, CALL, true, false);
		s = substring(s, EQ, false, true, CALL, true, false);
		return is(s.trim(), CALL);
	}

	private Integer countSP() throws Exception {
		int i = 0;
		for (String sql : safe(getContainer())) {
			if (isSP(sql))
				i++;
		}
		return i;
	}

	private boolean isErrorQuery(String sql) {
		return isLike(sql, ERROR_MARK);
	}

	private Integer countErrorSP() throws Exception {
		int i = 0;
		for (String sql : safe(getContainer())) {
			if (tutte(isSP(sql), isErrorQuery(sql))) {
				i++;
			}
		}
		return i;
	}

	private Time getTotalSPTime() {
		PList<String> sp = plstr();
		for (String sql : safe(getContainer())) {
			if (isSP(sql)) {
				sp.add(sql);
			}
		}
		return computeQueryTime(sp);
	}

	/**
	 * Ritorna tutte le distinte invocazioni di SP effettuate da questa istanza
	 * del dao
	 * 
	 * @return Set<String>
	 * @throws Exception
	 */
	private Set<String> getSPCalled() throws Exception {
		Set<String> sp = new HashSet<String>();
		for (String sql : safe(getContainer())) {
			if (isSP(sql))
				sp.add(substring(sql, OPEN_GRAFFA, true, false, CLOSE_GRAFFA, true, true));
		}
		return sp;
	}

	private String countQueryType(String qType) throws Exception {
		return String.valueOf(getContainer().like(qType).find().size());
	}

	private String countErrorQueryType(String qType) throws Exception {
		if (Null(errorsQueryType.get(qType))) {
			errorsQueryType.put(qType, getContainer().like(qType).like(ERROR_MARK).find().size());
		}
		return String.valueOf(errorsQueryType.get(qType));
	}

	private Integer countErrorQueryType(String ent, String qType) throws Exception {
		return getContainer().like(ent).like(qType).like(ERROR_MARK).find().size();
	}

	private Time getSelectTotalTime() throws Exception {
		return computeQueryTime(getContainer().like(SELECT).find());
	}

	private Time getUpdateTotalTime() throws Exception {
		return computeQueryTime(getContainer().like(UPDATE).find());
	}

	private Time getInsertTotalTime() throws Exception {
		return computeQueryTime(getContainer().like(INSERT).find());
	}

	private Time getDeleteTotalTime() throws Exception {
		return computeQueryTime(getContainer().like(DELETE).find());
	}

	class Total {

		private Integer numSelect = 0;
		private Integer numUpdate = 0;
		private Integer numInsert = 0;
		private Integer numDelete = 0;
		private Integer numSP = 0;
		private Integer numCommit = 0;
		private Integer numRollback = 0;

		private Integer errorSelect = 0;
		private Integer errorUpdate = 0;
		private Integer errorInsert = 0;
		private Integer errorDelete = 0;
		private Integer errorSP = 0;

		private Integer numRecordSelect = 0;
		private Integer numRecordUpdate = 0;
		private Integer numRecordInsert = 0;
		private Integer numRecordDelete = 0;

		private Time timeSelect = new Time();
		private Time timeUpdate = new Time();
		private Time timeInsert = new Time();
		private Time timeDelete = new Time();
		private Time timeSP = new Time();

		private Integer getNumQueryTotale() {
			return sommaInt(getNumSelect(), getNumUpdate(), getNumInsert(), getNumDelete(), getNumSP());
		}

		private Integer getNumErroriTotale() {
			return sommaInt(getErrorSelect(), getErrorUpdate(), getErrorInsert(), getErrorDelete(), getErrorSP());
		}

		private Time getTotalQueryTime() {
			Time totalTime = new Time();
			totalTime.addTime(timeSelect);
			totalTime.addTime(timeUpdate);
			totalTime.addTime(timeInsert);
			totalTime.addTime(timeDelete);
			totalTime.addTime(timeSP);
			return totalTime;
		}

		private void addCommit(Integer n) {
			setNumCommit(getNumCommit() + n);
		}

		private void addRollback(Integer n) {
			setNumRollback(getNumRollback() + n);
		}

		private void addTimeSelect(Time t) {
			getTimeSelect().addTime(t);
		}

		private void addTimeUpdate(Time t) {
			getTimeUpdate().addTime(t);
		}

		private void addTimeInsert(Time t) {
			getTimeInsert().addTime(t);
		}

		private void addTimeDelete(Time t) {
			getTimeDelete().addTime(t);
		}

		private void addTimeSP(Time t) {
			getTimeSP().addTime(t);
		}

		private void addNumRecordSelect(Integer n) {
			setNumRecordSelect(getNumRecordSelect() + n);
		}

		private void addNumRecordInsert(Integer n) {
			setNumRecordInsert(getNumRecordInsert() + n);
		}

		private void addNumRecordUpdate(Integer n) {
			setNumRecordUpdate(getNumRecordUpdate() + n);
		}

		private void addNumRecordDelete(Integer n) {
			setNumRecordDelete(getNumRecordDelete() + n);
		}

		private void addErrorSelect(Integer n) {
			setErrorSelect(getErrorSelect() + n);
		}

		private void addErrorUpdate(Integer n) {
			setErrorUpdate(getErrorUpdate() + n);
		}

		private void addErrorInsert(Integer n) {
			setErrorInsert(getErrorInsert() + n);
		}

		private void addErrorDelete(Integer n) {
			setErrorDelete(getErrorDelete() + n);
		}

		private void addErrorSP(Integer n) {
			setErrorSP(getErrorSP() + n);
		}

		private void addNumSelect(Integer n) {
			setNumSelect(getNumSelect() + n);
		}

		private void addNumUpdate(Integer n) {
			setNumUpdate(getNumUpdate() + n);
		}

		private void addNumInsert(Integer n) {
			setNumInsert(getNumInsert() + n);
		}

		private void addNumDelete(Integer n) {
			setNumDelete(getNumDelete() + n);
		}

		private void addNumSP(Integer n) {
			setNumSP(getNumSP() + n);
		}

		private Integer getNumSelect() {
			return numSelect;
		}

		private void setNumSelect(Integer numSelect) {
			this.numSelect = numSelect;
		}

		private Integer getNumUpdate() {
			return numUpdate;
		}

		private void setNumUpdate(Integer numUpdate) {
			this.numUpdate = numUpdate;
		}

		private Integer getNumInsert() {
			return numInsert;
		}

		private void setNumInsert(Integer numInsert) {
			this.numInsert = numInsert;
		}

		private Integer getNumDelete() {
			return numDelete;
		}

		private void setNumDelete(Integer numDelete) {
			this.numDelete = numDelete;
		}

		private Integer getNumSP() {
			return numSP;
		}

		private void setNumSP(Integer numSP) {
			this.numSP = numSP;
		}

		private Integer getNumCommit() {
			return numCommit;
		}

		private void setNumCommit(Integer numCommit) {
			this.numCommit = numCommit;
		}

		private Integer getNumRollback() {
			return numRollback;
		}

		private void setNumRollback(Integer numRollback) {
			this.numRollback = numRollback;
		}

		private Integer getErrorSelect() {
			return errorSelect;
		}

		private void setErrorSelect(Integer errorSelect) {
			this.errorSelect = errorSelect;
		}

		private Integer getErrorUpdate() {
			return errorUpdate;
		}

		private void setErrorUpdate(Integer errorUpdate) {
			this.errorUpdate = errorUpdate;
		}

		private Integer getErrorInsert() {
			return errorInsert;
		}

		private void setErrorInsert(Integer errorInsert) {
			this.errorInsert = errorInsert;
		}

		private Integer getErrorDelete() {
			return errorDelete;
		}

		private void setErrorDelete(Integer errorDelete) {
			this.errorDelete = errorDelete;
		}

		private Integer getErrorSP() {
			return errorSP;
		}

		private void setErrorSP(Integer errorSP) {
			this.errorSP = errorSP;
		}

		private Integer getNumRecordSelect() {
			return numRecordSelect;
		}

		private void setNumRecordSelect(Integer numRecordSelect) {
			this.numRecordSelect = numRecordSelect;
		}

		private Integer getNumRecordUpdate() {
			return numRecordUpdate;
		}

		private void setNumRecordUpdate(Integer numRecordUpdate) {
			this.numRecordUpdate = numRecordUpdate;
		}

		private Integer getNumRecordInsert() {
			return numRecordInsert;
		}

		private void setNumRecordInsert(Integer numRecordInsert) {
			this.numRecordInsert = numRecordInsert;
		}

		private Integer getNumRecordDelete() {
			return numRecordDelete;
		}

		private void setNumRecordDelete(Integer numRecordDelete) {
			this.numRecordDelete = numRecordDelete;
		}

		private Time getTimeSelect() {
			return timeSelect;
		}

		private Time getTimeUpdate() {
			return timeUpdate;
		}

		private Time getTimeInsert() {
			return timeInsert;
		}

		private Time getTimeDelete() {
			return timeDelete;
		}

		private Time getTimeSP() {
			return timeSP;
		}

	}

	private Total getTotal() throws Exception {
		String numSelect = countQueryType(SELECT);
		String numUpdate = countQueryType(UPDATE);
		String numInsert = countQueryType(INSERT);
		String numDelete = countQueryType(DELETE);
		String numSP = countSP().toString();

		String errorSelect = countErrorQueryType(SELECT);
		String errorUpdate = countErrorQueryType(UPDATE);
		String errorInsert = countErrorQueryType(INSERT);
		String errorDelete = countErrorQueryType(DELETE);
		Integer errorSP = countErrorSP();

		Time timeSelect = getSelectTotalTime();
		Time timeUpdate = getUpdateTotalTime();
		Time timeInsert = getInsertTotalTime();
		Time timeDelete = getDeleteTotalTime();
		Time timeSP = getTotalSPTime();

		String numCommit = countQueryType(COMMIT);
		String numRollback = countQueryType(ROLLBACK);

		Total t = new Total();
		t.addNumSelect(getInteger(numSelect));
		t.addNumUpdate(getInteger(numUpdate));
		t.addNumInsert(getInteger(numInsert));
		t.addNumDelete(getInteger(numDelete));
		t.addNumSP(getInteger(numSP));

		t.addErrorSelect(getInteger(errorSelect));
		t.addErrorUpdate(getInteger(errorUpdate));
		t.addErrorInsert(getInteger(errorInsert));
		t.addErrorDelete(getInteger(errorDelete));
		t.addErrorSP(errorSP);

		t.addNumRecordSelect(totalRecSelect.intValue());
		t.addNumRecordUpdate(totalRecUpdate.intValue());
		t.addNumRecordDelete(totalRecDelete.intValue());
		t.addNumRecordInsert(totalRecInsert.intValue());

		t.addTimeSelect(timeSelect);
		t.addTimeUpdate(timeUpdate);
		t.addTimeInsert(timeInsert);
		t.addTimeDelete(timeDelete);
		t.addTimeSP(timeSP);

		t.addCommit(getInteger(numCommit));
		t.addRollback(getInteger(numRollback));
		return t;
	}

	private PList<EntDetail> getEntDetail() throws Exception {
		PList<EntDetail> elenco = pl();
		Set<String> entities = getAllEntitiesInvolved();
		for (String ent : safe(entities)) {
			// QueryDetail qd = countEntitySelect(ent);
			QueryDetail qd = countEntityType(ent, SELECT);
			QueryDetail qdUpdate = countEntityType(ent, UPDATE);
			QueryDetail qdDelete = countEntityType(ent, DELETE);
			QueryDetail qdInsert = countEntityType(ent, INSERT);
			totalRecSelect += qd.getNumRecord();
			totalRecUpdate += qdUpdate.getNumRecord();
			totalRecDelete += qdDelete.getNumRecord();
			totalRecInsert += qdInsert.getNumRecord();
			EntDetail ed = new EntDetail();
			ed.setNome(ent);
			ed.setSelectDetail(qd);
			ed.setUpdateDetail(qdUpdate);
			ed.setDeleteDetail(qdDelete);
			ed.setInsertDetail(qdInsert);
			elenco.add(ed);
		}
		return elenco;
	}

	private class EntityDetail<T extends BaseEntity> {

		private Class<T> c;
		private Method met;
		private Method metDb;
		private Method metMock;
		private Method metExtLog;
		private Method metLogWhileRunning;
		private Method metUpdateDeleteLimit;
		private Method metSetTableCache;
		private Method metDb2;
		private Method metDsMode;

		private Class<T> getC() {
			return c;
		}

		private void setC(Class<T> c) {
			this.c = c;
		}

		public Method getMetDb2() {
			return metDb2;
		}

		public void setMetDb2(Method metDb2) {
			this.metDb2 = metDb2;
		}

		private Method getMet() {
			return met;
		}

		private void setMet(Method met) {
			this.met = met;
		}

		private Method getMetExtLog() {
			return metExtLog;
		}

		private void setMetExtLog(Method metExtLog) {
			this.metExtLog = metExtLog;
		}

		private Method getMetLogWhileRunning() {
			return metLogWhileRunning;
		}

		private void setMetLogWhileRunning(Method metLogWhileRunning) {
			this.metLogWhileRunning = metLogWhileRunning;
		}

		private Method getMetUpdateDeleteLimit() {
			return metUpdateDeleteLimit;
		}

		private void setMetUpdateDeleteLimit(Method metUpdateDeleteLimit) {
			this.metUpdateDeleteLimit = metUpdateDeleteLimit;
		}

		private Method getMetMock() {
			return metMock;
		}

		private void setMetMock(Method metMock) {
			this.metMock = metMock;
		}

		private Method getMetDb() {
			return metDb;
		}

		private void setMetDb(Method metDb) {
			this.metDb = metDb;
		}

		private Method getMetSetTableCache() {
			return metSetTableCache;
		}

		private void setMetSetTableCache(Method metSetTableCache) {
			this.metSetTableCache = metSetTableCache;
		}

		public Method getMetDsMode() {
			return metDsMode;
		}

		public void setMetDsMode(Method metDsMode) {
			this.metDsMode = metDsMode;
		}

	}

	private class QueryDetail {

		private Integer numRecord = 0;
		private Integer numQuery = 0;
		private Integer numErrors = 0;
		private Time time = new Time();

		private void addRecord(Integer n) {
			setNumRecord(getNumRecord() + n);
		}

		private void addQuery(Integer n) {
			setNumQuery(getNumQuery() + n);
		}

		private void addError(Integer n) {
			setNumErrors(getNumErrors() + n);
		}

		private void addTime(Time t) {
			getTime().addTime(t);
		}

		private Integer getNumRecord() {
			return numRecord;
		}

		private void setNumRecord(Integer numRecord) {
			this.numRecord = numRecord;
		}

		private Integer getNumQuery() {
			return numQuery;
		}

		private void setNumQuery(Integer numQuery) {
			this.numQuery = numQuery;
		}

		private Time getTime() {
			return time;
		}

		private void setTime(Time time) {
			this.time = time;
		}

		private String getTimeString() {
			return str(getTime().getTotalTime());

		}

		private Integer getNumErrors() {
			return numErrors;
		}

		private void setNumErrors(Integer numErrors) {
			this.numErrors = numErrors;
		}

		private String getInfo() {
			return str(OPEN_GRAFFA, getNumQuery(), NUM_QUERIES, getNumErrors(), NUM_ERRORS, getNumRecord(), NUM_RECORD, getTimeString(), CLOSE_GRAFFA);
		}
	}

	private QueryDetail countEntityType(String ent, String qType) throws Exception {
		QueryDetail qd = new QueryDetail();
		PList<String> sql = getContainer().like(ent).like(qType).find();
		qd.setTime(computeQueryTime(sql));
		Integer numRecord = 0;
		for (String s : safe(sql)) {
			numRecord += getNumRecord(s);
		}
		qd.setNumQuery(sql.size());
		qd.setNumRecord(numRecord);
		qd.setNumErrors(countErrorQueryType(ent, qType));
		return qd;
	}

	private Integer getNumRecord(String s) {
		s = substring(s.trim(), null, false, false, str(RECORD, CLOSE_QUADRA), false, false);
		return getInteger(substring(s, OPEN_QUADRA, false, true, null, false, false).trim());
	}

	private PList<String> getTabelle(String sql) {
		PList<String> nuoveTabelle = plstr();
		String sql2 = despaceString(sql);
		sql2 = sql2.toUpperCase();
		while (isLike(sql2, FROM)) {
			if (isLike(sql2, WHERE))
				sql2 = substring(sql2, FROM, false, false, WHERE, false, false);
			else
				sql2 = substring(sql2, FROM, false, false, OPEN_QUADRA, false, true);
			if (isLike(sql2, START_SELECT))
				continue;
			PList<String> tabelle = toListString(sql2, COMMA);
			for (String tab : safe(tabelle)) {
				tab = substring(tab, DOT, false, false, SPACE, false, true);
				tab = substring(tab, DOT, false, false, SPACE, false, true);
				nuoveTabelle.add(tab.trim());
			}
		}
		String[] arr = sql.split(JOIN);
		for (int i = 1; i < arr.length; i++) {
			String sql3 = despaceString(arr[i]);
			sql3 = sql3.toUpperCase();
			if (isLike(sql3, ON))
				sql3 = substring(sql3, null, false, false, ON, false, false);
			PList<String> tabelle = toListString(sql3, COMMA);
			for (String tab : safe(tabelle)) {
				tab = substring(tab, DOT, false, false, SPACE, false, true);
				tab = substring(tab, DOT, false, false, SPACE, false, true);
				nuoveTabelle.add(tab.trim());
			}
		}
		return nuoveTabelle;
	}

	private Set<String> getAllEntitiesInvolved() {
		Set<String> entities = new HashSet<String>();
		for (String sql : safe(getContainer())) {
			if (isLike(sql, SELECT)) {
				// String sql_[] = sql.toUpperCase().split(UNION);
				// for (String s : sql_) {
				List<String> tabelle = getTabelle(sql);
				for (String tab : tabelle) {
					entities.add(tab);
				}
				// }
			}
			if (isLike(sql, UPDATE)) {
				sql = substring(sql, START_UPDATE, false, false, SET, false, false);
				if (notNull(sql)) {
					entities.add(sql.trim());
				}
			}

			if (isLike(sql, INSERT)) {
				sql = substring(sql, str(START_INSERT, "INTO "), false, false, " (", false, false);
				if (notNull(sql)) {
					entities.add(sql.trim());
				}
			}

			if (isLike(sql, DELETE)) {
				sql = substring(sql, str(START_DELETE, "FROM "), false, false, WHERE, false, false);
				if (notNull(sql)) {
					entities.add(sql.trim());
				}
			}
		}
		return entities;
	}

	/**
	 * Metodo alias di getContainer
	 * 
	 * @return PList<String>
	 */
	public PList<String> getAllQueries() {
		return getContainer();
	}

	private Time computeQueryTime(PList<String> queries) {
		Time time = new Time();
		for (String s : safe(queries)) {
			String time_ = substring(s, OPEN_QUADRA, false, true, CLOSE_QUADRA, false, true);
			PList<String> elems = toListString(time_, COMMA);
			for (String e : safe(elems)) {
				PList<String> items = toListString(e, SPACE);
				Long val = getLong(getFirstElement(items));
				String tipo = getLastElement(items);
				if (is(tipo, DAYS)) {
					time.addDays(val);
				}
				if (is(tipo, HOURS)) {
					time.addHours(val);
				}
				if (is(tipo, MINUTES)) {
					time.addMinutes(val);
				}
				if (is(tipo, SECONDS)) {
					time.addSeconds(val);
				}
				if (is(tipo, MILLISECONDS)) {
					time.addMilliseconds(val);
				}
			}

		}
		return time;
	}

	/**
	 * Esegue la stampa di log di tipo error (log.error) degli oggetti passati
	 * come parametri. Se l'ultimo parametro è istanza di Throwable allora
	 * chiama il metodo di log.error che prevede come ultimo argomento
	 * l'eccezione e ossia log.error(messaggio,e)
	 * 
	 * @param k
	 */
	public void logError(Object... k) {
		if (k != null) {
			if (k[k.length - 1] instanceof Throwable) {
				logger.error(strSpaced(modificaLunghezzaArray(k, k.length - 1)), (Throwable) k[k.length - 1]);
			} else {
				logger.error(strSpaced(k));
			}
		}
	}

	/**
	 * Esegue la stampa di log di tipo info (log.info)
	 * 
	 * @param k
	 */
	public void log(Object... k) {
		logger.info(strSpaced(k));
	}

	private void saveReport(String path) throws Exception {
		if (notNull(path)) {
			String name = str(getClass().getSimpleName(), SEP, getUniqueNamehhmmss(), TXT);
			writeFile(str(path, SLASH, name), getDaoOutput().getReportContent());
		} else {
			logError("OutPath nullo. Impossibile procedere al salvataggio del report delle query eseguite dal dao");
		}
	}

	/**
	 * Stampa il file report del dao nel percorso specificato dal valore
	 * dell'attributo outPathFolder dell'annotazione @DaoConfig. Il nome del
	 * file è dato dal nome della classe che estende DaoHelper seguito dalla
	 * rappresentazione numerica della data di sistema
	 * 
	 * @throws Exception
	 */
	public void saveReport() throws Exception {
		saveReport(getOutPath());
	}

	/**
	 * Torna il contenuto complessivo del report come lista di stringhe
	 * 
	 * @return PList<String>
	 * @throws Exception
	 */
	public PList<String> getReportContent() throws Exception {
		PList<String> fileContent = plstr();
		if (isInMemory()) {
			fileContent.add(getTitle(TITLE_IN_MEMORY, l, SEP));
		}
		fileContent.add(getTitle(SP_CALLED, l, SEP));
		fileContent.addAll(getDaoOutput().getSpCalled());
		fileContent.add(LF);
		fileContent.add(getTitle(str(QUERY_ESEGUITE_IN_ERRORE, getDaoOutput().getErrorQueryDetails()), l, SEP));
		fileContent.aggiungiList(getDaoOutput().getErrorQuery());
		fileContent.add(LF);

		fileContent.add(getTitle(QUERY_BY_TABLE, l, SEP));
		fileContent.aggiungiList(getDaoOutput().getGroupBy());
		fileContent.add(LF);
		fileContent.add(getTitle(TOTAL_RECORD, l, SEP));
		fileContent.aggiungiList(getDaoOutput().getTotalReport());
		return fileContent;
	}

	private PList<String> getErrorQuery() throws Exception {
		return getContainer().like(ERROR_MARK).find();

	}

	/**
	 * Imposta a null l'istanza del singleton e chiude la connessione
	 */
	public void destroy() throws Exception {
		closeConnection();
		INSTANCE = null;
	}

	/**
	 * Esegue la finalizzazione delle operazioni del Dao. Se l'attributo in
	 * memory è true, esegue la serializzazione su file [nomeClasseDao]_DB.log
	 * del database in memoria. Se printLogReport è true, esegue la stampa del
	 * log del report finale delle query eseguite dalla classe del dao. Se il
	 * valore dell'attributo outPath di @DaoConfig è not null, esegue il
	 * salvataggio del file del report finale nel path indicato.Se impact è
	 * impostato su true, stampa nello stesso path, i file di resoconto di
	 * impatto delle entities indicate da impactAlias.Ripulisce la table cache.
	 * Esegue la commit. Chiude la connessione. Pone a null la variabile istanza
	 * del singleton.
	 * 
	 * Se sono in ambiente EJB Container (chiamata del costruttore con il nome
	 * del dataSource) con modalità CMT Container Managed Transaction, non
	 * vengono eseguite tutte queste operazioni di finalizzazione, l'istanza del
	 * dao resta attiva in memoria, non vengono loggate le query eseguite dal
	 * dao, non viene generato il report delle statistiche delle query e non
	 * viene eseguita la commit.
	 * 
	 * @param printLogReport
	 * @throws Exception
	 */
	public void finalize(boolean printLogReport) throws Exception {
		if (isFromBatchCall())
			finalizeEJBContainerFromBatch();
		else {
			if (!isDsMode()) {
				Date d = now();
				if (isInMemory()) {
					serializeToFile(getDb(), db());
				}
				commit();
				integraContainer();
				try {
					if (printLogReport)
						printReport();
				} catch (Exception e) {
					logError("Errore durante la stampa di log del report finale", e);
				}
				try {
					saveReport();
				} catch (Exception e) {
					logError("Errore durante il salvataggio del file del report finale nel path", getOutPath(), e);
				}
				impact();
				clearTableCache();
				destroy();
				log("FINALIZE TIME", getClass().getName(), elapsedTime(d));
			} else {
				log("FINALIZE NON ESEGUITA, MODALITA' EJB CONTAINER CMT: assenza di report delle query, no logging delle query, no destroy dell'istanza del dao e no chiusura connessione che viene chiusa automaticamente a ogni query eseguita");
			}
		}
	}

	/**
	 * Esegue la finalizzazione in caso di dao in ambiente EJB Container e
	 * invocato da un batch (quindi non WEB). Viene generato il report delle
	 * statistiche delle query che può essere restituito al batch chiamante e
	 * viene distrutta l'istanza del dao e chiusa la connessione. Vengono
	 * generati anche i file di impatto della chiamata.Alla prossima chiamata da
	 * parte del batch, verrà generata una nuova istanza del dao e un nuovo
	 * report delle query.
	 * 
	 * @throws Exception
	 */
	private void finalizeEJBContainerFromBatch() throws Exception {
		integraContainer();
		impact();
		clearTableCache();
		destroy();
	}

	/**
	 * Esegue la finalize senza stampare nulla a log o nel file di report o nei
	 * file di impatto
	 * 
	 * @throws Exception
	 */
	public void finalizeNoReport() throws Exception {
		setOutPath(null);
		clearTableCache();
		finalize(false);
	}

	private void addQuery(Object... testo) {
		if (!isDisableContainer()) {
			if (null != getContainer()) {
				getContainer().add(strSpaced(testo));
			}
		}
	}

	private Integer getNumCommitRollback() {
		return numCommitRollback;
	}

	private void setNumCommitRollback(Integer numCommitRollback) {
		this.numCommitRollback = numCommitRollback;
	}

	private PMap<String, PList<Entity>> getDb() {
		return db;
	}

	/**
	 * Stampa a log tutto il database in memoria per le esecuzioni in memory
	 */
	public void showDb() {
		if (isInMemory()) {
			log(getTitle(TABELLE_PRESENTI_IN_MEMORY, l, SEP));
			Integer recordTotali = 0;
			for (Map.Entry<String, PList<Entity>> entry : getDb().entrySet()) {
				recordTotali += entry.getValue().size();
				log(str(TAB, substring(entry.getKey(), DOT, false, true, null, false, false), DOUBLE_SPACE, entry.getValue().size(), _RECORDS, LF));
			}
			log(str(TAB, RECORD_TOTALI_IN_MEMORY, recordTotali));
			log(str(LF, LF));
			for (Map.Entry<String, PList<Entity>> entry : getDb().entrySet()) {
				log(getTitle(str(substring(entry.getKey(), DOT, false, true, null, false, false), DOUBLE_SPACE, entry.getValue().size(), _RECORDS), l, SEP));
				for (Entity e : entry.getValue()) {
					log(TAB, TAB, e);
				}
				log(LF, LF);
			}
		}
	}

	/**
	 * Esporta su file il contenuto del database in memoria. Path è il percorso
	 * senza il nome. Verrà creato il file [nomeClasseDao]_DBExport.log
	 * 
	 * @param path
	 */
	public void exportDb(String path) {
		if (isInMemory()) {
			PList<String> db = plstr();
			db.add(getTitle(TABELLE_PRESENTI_IN_MEMORY, l, SEP));
			Integer recordTotali = 0;
			for (Map.Entry<String, PList<Entity>> entry : getDb().entrySet()) {
				recordTotali += entry.getValue().size();
				db.add(str(TAB, substring(entry.getKey(), DOT, false, true, null, false, false), DOUBLE_SPACE, entry.getValue().size(), _RECORDS, LF));
			}
			db.add(str(TAB, RECORD_TOTALI_IN_MEMORY, recordTotali));
			db.add(str(LF, LF));
			for (Map.Entry<String, PList<Entity>> entry : getDb().entrySet()) {
				db.add(getTitle(str(substring(entry.getKey(), DOT, false, true, null, false, false), DOUBLE_SPACE, entry.getValue().size(), _RECORDS), l, SEP));
				for (Entity e : entry.getValue()) {
					db.add(str(TAB, TAB, e));
				}
				db.add(str(LF, LF));
			}
			path = emptyIfNull(path);
			if (notNull(path)) {
				path = str(path, SLASH);
			}
			writeFile(str(path, dbExport()), db);
		}
	}

	private void setDb(PMap<String, PList<Entity>> db) {
		this.db = db;
	}

	private boolean isInMemory() {
		return inMemory;
	}

	private Integer generateInt(Integer max) {
		return new Random().nextInt(max);
	}

	/**
	 * Alias di makeDb
	 */
	public void createDb() {
		makeDb();
	}

	/**
	 * Crea il database in memoria generando un massimo di 20 record per ognuna
	 * delle entities indicate nell'attributo entities dell'annotazione @DaoConfig
	 */
	public void makeDb() {
		if (!isInMemory())
			return;
		log(getTitle("CREAZIONE DEL DATABASE IN MEMORIA", l, SEP));
		String[] entities = getClass().getAnnotation(DaoConfig.class).entities();
		for (String ent : entities) {
			String nome = "";
			Class c = null;
			String alias = null;
			try {
				nome = str(getPkgName(), DOT, ent);
				c = Class.forName(nome);
			} catch (ClassNotFoundException e) {
				logError("Classe", nome, "non trovata", e);
			}
			if (notNull(c)) {
				for (int i = 0; i < generateInt(20); i++) {
					try {
						alias = ((Table) c.getAnnotation(Table.class)).alias();
						giveMeMock(alias).insert();
						log("Creato record per alias", alias, "--->", c.getName());
					} catch (Exception e) {
						logError("Errore durante l'inserimento dell'entity", c.getName(), e);
					}
				}
			}
		}
		log(getTitle("FINE CREAZIONE DEL DATABASE IN MEMORIA", l, SEP));
		showDb();
	}

	private Integer getUpdateDeleteLimit() {
		return updateDeleteLimit;
	}

	private void setUpdateDeleteLimit(Integer updateDeleteLimit) {
		this.updateDeleteLimit = updateDeleteLimit;
	}

	private boolean isLogWhileRunning() {
		return logWhileRunning;
	}

	private void setLogWhileRunning(boolean logWhileRunning) {
		this.logWhileRunning = logWhileRunning;
	}

	private boolean isAutocommit() {
		return autocommit;
	}

	private void setAutocommit(boolean autocommit) {
		this.autocommit = autocommit;
	}

	private boolean isPurgeDb() {
		return purgeDb;
	}

	private void setPurgeDb(boolean purgeDb) {
		this.purgeDb = purgeDb;
	}

	private void setInMemory(boolean inMemory) {
		this.inMemory = inMemory;
	}

	private boolean getInMemory() {
		return inMemory;
	}

	private void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	private Integer getTimeout() {
		return timeout;
	}

	private Integer getFlushThreshold() {
		return flushThreshold;
	}

	private void setFlushThreshold(Integer flushThreshold) {
		this.flushThreshold = flushThreshold;
	}

	private void flushContainer() throws Exception {
		if (!isDisableContainer()) {
			if (getContainer().size() >= getFlushThreshold()) {
				integraContainer();
				log("Flushed container!!!!!");
			}
		}
	}

	private void integraContainer() throws Exception {
		if (!isDisableContainer()) {
			getDaoOutput().addSP(getSPCalled());
			getDaoOutput().addError(getErrorQuery());
			getDaoOutput().addEntityDetail(getEntDetail());
			getDaoOutput().addErrorSelect(getInteger(countErrorQueryType(SELECT)));
			getDaoOutput().addErrorUpdate(getInteger(countErrorQueryType(UPDATE)));
			getDaoOutput().addErrorInsert(getInteger(countErrorQueryType(INSERT)));
			getDaoOutput().addErrorDelete(getInteger(countErrorQueryType(DELETE)));
			getDaoOutput().addErrorSP(countErrorSP());
			getDaoOutput().addTotal(getTotal());
			container = plstr();
			totalRecords = plstr();
			errorsQueryType = new HashMap<String, Integer>();
		}
	}

	private DaoOutput getDaoOutput() {
		return daoOutput;
	}

	/**
	 * Scrive sul file identificato da path l'impatto che la classe del dao ha
	 * avuto per la tabella identificata da alias. I record estratti e scritti
	 * direttamente su file sono quelli modificati nell'intervallo temporale che
	 * va tra l'istanziazione del dao e l'istante in cui si richiama il metodo
	 * stesso, modificati dal codice applicazione codAppl attributo di
	 * DaoConfig. Ritorna il nome del file di impatto creato.
	 * 
	 * @param alias
	 * @param path
	 * @throws Exception
	 * @return String
	 */
	public String impact(String alias, String path) throws Exception {
		if (tutte(isDsMode(), !isFromBatchCall()))
			throw new Exception("Modalità EJB in ambiente WEB. Generazione dei file di impatto disabilitata");
		return giveMe(alias).modifiedBetween(getStart(), now()).byApp(getCodAppl()).selectForWrite(path);
	}

	/**
	 * Ritorna i record dell'alias specificato modificati nell'intervallo
	 * temporale che va tra l'istanziazione del dao e l'istante in cui si
	 * richiama il metodo stesso, modificati dall'utente codUtente e dal codice
	 * applicazione codAppl entrambi attributi di @DaoConfig. Ritorna una lista
	 * di stringhe contenuto del file di impatto per l'entity alias specificata
	 * 
	 * @param alias
	 * @return PList<String>
	 * @throws Exception
	 */
	public PList<String> impact(String alias) throws Exception {
		if (tutte(isDsMode(), !isFromBatchCall()))
			throw new Exception("Modalità EJB in ambiente WEB. Generazione dei file di impatto disabilitata");
		return giveMe(alias).modifiedBetween(getStart(), now()).byApp(getCodAppl()).byUser(getCodUtente()).selectForWrite();
	}

	public Date getStart() {
		return start;
	}

	/**
	 * Se l'attributo impact di @DaoCOnfig è true scrive i file di resoconto di
	 * impatto del dao. Le entities coinvolte sono quelle indicate
	 * nell'attributo impactAlias di @DaoConfig e che hanno tutte e tre le
	 * colonne COD_UTENTE,COD_APPL,DATA_AGGIORNAMENTO. Il path in cui salvare i
	 * file di resoconto è il valore dell'attributo outPath di @DaoConfig
	 * 
	 * @param <T>
	 * @throws Exception
	 */
	private <T extends BaseEntity> void impact() throws Exception {
		if (isImpact()) {
			for (String alias : safe(getImpactAlias())) {
				T be = giveMe(alias);
				if (notNull(be)) {
					if (be.hasImpactColums()) {
						if (notNull(getOutPath())) {
							String nomeFile = impact(alias, str(getOutPath(), File.separator, alias, TXT));
							log("Creato file di resoconto di impatto per ", alias, " : ", nomeFile);
						} else {
							logError("OutPath nullo. File di impatto non generati");
						}
					} else {
						logError("L'entity", alias, "manca di almeno una tra le colonne COD_APPL, COD_UTENTE, DATA_AGGIORNAMENTO. Resoconto di impatto non calcolabile.");
					}
				}
			}
		}
	}

	private boolean isImpact() {
		return impact;
	}

	private void setImpact(boolean impact) {
		this.impact = impact;
	}

	private PList<String> getImpactAlias() {
		return impactAlias;
	}

	private void setImpactAlias(PList<String> impactAlias) {
		this.impactAlias = impactAlias;
	}

	private String getOutPath() {
		return outPath;
	}

	private void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	private boolean isDisableContainer() {
		return disableContainer;
	}

	public void setDisableContainer(boolean disableContainer) {
		this.disableContainer = disableContainer;
	}

	private DataSource getDs() {
		return ds;
	}

	private boolean isDsMode() {
		return dsMode;
	}

	private void setDsMode(boolean dsMode) {
		this.dsMode = dsMode;
	}

	private boolean isFromBatchCall() {
		return fromBatchCall;
	}

	private void setFromBatchCall(boolean fromBatchCall) {
		this.fromBatchCall = fromBatchCall;
	}

	/**
	 * Ritorna la connessione dal datasource
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection getConnectionDataSource() throws SQLException {
		return getDs().getConnection();
	}

	/**
	 * Chiude la connessione passata come parametro
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public void closeConnection(Connection conn) throws SQLException {
		if (notNull(conn)) {
			conn.close();
		}
	}

	private boolean isDb2() {
		return db2;
	}

	private void setDb2(boolean db2) {
		this.db2 = db2;
	}

	/**
	 * Imposta connection, codUtenteCostruttore, codAppCostruttore e container
	 * di query.
	 * 
	 * @param ent
	 * @return BaseEntity
	 * @throws Exception
	 */
	private BaseEntity impostaParametriEntity(BaseEntity ent) throws Exception {
		ent.setConnection(getConn());
		ent.setCodApplCostruttore(getCodAppl());
		ent.setCodUtenteCostruttore(getCodUtente());
		ent.setContainer(getContainer());
		return ent;
	}

}
