package it.eng.pilot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

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
	private transient Logger logger = Logger.getLogger(getClass().getName());
	private final Pilot p = new Pilot(logger);

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

	public PHashMap(Logger log) {
		p.setLog(log);
		this.logger = log;
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

	public PHashMap<K, V> setLog(Logger log) {
		p.setLog(log);
		this.logger = log;
		return this;
	}

}
