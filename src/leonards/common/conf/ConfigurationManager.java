/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: ConfigurationManager.java
 *
 * Property of Leonards / Mindpool
 * Created on Jul 2, 2006 (1:30:01 AM) 
 */
package leonards.common.conf;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class ConfigurationManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4743767719374442602L;
	private static final Map configurations = new Hashtable();

	/**
	 * 
	 */
	private ConfigurationManager() {
		super();
	}
	
	/**
	 * 
	 * @param configurationName
	 * @return
	 * @throws ConfigurationException
	 */
	public static synchronized Configuration getInstance(String configurationName) throws ConfigurationException {
		Configuration conf = (Configuration) configurations.get(configurationName);
		if (conf == null) {
			conf = new Configuration(configurationName);
			configurations.put(configurationName, conf);
		}
		return conf;
	}

}
