package it.eng.pilot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configurazione della classe dao che estende DaoHelper.
 * 
 * @author Antonio Corinaldi
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DaoConfig {

	public static final String EMPTY = "";

	/**
	 * Nome del pkg contenitore delle entities
	 * 
	 * @return String
	 */
	String pkg() default EMPTY;

	/**
	 * Elenco di entities con nome classe senza .class
	 * 
	 * @return String[]
	 */
	String[] entities() default {};

	/**
	 * CodUtente da usare nelle query
	 * 
	 * @return String
	 */
	String codUtente();

	/**
	 * CodApp da usare nelle query
	 * 
	 * @return String
	 */
	String codAppl();

	/**
	 * Nome del package contenente il file queries.properties per eseguire query
	 * di selezione e invocazione di stored procedures scritte nel file
	 * queries.properties
	 * 
	 * @return String
	 */
	String pkgPropertiesFile() default EMPTY;

	/**
	 * Timeout in secondi per eseguire le query
	 * 
	 * @return int
	 */
	int timeout() default 60;

	/**
	 * Limite massimo di righe da aggiornare o cancellare.
	 * 
	 * @return int
	 */
	int updateDeleteLimit() default 0;

	/**
	 * Se impostato a true, il DaoHelper logga le query durante la loro
	 * esecuzione. Se false (default) non logga le query durante l'esecuzione ma
	 * solo nel report complessivo finale
	 * 
	 * @return boolean
	 */
	boolean logWhileRunning() default false;

	/**
	 * Parametro di impostazione dell'autocommit della connessione del dao.
	 * 
	 * @return boolean
	 */
	boolean autocommit() default false;

	/**
	 * Se true tutte le query eseguite nel dao vengono eseguite in un database
	 * in memoria salvato su disco nel file [nomeClasseDao]_DB.log. Pertanto
	 * viene completamente bypassata la connessione al database reale e si
	 * lavora direttamente sul db in memoria salvato su disco in locale
	 * 
	 * @return boolean
	 */
	boolean inMemory() default false;

	/**
	 * Se true causa la cancellazione del file [nomeClasseDao]_DB.log
	 * cancellando tutto il database in memoria pre-esistente frutto di
	 * precedenti esecuzioni del dao in modalità in memory e riparte da una
	 * situazione ex-novo.
	 * 
	 * @return boolean
	 */
	boolean purgeDB() default false;

	/**
	 * Path completo di nome e estensione per il file di properties da cui
	 * recuperare i valori corrispondenti alle chiavi tramite i metodi
	 * getKey(...)
	 * 
	 * @return String
	 */
	String propertyFile() default EMPTY;

	/**
	 * Limite di soglia per il numero di query che il container può contenere
	 * prima di essere elaborato per evitare outOfMemoryError dell'heap di
	 * memoria
	 * 
	 * @return Integer
	 */
	int flushThreshold() default 10000;

	/**
	 * Se true vengono stampati nel path specificato da impactPath i file di
	 * resoconto di impatto delle entities specificate
	 * 
	 * @return boolean
	 */
	boolean impact() default false;

	/**
	 * Indica l'elenco degli alias delle entities di cui si vuole avere un
	 * resoconto di impatto del dao in termini di record inseriti/cancellati
	 * logicamente/modificati.
	 * 
	 * @return String[]
	 */
	String[] impactAlias() default {};

	/**
	 * E' il path assoluto dove verrà scritto il file dao di statistiche delle
	 * query eseguite durante l'elaborazione e anche i file di resoconto di
	 * impatto se l'attributo impact è a true
	 * 
	 * @return String
	 */
	String outPath() default EMPTY;

	/**
	 * Se impostato a true, esegue le query secondo SQL di DB2 (es. vedi
	 * date(to_date('10/01/2012','DD/MM/YYYY'))
	 * 
	 * @return boolean
	 */
	boolean db2() default false;

}
