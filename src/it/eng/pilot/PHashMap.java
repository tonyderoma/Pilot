package it.eng.pilot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Classe che estende una classica HashMap java.
 * 
 * @author Antonio Corinaldi
 * 
 * @param <K>
 * @param <V>
 */
public class PHashMap<K, V> extends HashMap<K, V> implements PMap<K, V> {

	private static final long serialVersionUID = 5913213559717269798L;
	private final Pilot p = new Pilot();

	public PHashMap() {
	}

	public PList<V> collectAll() {
		PList<V> all = p.pl();
		for (Map.Entry<K, PList<V>> entry : ((PMap<K, PList<V>>) this).entrySet()) {
			all = all.aggiungiList(entry.getValue());
		}
		return all;
	}

	public PHashMap(Map<K, V> map) {
		super(map);
	}

	public PMap<K, V> forEach(String name, Object... args) throws Exception {
		for (Map.Entry<K, V> entry : entrySet()) {
			if (entry.getValue() instanceof PList) {
				((PList) entry.getValue()).forEach(name, args);
			}
		}
		return this;
	}

	public PMap<K, V> forEach(PMapExecution<K, V> ex, Object... args) throws Exception {
		for (Map.Entry<K, V> entry : entrySet()) {
			ex.ex(entry, args);
		}
		return this;
	}

	public <T> PMap<K, PList<T>> map(String name, Class<T> c, Object... args) throws Exception {
		PMap<K, PList<T>> mappa = new PHashMap<K, PList<T>>();
		for (Map.Entry<K, V> entry : entrySet()) {
			if (entry.getValue() instanceof PList) {
				mappa.put(entry.getKey(), ((PList) entry.getValue()).map(name, c, args));
			}
		}
		return mappa;
	}

	public V getValue(K key) {
		return get(key);
	}

	public Integer keys() {
		return keySet().size();
	}

	public <K, V> void rimuoviMappaLista(K elem, V o) throws Exception {
		if (null != this) {
			PMap<K, PList<V>> map = (PMap<K, PList<V>>) this;
			PList<V> lista = (PList<V>) get(elem);
			p.safe(lista).remove(o);
			if (p.Null(lista)) {
				map.remove(elem);
			} else {
				map.put(elem, lista);
			}
		}
	}

	public <K, V extends BaseDaoEntity> void rimuoviMappaListaEnt(K elem, PList<V> elementi) throws Exception {
		if (null != this) {
			PMap<K, PList<V>> map = (PMap<K, PList<V>>) this;
			PList<V> lista = (PList<V>) get(elem);
			PList<V> nuova = p.pl();
			for (V v : p.safe(lista)) {
				nuova.add(v);
			}
			for (V item : p.safe(elementi)) {
				for (Iterator<V> iterator = nuova.iterator(); iterator.hasNext();) {
					V v = iterator.next();
					if (p.is(v.getPk(), item.getPk())) {
						iterator.remove();
					}
				}
			}
			if (p.Null(nuova)) {
				map.remove(elem);
			} else {
				map.put(elem, nuova);
			}
		}
	}

	public <K, V> void aggiungiMappaLista(K elem, V o) {
		if (null != this) {
			PMap<K, PList<V>> map = (PMap<K, PList<V>>) this;
			PList<V> lista = p.safe((PList<V>) get(elem));
			lista.add(o);
			map.put(elem, lista);
		}
	}

	public <K, V> void aggiungiMappaLista(K elem, V o, Integer limite) {
		if (null != this) {
			PMap<K, PList<V>> map = (PMap<K, PList<V>>) this;
			PList<V> lista = p.safe((PList<V>) get(elem));
			lista.setLimit(limite);
			lista.addElement(o);
			map.put(elem, lista);
		}
	}

	public <K, V> void aggiungiMappaLista(K elem, PList<V> listaDaAggiungere) {
		if (null != this) {
			PMap<K, PList<V>> map = (PMap<K, PList<V>>) this;
			PList<V> lista = p.safe((PList<V>) get(elem));
			lista.aggiungiList(listaDaAggiungere);
			map.put(elem, lista);
		}
	}

	public <K, V extends BaseDaoEntity> void aggiungiMappaListaEnt(K elem, V o) throws Exception {
		if (null != this) {
			PMap<K, PList<V>> map = (PMap<K, PList<V>>) this;
			PList<V> lista = p.safe((PList<V>) get(elem));
			if (p.tutte(p.notNull(o.getPk()), lista.narrow("pk").contains(o.getPk()))) {
				p.logError("Impossibile inserire l'entity ", o.getClass().getName(), ". Conflitto di chiave primaria per pk=", o.getPk());
			} else {
				lista.add(o);
			}
			map.put(elem, lista);
		}
	}

}
