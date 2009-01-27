/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.ldap
 * File: AuthenticationException.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 23, 2006 (12:58:02 AM) 
 */
package leonards.common.ldap;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class AuthenticationException extends LDAPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6434067405937564392L;

	/**
	 * 
	 */
	public AuthenticationException() {
		super();
	}

	/**
	 * @param message
	 */
	public AuthenticationException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public AuthenticationException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public AuthenticationException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public AuthenticationException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public AuthenticationException(Throwable cause) {
		super(cause);
	}

}
