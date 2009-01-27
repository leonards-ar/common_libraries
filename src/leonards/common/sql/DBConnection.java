package leonards.common.sql;

import java.sql.*;

import leonards.common.conf.*;

/**
 * @author Mariano Capurro (28/10/2002)
 *
 * This class is the abstraction of a database connection
 */
public abstract class DBConnection {
	protected static final String DEFAULT_CONFIGURATION_NAME = "sql";
	protected static final String DEFAULT_CONNECTION_NAME = "default";
	private Connection conn = null;
	private String name = null;
	private String configurationName = null;
	
	/**
	 * Constructor for DBConnection. Loads from the
	 * default properties file the default configuration
	 */
	public DBConnection() {
		super();
	}

	public static DBConnection getConnection() throws DBException {
		return getConnection(DEFAULT_CONNECTION_NAME, DEFAULT_CONFIGURATION_NAME);
	}

	public static DBConnection getConnection(String name) throws DBException {
		return getConnection(name, DEFAULT_CONFIGURATION_NAME);
	}

	public static DBConnection getConnection(String name, String configurationName) throws DBException {
		try {
			DBConnection conn = (DBConnection)Class.forName(getConnectionClassName(name, configurationName)).newInstance();
			conn.setName(name);
			conn.setConfigurationName(configurationName);
			conn.initialize();
			return conn;
		} catch (InstantiationException ex) {
			throw new DBException("Could not instantiate connection [" + name + "] from configuration [" + configurationName + "]", ex);
		} catch (IllegalAccessException ex) {
			throw new DBException("Could not instantiate connection [" + name + "] from configuration [" + configurationName + "]", ex);
		} catch (ClassNotFoundException ex) {
			throw new DBException("Could not instantiate connection [" + name + "] from configuration [" + configurationName + "]", ex);
		} catch( Throwable ex ) {
			throw new DBException("Could not obtain connection [" + name + "] from configuration [" + configurationName + "]", ex);
		}
	}
	
	protected abstract void initialize() throws DBException;
	
	/**
	 * Method loadConfiguration.
	 * @param configurationName
	 * @throws DBException
	 */
	protected static String getConnectionClassName(String name, String configurationName) throws DBException {
		try {
			Configuration conf = new Configuration(configurationName);
			return conf.getString(name + ".connection.class");
		} catch(ConfigurationException ex) {
			throw new DBException("Could not retrieve connection class name for connection [" + name + "]", ex);
		}
	}
	
	
	/**
	 * Method getCallableStatement.
	 * @param sqlStmt
	 * @return CallableStatement
	 * @throws DBException
	 */
	public CallableStatement getCallableStatement(String sqlStmt) throws DBException {
		try {
			return getConn().prepareCall(sqlStmt);
		} catch( SQLException sqlEx ) {
			throw new DBException("Could not prepare callable statement", sqlEx );
		}
	}

	/**
	 * Method getPreparedStatement.
	 * @param sqlStmt
	 * @return PreparedStatement
	 * @throws DBException
	 */
	public PreparedStatement getPreparedStatement(String sqlStmt) throws DBException {
		try {
			return getConn().prepareStatement(sqlStmt);
		} catch( SQLException sqlEx ) {
			throw new DBException("Could not prepare statement", sqlEx );
		}		
	}
	
	/**
	 * Method getStatement.
	 * @return Statement
	 * @throws DBException
	 */
	public Statement getStatement() throws DBException {
		try {
			return getConn().createStatement();
		} catch( SQLException sqlEx ) {
			throw new DBException("Could not create statement", sqlEx );
		}		
	}

	public void setAutoCommit(boolean autoCommit) throws DBException {
		try {
			getConn().setAutoCommit(autoCommit);
		} catch( SQLException sqlEx ) {
			throw new DBException("Could not set auto commit value.", sqlEx);
		}
	}
	
	public void commit() throws DBException {
		try {
			getConn().commit();
		} catch( SQLException sqlEx ) {
			throw new DBException("Could not perform commit.", sqlEx);
		}
	}

	public void rollback() throws DBException {
		try {
			getConn().rollback();
		} catch( SQLException sqlEx ) {
			throw new DBException("Could not perform rollback.", sqlEx);
		}
	}

	/**
	 * Returns the conn.
	 * @return Connection
	 * @throws DBException
	 */
	protected Connection getConn() throws DBException {
		if( conn == null ) {
			setConn(adquireConnection());
		}
		return conn;
	}

	protected void setConn(Connection conn) {
		this.conn = conn;
	}

	protected abstract Connection adquireConnection() throws DBException;

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return (name != null)?name:DEFAULT_CONNECTION_NAME;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getConfigurationName() {
		return configurationName != null ? configurationName : DEFAULT_CONFIGURATION_NAME;
	}

	/**
	 * @param string
	 */
	public void setConfigurationName(String string) {
		configurationName = string;
	}

	/** 
	 * @throws DBException
	 * @see leonards.common.sql.DBConnection#close()
	 */
	public void close() throws DBException {
		if( getConn() != null ) {
			try {
				getConn().close();
			} catch( SQLException sqlEx ) {
				throw new DBException("Error closing connection.", sqlEx );
			}
			setConn(null);
		}
	}
	
}
