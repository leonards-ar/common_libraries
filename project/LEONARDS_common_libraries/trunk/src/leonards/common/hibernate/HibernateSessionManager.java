/*
 * File: HibernateSessionManager.java
 * Created on 12/05/2005
 * 
 */
package leonards.common.hibernate;

import java.util.Enumeration;
import java.util.Hashtable;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

/**
 * @author Mariano Capurro (Mariano) - marianocapurro@fibertel.com.ar
 * 
 * This class is the abstraction
 * 
 * @version $Revision: 1.1 $
 */
public class HibernateSessionManager {
	private Hashtable sessionFactories = null;
	
	/**
	 * 
	 * Constructor
	 * @throws HibernateSessionManagerException
	 */
	public HibernateSessionManager() throws HibernateSessionManagerException {
		super();
		initialize();
	}
	
	/**
	 * 
	 * @throws HibernateSessionManagerException
	 */
	private void initialize() throws HibernateSessionManagerException {
		HibernateSessionManagerConfiguration conf = new HibernateSessionManagerConfiguration();
		Enumeration sessions = conf.getSessionEntries();
		HibernateSessionConfigurationEntry anEntry;
		while(sessions.hasMoreElements()) {
			anEntry = (HibernateSessionConfigurationEntry)sessions.nextElement();
			getSessionFactories().put(anEntry.getSessionName().toUpperCase(), getSessionFactory(anEntry));
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws HibernateSessionManagerException
	 */
	private SessionFactory getSessionFactory(HibernateSessionConfigurationEntry sessionEntry) throws HibernateSessionManagerException {
		SessionFactory sessionFactory = null;
		try {
			sessionFactory = new Configuration().configure(sessionEntry.getHibernateConfigurationFile()).buildSessionFactory();
			return sessionFactory;
		} catch(HibernateException ex) {
			throw new HibernateSessionManagerException("Could not create hibernate session factory", ex);
		} catch(Throwable ex) {
			throw new HibernateSessionManagerException("Could not create hibernate session factory", ex);
		}
	}
	
	/**
	 * 
	 * @param sessionName
	 * @return
	 * @throws HibernateSessionManagerException
	 */
	public Session getSession(String sessionName) throws HibernateSessionManagerException {
		
		SessionFactory sessionFactory = (SessionFactory)getSessionFactories().get(sessionName.toUpperCase());
		if(sessionFactory != null) {
			try {
				return sessionFactory.openSession();
			} catch(HibernateException ex) {
				throw new HibernateSessionManagerException("Could not retrieve an open a session for [" + sessionName + "]", ex);
			}
		} else {
			throw new HibernateSessionManagerException("There is no session factory for [" + sessionName + "]");
		}
		
	}

	/**
	 * @return
	 */
	protected Hashtable getSessionFactories() {
		if(sessionFactories == null) {
			sessionFactories = new Hashtable();
		}
		return sessionFactories;
	}

}
