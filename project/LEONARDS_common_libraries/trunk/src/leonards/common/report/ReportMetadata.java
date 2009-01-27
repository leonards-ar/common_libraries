/*
 * Created on Aug 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package leonards.common.report;

import java.util.Iterator;
import java.util.Vector;

import leonards.common.sql.DBResultSet;

/**
 * @author Mariano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ReportMetadata {
	private Vector columns = null;
	
	/**
	 * 
	 */
	public ReportMetadata() {
		super();
	}
	
	/**
	 * 
	 * @param rs
	 */
	public ReportMetadata(DBResultSet rs) {
		this();
		Iterator colNames = rs.getColumnNames();
		ColumnMetaData columnMetadata;
		while(colNames.hasNext()) {
			columnMetadata = new ColumnMetaData();
			columnMetadata.setColumnName(colNames.next().toString());
			addColumn(columnMetadata);			
		}		
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public ColumnMetaData getColumn(int index) {
		if( index >= 0 && index < getColumns().size() ) {
			return (ColumnMetaData)getColumns().elementAt(index);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param column
	 */
	public void addColumn(ColumnMetaData column) {
		getColumns().addElement(column);
	}

	/**
	 * 
	 * @param label
	 * @return
	 */
	public ColumnMetaData getColumn(String label) {
		return getColumn(getColumns().indexOf(new ColumnMetaData(label)));
	}
	
	/**
	 * @return
	 */
	protected Vector getColumns() {
		if(columns == null) {
			setColumns(new Vector());
		}
		return columns;
	}

	/**
	 * 
	 * @return
	 */
	public int getColumnCount() {
		return getColumns().size();
	}

	/**
	 * @param vector
	 */
	protected void setColumns(Vector vector) {
		columns = vector;
	}

}
