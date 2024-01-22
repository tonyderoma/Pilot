package it.eng.pilot;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Interfaccia che definisce i metodi disponibili per la classe PArrayList. I
 * filtri in,notIn,eq,neq,like,notLike,lte,lt,gte,gt non vengono applicati se il
 * valore passato è null
 * 
 * @author Antonio Corinaldi
 * 
 * @param <E>
 */
public interface PList<E> extends List<E> {

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista abbia valore val.
	 * 
	 * @param <T>
	 * @param campo
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> eq(String campo, T val);

	/**
	 * Aggiunge una condizione di filtro di uguaglianza per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> eq(T val);

	/**
	 * Aggiunge la condizione che l'attributo campo di tipo boolean sia true
	 * 
	 * @param campo
	 * @return PList<E>
	 */
	PList<E> trueValue(String campo);

	/**
	 * Aggiunge la condizione che l'attributo campo di tipo boolean sia false
	 * 
	 * @param campo
	 * @return PList<E>
	 */
	PList<E> falseValue(String campo);

	/**
	 * Metodo alias di falseValue
	 * 
	 * @param campo
	 * @return PList<E>
	 */
	PList<E> isFalse(String campo);

	/**
	 * Metodo alias di trueValue
	 * 
	 * @param campo
	 * @return PList<E>
	 */
	PList<E> isTrue(String campo);

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista abbia come valore almeno uno dei valori della lista
	 * val.
	 * 
	 * @param campo
	 * @param val
	 * @return PList<E>
	 */

	<T> PList<E> in(String campo, List<T> val);

	/**
	 * Aggiunge una condizione di filtro di insieme IN per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> in(List<T> val);

	/**
	 * Aggiunge una condizione di filtro di insieme IN per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> in(T... val);

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista abbia come valore almeno uno dei valori della lista
	 * val.
	 * 
	 * @param campo
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> inVals(String campo, T... val);

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista abbia come valore nessuno dei valori della lista
	 * val.
	 * 
	 * @param campo
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> notIn(String campo, List<T> val);

	/**
	 * Aggiunge una condizione di filtro di insieme NOT IN per liste di tipi
	 * Java (Integer,String,.....)
	 * 
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> notIn(List<T> val);

	/**
	 * Aggiunge una condizione di filtro di insieme NOT IN per liste di tipi
	 * Java (Integer,String,.....)
	 * 
	 * @param <T>
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> notIn(T... val);

	/**
	 * Aggiunge una condizione di filtro di insieme NOT IN per liste di tipi
	 * custom
	 * 
	 * @param <T>
	 * @param campo
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> notInVals(String campo, T... val);

	/**
	 * 
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista abbia come valore un elemento che sia like il valore
	 * vals.
	 * 
	 * 
	 * @param campo
	 * @param vals
	 * @return PList<E>
	 */

	<T> PList<E> likeValues(String campo, T... vals);

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista abbia come valore un elemento che sia not like il
	 * valore vals
	 * 
	 * @param campo
	 * @param vals
	 * @return PList<E>
	 */
	<T> PList<E> notLikeValues(String campo, T... vals);

	/**
	 * Aggiunge una condizione di filtro LIKE per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @param vals
	 * @return PList<E>
	 */
	<T> PList<E> like(T... vals);

	/**
	 * Aggiunge una condizione di filtro NOT LIKE per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @param vals
	 * @return PList<E>
	 */
	<T> PList<E> notLike(T... vals);

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista non abbia valore val
	 * 
	 * @param campo
	 * @param vals
	 * @return PList<E>
	 */
	<T> PList<E> neqVals(String campo, T... vals);

	/**
	 * Aggiunge una condizione di filtro <> (not equal) per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @param vals
	 * @return PList<E>
	 */
	<T> PList<E> neq(T... vals);

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista abbia un valore compreso tra val1 e val2
	 * 
	 * @param campo
	 * @param val1
	 * @param val2
	 * @return PList<E>
	 */
	<T> PList<E> between(String campo, T val1, T val2);

	/**
	 * Aggiunge una condizione di filtro BETWEEN per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @param val1
	 * @param val2
	 * @return PList<E>
	 */
	<T> PList<E> between(T val1, T val2);

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista abbia un valore maggiore di val
	 * 
	 * @param campo
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> gt(String campo, T val);

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista abbia un valore maggiore o uguale a val
	 * 
	 * @param campo
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> gte(String campo, T val);

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista abbia un valore minore di val
	 * 
	 * @param campo
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> lt(String campo, T val);

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista abbia un valore minore o uguale a val
	 * 
	 * @param campo
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> lte(String campo, T val);

	/**
	 * Aggiunge una condizione di filtro > per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> gt(T val);

	/**
	 * Aggiunge una condizione di filtro >= per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> gte(T val);

	/**
	 * Aggiunge una condizione di filtro < per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> lt(T val);

	/**
	 * Aggiunge una condizione di filtro <= per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @param val
	 * @return PList<E>
	 */
	<T> PList<E> lte(T val);

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista sia NULL
	 * 
	 * 
	 * @param campo
	 * @return PList<E>
	 */
	PList<E> isNull(String campo);

	/**
	 * Aggiunge una condizione di filtro che la proprietà campo del bean
	 * elemento della lista sia NOT NULL
	 * 
	 * 
	 * @param campo
	 * @return PList<E>
	 */
	PList<E> isNotNull(String campo);

	/**
	 * Esegue la ricerca sulla lista PList in base alle condizioni di filtro
	 * impostate e torna una nuova lista contenente i soli elementi che
	 * soddisfano le condizioni di filtro impostate
	 * 
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> find() throws Exception;

	/**
	 * Esegue la ricerca sulla lista PList in base alle condizioni di filtro
	 * impostate e torna l'unico elemento della lista che soddisfa le condizioni
	 * di filtro impostate
	 * 
	 * @return E
	 * @throws Exception
	 */
	E findOne() throws Exception;

	/**
	 * Svuota i filtri di ricerca impostati
	 * 
	 */
	void cleanFilters();

	/**
	 * Ritorna l'elemento della PList che si trova in posizione i all'interno
	 * della lista
	 * 
	 * @param i
	 * @return E
	 */
	E get(Integer i);

	/**
	 * Data una PList di bean custom,ritorna una PList di oggetti corrispondenti
	 * alla variabile istanza props
	 * 
	 * @param props
	 * @param <T>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T> PList<T> narrow(String props) throws Exception;

	/**
	 * Data una PList di bean custom,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza props
	 * 
	 * @param props
	 * @param <T>
	 * @return PList<T>s
	 * @throws Exception
	 */
	<T> PList<T> narrowDistinct(String props) throws Exception;

	/**
	 * Elimina dalla PList tutti gli oggetti che estendono l'interfaccia
	 * Validator e che non soddisfano il metodo validate()
	 * 
	 * @return PList<E>
	 */
	PList<E> cleanByValidator();

	/**
	 * 
	 * Aggiunge l'elemento t alla PList. Torna true se non é ancora stato
	 * raggiunto il limite impostato, false altrimenti
	 */
	boolean addElement(E k);

