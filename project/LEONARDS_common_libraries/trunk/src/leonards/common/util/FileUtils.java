/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.util
 * File: FileUtils.java
 *
 * Property of Leonards / Mindpool
 * Created on May 6, 2006 (5:04:46 PM) 
 */
package leonards.common.util;

import java.io.File;

import leonards.common.base.CommonUtils;
import leonards.common.base.NestedException;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class FileUtils {

	/**
	 * 
	 */
	private FileUtils() {
		super();
	}

	/**
	 * 
	 * @param directory
	 * @throws NestedException
	 */
	public static void createDirectory(String directory) throws NestedException {
		createDirectory(new File(directory));
	}

	/**
	 * 
	 * @param directory
	 * @throws NestedException
	 */
	public static void createDirectory(File directory) throws NestedException {
    	if(!directory.exists()) {
    		if(!directory.mkdirs()) {
    			throw new NestedException("Could not create directory [" + directory + "]");
    		}
    	}
	}
	
	/**
	 * 
	 * @param directory
	 * @param examiner
	 * @throws NestedException
	 */
	public static void examineDirectory(String directory, DirectoryExaminerCallback examiner) throws NestedException {
		examineDirectory(new File(directory), examiner);
	}

	/**
	 * 
	 * @param parentDir
	 * @param path
	 * @return
	 */
	public static String appendDirectory(String parentDir, String path) {
		StringBuffer completeDir = new StringBuffer(parentDir);
		if(!parentDir.trim().endsWith(CommonUtils.getPathSeparator())) {
			completeDir.append(CommonUtils.getPathSeparator());
		}
		completeDir.append(path);
		return completeDir.toString();
	}
	
	/**
	 * 
	 * @param parentDir
	 * @param path
	 * @return
	 */
	public static String appendDirectory(File parentDir, String path) {
		return appendDirectory(parentDir.getPath(), path);
	}
	
	/**
	 * 
	 * @param directory
	 * @param examiner
	 * @throws NestedException
	 */
	public static void examineDirectory(File directory, DirectoryExaminerCallback examiner) throws NestedException {
		if(!directory.isDirectory()) {
			throw new NestedException("Invalid or inexistant directory [" + directory + "]");
		}
		
		File[] children = directory.listFiles();
		File aChild;
		for(int i = 0; i < children.length; i++) {
			aChild = children[i];
			if(aChild.isDirectory()) {
				if(examiner.processDirectory(aChild)) {
					examineDirectory(aChild, examiner);
				}
			} else if(aChild.isFile()) {
				examiner.processFile(aChild);
			}
		}
	}

	/**
	 * 
	 * @param root
	 * @param file
	 * @return
	 */
	public static String getRelativePath(File root, File file) {
		String rootPath = root.getPath();
		String filePath = file.getPath();
		
		if(filePath.startsWith(rootPath)) {
			return filePath.substring(rootPath.length() + 1);
		} else {
			return filePath;
		}
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileExtension(File file) {
		return getFileExtension(file.getName());
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileExtension(String file) {
		int dotIdx = file != null ? file.lastIndexOf('.') : -1;
		if(dotIdx >= 0) {
			return file.substring(dotIdx + 1);
		} else {
			return "";
		}
	}
}
