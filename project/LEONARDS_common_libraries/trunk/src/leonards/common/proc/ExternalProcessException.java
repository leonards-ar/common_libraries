/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.proc
 * File: ExternalProcessException.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 22, 2006 (12:05:12 AM) 
 */
package leonards.common.proc;

import leonards.common.base.NestedException;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class ExternalProcessException extends NestedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6990613258323981255L;

	/**
	 * 
	 */
	public ExternalProcessException() {
		super();
	}

	/**
	 * @param message
	 */
	public ExternalProcessException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ExternalProcessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public ExternalProcessException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public ExternalProcessException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public ExternalProcessException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public ExternalProcessException(Throwable cause) {
		super(cause);
	}

}
