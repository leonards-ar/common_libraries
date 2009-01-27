/*
 * Created on 26/04/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package leonards.common.base;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * @author Mariano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CommonUtils {

	private static String newLine = null;
	private static String pathSeparator = null;
	
	protected static String DEFAULT_NEW_LINE_STR = "\n";
	protected static String DEFAULT_PATH_SEPARATOR_STR = "/";
	
	/**
	 * 
	 */
	private CommonUtils() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getPathSeparator() {
		if( pathSeparator == null ) {
			pathSeparator = System.getProperty("file.separator") != null ? System.getProperty("file.separator") : DEFAULT_PATH_SEPARATOR_STR;
		}
		return pathSeparator;
	}	
	
	/**
	 * 
	 * @return
	 */
	public static String getNewLine() {
		if( newLine == null ) {
			newLine = System.getProperty("line.separator") != null ? System.getProperty("line.separator") : DEFAULT_NEW_LINE_STR;
		}
		return newLine;
	}
	
	/**
	 * 
	 * @param hashtable
	 * @return
	 */
	public static List getVector(Hashtable hashtable) {
		List elements = new Vector();
		if(hashtable != null) {
			Enumeration keys = hashtable.keys();
			while(keys.hasMoreElements()) {
				elements.add(hashtable.get(keys.nextElement()));
			}
		}
		return elements;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean hasValue(String value) {
		return value != null && value.trim().length() > 0;
	}
}
