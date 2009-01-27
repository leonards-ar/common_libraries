/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web.framework
 * File: FrameworkException.java
 *
 * Property of Leonards / Mindpool
 * Created on 22/06/2004
 */
package leonards.common.web.framework;

import leonards.common.base.NestedException;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class WebFrameworkException extends NestedException {

	private static final long serialVersionUID = 7264162283654813741L;

	/**
	 * 
	 */
	public WebFrameworkException() {
		super();
	}

	/**
	 * @param message
	 */
	public WebFrameworkException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WebFrameworkException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public WebFrameworkException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public WebFrameworkException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public WebFrameworkException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public WebFrameworkException(Throwable cause) {
		super(cause);
	}

}
