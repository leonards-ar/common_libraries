/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.test
 * File: Tester.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 25, 2006 (3:31:08 PM) 
 */
package leonards.common.test;

import leonards.common.base.NestedException;
import leonards.common.ldap.LDAPConnection;
import leonards.common.ldap.LDAPEntry;
import leonards.common.ldap.LDAPEntryAttribute;
import leonards.common.ldap.LDAPMultiValueEntryAttribute;
import leonards.common.ldap.LDAPResultSet;
import leonards.common.ldap.LDAPSearchControls;
import leonards.common.ldap.LDAPSingleValueEntryAttribute;
import leonards.common.log.Logger;
import leonards.common.log.LoggerFactory;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class Tester {

	/**
	 * 
	 */
	public Tester() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LDAPConnection ldap = LDAPConnection.getConnection();
			ldap.bind("cn=admin,dc=leonards,dc=com,dc=ar", "tets97");
			/*
			LDAPEntry e = new LDAPEntry("ou=Test,dc=leonards,dc=com,dc=ar");
			LDAPMultiValueEntryAttribute a1 = new LDAPMultiValueEntryAttribute();
			a1.setName("objectclass");
			a1.addValue("top");
			a1.addValue("organizationalUnit");
			
			LDAPSingleValueEntryAttribute a2 = new LDAPSingleValueEntryAttribute("ou", "Test");
			
			e.addAttribute(a1);
			e.addAttribute(a2);
			
			ldap.addEntry(e);
*/
			ldap.addAttribute("ou=Test,dc=leonards,dc=com,dc=ar", new LDAPSingleValueEntryAttribute("description", "Una prueba"));
			
			LDAPResultSet res = ldap.search("dc=leonards,dc=com,dc=ar","(objectclass=*)");
			System.out.println(res.getEntries().size());

			
			ldap.close();
		} catch(NestedException ex){
			System.err.println(ex.getNestedMessage());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
