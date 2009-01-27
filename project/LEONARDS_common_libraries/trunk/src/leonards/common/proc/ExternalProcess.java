/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.proc
 * File: ExternalProcess.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 22, 2006 (12:00:39 AM) 
 */
package leonards.common.proc;

import java.io.IOException;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class ExternalProcess {
	private String command = null;
	private ExternalProcessExecutionResults executionResults = null;
	
	/**
	 * Constructor.
	 * @param command Command to execute. For example: /usr/bin/ls
	 */
	public ExternalProcess(String command) {
		super();
		setCommand(command);
	}

	/**
	 * @return Returns the command.
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command The command to set.
	 */
	protected void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return Returns the standardError.
	 */
	public String getStandardError() {
		return getExecutionResults().getStandardError();
	}

	/**
	 * @param standardError The standardError to set.
	 */
	protected void setStandardError(String standardError) {
		getExecutionResults().setStandardError(standardError);
	}

	/**
	 * @return Returns the standardOutput.
	 */
	public String getStandardOutput() {
		return getExecutionResults().getStandardOutput();
	}

	/**
	 * @param standardOutput The standardOutput to set.
	 */
	protected void setStandardOutput(String standardOutput) {
		getExecutionResults().setStandardOutput(standardOutput);
	}

	/**
	 * @return Returns the status.
	 */
	public int getStatus() {
		return getExecutionResults().getStatus();
	}

	/**
	 * @param status The status to set.
	 */
	protected void setStatus(int status) {
		getExecutionResults().setStatus(status);
	}

	/**
	 * Executes the command.
	 *
	 */
	public void execute() throws ExternalProcessException {
		try {
			long start = System.currentTimeMillis();
			Process proc = Runtime.getRuntime().exec(getCommand());
			
			ExternalProcessOutputReader stdOut = new ExternalProcessOutputReader(proc.getInputStream());
			ExternalProcessOutputReader stdErr = new ExternalProcessOutputReader(proc.getErrorStream());
			
			Thread stdOutThread = new Thread(stdOut);
			Thread stdErrThread = new Thread(stdErr);
			stdOutThread.start();
			stdErrThread.start();
			
			setStatus(proc.waitFor());
			
			stdOutThread.join();
			stdErrThread.join();
			
			setStandardOutput(stdOut.getOutput());
			setStandardError(stdErr.getOutput());
			
			setExecutionTime(System.currentTimeMillis() - start);
			
		} catch(IOException ex) {
			throw new ExternalProcessException(ex.getMessage(), ex);
		} catch(InterruptedException ex) {
			throw new ExternalProcessException(ex.getMessage(), ex);
		}
	}
	
	/**
	 * @return Returns the executionTime.
	 */
	public long getExecutionTime() {
		return getExecutionResults().getExecutionTime();
	}

	/**
	 * @param executionTime The executionTime to set.
	 */
	protected void setExecutionTime(long executionTime) {
		getExecutionResults().setExecutionTime(executionTime);
	}

	/**
	 * @return Returns the executionResults.
	 */
	public ExternalProcessExecutionResults getExecutionResults() {
		if(executionResults == null) {
			executionResults = new ExternalProcessExecutionResults();
		}
		return executionResults;
	}
}
