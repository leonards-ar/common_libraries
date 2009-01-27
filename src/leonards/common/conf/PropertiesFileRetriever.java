/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: PropertiesFileRetriever.java
 *
 * Property of Leonards / Mindpool
 * Created on 05/05/2004
 */
package leonards.common.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import leonards.common.util.StringUtils;
/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class PropertiesFileRetriever extends ConfigurationRetriever {
	private boolean searchClasspath = true;
	private String file = null;
	
	/**
	 * 
	 */
	public PropertiesFileRetriever() {
		super();
	}

	/** 
	 * @return
	 * @throws ConfigurationException
	 * @see leonards.common.conf.ConfigurationRetriever#retrieveConfiguration()
	 */
	public Properties retrieveConfiguration() throws ConfigurationException {
		InputStream in = null;
		try {
			Properties config = new Properties();
			
			if( isSearchClasspath() ) {
				in = getClass().getResourceAsStream(getFile());
			} else {
				in = new FileInputStream(getFile());
			}
			config.load(in);
			return config;
		} catch(IOException ioEx) {
			throw new ConfigurationException("Could not open file <" + getFile() + ">", ioEx);
		} catch(Exception ex) {
			throw new ConfigurationException("Could retrieve configuration from file <" + getFile() + ">", ex);
		} finally {
			if(in != null) {
				try {
					in.close();
					in = null;
				} catch(Throwable ex) {
					//:TODO: What???
				}
			}
		}
	}

	/** 
	 * This method setups the retriever with the sub configuration
	 * obtained from the configuration.propeties file.
	 * @param conf A configuration set containing the paremeters
	 * @throws ConfigurationException When the parameters are invalid
	 * @see leonards.common.conf.ConfigurationRetriever#setup(java.lang.String)
	 */
	public void setup(ConfigurationSet conf) throws ConfigurationException {
		setSearchClasspath(conf.getBool("classpath", true));
		setFile(conf.getString("file"));
		if(!StringUtils.hasValue(getFile())) {
			throw new ConfigurationException("The file parameter must have a valid value");
		}
	}

	/**
	 * @return
	 */
	protected String getFile() {
		return file;
	}

	/**
	 * @return
	 */
	protected boolean isSearchClasspath() {
		return searchClasspath;
	}

	/**
	 * @param newFile
	 */
	protected void setFile(String newFile) {
		file = newFile;
	}

	/**
	 * @param newSearchClasspath
	 */
	protected void setSearchClasspath(boolean newSearchClasspath) {
		searchClasspath = newSearchClasspath;
	}

}
