package it.eng.pilot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotazione che definisce la tabella del db su cui si vuole operare.
 * 
 * @author Antonio Corinaldi
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

	/**
	 * E' il nome della tabella sul DB
	 * 
	 * @return String
	 */
	String name();

	/**
	 * E' l'alias della tabella
	 * 
	 * @return String
	 */
	String alias() default "";

}
