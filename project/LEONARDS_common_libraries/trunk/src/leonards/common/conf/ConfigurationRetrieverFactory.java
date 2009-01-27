/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: ConfigurationRetrieverFactory.java
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
public class ConfigurationRetrieverFactory {
	private static Hashtable classes = null;
	
	/**
	 * 
	 */
	public ConfigurationRetrieverFactory() {
		super();
	}
	
	/**
	 * 
	 * @param type
	 * @param params
	 * @return
	 * @throws ConfigurationException
	 */
	public static ConfigurationRetriever createRetriever(String type, ConfigurationSet conf) throws ConfigurationException {
		try {
			ConfigurationRetriever retriever = (ConfigurationRetriever)Class.forName(getClasses().get(type.toUpperCase()).toString()).newInstance();
			retriever.setup(conf); 
			return retriever;
		} catch( ConfigurationException confEx ) {
			throw confEx;
		} catch( Exception ex ) {
			throw new ConfigurationException("Could not instantiate retriever for <" + type + ">", ex);
		}
	}

	/**
	 * @return
	 */
	protected static Hashtable getClasses() {
		if( classes == null ) {
			classes = new Hashtable();
			classes.put("FILE", PropertiesFileRetriever.class.getName());
		}
		return classes;
	}

}
