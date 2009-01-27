/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web
 * File: InvalidPasswordException.java
 *
 * Property of Leonards / Mindpool
 * Created on 17/06/2004
 */
package leonards.common.web;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class InvalidPasswordException extends LogonException {

	private static final long serialVersionUID = -3994365693474862953L;

	/**
	 * 
	 */
	public InvalidPasswordException() {
		super();
	}

	/**
	 * @param message
	 */
	public InvalidPasswordException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public InvalidPasswordException(
		int code,
		String message,
		Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public InvalidPasswordException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public InvalidPasswordException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public InvalidPasswordException(Throwable cause) {
		super(cause);
	}

}
