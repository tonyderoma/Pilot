package it.eng.pilot;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

/**
 * Classe di metodi alias di Pilot. Puï¿½ essere estesa in modo tale da avere
 * automaticamente un sottoinsieme di metodi di Pilot (metodi piï¿½ utilizzati
 * durante il coding) senza usare la variabile istanza p.nomeMetodo(...) ma
 * accedendo direttamente con nomeMetodo(..). Per la documentazione fare
 * riferimento ai corrispondenti metodi della classe Pilot.
 * 
 * @author Antonio Corinaldi
 * 
 */

public class PilotSupport {
	protected static int SQL_STRING = Pilot.SQL_STRING;
	protected static int SQL_CHAR = Pilot.SQL_CHAR;
	protected static int SQL_LONG = Pilot.SQL_LONG;
	protected static int SQL_INTEGER = Pilot.SQL_INTEGER;
	protected static int SQL_DOUBLE = Pilot.SQL_DOUBLE;
	protected static int SQL_FLOAT = Pilot.SQL_FLOAT;
	protected static int SQL_DATE = Pilot.SQL_DATE;
	protected static int SQL_TIMESTAMP = Pilot.SQL_TIMESTAMP;
	protected static int SQL_BIGDECIMAL = Pilot.SQL_BIGDECIMAL;
	protected static int SQL_BYTEARRAY = Pilot.SQL_BYTEARRAY;
	// short versions
	protected static int _S = Pilot._S;
	protected static int _C = Pilot._C;
	protected static int _L = Pilot._L;
	protected static int _I = Pilot._I;
	protected static int _D = Pilot._D;
	protected static int _F = Pilot._F;
	protected static int _DT = Pilot._DT;
	protected static int _TS = Pilot._TS;
	protected static int _BIG = Pilot._BIG;
	protected static int _BARR = Pilot._BARR;

	protected static final String ATTIVO = Pilot.ATTIVO;
	protected static final String DISATTIVO = Pilot.DISATTIVO;
	protected static final String S = Pilot.S;
	protected static final String N = Pilot.N;
	protected static final BigDecimal ZERO = Pilot.ZERO;
	protected static final BigDecimal ONE = Pilot.ONE;
	protected static final String SPACE = Pilot.SPACE;
	protected static final String THREE_SPACE = Pilot.THREE_SPACE;

	protected Pilot p = new Pilot();

	public PilotSupport() {
		aggiungiTimeCount();
	}

	public PilotSupport(Logger log) {
		setLog(log);
	}

	private void aggiungiTimeCount() {
		LapTime lt = getClass().getAnnotation(LapTime.class);
		if (notNull(lt)) {
			String[] timeCounters = lt.timeCounters();
			for (String tc : timeCounters) {
				aggiungiTimeCount(tc);
			}
		}
	}

	protected void setLog(Logger log) {
		p.setLog(log);
	}

	protected Logger getLog() {
		return p.getLog();
	}

	protected String lf() {
		return p.lf();
	}

	protected String lf2() {
		return str(lf(), lf());
	}

	protected String tab() {
		return p.tab();
	}

	protected String tab2() {
		return str(tab(), tab());
	}

	protected String tabn(Integer n) {
		return p.tabn(n);
	}

	protected String lfn(Integer n) {
		return p.lfn(n);
	}

	protected String str(Object... k) {
		return p.getString(k);
	}

	protected void log(Object... k) {
		p.log(k);
	}

	protected void logError(Object... k) {
		p.logError(k);
	}

	protected String strSpaced(Object... k) {
		return p.strSpaced(k);
	}

	protected String strSep(String sep, Object... k) {
		return p.strSep(sep, k);
	}

	protected boolean Null(Object... values) {
		return p.Null(values);
	}

	protected boolean notNull(Object... values) {
		return p.notNull(values);
	}

	protected boolean is(Object campo, Object... value) {
		return p.is(campo, value);
	}

	protected boolean isNot(Object campo, Object... value) {
		return p.isNot(campo, value);
	}

	protected <K> PList<K> toPList(List<K> l) {
		return p.toPList(l);
	}

	protected <K> List<K> safe(List<K> elenco) {
		return p.safe(elenco);
	}

	protected boolean maggioreZero(BigDecimal d) {
		return p.maggioreZero(d);
	}

	protected boolean minoreZero(BigDecimal d) {
		return p.minoreZero(d);
	}

	protected boolean maggioreUgualeZero(BigDecimal d) {
		return p.maggioreUgualeZero(d);
	}

	protected boolean minoreUgualeZero(BigDecimal d) {
		return p.minoreUgualeZero(d);
	}

	protected BigDecimal moltiplica(BigDecimal b1, BigDecimal b2) {
		return p.moltiplica(b1, b2);
	}

	protected BigDecimal moneyToBigDecimal(String importo) {
		return p.moneyToBigDecimal(importo);
	}

	protected java.sql.Date nowSql() {
		return p.nowSql();
	}

	protected boolean startsWith(String s, String... vals) {
		return p.startsWith(s, vals);
	}

	protected PList<BigDecimal> toListBigDecimal(List<String> l) {
		return p.toListBigDecimal(l);
	}

	protected PList<Integer> toListInteger(String s, String separator) {
		return p.toListInteger(s, separator);
	}

	protected <K, V> PMap<K, V> getPMap() {
		return p.getPMap();
	}

	protected <K, V> PMap<K, PList<V>> getPMapList() {
		return p.getPMapList();
	}

	protected String concatenaListaStringhe(List<String> l) {
		return p.concatenaListaStringhe(l);
	}

