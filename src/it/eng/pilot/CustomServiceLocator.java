package it.eng.pilot;

import it.inpdap.common.servicelocator.ServiceLocator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * Classe per la chiamata locale e remota degli EJB per INPS. Chiamare il metodo
 * getInstance(bindingJndiName) esempio bindingJndiName url/bindingV0.
 * 
 * @author Antonio Corinaldi
 * 
 */
public class CustomServiceLocator extends PilotSupport {

	private ServiceLocator serviceLocator;
	private static CustomServiceLocator istanza;

	// esempio bindingJndiName url/bindingV0
	private CustomServiceLocator(String bindingJndiName) throws NamingException {
		super();
		createServiceLocatorInstance(bindingJndiName);
	}

	private CustomServiceLocator(String bindingJndiName, Logger log) throws NamingException {
		this(bindingJndiName);
		setLog(log);
	}

	private void createServiceLocatorInstance(String bindingJndiName) throws NamingException {
		URL url = null;
		try {
			url = ServiceLocator.getInstance().getUrl(bindingJndiName);
		} catch (final NamingException ne) {
			log("Risorsa URL non trovata: ", bindingJndiName);
		}

		// Se viene trovato il file di mapping (contesto locale), viene caricato
		// il corrispondente
		// file di properties, altrimenti (contesto server) viene caricato il
		// serviceLocator di
		// default
		if (notNull(url)) {
			final Properties props = new Properties();
			InputStream is = null;
			try {
				is = url.openStream();
				props.load(is);
			} catch (Exception ioe) {
				log("Risorsa URL non caricata: " + bindingJndiName);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			serviceLocator = ServiceLocator.getInstance(props);
		} else {
			serviceLocator = ServiceLocator.getInstance();
		}

	}

	// esempio bindingJndiName url/bindingV0
	public static CustomServiceLocator getInstance(String bindingJndiName) throws NamingException {
		if (istanza == null) {
			istanza = new CustomServiceLocator(bindingJndiName);
		}
		return istanza;
	}

	// esempio bindingJndiName url/bindingV0
	public static CustomServiceLocator getInstance(String bindingJndiName, Logger log) throws NamingException {
		if (istanza == null) {
			istanza = new CustomServiceLocator(bindingJndiName, log);
		}
		return istanza;
	}

	/**
	 * Restituisce il valore serviceLocator.
	 * 
	 * @return ServiceLocator
	 */
	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	/**
	 * Restituisce l'oggetto LocalHome dell'EJB
	 * 
	 * @param resourceName
	 *            esempio "java:comp/env/ejb/LettureNuoReg"
	 * @return EJBLocalHome
	 * @throws NamingException
	 */
	public <K extends EJBLocalHome> K getLocalHome(String resourceName) throws NamingException {
		return (K) getServiceLocator().getLocalHome(resourceName);
	}

	/**
	 * Restituisce l'oggetto RemoteHome dell'EJB
	 * 
	 * @param resourceName
	 *            esempio "java:comp/env/ejb/LettureNuoReg"
	 * @param resourceClass
	 * @return EJBHome
	 * @throws NamingException
	 */
	public <K extends EJBHome> K getRemoteHome(String resourceName, Class resourceClass) throws NamingException {
		return (K) getServiceLocator().getRemoteHome(resourceName, resourceClass);
	}

	/**
	 * Restituisce l'oggetto URL
	 * 
	 * @param envName
	 * @return URL
	 * @throws NamingException
	 */
	public URL getUrl(String envName) throws NamingException {
		return getServiceLocator().getUrl(envName);
	}

}
