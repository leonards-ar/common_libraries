/*
 * File: Pop3MailAccount.java
 * Created on 20/05/2005
 * 
 */
package leonards.common.mail;

import leonards.common.base.CommonUtils;
import leonards.common.conf.Configuration;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class Pop3MailAccount extends MailAccount {
	private SmtpSession smtpSession = null;
	
	private String pop3Host = null;
	private int pop3Port = -1;
	private boolean pop3Auth = false;
	private boolean pop3Ssl = false;
	private String pop3Username = null;
	private String pop3Password = null;
	
	
	/**
	 * Constructor
	 * @param name
	 */
	public Pop3MailAccount(String name) {
		super(name);
	}

	/**
	 * Constructor
	 * 
	 */
	public Pop3MailAccount() {
		super();
	}

	/**
	 * @param folder
	 * @param removeFromServer
	 * @return
	 * @throws MailException
	 * @see leonards.common.mail.MailAccount#readMessages(java.lang.String, boolean)
	 */
	public MailMessage[] readMessages(String folder, boolean removeFromServer) throws MailException {
		return null;
	}

	/**
	 * @param message
	 * @throws MailException
	 * @see leonards.common.mail.MailAccount#sendMessages(leonards.common.mail.MailMessage[])
	 */
	public void sendMessage(MailMessage message) throws MailException {
		getSmtpSession().sendMessage(message);
	}

	/**
	 * @param conf
	 * @throws MailException
	 * @see leonards.common.mail.MailAccount#setup(leonards.common.conf.Configuration)
	 */
	public void setup(Configuration conf) throws MailException {
		setupPop3(conf);
		setupSmtp(conf);
	}

	/**
	 * 
	 * @param conf
	 * @throws MailException
	 */
	private void setupPop3(Configuration conf) throws MailException {
		/*
		setPop3Host(conf.getString(getName() + ".pop3.host"));
		if(!CommonUtils.hasValue(getPop3Host())) {
			throw new MailConfigurationException("No pop mail server host provided for account [" + getName() + "]");
		}
		setPop3Port(conf.getInt(getName() + ".pop3.port"));
		setPop3Auth(conf.getBool(getName() + ".pop3.auth", false));
		if(isPop3Auth()) {
			setPop3Username(conf.getString(getName() + ".pop3.username"));
			if(!CommonUtils.hasValue(getPop3Username())) {
				throw new MailConfigurationException("No pop3 username provided for account [" + getName() + "]. If authentication is required, then a username must be provided.");
			}
			setPop3Password(conf.getString(getName() + ".pop3.password"));
		}
		setPop3Ssl(conf.getBool(getName() + ".pop3.ssl", false));
		*/
	}
	
	/**
	 * 
	 * @param conf
	 * @throws MailException
	 */
	private void setupSmtp(Configuration conf) throws MailException {
		getSmtpSession().setHost(conf.getString(getName() + ".smtp.host"));
		if(!CommonUtils.hasValue(getSmtpSession().getHost())) {
			throw new MailConfigurationException("No smtp mail server host provided for account [" + getName() + "]");
		}
		getSmtpSession().setPort(conf.getInt(getName() + ".smtp.port"));
		getSmtpSession().setAuth(conf.getBool(getName() + ".smtp.auth", false));
		if(getSmtpSession().isAuth()) {
			getSmtpSession().setUsername(conf.getString(getName() + ".smtp.username"));
			if(!CommonUtils.hasValue(getSmtpSession().getUsername())) {
				throw new MailConfigurationException("No smtp username provided for account [" + getName() + "]. If authentication is required, then a username must be provided.");
			}
			getSmtpSession().setPassword(conf.getString(getName() + ".smtp.password"));
		}
		getSmtpSession().setSsl(conf.getBool(getName() + ".smtp.ssl", false));
	}

	/**
	 * @return
	 */
	public boolean isPop3Auth() {
		return pop3Auth;
	}

	/**
	 * @return
	 */
	public String getPop3Host() {
		return pop3Host;
	}

	/**
	 * @return
	 */
	public String getPop3Password() {
		return pop3Password;
	}

	/**
	 * @return
	 */
	public int getPop3Port() {
		return pop3Port;
	}

	/**
	 * @return
	 */
	public boolean isPop3Ssl() {
		return pop3Ssl;
	}

	/**
	 * @return
	 */
	public String getPop3Username() {
		return pop3Username;
	}

	/**
	 * @param pop3Auth
	 */
	public void setPop3Auth(boolean pop3Auth) {
		this.pop3Auth = pop3Auth;
	}

	/**
	 * @param pop3Host
	 */
	public void setPop3Host(String pop3Host) {
		this.pop3Host = pop3Host;
	}

	/**
	 * @param pop3Password
	 */
	public void setPop3Password(String pop3Password) {
		this.pop3Password = pop3Password;
	}

	/**
	 * @param pop3Port
	 */
	public void setPop3Port(int pop3Port) {
		this.pop3Port = pop3Port;
	}

	/**
	 * @param pop3Ssl
	 */
	public void setPop3Ssl(boolean pop3Ssl) {
		this.pop3Ssl = pop3Ssl;
	}

	/**
	 * @param pop3Username
	 */
	public void setPop3Username(String pop3Username) {
		this.pop3Username = pop3Username;
	}



	/**
	 * @return
	 */
	public SmtpSession getSmtpSession() {
		if(smtpSession == null) {
			smtpSession = new SmtpSession();
		}
		return smtpSession;
	}

}