	protected PList<Long> toLongList(List<String> l) {
		return p.toLongList(l);
	}

	protected PList<Integer> toListInteger(List<String> l) {
		return p.toListInteger(l);
	}

	protected Date toDate(String s) {
		return p.toDate(s);
	}

	protected Date toDate(String s, String format) {
		return p.toDate(s, format);
	}

	protected Date getInfiniteDate() throws ParseException {
		return p.getInfiniteDate();
	}

	protected Date getInfiniteStartDate() throws ParseException {
		return p.getInfiniteStartDate();
	}

	protected String money(BigDecimal bd) {
		return p.money(bd);
	}

	protected boolean zero(BigDecimal d) {
		return p.zero(d);
	}

	protected boolean zero(Long d) {
		return p.zero(d);
	}

	protected java.sql.Date getSQLDate(Date d) {
		return p.getSQLDate(d);
	}

	protected BigDecimal zeroIfNull(BigDecimal val) {
		return p.zeroIfNull(val);
	}

	protected Long zeroIfNull(Long val) {
		return p.zeroIfNull(val);
	}

	protected Integer zeroIfNull(Integer val) {
		return p.zeroIfNull(val);
	}

	protected String concatenaListaStringhe(List<String> l, String car) {
		return p.concatenaListaStringhe(l, car);
	}

	protected BigDecimal getBigDecimal(Long val) {
		return p.getBigDecimal(val);
	}

	protected BigDecimal getBigDecimal(Long val, Integer precision) {
		return p.getBigDecimal(val, precision);
	}

	protected String getTitle(String title, Integer lunghezza, String car) {
		return p.getTitle(title, lunghezza, car);
	}

	protected Integer getYear(Date d) {
		return p.getYear(d);
	}

	protected <K> PList<K> safe(PList<K> elenco) {
		return p.safe(elenco);
	}

	protected boolean isDateAfter(Date d, Date limite) {
		return p.isDateAfter(d, limite);
	}

	protected boolean isDateBefore(Date d, Date limite) {
		return p.isDateBefore(d, limite);
	}

	protected boolean isDateBetween(Date d, Date start, Date end) {
		return p.isDateBetween(d, start, end);
	}

	public boolean isDateBetweenEx(Date d, Date start, Date end) {
		return p.isDateBetweenEx(d, start, end);
	}

	protected PList<Long> toListLong(String s, String separator) {
		return p.toListLong(s, separator);
	}

	protected String substring(String s, String start, boolean compresoStart, boolean lastStart, String end, boolean compresoEnd, boolean lastEnd) {
		return p.substring(s, start, compresoStart, lastStart, end, compresoEnd, lastEnd);
	}

	protected <T> T valIfNull(T... val) {
		return p.valIfNull(val);
	}

	protected boolean zero(Integer d) {
		return p.zero(d);
	}

	protected boolean tutte(Boolean... b) {
		return p.tutte(b);
	}

	protected boolean almenoUna(Boolean... b) {
		return p.almenoUna(b);
	}

	protected boolean nessuna(Boolean... b) {
		return p.nessuna(b);
	}

	protected boolean isLike(Object campo, Object... value) {
		return p.isLike(campo, value);
	}

	protected <K> PList<K> getPList() {
		return p.getPList();
	}

	protected <K> PList<K> getPList(Integer limite) {
		return p.getPList(limite);
	}

	protected Integer getInteger(String val) {
		return p.getInteger(val);
	}

	protected BigDecimal getBigDecimal(String val) {
		return p.getBigDecimal(val);
	}

	protected BigDecimal getBigDecimal(String val, Integer precision) {
		return p.getBigDecimal(val, precision);
	}

	protected PList<String> toListString(String s, String separator) {
		return p.toListString(s, separator);
	}

	protected Object get(Object bean, String prop) throws Exception {
		return p.get(bean, prop);
	}

	protected PList<String> getPListString() {
		return p.getPListString();
	}

	protected String getYear(String d) {
		return p.getYear(d);
	}

	protected String nowString() {
		return p.nowString();
	}

	protected PDate now() {
		return p.now();
	}

	protected Date nowLessWeeks(Integer n) {
		return p.nowLessWeeks(n);
	}

	protected Date nowLessDays(Integer n) {
		return p.nowLessDays(n);
	}

	protected Date nowLessMonths(Integer n) {
		return p.nowLessMonths(n);
	}

	protected Date nowLessYears(Integer n) {
		return p.nowLessYears(n);
	}

	protected Date nowAddWeeks(Integer n) {
		return p.nowAddWeeks(n);
	}

	protected Date nowAddDays(Integer n) {
		return p.nowAddDays(n);
	}

	protected Date nowAddMonths(Integer n) {
		return p.nowAddMonths(n);
	}

	protected Date nowAddYears(Integer n) {
		return p.nowAddYears(n);
	}

	protected <T> PList<T> arrayToList(T[] a) {
		return p.arrayToList(a);
	}

	protected <K> Set<K> safe(Set<K> elenco) {
		return p.safe(elenco);
	}

	protected boolean notNullOR(Object... values) {
		return p.notNullOR(values);
	}

	protected boolean NullOR(Object... values) {
		return p.NullOR(values);
	}

	protected boolean si(String value) {
		return p.si(value);
	}

	protected boolean no(String value) {
		return p.no(value);
	}

	protected boolean diverso(BigDecimal d, BigDecimal d1) {
		return p.diverso(d, d1);
	}

	protected boolean uguale(BigDecimal d, BigDecimal d1) {
		return p.uguale(d, d1);
	}