	/**
	 * Esegue un filtro del tipo columnStartDate<=d<=columnEndDate
	 * columnStartDate è la variabile istanza che ha @Column( startDate=true)
	 * columnEndDate è la variabile istanza che ha @Column( endDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> betweenStartAndEndDate(Date d) throws Exception;

	/**
	 * Esegue un filtro del tipo columnStartDate<=sysdate<=columnEndDate
	 * 
	 * columnStartDate è la variabile istanza che ha @Column( startDate=true)
	 * columnEndDate è la variabile istanza che ha @Column( endDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> betweenNow() throws Exception;

	/**
	 * Esegue un filtro del tipo columnStartDate<d
	 * 
	 * columnStartDate è la variabile istanza che ha @Column( startDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> startBefore(Date d) throws Exception;

	/**
	 * Esegue un filtro del tipo columnStartDate<sysdate
	 * 
	 * columnStartDate è la variabile istanza che ha @Column( startDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> startBeforeNow() throws Exception;

	/**
	 * Esegue un filtro del tipo columnStartDate<=sysdate
	 * 
	 * columnStartDate è la variabile istanza che ha @Column( startDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> startBeforeEqualNow() throws Exception;

	/**
	 * Esegue un filtro del tipo columnStartDate>d
	 * 
	 * columnStartDate è la variabile istanza che ha @Column( startDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> startAfter(Date d) throws Exception;

	/**
	 * Esegue un filtro del tipo columnStartDate>sysdate
	 * 
	 * columnStartDate è la variabile istanza che ha @Column( startDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> startAfterNow() throws Exception;

	/**
	 * Esegue un filtro del tipo columnStartDate>=sysdate
	 * 
	 * columnStartDate è la variabile istanza che ha @Column( startDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> startAfterEqualNow() throws Exception;

	/**
	 * Esegue un filtro del tipo columnEndDate<d
	 * 
	 * columnEndDate è la variabile istanza che ha @Column( endDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> endBefore(Date d) throws Exception;

	/**
	 * Esegue un filtro del tipo columnEndDate<sysdate
	 * 
	 * columnEndDate è la variabile istanza che ha @Column( endDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> endBeforeNow() throws Exception;

	/**
	 * Esegue un filtro del tipo columnEndDate<=sysdate
	 * 
	 * columnEndDate è la variabile istanza che ha @Column( endDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> endBeforeEqualNow() throws Exception;

	/**
	 * Esegue un filtro del tipo columnEndDate>d
	 * 
	 * columnEndDate è la variabile istanza che ha @Column( endDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> endAfter(Date d) throws Exception;

	/**
	 * Esegue un filtro del tipo columnEndDate>sysdate
	 * 
	 * columnEndDate è la variabile istanza che ha @Column( endDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> endAfterNow() throws Exception;

	/**
	 * Esegue un filtro del tipo columnEndDate>=sysdate
	 * 
	 * columnEndDate è la variabile istanza che ha @Column( endDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */

	<K extends BaseEntity> PList<K> endAfterEqualNow() throws Exception;

	/**
	 * Esegue un filtro sulla colonna *COD_UTENTE=user
	 * 
	 * @param <K>
	 * @param user
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> byUser(String user) throws Exception;

	/**
	 * Esegue un filtro sulla colonna *COD_APPL=app
	 * 
	 * @param <K>
	 * @param app
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> byApp(String app) throws Exception;

	/**
	 * Esegue un filtro che dataAggiornamento>=d
	 * 
	 * @param <K>
	 * @param d
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> modifiedSince(Date d) throws Exception;

	/**
	 * Esegue un filtro che dataAggiornamento in [start,end]
	 * 
	 * @param <K>
	 * @param start
	 * @param end
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> modifiedBetween(Date start, Date end) throws Exception;

	/**
	 * Esegue un filtro su dataAggiornamento >= sysdate-1 anno, ossia filtra i
	 * soli record aggiornati nell'ultimo anno
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> lastYear() throws Exception;

	/**
	 * Esegue un filtro su dataAggiornamento >= sysdate-1 mese, ossia filtra i
	 * soli record aggiornati nell'ultimo mese
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> lastMonth() throws Exception;

	/**
	 * Esegue un filtro su dataAggiornamento >= sysdate-1 settimana, ossia
	 * filtra i soli record aggiornati nell'ultima settimana
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> lastWeek() throws Exception;

	/**
	 * Esegue un filtro su dataAggiornamento >= sysdate-1 giorno, ossia filtra i
	 * soli record aggiornati nell'ultimo giorno
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> lastDay() throws Exception;

	/**
	 * Esegue un filtro su dataAggiornamento >= sysdate-1 ora, ossia filtra i
	 * soli record aggiornati nell'ultima ora
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> lastHour() throws Exception;

	/**
	 * Esegue un filtro su dataAggiornamento >= sysdate-30 minuti, ossia filtra
	 * i soli record aggiornati nell'ultima mezz'ora
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> last30Minutes() throws Exception;

	/**
	 * Esegue un filtro su dataAggiornamento >= sysdate-15 minuti, ossia filtra
	 * i soli record aggiornati nell'ultimo quarto d'ora
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> last15Minutes() throws Exception;

	/**
	 * Esegue un filtro su dataAggiornamento >= sysdate-10 minuti, ossia filtra
	 * i soli record aggiornati negli ultimi 10 minuti
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> last10Minutes() throws Exception;

	/**
	 * Esegue un filtro su dataAggiornamento >= sysdate-5 minuti, ossia filtra i
	 * soli record aggiornati negli ultimi 5 minuti
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> last5Minutes() throws Exception;

	/**
	 * Data una lista di elementi di tipo K raggruppo la lista secondo la
	 * proprieta campo del bean K e ottengo una Mappa chiave-valore dove chiave
	 * è il valore della proprieta e valore è una lista di bean K la cui
	 * proprieta campo ha quel valore.
	 * 
	 * @param <T>
	 * @param campo
	 * @return Map<T, PList<E>>
	 * @throws Exception
	 */
	<T> PMap<T, PList<E>> groupBy(String campo) throws Exception;

	/**
	 * Data una lista di elementi di tipo K raggruppo la lista secondo la
	 * proprieta campo del bean K e ottengo una Mappa chiave-valore dove chiave
	 * è il valore della proprieta e valore è una lista di bean K la cui
	 * proprieta campo ha quel valore. La lista di ogni key viene poi tagliata
	 * ai primi n elementi.
	 * 
	 * @param <T>
	 * @param campo
	 * @return Map<T, PList<E>>
	 * @throws Exception
	 */
	<T> PMap<T, PList<E>> groupBy(String campo, int n) throws Exception;

	/**
	 * Esegue l'ordinamento della lista in base al metodo equals
	 * 
	 * @return PList<E>
	 */
	PList<E> sort();

	/**
	 * Esegue un ordinamento della lista di bean sui campi indicati in ordine
	 * ascendente
	 * 
	 * @param campi
	 * @return PList<E>
	 */
	PList<E> sort(String... campi);

