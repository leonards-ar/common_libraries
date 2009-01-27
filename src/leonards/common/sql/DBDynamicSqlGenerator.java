/*
 * Created on Jul 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package leonards.common.sql;

import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author Mariano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DBDynamicSqlGenerator {
	private Vector selectElements = null;
	private Vector orderByElements = null;
	private Vector whereElements = null;
	private Vector whereElementMappings = null;
	private Vector tableElements = null;
	private Vector finalElements = null;
	private Vector groupByElements = null;
	
	/**
	 * 
	 */
	public DBDynamicSqlGenerator() {
		super();
	}

	/**
	 * @return
	 */
	public Vector getOrderByElements() {
		if(orderByElements == null) {
			orderByElements = new Vector();
		}
		return orderByElements;
	}

	public Vector getGroupByElements() {
		if(groupByElements == null) {
			groupByElements = new Vector();
		}
		return groupByElements;		
	}

	/**
	 * @return
	 */
	public Vector getSelectElements() {
		if(selectElements == null) {
			selectElements = new Vector();
		}
		return selectElements;
	}

	/**
	 * @return
	 */
	public Vector getTableElements() {
		if(tableElements == null) {
			tableElements = new Vector();
		}
		return tableElements;
	}

	/**
	 * @return
	 */
	public Vector getFinalElements() {
		if(finalElements == null) {
			finalElements = new Vector();
		}
		return finalElements;
	}
		
	/**
	 * @return
	 */
	public Vector getWhereElements() {
		if(whereElements == null) {
			whereElements = new Vector();
		}
		return whereElements;
	}

	/**
	 * @return
	 */
	public Vector getWhereElementMappings() {
		if(whereElementMappings == null) {
			whereElementMappings = new Vector();
		}
		return whereElementMappings;
	}
	
	public void addWhereElement(String element, Object mapping) {
		if( mapping != null ) {
			getWhereElements().addElement(element);
			getWhereElementMappings().addElement(mapping);
		}
	}
	
	public void addGroupByElement(String element) {
		getGroupByElements().addElement(element);
	}
	
	public boolean hasGroupByElements() {
		return getGroupByElements().size() > 0;
	}
	
	public boolean hasWhereElements() {
		return getWhereElements().size() > 0;
	}
	
	public void updateParameters(DBStatement st) {
		Object aMapping;
		for( int i = 0; i < getWhereElementMappings().size(); i++) {
			aMapping = getWhereElementMappings().elementAt(i);
			if( aMapping instanceof java.util.Date ) {
				st.setDate(i+1, (Date)aMapping);		
			} else if( aMapping instanceof Integer ) {
				st.setInt(i+1, (Integer)aMapping);
			} else if( aMapping instanceof Double ) {
				st.setDouble(i+1, (Double)aMapping);
			} else if( aMapping instanceof Boolean ) {
				st.setBoolean(i+1, (Boolean)aMapping);
			} else if( aMapping instanceof Long ) {
				st.setLong(i+1, (Long)aMapping);
			} else {
				st.setString(i+1, aMapping.toString());
			}
		}
	}
	
	public void addConstantWhereElement(String element) {
		getWhereElements().addElement(element);
	}
		
	public void addSelectElement(String element) {
		getSelectElements().addElement(element);
	}
	
	public void addSelectElements(String elements) {
		StringTokenizer st = new StringTokenizer(elements, ",");
		while( st.hasMoreTokens() ) {
			addSelectElement(st.nextToken());
		}
	}

	public void addTableElement(String element) {
		getTableElements().addElement(element);
	}
	
	public void addTableElements(String elements) {
		StringTokenizer st = new StringTokenizer(elements, ",");
		while( st.hasMoreTokens() ) {
			addTableElement(st.nextToken());
		}
	}
	
	public void addOrderByElement(String element) {
		getOrderByElements().addElement(element);
	}
	
	public void addOrderByElements(String elements) {
		StringTokenizer st = new StringTokenizer(elements, ",");
		while( st.hasMoreTokens() ) {
			addOrderByElement(st.nextToken());
		}
	}
	
	public void addFinalElement(String element) {
		getFinalElements().addElement(element);
	}
	
	public String getSqlStatement() {
		StringBuffer sql = new StringBuffer();
		sql.append("select");
		sql.append(elementAppender(getSelectElements(), ","));
		sql.append("from");
		sql.append(elementAppender(getTableElements(), ","));
		if( hasWhereElements() ) {
			sql.append("where");
			sql.append(elementAppender(getWhereElements(), "and"));		
		}
		if( hasGroupByElements() ) {
			sql.append("group by");
			sql.append(elementAppender(getOrderByElements(), ","));
		}
		if( getOrderByElements().size() > 0 ) {
			sql.append("order by");
			sql.append(elementAppender(getOrderByElements(), ","));
		}
		if( getFinalElements().size() > 0 ) {
			sql.append(elementAppender(getFinalElements(), " "));
		}
		return sql.toString();
	}
	
	private String elementAppender(Vector elements, String separator) {
		StringBuffer sql = new StringBuffer();
		Iterator i = elements.iterator();
		sql.append(" ");
		while( i.hasNext() ) {
			sql.append(i.next().toString());
			if( i.hasNext() ) {
				sql.append(" " + separator + " ");	
			}
		}
		sql.append(" ");
		return sql.toString();		
	}
}
