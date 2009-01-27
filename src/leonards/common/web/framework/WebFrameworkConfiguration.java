/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web.framework
 * File: FrameworkConfiguration.java
 *
 * Property of Leonards / Mindpool
 * Created on 22/06/2004
 */
package leonards.common.web.framework;

import java.util.Enumeration;
import java.util.Hashtable;

import leonards.common.conf.*;
import leonards.common.util.StringUtils;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class WebFrameworkConfiguration {
	private Hashtable defaults = null;
	private Hashtable commons = null;
	private Hashtable webActions = null;
	private static WebFrameworkConfiguration singleton = null;
	public final static String DEFAULT_PREFIX = "default";
	public final static String COMMON_PREFIX = "common";
	
	public final static String COMMON_SERVLET_LOGGER = COMMON_PREFIX + ".servlet.logger.enabled";
	public final static String COMMON_LOGGER = COMMON_PREFIX + ".logger.enabled";
	public final static String COMMON_LOGGER_NAME = COMMON_PREFIX + ".logger.name";
	
	public final static String DEFAULT_ERROR_PAGE = DEFAULT_PREFIX + ".error.page";
	public final static String DEFAULT_APP_CONTEXT = DEFAULT_PREFIX + ".application.context";
	public final static String DEFAULT_DOC_PATH = DEFAULT_PREFIX + ".document.path";
	public final static String DEFAULT_SERVLET_PATH = DEFAULT_PREFIX + ".servlet.path";
	public final static String DEFAULT_IMAGES_PATH = DEFAULT_PREFIX + ".images.path";
	public final static String DEFAULT_INVALID_SESSION_FORWARD_PATH = DEFAULT_PREFIX + ".invalid.session.forward.path";
	public final static String DEFAULT_SHORT_DATE_FORMAT = DEFAULT_PREFIX + ".short.date.format";
	public final static String DEFAULT_LONG_DATE_FORMAT = DEFAULT_PREFIX + ".long.date.format";
	public final static String DEFAULT_LOCALE = DEFAULT_PREFIX + ".locale";
	public final static String DEFAULT_TIME_FORMAT = DEFAULT_PREFIX + ".time.format";
	public final static String DEFAULT_DATE_TIME_FORMAT = DEFAULT_PREFIX + ".date.time.format";
	public final static String WA_CMD_CLASS_SUFFIX = ".command.class";
	public final static String WA_ERROR_PAGE_SUFFIX = ".error.page";
	public final static String WA_SUCCESS_PAGE_SUFFIX = ".success.page";
	public final static String WA_VALIDATION_ERROR_PAGE_SUFFIX = ".validation.error.page";
	public final static String WA_CHECK_SESSION_SUFFIX = ".check.session";
	

	/**
	 * 
	 */
	public WebFrameworkConfiguration() {
		super();
	}

	public String getLoggerName() {
		return (String) getCommons().get(COMMON_LOGGER_NAME);
	}
	
	public String getApplicationContext() {
		return (String)getDefaults().get(DEFAULT_APP_CONTEXT);	
	}

	public String getTimeFormat() {
		return (String)getDefaults().get(DEFAULT_TIME_FORMAT);	
	}

	public String getShortDateFormat() {
		return (String)getDefaults().get(DEFAULT_SHORT_DATE_FORMAT);	
	}

	public String getLongDateFormat() {
		return (String)getDefaults().get(DEFAULT_LONG_DATE_FORMAT);	
	}

	public String getLocale() {
		return (String)getDefaults().get(DEFAULT_LOCALE);	
	}


	public String getDateTimeFormat() {
		return (String)getDefaults().get(DEFAULT_DATE_TIME_FORMAT);	
	}	

	public String getServletPath() {
		return (String)getDefaults().get(DEFAULT_SERVLET_PATH);
	}

	public String getDocumentPath() {
		return (String)getDefaults().get(DEFAULT_DOC_PATH);
	}
	
	public String getImagesPath() {
		return (String)getDefaults().get(DEFAULT_IMAGES_PATH);
	}

	

	public String getInvalidSessionForwardPath() {
		return (String)getDefaults().get(DEFAULT_INVALID_SESSION_FORWARD_PATH);
	}


	public String getDefaultErrorPage() {
		return (String)getDefaults().get(DEFAULT_ERROR_PAGE);
	}

	/**
	 * 
	 * @return
	 * @throws WebFrameworkException
	 */	
	public static WebFrameworkConfiguration singleton() throws WebFrameworkException {
		if( singleton == null ) {
			try {
				singleton = new WebFrameworkConfiguration();
				singleton.setup(new Configuration("web-framework")); 
			} catch(ConfigurationException ex) {
				throw new WebFrameworkException("Could not retrieve web framework configuration", ex);
			}
		}
		return singleton;
	}
	
	public boolean isServletLoggerEnabled() {
		Boolean val = StringUtils.getBooleanValue((String)getCommons().get(COMMON_SERVLET_LOGGER));
		return val != null ? val.booleanValue() : false;
	}

	public boolean isLoggerEnabled() {
		Boolean val = StringUtils.getBooleanValue((String)getCommons().get(COMMON_LOGGER));
		return val != null ? val.booleanValue() : false;
	}
	
	private void setup(Configuration conf) throws WebFrameworkException {
		try {
			Enumeration keys = conf.getConfigurationKeys();
			String aKey;
			WebAction aWebAction;
			while( keys.hasMoreElements() ) {
				aKey = keys.nextElement().toString();
				if( isDefaultKey(aKey) ) {
					getDefaults().put(aKey, conf.getString(aKey));
				} else if(isCommonKey(aKey)) {
					getCommons().put(aKey, conf.getString(aKey));
				} else if( !getWebActions().containsKey(getKeyPrefix(aKey)) ) {
					aWebAction = new WebAction();
					buildWebAction(aWebAction, getKeyPrefix(aKey), conf);
					getWebActions().put(aWebAction.getName(), aWebAction);
				}
			}
		} catch( ConfigurationException ex ) {
			throw new WebFrameworkException("Could not obtain configuration.", ex);
		}
		
	}

	private void buildWebAction(WebAction webAction, String name, Configuration conf ) throws WebFrameworkException {
		try {
			webAction.setName(name);
			webAction.setCommandClass(conf.getString(name + WA_CMD_CLASS_SUFFIX));
			webAction.setErrorPage(conf.getString(name + WA_ERROR_PAGE_SUFFIX, conf.getString(DEFAULT_ERROR_PAGE)));
			webAction.setSuccessPage(conf.getString(name + WA_SUCCESS_PAGE_SUFFIX));
			webAction.setValidationErrorPage(conf.getString(name + WA_VALIDATION_ERROR_PAGE_SUFFIX));
			if( conf.hasParameter(name + WA_CHECK_SESSION_SUFFIX) ) {
				webAction.setCheckSession(conf.getString(name + WA_CHECK_SESSION_SUFFIX).equalsIgnoreCase("yes"));
			} else {
				webAction.setCheckSession(false);
			}
		} catch( ConfigurationException ex ) {
			throw new WebFrameworkException("Could not retrieve configuration for web action [" + name + "]", ex);
		}
	}
	
	private boolean isDefaultKey(String key) {
		String prefix = getKeyPrefix(key);
		return prefix != null ? prefix.equals(DEFAULT_PREFIX) : false;
	}

	private boolean isCommonKey(String key) {
		String prefix = getKeyPrefix(key);
		return prefix != null ? prefix.equals(COMMON_PREFIX) : false;
	}

	private String getKeyPrefix(String key) {
		int idx = key != null ? key.indexOf('.') : -1;
		return idx >= 0 ? key.substring(0, idx) : key;
	}
	/**
	 * @return
	 */
	public Hashtable getDefaults() {
		if( defaults == null ) {
			setDefaults(new Hashtable());
		}
		return defaults;
	}

	public Hashtable getCommons() {
		if( commons == null ) {
			setCommons(new Hashtable());
		}
		return commons;
	}
	
	public void setCommons(Hashtable commons) {
		this.commons = commons;
	}
	
	/**
	 * @return
	 */
	public Hashtable getWebActions() {
		if( webActions == null ) {
			setWebActions(new Hashtable());
		}
		return webActions;
	}

	/**
	 * @param hashtable
	 */
	public void setDefaults(Hashtable hashtable) {
		defaults = hashtable;
	}

	/**
	 * @param hashtable
	 */
	public void setWebActions(Hashtable hashtable) {
		webActions = hashtable;
	}
	
	public WebAction getWebAction(String name) {
		return (WebAction)getWebActions().get(name);
	}

}
