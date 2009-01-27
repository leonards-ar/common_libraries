/*
 * File: MailTester.java
 * Created on 21/05/2005
 * 
 */
package leonards.common.mail;

import leonards.common.base.NestedException;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class MailTester {

	/**
	 * Constructor
	 * 
	 */
	public MailTester() {
		super();
	}

	public static void main(String[] args) {
		try {
			MailAccount acct = MailAccountFactory.getMailAccount("default");
			
			PlainTextMessage msg = new PlainTextMessage();
			msg.setFrom(new MailAddress("koja2002@gmail.com", "MindPool Mail"));
			//msg.addBccRecipient(new MailAddress("jmariano@cvtci.com.ar","Mariano Capurro"));
			//msg.addCcRecipient(new MailAddress("marianocapurro@fibertel.com.ar","Mariano Capurro"));
			msg.addToRecipient(new MailAddress("marianocapurro@fibertel.com.ar","Dino Muri"));
			
			//msg.addToRecipients(MailAddress.parse("marianocapurro@fibertel.com.ar;jmariano@cvtci.com.ar;nelsonk00@yahoo.com"));
			msg.setSubject("MindPool Mail Implementation");
			msg.setBody("If you receive this mail is because MindPool has what you need!!!\n\nMindPool Directory.");
			
			acct.sendMessage(msg);
			
			System.out.println("Message sent succesfully!");
			
		} catch( NestedException ex ) {
			System.err.println(ex.getNestedMessage());
			ex.printStackTrace();
		} catch( Exception ex ) {
			System.err.println(ex.getMessage() + "\n\n" );
			ex.printStackTrace();
		}
	}
}
