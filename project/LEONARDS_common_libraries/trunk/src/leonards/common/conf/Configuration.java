/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: Configuration.java
 *
 * Property of Leonards / Mindpool
 * Created on 05/05/2004
 */
package leonards.common.conf;

import java.util.*;

import leonards.common.util.StringUtils;

/**
 * @author Mariano
 *
 * This class is the abstraction of a Configuration.
 * A Configuration is a set of pairs name/value
 */
public class Configuration extends ConfigurationSet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3336704144990257460L;

	// The name of the configuration to load.
	private String name = null;
	
	private ConfigurationSet configuration = null;

	/**
	 *
	 */
	public Configuration() {
		super();
	}
	
	/**
	 * Default Constructor
	 * @param name
	 * @throws ConfigurationException
	 */	
	public Configuration(String name) throws ConfigurationException {
		super();
		setName(name);
		load();
	}

	/**
	 * This method returns the name of this configuration.
	 * An entry related to this name must be defined in the
	 * configuration.properties file
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param newName
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * 
	 * @return
	 * @throws ConfigurationException
	 */
	protected String getType() throws ConfigurationException {
		String type = getConfigurationProperties().getString(getName() + ".type");
		if(!StringUtils.hasValue(type)) {
			throw new ConfigurationException("The configuration <" + getName() + "> is not properly configured. Parameter <" + getName() + ".type> is missing.");
		}
		return type;
	}
	
	/**
	 * @return
	 */
	protected ConfigurationSet getConfigurationProperties() throws ConfigurationException {
		if( configuration == null ) {
			Properties configurationProperties = new Properties();
			try {
				//:TODO: The configuration file can be passed as an environment variable!
				configurationProperties.load(getClass().getResourceAsStream("/configuration.properties"));
				configuration = new ConfigurationSet();
				configuration.setConf(configurationProperties);
			} catch( Exception ex ) {
				throw new ConfigurationException("Could not load <configuration.properties>", ex);
			}
		}
		return configuration;
	}
	

	/**
	 * 
	 * @throws ConfigurationException
	 */
	public void load() throws ConfigurationException {
		String type = getType();
		ConfigurationSet conf =  getConfigurationProperties().getSubset(getName());
		setConf(ConfigurationRetrieverFactory.createRetriever(type, conf).retrieveConfiguration());
	}
}
