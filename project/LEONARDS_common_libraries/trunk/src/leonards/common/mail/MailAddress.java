/*
 * File: MailAddress.java
 * Created on 20/05/2005
 * 
 */
package leonards.common.mail;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class MailAddress {
	
	private String mailAddress = null;
	private String name = null;
	private static final String DEFAULT_SEPARATORS = ";,";

	/**
	 * Default constructor.
	 */
	public MailAddress()  {
		super();
	}

	/**
	 * Constructor.
	 * @param mailAddress The mailAddress to set
	 */
	public MailAddress( String mailAddress ) {
		this();
		setMailAddress(mailAddress);
	}

	/**
	 * Constructor.
	 * @param mailAddress The mailAddress to set
	 * @param name The name to set
	 */
	public MailAddress( String mailAddress, String name ) {
		this(mailAddress);
		setName(name);
	}
	
	/**
	 * Returns the mailAddress.
	 * @return String
	 */
	public String getMailAddress() {
		return (mailAddress != null)?mailAddress:"";
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return (name != null)?name:"";
	}

	/**
	 * Sets the mailAddress.
	 * @param mailAddress The mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns a parsed iterator of MailAddress objects separated by
	 * any of the separators in separators.
	 * @param addresses String with the addresses to be parsed
	 * @param separators String with the possible separatos (typically ",;")
	 * @return Parsed mail addresses
	 */
	public static Iterator parse( String addresses, String separators ) {
		if( addresses != null ) {
			Vector parsedAddresses = new Vector();
			StringTokenizer st = new StringTokenizer(addresses, separators);
			
			while( st.hasMoreTokens() ) {
				String address = st.nextToken();
				if( isValid(address) ) {
					parsedAddresses.addElement( new MailAddress(address));
				}
			}
			return parsedAddresses.iterator();
		} else {
			return (new Vector()).iterator();
		}
	}

	/**
	 * Returns a parsed iterator of MailAddress objects separated by
	 * any of the default separators ",;".
	 * @param addresses String with the addresses to be parsed
	 * @return Parsed mail addresses
	 */
	public static Iterator parse( String addresses ) {
		return parse(addresses, DEFAULT_SEPARATORS);
	}

	/**
	 * Returns if an email address is valid.
	 * @param mailAddress The mail address to check
	 * @return boolean
	 */
	private static boolean isValid(String mailAddress) {
		return	mailAddress != null &&
				!mailAddress.trim().equals("") &&
				mailAddress.indexOf("@") != -1;
	}	
}
