/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web
 * File: InvalidUsernameException.java
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
public class InvalidUsernameException extends LogonException {

	private static final long serialVersionUID = -1495341309373840715L;

	/**
	 * 
	 */
	public InvalidUsernameException() {
		super();
	}

	/**
	 * @param message
	 */
	public InvalidUsernameException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidUsernameException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public InvalidUsernameException(
		int code,
		String message,
		Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public InvalidUsernameException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public InvalidUsernameException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public InvalidUsernameException(Throwable cause) {
		super(cause);
	}

}
