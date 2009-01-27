/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.ldap
 * File: InvalidAttributesException.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 25, 2006 (9:30:24 AM) 
 */
package leonards.common.ldap;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class InvalidAttributesException extends LDAPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1608293876428310202L;

	/**
	 * 
	 */
	public InvalidAttributesException() {
		super();
	}

	/**
	 * @param message
	 */
	public InvalidAttributesException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidAttributesException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public InvalidAttributesException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public InvalidAttributesException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public InvalidAttributesException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public InvalidAttributesException(Throwable cause) {
		super(cause);
	}

}
