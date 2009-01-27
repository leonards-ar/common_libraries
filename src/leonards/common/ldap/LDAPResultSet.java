/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.ldap
 * File: LDAPResultSet.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 22, 2006 (1:00:57 AM) 
 */
package leonards.common.ldap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class LDAPResultSet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8725620491032853115L;
	
	private List entries = null;
	
	/**
	 * 
	 */
	public LDAPResultSet() {
		super();
	}

	/**
	 * @return Returns the entries.
	 */
	public List getEntries() {
		if(entries == null) {
			setEntries(new ArrayList());
		}
		return entries;
	}

	/**
	 * @param entries The entries to set.
	 */
	protected void setEntries(List entries) {
		this.entries = entries;
	}
	
	/**
	 * 
	 * @return
	 */
	public Iterator entries() {
		return getEntries().iterator();
	}
	
	/**
	 * 
	 * @param entry
	 */
	public void addEntry(LDAPEntry entry) {
		getEntries().add(entry);
	}

	/**
	 * 
	 * @param idx
	 * @return
	 */
	public LDAPEntry getEntry(int idx) {
		return (LDAPEntry) getEntries().get(idx);
	}
}
