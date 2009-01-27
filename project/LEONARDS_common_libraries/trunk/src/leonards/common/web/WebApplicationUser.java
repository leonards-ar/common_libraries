/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.web
 * File: WebApplicationUser.java
 *
 * Property of Leonards / Mindpool
 * Created on 13/06/2004
 */
package leonards.common.web;

import leonards.common.base.ApplicationUser;

/**
 * @author Mariano
 *
 * This class is the abstraction of
 */
public abstract class WebApplicationUser extends ApplicationUser {
	private String username = null;
	private String password = null;

	/**
	 * 
	 */
	public WebApplicationUser() {
		this(null, null);
	}

	public WebApplicationUser(String username, String password) {
		super();
		setUsername(username);
		setPassword(password);
	}
	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @param string
	 */
	public void setUsername(String string) {
		username = string;
	}

	/**
	 * @throws LogonException
	 */
	public abstract void logon() throws LogonException;
}
