/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.ldap
 * File: LDAPException.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 22, 2006 (12:59:56 AM) 
 */
package leonards.common.ldap;

import leonards.common.base.NestedException;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class LDAPException extends NestedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1220777755286608188L;

	/**
	 * 
	 */
	public LDAPException() {
		super();
	}

	/**
	 * @param message
	 */
	public LDAPException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LDAPException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public LDAPException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public LDAPException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public LDAPException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public LDAPException(Throwable cause) {
		super(cause);
	}

}
