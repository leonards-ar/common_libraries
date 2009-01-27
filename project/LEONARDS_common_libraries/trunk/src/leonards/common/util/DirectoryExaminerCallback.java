/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.util
 * File: DirectoryExaminerCallback.java
 *
 * Property of Leonards / Mindpool
 * Created on May 6, 2006 (5:10:14 PM) 
 */
package leonards.common.util;

import java.io.File;

import leonards.common.base.NestedException;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public interface DirectoryExaminerCallback {

	/**
	 * 
	 * @param directory The directory to process.
	 * @returns Whether this directory should be examined too.
	 * @throws NestedException
	 */
	boolean processDirectory(File directory) throws NestedException;
	
	/**
	 * 
	 * @param file
	 * @throws NestedException
	 */
	void processFile(File file) throws NestedException;
	
}
