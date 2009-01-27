/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.sql
 * File: SingleDBConnection.java
 *
 * Property of Leonards / Mindpool
 * Created on 14/06/2004
 */
package leonards.common.sql;

import java.sql.*;

import leonards.common.conf.*;
import leonards.common.util.StringUtils;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class SingleDBConnection extends DBConnection {
	protected static final String DEFAULT_CONNECTION_NAME = "default";
	private String url = null;
	private String driver = null;
	private String username = null;
	private String password = null;

	/**
	 * Constructor for DBConnection. Loads from the
	 * default properties file the default configuration
	 */
	public SingleDBConnection() {
		super();
	}

	/**
	 * Method loadConfiguration.
	 * @throws DBException
	 */
	protected void loadConfiguration() throws DBException {
		try {
			Configuration conf = new Configuration(getConfigurationName());
			setDriver(conf.getString(getName() + ".sql.conn.driver"));
			setUrl(conf.getString(getName() + ".sql.conn.url"));
			setUsername(conf.getString(getName() + ".sql.conn.username"));
			setPassword(conf.getString(getName() + ".sql.conn.password"));
		} catch(ConfigurationException ex) {
			throw new DBException("Could not retrieve connection parameters for connection [" + getName() + "]", ex);
		}
	}
	
	/**
	 * 
	 * @throws DBException
	 * @see leonards.common.sql.DBConnection#initialize()
	 */
	protected void initialize() throws DBException {
		loadConfiguration();
		// Load Driver Class
		try {
			Class.forName(getDriver());
		} catch( ClassNotFoundException cnfEx ) {
			throw new DBException("Driver class [" + getDriver() + "] not found.", cnfEx);
		}			
	}
	
	/**
	 * Returns the conn.
	 * @return Connection
	 * @throws DBException
	 */
	protected Connection adquireConnection() throws DBException {
		Connection conn;
		try {
			if( hasUsername() ) {
				conn = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
			} else {
				conn = DriverManager.getConnection(getUrl());
			}
		} catch( SQLException sqlEx ) {
			throw new DBException("Could not open connection. [" + getUrl() + "]", sqlEx);
		}
		return conn;
	}

	/**
	 * Returns the driver.
	 * @return String
	 */
	public String getDriver() {
		return ( driver != null )?driver:"";
	}

	/**
	 * Returns the password.
	 * @return String
	 */
	public String getPassword() {
		return ( password != null )?password:"";
	}

	/**
	 * Returns the url.
	 * @return String
	 */
	public String getUrl() {
		return ( url != null )?url:"";
	}

	/**
	 * Returns the username.
	 * @return String
	 */
	public String getUsername() {
		return ( username != null)?username:"";
	}

	/**
	 * Sets the driver.
	 * @param driver The driver to set
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * Sets the password.
	 * @param password The password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the url.
	 * @param url The url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Sets the username.
	 * @param username The username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Method hasUsername.
	 * @return boolean
	 */
	private boolean hasUsername() {
		return StringUtils.hasValue(this.username);
	}

}
