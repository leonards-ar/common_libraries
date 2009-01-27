/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.base
 * File: PaginableList.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 25, 2006 (10:02:51 AM) 
 */
package leonards.common.base;

import java.io.Serializable;
import java.util.List;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class PaginableList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7042964087721083509L;

	public static final int DEFAULT_PAGE_SIZE = 15;
	
	private List list = null;
	
	private int pageSize = DEFAULT_PAGE_SIZE;
	private int currentPage = 1;
	
	/**
	 * 
	 */
	public PaginableList(List list, int pageSize) {
		super();
	}

	public void changeToNextPage() {
		if(isNextPage()) {
			currentPage++;
		}
	}
	
	public void changeToPreviousPage() {
		if(isPreviousPage()) {
			currentPage--;
		}
	}
	
	public void changeToFirstPage() {
		
	}
	public void changeToLastPage() {
		
	}
	
	public List getFirstPage() {
		return null;
	}
	
	public List getLastPage() {
		return null;
	}
	
	public List getNextPage() {
		return null;
	}
	
	public List getPreviousPage() {
		return null;
	}

	public boolean isNextPage() {
		return false;
	}
	
	public boolean isPreviousPage() {
		return false;
	}
	
	public List getCurrentPage() {
		return null;
	}
}
