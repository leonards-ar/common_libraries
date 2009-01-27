/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web.framework
 * File: WebActionErrorException.java
 *
 * Property of Leonards / Mindpool
 * Created on 22/06/2004
 */
package leonards.common.web.framework;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class WebActionErrorException extends WebActionException {

	private static final long serialVersionUID = -5594443852204971022L;

	/**
	 * 
	 */
	public WebActionErrorException() {
		super();
	}

	/**
	 * @param message
	 */
	public WebActionErrorException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WebActionErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public WebActionErrorException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public WebActionErrorException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public WebActionErrorException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public WebActionErrorException(Throwable cause) {
		super(cause);
	}

}