	/**
	 * Ritorna l'ultimo elemento della lista
	 * 
	 * @return E
	 */
	E getLastElement();

	/**
	 * Ritorna il primo elemento della lista
	 * 
	 * @return E
	 */
	E getFirstElement();

	/**
	 * Inverte l'ordine della lista
	 * 
	 * @return PList<E>
	 */
	PList<E> inverti();

	/**
	 * Data una lista torna una nuova lista da cui ha eliminato gli elementi
	 * duplicati secondo il metodo equals
	 * 
	 * @return PList<E>
	 */
	PList<E> removeDuplicates();

	/**
	 * Esegue un ordinamento della lista di bean sui campi indicati in ordine
	 * discendente
	 * 
	 * @param campi
	 * @return PList<E>
	 */
	PList<E> sortDesc(String... campi);

	/**
	 * Esegue un ordinamento della list in ordine discendente secondo il metodo
	 * equals
	 * 
	 * @return PList<E>
	 */
	PList<E> sortDesc();

	/**
	 * Date n liste come parametro, somma alla lista attuale le n liste e torna
	 * la lista risultato
	 * 
	 * @param l
	 * @return PList<E>
	 */
	PList<E> aggiungiList(PList<E>... l);

	/**
	 * Date n liste, torna una nuova lista che contiene gli elementi della lista
	 * non presenti nelle altre liste in base al metodo equals
	 * 
	 * @param liste
	 * @return PList<E>
	 */
	PList<E> sottraiList(PList<E>... liste);

	/**
	 * Date n liste, torna una nuova lista di elementi comuni tra la lista e le
	 * n liste in base al metodo equals
	 * 
	 * @param liste
	 * @return PList<E>
	 */
	PList<E> intersection(PList<E>... liste);

	/**
	 * Rimuove gli elementi null dalla lista
	 * 
	 * @return boolean
	 */
	boolean cleanNull();

	/**
	 * Dato valore numerico positivo pageSize, ritorna una PList di Plist dove
	 * ogni lista ha al massimo pageSize elementi
	 * 
	 * @param pageSize
	 * @return PList<PList<E>>
	 */
	PList<PList<E>> listPagination(Integer pageSize);

	/**
	 * Data una PList aggiunge la PList a sè stessa per times volte
	 * 
	 * @param times
	 * @return PList<E>
	 */
	PList<E> selfExtend(Integer times);

	/**
	 * Ritorna una nuova lista contenente solo i primi n elementi
	 * 
	 * @param n
	 * @return PList<E>
	 */
	PList<E> cutToFirst(Integer n);

	/**
	 * Ritorna una nuova lista contenente solo gli ultimi n elementi
	 * 
	 * @param n
	 * @return PList<E>
	 */
	PList<E> cutToLast(Integer n);

	/**
	 * Ritorna una nuova PList di elementi distinti di tipo T dove T è il tipo
	 * della proprieta campo
	 * 
	 * @param <T>
	 * @param campo
	 * @return PList<T>
	 */
	<T> PList<T> distinct(String campo);

	/**
	 * Ritorna la sommatoria dei valori della proprietà campo (di tipo numerico)
	 * di tutti gli elementi della PList
	 * 
	 * @param <T>
	 * @param campo
	 * @param c
	 * @return T
	 * @throws Exception
	 */
	<T> T sommatoria(String campo, Class<T> c) throws Exception;

	/**
	 * Data una lista di tipi java calcolo la sommatoria dei valori di tutti gli
	 * elementi della lista
	 * 
	 * @param <T>
	 * @param c
	 * @return T
	 * @throws Exception
	 */
	<T> T sommatoria(Class<T> c) throws Exception;

	/**
	 * Ritorna true se ha più di un elemento
	 * 
	 * @return boolean
	 */
	boolean moreThanOne();

	/**
	 * Ritorna true se ha almeno un elemento
	 * 
	 * @return boolean
	 */
	boolean atLeastOne();

	/**
	 * Ritorna true se ha almeno i elementi
	 * 
	 * @param i
	 * @return boolean
	 */
	boolean atLeast(Integer i);

	/**
	 * Ritorna true se ha più di i elementi
	 * 
	 * @param i
	 * @return boolean
	 */
	boolean moreThan(Integer i);

	/**
	 * Ritorna true se la proprieta prop dei bean della lista ha il valore value
	 * per tutti gli elementi della lista
	 * 
	 * @param <T>
	 * @param prop
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	<T> boolean isAllListValues(String prop, T value) throws Exception;

	/**
	 * Ritorna true se la lista ha tutti i suoi elementi con valore value
	 * 
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	boolean isAllListValues(E value) throws Exception;

	/**
	 * Torna l'elemento della lista che ha il valore massimo delle proprietà
	 * prop
	 * 
	 * @param prop
	 * @return E
	 * @throws Exception
	 */
	E max(String... prop) throws Exception;

	/**
	 * Torna l'elemento della lista che ha il valore minimo delle proprietà prop
	 * 
	 * @param prop
	 * @return E
	 * @throws Exception
	 */
	E min(String... prop) throws Exception;

	/**
	 * Ritorna il primo elemento della lista che ha il valore massimo della
	 * proprieta prop
	 * 
	 * @param prop
	 * @return E
	 * @throws Exception
	 */
	E getFirstElementMaxValue(String prop) throws Exception;

	/**
	 * Ritorna il primo elemento della lista che ha il valore minimo della
	 * proprieta prop
	 * 
	 * @param prop
	 * @return E
	 * @throws Exception
	 */
	E getFirstElementMinValue(String prop) throws Exception;

	/**
	 * Ritorna l'ultimo elemento della lista che ha il valore massimo della
	 * proprieta prop
	 * 
	 * @param prop
	 * @return E
	 * @throws Exception
	 */
	E getLastElementMaxValue(String prop) throws Exception;

	/**
	 * Ritorna l'ultimo elemento della lista che ha il valore minimo della
	 * proprieta prop
	 * 
	 * @param prop
	 * @return E
	 * @throws Exception
	 */
	E getLastElementMinValue(String prop) throws Exception;

	/**
	 * Imposta il limite massimo di elementi che può contenere la PList. Vanno
	 * aggiunti tramite addElement
	 * 
	 * @param limit
	 */
	PList<E> setLimit(Integer limit);

	/**
	 * Ritorna l'elemento massimo della PList non bean
	 * 
	 * @return E
	 */
	E max();

	/**
	 * Ritorna l'elemento minimo della PList non bean
	 * 
	 * @return E
	 */
	E min();

	/**
	 * Ritorna una nuova lista risultato dell'operazione di masking secondo i
	 * campi indicati,di tutti gli elementi della lista originaria
	 * 
	 * @param campi
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> mask(String... campi) throws Exception;

	/**
	 * Esegue la conversione di una lista di elementi di tipo K extends
	 * BaseEntity in una nuova lista di elementi di tipo T seguendo la logica di
	 * conversione del metodo convert(Class<K> c, PList<T> entities) di
	 * BaseDaoEntity
	 * 
	 * 
	 * 
	 * @param <T>
	 * @param <K>
	 * @param typeToConvert
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> convert(Class<T> typeToConvert) throws Exception;

	/**
	 * Aggiorna valueToUpdate al valore newValue
	 * 
	 * @param valueToUpdate
	 * @param newValue
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> aggiornaListValue(E valueToUpdate, E newValue) throws Exception;

	/**
	 * Data una lista di tipi ritorna il numero di occorrenze dell'elemento e
	 * nella lista in base al metodo equals
	 * 
	 * @param e
	 * @return int
	 */
	int countInList(E e);

