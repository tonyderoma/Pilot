package it.eng.pilot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe che implementa la logica di business di basso livello per realizzare
 * la programmazione assistita.
 * 
 * @author Antonio Corinaldi
 */
/**
 * @author IT20428
 * 
 */
@SuppressWarnings("unchecked")
public class Pilot implements Serializable {

	public static int SQL_STRING = Types.VARCHAR;
	public static int SQL_CHAR = Types.CHAR;
	public static int SQL_LONG = Types.BIGINT;
	public static int SQL_INTEGER = Types.INTEGER;
	public static int SQL_DOUBLE = Types.DOUBLE;
	public static int SQL_FLOAT = Types.FLOAT;
	public static int SQL_DATE = Types.DATE;
	public static int SQL_TIMESTAMP = Types.TIMESTAMP;
	public static int SQL_BIGDECIMAL = Types.NUMERIC;
	public static int SQL_BYTEARRAY = Types.BLOB;

	// short versions
	public static int _S = Types.VARCHAR;
	public static int _C = Types.CHAR;
	public static int _L = Types.BIGINT;
	public static int _I = Types.INTEGER;
	public static int _D = Types.DOUBLE;
	public static int _F = Types.FLOAT;
	public static int _DT = Types.DATE;
	public static int _TS = Types.TIMESTAMP;
	public static int _BIG = Types.NUMERIC;
	public static int _BARR = Types.BLOB;

	private static final String SIZE = "   SIZE=";
	public static final String QS_START = "?";
	private static final String EQ = "=";
	private static final long K = 1024;
	private static final long M = K * K;
	private static final long G = M * K;
	private static final long T = G * K;

	private static final String AT = "@";
	private static final String CLOSE_QUADRA_GRAFFA = "]  }";
	private static final String OPEN_GRAFFA = "{";
	private static final String OPEN_QUADRA = "[";
	private static final String TO_STRING = "toString";
	public static final String DASHTRIM = "-";
	public static final String PIPE = "|";
	public static final String DASH = " - ";
	private static final int MOCK_LIST_SIZE = 10;
	private Comune comune;
	private Properties props = new Properties();
	private static final String CUTTER = "...";
	private List<Comune> comuni = new ArrayList<Comune>();
	private final List<String> mail = Arrays.asList("gmail.com", "gmail.it", "libero.it", "hotmail.com", "hotmail.it", "yahoo.it", "yahoo.com", "posteo.it", "starmail.com", "tutanota.com",
			"protonmail.it");
	private final List<String> sillabeStart = Arrays.asList("SC", "SB", "SD", "SF", "TR", "SG", "FR", "BR", "PL", "PR", "ST", "CL", "CR", "DR", "FR", "PR", "SL", "SV", "GR", "GN", "VR", "FL", "SM",
			"SN", "SQ");
	private final List<String> sillabeFull = Arrays.asList("FR", "TR", "SB", "SD", "SF", "SG", "BR", "LB", "PL", "PR", "RZ", "SQ", "ST", "CL", "GL", "CR", "DR", "FR", "PR", "PS", "SL", "VR", "SM",
			"SN", "SQ", "FL", "SV", "GR", "GN", "LP", "LS", "LT", "NT", "NS", "NV", "NC", "NZ", "ND", "MB", "ML", "MP", "SC", "SR", "SS", "TT", "PP", "MM", "NN", "BB", "CC", "DD", "FF", "GG", "LL",
			"PP", "RR", "VV", "ZZ");
	private static final String VOWELS = "AEIOU";
	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBERS = "0123456789";
	private static final String JAVA_DATE = "java.util.Date";
	private static final String JAVA_DATE_SQL = "java.sql.Date";
	private static final String JAVA_STRING = "java.lang.String";
	private static final String JAVA_INTEGER = "java.lang.Integer";
	private static final String JAVA_INTEGER_PRIV = "int";
	private static final String JAVA_LIST = "java.util.List";
	private static final String JAVA_SET = "java.util.Set";
	private static final String JAVA_PLIST = "it.eng.pilot.PList";
	private static final String JAVA_LONG = "java.lang.Long";
	private static final String JAVA_LONG_PRIV = "long";
	private static final String JAVA_SHORT = "java.lang.Short";
	private static final String JAVA_SHORT_PRIV = "short";
	private static final String JAVA_DOUBLE_PRIV = "double";
	private static final String JAVA_DOUBLE = "java.lang.Double";
	private static final String JAVA_FLOAT_PRIV = "float";
	private static final String JAVA_FLOAT = "java.lang.Float";
	private static final String JAVA_BIG_DECIMAL = "java.math.BigDecimal";
	private static final String JAVA_BOOLEAN = "java.lang.boolean";
	private static final String JAVA_BOOLEAN_PRIV = "boolean";
	public static final String THREE_SPACE = "   ";
	private static final String IS = "is";
	private static final String GET = "get";
	public static final String COMMA = ",";
	public static final String COLON = ":";
	public static final String SEMICOLON = ";";
	private static final String EQUAL = EQ;
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String UNDERSCORE = "_";
	private static final String SLASH = "/";
	private static final String DOT = ".";
	public static final String SPACE = " ";
	private static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	private static final String INFINITE_DATE = "31/12/9999 00:00:00";
	private static final String INFINITE_START_DATE = "01/01/0001 00:00:00";
	private static final String SEP = ", ";
	private static final String D = "%d ";
	private static final long serialVersionUID = -7600175492274427645L;
	private static final int MAX_FILE_SIZE = 10 * 1024 * 1024;
	protected static final String LF = "\n";
	protected static final String TAB = "\t";
	public static final String DAYS = DaoHelper.DAYS;
	public static final String HOURS = DaoHelper.HOURS;
	public static final String MINUTES = DaoHelper.MINUTES;
	public static final String SECONDS = DaoHelper.SECONDS;
	public static final String MILLISECONDS = DaoHelper.MILLISECONDS;
	public static final String livelloMilliSecondi = D + MILLISECONDS;
	public static final String livelloSecondi = D + SECONDS + SEP + livelloMilliSecondi;
	public static final String livelloMinuti = D + MINUTES + SEP + livelloSecondi;
	public static final String livelloOre = D + HOURS + SEP + livelloMinuti;
	public static final String livelloGiorni = D + DAYS + SEP + livelloOre;
	public static final String ATTIVO = "A";
	public static final String DISATTIVO = "C";
	public static final String SI = "SI";
	public static final String NO = "NO";
	public static final String S = "S";
	public static final String N = "N";
	public static final Character ATTIVO_CHAR = 'A';
	public static final Character DISATTIVO_CHAR = 'C';
	public static final String euro = Currency.getInstance(Locale.ITALY).getSymbol();
	public static final Character SI_CHAR = 'S';
	public static final Character NO_CHAR = 'N';
	public static final BigDecimal ZERO = BigDecimal.ZERO;
	public static final BigDecimal ONE = BigDecimal.ONE;
	public static final BigDecimal DIECI = BigDecimal.TEN;
	public static final BigDecimal CENTO = DIECI.multiply(DIECI);
	public static final BigDecimal MILLE = CENTO.multiply(DIECI);
	private static final String patternEmail = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern emailPattern = Pattern.compile(patternEmail);
	private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	private static final SimpleDateFormat sdfOracle = new SimpleDateFormat("dd-MMM-yy");
	private static final SimpleDateFormat sdfhhmmss = new SimpleDateFormat(DATETIME_FORMAT);
	public static final String SET = "set";
	private transient Logger logger = LoggerFactory.getLogger(Pilot.class);
	private List<String> nomi = new ArrayList<String>();
	private List<String> cognomi = new ArrayList<String>();
	private List<String> indirizzi = new ArrayList<String>();
	private boolean readFileComuni = false;
	private String nomeMock;
	private String cognomeMock;
	private String propertyFile;
	private Map<String, TimeCount> mapTimeCount = new HashMap<String, TimeCount>();

	public Pilot() {
	}

	/**
	 * Esegue la stampa di log di tipo error (log.error) degli oggetti passati
	 * come parametri. Se l'ultimo parametro ï¿½ istanza di Throwable allora
	 * chiama il metodo di log.error che prevede come ultimo argomento
	 * l'eccezione e ossia log.error(messaggio,e)
	 * 
	 * @param k
	 */
	public void logError(Object... k) {
		if (k != null) {
			if (k[k.length - 1] instanceof Throwable) {
				getLog().error(getStringSpaced(modificaLunghezzaArray(k, k.length - 1)), (Throwable) k[k.length - 1]);
			} else {
				getLog().error(getStringSpaced(k));
			}
		}
	}

	/**
	 * Esegue la stampa di log di tipo info (log.info)
	 * 
	 * @param k
	 */
	public void log(Object... k) {
		getLog().info(getStringSpaced(k));
	}

	/**
	 * Metodo che da una stringa data in formato dd/mm/yyyy genera una stringa
	 * data in formato dd-MMM-yy
	 * 
	 * @param d
	 * @return String
	 */
	public String stringToOracleDateString(String d) {
		String data = null;
		if (notNull(d)) {
			data = sdfOracle.format(stringToDate(d));
		}
		return data;
	}

	private String getString(Object o) {
		return (String) o;
	}

	private Boolean getBoolean(Object o) {
		return (Boolean) o;
	}

	private Date getDate(Object o) {
		return (Date) o;
	}

	private java.sql.Date getSqlDate(Object o) {
		return (java.sql.Date) o;
	}

	private Integer getInteger(Object o) {
		return (Integer) o;
	}

	private BigDecimal getBigDecimal(Object o) {
		return (BigDecimal) o;
	}

	private Double getDouble(Object o) {
		return (Double) o;
	}

	private Long getLong(Object o) {
		return (Long) o;
	}

	private Short getShort(Object o) {
		return (Short) o;
	}

	private Float getFloat(Object o) {
		return (Float) o;
	}

	private Character getCharacter(Object o) {
		return (Character) o;
	}

	private BigInteger getBigInteger(Object o) {
		return (BigInteger) o;
	}

	/**
	 * Data una lista torna la stessa lista ordinata in ordine ascendente
	 * secondo il metodo equals
	 * 
	 * @param l
	 * @return List<K>
	 */
	public <K extends Comparable<? super K>> List<K> sort(List<K> l) {
		Collections.sort(safe(l));
		return l;
	}

	/**
	 * Data una lista torna la stessa lista ordinata in ordine discendente
	 * secondo il metodo equals
	 * 
	 * @param l
	 * 
	 * @return List<K>
	 */
	public <K extends Comparable<? super K>> List<K> sortDesc(List<K> l) {
		return inverti(sort(l));
	}

	/**
	 * Data una lista ne inverte l'ordine
	 * 
	 * @param l
	 * @return List<K>
	 */
	public <K> List<K> inverti(List<K> l) {
		if (notNull(l))
			Collections.reverse(l);
		return l;
	}

	/**
	 * Data una collection trova l'elemento massimo
	 * 
	 * @param l
	 * @return K
	 */
	public <K extends Comparable<K>> K max(Collection<K> l) {
		K ret = null;
		if (notNull(l))
			ret = Collections.max(l);
		return ret;
	}

	/**
	 * Data una collection trova l'elemento minimo
	 * 
	 * @param l
	 * @return K
	 */
	public <K extends Comparable<K>> K min(Collection<K> l) {
		K ret = null;
		if (notNull(l))
			ret = Collections.min(l);
		return ret;
	}

	/**
	 * Data una lista torna una nuova lista da cui ha eliminato gli elementi
	 * duplicati secondo il metodo equals
	 * 
	 * @param l
	 * 
	 * @return PList<K>
	 */
	public <K> PList<K> removeDuplicates(List<K> l) {
		PList<K> nuova = new PArrayList<K>();
		if (notNull(l)) {
			nuova = setToList(listToSet(l));
		}
		return nuova;
	}

	/**
	 * Data una lista di oggetti K, esegue l'ordinamento della lista in base ai
	 * campi indicati dove i campi corrispondono alle variabili istanza del bean
	 * in dot notation
	 * 
	 * @param l
	 * @param campi
	 * @return List<K>
	 */

	public <K> List<K> sort(final List<K> l, final String... campi) {
		if (notNull(l)) {
			Collections.sort(l, new Comparator<K>() {
				public int compare(K k1, K k2) {
					int ret = 0;
					try {
						ret = ((Comparable) k1).compareTo((k2));
					} catch (Exception e) {
					}
					if (null != campi) {
						for (String campo : campi) {
							Object o1 = null;
							Object o2 = null;
							try {
								o1 = get(k1, campo);
							} catch (Exception e) {
							}
							try {
								o2 = get(k2, campo);
							} catch (Exception e) {
							}

							if (tutte(Null(o1), notNull(o2))) {
								return -1;
							} else if (tutte(notNull(o1), Null(o2))) {
								return 1;
							}
							if (notNull(o1, o2)) {
								if (o1 instanceof String) {
									ret = getString(o1).compareTo(getString(o2));
								} else if (o1 instanceof Date) {
									ret = getDate(o1).compareTo(getDate(o2));
								} else if (o1 instanceof Long) {
									ret = getLong(o1).compareTo(getLong(o2));
								} else if (o1 instanceof BigDecimal) {
									ret = getBigDecimal(o1).compareTo(getBigDecimal(o2));
								} else if (o1 instanceof Integer) {
									ret = getInteger(o1).compareTo(getInteger(o2));
								} else if (o1 instanceof Double) {
									ret = getDouble(o1).compareTo(getDouble(o2));
								} else if (o1 instanceof Boolean) {
									ret = getBoolean(o1).compareTo(getBoolean(o2));
								} else if (o1 instanceof Timestamp) {
									ret = getTimestamp((Date) o1).compareTo(getTimestamp((Date) o2));
								} else if (o1 instanceof java.sql.Date) {
									ret = getSqlDate(o1).compareTo(getSqlDate(o2));
								} else if (o1 instanceof Short) {
									ret = getShort(o1).compareTo(getShort(o2));
								} else if (o1 instanceof Float) {
									ret = getFloat(o1).compareTo(getFloat(o2));
								} else if (o1 instanceof Character) {
									ret = getCharacter(o1).compareTo(getCharacter(o2));
								} else if (o1 instanceof BigInteger) {
									ret = getBigInteger(o1).compareTo(getBigInteger(o2));
								}
							}
							if (ret != 0)
								break;
						}
					}
					return ret;
				}
			});
		}
		return l;
	}

	/**
	 * Data una lista di oggetti K, esegue l'ordinamento discendente della lista
	 * in base ai campi indicati dove i campi corrispondono alle variabili
	 * istanza del bean in dot notation
	 * 
	 * @param <K>
	 * @param l
	 * @param campi
	 * @return List<K>
	 */
	public <K> List<K> sortDesc(final List<K> l, final String... campi) {
		return inverti(sort(l, campi));
	}

	/**
	 * Esegue la scrittura sullo stdOut della stringa s piï¿½ l'oggetto o
	 * passato come parametro
	 * 
	 * @param s
	 * @param o
	 * 
	 */
	protected <K> void wr(String s, K o) {
		System.out.println(getString(s, o));
	}

	/**
	 * Ritorna la rappresentazione stringa dell'oggetto passato come parametro
	 * 
	 * @param o
	 * @return String
	 */
	public <K> String toStr(K o) {
		if (Null(o))
			return null;
		String ret = "";
		if (o instanceof Collection) {
			ret = listToString((Collection) o);
		} else {
			if (o instanceof Map) {
				ret = mapToString((Map) o);
			} else {
				ret = beanToString(o);
			}
		}
		return ret;
	}

	/**
	 * Ritorna la rappresentazione stringa dell'oggetto BaseEntity passato come
	 * parametro. Verranno stampati solo i campi che contengono l'annotazione @Column
	 * 
	 * @param <K>
	 * @param o
	 * @return K
	 */
	public <K extends BaseEntity> String toStrEntity(K o) {
		if (Null(o))
			return null;
		String ret = "";
		if (o instanceof Collection) {
			ret = listEntityToString((Collection) o);
		} else {
			if (o instanceof Map) {
				ret = mapEntityToString((Map) o);
			} else {
				ret = beanEntityToString(o);
			}
		}
		return ret;
	}

	private <K extends BaseEntity> String toStrListEntity(PList<K> o) {
		if (Null(o))
			return null;
		String ret = "";
		if (o instanceof Collection) {
			ret = listEntityToString((Collection) o);
		}
		return ret;
	}

	private <K extends Comparable<? super K>, T> String mapToString(Map<K, T> mappa) {
		StringBuffer sb = new StringBuffer();
		List<K> chiavi = new ArrayList<K>();
		for (Map.Entry<K, T> entry : mappa.entrySet()) {
			chiavi.add(entry.getKey());
		}
		log("Numero chiavi distinte=", chiavi.size());
		boolean nullPresent = cleanNullList(chiavi);
		if (nullPresent) {
			sb.append(beanToString(null)).append(EQUAL).append(listToString((Collection) mappa.get(null))).append(lf());
		}
		Collections.sort(chiavi);
		for (K k : safe(chiavi)) {
			sb.append(beanToString(k)).append(EQUAL).append(toStr(mappa.get(k))).append(lf());
		}
		return sb.toString();
	}

	private <K extends Comparable<? super K>, T extends BaseEntity> String mapEntityToString(Map<K, T> mappa) {
		StringBuffer sb = new StringBuffer();
		List<K> chiavi = new ArrayList<K>();
		for (Map.Entry<K, T> entry : mappa.entrySet()) {
			chiavi.add(entry.getKey());
		}
		log("Numero chiavi distinte=", chiavi.size());
		boolean nullPresent = cleanNullList(chiavi);
		if (nullPresent) {
			sb.append(beanToString(null)).append(EQUAL).append(listEntityToString((Collection) mappa.get(null))).append(lf());
		}
		Collections.sort(chiavi);
		for (K k : safe(chiavi)) {
			sb.append(beanToString(k)).append(EQUAL).append(toStrEntity(mappa.get(k))).append(lf());
		}
		return sb.toString();
	}

	private <K> String listToString(Collection<K> lista, Integer... i) {
		Integer c = 1;
		if (null != i && i.length == 1) {
			c = i[0];
		}
		StringBuffer sb = new StringBuffer();
		// sb.append(getString(" SIZE=", String.valueOf(lista.size()), lf()));
		sb.append(SIZE).append(String.valueOf(lista.size())).append(lf());
		for (K k : safe(lista)) {
			sb.append(tabn(c)).append(beanToString(k)).append(lf());
		}
		return sb.toString();
	}

	private <K extends BaseEntity> String listEntityToString(Collection<K> lista, Integer... i) {
		Integer c = 1;
		if (null != i && i.length == 1) {
			c = i[0];
		}
		StringBuffer sb = new StringBuffer();
		// sb.append(getString(" SIZE=", String.valueOf(lista.size()), lf()));
		sb.append(SIZE).append(String.valueOf(lista.size())).append(lf());
		for (K k : safe(lista)) {
			sb.append(tabn(c)).append(beanEntityToString(k)).append(lf());
		}
		return sb.toString();
	}

	/**
	 * Dato un oggetto bean e una proprieta prop in dot notation, torna il
	 * valore di quella proprieta.Se il bean ï¿½ null torna null
	 * 
	 * @param bean
	 * @param prop
	 * @return Object
	 * @throws Exception
	 */
	public Object get(Object bean, String prop) throws Exception {
		if (notNull(prop)) {
			String p[] = prop.split("\\.");
			for (String pr : p) {
				bean = getProp(bean, pr);
			}
		}
		return bean;
	}

	/**
	 * Dato un oggetto bean e una proprieta prop in dot notation, torna il
	 * valore di quella proprieta. Se il bean ï¿½ null, torna il default value
	 * 
	 * @param bean
	 * @param prop
	 * @param defaultValue
	 * @return Object
	 * @throws Exception
	 */
	public Object get(Object bean, String prop, Object defaultValue) throws Exception {
		String goneDefault = "";
		if (notNull(prop)) {
			String p[] = prop.split("\\.");
			for (String pr : p) {
				bean = getPropOrElse(bean, pr, defaultValue, goneDefault);
				if (si(goneDefault))
					return defaultValue;
			}
		}
		return bean;
	}

	/**
	 * Data una lista l1 e n liste, torna una nuova lista che contiene gli
	 * elementi di l1 non presenti nelle altre liste in base al metodo equals
	 * 
	 * @param l1
	 * @param liste
	 * @return List<K>
	 */
	public <K> List<K> sottraiList(List<K> l1, List<K>... liste) {
		List<K> differenza = new ArrayList<K>(safe(l1));
		if (null != liste) {
			for (List<K> list : liste) {
				differenza = sottraiList(differenza, list);
			}
		}
		return differenza;
	}

	/**
	 * Dato un set l1 e n set, torna un nuovo set che contiene gli elementi di
	 * l1 non presenti negli altri set in base al metodo equals
	 * 
	 * @param l1
	 * @param insiemi
	 * @return Set<K>
	 */

	public <K> Set<K> sottraiSet(Set<K> l1, Set<K>... insiemi) {
		Set<K> differenza = new HashSet<K>(safe(l1));
		if (null != insiemi) {
			for (Set<K> set : insiemi) {
				differenza = sottraiSet(differenza, set);
			}
		}
		return differenza;
	}

	private <K> List<K> sottraiList(List<K> l1, List<K> l2) {
		List<K> differenza = new ArrayList<K>(safe(l1));
		if (notNull(differenza)) {
			for (K k : safe(l2)) {
				if (differenza.contains(k)) {
					differenza.remove(k);
				}
			}
		}
		return differenza;
	}

	private <K> Set<K> sottraiSet(Set<K> l1, Set<K> l2) {
		Set<K> differenza = new HashSet<K>(safe(l1));
		if (notNull(differenza)) {
			for (K k : safe(l2)) {
				if (differenza.contains(k)) {
					differenza.remove(k);
				}
			}
		}
		return differenza;
	}

	/**
	 * Date n liste torna una nuova lista che contiene tutti gli elementi delle
	 * altre liste
	 * 
	 * @param l
	 * @return List<K>
	 */
	public <K> List<K> aggiungiList(List<K>... l) {
		List<K> somma = new ArrayList<K>();
		if (null != l) {
			for (List<K> k : l) {
				somma.addAll(safe(k));
			}
		}
		return somma;
	}

	/**
	 * Dati n set torna un nuovo set che contiene tutti gli elementi degli altri
	 * set passati in input
	 * 
	 * @param l
	 * @return Set<K>
	 */
	public <K> Set<K> aggiungiSet(Set<K>... l) {
		Set<K> somma = new HashSet<K>();
		if (null != l) {
			for (Set<K> k : l) {
				somma.addAll(safe(k));
			}
		}
		return somma;
	}

	private PList<String> trimStringList(List<String> l) {
		for (String s : safe(l)) {
			if (notNull(s)) {
				try {
					aggiornaListValue(l, s, s.trim());
				} catch (Exception e) {
				}
			}
		}
		return toPList(l);
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo una Lista
	 * di elementi di tipo c specificato dove c ï¿½ il tipo dell'elememto.Tipi
	 * previsti sono String, Integer, Long,Short,Double,BigDecimal
	 * 
	 * @param s
	 * @param separator
	 * @param c
	 * @return PList<K>
	 */
	public <K> PList<K> toList(String s, String separator, Class<K> c) {
		if (Null(s))
			return pl();
		s = emptyIfNull(s);
		String[] arr = s.split(Pattern.quote(separator));
		PList<String> ls = arrayToList(arr);

		if (is(c.getName(), JAVA_STRING)) {
			ls = trimStringList(ls);
		}

		if (is(c.getName(), JAVA_INTEGER)) {
			return (PList<K>) toIntList(ls);
		}

		if (is(c.getName(), JAVA_LONG)) {
			return (PList<K>) toLongList(ls);
		}

		if (is(c.getName(), JAVA_SHORT)) {
			return (PList<K>) toShortList(ls);
		}

		if (is(c.getName(), JAVA_DOUBLE)) {
			return (PList<K>) toDoubleList(ls);
		}

		if (is(c.getName(), JAVA_BIG_DECIMAL)) {
			return (PList<K>) toBigDecimalList(ls);

		}
		return (PList<K>) ls;
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo una Lista
	 * di elementi di tipo Long
	 * 
	 * @param s
	 * @param separator
	 * @return PList<Long>
	 */
	public PList<Long> toListLong(String s, String separator) {
		return toList(s, separator, Long.class);
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo una Lista
	 * di elementi di tipo Integer
	 * 
	 * @param s
	 * @param separator
	 * @return PList<Short>
	 */
	public PList<Integer> toListInteger(String s, String separator) {
		return toList(s, separator, Integer.class);
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo una Lista
	 * di elementi di tipo String
	 * 
	 * @param s
	 * @param separator
	 * @return PList<String>
	 */
	public PList<String> toListString(String s, String separator) {
		return toList(s, separator, String.class);
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo una Lista
	 * di elementi di tipo Short
	 * 
	 * @param s
	 * @param separator
	 * @return PList<Short>
	 */
	public PList<Short> toListShort(String s, String separator) {
		return toList(s, separator, Short.class);
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo una Lista
	 * di elementi di tipo Double
	 * 
	 * @param s
	 * @param separator
	 * @return PList<Double>
	 */
	public PList<Double> toListDouble(String s, String separator) {
		return toList(s, separator, Double.class);
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo una Lista
	 * di elementi di tipo BigDecimal
	 * 
	 * @param s
	 * @param separator
	 * @return PList<BigDecimal>
	 */
	public PList<BigDecimal> toListBigDecimal(String s, String separator) {
		return toList(s, separator, BigDecimal.class);
	}

	/**
	 * Data una lista di stringhe numeriche ottengo una lista di Short
	 * 
	 * @param l
	 * @return PList<Short>
	 */
	public PList<Short> toShortList(List<String> l) {
		PList<Short> ll = pl();
		for (String s : safe(l)) {
			ll.add(Short.parseShort(s.trim()));
		}
		return ll;
	}

	/**
	 * Dato un set di stringhe numeriche ottengo un set di Short
	 * 
	 * @param l
	 * @return Set<Short>
	 */
	public Set<Short> toShortSet(Set<String> l) {
		Set<Short> ll = new HashSet<Short>();
		for (String s : safe(l)) {
			ll.add(Short.parseShort(s.trim()));
		}
		return ll;
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo un Set di
	 * elementi di tipo c specificato dove c ï¿½ il tipo dell'elememto. Tipi
	 * previsti sono String, Integer, Long,Short,Double,BigDecimal
	 * 
	 * @param s
	 * @param separator
	 * @param c
	 * @return Set<K>
	 */
	public <K> Set<K> toSet(String s, String separator, Class<K> c) {
		s = emptyIfNull(s);
		String[] arr = s.split(Pattern.quote(separator));
		Set<String> ls = arrayToSet(arr);
		if (is(c.getName(), JAVA_INTEGER)) {
			return (Set<K>) toIntSet(ls);
		}
		if (is(c.getName(), JAVA_LONG)) {
			return (Set<K>) toLongSet(ls);
		}
		if (is(c.getName(), JAVA_SHORT)) {
			return (Set<K>) toShortSet(ls);
		}
		if (is(c.getName(), JAVA_DOUBLE)) {
			return (Set<K>) toDoubleSet(ls);
		}
		if (is(c.getName(), JAVA_BIG_DECIMAL)) {
			return (Set<K>) toBigDecimalSet(ls);
		}
		return (Set<K>) ls;
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo un Set di
	 * elementi di tipo Long
	 * 
	 * @param s
	 * @param separator
	 * @return Set<Long>
	 */
	public Set<Long> toSetLong(String s, String separator) {
		return toSet(s, separator, Long.class);
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo un Set di
	 * elementi di tipo Integer
	 * 
	 * @param s
	 * @param separator
	 * @return Set<Integer>
	 */
	public Set<Integer> toSetInteger(String s, String separator) {
		return toSet(s, separator, Integer.class);
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo un Set di
	 * elementi di tipo String
	 * 
	 * @param s
	 * @param separator
	 * @return Set<String>
	 */
	public Set<String> toSetString(String s, String separator) {
		return toSet(s, separator, String.class);
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo un Set di
	 * elementi di tipo Short
	 * 
	 * @param s
	 * @param separator
	 * @return Set<Short>
	 */
	public Set<Short> toSetShort(String s, String separator) {
		return toSet(s, separator, Short.class);
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo un Set di
	 * elementi di tipo Double
	 * 
	 * @param s
	 * @param separator
	 * @return Set<Double>
	 */
	public Set<Double> toSetDouble(String s, String separator) {
		return toSet(s, separator, Double.class);
	}

	/**
	 * Data una stringa di elementi con separatore separator, ottengo un Set di
	 * elementi di tipo BigDecimal
	 * 
	 * @param s
	 * @param separator
	 * @return Set<BigDecimal>
	 */
	public Set<BigDecimal> toSetBigDecimal(String s, String separator) {
		return toSet(s, separator, BigDecimal.class);
	}

	/**
	 * Data una lista di stringhe numeriche ottengo una lista di Integer
	 * 
	 * @param l
	 * @return PList<Integer>
	 */
	public PList<Integer> toIntList(List<String> l) {
		PList<Integer> ll = pl();
		for (String s : safe(l)) {
			Integer n = getInteger(s.trim());
			if (notNull(n)) {
				ll.add(n);
			}
		}
		return ll;
	}

	/**
	 * Data una lista di stringhe numeriche ottengo una lista di Double
	 * 
	 * @param l
	 * @return PList<Double>
	 */
	public PList<Double> toDoubleList(List<String> l) {
		PList<Double> ll = pl();
		for (String s : safe(l)) {
			Double n = getDouble(s.trim());
			if (notNull(n)) {
				ll.add(n);
			}
		}
		return ll;
	}

	/**
	 * Data una lista di stringhe numeriche ottengo una lista di BigDecimal
	 * 
	 * @param l
	 * @return PList<BigDecimal>
	 */
	public PList<BigDecimal> toBigDecimalList(List<String> l) {
		PList<BigDecimal> ll = pl();
		for (String s : safe(l)) {
			ll.add(getBigDecimal(s.trim()));
		}
		return ll;
	}

	/**
	 * Dato un set di stringhe numeriche ottengo un set di Integer
	 * 
	 * @param l
	 * @return Set<Integer>
	 */
	public Set<Integer> toIntSet(Set<String> l) {
		Set<Integer> ll = new HashSet<Integer>();
		for (String s : safe(l)) {
			Integer n = getInteger(s.trim());
			if (notNull(n)) {
				ll.add(n);
			}
		}
		return ll;
	}

	/**
	 * Dato un set di stringhe numeriche ottengo un set di Double
	 * 
	 * @param l
	 * @return Set<Double>
	 */
	public Set<Double> toDoubleSet(Set<String> l) {
		Set<Double> ll = new HashSet<Double>();
		for (String s : safe(l)) {
			Double n = getDouble(s.trim());
			if (notNull(n)) {
				ll.add(n);
			}
		}
		return ll;
	}

	/**
	 * Data una lista di stringhe numeriche ottengo una lista di Long
	 * 
	 * @param l
	 * @return PList<Long>
	 */
	public PList<Long> toLongList(List<String> l) {
		PList<Long> ll = pl();
		for (String s : safe(l)) {
			Long n = getLong(s.trim());
			if (notNull(n)) {
				ll.add(n);
			}
		}
		return ll;
	}

	/**
	 * Dato un set di stringhe numeriche ottengo un set di Long
	 * 
	 * @param l
	 * @return Set<Long>
	 */

	public Set<Long> toLongSet(Set<String> l) {
		Set<Long> ll = new HashSet<Long>();
		for (String s : safe(l)) {
			Long n = getLong(s.trim());
			if (notNull(n)) {
				ll.add(n);
			}
		}
		return ll;
	}

	/**
	 * Dato un set di stringhe numeriche ottengo un set di BigDecimal
	 * 
	 * @param l
	 * @return Set<BigDecimal>
	 */
	public Set<BigDecimal> toBigDecimalSet(Set<String> l) {
		Set<BigDecimal> ll = new HashSet<BigDecimal>();
		for (String s : safe(l)) {
			ll.add(getBigDecimal(s.trim()));
		}
		return ll;
	}

	/**
	 * Data una lista di oggetti di tipo T , torna la stessa lista da cui ha
	 * rimosso quegli oggetti la cui proprietï¿½ prop in dot notation ï¿½ uguale
	 * al valore val
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return List<T> torna la stessa lista da cui ho rimosso gli elementi
	 *         della proprieta props in dot notation che hanno valore val
	 * @throws Exception
	 */
	public <T> List<T> cleanList(List<T> l1, String props, Object val) throws Exception {
		if (notNull(l1)) {
			for (Iterator<T> iterator = l1.iterator(); iterator.hasNext();) {
				T t = iterator.next();
				if (is(get(t, props), val))
					iterator.remove();
			}
		}
		return l1;
	}

	/**
	 * Data una lista di oggetti di tipo T , torna la stessa lista da cui ha
	 * rimosso quegli oggetti la cui proprietï¿½ prop in dot notation NON ï¿½
	 * uguale al valore val
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return List<T> torna la stessa lista da cui ho rimosso gli elementi
	 *         della proprieta props in dot notation che NON hanno valore val
	 * @throws Exception
	 */
	public <T> List<T> cleanListNotEqual(List<T> l1, String props, Object val) throws Exception {
		if (notNull(l1)) {
			for (Iterator<T> iterator = l1.iterator(); iterator.hasNext();) {
				T t = iterator.next();
				if (isNot(get(t, props), val))
					iterator.remove();
			}
		}
		return l1;
	}

	/**
	 * Data una lista di oggetti di tipo T , torna la stessa lista da cui ha
	 * rimosso quegli oggetti la cui proprietï¿½ prop[i] in dot notation NON ï¿½
	 * uguale al valore val[i]
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return List<T> torna la stessa lista da cui ho rimosso gli elementi
	 *         delle proprieta props in dot notation che NON hanno valori val
	 * @throws Exception
	 */
	public <T> List<T> cleanListNotEqual(List<T> l1, String[] props, Object[] val) throws Exception {
		for (int i = 0; i < props.length; i++) {
			l1 = cleanListNotEqual(l1, props[i], val[i]);
		}
		return l1;
	}

	/**
	 * Data una lista di oggetti di tipo T , torna la stessa lista da cui ha
	 * rimosso quegli oggetti la cui proprietï¿½ prop[i] in dot notation ï¿½
	 * uguale al valore val[i]
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return List<T> torna la stessa lista da cui ho rimosso gli elementi
	 *         delle proprieta props in dot notation che hanno valori val
	 * @throws Exception
	 */
	public <T> List<T> cleanList(List<T> l1, String[] props, Object[] val) throws Exception {
		for (int i = 0; i < props.length; i++) {
			l1 = cleanList(l1, props[i], val[i]);
		}
		return l1;
	}

	/**
	 * Dato un set di oggetti di tipo T , torna lo stesso set da cui ha rimosso
	 * quegli oggetti la cui proprietï¿½ prop in dot notation ï¿½ uguale al
	 * valore val
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return Set<T> torna lo stesso Set da cui ho rimosso gli elementi delle
	 *         proprieta props in dot notation che hanno valori val
	 * @throws Exception
	 */

	public <T> Set<T> cleanSet(Set<T> l1, String[] props, Object[] val) throws Exception {
		for (int i = 0; i < props.length; i++) {
			l1 = cleanSet(l1, props[i], val[i]);
		}
		return l1;
	}

	/**
	 * Dato un set di oggetti di tipo T , torna lo stesso set da cui ha rimosso
	 * quegli oggetti la cui proprietï¿½ prop in dot notation ï¿½ uguale al
	 * valore val
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return Set<T> torna lo stesso Set da cui ho rimosso gli elementi della
	 *         proprieta props in dot notation che hanno valore val
	 * @throws Exception
	 */
	public <T> Set<T> cleanSet(Set<T> l1, String props, Object val) throws Exception {
		if (notNull(l1)) {
			for (Iterator<T> iterator = l1.iterator(); iterator.hasNext();) {
				T t = iterator.next();
				if (is(get(t, props), val))
					iterator.remove();
			}
		}
		return l1;
	}

	/**
	 * Dato un set di oggetti di tipo T , torna lo stesso set da cui ha rimosso
	 * quegli oggetti la cui proprietï¿½ prop in dot notation NON ï¿½ uguale al
	 * valore val
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return Set<T> torna lo stesso Set da cui ho rimosso gli elementi della
	 *         proprieta props in dot notation che NON hanno valore val
	 * @throws Exception
	 */
	public <T> Set<T> cleanSetNotEqual(Set<T> l1, String props, Object val) throws Exception {
		if (notNull(l1)) {
			for (Iterator<T> iterator = l1.iterator(); iterator.hasNext();) {
				T t = iterator.next();
				if (isNot(get(t, props), val))
					iterator.remove();
			}
		}
		return l1;
	}

	/**
	 * Dato un set di oggetti di tipo T , torna lo stesso set da cui ha rimosso
	 * quegli oggetti la cui proprietï¿½ prop in dot notation NON ï¿½ uguale al
	 * valore val
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return Set<T> torna lo stesso Set da cui ho rimosso gli elementi delle
	 *         proprieta props in dot notation che NON hanno valori val
	 * @throws Exception
	 */

	public <T> Set<T> cleanSetNotEqual(Set<T> l1, String[] props, Object[] val) throws Exception {
		for (int i = 0; i < props.length; i++) {
			l1 = cleanSetNotEqual(l1, props[i], val[i]);
		}
		return l1;
	}

	/**
	 * Utente String nome Integer eta Date dataNascita
	 * 
	 * Da Lista<Utente> con props=dataNascita ottengo una nuova Lista<Date>
	 * 
	 * @param l1
	 * @param props
	 * @return List<R>
	 * 
	 * 
	 * @throws Exception
	 */
	public <T, R> List<R> listFromListBean(List<T> l1, String props) throws Exception {
		List<R> inters = new ArrayList<R>();
		for (T t : safe(l1)) {
			inters.add((R) get(t, props));
		}
		return inters;
	}

	/**
	 * Utente String nome Integer eta Date dataNascita
	 * 
	 * Da Lista<Utente> con props=dataNascita ottengo una nuova Lista<Date> di
	 * elementi distinti
	 * 
	 * @param l1
	 * @param props
	 * @return List<R>
	 * 
	 * 
	 * @throws Exception
	 */
	public <T, R> List<R> listDistinctFromListBean(List<T> l1, String props) throws Exception {
		List<R> inters = new ArrayList<R>();
		for (T t : safe(l1)) {
			R o = (R) get(t, props);
			if (!inters.contains(o))
				inters.add(o);
		}
		return inters;
	}

	private Object getProp(Object bean, String prop) throws Exception {
		Object o = null;
		if (notNull(bean)) {
			Method[] methods = bean.getClass().getMethods();
			boolean trovato = false;
			for (Method method : methods) {
				method.setAccessible(true);
				if (is(method.getName(), getString(GET, prop), getString(IS, prop))) {
					try {
						trovato = true;
						o = method.invoke(bean);
					} catch (Exception e) {
					}
					break;
				}
			}
			if (!trovato)
				throw new Exception(getString("Metodo accessor per ", prop, " mancante"));
		}
		return o;
	}

	private Object getPropOrElse(Object bean, String prop, Object defaultVal, String goneDefault) throws Exception {
		Object o = getProp(bean, prop);
		goneDefault = Null(o) ? S : goneDefault;
		return valIfNull(o, defaultVal);
	}

	/**
	 * Dato un array torna una lista
	 * 
	 * @param a
	 * @return PList<T>
	 */
	public <T> PList<T> arrayToList(T[] a) {
		if (null == a)
			return null;
		PList<T> c = pl();
		for (T o : a) {
			c.add(o);
		}
		return c;
	}

	/**
	 * Data una lista ottengo un set
	 * 
	 * @param a
	 * @return Set<T>
	 */
	public <T> Set<T> listToSet(List<T> a) {
		if (null == a)
			return null;
		else
			return new HashSet<T>(a);
	}

	/**
	 * Dato un set ottengo una lista
	 * 
	 * @param a
	 * @return PList<T>
	 */
	public <T> PList<T> setToList(Set<T> a) {
		if (null == a)
			return null;
		else
			return toPList(new ArrayList<T>(a));
	}

	/**
	 * Data una lista List<Utente> recupero quell'unico Utente che abbia la
	 * proprieta campo (in dot notation) uguale a value
	 * 
	 * @param lista
	 * @param campo
	 * @param value
	 * @return K
	 * @throws Exception
	 */
	public <K, T> K findOne(List<K> lista, String campo, T value) throws Exception {
		return findOne(lista, new String[] { campo }, new Object[] { value });
	}

	/**
	 * Data una lista di bean torna il bean primo della lista
	 * 
	 * @param l
	 * @return K
	 */
	public <K> K getFirstElement(List<K> l) {
		if (notNull(l))
			return l.get(0);
		else
			return null;
	}

	/**
	 * Data una lista List<Utente> recupero quell'unico Utente che abbia la
	 * proprieta campo[i] (in dot notation) uguale a value[i]
	 * 
	 * @param lista
	 * @param campo
	 * @param value
	 * @return K
	 * @throws Exception
	 */
	public <K, T> K findOne(List<K> lista, String[] campo, T[] value) throws Exception {
		return getFirstElement(find(lista, campo, value));
	}

	/**
	 * Data una lista List<Utente> recupero la sottolista di elementi Utente
	 * (quindi piï¿½ di uno) che abbiano la proprieta campo[i] (in dot notation)
	 * uguale a value[i]
	 * 
	 * @param lista
	 * @param campo
	 * @param value
	 * @return List<K>
	 * @throws Exception
	 */
	public <K, T> List<K> find(List<K> lista, String[] campo, T[] value) throws Exception {
		List<K> shortList = lista;
		for (int i = 0; i < campo.length; i++) {
			shortList = find(shortList, campo[i], value[i]);
		}
		return shortList;
	}

	private boolean isEqLike(Object campo, Object... value) {
		boolean ret = false;
		if (campo instanceof Collection) {
			ret = collectionLike((Collection) campo, value);
		} else {
			ret = isObj(campo, value);
		}
		if (campo instanceof String) {
			if (notNull(value)) {
				ret = like((String) campo, Arrays.asList(value).toArray(new String[value.length]));
			}
		}
		if (campo instanceof BigDecimal) {
			if (notNull(value)) {
				ret = isEscludiZeriDecimali((BigDecimal) campo, Arrays.asList(value).toArray(new BigDecimal[value.length]));
			}
		}
		return ret;
	}

	/**
	 * Torna true se campo ï¿½ in like ad almeno uno dei %value%
	 * 
	 * @param campo
	 * @param value
	 * @return boolean
	 */
	public boolean isLike(Object campo, Object... value) {
		return almenoUna(tutte(Null(campo), NullOR(value)), isEqLike(campo, value));
	}

	/**
	 * Data una lista List<Utente> ottengo una nuova lista di elementi Utente
	 * che abbiano la proprieta campo (in dot notation) uguale a value
	 * 
	 * @param lista
	 * 
	 * @param campo
	 * 
	 * @param value
	 * 
	 * @return List<K>
	 * 
	 * @throws Exception
	 */
	public <K, T> List<K> find(List<K> lista, String campo, T value) throws Exception {
		if (value instanceof Operatore) {
			return find(lista, campo, (Operatore) value, (Object[]) null);
		}
		List<K> shortList = new ArrayList<K>();
		for (K k : safe(lista)) {
			if (is(get(k, campo), value)) {
				shortList.add(k);
			}
		}
		return shortList;
	}

	private <K> String beanToString(K bean, Integer... i) {
		Integer c = 1;
		if (null != i && i.length == 1) {
			c = i[0] + 1;
		}
		if (Null(bean))
			return null;
		StringBuffer sb = new StringBuffer();
		boolean toString = false;
		Method[] declaredMethods = bean.getClass().getDeclaredMethods();
		for (Method method : declaredMethods) {
			if (is(method.getName(), TO_STRING)) {
				toString = true;
				break;
			}
		}
		String className = bean.getClass().getName();
		if (!toString) {
			String nome = getString(OPEN_GRAFFA, className, OPEN_QUADRA, lf());
			sb.append(nome);
			for (Method method : declaredMethods) {
				method.setAccessible(true);
				if (like(method.getDeclaringClass().getName(), "java.", "oracle."))
					continue;

				if (almenoUna(method.getName().startsWith(GET), method.getName().startsWith(IS))) {
					if (method.getName().startsWith(GET)) {
						nome = substring(method.getName(), GET, false, false, null, false, false);
					}
					if (method.getName().startsWith(IS)) {
						nome = substring(method.getName(), IS, false, false, null, false, false);
					}
					try {
						Object valore = method.invoke(bean);
						String s = null;
						if (notNull(valore)) {
							if (valore instanceof Date) {
								s = dateToString((Date) valore);
							} else {
								if (valore instanceof Collection) {
									s = listToString((Collection) valore, c);
								} else if (!isJDKClass(valore)) {
									s = beanToString(valore, c);
								} else {
									s = valore.toString();
								}
							}
						}
						sb.append(str(tabn(c), nome, EQUAL, s, lf()));
					} catch (Exception e) {
					}
				}
			}
			sb.append(str(tabn(c))).append(CLOSE_QUADRA_GRAFFA).append(lf());
		} else {
			if (bean instanceof Date) {
				sb.append(dateToString((Date) bean));
			} else {
				sb.append(bean.toString());
			}
		}
		return sb.toString();
	}

	private <K> String beanEntityToString(K bean, Integer... i) {
		Integer c = 1;
		if (null != i && i.length == 1) {
			c = i[0] + 1;
		}
		if (Null(bean))
			return null;
		StringBuffer sb = new StringBuffer();
		boolean toString = false;
		Method[] declaredMethods = bean.getClass().getDeclaredMethods();
		Field[] declaredAttr = bean.getClass().getDeclaredFields();
		PList<String> campiAnnotati = pl();
		for (Field fd : declaredAttr) {
			if (notNull(fd.getAnnotation(Column.class))) {
				campiAnnotati.add(fd.getName());
			}
		}
		for (Method method : declaredMethods) {
			if (is(method.getName(), TO_STRING)) {
				toString = true;
				break;
			}
		}
		String className = bean.getClass().getName();
		if (!toString) {
			String nome = getString(OPEN_GRAFFA, className, OPEN_QUADRA, lf());
			sb.append(nome);
			for (Method method : declaredMethods) {
				String name = method.getName();
				if (almenoUna(name.startsWith(IS), name.startsWith(GET))) {
					if (name.startsWith(IS)) {
						String nomeAtt = decapFirstLetter(substring(name, IS, false, false, null, false, false));
						if (!is(campiAnnotati, nomeAtt)) {
							continue;
						}
					}

					if (name.startsWith(GET)) {
						String nomeAtt = decapFirstLetter(substring(name, GET, false, false, null, false, false));
						if (!is(campiAnnotati, nomeAtt)) {
							continue;
						}
					}

				}
				method.setAccessible(true);
				if (like(method.getDeclaringClass().getName(), "java.", "oracle."))
					continue;

				if (almenoUna(method.getName().startsWith(GET), method.getName().startsWith(IS))) {
					if (method.getName().startsWith(GET)) {
						nome = substring(method.getName(), GET, false, false, null, false, false);
					}
					if (method.getName().startsWith(IS)) {
						nome = substring(method.getName(), IS, false, false, null, false, false);
					}
					try {
						Object valore = method.invoke(bean);
						String s = null;
						if (notNull(valore)) {
							if (valore instanceof Date) {
								s = dateToString((Date) valore);
							} else {
								if (valore instanceof Collection) {
									s = listToString((Collection) valore, c);
								} else if (!isJDKClass(valore)) {
									s = beanToString(valore, c);
								} else {
									s = valore.toString();
								}
							}
						}
						sb.append(str(tabn(c), nome, EQUAL, s, lf()));
					} catch (Exception e) {
					}
				}
			}
			sb.append(str(tabn(c))).append(CLOSE_QUADRA_GRAFFA).append(lf());
		} else {
			if (bean instanceof Date) {
				sb.append(dateToString((Date) bean));
			} else {
				sb.append(bean.toString());
			}
		}
		return sb.toString();
	}

	/**
	 * Ritorna la stringa tabulatore
	 * 
	 * @return String
	 */
	public String tab() {
		return TAB;
	}

	/**
	 * Ritorna la stringa line feed
	 * 
	 * @return String
	 */
	protected String lf() {
		return LF;
	}

	/**
	 * Ritorna la stringa formata da una sequenza di n tabulatori
	 * 
	 * @return String
	 */
	public String tabn(Integer n) {
		String s = "";
		for (int i = 1; i <= n; i++) {
			s = str(s, tab());
		}
		return s;
	}

	/**
	 * Ritorna la stringa formata da una sequenza di n line feed
	 * 
	 * @return String
	 */
	public String lfn(Integer n) {
		String s = "";
		for (int i = 1; i <= n; i++) {
			s = str(s, lf());
		}
		return s;
	}

	private <T> boolean isJDKClass(T t) {
		if (Null(t.getClass().getPackage()))
			return true;
		return t.getClass().getPackage().getName().startsWith("java");
	}

	private boolean isJDKClass2(Class c) {
		if (Null(c.getPackage()))
			return true;
		return c.getPackage().getName().startsWith("java");
	}

	private boolean isPilotClass(Class t) {
		return is(t.getPackage().getName(), "it.eng.pilot");
	}

	private boolean isPilotJDKClass(Class t) {
		return isJDKClass2(t) || isPilotClass(t);
	}

	/**
	 * Data una lista di bean di tipo K calcolo la media aritmetica dei valori
	 * della proprietï¿½ campo (di tipo numerico) di tutti gli elementi della
	 * lista
	 * 
	 * @param lista
	 * @param campo
	 * @param c
	 * @return T
	 * @throws Exception
	 */
	public <K, T> T media(List<K> lista, String campo, Class<T> c) throws Exception {
		T sommatoria = sommatoria(lista, campo, c);
		if (sommatoria instanceof BigDecimal) {
			return (T) dividi((BigDecimal) sommatoria, getBigDecimal(lista.size()));
		}
		if (sommatoria instanceof Double) {
			return (T) new Double(((Double) sommatoria / new Double(lista.size())));
		}
		if (sommatoria instanceof Integer) {
			return (T) new Double(((Integer) sommatoria / new Integer(lista.size())));
		}
		if (sommatoria instanceof Short) {
			return (T) new Double(((Short) sommatoria / new Short((short) lista.size())));
		}
		if (sommatoria instanceof Long) {
			return (T) new Double(((Long) sommatoria / new Long(lista.size())));
		}
		return null;
	}

	/**
	 * Data una lista di tipi java K calcolo la media aritmetica(di tipo
	 * numerico) di tutti gli elementi della lista
	 * 
	 * @param <K>
	 * @param <T>
	 * @param lista
	 * @param c
	 * @return T
	 * @throws Exception
	 */
	public <K, T> T media(List<K> lista, Class<T> c) throws Exception {
		return media(lista, null, c);
	}

	/**
	 * Data una lista di bean di tipo K calcolo la sommatoria dei valori della
	 * proprietï¿½ campo (di tipo numerico) di tutti gli elementi della lista
	 * 
	 * @param <K>
	 * @param <T>
	 * @param lista
	 * @param campo
	 * @param c
	 * @return T
	 * @throws Exception
	 */
	public <K, T> T sommatoria(List<K> lista, String campo, Class<T> c) throws Exception {
		Object sommatoria = null;
		if (is(c.getName(), JAVA_BIG_DECIMAL)) {
			sommatoria = ZERO;
		}
		if (is(c.getName(), JAVA_DOUBLE)) {
			sommatoria = 0d;
		}
		if (is(c.getName(), JAVA_FLOAT)) {
			sommatoria = 0f;
		}
		if (is(c.getName(), JAVA_LONG)) {
			sommatoria = 0l;
		}
		if (is(c.getName(), JAVA_INTEGER)) {
			sommatoria = 0;
		}
		if (is(c.getName(), JAVA_SHORT)) {
			sommatoria = new Integer(0).shortValue();
		}

		if (Null(campo)) {
			for (K k : safe(lista)) {
				if (k instanceof BigDecimal) {
					sommatoria = (T) aggiungi((BigDecimal) sommatoria, (BigDecimal) k);
				} else if (k instanceof Double) {
					sommatoria = (Double) sommatoria + (Double) k;
				} else if (k instanceof Float) {
					sommatoria = (Float) sommatoria + (Float) k;
				} else if (k instanceof Integer) {
					sommatoria = (Integer) sommatoria + (Integer) k;
				} else if (k instanceof Long) {
					sommatoria = (Long) sommatoria + (Long) k;
				} else if (k instanceof Short) {
					sommatoria = new Integer((Short) sommatoria + (Short) k).shortValue();
				}

			}
			return (T) sommatoria;
		}

		for (K k : safe(lista)) {
			Object valore = get(k, campo);
			if (valore instanceof BigDecimal) {
				sommatoria = (T) aggiungi((BigDecimal) sommatoria, (BigDecimal) valore);
			} else if (valore instanceof Short) {
				sommatoria = new Integer((Short) sommatoria + (Short) valore).shortValue();
			} else if (valore instanceof Double) {
				sommatoria = (Double) sommatoria + (Double) valore;
			} else if (valore instanceof Float) {
				sommatoria = (Float) sommatoria + (Float) valore;
			} else if (valore instanceof Integer) {
				sommatoria = (Integer) sommatoria + (Integer) valore;
			} else if (valore instanceof Long) {
				sommatoria = (Long) sommatoria + (Long) valore;
			}
		}
		return (T) sommatoria;
	}

	/**
	 * Data una lista di tipi java K calcolo la sommatoria dei valori di tutti
	 * gli elementi della lista
	 * 
	 * @param <K>
	 * @param <T>
	 * @param lista
	 * @param c
	 * @return T
	 * @throws Exception
	 */

	public <K, T> T sommatoria(List<K> lista, Class<T> c) throws Exception {
		return sommatoria(lista, null, c);
	}

	private Method findMethod(Method[] metodi, String nome) {
		Method met = null;
		for (Method m : metodi) {
			if (is(m.getName(), nome)) {
				met = m;
				break;
			}
		}
		return met;
	}

	private void invokeSetter(Object o, Object id, Field att) throws Exception {
		invokeSetter(o, id, att.getName());
	}

	private void invokeSetter(Object o, Object id, String att) throws Exception {
		PList<String> campi = toListString(att, DOT);
		Object obj = o;
		for (int i = 0; i < campi.size() - 1; i++) {
			obj = invokeGetter(obj, campi.get(i));
		}
		Method[] metodi = obj.getClass().getDeclaredMethods();
		Method metodo = findMethod(metodi, str(SET, campi.getLastElement()));
		if (notNull(metodo)) {
			metodo.invoke(obj, id);
		}
	}

	/**
	 * Data una lista di elementi di tipo K raggruppo la lista secondo la
	 * proprieta campo del bean K e ottengo una Mappa chiave-valore dove chiave
	 * ï¿½ il valore della proprieta e valore ï¿½ una lista di bean K la cui
	 * proprieta campo ha quel valore.
	 * 
	 * @param lista
	 * @param campo
	 * @return Map<T, List<K>>
	 * @throws Exception
	 */
	public <K, T> Map<T, List<K>> groupBy(List<K> lista, String campo) throws Exception {
		Map<T, List<K>> mappa = new HashMap<T, List<K>>();
		for (K k : safe(lista)) {
			aggiungiMappaLista(mappa, (T) get(k, campo), k);
		}
		return mappa;
	}

	/**
	 * Serve a recuperare i valori distinti multicampo tramite campi... indicati
	 * come parametro. Ritorna una PList<String> dove ogni elemento ï¿½ nel
	 * formato [nomeCampo]=[valoreCampo
	 * ]|[nomeCampo_1]=[valoreCampo_1]|...|[nomeCampo_n]=[valoreCampo_n]
	 * 
	 * Campi ï¿½ l'elenco dei campi del tipo K di cui si vogliono i valori
	 * distinti concatenati insieme in una multipla. Equivale in pratica a una
	 * select distinct sql
	 * 
	 * @param <K>
	 * @param lista
	 * @param campi
	 * @return PList<String>
	 */
	public <K> PList<String> distinctMulti(PList<K> lista, String... campi) {
		if (null == campi)
			return plstr();
		Set<String> dist = new HashSet<String>();
		String s = "";
		for (K k : safe(lista)) {
			s = "";
			for (String campo : campi) {
				try {
					s = str(s, campo, EQUAL, invokeGetter(k, campo), PIPE);
				} catch (Exception e) {
					logError("L'attributo", campo, "non ha metodo accessor");
				}
			}
			dist.add(cutLast(s));
		}
		return pl(setToList(dist));

	}

	/**
	 * Data una lista di bean K ottengo una lista di elementi distinti di tipo T
	 * dove T ï¿½ il tipo della proprieta campo Utente nome cognome eta
	 * List<Utente> ottengo una List<Integer> di eta distinte degli utenti
	 * 
	 * @param lista
	 * @param campo
	 * @return PList<T>
	 */
	public <K, T> PList<T> distinct(List<K> lista, String campo) {
		Method[] methods = null;
		Set<T> distinti = new HashSet<T>();
		Integer conta = 0;
		Method met = null;
		for (K k : safe(lista)) {
			if (null == methods) {
				methods = k.getClass().getMethods();
			}
			if (Null(met)) {
				for (Method method : methods) {
					method.setAccessible(true);
					if (is(method.getName(), getString(GET, campo), getString(IS, campo))) {
						try {
							met = method;
							break;
						} catch (Exception e) {
						}
					}
				}
			}
			if (notNull(met)) {
				try {
					conta++;
					distinti.add((T) met.invoke(k));
				} catch (Exception e) {
				}
			} else {
				logError("Il campo", campo, "non presenta alcun metodo accessor.");
				return null;
			}
		}
		List<T> ll = new ArrayList<T>(distinti);
		PList<T> listaOrd = new PArrayList<T>();
		boolean nullPresent = cleanNullList(ll);
		if (nullPresent) {
			listaOrd.add(null);
		}
		listaOrd.addAll(ll);
		return listaOrd;
	}

	/**
	 * Ritorna quanti elementi distinti della proprietï¿½ campo ci sono in una
	 * lista di bean di tipo T
	 * 
	 * @param <T>
	 * @param lista
	 * @param campo
	 * @return Integer
	 */
	public <T> Integer countDistinct(List<T> lista, String campo) {
		return safe(distinct(lista, campo)).size();
	}

	/**
	 * Data una mappa<K,T> ottengo l'unico elemento T corrispondente alla chiave
	 * K
	 * 
	 * @param mappa
	 * @param chiave
	 * @return T
	 * @throws Exception
	 */
	public <K, T> T findOne(Map<K, T> mappa, K chiave) throws Exception {
		return getFirstElement(find(mappa, chiave));
	}

	/**
	 * Data una mappa<K,T> ottengo una lista di elementi T corrispondenti alla
	 * chiave K
	 * 
	 * @param mappa
	 * @param chiave
	 * @return List<T>
	 * @throws Exception
	 */

	public <K, T> List<T> find(Map<K, T> mappa, K chiave) throws Exception {
		List<T> valore = new ArrayList<T>();
		for (Map.Entry<K, T> entry : mappa.entrySet()) {
			if (is(entry.getKey(), chiave)) {
				valore.addAll((List<T>) entry.getValue());
			}
		}
		return valore;
	}

	/**
	 * Trasforma un array in un set
	 * 
	 * @param a
	 * @return Set<T>
	 */

	public <T> Set<T> arrayToSet(T[] a) {
		if (null == a)
			return null;
		Set<T> c = new HashSet<T>();
		for (T o : a) {
			c.add(o);
		}
		return c;
	}

	/**
	 * Data una data torna una stringa in formato dd/mm/yyyy del primo giorno
	 * del mese successivo alla data
	 * 
	 * @param d
	 * @return String
	 */
	public String primoGiornoDelMeseSuccessivo(Date d) {
		Calendar today = Calendar.getInstance();
		today.setTime(d);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, today.get(Calendar.YEAR));
		c.set(Calendar.MONTH, today.get(Calendar.MONTH) + 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return dateToString(c.getTime());
	}

	/**
	 * Data una data torna una stringa in formato dd/mm/yyyy del primo giorno
	 * del mese
	 * 
	 * @param d
	 * @return String
	 */

	public String primoGiornoDelMese(Date d) {
		Calendar today = Calendar.getInstance();
		today.setTime(d);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, today.get(Calendar.YEAR));
		c.set(Calendar.MONTH, today.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, 1);
		return dateToString(c.getTime());
	}

	/**
	 * Data una data torna ritorna la data impostata al primo giorno del mese
	 * della data
	 * 
	 * @param d
	 * @return String
	 */

	public Date primoGiornoDelMeseDate(Date d) {
		Calendar today = Calendar.getInstance();
		today.setTime(d);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, today.get(Calendar.YEAR));
		c.set(Calendar.MONTH, today.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * Data una data ne torna la rappresentazione stringa in formato dd/mm/yyyy
	 * 
	 * @param d
	 * @return String
	 */
	public String dateToString(Date d) {
		String data = null;
		if (notNull(d)) {
			data = sdf.format(d);
		}
		return data;
	}

	/**
	 * Data una data ne torna la rappresentazione stringa in formato format
	 * passato come parametro
	 * 
	 * @param d
	 * @return String
	 */
	public String dateToString(Date d, String format) {
		String data = null;
		if (notNull(d)) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			data = sdf.format(d);
		}
		return data;
	}

	/**
	 * Data una data ne torna la rappresentazione stringa dd/mm/yyyy hh:mm:ss
	 * 
	 * @param d
	 * @return String
	 */
	public String dateToStringhhmmss(Date d) {
		String data = null;
		if (notNull(d)) {
			data = sdfhhmmss.format(d);
		}
		return data;
	}

	/**
	 * Dati due valori b1 e b2 indica la percentuale di b1 rispetto a b2
	 * 
	 * @param b1
	 * @param b2
	 * @return BigDecimal
	 */
	public BigDecimal percentuale(BigDecimal b1, BigDecimal b2) {
		if (zero(b2))
			return ZERO;
		if (NullOR(b1, b2))
			return null;
		return setScale(moltiplica(dividi(b1, b2), CENTO));
	}

	/**
	 * Data un numero e un valore di percentuale ottengo il numero
	 * corrispondente a quella percentuale
	 * 
	 * @param numero
	 * @param percentuale
	 * @return BigDecimal
	 */
	public BigDecimal percentualeToValore(BigDecimal numero, BigDecimal percentuale) {
		return dividi(moltiplica(numero, percentuale), CENTO);
	}

	/**
	 * Data una rappresentazione stringa dd/mm/yyyy ottengo una data
	 * 
	 * @param d
	 * @return Date
	 */
	public Date stringToDate(String d) {
		Date data = null;
		if (notNull(d)) {
			try {
				sdf.setLenient(false);
				data = sdf.parse(d);
			} catch (ParseException e) {
			}
		}
		return data;
	}

	/**
	 * Ritorna l'oggetto data corrispondente alla stringa d secondo il formato
	 * format passato
	 * 
	 * @param d
	 * @param format
	 * @return Date
	 */
	public Date stringToDate(String d, String format) {
		Date data = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (notNull(d)) {
			try {
				sdf.setLenient(false);
				data = sdf.parse(d);
			} catch (ParseException e) {
			}
		}
		return data;
	}

	/**
	 * Data una rappresentazione stringa dd/mm/yyyy hh:mm:ss ottengo una data
	 * 
	 * @param d
	 * @return Date
	 */
	public Date stringToDatehhmmss(String d) {
		Date data = null;
		if (notNull(d)) {
			try {
				sdfhhmmss.setLenient(false);
				data = sdfhhmmss.parse(d);
			} catch (ParseException e) {
			}
		}
		return data;
	}

	/**
	 * Dalla stringa 201509 ottengo l'anno ossia 2015
	 * 
	 * @param aaaamm
	 * @return String
	 */
	public String getYearFromAAAAMM(String aaaamm) {
		String year = "";
		try {
			if (notNull(aaaamm))
				year = aaaamm.substring(0, 4);
		} catch (Exception e) {
		}
		return year;
	}

	/**
	 * Dalla stringa 201509 ottengo 09
	 * 
	 * @param aaaamm
	 * @return String
	 */
	public String getMonthFromAAAAMM(String aaaamm) {
		String month = "";
		try {
			if (notNull(aaaamm))
				month = aaaamm.substring(4, 6);
		} catch (Exception e) {
		}
		return month;
	}

	/**
	 * Dalla stringa 201509 ottendo 2015 settembre
	 * 
	 * @param aaaamm
	 * @return String
	 */

	public String decodeAAAAMM(String aaaamm) {
		String decoded = "";
		String anno = "";
		String mese = "";
		try {
			if (notNull(aaaamm)) {
				anno = getYearFromAAAAMM(aaaamm);
				mese = getMonthFromAAAAMM(aaaamm);
				decoded = getString(anno, SPACE, decodeMonth(mese));
			}
		} catch (Exception e) {
		}
		return decoded;
	}

	/**
	 * Dalla stringa 201509 ottengo settembre 2015
	 * 
	 * @param aaaamm
	 * @return String
	 */
	public String decodeAAAAMMPrimaIlMese(String aaaamm) {
		String decoded = "";
		String anno = "";
		String mese = "";
		try {
			if (notNull(aaaamm)) {
				anno = getYearFromAAAAMM(aaaamm);
				mese = getMonthFromAAAAMM(aaaamm);
				decoded = getString(decodeMonth(mese), SPACE, anno);
			}
		} catch (Exception e) {
		}
		return decoded;
	}

	/**
	 * Dato il valore numerico del mese ottengo la descrizione letterale del
	 * mese dove 1 ï¿½ gennaio e 12 ï¿½ dicembre
	 * 
	 * @param mese
	 * @return String
	 */
	public String decodeMonth(String mese) {
		if (notNull(mese) && mese.startsWith("0"))
			mese = mese.substring(1);
		return switchCase(mese, null, "1|GENNAIO", "2|FEBBRAIO", "3|MARZO", "4|APRILE", "5|MAGGIO", "6|GIUGNO", "7|LUGLIO", "8|AGOSTO", "9|SETTEMBRE", "10|OTTOBRE", "11|NOVEMBRE", "12|DICEMBRE");
	}

	/**
	 * Metodo di realizzazione di una mappatura del tipo voglio che ai valori 2
	 * e 3 corrisponda la stringa "DueOTre" e al valore 5 corrisponda la stringa
	 * "cinque" allora valueToCheck poniamo ad esempio ï¿½ 3, defaultValue ï¿½
	 * il valore che torno se non ho corrispondenza valuePipeSeparatorReturn
	 * sarï¿½ 2,3|DueoTre e 5|cinque. Con value to check 3 ottengo "DueOTre",
	 * con valueToCheck 5 ottengo "cinque", con valueToCheck 12 ottengo il
	 * defaultValue
	 * 
	 * @param valueToCheck
	 * @param defaultValue
	 * @param valuePipeSeparatorReturn
	 * @return String
	 */
	public String switchCase(String valueToCheck, String defaultValue, String... valuePipeSeparatorReturn) {
		// 2,3|DueOTre 5|CINQUE
		try {
			for (String coppia : valuePipeSeparatorReturn) {
				StringTokenizer st = new StringTokenizer(coppia, PIPE);
				String v1 = st.nextToken();
				String v2 = st.nextToken();
				if (v1.indexOf(COMMA) > -1) {
					String[] vals = v1.split(COMMA);
					if (is(valueToCheck, vals))
						return v2;
				} else {
					if (is(valueToCheck, v1))
						return v2;
				}
			}
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * Metodo di realizzazione di una mappatura condizionale del tipo voglio che
	 * ai valori 2 e 3 corrisponda la stringa DueOTre in base a una certa
	 * condizione e al valore 5 corrisponda la stringa cinque in base a un'altra
	 * condizione. valueToCheck ï¿½ il valore da sottoporre a traduzione secondo
	 * la mappatura, defaultValue ï¿½ il valore che torno se non ho
	 * corrispondenza. La mappauta condizionale avviene utilizzando la classe
	 * CaseCondition. Se la condizione ï¿½ false allora torno il valore
	 * originale senza mapparlo nel nuovo valore
	 * 
	 * @param valueToCheck
	 * @param defaultValue
	 * @param cc
	 * @return String
	 */
	public String switchCase(String valueToCheck, String defaultValue, CaseCondition... cc) {
		// 2,3|DueOTre 5|CINQUE
		try {
			for (CaseCondition _cc : cc) {
				StringTokenizer st = new StringTokenizer(_cc.getValore(), PIPE);
				String v1 = st.nextToken();
				String v2 = st.nextToken();
				if (v1.indexOf(COMMA) > -1) {
					String[] vals = v1.split(COMMA);
					if (tutte(is(valueToCheck, vals), _cc.isCondizione()))
						return v2;
					if (tutte(is(valueToCheck, vals), !_cc.isCondizione()))
						return valueToCheck;
				} else {
					if (tutte(is(valueToCheck, v1), _cc.isCondizione()))
						return v2;
					if (tutte(is(valueToCheck, v1), !_cc.isCondizione()))
						return valueToCheck;
				}
			}
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * Data una stringa torna true se value ï¿½ contenuto ignorecase nella
	 * stringa
	 * 
	 * @param campo
	 * @param value
	 * @return boolean
	 */
	private boolean like(String campo, String value) {
		return notNull(campo, value) && campo.toLowerCase().contains(value.toLowerCase());
	}

	/**
	 * Data una stringa campo torna true se almeno uno dei value ï¿½ contenuto
	 * ignorecase in campo
	 * 
	 * @param campo
	 * @param value
	 * @return boolean
	 */
	private boolean like(String campo, String... value) {
		boolean esito = false;
		if (Null(campo))
			return false;
		for (String el : value) {
			esito = almenoUna(esito, like(campo, el));
		}
		return esito;
	}

	/**
	 * Data una stringa con spazi multipli all'interno, la trasforma in una
	 * stringa con spazi singoli
	 * 
	 * @param s
	 * @return String
	 */
	public String despaceString(String s) {
		return notNull(s) ? s.replaceAll("[\\s\\u00A0]+", SPACE) : s;
	}

	/**
	 * Torna true se d ï¿½ maggiore o uguale a 0
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean maggioreUgualeZero(Long d) {
		return maggioreUgualeDi(d, 0l);
	}

	/**
	 * Torna true se d ï¿½ maggiore di 0
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean maggioreZero(Long d) {
		return maggioreDi(d, 0l);
	}

	/**
	 * Torna true se d ï¿½ minore o uguale a 0
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean minoreUgualeZero(Long d) {
		return minoreUgualeDi(d, 0l);
	}

	/**
	 * Torna true se d ï¿½ minore di 0
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean minoreZero(Long d) {
		return minoreDi(d, 0l);
	}

	/**
	 * Data una data torna il Timestamp di quella data
	 * 
	 * @param d
	 * @return Timestamp
	 */
	public <K extends Date> Timestamp getTimestamp(K d) {
		return notNull(d) ? new Timestamp(d.getTime()) : null;
	}

	/**
	 * Data un java.util.Date torna un java.sql.Date
	 * 
	 * @param d
	 * @return sql.Date
	 */
	public java.sql.Date getSQLDate(Date d) {
		return notNull(d) ? new java.sql.Date(d.getTime()) : null;
	}

	/**
	 * Dati due valori val e limite, limita val ad assumere come valore massimo
	 * limite e non oltre
	 * 
	 * @param <K>
	 * @param val
	 * @param limite
	 * @return K
	 */
	public <K extends Comparable<K>> K limiteSuperiore(K val, K limite) {
		return maggioreDi(val, limite) ? limite : val;
	}

	/**
	 * Limita val all'intervallo [start,end]
	 * 
	 * @param <K>
	 * @param val
	 * @param start
	 * @param end
	 * @return K
	 */
	public <K extends Comparable<K>> K between(K val, K start, K end) {
		return limiteSuperiore(limiteInferiore(val, start), end);

	}

	/**
	 * Dati due valori val e limite, limita val ad assumere come valore minimo
	 * limite e non al di sotto
	 * 
	 * @param val
	 * @param limite
	 * @return K
	 */
	public <K extends Comparable<K>> K limiteInferiore(K val, K limite) {
		return minoreDi(val, limite) ? limite : val;
	}

	/**
	 * Torna true se i due BigDecimal sono uguali
	 * 
	 * @param d
	 * @param d1
	 * @return boolean
	 */
	public boolean uguale(BigDecimal d, BigDecimal d1) {
		return is(d, d1);
	}

	/**
	 * Torna true se i due BigDecimal sono diversi
	 * 
	 * @param d
	 * @param d1
	 * @return boolean
	 */
	public boolean diverso(BigDecimal d, BigDecimal d1) {
		return isNot(d, d1);
	}

	/**
	 * Torna true se d ï¿½ maggiore di zero
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean maggioreZero(BigDecimal d) {
		return notNull(d) && d.compareTo(ZERO) > 0;
	}

	/**
	 * Torna true se d ï¿½ minore di zero
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean minoreZero(BigDecimal d) {
		return notNull(d) && d.compareTo(ZERO) < 0;
	}

	/**
	 * Torna true se d ï¿½ maggiore o uguale a zero
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean maggioreUgualeZero(BigDecimal d) {
		return notNull(d) && d.compareTo(ZERO) >= 0;
	}

	/**
	 * Torna true se d ï¿½ minore o uguale a zero
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean minoreUgualeZero(BigDecimal d) {
		return notNull(d) && d.compareTo(ZERO) <= 0;
	}

	/**
	 * Torna true se d ï¿½ zero
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean zero(BigDecimal d) {
		return notNull(d) && d.compareTo(ZERO) == 0;
	}

	/**
	 * Torna true se d ï¿½ zero
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean zero(Long d) {
		return notNull(d) && d.longValue() == 0l;
	}

	/**
	 * Torna true se d ï¿½ zero
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean zero(Integer d) {
		return notNull(d) && d.intValue() == 0;
	}

	/**
	 * Data una data e un numero intero torna una nuova data che ï¿½ la
	 * precedente meno il numero di mesi indicato
	 * 
	 * @param d
	 * @param numMonts
	 * @return Date
	 */
	public Date subtractMonthsToDate(Date d, int numMonts) {
		if (notNull(d)) {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.MONTH, -numMonts);
			return c.getTime();
		}
		return null;
	}

	/**
	 * Data una data ne torna la rappresentazione in formato stringa yyyyMM
	 * 
	 * @param d
	 * @return String
	 */
	public String dateToStringyyyyMM(Date d) {
		String s = null;
		if (notNull(d)) {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");
			s = sdf2.format(d);
		}
		return s;
	}

	/**
	 * Torna il numero di mesi tra due date
	 * 
	 * @param startDate
	 * @param endDate
	 * @return int
	 */
	public int monthsBetweenDates(Date startDate, Date endDate) {
		if (notNull(startDate, endDate)) {
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(startDate);
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(endDate);
			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
			return diffMonth;
		} else
			return 0;
	}

	/**
	 * Data una data ritorna l'oggetto Calendar corrispondente in formato
	 * italiano
	 * 
	 * @param date
	 * @return Calendar
	 */
	public Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance(Locale.ITALIAN);
		cal.setTime(date);
		return cal;
	}

	/**
	 * Numero di anni intercorrente tra due date
	 * 
	 * @param first
	 * @param last
	 * @return int
	 */
	public int yearsBetweenDates(Date first, Date last) {
		int diff = 0;
		if (notNull(first, last)) {
			Calendar a = dateToCalendar(first);
			Calendar b = dateToCalendar(last);
			diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
			if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
				diff--;
			}
		}
		return diff;
	}

	/**
	 * Torna la data attuale in Timestamp
	 * 
	 * @return Timestamp
	 */
	public Timestamp nowTS() {
		return new Timestamp(now().getTime());
	}

	/**
	 * Torna la data attuale
	 * 
	 * @return PDate
	 */
	public PDate now() {
		return pd(Calendar.getInstance().getTime());
	}

	/**
	 * Torna la data indietro nel tempo di n settimane dalla data odierna.
	 * 
	 * @param n
	 * @return Date
	 */
	public Date nowLessWeeks(Integer n) {
		return nowGeneral(-n, Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Torna la data indietro nel tempo di n giorni dalla data odierna.
	 * 
	 * @param n
	 * @return Date
	 */
	public Date nowLessDays(Integer n) {
		return nowGeneral(-n, Calendar.DATE);
	}

	/**
	 * Torna la data indietro nel tempo di n mesi dalla data odierna.
	 * 
	 * @param n
	 * @return Date
	 */
	public Date nowLessMonths(Integer n) {
		return nowGeneral(-n, Calendar.MONTH);
	}

	/**
	 * Torna la data indietro nel tempo di n anni dalla data odierna.
	 * 
	 * @param n
	 * @return Date
	 */
	public Date nowLessYears(Integer n) {
		return nowGeneral(-n, Calendar.YEAR);
	}

	/**
	 * Torna la data avanti nel tempo di n settimane dalla data odierna.
	 * 
	 * @param n
	 * @return Date
	 */
	public Date nowAddWeeks(Integer n) {
		return nowGeneral(n, Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Torna la data avanti nel tempo di n giorni dalla data odierna.
	 * 
	 * @param n
	 * @return Date
	 */
	public Date nowAddDays(Integer n) {
		return nowGeneral(n, Calendar.DATE);
	}

	/**
	 * Torna la data avanti nel tempo di n mesi dalla data odierna.
	 * 
	 * @param n
	 * @return Date
	 */
	public Date nowAddMonths(Integer n) {
		return nowGeneral(n, Calendar.MONTH);
	}

	/**
	 * Torna la data avanti nel tempo di n anni dalla data odierna.
	 * 
	 * @param n
	 * @return Date
	 */
	public Date nowAddYears(Integer n) {
		return nowGeneral(n, Calendar.YEAR);
	}

	private Date nowGeneral(Integer n, int type) {
		Calendar c = Calendar.getInstance();
		c.add(type, n);
		return c.getTime();

	}

	/**
	 * Torna la data attuale in java.sql.Date
	 * 
	 * @return sql.Date
	 */
	public java.sql.Date nowSql() {
		return getSQLDate(now());
	}

	/**
	 * Torna true se value ï¿½ A ignorecase
	 * 
	 * @param value
	 * @return boolean
	 */
	public boolean attivo(String value) {
		return is(value, ATTIVO);
	}

	/**
	 * Torna true se value ï¿½ S o SI ignorecase
	 * 
	 * @param value
	 * @return boolean
	 */
	public boolean si(String value) {
		return is(value, SI, S);
	}

	/**
	 * Torna true se value ï¿½ N o NO ignorecase
	 * 
	 * @param value
	 * @return boolean
	 */
	public boolean no(String value) {
		return is(value, NO, N);
	}

	/**
	 * Torna true se data d ï¿½ compresa tra [start, end] intervalli compresi
	 * 
	 * @param d
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public boolean isDateBetween(Date d, Date start, Date end) {
		boolean cond = false;
		if (notNull(d, start, end)) {
			cond = !(d.before(start) || d.after(end));
		} else if (notNull(d, start)) {
			cond = !(d.before(start));
		} else if (notNull(d, end)) {
			cond = !d.after(end);
		}
		return cond;
	}

	/**
	 * Torna true se data d ï¿½ compresa tra ]start,end[ intervalli esclusi
	 * 
	 * @param d
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public boolean isDateBetweenEx(Date d, Date start, Date end) {
		boolean cond = false;
		if (notNull(d, start, end)) {
			cond = tutte(start.before(d), end.after(d));
		} else if (notNull(d, start)) {
			cond = start.before(d);
		} else if (notNull(d, end)) {
			cond = end.after(d);
		}
		return cond;
	}

	/**
	 * Data una data torna una data aggiungendo ad essa il numero di giorni
	 * indicato
	 * 
	 * @param data
	 * @param numeroGiorni
	 * @return Date
	 */
	public Date addDays(Date data, Integer numeroGiorni) {
		Date nuova = null;
		if (notNull(data)) {
			Calendar c = Calendar.getInstance();
			c.setTime(data);
			c.add(Calendar.DATE, numeroGiorni);
			nuova = c.getTime();
		}
		return nuova;
	}

	/**
	 * Data una data torna una data aggiungendo ad essa il numero di ore
	 * indicato
	 * 
	 * 
	 * @param data
	 * @param numeroOre
	 * @return Date
	 */
	public Date addHours(Date data, Integer numeroOre) {
		Date nuova = null;
		if (notNull(data)) {
			Calendar c = Calendar.getInstance();
			c.setTime(data);
			c.add(Calendar.HOUR_OF_DAY, numeroOre);
			nuova = c.getTime();
		}
		return nuova;
	}

	/**
	 * Data una data torna una data aggiungendo ad essa il numero di minuti
	 * indicato
	 * 
	 * 
	 * @param data
	 * @param numeroMinuti
	 * @return Date
	 */
	public Date addMinutes(Date data, Integer numeroMinuti) {
		Date nuova = null;
		if (notNull(data)) {
			Calendar c = Calendar.getInstance();
			c.setTime(data);
			c.add(Calendar.MINUTE, numeroMinuti);
			nuova = c.getTime();
		}
		return nuova;
	}

	/**
	 * Data una data torna una data aggiungendo ad essa il numero di secondi
	 * indicato
	 * 
	 * 
	 * @param data
	 * @param numeroSecondi
	 * @return Date
	 */
	public Date addSeconds(Date data, Integer numeroSecondi) {
		Date nuova = null;
		if (notNull(data)) {
			Calendar c = Calendar.getInstance();
			c.setTime(data);
			c.add(Calendar.SECOND, numeroSecondi);
			nuova = c.getTime();
		}
		return nuova;
	}

	/**
	 * Data una data torna una data aggiungendo ad essa il numero di
	 * millisecondi indicato
	 * 
	 * 
	 * @param data
	 * @param numeroMillisecondi
	 * @return Date
	 */
	public Date addMilliseconds(Date data, Integer numeroMillisecondi) {
		Date nuova = null;
		if (notNull(data)) {
			Calendar c = Calendar.getInstance();
			c.setTime(data);
			c.add(Calendar.MILLISECOND, numeroMillisecondi);
			nuova = c.getTime();
		}
		return nuova;
	}

	/**
	 * Alla data start aggiunge la quantitï¿½ temporale indicata da t e ritorna
	 * la nuova data
	 * 
	 * @param start
	 * @param t
	 * @return Date
	 */
	public Date addTimeToDate(Date start, Time t) {
		if (notNull(start, t)) {
			start = addDays(start, t.getDays().intValue());
			start = addHours(start, t.getHours().intValue());
			start = addMinutes(start, t.getMinutes().intValue());
			start = addSeconds(start, t.getSeconds().intValue());
			start = addMilliseconds(start, t.getMilliseconds().intValue());
		}
		return start;
	}

	/**
	 * Torna true se le date start e end sono temporalmente ordinate
	 * 
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public boolean isOrderedDate(Date start, Date end) {
		boolean ret = true;
		if (notNull(start, end)) {
			ret = !isDateBefore(end, start);
		}
		return ret;
	}

	/**
	 * Torna true se la data d ï¿½ prima della data limite
	 * 
	 * @param d
	 * @param limite
	 * @return boolean
	 */
	public boolean isDateBefore(Date d, Date limite) {
		boolean ret = false;
		if (notNull(d, limite)) {
			ret = d.before(limite);
		}
		return ret;
	}

	/**
	 * Torna true se la data d ï¿½ dopo la data limite
	 * 
	 * @param d
	 * @param limite
	 * @return boolean
	 */
	public boolean isDateAfter(Date d, Date limite) {
		boolean ret = false;
		if (notNull(d, limite)) {
			ret = d.after(limite);
		}
		return ret;
	}

	/**
	 * Torna true se l'array ï¿½ nullo o vuoto
	 * 
	 * @param array
	 * @return boolean
	 */
	public <T> boolean isEmpty(T[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * Ritorna True se la string passata ï¿½ nulla, ha lunghezza 0 oppure
	 * contiene la stringa "null"
	 * 
	 * @param string
	 * @return boolean
	 */
	public boolean isEmpty(String string) {
		return (string == null || string.length() == 0 || (string != null && string.equals("null")));
	}

	/**
	 * Ritorna True se la collection passata ï¿½ nulla o vuota
	 * 
	 * @param collection
	 * @return boolean
	 */
	public boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * Ritorna True se la Map passata ï¿½ nulla o vuota
	 * 
	 * @param map
	 * @return boolean
	 */
	public boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}

	/**
	 * Torna true se tutti gli oggetti values sono nulli (una stringa si
	 * considera nulla se nulla , vuota o contiene la stringa "null", una
	 * collection ï¿½ nulla se null o vuota)
	 * 
	 * @param values
	 * @return boolean
	 */
	public boolean Null(Object... values) {
		boolean isNull = true;
		if (null == values)
			return true;
		for (Object k : values) {
			if (k instanceof Collection) {
				isNull = isNull && isEmpty((Collection) k);
				continue;
			}
			if (k instanceof Map) {
				isNull = isNull && isEmpty((Map) k);
				continue;
			}
			if (k instanceof String) {
				isNull = isNull && isEmpty((String) k);
				continue;
			}
			isNull = isNull && null == k;
		}
		return isNull;
	}

	/**
	 * Torna true se almeno uno degli gli oggetti values ï¿½ nullo (una stringa
	 * si considera nulla se nulla , vuota o contiene la stringa "null", una
	 * collection ï¿½ nulla se null o vuota)
	 * 
	 * @param values
	 * @return boolean
	 */

	public boolean NullOR(Object... values) {
		boolean isNull = false;
		for (Object k : values) {
			isNull = almenoUna(isNull, Null(k));
		}
		return isNull;
	}

	/**
	 * Torna true se la collection passata contiene valori duplicati in base al
	 * metodo equals
	 * 
	 * @param all
	 * @return boolean
	 */
	public <T> boolean hasDuplicate(Iterable<T> all) {
		Set<T> set = new HashSet<T>();
		if (Null(all))
			return false;
		for (T each : all)
			if (!set.add(each))
				return true;
		return false;
	}

	/**
	 * Data una lista l1 e n liste, torna una nuova lista di elementi comuni tra
	 * l1 e le n liste in base al metodo equals
	 * 
	 * @param l1
	 * @param liste
	 * @return List<K> ritorna una nuova lista che contiene l'intersezione degli
	 *         elementi in comune tra la lista l1 e tutte le liste liste
	 */
	public <K> List<K> intersection(List<K> l1, List<K>... liste) {
		List<K> inters = new ArrayList<K>(l1);
		if (null != liste) {
			for (List<K> coll : liste) {
				inters = _intersection(inters, coll);
			}
		}
		return inters;
	}

	private <K> List<K> _intersection(List<K> l1, List<K> l2) {
		List<K> inters = new ArrayList<K>();
		if (notNull(l1)) {
			if (Null(l2))
				inters = Collections.EMPTY_LIST;
			for (K k : safe(l2)) {
				if (l1.contains(k)) {
					inters.add(k);
				}
			}
		}
		return inters;
	}

	/**
	 * 
	 * Dato un Set l1 e n Set, torna una nuovo Set di elementi comuni tra l1 e
	 * gli n Set in base al metodo equals
	 * 
	 * @param l1
	 * @param insiemi
	 * @return ritorna un nuovo Set che contiene l'intersezione degli elementi
	 *         in comune tra il Set l1 e tutte gli altri Set
	 */
	public <K> Set<K> intersection(Set<K> l1, Set<K>... insiemi) {
		Set<K> inters = new HashSet<K>(l1);
		if (null != insiemi) {
			for (Set<K> coll : insiemi) {
				inters = _intersection(inters, coll);
			}
		}
		return inters;
	}

	private <K> Set<K> _intersection(Set<K> l1, Set<K> l2) {
		Set<K> inters = new HashSet<K>();
		if (notNull(l1)) {
			if (Null(l2))
				inters = Collections.emptySet();
			for (K k : safe(l2)) {
				if (l1.contains(k)) {
					inters.add(k);
				}
			}
		}
		return inters;
	}

	/**
	 * Dati gli oggetti values concatena la loro rappresentazione stringa
	 * tramite stringBuffer tornando una unica stringa
	 * 
	 * @param values
	 * @return String
	 */
	public String getString(Object... values) {
		StringBuffer sb = new StringBuffer();
		for (Object clause : values) {
			sb.append(emptyIfNull(toStr(clause)));
		}
		return sb.toString();
	}

	/**
	 * Dati gli oggetti values concatena la loro rappresentazione stringa
	 * tramite stringBuffer tornando una unica stringa usando la stringa sep
	 * come separatore tra gli elementi values
	 * 
	 * @param sep
	 * @param values
	 * @return String
	 */
	public String getStringSep(String sep, Object... values) {
		StringBuffer sb = new StringBuffer();
		for (Object clause : values) {
			sb.append(emptyIfNull(toStr(clause))).append(sep);
		}
		String s = sb.toString();
		if (s.endsWith(sep)) {
			s = cutToLastOccurence(s, sep);
		}
		return s;
	}

	/**
	 * Dati gli oggetti values concatena la loro rappresentazione stringa
	 * tramite stringBuffer tornando una unica stringa usando la stringa SPACE
	 * (spazio vuoto) come separatore tra gli elementi values
	 * 
	 * @param values
	 * @return String
	 */
	public String getStringSpaced(Object... values) {
		return getStringSep(SPACE, values);
	}

	/**
	 * Metodo alias di getString
	 * 
	 * @param values
	 * @return String
	 */
	public String str(Object... values) {
		return getString(values);
	}

	/**
	 * Metodo alias di getStringSep(String sep, Object... values)
	 * 
	 * @param values
	 * @return String
	 */
	public String strSep(String sep, Object... values) {
		return getStringSep(sep, values);
	}

	/**
	 * Metodo alias di getStringSpaced(String sep, Object... values)
	 * 
	 * @param values
	 * @return String
	 */
	public String strSpaced(Object... values) {
		return getStringSpaced(values);
	}

	/**
	 * Se l'array passato ï¿½ null, ritorna un array di tipi c non nullo e di
	 * dimensione 0, altrimenti torna l'array arr stesso se non nullo
	 * 
	 * @param <K>
	 * @param arr
	 * @param c
	 * @return K[]
	 * @throws Exception
	 */
	public <K> K[] safe(K[] arr, Class<K> c) throws Exception {
		return valIfNull(arr, (K[]) Array.newInstance(c, 0));
	}

	/**
	 * Data una collection esegue il controllo di nullitï¿½ e se null torna una
	 * collection non nulla ma vuota
	 * 
	 * @param elenco
	 * @return Collection<K>
	 */
	public <K> Collection<K> safe(Collection<K> elenco) {
		if (elenco instanceof List) {
			List<K> emptyList = new ArrayList<K>();
			return elenco == null ? emptyList : elenco;
		}
		if (elenco instanceof Set) {
			Set<K> emptySet = new HashSet<K>();
			return elenco == null ? emptySet : elenco;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * Data una lista elimina gli elementi nulli
	 * 
	 * @param elenco
	 * @return boolean
	 */
	public boolean cleanNullList(Collection elenco) {
		return elenco.removeAll(Collections.singleton(null));
	}

	/**
	 * Torno true se tutti gli elementi values sono non nulli (una stringa ï¿½
	 * nulla se vuota null o "null", una collection ï¿½ nulla se null o vuota)
	 * 
	 * @param values
	 * @return boolean
	 */
	public boolean notNull(Object... values) {
		boolean notNull = true;
		if (null == values)
			return false;
		for (Object k : values) {
			if (k instanceof Collection) {
				notNull = notNull && !isEmpty((Collection) k);
				continue;
			}
			if (k instanceof Map) {
				notNull = notNull && !isEmpty((Map) k);
				continue;
			}
			if (k instanceof String) {
				notNull = notNull && !isEmpty((String) k);
				continue;
			}
			notNull = notNull && null != k;
		}
		return notNull;
	}

	/**
	 * Torna il Timestamp della data 31/12/9999 00:00:00
	 * 
	 * @return Timestamp
	 * @throws ParseException
	 */
	public Timestamp getInfiniteDateTS() throws ParseException {
		Date d = sdfhhmmss.parse(INFINITE_DATE);
		return new Timestamp(d.getTime());
	}

	/**
	 * Torna java.util.Date al 31/12/9999 00:00:00
	 * 
	 * @return Date
	 * @throws ParseException
	 */
	public Date getInfiniteDate() throws ParseException {
		Date d = sdfhhmmss.parse(INFINITE_DATE);
		return d;
	}

	/**
	 * Torna java.util.Date al 01/01/0001 00:00:00
	 * 
	 * @return Date
	 * @throws ParseException
	 */
	public Date getInfiniteStartDate() throws ParseException {
		Date d = sdfhhmmss.parse(INFINITE_START_DATE);
		return d;
	}

	/**
	 * Torna java.sql.Date al 31/12/9999 00:00:00
	 * 
	 * @return java.sqlDate
	 * @throws ParseException
	 */
	public java.sql.Date getInfiniteDateSql() throws ParseException {
		Date d = sdfhhmmss.parse(INFINITE_DATE);
		return getSQLDate(d);
	}

	/**
	 * Dato un valore numerico ne torna la rappresentazione monetaria italiana
	 * tipo 3.124,45
	 * 
	 * @param bd
	 * @return String
	 */
	public String money(BigDecimal bd) {
		return formatImporto(bd);
	}

	private String formatImporto(BigDecimal importo) {
		String ret = null;
		final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ITALIAN);
		numberFormat.setGroupingUsed(true);
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		if (notNull(importo)) {
			ret = numberFormat.format(importo);
		}
		return ret;
	}

	/**
	 * Data una rappresentazione numerica italiana del tipo 3.124,34 torna il
	 * corrispondente valore numerico BigDecimal
	 * 
	 * @param importo
	 * @return BigDecimal
	 */
	public BigDecimal moneyToBigDecimal(String importo) {
		BigDecimal ret = null;
		final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ITALIAN);
		numberFormat.setGroupingUsed(true);
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		if (notNull(importo)) {
			try {
				ret = new BigDecimal(numberFormat.parse(importo).toString());
			} catch (ParseException e) {
			}
		}
		return ret;
	}

	/**
	 * Torno true se almeno uno dei values ï¿½ non nullo (una stringa ï¿½ nulla
	 * se vuota null o "null", una collection ï¿½ nulla se null o vuota)
	 * 
	 * @param values
	 * @return boolean
	 */

	public boolean notNullOR(Object... values) {
		boolean notNull = false;
		for (Object k : values) {
			notNull = almenoUna(notNull, notNull(k));
		}
		return notNull;

	}

	/**
	 * Operatore ternario in forma di metodo se la cond ï¿½ vera torno trueValue
	 * altrimenti torno falseVale
	 * 
	 * @param cond
	 * @param trueValue
	 * @param falseValue
	 * @return String
	 */
	public String ternary(Boolean cond, String trueValue, String falseValue) {
		return cond ? trueValue : falseValue;
	}

	/**
	 * se campo ï¿½ uguale ad almeno uno dei value torno trueVale altrimento
	 * torno falseValue
	 * 
	 * @param trueValue
	 * @param falseValue
	 * @param campo
	 * @param value
	 * @return T
	 */
	public <K, T> T isTernary(T trueValue, T falseValue, K campo, K... value) {
		return (is(campo, value)) ? trueValue : falseValue;
	}

	private <K> boolean collectionContains(Collection<K> c, K... value) {
		if (Null(c))
			return false;
		boolean ret = false;
		for (K k : value) {
			ret = ret || c.contains(k);
		}
		return ret;

	}

	private boolean stringLike(String s, List<String> l) {
		boolean ret = false;
		for (String elem : safe(l)) {
			ret = emptyIfNull(s).toLowerCase().contains(emptyIfNull(elem).toLowerCase());
			if (ret)
				break;
		}
		return ret;
	}

	private boolean collectionLike_(Collection<String> c, String value) {
		boolean ret = false;
		for (String val : c) {
			ret = emptyIfNull(val).toLowerCase().contains(emptyIfNull(value).toLowerCase());
			if (ret)
				break;
		}
		return ret;
	}

	private boolean collectionLike(Collection c, Object... value) {
		if (Null(c))
			return false;
		boolean ret = false;
		for (Object k : value) {
			if (k instanceof String) {
				ret = ret || collectionLike_((Collection<String>) c, (String) k);
			} else {
				ret = ret || c.contains(k);
			}
		}
		return ret;
	}

	private boolean arrayContains(Object[] arr, Object... value) {
		for (Object o : arr) {
			for (Object v : value) {
				if (o.equals(v))
					return true;
			}
		}
		return false;
	}

	private boolean arrayIntContains(int[] arr, Object... value) {
		for (int i : arr) {
			for (Object in : value) {
				if (i == (Integer) in)
					return true;
			}
		}
		return false;
	}

	private boolean arrayLongContains(long[] arr, Object... value) {
		for (long i : arr) {
			for (Object in : value) {
				if (i == (Long) in)
					return true;
			}
		}
		return false;
	}

	private boolean arrayDoubleContains(double[] arr, Object... value) {
		for (double i : arr) {
			for (Object in : value) {
				if (i == (Double) in)
					return true;
			}
		}
		return false;
	}

	private boolean arrayShortContains(short[] arr, Object... value) {
		for (short i : arr) {
			for (Object in : value) {
				if (i == (Integer) in)
					return true;
			}
		}
		return false;
	}

	private boolean isEq(Object campo, Object... value) {
		boolean ret = false;
		if (null != campo && campo.getClass().isArray()) {
			if (campo instanceof int[]) {
				return arrayIntContains((int[]) campo, value);
			}
			if (campo instanceof long[]) {
				return arrayLongContains((long[]) campo, value);
			}
			if (campo instanceof double[]) {
				return arrayDoubleContains((double[]) campo, value);
			}
			if (campo instanceof short[]) {
				return arrayShortContains((short[]) campo, value);
			}

			return arrayContains((Object[]) campo, value);
		}
		if (campo instanceof Collection) {
			ret = collectionContains((Collection) campo, value);
		} else {
			ret = isObj(campo, value);
		}
		if (campo instanceof String) {
			if (notNull(value)) {
				ret = is((String) campo, Arrays.asList(value).toArray(new String[value.length]));
			}
		}
		if (campo instanceof BigDecimal) {
			if (notNull(value)) {
				ret = isEscludiZeriDecimali((BigDecimal) campo, Arrays.asList(value).toArray(new BigDecimal[value.length]));
			}
		}
		return ret;
	}

	private boolean is(String campo, String value) {
		return notNull(campo, value) && campo.trim().equalsIgnoreCase(value.trim());
	}

	private boolean is(String campo, String... value) {
		boolean esito = false;
		if (Null(campo))
			return false;
		if (null != value) {
			for (String el : value) {
				esito = almenoUna(esito, is(campo, el));
			}
		}
		return esito;
	}

	/**
	 * Torna true se campo ï¿½ uguale ad almeno uno dei value oppure se campo
	 * ï¿½ nullo e almeno uno dei value ï¿½ nullo. Se campo ï¿½ una collection
	 * la condizione ï¿½ uguale significa che la collection contiene almeno uno
	 * dei value. Se campo ï¿½ una stringa la condizione ï¿½ uguale significa
	 * che la stringa ï¿½ uguale in base al metodo ignorecase. Se campo ï¿½ un
	 * BigDecimal la condizione ï¿½ uguale significa che ignora gli zeri dopo la
	 * virgola. Per gli altri casi la condizione ï¿½ uguale significa che ï¿½
	 * rispettata la condizione in base al metodo equals;
	 * 
	 * @param campo
	 * @param value
	 * @return boolean
	 */
	public boolean is(Object campo, Object... value) {
		return isEq(campo, value);
	}

	/**
	 * Torna true se campo NON ï¿½ uguale a nessuno uno dei value. Se campo ï¿½
	 * una collection torna true se la collection non contiene nessuno dei
	 * value. Se campo ï¿½ una stringa la verifica di avviene ignorecase. Se
	 * campo ï¿½ un BigDecimal la condizione ï¿½ uguale significa che ignora gli
	 * zeri dopo la virgola. Per gli altri casi la condizione avviene in base al
	 * metodo equals
	 * 
	 * @param campo
	 * @param value
	 * @return boolean
	 */
	public boolean isNot(Object campo, Object... value) {
		return !is(campo, value);
	}

	private boolean isEscludiZeriDecimali(BigDecimal campo, BigDecimal... value) {
		boolean notNull = false;
		if (Null(campo))
			return false;
		for (BigDecimal el : value) {
			notNull = notNull || (notNull(el) ? campo.stripTrailingZeros().equals(el.stripTrailingZeros()) : false);
		}
		return notNull;
	}

	/**
	 * Torna true se almeno una delle condizioni booleane ï¿½ vera
	 * 
	 * @param b
	 * @return boolean
	 */
	public boolean almenoUna(Boolean... b) {
		boolean res = false;
		for (Boolean b_ : b) {
			res = res || b_;
		}
		return res;
	}

	/**
	 * Torna true se tutte le condizioni parametro sono vere
	 * 
	 * @param b
	 * @return boolean
	 */
	public boolean tutte(Boolean... b) {
		boolean res = true;
		for (Boolean b_ : b) {
			res = res && b_;
		}
		return res;
	}

	/**
	 * Torna true se tutte le condizioni parametro sono false
	 * 
	 * @param b
	 * @return boolean
	 */
	public boolean nessuna(Boolean... b) {
		return !almenoUna(b);
	}

	/**
	 * Torna true se soltanto una delle condizioni ï¿½ vera
	 * 
	 * @param b
	 * @return boolean
	 */
	public boolean soloUna(Boolean... b) {
		int vere = 0;
		for (Boolean b_ : b) {
			if (b_)
				vere++;
		}
		return vere == 1;
	}

	private boolean isObj(Object campo, Object... value) {
		if (campo instanceof Date) {
			campo = toMidnight((Date) campo);
		}

		boolean trovato = false;
		if (null != value) {
			for (Object el : value) {
				if (Null(campo)) {
					if (Null(el))
						return true;
				} else {
					if (el instanceof Date && campo instanceof Date) {
						el = toMidnight((Date) el);
					}
					trovato = almenoUna(trovato, campo.equals(el));
				}
			}
		}
		return trovato;
	}

	/**
	 * Da una data ritorna un nuovo oggetto Date impostato alla mezzanotte dello
	 * stesso giorno
	 * 
	 * @param d
	 * @return Date
	 */
	private Date toMidnight(Date d) {
		if (Null(d))
			return d;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * Se la stringa ï¿½ nulla torna stringa vuota
	 * 
	 * @param s
	 * @return String
	 */
	public String emptyIfNull(String s) {
		return notNull(s) ? s : "";
	}

	/**
	 * Esegue la sommatoria dei valori BigDecimal passati come parametri
	 * 
	 * @param vals
	 * @return BigDecimal
	 */
	public BigDecimal aggiungi(BigDecimal... vals) {
		BigDecimal start = ZERO;
		if (null == vals)
			return start;
		for (BigDecimal v : vals) {
			start = setScale(start.add(zeroIfNull(v)));

		}
		return start;
	}

	/**
	 * Esegue la sottrazione dei due BigDecimal
	 * 
	 * @param b1
	 * @param b2
	 * @return BigDecimal
	 */
	public BigDecimal sottrai(BigDecimal b1, BigDecimal b2) {
		if (NullOR(b1, b2))
			return null;
		return setScale(b1.subtract(b2));
	}

	/**
	 * Esegue la moltiplicazione dei due BigDecimal
	 * 
	 * @param b1
	 * @param b2
	 * @return BigDecimal
	 */
	public BigDecimal moltiplica(BigDecimal b1, BigDecimal b2) {
		if (NullOR(b1, b2))
			return null;
		return setScale(b1.multiply(b2));
	}

	/**
	 * Esegue la divisione dei due BigDecimal
	 * 
	 * @param b1
	 * @param b2
	 * @return BigDecimal
	 */
	public BigDecimal dividi(BigDecimal b1, BigDecimal b2) {
		if (NullOR(b1, b2))
			return null;
		return setScale(b1.divide(b2, 6, BigDecimal.ROUND_HALF_UP));
	}

	private BigDecimal setScale(BigDecimal b) {
		return notNull(b) ? b.setScale(2, BigDecimal.ROUND_HALF_UP) : null;
	}

	private BigDecimal setScale(BigDecimal b, Integer precision) {
		return notNull(b) ? b.setScale(precision, BigDecimal.ROUND_HALF_UP) : null;
	}

	/**
	 * Limita il BigDecimal a zero se ï¿½ negativo
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal zeroIfNegative(BigDecimal val) {
		return minoreZero(val) ? ZERO : val;
	}

	/**
	 * Limita il double a zero se ï¿½ negativo
	 * 
	 * @param val
	 * @return Double
	 */
	public Double zeroIfNegative(Double val) {
		return Null(val) ? null : val < 0 ? 0 : val;
	}

	/**
	 * Limita il double a zero se ï¿½ negativo
	 * 
	 * @param val
	 * @return double
	 */
	public double zeroIfNegative(double val) {
		return val < 0 ? 0 : val;
	}

	/**
	 * Se il BigDecimal ï¿½ null torno ZERO
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal zeroIfNull(BigDecimal val) {
		return Null(val) ? ZERO : val;
	}

	/**
	 * Se il double ï¿½ null torno ZERO
	 * 
	 * @param val
	 * @return Double
	 */
	public Double zeroIfNull(Double val) {
		return Null(val) ? 0 : val;
	}

	/**
	 * Converto da double BigDecimal
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(Double val) {
		return notNull(val) ? setScale(BigDecimal.valueOf(val)) : null;
	}

	/**
	 * Converto da double BigDecimal
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(Double val, Integer precision) {
		return notNull(val) ? setScale(BigDecimal.valueOf(val), precision) : null;
	}

	/**
	 * Alias di getBigDecimal(Double val)
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal bd(Double val) {
		return getBigDecimal(val);
	}

	/**
	 * Alias di getBigDecimal(Double val,Integer precision)
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal bd(Double val, Integer precision) {
		return getBigDecimal(val, precision);
	}

	/**
	 * Connverto da integer a BigDecimal
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(Integer val) {
		return notNull(val) ? setScale(BigDecimal.valueOf(val)) : null;
	}

	/**
	 * Connverto da integer a BigDecimal
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(Integer val, Integer precision) {
		return notNull(val) ? setScale(BigDecimal.valueOf(val), precision) : null;
	}

	/**
	 * Alias di getBigDecimal(Integer val)
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal bd(Integer val) {
		return getBigDecimal(val);
	}

	/**
	 * Alias di getBigDecimal(Integer val,Integer precision)
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal bd(Integer val, Integer precision) {
		return getBigDecimal(val, precision);
	}

	/**
	 * Esegue lo scale 2 arrotondamento metà superiore del BigDecimal
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(BigDecimal val) {
		return notNull(val) ? setScale(val) : null;
	}

	/**
	 * Esegue lo scale precision arrotondamento metà superiore del BigDecimal
	 * 
	 * @param val
	 * @param precision
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(BigDecimal val, Integer precision) {
		return notNull(val) ? setScale(val, precision) : null;
	}

	/**
	 * Alias di getBigDecimal(BigDecimal val)
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal bd(BigDecimal val) {
		return getBigDecimal(val);
	}

	/**
	 * Alias di getBigDecimal(BigDecimal val,Integer precision)
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal bd(BigDecimal val, Integer precision) {
		return getBigDecimal(val, precision);
	}

	/**
	 * Converto da long a BigDecimal
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(Long val) {
		return notNull(val) ? setScale(BigDecimal.valueOf(val)) : null;
	}

	/**
	 * Converto da long a BigDecimal
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(Long val, Integer precision) {
		return notNull(val) ? setScale(BigDecimal.valueOf(val), precision) : null;
	}

	/**
	 * Alias di getBigDecimal(Long val)
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal bd(Long val) {
		return getBigDecimal(val);
	}

	/**
	 * Alias di getBigDecimal(Long val,Integer precision)
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal bd(Long val, Integer precision) {
		return getBigDecimal(val, precision);
	}

	/**
	 * Converto da String a BigDecimal
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(String val) {
		return getBigDecimal(getDouble(val));
	}

	/**
	 * Converto da String a BigDecimal
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(String val, Integer precision) {
		return getBigDecimal(getDouble(val), precision);
	}

	/**
	 * Alias di getBigDecimal(String val)
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal bd(String val) {
		return getBigDecimal(val);
	}

	/**
	 * Alias di getBigDecimal(String val,Integer precision)
	 * 
	 * @param val
	 * @return BigDecimal
	 */
	public BigDecimal bd(String val, Integer precision) {
		return getBigDecimal(val, precision);
	}

	/**
	 * Torna true se num ï¿½ un intero positivo in base alla condizione nullable
	 * true vuol dire che puï¿½ ammettere valore nullo, false non puï¿½ essere
	 * nullo
	 * 
	 * @param num
	 * @param nullable
	 * @return boolean
	 */
	public boolean validaInteroPositivo(String num, boolean nullable) {
		if (nullable && Null(num))
			return true;
		boolean ret = false;
		try {
			Integer n = Integer.parseInt(num);
			ret = tutte(notNull(n), n.compareTo(0) >= 0);
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * Torna true se num ï¿½ un intero in base alla condizione nullable true
	 * vuol dire che puï¿½ ammettere valore nullo, false non puï¿½ essere nullo
	 * 
	 * @param num
	 * @param nullable
	 * @return boolean
	 */
	public boolean validaIntero(String num, boolean nullable) {
		if (nullable && Null(num))
			return true;
		boolean ret = false;
		try {
			Integer n = Integer.parseInt(num);
			ret = notNull(n);
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * Torna true se num ï¿½ un decimale (formato italiano con la , per i
	 * decimali) in base alla condizione nullable true vuol dire che puï¿½
	 * ammettere valore nullo, false non puï¿½ essere nullo
	 * 
	 * @param num
	 * @param nullable
	 * @return boolean
	 */
	public boolean validaDecimale(String num, boolean nullable) {
		if (nullable && Null(num))
			return true;
		boolean ret = false;
		try {
			BigDecimal b = moneyToBigDecimal(num);
			ret = notNull(b);
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * Torna true se num ï¿½ un decimale positivo (formato italiano con la , per
	 * i decimali) in base alla condizione nullable true vuol dire che puï¿½
	 * ammettere valore nullo, false non puï¿½ essere nullo
	 * 
	 * @param num
	 * @param nullable
	 * @return boolean
	 */
	public boolean validaDecimalePositivo(String num, boolean nullable) {
		if (nullable && Null(num))
			return true;
		boolean ret = false;
		try {
			BigDecimal b = moneyToBigDecimal(num);
			ret = notNull(b) && maggioreUgualeZero(b);
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * Torna true se b ï¿½ un decimale positivo in base alla condizione nullable
	 * true vuol dire che puï¿½ ammettere valore nullo, false non puï¿½ essere
	 * nullo
	 * 
	 * @param b
	 * @param nullable
	 * @return boolean
	 */
	public boolean validaDecimalePositivo(BigDecimal b, boolean nullable) {
		if (nullable && Null(b))
			return true;
		boolean ret = false;
		try {
			ret = notNull(b) && maggioreUgualeZero(b);
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * Valida la maxlength della stringa in base alla condizione booleana
	 * nullable se true puï¿½ essere nulla se false non puï¿½ essere nulla
	 * 
	 * @param s
	 * @param l
	 * @param nullable
	 * @return boolean
	 */
	public boolean validaMaxLength(String s, Integer l, boolean nullable) {
		if (nullable && Null(s))
			return true;
		boolean ret = false;
		try {
			ret = tutte(notNull(s), s.length() <= l);
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * Valida la data passata come stringa in base alla condizione nullable se
	 * true puï¿½ essere nulla se false non puï¿½ essere nulla
	 * 
	 * @param data
	 * @param nullable
	 * @return boolean
	 */
	public boolean validaData(String data, boolean nullable) {
		if (nullable && Null(data))
			return true;
		boolean ret = false;
		try {
			ret = notNull(stringToDate(data));
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * Valida la stringa che sia un numero in base alla condizione nullable se
	 * true puï¿½ essere nulla se false non puï¿½ essere nulla
	 * 
	 * @param s
	 * @param nullable
	 * @return boolean
	 */
	public boolean validaNumber(String s, boolean nullable) {
		if (tutte(nullable, Null(s)))
			return true;
		s = emptyIfNull(s).trim();
		Pattern pattern = Pattern.compile("-?\\d+((\\.||\\,)\\d+)?");
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}

	private boolean validaData(String data) {
		if (Null(data))
			return true;
		boolean ret = true;
		try {
			sdf.setLenient(false);
			sdf.parse(data);
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	/**
	 * Valida le date inizio e fine che siano ordinate temporalmente in base
	 * alla condizione nullable se true possono essere nulle se false non
	 * possono essere tutte nulle ma almeno una deve essere non nulla
	 * 
	 * @param inizio
	 * @param fine
	 * @param nullable
	 * @return boolean
	 */
	public boolean validaDate(String inizio, String fine, boolean nullable) {
		if (tutte(Null(inizio, fine), !nullable))
			return false;
		boolean cond1 = (validaData(inizio));
		boolean cond2 = (validaData(fine));
		boolean ret = tutte(cond1, cond2);
		try {
			ret = tutte(ret, isOrderedDate(stringToDate(inizio), stringToDate(fine)));
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * Valida le date inizio e fine che siano ordinate temporalmente in base
	 * alla condizione nullable se true possono essere nulle se false non
	 * possono essere tutte nulle ma almeno una deve essere non nulla
	 * 
	 * @param inizio
	 * @param fine
	 * @param nullable
	 * @return boolean
	 */
	public boolean validaDate(Date inizio, Date fine, boolean nullable) {
		if (tutte(Null(inizio, fine), !nullable))
			return false;
		boolean cond1 = (validaData(dateToString(inizio)));
		boolean cond2 = (validaData(dateToString(fine)));
		boolean ret = tutte(cond1, cond2);
		try {
			ret = tutte(ret, isOrderedDate(inizio, fine));
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * Valida la lunghezza della stringa in base alla condizione booleana
	 * nullable se true puï¿½ essere nulla se false non puï¿½ essere nulla
	 * 
	 * @param s
	 * @param l
	 * @param nullable
	 * @return boolean
	 */
	public boolean validaLength(String s, Integer l, boolean nullable) {
		if (nullable && Null(s))
			return true;
		boolean ret = false;
		try {
			ret = tutte(notNull(s), s.length() == l);
		} catch (Exception e) {
		}
		return ret;
	}

	public boolean validaCodiceFiscale(String cf, Boolean nullable) {
		if (tutte(nullable, Null(cf)))
			return true;
		return Null(new CodiceFiscale(cf).validate_regular());
	}

	private class CodiceFiscale {

		private String cf;

		private void setCf(String cf) {
			this.cf = cf;
		}

		CodiceFiscale(String cf) {
			setCf(cf);
		};

		/**
		 * Normalizes a CF by removing white spaces and converting to
		 * upper-case. Useful to clean-up user's input and to save the result in
		 * the DB.
		 * 
		 * @param cf
		 *            Raw CF, possibly with spaces.
		 * @return Normalized CF.
		 */
		private String normalize() {
			cf = cf.replaceAll("[ \t\r\n]", "");
			cf = cf.toUpperCase();
			return cf;
		}

		/**
		 * Validates a regular CF.
		 * 
		 * @param cf
		 *            Normalized, 16 characters CF.
		 * @return Null if valid, or string describing why this CF must be
		 *         rejected.
		 */
		private String validate_regular() {
			cf = normalize();
			if (!cf.matches("^[0-9A-Z]{16}$"))
				return "Invalid characters.";
			int s = 0;
			String even_map = "BAFHJNPRTVCESULDGIMOQKWZYX";
			for (int i = 0; i < 15; i++) {
				int c = cf.charAt(i);
				int n;
				if ('0' <= c && c <= '9')
					n = c - '0';
				else
					n = c - 'A';
				if ((i & 1) == 0)
					n = even_map.charAt(n) - 'A';
				s += n;
			}
			if (s % 26 + 'A' != cf.charAt(15))
				return "Invalid checksum.";
			return null;
		}

	}

	/**
	 * Ritorna l'oggetto Long corrispondente alla stringa val
	 * 
	 * @param val
	 * @return Long
	 */
	public Long getLong(String val) {
		Long l = null;
		try {
			l = Long.parseLong(val);
		} catch (Exception e) {
		}
		return l;
	}

	private Long getLong2(String val) {
		Long l = null;
		try {
			l = Long.parseLong(val);
		} catch (Exception e) {
		}
		return l;
	}

	private Long getLong2(Double val) {
		Long ret = null;
		if (notNull(val)) {
			ret = val.longValue();
		}
		return ret;
	}

	private Integer getInteger2(Double val) {
		Integer ret = null;
		if (notNull(val)) {
			ret = val.intValue();
		}
		return ret;
	}

	private Float getFloat2(Double val) {
		Float ret = null;
		if (notNull(val)) {
			ret = val.floatValue();
		}
		return ret;
	}

	private Long getLong2(Integer val) {
		if (notNull(val)) {
			return val.longValue();
		}
		return null;
	}

	/**
	 * Ritorna l'oggetto Integer rappresentato dalla stringa val
	 * 
	 * @param val
	 * @return Integer
	 */
	public Integer getInteger(String val) {
		Integer l = null;
		try {
			l = Integer.parseInt(val);
		} catch (Exception e) {
		}
		return l;
	}

	/**
	 * Ritorna l'oggetto Double rappresentato dalla stringa val
	 * 
	 * @param val
	 * @return Double
	 */
	public Double getDouble(String val) {
		Double l = null;
		try {
			l = Double.parseDouble(val);
		} catch (Exception e) {
		}
		return l;
	}

	/**
	 * Ritorna l'oggetto Float rappresentato dalla stringa val
	 * 
	 * @param val
	 * @return Float
	 */
	public Float getFloat(String val) {
		Float l = null;
		try {
			l = Float.parseFloat(val);
		} catch (Exception e) {
		}
		return l;
	}

	/**
	 * Ritorna l'oggetto Short rappresentato dalla stringa val
	 * 
	 * @param val
	 * @return Short
	 */
	public Short getShort(String val) {
		Short l = null;
		try {
			l = Short.parseShort(val);
		} catch (Exception e) {
		}
		return l;
	}

	/**
	 * Dato un file path+nome.estensione legge riga per riga e torna una lista
	 * di righe quante sono le righe del file
	 * 
	 * @param path
	 * @return PList<String>
	 */
	public PList<String> readFile(String path) {
		PList<String> file = plstr();
		try {
			FileInputStream fis = new FileInputStream(path);
			Scanner sc = new Scanner(fis);
			while (sc.hasNextLine()) {
				file.add(getString(sc.nextLine(), SPACE));
			}
			sc.close();
		} catch (IOException e) {
			ex(e);
		}
		return file;
	}

	private void ex(Exception e) {
		logError("ECCEZIONE! ", e);
	}

	/**
	 * Dato un file path+nome.estensione legge riga per riga e torna l'array di
	 * byte che compongono il file
	 * 
	 * @param path
	 * @return byte[]
	 */
	public byte[] readFileIntoByteArray(String path) {
		byte[] content = null;
		try {
			File f = new File(path);
			if (f.exists() && f.isFile())
				content = readToBytes(f);
		} catch (Exception e) {
			ex(e);
		}
		return content;
	}

	/**
	 * Data una lista di tipi Java (non custom quindi, tipo String, Integer,
	 * BigDecimal, ecc.) ottengo una nuova lista di elementi dello stesso tipo
	 * che siano uguali a value
	 * 
	 * @param lista
	 * @param value
	 * @return List<K>
	 * @throws Exception
	 */

	public <K> List<K> find(List<K> lista, K value) throws Exception {
		List<K> shortList = new ArrayList<K>();
		for (K k : safe(lista)) {
			if (is(k, value)) {
				shortList.add(k);
			}
		}
		return shortList;
	}

	/**
	 * Data una lista di tipi Java (non custom quindi, tippo String, Integer,
	 * BigDecimal, ecc.) ottengo l'unico elemento della lista che sia value
	 * 
	 * @param lista
	 * @param value
	 * @return K
	 * @throws Exception
	 */
	public <K> K findOne(List<K> lista, K value) throws Exception {
		return getFirstElement(find(lista, value));
	}

	/**
	 * 
	 * Dato un file (path+nome.estensione) e una stringa , crea il file avente
	 * per contenuto quella stringa passata
	 * 
	 * @param path
	 * @param data
	 */

	public void writeFile(String path, String data) {
		File file = new File(path);
		createFolder(path);
		FileWriter fr = null;
		try {
			fr = new FileWriter(file);
			if (notNull(data))
				fr.write(data);
		} catch (IOException e) {
			ex(e);
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				ex(e);
			}
		}
	}

	/**
	 * Scrive nel file path l'oggetto T di tipo BaseEntity stampando solo i
	 * campi che riportano l'annotazione @Column
	 * 
	 * @param <T>
	 * @param path
	 * @param data
	 */
	public <T extends BaseEntity> void writeFile(String path, T data) {
		writeFile(path, toStrEntity(data));
	}

	/**
	 * Scrive nel file path l'oggetto PList<T> con T di tipo BaseEntity
	 * stampando solo i campi che riportano l'annotazione @Column
	 * 
	 * @param <T>
	 * @param path
	 * @param data
	 */
	public <T extends BaseEntity> void writeFile(String path, PList<T> data) {
		writeFile(path, toStrListEntity(data));
	}

	/**
	 * Dato un file (path+nome.estensione) e una stringa di liste, crea il file
	 * inserendo tutto il contenuto della lista di stringhe, ogni elemento della
	 * lista viene separato dal carattere linefeed per andare a capo
	 * 
	 * @param <T>
	 * @param path
	 * @param data
	 */
	public <T> void writeFile(String path, List<T> data) {
		File file = new File(path);
		createFolder(path);
		FileWriter fr = null;
		try {
			fr = new FileWriter(file);
			for (T s : safe(data)) {
				fr.write(getString(s, lf()));
			}
		} catch (IOException e) {
			ex(e);
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				ex(e);
			}
		}
	}

	/**
	 * Dato un file e una stringa inizio e fine, restituisce tutto il contenuto
	 * del file compreso tra le stringhe inizio e fine quando queste sono
	 * individuate all'interno in modalitï¿½ like %% e restituisce una lista di
	 * stringhe corrispondenti al contenuto trovato. Le stringhe inizio e fine
	 * non vengono incluse nel contenuto restituito.
	 * 
	 * @param path
	 * @param inizio
	 * @param fine
	 * @return PList<String>
	 */
	public PList<String> readFileBetweenContentLike(String path, String inizio, String fine) {
		PList<String> file = plstr();
		try {
			FileInputStream fis = new FileInputStream(path);
			Scanner sc = new Scanner(fis);
			String line = "";
			boolean trovato = false;
			boolean trovatoFine = false;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (!trovato) {
					trovato = isLike(line, inizio);
					if (trovato)
						continue;
				}
				if (tutte(trovato, !trovatoFine)) {
					trovatoFine = isLike(line, fine);
				}
				if (trovato) {
					if (trovatoFine)
						break;
					file.add(line);
				}
			}
			sc.close();
		} catch (IOException e) {
			ex(e);
		}
		return file;
	}

	/**
	 * Dato un file e una stringa inizio e fine, restituisce tutto il contenuto
	 * del file compreso tra le stringhe inizio e fine quando queste sono
	 * individuate all'interno in modalitï¿½ is (ossia uguali alle stringhe
	 * inizio e fine) e restituisce una lista di stringhe corrispondenti al
	 * contenuto trovato. Le stringhe inizio e fine non vengono incluse nel
	 * contenuto restituito.
	 * 
	 * @param path
	 * @param inizio
	 * @param fine
	 * @return PList<String>
	 */
	public PList<String> readFileBetweenContent(String path, String inizio, String fine) {
		PList<String> file = plstr();
		try {
			FileInputStream fis = new FileInputStream(path);
			Scanner sc = new Scanner(fis);
			String line = "";
			boolean trovato = false;
			boolean trovatoFine = false;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (!trovato) {
					trovato = is(line, inizio);
					if (trovato)
						continue;
				}
				if (tutte(trovato, !trovatoFine)) {
					trovatoFine = is(line, fine);
				}
				if (trovato) {
					if (trovatoFine)
						break;
					file.add(line);
				}
			}
			sc.close();
		} catch (IOException e) {
			ex(e);
		}
		return file;
	}

	/**
	 * Data una lista di stringhe, restituisce una unica stringa che ï¿½ la
	 * concatenazione degli elementi della lista
	 * 
	 * @param l
	 * @return String
	 */
	public String concatenaListaStringhe(List<String> l) {
		return getString((Object[]) toArray(l, String.class));
	}

	/**
	 * Trasforma una lista in un array
	 * 
	 * @param list
	 * @return T[]
	 */

	public <T> T[] toArray(List<T> list, Class<T> clazz) {
		T[] array = (T[]) Array.newInstance(clazz, list.size());
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return (T[]) array;
	}

	/**
	 * Dato un file inteso come risorsa applicativa quindi non un percorso
	 * assoluto ma solo il nome.estensione preceduto dal pkg di appartenenza
	 * ossia ad esempio /it/inps/queries.properties e una stringa inizio e fine,
	 * restituisce tutto il contenuto del file compreso tra le stringhe inizio e
	 * fine quando queste sono individuate all'interno in modalitï¿½ like %% e
	 * restituisce una lista di stringhe corrispondenti al contenuto trovato. Le
	 * stringhe inizio e fine non vengono incluse nel contenuto restituito.
	 * 
	 * 
	 * @param c
	 *            (passato come getClass() dal chiamante)
	 * @param nomeFile
	 *            (nome.estensione se si trova nello stesso pkg oppure se si
	 *            trova in un package diverso dal chiamante occorre definirlo
	 *            nella forma it/inpt/eng/nome.estensione
	 * @param inizio
	 * @param fine
	 * @return PList<String>
	 * @throws Exception
	 */
	public <T> PList<String> readFileAsResourceBetweenContentLike(Class<T> c, String nomeFile, String inizio, String fine) throws Exception {
		PList<String> file = plstr();
		try {
			String pkg = "";
			if (nomeFile.indexOf(DOT) <= 1) {
				pkg = getString(c.getPackage().getName(), DOT);
				pkg = pkg.replace('.', '/');
				nomeFile = getString(pkg, nomeFile);
			}
			InputStream fis = c.getClassLoader().getResourceAsStream(nomeFile);
			if (Null(fis))
				throw fileNotFound(nomeFile);
			Scanner sc = new Scanner(fis);
			String line = "";
			boolean trovato = false;
			boolean trovatoFine = false;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (!trovato) {
					trovato = isLike(line, inizio);
					if (trovato)
						continue;
				}
				if (tutte(trovato, !trovatoFine)) {
					trovatoFine = isLike(line, fine);
				}
				if (trovato) {
					if (trovatoFine)
						break;
					file.add(line);
				}
			}
			sc.close();
		} catch (IOException e) {
			ex(e);
		}
		return file;
	}

	/**
	 * Dato un file inteso come risorsa applicativa quindi non un percorso
	 * assoluto ma solo il nome.estensione preceduto dal pkg di appartenenza
	 * ossia ad esempio /it/inps/queries.properties, e una stringa inizio e
	 * fine, restituisce tutto il contenuto del file compreso tra le stringhe
	 * inizio e e restituisce una lista di stringhe corrispondenti al contenuto
	 * trovato. Le stringhe inizio e fine non vengono incluse nel contenuto
	 * restituito.
	 * 
	 * 
	 * @param c
	 *            (passato come getClass() dal chiamante)
	 * @param nomeFile
	 *            (nome.estensione se si trova nello stesso pkg oppure se si
	 *            trova in un package diverso dal chiamante occorre definirlo
	 *            nella forma it/inpt/eng/nome.estensione
	 * @param inizio
	 * @param fine
	 * @return PList<String>
	 * @throws Exception
	 */
	public <T> PList<String> readFileAsResourceBetweenContent(Class<T> c, String nomeFile, String inizio, String fine) throws Exception {
		PList<String> file = plstr();
		try {
			String pkg = "";
			if (nomeFile.indexOf(DOT) <= 1) {
				pkg = getString(c.getPackage().getName(), DOT);
				pkg = pkg.replace('.', '/');
				nomeFile = getString(pkg, nomeFile);
			}
			InputStream fis = c.getClassLoader().getResourceAsStream(nomeFile);
			if (Null(fis))
				throw fileNotFound(nomeFile);
			Scanner sc = new Scanner(fis);
			String line = "";
			boolean trovato = false;
			boolean trovatoFine = false;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (!trovato) {
					trovato = is(line, inizio);
					if (trovato)
						continue;
				}
				if (tutte(trovato, !trovatoFine)) {
					trovatoFine = is(line, fine);
				}
				if (trovato) {
					if (trovatoFine)
						break;
					file.add(line);
				}
			}
			sc.close();
		} catch (IOException e) {
			ex(e);
		}
		return file;
	}

	/**
	 * Legge da un file inteso come risorsa applicativa (file contenuto
	 * nell'applicazione o nel progetto, quindi non un percorso assoluto ma solo
	 * il nome del file) nome.estensione legge riga per riga e torna una lista
	 * di righe quante sono le righe del file
	 * 
	 * Nome file + nome.estensione se si trova nello stesso pkg del chiamante.
	 * Altrimenti se si trova in un pkg diverso occorre definirlo nella forma
	 * nomePackage/nome.estensione esempio it/eng/inps/query.txt
	 * 
	 * @param c
	 *            (passato come getClass() dal chiamante)
	 * @param nomeFile
	 *            (nome.estensione se si trova nello stesso pkg oppure se si
	 *            trova in un package diverso dal chiamante occorre definirlo
	 *            nella forma it/inpt/eng/nome.estensione
	 * @return PList<String>
	 * @throws Exception
	 */
	public <T> PList<String> readFileAsResource(Class<T> c, String nomeFile) throws Exception {
		PList<String> file = plstr();
		try {
			String pkg = "";
			if (nomeFile.indexOf(DOT) <= 1) {
				pkg = getString(c.getPackage().getName(), DOT);
				pkg = pkg.replace('.', '/');
				nomeFile = getString(pkg, nomeFile);
			}
			InputStream fis = c.getClassLoader().getResourceAsStream(nomeFile);
			if (Null(fis))
				throw fileNotFound(nomeFile);
			Scanner sc = new Scanner(fis);
			while (sc.hasNextLine()) {
				file.add(sc.nextLine());
			}
			sc.close();
		} catch (IOException e) {
			ex(e);
		}
		return file;
	}

	private Exception fileNotFound(String nomeFile) {
		return new Exception(getString("File ", nomeFile, " NON TROVATO"));
	}

	private byte[] readToBytes(File file) throws Exception {
		if (file.length() > MAX_FILE_SIZE) {
			throw fileTooBig();
		}
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			byte[] buffer = new byte[(int) file.length()];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
			}
			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}
		return ous.toByteArray();
	}

	private byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[MAX_FILE_SIZE];
		int len;
		while ((len = in.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		return os.toByteArray();
	}

	/**
	 * Legge da un file inteso come risorsa applicativa (file contenuto
	 * nell'applicazione o nel progetto, quindi non un percorso assoluto ma solo
	 * il nome del file) nome.estensione e ritorna l'array di byte che
	 * compongono il file
	 * 
	 * Nome file + nome.estensione se si trova nello stesso pkg del chiamante.
	 * Altrimenti se si trova in un pkg diverso occorre definirlo nella forma
	 * nomePackage/nome.estensione esempio it/eng/inps/query.txt
	 * 
	 * (passato come getClass() dal chiamante)
	 * 
	 * (nome.estensione se si trova nello stesso pkg oppure se si trova in un
	 * package diverso dal chiamante occorre definirlo nella forma
	 * it/inpt/eng/nome.estensione
	 * 
	 * @param c
	 * @param nomeFile
	 * @return byte[]
	 * @throws Exception
	 */
	public <T> byte[] readFileAsResourceToByteArray(Class<T> c, String nomeFile) throws Exception {
		byte[] content = null;
		try {
			String pkg = "";
			if (nomeFile.indexOf(DOT) <= 1) {
				pkg = getString(c.getPackage().getName(), DOT);
				pkg = pkg.replace('.', '/');
				nomeFile = getString(pkg, nomeFile);
			}
			InputStream is = c.getClassLoader().getResourceAsStream(nomeFile);
			if (notNull(is)) {
				content = toByteArray(is);
			}
			if (Null(is))
				throw fileNotFound(nomeFile);
		} catch (IOException e) {
			throw fileTooBig();
		}
		return content;
	}

	private Exception fileTooBig() {
		return new Exception(getString("File troppo grande.Dimensione massima ammessa ", String.valueOf(MAX_FILE_SIZE / (1024 * 1024)), "MB"));
	}

	/**
	 * Dato un file (path+nome.estensione) legge il file come array di byte e lo
	 * restituisce
	 * 
	 * @param path
	 * @return byte[]
	 */

	public byte[] readContentIntoByteArray(String path) {
		byte[] bFile = null;
		try {
			File file = new File(path);
			bFile = new byte[(int) file.length()];
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (Exception e) {
			ex(e);
		}
		return bFile;
	}

	/**
	 * Se non esiste, crea la folder identificata da path
	 * 
	 * @param path
	 */
	public void createFolder(String path) {
		path = replace(path, SLASH, File.separator);
		if (like(path, File.separator)) {
			String dir = substring(path, null, false, false, File.separator, false, true);
			File folder = new File(dir);
			if (!folder.exists()) {
				folder.mkdirs();
			}
		}
	}

	/**
	 * Dato un file (path+nome.estensione) e un array di byte, scrive l'array di
	 * byte come contenuto nel file specificato
	 * 
	 * @param path
	 * @param content
	 */
	public void writeContentByteArrayToFile(String path, byte[] content) {
		try {
			File file = new File(path);
			createFolder(path);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(content);
			fileOutputStream.close();
		} catch (Exception e) {
			ex(e);
		}
	}

	/**
	 * Copia il file pathOrigine in pathDestinazione
	 * 
	 * @param pathOrigine
	 * @param pathDestinazione
	 */
	public void fileCopy(String pathOrigine, String pathDestinazione) {
		writeContentByteArrayToFile(pathDestinazione, readContentIntoByteArray(pathOrigine));
	}

	/**
	 * Esegue la compressione ZIP dei file pathOrigine nel file pathDestinazione
	 * che deve essere un file .zip
	 * 
	 * @param pathOrigine
	 * @param pathDestinazione
	 */
	public void zip(String pathDestinazione, String... pathOrigine) {
		try {
			List<String> srcFiles = arrayToList(pathOrigine);
			FileOutputStream fos = new FileOutputStream(pathDestinazione);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			for (String srcFile : srcFiles) {
				File fileToZip = new File(srcFile);
				FileInputStream fis = new FileInputStream(fileToZip);
				ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
				zipOut.putNextEntry(zipEntry);
				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
			}
			zipOut.close();
			fos.close();
		} catch (Exception e) {
			ex(e);
		}
	}

	/**
	 * Esegue la decompressione del file zip zipFile nella directory
	 * destinazione folderDestination
	 * 
	 * @param zipFile
	 * @param folderDestination
	 */
	public void unzip(String zipFile, String folderDestination) {
		try {
			byte[] buffer = new byte[1024];
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry zipEntry = zis.getNextEntry();
			File f = new File(folderDestination);
			if (!f.exists())
				f.mkdirs();
			while (notNull(zipEntry)) {
				File nuovo = newFile(f, zipEntry);
				FileOutputStream fos = new FileOutputStream(nuovo);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch (Exception e) {
			ex(e);
		}
	}

	private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());
		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();
		if (!destFilePath.startsWith(getString(destDirPath, File.separator))) {
			throw new IOException(getString("Entry is outside of the target dir: ", zipEntry.getName()));
		}
		return destFile;
	}

	/**
	 * Esegue la compressione della folder folderToZip nel file zip path
	 * (path+nome.zip)
	 * 
	 * @param folderTozip
	 * @param path
	 */
	public void zipFolder(String folderTozip, String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			File fileToZip = new File(folderTozip);
			zipFile(fileToZip, fileToZip.getName(), zipOut);
			zipOut.close();
			fos.close();
		} catch (Exception e) {
			ex(e);
		}
	}

	private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
		if (fileToZip.isHidden()) {
			return;
		}
		if (fileToZip.isDirectory()) {
			if (fileName.endsWith(SLASH)) {
				zipOut.putNextEntry(new ZipEntry(fileName));
				zipOut.closeEntry();
			} else {
				zipOut.putNextEntry(new ZipEntry(getString(fileName, SLASH)));
				zipOut.closeEntry();
			}
			File[] children = fileToZip.listFiles();
			for (File childFile : children) {
				zipFile(childFile, getString(fileName, SLASH, childFile.getName()), zipOut);
			}
			return;
		}
		FileInputStream fis = new FileInputStream(fileToZip);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zipOut.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		fis.close();
	}

	private boolean dateOverlap(DateInterval inter1, DateInterval inter2) {
		if (NullOR(inter1.getStart(), inter1.getEnd(), inter2.getStart(), inter2.getEnd()))
			return false;
		if ((inter1.getStart().getTime() <= inter2.getEnd().getTime()) && (inter2.getStart().getTime() <= inter1.getEnd().getTime()))
			return true;
		return false;
	}

	/**
	 * Dato aver caricato la lista di oggetti intervalliDate attraverso il
	 * metodo addDateInterval, ognuno dei quali rappresenta un range di date
	 * [start,end], restituisce true se tutti i range sono disgiunti fra loro,
	 * ossia non esistono sovrapposizioni di intervalli di date e false
	 * altrimenti
	 * 
	 * @return boolean
	 */
	public boolean validaDateIntervalNotOverlap(List<DateInterval> interval) {
		boolean overlap = false;
		for (int i = 0; i < interval.size(); i++) {
			DateInterval d = interval.get(i);
			for (int j = 0; j < interval.size(); j++) {
				if (i == j) {
					continue;
				} else {
					DateInterval k = interval.get(j);
					overlap = almenoUna(overlap, dateOverlap(d, k));
				}
			}

		}
		return !overlap;
	}

	/**
	 * Data una lista di DateInterval, restituisce una mappa Intervallo Data -
	 * Elenco Intervalli Date in sovrapposizione
	 * 
	 * @return Map<String, List<String>>
	 */

	public Map<String, List<String>> validaDateIntervalNotOverlapWithMessages(List<DateInterval> interval) {
		Map<String, List<String>> sovrapposizioni = new HashMap<String, List<String>>();
		for (int i = 0; i < interval.size(); i++) {
			DateInterval d = interval.get(i);
			for (int j = 0; j < interval.size(); j++) {
				if (i == j) {
					continue;
				} else {
					DateInterval k = interval.get(j);
					Boolean overlapping = dateOverlap(d, k);
					if (overlapping) {
						String periodoK = getString(dateToString(k.getStart()), DASHTRIM, dateToString(k.getEnd()));
						String periodoD = getString(dateToString(d.getStart()), DASHTRIM, dateToString(d.getEnd()));
						aggiungiMappaLista(sovrapposizioni, periodoK, periodoD);
					}
				}
			}

		}
		return sovrapposizioni;
	}

	/**
	 * Data una stringa che rappresenta una data, ritorna l'oggetto Date
	 * corrispondente:
	 * 
	 * @param s
	 * @return Date
	 */
	public Date toDate(String s) {
		Date d = null;
		if (isLike(s, ":"))
			d = stringToDatehhmmss(s);
		else
			d = stringToDate(s);
		return d;
	}

	/**
	 * Metodo alias di toDate(format)
	 * 
	 * @param s
	 * @param format
	 * @return Date
	 */
	public Date toDate(String s, String format) {
		return stringToDate(s, format);
	}

	/**
	 * Data una stringa che rappresenta una data, ritorna l'anno in formato
	 * stringa
	 * 
	 * @param s
	 * @return String
	 */
	public String getYear(String s) {
		String ret = null;
		try {
			Date d = toDate(s);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			ret = String.valueOf(c.get(Calendar.YEAR));
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * Ritorna l'anno in formato intero a partire da una data d
	 * 
	 * @param d
	 * @return Integer
	 */
	public Integer getYear(Date d) {
		if (Null(d))
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.YEAR);
	}

	/**
	 * Ritorna il mese in formato intero a partire da una data d
	 * 
	 * @param d
	 * @return Integer
	 */
	public Integer getMonth(Date d) {
		if (Null(d))
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * Ritorna l'ora in formato intero a partire da una data d
	 * 
	 * @param d
	 * @return Integer
	 */
	public Integer getHour(Date d) {
		if (Null(d))
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Ritorna i minuti in formato intero a partire da una data d
	 * 
	 * @param d
	 * @return Integer
	 */
	public Integer getMinute(Date d) {
		if (Null(d))
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * Ritorna i secondi in formato intero a partire da una data d
	 * 
	 * @param d
	 * @return Integer
	 */
	public Integer getSecond(Date d) {
		if (Null(d))
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.SECOND);
	}

	/**
	 * Ritorna il giorno in formato intero a partire da una data d
	 * 
	 * @param d
	 * @return Integer
	 */
	public Integer getDay(Date d) {
		if (Null(d))
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Data una stringa che rappresenta una data, ritorna il mese in formato
	 * stringa letterale
	 * 
	 * @param s
	 * @return String
	 */
	public String getMonth(String s) {
		String ret = null;
		try {
			Date d = toDate(s);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			ret = decodeMonth(String.valueOf(c.get(Calendar.MONTH) + 1));
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * Data una stringa che rappresenta una data, ritorna il giorno del mese in
	 * formato stringa
	 * 
	 * @param s
	 * @return String
	 */
	public String getDay(String s) {
		String ret = null;
		try {
			Date d = toDate(s);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			ret = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * Torna true se campo ï¿½ uguale a tutti i value oppure se campo ï¿½ nullo
	 * e tutti i value sono nulli. Se campo ï¿½ una collection la condizione ï¿½
	 * uguale significa che la collection contiene tutti i value. Se campo ï¿½
	 * una stringa la condizione ï¿½ uguale significa che la stringa ï¿½ uguale
	 * a tutti ivalue in base al metodo ignorecase. Se campo ï¿½ un BigDecimal
	 * la condizione ï¿½ uguale significa che ignora gli zeri dopo la virgola.
	 * Per gli altri casi la condizione ï¿½ uguale significa che ï¿½ rispettata
	 * la condizione in base al metodo equals;
	 * 
	 * @param campo
	 * @param value
	 * @return boolean
	 */
	public boolean isAll(Object campo, Object... value) {
		return almenoUna(tutte(Null(campo), Null(value)), isEqAll(campo, value));
	}

	private boolean isAll(String campo, String... value) {
		boolean esito = true;
		if (Null(campo))
			return false;
		if (value != null) {
			for (String el : value) {
				esito = tutte(esito, is(campo, el));
			}
		}
		return esito;
	}

	private boolean isEqAll(Object campo, Object... value) {
		boolean ret = false;
		if (campo instanceof Collection) {
			ret = collectionContainsAll((Collection) campo, value);
		} else {
			ret = isObjAll(campo, value);
		}
		if (campo instanceof String) {
			if (notNull(value)) {
				ret = isAll((String) campo, Arrays.asList(value).toArray(new String[value.length]));
			}
		}
		if (campo instanceof BigDecimal) {
			if (notNull(value)) {
				ret = isAllEscludiZeriDecimali((BigDecimal) campo, Arrays.asList(value).toArray(new BigDecimal[value.length]));
			}
		}
		return ret;
	}

	private boolean isAllEscludiZeriDecimali(BigDecimal campo, BigDecimal... value) {
		boolean notNull = true;
		if (Null(campo))
			return false;
		for (BigDecimal el : value) {
			notNull = notNull && (notNull(el) ? campo.stripTrailingZeros().equals(el.stripTrailingZeros()) : false);
		}
		return notNull;
	}

	private boolean collectionContainsAll(Collection c, Object... value) {
		if (Null(c))
			return false;
		boolean ret = true;
		for (Object k : value) {
			ret = ret && c.contains(k);
		}
		return ret;
	}

	private boolean isObjAll(Object campo, Object... value) {
		boolean notNull = true;
		if (Null(campo))
			return false;
		if (notNull(value)) {
			for (Object el : value) {
				notNull = tutte(notNull, campo.equals(el));
			}
		}
		return notNull;
	}

	/**
	 * Data una mappa aggiunge/aggiorna alla stessa la chiave elem con
	 * l'elemento o
	 * 
	 * @param mappa
	 * @param elem
	 * @param o
	 */
	public <K, T> void aggiungi(Map<K, T> mappa, K elem, T o) {
		if (null != mappa) {
			mappa.put(elem, o);
		}
	}

	/**
	 * Data una mappa rimuove dalla stessa la chiave elem con l'elemento o
	 * 
	 * @param mappa
	 * @param elem
	 */
	public <K, T> void rimuovi(Map<K, T> mappa, K elem) {
		if (null != mappa) {
			mappa.remove(elem);
		}
	}

	/**
	 * Data una lista di tipi Java (non custom quindi, tipo String, Integer,
	 * BigDecimal, ecc.) ritorna la stessa lista da cui ho rimosso quel valore
	 * 
	 * @param lista
	 * @param value
	 * @return List<K>
	 * @throws Exception
	 */
	public <K> List<K> cleanList(List<K> lista, K value) throws Exception {
		for (Iterator<K> iterator = safe(lista).iterator(); iterator.hasNext();) {
			K k = iterator.next();
			if (is(k, value))
				iterator.remove();
		}
		return lista;
	}

	/**
	 * Data una lista di tipi Java (non custom quindi, tipo String, Integer,
	 * BigDecimal, ecc.) ritorna la stessa lista da cui ho rimosso tutti i
	 * valori che non sono uguali a value
	 * 
	 * @param lista
	 * @param value
	 * @return List<K>
	 * @throws Exception
	 */
	public <K> List<K> cleanListNotEqual(List<K> lista, K value) throws Exception {
		for (Iterator<K> iterator = safe(lista).iterator(); iterator.hasNext();) {
			K k = iterator.next();
			if (isNot(k, value))
				iterator.remove();
		}
		return lista;
	}

	/**
	 * Data una lista di tipi Java (non custom quindi, tipo String, Integer,
	 * BigDecimal, ecc.) ritorna la stessa lista da cui ho rimosso i valori
	 * value passati
	 * 
	 * @param lista
	 * @param value
	 * @return List<K>
	 * @throws Exception
	 */
	public <K> List<K> cleanList(List<K> lista, K... value) throws Exception {
		for (K val : value) {
			lista = cleanList(lista, val);
		}
		return lista;
	}

	/**
	 * Sostituisce il valore valueToUpdate con il valore newValue mantenendo la
	 * posizione
	 * 
	 * @param <K>
	 * @param lista
	 * @param valueToUpdate
	 * @param newValue
	 * @return List<K>
	 * @throws Exception
	 */
	public <K> List<K> aggiornaListValue(List<K> lista, K valueToUpdate, K newValue) throws Exception {
		int h = lista.indexOf(valueToUpdate);
		if (h >= 0) {
			lista.set(h, newValue);
		}
		return lista;
	}

	/**
	 * Data una lista di oggetti di tipo T , torna una nuova lista da cui ha
	 * rimosso quegli oggetti la cui proprietï¿½ prop in dot notation ï¿½ uguale
	 * al valore val e lo sostituisce con newElement
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return torna la stessa lista da cui ho rimosso gli elementi della
	 *         proprieta props in dot notation che hanno valore val e lo
	 *         sostituisce con newElement List<T>
	 * @throws Exception
	 */
	public <T, K> List<T> aggiornaListValue(List<T> l1, String props, K val, T newElement) throws Exception {
		List<T> nuovaLista = new ArrayList<T>();
		for (Iterator<T> iterator = safe(l1).iterator(); iterator.hasNext();) {
			T t = iterator.next();
			if (is(get(t, props), val)) {
				iterator.remove();
				nuovaLista.add(newElement);
			}
		}
		return aggiungiList(nuovaLista, l1);
	}

	/**
	 * Data una mappa chiave/valore con valore lista di oggetti,
	 * aggiunge/aggiorna alla stessa chiave la corrispondente lista aggiungendo
	 * ad essa l'elemento o se la chiave ï¿½ presente , mentre se la chiave
	 * manca crea una nuova entry con una nuova lista contenente l'elemento o
	 * 
	 * @param mappa
	 * @param elem
	 * @param o
	 */
	public <K, T> void aggiungiMappaLista(Map<K, List<T>> mappa, K elem, T o) {
		if (null != mappa) {
			List<T> lista = safe(mappa.get(elem));
			lista.add(o);
			mappa.put(elem, lista);
		}
	}

	/**
	 * Data una mappa chiave/valore con valore lista di oggetti, rimuove
	 * l'elemento o dalla lista associata alla chiave elem
	 * 
	 * @param mappa
	 * @param elem
	 * @param o
	 * @throws Exception
	 */
	public <K, T> void rimuoviMappaLista(Map<K, List<T>> mappa, K elem, T o) {
		if (null != mappa) {
			List<T> lista = safe(mappa.get(elem));
			lista.remove(o);
			if (Null(lista)) {
				mappa.remove(elem);
			} else {
				mappa.put(elem, lista);
			}
		}
	}

	/**
	 * Data una lista esegue il controllo di nullitï¿½ e se null torna una Lista
	 * non nulla ma vuota
	 * 
	 * @param elenco
	 * @return List<K>
	 */
	public <K> List<K> safe(List<K> elenco) {
		List<K> emptyList = new ArrayList<K>();
		return elenco == null ? emptyList : elenco;
	}

	/**
	 * Data una LPist esegue il controllo di nullitï¿½ e se null torna una PList
	 * non nulla ma vuota
	 * 
	 * @param elenco
	 * @return PList<K>
	 */

	public <K> PList<K> safe(PList<K> elenco) {
		PList<K> emptyList = pl();
		return elenco == null ? emptyList : elenco;
	}

	/**
	 * Dato un Set esegue il controllo di nullitï¿½ e se null torna un Set non
	 * nullo ma vuoto
	 * 
	 * @param elenco
	 * @return Set<K>
	 */
	public <K> Set<K> safe(Set<K> elenco) {
		Set<K> emptySet = new HashSet<K>();
		return elenco == null ? emptySet : elenco;
	}

	/**
	 * Se l'Integer ï¿½ null torno ZERO
	 * 
	 * @param val
	 * @return Integer
	 */
	public Integer zeroIfNull(Integer val) {
		return Null(val) ? 0 : val;
	}

	/**
	 * Se il Long ï¿½ null torno ZERO
	 * 
	 * @param val
	 * @return Long
	 */
	public Long zeroIfNull(Long val) {
		return Null(val) ? 0 : val;
	}

	/**
	 * Data una stringa restituisce un'altra stringa in cui la sottostringa old
	 * ï¿½ sostituita con la sottostringa nuova
	 * 
	 * @param s
	 * @param old
	 * @param nuova
	 * @return String
	 */
	public String replace(String s, String old, String nuova) {
		s = emptyIfNull(s);
		return s.replace(emptyIfNull(old), emptyIfNull(nuova));
	}

	/**
	 * Data una rappresentazione stringa dd/mm/yyyy ottengo una data sql
	 * 
	 * @param d
	 * @return sql.Date
	 */
	public java.sql.Date stringToSQLDate(String d) {
		return getSQLDate(stringToDate(d));
	}

	/**
	 * Data una rappresentazione stringa dd/mm/yyyy hh:mm:ss ottengo una data
	 * sql
	 * 
	 * @param d
	 * @return Date
	 */
	public Date stringToSQLDatehhmmss(String d) {
		return getSQLDate(stringToDatehhmmss(d));
	}

	/**
	 * Data una mappa<K,List<T>> ottengo l'unica lista<T> corrispondente alla
	 * chiave K
	 * 
	 * @param mappa
	 * @param chiave
	 * @return List<T>
	 * @throws Exception
	 */
	public <K, T> List<T> findOneMapList(Map<K, List<T>> mappa, K chiave) throws Exception {
		List<T> lista = null;
		if (notNull(mappa)) {
			lista = mappa.get(chiave);
		}
		return lista;
	}

	/**
	 * Data una lista ritorna true se ha piï¿½ di un elemento
	 * 
	 * @param elenco
	 * @return boolean
	 */
	public <K> boolean moreThanOne(List<K> elenco) {
		return moreThan(elenco, 1);
	}

	/**
	 * Data una lista ritorna true se ha almeno un elemento
	 * 
	 * @param elenco
	 * @return boolean
	 */
	public <K> boolean atLeastOne(List<K> elenco) {
		return atLeast(elenco, 1);
	}

	/**
	 * Data una lista ritorna true se ha almeno i elementi
	 * 
	 * @param elenco
	 * @return boolean
	 */
	public <K> boolean atLeast(List<K> elenco, Integer i) {
		return safe(elenco).size() >= i;
	}

	/**
	 * Data una lista ritorna true se ha piï¿½ di i elementi
	 * 
	 * @param elenco
	 * @return boolean
	 */
	public <K> boolean moreThan(List<K> elenco, Integer i) {
		return safe(elenco).size() > i;
	}

	/**
	 * Date due stringhe data, torna il numero di giorni tra le due date
	 * 
	 * @param s1
	 * @param s2
	 * @return int
	 */
	public int daysBetween(String s1, String s2) {
		int gg = 0;
		try {
			Date d1 = stringToDate(s1);
			Date d2 = stringToDate(s2);
			gg = (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
		} catch (Exception e) {
		}
		return gg;
	}

	/**
	 * Data una lista di bean torna il bean ultimo della lista
	 * 
	 * @param l
	 * @return K
	 */
	public <K> K getLastElement(List<K> l) {
		if (notNull(l))
			return l.get(l.size() - 1);
		else
			return null;
	}

	/**
	 * Data una stringa e un numero n, ritorna la sottostringa limitata a n
	 * caratteri con l'aggiunta di tre puntini di sospensione
	 * 
	 * @param s
	 * @param n
	 * @return String
	 */
	public String cut(String s, Integer n) {
		String res = emptyIfNull(s);
		if (tutte(notNull(n), n > 0, res.length() > n)) {
			res = getString(res.substring(0, n), CUTTER);
		}
		return res;
	}

	/**
	 * Data una lista di tipi ritorna il numero di occorrenze dell'elemento e
	 * nella lista in base al metodo equals
	 * 
	 * @param <K>
	 * @param l
	 * @param e
	 * @return int
	 */
	public <K> int countInList(List<K> l, K e) {
		l = safe(l);
		return Collections.frequency(l, e);
	}

	/**
	 * Data una lista di bean, torna il numero di bean della lista che hanno la
	 * proprieta prop valorizzata con val
	 * 
	 * @param l
	 * @param prop
	 * @param val
	 * @return int
	 * @throws Exception
	 */
	public <K, T> int countInList(List<K> l, String prop, T val) throws Exception {
		List<?> lb = safe(listFromListBean(l, prop));
		return Collections.frequency(lb, val);
	}

	/**
	 * Torna la data attuale in formato DD/MM/YYYY
	 * 
	 * @return String
	 */
	public String nowString() {
		return dateToString(now());
	}

	/**
	 * Torna la data attuale in formato DD/MM/YYYY HH:mm:ss
	 * 
	 * @return String
	 */
	public String nowhhmmssString() {
		return dateToStringhhmmss(now());
	}

	/**
	 * Data una lista di stringhe, ritorna una stringa che ï¿½ la concatenazione
	 * di tutte le stringhe della lista, mettendo la stringa car come separatore
	 * tra le stringhe
	 * 
	 * @param l
	 * @param car
	 * @return String
	 */
	public String concatenaListaStringhe(List<String> l, String car) {
		car = emptyIfNull(car);
		StringBuffer sb = new StringBuffer();
		for (String p : safe(l)) {
			sb.append(p).append(car);
		}
		if (sb.length() > 1) {
			sb.setLength(sb.length() - car.length());
		}
		return sb.toString();
	}

	/**
	 * Limita l'indice h della lista l, alla dimensione della lista. Ossia h
	 * viene limitato alla size()-1 della lista se supera quel limite e a 0 se
	 * va al di sotto di 0
	 * 
	 * @param l
	 * @param h
	 * @return Integer
	 */
	public <K> Integer limitListIndex(List<K> l, Integer h) {
		Integer c = null;
		if (notNull(l)) {
			Integer endIndex = l.size() - 1;
			Integer startIndex = 0;
			c = limiteInferiore(h, startIndex);
			c = limiteSuperiore(c, endIndex);
		}
		return c;
	}

	/**
	 * Torna l'elemento della lista alla posizione i. Forza l'indice i
	 * all'intervallo [0,lista.size()-1]. Se la lista ï¿½ nulla o vuota torna
	 * null
	 * 
	 * @param <K>
	 * @param l
	 * @param i
	 * @return K
	 */
	public <K> K get(List<K> l, Integer i) {
		K elem = null;
		i = limitListIndex(l, i);
		if (notNull(i)) {
			elem = l.get(i);
		}
		return elem;
	}

	/**
	 * Torna la sottostringa della stringa s, [start,end] specificando con i
	 * booleani compresoStart e compresoEnd se start e end devono essere
	 * comprese oppure no e con i booleani lastStart e lastEnd se l'indice di
	 * riferimento deve essere il lastIndexOf oppure il primo semplice indexOf.
	 * Se end ï¿½ null la sottostringa va da start fino alla fine della stringa.
	 * Se start ï¿½ null la sottostringa va da inizio stringa fino a end. Se s
	 * ï¿½ null o se start e end sono entrambi null, allora ritorno la stringa
	 * passata come inalterata.
	 * 
	 * 
	 * @param s
	 * @param start
	 * @param compresoStart
	 * @param lastStart
	 * @param end
	 * @param compresoEnd
	 * @param lastEnd
	 * @return String
	 */
	public String substring(String s, String start, boolean compresoStart, boolean lastStart, String end, boolean compresoEnd, boolean lastEnd) {
		if (almenoUna(Null(s), Null(start, end)))
			return s;
		int indiceInizio = 0;
		int indiceFine = s.length();
		int inizio = 0;
		int fine = s.length();
		if (notNull(start)) {
			indiceInizio = lastStart ? s.toUpperCase().lastIndexOf(start.toUpperCase()) : s.toUpperCase().indexOf(start.toUpperCase());
			if (indiceInizio >= 0) {
				inizio = compresoStart ? indiceInizio : indiceInizio + start.length();
			}
			if (inizio < 0)
				inizio = 0;
		}
		if (notNull(end)) {
			indiceFine = lastEnd ? s.toUpperCase().lastIndexOf(end.toUpperCase()) : s.toUpperCase().indexOf(end.toUpperCase());
			if (indiceFine >= 0) {
				fine = compresoEnd ? indiceFine + end.length() : indiceFine;
			}
			if (fine < 0)
				fine = s.length();
		}
		if (inizio > fine)
			inizio = fine;
		s = s.substring(inizio, fine);
		return s;
	}

	/**
	 * Data la stringa s torna la stessa stringa tra apici
	 * 
	 * @param s
	 * @return String
	 */
	public String apiceString(String s) {
		s = emptyIfNull(s);
		s = replace(s, "'", "''");
		return getString("'", Matcher.quoteReplacement(s), "' ");
	}

	/**
	 * Concatena la rappresentazione stringa di ogni elemento della lista con il
	 * carattere car. Se apici ï¿½ true pone ogni elemento tra apici
	 * 
	 * @param <K>
	 * @param l
	 * @param car
	 * @param apici
	 * @return String
	 */
	public <K> String concatenaLista(List<K> l, String car, boolean apici) {
		car = emptyIfNull(car);
		StringBuffer sb = new StringBuffer();
		String v = null;
		for (K k : safe(l)) {
			if (apici) {
				v = apiceString(toStr(k));
			} else {
				v = toStr(k);
			}
			sb.append(v).append(car);
		}
		if (sb.length() > 1) {
			sb.setLength(sb.length() - car.length());
		}
		return sb.toString();
	}

	/**
	 * Restituisce la stringa passata mettendola in formato camelCase. La prima
	 * lettera della stringa ï¿½ minuscola, tutte quelle che seguono il
	 * carattere separatore, vengono capitalizzate
	 * 
	 * @param s
	 * @param separatore
	 * @return String
	 */
	public String toCamelCase(String s, String separatore) {
		String[] parts = s.split(Pattern.quote(separatore));
		String camelCaseString = "";
		for (String part : parts) {
			if (Null(part))
				continue;
			camelCaseString = getString(camelCaseString, toProperCase(part));
		}
		return camelCaseString;
	}

	/**
	 * Imposta a maiuscolo la prima lettera della stringa s
	 * 
	 * @param s
	 * @return String
	 */
	public String capFirstLetter(String s) {
		if (Null(s))
			return s;
		return getString(s.substring(0, 1).toUpperCase(), s.substring(1));
	}

	/**
	 * Imposta a minuscolo la prima lettera della stringa s
	 * 
	 * @param s
	 * @return String
	 */
	public String decapFirstLetter(String s) {
		if (Null(s))
			return s;
		return getString(s.substring(0, 1).toLowerCase(), s.substring(1));
	}

	private String toProperCase(String s) {
		if (Null(s))
			return s;
		return getString(s.substring(0, 1).toUpperCase(), s.substring(1).toLowerCase());
	}

	/**
	 * Rinomina il file o folder path in newPath (
	 * 
	 * @param path
	 * @param newPath
	 */
	public void renameFile(String path, String newPath) {
		File f = new File(newPath);
		File old = new File(path);
		old.renameTo(f);
	}

	/**
	 * Data una data torna una data aggiungendo ad essa il numero di anni
	 * indicato
	 * 
	 * @param data
	 * @param numeroAnni
	 * @return Date
	 */
	public Date addYears(Date data, Integer numeroAnni) {
		Date nuova = null;
		if (notNull(data)) {
			Calendar c = Calendar.getInstance();
			c.setTime(data);
			c.add(Calendar.YEAR, numeroAnni);
			nuova = c.getTime();
		}
		return nuova;
	}

	/**
	 * Data una data torna una data aggiungendo ad essa il numero di mesi
	 * indicato
	 * 
	 * @param data
	 * @param numeroMesi
	 * @return Date
	 */
	public Date addMonths(Date data, Integer numeroMesi) {
		Date nuova = null;
		if (notNull(data)) {
			Calendar c = Calendar.getInstance();
			c.setTime(data);
			c.add(Calendar.MONTH, numeroMesi);
			nuova = c.getTime();
		}
		return nuova;
	}

	private Set<File> getFiles(String path, boolean ricorsivo) {
		Set<File> fileTree = new HashSet<File>();
		if (Null(path))
			return fileTree;
		File dir = new File(path);
		if (dir == null || dir.listFiles() == null) {
			return fileTree;
		}
		for (File entry : dir.listFiles()) {
			if (entry.isFile())
				fileTree.add(entry);
			else {
				if (ricorsivo)
					fileTree.addAll(getFiles(entry.getAbsolutePath(), ricorsivo));
			}
		}
		return fileTree;
	}

	/**
	 * Dato un determinato percorso, ritorna un Set di oggetti file che sono
	 * tutti i file presenti nel percorso indicato e nelle relative
	 * sottocartelle (ricorsivo=true). I seguenti parametri fanno da filtro
	 * sull'insieme dei file tornati. I parametri null non vengono considerati
	 * come filtro. Like indica che il percorso deve contenere la stringa like.
	 * notLike indica che il percorso NON deve contenere la stringa notLike.
	 * Estensione (comprensiva del .) indica l'estensione dei file Data indica
	 * la data limite di ultima modifica dei file. Ossia vegono ritornati solo i
	 * file che hanno come data di ultima modifica una data uguale o posteriore
	 * alla data indicata
	 * 
	 * @param path
	 * @param like
	 * @param notLike
	 * @param estensione
	 * @param data
	 * @param ricorsivo
	 * @return Set<File>
	 * @throws IOException
	 */
	public Set<File> getFiles(String path, String like, String notLike, String estensione, String data, boolean ricorsivo) throws IOException {
		Date d = stringToDate(data);
		Set<File> f = getFiles(path, ricorsivo);
		Set<File> nuovaLista = new HashSet<File>();
		for (File file : safe(f)) {
			if (tutte(notNull(like), !isLike(file.getAbsolutePath(), like)))
				continue;
			if (tutte(notNull(notLike), isLike(file.getAbsolutePath(), notLike)))
				continue;
			if (tutte(notNull(estensione), !is(substring(file.getAbsolutePath(), DOT, true, true, null, false, true), estensione)))
				continue;
			Date ultimaModifica = stringToDate(sdf.format(file.lastModified()));
			if (!isDateAfter(d, ultimaModifica)) {
				nuovaLista.add(file);
			}
		}
		return nuovaLista;
	}

	/**
	 * Dato un determinato percorso, ritorna un Set di oggetti file che sono
	 * tutti i file presenti nel percorso indicato e nelle relative
	 * sottocartelle (ricorsivo=true). I seguenti parametri fanno da filtro
	 * sull'insieme dei file tornati. I parametri null non vengono considerati
	 * come filtro. Like indica che il percorso deve contenere la stringa like.
	 * notLike indica che il percorso NON deve contenere la stringa notLike.
	 * Estensione (comprensiva del .) indica l'estensione dei file Data indica
	 * la data limite di ultima modifica dei file. Ossia vegono ritornati solo i
	 * file che hanno come data di ultima modifica una data uguale o posteriore
	 * alla data indicata
	 * 
	 * @param path
	 * @param like
	 * @param notLike
	 * @param estensione
	 * @param data
	 * @return Set<File>
	 * @throws IOException
	 */
	public Set<File> getFiles(String path, String[] like, String[] notLike, String[] estensione, String data, boolean ricorsivo) throws IOException {
		Date d = stringToDate(data);
		Set<File> f = getFiles(path, ricorsivo);
		List<String> like_ = arrayToList(like);
		List<String> notLike_ = arrayToList(notLike);
		List<String> extension_ = arrayToList(estensione);
		Set<File> nuovaLista = new HashSet<File>();
		for (File file : safe(f)) {
			String absPath = file.getAbsolutePath();
			String ext = substring(absPath, DOT, true, true, null, false, true);
			if (tutte(notNull(like_), !stringLike(absPath, like_)))
				continue;
			if (tutte(notNull(notLike_), stringLike(absPath, notLike_)))
				continue;
			if (tutte(notNull(extension_), !is(extension_, ext)))
				continue;
			Date ultimaModifica = stringToDate(sdf.format(file.lastModified()));
			if (!isDateAfter(d, ultimaModifica)) {
				nuovaLista.add(file);
			}
		}
		return nuovaLista;
	}

	/**
	 * Data una lista di tipi Java (non custom quindi, tipo String, Integer,
	 * BigDecimal, ecc.) ritorna la stessa lista da cui ho rimosso quel valore
	 * che ï¿½ like value
	 * 
	 * @param lista
	 * @param value
	 * @return List<K>
	 * @throws Exception
	 */

	public <K> List<K> cleanListLike(List<K> lista, K value) throws Exception {
		for (Iterator<K> iterator = safe(lista).iterator(); iterator.hasNext();) {
			K k = iterator.next();
			if (tutte(notNull(value), isLike(k, value)))
				iterator.remove();
		}
		return lista;
	}

	/**
	 * Data una lista di tipi Java (non custom quindi, tipo String, Integer,
	 * BigDecimal, ecc.) ritorna la stessa lista da cui ho rimosso i valori like
	 * value passati
	 * 
	 * @param lista
	 * @param value
	 * @return List<K>
	 * @throws Exception
	 */
	public <K> List<K> cleanListLike(List<K> lista, K... value) throws Exception {
		for (K val : value) {
			lista = cleanListLike(lista, val);
		}
		return lista;
	}

	/**
	 * Data una lista di oggetti di tipo T , torna la stessa lista da cui ha
	 * rimosso quegli oggetti la cui proprietï¿½ prop in dot notation ï¿½ like
	 * valore val
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return List<T> torna la stessa lista da cui ho rimosso gli elementi
	 *         della proprieta props in dot notation che hanno valore val
	 * @throws Exception
	 */

	public <T, K> List<T> cleanListLike(List<T> l1, String props, K val) throws Exception {
		if (notNull(l1)) {
			for (Iterator<T> iterator = l1.iterator(); iterator.hasNext();) {
				T t = iterator.next();
				if (tutte(notNull(val), isLike(get(t, props), val)))
					iterator.remove();
			}
		}
		return l1;
	}

	/**
	 * Data una lista di oggetti di tipo T , torna la stessa lista da cui ha
	 * rimosso quegli oggetti la cui proprietï¿½ prop[i] in dot notation ï¿½
	 * like al val[i]
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return List<T> torna la stessa lista da cui ho rimosso gli elementi
	 *         delle proprieta props in dot notation che hanno valori val
	 * @throws Exception
	 */
	public <T, K> List<T> cleanListLike(List<T> l1, String[] props, K[] val) throws Exception {
		for (int i = 0; i < props.length; i++) {
			l1 = cleanListLike(l1, props[i], val[i]);
		}
		return l1;
	}

	/**
	 * Dato un set di oggetti di tipo T , torna lo stesso set da cui ha rimosso
	 * quegli oggetti la cui proprietï¿½ prop in dot notation ï¿½ like il valore
	 * val
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return Set<T> torna lo stesso Set da cui ho rimosso gli elementi della
	 *         proprieta props in dot notation che hanno valore val
	 * @throws Exception
	 */

	public <T, K> Set<T> cleanSetLike(Set<T> l1, String props, K val) throws Exception {
		if (notNull(l1)) {
			for (Iterator<T> iterator = l1.iterator(); iterator.hasNext();) {
				T t = iterator.next();
				if (tutte(notNull(val), isLike(get(t, props), val)))
					iterator.remove();
			}
		}
		return l1;
	}

	/**
	 * 
	 * Dato un set di oggetti di tipo T , torna lo stesso set da cui ha rimosso
	 * quegli oggetti la cui proprietï¿½ prop in dot notation ï¿½ like il valore
	 * val
	 * 
	 * @param l1
	 * @param props
	 * @param val
	 * @return Set<T> torna lo stesso Set da cui ho rimosso gli elementi delle
	 *         proprieta props in dot notation che hanno valori val
	 * @throws Exception
	 */

	public <T, K> Set<T> cleanSetLike(Set<T> l1, String[] props, K[] val) throws Exception {
		for (int i = 0; i < props.length; i++) {
			l1 = cleanSetLike(l1, props[i], val[i]);
		}
		return l1;
	}

	/**
	 * Data una lista List<K> ottengo una nuova lista di elementi K che abbiano
	 * la proprieta campo (in dot notation) che soddisfa la condizione in base
	 * all'operatore logico applicato e ai valori passati
	 * 
	 * @param <K>
	 * @param lista
	 * @param campo
	 * @param value
	 * @param op
	 * @return List<K>
	 * @throws Exception
	 */

	public <K, T> List<K> find(List<K> lista, String campo, Operatore op, T... value) throws Exception {
		List<K> shortList = new ArrayList<K>();
		for (K k : safe(lista)) {

			if (is(op, Operatore.IN)) {
				if (is(get(k, campo), value)) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.NOTIN)) {
				if (isNot(get(k, campo), value)) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.EQUAL)) {
				if (is(get(k, campo), value[0])) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.ISNULL)) {
				if (Null(get(k, campo))) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.ISNOTNULL)) {
				if (notNull(get(k, campo))) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.NOT_EQUAL)) {
				if (isNot(get(k, campo), value)) {
					shortList.add(k);
				}
			}
			if (is(op, Operatore.LIKE)) {
				if (isLike(get(k, campo), value)) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.NOTLIKE)) {
				if (!isLike(get(k, campo), value)) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.GT)) {
				if (isGreaterThan((Comparable) get(k, campo), (Comparable) value[0])) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.GTE)) {
				if (isGreaterEqualThan((Comparable) get(k, campo), (Comparable) value[0])) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.LT)) {
				if (isLessThan((Comparable) get(k, campo), (Comparable) value[0])) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.LTE)) {
				if (isLessEqualThan((Comparable) get(k, campo), (Comparable) value[0])) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.BETWEEN)) {
				boolean minoreUguale = isLessEqualThan((Comparable) get(k, campo), (Comparable) value[1]);
				boolean maggioreUguale = isGreaterEqualThan((Comparable) get(k, campo), (Comparable) value[0]);
				if (tutte(minoreUguale, maggioreUguale)) {
					shortList.add(k);
				}
			}
		}
		return shortList;
	}

	/**
	 * Data una lista List<K> ottengo il primo elemento K della lista di
	 * elementi K che abbiano la proprieta campo (in dot notation) che soddisfa
	 * la condizione in base all'operatore logico applicato e ai valori passati
	 * 
	 * @param <K>
	 * @param lista
	 * @param campo
	 * @param value
	 * @param value2
	 * @param op
	 * @return K
	 * @throws Exception
	 */

	public <K, T> K findOne(List<K> lista, String campo, T value, T value2, Operatore op) throws Exception {
		return getFirstElement(find(lista, campo, op, value, value2));
	}

	/**
	 * Data una lista List<K> ottengo una nuova lista di elementi K (non custom)
	 * che cui valori soddisfano la condizione in base all'operatore logico
	 * applicato e ai valori passati
	 * 
	 * @param <K>
	 * @param lista
	 * @param op
	 * @param value
	 * @return List<K>
	 * @throws Exception
	 */

	public <K> List<K> find(List<K> lista, Operatore op, Object... value) throws Exception {
		List<K> shortList = new ArrayList<K>();
		for (K k : safe(lista)) {

			if (is(op, Operatore.IN)) {
				if (is(k, value)) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.NOTIN)) {
				if (isNot(k, value)) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.EQUAL)) {
				if (is(k, value[0])) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.ISNULL)) {
				if (Null(k)) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.ISNOTNULL)) {
				if (notNull(k)) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.NOT_EQUAL)) {
				if (isNot(k, value)) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.LIKE)) {
				if (isLike(k, value)) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.NOTLIKE)) {
				if (!isLike(k, value)) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.GT)) {
				if (isGreaterThan((Comparable) k, (Comparable) value[0])) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.GTE)) {
				if (isGreaterEqualThan((Comparable) k, (Comparable) value[0])) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.LT)) {
				if (isLessThan((Comparable) k, (Comparable) value[0])) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.LTE)) {
				if (isLessEqualThan((Comparable) k, (Comparable) value[0])) {
					shortList.add(k);
				}
			}

			if (is(op, Operatore.BETWEEN)) {
				boolean minoreUguale = isLessEqualThan((Comparable) k, (Comparable) value[1]);
				boolean maggioreUguale = isGreaterEqualThan((Comparable) k, (Comparable) value[0]);
				if (tutte(minoreUguale, maggioreUguale)) {
					shortList.add(k);
				}
			}
		}
		return shortList;
	}

	/**
	 * Data una lista List<K> ottengo l'elemento K primo della lista di elementi
	 * K (non custom) i cui valori soddisfano la condizione in base
	 * all'operatore logico applicato e ai valori passati
	 * 
	 * @param <K>
	 * @param lista
	 * @param op
	 * @param value
	 * @param value1
	 * @return K
	 * @throws Exception
	 */

	public <K> K findOne(List<K> lista, Operatore op, K value, K value1) throws Exception {
		return getFirstElement(find(lista, op, value, value1));

	}

	private <T extends Comparable<T>> boolean isGreaterThan(T uno, T due) {
		if (tutte(Null(uno), notNull(due))) {
			return false;
		} else if (tutte(notNull(uno), Null(due))) {
			return true;
		}
		return uno.compareTo(due) > 0;
	}

	private <T extends Comparable<T>> boolean isGreaterEqualThan(T uno, T due) {
		if (tutte(Null(uno), notNull(due))) {
			return false;
		} else if (tutte(notNull(uno), Null(due))) {
			return true;
		}
		return uno.compareTo(due) >= 0;
	}

	private <T extends Comparable<T>> boolean isLessThan(T uno, T due) {
		if (tutte(Null(uno), notNull(due))) {
			return true;
		} else if (tutte(notNull(uno), Null(due))) {
			return false;
		}
		return uno.compareTo(due) < 0;
	}

	private <T extends Comparable<T>> boolean isLessEqualThan(T uno, T due) {
		if (tutte(Null(uno), notNull(due))) {
			return true;
		} else if (tutte(notNull(uno), Null(due))) {
			return false;
		}
		return uno.compareTo(due) <= 0;
	}

	/**
	 * Dato un Long valore timestamp, ritorna un oggetto Date
	 * 
	 * @param l
	 * @return Date
	 */
	public Date getDate(Long l) {
		Date d = null;
		if (notNull(l)) {
			d = new Date(l);
		}
		return d;
	}

	/**
	 * Torna true se d ï¿½ 1
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean one(BigDecimal d) {
		return notNull(d) && d.compareTo(ONE) == 0;
	}

	/**
	 * Torna true se d ï¿½ 1
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean one(Long d) {
		return notNull(d) && d.longValue() == 1l;
	}

	/**
	 * Torna true se d ï¿½ 1
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean one(Integer d) {
		return notNull(d) && d.intValue() == 1;
	}

	/**
	 * Torna il primo valore non nullo tra i valori passati secondo l'ordine dei
	 * valori passati
	 * 
	 * @param <T>
	 * @param vals
	 * @return T
	 */
	public <T> T valIfNull(T... vals) {
		T t = null;
		for (T item : vals) {
			if (notNull(item)) {
				t = item;
				break;
			}
		}
		return t;

	}

	/**
	 * Aggiunge un oggetto DateInteval(inizio,fine) alla lista intervalliDate
	 * che sarï¿½ poi sottoposta a validazione attraverso il metodo
	 * validaDateIntervalNotOverlap
	 * 
	 * @param intervalliDate
	 * @param inizio
	 * @param fine
	 * @return List<DateInterval>
	 * @throws Exception
	 */
	public List<DateInterval> addDateInterval(List<DateInterval> intervalliDate, String inizio, String fine) throws Exception {
		if (null != intervalliDate) {
			intervalliDate.add(new DateInterval(inizio, fine));
		}
		return intervalliDate;
	}

	/**
	 * Data una lista di bean e le proprieta prop, torna l'elemento della lista
	 * che ha il valore massimo di quelle proprieta
	 * 
	 * 
	 * @param <T>
	 * @param l1
	 * @param prop
	 * @return T
	 * @throws Exception
	 */
	public <T> T maxBean(List<T> l1, String... prop) throws Exception {
		return getLastElement(sort(l1, prop));
	}

	/**
	 * Data una lista di bean e le proprieta prop, torna l'elemento della lista
	 * che ha il valore minimo di quelle proprieta
	 * 
	 * @param <T>
	 * @param l1
	 * @param prop
	 * @return T
	 * @throws Exception
	 */
	public <T> T minBean(List<T> l1, String... prop) throws Exception {
		return getFirstElement(sort(l1, prop));
	}

	/**
	 * Ritorna true se la proprieta prop dei bean della lista ha il valore value
	 * per tutti gli elementi della lista
	 * 
	 * @param l1
	 * @param prop
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	public <T> boolean isAllListBeanValues(List<T> l1, String prop, Object value) throws Exception {
		if (Null(l1))
			return false;
		boolean alls = true;
		for (T t : safe(l1)) {
			if (isNot(get(t, prop), value)) {
				alls = false;
				break;
			}
		}
		return alls;
	}

	/**
	 * Ritorna true se la proprieta prop dei bean della lista non ha il valore
	 * value per tutti gli elementi della lista
	 * 
	 * @param <T>
	 * @param l1
	 * @param prop
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	public <T> boolean isNoneListBeanValues(List<T> l1, String prop, Object value) throws Exception {
		if (Null(l1))
			return false;
		boolean none = true;
		for (T t : safe(l1)) {
			if (is(get(t, prop), value)) {
				none = false;
				break;
			}
		}
		return none;
	}

	/**
	 * Ritorna true se la proprieta prop dei bean della lista ha il valore value
	 * per almeno uno degli elementi della lista
	 * 
	 * @param <T>
	 * @param l1
	 * @param prop
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	public <T> boolean isAnyListBeanValues(List<T> l1, String prop, Object value) throws Exception {
		if (Null(l1))
			return false;
		boolean any = false;
		for (T t : safe(l1)) {
			if (is(get(t, prop), value)) {
				any = true;
				break;
			}
		}
		return any;
	}

	/**
	 * Ritorna true se la lista ha tutti i suoi elementi con valore value
	 * 
	 * @param <T>
	 * @param l1
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	public <T> boolean isAllListValues(List<T> l1, T value) throws Exception {
		if (Null(l1))
			return false;
		boolean alls = true;
		for (T t : safe(l1)) {
			if (isNot(t, value)) {
				alls = false;
				break;
			}
		}
		return alls;
	}

	/**
	 * Ritorna true se la lista non ha alcun elemento con valore value
	 * 
	 * @param <T>
	 * @param l1
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	public <T> boolean isNoneListValues(List<T> l1, T value) throws Exception {
		if (Null(l1))
			return false;
		boolean none = true;
		for (T t : safe(l1)) {
			if (is(t, value)) {
				none = false;
				break;
			}
		}
		return none;
	}

	/**
	 * Ritorna true se la lista ha almeno un elemento con valore value
	 * 
	 * @param <T>
	 * @param l1
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	public <T> boolean isAnyListValues(List<T> l1, T value) throws Exception {
		if (Null(l1))
			return false;
		boolean any = false;
		for (T t : safe(l1)) {
			if (is(t, value)) {
				any = true;
				break;
			}
		}
		return any;
	}

	/**
	 * Legge dal classpath la proprietï¿½ propName dal file di properties file
	 * 
	 * 
	 * Looks up a resource named 'name' in the classpath. The resource must map
	 * to a file with .properties extention. The name is assumed to be absolute
	 * and can use either "/" or "." for package segment separation with an
	 * optional leading "/" and optional ".properties" suffix. Thus, the
	 * following names refer to the same resource:
	 * 
	 * @param c
	 *            (passato come getClass() dal chiamante)
	 * @param file
	 * @param propName
	 * @return String
	 * @throws IOException
	 */
	public <T> String readPropertyFromClassPath(Class<T> c, String file, String propName) throws IOException {
		return PropertyLoader.loadProperties(file, c).getProperty(propName);
	}

	/**
	 * Se la stringa s ï¿½ uguale ad uno dei valori della stringa valori,
	 * traduce la stringa s come tradottoIn, altrimenti ritorna la stringa s
	 * originale non tradotta
	 * 
	 * @param s
	 * @param valori
	 * @param tradottoIn
	 * @return String
	 */
	public String translate(String s, String tradottoIn, String... valori) {
		String ret = s;
		if (is(s, valori))
			ret = tradottoIn;
		return ret;
	}

	/**
	 * Se la stringa s in like ad uno dei valori della stringa valori, traduce
	 * la stringa s come tradottoIn, altrimenti ritorna la stringa s originale
	 * non tradotta
	 * 
	 * @param s
	 * @param valori
	 * @param tradottoIn
	 * @return String
	 */
	public String translateLike(String s, String tradottoIn, String... valori) {
		String ret = s;
		if (isLike(s, (Object[]) valori))
			ret = tradottoIn;
		return ret;
	}

	/**
	 * Se la stringa s ï¿½ uguale ad uno dei valori della stringa valori,
	 * traduce la stringa s come tradottoIn qualora condizione di traduzione ï¿½
	 * true, altrimenti ritorna la stringa s originale non tradotta
	 * 
	 * @param s
	 * @param valori
	 * @param condizioneDiTraduzione
	 * @param tradottoIn
	 * @return String
	 */
	public String translateWithCondition(String s, Boolean condizioneDiTraduzione, String tradottoIn, String... valori) {
		String ret = s;
		if (tutte(condizioneDiTraduzione, is(s, valori)))
			ret = tradottoIn;
		return ret;
	}

	/**
	 * Se la stringa s ï¿½ like uno dei valori della stringa valori, traduce la
	 * stringa s come tradottoIn qualora condizione di traduzione ï¿½ true,
	 * altrimenti ritorna la stringa s originale non tradotta
	 * 
	 * @param s
	 * @param valori
	 * @param condizioneDiTraduzione
	 * @param tradottoIn
	 * @return String
	 */
	public String translateLikeWithCondition(String s, Boolean condizioneDiTraduzione, String tradottoIn, String... valori) {
		String ret = s;
		if (tutte(condizioneDiTraduzione, isLike(s, (Object[]) valori)))
			ret = tradottoIn;
		return ret;
	}

	/**
	 * Valida un indirizzo email
	 * 
	 * @param emailAddress
	 * @param nullable
	 * @return boolean
	 */
	public boolean validaEmail(String emailAddress, Boolean nullable) {
		if (nullable && Null(emailAddress))
			return true;
		if (!nullable && Null(emailAddress))
			return false;
		boolean ret = false;
		Matcher mtch = emailPattern.matcher(emailAddress);
		if (mtch.matches()) {
			ret = true;
		}
		return ret;
	}

	/**
	 * Data una stringa e un carattere, ritorna il numero di occorrenze di quel
	 * carattere all'interno della stringa
	 * 
	 * @param s
	 * @param t
	 * @return Integer
	 */
	public Integer howManyChars(String s, char t) {
		Integer ret = 0;
		if (notNull(s)) {
			char[] c_ = s.toCharArray();
			for (char car : c_) {
				if (t == car) {
					ret++;
				}
			}
		}
		return ret;
	}

	/**
	 * Metodo che data una lista la trasforma in una PList
	 * 
	 * @param <K>
	 * @param l
	 * @return PList<K>
	 */
	public <K> PList<K> toPList(List<K> l) {
		return pl(safe(l));
	}

	/**
	 * Metodo che data una mappa la trasforma in una PMap
	 * 
	 * @param <K>
	 * @param mp
	 * @return PMap<K,V>
	 */
	public <K, V> PMap<K, V> toPMap(Map<K, V> mp) {
		return new PHashMap<K, V>(mp);
	}

	/**
	 * Metodo che data una lista di oggetti BaseEntity la trasforma in una PList
	 * di oggetti BaseEntity
	 * 
	 * @param <K>
	 * @param l
	 * @return PList<K>
	 */
	public <K extends BaseEntity> PList<K> toPListEnt(List<K> l) {
		return pl(safe(l));
	}

	/**
	 * Ritorna un oggetto ValidationBox
	 * 
	 * @return ValidationBox
	 */
	public ValidationBox getValidationBox() {
		return new ValidationBox();
	}

	/**
	 * Ritorna un oggetto DateIntervalBox
	 * 
	 * @return DateIntervalBox
	 */
	public DateIntervalBox getDateIntervalBox() {
		return new DateIntervalBox();
	}

	/**
	 * Ritorna un oggetto DateInterval [inizio,fine]
	 * 
	 * @param inizio
	 * @param fine
	 * @return DateInterval
	 * @throws Exception
	 */
	public DateInterval getDateInterval(String inizio, String fine) throws Exception {
		return new DateInterval(inizio, fine);
	}

	/**
	 * Ritorna un oggetto DateInterval [inizio,fine]
	 * 
	 * @param inizio
	 * @param fine
	 * @return DateInterval
	 * @throws Exception
	 */
	public DateInterval getDateInterval(Date inizio, Date fine) throws Exception {
		return new DateInterval(inizio, fine);
	}

	/**
	 * Ritorna un oggetto PArrayList vuoto
	 * 
	 * @param <K>
	 * @return PList<K>
	 */
	public <K> PList<K> getPList() {
		return new PArrayList<K>();
	}

	/**
	 * Alias di getPList()
	 * 
	 * @param <K>
	 * @return PList<K>
	 */
	public <K> PList<K> pl() {
		return getPList();
	}

	/**
	 * Ritorna un oggetto di tipo PList con il limite di elementi impostato a
	 * limite (elementi aggiunti tramite addElement)
	 * 
	 * @param <K>
	 * @param limite
	 * @return PList<K>
	 */
	public <K> PList<K> getPList(Integer limite) {
		return new PArrayList<K>().setLimit(limite);
	}

	/**
	 * Alias di getPList(Integer limite)
	 * 
	 * @param <K>
	 * @param limite
	 * @return PList<K>
	 */
	public <K> PList<K> plLimit(Integer limite) {
		return getPList(limite);
	}

	/**
	 * Ritorna un oggetto PHashMap vuoto
	 * 
	 * @param <K>
	 * @param <V>
	 * @return PMap<K, V>
	 */
	public <K, V> PMap<K, V> getPMap() {
		return new PHashMap<K, V>();
	}

	/**
	 * Alias di getPMap();
	 * 
	 * @param <K>
	 * @param <V>
	 * @return PMap<K,V>
	 */
	public <K, V> PMap<K, V> pmap() {
		return getPMap();
	}

	/**
	 * Ritorna un oggetto PHashMap vuoto con valori di tipo PList<V>
	 * 
	 * @param <K>
	 * @param <V>
	 * @return PMap<K, PList<V>>
	 */
	public <K, V> PMap<K, PList<V>> getPMapList() {
		return new PHashMap<K, PList<V>>();
	}

	/**
	 * Alias di getPMapList()
	 * 
	 * @param <K>
	 * @param <V>
	 * @return PMap<K, PList<V>>
	 */
	public <K, V> PMap<K, PList<V>> pmapl() {
		return getPMapList();
	}

	/**
	 * Ritorna un oggetto PArrayList<String> vuoto
	 * 
	 * @return PList<String>
	 */
	public PList<String> getPListString() {
		return new PArrayList<String>();
	}

	/**
	 * Alias di getPListString
	 * 
	 * @return PList<String>
	 */
	public PList<String> plstr() {
		return getPListString();
	}

	/**
	 * Ritorna un oggetto PArrayList a partire dalla lista list
	 * 
	 * @param list
	 * @return Collection<K>
	 */
	public <K> PList<K> getPList(Collection<K> list) {
		return new PArrayList<K>(safe(list));
	}

	/**
	 * Alias di getPList(Collection<K> list)
	 * 
	 * @return PList<String>
	 */
	public <K> PList<K> pl(Collection<K> list) {
		return getPList(list);
	}

	/**
	 * Ritorna un oggetto PArrayList inizializzato con gli elementi items
	 * 
	 * @param <K>
	 * @param items
	 * @return PList<K>
	 */
	public <K> PList<K> getPList(K... items) {
		return new PArrayList<K>(items);
	}

	/**
	 * Alias di getPList(K...items)
	 * 
	 * @param <K>
	 * @param items
	 * @return PList<K>
	 */
	public <K> PList<K> pl(K... items) {
		return getPList(items);
	}

	/**
	 * Ritorna un oggetto PArrayList di stringhe inizializzato con gli elementi
	 * items
	 * 
	 * @param items
	 * @return PList<String>
	 */
	public PList<String> getPListString(String... items) {
		return new PArrayList<String>(items);
	}

	/**
	 * Alias di getPListString(String...items)
	 * 
	 * @param items
	 * @return PList<String>
	 */
	public PList<String> plstr(String... items) {
		return getPListString(items);
	}

	/**
	 * Da una lista di bean ritorna una lista di oggetti della proprietï¿½ prop
	 * 
	 * @param <T>
	 * @param l
	 * @param prop
	 * @return List<R>
	 * @throws Exception
	 */
	public <T, R> List<R> narrow(List<T> l, String prop) throws Exception {
		return listFromListBean(l, prop);
	}

	/**
	 * Da una lista di bean ritorna una lista di oggetti della proprietï¿½ prop
	 * con valori distinti
	 * 
	 * @param <T>
	 * @param <R>
	 * @param l
	 * @param props
	 * @return List<R>
	 * @throws Exception
	 */
	public <T, R> List<R> narrowDistinct(List<T> l, String props) throws Exception {
		return listDistinctFromListBean(l, props);
	}

	/**
	 * Esegue la serializzazione dell'oggetto o nel file indicato
	 * 
	 * @param o
	 * @param file
	 */
	public void serializeToFile(Object o, String file) {
		FileOutputStream out = null;
		ObjectOutputStream oos = null;
		try {
			out = new FileOutputStream(file);
			oos = new ObjectOutputStream(out);
			oos.writeObject(o);
			oos.flush();
		} catch (Exception e) {
			logError("Problem serializing: ", e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
			try {
				oos.close();
			} catch (IOException e) {
			}
		}

	}

	/**
	 * Esegue la deserializzazione leggendo da file e ricostruendo l'oggetto
	 * precedentemente serializzato nel file stesso
	 * 
	 * @param file
	 * @return Object
	 */
	public Object deserializeFromFile(String file) {
		Object o = null;
		FileInputStream in = null;
		ObjectInputStream ois = null;
		try {
			in = new FileInputStream(file);
			ois = new ObjectInputStream(in);
			o = ois.readObject();
		} catch (Exception e) {
			logError("Problem deserializing: ", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
			}
			try {
				ois.close();
			} catch (IOException e) {
			}
		}
		return o;
	}

	/**
	 * Se negativo torna 0, altrimenti torna il valore stesso
	 * 
	 * @param val
	 * @return Long
	 */
	public Long zeroIfNegative(Long val) {
		return Null(val) ? null : val < 0 ? 0 : val;
	}

	/**
	 * Se negativo torna 0, altrimenti torna il valore stesso
	 * 
	 * @param val
	 * @return Integer
	 */
	public Integer zeroIfNegative(Integer val) {
		return Null(val) ? null : val < 0 ? 0 : val;
	}

	/**
	 * Data una lista l, ritorna una lista limitata ai primi n elementi
	 * eliminando i successivi
	 * 
	 * @param <K>
	 * @param l
	 * @param n
	 * @return List<K>
	 */
	public <K> List<K> cutToFirst(List<K> l, Integer n) {
		l = safe(l);
		return l.subList(0, limiteInferiore(0, limiteSuperiore(zeroIfNull(n), l.size())));
	}

	/**
	 * Ritorna una da cui ha rimosso i primi n elementi della lista l
	 * 
	 * @param <K>
	 * @param l
	 * @param n
	 * @return List<K>
	 */
	public <K> List<K> skip(List<K> l, Integer n) {
		l = safe(l);
		return cutToLast(l, l.size() - n);
	}

	/**
	 * Ritorna la lista da cui ha rimosso il primo elemento
	 * 
	 * @param <K>
	 * @param l
	 * @return List<K>
	 */
	public <K> List<K> skipFirst(List<K> l) {
		return skip(l, 1);
	}

	/**
	 * Dato un set l, ritorna lo stesso set limitato ai primi n elementi
	 * eliminando i successivi
	 * 
	 * @param <K>
	 * @param l
	 * @param n
	 * @return Set<K>
	 */
	public <K> Set<K> cutToFirst(Set<K> l, Integer n) {
		l = safe(l);
		return listToSet(setToList(l).cutToFirst(n));
	}

	/**
	 * Ritorna una stringa che indica il tempo trascorso tra due date
	 * 
	 * @param startDate
	 * @param endDate
	 * @return String
	 */
	public String elapsedTime(Date startDate, Date endDate) {
		if (NullOR(startDate, endDate))
			return "Tempo non rilevato";
		return computeElapsedTime(startDate, endDate);
	}

	private String computeElapsedTime(Date startDate, Date endDate) {
		long different = endDate.getTime() - startDate.getTime();
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;

		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;
		different = different % secondsInMilli;
		long elapsedMilliSeconds = different;
		String time = "";
		if (elapsedDays > 0) {
			time = String.format(livelloGiorni, elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds, elapsedMilliSeconds);
		} else if (elapsedHours > 0) {
			time = String.format(livelloOre, elapsedHours, elapsedMinutes, elapsedSeconds, elapsedMilliSeconds);
		} else if (elapsedMinutes > 0) {
			time = String.format(livelloMinuti, elapsedMinutes, elapsedSeconds, elapsedMilliSeconds);
		} else if (elapsedSeconds > 0) {
			time = String.format(livelloSecondi, elapsedSeconds, elapsedMilliSeconds);
		} else {
			time = String.format(livelloMilliSecondi, elapsedMilliSeconds);
		}
		return time;
	}

	/**
	 * Ritorna una stringa che indica il tempo trascorso corrispondente ai
	 * millisecondi indicati
	 * 
	 * @param milliseconds
	 * @return String
	 */
	public String elapsedTime(Long milliseconds) {
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = milliseconds / daysInMilli;
		milliseconds = milliseconds % daysInMilli;

		long elapsedHours = milliseconds / hoursInMilli;
		milliseconds = milliseconds % hoursInMilli;

		long elapsedMinutes = milliseconds / minutesInMilli;
		milliseconds = milliseconds % minutesInMilli;

		long elapsedSeconds = milliseconds / secondsInMilli;
		milliseconds = milliseconds % secondsInMilli;
		long elapsedMilliSeconds = milliseconds;
		String time = "";
		if (elapsedDays > 0) {
			time = String.format(livelloGiorni, elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds, elapsedMilliSeconds);
		} else if (elapsedHours > 0) {
			time = String.format(livelloOre, elapsedHours, elapsedMinutes, elapsedSeconds, elapsedMilliSeconds);
		} else if (elapsedMinutes > 0) {
			time = String.format(livelloMinuti, elapsedMinutes, elapsedSeconds, elapsedMilliSeconds);
		} else if (elapsedSeconds > 0) {
			time = String.format(livelloSecondi, elapsedSeconds, elapsedMilliSeconds);
		} else {
			time = String.format(livelloMilliSecondi, elapsedMilliSeconds);
		}
		return time;
	}

	/**
	 * Ritorna un oggetto Time corrispondete alla durata in millisecondi
	 * indicata dal parametro milliseconds
	 * 
	 * @param milliseconds
	 * @return Time
	 */
	public Time elapsedTimeTime(Long milliseconds) {
		return getTimeFromElapsedTime(elapsedTime(milliseconds));
	}

	/**
	 * Ritorna una stringa che indica il tempo trascorso tra la data inzio
	 * passata e la data al momento dell'esecuzione del metodo stesso
	 * 
	 * @param startDate
	 * @return String
	 */
	public String elapsedTime(Date startDate) {
		if (Null(startDate))
			return "La data inizio ï¿½ nulla";
		return computeElapsedTime(startDate, now());
	}

	private Long getTimeValue(String s) {
		return getLong(substring(s, null, false, false, SPACE, false, false).trim());
	}

	private Time getTimeFromElapsedTime(String elapsed) {
		PList<String> l = toListString(elapsed, COMMA);
		Time t = new Time();
		for (String s : safe(l)) {
			if (isLike(s, DAYS)) {
				t.addDays(getTimeValue(s));
			}
			if (isLike(s, HOURS)) {
				t.addHours(getTimeValue(s));
			}
			if (isLike(s, MINUTES)) {
				t.addMinutes(getTimeValue(s));
			}
			if (isLike(s, MILLISECONDS)) {
				t.addMilliseconds(getTimeValue(s));
			} else if (isLike(s, SECONDS)) {
				t.addSeconds(getTimeValue(s));
			}

		}
		return t;
	}

	/**
	 * Ritorna l'oggetto Time corrispondente al tempo trascorso da startDate a
	 * now()
	 * 
	 * @param startDate
	 * @return Time
	 */
	public Time elapsed(Date startDate) {
		return getTimeFromElapsedTime(elapsedTime(startDate));
	}

	/**
	 * Ritorna l'oggetto Time corrispondente al tempo trascorso da startDate a
	 * endDate
	 * 
	 * @param startDate
	 * @param endDate
	 * @return Time
	 */
	public Time elapsed(Date startDate, Date endDate) {
		return getTimeFromElapsedTime(elapsedTime(startDate, endDate));
	}

	/**
	 * Data una lista di oggetti che implementano l'interfaccia Validator, torna
	 * la lista ripulita di quegli oggetti che NON SODDISFANO la validazione
	 * implementata nel metodo validate()
	 * 
	 * @param l1
	 * @return List<Validator>
	 * @throws Exception
	 */
	public List<Validator> cleanList(List<Validator> l1) throws Exception {
		if (notNull(l1)) {
			for (Iterator<Validator> iterator = l1.iterator(); iterator.hasNext();) {
				Validator t = iterator.next();
				if (!t.validate())
					iterator.remove();
			}
		}
		return l1;
	}

	/**
	 * Dato un set di oggetti che implementano l'interfaccia Validator, torna il
	 * set ripulito di quegli oggetti che NON SODDISFANO la validazione
	 * implementata nel metodo validate()
	 * 
	 * @param l1
	 * @return Set<Validator>
	 * @throws Exception
	 */
	public Set<Validator> cleanSet(Set<Validator> l1) throws Exception {
		if (notNull(l1)) {
			for (Iterator<Validator> iterator = l1.iterator(); iterator.hasNext();) {
				Validator t = iterator.next();
				if (!t.validate())
					iterator.remove();
			}
		}
		return l1;
	}

	/**
	 * Sostituisce l'ultima occorrenza di toReplace nella stringa string con la
	 * nuova replacement
	 * 
	 * @param string
	 * @param toReplace
	 * @param replacement
	 * @return String
	 */
	public String replaceLast(String string, String toReplace, String replacement) {
		int pos = string.lastIndexOf(toReplace);
		if (pos > -1) {
			return getString(string.substring(0, pos), replacement, string.substring(pos + toReplace.length()));
		} else {
			return string;
		}
	}

	/**
	 * Data una lista di numeri qualsiasi, ritorna la corrispondente lista di
	 * oggetti String
	 * 
	 * @param <K>
	 * @param l
	 * @return PList<String>
	 */
	public <K extends Number> PList<String> toListString(List<K> l) {
		PList<String> elenco = getPListString();
		for (Number item : safe(l)) {
			if (notNull(item)) {
				elenco.add(item.toString());
			} else {
				elenco.add(null);
			}
		}
		return elenco;
	}

	/**
	 * Data una lista di stringhe, ritorna la corrispondente lista di oggetti
	 * Long
	 * 
	 * @param l
	 * @return PList<Long>
	 */
	public PList<Long> toListLong(List<String> l) {
		PList<Long> elenco = pl();
		for (String item : safe(l)) {
			if (notNull(item)) {
				elenco.add(getLong(item.trim()));
			} else {
				elenco.add(null);
			}
		}
		return elenco;
	}

	/**
	 * Data una lista di stringhe, ritorna la corrispondente lista di oggetti
	 * Integer
	 * 
	 * @param l
	 * @return PList<Integer>
	 */
	public PList<Integer> toListInteger(List<String> l) {
		PList<Integer> elenco = pl();
		for (String item : safe(l)) {
			if (notNull(item)) {
				elenco.add(getInteger(item.trim()));
			} else {
				elenco.add(null);
			}
		}
		return elenco;
	}

	/**
	 * Data una lista di stringhe, ritorna la corrispondente lista di oggetti
	 * Double
	 * 
	 * @param l
	 * @return PList<Double>
	 */
	public PList<Double> toListDouble(List<String> l) {
		PList<Double> elenco = pl();
		for (String item : safe(l)) {
			if (notNull(item)) {
				elenco.add(getDouble(item.trim()));
			} else {
				elenco.add(null);
			}
		}
		return elenco;
	}

	/**
	 * Data una lista di stringhe, ritorna la corrispondente lista di oggetti
	 * Float
	 * 
	 * @param l
	 * @return PList<Float>
	 */
	public PList<Float> toListFloat(List<String> l) {
		PList<Float> elenco = pl();
		for (String item : safe(l)) {
			if (notNull(item)) {
				elenco.add(getFloat(item.trim()));
			} else {
				elenco.add(null);
			}
		}
		return elenco;
	}

	/**
	 * Data una lista di stringhe, ritorna la corrispondente lista di oggetti
	 * BigDecimal
	 * 
	 * @param l
	 * @return PList<BigDecimal>
	 */
	public PList<BigDecimal> toListBigDecimal(List<String> l) {
		PList<BigDecimal> elenco = pl();
		for (String item : safe(l)) {
			if (notNull(item)) {
				elenco.add(getBigDecimal(item.trim()));
			} else {
				elenco.add(null);
			}
		}
		return elenco;
	}

	/**
	 * Torna l'Integer corrispondente al valore s passato come stringa o zero se
	 * s non ï¿½ un valore numerico o ï¿½ nullo o ï¿½ negativo
	 * 
	 * @param s
	 * @return Integer
	 */
	public Integer zeroInteger(String s) {
		return zeroIfNegative(zeroIfNull(getInteger(s)));
	}

	/**
	 * Torna il Double corrispondente al valore s passato come stringa o zero se
	 * s non ï¿½ un valore numerico o ï¿½ nullo o ï¿½ negativo
	 * 
	 * @param s
	 * @return Double
	 */
	public Double zeroDouble(String s) {
		return zeroIfNegative(zeroIfNull(getDouble(s)));
	}

	/**
	 * Torna il BigDecimal corrispondente al valore s passato come stringa o
	 * zero se s non ï¿½ un valore numerico o ï¿½ nullo o ï¿½ negativo
	 * 
	 * @param s
	 * @return BigDecimal
	 */
	public BigDecimal zeroBigDecimal(String s) {
		return zeroIfNegative(zeroIfNull(getBigDecimal(s)));
	}

	/**
	 * Data una lista di tipi qualsiasi, e un valore numerico positivo pageSize,
	 * ritorna una lista di liste dove ogni lista ha al massimo pageSize
	 * elementi
	 * 
	 * 
	 * @param <T>
	 * @param c
	 * @param pageSize
	 * @return List<List<T>>
	 */
	public <T> List<List<T>> listPagination(Collection<T> c, Integer pageSize) {
		if (Null(c))
			return Collections.emptyList();
		List<T> list = new ArrayList<T>(c);
		if (almenoUna(Null(pageSize), pageSize <= 0, pageSize > list.size()))
			pageSize = list.size();
		int numPages = (int) Math.ceil((double) list.size() / (double) pageSize);
		List<List<T>> pages = new ArrayList<List<T>>(numPages);
		for (int pageNum = 0; pageNum < numPages;)
			pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
		return pages;
	}

	/**
	 * Data una stringa s torna la stringa trimmata e con tutti i caratteri
	 * tranne l'ultimo che viene tagliato
	 * 
	 * @param s
	 * @return String
	 */
	public String cutLast(String s) {
		if (notNull(s)) {
			s = s.trim();
			return s.substring(0, s.length() - 1);
		} else
			return s;
	}

	/**
	 * Dato uno StringBuilder s torna la stringa con tutti i caratteri tranne
	 * l'ultimo che viene tagliato
	 * 
	 * @param s
	 * @return String
	 */
	public String cutLast(StringBuilder s) {
		if (notNull(s)) {
			return s.substring(0, s.length() - 1).toString();
		} else
			return null;
	}

	/**
	 * Dato uno StringBuffer s torna la stringa con tutti i caratteri tranne
	 * l'ultimo che viene tagliato
	 * 
	 * @param s
	 * @return String
	 */
	public String cutLast(StringBuffer s) {
		if (notNull(s)) {
			return s.substring(0, s.length() - 1).toString();
		} else
			return null;
	}

	/**
	 * Dato un set l, ritorna lo stesso set limitato agli ultimi n elementi
	 * eliminando i precedenti
	 * 
	 * @param <K>
	 * @param l
	 * @param n
	 * @return Set<K>
	 */
	public <K> Set<K> cutToLast(Set<K> l, Integer n) {
		l = safe(l);
		return listToSet(setToList(l).cutToLast(n));
	}

	/**
	 * Data una lista l, ritorna una lista limitata agli ultimi n elementi
	 * eliminando i precedenti
	 * 
	 * @param <K>
	 * @param l
	 * @param n
	 * @return List<K>
	 */
	public <K> List<K> cutToLast(List<K> l, Integer n) {
		l = safe(l);
		return l.subList(l.size() - limiteInferiore(0, limiteSuperiore(zeroIfNull(n), l.size())), l.size());
	}

	/**
	 * Genera un titolo centrato con a SX e DX la ripetizione della stringa car
	 * per una lunghezza massima del titolo e della cornice uguale al parametro
	 * lunghezza. Esempio: --------------------------------Titolo della
	 * pagina--------------------------------
	 * 
	 * @param title
	 * @param lunghezza
	 * @param car
	 * @return String
	 */
	public String getTitle(String title, Integer lunghezza, String car) {
		String t = "";
		title = emptyIfNull(title);
		lunghezza = valIfNull(lunghezza, 0);
		lunghezza = limiteInferiore(zeroIfNegative(lunghezza), title.length());
		Integer lu = (lunghezza - (title.length())) / 2;
		for (int i = 1; i <= lu; i++) {
			t = getString(t, car);
		}
		t = getString(t, title);
		for (int i = 1; i <= lu; i++) {
			t = getString(t, car);
		}
		t = getString(t, lf());
		return t;
	}

	/**
	 * Stampa il contenuto della Lista<String> container. Fa precedere il tutto
	 * da un titolo title con di seguito il numero di occorrenze stampate,
	 * circondato da una cornice con il carattere "_" per una lunghezza totale
	 * del titolo cosï¿½ composto di length caratteri. Il titolo verrï¿½
	 * centrato. Esempio: ______________________________________INFOMAZIONI
	 * GENERALI 4______________________________________
	 * 
	 * @param title
	 * @param length
	 * @param container
	 */
	public void printList(String title, Integer length, List<String> container) {
		log(getTitle(getString(title, THREE_SPACE, String.valueOf(safe(container).size())), length, UNDERSCORE));
		for (String s : safe(container)) {
			log(getString(tab(), s, lf()));
		}
		log(lfn(2));
	}

	/**
	 * Stampa il contenuto della Lista<String> container. Fa precedere il tutto
	 * da un titolo title con di seguito il numero di occorrenze stampate,
	 * circondato da una cornice con il carattere "_" per una lunghezza totale
	 * del titolo cosï¿½ composto di length caratteri. Il titolo verrï¿½
	 * centrato. Esempio: ______________________________________INFOMAZIONI
	 * GENERALI 4______________________________________ Ogni stampa di elemento
	 * di lista ï¿½ preceduto dal valore prefixBeforeItem
	 * 
	 * @param title
	 * @param prefixBeforeItem
	 * @param length
	 * @param container
	 */
	public void printList(String title, Integer length, String prefixBeforeItem, List<String> container) {
		log(getTitle(getString(title, THREE_SPACE, String.valueOf(safe(container).size())), length, UNDERSCORE));
		for (String s : safe(container)) {
			log(getString(tab(), prefixBeforeItem, s, lf()));
		}
		log(lfn(2));
	}

	/**
	 * Data una lista di bean K, e un valore limite limit, aggiunge alla lista
	 * l'elemento k solo se tale operazione non fa superare il limite voluto di
	 * limit elementi. Torna false se dopo aver aggiunto l'elemento ï¿½ stato
	 * raggiunto il limite desiderato, true altrimenti
	 * 
	 * @param <K>
	 * @param l
	 * @param limit
	 * @param k
	 * @return boolean
	 */
	public <K> boolean addToLimit(List<K> l, Integer limit, K k) {
		if (Null(limit) || limit < 0) {
			l.add(k);
			return true;
		}
		boolean ancora = true;
		if (null != l) {
			if (l.size() < limit) {
				l.add(k);
			}
			ancora = l.size() >= limit.intValue();
		}
		return !ancora;
	}

	/**
	 * Dato un Set di bean K, e un valore limite limit, aggiunge al Set
	 * l'elemento k solo se tale operazione non fa superare il limite voluto di
	 * limit elementi. Torna false se dopo aver aggiunto l'elemento ï¿½ stato
	 * raggiunto il limite desiderato, true altrimenti
	 * 
	 * @param <K>
	 * @param l
	 * @param limit
	 * @param k
	 * @return boolean
	 */
	public <K> boolean addToLimit(Set<K> l, Integer limit, K k) {
		if (Null(limit) || limit < 0) {
			l.add(k);
			return true;
		}
		boolean ancora = true;
		if (null != l) {
			if (l.size() < limit) {
				l.add(k);
			}
			ancora = l.size() >= limit.intValue();
		}
		return !ancora;
	}

	private <K> String getFieldStato(K k) {
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.stato()) {
					campo = att.getName();
				}
			}
		}
		return campo;
	}

	public <K> List<K> stato(List<K> lista, String campo, Object value) throws Exception {
		K k = getFirstElement(lista);
		String campoStato = getFieldStato(k);
		return find(lista, campoStato, value);
	}

	/**
	 * Data una lista aggiunge la lista a sï¿½ stessa per times volte
	 * 
	 * @param <T>
	 * @param l
	 * @param times
	 * @return List<T>
	 */
	public <T> List<T> selfExtendList(List<T> l, Integer times) {
		List<T> somma = new ArrayList<T>();
		for (int i = 0; i < times; i++) {
			for (T t : safe(l)) {
				somma.add(t);
			}
		}
		return somma;
	}

	/**
	 * Data una stringa s e un carattere ch, torna il numero di occorrenze del
	 * carattere ch presenti nella stringa s
	 * 
	 * @param s
	 * @param ch
	 * @return int
	 */
	public int countChar(String s, char ch) {
		int num = 0;
		if (notNull(s)) {
			char[] c = s.toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (c[i] == ch) {
					num++;
				}
			}
		}
		return num;
	}

	/**
	 * Data una stringa s torna true se le parentesi al suo interno sono
	 * correttamente bilanciate, false altrimenti
	 * 
	 * @param s
	 * @return boolean
	 */
	public boolean checkParenthesis(String s) {
		s = emptyIfNull(s);
		Stack<Character> stack = new Stack<Character>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '[' || c == '(' || c == '{') {
				stack.push(c);
			} else if (c == ']') {
				if (stack.isEmpty() || stack.pop() != '[') {
					return false;
				}
			} else if (c == ')') {
				if (stack.isEmpty() || stack.pop() != '(') {
					return false;
				}
			} else if (c == '}') {
				if (stack.isEmpty() || stack.pop() != '{') {
					return false;
				}
			}
		}
		return stack.isEmpty();
	}

	/**
	 * Ritorna un nome univoco formato dalla data odierna,"_" e il timestamp
	 * 
	 * @return String
	 */
	public String getUniqueName() {
		return getString(nowString().replace(SLASH, ""), UNDERSCORE, String.valueOf(System.currentTimeMillis()));
	}

	/**
	 * Ritorna un nome univoco formato dalla data odierna in formato dd-MM-yyyy
	 * hh-mm-ss
	 * 
	 * @return String
	 */
	public String getUniqueNamehhmmss() {
		return getString(replace(nowhhmmssString().replace(SLASH, DASHTRIM), ":", DASHTRIM));
	}

	public Logger getLog() {
		return logger;
	}

	/**
	 * Ritorna la somma di tutti gli elementi passati come stringa
	 * 
	 * @param vals
	 * @return Integer
	 */
	public Integer sommaInt(String... vals) {
		Integer res = 0;
		for (String num : vals) {
			res += getInteger(num);
		}
		return res;
	}

	/**
	 * Ritorna la somma di tutti gli elementi passati come stringa
	 * 
	 * @param vals
	 * @return Long
	 */
	public Long sommaLong(String... vals) {
		Long res = 0l;
		for (String num : vals) {
			res += getLong(num);
		}
		return res;
	}

	/**
	 * Ritorna la somma di tutti gli elementi passati come interi
	 * 
	 * @param vals
	 * @return Integer
	 */
	public Integer sommaInt(Integer... vals) {
		Integer res = 0;
		for (Integer num : vals) {
			res += num;
		}
		return res;
	}

	/**
	 * Ritorna la somma di tutti gli elementi passati come Long
	 * 
	 * @param vals
	 * @return Long
	 */
	public Long sommaLong(Long... vals) {
		Long res = 0l;
		for (Long num : vals) {
			res += num;
		}
		return res;
	}

	/**
	 * Ritorna la somma di tutti gli elementi passati come stringa
	 * 
	 * @param vals
	 * @return Double
	 */
	public Double sommaDouble(String... vals) {
		Double res = 0d;
		for (String num : vals) {
			res += getDouble(num);
		}
		return res;
	}

	/**
	 * Ritorna la somma di tutti gli elementi passati come Double
	 * 
	 * @param vals
	 * @return Double
	 */
	public Double sommaDouble(Double... vals) {
		Double res = 0d;
		for (Double num : vals) {
			res += num;
		}
		return res;
	}

	/**
	 * Modifica la lunghezza dell'array arr al valore i
	 * 
	 * @param <K>
	 * @param arr
	 * @param i
	 * @return K[]
	 */
	public <K> K[] modificaLunghezzaArray(K[] arr, Integer i) {
		if (null != arr)
			return java.util.Arrays.copyOf(arr, limiteInferiore(i, 0));
		return arr;
	}

	/**
	 * Modifica la lunghezza dell'array aggiungendo i elementi vuoti
	 * 
	 * @param <K>
	 * @param arr
	 * @param i
	 * @return K[]
	 */
	public <K> K[] estendiLunghezzaArrayDi(K[] arr, Integer i) {
		if (null != arr)
			return modificaLunghezzaArray(arr, arr.length + i);
		return arr;
	}

	/**
	 * Restituisce la stringa s tagliata alla prima occorrenza di element non
	 * incluso
	 * 
	 * @param s
	 * @param elemento
	 * @return String
	 */
	public String cutToFirstOccurence(String s, String elemento) {
		return substring(s, elemento, false, false, null, false, false);
	}

	/**
	 * Restituisce la stringa s tagliata alla ultima occorrenza di element non
	 * incluso
	 * 
	 * @param s
	 * @param elemento
	 * @return String
	 */
	public String cutToLastOccurence(String s, String elemento) {
		return substring(s, null, false, false, elemento, false, true);
	}

	/**
	 * Torna true se l'intervallo [start,end] ï¿½ completamente contenuto
	 * all'interno dell'intervallo [start1,end1]
	 * 
	 * @param start
	 * @param end
	 * @param start1
	 * @param end1
	 * @return boolean
	 */
	public boolean isTimeIntervalContained(String start, String end, String start1, String end1) {
		return isTimeIntervalContained(stringToDate(start), stringToDate(end), stringToDate(start1), stringToDate(end1));
	}

	/**
	 * Torna true se l'intervallo [start,end] ï¿½ completamente contenuto
	 * all'interno dell'intervallo [start1,end1]
	 * 
	 * @param start
	 * @param end
	 * @param start1
	 * @param end1
	 * @return boolean
	 */
	public boolean isTimeIntervalContained(Date start, Date end, Date start1, Date end1) {
		boolean esito = false;
		boolean valida = validaDate(start, end, false);
		boolean valida1 = validaDate(start1, end1, false);
		if (tutte(valida, valida1)) {
			boolean inizioDentro = isDateBetween(start, start1, end1);
			boolean fineDentro = isDateBetween(end, start1, end1);
			esito = tutte(inizioDentro, fineDentro);
		}
		return esito;

	}

	/**
	 * Torna true se l'intervallo [start,end] contiene completamente
	 * l'intervallo [start1,end1]
	 * 
	 * @param start
	 * @param end
	 * @param start1
	 * @param end1
	 * @return boolean
	 */
	public boolean isTimeIntervalContains(String start, String end, String start1, String end1) {
		return isTimeIntervalContains(stringToDate(start), stringToDate(end), stringToDate(start1), stringToDate(end1));
	}

	/**
	 * Torna true se l'intervallo [start,end] contiene completamente
	 * l'intervallo [start1,end1]
	 * 
	 * @param start
	 * @param end
	 * @param start1
	 * @param end1
	 * @return boolean
	 */
	public boolean isTimeIntervalContains(Date start, Date end, Date start1, Date end1) {
		boolean esito = false;
		boolean valida = validaDate(start, end, false);
		boolean valida1 = validaDate(start1, end1, false);
		if (tutte(valida, valida1)) {
			boolean inizioNotAfter = !isDateAfter(start, start1);
			boolean fineNotBefore = !isDateBefore(end, end1);
			esito = tutte(inizioNotAfter, fineNotBefore);
		}
		return esito;

	}

	/**
	 * Ritorna il primo elemento della lista che ha il valore massimo della
	 * proprieta prop
	 * 
	 * @param <K>
	 * @param <T>
	 * @param lista
	 * @param prop
	 * @return K
	 * @throws Exception
	 */

	public <K, T> K getFirstElementMaxValue(List<K> lista, String prop) throws Exception {
		T value = (T) get(maxBean(lista, prop), prop);
		K elem = null;
		for (K item : safe(lista)) {
			if (is(get(item, prop), value)) {
				elem = item;
				break;
			}
		}
		return elem;
	}

	/**
	 * Ritorna l'ultimo elemento della lista che ha il valore massimo della
	 * proprieta prop
	 * 
	 * @param <K>
	 * @param <T>
	 * @param lista
	 * @param prop
	 * @return K
	 * @throws Exception
	 */

	public <K, T> K getLastElementMaxValue(List<K> lista, String prop) throws Exception {
		T value = (T) get(maxBean(lista, prop), prop);
		K elem = null;
		for (K item : safe(lista)) {
			if (is(get(item, prop), value)) {
				elem = item;
			}
		}
		return elem;
	}

	/**
	 * Ritorna il primo elemento della lista che ha il valore minimo della
	 * proprieta prop
	 * 
	 * @param <K>
	 * @param <T>
	 * @param lista
	 * @param prop
	 * @return K
	 * @throws Exception
	 */

	public <K, T> K getFirstElementMinValue(List<K> lista, String prop) throws Exception {
		T value = (T) get(minBean(lista, prop), prop);
		K elem = null;
		for (K item : safe(lista)) {
			if (is(get(item, prop), value)) {
				elem = item;
				break;
			}
		}
		return elem;
	}

	/**
	 * Ritorna l'ultimo elemento della lista che ha il valore minimo della
	 * proprieta prop
	 * 
	 * @param <K>
	 * @param <T>
	 * @param lista
	 * @param prop
	 * @return K
	 * @throws Exception
	 */

	public <K, T> K getLastElementMinValue(List<K> lista, String prop) throws Exception {
		T value = (T) get(minBean(lista, prop), prop);
		K elem = null;
		for (K item : safe(lista)) {
			if (is(get(item, prop), value)) {
				elem = item;
			}
		}
		return elem;
	}

	/**
	 * Torna true se val appartiene all'intervallo [start,end] estremi compresi.
	 * Se start ï¿½ null torna true se val<=end. Se end ï¿½ null torna true se
	 * val>=start. Se val ï¿½ null torna false;
	 * 
	 * @param <K>
	 * @param val
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public <K extends Comparable<K>> boolean insideInterval(K val, K start, K end) {
		if (Null(val))
			return false;
		boolean esito = false;
		if (tutte(notNull(start), Null(end)))
			esito = maggioreUgualeDi(val, start);
		else if (tutte(Null(start), notNull(end)))
			esito = minoreUgualeDi(val, end);
		else if (notNull(start, end))
			esito = tutte(maggioreUgualeDi(val, start), minoreUgualeDi(val, end));
		return esito;
	}

	/**
	 * Torna true se l'intervallo [val1,val2] ï¿½ compreso all'interno
	 * dell'intervallo [start,end]
	 * 
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @param start
	 * @param end
	 * @return boolean
	 */
	public <K extends Comparable<K>> boolean insideInterval(K val1, K val2, K start, K end) {
		return tutte(insideInterval(val1, start, end), insideInterval(val2, start, end));
	}

	/**
	 * Torna true se d ï¿½ maggiore uguale d1
	 * 
	 * @param <K>
	 * @param d
	 * @param d1
	 * @return boolean
	 */
	public <K extends Comparable<K>> boolean maggioreUgualeDi(K d, K d1) {
		return notNull(d, d1) && d.compareTo(d1) >= 0;
	}

	/**
	 * Torna true se d ï¿½ maggiore d1
	 * 
	 * @param <K>
	 * @param d
	 * @param d1
	 * @return boolean
	 */
	public <K extends Comparable<K>> boolean maggioreDi(K d, K d1) {
		return notNull(d, d1) && d.compareTo(d1) > 0;
	}

	/**
	 * Torna true se d ï¿½ minore uguale d1
	 * 
	 * @param <K>
	 * @param d
	 * @param d1
	 * @return boolean
	 */
	public <K extends Comparable<K>> boolean minoreUgualeDi(K d, K d1) {
		return notNull(d, d1) && d.compareTo(d1) <= 0;
	}

	/**
	 * Torna true se d ï¿½ minore d1
	 * 
	 * @param <K>
	 * @param d
	 * @param d1
	 * @return boolean
	 */
	public <K extends Comparable<K>> boolean minoreDi(K d, K d1) {
		return notNull(d, d1) && d.compareTo(d1) < 0;
	}

	/**
	 * Torna true se la stringa s inizia per almeno uno dei valori vals
	 * 
	 * @param s
	 * @param vals
	 * @return boolean
	 */
	public boolean startsWith(String s, String... vals) {
		s = emptyIfNull(s);
		boolean esito = false;
		for (String v : vals) {
			esito = almenoUna(esito, s.startsWith(v));
		}
		return esito;
	}

	/**
	 * Torna true se la stringa s termina per almeno uno dei valori vals
	 * 
	 * @param s
	 * @param vals
	 * @return boolean
	 */
	public boolean endsWith(String s, String... vals) {
		s = emptyIfNull(s);
		boolean esito = false;
		for (String v : vals) {
			esito = almenoUna(esito, s.endsWith(v));
		}
		return esito;
	}

	private void append(StringBuilder sb, String from) {
		Random r = new Random();
		sb.append(from.charAt(r.nextInt(from.length())));
	}

	private void append(StringBuilder sb, List<String> elenco) {
		Random r = new Random();
		sb.append(elenco.get(r.nextInt(elenco.size() - 1)));
	}

	private String generateStringForCf(Mock... mock) {
		if (goAllowed(mock)) {
			return generateString(mock[0].allowed());
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 16; i++) {
			if (i <= 6 || i == 9 || i == 12 || i == 16)
				append(sb, ALPHABET);
			else
				append(sb, NUMBERS);
		}
		return sb.toString();
	}

	private String generateStringForIva(Mock... mock) {
		if (goAllowed(mock)) {
			return generateString(mock[0].allowed());
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 11; i++) {
			append(sb, NUMBERS);
		}
		return sb.toString();
	}

	private String generateStringForMail(Mock... mock) {
		if (goAllowed(mock)) {
			return generateString(mock[0].allowed());
		}
		Random r = new Random();
		if (notNull(getNomeMock(), getCognomeMock())) {
			return replace(str(getNomeMock(), DOT, getCognomeMock(), AT, mail.get(r.nextInt(mail.size()))).toLowerCase(), SPACE, DOT);
		}
		return replace(str(generateNome(), DOT, generateCognome(), AT, mail.get(r.nextInt(mail.size()))).toLowerCase(), SPACE, DOT);
	}

	private String generateString(String[] allowed) {
		String s = "";
		if (null != allowed && allowed.length > 0) {
			s = allowed[generateInt(allowed.length)];
		}
		return s;
	}

	private String generateString(Integer length, Mock... mock) {
		if (goAllowed(mock)) {
			return generateString(mock[0].allowed());
		}
		StringBuilder sb = new StringBuilder();
		boolean sillaba = false;
		for (int i = 1; i <= length; i++) {
			if (i % 2 == 0 || sillaba) {
				if (sillaba) {
					if (sb.toString().endsWith("SQ")) {
						sb.append("U");
					} else
						append(sb, VOWELS);
				} else
					append(sb, VOWELS);
				sillaba = false;
			} else {
				if (generateBoolean()) {
					if (i == 1) {
						append(sb, sillabeStart);
					} else {
						append(sb, sillabeFull);
					}
					sillaba = true;
				} else {
					append(sb, ALPHABET);
				}
			}
			if (sb.toString().length() > length) {
				String s = substring(cut(sb.toString(), length), null, false, false, CUTTER, false, true);
				return s;
			}

		}
		return sb.toString();
	}

	private Integer generateCode(Integer length, Mock... mock) {
		if (goAllowed(mock)) {
			return getInteger(generateString(mock[0].allowed()));
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			append(sb, NUMBERS);
		}
		return getInteger(sb.toString());
	}

	/**
	 * Ritorna una lista di 10 oggetti valorizzati con dati mock. Se la classe
	 * presenta l'annotazione @MockFile, i dati vengono presi dal file indicato
	 * dall'attributo file. Altrimenti i dati vengono generati automaticamente
	 * casualmente rispettando le restrizioni imposte attraverso gli attributi
	 * dell'annotazione @Mock
	 * 
	 * @param <K>
	 * @param c
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K> PList<K> mockList(Class<K> c) throws Exception {
		return mockList(c, 10);
	}

	/**
	 * Ritorna una lista di n oggetti di tipo c valorizzati con valori mock. Se
	 * la classe riporta l'annotazione @MockFile allora i valori di mock vengono
	 * prelevati dal file indicato nell'annotazione e vengono bypassati i valori
	 * generati casualmente con le relative restrizioni impostate nella classe
	 * 
	 * @param <K>
	 * @param c
	 * @param n
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K> PList<K> mockList(Class<K> c, Integer n) throws Exception {
		MockFile mf = c.getAnnotation(MockFile.class);
		boolean mockFile = null != mf && null != mf.file() && null != mf.separator();
		PList<K> elenco = pl();
		if (mockFile) {
			if (!fileExists(mf.file())) {
				logError("File", mf.file(), "mancante.Procedo con la generazione casuale della lista di mock");
				for (int i = 1; i <= n; i++) {
					elenco.add(mock(c));
				}
			} else {
				PList<String> fileContent = readFile(mf.file());
				PList<K> mockListFromFile = pl();
				Field[] attributi = c.getDeclaredFields();
				for (String row : safe(fileContent)) {
					mockListFromFile.add(mockFromFile(c, row, mf.separator(), attributi));
				}
				return mockListFromFile;
			}

		}

		if (!mockFile) {
			for (int i = 1; i <= n; i++) {
				elenco.add(mock(c));
			}
		}
		return elenco;
	}

	private <K> boolean customFields(Object o, Field f, Method[] metodi) throws Exception {
		String tipo = f.getType().getName();
		Mock mock = f.getAnnotation(Mock.class);
		boolean goList = is(tipo, JAVA_LIST, JAVA_PLIST, JAVA_SET);
		String attName = f.getName().toLowerCase();
		if (goList) {
			tipo = getGenericType(f);
		}
		if (isLike(attName, "anno", "year")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(String.valueOf(generateAnno(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, String.valueOf(generateAnno(mock)), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getLong2(generateAnno(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getLong2(generateAnno(mock)), f);
				}
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateAnno(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateAnno(mock), f);
				}
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getBigDecimal(generateAnno(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getBigDecimal(generateAnno(mock)), f);
				}
			}
			return true;
		} else if (isLike(attName, "mese", "month")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(String.valueOf(generateMese(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, String.valueOf(generateMese(mock)), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getLong2(generateMese(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getLong2(generateMese(mock)), f);
				}
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateMese(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateMese(mock), f);
				}
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getBigDecimal(generateMese(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getBigDecimal(generateMese(mock)), f);
				}
			}
			return true;
		} else if (isLike(attName, "giorno", "day")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(String.valueOf(generateGiorno(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, String.valueOf(generateGiorno(mock)), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getLong2(generateGiorno(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getLong2(generateGiorno(mock)), f);
				}
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateGiorno(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateGiorno(mock), f);
				}
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getBigDecimal(generateGiorno(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getBigDecimal(generateGiorno(mock)), f);
				}
			}
			return true;
		} else if (isLike(attName, "tipo", "type", "tipi")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateTipoString(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateTipoString(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getLong2(generateTipo(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getLong2(generateTipo(mock)), f);
				}
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateTipo(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateTipo(mock), f);
				}
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getBigDecimal(generateTipo(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getBigDecimal(generateTipo(mock)), f);
				}
			}
			return true;
		} else if (tutte(isLike(attName, "codice", "cod"), !isLike(attName, "fiscale", "codf", "cf"))) {
			int l = 2;
			if (isLike(attName, "utente", "utenti", "user")) {
				l = 6;
			}
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateString(l, mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateString(l, mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getLong2(generateCode(l, mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getLong2(generateCode(l, mock)), f);
				}
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateCode(l, mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateCode(l, mock), f);
				}
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getBigDecimal(generateCode(l, mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getBigDecimal(generateCode(l, mock)), f);
				}
			}
			return true;
		} else if (isLike(attName, "flag")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateFlag(attName, mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateFlag(attName, mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateLong(mock));
					}
					invokeSetter(o, generateLong(mock), f);
				} else {
					invokeSetter(o, generateLong(mock), f);
				}
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "fiscale", "cf", "codf")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateStringForCf(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateStringForCf(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateLong(mock));
					}
					invokeSetter(o, generateLong(mock), f);
				} else {
					invokeSetter(o, generateLong(mock), f);
				}
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "perc")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(String.valueOf(generatePerc(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, String.valueOf(generatePerc(mock)), f);
				}
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generatePerc(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generatePerc(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getLong2(generatePerc(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getLong2(generatePerc(mock)), f);
				}
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getInteger2(generatePerc(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getInteger2(generatePerc(mock)), f);
				}
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getBigDecimal(generatePerc(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getBigDecimal(generatePerc(mock)), f);
				}
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getFloat2(generatePerc(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getFloat2(generatePerc(mock)), f);
				}
			}
			return true;
		} else if (isLike(attName, "mail")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateStringForMail(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateStringForMail(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				customLong(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "iva")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateStringForIva(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateStringForIva(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				customLong(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "sesso")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateSesso(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateSesso(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				customLong(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "telef")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generatePhoneNumber(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generatePhoneNumber(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getLong(generatePhoneNumber(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getLong(generatePhoneNumber(mock)), f);
				}
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(getInteger(generatePhoneNumber(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, getInteger(generatePhoneNumber(mock)), f);
				}
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "num")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(String.valueOf(generateInt(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, String.valueOf(generateInt(mock)), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				customLong(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "prov", "sigla")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateProvincia(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateProvincia(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				customLong(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "cogn", "surname")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateCognome(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateCognome(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				customLong(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV, mock)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "nome", "name")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateNome(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateNome(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				customLong(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "indirizzo", "indirizzi", "address")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateIndirizzo(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateIndirizzo(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				customLong(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "comune", "comuni", "citta")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateComune(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateComune(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				customLong(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "cap")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateCap(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateCap(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				customLong(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		} else if (isLike(attName, "data", "date")) {
			if (is(tipo, JAVA_STRING)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(dateToString(generateDate(mock)));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, dateToString(generateDate(mock)), f);
				}
			} else if (is(tipo, JAVA_DATE, JAVA_DATE_SQL)) {
				if (goList) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateDate(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					invokeSetter(o, generateDate(mock), f);
				}
			} else if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				customLong(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				customInt(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_BIG_DECIMAL)) {
				customBigDecimal(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				customFloat(o, f, tipo, goList, mock);
			} else if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				customDouble(o, f, tipo, goList, mock);
			}
			return true;
		}

		return false;
	}

	private void customBigDecimal(Object o, Field f, String tipo, boolean goList, Mock mock) throws Exception {
		if (goList) {
			Collection mockColl = getMockColl(tipo);
			for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
				mockColl.add(generateBigDecimal(mock));
			}
			invokeSetter(o, mockColl, f);
		} else {
			invokeSetter(o, generateBigDecimal(mock), f);
		}
	}

	private void customDouble(Object o, Field f, String tipo, boolean goList, Mock mock) throws Exception {
		if (goList) {
			Collection mockColl = getMockColl(tipo);
			for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
				mockColl.add(generateDouble(mock));
			}
			invokeSetter(o, mockColl, f);
		} else {
			invokeSetter(o, generateDouble(mock), f);
		}
	}

	private String generateFlag(String attName, Mock... mock) {
		if (goAllowed(mock)) {
			return generateString(mock[0].allowed());
		}
		if (isLike(attName, "stato", "stati")) {
			return generateBoolean() ? ATTIVO : DISATTIVO;
		}
		return generateBoolean() ? S : N;
	}

	private String generateSesso(Mock... mock) {
		if (goAllowed(mock)) {
			return generateString(mock[0].allowed());
		}
		return generateBoolean() ? "M" : "F";
	}

	private void customInt(Object o, Field f, String tipo, boolean goList, Mock mock) throws Exception {
		if (goList) {
			Collection mockColl = getMockColl(tipo);
			for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
				mockColl.add(generateInt(mock));
			}
			invokeSetter(o, mockColl, f);
		} else {
			invokeSetter(o, generateInt(mock), f);
		}
	}

	private void customFloat(Object o, Field f, String tipo, boolean goList, Mock mock) throws Exception {
		if (goList) {
			Collection mockColl = getMockColl(tipo);
			for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
				mockColl.add(generateFloat(mock));
			}
			invokeSetter(o, mockColl, f);
		} else {
			invokeSetter(o, generateFloat(mock), f);
		}
	}

	private void customLong(Object o, Field f, String tipo, boolean goList, Mock mock) throws Exception {
		if (goList) {
			Collection mockColl = getMockColl(tipo);
			for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
				mockColl.add(generateLong(mock));
			}
			invokeSetter(o, mockColl, f);
		} else {
			invokeSetter(o, generateLong(mock), f);
		}
	}

	private class Comune extends PilotSupport {

		private String nome;
		private String provincia;
		private String prefisso;
		private String cap;
		private PList<String> nomi = plstr();
		private PList<String> caps = plstr();
		private PList<String> prefissi = plstr();
		private PList<String> provs = plstr();

		public PList<String> getProvs() {
			return provs;
		}

		public void setProvs(PList<String> provs) {
			this.provs = provs;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getProvincia() {
			return provincia;
		}

		public void setProvincia(String provincia) {
			this.provincia = provincia;
		}

		public String getPrefisso() {
			return prefisso;
		}

		public void setPrefisso(String prefisso) {
			this.prefisso = prefisso;
		}

		public String getCap() {
			return cap;
		}

		public void setCap(String cap) {
			this.cap = cap;
		}

		public String toString() {
			return strSep(DASHTRIM, getNome(), getCap(), getPrefisso(), getProvincia());
		}

		public PList<String> getNomi() {
			return nomi;
		}

		public void setNomi(PList<String> nomi) {
			this.nomi = nomi;
		}

		public PList<String> getCaps() {
			return caps;
		}

		public void setCaps(PList<String> caps) {
			this.caps = caps;
		}

		public PList<String> getPrefissi() {
			return prefissi;
		}

		public void setPrefissi(PList<String> prefissi) {
			this.prefissi = prefissi;
		}

		public boolean isComune(Comune comuneAllowed) {
			boolean cond = true;
			if (notNull(comuneAllowed)) {
				if (notNull(comuneAllowed.getNomi())) {
					cond = tutte(cond, is(comuneAllowed.getNomi(), getNome()));
				}
				if (notNull(comuneAllowed.getCaps())) {
					cond = tutte(cond, is(comuneAllowed.getCaps(), getCap()));
				}
				if (notNull(comuneAllowed.getPrefissi())) {
					cond = tutte(cond, is(comuneAllowed.getPrefissi(), getPrefisso()));
				}
				if (notNull(comuneAllowed.getProvs())) {
					cond = tutte(cond, is(comuneAllowed.getProvs(), getProvincia()));
				}
				return cond;
			}
			return cond;
		}
	}

	private void readFileComuni(Comune comuneAllowed) throws Exception {
		PList<String> elenco = readFileAsResource(getClass(), "comuni.txt");
		for (String val : safe(elenco)) {
			PList<String> row = toListString(val, DASHTRIM);
			Comune c = new Comune();
			c.setNome(row.get(0));
			c.setProvincia(row.get(3));
			c.setCap(row.get(1));
			c.setPrefisso(row.get(2));
			if (c.isComune(comuneAllowed)) {
				getComuni().add(c);
			}

		}
		setReadFileComuni(true);

	}

	private String generateComune(Mock... mock) {
		if (notNull(getComune())) {
			if (goAllowed(mock)) {
				return generateString(mock[0].allowed());
			}
			return getComune().getNome();
		}
		if (!isReadFileComuni()) {
			try {
				Comune comuneAllowed = null;
				if (goAllowed(mock)) {
					comuneAllowed = new Comune();
					comuneAllowed.setNomi(arrayToList(mock[0].allowed()));
				}
				readFileComuni(comuneAllowed);
			} catch (Exception e) {
				logError("Errore durante la lettura del file comuni.txt,genero una stringa casuale come comune");
				return generateString(8);
			}
		}
		if (goAllowed(mock)) {
			if (notNull(getComuni())) {
				setComune((Comune) getComuni().get(generateInt(limiteInferiore(getComuni().size(), 1))));
				return getComune().getNome();
			}
			return generateString(mock[0].allowed());
		}
		if (notNull(getComuni())) {
			setComune((Comune) getComuni().get(generateInt(limiteInferiore(getComuni().size(), 1))));
			return getComune().getNome();
		}
		return generateString(8);
	}

	private String generateCap(Mock... mock) {
		if (notNull(getComune())) {
			if (goAllowed(mock)) {
				return generateString(mock[0].allowed());
			}
			return getComune().getCap();
		}
		if (!isReadFileComuni()) {
			try {
				Comune comuneAllowed = null;
				if (goAllowed(mock)) {
					comuneAllowed = new Comune();
					comuneAllowed.setCaps(arrayToList(mock[0].allowed()));
				}
				readFileComuni(comuneAllowed);
			} catch (Exception e) {
				logError("Errore durante la lettura del file comuni.txt,genero un cap casuale");
				return generateCapRadom();
			}
		}
		if (goAllowed(mock)) {
			if (notNull(getComuni())) {
				setComune((Comune) getComuni().get(generateInt(limiteInferiore(getComuni().size(), 1))));
				return getComune().getCap();
			}
			return generateString(mock[0].allowed());
		}
		if (notNull(getComuni())) {
			setComune((Comune) getComuni().get(generateInt(limiteInferiore(getComuni().size(), 1))));
			return getComune().getCap();
		}
		return generateCapRadom();
	}

	private String generateCapRadom() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 5; i++) {
			append(sb, NUMBERS);
		}
		return sb.toString();
	}

	private String generatePrefisso(Mock... mock) {
		if (notNull(getComune())) {
			if (goAllowed(mock)) {
				return generateString(mock[0].allowed());
			}
			return getComune().getPrefisso();
		}
		if (!isReadFileComuni()) {
			try {
				Comune comuneAllowed = null;
				if (goAllowed(mock)) {
					comuneAllowed = new Comune();
					comuneAllowed.setPrefissi(arrayToList(mock[0].allowed()));
				}
				readFileComuni(comuneAllowed);
			} catch (Exception e) {
				logError("Errore durante la lettura del file comuni.txt,genero un prefisso casuale");
				return generatePrefix();
			}
		}
		if (goAllowed(mock)) {
			if (notNull(getComuni())) {
				setComune((Comune) getComuni().get(generateInt(limiteInferiore(getComuni().size(), 1))));
				return getComune().getPrefisso();
			}
			return generateString(mock[0].allowed());
		}
		if (notNull(getComuni())) {
			setComune((Comune) getComuni().get(generateInt(limiteInferiore(getComuni().size(), 1))));
			return getComune().getPrefisso();
		}
		return generatePrefix();
	}

	private String generateIndirizzo(Mock... mock) {
		if (goAllowed(mock)) {
			return generateString(mock[0].allowed());
		}
		if (Null(indirizzi)) {
			try {
				indirizzi = readFileAsResource(getClass(), "indirizzi.txt");
			} catch (Exception e) {
				logError("Errore durante la lettura del file indirizzi.txt,genero una stringa casuale come indirizzo");
				return generateString(12);
			}
		}
		StringBuilder sb = new StringBuilder();
		append(sb, indirizzi);
		return sb.toString().trim();
	}

	private String generatePrefix() {
		StringBuilder sb = new StringBuilder();
		append(sb, "0");
		for (int i = 1; i <= 2; i++) {
			append(sb, NUMBERS);
		}
		return sb.toString();
	}

	private String generatePhoneNumber(Mock... mock) {
		if (goAllowed(mock)) {
			return generateString(mock[0].allowed());
		}
		StringBuilder sb = new StringBuilder();
		sb.append(generatePrefisso());
		for (int i = 1; i <= 7; i++) {
			append(sb, NUMBERS);
		}
		return sb.toString();
	}

	private String generateNome(Mock... mock) {
		setNomeMock(null);
		if (goAllowed(mock)) {
			setNomeMock(generateString(mock[0].allowed()));
			return getNomeMock();
		}
		if (Null(nomi)) {
			try {
				nomi = readFileAsResource(getClass(), "nomi.txt");
			} catch (Exception e) {
				logError("Errore durante la lettura del file nomi.txt,genero una stringa casuale come nome");
				return generateString(8);
			}
		}
		StringBuilder sb = new StringBuilder();
		append(sb, nomi);
		setNomeMock(capFirstLetter(sb.toString()).trim());
		return getNomeMock();
	}

	private String generateProvincia(Mock... mock) {
		if (notNull(getComune())) {
			if (goAllowed(mock)) {
				return generateString(mock[0].allowed());
			}
			return getComune().getProvincia();
		}
		if (!isReadFileComuni()) {
			try {
				Comune comuneAllowed = null;
				if (goAllowed(mock)) {
					comuneAllowed = new Comune();
					comuneAllowed.setProvs(arrayToList(mock[0].allowed()));
				}
				readFileComuni(comuneAllowed);
			} catch (Exception e) {
				logError("Errore durante la lettura del file comuni.txt,genero una provincia casuale");
				return generateString(2);
			}
		}
		if (goAllowed(mock)) {
			if (notNull(getComuni())) {
				setComune((Comune) getComuni().get(generateInt(limiteInferiore(getComuni().size(), 1))));
				return getComune().getProvincia();
			}
			return generateString(mock[0].allowed());
		}
		if (notNull(getComuni())) {
			setComune((Comune) getComuni().get(generateInt(limiteInferiore(getComuni().size(), 1))));
			return getComune().getProvincia();
		}

		return generateString(2);
	}

	private boolean goAllowed(Mock... mock) {
		return (mock.length > 0 && notNull(mock[0]) && mock[0].allowed().length > 0);
	}

	private String generateCognome(Mock... mock) {
		setCognomeMock(null);
		if (goAllowed(mock)) {
			setCognomeMock(generateString(mock[0].allowed()));
			return getCognomeMock();
		}
		if (Null(cognomi)) {
			try {
				cognomi = readFileAsResource(getClass(), "cognomi.txt");
			} catch (Exception e) {
				logError("Errore durante la lettura del file cognomi.txt,genero una stringa casusale come cognome");
				return generateString(8);
			}
		}
		StringBuilder sb = new StringBuilder();
		append(sb, cognomi);
		setCognomeMock(capFirstLetterAfterSpace(sb.toString()).trim());
		return getCognomeMock();
	}

	private Field findAttribute(String nome, Field[] attr) throws Exception {
		Field attributo = null;
		for (Field att : safe(attr, Field.class)) {
			if (is(att.getName(), nome)) {
				attributo = att;
				break;
			}

		}
		return attributo;
	}

	private <K> K mockFromFile(Class<K> c, String row, String separator, Field[] attributi) throws Exception {
		K o = (K) c.newInstance();
		PList<String> elementi = toListString(row, separator);
		for (String el : safe(elementi)) {
			PList<String> varVal = toListString(el, EQUAL);
			String vi = varVal.getFirstElement();
			String valore = varVal.getLastElement();
			if (like(vi, DOT)) {// gestisco la valorizzazione di proprietà
				// innestate art.nome
				PList<String> attributi_ = split(vi, DOT);
				Field attributo = findAttribute(attributi_.getFirstElement(), attributi);
				Class type = attributo.getType();
				K innerObj = invokeGetter(o, attributi_.getFirstElement());
				if (Null(innerObj)) {
					innerObj = (K) type.newInstance();
					invokeSetter(o, innerObj, attributo);
				}
				Field[] attrs = innerObj.getClass().getDeclaredFields();
				attributo = findAttribute(attributi_.getLastElement(), attrs);
				if (notNull(attributo)) {
					type = attributo.getType();
					if (String.class.isAssignableFrom(type)) {
						invokeSetter(innerObj, valore, attributo);
					}
					if (Date.class.isAssignableFrom(type)) {
						invokeSetter(innerObj, toDate(valore), attributo);
					}
					if (Integer.class.isAssignableFrom(type)) {
						invokeSetter(innerObj, getInteger(valore), attributo);
					}
					if (Long.class.isAssignableFrom(type)) {
						invokeSetter(innerObj, getLong(valore), attributo);
					}
					if (Double.class.isAssignableFrom(type)) {
						invokeSetter(innerObj, getDouble(valore), attributo);
					}
					if (Short.class.isAssignableFrom(type)) {
						invokeSetter(innerObj, getShort(valore), attributo);
					}
					if (Float.class.isAssignableFrom(type)) {
						invokeSetter(innerObj, getFloat(valore), attributo);
					}
					if (BigDecimal.class.isAssignableFrom(type)) {
						invokeSetter(innerObj, getBigDecimal(valore), attributo);
					}
					if (Boolean.class.isAssignableFrom(type)) {
						invokeSetter(innerObj, toBool(valore), attributo);
					}
				}
				continue;
			}

			Field attributo = findAttribute(vi, attributi);
			if (notNull(attributo)) {
				Class type = attributo.getType();
				if (String.class.isAssignableFrom(type)) {
					invokeSetter(o, valore, attributo);
				}
				if (Date.class.isAssignableFrom(type)) {
					invokeSetter(o, toDate(valore), attributo);
				}
				if (Integer.class.isAssignableFrom(type)) {
					invokeSetter(o, getInteger(valore), attributo);
				}
				if (Long.class.isAssignableFrom(type)) {
					invokeSetter(o, getLong(valore), attributo);
				}
				if (Double.class.isAssignableFrom(type)) {
					invokeSetter(o, getDouble(valore), attributo);
				}
				if (Short.class.isAssignableFrom(type)) {
					invokeSetter(o, getShort(valore), attributo);
				}
				if (Float.class.isAssignableFrom(type)) {
					invokeSetter(o, getFloat(valore), attributo);
				}
				if (BigDecimal.class.isAssignableFrom(type)) {
					invokeSetter(o, getBigDecimal(valore), attributo);
				}
				if (Boolean.class.isAssignableFrom(type)) {
					invokeSetter(o, toBool(valore), attributo);
				}
			}

		}
		return o;
	}

	/**
	 * Data una classe c genera una istanza della classe valorizzando le sue
	 * variabili istanza (dotate di metodi mutator) con valori tipo mock. Se la
	 * classe riporta l'annotazione @MockFile allora i valori di mock vengono
	 * prelevati dal file indicato nell'annotazione e vengono bypassati i valori
	 * generati casualmente con le relative restrizioni impostate nella
	 * annotazione @Mock
	 * 
	 * @param <K>
	 * @param c
	 * @return K
	 * @throws Exception
	 */
	public <K> K mock(Class<K> c) throws Exception {
		setComune(null);
		setNomeMock(null);
		setCognomeMock(null);
		if (String.class.isAssignableFrom(c)) {
			return (K) generateString(generateInt(10));
		}
		if (Date.class.isAssignableFrom(c)) {
			return (K) generateDate();
		}
		if (BigDecimal.class.isAssignableFrom(c)) {
			return (K) generateBigDecimal();
		}
		if (Long.class.isAssignableFrom(c)) {
			return (K) generateLong();
		}
		if (Integer.class.isAssignableFrom(c)) {
			return (K) generateInt();
		}
		if (Double.class.isAssignableFrom(c)) {
			return (K) generateDouble();
		}
		if (Short.class.isAssignableFrom(c)) {
			return (K) generateShort();
		}
		if (Float.class.isAssignableFrom(c)) {
			return (K) generateFloat();
		}
		if (Boolean.class.isAssignableFrom(c)) {
			return (K) generateBoolean();
		}
		K o = (K) c.newInstance();
		Field[] attributi = c.getDeclaredFields();
		Method[] metodi = c.getDeclaredMethods();

		for (Field f : attributi) {
			Mock mock = f.getAnnotation(Mock.class);
			if (mock != null && mock.skip())
				continue;
			String tipo = f.getType().getName().trim();
			if (!isPilotJDKClass(f.getType())) {
				invokeSetter(o, mock(Class.forName(tipo)), f);
			}

			if (is(tipo, JAVA_LIST, JAVA_PLIST, JAVA_SET)) {
				// LISTE
				String genericClassName = getGenericType(f);
				if (is(genericClassName, JAVA_STRING)) {
					if (!customFields(o, f, metodi)) {
						Collection mockColl = getMockColl(tipo);
						for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
							mockColl.add(generateString(generateInt(10), mock));
						}
						invokeSetter(o, mockColl, f);
					}
				} else if (is(genericClassName, JAVA_LONG)) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateLong(mock));
					}
					invokeSetter(o, mockColl, f);
				} else if (is(genericClassName, JAVA_INTEGER)) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateInt(mock));
					}
					invokeSetter(o, mockColl, f);
				} else if (is(genericClassName, JAVA_SHORT)) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateShort(mock));
					}
					invokeSetter(o, mockColl, f);
				} else if (is(genericClassName, JAVA_DOUBLE)) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateDouble(mock));
					}
					invokeSetter(o, mockColl, f);
				} else if (is(genericClassName, JAVA_FLOAT)) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateFloat(mock));
					}
					invokeSetter(o, mockColl, f);
				} else if (is(genericClassName, JAVA_BIG_DECIMAL)) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateBigDecimal(mock));
					}
					invokeSetter(o, mockColl, f);
				} else if (is(genericClassName, JAVA_DATE, JAVA_DATE_SQL)) {
					Collection mockColl = getMockColl(tipo);
					for (int i = 1; i <= MOCK_LIST_SIZE; i++) {
						mockColl.add(generateDate(mock));
					}
					invokeSetter(o, mockColl, f);
				} else {
					Collection mockColl = mockList(Class.forName(genericClassName), MOCK_LIST_SIZE);
					if (is(tipo, JAVA_SET)) {
						mockColl = new HashSet<String>(mockColl);
					}
					invokeSetter(o, mockColl, f);
				}

			}

			if (is(tipo, JAVA_LONG, JAVA_LONG_PRIV)) {
				if (!customFields(o, f, metodi))
					invokeSetter(o, generateLong(mock), f);
			}
			if (is(tipo, JAVA_BIG_DECIMAL)) {
				if (!customFields(o, f, metodi))
					invokeSetter(o, generateBigDecimal(mock), f);
			}
			if (is(tipo, JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
				if (!customFields(o, f, metodi))
					invokeSetter(o, generateInt(mock), f);
			}
			if (is(tipo, JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
				if (!customFields(o, f, metodi))
					invokeSetter(o, generateDouble(mock), f);
			}
			if (is(tipo, JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
				if (!customFields(o, f, metodi))
					invokeSetter(o, generateFloat(mock), f);
			}
			if (is(tipo, JAVA_BOOLEAN, JAVA_BOOLEAN_PRIV)) {
				invokeSetter(o, generateBoolean(mock), f);
			}
			if (is(tipo, JAVA_SHORT, JAVA_SHORT_PRIV)) {
				invokeSetter(o, generateShort(mock), f);
			}
			if (is(tipo, JAVA_DATE)) {
				invokeSetter(o, generateDate(mock), f);
			}
			if (is(tipo, JAVA_DATE_SQL)) {
				invokeSetter(o, getSQLDate(generateDate(mock)), f);
			}
			if (is(tipo, JAVA_STRING)) {
				if (!customFields(o, f, metodi)) {
					invokeSetter(o, generateString(6, mock), f);
				}
			}
		}
		checkOrdinamentoValori(o);
		checkBetween(o);
		checkBetweenValues(o);
		return o;

	}

	private <K, M extends Comparable> void checkOrdinamentoValori(K o) {
		Field[] attributi = o.getClass().getDeclaredFields();
		for (Field f : attributi) {
			Mock mock = f.getAnnotation(Mock.class);
			if (notNull(mock)) {
				if (notNull(mock.greaterThan())) {
					if (!checkSameType(attributi, f.getName(), mock.greaterThan())) {
						log("I campi", f.getName(), "e", mock.greaterThan(), "sono di tipo diverso. Ordinamento greaterThan non applicato sul campo", f.getName());
						return;
					}
					M valBase = null;
					try {
						valBase = (M) invokeGetter(o, f.getName());
					} catch (Exception e) {
						logError("Errore nel recupero del valore dell'attributo", f.getName(), e);
					}
					String campo = mock.greaterThan();
					M val = null;
					try {
						val = (M) invokeGetter(o, campo);
					} catch (Exception e) {
						logError("Errore nel recupero del valore dell'attributo", campo, e);
					}
					if (!maggioreDi(valBase, val)) {
						try {
							invokeSetter(o, val, f.getName());
						} catch (Exception e) {
							logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
						}
						try {
							invokeSetter(o, valBase, campo);
						} catch (Exception e) {
							logError("Errore nell'impostazione del valore dell'attributo", campo, e);
						}
					}
				} else if (notNull(mock.lowerThan())) {
					if (!checkSameType(attributi, f.getName(), mock.lowerThan())) {
						log("I campi", f.getName(), "e", mock.lowerThan(), "sono di tipo diverso. Ordinamento lowerThan non applicato sul campo", f.getName());
						return;
					}
					M valBase = null;
					try {
						valBase = (M) invokeGetter(o, f.getName());
					} catch (Exception e) {
						logError("Errore nel recupero del valore dell'attributo", f.getName(), e);
					}
					String campo = mock.lowerThan();
					M val = null;
					try {
						val = (M) invokeGetter(o, campo);
					} catch (Exception e) {
						logError("Errore nel recupero del valore dell'attributo", campo, e);
					}
					if (!minoreDi(valBase, val)) {
						try {
							invokeSetter(o, val, f.getName());
						} catch (Exception e) {
							logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
						}
						try {
							invokeSetter(o, valBase, campo);
						} catch (Exception e) {
							logError("Errore nell'impostazione del valore dell'attributo", campo, e);
						}
					}
				}

			}
		}
	}

	private Field getField(Field[] att, String campo) {
		Field attr = null;
		for (Field f : att) {
			if (is(f.getName(), campo)) {
				attr = f;
				break;
			}
		}
		return attr;
	}

	private boolean checkSameType(Field[] att, String campo1, String campo2) {
		if (NullOR(campo1, campo2))
			return false;
		Field att1 = getField(att, campo1);
		Field att2 = getField(att, campo2);
		if (NullOR(att1, att2))
			return false;
		return is(att1.getType().getName(), att2.getType().getName());
	}

	private <K, M extends Comparable> void checkBetween(K o) {
		Field[] attributi = o.getClass().getDeclaredFields();
		for (Field f : attributi) {
			Mock mock = f.getAnnotation(Mock.class);
			if (notNull(mock)) {
				if (null != mock.between() && mock.between().length == 2 && mock.allowed().length == 0) {
					String campo1 = mock.between()[0];
					String campo2 = mock.between()[1];
					if (!checkSameType(attributi, campo1, campo2)) {
						log("I campi", campo1, "e", campo2, "sono di tipo diverso. Between non applicata sul campo", f.getName());
						return;
					}
					M v1 = null;
					try {
						v1 = (M) invokeGetter(o, campo1);
					} catch (Exception e) {
						logError("Errore nel recupero del valore dell'attributo", campo1, e);
					}
					M v2 = null;
					try {
						v2 = (M) invokeGetter(o, campo2);
					} catch (Exception e) {
						logError("Errore nel recupero del valore dell'attributo", campo2, e);
					}
					Field f1 = getField(attributi, campo1);
					if (notNull(f1)) {
						if (is(f1.getType().getName(), JAVA_DATE, JAVA_DATE_SQL)) {
							try {
								invokeSetter(o, getDateBetween((Date) v1, (Date) v2), f);
							} catch (Exception e) {
								logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
							}
						}
						if (is(f1.getType().getName(), JAVA_LONG, JAVA_LONG_PRIV)) {
							try {
								invokeSetter(o, getLongBetween((Long) v1, (Long) v2), f);
							} catch (Exception e) {
								logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
							}
						}
						if (is(f1.getType().getName(), JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
							try {
								invokeSetter(o, getIntBetween((Integer) v1, (Integer) v2), f);
							} catch (Exception e) {
								logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
							}
						}
						if (is(f1.getType().getName(), JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
							try {
								invokeSetter(o, getDoubleBetween((Double) v1, (Double) v2), f);
							} catch (Exception e) {
								logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
							}
						}
						if (is(f1.getType().getName(), JAVA_SHORT, JAVA_SHORT_PRIV)) {
							try {
								invokeSetter(o, getShortBetween((Short) v1, (Short) v2), f);
							} catch (Exception e) {
								logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
							}
						}
						if (is(f1.getType().getName(), JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
							try {
								invokeSetter(o, getFloatBetween((Float) v1, (Float) v2), f);
							} catch (Exception e) {
								logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
							}
						}
						if (is(f1.getType().getName(), JAVA_BIG_DECIMAL)) {
							try {
								invokeSetter(o, getBigDecimalBetween((BigDecimal) v1, (BigDecimal) v2), f);
							} catch (Exception e) {
								logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
							}
						}
					}

				}

			}
		}
	}

	private <K, M extends Comparable> void checkBetweenValues(K o) {
		Field[] attributi = o.getClass().getDeclaredFields();
		for (Field f : attributi) {
			Mock mock = f.getAnnotation(Mock.class);
			if (notNull(mock)) {
				if (null != mock.between() && mock.betweenValues().length == 2 && mock.allowed().length == 0) {
					String v1 = mock.betweenValues()[0];
					String v2 = mock.betweenValues()[1];
					if (is(f.getType().getName(), JAVA_DATE, JAVA_DATE_SQL)) {
						try {
							invokeSetter(o, getDateBetween(toDate(v1), toDate(v2)), f);
						} catch (Exception e) {
							logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
						}
					}
					if (is(f.getType().getName(), JAVA_LONG, JAVA_LONG_PRIV)) {
						try {
							invokeSetter(o, getLongBetween(getLong(v1), getLong(v2)), f);
						} catch (Exception e) {
							logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
						}
					}
					if (is(f.getType().getName(), JAVA_INTEGER, JAVA_INTEGER_PRIV)) {
						try {
							invokeSetter(o, getIntBetween(getInteger(v1), getInteger(v2)), f);
						} catch (Exception e) {
							logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
						}
					}
					if (is(f.getType().getName(), JAVA_DOUBLE, JAVA_DOUBLE_PRIV)) {
						try {
							invokeSetter(o, getDoubleBetween(getDouble(v1), getDouble(v2)), f);
						} catch (Exception e) {
							logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
						}
					}
					if (is(f.getType().getName(), JAVA_SHORT, JAVA_SHORT_PRIV)) {
						try {
							invokeSetter(o, getShortBetween(getShort(v1), getShort(v2)), f);
						} catch (Exception e) {
							logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
						}
					}
					if (is(f.getType().getName(), JAVA_FLOAT, JAVA_FLOAT_PRIV)) {
						try {
							invokeSetter(o, getFloatBetween(getFloat(v1), getFloat(v2)), f);
						} catch (Exception e) {
							logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
						}
					}
					if (is(f.getType().getName(), JAVA_BIG_DECIMAL)) {
						try {
							invokeSetter(o, getBigDecimalBetween(getBigDecimal(v1), getBigDecimal(v2)), f);
						} catch (Exception e) {
							logError("Errore nell'impostazione del valore dell'attributo", f.getName(), e);
						}
					}

				}

			}
		}
	}

	private Long getLongBetween(Long start, Long end) {
		if (Null(start, end))
			return null;
		if (Null(start))
			return end - generateLong();
		if (Null(end))
			return start + generateLong();
		if (end < start)
			return null;
		PList<Long> values = pl();
		Long l = start;
		while (l <= end) {
			values.add(l);
			l++;
		}
		return values.get(generateInt(values.size()));
	}

	private Integer getIntBetween(Integer start, Integer end) {
		if (Null(start, end))
			return null;
		if (Null(start))
			return end - generateInt();
		if (Null(end))
			return start + generateInt();
		if (end < start)
			return null;
		return getLongBetween(start.longValue(), end.longValue()).intValue();
	}

	private Float getFloatBetween(Float start, Float end) {
		if (Null(start, end))
			return null;
		if (Null(start))
			return end - generateFloat();
		if (Null(end))
			return start + generateFloat();
		if (end < start)
			return null;
		Float l = getLongBetween(start.longValue(), end.longValue()).floatValue() + generateFloat();
		return between(l, start, end);
	}

	private Double getDoubleBetween(Double start, Double end) {
		if (Null(start, end))
			return null;
		if (Null(start))
			return end - generateLong() + generateDouble();
		if (Null(end))
			return start + generateLong() + generateDouble();
		if (end < start)
			return null;
		Double l = getLongBetween(start.longValue(), end.longValue()).doubleValue() + generateDouble();
		return between(l, start, end);
	}

	private Short getShortBetween(Short start, Short end) {
		if (Null(start, end))
			return null;
		if (Null(start))
			return new Integer((end - generateShort())).shortValue();
		if (Null(end))
			return new Integer((start + generateShort())).shortValue();
		if (end < start)
			return null;
		Integer l = getIntBetween(start.intValue(), end.intValue());
		return between(l.shortValue(), start, end);

	}

	private BigDecimal getBigDecimalBetween(BigDecimal start, BigDecimal end) {
		if (Null(start, end))
			return null;
		if (Null(start))
			return sottrai(end, generateBigDecimal());
		if (Null(end))
			return aggiungi(start, generateBigDecimal());
		if (minoreDi(end, start))
			return null;
		return getBigDecimal(getDoubleBetween(start.doubleValue(), end.doubleValue()));
	}

	private Date getDateBetween(Date start, Date end) {
		if (!isOrderedDate(start, end))
			return null;
		if (Null(start, end))
			return null;
		if (Null(start))
			return addDays(end, -generateInt(100));
		if (Null(end))
			return addDays(start, +generateInt(100));
		PList<Date> dateTra = getDatesBetween(start, end);
		return dateTra.get(generateInt(dateTra.size()));
	}

	private PList<Date> getDatesBetween(Date startDate, Date endDate) {
		PList<Date> datesInRange = pl();
		Calendar calendar = getCalendarWithoutTime(startDate);
		Calendar endCalendar = getCalendarWithoutTime(endDate);

		while (!calendar.after(endCalendar)) {
			Date result = calendar.getTime();
			datesInRange.add(result);
			calendar.add(Calendar.DATE, 1);
		}

		return datesInRange;
	}

	private static Calendar getCalendarWithoutTime(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	private String getGenericType(Field f) {
		String genericTypeName = f.getGenericType().toString();
		String genericClassName = JAVA_STRING;
		if (isLike(genericTypeName, "<")) {
			genericClassName = substring(f.getGenericType().toString(), "<", false, false, ">", false, true);
		}
		return genericClassName;
	}

	private <E> Collection<E> getMockColl(String tipo) {
		Collection<E> mockColl = null;
		if (is(tipo, JAVA_SET)) {
			mockColl = new HashSet<E>();
		} else {
			mockColl = pl();
		}
		return mockColl;
	}

	private Short generateShort(Mock... mock) {
		if (goAllowed(mock)) {
			return getShort(generateString(mock[0].allowed()));
		}
		return (short) new Random().nextInt(1 << 16);
	}

	private Long generateLong(Mock... mock) {
		if (goAllowed(mock)) {
			return getLong2(generateString(mock[0].allowed()));
		}
		return ThreadLocalRandom.current().nextLong(100);
	}

	private Date generateDate(Mock... mock) {
		if (goAllowed(mock)) {
			return toDate(generateString(mock[0].allowed()));
		}
		return stringToDate(strSep(SLASH, generateGiorno(), generateMese(), generateAnno()));
	}

	private BigDecimal generateBigDecimal(Mock... mock) {
		if (goAllowed(mock)) {
			return getBigDecimal(generateString(mock[0].allowed()));
		}
		return getBigDecimal(new Random().nextInt(10000) + generateDouble());
	}

	private Integer generateInt(Mock... mock) {
		if (goAllowed(mock)) {
			return getInteger(generateString(mock[0].allowed()));
		}
		return new Random().nextInt(100);
	}

	private Integer generateInt(Integer max) {
		return new Random().nextInt(max);
	}

	private Integer generateAnno(Mock... mock) {
		if (goAllowed(mock)) {
			return getInteger(generateString(mock[0].allowed()));
		}
		return getYear(now()) - new Random().nextInt(100);
	}

	private Integer generateMese(Mock... mock) {
		if (goAllowed(mock)) {
			return getInteger(generateString(mock[0].allowed()));
		}
		return 12 - new Random().nextInt(11);
	}

	private Integer generateGiorno(Mock... mock) {
		if (goAllowed(mock)) {
			return getInteger(generateString(mock[0].allowed()));
		}
		return 29 - new Random().nextInt(28);
	}

	private Integer generateTipo(Mock... mock) {
		if (goAllowed(mock)) {
			return getInteger(generateString(mock[0].allowed()));
		}
		return 100 - new Random().nextInt(99);
	}

	private String generateTipoString(Mock... mock) {
		if (goAllowed(mock)) {
			return generateString(mock[0].allowed());
		}
		return String.valueOf((100 - new Random().nextInt(99)));
	}

	private Double generateDouble(Mock... mock) {
		if (goAllowed(mock)) {
			return getDouble(generateString(mock[0].allowed()));
		}
		return new Random().nextDouble();
	}

	private Float generateFloat(Mock... mock) {
		if (goAllowed(mock)) {
			return getFloat(generateString(mock[0].allowed()));
		}
		return new Random().nextFloat();
	}

	private Double generatePerc(Mock... mock) {
		if (goAllowed(mock)) {
			return getDouble(generateString(mock[0].allowed()));
		}
		return getBigDecimal(100 * new Random().nextDouble()).doubleValue();
	}

	private Boolean generateBoolean(Mock... mock) {
		if (goAllowed(mock)) {
			return new Boolean(generateString(mock[0].allowed()));
		}
		return new Random().nextBoolean();
	}

	/**
	 * Torna true con una probabilitï¿½ del 10%
	 * 
	 * @return boolean
	 */
	public boolean prob10() {
		return prob(9);
	}

	/**
	 * Torna true con una probabilitï¿½ del 5%
	 * 
	 * @return boolean
	 */
	public boolean prob5() {
		return prob(4);
	}

	/**
	 * Torna true con una probabilitï¿½ del 20%
	 * 
	 * @return boolean
	 */
	public boolean prob20() {
		return prob(19);
	}

	/**
	 * Torna true con una probabilitï¿½ del 30%
	 * 
	 * @return boolean
	 */
	public boolean prob30() {
		return prob(29);
	}

	/**
	 * Torna true con una probabilitï¿½ del 40%
	 * 
	 * @return boolean
	 */
	public boolean prob40() {
		return prob(39);
	}

	/**
	 * Torna true con una probabilitï¿½ del 50%
	 * 
	 * @return boolean
	 */
	public boolean prob50() {
		return prob(49);
	}

	/**
	 * Torna true con una probabilitï¿½ del 60%
	 * 
	 * @return boolean
	 */
	public boolean prob60() {
		return prob(59);
	}

	/**
	 * Torna true con una probabilitï¿½ del 70%
	 * 
	 * @return boolean
	 */
	public boolean prob70() {
		return prob(69);
	}

	/**
	 * Torna true con una probabilitï¿½ del 80%
	 * 
	 * @return boolean
	 */
	public boolean prob80() {
		return prob(79);
	}

	/**
	 * Torna true con una probabilitï¿½ del 90%
	 * 
	 * @return boolean
	 */
	public boolean prob90() {
		return prob(89);
	}

	private boolean prob(Integer i) {
		return new Random().nextInt(100) <= i;
	}

	private <K> K invokeGetter(K k, String campo) throws Exception {
		PList<String> campi = toListString(campo, DOT);
		Object obj = k;
		for (int i = 0; i < campi.size() - 1; i++) {
			obj = invokeGetter(obj, campi.get(i));
		}
		return (K) obj.getClass().getMethod(str(GET, capFirstLetter((String) campi.getLastElement()))).invoke(obj);
	}

	private boolean isJavaType(Field att) {
		return almenoUna(startsWith(att.getType().getName(), "java."), is(att.getType().getName(), JAVA_PLIST, JAVA_BOOLEAN_PRIV, JAVA_DOUBLE_PRIV, JAVA_FLOAT_PRIV, JAVA_INTEGER_PRIV, JAVA_LONG_PRIV,
				JAVA_SHORT_PRIV));
	}

	/**
	 * Dato un oggetto K esegue il mask dell'oggetto secondo i campi indicati in
	 * fields, variabili istanza di quell'oggetto, restituendo un oggetto dello
	 * stesso tipo con valorizzate le sole variabili istanza indicate fields
	 * attribuendo loro gli stessi valori dell'oggetto in input k di cui si
	 * vuole eseguire il masking. Se fields elenco dei campi mask ï¿½ NULL,
	 * allora esegue il masking su tutti i campi dell'oggetto restituendo in
	 * pratica un altro oggetto con tutti i campi variabili istanza impostati
	 * agli stessi valori dell'oggetto k di input, in pratica fa una copia campo
	 * campo dell'oggetto passato in input
	 * 
	 * @param <K>
	 * @param k
	 * @param fields
	 * @return K
	 * @throws Exception
	 */
	public <K> K mask(K k, String... fields) throws Exception {
		if (Null(k))
			return null;
		Class cl = k.getClass();
		Constructor c = cl.getConstructor();
		K kNew = (K) c.newInstance();
		Field[] attributi = kNew.getClass().getDeclaredFields();
		for (Field f : attributi) {
			if (!isJavaType(f)) {
				// creo nuova istanza attributi
				invokeSetter(kNew, f.getType().newInstance(), f);
			}
		}
		PList<String> fieldsList = getPList(fields);
		if (Null(fieldsList)) {
			fieldsList = getPList(cl.getDeclaredFields()).narrow("name");
		}
		for (String campo : safe(fieldsList)) {
			try {
				K valueFrom = invokeGetter(k, campo);
				if (notNull(valueFrom))
					invokeSetter(kNew, valueFrom, campo);
			} catch (Exception e) {
				log("No setter or getter method for field", campo, e);
			}

		}
		return kNew;
	}

	/**
	 * Data una lista di oggetti di tipo K torna una nuova lista di oggetti
	 * dello stesso tipo masked secondo i campi indicati
	 * 
	 * @param <K>
	 * @param lista
	 * @param campi
	 * @return PList<K>
	 * @throws Exception
	 */
	public <K> PList<K> maskList(PList<K> lista, String... campi) throws Exception {
		PList<K> maskedList = pl();
		for (K k : safe(lista)) {
			maskedList.add(mask(k, campi));
		}
		return maskedList;
	}

	/**
	 * Ritorna una nuova stringa in cui ha impostato a maiuscola la prima
	 * lettera di ogni parola separata da spazi
	 * 
	 * @param s
	 * @return String
	 */
	public String capFirstLetterAfterSpace(String s) {
		s = emptyIfNull(despaceString(s));
		PList<String> l = toListString(s, SPACE);
		String out = null;
		for (String item : safe(l)) {
			out = str(out, capFirstLetter(item), SPACE);
		}
		return cutToLastOccurence(out, SPACE);
	}

	/**
	 * Ritorna una nuova stringa in cui ha impostato a minuscola la prima
	 * lettera di ogni parola separata da spazi
	 * 
	 * @param s
	 * @return String
	 */
	public String decapFirstLetterAfterSpace(String s) {
		s = emptyIfNull(despaceString(s));
		PList<String> l = toListString(s, SPACE);
		String out = null;
		for (String item : safe(l)) {
			out = str(out, decapFirstLetter(item), SPACE);
		}
		return cutLast(out);
	}

	/**
	 * Cancella il file individuato da pathFile percorso+nome
	 * 
	 * @param pathFile
	 */
	public void removeFile(String pathFile) {
		File f = new File(pathFile);
		if (f.exists()) {
			f.delete();
		} else {
			logError("File", pathFile, "inesistente");
		}
	}

	/**
	 * Torna true se pathFile (percorso+nome) esiste, false altrimenti
	 * 
	 * @param pathFile
	 * @return boolean
	 */
	public boolean fileExists(String pathFile) {
		return new File(pathFile).exists();
	}

	/**
	 * Esegue una iterazione anno-mese a partire dalla data d1 e fino alla data
	 * d2. Le date devono essere non nulle, valide e ordinate con d1<=d2;
	 * Ritorna una lista di interi dove ogni elemento rappresenta il mese a
	 * partire da Gennaio-->1 fino a Dicembre-->12. Esempio le date
	 * [09/04/2022,10/12/2023] producono in output
	 * 4,5,6,7,8,9,10,11,12,1,2,3,4,5,6,7,8,9,10,11,12
	 * 
	 * @param d1
	 * @param d2
	 * @return PList<Integer>
	 */
	public PList<Integer> iterateOverDates(Date d1, Date d2) {
		PList<Integer> mesi = pl();
		if (tutte(notNull(d1, d2), validaDate(d1, d2, false))) {
			Integer annoStart = getYear(d1);
			Integer annoEnd = getYear(d2);
			Integer meseStart = getMonth(d1);
			Integer meseEnd = getMonth(d2);
			int inizio = 1;
			int fine = 12;
			for (int anno = annoStart; anno <= annoEnd; anno++) {
				if (is(anno, annoStart)) {
					inizio = meseStart;
				} else {
					inizio = 1;
				}
				if (is(anno, annoEnd)) {
					fine = meseEnd;
				}
				for (int mese = inizio; mese <= fine; mese++) {
					mesi.add(mese);
				}
			}
		}
		return mesi;

	}

	/**
	 * Torna true se la data d coincide con l'ultimo giorno del mese
	 * 
	 * @param d
	 * @return boolean
	 */
	public boolean isLastDayOfMonth(Date d) {
		if (Null(d))
			return false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return is(getDay(d), cal.getActualMaximum(Calendar.DATE));
	}

	/**
	 * Trasforma una oggetto PList<K> in un classico ArrayList<K> java standard
	 * 
	 * @param <K>
	 * @param lista
	 * @return List<K>
	 */
	public <K> List<K> toArrayList(PList<K> lista) {
		List<K> l = new ArrayList<K>();
		for (K k : safe(lista)) {
			l.add(k);
		}
		return l;
	}

	private List<Comune> getComuni() {
		return comuni;
	}

	private Comune getComune() {
		return comune;
	}

	private void setComune(Comune comune) {
		this.comune = comune;
	}

	private boolean isReadFileComuni() {
		return readFileComuni;
	}

	private void setReadFileComuni(boolean readFileComuni) {
		this.readFileComuni = readFileComuni;
	}

	private String getNomeMock() {
		return nomeMock;
	}

	private void setNomeMock(String nomeMock) {
		this.nomeMock = nomeMock;
	}

	private String getCognomeMock() {
		return cognomeMock;
	}

	private void setCognomeMock(String cognomeMock) {
		this.cognomeMock = cognomeMock;
	}

	/**
	 * Esegue la deepCopy dell'oggetto K serializzabile
	 * 
	 * @param <K>
	 * @param o
	 * @return K
	 */
	public <K extends Serializable> K copyObj(K o) {
		try {
			return (K) SerializationUtils.clone(o);
		} catch (Exception e) {
			logError("Errore durante il clone dell'oggetto", o);
		}
		return null;
	}

	/**
	 * Alias di copyObj
	 * 
	 * @param <K>
	 * @param o
	 * @return K
	 */
	public <K extends Serializable> K clone(K o) {
		return copyObj(o);
	}

	private Properties readPropertyFile(String path) {
		if (Null(path)) {
			logError("Specificare il file properties da cui recuperare le associazioni key/value, attraverso il metodo setPropertyFile");
			return props;
		}
		if (Null(props)) {
			try {
				props.load(new FileInputStream(path));
			} catch (Exception e) {
				logError("Errore durante l'accesso al file ", path);
			}
		}
		return props;
	}

	/**
	 * Ritorna il valore della key all'interno del file di properties
	 * specificato precedentemente tramite il metodo setPropertyFile.
	 * 
	 * @param key
	 * @return String
	 */
	public String getKey(String key) {
		return readPropertyFile(getPropertyFile()).getProperty(key);
	}

	/**
	 * Ritorna il valore Integer della key all'interno del file di properties
	 * specificato precedentemente tramite il metodo setPropertyFile.
	 * 
	 * @param key
	 * @return Integer
	 */
	public Integer getKeyInt(String key) {
		return getInteger(getKey(key));
	}

	/**
	 * Ritorna il valore Integer della key all'interno del file di properties
	 * specificato precedentemente tramite il metodo setPropertyFile. Se la
	 * chiave non ï¿½ mappata o non ha specificato alcun valore, ritorna il
	 * defaultValue
	 * 
	 * @param key
	 * @return Integer
	 */
	public Integer getKeyInt(String key, Integer defaultValue) {
		return getInteger(getKey(key, zeroIfNull(defaultValue).toString()));
	}

	/**
	 * Ritorna il valore Long della key all'interno del file di properties
	 * specificato precedentemente tramite il metodo setPropertyFile. Se la
	 * chiave non ï¿½ mappata o non ha specificato alcun valore, ritorna il
	 * defaultValue
	 * 
	 * @param key
	 * @return Integer
	 */
	public Long getKeyLong(String key, Long defaultValue) {
		return getLong(getKey(key, zeroIfNull(defaultValue).toString()));
	}

	/**
	 * Ritorna il valore Long della key all'interno del file di properties
	 * specificato precedentemente tramite il metodo setPropertyFile.
	 * 
	 * @param key
	 * @return Long
	 */
	public Long getKeyLong(String key) {
		return getLong(getKey(key));
	}

	/**
	 * Ritorna il valore della key all'interno del file di properties
	 * specificato precedentemente tramite il metodo setPropertyFile. Se non la
	 * trova o la trova ma senza alcun valore, ritorna il default value.
	 * 
	 * @param key
	 * @return String
	 */
	public String getKey(String key, String defaultValue) {
		return valIfNull(getKey(key), defaultValue);
	}

	/**
	 * Ritorna true se la stringa s ï¿½ uno tra i valori 1,"true","S","SI","Y",
	 * false altrimenti
	 * 
	 * @param s
	 * @return boolean
	 */
	public boolean toBool(String s) {
		return is(s, "true", "1", S, SI, "Y");
	}

	/**
	 * Ritorna true se il valore corrispondente alla chiave key ï¿½ uno tra i
	 * valori 1,"true","S","SI","Y", false altrimenti. Il file di properties
	 * deve essere precedentemente impostato attraverso il metodo
	 * setPropertyFile.
	 * 
	 * @param key
	 * @return Boolean
	 */
	public Boolean getKeyBool(String key) {
		return toBool(getKey(key));
	}

	/**
	 * Ritorna true se il valore corrispondente alla chiave key ï¿½ uno tra i
	 * valori 1,"true","S","SI","Y", false altrimenti. Se il valore ï¿½ null
	 * ritorna il defaultValue booleano. Il file di properties deve essere
	 * precedentemente impostato attraverso il metodo setPropertyFile.
	 * 
	 * @param key
	 * @return Boolean
	 */
	public Boolean getKeyBool(String key, boolean defaultValue) {
		String value = getKey(key);
		return Null(value) ? defaultValue : toBool(value);
	}

	/**
	 * Ritorna una PList<String> contenente i valori corrispondenti alla chiave
	 * key quando questi sono separati da ",". Il file di properties deve essere
	 * precedentemente impostato attraverso il metodo setPropertyFile.
	 * 
	 * @param key
	 * @return PList<String>
	 */
	public PList<String> getKeyList(String key) {
		return toListString(getKey(key), COMMA);
	}

	/**
	 * Ritorna una PList<String> contenente i valori corrispondenti alla chiave
	 * key quando questi sono separati dal carattere separator. Il file di
	 * properties deve essere precedentemente impostato attraverso il metodo
	 * setPropertyFile.
	 * 
	 * @param key
	 * @return PList<String>
	 */
	public PList<String> getKeyList(String key, String separator) {
		return toListString(getKey(key), separator);
	}

	/**
	 * Ritorna una PList<Integer> contenente i valori Integer corrispondenti
	 * alla chiave key quando questi sono separati da ",". Il file di properties
	 * deve essere precedentemente impostato attraverso il metodo
	 * setPropertyFile.
	 * 
	 * @param key
	 * @return PList<Integer>
	 */
	public PList<Integer> getKeyListInt(String key) {
		return toListInteger(getKey(key), COMMA);
	}

	/**
	 * Ritorna una PList<Integer> contenente i valori Integer corrispondenti
	 * alla chiave key quando questi sono separati dal carattere separator. Il
	 * file di properties deve essere precedentemente impostato attraverso il
	 * metodo setPropertyFile.
	 * 
	 * @param key
	 * @param separator
	 * @return PList<Integer>
	 */
	public PList<Integer> getKeyListInt(String key, String separator) {
		return toListInteger(getKey(key), separator);
	}

	/**
	 * Ritorna una PList<Long> contenente i valori Integer corrispondenti alla
	 * chiave key quando questi sono separati da ",". Il file di properties deve
	 * essere precedentemente impostato attraverso il metodo setPropertyFile.
	 * 
	 * @param key
	 * @return PList<Long>
	 */
	public PList<Long> getKeyListLong(String key) {
		return toListLong(getKey(key), COMMA);
	}

	/**
	 * Ritorna una PList<Long> contenente i valori Integer corrispondenti alla
	 * chiave key quando questi sono separati dal carattere separator. Il file
	 * di properties deve essere precedentemente impostato attraverso il metodo
	 * setPropertyFile.
	 * 
	 * @param key
	 * @param separator
	 * @return PList<Long>
	 */
	public PList<Long> getKeyListLong(String key, String separator) {
		return toListLong(getKey(key), separator);
	}

	/**
	 * Ritorna una PList<Double> contenente i valori Integer corrispondenti alla
	 * chiave key quando questi sono separati da ",". Il file di properties deve
	 * essere precedentemente impostato attraverso il metodo setPropertyFile.
	 * 
	 * @param key
	 * @return PList<Double>
	 */
	public PList<Double> getKeyListDouble(String key) {
		return toListDouble(getKey(key), COMMA);
	}

	/**
	 * Ritorna una PList<Double> contenente i valori Integer corrispondenti alla
	 * chiave key quando questi sono separati dal carattere separator. Il file
	 * di properties deve essere precedentemente impostato attraverso il metodo
	 * setPropertyFile.
	 * 
	 * @param key
	 * @param separator
	 * @return PList<Double>
	 */
	public PList<Double> getKeyListDouble(String key, String separator) {
		return toListDouble(getKey(key), separator);
	}

	/**
	 * Ritorna il nome completo del file di properties
	 * 
	 * @return String
	 */
	public String getPropertyFile() {
		return propertyFile;
	}

	/**
	 * Imposta il file di properties (path+nome.properties) da cui recuperare le
	 * associazioni key/value tramite i metodi getKey(...)
	 * 
	 * @param propertyFile
	 */
	public void setPropertyFile(String propertyFile) {
		this.propertyFile = propertyFile;
	}

	/**
	 * Ritorna la stringa "0"+num se num ï¿½ compreso tra [0,9]. Altrimenti
	 * ritorna num in formato stringa
	 * 
	 * @param num
	 * @return String
	 */
	public String zeroStart(int num) {
		if (num > 0 && num <= 9) {
			return "0" + num;
		} else
			return "" + num;
	}

	/**
	 * Aggiunge alla mappa TimeCount contenitore, il TimeCount identificato da
	 * name
	 * 
	 * @param name
	 */
	public void aggiungiTimeCount(String name) {
		getMapTimeCount().put(name, new TimeCount(name));
	}

	/**
	 * Rimuove dalla mappa TimeCount contenitore il TimeCount identificato da
	 * name
	 * 
	 * @param name
	 */
	public void rimuoviTimeCount(String name) {
		getMapTimeCount().remove(name);
	}

	/**
	 * Azzera completamente la mappa TimeCount contenitore di tutti i TimeCount
	 */
	public void resetTimeCount() {
		setMapTimeCount(new HashMap<String, TimeCount>());
	}

	/**
	 * Al TimeCount identificato da name, aggiunge il Time t
	 * 
	 * @param name
	 * @param t
	 */
	public void addTimeToTimeCount(String name, Time t) {
		TimeCount tc = getMapTimeCount().get(name);
		if (notNull(tc)) {
			tc.addTime(t);
		}
	}

	/**
	 * Metodo alias di addTimeToTimeCount
	 * 
	 * @param name
	 * @param t
	 */
	public void lap(String name, Time t) {
		addTimeToTimeCount(name, t);
	}

	/**
	 * Al TimeCount identificato da name, aggiunge il tempo trascorso tra
	 * startDate e now;
	 * 
	 * @param name
	 * @param startDate
	 */
	public void addTimeToTimeCount(String name, Date startDate) {
		TimeCount tc = getMapTimeCount().get(name);
		if (notNull(tc)) {
			tc.addTime(startDate);
		}
	}

	/**
	 * Metodo alias di addTimeToTimeCount
	 * 
	 * @param name
	 * @param startDate
	 */
	public void lap(String name, Date startDate) {
		addTimeToTimeCount(name, startDate);
	}

	/**
	 * Ritorna le info del TimeCount specificato
	 * 
	 * @param name
	 * @return String
	 */
	public String getTimeCountInfo(String name) {
		String info = "";
		TimeCount tc = getMapTimeCount().get(name);
		if (notNull(tc)) {
			info = tc.getInfo();
		} else {
			info = strSpaced("TimeCount", name, " inesistente");
		}
		return info;
	}

	/**
	 * Ritorna la stringa complessiva dei tempi intermedi di tutti i servizi
	 * impostati nella mappa TimeCount globale. I TimeCount che non hanno
	 * chiamate non vengono stampati
	 * 
	 * @return String
	 */
	public String getTimeCountTotalInfo() {
		String s = "";
		PList<String> contenuto = plstr();
		for (Map.Entry<String, TimeCount> entry : getMapTimeCount().entrySet()) {
			s = getTimeCountInfo(entry.getKey());
			if (notNull(s)) {
				contenuto.add(s);
			}
		}
		return getInfoFramed("RIEPILOGO TEMPI INTERMEDI", contenuto);
	}

	/**
	 * Metodo alias di getTimeCountTotalInfo
	 * 
	 * @return String
	 */
	public String getLapTotalInfo() {
		return getTimeCountTotalInfo();
	}

	/**
	 * Ritorna in formato stringa l'ultimo oggetto Time aggiunto al TimeCount
	 * identificato da nome
	 * 
	 * @param name
	 * @return String
	 */
	public String getLastAddedTimeInTimeCount(String name) {
		String info = "";
		TimeCount tc = getMapTimeCount().get(name);
		if (notNull(tc)) {
			info = tc.getLastAddedTime().getTotalTime();
		} else {
			info = strSpaced("TimeCount", name, " inesistente");
		}
		return info;
	}

	/**
	 * Metodo alias di getLastAddedTimeInTimeCount
	 * 
	 * @param name
	 * @return String
	 */
	public String getLap(String name) {
		return getLastAddedTimeInTimeCount(name);
	}

	private Map<String, TimeCount> getMapTimeCount() {
		return mapTimeCount;
	}

	private void setMapTimeCount(Map<String, TimeCount> mapTimeCount) {
		this.mapTimeCount = mapTimeCount;
	}

	/**
	 * Al TimeCount identificato da timeCounter, aggiunge il tempo trascorso tra
	 * startDate e now ed esegue la stampa di log dell'intertempo aggiunto al
	 * totale. Prefix ï¿½ una stringa descrittiva di libera scelta.
	 * 
	 * @param prefix
	 * @param timeCounter
	 * @param start
	 */
	public void lapAndLog(String prefix, String timeCounter, Date start) {
		lap(timeCounter, start);
		log("------->LAP TIME------>", prefix, getLap(timeCounter));
	}

	/**
	 * Ritorna la stringa risultato della concatenazione del carattere car per l
	 * volte
	 * 
	 * @param l
	 * @param car
	 * @return String
	 */
	public String repeat(Integer l, String car) {
		String out = "";
		for (int i = 1; i <= l; i++) {
			out = str(out, car);
		}
		return out;
	}

	/**
	 * Ritorna la stampa del titolo title al centro di una cornice fatta dalla
	 * ripetizione del carattere car per length volte. TabIndent indica il
	 * margine da usare per la stampa delle stringhe di container all'interno
	 * della cornice. Length ï¿½ la lunghezza della cornice. Container ï¿½ la
	 * lista elenco che contiene il contenuto che verrï¿½ stampato all'interno
	 * della cornice.
	 * 
	 * 
	 * @param title
	 * @param length
	 * @param car
	 * @param tabIndent
	 * @param container
	 * @return String
	 */
	public String getInfoFramed(String title, Integer length, String car, Integer tabIndent, PList<String> container) {
		String out = "";
		if (notNull(container)) {
			PList<Integer> lunghezze = pl();
			for (String s : container) {
				lunghezze.add(s.length());
			}
			Integer max = lunghezze.sortDesc().getFirstElement();
			if (title.length() > max) {
				max = title.length();
			}
			if (max >= length) {
				length = max + 20;
			}
			tabIndent = limiteSuperiore(tabIndent, 3);
			if (tabIndent <= 0)
				tabIndent = 0;
			String margin = str(car, repeat(tabIndent * 2, SPACE));
			out = str(lf(), getTitle(title, length, car));
			int mancanti = 0;
			int marginLengh = 1 + tabIndent * 2;
			for (String s : safe(container)) {
				s = replace(s, tab(), SPACE);
				int offset = marginLengh + s.length();
				mancanti = length - offset - 2;
				out = str(out, margin, s, repeat(mancanti, SPACE), car, lf());
			}
			out = str(out, getTitle(car, length, car));
		}
		return out;
	}

	/**
	 * Ritorna una stringa incorniciata con il carattere "*". Title ï¿½ il
	 * titolo centrato nel lato superiore della cornice. Container ï¿½ l'elenco
	 * delle stringhe da stampare all'interno. Ogni stringa viene stampata con
	 * margine di 2 tabulazione a sinistra
	 * 
	 * @param title
	 * @param container
	 * @return String
	 */
	public String getInfoFramed(String title, PList<String> container) {
		return getInfoFramed(title, 0, "*", 2, container);
	}

	/**
	 * Alias di getInfoFramed
	 * 
	 * @param title
	 * @param container
	 * @return String
	 */
	public String frame(String title, PList<String> container) {
		return getInfoFramed(title, container);
	}

	/**
	 * Alias di getInfoFramed
	 * 
	 * @param title
	 * @param length
	 * @param car
	 * @param tabIndent
	 * @param container
	 * @return String
	 */
	public String frame(String title, Integer length, String car, Integer tabIndent, PList<String> container) {
		return getInfoFramed(title, length, car, tabIndent, container);
	}

	/**
	 * Esegue in modalitï¿½ asincrona il codice inserito dentro il metodo
	 * execute dell'oggetto o che implementa l'interfaccia Execution
	 * 
	 * @param <K>
	 * @param o
	 * @return Future
	 */
	public <K extends Execution> Future stacca(final K o) {
		final ExecutorService e = Executors.newFixedThreadPool(3);
		return e.submit(new Runnable() {
			@Override
			public void run() {
				o.execute();
				e.shutdownNow();
			}
		});
	}

	/**
	 * Dato un valore numerico di byte, ritorna la rappresentazione piï¿½ adatta
	 * corrispondente in Tb,Gb,Mb,Kb,B
	 * 
	 * @param value
	 * @return String
	 */
	public String convertToStringRepresentation(long value) {
		long[] dividers = new long[] { T, G, M, K, 1 };
		String[] units = new String[] { "TB", "GB", "MB", "KB", "B" };
		if (value < 1)
			throw new IllegalArgumentException("Invalid file size: " + value);
		String result = null;
		for (int i = 0; i < dividers.length; i++) {
			long divider = dividers[i];
			if (value >= divider) {
				result = format(value, divider, units[i]);
				break;
			}
		}
		return result;
	}

	private String format(long value, long divider, String unit) {
		double result = divider > 1 ? (double) value / (double) divider : (double) value;
		return str(new DecimalFormat("#,##0.#").format(result), SPACE, unit);
	}

	/**
	 * Stampa a video le Machine Info (numero di processori, memoria RAM, Spazio
	 * Libero sul File System)
	 */
	public void hw() {
		PList<String> cont = plstr();
		cont.add(str("Number of cores: ", Runtime.getRuntime().availableProcessors()));
		File[] roots = File.listRoots();
		cont.add(str("RAM: ", convertToStringRepresentation(((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize())));
		cont.add(repeat(30, "-"));
		for (File root : roots) {
			cont.add(str("File system root: " + root.getAbsolutePath()));
			long totalSpace = root.getTotalSpace();
			long freeSpace = root.getFreeSpace();
			long usableSpace = root.getUsableSpace();
			cont.add(str("Total space: ", convertToStringRepresentation(totalSpace)));
			cont.add(str("Free space: ", convertToStringRepresentation(freeSpace), tab(), percentuale(getBigDecimal(freeSpace), getBigDecimal(totalSpace)), "%"));
			cont.add(str("Usable space: ", convertToStringRepresentation(usableSpace), tab(), percentuale(getBigDecimal(usableSpace), getBigDecimal(totalSpace)), "%"));
			cont.add(repeat(30, "-"));
		}
		log(frame("MACHINE INFO", cont));
	}

	/**
	 * Elimina dalla stringa s tutte le stringhe vals indicate
	 * 
	 * @param s
	 * @param vals
	 * @return String
	 */
	public String elimina(String s, String... vals) {
		if (notNull(s)) {
			for (String t : vals) {
				s = replace(s, t, "");
			}

		}
		return s;
	}

	/**
	 * Trova il massimo tra i valori vals passati
	 * 
	 * @param <K>
	 * @param vals
	 * @return K
	 */
	public <K extends Comparable<K>> K max(K... vals) {
		K max = null;
		if (vals != null && vals.length > 0) {
			max = vals[0];
			for (int i = 1; i < vals.length; i++) {
				if (maggioreDi(vals[i], max)) {
					max = vals[i];
				}
			}
		}
		return max;
	}

	/**
	 * Trova il minimo tra i valori vals passati
	 * 
	 * @param <K>
	 * @param vals
	 * @return K
	 */
	public <K extends Comparable<K>> K min(K... vals) {
		K min = null;
		if (vals != null && vals.length > 0) {
			min = vals[0];
			for (int i = 1; i < vals.length; i++) {
				if (minoreDi(vals[i], min)) {
					min = vals[i];
				}
			}
		}
		return min;
	}

	/**
	 * Data una stringa, ritorna una PList di oggetti KeyValue<String,String>
	 * dove key ï¿½ il nome del parametro i-esimo della query string (tutto
	 * quello dopo ?) e value ï¿½ il corrispondente valore. Esempio: stringa del
	 * tipo: stringaconqueryString?par=value&par1=value1&par2=value2 la
	 * querystring ï¿½ tutto ciï¿½ che si trova a destra del primo ?
	 * 
	 * @param s
	 * @return PList<KeyValue<String, String>>
	 */
	public PList<KeyValue<String, String>> getQueryString(String s) {
		PList<KeyValue<String, String>> params = pl();
		if (isLike(s, QS_START)) {
			String nome = "";
			String valore = "";
			String after = substring(s, QS_START, false, false, null, false, false);
			for (String item : after.split("&")) {
				nome = substring(item, null, false, false, EQ, false, false);
				valore = substring(item, EQ, false, false, null, false, false);
				params.add(new KeyValue<String, String>(nome, valore));
			}
		}
		return params;
	}

	/**
	 * Data una stringa input e un delimitatore, spezzetta la stringa in
	 * ingresso in base al delimitatore restituendo in output una PList dei
	 * pezzi di stringa risultato dello spezzettamento della stringa input in
	 * base al delimitatore.
	 * 
	 * @param input
	 * @param delimiter
	 * @return PList<String>
	 */
	public PList<String> split(String input, String delimiter) {
		PList<String> result = plstr();
		input = emptyIfNull(input);
		if (Null(delimiter)) {
			result.add(input);
		} else {
			int startIndex = 0;
			int endIndex;
			while ((endIndex = input.indexOf(delimiter, startIndex)) != -1) {
				String part = input.substring(startIndex, endIndex);
				if (notNull(part)) {
					result.add(part);
				}
				startIndex = endIndex + delimiter.length();
			}
			String lastPart = input.substring(startIndex);
			if (notNull(lastPart))
				result.add(lastPart);
		}
		return result;
	}

	/**
	 * Esegue lo split della stringa di input in base al carattere "-"
	 * 
	 * @param input
	 * @return PList<String>
	 */
	public PList<String> splitDash(String input) {
		return split(input, DASHTRIM);
	}

	/**
	 * Esegue lo split della stringa di input in base al carattere ","
	 * 
	 * @param input
	 * @return PList<String>
	 */
	public PList<String> splitComma(String input) {
		return split(input, COMMA);
	}

	/**
	 * Esegue lo split della stringa di input in base al carattere "|"
	 * 
	 * @param input
	 * @return PList<String>
	 */
	public PList<String> splitPipe(String input) {
		return split(input, PIPE);
	}

	/**
	 * Esegue lo split della stringa di input in base al carattere " "
	 * 
	 * @param input
	 * @return PList<String>
	 */
	public PList<String> splitSpace(String input) {
		return split(input, SPACE);
	}

	/**
	 * Ritorna l'ultimo carattere di una stringa s
	 * 
	 * @param s
	 *            s
	 * @return String
	 */
	public String getLast(String s) {
		if (Null(s))
			return s;
		return s.substring(s.length() - 1);
	}

	/**
	 * Ritorna il primo carattere di una stringa s
	 * 
	 * @param s
	 *            s
	 * @return String
	 */
	public String getFirst(String s) {
		if (Null(s))
			return s;
		return s.substring(0);
	}

	/**
	 * Ritorna la rappresentazione plurale o singolare della stringa in base al
	 * valore del parametro num. Singolare se num è 1. Plurale in tutti gli
	 * altri casi.
	 * 
	 * La stringa s deve essere indicata in questo modo : persona/e che
	 * lavora/ano, assegnata/e al progetto in squadra
	 * 
	 * separando la desinenza singolare/plurale
	 * 
	 * @param num
	 *            num
	 * @param s
	 *            s
	 * @return String
	 */
	public String pluraleSingolare(int num, String s) {
		if (Null(s))
			return s;
		boolean singolare = num == 1;
		String[] parti = s.split(SPACE);
		PList<String> nuove = plstr();
		String sing = null;
		String pl = null;
		String mettere = null;
		for (String _s : parti) {
			if (!isLike(_s, SLASH)) {
				nuove.add(_s);
				continue;
			}
			sing = getLast(substring(_s, null, false, false, SLASH, false, false));
			pl = getFirst(substring(_s, SLASH, false, false, null, false, false));
			mettere = singolare ? sing : pl;
			String davanti = cutLast(substring(_s, null, false, false, SLASH, false, false));
			nuove.add(str(davanti, mettere));
		}
		return concatenaListaStringhe(nuove, SPACE);
	}

	/**
	 * Concatena gli oggetti values nella loro rappresentazione stringa
	 * concatenandoli tra loro con il carattere "-"
	 * 
	 * @param values
	 * @return String
	 */
	public String strSepDash(Object... values) {
		return getStringSep(DASHTRIM, values);
	}

	/**
	 * Concatena gli oggetti values nella loro rappresentazione stringa
	 * concatenandoli tra loro con il carattere ","
	 * 
	 * @param values
	 * @return String
	 */
	public String strSepComma(Object... values) {
		return getStringSep(COMMA, values);
	}

	/**
	 * Concatena gli oggetti values nella loro rappresentazione stringa
	 * concatenandoli tra loro con il carattere " "
	 * 
	 * @param values
	 * @return String
	 */
	public String strSepSpace(Object... values) {
		return getStringSep(SPACE, values);
	}

	/**
	 * Concatena gli oggetti values nella loro rappresentazione stringa
	 * concatenandoli tra loro con il carattere "|"
	 * 
	 * @param values
	 * @return String
	 */
	public String strSepPipe(Object... values) {
		return getStringSep(PIPE, values);
	}

	/**
	 * Concatena gli elementi della lista con il carattere "-"
	 * 
	 * @param l
	 * @return String
	 */
	public String concatenaDashListaStringhe(List<String> l) {
		return concatenaListaStringhe(l, "-");
	}

	/**
	 * Concatena gli elementi della lista con il carattere ","
	 * 
	 * @param l
	 * @return String
	 */
	public String concatenaCommaListaStringhe(List<String> l) {
		return concatenaListaStringhe(l, COMMA);
	}

	/**
	 * Concatena gli elementi della lista con il carattere "|"
	 * 
	 * @param l
	 * @return String
	 */
	public String concatenaPipeListaStringhe(List<String> l) {
		return concatenaListaStringhe(l, PIPE);
	}

	/**
	 * Concatena gli elementi della lista con il carattere " "
	 * 
	 * @param l
	 * @return String
	 */
	public String concatenaSpaceListaStringhe(List<String> l) {
		return concatenaListaStringhe(l, SPACE);
	}

	/**
	 * Data una stringa di elementi con separatore "-", ottengo una Lista di
	 * elementi di tipo c specificato dove c è il tipo dell'elememto.Tipi
	 * previsti sono String, Integer, Long,Short,Double,BigDecimal
	 * 
	 * @param s
	 * @param c
	 * @return PList<K>
	 */
	public <K> PList<K> toListDashSep(String s, Class<K> c) {
		return toList(s, DASHTRIM, c);
	}

	/**
	 * Data una stringa di elementi con separatore ",", ottengo una Lista di
	 * elementi di tipo c specificato dove c è il tipo dell'elememto.Tipi
	 * previsti sono String, Integer, Long,Short,Double,BigDecimal
	 * 
	 * @param s
	 * @param c
	 * @return PList<K>
	 */
	public <K> PList<K> toListCommaSep(String s, Class<K> c) {
		return toList(s, COMMA, c);
	}

	/**
	 * Data una stringa di elementi con separatore "|", ottengo una Lista di
	 * elementi di tipo c specificato dove c è il tipo dell'elememto.Tipi
	 * previsti sono String, Integer, Long,Short,Double,BigDecimal
	 * 
	 * @param s
	 * @param c
	 * @return PList<K>
	 */
	public <K> PList<K> toListPipeSep(String s, Class<K> c) {
		return toList(s, PIPE, c);
	}

	/**
	 * Data una stringa di elementi con separatore "-", ottengo un Set di
	 * elementi di tipo c specificato dove c è il tipo dell'elememto. Tipi
	 * previsti sono String, Integer, Long,Short,Double,BigDecimal
	 * 
	 * @param s
	 * @param c
	 * @return Set<K>
	 */
	public <K> Set<K> toSetDashSep(String s, Class<K> c) {
		return toSet(s, DASHTRIM, c);
	}

	/**
	 * Data una stringa di elementi con separatore ",", ottengo un Set di
	 * elementi di tipo c specificato dove c è il tipo dell'elememto. Tipi
	 * previsti sono String, Integer, Long,Short,Double,BigDecimal
	 * 
	 * @param s
	 * @param c
	 * @return Set<K>
	 */
	public <K> Set<K> toSetCommaSep(String s, Class<K> c) {
		return toSet(s, COMMA, c);
	}

	/**
	 * Data una stringa di elementi con separatore "|", ottengo un Set di
	 * elementi di tipo c specificato dove c è il tipo dell'elememto. Tipi
	 * previsti sono String, Integer, Long,Short,Double,BigDecimal
	 * 
	 * @param s
	 * @param c
	 * @return Set<K>
	 */
	public <K> Set<K> toSetPipeSep(String s, Class<K> c) {
		return toSet(s, PIPE, c);
	}

	/**
	 * Dato un oggetto date ritorna un oggetto PDate
	 * 
	 * @param d
	 * @return PDate
	 */
	public PDate pd(Date d) {
		return new PDate(d);
	}

	/**
	 * Ritorna un oggetto PDate
	 * 
	 * @return PDate
	 */
	public PDate pd() {
		return new PDate();
	}

	/**
	 * genera un id univoco
	 * 
	 * @return String
	 */
	public String getIdUnivoco() {
		return str(System.currentTimeMillis(), Math.random());
	}

	/**
	 * Imposta alla key il valore val come variabile di stato dell'intera
	 * applicazione in modo da evitare il continuo passaggio di parametri tra
	 * metodi (parameters drilling)
	 * 
	 * @param key
	 * @param val
	 */
	public void setState(String key, Object val) {
		State.getInstance().setState(key, val);
	}

	/**
	 * Ritorna il valore corrispondente alla key variabile di stato dell'intera
	 * applicazione
	 * 
	 * @param key
	 * @return Object
	 */
	public Object getState(String key) {
		return State.getInstance().getState(key);
	}

	/**
	 * Ritorna il valore String corrispondente alla key variabile di stato
	 * dell'intera applicazione
	 * 
	 * @param key
	 * @return String
	 */
	public String getStateStr(String key) {
		return (String) getState(key);
	}

	/**
	 * Ritorna il valore Long corrispondente alla key variabile di stato
	 * dell'intera applicazione
	 * 
	 * @param key
	 * @return Long
	 */
	public Long getStateLong(String key) {
		return (Long) getState(key);
	}

	/**
	 * Ritorna il valore Date corrispondente alla key variabile di stato
	 * dell'intera applicazione
	 * 
	 * @param key
	 * @return Date
	 */
	public Date getStateDate(String key) {
		return (Date) getState(key);
	}

	/**
	 * Svuota completamente lo stato dell'applicazione con tutte le sue
	 * variabili impostate
	 */
	public void setStateEmpty() {
		State.getInstance().destroy();
	}

	/**
	 * Svuota la variabile di stato identificata da key
	 * 
	 * @param key
	 */
	public void clearState(String key) {
		State.getInstance().clearState(key);
	}

	/**
	 * Alias di setStateEmpty
	 * 
	 */
	public void clearState() {
		setStateEmpty();
	}

	/**
	 * Ritorna lo stato completo dell'applicazione con i valori di ogni
	 * variabile di stato impostata
	 * 
	 * @return PMap<String, Object>
	 */
	public PMap<String, Object> getState() {
		return State.getInstance().getStateFull();
	}

	/**
	 * Se val è true torna S altrimenti torna N
	 * 
	 * @param val
	 * @return String
	 */
	public String sn(boolean val) {
		return val ? S : N;
	}

	/**
	 * Se val è true torna Y altrimenti torna N
	 * 
	 * @param val
	 * @return String
	 */
	public String yn(boolean val) {
		return val ? "Y" : N;
	}

	/**
	 * Se val è true torna 1 altrimenti torna 0
	 * 
	 * @param val
	 * @return String
	 */
	public String unozero(boolean val) {
		return val ? "1" : "0";
	}

	/**
	 * Se val è null torna null. Se val è true torna S altrimenti torna N
	 * 
	 * 
	 * @param val
	 * @return String
	 */
	public String sn(Boolean val) {
		if (Null(val))
			return null;
		return sn(val.booleanValue());
	}

	/**
	 * Se val è null torna null. Se val è true torna Y altrimenti torna N
	 * 
	 * 
	 * @param val
	 * @return String
	 */
	public String yn(Boolean val) {
		if (Null(val))
			return null;
		return yn(val.booleanValue());
	}

	/**
	 * Se val è null torna null. Se val è true torna 1 altrimenti torna 0
	 * 
	 * 
	 * @param val
	 * @return String
	 */
	public String unozero(Boolean val) {
		if (Null(val))
			return null;
		return unozero(val.booleanValue());
	}

	/**
	 * Imposta i valori nel preparedStatement secondo l'ordine di inserimento
	 * degli argomenti vals e dei tipi sql passati. Esegue il controllo di
	 * validazione della coerenza della query con i valori passati
	 * 
	 * @param sql
	 * @param ps
	 * @param tipi
	 * @param vals
	 * @throws Exception
	 */
	public void ps(String sql, PreparedStatement ps, int[] tipi, Object... vals) throws Exception {
		checkQuery(sql, tipi, vals);
		if (Null(ps))
			throw new IllegalArgumentException("Il prepared statement è null");
		int i = 0;
		int k = -1;
		int tipo = 0;
		if (null != vals)
			for (Object val : vals) {
				i++;
				k++;
				tipo = tipi[k];
				if (Null(val)) {
					ps.setNull(i, tipo);
					continue;
				}
				switch (tipo) {
				case Types.VARCHAR:
				case Types.CHAR:
					ps.setString(i, (String) val);
					continue;
				case Types.BIGINT:
					ps.setLong(i, (Long) val);
					continue;
				case Types.INTEGER:
					ps.setInt(i, (Integer) val);
					continue;
				case Types.DOUBLE:
					ps.setDouble(i, (Double) val);
					continue;
				case Types.FLOAT:
					ps.setFloat(i, (Float) val);
					continue;
				case Types.DATE:
					ps.setDate(i, getSQLDate((Date) val));
					continue;
				case Types.TIMESTAMP:
					ps.setTimestamp(i, (Timestamp) val);
					continue;
				case Types.NUMERIC:
					ps.setBigDecimal(i, (BigDecimal) val);
					continue;
				case Types.BLOB:
					ps.setBlob(i, (new ByteArrayInputStream((byte[]) val)));
					continue;
				}

			}
	}

	private void checkQuery(String sql, int[] tipi, Object... valori) {
		if (Null(sql)) {
			throw new IllegalArgumentException("Query sql mancante");
		} else {
			// sql = sql.toLowerCase();
			// if (!sql.trim().startsWith("select")) {
			// throw new
			// IllegalArgumentException("Occorre definire una query di select da eseguire. La query attuale non mostra la clausola SELECT");
			// }
			PList<Integer> tipiSql = pl(Types.VARCHAR, Types.CHAR, Types.BIGINT, Types.INTEGER, Types.DOUBLE, Types.FLOAT, Types.DATE, Types.TIMESTAMP, Types.NUMERIC, Types.BLOB);
			int quantiInterr = countChar(sql, '?');
			if (null == tipi && null != valori)
				throw new IllegalArgumentException("L'array di tipi sql è null ma sono presenti valori");
			if (null != tipi && null == valori)
				throw new IllegalArgumentException("L'array di valori è null ma sono presenti dei tipi sql");
			if (null != tipi && null != valori) {
				if (tipi.length != valori.length)
					throw new IllegalArgumentException("Il numero di tipi sql passati è diverso dal numero dei valori vals");
				if (zero(quantiInterr)) {
					throw new IllegalArgumentException("La query non presenta placeholder '?' ma sono presenti dei tipi sql / valori");
				}
				if (quantiInterr != tipi.length || quantiInterr != valori.length) {
					throw new IllegalArgumentException("Il numero di tipi/valori sql passati è diverso dal numero dei placeholder '?' presenti nella query");
				}
			}
			if (null != tipi)
				for (int t : tipi) {
					if (isNot(tipiSql, t)) {
						throw new IllegalArgumentException("Il tipo " + t + " non è ammesso.");
					}
				}
		}
	}

	/**
	 * Ritorna true se la lista ha tutti i suoi elementi la cui proprietà prop è
	 * Null
	 * 
	 * @param <T>
	 * @param l1
	 * @return boolean
	 * @throws Exception
	 */
	public <T> boolean isAllNullListBeanValues(List<T> l1, String prop) throws Exception {
		if (Null(l1))
			return false;
		boolean alls = true;
		for (T t : safe(l1)) {
			if (notNull(get(t, prop))) {
				alls = false;
				break;
			}
		}
		return alls;
	}

	/**
	 * Ritorna true se la lista ha tutti i suoi elementi la cui proprietà prop è
	 * Not Null
	 * 
	 * @param <T>
	 * @param l1
	 * @return boolean
	 * @throws Exception
	 */
	public <T> boolean isAllNotNullListBeanValues(List<T> l1, String prop) throws Exception {
		if (Null(l1))
			return false;
		boolean alls = true;
		for (T t : safe(l1)) {
			if (Null(get(t, prop))) {
				alls = false;
				break;
			}
		}
		return alls;
	}

	/**
	 * Ritorna true se la proprieta prop dei bean della lista ha il valore Null
	 * per almeno uno degli elementi della lista
	 * 
	 * @param <T>
	 * @param l1
	 * @param prop
	 * @return boolean
	 * @throws Exception
	 */
	public <T> boolean isAnyNullListBeanValues(List<T> l1, String prop) throws Exception {
		if (Null(l1))
			return false;
		boolean any = false;
		for (T t : safe(l1)) {
			if (Null(get(t, prop))) {
				any = true;
				break;
			}
		}
		return any;
	}

	/**
	 * Ritorna true se la proprieta prop dei bean della lista ha il valore Not
	 * Null per almeno uno degli elementi della lista
	 * 
	 * @param <T>
	 * @param l1
	 * @param prop
	 * @return boolean
	 * @throws Exception
	 */
	public <T> boolean isAnyNotNullListBeanValues(List<T> l1, String prop) throws Exception {
		if (Null(l1))
			return false;
		boolean any = false;
		for (T t : safe(l1)) {
			if (notNull(get(t, prop))) {
				any = true;
				break;
			}
		}
		return any;
	}

	/**
	 * Ritorna true se la proprieta prop dei bean della lista non ha il valore
	 * Null per tutti gli elementi della lista
	 * 
	 * @param <T>
	 * @param l1
	 * @param prop
	 * @return boolean
	 * @throws Exception
	 */
	public <T> boolean isNoneNullListBeanValues(List<T> l1, String prop) throws Exception {
		if (Null(l1))
			return false;
		boolean none = true;
		for (T t : safe(l1)) {
			if (Null(get(t, prop))) {
				none = false;
				break;
			}
		}
		return none;
	}

	/**
	 * Ritorna true se la proprieta prop dei bean della lista non ha il valore
	 * Not Null per tutti gli elementi della lista
	 * 
	 * @param <T>
	 * @param l1
	 * @param prop
	 * @return boolean
	 * @throws Exception
	 */
	public <T> boolean isNoneNotNullListBeanValues(List<T> l1, String prop) throws Exception {
		if (Null(l1))
			return false;
		boolean none = true;
		for (T t : safe(l1)) {
			if (notNull(get(t, prop))) {
				none = false;
				break;
			}
		}
		return none;
	}

	/**
	 * Ritorna la data di ieri
	 * 
	 * @return PDate
	 */
	public PDate ieri() {
		return pd().ieri();
	}

	/**
	 * Ritorna la data di domani
	 * 
	 * @return PDate
	 */
	public PDate domani() {
		return pd().domani();
	}

	/**
	 * Ritorna la data di n giorni fa a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	public PDate giorniFa(int n) {
		return pd().giorniFa(n);
	}

	/**
	 * Ritorna la data di n settimane fa a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	public PDate settimaneFa(int n) {
		return pd().settimaneFa(n);
	}

	/**
	 * Ritorna la data di n settimane nel futuro a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	public PDate settimaneFuturo(int n) {
		return pd().settimaneFuturo(n);
	}

	/**
	 * Ritorna la data di n mesi fa a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	public PDate mesiFa(int n) {
		return pd().mesiFa(n);
	}

	/**
	 * Ritorna la data di n mesi nel futuro a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	public PDate mesiFuturo(int n) {
		return pd().mesiFuturo(n);
	}

	/**
	 * Ritorna la data di n anni fa a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	public PDate anniFa(int n) {
		return pd().anniFa(n);
	}

	/**
	 * Ritorna la data di n anni nel futuro a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	public PDate anniFuturo(int n) {
		return pd().anniFuturo(n);
	}

	/**
	 * Ritorna la data di n giorni nel futuro a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	public PDate giorniFuturo(int n) {
		return pd().giorniFuturo(n);
	}

	/**
	 * Scrive su file in modalità append
	 * 
	 * @param <T>
	 * @param path
	 * @param data
	 */
	public <T> void appendFile(String path, List<T> data) {
		File file = new File(path);
		createFolder(path);
		FileWriter fr = null;
		try {
			fr = new FileWriter(file, true);
			for (T s : safe(data)) {
				fr.write(getString(s, lf()));
			}
		} catch (IOException e) {
			ex(e);
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				ex(e);
			}
		}
	}

	/**
	 * Ritorna la stringa "-"
	 * 
	 * @return String
	 */
	public String dash() {
		return DASHTRIM;
	}

	/**
	 * Ritorna la stringa ","
	 * 
	 * @return String
	 */
	public String comma() {
		return COMMA;
	}

	/**
	 * Ritorna la stringa ":"
	 * 
	 * @return String
	 */
	public String colon() {
		return COLON;
	}

	/**
	 * Ritorna la stringa ";"
	 * 
	 * @return String
	 */
	public String semicolon() {
		return SEMICOLON;
	}

	/**
	 * Ritorna la stringa " "
	 * 
	 * @return String
	 */
	public String space() {
		return SPACE;
	}

	/**
	 * Ritorna la stringa "."
	 * 
	 * @return String
	 */
	public String dot() {
		return DOT;
	}

	/**
	 * Ritorna la stringa "|"
	 * 
	 * @return String
	 */
	public String pipe() {
		return PIPE;
	}

	/**
	 * Ritorna la stringa "/"
	 * 
	 * @return String
	 */
	public String slash() {
		return SLASH;
	}

	/**
	 * Ritorna la stringa "_"
	 * 
	 * @return String
	 */
	public String lodash() {
		return UNDERSCORE;
	}

	/**
	 * Ritorna la stringa "_"
	 * 
	 * @return String
	 */
	public String underscore() {
		return UNDERSCORE;
	}

	/**
	 * Ritorna la stringa formata da n spazi
	 * 
	 * @return String
	 */
	public String space(Integer n) {
		String s = "";
		for (int i = 1; i <= n; i++) {
			s += space();
		}
		return s;
	}

	/**
	 * Ritorna la stringa "["
	 * 
	 * @return String
	 */
	public String quadra() {
		return OPEN_QUADRA;
	}

	/**
	 * Ritorna la stringa "]"
	 * 
	 * @return String
	 */
	public String quadraClose() {
		return "]";
	}

	/**
	 * Ritorna la stringa "{"
	 * 
	 * @return String
	 */
	public String graffa() {
		return OPEN_GRAFFA;
	}

	/**
	 * Ritorna la stringa "}"
	 * 
	 * @return String
	 */
	public String graffaClose() {
		return "}";
	}

	/**
	 * Ritorna la stringa "("
	 * 
	 * @return String
	 */
	public String tonda() {
		return "(";
	}

	/**
	 * Ritorna la stringa ")"
	 * 
	 * @return String
	 */
	public String tondaClose() {
		return ")";
	}

	/**
	 * Genera un numero casuale compreso tra [min,max]
	 * 
	 * @param min
	 * @param max
	 * @return Integer
	 */
	public Integer generaNumeroCasuale(Integer min, Integer max) {
		return Double.valueOf(Math.floor(Math.random() * (max - min + 1)) + min).intValue();
	}

	/**
	 * Ritorna la stringa "-->"
	 * 
	 * @return String
	 */
	public String arrow() {
		return "-->";
	}

	/**
	 * Blocca l'esecuzione del current thread per i secondi specificati
	 * 
	 * @param secondi
	 * @throws InterruptedException
	 */
	public void attendiSecondi(Integer secondi) throws InterruptedException {
		Thread.sleep(secondi * 1000);
	}

	/**
	 * Blocca l'esecuzione del current thread per i minuti specificati
	 * 
	 * @param minuti
	 * @throws InterruptedException
	 */
	public void attendiMinuti(Integer minuti) throws InterruptedException {
		attendiSecondi(minuti * 60);
	}

	/**
	 * Svuota il contenuto dei file individuati dai path
	 * 
	 * @param path
	 */
	public void svuotaFile(String... path) {
		for (String p : safe(arrayToList(path))) {
			FileWriter writer = null;
			try {
				writer = new FileWriter(p);
			} catch (IOException e) {
				logError("Errore durante lo svuotamento del file ", p);
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					logError("Errore durante la chiusura dello stream per il file ", p);
				}
			}
		}
	}

	/**
	 * Svuota il contenuto dei file
	 * 
	 * @param files
	 */
	public void svuotaFile(File... files) {
		for (File f : safe(arrayToList(files))) {
			FileWriter writer = null;
			try {
				writer = new FileWriter(f);
			} catch (IOException e) {
				logError("Errore durante lo svuotamento del file ", f.getName());
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					logError("Errore durante la chiusura dello stream per il file ", f.getName());
				}
			}
		}
	}

	/**
	 * Ritorna la stringa "€"
	 * 
	 * @return String
	 */
	public String euro() {
		return "€";
	}

	/**
	 * Ritorna la stringa "$"
	 * 
	 * @return String
	 */
	public String dollar() {
		return "$";
	}

	/**
	 * Ritorna la stringa "£"
	 * 
	 * @return String
	 */
	public String pound() {
		return "£";
	}

	/**
	 * Ritorna la stringa "*"
	 * 
	 * @return String
	 */
	public String star() {
		return "*";
	}

	/**
	 * Ritorna la stringa "~"
	 * 
	 * @return String
	 */
	public String tilde() {
		return "~";
	}

}
