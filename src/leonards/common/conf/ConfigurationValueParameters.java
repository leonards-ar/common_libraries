/*
 * File: ConfigurationValueParameters.java
 * Created on 16/05/2005
 * 
 */
package leonards.common.conf;

import java.util.Properties;
import java.util.StringTokenizer;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.2 $
 */
public class ConfigurationValueParameters {
	private Properties properties = null;
	protected final static int NULL_INT_VALUE = -1;
	protected final static String PARAM_TOKEN = ";";
	protected final static String PARAM_VALUE_TOKEN = "=";
	
	/**
	 * 
	 * Constructor
	 * @param params
	 * @throws ConfigurationException
	 */	
	public ConfigurationValueParameters(String params) throws ConfigurationException {
		super();
		parseParameters(params);
	}
	
	/**
	 * 
	 * @return
	 */
	protected Properties getProperties() {
		if(properties == null) {
			properties = new Properties();
		}
		return properties;
	}

	/**
	 * 
	 * @param params
	 * @throws ConfigurationException
	 */
	protected void parseParameters(String params) throws ConfigurationException {
		try {
			StringTokenizer st = new StringTokenizer(params, PARAM_TOKEN);
			String aParam;
			while( st.hasMoreTokens() ) {
				aParam = st.nextToken();
				getProperties().put(getKey(aParam), getValue(aParam));
			}
		} catch( Exception ex ) {
			throw new ConfigurationException("Could not parse parameters [" + params + "]", ex);
		}
	}
	
	/**
	 * This method extracts the key from the string
	 * containig 'key=value'.
	 * @param param The complete 'key=value' string
	 * @return The key
	 */
	private String getKey(String param) {
		if(param != null) {
			int idx = param.indexOf(PARAM_VALUE_TOKEN);
			return idx >= 0 ? param.substring(0, idx).trim() : param.trim();
		} else {
			return null;
		}
	}

	/**
	 * This method extracts the value from the string
	 * containig 'key=value'.
	 * @param param The complete 'key=value' string
	 * @return The value
	 */
	private String getValue(String param) {
		if(param != null) {
			int idx = param.indexOf(PARAM_VALUE_TOKEN);
			return idx >= 0 ? param.substring(idx+1, param.length()).trim() : "";
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return getProperties().getProperty(key);
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getProperty(String key, String defaultValue) {
		return getProperties().getProperty(key, defaultValue);
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Integer getPropertyAsInteger(String key, Integer defaultValue) {
		String num = getProperty(key);
		return num != null ? new Integer(num) : defaultValue;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Integer getPropertyAsInteger(String key) {
		return getPropertyAsInteger(key, null);
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Boolean getPropertyAsBoolean(String key, Boolean defaultValue) {
		String value = getProperty(key);
		if(value != null) {
			return new Boolean(value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true"));	
		} else {
			return defaultValue;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Boolean getPropertyAsBoolean(String key) {
		return getPropertyAsBoolean(key, Boolean.FALSE);
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public boolean getPropertyAsBool(String key, boolean defaultValue) {
		return getPropertyAsBoolean(key, new Boolean(defaultValue)).booleanValue();
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean getPropertyAsBool(String key) {
		return getPropertyAsBool(key, false);
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getPropertyAsInt(String key, int defaultValue) {
		Integer num = getPropertyAsInteger(key, new Integer(defaultValue));
		return num != null ? num.intValue() : NULL_INT_VALUE;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public int getPropertyAsInt(String key) {
		return getPropertyAsInt(key, NULL_INT_VALUE);
	}
}
