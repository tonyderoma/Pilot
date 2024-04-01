package it.eng.pilot;

import java.util.Map;

/**
 * Interfaccia che definisce i metodi disponibili per la classe PHashMap.
 * 
 * @author Antonio Corinaldi
 * 
 */
public interface PMap<K, V> extends Map<K, V> {

	/**
	 * Inserisce il valore v corrispondente alla chiave k
	 * 
	 * @param k
	 * @return V
	 */
	V put(K k, V v);

	/**
	 * Recupera il value corrispondente alla chiave key passata
	 * 
	 * @param key
	 * @return V
	 */
	V getValue(K key);

	/**
	 * Ritorna il numero di chiavi della mappa
	 * 
	 * @return Integer
	 */
	Integer keys();

	/**
	 * Rimuove l'elemento o dalla lista di elementi corrispondente alla chiave
	 * elem
	 * 
	 * @param <K>
	 * @param <V>
	 * @param elem
	 * @param o
	 * @throws Exception
	 */
	<K, V> void rimuoviMappaLista(K elem, V o) throws Exception;

	/**
	 * Aggiunge l'elemento o alla lista di elementi corrispondente alla chiave
	 * elem
	 * 
	 * @param <K>
	 * @param <V>
	 * @param elem
	 * @param o
	 */
	<K, V> void aggiungiMappaLista(K elem, V o);

	/**
	 * Aggiunge l'elemento o di tipo Entity alla lista di elementi
	 * corrispondente alla chiave elem solo se non c'è conflitto di chiave
	 * primaria (usato per DaoHelper con inMemory=true)
	 * 
	 * @param <K>
	 * @param <V>
	 * @param elem
	 * @param o
	 * @throws Exception
	 */
	<K, V extends BaseDaoEntity> void aggiungiMappaListaEnt(K elem, V o) throws Exception;

	/**
	 * Aggiunge l'elemento o alla lista di elementi corrispondente alla chiave
	 * elem fino a un massimo di elementi limite
	 * 
	 * @param <K>
	 * @param <V>
	 * @param elem
	 * @param o
	 * @param limite
	 */
	<K, V> void aggiungiMappaLista(K elem, V o, Integer limite);

	/**
	 * Rimuove dalla mappa la lista o corrispondente alla chiave elem
	 * 
	 * @param <K>
	 * @param <V>
	 * @param elem
	 * @param o
	 * @throws Exception
	 */
	<K, V extends BaseDaoEntity> void rimuoviMappaListaEnt(K elem, PList<V> o) throws Exception;

	/**
	 * Aggiunge una intera lista alla lista corrispondente alla chiave elem
	 * 
	 * @param <K>
	 * @param <V>
	 * @param elem
	 * @param listaDaAggiungere
	 */
	public <K, V> void aggiungiMappaLista(K elem, PList<V> listaDaAggiungere);

	/**
	 * 
	 * Ritorna la lista complessiva formata dalla concatenazione di tutte le
	 * liste valore delle chiavi della mappa
	 * 
	 * @return PList<V>
	 */
	PList<V> collectAll();

	/**
	 * Per ogni elemento value della PMap<K,List<V>> esegue l'iterazione
	 * identificata da name, eseguendo il metodo del bean V della lista che è
	 * annotato con @Logic e il cui attributo name è identificato dal parametro
	 * name
	 * 
	 * @param name
	 * @param args
	 * @throws Exception
	 */
	PMap<K, V> forEach(String name, Object... args) throws Exception;

	/**
	 * Esegue la conversione di tipo identificata da name, eseguendo il metodo
	 * del bean K della lista che è annotato con @Mapping e il cui attributo
	 * name è identificato dal parametro name. Ritorna una nuova mappa key-value
	 * dove value è un oggetto PList<T> con T nuovo tipo derivante dal mapping
	 * 
	 * @param <T>
	 * @param name
	 * @param c
	 * @param args
	 * @return PMap<K, PList<T>>
	 * @throws Exception
	 */
	<T> PMap<K, PList<T>> map(String name, Class<T> c, Object... args) throws Exception;

	/**
	 * Esegue per ogni entry della mappa, la logica di business definita nel
	 * metodo ex della classe executor che implementa l'interfaccia
	 * PMapExecution
	 * 
	 * @param executor
	 * @param args
	 * @return PMap<K,V>
	 * @throws Exception
	 */
	PMap<K, V> forEach(PMapExecution<K, V> executor, Object... args) throws Exception;
}