	protected Timestamp nowTS() {
		return p.nowTS();
	}

	protected Long getLong(String val) {
		return p.getLong(val);
	}

	protected Double getDouble(String val) {
		return p.getDouble(val);
	}

	protected Float getFloat(String val) {
		return p.getFloat(val);
	}

	protected Short getShort(String val) {
		return p.getShort(val);
	}

	protected int countChar(String s, char ch) {
		return p.countChar(s, ch);
	}

	protected <K> K getFirstElement(List<K> l) {
		return p.getFirstElement(l);
	}

	protected <K> K getLastElement(List<K> l) {
		return p.getLastElement(l);
	}

	protected boolean one(BigDecimal d) {
		return p.one(d);
	}

	protected boolean one(Long d) {
		return p.one(d);
	}

	protected boolean one(Integer d) {
		return p.one(d);
	}

	protected String replace(String s, String old, String nuova) {
		return p.replace(s, old, nuova);
	}

	protected String replaceLast(String string, String toReplace, String replacement) {
		return p.replaceLast(string, toReplace, replacement);
	}

	protected String cut(String s, Integer n) {
		return p.cut(s, n);
	}

	protected String cutLast(String s) {
		return p.cutLast(s);
	}

	protected String cutLast(StringBuffer s) {
		return p.cutLast(s);
	}

	protected String cutLast(StringBuilder s) {
		return p.cutLast(s);
	}

	protected boolean maggioreZero(Long d) {
		return p.maggioreZero(d);
	}

	protected <K, T> void aggiungi(Map<K, T> mappa, K elem, T o) {
		p.aggiungi(mappa, elem, o);
	}

	protected <K, T> void rimuovi(Map<K, T> mappa, K elem) {
		p.rimuovi(mappa, elem);
	}

	protected <K, T> void rimuoviMappaLista(Map<K, List<T>> mappa, K elem, T o) throws Exception {
		p.rimuoviMappaLista(mappa, elem, o);
	}

	protected BigDecimal aggiungi(BigDecimal... vals) {
		return p.aggiungi(vals);
	}

	protected BigDecimal sottrai(BigDecimal b1, BigDecimal b2) {
		return p.sottrai(b1, b2);
	}

	protected String capFirstLetter(String s) {
		return p.capFirstLetter(s);
	}

	protected String decapFirstLetter(String s) {
		return p.decapFirstLetter(s);
	}

	protected String dateToString(Date d) {
		return p.dateToString(d);
	}

	protected String dateToStringhhmmss(Date d) {
		return p.dateToStringhhmmss(d);
	}

	protected String dateToString(Date d, String format) {
		return p.dateToString(d, format);
	}

	protected String elapsedTime(Date startDate) {
		return p.elapsedTime(startDate);
	}

	protected String elapsedTime(Date startDate, Date endDate) {
		return p.elapsedTime(startDate, endDate);
	}

	protected String toCamelCase(String s, String separatore) {
		return p.toCamelCase(s, separatore);
	}

	protected BigDecimal getBigDecimal(BigDecimal val) {
		return p.getBigDecimal(val);
	}

	protected BigDecimal getBigDecimal(BigDecimal val, Integer precision) {
		return p.getBigDecimal(val, precision);
	}

	protected String emptyIfNull(String s) {
		return p.emptyIfNull(s);
	}

	protected String apiceString(String s) {
		return p.apiceString(s);
	}

	protected Date addDays(Date data, Integer numeroGiorni) {
		return p.addDays(data, numeroGiorni);
	}

	protected Date addHours(Date data, Integer numeroOre) {
		return p.addHours(data, numeroOre);
	}

	protected Date addMinutes(Date data, Integer numeroMinuti) {
		return p.addMinutes(data, numeroMinuti);
	}

	protected Date addSeconds(Date data, Integer numeroSecondi) {
		return p.addSeconds(data, numeroSecondi);
	}

	protected Date addMilliseconds(Date data, Integer numeroMillisecondi) {
		return p.addMilliseconds(data, numeroMillisecondi);
	}

	protected Date addTimeToDate(Date start, Time t) {
		return p.addTimeToDate(start, t);
	}

	protected Date addMonths(Date data, Integer numeroMesi) {
		return p.addMonths(data, numeroMesi);
	}

	protected Date addYears(Date data, Integer numeroAnni) {
		return p.addYears(data, numeroAnni);
	}

	protected BigDecimal getBigDecimal(Integer val) {
		return p.getBigDecimal(val);
	}

	protected BigDecimal getBigDecimal(Integer val, Integer precision) {
		return p.getBigDecimal(val, precision);
	}

	protected BigDecimal getBigDecimal(Double val) {
		return p.getBigDecimal(val);
	}

	protected BigDecimal getBigDecimal(Double val, Integer precision) {
		return p.getBigDecimal(val, precision);
	}

	protected BigDecimal dividi(BigDecimal b1, BigDecimal b2) {
		return p.dividi(b1, b2);
	}

	protected Integer howManyChars(String s, char t) {
		return p.howManyChars(s, t);
	}

	protected <T> T[] toArray(List<T> list, Class<T> clazz) {
		return p.toArray(list, clazz);
	}

	protected String despaceString(String s) {
		return p.despaceString(s);
	}

	protected <K> PList<K> getPList(K... items) {
		return p.getPList(items);
	}

	protected <K extends Comparable<K>> K max(Collection<K> l) {
		return p.max(l);
	}

	protected <K extends Comparable<K>> K min(Collection<K> l) {
		return p.min(l);
	}

