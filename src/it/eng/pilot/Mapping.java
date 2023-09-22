package it.eng.pilot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotazione per individuare il metodo che esegue la logica di trasformazione
 * di tipo (mapping) dell'elemento i-esimo della lista
 * 
 * @author Antonio Corinaldi
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {

	String name() default "";

}
