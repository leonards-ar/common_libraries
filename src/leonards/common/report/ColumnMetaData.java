/*
 * Created on Aug 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package leonards.common.report;

/**
 * @author Mariano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ColumnMetaData {
	private String label = null;
	private String alignment = null;
	private String columnName = null;
	private ColumnFormatter formatter = null;
	
	public static final String DEFAULT_ALIGNMENT = "left";
	
	/**
	 * 
	 */
	public ColumnMetaData() {
		this(null);
	}

	/**
	 * 
	 * @param label
	 */
	public ColumnMetaData(String label) {
		this(label, null, null, null);
	}

	/**
	 * 
	 * @param label
	 * @param columnName
	 * @param alignment
	 */
	public ColumnMetaData(String label, String columnName, String alignment) {
		this(label, columnName, alignment, null);
	}
		
	/**
	 * 
	 * @param label
	 * @param columnName
	 * @param alignment
	 * @param formatter
	 */
	public ColumnMetaData(String label, String columnName, String alignment, ColumnFormatter formatter) {
		super();
		setLabel(label);
		setColumnName(columnName);
		setAlignment(alignment);
		setFormatter(formatter);
	}

	/**
	 * @return
	 */
	public String getAlignment() {
		return alignment != null ? alignment : DEFAULT_ALIGNMENT;
	}

	/**
	 * @return
	 */
	public String getColumnName() {
		return columnName != null ? columnName : "undefined";
	}

	/**
	 * @return
	 */
	public ColumnFormatter getFormatter() {
		if(formatter == null) {
			formatter = new DefaultFormatter();
		}
		return formatter;
	}

	/**
	 * @return
	 */
	public String getLabel() {
		return label != null ? label : getColumnName();
	}

	/**
	 * @param string
	 */
	public void setAlignment(String string) {
		alignment = string;
	}

	/**
	 * @param string
	 */
	public void setColumnName(String string) {
		columnName = string;
	}

	/**
	 * @param formatter
	 */
	public void setFormatter(ColumnFormatter formatter) {
		this.formatter = formatter;
	}

	/**
	 * @param string
	 */
	public void setLabel(String string) {
		label = string;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return obj != null && obj instanceof ColumnMetaData && ((ColumnMetaData)obj).getLabel().equals(getLabel());
	}

}
