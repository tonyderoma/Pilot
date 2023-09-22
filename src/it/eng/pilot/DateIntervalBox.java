package it.eng.pilot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Classe contenitrice di oggetti DateInterval da sottoporre a controllo di
 * validità per non sovrapposizione temporale.
 * 
 * @author Antonio Corinaldi
 */
public class DateIntervalBox extends PilotSupport implements Serializable {

	private static final long serialVersionUID = 2777589177996879365L;

	private List<DateInterval> intervalli = new ArrayList<DateInterval>();

	public List<DateInterval> getIntervalli() {
		return intervalli;
	}

	/**
	 * Aggiunge un range di date [inizio,fine] alla lista di intervalli da
	 * verificare per sovrapposizione temporale
	 * 
	 * @param inizio
	 * @param fine
	 * @return DateIntervalBox
	 * @throws Exception
	 */
	public DateIntervalBox addDateInterval(String inizio, String fine) throws Exception {
		getIntervalli().add(new DateInterval(inizio, fine));
		return this;
	}

	/**
	 * Aggiunge un range di date [inizio,fine] alla lista di intervalli da
	 * verificare per sovrapposizione temporale
	 * 
	 * @param inizio
	 * @param fine
	 * @return DateIntervalBox
	 * @throws Exception
	 */
	public DateIntervalBox addDateInterval(Date inizio, Date fine) throws Exception {
		getIntervalli().add(new DateInterval(inizio, fine));
		return this;
	}

	/**
	 * Ritorna true se tutti gli intervalli di date non hanno nessuna
	 * sovrapposizione temporale tra di loro, false altrimenti
	 * 
	 * @return boolean
	 */
	public boolean validaDateIntervalNotOverlapping() {
		return p.validaDateIntervalNotOverlap(getIntervalli());
	}

	/**
	 * Restituisce una mappa Intervallo Data - Elenco Intervalli Date in
	 * sovrapposizione
	 * 
	 * @return Map<String, List<String>>
	 */
	public Map<String, List<String>> validaDateIntervalNotOverlappingWithMessages() {
		return p.validaDateIntervalNotOverlapWithMessages(getIntervalli());
	}

}
