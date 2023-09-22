package it.eng.pilot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotazione per configurare le join che può usare questa classe nelle select.
 * 
 * @author Antonio Corinaldi
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {

	/**
	 * Nome della join
	 * 
	 * @return String
	 */
	String name() default "";

	/**
	 * Indica il nome della tabella con cui andare in join. Le tabelle possono
	 * essere anche più di una ma i loro nomi devono essere separati da ","
	 * 
	 * @return String
	 */
	String table() default "";

	/**
	 * Indica la condizione sql di join. Scritta in sql nativo. Si aggancia
	 * automaticamente in AND sulla where condition.
	 * 
	 * @return String
	 */
	String condition() default "";

}