	/**
	 * Data una lista di bean, torna il numero di bean della lista che hanno la
	 * proprieta prop valorizzata con val
	 * 
	 * @param <T>
	 * @param prop
	 * @param val
	 * @return int
	 * @throws Exception
	 */
	<T> int countInList(String prop, T val) throws Exception;

	/**
	 * Ritorna un List<E> a partire da PList<E> in modo da ritornare alle lista
	 * java standard
	 * 
	 * @return List<E>
	 */
	List<E> toArrayList();

	/**
	 * Serve a recuperare i valori distinti multicampo tramite campi... indicati
	 * come parametro. Ritorna una PList<String> dove ogni elemento è nel
	 * formato [nomeCampo]=[valoreCampo
	 * ]|[nomeCampo_1]=[valoreCampo_1]|...|[nomeCampo_n]=[valoreCampo_n]
	 * 
	 * Campi è l'elenco dei campi del tipo E di cui si vogliono i valori
	 * distinti concatenati insieme in una multipla. Equivale in pratica a una
	 * select distinct sql
	 * 
	 * @param campi
	 * @return PList<String>
	 */
	PList<String> distinctMulti(String... campi);

	/**
	 * Data una lista di bean calcolo la media aritmetica dei valori della
	 * proprietà campo (di tipo numerico) di tutti gli elementi della lista
	 * 
	 * @param <T>
	 * @param campo
	 * @param c
	 * @return T
	 * @throws Exception
	 */
	<T> T media(String campo, Class<T> c) throws Exception;

	/**
	 * Data una lista di tipi java calcolo la media aritmetica(di tipo numerico)
	 * di tutti gli elementi della lista
	 * 
	 * @param <T>
	 * @param c
	 * @return T
	 * @throws Exception
	 */
	<T> T media(Class<T> c) throws Exception;

	/**
	 * Salta i primi n elementi ritorna la lista senza i primi n elementi
	 * 
	 * @param n
	 * @return PList<E>
	 */
	PList<E> skip(Integer n);

	/**
	 * Ritorna la lista saltando il primo elemento rimuovendolo
	 * 
	 * @return PList<EK>
	 */
	PList<E> skipFirst();

	/**
	 * Torna true se la lista ha ogni elemento con valore value
	 * 
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	boolean allMatch(E value) throws Exception;

	/**
	 * Torna true se la lista ha almeno un elemento con valore value
	 * 
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	boolean anyMatch(E value) throws Exception;

	/**
	 * Torna true se la lista non ha alcun elemento con valore value
	 * 
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	boolean noneMatch(E value) throws Exception;

	/**
	 * Torna true se la lista non ha alcun elemento con valore value della
	 * proprieta prop
	 * 
	 * @param <T>
	 * @param prop
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	<T> boolean noneMatch(String prop, T value) throws Exception;

	/**
	 * Torna true se la lista ha tutti gli elementi con valore value della
	 * proprieta prop
	 * 
	 * @param <T>
	 * @param prop
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	<T> boolean allMatch(String prop, T value) throws Exception;

	/**
	 * Torna true se la lista ha almeno un elemento con valore value della
	 * proprieta prop
	 * 
	 * @param <T>
	 * @param prop
	 * @param value
	 * @return boolean
	 * @throws Exception
	 */
	<T> boolean anyMatch(String prop, T value) throws Exception;

	/**
	 * Esegue un filtro del tipo columnEndDate>=d
	 * 
	 * columnEndDate è la variabile istanza che ha @Column( endDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> endAfterEqual(Date d) throws Exception;

	/**
	 * Esegue un filtro del tipo columnStartDate>=d
	 * 
	 * columnStartDate è la variabile istanza che ha @Column( startDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> startAfterEqual(Date d) throws Exception;

	/**
	 * Esegue un filtro del tipo columnEndDate<=d
	 * 
	 * columnEndDate è la variabile istanza che ha @Column( endDate=true)
	 * 
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> endBeforeEqual(Date d) throws Exception;

	/**
	 * Esegue un filtro del tipo columnStartDate<=d
	 * 
	 * columnStartDate è la variabile istanza che ha @Column( startDate=true)
	 * 
	 * @param <K>
	 * @param d
	 * @return PList<K>
	 * @throws Exception
	 */
	<K extends BaseEntity> PList<K> startBeforeEqual(Date d) throws Exception;

	/**
	 * Ritorna una mappa a due chiavi true/false, dove a true associa la lista
	 * di elementi che soddisfano la condizione di test definita all'interno del
	 * metodo accessor "is/get campo" della classe E e a false associa la lista
	 * di elementi che non soddisfano la condizione di test del metodo accessor
	 * 
	 * @param campo
	 * @return Map<Boolean, PList<E>>
	 */
	Map<Boolean, PList<E>> splitBy(String campo) throws Exception;

	/**
	 * Elimina gli ultimi n elementi dalla lista
	 * 
	 * @param n
	 * @return PList<E>
	 */
	PList<E> drop(Integer n);

	/**
	 * Elimina l'ultimo elemento della lista
	 * 
	 * @return PList<E>
	 */
	PList<E> dropLast();

	/**
	 * Ritorna la lista da cui ha rimosso gli elementi uguali a value
	 * 
	 * @param value
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> cleanList(E value) throws Exception;

	/**
	 * Ritorna la lista da cui ha rimosso gli elementi uguali ai valori multipli
	 * value
	 * 
	 * @param value
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> cleanList(E... value) throws Exception;

	/**
	 * Ritorna la lista da cui ha rimosso gli elementi bean la cui proprietà
	 * prop è uguale a value
	 * 
	 * @param prop
	 * @param val
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> cleanListBean(String prop, Object val) throws Exception;

	/**
	 * Ritorna la lista da cui ha rimosso gli elementi bean le cui proprietà
	 * props sono uguali ai corrispondenti valori value
	 * 
	 * @param props
	 * @param val
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> cleanListBean(String[] props, Object[] val) throws Exception;

	/**
	 * Ritorna la lista da cui ha rimosso gli elementi che sono like value
	 * 
	 * @param value
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> cleanListLike(E value) throws Exception;

	/**
	 * Ritorna la lista da cui ha rimosso gli elementi che sono like i valori
	 * multipli value
	 * 
	 * @param value
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> cleanListLike(E... value) throws Exception;

	/**
	 * Ritorna la lista da cui ha rimosso gli elementi bean la cui proprietà
	 * prop è like value
	 * 
	 * @param prop
	 * @param val
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> cleanListBeanLike(String prop, E val) throws Exception;

	/**
	 * Ritorna la lista da cui ha rimosso gli elementi bean le cui proprietà
	 * props sono like i valori multipli value
	 * 
	 * @param props
	 * @param val
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> cleanListBeanLike(String[] props, E[] val) throws Exception;

	/**
	 * Ritorna la lista da cui ho rimosso i bean la cui proprietà prop non è
	 * uguale a val
	 * 
	 * @param prop
	 * @param val
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> cleanListBeanNotEqual(String prop, Object val) throws Exception;

	/**
	 * Ritorna la lista da cui ho rimosso i bean le cui proprietà props non sono
	 * uguali ai corrispondenti valori val
	 * 
	 * @param props
	 * @param val
	 * @return PList<E>
	 * @throws Exception
	 */

