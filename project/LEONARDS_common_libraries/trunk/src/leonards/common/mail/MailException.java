/*
 * File: MailException.java
 * Created on 20/05/2005
 * 
 */
package leonards.common.mail;

import leonards.common.base.NestedException;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class MailException extends NestedException {

	private static final long serialVersionUID = 5638777176336899578L;

	/**
	 * Constructor
	 * 
	 */
	public MailException() {
		super();
	}

	/**
	 * Constructor
	 * @param message
	 */
	public MailException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message
	 * @param cause
	 */
	public MailException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * @param code
	 * @param message
	 * @param cause
	 */
	public MailException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * Constructor
	 * @param code
	 * @param cause
	 */
	public MailException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * Constructor
	 * @param code
	 * @param message
	 */
	public MailException(int code, String message) {
		super(code, message);
	}

	/**
	 * Constructor
	 * @param cause
	 */
	public MailException(Throwable cause) {
		super(cause);
	}

}
