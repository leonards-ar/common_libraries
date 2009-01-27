package leonards.common.sql;

import leonards.common.base.*;
;

/**
 * @author Mariano Capurro (25/10/2002)
 *
 * This class represents a database exception
 */
public class DBException extends NestedException {

	private static final long serialVersionUID = -6158056617307329290L;

	/**
	 * 
	 */
	public DBException() {
		super();
	}

	/**
	 * @param message
	 */
	public DBException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public DBException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public DBException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public DBException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public DBException(Throwable cause) {
		super(cause);
	}

}