	PList<E> cleanListBeanNotEqual(String[] props, Object[] val) throws Exception;

	/**
	 * RItorna la lista da cui ho rimosso tutti i valori che non sono uguali a
	 * value
	 * 
	 * @param value
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> cleanListNotEqual(E value) throws Exception;

	/**
	 * Esegue l'iterazione identificata da name, eseguendo il metodo del bean K
	 * della lista che è annotato con @Logic e il cui attributo name è
	 * identificato dal parametro name
	 * 
	 * @param name
	 * @param args
	 * @return PList<E>
	 */
	PList<E> forEach(String name, Object... args);

	/**
	 * Esegue la conversione di tipo identificata da name, eseguendo il metodo
	 * del bean K della lista che è annotato con @Mapping e il cui attributo
	 * name è identificato dal parametro name
	 * 
	 * @param <T>
	 * @param name
	 * @param args
	 * @param tipo
	 * @return PList<T>
	 */
	<T> PList<T> map(String name, Class<T> tipo, Object... args);

	/**
	 * Esegue la conversione di tipo identificata da name, eseguendo il metodo
	 * del bean K della lista che è annotato con @Mapping e il cui attributo
	 * name è identificato dal parametro name
	 * 
	 * @param <T>
	 * @param name
	 * @param args
	 * @return PList<T>
	 */
	<T> PList<T> map(String name, Object... args);

	/**
	 * Ritorna il primo elemento non nullo della lista
	 * 
	 * @return E
	 */
	E getFirstNotNullElement();

	/**
	 * Elimina dalla lista tutti gli elementi che soddisfano la condizione
	 * booleana indicata dal metodo accessor is[campo] del bean E
	 * 
	 * @param campo
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> removeIf(String campo) throws Exception;

	/**
	 * Aggiunge alla lista l'elemento E solo se soddisfa la condizione booleana
	 * indicata dal metodo accessor is[campo] del bean E. L'aggiunta
	 * dell'elemento avviene fino al limite di limit elementi stabilito nella
	 * lista se la lista è limitata a un numero massimo di elementi
	 * 
	 * @param campo
	 * @param e
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> addElementIf(E e, String campo) throws Exception;

	/**
	 * Aggiunge alla lista l'elemento E solo se soddisfa la condizione booleana
	 * indicata dal metodo accessor is[campo] del bean E.
	 * 
	 * @param e
	 * @param campo
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> addIf(E e, String campo) throws Exception;

	/**
	 * Aggiunge alla lista gli elementi della lista passata che soddisfano la
	 * condizione booleana indicata dal metodo accessor is[campo] del bean E. *
	 * 
	 * @param lista
	 * @param campo
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> addAllIf(PList<E> lista, String campo) throws Exception;

	/**
	 * Alias di addAllIf
	 * 
	 * @param lista
	 * @param campo
	 * @return PList<E>
	 * @throws Exception
	 */
	PList<E> aggiungiListaIf(PList<E> lista, String campo) throws Exception;

	/**
	 * Esegue la groupBy multi campo sulla lista
	 * 
	 * @param fields
	 * @return Map<PList<Object>, PList<E>>
	 */
	Map<PList<Object>, PList<E>> groupByFields(String... fields);

	/**
	 * Esegue la concatenazione dei valori della lista separandoli con il
	 * carattere sep
	 * 
	 * @param sep
	 * @return String
	 */
	String concatena(String sep);

	/**
	 * Esegue il collapse di una PList generando un singolo oggetto i cui valori
	 * delle variabili istanza numeriche sono la sommatoria dei singoli valori e
	 * i cui valori di variabili istanza di tipo stringa sono la concatenazione
	 * dei singoli valori con il carattere | come separatore.
	 * 
	 * @return E
	 */
	E collapse();

