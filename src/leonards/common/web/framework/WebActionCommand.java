/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web.framework
 * File: WebActionCommand.java
 *
 * Property of Leonards / Mindpool
 * Created on 22/06/2004
 */
package leonards.common.web.framework;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import leonards.common.log.Logger;
import leonards.common.log.LoggerFactory;
import leonards.common.util.StringUtils;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public abstract class WebActionCommand {
	public static final String GLOBAL_ERRORS = "validationErrors";
	public static final String GLOBAL_SUCCESS = "successMessage";
	
	public static final String ERRORS_ATTR_NAME = "currErrors";
	public static final String SUCCESS_ATTR_NAME = "currSuccess";
	
	private HttpServletRequest request = null;
	private HttpSession session = null;
	private String successPageBackup = null;
	private String errorPageBackup = null;
	private String validationErrorPageBackup = null;
	private String successPage = null;
	private String errorPage= null;
	private String validationErrorPage = null;
	private boolean checkSession = false;
	private Hashtable allErrors = null;
	
	private Logger logger = null;
	
	/**
	 * 
	 */
	public WebActionCommand() {
		super();
	}

	/**
	 * @return Returns the logger.
	 */
	protected Logger getLogger() {
		if (logger == null) {
			logger = LoggerFactory.createSilentLogger();
		}
		return logger;
	}

	/**
	 * @param logger The logger to set.
	 */
	protected void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	/**
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @return
	 */
	protected HttpSession getSession() {
		if( session == null ) {
			setSession(getRequest() != null ? getRequest().getSession() : null);
		}
		return session;
	}

	/**
	 * @param hashtable
	 */
	protected void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @param hashtable
	 */
	protected void setSession(HttpSession session) {
		this.session = session;
	}
	
	public abstract void execute() throws WebActionException;


	/**
	 * @return
	 */
	public String getErrorPage() {
		return errorPage;
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
	public void setSuccessPage(String string) {
		successPage = string;
	}

	/**
	 * @param string
	 */
	public void setValidationErrorPage(String string) {
		validationErrorPage = string;
	}
	
	protected String getStringParameter(String name) {
		return getRequest() != null ? getRequest().getParameter(name) : null;
	}
	
	public void setSuccessMessage(String name, String message) {
		removeValidatationErrors();
		putInSession(SUCCESS_ATTR_NAME, name);
		putInSession(name, message);
	}
	
	public void removeSuccessMessage() {
		String inSessionName = (String) getFromSession(SUCCESS_ATTR_NAME);
		if(StringUtils.hasValue(inSessionName)) {
			removeFromSession(inSessionName);
		}
		removeFromSession(GLOBAL_SUCCESS);
	}
	
	public void setValidationErrors(String name, Vector errors) {
		removeValidatationErrors();
		putInSession(ERRORS_ATTR_NAME, name);
		putInSession(name, errors);
	}
	
	public void removeValidatationErrors() {
		String inSessionName = (String) getFromSession(ERRORS_ATTR_NAME);
		if(StringUtils.hasValue(inSessionName)) {
			removeFromSession(inSessionName);
		}
		removeFromSession(GLOBAL_ERRORS);
	}
	
	protected Date getDateParameter(String name) throws WebActionException {
		try {
			SimpleDateFormat df = new SimpleDateFormat(WebFrameworkConfiguration.singleton().getShortDateFormat());
			return getRequest() != null && hasParameterValue(name) ? df.parse(getRequest().getParameter(name)) : null;
		} catch( Throwable ex ) {
			throw new WebActionException("Could not obtain and parse date [" + name + "] from request.", ex);
		}
	}
	
	protected boolean isValidDateParameter(String name) {
		try {
			// :TODO: Make this validation better 
			getDateParameter(name);
			return true;
		} catch( WebActionException ex ) {
			return false;
		}
	}
	
	protected Hashtable getNonFrameworkRequestParameters() {
		Hashtable params = new Hashtable();
		Enumeration keys = getRequest().getParameterNames();
		String aKey;
		while( keys.hasMoreElements() ) {
			aKey = keys.nextElement().toString();
			if( !aKey.equalsIgnoreCase(WebActionRouterServlet.ACTION_PARAMETER)) {
				params.put(aKey, getStringParameter(aKey));
			}
		}
		return params;
	}
	
	protected Integer getIntegerParameter(String name) {
		return new Integer(getStringParameter(name));
	}

	protected Double getDoubleParameter(String name) {
		return new Double(getStringParameter(name));
	}

	protected boolean isValidDoubleParameter(String name) {
		try {
			getDoubleParameter(name);
			return true;
		} catch( Exception ex ) {
			return false;
		}
	}
		
	protected boolean hasParameterValue(String name) {
		String value = getStringParameter(name);
		return value != null && value.trim().length() > 0; 
	}
	
	protected boolean isValidIntegerParameter(String name) {
		try {
			getIntegerParameter(name);
			return true;
		} catch( Exception ex ) {
			return false;
		}
	}
	
	public Object getFromSession(String name) {
		return getSession().getAttribute(name);
	}
	
	public void putInSession(String name, Object obj) {
		try {
			getSession().setAttribute(name, obj);
		} catch( Throwable ex ) {
		}		
	}

	public void putInRequest(String name, Object obj) {
		try {
			getRequest().setAttribute(name, obj);
		} catch( Throwable ex ) {
		}		
	}

	public void removeFromSession(String name) {
		try {
			getSession().removeAttribute(name);
		} catch( Throwable ex ) {
		}
		
	}
	
	/**
	 * @return
	 */
	protected Vector getErrors() {
		Vector errors = (Vector)getAllErrors().get(GLOBAL_ERRORS);
		if( errors == null ) {
			errors = new Vector();
			getAllErrors().put(GLOBAL_ERRORS, errors);
		}
		return errors;
	}
	
	protected boolean hasErrors() {
		return getErrors().size() > 0;
	}

	/**
	 * @return
	 */
	protected Vector getErrors(String name) {
		Vector errors = (Vector)getAllErrors().get(name);
		if( errors == null ) {
			errors = new Vector();
			getAllErrors().put(name, errors);
		}
		return errors;
	}
	
	protected boolean hasErrors(String name) {
		return getErrors(name).size() > 0;
	}
	
	/**
	 * @param message
	 */
	public void setSuccessMessage(String message) {
		setSuccessMessage(GLOBAL_SUCCESS, message);
	}
	
	public void backupResponsePages() {
		setSuccessPageBackup(getSuccessPage());
		setErrorPageBackup(getErrorPage());
		setValidationErrorPageBackup(getValidationErrorPage());
	}
	
	public void restoreResponsePages() {
		setSuccessPage(getSuccessPageBackup());
		setErrorPage(getErrorPageBackup());
		setValidationErrorPage(getValidationErrorPageBackup());
	}

	/**
	 * @return
	 */
	private String getErrorPageBackup() {
		return errorPageBackup;
	}

	/**
	 * @return
	 */
	private String getSuccessPageBackup() {
		return successPageBackup;
	}

	/**
	 * @return
	 */
	private String getValidationErrorPageBackup() {
		return validationErrorPageBackup;
	}

	/**
	 * @param string
	 */
	private void setErrorPageBackup(String string) {
		errorPageBackup = string;
	}

	/**
	 * @param string
	 */
	private void setSuccessPageBackup(String string) {
		successPageBackup = string;
	}

	/**
	 * @param string
	 */
	private void setValidationErrorPageBackup(String string) {
		validationErrorPageBackup = string;
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
	
	/**
	 * Must be overwritten to perform real checks
	 * @throws WebActionInvalidSessionException
	 */
	public void checkSession() throws WebActionInvalidSessionException {
		return;
	}

	/**
	 * @return Returns the allErrors.
	 */
	protected Hashtable getAllErrors() {
		if (allErrors == null) {
			allErrors = new Hashtable();
		}
		return allErrors;
	}
}
