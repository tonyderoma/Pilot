package it.eng.pilot;

import java.sql.Connection;

/**
 * Classe per la esecuzione delle query di selezione in modalità BeanSelect
 * scritte nel file queries.properties ed eseguite tramite l'ausilio di
 * DaoHelper.
 * 
 * @author Antonio Corinaldi
 * 
 */
public class DaoHelperBeanSelect extends DaoHelperBaseBeanSelect {

	private static final long serialVersionUID = 6309516532474613252L;

	public DaoHelperBeanSelect(Connection conn) {
		setConnection(conn);
	}

	public DaoHelperBeanSelect(Connection conn, PList<String> container) {
		this(conn);
		setContainer(container);
	}

	public DaoHelperBeanSelect() {
	}

}