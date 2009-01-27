package leonards.common.sql;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Mariano (03/11/2002)
 *
 * This class is the abstraction
 */
public class DBStaticStatement extends DBStatement {
	public static final char PARAM_TOKEN = '?';
	
	private Statement statement = null;
	private Hashtable parameters = null;
	
	/**
	 * Constructor for DBStaticStatement.
	 * @param sqlStmt
	 */
	public DBStaticStatement(String sqlStmt) throws DBException {
		super(sqlStmt);
	}

	public DBStaticStatement(String sqlStmt, String connectionName) throws DBException {
		super(sqlStmt, connectionName);
	}

	/**
	 * Constructor for DBStatement.
	 * @param sqlStmt
	 */
	public DBStaticStatement(String sqlStmt, String connectionName, String configurationName) throws DBException {
		super(sqlStmt, connectionName, configurationName);
	}
	
	/**
	 * Constructor for DBStaticStatement.
	 * @param sqlStmt
	 * @param conn
	 */
	public DBStaticStatement(String sqlStmt, DBConnection conn) throws DBException {
		super(sqlStmt, conn);
	}

	/**
	 * Constructor for DBStatement.
	 * @param sqlStmt
	 * @param conn
	 */
	public DBStaticStatement(String sqlStmt, DBTransactionContext trxContext) throws DBException {
		super(sqlStmt, trxContext);
	}
	
	/**
	 * @see mindpool.sql.DBStatement#executeQuery()
	 */
	public DBResultSet executeQuery() throws DBException {
		ResultSet rs = null;
		try {
			rs = getStatement().executeQuery(getParsedSqlStatement());
			return new DBResultSet( rs );
		} catch( SQLException sqlEx ) {
			throw new DBException( "Error executing statement.", sqlEx );
		} finally {
			if( rs != null ) {
				try { rs.close(); } catch(Throwable ex) {}
			}
		}
	}

	/**
	 * @see mindpool.sql.DBStatement#execute()
	 */
	public void execute() throws DBException {
		try {
			getStatement().executeUpdate(getParsedSqlStatement());
		} catch( SQLException sqlEx ) {
			throw new DBException("Error executing statement.", sqlEx );
		}
	}

	/**
	 * @see mindpool.sql.DBStatement#close()
	 */
	public void close() throws DBException {
		super.close();
		if( statement != null ) {
			try {
				getStatement().close();
			} catch( SQLException sqlEx ) {
				throw new DBException("Error closing statement.", sqlEx);
			}
			statement = null;
		}
	}

	/**
	 * Method setString.
	 * @param idx
	 * @param str
	 */
	public void setString(int idx, String str) {
		if( str != null ) {
			setParameter(idx, "'" + escapeSpecialCharacters(str) + "'");
		} else {
			setParameter(idx, "null");
		}		
		
	}

	/**
	 * Method setInt.
	 * @param idx
	 * @param num
	 */
	public void setInt( int idx, int num ) {
		setInt(idx, new Integer(num));
	}
	
	/**
	 * Method setInt.
	 * @param idx
	 * @param num
	 */
	public void setInt( int idx, Integer num ) {
		if( num != null ) {
			setParameter(idx, num.toString());
		} else {
			setParameter(idx, "null");
		}		
	}

	/**
	 * Method setDouble.
	 * @param idx
	 * @param num
	 */
	public void setDouble( int idx, double num ) {
		setDouble(idx, new Double(num));
	}
	
	/**
	 * Method setDouble.
	 * @param idx
	 * @param num
	 */
	public void setDouble( int idx, Double num ) {
		if( num != null ) {
			setParameter(idx, num.toString());
		} else {
			setParameter(idx, "null");
		}		
	}
	
	/**
	 * Method setGregorianCalendarShortDate.
	 * @param idx
	 * @param cal
	 */
	public void setShortGregorianCalendar( int idx, GregorianCalendar cal ) {
		if( cal != null ) {
			GregorianCalendar theCalendar = (cal != null)?cal: new GregorianCalendar();
			setShortDate(idx, theCalendar.getTime());
		} else {
			setParameter(idx, "null");
		}		
	}

	public void setShortDate( int idx, java.util.Date date ) {
		setDate(idx, date, "yyyy/MM/dd" );
	}

	public void setDate( int idx, java.util.Date date ) {
		setDate(idx, date, "yyyy/MM/dd HH:mm:ss" );
	}
	
	private void setDate( int idx, java.util.Date date, String dateFormat ) {
		if( date != null ) {
			SimpleDateFormat df = new SimpleDateFormat(dateFormat);
			setParameter(idx, "'" + df.format(date) + "'");
		} else {
			setParameter(idx, "null");
		}
	}
		
	/**
	 * Method setLong.
	 * @param idx
	 * @param num
	 */
	public void setLong(int idx, long num) {
		setLong(idx, new Long(num));
	}
	
	/**
	 * Method setLong.
	 * @param idx
	 * @param num
	 */
	public void setLong( int idx, Long num ) {
		if( num != null ) {
			setParameter(idx, num.toString());
		} else {
			setParameter(idx, "null");
		}
	}
	
	/**
	 * Method setBoolean.
	 * @param idx
	 * @param bool
	 */
	public void setBoolean( int idx, Boolean bool ) {
		if( bool != null ) {
			setBoolean(idx, bool.booleanValue());
		} else {
			setBoolean(idx, false);
		}		
			
	}
	
	/**
	 * Method setBoolean.
	 * @param idx
	 * @param bool
	 */
	public void setBoolean( int idx, boolean bool ) {
		setParameter(idx, (bool)?"1":"0");
	}
	
	/**
	 * Method setParameter.
	 * @param idx
	 * @param value
	 */
	private void setParameter( int idx, String value ) {
		getParameters().put(Integer.toString(idx), value);
	}
	
	/**
	 * Method getParameter.
	 * @param idx
	 * @return String
	 */
	private String getParameter( int idx ) {
		return (String)getParameters().get(Integer.toString(idx));
	}
	
	/**
	 * Method getParsedSqlStatement.
	 * @return String
	 */
	private String getParsedSqlStatement() {
		StringBuffer parsedStmt = new StringBuffer();
		char c;
		for( int i = 0, paramIdx = 1; i < getSqlStmt().length(); i++ ) {
			if( (c = getSqlStmt().charAt(i)) == PARAM_TOKEN ) {
				parsedStmt.append(getParameter(paramIdx));
				paramIdx++;
			} else {
				parsedStmt.append(c);
			}
		}
		
		return parsedStmt.toString();
	}

	/**
	 * Returns the parameters.
	 * @return Hashtable
	 */
	private Hashtable getParameters() {
		if( parameters == null ) {
			parameters = new Hashtable();
		}
		return parameters;
	}

	/**
	 * Returns the statement.
	 * @return Statement
	 * @throws DBException
	 */
	private Statement getStatement() throws DBException {
		if( statement == null ) {
			statement = getConn().getStatement();	
		}
		return statement;
	}

	/**
	 * @param value
	 * @return
	 */
	private String escapeSpecialCharacters(String value) {
		StringBuffer scapedValue = new StringBuffer();
		char c;
		for( int i = 0; i < value.length(); i++ ) {
			c = value.charAt(i);
			switch( c ) {
				case '\'':
					scapedValue.append("\'\'");
					break;
				case '\\':
					scapedValue.append("\\\\");
					break;
				default:
					scapedValue.append(c);
					break;
			}
		}
		return scapedValue.toString();
	}

}
