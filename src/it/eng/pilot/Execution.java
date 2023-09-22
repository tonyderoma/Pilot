package it.eng.pilot;

/**
 * Interfaccia per la definizione della logica di business di una esecuzione
 * asincrona. La classe che implementa l'interfaccia deve implementare il metodo
 * execute la cui logica di business verrà eseguita in modalità asincrona
 * attraverso l'utilizzo del metodo della classe Pilot "stacca"
 * 
 * @author Antonio Corinaldi
 * 
 */
public interface Execution {

	void execute();

}
