package it.eng.pilot;

import java.util.Map;

/**
 * Classe per la gestione dello stato di una applicazione per evitare il
 * passaggio continuo di parametri tra metodi. Gestisce variabili di stato
 * globali application-scoped di tipo Object generico, PList<Object> e
 * PMap<Object,Object>
 * 
 * @author Antonio Corinaldi
 * 
 */
public class State {
	private static State INSTANCE;
	private final Pilot p = new Pilot();
	private PMap<String, Object> mappa = p.pmap();
	private PMap<String, PList<Object>> mappaLista = p.pmapl();
	private PMap<String, PMap<Object, Object>> mappaMap = p.pmap();

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
		if (val instanceof PList) {
			mappaLista.put(key, (PList<Object>) val);
			return;
		}
		if (val instanceof PMap) {
			mappaMap.put(key, (PMap<Object, Object>) val);
			return;
		}
		mappa.put(key, val);
	}

	/**
	 * Ritorna il valore della variabile di stato globale key
	 * 
	 * @param key
	 * @return Object
	 */
	public Object getState(String key) {
		if (mappa.containsKey(key))
			return mappa.get(key);
		if (mappaLista.containsKey(key))
			return mappaLista.get(key);
		if (mappaMap.containsKey(key))
			return mappaMap.get(key);
		return null;
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
		if (mappa.containsKey(key))
			mappa.remove(key);
		if (mappaLista.containsKey(key))
			mappaLista.remove(key);
		if (mappaMap.containsKey(key))
			mappaMap.remove(key);
	}

	/**
	 * Ritorna l'intero stato dell'applicazione con tutti i valori delle
	 * variabili di stato globali in esso presenti
	 * 
	 * @return PMap<String, Object>
	 */
	public PMap<String, Object> getStateFull() {
		PMap<String, Object> map = p.pmap();
		for (Map.Entry<String, Object> entry : mappa.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<String, PList<Object>> entry : mappaLista.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<String, PMap<Object, Object>> entry : mappaMap.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}
}
