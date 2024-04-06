package it.eng.pilot;

import java.util.Map;

/**
 * Classe per la gestione dello stato di una applicazione per evitare il
 * passaggio continuo di parametri tra metodi. Gestisce variabili di stato di
 * tipo Object generico, PList<Object> e PMap<Object,Object>
 * 
 * @author Antonio Corinaldi
 * 
 */
public class State extends PilotSupport {
	private static State INSTANCE;
	private PMap<String, Object> mappa = pmap();
	private PMap<String, PList<Object>> mappaLista = pmapl();
	private PMap<String, PMap<Object, Object>> mappaMap = pmap();

	public static State getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new State();
		}
		return INSTANCE;
	}

	public void setVal(String key, Object val) {
		mappa.put(key, val);
	}

	public void setVal(String key, PList<Object> val) {
		mappaLista.put(key, val);
	}

	public void setVal(String key, PMap<Object, Object> val) {
		mappaMap.put(key, val);
	}

	public Object getVal(String key) {
		return mappa.getValue(key);
	}

	public PList<Object> getValList(String key) {
		return mappaLista.getValue(key);
	}

	public PMap<Object, Object> getValMap(String key) {
		return mappaMap.getValue(key);
	}

	public void destroy() {
		INSTANCE = null;
	}

	public void clearKey(String key) {
		mappa.remove(key);
	}

	public void clearKeyList(String key) {
		mappaLista.remove(key);
	}

	public void clearKeyMap(String key) {
		mappaMap.remove(key);
	}

	public PMap<String, Object> getState() {
		PMap<String, Object> map = pmap();
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
