/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web.framework
 * File: WebActionFormValidationException.java
 *
 * Property of Leonards / Mindpool
 * Created on 22/06/2004
 */
package leonards.common.web.framework;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class WebActionFormValidationException extends WebActionException {

	private static final long serialVersionUID = 2095642237582021752L;
	private Vector errors = null;
	private String errorsName = null;
	
	/**
	 * 
	 */
	public WebActionFormValidationException() {
		super();
	}

	/**
	 * @param message
	 */
	public WebActionFormValidationException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WebActionFormValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param code
	 * @param message
	 * @param cause
	 */
	public WebActionFormValidationException(int code, String message, Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * @param code
	 * @param cause
	 */
	public WebActionFormValidationException(int code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * @param code
	 * @param message
	 */
	public WebActionFormValidationException(int code, String message) {
		super(code, message);
	}

	/**
	 * @param cause
	 */
	public WebActionFormValidationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param errors
	 */
	public WebActionFormValidationException(Vector errors) {
		this(errors, WebActionCommand.GLOBAL_ERRORS);
	}

	/**
	 * @param errors
	 */
	public WebActionFormValidationException(Vector errors, String errorsName) {
		super();
		setErrors(errors);
		setErrorsName(errorsName);
	}
	
	protected Vector getErrors() {
		if( errors == null ) {
			setErrors(new Vector());
		}
		return errors;
	}

	public Iterator errors() {
		return getErrors().iterator();
	}
	
	/**
	 * @param vector
	 */
	public void setErrors(Vector vector) {
		errors = vector;
	}

	/**
	 * @return Returns the errorsName.
	 */
	public String getErrorsName() {
		return errorsName;
	}

	/**
	 * @param errorsName The errorsName to set.
	 */
	public void setErrorsName(String errorsName) {
		this.errorsName = errorsName;
	}

}
