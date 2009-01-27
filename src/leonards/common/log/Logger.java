/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: Configuration.java
 *
 * Property of Leonards / Mindpool
 * Created on 05/05/2004
 */
package leonards.common.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import leonards.common.base.CommonUtils;
import leonards.common.base.NestedException;
import leonards.common.conf.ConfigurationSet;
import leonards.common.util.StringUtils;

/**
 * @author Mariano
 *
 * This class is the abstraction 
 */
public abstract class Logger {
	private static Logger singleton = null;
	public static final int LOG_DEBUG = 400;
	public static final int LOG_INFO = 300;
	public static final int LOG_WARNING = 200;
	public static final int LOG_ERROR = 100;
	public static final int LOG_CRITICAL = 0;

	public static final String LOG_DEBUG_DESC = "debug";
	public static final String LOG_INFO_DESC = "info";
	public static final String LOG_WARNING_DESC = "warning";
	public static final String LOG_ERROR_DESC = "error";
	public static final String LOG_CRITICAL_DESC = "critic";
	public static final String LOG_OTHER_DESC = "other";
	
	private boolean enabled = true;
	private boolean logStackTrace = false;
	
	private int level = LOG_INFO;
	private boolean silentMode = false;
	private Object context = null;
	private String dateFormat = null;
	private SimpleDateFormat dateFormatter = null;
	
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
	
	protected static final String URL_PARAMS_TOKENS  = ";,";
	protected static final String URL_PARAM_VALUE_TOKENS  = ":=";
	
	/**
	 * 
	 */
	public Logger() {
		super();
	}

	/**
	 * 
	 * @param url
	 * @throws LogException
	 */
	public Logger(ConfigurationSet conf) throws LogException {
		this();
		setup(conf);
	}
	
	/**
	 * 
	 * @param conf
	 * @throws LogException
	 */
	public abstract void setup(ConfigurationSet conf) throws LogException;
	
	/**
	 * 
	 * @return
	 */
	public static Logger getInstance() throws LogException {
		if(singleton == null) {
			singleton = LoggerFactory.createLogger();
		}
		return singleton;
	}

	/**
	 * 
	 * @return
	 */
	public static Logger getInstanceOrNull() {
		try {
			return getInstance();
		} catch(Throwable ex) {
			return null;
		}
	}

	/**
	 * 
	 * @param level
	 * @param message
	 * @param context
	 * @throws LogException
	 */
	protected abstract void log(int level, String message, Object context) throws LogException;

	/**
	 * 
	 * @param level
	 * @param message
	 * @param context
	 */
	private void doLog(int level, Object message, Object context) {
		try {
			if( isEnabled() && getLevel() >= level ) {
			    log(level, String.valueOf(message), context);
			}
		} catch(Throwable ex) {
			handleException(ex);
		}
	}

	/**
	 * 
	 * @param message
	 * @param context
	 */
	protected void handleException(LogException ex) {
		if(!isSilentMode()) {
			System.err.println(ex.getNestedMessage());
			ex.printStackTrace(System.err);
		}
	}
	
	/**
	 * 
	 * @param message
	 * @param context
	 */
	protected void handleException(Throwable ex) {
		if(!isSilentMode()) {
			System.err.println(ex.getMessage());
			ex.printStackTrace(System.err);
		}
	}
	
