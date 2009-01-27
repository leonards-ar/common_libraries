/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.log
 * File: NoSuchLoggerException.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 24, 2006 (9:40:01 PM) 
 */
package leonards.common.log;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class NoSuchLoggerException extends LogException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4600757112962501975L;

	/**
	 * 
	 */
	public NoSuchLoggerException() {
		super();
	}

	/**
	 * @param message
	 */
	public NoSuchLoggerException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoSuchLoggerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public NoSuchLoggerException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public NoSuchLoggerException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public NoSuchLoggerException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public NoSuchLoggerException(Throwable cause) {
		super(cause);
	}

}
