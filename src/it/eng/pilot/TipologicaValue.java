package it.eng.pilot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotazione che identifica una colonna di una tabella come valore della
 * tipologica corrispondente alla chiave @TipologicaKey.
 * 
 * @author Antonio Corinaldi
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TipologicaValue {

}