	protected <T> T maxBean(List<T> l1, String... prop) throws Exception {
		return p.maxBean(l1, prop);
	}

	protected <T> T minBean(List<T> l1, String... prop) throws Exception {
		return p.minBean(l1, prop);
	}

	protected <K extends Comparable<K>> boolean insideInterval(K val, K start, K end) {
		return p.insideInterval(val, start, end);
	}

	protected <K extends Comparable<K>> boolean insideInterval(K val1, K val2, K start, K end) {
		return p.insideInterval(val1, val2, start, end);
	}

	protected <K> PList<K> mockList(Class<K> c, Integer n) throws Exception {
		return p.mockList(c, n);
	}

	protected <K> K mock(Class<K> c) throws Exception {
		return p.mock(c);
	}

	protected <K> K mask(K k, String... fields) throws Exception {
		return p.mask(k, fields);
	}

	protected <K> PList<K> maskList(PList<K> lista, String... campi) throws Exception {
		return p.maskList(lista, campi);
	}

	protected <K, T> void aggiungiMappaLista(Map<K, List<T>> mappa, K elem, T o) {
		p.aggiungiMappaLista(mappa, elem, o);
	}

	protected String getUniqueName() {
		return p.getUniqueName();
	}

	protected <K> List<K> intersection(List<K> l1, List<K>... liste) {
		return p.intersection(l1, liste);
	}

	protected <K> List<K> inverti(List<K> l) {
		return p.inverti(l);
	}

	protected <T> PList<T> setToList(Set<T> a) {
		return p.setToList(a);
	}

	protected Integer sommaInt(String... vals) {
		return p.sommaInt(vals);
	}

	protected Long sommaLong(String... vals) {
		return p.sommaLong(vals);
	}

	protected <K> K[] modificaLunghezzaArray(K[] arr, Integer i) {
		return p.modificaLunghezzaArray(arr, i);
	}

	protected Long sommaLong(Long... vals) {
		return p.sommaLong(vals);
	}

	protected Integer sommaInt(Integer... vals) {
		return p.sommaInt(vals);
	}

	protected <K extends Number> PList<String> toListString(List<K> l) {
		return p.toListString(l);
	}

	protected void printList(String title, Integer length, List<String> container) {
		p.printList(title, length, container);
	}

	protected void printList(String title, Integer length, String prefixBeforeItem, List<String> container) {
		p.printList(title, length, prefixBeforeItem, container);
	}

	/**
	 * Alias di getPList()
	 * 
	 * @param <K>
	 * @return PList<K>
	 */
	protected <K> PList<K> pl() {
		return getPList();
	}

	/**
	 * Alias di getPList(Integer)
	 * 
	 * @param <K>
	 * @param limite
	 * @return PList<K>
	 */
	protected <K> PList<K> plLimit(Integer limite) {
		return getPList(limite);
	}

	/**
	 * Alias di getPMap()
	 * 
	 * @param <K>
	 * @param <V>
	 * @return PMap<K, V>
	 */
	protected <K, V> PMap<K, V> pmap() {
		return getPMap();
	}

	/**
	 * Alias di getPMapList()
	 * 
	 * @param <K>
	 * @param <V>
	 * @return PMap<K, PList<V>>
	 */
	protected <K, V> PMap<K, PList<V>> pmapl() {
		return getPMapList();
	}

	/**
	 * Alias di getPListString()
	 * 
	 * @return PList<String>
	 */
	protected PList<String> plstr() {
		return getPListString();
	}

	protected <K> PList<K> getPList(Collection<K> list) {
		return p.getPList(list);
	}

	/**
	 * Alias di getPList(Collection<K> list)
	 * 
	 * @param <K>
	 * @param list
	 * @return Collection<K>
	 */
	protected <K> PList<K> pl(Collection<K> list) {
		return p.pl(list);
	}

	/**
	 * Alias di getPList(K...items)
	 * 
	 * @param <K>
	 * @param items
	 * @return PList<K>
	 */
	protected <K> PList<K> pl(K... items) {
		return getPList(items);
	}

	protected <K> K[] estendiLunghezzaArrayDi(K[] arr, Integer i) {
		return p.estendiLunghezzaArrayDi(arr, i);
	}

	protected <K> K[] safe(K[] arr, Class<K> c) throws Exception {
		return p.safe(arr, c);
	}

	protected PList<String> readFile(String path) {
		return p.readFile(path);
	}

	protected <T> PList<String> readFileAsResource(Class<T> c, String nomeFile) throws Exception {
		return p.readFileAsResource(c, nomeFile);
	}

	protected <T> byte[] readFileAsResourceToByteArray(Class<T> c, String nomeFile) throws Exception {
		return p.readFileAsResourceToByteArray(c, nomeFile);
	}

	protected byte[] readFileIntoByteArray(String path) {
		return p.readFileIntoByteArray(path);
	}

	protected String capFirstLetterAfterSpace(String s) {
		return p.capFirstLetterAfterSpace(s);
	}

	protected String decapFirstLetterAfterSpace(String s) {
		return p.decapFirstLetterAfterSpace(s);
	}

	protected Set<File> getFiles(String path, String like, String notLike, String estensione, String data, boolean ricorsivo) throws IOException {
		return p.getFiles(path, like, notLike, estensione, data, ricorsivo);
	}

	protected <K> String concatenaLista(PList<K> l, String car, boolean apici) {
		return p.concatenaLista(l, car, apici);
	}

	protected void serializeToFile(Object o, String file) {
		p.serializeToFile(o, file);
	}

	protected Object deserializeFromFile(String file) {
		return p.deserializeFromFile(file);
	}

