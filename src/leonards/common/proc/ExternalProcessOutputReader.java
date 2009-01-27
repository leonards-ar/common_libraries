/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.proc
 * File: ExternalProcessOutputReader.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 22, 2006 (12:09:25 AM) 
 */
package leonards.common.proc;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import leonards.common.base.CommonUtils;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class ExternalProcessOutputReader implements Runnable {
	private InputStream source = null;
	private String output = null;
	
	/**
	 * 
	 */
	public ExternalProcessOutputReader(InputStream source) {
		super();
		this.source = new BufferedInputStream(source);
	}

	/**
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		StringBuffer sb = new StringBuffer();
		try {
			int c;
			while((c = this.source.read()) != -1) {
				sb.append((char)c);
			}
		} catch(IOException ex) {
			sb.append(CommonUtils.getNewLine());
			sb.append("ERROR. Could not read: [" + ex.getClass().getName() + "] " + ex.getMessage());
		} finally {
			setOutput(sb.toString());
		}
	}

	/**
	 * @return Returns the output.
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * @param output The output to set.
	 */
	protected void setOutput(String output) {
		this.output = output;
	}

}
