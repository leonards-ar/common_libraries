/*
 * File: RotationHashtableElement.java
 * Created on 16/05/2005
 * 
 */
package leonards.common.util;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class RotationHashtableElement {

	private Object data = null;
	private RotationHashtableElement nextElement = null;
	private RotationHashtableElement previousElement = null;
	private Object key = null;

	/**
	 * 
	 * @param data
	 * @param previousElement
	 * @param nextElement
	 */
	public RotationHashtableElement(Object key, Object data, RotationHashtableElement previousElement, RotationHashtableElement nextElement) {
		super();
		setKey(key);
		setData(data);
		setPreviousElement(previousElement);
		setNextElement(nextElement);
	}
		
	/**
	 * 
	 *
	 */
	public RotationHashtableElement() {
		this(null, null, null, null);
	}
		
	/**
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @return
	 */
	public RotationHashtableElement getNextElement() {
		return nextElement;
	}

	/**
	 * @return
	 */
	public RotationHashtableElement getPreviousElement() {
		return previousElement;
	}

	/**
	 * @param object
	 */
	public void setData(Object object) {
		data = object;
	}

	/**
	 * @param element
	 */
	public void setNextElement(RotationHashtableElement element) {
		nextElement = element;
	}

	/**
	 * @param element
	 */
	public void setPreviousElement(RotationHashtableElement element) {
		previousElement = element;
	}

	/**
	 * @return
	 */
	public Object getKey() {
		return key;
	}

	/**
	 * @param object
	 */
	public void setKey(Object object) {
		key = object;
	}

	/** 
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String key = getKey() != null ? getKey().toString() : "null";
		String data = getData() != null ? getData().toString() : "null";
		return key + " -> " + data;
	}

}