	protected void removeFile(String pathFile) {
		p.removeFile(pathFile);
	}

	protected boolean fileExists(String pathFile) {
		return p.fileExists(pathFile);
	}

	protected Integer getMonth(Date d) {
		return p.getMonth(d);
	}

	protected Integer getDay(Date d) {
		return p.getDay(d);
	}

	protected int monthsBetweenDates(Date startDate, Date endDate) {
		return p.monthsBetweenDates(startDate, endDate);
	}

	protected int yearsBetweenDates(Date first, Date last) {
		return p.yearsBetweenDates(first, last);
	}

	protected PList<Integer> iterateOverDates(Date d1, Date d2) {
		return p.iterateOverDates(d1, d2);
	}

	protected boolean isLastDayOfMonth(Date d) {
		return p.isLastDayOfMonth(d);
	}

	protected <K> List<K> toArrayList(PList<K> lista) {
		return p.toArrayList(lista);
	}

	protected <K extends Comparable<K>> boolean maggioreDi(K d, K d1) {
		return p.maggioreDi(d, d1);
	}

	protected <K extends Comparable<K>> boolean minoreDi(K d, K d1) {
		return p.minoreDi(d, d1);
	}

	protected <K extends Comparable<K>> boolean maggioreUgualeDi(K d, K d1) {
		return p.maggioreUgualeDi(d, d1);
	}

	protected <K extends Comparable<K>> boolean minoreUgualeDi(K d, K d1) {
		return p.minoreDi(d, d1);
	}

	protected String cutToFirstOccurence(String s, String elemento) {
		return p.cutToFirstOccurence(s, elemento);
	}

	protected String cutToLastOccurence(String s, String elemento) {
		return p.cutToLastOccurence(s, elemento);
	}

	protected BigDecimal percentuale(BigDecimal b1, BigDecimal b2) {
		return p.percentuale(b1, b2);
	}

	protected BigDecimal percentualeToValore(BigDecimal numero, BigDecimal percentuale) {
		return p.percentualeToValore(numero, percentuale);
	}

	protected <K extends Comparable<K>> K limiteInferiore(K val, K limite) {
		return p.limiteInferiore(val, limite);
	}

	protected <K extends Comparable<K>> K limiteSuperiore(K val, K limite) {
		return p.limiteSuperiore(val, limite);
	}

	protected <K extends Comparable<K>> K between(K val, K start, K end) {
		return p.between(val, start, end);
	}

	protected <K> PList<K> mockList(Class<K> c) throws Exception {
		return p.mockList(c);
	}

	protected <K extends Serializable> K copyObj(K o) {
		return p.copyObj(o);
	}

	protected <K extends Serializable> K clone(K o) {
		return copyObj(o);
	}

	protected BigDecimal bd(BigDecimal val) {
		return p.bd(val);
	}

	protected BigDecimal bd(Double val) {
		return p.bd(val);
	}

	protected BigDecimal bd(Integer val) {
		return p.bd(val);
	}

	protected BigDecimal bd(Long val) {
		return p.bd(val);
	}

	protected BigDecimal bd(String val) {
		return p.bd(val);
	}

	protected BigDecimal bd(BigDecimal val, Integer precision) {
		return p.bd(val, precision);
	}

	protected BigDecimal bd(Double val, Integer precision) {
		return p.bd(val, precision);
	}

	protected BigDecimal bd(Integer val, Integer precision) {
		return p.bd(val, precision);
	}

	protected BigDecimal bd(Long val, Integer precision) {
		return p.bd(val, precision);
	}

	protected BigDecimal bd(String val, Integer precision) {
		return p.bd(val, precision);
	}

	protected boolean endsWith(String s, String... vals) {
		return p.endsWith(s, vals);
	}

	protected <K> PList<K> toList(String s, String separator, Class<K> c) {
		return p.toList(s, separator, c);
	}

	protected void setPropertyFile(String propertyFile) {
		p.setPropertyFile(propertyFile);
	}

	protected String getKey(String key) {
		return p.getKey(key);
	}

	protected String getKey(String key, String defaultValue) {
		return p.getKey(key, defaultValue);
	}

	protected boolean toBool(String s) {
		return p.toBool(s);
	}

	protected Boolean getKeyBool(String key) {
		return p.getKeyBool(key);
	}

	protected Boolean getKeyBool(String key, boolean defaultValue) {
		return p.getKeyBool(key, defaultValue);
	}

	protected PList<String> getKeyList(String key) {
		return p.getKeyList(key);
	}

	protected PList<String> getKeyList(String key, String separator) {
		return p.getKeyList(key, separator);
	}

	protected PList<Integer> getKeyListInt(String key) {
		return p.getKeyListInt(key);
	}

	protected PList<Integer> getKeyListInt(String key, String separator) {
		return p.getKeyListInt(key, separator);
	}

	protected PList<Long> getKeyListLong(String key) {
		return p.getKeyListLong(key);
	}

	protected PList<Long> getKeyListLong(String key, String separator) {
		return p.getKeyListLong(key, separator);
	}

	protected PList<Double> getKeyListDouble(String key) {
		return p.getKeyListDouble(key);
	}

	protected PList<Double> getKeyListDouble(String key, String separator) {
		return p.getKeyListDouble(key, separator);
	}

	protected Integer getKeyInt(String key) {
		return p.getKeyInt(key);
	}

	protected Long getKeyLong(String key) {
		return p.getKeyLong(key);
	}

	protected Integer getKeyInt(String key, Integer defaultValue) {
		return p.getKeyInt(key, defaultValue);
	}

