/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.util
 * File: ZipUtils.java
 *
 * Property of Leonards / Mindpool
 * Created on May 6, 2006 (4:26:33 PM) 
 */
package leonards.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import leonards.common.base.NestedException;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class ZipUtils {

	/**
	 * 
	 */
	private ZipUtils() {
		super();
	}

	/**
	 * Unzips the file in the destination directory.
	 * @param zipFilePath File to unzip
	 * @param destinationDirectory Directory where the zip file will be unzipped.
	 * @throws NestedException If the file cannot be unzipped in the given directory.
	 */
	public static void unzip(String zipFilePath, String destinationDirectory) throws NestedException {
		   Enumeration entries;
		    ZipFile zipFile = null;

		    try {
		      zipFile = new ZipFile(zipFilePath);

		      entries = zipFile.entries();

		      while(entries.hasMoreElements()) {
		        ZipEntry entry = (ZipEntry)entries.nextElement();

		        if(entry.isDirectory()) {
		        	FileUtils.createDirectory(FileUtils.appendDirectory(destinationDirectory, entry.getName()));
		        } else {
		        	File file = new File(FileUtils.appendDirectory(destinationDirectory, entry.getName()));
		        	File parentFile = file.getParentFile();
	        		FileUtils.createDirectory(parentFile.getPath());
		        	copyInputStream(zipFile.getInputStream(entry),
		 		           new BufferedOutputStream(new FileOutputStream(file.getPath())));
		        }
		      }
		    } catch (IOException ex) {
		    	throw new NestedException("Could not extract file [" + zipFilePath + "] into directory [" + destinationDirectory + "]", ex);
		    } finally {
		    	if(zipFile != null) {
		    		try { zipFile.close(); } catch(IOException ex) {}
		    	}
		    }
	}

	/**
	 * Reads from the input stream and copies to the output stream.
	 * @param in Input stream.
	 * @param out Output stream.
	 * @throws IOException If I/O errors occur.
	 */
	private static final void copyInputStream(InputStream in, OutputStream out) throws IOException {
		try {
			byte[] buffer = new byte[1024];
			int len;
			
			while((len = in.read(buffer)) >= 0) {
				out.write(buffer, 0, len);
			}
		} finally {
			try { in.close(); } catch(IOException ex) {}
			try { out.close(); } catch(IOException ex) {}
		}
	}
	
	public static void main(String args[]) {
		try {
			ZipUtils.unzip("/home/mariano/test/a.zip", "/home/mariano/test/dir");
		} catch(NestedException ex) {
			System.err.println(ex.getNestedMessage());
			ex.printStackTrace();
		}
	}
}
