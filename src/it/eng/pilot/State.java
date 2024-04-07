package it.eng.pilot;

/**
 * Classe per la gestione dello stato di una applicazione per evitare il
 * passaggio continuo di parametri tra metodi. Gestisce variabili di stato
 * globali application scoped
 * 
 * @author Antonio Corinaldi
 * 
 */
public class State {
	private static State INSTANCE;
	private final Pilot p = new Pilot();
	private PMap<String, Object> mappa = p.pmap();

	public static State getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new State();
		}
		return INSTANCE;
	}

	/**
	 * Imposta la variabile di stato globale key al valore val
	 * 
	 * @param key
	 * @param val
	 */
	public void setState(String key, Object val) {
		mappa.put(key, val);
	}

	/**
	 * Ritorna il valore della variabile di stato globale key
	 * 
	 * @param key
	 * @return Object
	 */
	public Object getState(String key) {
		return mappa.get(key);
	}

	/**
	 * Svuota completamente l'intero stato applicativo
	 */
	public void destroy() {
		INSTANCE = null;
	}

	/**
	 * Rimuove dallo stato dell'applicazione la variabile di stato globale key
	 * 
	 * @param key
	 */
	public void clearState(String key) {
		mappa.remove(key);
	}

	/**
	 * Ritorna l'intero stato dell'applicazione con tutti i valori delle
	 * variabili di stato globali in esso presenti
	 * 
	 * @return PMap<String, Object>
	 */
	public PMap<String, Object> getStateFull() {
		return mappa;
	}

}
