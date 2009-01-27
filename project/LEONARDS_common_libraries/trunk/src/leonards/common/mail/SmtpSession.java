/*
 * File: SmtpSession.java
 * Created on 21/05/2005
 * 
 */
package leonards.common.mail;

import java.security.Provider;
import java.security.Security;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class SmtpSession {
	public static final int DEFAULT_SMTP_PORT = 25;
	public static final int DEFAULT_SMTP_SSL_PORT = 465;
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	private static final String SSL_PROVIDER = "com.sun.net.ssl.internal.ssl.Provider";
	
	private String host = null;
	private int port = -1;
	private boolean auth = false;
	private boolean ssl = false;
	private Authenticator authenticator = null;
	private String username = null;
	private String password = null;
	

	/**
	 * Constructor
	 * 
	 */
	public SmtpSession() {
		super();
	}

	/**
	 * 
	 * @throws MailException
	 */
	private Session getSession() throws MailException {
		Session session = null;
		
		Properties props = System.getProperties();
		props.put("mail.smtp.host", getHost());
		props.put("mail.smtp.port", Integer.toString(getPort()));

		if(isSsl()) {
			try {
				Provider p
					= (Provider) Class.forName(SSL_PROVIDER).newInstance();
				Security.addProvider(p);
			} catch (Exception ex) {
				throw new MailException("Could not instantiate ssl security provider, check that you have JSSE in your classpath", ex);
			}
			// SMTP provider
			props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.put("mail.smtp.socketFactory.fallback", "false");
			
		}

		if( isAuth() ) {
			props.put("mail.smtp.auth", "true");
			session = Session.getInstance(props, getAuthenticator());
		} else {
			session = Session.getDefaultInstance(props);		
		}
		return session;			
	}
	
	/**
	 * 
	 * @param message
	 * @throws MailException
	 */
	public void sendMessage(MailMessage message) throws MailException {
		Session session = getSession();
		try {
			Transport.send(MailMessageManager.buildMessage(message, session));
		} catch(MessagingException ex) {
			throw new MailException("Could not send mail message", ex);
		} finally {
			session = null;
		}
	}
	
	/**
	 * @return
	 */
	public boolean isAuth() {
		return auth;
	}

	/**
	 * @return
	 */
	public String getHost() {
		return host;
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
	public int getPort() {
		if(port <= 0) {
			setPort(isSsl() ? DEFAULT_SMTP_SSL_PORT : DEFAULT_SMTP_PORT);
		}
		return port;
	}

	/**
	 * @return
	 */
	public boolean isSsl() {
		return ssl;
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param auth
	 */
	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	/**
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @param ssl
	 */
	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return
	 */
	public Authenticator getAuthenticator() {
		if(authenticator == null) {
			authenticator = new SimpleAuthenticator(getUsername(), getPassword());
		}
		return authenticator;
	}

}
