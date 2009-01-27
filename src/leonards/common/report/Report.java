/*
 * Created on Aug 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package leonards.common.report;

import java.util.Iterator;

import leonards.common.sql.DBResultSet;

/**
 * @author Mariano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Report {
	private ReportMetadata metaData = null;
	private ReportData data = null;
	private String name = null;
	private String description = null;
	
	/**
	 * 
	 */
	public Report() {
		super();
	}

	/**
	 * 
	 * @param rs
	 */
	public Report(DBResultSet rs) {
		this();
		setup(rs);
	}

	/**
	 * 
	 * @param rs
	 */
	protected void setup(DBResultSet rs) {
		setMetaData(new ReportMetadata(rs));
		setData(new ReportData(rs));
	}

	/**
	 * 
	 * @param rs
	 */
	protected void setupData(DBResultSet rs) {
		Iterator rows = rs.getDataRows();
		while(rows.hasNext()) {
			
		}
	}

	/**
	 * @return
	 */
	public ReportData getData() {
		return data;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return
	 */
	public ReportMetadata getMetaData() {
		return metaData;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param data
	 */
	public void setData(ReportData data) {
		this.data = data;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * @param metadata
	 */
	public void setMetaData(ReportMetadata metadata) {
		metaData = metadata;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

}
