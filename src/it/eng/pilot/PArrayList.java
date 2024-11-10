package it.eng.pilot;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Classe che estende le funzionalità di un classico ArrayList aggiungendo le
 * funzionalità di ricerca su lista in base agli operatori relazionali della
 * Enumeration it.eng.pilot.Operatore.
 * 
 * In tutti i metodi, se il parametro campo lo passo a NULL mi riferisco
 * automaticamente a una ricerca su liste di tipi Java ossia non liste di bean
 * custom. Non possono esistere condizioni di filtro miste, ossia filtri che non
 * specificano il parametro campo insieme a filtri che lo specificano, ossia non
 * possono coesistere insieme filtri di selezione per liste miste, le liste di
 * ricerca o sono esclusivamente di tipi Java o sono elementi bean custom con
 * definite variabli istanza su cui effettuare selezioni.
 * 
 * I parametri di ricerca passati a Null NON VENGONO APPLICATI nelle condizioni
 * di filtro.
 * 
 * @param <K>
 * @author Antonio Corinaldi
 */
@SuppressWarnings( { "unchecked", "hiding" })
public class PArrayList<K> extends ArrayList<K> implements PList<K> {
	private static final String GTE = ">=";
	private static final String LTE = "<=";
	private static final String GT = ">";
	private static final String LT = "<";
	private static final long serialVersionUID = 6964051254617182620L;
	private final Pilot p = new Pilot();
	private List<Filter> filtri = new ArrayList<Filter>();
	private Boolean beanMode;
	private Boolean javaMode;
	private Integer limit;
	private static final String METHOD_COD_UTENTE = "CODUTENTE";
	private static final String METHOD_COD_APPL = "CODAPPL";
	private static final String METHOD_DATA_AGGIORN = "DATAAGGIORN";

	private String str(Object... s) {
		return p.getString(s);
	}

	private boolean Null(Object... values) {
		return p.Null(values);
	}

	private boolean notNull(Object... values) {
		return p.notNull(values);
	}

	/**
	 * Limit è il limite massimo di elementi che l'oggetto PList può contenere.
	 * Oltre questo limite non si possono più aggiungere elementi e il metodo
	 * addElement torna false
	 * 
	 * @param limit
	 */
	public PArrayList(Integer limit) {
		this.limit = limit;
	}

	public PArrayList(Collection<K> l) {
		super(l);
	}

	public PArrayList(K... items) {
		super();
		if (null != items)
			for (K k : items) {
				add(k);
			}
	}

	public PArrayList() {
	}

	public <T> PList<K> eq(String campo, T val) {
		if (null != val)
			filtri.add(new Filter(campo, Operatore.EQUAL, val));
		return this;
	}

	public PList<K> trueValue(String campo) {
		return eq(campo, true);
	}

	public PList<K> falseValue(String campo) {
		return eq(campo, false);
	}

	public PList<K> isFalse(String campo) {
		return falseValue(campo);
	}

	public PList<K> isTrue(String campo) {
		return trueValue(campo);
	}

	public <T> PList<K> eq(T val) {
		return eq(null, val);
	}

	public <T> PList<K> in(String campo, List<T> val) {
		if (notNull(val))
			filtri.add(new Filter(campo, Operatore.IN, val));
		return this;
	}

	public <T> PList<K> in(List<T> val) {
		return in(null, val);
	}

	public <T> PList<K> inVals(String campo, T... val) {
		return in(campo, p.arrayToList(val));
	}

	public <T> PList<K> in(T... val) {
		return in(null, p.arrayToList(val));
	}

	public <T> PList<K> notIn(String campo, List<T> val) {
		if (notNull(val))
			filtri.add(new Filter(campo, Operatore.NOTIN, val));
		return this;
	}

	public <T> PList<K> notInVals(String campo, T... val) {
		return notIn(campo, p.arrayToList(val));
	}

	public <T> PList<K> notIn(T... val) {
		return notIn(null, p.arrayToList(val));
	}

	public <T> PList<K> notIn(List<T> val) {
		return notIn(null, val);
	}

	public <T> PList<K> likeValues(String campo, T... vals) {
		filtri.add(new Filter(campo, Operatore.LIKE, p.arrayToList(vals)));
		return this;
	}

	public <T> PList<K> like(T... vals) {
		return likeValues(null, vals);
	}

	public <T> PList<K> notLikeValues(String campo, T... vals) {
		filtri.add(new Filter(campo, Operatore.NOTLIKE, p.arrayToList(vals)));
		return this;
	}

	public <T> PList<K> notLike(T... vals) {
		return notLikeValues(null, vals);
	}

	public <T> PList<K> neqVals(String campo, T... vals) {
		filtri.add(new Filter(campo, Operatore.NOT_EQUAL, p.arrayToList(vals)));
		return this;
	}

	public <T> PList<K> neq(T... vals) {
		return neqVals(null, vals);
	}

	public <T> PList<K> between(String campo, T val1, T val2) {
		filtri.add(new Filter(campo, Operatore.BETWEEN, val1, val2));
		return this;
	}

	public <T> PList<K> between(T val1, T val2) {
		return between(null, val1, val2);
	}

	public <T> PList<K> gt(String campo, T val) {
		if (val != null)
			filtri.add(new Filter(campo, Operatore.GT, val));
		return this;
	}

	public <T> PList<K> gt(T val) {
		return gt(null, val);
	}

	public <T> PList<K> gte(String campo, T val) {
		if (val != null)
			filtri.add(new Filter(campo, Operatore.GTE, val));
		return this;
	}

	public <T> PList<K> gte(T val) {
		return gte(null, val);
	}

	public <T> PList<K> lt(String campo, T val) {
		if (val != null)
			filtri.add(new Filter(campo, Operatore.LT, val));
		return this;
	}

	public <T> PList<K> lt(T val) {
		return lt(null, val);
	}

	public <T> PList<K> lte(String campo, T val) {
		if (val != null)
			filtri.add(new Filter(campo, Operatore.LTE, val));
		return this;
	}

	public <T> PList<K> lte(T val) {
		return lte(null, val);
	}

	public PList<K> isNull(String campo) {
		filtri.add(new Filter(campo, Operatore.ISNULL));
		return this;
	}

	public PList<K> isNotNull(String campo) {
		filtri.add(new Filter(campo, Operatore.ISNOTNULL));
		return this;
	}

	public PList<K> isNotNull() {
		filtri.add(new Filter(null, Operatore.ISNOTNULL));
		return this;
	}

	public PList<K> isNull() {
		filtri.add(new Filter(null, Operatore.ISNULL));
		return this;
	}

	public PList<K> find() throws Exception {
		PList<K> sel = this;
		checkFilters();
		for (Filter f : filtri) {
			if (notNull(beanMode) && beanMode) {
				sel = p.toPList(p.find((List<K>) sel, f.getCampo(), f.getOp(), f.getVals()));
			}
			if (notNull(javaMode) && javaMode) {
				sel = p.toPList(p.find((List<K>) sel, f.getOp(), f.getVals()));
			}
		}
		beanMode = null;
		javaMode = null;
		cleanFilters();
		return sel;
	}

