/*
 * File: SimpleAuthenticator.java
 * Created on 21/05/2005
 * 
 */
package leonards.common.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class SimpleAuthenticator extends Authenticator {
	
	private String username = null;
	private String password = null;
	
	public SimpleAuthenticator() {
		this(null, null);
	}
	
	/**
	 * 
	 * Constructor
	 * @param username
	 * @param password
	 */
	public SimpleAuthenticator(String username, String password) {
		super();
		setUsername(username);
		setPassword(password);
	}
	
	/**
	 * 
	 * @return
	 * @see javax.mail.Authenticator#getPasswordAuthentication()
	 */
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(getUsername(), getPassword());
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
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
