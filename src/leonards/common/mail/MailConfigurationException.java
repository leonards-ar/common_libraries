/*
 * File: MailConfigurationException.java
 * Created on 20/05/2005
 * 
 */
package leonards.common.mail;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class MailConfigurationException extends MailException {

	private static final long serialVersionUID = -8415846609511363454L;

	/**
	 * Constructor
	 * 
	 */
	public MailConfigurationException() {
		super();
	}

	/**
	 * Constructor
	 * @param message
	 */
	public MailConfigurationException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message
	 * @param cause
	 */
	public MailConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * @param code
	 * @param message
	 * @param cause
	 */
	public MailConfigurationException(
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
	public MailConfigurationException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * Constructor
	 * @param code
	 * @param message
	 */
	public MailConfigurationException(int code, String message) {
		super(code, message);
	}

	/**
	 * Constructor
	 * @param cause
	 */
	public MailConfigurationException(Throwable cause) {
		super(cause);
	}

}
