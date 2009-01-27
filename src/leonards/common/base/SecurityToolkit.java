/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.base
 * File: SecurityToolkit.java
 *
 * Property of Leonards / Mindpool
 * Created on 17/06/2004
 */
package leonards.common.base;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class SecurityToolkit {

	/**
	 * 
	 */
	private SecurityToolkit() {
		super();
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static long crc32(String value) {
		java.util.zip.CRC32 crc = new java.util.zip.CRC32();
		crc.update(value.getBytes());
		return crc.getValue();
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String md5(String value) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(value.getBytes() );
	
			return toHex( md.digest() );
		} catch( java.security.NoSuchAlgorithmException ex ) {
			return null;
		}
	}

	/**
	 * 
	 * @param hash
	 * @return
	 */
	private static String toHex(byte[] hash) {
		StringBuffer buf = new StringBuffer(hash.length * 2);
		int i; 
	
		for (i = 0; i < hash.length; i++) {
			if (((int) hash[i] & 0xff) < 0x10)
				buf.append("0");
			buf.append(Long.toString((int) hash[i] & 0xff, 16));
		}
			return buf.toString();
	} 
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String encodeBase64(byte value[]) {
		BASE64Encoder enc = new BASE64Encoder();
		return enc.encode(value);
	}

	/**
	 * 
	 * @param base64Value
	 * @return
	 */
	public static byte[] decodeBase64(String base64Value) {
		try {
			BASE64Decoder dec = new BASE64Decoder();
			return dec.decodeBuffer(base64Value);
		} catch(IOException ex) {
			return null;
		}
	}
}
