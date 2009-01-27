/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.sql
 * File: TransactionContext.java
 *
 * Property of Leonards / Mindpool
 * Created on 14/06/2004
 */
package leonards.common.sql;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class DBTransactionContext {
	private DBConnection dbConn = null;
	
	/**
	 * 
	 */
	public DBTransactionContext() throws DBException {
		super();
		setDbConn(DBConnection.getConnection());
	}
	
	/**
	 * 
	 * Constructor
	 * @param connectionName
	 * @throws DBException
	 */
	public DBTransactionContext(String connectionName) throws DBException {
		super();
		setDbConn(DBConnection.getConnection(connectionName));
	}

	/**
	 * 
	 * Constructor
	 * @param connectionName
	 * @param configurationName
	 * @throws DBException
	 */
	public DBTransactionContext(String connectionName, String configurationName) throws DBException {
		super();
		setDbConn(DBConnection.getConnection(connectionName, configurationName));
	}
	
	/**
	 * @return
	 */
	public DBConnection getDbConn() {
		return dbConn;
	}

	/**
	 * @param connection
	 */
	public void setDbConn(DBConnection connection) {
		dbConn = connection;
	}

	/**
	 * 
	 * @throws DBException
	 */
	public void beginTransaction() throws DBException {
		getDbConn().setAutoCommit( false );
	}

	/**
	 * 
	 * @throws DBException
	 */
	public void commit() throws DBException {
		getDbConn().commit();
	}

	/**
	 * 
	 * @throws DBException
	 */
	public void rollback() throws DBException {
		getDbConn().rollback();
	}

	/**
	 * 
	 * @throws DBException
	 */
	public void close() throws DBException {
		try {
			getDbConn().setAutoCommit(true);			
		} finally {
			getDbConn().close();
		}
	}
}
