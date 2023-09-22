package it.eng.pilot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotazione che definisce i nomi dei contatori di chiamata e temporali di
 * tipo TimeCount che verranno poi utilizzati all'interno della classe che usa
 * questa annotazione allo scopo di rilevare le informazioni delle tempistiche
 * di risposta dei servizi che si vogliono monitorare
 * 
 * @author Antonio Corinaldi
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LapTime {

	/**
	 * Nomi degli oggetti di tipo TimeCount che si vogliono usare
	 * 
	 * @return String
	 */
	String[] timeCounters() default {};

}
