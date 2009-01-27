/*
 * File: RotationalHashtable.java
 * Created on 16/05/2005
 * 
 */
package leonards.common.util;

import java.io.PrintStream;
import java.util.Hashtable;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class RotationalHashtable extends Hashtable {

	private static final long serialVersionUID = -4407851004766095528L;
	
	public final static int DEFAULT_MAX_SIZE = 100000;
	private int maxSize = DEFAULT_MAX_SIZE;
	private RotationHashtableElement firstElement = null;
	private RotationHashtableElement lastElement = null;
	
	/**
	 * 
	 */
	public RotationalHashtable() {
		this(DEFAULT_MAX_SIZE);
	}

	/**
	 * @param initialCapacity
	 */
	public RotationalHashtable(int maxSize) {
		super(maxSize);
		setMaxSize(maxSize);
	}

	/**
	 * @return
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param maxSize
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/** 
	 * @param key
	 * @return
	 * @see java.util.Dictionary#get(java.lang.Object)
	 */
	public synchronized Object get(Object key) {
		RotationHashtableElement elem = (RotationHashtableElement)super.get(key);
		doubleLinkedListGetUpdate(elem);
		return elem != null ? elem.getData() : null;
	}

	/** 
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Dictionary#put(java.lang.Object, java.lang.Object)
	 */
	public synchronized Object put(Object key, Object value) {
		RotationHashtableElement elem = new  RotationHashtableElement();
		elem.setKey(key);
		elem.setData(value);

		while(size() >= getMaxSize()) {
			remove(firstElement.getKey());
		}

		doubleLinkedListAddLast(elem);
		return super.put(key,elem);
	}

	/** 
	 * @param key
	 * @return
	 * @see java.util.Dictionary#remove(java.lang.Object)
	 */
	public synchronized Object remove(Object key) {
		RotationHashtableElement elem = (RotationHashtableElement)super.remove(key);
		
		if(elem == null) return null;
		
		doubleLinkedListRemove(elem);
		
		return elem.getData();
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	private void doubleLinkedListAddLast(RotationHashtableElement elem) {
		elem.setNextElement(null);

		if( firstElement == null ) {
			elem.setPreviousElement(null);
			firstElement = elem;
		} else {
			lastElement.setNextElement(elem);
			elem.setPreviousElement(lastElement);
		}
		lastElement = elem;
	}
	
	/**
	 * 
	 * @param elem
	 */
	private void doubleLinkedListGetUpdate(RotationHashtableElement elem) {
		doubleLinkedListRemove(elem);
		doubleLinkedListAddLast(elem);
	}
	
	/**
	 * 
	 * @param elem
	 */
	private void doubleLinkedListRemove(RotationHashtableElement elem) {
		if(elem.getPreviousElement() == null) {
			// First element
			if( elem.getNextElement() != null ) {
				// This is not the only element
				elem.getNextElement().setPreviousElement(null);
			}
			firstElement = elem.getNextElement();
		} else if( elem.getNextElement() == null) {
			// Last element
			elem.getPreviousElement().setNextElement(null);
			lastElement = elem.getPreviousElement();
		} else {
			// Nor first nor last element
			elem.getPreviousElement().setNextElement(elem.getNextElement());
			elem.getNextElement().setPreviousElement(elem.getPreviousElement());
		}
	}
	
	/**
	 * 
	 * @param out
	 */
	public void printdoubleLinkedList(PrintStream out) {
		RotationHashtableElement anElement = firstElement;
		int spaces = 0;
		while(anElement != null) {
			for(int i = 0; i < spaces; i++) {
				out.print(" ");
			}
			spaces++;
			out.println(anElement.toString());
			anElement = anElement.getNextElement();
		}
	}
}