	protected Long getKeyLong(String key, Long defaultValue) {
		return p.getKeyLong(key, defaultValue);
	}

	protected void writeFile(String path, String data) {
		p.writeFile(path, data);
	}

	protected <T extends BaseEntity> void writeFile(String path, T data) {
		p.writeFile(path, data);
	}

	protected <T extends BaseEntity> void writeFile(String path, PList<T> data) {
		p.writeFile(path, data);
	}

	protected <T> void writeFile(String path, List<T> data) {
		p.writeFile(path, data);
	}

	protected String getUniqueNamehhmmss() {
		return p.getUniqueNamehhmmss();
	}

	protected <K> String toStr(K o) {
		return p.toStr(o);
	}

	protected <K extends BaseEntity> String toStrEntity(K o) {
		return p.toStrEntity(o);
	}

	protected String zeroStart(int num) {
		return p.zeroStart(num);
	}

	protected Time elapsed(Date startDate) {
		return p.elapsed(startDate);
	}

	protected Time elapsed(Date startDate, Date endDate) {
		return p.elapsed(startDate, endDate);
	}

	protected String elapsedTime(Long milliseconds) {
		return p.elapsedTime(milliseconds);
	}

	protected Time elapsedTimeTime(Long milliseconds) {
		return p.elapsedTimeTime(milliseconds);
	}

	protected void aggiungiTimeCount(String name) {
		p.aggiungiTimeCount(name);
	}

	protected void rimuoviTimeCount(String name) {
		p.rimuoviTimeCount(name);
	}

	protected void resetTimeCount() {
		p.resetTimeCount();
	}

	protected void addTimeToTimeCount(String name, Time t) {
		p.addTimeToTimeCount(name, t);
	}

	protected String getTimeCountInfo(String name) {
		return p.getTimeCountInfo(name);
	}

	protected String getLastAddedTimeInTimeCount(String name) {
		return p.getLastAddedTimeInTimeCount(name);
	}

	protected void addTimeToTimeCount(String name, Date startDate) {
		p.addTimeToTimeCount(name, startDate);
	}

	protected String getTimeCountTotalInfo() {
		return p.getTimeCountTotalInfo();
	}

	protected String getLap(String name) {
		return p.getLap(name);
	}

	protected void lap(String name, Time t) {
		p.lap(name, t);
	}

	protected void lap(String name, Date startDate) {
		p.lap(name, startDate);
	}

	protected String getLapTotalInfo() {
		return p.getLapTotalInfo();
	}

	protected void lapAndLog(String prefix, String timeCounter, Date start) {
		p.lapAndLog(prefix, timeCounter, start);
	}

	protected String repeat(Integer l, String car) {
		return p.repeat(l, car);
	}

	protected String getInfoFramed(String title, Integer length, String car, Integer tabIndent, PList<String> container) {
		return p.getInfoFramed(title, length, car, tabIndent, container);
	}

	protected String getInfoFramed(String title, PList<String> container) {
		return p.getInfoFramed(title, container);
	}

	protected String frame(String title, PList<String> container) {
		return getInfoFramed(title, container);
	}

	protected String frame(String title, Integer length, String car, Integer tabIndent, PList<String> container) {
		return getInfoFramed(title, length, car, tabIndent, container);
	}

	protected PList<String> plstr(String... items) {
		return p.plstr(items);
	}

	protected <K extends Execution> Future stacca(final K o) {
		return p.stacca(o);
	}

	protected String convertToStringRepresentation(long value) {
		return p.convertToStringRepresentation(value);
	}

	protected void hw() {
		p.hw();
	}

	protected String elimina(String s, String... vals) {
		return p.elimina(s, vals);
	}

	protected <K extends Comparable<K>> K max(K... vals) {
		return p.max(vals);
	}

	protected <K extends Comparable<K>> K min(K... vals) {
		return p.min(vals);
	}

	protected Date primoGiornoDelMeseDate(Date d) {
		return p.primoGiornoDelMeseDate(d);
	}

	protected String primoGiornoDelMese(Date d) {
		return p.primoGiornoDelMese(d);
	}

	protected PList<KeyValue<String, String>> getQueryString(String s) {
		return p.getQueryString(s);
	}

	protected PList<String> split(String input, String delimiter) {
		return p.split(input, delimiter);
	}

	protected String getLast(String s) {
		return p.getLast(s);
	}

	protected String getFirst(String s) {
		return p.getFirst(s);
	}

	protected String pluraleSingolare(int num, String s) {
		return p.pluraleSingolare(num, s);
	}

	protected String strSepDash(Object... values) {
		return p.strSepDash(values);
	}

	protected String strSepComma(Object... values) {
		return p.strSepComma(values);
	}

	protected String strSepPipe(Object... values) {
		return p.strSepPipe(values);
	}

	protected String strSepSpace(Object... values) {
		return p.strSepSpace(values);
	}

	protected String concatenaDashListaStringhe(List<String> l) {
		return p.concatenaDashListaStringhe(l);
	}

	protected String concatenaCommaListaStringhe(List<String> l) {
		return p.concatenaCommaListaStringhe(l);
	}

	protected String concatenaPipeListaStringhe(List<String> l) {
		return p.concatenaPipeListaStringhe(l);
	}

	protected String concatenaSpaceListaStringhe(List<String> l) {
		return p.concatenaSpaceListaStringhe(l);
	}

	protected <K> PList<K> toListDashSep(String s, Class<K> c) {
		return p.toListDashSep(s, c);
	}

