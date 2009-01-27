/*
 * Created on Aug 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package leonards.common.report;

import java.util.Iterator;
import java.util.Vector;

import leonards.common.sql.DBResultSetRow;

/**
 * @author Mariano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ReportDataRow {
	private Vector values = null;
	
	/**
	 * 
	 */
	public ReportDataRow() {
		super();
	}

	/**
	 * 
	 * @param row
	 */
	public ReportDataRow(DBResultSetRow row) {
		this();
		Iterator elements = row.rowElements();
		while(elements.hasNext()) {
			addValue(elements.next());
		}
	}
	
	/**
	 * @return
	 */
	protected Vector getValues() {
		if(values == null) {
			setValues(new Vector());
		}
		return values;
	}

	/**
	 * @param vector
	 */
	protected void setValues(Vector vector) {
		values = vector;
	}

	/**
	 * 
	 * @param columnIndex
	 * @return
	 */
	public Object getValue(int columnIndex) {
		if(columnIndex >= 0 && columnIndex < getValues().size()) {
			return getValues().elementAt(columnIndex);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param Value
	 */
	public void addValue(Object Value) {
		getValues().addElement(Value);
	}
}
