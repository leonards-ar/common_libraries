/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.proc
 * File: ExternalProcessExecutionResults.java
 *
 * Property of Leonards / Mindpool
 * Created on Jul 8, 2006 (11:39:23 AM) 
 */
package leonards.common.proc;

import java.io.Serializable;

import leonards.common.util.StringUtils;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class ExternalProcessExecutionResults implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6086739297702715120L;
	private int status = -1;
	private String standardOutput = null;
	private String standardError = null;
	private long executionTime = -1L;

	/**
	 * 
	 */
	public ExternalProcessExecutionResults() {
		super();
	}

	/**
	 * @return Returns the standardError.
	 */
	public String getStandardError() {
		return StringUtils.hasValue(standardError) ? standardError : "";
	}

	/**
	 * @param standardError The standardError to set.
	 */
	protected void setStandardError(String standardError) {
		this.standardError = standardError;
	}

	/**
	 * @return Returns the standardOutput.
	 */
	public String getStandardOutput() {
		return StringUtils.hasValue(standardOutput) ? standardOutput : "";
	}

	/**
	 * @param standardOutput The standardOutput to set.
	 */
	protected void setStandardOutput(String standardOutput) {
		this.standardOutput = standardOutput;
	}

	/**
	 * @return Returns the status.
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	protected void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * @return Returns the executionTime.
	 */
	public long getExecutionTime() {
		return executionTime;
	}

	/**
	 * @param executionTime The executionTime to set.
	 */
	protected void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}	
}
