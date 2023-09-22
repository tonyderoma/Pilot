package it.eng.pilot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotazione per configurare il tipo di Mock per il particolare attributo che
 * la usa.
 * 
 * @author Antonio Corinaldi
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mock {

	/**
	 * Indica i valori possibili che può assumere la variabile istanza. Se il
	 * campo è di tipo diverso da stringa, verrà effettuata automaticamente la
	 * conversione da stringa al tipo del campo e se fallisce, si metterà null
	 * 
	 * @return String[]
	 */
	String[] allowed() default {};

	/**
	 * Indica che l'attributo attuale deve avere un valore maggiore
	 * dell'attributo specificato da greaterThan
	 * 
	 * @return String
	 */
	String greaterThan() default "";

	/**
	 * Indica che l'attributo attuale deve avere un valore minore dell'attributo
	 * specificato da lowerThan
	 * 
	 * @return String
	 */
	String lowerThan() default "";

	/**
	 * Indica le due variabili istanza i cui valori saranno i limiti
	 * dell'intervallo di appartenenza del valore della variabile istanza che
	 * contiene tale attributo
	 * 
	 * valore campo in [valore(campo1),valore(campo2)]
	 * 
	 * @return String[]
	 */
	String[] between() default {};

	/**
	 * Il valore della variabile istanza che riporta questo attributo deve
	 * essere compreso tra i due valori indicati
	 * 
	 * valore in [valore1,valore2]
	 * 
	 * @return String[]
	 */
	String[] betweenValues() default {};

	/**
	 * Se true non verrà generato un valore di mock per la relativa variabile
	 * istanza
	 * 
	 * @return boolean
	 */
	boolean skip() default false;

}