	protected <K> PList<K> toListCommaSep(String s, Class<K> c) {
		return p.toListCommaSep(s, c);
	}

	protected <K> PList<K> toListPipeSep(String s, Class<K> c) {
		return p.toListPipeSep(s, c);
	}

	protected <K> Set<K> toSetDashSep(String s, Class<K> c) {
		return p.toSetDashSep(s, c);
	}

	protected <K> Set<K> toSetCommaSep(String s, Class<K> c) {
		return p.toSetCommaSep(s, c);
	}

	protected <K> Set<K> toSetPipeSep(String s, Class<K> c) {
		return p.toSetPipeSep(s, c);
	}

	/**
	 * Da un oggetto Date ritorna un oggetto PDate
	 * 
	 * @param d
	 * @return PDate
	 */
	protected PDate pd(Date d) {
		return p.pd(d);
	}

	/**
	 * Ritorna un oggetto PDate con la data attuale
	 * 
	 * @return PDate
	 */
	protected PDate pd() {
		return p.pd(now());
	}

	/**
	 * Dalla data in formato stringa ritorna un oggetto PDate. Se il valore
	 * passato non è una data valida secondo il formato italiano, ritorna la
	 * data odierna
	 * 
	 * @param s
	 * @return PDate
	 */
	protected PDate pd(String s) {
		return p.pd().from(s);
	}

	protected PList<String> splitDash(String input) {
		return p.splitDash(input);
	}

	protected PList<String> splitComma(String input) {
		return p.splitComma(input);
	}

	protected PList<String> splitPipe(String input) {
		return p.splitPipe(input);
	}

	protected PList<String> splitSpace(String input) {
		return p.splitSpace(input);
	}

	protected String getIdUnivoco() {
		return p.getIdUnivoco();
	}

	protected void setState(String key, Object val) {
		p.setState(key, val);
	}

	protected Object getState(String key) {
		return p.getState(key);
	}

	protected void setStateEmpty() {
		p.setStateEmpty();
	}

	protected void clearState(String key) {
		p.clearState(key);
	}

	protected void clearState() {
		setStateEmpty();
	}

	protected PMap<String, Object> getState() {
		return p.getState();
	}

	protected String sn(boolean val) {
		return p.sn(val);
	}

	protected String yn(boolean val) {
		return p.yn(val);
	}

	protected String unozero(boolean val) {
		return p.unozero(val);
	}

	protected String sn(Boolean val) {
		return p.sn(val);
	}

	protected String yn(Boolean val) {
		return p.yn(val);
	}

	protected String unozero(Boolean val) {
		return p.unozero(val);
	}

	protected String getStateStr(String key) {
		return p.getStateStr(key);
	}

	protected Long getStateLong(String key) {
		return p.getStateLong(key);
	}

	protected Date getStateDate(String key) {
		return p.getStateDate(key);
	}

	protected void ps(String sql, PreparedStatement ps, int[] tipi, Object... vals) throws Exception {
		p.ps(sql, ps, tipi, vals);
	}

	protected boolean checkParenthesis(String s) {
		return p.checkParenthesis(s);
	}

	/**
	 * Ritorna la data di ieri
	 * 
	 * @return PDate
	 */
	protected PDate ieri() {
		return p.ieri();
	}

	/**
	 * Ritorna la data di domani
	 * 
	 * @return PDate
	 */
	protected PDate domani() {
		return p.domani();
	}

	/**
	 * Ritorna la data di n giorni fa a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	protected PDate giorniFa(int n) {
		return p.giorniFa(n);
	}

	/**
	 * Ritorna la data di n settimane fa a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	protected PDate settimaneFa(int n) {
		return p.settimaneFa(n);
	}

	/**
	 * Ritorna la data di n settimane nel futuro a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	protected PDate settimaneFuturo(int n) {
		return p.settimaneFuturo(n);
	}

	/**
	 * Ritorna la data di n mesi fa a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	protected PDate mesiFa(int n) {
		return p.mesiFa(n);
	}

	/**
	 * Ritorna la data di n mesi nel futuro a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	protected PDate mesiFuturo(int n) {
		return p.mesiFuturo(n);
	}

	/**
	 * Ritorna la data di n anni fa a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	protected PDate anniFa(int n) {
		return p.anniFa(n);
	}

	/**
	 * Ritorna la data di n anni nel futuro a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	protected PDate anniFuturo(int n) {
		return p.anniFuturo(n);
	}

	/**
	 * Ritorna la data di n giorni nel futuro a partire dalla data odierna
	 * 
	 * @param n
	 * @return PDate
	 */
	protected PDate giorniFuturo(int n) {
		return p.giorniFuturo(n);
	}

	/**
	 * Scrive su file in modalità append
	 * 
	 * @param <T>
	 * @param path
	 * @param data
	 */
	protected <T> void appendFile(String path, List<T> data) {
		p.appendFile(path, data);
	}

	/**
	 * Ritorna la stringa "-"
	 * 
	 * @return String
	 */
	protected String dash() {
		return p.dash();
	}

	/**
	 * Ritorna la stringa ","
	 * 
	 * @return String
	 */
	protected String comma() {
		return p.comma();
	}

	/**
	 * Ritorna la stringa ":"
	 * 
	 * @return String
	 */
	protected String colon() {
		return p.colon();
	}

	/**
	 * Ritorna la stringa ";"
	 * 
	 * @return String
	 */
	protected String semicolon() {
		return p.semicolon();
	}

	/**
	 * Ritorna la stringa " "
	 * 
	 * @return String
	 */
	protected String space() {
		return p.space();
	}

