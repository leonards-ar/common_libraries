/*
 * File: MailAccount.java
 * Created on 20/05/2005
 * 
 */
package leonards.common.mail;

import leonards.common.conf.Configuration;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public abstract class MailAccount {
	
	
	private String name = null;
		
	/**
	 * Constructor
	 * 
	 */
	public MailAccount() {
		this(null);
	}
	
	/**
	 * 
	 * Constructor
	 * @param name
	 */
	public MailAccount(String name) {
		super();
		setName(name);
	}
	
	/**
	 * 
	 * @param conf
	 * @throws MailException
	 */
	public abstract void setup(Configuration conf) throws MailException;
	
	/**
	 * 
	 * @param message
	 * @throws MailException
	 */
	public abstract void sendMessage(MailMessage message) throws MailException;
	
 	/**
	 * 
	 * @param messages
	 * @throws MailException
	 */	
	public void sendMessages(MailMessage messages[]) throws MailException {
		if(messages != null) {
			for(int i = 0; i < messages.length; i++) {
				sendMessage(messages[i]);
			}
		}
	}
	
	/**
	 * 
	 * @param folder
	 * @param removeFromServer
	 * @return
	 * @throws MailException
	 */
	public abstract MailMessage[] readMessages(String folder, boolean removeFromServer) throws MailException;
	
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
