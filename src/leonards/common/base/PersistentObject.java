/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.base
 * File: PersistentObject.java
 *
 * Property of Leonards / Mindpool
 * Created on 17/06/2004
 */
package leonards.common.base;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public abstract class PersistentObject implements Cloneable {
	private Object id = null;
	
	/**
	 * 
	 */
	public PersistentObject() {
		this(null);
	}

	/**
	 * 
	 */
	public PersistentObject(Object id) {
		super();
		setId(id);
	}

	/**
	 * @return
	 */
	public Object getId() {
		return id;
	}

	/**
	 * @param object
	 */
	public void setId(Object object) {
		id = object;
	}

	/**
	 * 
	 * @return
	 */
	public Long getIdAsLong() {
		return (Long)getId();
	}

	/**
	 * 
	 * @return
	 */
	public Integer getIdAsInteger() {
		return (Integer)getId();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getIdAsString() {
		return getId().toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isPersistent() {
		return getId() != null;
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if( o == this ) return true;
		if( getId() == null || o == null) return false;
		if( !this.getClass().equals(o.getClass()) ) return false;
		
		return getId().equals(((PersistentObject)o).getId());
	}

	

	/**
	 * 
	 * @throws NestedException
	 */
	protected abstract void update() throws NestedException;
	
	/**
	 * 
	 * @throws NestedException
	 */
	protected abstract void insert() throws NestedException;
	
	/**
	 * 
	 * @throws NestedException
	 */
	public abstract void delete() throws NestedException;
	
	/**
	 * 
	 * @throws NestedException
	 */
	public void save() throws NestedException {
		if( isPersistent() ) {
			update();
		} else {
			insert();
		}
	}
	
	/** 
	 * Makes a shallow copy of this instance
	 * @return An object representing the copy 
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return getId() == null ? System.identityHashCode(this) : getId().hashCode();
	}

}
