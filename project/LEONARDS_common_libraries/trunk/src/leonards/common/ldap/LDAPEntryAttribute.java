/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.ldap
 * File: LDAPEntryAttribute.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 22, 2006 (1:03:40 AM) 
 */
package leonards.common.ldap;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import leonards.common.base.CommonUtils;
import leonards.common.base.SecurityToolkit;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public abstract class LDAPEntryAttribute implements Serializable {
	private String name = null;
	
	/**
	 * 
	 */
	public LDAPEntryAttribute() {
		super();
	}

	/**
	 * 
	 * @param name
	 */
	public LDAPEntryAttribute(String name) {
		super();
		setName(name);
	}

	/**
	 * 
	 * @return
	 */
	public abstract Object getValue();

	/**
	 * 
	 * @return
	 */
	public abstract List getValues();

	/**
	 * 
	 * @return
	 */
	public abstract boolean isMultiValued();
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public String toLDIFString() {
		StringBuffer buffer = new StringBuffer();
		Iterator valuesToFormat = getValues().iterator();
		while( valuesToFormat.hasNext() ) {
			Object value = valuesToFormat.next();
			buffer.append( getName() );
			if(value instanceof byte[]) {
				buffer.append(":: ");
				buffer.append(SecurityToolkit.encodeBase64((byte[])value));
			} else {
				buffer.append(": ");
				buffer.append(String.valueOf(value) );
			}			
			buffer.append(CommonUtils.getNewLine());
		}
		return buffer.toString();
	}
	
}