	public PList<PList<K>> listPagination(Integer pageSize) {
		if (p.almenoUna(Null(pageSize), pageSize <= 0, pageSize > size()))
			pageSize = size();
		int numPages = (int) Math.ceil((double) size() / (double) pageSize);
		PList<PList<K>> pages = new PArrayList<PList<K>>(numPages);
		for (int pageNum = 0; pageNum < numPages;)
			pages.add((PList<K>) p.toPList(subList(pageNum * pageSize, Math.min(++pageNum * pageSize, size()))));
		return pages;
	}

	/**
	 * Metodo che controlla la congruità dei filtri impostati, se sono tutti in
	 * modalità assoluta (per liste di tipi Java) o tutti in modalità bean
	 * custom. Se sono misti solleva eccezione.
	 * 
	 * @throws Exception
	 */
	private void checkFilters() throws Exception {
		List<Filter> filtriJava = p.find(filtri, "campo", Operatore.ISNULL);
		List<Filter> filtriBean = p.find(filtri, "campo", Operatore.ISNOTNULL);
		if (p.tutte(p.is(filtriJava.size(), filtri.size()), Null(filtriBean))) {
			javaMode = true;
		}
		if (p.tutte(p.is(filtriBean.size(), filtri.size()), Null(filtriJava))) {
			beanMode = true;
		}
		if (Null(beanMode, javaMode))
			throw new Exception(
					"Filtri impostati in modo errato. Non si possono inserire filtri assoluti (per liste di tipi classe base Java) con filtri relativi a campi di bean (per lista di tipi custom definiti)");

	}

	public K findOne() throws Exception {
		return p.getFirstElement(find());
	}

	public void cleanFilters() {
		filtri = new ArrayList<Filter>();
	}

	public K get(Integer i) {
		return p.get((List<K>) this, i);
	}

