/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.util
 * File: NumberUtils.java
 *
 * Property of Leonards / Mindpool
 * Created on Jul 1, 2006 (9:31:56 PM) 
 */
package leonards.common.util;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class NumberUtils {

	/**
	 * 
	 */
	public NumberUtils() {
		super();
	}

	public static boolean isValidLong(String num) {
		try {
			Long.parseLong(num);
			return true;
		} catch(Throwable ex) {
			return false;
		}
	}
}
