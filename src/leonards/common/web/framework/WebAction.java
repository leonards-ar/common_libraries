/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web.framework
 * File: WebAction.java
 *
 * Property of Leonards / Mindpool
 * Created on 22/06/2004
 */
package leonards.common.web.framework;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class WebAction {
	private String name = null;
	private String commandClass = null;
	private String errorPage = null;
	private String successPage = null;
	private String validationErrorPage = null; 
	private boolean checkSession = false;
	
	/**
	 * 
	 */
	public WebAction() {
		super();
	}

	/**
	 * @return
	 */
	public String getErrorPage() {
		return errorPage;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getSuccessPage() {
		return successPage;
	}

	/**
	 * @return
	 */
	public String getValidationErrorPage() {
		return validationErrorPage;
	}


	/**
	 * @param string
	 */
	public void setErrorPage(String string) {
		errorPage = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setSuccessPage(String string) {
		successPage = string;
	}

	/**
	 * @param string
	 */
	public void setValidationErrorPage(String string) {
		validationErrorPage = string;
	}

	/**
	 * @return
	 */
	public String getCommandClass() {
		return commandClass;
	}

	/**
	 * @param string
	 */
	public void setCommandClass(String string) {
		commandClass = string;
	}

	/**
	 * @return
	 */
	public boolean isCheckSession() {
		return checkSession;
	}

	/**
	 * @param b
	 */
	public void setCheckSession(boolean b) {
		checkSession = b;
	}
	
}
