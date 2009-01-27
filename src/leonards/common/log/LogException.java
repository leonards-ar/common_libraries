/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: Configuration.java
 *
 * Property of Leonards / Mindpool
 * Created on 05/05/2004
 */
package leonards.common.log;

import leonards.common.base.NestedException;

/**
 * @author Mariano
 *
 * This class is the abstraction 
 */
public class LogException extends NestedException {

	private static final long serialVersionUID = 4806549557397112723L;

	/**
	 * Constructor
	 * 
	 */
	public LogException() {
		super();
	}

	/**
	 * Constructor
	 * @param message
	 */
	public LogException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message
	 * @param cause
	 */
	public LogException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * @param code
	 * @param message
	 * @param cause
	 */
	public LogException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * Constructor
	 * @param code
	 * @param cause
	 */
	public LogException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * Constructor
	 * @param code
	 * @param message
	 */
	public LogException(int code, String message) {
		super(code, message);
	}

	/**
	 * Constructor
	 * @param cause
	 */
	public LogException(Throwable cause) {
		super(cause);
	}

}
