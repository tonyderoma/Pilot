package it.eng.pilot;

import java.io.Serializable;

/**
 * Classe di mappatura di oggetti chiave-valore.
 * 
 * @param <K>
 * @param <V>
 * @author Antonio Corinaldi
 */
public class KeyValue<K, V> implements Serializable {

	private static final long serialVersionUID = -5645824073924133415L;
	private K key;
	private V value;

	public KeyValue(K k, V v) {
		setKey(k);
		setValue(v);
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