	/**
	 * Ritorna la stringa "."
	 * 
	 * @return String
	 */
	protected String dot() {
		return p.dot();
	}

	/**
	 * Ritorna la stringa "|"
	 * 
	 * @return String
	 */
	protected String pipe() {
		return p.pipe();
	}

	/**
	 * Ritorna la stringa "/"
	 * 
	 * @return String
	 */
	protected String slash() {
		return p.slash();
	}

	/**
	 * Ritorna la stringa "_"
	 * 
	 * @return String
	 */
	protected String lodash() {
		return p.lodash();
	}

	/**
	 * Ritorna la stringa "_"
	 * 
	 * @return String
	 */
	protected String underscore() {
		return p.underscore();
	}

	/**
	 * Ritorna la stringa formata da n spazi
	 * 
	 * @return String
	 */
	protected String space(Integer n) {
		return p.space(n);
	}

	/**
	 * Ritorna la stringa "["
	 * 
	 * @return String
	 */
	protected String quadra() {
		return p.quadra();
	}

	/**
	 * Ritorna la stringa "]"
	 * 
	 * @return String
	 */
	protected String quadraClose() {
		return p.quadraClose();
	}

	/**
	 * Ritorna la stringa "{"
	 * 
	 * @return String
	 */
	protected String graffa() {
		return p.graffa();
	}

	/**
	 * Ritorna la stringa "}"
	 * 
	 * @return String
	 */
	protected String graffaClose() {
		return p.graffaClose();
	}

	/**
	 * Ritorna la stringa "("
	 * 
	 * @return String
	 */
	protected String tonda() {
		return p.tonda();
	}

	/**
	 * Ritorna la stringa ")"
	 * 
	 * @return String
	 */
	protected String tondaClose() {
		return p.tondaClose();
	}

	/**
	 * Genera un numero casuale compreso tra [min,max]
	 * 
	 * @param min
	 * @param max
	 * @return Integer
	 */
	protected Integer generaNumeroCasuale(Integer min, Integer max) {
		return p.generaNumeroCasuale(min, max);
	}

	/**
	 * Ritorna la stringa "-->"
	 * 
	 * @return String
	 */
	protected String arrow() {
		return p.arrow();
	}

	/**
	 * Blocca l'esecuzione del current thread per i secondi specificati
	 * 
	 * @param secondi
	 * @throws InterruptedException
	 */
	protected void attendiSecondi(Integer secondi) throws InterruptedException {
		p.attendiSecondi(secondi);
	}

	/**
	 * Blocca l'esecuzione del current thread per i minuti specificati
	 * 
	 * @param minuti
	 * @throws InterruptedException
	 */
	protected void attendiMinuti(Integer minuti) throws InterruptedException {
		p.attendiMinuti(minuti);
	}

	/**
	 * Svuota il contenuto dei file individuati dai path
	 * 
	 * @param path
	 */
	protected void svuotaFile(String... path) {
		p.svuotaFile(path);
	}

	/**
	 * Svuota il contenuto dei file
	 * 
	 * @param f
	 */
	protected void svuotaFile(File... f) {
		p.svuotaFile(f);
	}

	/**
	 * Ritorna la stringa "€"
	 * 
	 * @return String
	 */
	protected String euro() {
		return p.euro();
	}

	/**
	 * Ritorna la stringa "$"
	 * 
	 * @return String
	 */
	protected String dollar() {
		return "$";
	}

	/**
	 * Ritorna la stringa "£"
	 * 
	 * @return String
	 */
	protected String pound() {
		return "£";
	}

	/**
	 * Ritorna la stringa "*"
	 * 
	 * @return String
	 */
	protected String star() {
		return "*";
	}

	/**
	 * Ritorna la stringa "~"
	 * 
	 * @return String
	 */
	protected String tilde() {
		return "~";
	}

	/**
	 * Concatena il simbolo dell'euro al risultato di money
	 * 
	 * @param bd
	 * @return String
	 */
	protected String moneyEuro(BigDecimal bd) {
		return p.moneyEuro(bd);
	}

	/**
	 * Concatena il simbolo del dollaro al risultato di money
	 * 
	 * @param bd
	 * @return String
	 */
	protected String moneyDollar(BigDecimal bd) {
		return p.moneyDollar(bd);
	}

	/**
	 * Concatena il simbolo della sterlina al risultato di money
	 * 
	 * @param bd
	 * @return String
	 */
	protected String moneyPound(BigDecimal bd) {
		return p.moneyPound(bd);
	}

	/**
	 * Ritorna la stringa s con il colore e gli effetti voluti
	 * 
	 * @param s
	 * @param c
	 * @param grassetto
	 * @param corsivo
	 * @param underscore
	 * @param barrato
	 * @return String
	 */
	protected String color(String s, Color c, boolean grassetto, boolean corsivo, boolean underscore, boolean barrato) {
		return p.color(s, c, grassetto, corsivo, underscore, barrato);
	}

	/**
	 * Alias di generaNumeroCasuale
	 * 
	 * @param min
	 * @param max
	 * @return Integer
	 */
	protected Integer random(Integer min, Integer max) {
		return p.random(min, max);
	}

	/**
	 * Data una lista di elementi K lunga n e un valore k intero, ritorna la
	 * lista di tutte le possibili combinazioni di n elementi di classe k
	 * 
	 * @param elems
	 * @param k
	 * @return PList<PList<K>>
	 */
	protected <K> PList<PList<K>> combinations(PList<K> elems, Integer k) {
		return p.combinations(elems, k);
	}
}
