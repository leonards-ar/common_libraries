/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.ldap
 * File: NameNotFoundException.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 23, 2006 (12:56:19 AM) 
 */
package leonards.common.ldap;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class NameNotFoundException extends LDAPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4610170776459043832L;

	/**
	 * 
	 */
	public NameNotFoundException() {
		super();
	}

	/**
	 * @param message
	 */
	public NameNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NameNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public NameNotFoundException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public NameNotFoundException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public NameNotFoundException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public NameNotFoundException(Throwable cause) {
		super(cause);
	}

}
