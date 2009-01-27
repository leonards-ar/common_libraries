/*
 * File: HibernateSessionConfigurationEntry.java
 * Created on 12/05/2005
 * 
 */
package leonards.common.hibernate;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class HibernateSessionConfigurationEntry {
	private String sessionName = null;
	private String hibernateConfigurationFile = null;
	
	/**
	 * Constructor
	 * 
	 */
	public HibernateSessionConfigurationEntry() {
		this(null, null);
	}

	/**
	 * 
	 * Constructor
	 * @param sessionName
	 * @param hibernateConfigurationFile
	 */
	public HibernateSessionConfigurationEntry(String sessionName, String hibernateConfigurationFile) {
		super();
		setSessionName(sessionName);
		setHibernateConfigurationFile(hibernateConfigurationFile);
	}

	/**
	 * @return
	 */
	public String getHibernateConfigurationFile() {
		return hibernateConfigurationFile;
	}

	/**
	 * @return
	 */
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * @param string
	 */
	public void setHibernateConfigurationFile(String string) {
		hibernateConfigurationFile = string;
	}

	/**
	 * @param string
	 */
	public void setSessionName(String string) {
		sessionName = string;
	}

}
