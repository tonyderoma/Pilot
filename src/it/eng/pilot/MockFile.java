package it.eng.pilot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotazione per configurare il Mock mediante la volorizzazione delle
 * variabili istanza della classe attraverso dati presi da un file. Il file
 * conterrà stringhe separate da LF del tipo:
 * [nomeVariabileIstanza]=valore[separator]....[nomeVariabileIstanza]=valore.
 * 
 * @author Antonio Corinaldi
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockFile {

	/**
	 * Indica il file (path+nomeFile.ext) che conterrà i dati di mock per la
	 * classe
	 * 
	 * @return String
	 */
	String file() default "";

	/**
	 * Indica la stringa da usare come separatore tra le variabili istanza
	 * 
	 * @return String
	 */
	String separator() default ";";

}