	/**
	 * Torna true se la lista ha solo un elemento
	 * 
	 * @return boolean
	 */
	boolean onlyOne();

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(cassa=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowCassa() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(progr=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowProgr() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(ente=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowEnte() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(sede=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowSede() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(fascicolo=true)
	 * 
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowFascicolo() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(tipo=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowTipo() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(codice=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowCodice() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(stato=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowStato() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(stato1=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowStato1() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(descr=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDescrizione() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(startDate=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowStartDate() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(endDate=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowEndDate() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(iscritto=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowIscritto() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(cassa=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctCassa() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(progr=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctProgr() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(ente=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctEnte() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(sede=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctSede() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(fascicolo=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctFascicolo() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(tipo=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctTipo() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(codice=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctCodice() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(stato=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctStato() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(stato1=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctStato1() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(descr=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctDescrizione() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(startDate=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctStartDate() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(endDate=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctEndDate() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(iscritto=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctIscritto() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(id=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctId() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(id=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowId() throws Exception;

	/**
	 * Da una stringa numerica ritorna una stringa di stringhe corrispondenti ai
	 * valori numerici
	 * 
	 * @param <K>
	 * @return PList<String>
	 */
	<K extends Number> PList<String> toListString();

	/**
	 * Da una lista di stringhe numeriche ritorna una stringa di Double
	 * 
	 * @return PList<Double>
	 */
	PList<Double> toListDouble();

	/**
	 * Da una lista di stringhe numeriche ritorna una stringa di Integer
	 * 
	 * @return PList<Integer>
	 */
	PList<Integer> toListInteger();

	/**
	 * Da una lista di stringhe numeriche ritorna una stringa di Long
	 * 
	 * @return PList<Long>
	 */
	PList<Long> toListLong();

	/**
	 * Da una lista di stringhe numeriche ritorna una stringa di Float
	 * 
	 * @return PList<Float>
	 */
	PList<Float> toListFloat();

	/**
	 * Da una lista di stringhe numeriche ritorna una stringa di BigDecimal
	 * 
	 * @return PList<BigDecimal>
	 */
	PList<BigDecimal> toListBigDecimal();

	/**
	 * Scrive nel file individuato da path, il contenuto della PList<String>
	 * 
	 * @param path
	 */
	void toFile(String path);

	/**
	 * Dato un file identificato da path, ritorna una PList<String> che contiene
	 * tutte le righe del file
	 * 
	 * @param path
	 * @return PList<String>
	 */
	PList<String> fromFile(String path);

	/**
	 * Data una PList<String> torna una nuova PList<String> i cui elementi sono
	 * trimmati;
	 * 
	 * @return PList<String>
	 */
	PList<String> trim();

	/**
	 * Aggiunge una condizione di filtro NOTNULL per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @return PList<E>
	 */
	PList<E> isNotNull();

	/**
	 * Aggiunge una condizione di filtro NULL per liste di tipi Java
	 * (Integer,String,.....)
	 * 
	 * @return PList<E>
	 */
	PList<E> isNull();

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(nome=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctNome() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(nome=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowNome() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(cognome=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctCognome() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(cognome=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowCognome() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(eta=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctEta() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(eta=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowEta() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(cf=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctCf() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(cf=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowCf() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(importo=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctImporto() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(importo=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowImporto() throws Exception;

	/**
	 * Esegue un filtro sulla colonna citta (@Column(citta=true)). Colonna
	 * citta=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> citta(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna citta (@Column(citta=true)). Colonna citta
	 * like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cittaLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna citta (@Column(citta=true)). Colonna citta
	 * notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cittaNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna citta (@Column(citta=true)). Colonna citta
	 * <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cittaNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna citta (@Column(citta=true)). Colonna citta
	 * in (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cittaIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna citta (@Column(citta=true)). Colonna citta
	 * not in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cittaNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna citta (@Column(citta=true)). Colonna citta
	 * between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cittaBetween(T val1, T val2) throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(citta=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowCitta() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(citta=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctCitta() throws Exception;

	/**
	 * Esegue un filtro sulla colonna citta (@Column(titolo=true)). Colonna
	 * titolo=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> titolo(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna titolo (@Column(titolo=true)). Colonna
	 * titolo like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> titoloLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna titolo (@Column(titolo=true)). Colonna
	 * titolo notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> titoloNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna titolo (@Column(titolo=true)). Colonna
	 * titolo <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> titoloNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna titolo (@Column(titolo=true)). Colonna
	 * titolo in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> titoloIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna titolo (@Column(titolo=true)). Colonna
	 * titolo not in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> titoloNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna titolo (@Column(titolo=true)). Colonna
	 * titolo between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> titoloBetween(T val1, T val2) throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(titolo=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowTitolo() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(titolo=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctTitolo() throws Exception;

	/**
	 * Esegue un filtro sulla colonna indirizzo (@Column(indirizzo=true)).
	 * Colonna indirizzo=val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> indirizzo(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna indirizzo (@Column(indirizzo=true)).
	 * Colonna indirizzo like val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> indirizzoLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna indirizzo (@Column(indirizzo=true)).
	 * Colonna indirizzo notLike val. Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> indirizzoNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna indirizzo (@Column(indirizzo=true)).
	 * Colonna indirizzo <>val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> indirizzoNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna indirizzo (@Column(indirizzo=true)).
	 * Colonna indirizzo in (val1,val2,....,val n). Non serve invocare il find()
	 * in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> indirizzoIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna indirizzo (@Column(indirizzo=true)).
	 * Colonna indirizzo not in (val1,val2,....,val n). Non serve invocare il
	 * find() in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> indirizzoNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna indirizzo (@Column(indirizzo=true)).
	 * Colonna indirizzo between (val1,val2). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> indirizzoBetween(T val1, T val2) throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(indirizzo=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowIndirizzo() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(indirizzo=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctIndirizzo() throws Exception;

	/**
	 * Esegue un filtro sulla colonna piva (@Column(piva=true)). Colonna
	 * piva=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> piva(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna piva (@Column(piva=true)). Colonna piva
	 * like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> pivaLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna piva (@Column(piva=true)). Colonna piva
	 * notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> pivaNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna piva (@Column(piva=true)). Colonna piva
	 * <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> pivaNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna piva (@Column(piva=true)). Colonna piva in
	 * (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> pivaIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna piva (@Column(piva=true)). Colonna piva
	 * not in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> pivaNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna piva (@Column(piva=true)). Colonna piva
	 * between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> pivaBetween(T val1, T val2) throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(piva=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowPiva() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(piva=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctPiva() throws Exception;

	/**
	 * Esegue un filtro sulla colonna nazione (@Column(nazione=true)). Colonna
	 * nazione=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nazione(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nazione (@Column(nazione=true)). Colonna
	 * nazione like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nazioneLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nazione (@Column(nazione=true)). Colonna
	 * nazione notLike val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nazioneNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nazione (@Column(nazione=true)). Colonna
	 * nazione <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nazioneNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nazione (@Column(nazione=true)). Colonna
	 * nazione in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nazioneIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nazione (@Column(nazione=true)). Colonna
	 * nazione not in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nazioneNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nazione (@Column(nazione=true)). Colonna
	 * nazione between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nazioneBetween(T val1, T val2) throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(nazione=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowNazione() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza identificata dalla annotazione
	 * Column(nazione=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctNazione() throws Exception;

	/**
	 * Esegue un filtro sulla colonna ente (@Column(ente=true)). Colonna
	 * ente=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> ente(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna ente (@Column(ente=true)). Colonna ente
	 * like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> enteLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna ente (@Column(ente=true)). Colonna ente
	 * notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> enteNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna ente (@Column(ente=true)). Colonna ente
	 * <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> enteNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna ente (@Column(ente=true)). Colonna ente in
	 * (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> enteIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna ente (@Column(ente=true)). Colonna ente
	 * not in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> enteNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna ente (@Column(ente=true)). Colonna ente
	 * between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> enteBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna sede (@Column(sede=true)). Colonna
	 * sede=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> sede(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna sede (@Column(sede=true)). Colonna sede
	 * like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> sedeLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna sede (@Column(sede=true)). Colonna sede
	 * notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> sedeNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna sede (@Column(sede=true)). Colonna sede
	 * <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> sedeNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna sede (@Column(sede=true)). Colonna sede in
	 * (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> sedeIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna sede (@Column(sede=true)). Colonna sede
	 * not in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> sedeNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna sede (@Column(sede=true)). Colonna sede
	 * between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> sedeBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cassa (@Column(cassa=true)). Colonna
	 * cassa=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cassa(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cassa (@Column(cassa=true)). Colonna cassa
	 * like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cassaLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cassa (@Column(cassa=true)). Colonna cassa
	 * notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cassaNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cassa (@Column(cassa=true)). Colonna cassa
	 * <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cassaNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cassa (@Column(cassa=true)). Colonna cassa
	 * in (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cassaIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cassa (@Column(cassa=true)). Colonna cassa
	 * not in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cassaNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cassa (@Column(cassa=true)). Colonna cassa
	 * between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cassaBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cf (@Column(cf=true)). Colonna cf=val. Non
	 * serve invocare il find() in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cf(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cf (@Column(cf=true)). Colonna cf like
	 * val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cfLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cf (@Column(cf=true)). Colonna cf notLike
	 * val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cfNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cf (@Column(cf=true)). Colonna cf <>val.
	 * Non serve invocare il find() in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cfNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cf (@Column(cf=true)). Colonna cf in
	 * (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cfIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cf (@Column(cf=true)). Colonna cf not in
	 * (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cfNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cf (@Column(cf=true)). Colonna cf between
	 * (val1,val2). Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cfBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna codice (@Column(codice=true)). Colonna
	 * codice=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> codice(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna codice (@Column(codice=true)). Colonna
	 * codice like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> codiceLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna codice (@Column(codice=true)). Colonna
	 * codice notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> codiceNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna codice (@Column(codice=true)). Colonna
	 * codice <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> codiceNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna codice (@Column(codice=true)). Colonna
	 * codice in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> codiceIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna codice (@Column(codice=true)). Colonna
	 * codice not in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> codiceNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna codice (@Column(codice=true)). Colonna
	 * codice between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> codiceBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cognome (@Column(cognome=true)). Colonna
	 * cognome=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cognome(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cognome (@Column(cognome=true)). Colonna
	 * cognome like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cognomeLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cognome (@Column(cognome=true)). Colonna
	 * cognome notLike val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cognomeNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cognome (@Column(cognome=true)). Colonna
	 * cognome <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cognomeNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cognome (@Column(cognome=true)). Colonna
	 * cognome in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cognomeIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cognome (@Column(cognome=true)). Colonna
	 * cognome not in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cognomeNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna cognome (@Column(cognome=true)). Colonna
	 * cognome between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> cognomeBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna descrizione (@Column(descrizione=true)).
	 * Colonna descrizione=val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> descrizione(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna descrizione (@Column(descrizione=true)).
	 * Colonna descrizione like val. Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> descrizioneLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna descrizione (@Column(descrizione=true)).
	 * Colonna descrizione notLike val. Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> descrizioneNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna descrizione (@Column(descrizione=true)).
	 * Colonna descrizione <>val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> descrizioneNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna descrizione (@Column(descrizione=true)).
	 * Colonna descrizione in (val1,val2,....,val n). Non serve invocare il
	 * find() in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> descrizioneIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna descrizione (@Column(descrizione=true)).
	 * Colonna descrizione not in (val1,val2,....,val n). Non serve invocare il
	 * find() in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> descrizioneNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna descrizione (@Column(descrizione=true)).
	 * Colonna descrizione between (val1,val2). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> descrizioneBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna endDate (@Column(endDate=true)). Colonna
	 * endDate=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> endDate(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna endDate (@Column(endDate=true)). Colonna
	 * endDate like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> endDateLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna endDate (@Column(endDate=true)). Colonna
	 * endDate notLike val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> endDateNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna endDate (@Column(endDate=true)). Colonna
	 * endDate <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> endDateNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna endDate (@Column(endDate=true)). Colonna
	 * endDate in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> endDateIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna endDate (@Column(endDate=true)). Colonna
	 * endDate not in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> endDateNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna endDate (@Column(endDate=true)). Colonna
	 * endDate between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> endDateBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna startDate (@Column(startDate=true)).
	 * Colonna startDate=val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> startDate(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna startDate (@Column(startDate=true)).
	 * Colonna startDate like val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> startDateLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna startDate (@Column(startDate=true)).
	 * Colonna startDate notLike val. Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> startDateNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna startDate (@Column(startDate=true)).
	 * Colonna startDate <>val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> startDateNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna startDate (@Column(startDate=true)).
	 * Colonna startDate in (val1,val2,....,val n). Non serve invocare il find()
	 * in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> startDateIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna startDate (@Column(startDate=true)).
	 * Colonna startDate not in (val1,val2,....,val n). Non serve invocare il
	 * find() in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> startDateNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna startDate (@Column(startDate=true)).
	 * Colonna startDate between (val1,val2). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> startDateBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna eta (@Column(eta=true)). Colonna eta=val.
	 * Non serve invocare il find() in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> eta(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna eta (@Column(eta=true)). Colonna eta like
	 * val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> etaLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna eta (@Column(eta=true)). Colonna eta
	 * notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> etaNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna eta (@Column(eta=true)). Colonna eta
	 * <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> etaNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna eta (@Column(eta=true)). Colonna eta in
	 * (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> etaIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna eta (@Column(eta=true)). Colonna eta not
	 * in (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> etaNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna eta (@Column(eta=true)). Colonna eta
	 * between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> etaBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna fascicolo (@Column(fascicolo=true)).
	 * Colonna fascicolo=val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> fascicolo(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna fascicolo (@Column(fascicolo=true)).
	 * Colonna fascicolo like val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> fascicoloLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna fascicolo (@Column(fascicolo=true)).
	 * Colonna fascicolo notLike val. Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> fascicoloNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna fascicolo (@Column(fascicolo=true)).
	 * Colonna fascicolo <>val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> fascicoloNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna fascicolo (@Column(fascicolo=true)).
	 * Colonna fascicolo in (val1,val2,....,val n). Non serve invocare il find()
	 * in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> fascicoloIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna fascicolo (@Column(fascicolo=true)).
	 * Colonna fascicolo not in (val1,val2,....,val n). Non serve invocare il
	 * find() in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> fascicoloNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna fascicolo (@Column(fascicolo=true)).
	 * Colonna fascicolo between (val1,val2). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> fascicoloBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna id (@Column(id=true)). Colonna id=val. Non
	 * serve invocare il find() in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> id(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna id (@Column(id=true)). Colonna id like
	 * val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> idLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna id (@Column(id=true)). Colonna id notLike
	 * val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> idNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna id (@Column(id=true)). Colonna id <>val.
	 * Non serve invocare il find() in quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> idNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna id (@Column(id=true)). Colonna id in
	 * (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> idIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna id (@Column(id=true)). Colonna id not in
	 * (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> idNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna id (@Column(id=true)). Colonna id between
	 * (val1,val2). Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> idBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna importo (@Column(importo=true)). Colonna
	 * importo=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> importo(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna importo (@Column(importo=true)). Colonna
	 * importo like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> importoLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna importo (@Column(importo=true)). Colonna
	 * importo notLike val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> importoNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna importo (@Column(importo=true)). Colonna
	 * importo <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> importoNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna importo (@Column(importo=true)). Colonna
	 * importo in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> importoIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna importo (@Column(importo=true)). Colonna
	 * importo not in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> importoNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna importo (@Column(importo=true)). Colonna
	 * importo between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> importoBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna iscritto (@Column(iscritto=true)). Colonna
	 * iscritto=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> iscritto(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna iscritto (@Column(iscritto=true)). Colonna
	 * iscritto like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> iscrittoLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna iscritto (@Column(iscritto=true)). Colonna
	 * iscritto notLike val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> iscrittoNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna iscritto (@Column(iscritto=true)). Colonna
	 * iscritto <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> iscrittoNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna iscritto (@Column(iscritto=true)). Colonna
	 * iscritto in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> iscrittoIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna iscritto (@Column(iscritto=true)). Colonna
	 * iscritto not in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> iscrittoNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna iscritto (@Column(iscritto=true)). Colonna
	 * iscritto between (val1,val2). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> iscrittoBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nome (@Column(nome=true)). Colonna
	 * nome=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nome(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nome (@Column(nome=true)). Colonna nome
	 * like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nomeLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nome (@Column(nome=true)). Colonna nome
	 * notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nomeNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nome (@Column(nome=true)). Colonna nome
	 * <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nomeNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nome (@Column(nome=true)). Colonna nome in
	 * (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nomeIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nome (@Column(nome=true)). Colonna nome
	 * not in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nomeNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna nome (@Column(nome=true)). Colonna nome
	 * between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> nomeBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna progr (@Column(progr=true)). Colonna
	 * progr=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> progr(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna progr (@Column(progr=true)). Colonna progr
	 * like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> progrLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna progr (@Column(progr=true)). Colonna progr
	 * notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> progrNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna progr (@Column(progr=true)). Colonna progr
	 * <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> progrNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna progr (@Column(progr=true)). Colonna progr
	 * in (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> progrIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna progr (@Column(progr=true)). Colonna progr
	 * not in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> progrNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna progr (@Column(progr=true)). Colonna progr
	 * between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> progrBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato (@Column(stato=true)). Colonna
	 * stato=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> stato(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato (@Column(stato=true)). Colonna stato
	 * like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> statoLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato (@Column(stato=true)). Colonna stato
	 * notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> statoNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato (@Column(stato=true)). Colonna stato
	 * <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> statoNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato (@Column(stato=true)). Colonna stato
	 * in (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> statoIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato (@Column(stato=true)). Colonna stato
	 * not in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> statoNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato (@Column(stato=true)). Colonna stato
	 * between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> statoBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato1 (@Column(stato1=true)). Colonna
	 * stato1=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> stato1(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato1 (@Column(stato1=true)). Colonna
	 * stato1 like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> stato1Like(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato1 (@Column(stato1=true)). Colonna
	 * stato1 notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> stato1NotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato1 (@Column(stato1=true)). Colonna
	 * stato1 <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> stato1Neq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato1 (@Column(stato1=true)). Colonna
	 * stato1 in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> stato1In(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato1 (@Column(stato1=true)). Colonna
	 * stato1 not in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> stato1NotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna stato1 (@Column(stato1=true)). Colonna
	 * stato1 between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> stato1Between(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna tipo (@Column(tipo=true)). Colonna
	 * tipo=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> tipo(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna tipo (@Column(tipo=true)). Colonna tipo
	 * like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> tipoLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna tipo (@Column(tipo=true)). Colonna tipo
	 * notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> tipoNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna tipo (@Column(tipo=true)). Colonna tipo
	 * <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> tipoNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna tipo (@Column(tipo=true)). Colonna tipo in
	 * (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> tipoIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna tipo (@Column(tipo=true)). Colonna tipo
	 * not in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> tipoNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna tipo (@Column(tipo=true)). Colonna tipo
	 * between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> tipoBetween(T val1, T val2) throws Exception;

	/**
	 * Esegue un filtro sulla colonna totale (@Column(totale=true)). Colonna
	 * totale=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> totale(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna totale (@Column(totale=true)). Colonna
	 * totale like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> totaleLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna totale (@Column(totale=true)). Colonna
	 * totale notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> totaleNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna totale (@Column(totale=true)). Colonna
	 * totale <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> totaleNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna totale (@Column(totale=true)). Colonna
	 * totale in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> totaleIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna totale (@Column(totale=true)). Colonna
	 * totale not in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> totaleNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna totale (@Column(totale=true)). Colonna
	 * totale between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> totaleBetween(T val1, T val2) throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza totale identificata dalla
	 * annotazione Column(totale=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowTotale() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza totale identificata dalla
	 * annotazione Column(totale=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctTotale() throws Exception;

	/**
	 * Esegue un filtro sulla colonna valore (@Column(valore=true)). Colonna
	 * valore=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> valore(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna valore (@Column(valore=true)). Colonna
	 * valore like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> valoreLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna valore (@Column(valore=true)). Colonna
	 * valore notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> valoreNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna valore (@Column(valore=true)). Colonna
	 * valore <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> valoreNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna valore (@Column(valore=true)). Colonna
	 * valore in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> valoreIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna valore (@Column(valore=true)). Colonna
	 * valore not in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> valoreNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna valore (@Column(valore=true)). Colonna
	 * valore between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val1
	 * @param val2
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> valoreBetween(T val1, T val2) throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza valore identificata dalla
	 * annotazione Column(valore=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowValore() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza valore identificata dalla
	 * annotazione Column(valore=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctValore() throws Exception;

	/**
	 * Esegue un filtro sulla colonna username (@Column(username=true)). Colonna
	 * username=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> username(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna username (@Column(username=true)). Colonna
	 * username like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> usernameLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna username (@Column(username=true)). Colonna
	 * username notLike val. Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> usernameNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna username (@Column(username=true)). Colonna
	 * username <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> usernameNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna username (@Column(username=true)). Colonna
	 * username in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> usernameIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna username (@Column(username=true)). Colonna
	 * username not in (val1,val2,....,val n). Non serve invocare il find() in
	 * quanto viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> usernameNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna username (@Column(username=true)). Colonna
	 * username between (val1,val2). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> usernameBetween(T val1, T val2) throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza username identificata dalla
	 * annotazione Column(username=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowUsername() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza username identificata dalla
	 * annotazione Column(username=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctUsername() throws Exception;

	/**
	 * Esegue un filtro sulla colonna email (@Column(email=true)). Colonna
	 * email=val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> email(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna email (@Column(email=true)). Colonna email
	 * like val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> emailLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna email (@Column(email=true)). Colonna email
	 * notLike val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> emailNotLike(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna email (@Column(email=true)). Colonna email
	 * <>val. Non serve invocare il find() in quanto viene invocato
	 * automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> emailNeq(T val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna email (@Column(email=true)). Colonna email
	 * in (val1,val2,....,val n). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> emailIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna email (@Column(email=true)). Colonna email
	 * not in (val1,val2,....,val n). Non serve invocare il find() in quanto
	 * viene invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @param val
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> emailNotIn(T... val) throws Exception;

	/**
	 * Esegue un filtro sulla colonna email (@Column(email=true)). Colonna email
	 * between (val1,val2). Non serve invocare il find() in quanto viene
	 * invocato automaticamente
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<K>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<K> emailBetween(T val1, T val2) throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti
	 * corrispondenti alla variabile istanza email identificata dalla
	 * annotazione Column(email=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowEmail() throws Exception;

	/**
	 * Data una PList di bean BaseEntity,ritorna una PList di oggetti distinti
	 * corrispondenti alla variabile istanza email identificata dalla
	 * annotazione Column(email=true)
	 * 
	 * @param <T>
	 * @param <K>
	 * @return PList<T>
	 * @throws Exception
	 */
	<T, K extends BaseEntity> PList<T> narrowDistinctEmail() throws Exception;

	/**
	 * Torna true se la lista ha almeno un elemento tra i valori indicati
	 * 
	 * @param <T>
	 * @param prop
	 * @param values
	 * @return boolean
	 * @throws Exception
	 */
	<T> boolean anyMatch(String prop, T... values) throws Exception;

	/**
	 * Torna true se la lista non ha alcun elemento tra i valori indicati
	 * 
	 * @param values
	 * @return boolean
	 * @throws Exception
	 */
	<T> boolean noneMatch(String prop, T... values) throws Exception;
}
