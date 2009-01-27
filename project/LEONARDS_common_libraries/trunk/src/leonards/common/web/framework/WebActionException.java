/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web.framework
 * File: WebActionException.java
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
public class WebActionException extends WebFrameworkException {

	private static final long serialVersionUID = 3807333780645294944L;

	/**
	 * 
	 */
	public WebActionException() {
		super();
	}

	/**
	 * @param message
	 */
	public WebActionException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WebActionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public WebActionException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public WebActionException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public WebActionException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public WebActionException(Throwable cause) {
		super(cause);
	}

}
