/*
 * File: PlainTextMessage.java
 * Created on 21/05/2005
 * 
 */
package leonards.common.mail;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class PlainTextMessage extends MailMessage {

	/**
	 * Constructor
	 * 
	 */
	public PlainTextMessage() {
		super();
	}

	/**
	 * @return
	 * @see leonards.common.mail.MailMessage#isMultipartMessage()
	 */
	public boolean isMultipartMessage() {
		return false;
	}

	/**
	 * @return
	 * @see leonards.common.mail.MailMessage#isHtmlMessage()
	 */
	public boolean isHtmlMessage() {
		return false;
	}

}
