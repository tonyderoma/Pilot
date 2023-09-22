package it.eng.pilot;

import java.io.Serializable;

/**
 * Interfaccia per le validazioni.
 * 
 * @author Antonio Corinaldi
 * 
 */
public interface Validator extends Serializable {

	/**
	 * Torna true se la validazione implementata al suo interno è soddisfatta,
	 * false altrimenti
	 * 
	 * @return boolean
	 */
	boolean validate();

}
