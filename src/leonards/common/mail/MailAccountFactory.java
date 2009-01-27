/*
 * File: MailAccountFactory.java
 * Created on 20/05/2005
 * 
 */
package leonards.common.mail;

import java.util.Hashtable;
import java.util.Iterator;

import leonards.common.base.CommonUtils;
import leonards.common.conf.Configuration;
import leonards.common.conf.ConfigurationException;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class MailAccountFactory {
	private static Hashtable mailAccounts = null;
	private static Configuration mailConfiguration = null;
	
	protected static final String POP_ACCOUNT_TYPE = "POP3";
	protected static final String IMAP_ACCOUNT_TYPE = "IMAP";
	
	protected static final String DEFAULT_ACCOUNT_NAME = "default";
	
	/**
	 * Constructor
	 * 
	 */
	private MailAccountFactory() {
		super();
	}

	/**
	 * 
	 * @param accountName
	 * @return
	 * @throws MailException
	 */
	public static MailAccount getMailAccount(String accountName) throws MailException {
		String accountType = (String)getMailAccounts().get(accountName.toUpperCase());
		MailAccount account = null;
		if(CommonUtils.hasValue(accountType)) {
			if(POP_ACCOUNT_TYPE.equalsIgnoreCase(accountType)) {
				account = new Pop3MailAccount(accountName);
				account.setup(getMailConfiguration());
				return account;
			} else {
				throw new MailConfigurationException("The account type [" + accountType + "] is invalid for mail account [" + accountName + "]");
			}
		} else {
			throw new MailConfigurationException("There is no account named [" + accountName + "] in mail configuration");
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws MailException
	 */
	public static MailAccount getMailAccount() throws MailException {
		return getMailAccount(DEFAULT_ACCOUNT_NAME);
	}
	

	/**
	 * 
	 * @return
	 * @throws MailConfigurationException
	 */
	private static Hashtable getMailAccounts() throws MailConfigurationException {
		if(mailAccounts == null) {
			mailAccounts = retrieveMailAccounts();
		}
		return mailAccounts;
	}

	/**
	 * 
	 * @return
	 * @throws MailConfigurationException
	 */
	private static Configuration getMailConfiguration() throws MailConfigurationException {
		if(mailConfiguration == null) {
			try {
				mailConfiguration = new Configuration("mail");
			} catch(ConfigurationException ex) {
				throw new MailConfigurationException("Could not retrieve mail configuration", ex);
			}
		}
		
		return mailConfiguration;
	}

	/**
	 * 
	 * @return
	 * @throws MailConfigurationException
	 */
	private static Hashtable retrieveMailAccounts() throws MailConfigurationException {
		try {
			Hashtable accounts = new Hashtable();
			Iterator accountNames = getMailConfiguration().getConfigurationKeyPrefixes();
			String anAccountName, anAccountType;
			
			while(accountNames.hasNext()) {
				anAccountName = accountNames.next().toString();
				anAccountType = getMailConfiguration().getString(anAccountName + ".account_type");
				accounts.put(anAccountName.toUpperCase(), anAccountType);
			}
					
			return accounts;
		} catch(ConfigurationException ex) {
			throw new MailConfigurationException("Could not retrieve mail accounts configuration", ex);
		}
	}

	/**
	 * 
	 *
	 */
	public static void reset() {
		mailAccounts = null;
	}
}
