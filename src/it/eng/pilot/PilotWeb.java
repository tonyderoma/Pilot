package it.eng.pilot;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe di utilità per le funzioni web.
 * 
 * @author Antonio Corinaldi
 * 
 */
public class PilotWeb extends Pilot {

	private static final long serialVersionUID = 2431006036994881769L;

	/**
	 * Imposta in session l'attributo prop al valore val
	 * 
	 * @param req
	 * @param prop
	 * @param val
	 */
	public void setSessionAttribute(HttpServletRequest req, String prop, Object val) {
		req.getSession().setAttribute(prop, val);
	}

	/**
	 * Recupera dalla sessione il valore dell'attributo prop e lo casta alla
	 * classe C solo se non nullo
	 * 
	 * 
	 * @param <K>
	 * @param req
	 * @param prop
	 * @param c
	 * @return K
	 */
	public <K> K getSessionAttribute(HttpServletRequest req, String prop, Class<K> c) {
		K att = null;
		if (notNull(req.getSession().getAttribute(prop))) {
			att = (K) req.getSession().getAttribute(prop);
		}
		return att;
	}

	/**
	 * Recupera dalla sessione il valore dell'attributo Stringa prop
	 * 
	 * @param req
	 * @param prop
	 * @return String
	 */
	public String getStringSessionAttribute(HttpServletRequest req, String prop) {
		return (String) req.getSession().getAttribute(prop);
	}

	/**
	 * Rimuove dalla sessione l'attributo prop
	 * 
	 * @param req
	 * @param prop
	 */
	public void removeSessionAttribute(HttpServletRequest req, String prop) {
		req.getSession().removeAttribute(prop);
	}

	/**
	 * Imposta l'attributo prop nella request al valore val
	 * 
	 * @param req
	 * @param prop
	 * @param val
	 */
	public void setRequestAttribute(HttpServletRequest req, String prop, Object val) {
		req.setAttribute(prop, val);
	}

	/**
	 * Recupera l'attributo prop dalla request
	 * 
	 * @param req
	 * @param prop
	 * @return Object
	 */
	public Object getRequestAttribute(HttpServletRequest req, String prop) {
		return req.getAttribute(prop);
	}

	/**
	 * nomeFileProperties è il nome del file di properties senza l'estensione.
	 * key è la chiave da cercare params sono i parametri che vanno a
	 * sostituirsi ai bookmark {i} Recupera il messaggio corrispondente alla
	 * chiave key
	 * 
	 * @param nomeFileProperties
	 * @param key
	 * @param params
	 * @return String
	 */
	public String getMessage(String nomeFileProperties, String key, String[] params) {
		String message = null;
		ResourceBundle rb = ResourceBundle.getBundle(nomeFileProperties);
		String messaggio = rb.getString(key);
		if (notNull(messaggio)) {
			message = MessageFormat.format(messaggio, (Object[]) params);
		} else {
			log("chiave ", key, " non trovata nel file ", nomeFileProperties);
		}
		return message;

	}

	/**
	 * nomeFileProperties è il nome del file di properties senza l'estensione.
	 * key è la chiave da cercare
	 * 
	 * Recupera il messaggio corrispondente alla chiave key
	 * 
	 * @param nomeFileProperties
	 * @param key
	 * @return String
	 */
	public String getMessage(String nomeFileProperties, String key) {
		return getMessage(nomeFileProperties, key, null);

	}
}
