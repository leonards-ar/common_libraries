/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web.framework
 * File: RouterServlet.java
 *
 * Property of Leonards / Mindpool
 * Created on 22/06/2004
 */
package leonards.common.web.framework;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import leonards.common.log.Logger;
import leonards.common.log.LoggerFactory;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class WebActionRouterServlet extends HttpServlet {
	private static final long serialVersionUID = -8464044998200485514L;
	public static final String LAST_ERROR = "lastError";
	public static final String ACTION_PARAMETER = "webaction";
	
	public static final String DEFAULT_LOGGER_NAME = "web-framework";
	private String errorPage = null;
	private boolean servletLoggerEnabled = true;
	protected Logger logger = null;
	
	/**
	 * 
	 */
	public WebActionRouterServlet() {
		super();
	}

	/**
	 * @param config
	 * @throws ServletException
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String loggerName = null;
		try {
			loggerName = WebFrameworkConfiguration.singleton().getLoggerName();
			logger = LoggerFactory.createSilentLogger(loggerName);
		} catch (WebFrameworkException ex) {
			log("Could not retrieve logger named <" + loggerName + ">", ex);
		}
		
		try {
			this.servletLoggerEnabled = WebFrameworkConfiguration.singleton().isServletLoggerEnabled();
		} catch (WebFrameworkException ex) {
			log("Could not retrieve if servlet logger is enabled", ex);
		}
		
		try {
			this.errorPage = WebFrameworkToolkit.getDocumentForwardUrl(WebFrameworkConfiguration.singleton().getDefaultErrorPage());
		} catch (WebFrameworkException ex) {
			log("Could not retrieve error page", ex);
		}
	}

	protected void logError(String message) {
		logError(message, null);
	}

	protected void logWarning(String message) {
		logWarning(message, null);
	}

	protected void logInfo(String message) {
		logInfo(message, null);
	}

	protected void logDebug(String message) {
		logDebug(message, null);
	}
	
	protected void logError(String message, Throwable ex) {
		servletLog(message, ex);
		if(logger != null) {
			logger.logError(message, ex);
		}
	}
	
	protected void logWarning(String message, Throwable ex) {
		servletLog(message, ex);
		if(logger != null) {
			logger.logWarning(message, ex);
		}
	}

	protected void logInfo(String message, Throwable ex) {
		servletLog(message, ex);
		if(logger != null) {
			logger.logInfo(message, ex);
		}
	}

	protected void logDebug(String message, Throwable ex) {
		servletLog(message, ex);
		if(logger != null) {
			logger.logDebug(message, ex);
		}
	}
	
	/**
	 * 
	 * @param message
	 * @param ex
	 */
	private void servletLog(String message, Throwable ex) {
		if(servletLoggerEnabled) {
			if(ex != null) {
				log(message, ex);
			} else {
				log(message);
			}
		}
		
	}

	/** 
	 * @param request
	 * @param response
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			performTask(request, response);
		} catch( WebFrameworkException ex ) {
			logError("Error performing action [" + request.getParameter(ACTION_PARAMETER) + "]. Forwarding to error page [" + errorPage + "]", ex);
			request.getSession().setAttribute(LAST_ERROR, ex.getNestedMessage());
			forward(errorPage, request, response);
		} catch( Throwable ex ) {
			logError("Unexpected error performing action [" + request.getParameter(ACTION_PARAMETER) + "]. Forwarding to error page [" + errorPage + "]", ex);
			request.getSession().setAttribute(LAST_ERROR, ex.getMessage());
			forward(errorPage, request, response);
		}
	}

	/** 
	 * @param request
	 * @param response
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void forward(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(url).forward(request, response);
	}
	/** 
	 * @param request
	 * @param response
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, WebFrameworkException {
		WebActionCommand cmd = null;
		String commandName = request.getParameter(ACTION_PARAMETER);
		if( commandName == null || commandName.length() <= 0 ) {
			throw new WebFrameworkException("Missing parameter [" + ACTION_PARAMETER + "] in request.");
		}
		try {
			cmd = WebActionCommandFactory.create(commandName, request, response);
			cmd.setLogger(logger);
			cmd.backupResponsePages();
			cmd.removeValidatationErrors();
			cmd.removeFromSession(LAST_ERROR);
			cmd.removeSuccessMessage();
			if( cmd.isCheckSession() ) {
				cmd.checkSession();
			}
			cmd.execute();
			forward(WebFrameworkToolkit.getDocumentForwardUrl(cmd.getSuccessPage()), request, response);
		} catch(WebActionInvalidSessionException ex) {
			cmd.removeSuccessMessage();
			request.getSession().invalidate();
			logWarning(ex.getMessage(), ex);
			response.sendRedirect(WebFrameworkToolkit.getDocumentUrl(WebFrameworkConfiguration.singleton().getInvalidSessionForwardPath()));
		} catch(WebActionFormValidationException valEx) {
			cmd.removeSuccessMessage();
			cmd.setValidationErrors(valEx.getErrorsName(), valEx.getErrors());
			logWarning(valEx.getMessage(), valEx);
			forward(WebFrameworkToolkit.getDocumentForwardUrl(cmd.getValidationErrorPage()), request, response);
		} catch(WebActionErrorException errEx) {
			cmd.removeSuccessMessage();
			logError(errEx.getMessage(), errEx);
			cmd.putInSession(LAST_ERROR, errEx.getNestedMessage());
			forward(WebFrameworkToolkit.getDocumentForwardUrl(cmd.getErrorPage()), request, response);
		} catch(WebFrameworkException ex) {
			cmd.removeSuccessMessage();
			throw ex;
		} finally {
			if( cmd != null ) {
				cmd.restoreResponsePages();
			}
		}
	}
}
