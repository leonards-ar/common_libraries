/*
 * Created on Jul 14, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package leonards.common.web.framework;

/**
 * @author Mariano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WebActionInvalidSessionException extends WebActionException {

	private static final long serialVersionUID = -4227935735544318404L;

	/**
	 * 
	 */
	public WebActionInvalidSessionException() {
		super();
	}

	/**
	 * @param message
	 */
	public WebActionInvalidSessionException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WebActionInvalidSessionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public WebActionInvalidSessionException(
		int code,
		String message,
		Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public WebActionInvalidSessionException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public WebActionInvalidSessionException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public WebActionInvalidSessionException(Throwable cause) {
		super(cause);
	}

}
