package it.eng.pilot;

import java.io.Serializable;

/**
 * Interfaccia che deve essere implementata dalla classi che vogliono essere
 * Entity ai fini di insert, delete, update, upsert e select.
 * 
 * Per delete e update è NECESSARIO fornire la WHERE condition da applicare. In
 * assenza di WHERE condition non verrà eseguita la query di delete o di update
 * per ragioni di sicurezza.
 * 
 * @author Antonio Corinaldi
 * 
 */
public interface Entity extends Serializable {

	PList<Where> getWhereCondition();

	/**
	 * Lista di condizioni di where
	 * 
	 * @param wc
	 */
	BaseEntity setWhereCondition(PList<Where> wc);

	/**
	 * Ritorna l'elenco delle variabili istanza escluse dall'update
	 * 
	 * @return PList<String>
	 */
	PList<String> getFieldsToExcludeInUpdate();

	/**
	 * Lista di variabili istanza le cui corrispondenti colonne saranno escluse
	 * dall'update a prescindere dal valore che contengono
	 * 
	 * 
	 * @param fieldsToExclude
	 * @return BaseEntity
	 */
	BaseEntity setFieldsToExcludeInUpdate(String... fieldsToExclude);

	/**
	 * Lista di variabili istanza le cui corrispondenti colonne saranno escluse
	 * dall'update a prescindere dal valore che contengono
	 * 
	 * 
	 * @param fieldsToExclude
	 * @return BaseEntity
	 */
	BaseEntity setFieldsToExcludeInUpdate(PList<String> fieldsToExclude);

	/**
	 * Sinonimo di setFieldsToExcludeInUpdate
	 * 
	 * @param fieldsToExclude
	 * @return BaseEntity
	 */
	BaseEntity exclude(String... fieldsToExclude);

	/**
	 * Ritorna l'elenco di variabili istanza su cui eseguire l'ordinamento
	 * 
	 * @return PList<String>
	 */
	PList<String> getOrderBy();

	/**
	 * Lista di campi di ordinamento ascendente della select
	 * 
	 * @param orderBy
	 */
	BaseEntity orderByASC(String... orderBy);

	/**
	 * Lista di campi di ordinamento discendente della select
	 * 
	 * @param orderBy
	 */
	BaseEntity orderByDESC(String... orderBy);

	/**
	 * Lista di variabili istanza le cui corrispondenti colonne saranno
	 * aggiornate escludendo tutte le altre che non saranno aggiornate anche se
	 * provided
	 * 
	 * 
	 * @param fieldsToUpdate
	 * @return BaseEntity
	 */
	BaseEntity setFieldsToUpdate(String... fieldsToUpdate);

	/**
	 * Lista di variabili istanza le cui corrispondenti colonne saranno
	 * aggiornate escludendo tutte le altre che non saranno aggiornate anche se
	 * provided
	 * 
	 * 
	 * @param fieldsToUpdate
	 * @return BaseEntity
	 */
	BaseEntity setFieldsToUpdate(PList<String> fieldsToUpdate);

	/**
	 * Ritorna l'elenco delle variabili istanza da aggiornare
	 * 
	 * @return PList<String>
	 */
	PList<String> getFieldsToUpdate();

	/**
	 * Sinonimo di setFieldsToUpdate
	 * 
	 * @param fieldsToUpdate
	 * @return BaseEntity
	 */
	BaseEntity setOnly(String... fieldsToUpdate);

}
