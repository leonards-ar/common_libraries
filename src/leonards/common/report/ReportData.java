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
import leonards.common.sql.DBResultSetRow;

/**
 * @author Mariano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ReportData {
	private Vector dataRows = null;
	
	/**
	 * 
	 */
	public ReportData() {
		super();
	}
	
	/**
	 * 
	 * @param rs
	 */
	public ReportData(DBResultSet rs) {
		this();
		Iterator rows = rs.getDataRows();
		while(rows.hasNext()) {
			addRow(new ReportDataRow((DBResultSetRow)rows.next()));
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int getRowCount() {
		return getDataRows().size();
	}
	
	/**
	 * 
	 * @return
	 */
	public int size() {
		return getDataRows().size();
	}

	/**
	 * @return
	 */
	public Vector getDataRows() {
		if(dataRows == null) {
			setDataRows(new Vector());
		}
		return dataRows;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public ReportDataRow getRow(int index) {
		if( index >= 0 && index < getDataRows().size() ) {
			return (ReportDataRow)getDataRows().elementAt(index);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param row
	 */
	public void addRow(ReportDataRow row) {
		getDataRows().addElement(row);
	}

	/**
	 * @param vector
	 */
	protected void setDataRows(Vector vector) {
		dataRows = vector;
	}

}
