/*
 * File: HibernateSessionManagerException.java
 * Created on 12/05/2005
 * 
 */
package leonards.common.hibernate;

import leonards.common.base.NestedException;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class HibernateSessionManagerException extends NestedException {

	private static final long serialVersionUID = 3173637553374654999L;

	/**
	 * Constructor
	 * 
	 */
	public HibernateSessionManagerException() {
		super();
	}

	/**
	 * Constructor
	 * @param message
	 */
	public HibernateSessionManagerException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message
	 * @param cause
	 */
	public HibernateSessionManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * @param code
	 * @param message
	 * @param cause
	 */
	public HibernateSessionManagerException(
		int code,
		String message,
		Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * Constructor
	 * @param code
	 * @param cause
	 */
	public HibernateSessionManagerException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * Constructor
	 * @param code
	 * @param message
	 */
	public HibernateSessionManagerException(int code, String message) {
		super(code, message);
	}

	/**
	 * Constructor
	 * @param cause
	 */
	public HibernateSessionManagerException(Throwable cause) {
		super(cause);
	}

}
