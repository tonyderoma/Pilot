package it.eng.pilot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe contenitrice delle validazioni (Validation) da sottoporre a controllo.
 * 
 * @author Antonio Corinaldi
 */
public class ValidationBox implements Serializable {

	private final Pilot p = new Pilot();

	private static final long serialVersionUID = 2777589177996879365L;

	private List<Validation> elencoValidazioni = new ArrayList<Validation>();
	private List<String> messaggi = new ArrayList<String>();

	private List<Validation> getElencoValidazioni() {
		return elencoValidazioni;
	}

	/**
	 * Aggiunge una validazione da sottoporre a controllo. Valido true significa
	 * che la validazione è soddisfatta. Messaggio è il messaggio di errore che
	 * bisogna ritornare nel caso in cui la validazione non sia soddisfatta
	 * 
	 * @param valido
	 * @param messaggio
	 * @return ValidationBox
	 */
	public ValidationBox addValidation(Boolean valido, String messaggio) {
		getElencoValidazioni().add(new Validation(valido, messaggio));
		return this;
	}

	/**
	 * Aggiunge una validazione da sottoporre a controllo.
	 * CondizioneApplicabilita se true viene applicata la validazione, se false
	 * non viene considerata e non sottoposta a controllo. Valido true significa
	 * che la validazione è soddisfatta. Messaggio è il messaggio di errore che
	 * bisogna ritornare nel caso in cui la validazione non sia soddisfatta
	 * 
	 * @param condizioneApplicabilita
	 * @param valido
	 * @param messaggio
	 * @return ValidationBox
	 */
	public ValidationBox addValidation(Boolean condizioneApplicabilita, Boolean valido, String messaggio) {
		getElencoValidazioni().add(new Validation(condizioneApplicabilita, valido, messaggio));
		return this;
	}

	/**
	 * Esegue la validazione di ogni oggetto validation presente nella lista di
	 * oggetti Validation e ritorna l'elenco di messaggi di errore per ogni
	 * validazione non soddisfatta
	 * 
	 * @return List
	 * 
	 */
	public List<String> valida() {
		String msg = "";
		for (Validation v : p.safe(elencoValidazioni)) {
			if (p.notNull(v)) {
				msg = v.execVal();
				if (p.notNull(msg)) {
					messaggi.add(msg);
				}
			}
		}
		return messaggi;
	}

	public List<String> getMessaggi() {
		return messaggi;
	}

}
