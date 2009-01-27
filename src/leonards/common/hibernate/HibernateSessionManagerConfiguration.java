/*
 * File: HibernateSessionManagerConfiguration.java
 * Created on 12/05/2005
 * 
 */
package leonards.common.hibernate;

import java.util.Enumeration;
import java.util.Hashtable;

import leonards.common.conf.Configuration;
import leonards.common.conf.ConfigurationException;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class HibernateSessionManagerConfiguration {
	private Hashtable sessions = null;
	protected static String CONFIGURATION_NAME = "hibernate";
	
	protected static String HIBERNATE_CONFIGURATION_FILE_SUFFIX = "config_file";
	

	/**
	 * 
	 * Constructor
	 * @throws HibernateSessionManagerException
	 */	
	public HibernateSessionManagerConfiguration() throws HibernateSessionManagerException {
		super();
		setup();
	}
	
	/**
	 * 
	 * @throws HibernateSessionManagerException
	 */
	private void setup() throws HibernateSessionManagerException {
		try {
			Configuration config = new Configuration(CONFIGURATION_NAME);
			Enumeration keys = config.getConfigurationKeys();
			String sessionName;
			HibernateSessionConfigurationEntry anEntry;
			while(keys.hasMoreElements()) {
				sessionName = getKeyPrefix(keys.nextElement().toString());
				if(sessionName != null && !getSessions().containsKey(sessionName.toUpperCase())) {
					anEntry = new HibernateSessionConfigurationEntry(sessionName.toUpperCase(), config.getString(sessionName + "." + HIBERNATE_CONFIGURATION_FILE_SUFFIX));
					getSessions().put(anEntry.getSessionName(), anEntry);
				}
			}
		} catch(ConfigurationException ex) {
			throw new HibernateSessionManagerException("Could not retrieve configuration [" + CONFIGURATION_NAME + "]", ex);
		} catch(Throwable ex) {
			throw new HibernateSessionManagerException("Could not retrieve configuration [" + CONFIGURATION_NAME + "]", ex);
		}
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	private String getKeyPrefix(String key) {
		int idx = key != null ? key.indexOf('.') : -1;
		return idx >= 0 ? key.substring(0, idx) : key;
	}
	
	/**
	 * @return
	 */
	public Hashtable getSessions() {
		if(sessions == null) {
			sessions = new Hashtable();
		}
		return sessions;
	}

	/**
	 * 
	 * @return
	 */
	public Enumeration getSessionEntries() {
		return getSessions().elements();
	}
}



