/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.sql
 * File: JNDIPooledConnection.java
 *
 * Property of Leonards / Mindpool
 * Created on 14/06/2004
 */
package leonards.common.sql;

import java.sql.*;

import javax.naming.*;
import javax.sql.*;

import leonards.common.conf.*;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class JNDIPooledDBConnection extends DBConnection {
	private String environmentContext = null;
	private String jdbcName = null;
	
	/**
	 * 
	 */
	public JNDIPooledDBConnection() {
		super();
	}

	/** 
	 * @throws DBException
	 * @see leonards.common.sql.DBConnection#initialize()
	 */
	protected void initialize() throws DBException {
		loadConfiguration();
	}

	/**
	 * Method loadConfiguration.
	 * @throws DBException
	 */
	public void loadConfiguration() throws DBException {
		try {
			Configuration conf = new Configuration(getConfigurationName());
			setEnvironmentContext(conf.getString(getName() + ".env.context", ""));
			setJdbcName(conf.getString(getName() + ".jdbc.name", getName()));
		} catch( Exception confEx ) {
			throw new DBException("Could not load configuration.", confEx);
		}
	}
	
	/** 
	 * @return
	 * @throws DBException
	 * @see leonards.common.sql.DBConnection#obtainConnection()
	 */
	protected Connection adquireConnection() throws DBException {
		try {
			Context envCtx = null;
			if( hasEnvironmentContext() ) {
				Context initCtx = new InitialContext();
				envCtx = (Context) initCtx.lookup(getEnvironmentContext());
			} else {
				envCtx = new InitialContext();
			}
			DataSource ds = (DataSource)envCtx.lookup(getJdbcName());
			return ds.getConnection();
		} catch( NamingException ex ) {
			throw new DBException("Could not retrieve Data Source from JNDI Context.", ex);
		} catch( SQLException sqlEx ) {
			throw new DBException("Could not adquire connection from Data Source.", sqlEx);
		}
	}

	/**
	 * @return
	 */
	public String getEnvironmentContext() {
		return environmentContext;
	}

	/**
	 * @param string
	 */
	public void setEnvironmentContext(String string) {
		environmentContext = string;
	}

	protected boolean hasEnvironmentContext() {
		return getEnvironmentContext() != null && getEnvironmentContext().length() > 0;
	}

	/**
	 * @return Returns the jdbcName.
	 */
	public String getJdbcName() {
		return jdbcName;
	}

	/**
	 * @param jdbcName The jdbcName to set.
	 */
	public void setJdbcName(String jdbcName) {
		this.jdbcName = jdbcName;
	}
}
