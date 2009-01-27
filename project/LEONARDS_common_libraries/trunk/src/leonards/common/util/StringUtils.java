/**
 * 
 */
package leonards.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import leonards.common.base.CommonUtils;
import leonards.common.base.NestedException;

/**
 * @author Mariano
 *
 */
public class StringUtils {

	/**
	 * 
	 */
	public StringUtils() {
		super();
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasValue(String str) {
		return str != null && str.trim().length() > 0;
	}
	
	/**
	 * 
	 * @param file
	 * @param contents
	 * @throws NestedException
	 */
	public static void writeFile(String file, String contents) throws NestedException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(contents);
		} catch(IOException ex) {
			throw new NestedException("Could not write to file [" + file + "] contents.", ex);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException ex) {
					// What??
				}
			}

		}
		
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws NestedException
	 */
	public static String readFile(String file) throws NestedException {
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			boolean isFirstLine = true;
			while( (line = reader.readLine()) != null) {
				if(!isFirstLine) {
					contents.append(CommonUtils.getNewLine());
				}
				contents.append(line);
				if(isFirstLine) {
					isFirstLine = false;
				}
			}
		} catch(IOException ex) {
			throw new NestedException("Could not read file [" + file + "] contents.", ex);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					// What??
				}
			}
		}
		
		return contents.toString();
	}
	
	/**
	 * 
	 * @param txt
	 * @param oldVal
	 * @param newVal
	 * @return
	 */
	public static String replaceAll(String txt, String oldVal, String newVal) {
		StringBuffer newTxt = new StringBuffer();
		
		int fromIdx = 0;
		int toIdx = txt.indexOf(oldVal);

		while (fromIdx < txt.length() && toIdx >= 0 && toIdx < txt.length()) {
			newTxt.append(txt.substring(fromIdx, toIdx));
			newTxt.append(newVal);
			fromIdx = toIdx + oldVal.length();
			toIdx = txt.indexOf(oldVal, fromIdx);
		}

		newTxt.append(txt.substring(fromIdx, txt.length()));
		
		return newTxt.toString();
	}
	
	public static Boolean getBooleanValue(String str) {
		if(str != null) {
			return (str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("y") || str.equalsIgnoreCase("true")) ? Boolean.TRUE : Boolean.FALSE;
		} else {
			return null;
		}
		
	}
}
