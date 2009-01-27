/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web
 * File: LogonException.java
 *
 * Property of Leonards / Mindpool
 * Created on 13/06/2004
 */
package leonards.common.web;

import leonards.common.base.NestedException;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class LogonException extends NestedException {

	private static final long serialVersionUID = 8526084312818123564L;

	/**
	 * 
	 */
	public LogonException() {
		super();
	}

	/**
	 * @param message
	 */
	public LogonException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LogonException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public LogonException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public LogonException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public LogonException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public LogonException(Throwable cause) {
		super(cause);
	}

}
