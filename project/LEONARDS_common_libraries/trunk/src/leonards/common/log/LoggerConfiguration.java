/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: Configuration.java
 *
 * Property of Leonards / Mindpool
 * Created on 05/05/2004
 */
package leonards.common.log;

import leonards.common.conf.ConfigurationSet;

/**
 * @author Mariano
 *
 * This class is the abstraction 
 */
public class LoggerConfiguration {
	private String name = null;
	private String className = null;
	private ConfigurationSet config  = null;
	private String level = null;
	private boolean silent = false;
	private String dateFormat = null;
	private boolean logStackTrace = false;
	private boolean enabled = false;
	
	/**
	 * 
	 */
	public LoggerConfiguration() {
		this(null, null, null);
	}

	public LoggerConfiguration(String name, String className, ConfigurationSet config) {
		super();
		setName(name);
		setClassName(className);
		setConfig(config);
	}
	
	/**
	 * @return
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param className
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** 
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof LoggerConfiguration) || getName() == null ) {
			return false;
		}
		LoggerConfiguration conf = (LoggerConfiguration)obj;
		return getName().equals(conf.getName());
	}

	/** 
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return getName() != null ? getName().hashCode() : super.hashCode();
	}

	/** 
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getName();
	}

	/**
	 * @return
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return
	 */
	public boolean isSilent() {
		return silent;
	}

	/**
	 * @param b
	 */
	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	/**
	 * @return
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @return Returns the logStackTrace.
	 */
	public boolean isLogStackTrace() {
		return logStackTrace;
	}

	/**
	 * @param logStackTrace The logStackTrace to set.
	 */
	public void setLogStackTrace(boolean logStackTrace) {
		this.logStackTrace = logStackTrace;
	}

	/**
	 * @return Returns the config.
	 */
	public ConfigurationSet getConfig() {
		return config;
	}

	/**
	 * @param config The config to set.
	 */
	public void setConfig(ConfigurationSet config) {
		this.config = config;
	}

	/**
	 * @return Returns the enabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled The enabled to set.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
