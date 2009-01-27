/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web.framework
 * File: WebActionFactory.java
 *
 * Property of Leonards / Mindpool
 * Created on 22/06/2004
 */
package leonards.common.web.framework;

import javax.servlet.http.*;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class WebActionCommandFactory {

	/**
	 * 
	 */
	private WebActionCommandFactory() {
		super();
	}
	
	public static WebActionCommand create(String name, HttpServletRequest request, HttpServletResponse response) throws WebFrameworkException {
		WebActionCommand cmd = null;
		WebAction webAction = WebFrameworkConfiguration.singleton().getWebAction(name);
		
		if( webAction == null ) {
			throw new WebFrameworkException("Could not find web action for name [" + name + "].");
		}
		
		try {
			cmd = (WebActionCommand)Class.forName(webAction.getCommandClass()).newInstance();
		} catch( Exception ex ) {
			throw new WebFrameworkException("Could not instantiate class [" + webAction.getCommandClass() + "].", ex);
		}
		
		cmd.setErrorPage(webAction.getErrorPage());
		cmd.setSuccessPage(webAction.getSuccessPage());
		cmd.setValidationErrorPage(webAction.getValidationErrorPage());
		cmd.setRequest(request);
		cmd.setSession(request.getSession());
		cmd.setCheckSession(webAction.isCheckSession());
				
		return cmd;
	}

}