	/**
	 * 
	 * @param message
	 * @param context
	 */
	public void logDebug(Object message, Object context) {
		doLog(LOG_DEBUG, message, context);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logDebug(Object message, Throwable ex, Object context) {
		logDebug(buildCompleteExceptionMessage(message, ex), context);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logDebug(Object message, Throwable ex) {
		logDebug(message, ex, null);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logDebug(Throwable ex) {
		logDebug(null, ex, null);
	}
	
	/**
	 * 
	 * @param message
	 * @param context
	 */
	public void logInfo(Object message, Object context) {
		doLog(LOG_INFO, message, context);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logInfo(Object message, Throwable ex, Object context) {
		logInfo(buildCompleteExceptionMessage(message, ex), context);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logInfo(Object message, Throwable ex) {
		logInfo(message, ex, null);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logInfo(Throwable ex) {
		logInfo(null, ex, null);
	}
	
	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logWarning(Object message, Throwable ex, Object context) {
		logWarning(buildCompleteExceptionMessage(message, ex), context);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logWarning(Object message, Throwable ex) {
		logWarning(message, ex, null);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logWarning(Throwable ex) {
		logWarning(null, ex, null);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logError(Object message, Throwable ex, Object context) {
		logError(buildCompleteExceptionMessage(message, ex), context);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logError(Object message, Throwable ex) {
		logError(message, ex, null);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logError(Throwable ex) {
		logError(null, ex, null);
	}
	
	/**
	 * 
	 * @param message
	 * @param ex
	 * @return
	 */
	private String buildCompleteExceptionMessage(Object message, Throwable ex) {
		String completeMsg;
		if(message != null) {
			completeMsg = message + CommonUtils.getNewLine() + buildExceptionMessage(ex);
		} else {
			completeMsg = buildExceptionMessage(ex);
		}
		return completeMsg;
	}
	
	/**
	 * 
	 * @param ex
	 * @return
	 */
	protected String buildExceptionMessage(Throwable ex) {
		StringBuffer msg = new StringBuffer();
		if(ex != null) {
			msg.append(ex.getClass().getName() + ": ");
			if(ex instanceof NestedException) {
				msg.append(((NestedException)ex).getNestedMessage());
			} else {
				msg.append(ex.getMessage());
			}
			if(isLogStackTrace()) {
				StringWriter traceStr = new StringWriter();
				PrintWriter trace = new PrintWriter(traceStr);
				ex.printStackTrace(trace);
				msg.append(CommonUtils.getNewLine());
				msg.append(traceStr.toString());
			}
		}
		return msg.toString();
	}
	
	/**
	 * 
	 * @param message
	 * @param context
	 */
	public void logWarning(Object message, Object context) {
		doLog(LOG_WARNING, message, context);
	}
	
	/**
	 * 
	 * @param level
	 * @param message
	 * @param context
	 */
	public void logOther(int level, Object message, Object context) {
		doLog(level, message, context);
	}

	/**
	 * 
	 * @param level
	 * @param message
	 */
	public void logOther(int level, Object message) {
		doLog(level, message, getContext());
	}	
	/**
	 * 
	 * @param message
	 */
	public void logError(Object message, Object context) {
		doLog(LOG_ERROR, message, context);
	}

	/**
	 * 
	 * @param message
	 */
	public void logCritical(Object message, Object context) {
		doLog(LOG_CRITICAL, message, context);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logCritical(Object message, Throwable ex, Object context) {
		logCritical(buildCompleteExceptionMessage(message, ex), context);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logCritical(Object message, Throwable ex) {
		logCritical(message, ex, null);
	}

	/**
	 * 
	 * @param message
	 * @param ex
	 * @param context
	 */
	public void logCritical(Throwable ex) {
		logCritical(null, ex, null);
	}
	
	/**
	 * 
	 * @param message
	 */
	public void logDebug(Object message) {
		doLog(LOG_DEBUG, message,  getContext());
	}

	/**
	 * 
	 * @param message
	 */
	public void logInfo(Object message) {
		doLog(LOG_INFO, message,  getContext());
	}

	/**
	 * 
	 * @param message
	 */
	public void logWarning(Object message) {
		doLog(LOG_WARNING, message,  getContext());
	}
	
	/**
	 * 
	 * @param message
	 */
	public void logError(Object message) {
		doLog(LOG_ERROR, message,  getContext());
	}

	/**
	 * 
	 * @param message
	 */
	public void logCritical(Object message) {
		doLog(LOG_CRITICAL, message,  getContext());
	}
	
	/**
	 * @return
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return
	 */
	public boolean isSilentMode() {
		return silentMode;
	}

	/**
	 * @param level
	 */
	public void setLevel(int level) {
		if(level >= 0) {
			this.level = level;
		}
	}

	/**
	 * 
	 * @param level
	 */
	public void setLevel(String level) {
		int l = toInt(level);
		if( l >= 0 ) {
			setLevel(l);
		} else {
			setLevel(getLevelFromDescription(level));
		}
		
	}

	/**
	 * 
	 * @param level
	 * @return
	 */
	private int getLevelFromDescription(String level) {
		if(LOG_CRITICAL_DESC.equalsIgnoreCase(level)) {
			return LOG_CRITICAL;
		} else if(LOG_ERROR_DESC.equalsIgnoreCase(level)) {
			return LOG_ERROR;
		} else if(LOG_WARNING_DESC.equalsIgnoreCase(level)) {
			return LOG_WARNING;
		}  else if(LOG_INFO_DESC.equalsIgnoreCase(level)) {
			return LOG_INFO;
		} else if(LOG_DEBUG_DESC.equalsIgnoreCase(level)) {
			return LOG_DEBUG;
		} else {
			return LOG_INFO;
		}
	}

	/**
	 * 
	 * @param num
	 * @return
	 */
	private int toInt(String num) {
		try {
			return Integer.parseInt(num);
		} catch(Throwable ex) {
			return -1;
		}
	}

	/**
	 * @param silentMode
	 */
	public void setSilentMode(boolean silentMode) {
		this.silentMode = silentMode;
	}

	/**
	 * @return
	 */
	public Object getContext() {
		return context;
	}

	/**
	 * @param object
	 */
	public void setContext(Object object) {
		context = object;
	}

	/**
	 * @return
	 */
	public String getDateFormat() {
	    if(!StringUtils.hasValue(dateFormat)) {
	        dateFormat = DEFAULT_DATE_FORMAT;
	    }
		return dateFormat;
	}

	/**
	 * @param dateFormat
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * 
	 * @return
	 */
	protected String getFormattedDate() {
		return getDateFormatter().format(new Date());
	}

	/**
	 * @return
	 */
	protected SimpleDateFormat getDateFormatter() {
		if(dateFormatter == null) {
			dateFormatter = new SimpleDateFormat(getDateFormat());
		}
		return dateFormatter;
	}

	/**
	 * 
	 * @return
	 */
	protected String getLevelDescription(int level) {
		switch(level) {
			case LOG_CRITICAL: return LOG_CRITICAL_DESC;
			case LOG_ERROR:    return LOG_ERROR_DESC;
			case LOG_WARNING:  return LOG_WARNING_DESC;
			case LOG_INFO:     return LOG_INFO_DESC;
			case LOG_DEBUG:    return LOG_DEBUG_DESC;
			default:           return LOG_OTHER_DESC;
		}
	}

	/**
	 * @return Returns the enabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled The enabled to set.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return Returns the logStackTrace.
	 */
	public boolean isLogStackTrace() {
		return logStackTrace;
	}

	/**
	 * @param logStackTrace The logStackTrace to set.
	 */
	public void setLogStackTrace(boolean logStackTrace) {
		this.logStackTrace = logStackTrace;
	}
}
