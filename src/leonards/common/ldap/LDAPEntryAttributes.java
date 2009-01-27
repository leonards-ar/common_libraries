/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.ldap
 * File: LDAPEntryAttributes.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 25, 2006 (11:40:32 AM) 
 */
package leonards.common.ldap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class LDAPEntryAttributes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7394952573580371628L;

	/**
	 * 
	 */
	private Map attributes = new HashMap();

	/**
	 * 
	 */
	public LDAPEntryAttributes() {
		super();
	}

	/**
	 * 
	 * @param attribute
	 */
	public void addAttribute(LDAPEntryAttribute attribute) {
		this.attributes.put(attribute.getName().toLowerCase(), attribute);
	}
	
	/**
	 * 
	 *
	 */
	public void resetAttributes() {
		this.attributes.clear();
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCount() {
		return this.attributes.size();
	}
	
	/**
	 * 
	 * @return
	 */
	public Iterator attributes() {
		List attrs = new ArrayList();
		Map.Entry anEntry;
		for(Iterator it = this.attributes.entrySet().iterator(); it.hasNext(); ) {
			anEntry = (Map.Entry) it.next();
			attrs.add(anEntry.getValue());
		}
		return attrs.iterator();
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public LDAPEntryAttribute getAttribute(String name) {
		return (LDAPEntryAttribute) this.attributes.get(name.toLowerCase());
	}
}
