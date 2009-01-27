/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web.framework
 * File: WebFrameworkToolkit.java
 *
 * Property of Leonards / Mindpool
 * Created on 22/06/2004
 */
package leonards.common.web.framework;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import leonards.common.util.StringUtils;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public class WebFrameworkToolkit {

	/**
	 * 
	 */
	private WebFrameworkToolkit() {
		super();
	}
	
	private static String getCorrectPath(String path) {
		if( path != null ) {
			return path.trim().endsWith("/") ? path.trim() : path.trim() + "/"; 			
		} else {
			return "";
		}
	}

	private static String getApplicationContextPath(String path) {
		if( path != null ) {
			return path.trim().startsWith("/") ? path.trim() : "/" + path.trim(); 			
		} else {
			return "";
		}
	}

	
	private static String concatenatePath(String path1, String path2) {
		if(StringUtils.hasValue(path1) && StringUtils.hasValue(path1)) {
			path1 = getCorrectPath(path1);
			if(path2.startsWith("/")) {
				path2 = path2.substring(1);
			}
			return path1 + path2;
		} else if(StringUtils.hasValue(path1)) {
			return path1;
		} else if(StringUtils.hasValue(path2)) {
			return path2;
		} else {
			return "";
		}
	}
	
	/**
	 * 
	 * @param document
	 * @param request
	 * @return
	 * @throws WebFrameworkException
	 */
	public static String getDocumentUrl(String document, HttpServletRequest request) throws WebFrameworkException {
		return getDocumentUrl(document, request.getContextPath());
	}

	/**
	 * 
	 * @param servlet
	 * @param request
	 * @return
	 * @throws WebFrameworkException
	 */
	public static String getServletUrl(String servlet, HttpServletRequest request) throws WebFrameworkException {
		return getServletUrl(servlet, request.getContextPath());
	}

	/**
	 * 
	 * @param image
	 * @param request
	 * @return
	 * @throws WebFrameworkException
	 */
	public static String getImageUrl(String image, HttpServletRequest request) throws WebFrameworkException {
		return getImageUrl(image, request.getContextPath());
	}

	/**
	 * 
	 * @param document
	 * @param applicationContext
	 * @return
	 * @throws WebFrameworkException
	 */
	public static String getDocumentUrl(String document, String applicationContext) throws WebFrameworkException {
		return getCorrectPath(concatenatePath(getApplicationContextPath(applicationContext), WebFrameworkConfiguration.singleton().getDocumentPath())) + document;
	}

	/**
	 * 
	 * @param servlet
	 * @param applicationContext
	 * @return
	 * @throws WebFrameworkException
	 */
	public static String getServletUrl(String servlet, String applicationContext) throws WebFrameworkException {
		return getCorrectPath(concatenatePath(getApplicationContextPath(applicationContext), WebFrameworkConfiguration.singleton().getServletPath())) + servlet;
	}

	/**
	 * 
	 * @param image
	 * @param applicationContext
	 * @return
	 * @throws WebFrameworkException
	 */
	public static String getImageUrl(String image, String applicationContext) throws WebFrameworkException {
		return getCorrectPath(concatenatePath(getApplicationContextPath(applicationContext), WebFrameworkConfiguration.singleton().getImagesPath())) + image; 
	}
	
	/**
	 * 
	 * @param document
	 * @return
	 * @throws WebFrameworkException
	 */
	public static String getDocumentUrl(String document) throws WebFrameworkException {
		return getCorrectPath(concatenatePath(WebFrameworkConfiguration.singleton().getApplicationContext(), WebFrameworkConfiguration.singleton().getDocumentPath())) + document; 
	}

	/**
	 * 
	 * @param servlet
	 * @return
	 * @throws WebFrameworkException
	 */
	public static String getServletUrl(String servlet) throws WebFrameworkException {
		return getServletUrl(servlet, WebFrameworkConfiguration.singleton().getApplicationContext());
		
	}

	/**
	 * 
	 * @param image
	 * @return
	 * @throws WebFrameworkException
	 */
	public static String getImageUrl(String image) throws WebFrameworkException {
		return getImageUrl(image, WebFrameworkConfiguration.singleton().getApplicationContext());
	}
	
	/**
	 * 
	 * @param document
	 * @return
	 * @throws WebFrameworkException
	 */
	public static String getDocumentForwardUrl(String document) throws WebFrameworkException {
		return getCorrectPath(WebFrameworkConfiguration.singleton().getDocumentPath()) + document; 
	}

	/**
	 * 
	 * @param servlet
	 * @return
	 * @throws WebFrameworkException
	 */
	public static String getServletForwardUrl(String servlet) throws WebFrameworkException {
		return getCorrectPath(WebFrameworkConfiguration.singleton().getServletPath()) + servlet; 
	}

	public static String getHtmlObject(Object obj) {
		return obj != null ? obj.toString() : "";
	}
	
	public static String getHtmlString(String str) {
		//:TODO: Scape special HTML characters
		return str != null ? str : "";
	}

	public static String getHtmlTime(Date date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(WebFrameworkConfiguration.singleton().getTimeFormat(), getLocale());
			return df.format(date);
		} catch( Throwable ex ) {
			return "";
		}
	}
	
	public static String getHtmlLongDate(Date date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(WebFrameworkConfiguration.singleton().getLongDateFormat(), getLocale());
			return df.format(date);
		} catch( Throwable ex ) {
			return "";
		}
	}

	public static String getHtmlShortDate(Date date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(WebFrameworkConfiguration.singleton().getShortDateFormat(), getLocale());
			return df.format(date);
		} catch( Throwable ex ) {
			return "";
		}
	}

	public static Locale getLocale() {
		try {
			return new Locale(WebFrameworkConfiguration.singleton().getLocale());
		} catch(Throwable ex) {
			return new Locale("es");
		}
		
	}

	public static String getHtmlDateTime(Date date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(WebFrameworkConfiguration.singleton().getDateTimeFormat(), getLocale());
			return df.format(date);
		} catch( Throwable ex ) {
			return "";
		}
	}
	
	public static String getJSString(String str) {
		String text = getHtmlString(str);
		StringBuffer jsText = new StringBuffer();
		char c;
		
		for( int i = 0; i < text.length(); i++) {
			c = text.charAt(i);
			switch(c) {
				case '\'':
				jsText.append("\\'");
					break;
				default:
				jsText.append(c);
					break;
			}
		}
		return jsText.toString();
	}
	
	public static String completeWithZeros(String value, int totalLenght) {
		for(int i=0; value != null && i < totalLenght - value.length(); i++) {
			value = "0" + value;
		}
		return value;
	}
	
	public static String getHtmlParagraph(String str) {
		String text = getHtmlString(str);
		StringBuffer paragraph = new StringBuffer();
		char c;
		
		for( int i = 0; i < text.length(); i++) {
			c = text.charAt(i);
			switch(c) {
				case '\n':
					paragraph.append("<br>");
					break;
				case '\r':
					break;
				default:
					paragraph.append(c);
					break;
			}
		}
		return paragraph.toString();
	}
	
	public static String getHtmlDouble(Double num) {
		if( num != null ) {
			try {
				java.text.DecimalFormat df = (java.text.DecimalFormat)java.text.NumberFormat.getNumberInstance(new java.util.Locale("EN", "US"));
				df.applyPattern("0.00");
				return df.format(num);
			} catch( Exception ex ) {
				return null;
			}
	
		} else {
			return "";
		}
	}
	
}
