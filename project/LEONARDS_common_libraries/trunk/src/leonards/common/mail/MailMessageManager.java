/*
 * File: MailMessageManager.java
 * Created on 21/05/2005
 * 
 */
package leonards.common.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import leonards.common.base.CommonUtils;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class MailMessageManager {

	/**
	 * Constructor
	 * 
	 */
	private MailMessageManager() {
		super();
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 * @throws MailException
	 */
	public static Message buildMessage(MailMessage message, Session session) throws MailException {
		if(message instanceof PlainTextMessage) {
			return buildMessage((PlainTextMessage)message, session);
		} else {
			throw new MailException("Unsupported mail message class [" + message.getClass().getName() + "]");
		}
	}

	/**
	 * Builds the address object as needed by the mail implementation.
	 * @param mailAddress The mail address.
	 * @return InternetAddress
	 * @throws MailException
	 */
	private static InternetAddress buildMailAddress(MailAddress mailAddress) throws MailException {
		try {
			if(CommonUtils.hasValue(mailAddress.getName())) {
				return new InternetAddress( mailAddress.getMailAddress(), mailAddress.getName() );
			} else {
				return new InternetAddress( mailAddress.getMailAddress() );
			}
		} catch(AddressException ex) {
			throw new MailException("Invalid mail address [" + mailAddress.getMailAddress() + "]", ex);
		} catch( UnsupportedEncodingException ex ) {
			throw new MailException("Invalid mail address [" + mailAddress.getMailAddress() + "]", ex);
		}
	}

	/**
	 * Builds the addresses object as needed by the mail implementation.
	 * @param mailAddresses An array with the email addresses.
	 * @return InternetAddress[]
	 * @throws MailException
	 */
	private static InternetAddress[] buildMailAddresses( MailAddress[] mailAddresses ) throws MailException {
		InternetAddress[] addresses = new InternetAddress[mailAddresses.length];
		for( int i = 0; i < mailAddresses.length; i++ ) {
			addresses[i] = buildMailAddress(mailAddresses[i]);
		}
		return addresses;
	}

	/**
	 * 
	 * @param message
	 * @return
	 * @throws MailException
	 */
	private static Message buildMessage(PlainTextMessage message, Session session) throws MailException {
		try {
			Message msg = new MimeMessage(session);
		
			setCommonMessageAttributes(message, msg);
			setAttachments(message, msg);
			
			msg.setText(message.getBody());
		
			return msg;
		} catch(MessagingException ex) {
			throw new MailException("Could not build plain text message", ex);
		}
	}
	
	/**
	 * 
	 * @param srcMessage
	 * @param destMessage
	 * @throws MailException
	 */
	private static void setAttachments(MailMessage srcMessage, Message destMessage) throws MailException {
		//:TODO: Implement!
	}
	
	/**
	 * 
	 * @param srcMessage
	 * @param destMessage
	 * @throws MailException
	 */
	private static void setCommonMessageAttributes(MailMessage srcMessage, Message destMessage) throws MailException {
		try {
			if( srcMessage.hasFrom() ) {
				destMessage.setFrom(buildMailAddress(srcMessage.getFrom()));
			}
			
			if( srcMessage.hasToRecipients() ) {
				destMessage.setRecipients(Message.RecipientType.TO, buildMailAddresses(srcMessage.getToRecipientsArray()) );
			}
			
	
			if( srcMessage.hasCcRecipients() ) {
				destMessage.setRecipients(Message.RecipientType.CC, buildMailAddresses(srcMessage.getCcRecipientsArray()) );
			}
	
			if( srcMessage.hasBccRecipients() ) {
				destMessage.setRecipients(Message.RecipientType.BCC, buildMailAddresses(srcMessage.getBccRecipientsArray()) );
			}
			
			destMessage.setSubject(srcMessage.getSubject());
			
			destMessage.addHeader("Date", new Date().toString());
			destMessage.setHeader("X-Mailer", "LeonardsMailAPI");
			
		} catch(MessagingException ex) {
			throw new MailException("Could not set common message attributes", ex);
		}
	}
}
