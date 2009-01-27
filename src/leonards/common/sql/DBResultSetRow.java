/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.sql
 * File: DBResultSetRow.java
 *
 * Property of Leonards / Mindpool
 * Created on 17/06/2004
 */
package leonards.common.sql;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class DBResultSetRow {
	private Vector row = null;
	private DBResultSet resultSet = null;
	
	public final static int INVALID_NUMERIC_DATA_VALUE = -1;
	/**
	 * 
	 */
	public DBResultSetRow() {
		this(null);
	}

	public DBResultSetRow(DBResultSet rs) {
		super();
		setResultSet(rs);
	}

	/**
	 * @return
	 */
	public DBResultSet getResultSet() {
		return resultSet;
	}

	/**
	 * @return
	 */
	 protected Vector getRow() {
	 	if( row == null ) {
	 		row = new Vector();
	 	}
		return row;
	}

	/**
	 * @param set
	 */
	public void setResultSet(DBResultSet set) {
		resultSet = set;
	}

	public void addRowElement(Object element) {
		getRow().addElement(element);
	}
	
	public Object getObject(String columnName) {
		try {
			return getRow().elementAt(getResultSet().getColumnIndex(columnName.toUpperCase()));
		} catch( Throwable ex ) {
			return null;
		}
	}
	
	public String getString(String columnName) {
		Object obj = getObject(columnName);
		return obj != null ? obj.toString() : null;
	}
	
	public Date getDate(String columnName) {
		Object obj = getObject(columnName);
		return obj != null ? (Date)obj : null;
	}
	
	public Integer getInteger(String columnName) {
		String num = getString(columnName);
		return num != null ? new Integer(num) : null;
	}
	
	public int getInt(String columnName) {
		Integer num = getInteger(columnName);
		return num != null ? num.intValue() : INVALID_NUMERIC_DATA_VALUE; 
	}
	
	public Long getLong(String columnName) {
		String num = getString(columnName);
		return num != null ? new Long(num) : null; 		
	}

	public long getNativeLong(String columnName) {
		Long num = getLong(columnName);
		return num != null ? num.longValue() : INVALID_NUMERIC_DATA_VALUE; 		
	}
	
	public Iterator rowElements() {
		return getRow().iterator();
	}
	
	public Double getDouble(String columnName) {
		String num = getString(columnName);
		return num != null ? new Double(num) : null;
	}

	public double getNativeDouble(String columnName) {
		Double num = getDouble(columnName);
		return num != null ? num.doubleValue() : (double)INVALID_NUMERIC_DATA_VALUE; 
	}	
}
