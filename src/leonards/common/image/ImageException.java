/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.image
 * File: ImageException.java
 *
 * Property of Leonards / Mindpool
 * Created on May 6, 2006 (4:13:57 PM) 
 */
package leonards.common.image;

import leonards.common.base.NestedException;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class ImageException extends NestedException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6037626289718748084L;

	/**
	 * 
	 */
	public ImageException() {
		super();
	}

	/**
	 * @param message
	 */
	public ImageException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ImageException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public ImageException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public ImageException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public ImageException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public ImageException(Throwable cause) {
		super(cause);
	}

}
