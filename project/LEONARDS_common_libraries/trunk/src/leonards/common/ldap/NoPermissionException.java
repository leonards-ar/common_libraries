/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.ldap
 * File: NoPermissionException.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 25, 2006 (9:12:54 AM) 
 */
package leonards.common.ldap;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class NoPermissionException extends LDAPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5581541484202999596L;

	/**
	 * 
	 */
	public NoPermissionException() {
		super();
	}

	/**
	 * @param message
	 */
	public NoPermissionException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoPermissionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public NoPermissionException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public NoPermissionException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public NoPermissionException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public NoPermissionException(Throwable cause) {
		super(cause);
	}

}
