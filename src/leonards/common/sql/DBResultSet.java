package leonards.common.sql;

import java.util.*;
import java.sql.*;

import leonards.common.util.StringUtils;

/**
 * @author Mariano Capurro (28/10/2002)
 *
 * This class is the abstraction of a query result set.
 */
public class DBResultSet {
	private Vector data = null;
	private Hashtable metaData = null;
	private Iterator dataIterator = null;
		
	/**
	 * Constructor for DBResultSet.
	 */
	private DBResultSet() {
		super();
	}
	
	/**
	 * Constructor for DBResultSet.
	 * @param rs
	 * @throws DBException
	 */
	public DBResultSet(ResultSet rs) throws DBException {
		this();
		buildVectorResultSet(rs);
	}

	/**
	 * Method buildMetaDataVector.
	 * @param md
	 * @throws SQLException
	 */
	private void buildMetaDataVector( ResultSetMetaData md ) throws SQLException {
		if( md != null ) {
			String name;
			for( int i = 1; i <= md.getColumnCount(); i++ ) {
				name = md.getColumnLabel(i);
				if(!StringUtils.hasValue(name)) {
					name = md.getColumnName(i);
				}
				if(StringUtils.hasValue(name)) {
					name = name.toUpperCase();
					getMetaData().put(name, new DBResultSetColumn(i-1, name));
				}
			}
		} 
	}
	
	/**
	 * Method buildDataVector.
	 * @param rs
	 * @throws SQLException
	 */
	private void buildDataVector(ResultSet rs) throws SQLException {
		if( rs != null ) {
			while( rs.next() ) {
				DBResultSetRow row = new DBResultSetRow(this);
				for( int i = 1; i <= rs.getMetaData().getColumnCount(); i++ ) {
					row.addRowElement(rs.getObject(i));
				}
				getData().addElement(row);
			}
		}
	}
	
	/**
	 * Method buildVectorResultSet.
	 * @param rs
	 * @throws DBException
	 */
	private void buildVectorResultSet( ResultSet rs ) throws DBException {
		if( rs != null ) {
			try {
				buildMetaDataVector(rs.getMetaData());
				buildDataVector(rs);
			} catch (SQLException sqlEx ) {
				throw new DBException("Could not build result set vector", sqlEx);
			}
		}
	}

	/**
	 * Returns the data.
	 * @return Vector
	 */
	private Vector getData() {
		if( data == null ) {
			data = new Vector();
		}
		return data;
	}

	/**
	 * Returns the metaData.
	 * @return Vector
	 */
	private Hashtable getMetaData() {
		if( metaData == null ) {
			metaData = new Hashtable();
		}
		return metaData;
	}
	
	/**
	 * Method getDataRows.
	 * @return Iterator
	 */
	public Iterator getDataRows() {
		return getData().iterator();
	}
	
	/**
	 * Method getColumnIndex.
	 * @param columnName
	 * @return int
	 */
	public int getColumnIndex(String columnName ) {
		DBResultSetColumn colInfo = (DBResultSetColumn)getMetaData().get(columnName.toUpperCase());
		return colInfo.getIdx();
	}
	
	/**
	 * Method getColumnNames.
	 * @return
	 */
	public Iterator getColumnNames() {
		Vector columnNames = new Vector();
		for(int i = 0; i < getColumnCount(); i++) {
			columnNames.addElement(getColumnName(i));
		}
		return columnNames.iterator();
	}
	
	/**
	 * Method getRowCount.
	 * @return int
	 */
	public int getRowCount() {
		return getData().size();
	}
	
	/**
	 * Method getColumnCount.
	 * @return int
	 */
	public int getColumnCount() {
		return getMetaData().size();
	}
	
	/**
	 * Method getColumnName.
	 * @param idx
	 * @return String
	 */
	public String getColumnName( int idx ) {
		DBResultSetColumn colInfo;
		Enumeration cols = getMetaData().elements();

		while( cols.hasMoreElements() ) {
			colInfo = (DBResultSetColumn)cols.nextElement();
			if( colInfo.getIdx() == idx ) {
				return colInfo.getName();
			}
		}
	
		return null;
	}
	
	public boolean hasDataRows() {
		return getRowCount() > 0;
	}
	
	public DBResultSetRow getFirstDataRow() {
		return getDataRow(0);
	}
	
	public DBResultSetRow getDataRow(int rowIndex) {
		try {
			return (DBResultSetRow)getData().elementAt(rowIndex);
		} catch( Throwable ex ) {
			return null;
		}
	}

	/**
	 * @return
	 */
	protected Iterator getDataIterator() {
		if( dataIterator == null ) {
			dataIterator = getDataRows();
		}
		return dataIterator;
	}
	
	public boolean hasNext() {
		return getDataIterator().hasNext();
	}
	
	public DBResultSetRow next() {
		return (DBResultSetRow)getDataIterator().next();
	}

}
