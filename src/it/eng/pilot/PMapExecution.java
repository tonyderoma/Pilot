package it.eng.pilot;

/**
 * Interfaccia che definisce il metodo ex che dovrà essere implementato con la
 * logica di business. L'istanza della classe che implementa questa interfaccia
 * dovrà essere passata al metodo forEach della classe PHashMap
 * 
 * @author Antonio Corinaldi
 * 
 */
public interface PMapExecution<K, V> {

	void ex(PMap.Entry<K, V> e, Object... args);
}
