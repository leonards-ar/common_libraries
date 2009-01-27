/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.ldap
 * File: LDAPConnection.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 22, 2006 (12:57:35 AM) 
 */
package leonards.common.ldap;

import java.util.Hashtable;
import java.util.Map;

import leonards.common.conf.Configuration;
import leonards.common.conf.ConfigurationException;
import leonards.common.log.Logger;
import leonards.common.log.LoggerFactory;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public abstract class LDAPConnection {
	
	protected final Logger logger = LoggerFactory.createSilentLogger("ldap");
	
	protected static final String DEFAULT_CONFIGURATION_NAME = "ldap";
	protected static final String DEFAULT_CONNECTION_NAME = "default";

	private String name = null;
	private String configurationName = null;
	
	private static final Map instances = new Hashtable();
	
	/**
	 * 
	 */
	public LDAPConnection() {
		super();
	}

	/**
	 * 
	 * @param name
	 * @param configurationName
	 * @return
	 * @throws LDAPException
	 */
	public static synchronized LDAPConnection getInstance(String name, String configurationName) throws LDAPException {
		LDAPConnection conn = null;
		if(!instances.containsKey(name + configurationName)) {
			conn = getConnection(name, configurationName);
			instances.put(name + configurationName, conn);
		} else {
			conn = (LDAPConnection) instances.get(name + configurationName);
		}

		if(conn.isClosed()) {
			Configuration conf = getConnectionConfiguration(configurationName);
			conn.bind(conf.getString(name + ".ldap.bind.dn"), conf.getString(name + ".ldap.bind.password"));
		}
		
		return conn;
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws LDAPException
	 */
	public static LDAPConnection getInstance(String name) throws LDAPException {
		return getInstance(name, DEFAULT_CONFIGURATION_NAME);
	}

	/**
	 * 
	 * @return
	 * @throws LDAPException
	 */
	public static LDAPConnection getInstance() throws LDAPException {
		return getInstance(DEFAULT_CONNECTION_NAME, DEFAULT_CONFIGURATION_NAME);
	}
	
	/**
	 * 
	 * @return
	 * @throws LDAPException
	 */
	public static LDAPConnection getConnection() throws LDAPException {
		return getConnection(DEFAULT_CONNECTION_NAME, DEFAULT_CONFIGURATION_NAME);
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws LDAPException
	 */
	public static LDAPConnection getConnection(String name) throws LDAPException {
		return getConnection(name, DEFAULT_CONFIGURATION_NAME);
	}
	
	/**
	 * 
	 * @param name
	 * @param configurationName
	 * @return
	 * @throws LDAPException
	 */
	public static LDAPConnection getConnection(String name, String configurationName) throws LDAPException {
		try {
			Configuration conf = getConnectionConfiguration(configurationName);
			LDAPConnection conn = (LDAPConnection)Class.forName(conf.getString(name + ".ldap.connection.class")).newInstance();
			conn.setName(name);
			conn.setConfigurationName(configurationName);
			conn.initialize(conf);
			return conn;
		} catch (InstantiationException ex) {
			throw new LDAPException("Could not instantiate connection [" + name + "] from configuration [" + configurationName + "]", ex);
		} catch (IllegalAccessException ex) {
			throw new LDAPException("Could not instantiate connection [" + name + "] from configuration [" + configurationName + "]", ex);
		} catch (ClassNotFoundException ex) {
			throw new LDAPException("Could not instantiate connection [" + name + "] from configuration [" + configurationName + "]", ex);
		} catch( Throwable ex ) {
			throw new LDAPException("Could not obtain connection [" + name + "] from configuration [" + configurationName + "]", ex);
		}
	}

	/**
	 * 
	 * @param configurationName
	 * @return
	 * @throws LDAPException
	 */
	protected static Configuration getConnectionConfiguration(String configurationName) throws LDAPException {
		try {
			return new Configuration(configurationName);
		} catch(ConfigurationException ex) {
			throw new LDAPException("Could not retrieve configuration [" + configurationName + "]", ex);
		}
	}
	
	/**
	 * 
	 * @param conf
	 * @throws LDAPException
	 */
	protected abstract void initialize(Configuration conf) throws LDAPException;
	
	/**
	 * 
	 * @param dn
	 * @param password
	 * @throws LDAPException
	 */
	public abstract void bind(String dn, String password) throws LDAPException;

	/**
	 * 
	 * @param baseDn
	 * @param filter
	 * @param filterArgs
	 * @param controls
	 * @return
	 * @throws LDAPException
	 */
	public abstract LDAPResultSet search(String baseDn, String filter, Object[] filterArgs, LDAPSearchControls controls) throws LDAPException;

	/**
	 * 
	 * @param baseDn
	 * @param filter
	 * @param controls
	 * @return
	 * @throws LDAPException
	 */
	public abstract LDAPResultSet search(String baseDn, String filter, LDAPSearchControls controls) throws LDAPException;

	/**
	 * 
	 * @param baseDn
	 * @param filter
	 * @return
	 * @throws LDAPException
	 */
	public abstract LDAPResultSet search(String baseDn, String filter) throws LDAPException;

	/**
	 * 
	 * @param baseDn
	 * @param matchingAttributes
	 * @return
	 * @throws LDAPException
	 */
	public abstract LDAPResultSet search(String baseDn, LDAPEntryAttributes matchingAttributes) throws LDAPException;

	/**
	 * 
	 * @param dn
	 * @return
	 * @throws LDAPException
	 */
	public abstract LDAPEntry lookup(String dn) throws LDAPException;

	/**
	 * Returns the LDAP entry matching the given dn. The entry will contain
	 * only the defined attributes.
	 * @param dn
	 * @param attributes Attributes names to fetch.
	 * @return
	 * @throws LDAPException
	 */
	public abstract LDAPEntry lookup(String dn, String attributes[]) throws LDAPException;
	
	/**
	 * 
	 * @param entry
	 * @throws LDAPException
	 */
	public abstract void addEntry(LDAPEntry entry) throws LDAPException;
	
	/**
	 * 
	 * @param entry
	 * @throws LDAPException
	 */
	public abstract void updateEntry(LDAPEntry entry) throws LDAPException;
	
	/**
	 * 
	 * @param entry
	 * @throws LDAPException
	 */
	public abstract void deleteEntry(LDAPEntry entry) throws LDAPException;

	/**
	 * 
	 * @param dn
	 * @throws LDAPException
	 */
	public abstract void deleteEntry(String dn) throws LDAPException;
	
	/**
	 * 
	 * @param dn
	 * @param attribute
	 * @throws LDAPException
	 */
	public abstract void addAttribute(String dn, LDAPEntryAttribute attribute) throws LDAPException;
	
	/**
	 * 
	 * @param dn
	 * @param attribute
	 * @throws LDAPException
	 */
	public abstract void updateAttribute(String dn, LDAPEntryAttribute attribute) throws LDAPException;

	/**
	 * 
	 * @param dn
	 * @param attribute
	 * @throws LDAPException
	 */
	public abstract void deleteAttribute(String dn, LDAPEntryAttribute attribute) throws LDAPException;
	
	/**
	 * 
	 * @throws LDAPException
	 */
	public abstract void close() throws LDAPException;
	
	/**
	 * 
	 * @return
	 * @throws LDAPException
	 */
	public abstract boolean isClosed() throws LDAPException;
	
	/**
	 * @return Returns the configurationName.
	 */
	public String getConfigurationName() {
		return configurationName;
	}

	/**
	 * @param configurationName The configurationName to set.
	 */
	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
}
