/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.ldap
 * File: NameAlreadyBoundException.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 25, 2006 (9:30:46 AM) 
 */
package leonards.common.ldap;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class NameAlreadyBoundException extends LDAPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3869273274579892302L;

	/**
	 * 
	 */
	public NameAlreadyBoundException() {
		super();
	}

	/**
	 * @param message
	 */
	public NameAlreadyBoundException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NameAlreadyBoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public NameAlreadyBoundException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public NameAlreadyBoundException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public NameAlreadyBoundException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public NameAlreadyBoundException(Throwable cause) {
		super(cause);
	}

}
