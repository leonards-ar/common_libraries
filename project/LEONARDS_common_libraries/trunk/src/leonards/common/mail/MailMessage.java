/*
 * File: MailMessage.java
 * Created on 20/05/2005
 * 
 */
package leonards.common.mail;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public abstract class MailMessage {
	private Hashtable recipients = null;
	private MailAddress from = null;
	private String subject = null;
	private static final String TO_TYPE = "to";
	private static final String CC_TYPE = "cc";
	private static final String BCC_TYPE = "bcc";
	private String contentType = null;
	private String body = null;
	private Vector attachments = null;
		
	/**
	 * Default constructor
	 */
	public MailMessage() {
	}
		
	/**
	 * Returns the from.
	 * @return MailAddress
	 */
	public MailAddress getFrom() {
		return (from != null)?from:new MailAddress();
	}

	/**
	 * Returns the recipients.
	 * @return Hashtable
	 */
	private Hashtable getRecipients() {
		if( recipients == null ) {
			// TO, CC and BCC recipients
			recipients = new Hashtable(3);
		}
		
		return recipients;
	}

	/**
	 * Returns the subject.
	 * @return String
	 */
	public String getSubject() {
		return (subject != null)?subject:"";
	}

	/**
	 * Sets the from.
	 * @param from The from to set
	 */
	public void setFrom(MailAddress from) {
		this.from = from;
	}

	/**
	 * Sets the subject.
	 * @param subject The subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Adds a recipient of a given type (TO, CC or BCC).
	 * @param recipient The recipient to add
	 * @param recipientType The type of the recipient to add
	 */
	private void addRecipient(MailAddress recipient, String recipientType ) {

		if( recipient != null ) {		
			if( getRecipients().get(recipientType) == null ) {
				getRecipients().put(recipientType, new Vector());
			}
			((Vector)getRecipients().get(recipientType)).addElement(recipient);
		}				
	}
	
	/**
	 * Adds multiple recipients of a given type (TO, CC or BCC).
	 * @param recipients The recipients to add
	 * @param recipientType The type of the recipients to add
	 */
	private void addRecipients(Iterator recipients, String recipientType ) {
		while( recipients.hasNext() ) {
			try {
				addRecipient((MailAddress)recipients.next(), recipientType);
			} catch( ClassCastException ex ) {
				// Do not add invalid mail addresses
			}
		}
	}

	/**
	 * Returns the recipients of a given type (TO, CC or BCC).
	 * @param recipientType The type of the recipients to get
	 * @return The recipients
	 */
	private Iterator getRecipients(String recipientType ) {
		return ( getRecipients().get(recipientType) != null )?
					((Vector)getRecipients().get(recipientType)).iterator():
					(new Vector()).iterator();
	}

	/**
	 * Returns if there are recipients of a given type (TO, CC or BCC).
	 * @param recipientType The type of the recipients to get
	 * @return boolean
	 */
	private boolean hasRecipients(String recipientType ) {
		return ( getRecipients().get(recipientType) != null )?
					((Vector)getRecipients().get(recipientType)).size() > 0:
					false;
	}

	/**
	 * Returns a recipients array of a given type (TO, CC or BCC).
	 * @param recipientType The type of the recipients to get
	 * @return The recipients
	 */
	private MailAddress[] getRecipientsArray(String recipientType ) {
		if( getRecipients().get(recipientType) != null ) {
			MailAddress recipientsArray[] = new MailAddress[((Vector)getRecipients().get(recipientType)).size()];
			Iterator recipientsIterator = ((Vector)getRecipients().get(recipientType)).iterator();
			for( int i = 0; recipientsIterator.hasNext(); i++ ) {
				try {
					recipientsArray[i] = (MailAddress)recipientsIterator.next();
				} catch( IndexOutOfBoundsException ex ) {
					// Simply don't add to array
				}
			}
			return recipientsArray;
		} else {
			return new MailAddress[0];
		}
	}


	/**
	 * Adds a TO recipient.
	 * @param recipient The TO recipient to add
	 */
	public void addToRecipient(MailAddress recipient ) {
		addRecipient(recipient, TO_TYPE);
	}
	
	/**
	 * Adds a CC recipient.
	 * @param recipient The CC recipient to add
	 */
	public void addCcRecipient(MailAddress recipient ) {
		addRecipient(recipient, CC_TYPE);
	}

	/**
	 * Adds a BCC recipient.
	 * @param recipient The BCC recipient to add
	 */
	public void addBccRecipient(MailAddress recipient ) {
		addRecipient(recipient, BCC_TYPE);
	}
	
	/**
	 * Adds multiple TO recipients.
	 * @param recipients The TO recipients to add
	 */
	public void addToRecipients(Iterator recipients ) {
		addRecipients(recipients, TO_TYPE);
	}
	
	/**
	 * Adds multiple CC recipients.
	 * @param recipients The CC recipients to add
	 */
	public void addCcRecipients(Iterator recipients ) {
		addRecipients(recipients, CC_TYPE);
	}

	/**
	 * Adds multiple BCC recipients.
	 * @param recipients The BCC recipients to add
	 */
	public void addBccRecipients(Iterator recipients ) {
		addRecipients(recipients, BCC_TYPE);
	}

	/**
	 * Returns the TO recipients.
	 * @return The TO recipients
	 */
	public Iterator getToRecipients() {
		return getRecipients(TO_TYPE);
	}

	/**
	 * Returns the CC recipients.
	 * @return The CC recipients
	 */
	public Iterator getCcRecipients() {
		return getRecipients(CC_TYPE);
	}

	/**
	 * Returns the BCC recipients.
	 * @return The BCC recipients
	 */
	public Iterator getBccRecipients() {
		return getRecipients(BCC_TYPE);
	}

	/**
	 * Returns the TO recipients as an array.
	 * @return The TO recipients
	 */
	public MailAddress[] getToRecipientsArray() {
		return getRecipientsArray(TO_TYPE);
	}

	/**
	 * Returns the CC recipients as an array.
	 * @return The CC recipients
	 */
	public MailAddress[] getCcRecipientsArray() {
		return getRecipientsArray(CC_TYPE);
	}

	/**
	 * Returns the BCC recipients as an array.
	 * @return The BCC recipients
	 */
	public MailAddress[] getBccRecipientsArray() {
		return getRecipientsArray(BCC_TYPE);
	}
	
	/**
	 * Returns if the message has from mail address set
	 * @return boolean
	 */
	public boolean hasFrom() {
		return (from != null);
	}

	/**
	 * Returns if the message has TO recipients set (At least one)
	 * @return boolean
	 */
	public boolean hasToRecipients() {
		return hasRecipients(TO_TYPE);
	}

	/**
	 * Returns if the message has CC recipients set (At least one)
	 * @return boolean
	 */
	public boolean hasCcRecipients() {
		return hasRecipients(CC_TYPE);
	}

	/**
	 * Returns if the message has BCC recipients set (At least one)
	 * @return boolean
	 */
	public boolean hasBccRecipients() {
		return hasRecipients(BCC_TYPE);
	}
	
	/**
	 * Returns if the message has at least one recipient of any type
	 * (TO, CC or BCC).
	 * @return boolean
	 */
	public boolean hasAnyRecipient() {
		return hasToRecipients() || hasCcRecipients() || hasBccRecipients();
	}

	/**
	 * @return
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @return
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @param contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * 
	 * @return
	 */
	public abstract boolean isMultipartMessage();

	/**
	 * 
	 * @return
	 */
	public abstract boolean isHtmlMessage();
	
	/**
	 * @return
	 */
	public Vector getAttachments() {
		if(attachments == null) {
			attachments = new Vector();
		}
		return attachments;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasAttachments() {
		return attachments != null && getAttachments().size() > 0;
	}

	/**
	 * 
	 * @param filename
	 */
	public void addAttachment(String filename) {
		getAttachments().add(filename);
	}
}
