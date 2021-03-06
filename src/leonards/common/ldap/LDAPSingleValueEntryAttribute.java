/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.ldap
 * File: LDAPSingleValueEntryAttribute.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 24, 2006 (10:59:34 PM) 
 */
package leonards.common.ldap;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class LDAPSingleValueEntryAttribute extends LDAPEntryAttribute {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2501023016379810331L;
	
	private Object value = null;
	
	/**
	 * 
	 */
	public LDAPSingleValueEntryAttribute() {
		super();
	}

	/**
	 * @param name
	 */
	public LDAPSingleValueEntryAttribute(String name) {
		super(name);
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	public LDAPSingleValueEntryAttribute(String name, Object value) {
		this(name);
		setValue(value);
	}

	/**
	 * @return
	 * @see leonards.common.ldap.LDAPEntryAttribute#getValue()
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * @return
	 * @see leonards.common.ldap.LDAPEntryAttribute#getValues()
	 */
	public List getValues() {
		List values = new ArrayList();
		values.add(this.value);
		return values;
	}

	/**
	 * @return
	 * @see leonards.common.ldap.LDAPEntryAttribute#isMultiValued()
	 */
	public boolean isMultiValued() {
		return false;
	}

	/**
	 * @param value The value to set.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
