package it.eng.pilot;

import java.sql.Connection;

import org.apache.log4j.Logger;

/**
 * Classe base per i bean di mapping ORM, per query di selezione eseguite con
 * l'ausilio di DaoHelper. Imposta la connessione e offre i metodi di
 * impostazione delle query scritte manualmente o recuperate esternamente da
 * apposito file di query (default queries.properties).
 * 
 * @author Antonio Corinaldi
 * 
 */
public class DaoHelperBaseBeanSelect extends DaoHelperBaseResult {

	private static final long serialVersionUID = -5513048393959461500L;
	private transient Connection connection;
	public PList<String> container = null;

	private String query;

	/**
	 * Imposta il logger passato
	 * 
	 * @param log
	 */
	public void setExternalLogger(Logger log) {
		this.log = log;
		setLog(log);
	}

	public DaoHelperBaseBeanSelect(Connection conn) {
		setConnection(conn);
	}

	public DaoHelperBaseBeanSelect(Connection conn, PList<String> container) {
		this(conn);
		setContainer(container);
	}

	public DaoHelperBaseBeanSelect() {
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void setLogger(Logger logger) {
		this.log = logger;
	}

	public String getQuery() {
		return query;
	}

	/**
	 * Imposta la query sql passata direttamente (senza leggere da file di
	 * queries.properties)
	 * 
	 * @param query
	 * @throws Exception
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Imposta la query dando il nome della query presente nel file di
	 * properties queries.properties messo sotto lo stesso pkg della classe che
	 * lo chiama
	 * 
	 * @param queryName
	 * @throws Exception
	 */
	public void setQueryByName(String queryName) throws Exception {
		setQueryName(queryName);
		setQuery(getQueryProp(queryName));
	}

	/**
	 * Imposta la query dando il nome della query presente nel file di
	 * properties queryFile (indicato nella forma del package in cui si trova
	 * it.eng.queries.properties ad esempio)
	 * 
	 * @param queryName
	 * @throws Exception
	 */
	public void setQueryByName(String queryFile, String queryName) throws Exception {
		setQueryName(queryName);
		setQuery(getQueryProp(queryFile, queryName));
	}

	public PList<String> getContainer() {
		return container;
	}

	public void setContainer(PList<String> container) {
		this.container = container;
	}

}
