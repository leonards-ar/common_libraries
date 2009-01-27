/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: Configuration.java
 *
 * Property of Leonards / Mindpool
 * Created on 05/05/2004
 */
package leonards.common.log;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import leonards.common.conf.Configuration;
import leonards.common.conf.ConfigurationException;

/**
 * @author Mariano
 *
 * This class is the abstraction 
 */
public class LoggerFactory {
	public static final String DEFAULT_LOGGER = "default";
	private static Hashtable configurations = null;
	private static final ConsoleLogger DEFAULT_CONSOLE_LOGGER = new ConsoleLogger();
	
	static {
		DEFAULT_CONSOLE_LOGGER.setConsole(System.err);		
	}
	
	/**
	 * 
	 */
	public LoggerFactory() {
		super();
	}
	
	/**
	 * 
	 * @return
	 * @throws LogException
	 */
	public static Logger createLogger() throws LogException {
		return createLogger(DEFAULT_LOGGER);
	}
	
	/**
	 * 
	 * @return
	 */
	public static Logger createSilentLogger() {
		return createSilentLogger(DEFAULT_LOGGER);
	}
	
	/**
	 * This method returns a new logger for the given name.
	 * If the logger creatin fails, then the default console
	 * logger will be returned.
	 * @param name
	 * @return
	 */
	public static Logger createSilentLogger(String name) {
		try {
			return createLogger(name);
		} catch(NoSuchLoggerException ex) {
			/*
			 * Trying default logger.
			 */
			try {
				Logger defaultLogger = createLogger();
				defaultLogger.logInfo("There is no logger configured under name [" + name + "]. Using default logger.");
				return defaultLogger;
			} catch(Throwable t) {
				DEFAULT_CONSOLE_LOGGER.logError(ex.getClass().getName() + ":" + ex.getMessage());
				return DEFAULT_CONSOLE_LOGGER;
			}
		} catch(Throwable ex) {
			DEFAULT_CONSOLE_LOGGER.logError(ex.getClass().getName() + ":" + ex.getMessage());
			return DEFAULT_CONSOLE_LOGGER;
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws LogException
	 */
	public static Logger createLogger(String name) throws LogException {
		LoggerConfiguration conf = (LoggerConfiguration)getConfigurations().get(name);
		if( conf == null ) {
			throw new NoSuchLoggerException("There is no configuration for logger named [" + name + "]");
		}
		Logger logger = null;
		try {
			logger = (Logger)Class.forName(conf.getClassName()).newInstance();
		} catch(Exception ex) {
			throw new LogException("Could not create instance for logger [" + conf.getClassName() + "]", ex);
		}
		
		logger.setup(conf.getConfig());
		logger.setLevel(conf.getLevel());
		logger.setSilentMode(conf.isSilent());
		logger.setDateFormat(conf.getDateFormat());
		logger.setLogStackTrace(conf.isLogStackTrace());
		logger.setEnabled(conf.isEnabled());
		
		return logger;
		
	}

	/**
	 * 
	 * @return
	 * @throws LogException
	 */
	private static Hashtable getConfigurations() throws LogException {
		if(configurations == null) {
			configurations = new Hashtable();
			loadConfiguration(configurations);
		}
		return configurations;
	}
	
	/**
	 * 
	 * @param configuration
	 * @throws LogException
	 */
	private static void loadConfiguration(Hashtable configuration) throws LogException {
		try {
			Configuration logConfiguration = new Configuration("log");
			logConfiguration.load();
			Enumeration keys = logConfiguration.getConfigurationKeys();
			String loggerName;
			while(keys.hasMoreElements()) {
				loggerName = getLoggerName(keys.nextElement());
				if(!configuration.containsKey(loggerName)) {
					configuration.put(loggerName, buildLoggerConfiguration(loggerName, logConfiguration));
				}
			}
		} catch(ConfigurationException ex) {
			throw new LogException("Could not load configuration", ex);
		}
	}
	
	/**
	 * 
	 * @param loggerName
	 * @param conf
	 * @return
	 * @throws LogException
	 */
	private static LoggerConfiguration buildLoggerConfiguration(String loggerName, Configuration conf) throws LogException {
		try {
			LoggerConfiguration logConf = new LoggerConfiguration();
			logConf.setName(loggerName);
			logConf.setClassName(conf.getString(loggerName + ".logger.class"));
			logConf.setConfig(conf.getSubset(loggerName + ".logger"));
			logConf.setLevel(conf.getString(loggerName + ".logger.level", Logger.LOG_INFO_DESC));
			logConf.setSilent(conf.getBool(loggerName + ".logger.silent"));
			logConf.setDateFormat(conf.getString(loggerName + ".logger.dateformat", Logger.DEFAULT_DATE_FORMAT));
			logConf.setLogStackTrace(conf.getBool(loggerName + ".logger.stack_trace", true));
			logConf.setEnabled(conf.getBool(loggerName + ".logger.enabled", true));
			return logConf;
		} catch(ConfigurationException ex) {
			throw new LogException("Could not build logger configuration", ex);
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	private static String getLoggerName(Object key) {
		if(key == null) return null;
		
		StringTokenizer st = new StringTokenizer(key.toString(), ".");
		
		return st.hasMoreTokens() ? st.nextToken() : key.toString();
	}
	
	/**
	 * 
	 *
	 */
	public static void reset() {
		configurations = null;
	}
	
}
