/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: ConfigurationException.java
 *
 * Property of Leonards / Mindpool
 * Created on 05/05/2004
 */
package leonards.common.conf;

import leonards.common.base.NestedException;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class ConfigurationException extends NestedException {

	private static final long serialVersionUID = 959820436728768977L;

	/**
	 * 
	 */
	public ConfigurationException() {
		super();
	}

	/**
	 * @param message
	 */
	public ConfigurationException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public ConfigurationException(Throwable cause) {
		super(cause);
	}

}
