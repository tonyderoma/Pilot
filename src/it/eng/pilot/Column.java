package it.eng.pilot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotazione che identifica una colonna di una tabella.
 * 
 * @author Antonio Corinaldi
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

	/**
	 * Nome della colonna della tabella
	 * 
	 * @return String
	 */
	String name();

	/**
	 * Se true identifica le variabili istanza chiave primaria. Default false
	 * 
	 * @return boolean
	 */
	boolean pk() default false;

	/**
	 * Se true identifica le variabili istanza chiave primaria di tipo
	 * auto-incrementale. Va impostata manualmente sulle variabili istanza di
	 * cui si sa che sono autoincrementali in quanto non � possibile farlo
	 * generare automaticamente poich� Oracle non permette l'individuazione
	 * delle colonne di chiave primaria autoincrementali
	 * 
	 * @return boolean
	 */
	boolean autoInc() default false;

	/**
	 * Se true identifica la variabile istanza corrispondente al nome di colonna
	 * che consente la cancellazione logica del record. In ambiente INPS ad
	 * esempio � la colonna che termina con ....FLAG_STATO. Questa colonna di
	 * cancellazione logica � fondamentale conoscerla per rendere automatiche
	 * e intelligenti le funzionalit� di delete fisica/logica e di
	 * insert/upsert
	 * 
	 * @return boolean
	 */
	boolean deleteLogic() default false;

	/**
	 * Se true indica che questo campo identifica una data inizio
	 * 
	 * @return boolean
	 */
	boolean startDate() default false;

	/**
	 * Se true indica che questo campo identifica una data fine
	 * 
	 * @return boolean
	 */
	boolean endDate() default false;

	/**
	 * Se true indica che questo campo identifica una colonna di tipo STATO.
	 * 
	 * @return boolean
	 */
	boolean stato() default false;

	/**
	 * Se true indica che questo campo identifica una colonna di tipo STATO1.
	 */
	boolean stato1() default false;

	/**
	 * Indica l'alias della tabella
	 * 
	 * @return String
	 */
	String alias() default "";

	/**
	 * Se true indica che questo campo identifica una colonna ENTE
	 * 
	 * @return boolean
	 */
	boolean ente() default false;

	/**
	 * Se true indica che questo campo identifica una colonna ISCRITTO
	 * 
	 * @return boolean
	 */
	boolean iscritto() default false;

	/**
	 * Se true indica che questo campo identifica una colonna identificativo
	 * generico univoco
	 * 
	 * @return boolean
	 */
	boolean id() default false;

	/**
	 * Se true indica che questo campo identifica una colonna cassa
	 * 
	 * @return boolean
	 */
	boolean cassa() default false;

	/**
	 * Se true indica che questo campo identifica una colonna progressivo
	 * 
	 * @return boolean
	 */
	boolean progr() default false;

	/**
	 * Se true indica che questo campo identifica una colonna codice
	 * 
	 * @return boolean
	 */
	boolean codice() default false;

	/**
	 * Se true indica che questo campo identifica una colonna descrizione
	 * 
	 * @return boolean
	 */
	boolean descrizione() default false;

	/**
	 * Se true indica che questo campo identifica una colonna tipo
	 * 
	 * @return boolean
	 */
	boolean tipo() default false;

	/**
	 * Se true indica che questo campo identifica una colonna fascicolo
	 * 
	 * @return boolean
	 */
	boolean fascicolo() default false;

	/**
	 * Indica il nome della sequence da utilizzare per l'inserimento
	 * incrementale di un progressivo di chiave primaria durante l'insert
	 * 
	 * @return String
	 */
	String seq() default "";

	/**
	 * E' true per i campi not nullable
	 * 
	 * @return boolean
	 */
	boolean notNull() default false;

	/**
	 * Se true indica che questo campo identifica una colonna codice sede
	 * 
	 * @return boolean
	 */
	boolean sede() default false;

	/**
	 * Se true indica che questo campo identifica una colonna nome
	 * 
	 * @return boolean
	 */
	boolean nome() default false;

	/**
	 * Se true indica che questo campo identifica una colonna cognome
	 * 
	 * @return boolean
	 */
	boolean cognome() default false;

	/**
	 * Se true indica che questo campo identifica una colonna eta
	 * 
	 * @return boolean
	 */
	boolean eta() default false;

	/**
	 * Se true indica che questo campo identifica una colonna codiceFiscale
	 * 
	 * @return boolean
	 */
	boolean cf() default false;

	/**
	 * Se true indica che questo campo identifica una colonna importo
	 * 
	 * @return boolean
	 */
	boolean importo() default false;

	/**
	 * Se true indica che questo campo identifica una colonna citta
	 * 
	 * @return boolean
	 */
	boolean citta() default false;

	/**
	 * Se true indica che questo campo identifica una colonna indirizzo
	 * 
	 * @return boolean
	 */
	boolean indirizzo() default false;

	/**
	 * Se true indica che questo campo identifica una colonna titolo
	 * 
	 * @return boolean
	 */
	boolean titolo() default false;

	/**
	 * Se true indica che questo campo identifica una colonna partita iva
	 * 
	 * @return boolean
	 */
	boolean piva() default false;

	/**
	 * Se true indica che questo campo identifica una colonna nazione
	 * 
	 * @return boolean
	 */
	boolean nazione() default false;

	/**
	 * Se true indica che questo campo identifica una colonna totale
	 * 
	 * @return boolean
	 */
	boolean totale() default false;

	/**
	 * Se true indica che questo campo identifica una colonna valore
	 * 
	 * @return boolean
	 */
	boolean valore() default false;

	/**
	 * Se true indica che questo campo identifica una colonna username
	 * 
	 * @return boolean
	 */
	boolean username() default false;

	/**
	 * Se true indica che questo campo identifica una colonna email
	 * 
	 * @return boolean
	 */
	boolean email() default false;

}
