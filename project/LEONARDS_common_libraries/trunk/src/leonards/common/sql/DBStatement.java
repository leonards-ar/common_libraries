 package leonards.common.sql;

import java.util.GregorianCalendar;

/**
 * @author Mariano Capurro (28/10/2002)
 *
 * This class is the super class for database statements
 */
public abstract class DBStatement {

	private String sqlStmt = null;
	private DBConnection conn = null;
	private boolean transactionConnection = false;
	
	/**
	 * Constructor for DBStatement.
	 */
	private DBStatement() {
		super();
		setTransactionConnection(false);
	}
	
	/**
	 * Constructor for DBStatement.
	 * @param sqlStmt
	 */
	public DBStatement(String sqlStmt) throws DBException {
		this();
		setSqlStmt(sqlStmt);
	}

	/**
	 * Constructor for DBStatement.
	 * @param sqlStmt
	 */
	public DBStatement(String sqlStmt, String connectionName) throws DBException {
		this(sqlStmt);
		setConn(DBConnection.getConnection(connectionName));
	}

	/**
	 * Constructor for DBStatement.
	 * @param sqlStmt
	 */
	public DBStatement(String sqlStmt, String connectionName, String configurationName) throws DBException {
		this(sqlStmt);
		setConn(DBConnection.getConnection(connectionName, configurationName));
	}
	
	/**
	 * Constructor for DBStatement.
	 * @param sqlStmt
	 * @param conn
	 */
	public DBStatement(String sqlStmt, DBConnection conn) throws DBException {
		this(sqlStmt);
		setConn(conn);
	}

	/**
	 * Constructor for DBStatement.
	 * @param sqlStmt
	 * @param conn
	 */
	public DBStatement(String sqlStmt, DBTransactionContext trxContext) throws DBException {
		this(sqlStmt);
		setConn(trxContext.getDbConn());
		setTransactionConnection(true);
	}
	
	/**
	 * Method executeQuery.
	 * @return DBResultSet
	 * @throws DBException
	 */
	public abstract DBResultSet executeQuery() throws DBException;
	
	/**
	 * Method execute.
	 * @throws DBException
	 */
	public abstract void execute() throws DBException;
	
	/**
	 * Method close. Only closes the connection for non transactional
	 * operations.
	 * @throws DBException
	 */
	public void close() throws DBException {
		if( conn != null && !isTransactionConnection() ) {
			getConn().close();
			setConn(null);
		}
	}
	

	/**
	 * Returns the sqlStmt.
	 * @return String
	 */
	protected String getSqlStmt() {
		return sqlStmt;
	}

	/**
	 * Sets the sqlStmt.
	 * @param sqlStmt The sqlStmt to set
	 */
	protected void setSqlStmt(String sqlStmt) {
		this.sqlStmt = sqlStmt;
	}

	/**
	 * Sets the conn.
	 * @param conn The conn to set
	 */
	protected void setConn(DBConnection conn) {
		this.conn = conn;
	}

	/**
	 * Returns the conn.
	 * @return DBConnection
	 */
	protected DBConnection getConn() throws DBException {
		if( conn == null ) {
			conn = DBConnection.getConnection();
		}
		return conn;
	}

	/**
	 * @return
	 */
	public boolean isTransactionConnection() {
		return transactionConnection;
	}

	/**
	 * @param b
	 */
	public void setTransactionConnection(boolean b) {
		transactionConnection = b;
	}

	/**
	 * Method setString.
	 * @param idx
	 * @param str
	 */
	public abstract void setString(int idx, String str);

	/**
	 * Method setInt.
	 * @param idx
	 * @param num
	 */
	public abstract void setInt( int idx, int num );
	
	/**
	 * Method setInt.
	 * @param idx
	 * @param num
	 */
	public abstract void setInt( int idx, Integer num );

	/**
	 * Method setDouble.
	 * @param idx
	 * @param num
	 */
	public abstract void setDouble( int idx, double num );
	
	/**
	 * Method setDouble.
	 * @param idx
	 * @param num
	 */
	public abstract void setDouble( int idx, Double num );
	
	/**
	 * Method setGregorianCalendarShortDate.
	 * @param idx
	 * @param cal
	 */
	public abstract void setShortGregorianCalendar( int idx, GregorianCalendar cal );

	public abstract void setShortDate( int idx, java.util.Date date );

	public abstract void setDate( int idx, java.util.Date date );
	
	/**
	 * Method setLong.
	 * @param idx
	 * @param num
	 */
	public abstract void setLong(int idx, long num);
	
	/**
	 * Method setLong.
	 * @param idx
	 * @param num
	 */
	public abstract void setLong( int idx, Long num );
	
	/**
	 * Method setBoolean.
	 * @param idx
	 * @param bool
	 */
	public abstract void setBoolean( int idx, Boolean bool );
	
	/**
	 * Method setBoolean.
	 * @param idx
	 * @param bool
	 */
	public abstract void setBoolean( int idx, boolean bool );
	
}
