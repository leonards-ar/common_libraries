/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: ConfigurationSet.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 25, 2006 (2:09:14 PM) 
 */
package leonards.common.conf;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import leonards.common.base.CommonUtils;
import leonards.common.util.StringUtils;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class ConfigurationSet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6162826691800374661L;
	
	protected static final boolean NULL_BOOLEAN_VALUE = false;
	protected static final int NULL_INTEGER_VALUE = -1;
	protected static final long NULL_LONG_VALUE = -1L;
	
	public static final String CONF_KEY_PREFIX_TOKEN = ".";
	
	// Configuration parameters are stored here
	private Properties conf = null;
	
	/**
	 * 
	 */
	public ConfigurationSet() {
		super();
	}

	/**
	 * @return
	 */
	protected Properties getConf() throws ConfigurationException {
		return conf;
	}

	/**
	 * @param conf The conf to set.
	 */
	protected void setConf(Properties conf) {
		this.conf = conf;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		try {
			return getConf() != null ? getConf().getProperty(key) : null;	
		} catch(ConfigurationException ex) {
			return null;
		}
		
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws ConfigurationException
	 */
	public boolean hasParameter(String key) throws ConfigurationException {
		return getString(key) != null;
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getString(String key, String defaultValue) {
		String value = getString(key);
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key) {
		return getInteger(key, null);
	}
	
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public long getNativeLong(String key, long defaultValue) {
		Long num = getLong(key, new Long(defaultValue));
		return num != null ? num.longValue() : defaultValue;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public long getNativeLong(String key) {
		return getNativeLong(key, NULL_LONG_VALUE);
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String key, int defaultValue) {
		Integer num = getInteger(key, new Integer(defaultValue));
		return num != null ? num.intValue() : defaultValue;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		return getInt(key, NULL_INTEGER_VALUE);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Long getLong(String key) {
		return getLong(key, null);
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Long getLong(String key, Long defaultValue) {
		try {
			String value = getString(key);
			return value != null ? new Long(value) : defaultValue;
		} catch( Throwable ex ) {
			return defaultValue;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Integer getInteger(String key, Integer defaultValue) {
		try {
			String value = getString(key);
			return value != null ? new Integer(value) : defaultValue;
		} catch( Throwable ex ) {
			return defaultValue;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Boolean getBoolean(String key) throws ConfigurationException {
		return getBoolean(key, null);
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Boolean getBoolean(String key, Boolean defaultValue) {
		String value = getString(key);
		if(value != null) {
			return StringUtils.getBooleanValue(value);
		} else {
			return defaultValue;
		}
	}
	
	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public boolean getBool(String key, boolean defaultValue) {
		Boolean value = getBoolean(key, new Boolean(defaultValue));
		return value != null ? value.booleanValue() : defaultValue;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBool(String key) {
		return getBool(key, false);
	}
	
	/**
	 * 
	 * @return
	 * @throws ConfigurationException
	 */
	public Enumeration getConfigurationKeys() throws ConfigurationException {
		return getConf() != null ? getConf().keys() : null;
	}
	
	/**
	 * 
	 * @param prefix
	 * @return
	 * @throws ConfigurationException
	 */
	public ConfigurationSet getSubset(String prefix) throws ConfigurationException {
		if (getConf() != null) {
			ConfigurationSet subSet = new ConfigurationSet();
			subSet.setConf(new Properties());
			
	        prefix = prefix.endsWith(".") ? prefix : prefix + ".";
	        String aKey;
	        Enumeration keys = getConfigurationKeys();
	        while(keys.hasMoreElements()) {
	            aKey = (String)keys.nextElement();
	            if(aKey.startsWith(prefix)) {
	                // Found a match
	            	subSet.getConf().put(aKey.substring(prefix.length()), getString(aKey));
	            }
	        }
			return subSet;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public Iterator getConfigurationKeyPrefixes() throws ConfigurationException {
		Vector prefixes = new Vector();
		Enumeration confKeys = getConfigurationKeys();
		String aPrefix;
		while(confKeys.hasMoreElements()) {
			aPrefix = getConfigurationKeyPrefix(confKeys.nextElement().toString());
			if(CommonUtils.hasValue(aPrefix) && !prefixes.contains(aPrefix)) {
				prefixes.add(aPrefix);
			}
		}
		return prefixes.iterator();		
	}
	
	/**
	 * 
	 * @param configurationKey
	 * @return
	 */
	protected String getConfigurationKeyPrefix(String configurationKey) {
		if(configurationKey != null) {
			int idx = configurationKey.indexOf(CONF_KEY_PREFIX_TOKEN);
			return idx >= 0 ? configurationKey.substring(0, idx).trim() : configurationKey;
		} else {
			return null;
		}
	}		
}