	public <T> PList<T> narrow(String props) throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, props));
	}

	public <T> PList<T> narrowDistinct(String props) throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, props));
	}

	public PList<K> cleanByValidator() {
		for (Iterator<K> iterator = this.iterator(); iterator.hasNext();) {
			K item = (K) iterator.next();
			if (item instanceof Validator) {
				Validator item_ = (Validator) item;
				if (!item_.validate()) {
					iterator.remove();
				}
			}
		}
		return this;
	}

	private Integer getLimit() {
		return limit;
	}

	public boolean addElement(K t) {
		return p.addToLimit((List<K>) this, getLimit(), t);
	}

	private <K extends BaseEntity> String getFieldStato(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.stato()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldId(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.id()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldCassa(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.cassa()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldNome(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.nome()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldCognome(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.cognome()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldEta(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.eta()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldCf(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.cf()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldImporto(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.importo()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldCitta(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.citta()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldIndirizzo(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.indirizzo()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldTitolo(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.titolo()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldPiva(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.piva()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldNazione(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.nazione()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldProgr(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.progr()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldSede(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.sede()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldIscritto(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.iscritto()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldEnte(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.ente()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldStartDate(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.startDate()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldEndDate(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.endDate()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldCodice(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.codice()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldTipo(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.tipo()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldFascicolo(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.fascicolo()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldDescrizione(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.descrizione()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldStato1(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.stato1()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldTotale(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.totale()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldValore(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.valore()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldUsername(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.username()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldEmail(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.email()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldDataInizio(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.startDate()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldDataFine(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.endDate()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	public <K extends BaseEntity> PList<K> betweenStartAndEndDate(Date d) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		String inizio = getFieldDataInizio(k);
		String fine = getFieldDataFine(k);
		if (p.NullOR(inizio, fine)) {
			logMissingCol("con @Column(...startDate=true)", k);
			return (PList<K>) this;
		} else {
			PList<K> ret = (PList<K>) lte(inizio, d).gte(fine, d).find();
			String s = str(inizio, LTE, p.dateToString(d), LTE, fine, getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	public <K extends BaseEntity> PList<K> betweenNow() throws Exception {
		return betweenStartAndEndDate(p.now());
	}

	public <K extends BaseEntity> PList<K> startBeforeNow() throws Exception {
		return startBefore(p.now());
	}

	public <K extends BaseEntity> PList<K> startBeforeEqualNow() throws Exception {
		return startBeforeEqual(p.now());
	}

	public <K extends BaseEntity> PList<K> startBefore(Date d) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		if (Null(d))
			return (PList<K>) this;
		String campo = getFieldDataInizio(k);
		if (Null(campo)) {
			logMissingCol("con @Column(...startDate=true)", k);
			return (PList<K>) this;
		} else {
			PList<K> ret = (PList<K>) lt(campo, d).find();
			String s = str(campo, LT, p.dateToString(d), getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	public <K extends BaseEntity> PList<K> startBeforeEqual(Date d) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		if (Null(d))
			return (PList<K>) this;
		String campo = getFieldDataInizio(k);
		if (Null(campo)) {
			logMissingCol("con @Column(...startDate=true)", k);
			return (PList<K>) this;
		} else {
			PList<K> ret = (PList<K>) lte(campo, d).find();
			String s = str(campo, LTE, p.dateToString(d), getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	public <K extends BaseEntity> PList<K> endBefore(Date d) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		if (Null(d))
			return (PList<K>) this;
		String campo = getFieldDataFine(k);
		if (Null(campo)) {
			logMissingCol("con @Column(...endDate=true)", k);
			return (PList<K>) this;
		} else {
			PList<K> ret = (PList<K>) lt(campo, d).find();
			String s = str(campo, LT, p.dateToString(d), getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	public <K extends BaseEntity> PList<K> endBeforeEqual(Date d) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		if (Null(d))
			return (PList<K>) this;
		String campo = getFieldDataFine(k);
		if (Null(campo)) {
			logMissingCol("con @Column(...endDate=true)", k);
			return (PList<K>) this;
		} else {
			PList<K> ret = (PList<K>) lte(campo, d).find();
			String s = str(campo, LTE, p.dateToString(d), getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	public <K extends BaseEntity> PList<K> endBeforeNow() throws Exception {
		return endBefore(p.now());
	}

	public <K extends BaseEntity> PList<K> endBeforeEqualNow() throws Exception {
		return endBeforeEqual(p.now());
	}

	public <K extends BaseEntity> PList<K> startAfterNow() throws Exception {
		return startAfter(p.now());
	}

	public <K extends BaseEntity> PList<K> startAfterEqualNow() throws Exception {
		return startAfterEqual(p.now());
	}

	public <K extends BaseEntity> PList<K> endAfterNow() throws Exception {
		return endAfter(p.now());
	}

	public <K extends BaseEntity> PList<K> endAfterEqualNow() throws Exception {
		return endAfterEqual(p.now());
	}

	public <K extends BaseEntity> PList<K> startAfter(Date d) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		if (Null(d))
			return (PList<K>) this;
		String campo = getFieldDataInizio(k);
		if (Null(campo)) {
			logMissingCol("con @Column(...startDate=true)", k);
			return (PList<K>) this;
		} else {
			PList<K> ret = (PList<K>) gt(campo, d).find();
			String s = str(campo, GT, p.dateToString(d), getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	public <K extends BaseEntity> PList<K> startAfterEqual(Date d) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		if (Null(d))
			return (PList<K>) this;
		String campo = getFieldDataInizio(k);
		if (Null(campo)) {
			logMissingCol("con @Column(...startDate=true)", k);
			return (PList<K>) this;
		} else {
			PList<K> ret = (PList<K>) gte(campo, d).find();
			String s = str(campo, GTE, p.dateToString(d), getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	public <K extends BaseEntity> PList<K> endAfter(Date d) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		if (Null(d))
			return (PList<K>) this;
		String campo = getFieldDataFine(k);
		if (Null(campo)) {
			logMissingCol("con @Column(...endDate=true)", k);
			return (PList<K>) this;
		} else {
			PList<K> ret = (PList<K>) gt(campo, d).find();
			String s = str(campo, GT, p.dateToString(d), getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	public <K extends BaseEntity> PList<K> endAfterEqual(Date d) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		if (Null(d))
			return (PList<K>) this;
		String campo = getFieldDataFine(k);
		if (Null(campo)) {
			logMissingCol("con @Column(...endDate=true)", k);
			return (PList<K>) this;
		} else {
			PList<K> ret = (PList<K>) gte(campo, d).find();
			String s = str(campo, GTE, p.dateToString(d), getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	private <K extends BaseEntity> String getInfoSize(PList<K> l) {
		if (l.size() < size()) {
			return str("----->[Riduco a ", String.valueOf(l.size()), " record]");
		} else {
			return " [Already. No Filter Effect]";
		}
	}

	private <K extends BaseEntity> void addQuery(K ent, String testo) throws Exception {
		Method m = ent.getClass().getMethod("addQuery", String.class);
		m.invoke(ent, str("\tPOST-QUERY IN MEMORY FILTER ", testo));
	}

	private <K extends BaseEntity> String getFieldCodUtente(K k) {
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			if (att.getName().toUpperCase().endsWith(METHOD_COD_UTENTE)) {
				campo = att.getName();
				break;
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldCodApp(K k) {
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			if (att.getName().toUpperCase().endsWith(METHOD_COD_APPL)) {
				campo = att.getName();
				break;
			}
		}
		return campo;
	}

	private <K extends BaseEntity> String getFieldDataAgg(K k) {
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			if (att.getName().toUpperCase().endsWith(METHOD_DATA_AGGIORN)) {
				campo = att.getName();
				break;
			}
		}
		return campo;
	}

	public <K extends BaseEntity> PList<K> byUser(String user) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		if (Null(user))
			return (PList<K>) this;
		String campo = getFieldCodUtente(k);
		if (Null(campo)) {
			logMissingCol("*COD_UTENTE", k);
			return (PList<K>) this;
		} else {
			PList<K> ret = (PList<K>) eq(campo, user).find();
			String s = str(campo, "=", user, getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	private <K extends BaseEntity> void logMissingCol(String colName, K k) {
		p.log("Mancanza della colonna ", colName, " per l'Entity", k.getClass().getSimpleName());
	}

	public <K extends BaseEntity> PList<K> byApp(String app) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		if (Null(app))
			return (PList<K>) this;
		String campo = getFieldCodApp(k);
		if (Null(campo)) {
			logMissingCol("*COL_APPL", k);
			return (PList<K>) this;
		} else {
			PList<K> ret = (PList<K>) eq(campo, app).find();
			String s = str(campo, "=", app, getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	public <K extends BaseEntity> PList<K> modifiedSince(Date d) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		if (Null(d))
			return (PList<K>) this;
		String campo = getFieldDataAgg(k);
		if (Null(campo)) {
			logMissingCol("*DATA_AGGIORN", k);
			return (PList<K>) this;
		} else {
			PList<K> ret = (PList<K>) gte(campo, d).find();
			String s = str(campo, GTE, p.dateToString(d), getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	public <K extends BaseEntity> PList<K> modifiedBetween(Date start, Date end) throws Exception {
		K k = (K) getFirstElement();
		if (Null(k))
			return new PArrayList<K>();
		if (Null(start, end)) {
			return (PList<K>) this;
		}
		String campo = getFieldDataAgg(k);
		if (Null(campo)) {
			logMissingCol("*DATA_AGGIORN", k);
			return (PList<K>) this;
		} else {
			if (Null(end)) {
				PList<K> ret = (PList<K>) gte(campo, start).find();
				String s = str(campo, GTE, p.dateToString(start), getInfoSize(ret));
				p.log(s);
				addQuery(k, s);
				return ret;
			}
			if (Null(start)) {
				PList<K> ret = (PList<K>) lte(campo, end).find();
				String s = str(campo, LTE, p.dateToString(end), getInfoSize(ret));
				p.log(s);
				addQuery(k, s);
				return ret;
			}
			PList<K> ret = (PList<K>) lte(campo, end).gte(campo, start).find();
			String s = str(campo, " between [", p.dateToString(start), ",", p.dateToString(end), "]", getInfoSize(ret));
			p.log(s);
			addQuery(k, s);
			return ret;
		}
	}

	public <K extends BaseEntity> PList<K> lastYear() throws Exception {
		return modifiedSince(p.addYears(p.now(), -1));
	}

	public <K extends BaseEntity> PList<K> lastMonth() throws Exception {
		return modifiedSince(p.addMonths(p.now(), -1));
	}

	public <K extends BaseEntity> PList<K> lastWeek() throws Exception {
		return modifiedSince(p.addDays(p.now(), -7));
	}

	public <K extends BaseEntity> PList<K> lastDay() throws Exception {
		return modifiedSince(p.addDays(p.now(), -1));
	}

	public <K extends BaseEntity> PList<K> lastHour() throws Exception {
		return modifiedSince(p.addHours(p.now(), -1));
	}

	public <K extends BaseEntity> PList<K> last30Minutes() throws Exception {
		return modifiedSince(p.addMinutes(p.now(), -30));
	}

	public <K extends BaseEntity> PList<K> last15Minutes() throws Exception {
		return modifiedSince(p.addMinutes(p.now(), -15));
	}

	public <K extends BaseEntity> PList<K> last10Minutes() throws Exception {
		return modifiedSince(p.addMinutes(p.now(), -10));
	}

	public <K extends BaseEntity> PList<K> last5Minutes() throws Exception {
		return modifiedSince(p.addMinutes(p.now(), -5));
	}

	public <T> PMap<T, PList<K>> groupBy(String campo) throws Exception {
		Map<T, List<K>> mappa = p.groupBy((List<K>) this, campo);
		PMap<T, PList<K>> nuova = new PHashMap<T, PList<K>>();
		for (Map.Entry<T, List<K>> entry : mappa.entrySet()) {
			nuova.put(entry.getKey(), p.toPList(entry.getValue()));
		}
		return nuova;
	}

	public <T> PMap<T, PList<K>> groupBy(String campo, int n) throws Exception {
		Map<T, List<K>> mappa = p.groupBy((List<K>) this, campo);
		PMap<T, PList<K>> nuova = new PHashMap<T, PList<K>>();
		for (Map.Entry<T, List<K>> entry : mappa.entrySet()) {
			nuova.put(entry.getKey(), (PList<K>) p.toPList(entry.getValue()).cutToFirst(n));
		}
		return nuova;
	}

	public PList<K> sort() {
		return (PList<K>) p.sort(this);
	}

	public PList<K> sortDesc() {
		return (PList<K>) p.sortDesc(this);
	}

	public PList<K> sort(String... campi) {
		return (PList<K>) p.sort(this, campi);
	}

	public PList<K> sortDesc(String... campi) {
		return (PList<K>) p.sortDesc(this, campi);
	}

	public K getFirstElement() {
		return p.getFirstElement(this);
	}

	public K getLastElement() {
		return p.getLastElement(this);
	}

	public PList<K> inverti() {
		return (PList<K>) p.inverti(this);
	}

	public PList<K> removeDuplicates() {
		return (PList<K>) p.removeDuplicates(this);
	}

	public PList<K> aggiungiList(PList<K>... l) {
		if (null != l) {
			for (PList<K> k : l) {
				addAll(p.safe(k));
			}
		}
		return this;
	}

	public PList<K> sottraiList(PList<K>... liste) {
		return p.toPList(p.sottraiList(this, (List<K>[]) liste));
	}

	public PList<K> intersection(PList<K>... liste) {
		return p.toPList(p.intersection(this, (List<K>[]) liste));
	}

	public boolean cleanNull() {
		return p.cleanNullList(this);
	}

	public PList<K> selfExtend(Integer times) {
		return p.toPList(p.selfExtendList(this, times));
	}

	public PList<K> cutToFirst(Integer n) {
		return p.toPList(p.cutToFirst(this, n));
	}

	public PList<K> cutToLast(Integer n) {
		return p.toPList(p.cutToLast(this, n));
	}

	public <T> PList<T> distinct(String campo) {
		return (PList<T>) p.toPList(p.distinct(this, campo));
	}

	public PList<K> distinct() {
		return p.removeDuplicates(p.removeDuplicates(this));
	}

	public <T> T sommatoria(String campo, Class<T> c) throws Exception {
		return p.sommatoria(this, campo, c);
	}

	public <T> T sommatoria(Class<T> c) throws Exception {
		return p.sommatoria(this, null, c);
	}

	public <T> T media(String campo, Class<T> c) throws Exception {
		return p.media(this, campo, c);
	}

	public <T> T media(Class<T> c) throws Exception {
		return p.media(this, null, c);
	}

	public boolean moreThanOne() {
		return moreThan(1);
	}

	public boolean atLeastOne() {
		return atLeast(1);
	}

	public boolean atLeast(Integer i) {
		return size() >= i;
	}

	public boolean moreThan(Integer i) {
		return size() > i;
	}

	public <T> boolean isAllListValues(String prop, T value) throws Exception {
		return p.isAllListBeanValues(this, prop, value);
	}

	public <T> boolean allMatch(String prop, T value) throws Exception {
		return isAllListValues(prop, value);
	}

	public <T> boolean noneMatch(String prop, T value) throws Exception {
		return p.isNoneListBeanValues(this, prop, value);
	}

	public <T> boolean anyMatch(String prop, T value) throws Exception {
		return p.isAnyListBeanValues(this, prop, value);
	}

	public <T> boolean anyMatch(String prop, T... values) throws Exception {
		if (null == values || values.length == 0)
			return false;
		boolean ret = false;
		for (T item : values) {
			ret = p.isAnyListBeanValues(this, prop, item);
			if (ret)
				break;
		}
		return ret;
	}

	public <T> boolean noneMatch(String prop, T... values) throws Exception {
		if (null == values || values.length == 0)
			return false;
		boolean ret = true;
		for (T item : values) {
			ret = p.isNoneListBeanValues(this, prop, item);
			if (!ret)
				break;
		}
		return ret;
	}

	public boolean isAllListValues(K value) throws Exception {
		return p.isAllListValues(this, value);
	}

	public boolean allMatch(K value) throws Exception {
		return isAllListValues(value);
	}

	public boolean noneMatch(K value) throws Exception {
		return p.isNoneListValues(this, value);
	}

	public boolean anyMatch(K value) throws Exception {
		return p.isAnyListValues(this, value);
	}

	public K max(String... prop) throws Exception {
		return p.maxBean(this, prop);
	}

	public K min(String... prop) throws Exception {
		return p.minBean(this, prop);
	}

	public K getFirstElementMaxValue(String prop) throws Exception {
		return p.getFirstElementMaxValue(this, prop);
	}

	public K getFirstElementMinValue(String prop) throws Exception {
		return p.getFirstElementMinValue(this, prop);
	}

	public K getLastElementMaxValue(String prop) throws Exception {
		return p.getLastElementMaxValue(this, prop);
	}

	public K getLastElementMinValue(String prop) throws Exception {
		return p.getLastElementMinValue(this, prop);
	}

	public PList<K> setLimit(Integer limit) {
		this.limit = limit;
		return this;
	}

	public K max() {
		if (Null(this))
			return null;
		return (K) Collections.max((Collection) this);
	}

	public K min() {
		if (Null(this))
			return null;
		return (K) Collections.min((Collection) this);
	}

	public PList<K> mask(String... campi) throws Exception {
		return p.maskList(this, campi);
	}

	public <T, K extends BaseEntity> PList<T> convert(Class<T> typeToConvert) throws Exception {
		PList<T> resConv = p.pl();
		K k = (K) getFirstElement();
		if (notNull(k)) {
			resConv.aggiungiList(k.convert(typeToConvert, (PList<K>) this));
		}
		return resConv;
	}

	public PList<K> aggiornaListValue(K valueToUpdate, K newValue) throws Exception {
		return p.toPList(p.aggiornaListValue(this, valueToUpdate, newValue));
	}

	public int countInList(K e) {
		return p.countInList(this, e);
	}

	public <T> int countInList(String prop, T val) throws Exception {
		return p.countInList(this, prop, val);
	}

	public List<K> toArrayList() {
		return p.toArrayList(this);
	}

	public PList<String> distinctMulti(String... campi) {
		return p.distinctMulti(this, campi);
	}

	public PList<K> skip(Integer n) {
		return p.toPList(p.skip(this, n));
	}

	public PList<K> skipFirst() {
		return p.toPList(p.skipFirst(this));
	}

	public Map<Boolean, PList<K>> splitBy(String campo) throws Exception {
		PMap<Boolean, PList<K>> map = new PHashMap<Boolean, PList<K>>();
		for (K k : this) {
			Object o = p.get(k, campo);
			if (!(o instanceof Boolean)) {
				noBoolean(campo);
				break;
			}
			map.aggiungiMappaLista(o, k);
		}
		return map;
	}

	public PList<K> drop(Integer n) {
		return p.toPList(subList(0, size() - p.limiteInferiore(0, p.limiteSuperiore(n, size()))));
	}

	public PList<K> dropLast() {
		return drop(1);
	}

	public PList<K> cleanList(K value) throws Exception {
		return p.toPList(p.cleanList(this, value));
	}

	public PList<K> cleanList(K... value) throws Exception {
		return p.toPList(p.cleanList(this, value));
	}

	public PList<K> cleanListBean(String prop, Object val) throws Exception {
		return p.toPList(p.cleanList(this, prop, val));
	}

	public PList<K> cleanListBean(String[] props, Object[] val) throws Exception {
		return p.toPList(p.cleanList(this, props, val));
	}

	public PList<K> cleanListLike(K value) throws Exception {
		return p.toPList(p.cleanListLike(this, value));
	}

	public PList<K> cleanListLike(K... value) throws Exception {
		return p.toPList(p.cleanListLike(this, value));
	}

	public PList<K> cleanListBeanLike(String prop, K val) throws Exception {
		return p.toPList(p.cleanListLike(this, prop, val));
	}

	public PList<K> cleanListBeanLike(String[] props, K[] val) throws Exception {
		return p.toPList(p.cleanListLike(this, props, val));
	}

	public PList<K> cleanListBeanNotEqual(String prop, Object val) throws Exception {
		return p.toPList(p.cleanListNotEqual(this, prop, val));
	}

	public PList<K> cleanListBeanNotEqual(String[] props, Object[] val) throws Exception {
		return p.toPList(p.cleanListNotEqual(this, props, val));
	}

	public PList<K> cleanListNotEqual(K value) throws Exception {
		return p.toPList(p.cleanListNotEqual(this, value));
	}

	public PList<K> forEach(String name, Object... args) throws Exception {
		K k = getFirstNotNullElement();
		if (notNull(k)) {
			Method metodo = null;
			for (Method met : k.getClass().getDeclaredMethods()) {
				if (notNull(met.getAnnotation(Logic.class))) {
					if (p.is(met.getAnnotation(Logic.class).name(), name)) {
						metodo = met;
						break;
					}
				}
			}
			if (notNull(metodo)) {
				for (K item : this) {
					if (Null(item))
						continue;
					try {
						metodo.invoke(item, args);
					} catch (Exception e) {
						p.logError("Errore durante il forEach per ", item, e);
						throw new Exception(str("Errore durante il forEach per ", item, e));
					}
				}
			} else {
				p.logError("Iterazione forEach  ", name, "non trovata!");
			}
		}
		return this;
	}

	public <T> PList<T> map(String name, Class<T> tipo, Object... args) throws Exception {
		Method metodo = null;
		K k = getFirstNotNullElement();
		if (notNull(k)) {
			for (Method met : k.getClass().getDeclaredMethods()) {
				if (notNull(met.getAnnotation(Mapping.class))) {
					if (p.is(met.getAnnotation(Mapping.class).name(), name)) {
						metodo = met;
						break;
					}
				}
			}
		}
		PList<T> nuova = new PArrayList<T>();
		if (notNull(metodo)) {
			for (K item : this) {
				try {
					if (Null(item))
						nuova.add(null);
					else
						nuova.add((T) metodo.invoke(item, args));
				} catch (Exception e) {
					p.logError("Errore durante il mapping per ", item, e);
					throw new Exception(str("Errore durante il mapping per ", item, e));
				}
			}
		} else {
			p.logError("Iterazione mapping  ", name, "non trovata!");
		}
		return nuova;
	}

	public <T> PList<T> map(String name, Object... args) throws Exception {
		Method metodo = null;
		K k = getFirstNotNullElement();
		if (notNull(k)) {
			for (Method met : k.getClass().getDeclaredMethods()) {

				if (notNull(met.getAnnotation(Mapping.class))) {
					if (p.is(met.getAnnotation(Mapping.class).name(), name)) {
						metodo = met;
						break;
					}
				}
			}
		}
		PList<T> nuova = new PArrayList<T>();
		if (notNull(metodo)) {
			for (K item : this) {
				try {
					if (Null(item))
						nuova.add(null);
					else
						nuova.add((T) metodo.invoke(item, args));
				} catch (Exception e) {
					p.logError("Errore durante il mapping per ", item, e);
					throw new Exception(str("Errore durante il mapping per ", item, e));
				}
			}
		} else {
			p.logError("Iterazione mapping  ", name, "non trovata!");
		}
		return nuova;
	}

	public K getFirstNotNullElement() {
		for (K k : this) {
			if (notNull(k))
				return k;
		}
		return null;
	}

	public PList<K> addIf(K k, String campo) throws Exception {
		Object o = p.get(k, campo);
		if (!(o instanceof Boolean)) {
			noBoolean(campo);
		}
		if ((Boolean) o) {
			add(k);
		}
		return this;
	}

	public PList<K> addElementIf(K k, String campo) throws Exception {
		Object o = p.get(k, campo);
		if (!(o instanceof Boolean)) {
			noBoolean(campo);
		}
		if ((Boolean) o) {
			addElement(k);
		}
		return this;
	}

	public PList<K> removeIf(String campo) throws Exception {
		for (Iterator<K> iterator = iterator(); iterator.hasNext();) {
			Object o = p.get(iterator.next(), campo);
			if (!(o instanceof Boolean)) {
				noBoolean(campo);
				continue;
			}
			if ((Boolean) o) {
				iterator.remove();
			}
		}
		return this;
	}

	public PList<K> addAllIf(PList<K> lista, String campo) throws Exception {
		K k = lista.getFirstNotNullElement();
		if (Null(k)) {
			k = getFirstNotNullElement();
		}
		if (notNull(k)) {
			for (K item : p.safe(lista)) {
				Object o = p.get(item, campo);
				if (!(o instanceof Boolean)) {
					noBoolean(campo);
					continue;
				}
				if ((Boolean) o) {
					add(k);
				}
			}
		} else {
			addAll(lista);
		}
		return this;
	}

	public PList<K> aggiungiListaIf(PList<K> lista, String campo) throws Exception {
		return addAllIf(lista, campo);
	}

	private void noBoolean(String campo) {
		p.logError("Il campo", campo, "non è di tipo boolean");
	}

	private static String getGetterMethodName(String field) {
		return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}

	public Map<PList<Object>, PList<K>> groupByFields(String... fields) {
		Map<PList<Object>, PList<K>> groupedMap = new HashMap<PList<Object>, PList<K>>();
		for (K item : this) {
			PList<Object> key = new PArrayList<Object>();
			for (String field : fields) {
				try {
					key.add(item.getClass().getMethod(getGetterMethodName(field)).invoke(item));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (!groupedMap.containsKey(key)) {
				groupedMap.put(key, new PArrayList<K>());
			}
			groupedMap.get(key).add(item);
		}

		return groupedMap;
	}

	public String concatena(String sep) {
		return p.concatenaLista(this, sep, false);
	}

	public String concatenaDash() {
		return concatena(Pilot.DASHTRIM);
	}

	public String concatenaComma() {
		return concatena(Pilot.COMMA);
	}

	public String concatenaPipe() {
		return concatena(Pilot.PIPE);
	}

	private Method findMethod(Method[] metodi, String nome) {
		Method met = null;
		for (Method m : metodi) {
			if (p.is(m.getName(), nome)) {
				met = m;
				break;
			}
		}
		return met;
	}

	private void invokeSetter(K o, Object valore, Field att, Method[] metodi) throws Exception {
		findMethod(metodi, p.str(Pilot.SET, p.capFirstLetter(att.getName()))).invoke(o, valore);
	}

	public K collapse() {
		K collapsed = null;
		if (notNull(this)) {
			PList<Field> campiNumerici = new PArrayList<Field>();
			PList<Field> campiStringa = new PArrayList<Field>();
			Field[] campi = getFirstElement().getClass().getDeclaredFields();
			Method[] metodi = getFirstElement().getClass().getDeclaredMethods();
			for (Field f : campi) {
				if (Number.class.isAssignableFrom(f.getType())) {
					campiNumerici.add(f);
				}
				if (String.class.isAssignableFrom(f.getType())) {
					campiStringa.add(f);
				}
			}
			try {
				collapsed = (K) getFirstElement().getClass().newInstance();
			} catch (Exception e) {
				p.logError("Errore durante il collapse della lista", e);
			}
			for (Field c : p.safe(campiNumerici)) {
				try {
					invokeSetter(collapsed, sommatoria(c.getName(), c.getType()), c, metodi);
				} catch (Exception e) {
					p.logError("Errore durante il collapse dell'attributo ", c.getName(), e);
				}
			}

			for (Field c : p.safe(campiStringa)) {
				try {
					invokeSetter(collapsed, p.concatenaLista(narrow(c.getName()), Pilot.PIPE, false), c, metodi);
				} catch (Exception e) {
					p.logError("Errore durante il collapse dell'attributo ", c.getName(), e);
				}
			}
		}
		return collapsed;
	}

	public boolean onlyOne() {
		return size() == 1;
	}

	public <K extends Number> PList<String> toListString() {
		return p.toListString((PList<K>) this);
	}

	public PList<Long> toListLong() {
		return p.toListLong((PList<String>) this);
	}

	public PList<Double> toListDouble() {
		return p.toListDouble((PList<String>) this);
	}

	public PList<Float> toListFloat() {
		return p.toListFloat((PList<String>) this);
	}

	public PList<BigDecimal> toListBigDecimal() {
		return p.toListBigDecimal((PList<String>) this);
	}

	public PList<Integer> toListInteger() {
		return p.toListInteger((PList<String>) this);
	}

	public void toFile(String path) {
		p.writeFile(path, (PList<String>) this);
	}

	public PList<String> fromFile(String path) {
		return p.readFile(path);
	}

	public PList<String> trim() {
		PList<String> lista = new PArrayList<String>();
		for (String s : (PList<String>) this) {
			lista.add(p.emptyIfNull(s).trim());
		}
		return lista;
	}

	public <T, K extends BaseEntity> PList<K> citta(T val) throws Exception {
		return (PList<K>) eq(getFieldCitta((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cittaNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldCitta((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cittaLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldCitta((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cittaNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldCitta((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cittaIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldCitta((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cittaNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldCitta((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cittaBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldCitta((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowCitta() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldCitta((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctCitta() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldCitta((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> titolo(T val) throws Exception {
		return (PList<K>) eq(getFieldTitolo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> titoloNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldTitolo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> titoloLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldTitolo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> titoloNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldTitolo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> titoloIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldTitolo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> titoloNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldTitolo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> titoloBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldTitolo((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowTitolo() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldTitolo((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctTitolo() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldTitolo((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> indirizzo(T val) throws Exception {
		return (PList<K>) eq(getFieldIndirizzo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> indirizzoNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldIndirizzo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> indirizzoLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldIndirizzo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> indirizzoNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldIndirizzo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> indirizzoIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldIndirizzo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> indirizzoNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldIndirizzo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> indirizzoBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldIndirizzo((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowIndirizzo() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldIndirizzo((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctIndirizzo() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldIndirizzo((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> piva(T val) throws Exception {
		return (PList<K>) eq(getFieldPiva((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> pivaNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldPiva((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> pivaLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldPiva((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> pivaNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldPiva((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> pivaIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldPiva((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> pivaNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldPiva((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> pivaBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldPiva((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowPiva() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldPiva((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctPiva() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldPiva((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> nazione(T val) throws Exception {
		return (PList<K>) eq(getFieldNazione((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> nazioneNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldNazione((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> nazioneLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldNazione((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> nazioneNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldNazione((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> nazioneIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldNazione((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> nazioneNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldNazione((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> nazioneBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldNazione((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowNazione() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldNazione((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctNazione() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldNazione((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> ente(T val) throws Exception {
		return (PList<K>) eq(getFieldEnte((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> enteNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldEnte((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> enteLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldEnte((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> enteNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldEnte((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> enteIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldEnte((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> enteNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldEnte((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> enteBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldEnte((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowEnte() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldEnte((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctEnte() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldEnte((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> sede(T val) throws Exception {
		return (PList<K>) eq(getFieldSede((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> sedeNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldSede((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> sedeLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldSede((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> sedeNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldSede((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> sedeIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldSede((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> sedeNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldSede((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> sedeBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldSede((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowSede() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldSede((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctSede() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldSede((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> cassa(T val) throws Exception {
		return (PList<K>) eq(getFieldCassa((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cassaNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldCassa((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cassaLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldCassa((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cassaNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldCassa((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cassaIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldCassa((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cassaNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldCassa((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cassaBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldCassa((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowCassa() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldCassa((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctCassa() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldCassa((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> cf(T val) throws Exception {
		return (PList<K>) eq(getFieldCf((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cfNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldCf((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cfLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldCf((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cfNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldCf((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cfIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldCf((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cfNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldCf((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cfBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldCf((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowCf() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldCf((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctCf() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldCf((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> codice(T val) throws Exception {
		return (PList<K>) eq(getFieldCodice((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> codiceNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldCodice((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> codiceLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldCodice((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> codiceNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldCodice((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> codiceIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldCodice((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> codiceNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldCodice((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> codiceBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldCodice((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowCodice() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldCodice((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctCodice() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldCodice((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> cognome(T val) throws Exception {
		return (PList<K>) eq(getFieldCognome((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cognomeNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldCognome((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cognomeLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldCognome((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cognomeNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldCognome((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cognomeIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldCognome((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cognomeNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldCognome((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> cognomeBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldCognome((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowCognome() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldCognome((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctCognome() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldCognome((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> descrizione(T val) throws Exception {
		return (PList<K>) eq(getFieldDescrizione((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> descrizioneNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldDescrizione((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> descrizioneLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldDescrizione((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> descrizioneNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldDescrizione((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> descrizioneIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldDescrizione((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> descrizioneNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldDescrizione((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> descrizioneBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldDescrizione((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowDescrizione() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldDescrizione((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctDescrizione() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldDescrizione((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> endDate(T val) throws Exception {
		return (PList<K>) eq(getFieldEndDate((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> endDateNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldEndDate((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> endDateLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldEndDate((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> endDateNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldEndDate((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> endDateIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldEndDate((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> endDateNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldEndDate((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> endDateBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldEndDate((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowEndDate() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldEndDate((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctEndDate() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldEndDate((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> startDate(T val) throws Exception {
		return (PList<K>) eq(getFieldStartDate((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> startDateNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldStartDate((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> startDateLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldStartDate((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> startDateNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldStartDate((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> startDateIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldStartDate((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> startDateNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldStartDate((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> startDateBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldStartDate((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowStartDate() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldStartDate((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctStartDate() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldStartDate((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> eta(T val) throws Exception {
		return (PList<K>) eq(getFieldEta((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> etaNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldEta((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> etaLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldEta((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> etaNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldEta((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> etaIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldEta((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> etaNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldEta((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> etaBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldEta((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowEta() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldEta((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctEta() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldEta((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> fascicolo(T val) throws Exception {
		return (PList<K>) eq(getFieldFascicolo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> fascicoloNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldFascicolo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> fascicoloLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldFascicolo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> fascicoloNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldFascicolo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> fascicoloIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldFascicolo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> fascicoloNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldFascicolo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> fascicoloBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldFascicolo((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowFascicolo() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldFascicolo((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctFascicolo() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldFascicolo((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> id(T val) throws Exception {
		return (PList<K>) eq(getFieldId((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> idNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldId((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> idLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldId((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> idNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldId((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> idIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldId((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> idNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldId((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> idBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldId((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowId() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldId((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctId() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldId((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> importo(T val) throws Exception {
		return (PList<K>) eq(getFieldImporto((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> importoNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldImporto((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> importoLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldImporto((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> importoNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldImporto((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> importoIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldImporto((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> importoNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldImporto((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> importoBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldImporto((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowImporto() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldImporto((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctImporto() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldImporto((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> iscritto(T val) throws Exception {
		return (PList<K>) eq(getFieldIscritto((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> iscrittoNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldIscritto((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> iscrittoLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldIscritto((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> iscrittoNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldIscritto((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> iscrittoIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldIscritto((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> iscrittoNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldIscritto((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> iscrittoBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldIscritto((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowIscritto() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldIscritto((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctIscritto() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldIscritto((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> nome(T val) throws Exception {
		return (PList<K>) eq(getFieldNome((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> nomeNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldNome((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> nomeLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldNome((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> nomeNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldNome((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> nomeIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldNome((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> nomeNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldNome((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> nomeBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldNome((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowNome() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldNome((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctNome() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldNome((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> progr(T val) throws Exception {
		return (PList<K>) eq(getFieldProgr((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> progrNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldProgr((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> progrLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldProgr((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> progrNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldProgr((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> progrIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldProgr((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> progrNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldProgr((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> progrBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldProgr((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowProgr() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldProgr((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctProgr() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldProgr((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> stato(T val) throws Exception {
		return (PList<K>) eq(getFieldStato((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> statoNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldStato((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> statoLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldStato((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> statoNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldStato((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> statoIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldStato((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> statoNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldStato((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> statoBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldStato((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowStato() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldStato((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctStato() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldStato((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> stato1(T val) throws Exception {
		return (PList<K>) eq(getFieldStato1((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> stato1Neq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldStato1((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> stato1Like(T val) throws Exception {
		return (PList<K>) likeValues(getFieldStato1((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> stato1NotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldStato1((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> stato1In(T... val) throws Exception {
		return (PList<K>) inVals(getFieldStato1((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> stato1NotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldStato1((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> stato1Between(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldStato1((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowStato1() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldStato1((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctStato1() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldStato1((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> tipo(T val) throws Exception {
		return (PList<K>) eq(getFieldTipo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> tipoNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldTipo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> tipoLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldTipo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> tipoNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldTipo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> tipoIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldTipo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> tipoNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldTipo((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> tipoBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldTipo((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowTipo() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldTipo((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctTipo() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldTipo((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> totale(T val) throws Exception {
		return (PList<K>) eq(getFieldTotale((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> totaleNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldTotale((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> totaleLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldTotale((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> totaleNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldTotale((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> totaleIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldTotale((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> totaleNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldTotale((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> totaleBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldTotale((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowTotale() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldTotale((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctTotale() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldTotale((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> valore(T val) throws Exception {
		return (PList<K>) eq(getFieldValore((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> valoreNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldValore((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> valoreLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldValore((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> valoreNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldValore((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> valoreIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldValore((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> valoreNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldValore((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> valoreBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldValore((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowValore() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldValore((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctValore() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldValore((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> username(T val) throws Exception {
		return (PList<K>) eq(getFieldUsername((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> usernameNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldUsername((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> usernameLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldUsername((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> usernameNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldUsername((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> usernameIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldUsername((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> usernameNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldUsername((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> usernameBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldUsername((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowUsername() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldUsername((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctUsername() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldUsername((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<K> email(T val) throws Exception {
		return (PList<K>) eq(getFieldEmail((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> emailNeq(T val) throws Exception {
		return (PList<K>) neqVals(getFieldEmail((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> emailLike(T val) throws Exception {
		return (PList<K>) likeValues(getFieldEmail((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> emailNotLike(T val) throws Exception {
		return (PList<K>) notLikeValues(getFieldEmail((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> emailIn(T... val) throws Exception {
		return (PList<K>) inVals(getFieldEmail((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> emailNotIn(T... val) throws Exception {
		return (PList<K>) notInVals(getFieldEmail((K) getFirstElement()), val).find();
	}

	public <T, K extends BaseEntity> PList<K> emailBetween(T val1, T val2) throws Exception {
		return (PList<K>) between(getFieldEmail((K) getFirstElement()), val1, val2).find();
	}

	public <T, K extends BaseEntity> PList<T> narrowEmail() throws Exception {
		return (PList<T>) p.toPList(p.narrow(this, getFieldEmail((K) getFirstElement())));
	}

	public <T, K extends BaseEntity> PList<T> narrowDistinctEmail() throws Exception {
		return (PList<T>) p.toPList(p.narrowDistinct(this, getFieldEmail((K) getFirstElement())));
	}

	private String getFieldFlagStato(K k) {
		if (Null(k))
			return null;
		String campo = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.deleteLogic()) {
					campo = att.getName();
					break;
				}
			}
		}
		return campo;
	}

	private Field getFieldFlagStatoField(K k) {
		if (Null(k))
			return null;
		Field attFound = null;
		Field[] attributi = k.getClass().getDeclaredFields();
		for (Field att : attributi) {
			Column annCol = att.getAnnotation(Column.class);
			if (notNull(annCol)) {
				if (annCol.deleteLogic()) {
					attFound = att;
					break;
				}
			}
		}
		return attFound;
	}

	public PList<K> addIfNotNull(K k) throws Exception {
		if (notNull(k))
			add(k);
		return this;
	}

	public PList<K> attivo() throws Exception {
		return (PList<K>) eq(getFieldFlagStato((K) getFirstElement()), Pilot.ATTIVO).find();
	}

	public PList<K> disattivo() throws Exception {
		return (PList<K>) eq(getFieldFlagStato((K) getFirstElement()), Pilot.DISATTIVO).find();
	}

	public K findByPk(String pk) throws Exception {
		return (K) eq("pk", pk).findOne();
	}

	public boolean setAttivo(String pk) throws Exception {
		return setAttivo(findByPk(pk));
	}

	public boolean setDisattivo(String pk) throws Exception {
		return setDisattivo(findByPk(pk));
	}

	public boolean setAttivo(K elem) throws Exception {
		if (Null(getFirstElement()))
			return false;
		if (Null(elem))
			return false;
		invokeSetter(elem, Pilot.ATTIVO, getFieldFlagStatoField((K) getFirstElement()), elem.getClass().getDeclaredMethods());
		return true;
	}

	public boolean setDisattivo(K elem) throws Exception {
		if (Null(getFirstElement()))
			return false;
		if (Null(elem))
			return false;
		invokeSetter(elem, Pilot.DISATTIVO, getFieldFlagStatoField((K) getFirstElement()), elem.getClass().getDeclaredMethods());
		return true;
	}

	public <K extends BaseEntity> PList<K> dataInizio(Date d) throws Exception {
		return (PList<K>) eq(getFieldDataInizio((K) getFirstElement()), d).find();
	}

	public <K extends BaseEntity> PList<K> dataFine(Date d) throws Exception {
		return (PList<K>) eq(getFieldDataFine((K) getFirstElement()), d).find();
	}

	public <T> boolean anyNull(String prop) throws Exception {
		return p.isAnyNullListBeanValues(this, prop);
	}

	public <T> boolean anyNotNull(String prop) throws Exception {
		return p.isAnyNotNullListBeanValues(this, prop);
	}

	public <T> boolean noneNull(String prop) throws Exception {
		return p.isNoneNullListBeanValues(this, prop);
	}

	public <T> boolean noneNotNull(String prop) throws Exception {
		return p.isNoneNotNullListBeanValues(this, prop);
	}

	public boolean allNull(String prop) throws Exception {
		return p.isAllNullListBeanValues(this, prop);
	}

	public boolean allNotNull(String prop) throws Exception {
		return p.isAllNotNullListBeanValues(this, prop);
	}

	public Integer quanti(K elem) {
		Integer i = 0;
		for (K e : this) {
			if (p.is(e, elem))
				i++;
		}
		return i;
	}

	public PList<K> random(Integer quantiCasuali) {
		if (size() < quantiCasuali)
			quantiCasuali = size();
		PList<K> sottolistaCasuale = new PArrayList<K>();
		for (int i = 1; i <= quantiCasuali; i++) {
			int pos = p.generaNumeroCasuale(0, size() - 1);
			K num = get(pos);
			if (sottolistaCasuale.contains(num)) {
				i--;
				continue;
			}
			sottolistaCasuale.add(get(pos));
		}
		return sottolistaCasuale.sort();
	}

	public K randomOne() {
		return random(1).getFirstElement();
	}

	public PList<K> pari() {
		PList<K> out = new PArrayList<K>();
		K el = getFirstElement();
		if (notNull(el)) {
			boolean isInteger = el instanceof Integer;
			boolean isLong = el instanceof Long;
			boolean isShort = el instanceof Short;
			if (p.nessuna(isInteger, isLong, isShort))
				return this;
			for (K e : this) {
				if (isInteger) {
					if ((Integer) e % 2 == 0)
						out.add(e);
				}
				if (isLong) {
					if ((Long) e % 2 == 0)
						out.add(e);
				}
				if (isShort) {
					if ((Short) e % 2 == 0)
						out.add(e);
				}
			}
		}
		return out;
	}

	public PList<K> dispari() {
		PList<K> out = new PArrayList<K>();
		K el = getFirstElement();
		if (notNull(el)) {
			boolean isInteger = el instanceof Integer;
			boolean isLong = el instanceof Long;
			boolean isShort = el instanceof Short;
			if (p.nessuna(isInteger, isLong, isShort))
				return this;
			for (K e : this) {
				if (isInteger) {
					if ((Integer) e % 2 != 0)
						out.add(e);
				}
				if (isLong) {
					if ((Long) e % 2 != 0)
						out.add(e);
				}
				if (isShort) {
					if ((Short) e % 2 != 0)
						out.add(e);
				}
			}
		}
		return out;
	}

	public PList<K> mescola() {
		PList<K> listaMescolata = new PArrayList(this);
		// Utilizza l'algoritmo di Fisher-Yates per mescolare la lista
		Random random = new Random();
		for (int i = listaMescolata.size() - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);
			// Scambia gli elementi in posizione i e j
			K temp = listaMescolata.get(i);
			listaMescolata.set(i, listaMescolata.get(j));
			listaMescolata.set(j, temp);
		}
		return listaMescolata;
	}

}
