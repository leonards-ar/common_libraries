/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.base
 * File: ApplicationUser.java
 *
 * Property of Leonards / Mindpool
 * Created on 13/06/2004
 */
package leonards.common.base;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public abstract class ApplicationUser extends PersistentObject {
	private String name = null;
	private String surname = null;
	private String email = null;
	
	/**
	 * 
	 */
	public ApplicationUser() {
		super();
	}


	/**
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param string
	 */
	public void setEmail(String string) {
		email = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setSurname(String string) {
		surname = string;
	}

}
