/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: ConfigurationRetriever.java
 *
 * Property of Leonards / Mindpool
 * Created on 05/05/2004
 */
package leonards.common.conf;

import java.util.*;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
abstract class ConfigurationRetriever {

	/**
	 * Default Constructor
	 */
	public ConfigurationRetriever() {
		super();
	}
	
	/**
	 * This method retrieves the configuration from its source.
	 * @return A java.util.Properties object with the
	 *         configuration.
	 * @throws ConfigurationException When the configuration could not be retrieved.
	 */
	public abstract Properties retrieveConfiguration() throws ConfigurationException;
	
	/**
	 * This method setups the retriever with the sub configuration
	 * obtained from the configuration.propeties file.
	 * @param conf A configuration set containing the paremeters
	 * @throws ConfigurationException When the parameters are invalid
	 */
	public abstract void setup(ConfigurationSet conf) throws ConfigurationException;

}
