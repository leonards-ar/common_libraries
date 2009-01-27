/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.conf
 * File: Configuration.java
 *
 * Property of Leonards / Mindpool
 * Created on 05/05/2004
 */
package leonards.common.log;

import java.io.PrintStream;

import leonards.common.conf.ConfigurationSet;

/**
 * @author Mariano
 *
 * This class is the abstraction 
 */
public class ConsoleLogger extends Logger {
	PrintStream console = null;
	
	/**
	 * 
	 */
	public ConsoleLogger() {
		super();
	}

	/**
	 * @param conf
	 * @throws LogException
	 */
	public ConsoleLogger(ConfigurationSet conf) throws LogException {
		super(conf);
	}

	/**
	 * 
	 * @param conf
	 * @throws LogException
	 * @see leonards.common.log.Logger#setup(java.lang.String)
	 */
	public void setup(ConfigurationSet conf) throws LogException {
		String console = null;
		
		console = conf.getString("console");
		
		if(console != null && console.equalsIgnoreCase("out")) {
			setConsole(System.out);
		} else if(console != null && console.equalsIgnoreCase("err")) {
			setConsole(System.err);
		}
	}

	/**
	 * 
	 * @param level
	 * @param message
	 * @param context
	 * @throws LogException
	 * @see leonards.common.log.Logger#log(int, java.lang.String, java.lang.Object)
	 */
	protected void log(int level, String message, Object context) throws LogException {
		getConsole().print(getFormattedDate() + " ");
		getConsole().print("[" + getLevelDescription(level) + "] ");
		if(context != null) {
			getConsole().print("Ctx Info: [" + context.toString() + "] ");
		}
		getConsole().println(message);
	}

	/**
	 * @return
	 */
	protected PrintStream getConsole() {
		if( console == null ) {
			setConsole(System.out);
		}
		return console;
	}

	/**
	 * @param stream
	 */
	protected void setConsole(PrintStream stream) {
		console = stream;
	}

}
