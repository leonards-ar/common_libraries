/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.ldap.jndi
 * File: LDAPJNDIConnection.java
 *
 * Property of Leonards / Mindpool
 * Created on Jun 22, 2006 (10:43:40 PM) 
 */
package leonards.common.ldap.jndi;

import java.util.Iterator;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import leonards.common.conf.Configuration;
import leonards.common.ldap.AuthenticationException;
import leonards.common.ldap.InvalidAttributesException;
import leonards.common.ldap.LDAPConnection;
import leonards.common.ldap.LDAPEntry;
import leonards.common.ldap.LDAPEntryAttribute;
import leonards.common.ldap.LDAPEntryAttributes;
import leonards.common.ldap.LDAPException;
import leonards.common.ldap.LDAPMultiValueEntryAttribute;
import leonards.common.ldap.LDAPResultSet;
import leonards.common.ldap.LDAPSearchControls;
import leonards.common.ldap.LDAPSingleValueEntryAttribute;
import leonards.common.ldap.NameAlreadyBoundException;
import leonards.common.ldap.NameNotFoundException;
import leonards.common.ldap.NoPermissionException;
import leonards.common.util.StringUtils;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class LDAPJNDIConnection extends LDAPConnection {
	public static final String NO_AUTHENTICATION = "none";
	public static final int DEFAULT_MAX_POOL_CONNECTIONS = 0;
	public static final int DEFAULT_INITIAL_POOL_CONNECTIONS = 0;
	public static final long DEFAULT_POOL_TIMEOUT = 0L;
	public static final String DEFAULT_INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	
	protected static final boolean IGNORE_CASE = true;
	
	/**
	 * Final constants for managing JNDI Pool connections.
	 */
	private static final String POOL_ENABLED_ENV_PARAM="com.sun.jndi.ldap.connect.pool";
	private static final String MAX_POOL_SIZE_ENV_PARAM="com.sun.jndi.ldap.connect.pool.maxsize";
	private static final String INIT_POOL_SIZE_ENV_PARAM="com.sun.jndi.ldap.connect.pool.initsize";
	private static final String TIME_OUT_ENV_PARAM="com.sun.jndi.ldap.connect.pool.timeout";
	private static final String AUTHENTICATION_ENV_PARAM="com.sun.jndi.ldap.pool.authentication";

	private String providerUrl = null;
	private int maxPoolConnections = DEFAULT_MAX_POOL_CONNECTIONS;
	private int initialPoolSizeConnections = DEFAULT_INITIAL_POOL_CONNECTIONS;
	private long poolTimeout = DEFAULT_POOL_TIMEOUT;
	private String authentication = NO_AUTHENTICATION;
	private String initialContextFactory = DEFAULT_INITIAL_CONTEXT_FACTORY;
	private String bindDn = null;
	private String bindPassword = null;
	
	private DirContext conn = null;
	
	/**
	 * 
	 */
	public LDAPJNDIConnection() {
		super();
	}

	/**
	 * 
	 * @param providerUrl
	 * @throws LDAPException
	 */
	public LDAPJNDIConnection(String providerUrl) throws LDAPException {
		this(providerUrl, DEFAULT_INITIAL_CONTEXT_FACTORY);
	}
	
	/**
	 * 
	 * @param providerUrl
	 * @param initialContextFactory
	 * @throws LDAPException
	 */
	public LDAPJNDIConnection(String providerUrl, String initialContextFactory) throws LDAPException {
		this(providerUrl, initialContextFactory, NO_AUTHENTICATION);
	}

	/**
	 * 
	 * @param providerUrl
	 * @param initialContextFactory
	 * @param authentication
	 * @param maxPoolConnections
	 * @param initialPoolSizeConnections
	 * @param poolTimeout
	 * @throws LDAPException
	 */
	public LDAPJNDIConnection(String providerUrl, String initialContextFactory, String authentication, int maxPoolConnections, int initialPoolSizeConnections, long poolTimeout) throws LDAPException {
		this();
		setProviderUrl(providerUrl);
		setInitialContextFactory(initialContextFactory);
		setAuthentication(authentication);
		setMaxPoolConnections(maxPoolConnections);
		setInitialPoolSizeConnections(initialPoolSizeConnections);
		setPoolTimeout(poolTimeout);
	}
	
	/**
	 * 
	 * @param providerUrl
	 * @param initialContextFactory
	 * @param authentication
	 * @throws LDAPException
	 */
	public LDAPJNDIConnection(String providerUrl, String initialContextFactory, String authentication) throws LDAPException {
		this(providerUrl, initialContextFactory, authentication, DEFAULT_MAX_POOL_CONNECTIONS, DEFAULT_INITIAL_POOL_CONNECTIONS, DEFAULT_POOL_TIMEOUT);
	}

	/**
	 * @param conf
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#initialize(leonards.common.conf.Configuration)
	 */
	protected void initialize(Configuration conf) throws LDAPException {
		if(conf != null) {
			setAuthentication(conf.getString(getName() + ".ldap.authentication", NO_AUTHENTICATION));
			setInitialContextFactory(conf.getString(getName() + ".ldap.initial.context.factory", DEFAULT_INITIAL_CONTEXT_FACTORY));
			setInitialPoolSizeConnections(conf.getInt(getName() + ".ldap.init.pool.connections", DEFAULT_INITIAL_POOL_CONNECTIONS));
			setMaxPoolConnections(conf.getInt(getName() + ".ldap.max.pool.connections", DEFAULT_MAX_POOL_CONNECTIONS));
			setPoolTimeout(conf.getNativeLong(getName() + ".ldap.connection.pool.timeout", DEFAULT_POOL_TIMEOUT));
			setProviderUrl(conf.getString(getName() + ".ldap.provider.url"));
		}
	}

	/**
	 * 
	 * @return
	 */
	private void logConfiguration() {
		StringBuffer conf = new StringBuffer();
		
		conf.append("{");
		conf.append("name: " + getName() + ", ");
		conf.append("provider_url: " + getProviderUrl() + ", ");
		conf.append("initial_ctx_factory: " + getInitialContextFactory() + ", ");
		conf.append("auth: " + getAuthentication() + ", ");
		if(!isNoAuthentication() && StringUtils.hasValue(getBindDn())) {
			conf.append("bindDn: " + getBindDn() + ", ");
		}
		if(isConnectionPoolEnabled()) {
			conf.append("init_pool_conns: " + getInitialPoolSizeConnections() + ", ");
			conf.append("max_pool_conns: " + getMaxPoolConnections() + ", ");
			conf.append("pool_timeout: " + getPoolTimeout());
		} else {
			conf.append("pool: disabled");
		}
		conf.append("}");
		logger.logDebug(conf.toString());
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isNoAuthentication() {
		return NO_AUTHENTICATION.equalsIgnoreCase(getAuthentication());
	}
	
	/**
	 * @return
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#isClosed()
	 */
	public boolean isClosed() throws LDAPException {
		return getConn() == null;
	}

	/**
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#close()
	 */
	public void close() throws LDAPException {
		if(getConn() != null) {
			try {
				getConn().close();
				setConn(null);
				logger.logInfo("Connection closed.");
			} catch(NamingException nex) {
				logger.logError("close", nex);
				throw new LDAPException("Could not close connection.", nex);
			}
		}
	}

	/**
	 * 
	 * @param dn
	 * @param password
	 * @return
	 * @throws LDAPException
	 */
	private Properties buildEnvironment(String dn, String password) throws LDAPException {
		Properties env = new Properties();
		
		env.put(Context.SECURITY_AUTHENTICATION, getAuthentication());
		if (!isNoAuthentication()) {
			env.put(Context.SECURITY_PRINCIPAL, dn);
			env.put(Context.SECURITY_CREDENTIALS, password);
		}
		env.put(Context.INITIAL_CONTEXT_FACTORY, getInitialContextFactory());
		env.put(Context.PROVIDER_URL, getProviderUrl());
		
		if (isConnectionPoolEnabled()) {
			env.put(POOL_ENABLED_ENV_PARAM, "true");
			if(getMaxPoolConnections() > 0) {
				env.put(MAX_POOL_SIZE_ENV_PARAM, String.valueOf(getMaxPoolConnections()));
			}

			if(getInitialPoolSizeConnections() > 0) {
				env.put(INIT_POOL_SIZE_ENV_PARAM, String.valueOf(getInitialPoolSizeConnections()));
			}
			
			env.put(AUTHENTICATION_ENV_PARAM, getAuthentication());

			if(getPoolTimeout() > 0) {
				env.put(TIME_OUT_ENV_PARAM, String.valueOf(getPoolTimeout()));
			}
		}
		return env;
		
	}
	
	/**
	 * @param dn
	 * @param password
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#bind(java.lang.String, java.lang.String)
	 */
	public void bind(String dn, String password) throws LDAPException {
		try {
			
			logConfiguration();
			setConn(new InitialDirContext( buildEnvironment(dn, password) ));
			logger.logInfo("Binded as " + dn);
			
		} catch( javax.naming.AuthenticationException nex ) {
			
			logger.logError("bind", nex);
			throw new AuthenticationException( nex.getMessage(), nex );
			
		} catch( javax.naming.NameNotFoundException nex ) {
			
			logger.logError("bind", nex);
			throw new NameNotFoundException( nex.getMessage(), nex );
			
		} catch( NamingException nex ) {
			
			logger.logError("bind", nex);
			throw new LDAPException( nex.getMessage(), nex );
			
		}
	}

	/**
	 * 
	 * @param scope
	 * @return
	 */
	protected int transformScope(int scope) {
		switch(scope) {
			case LDAPSearchControls.OBJECT_SCOPE: return SearchControls.OBJECT_SCOPE;
			case LDAPSearchControls.ONELEVEL_SCOPE: return SearchControls.ONELEVEL_SCOPE;
			case LDAPSearchControls.SUBTREE_SCOPE: return SearchControls.SUBTREE_SCOPE;
			default: return SearchControls.ONELEVEL_SCOPE;
		}
	}

	/**
	 * 
	 * @param controls
	 * @return
	 */
	protected SearchControls buildSearchControls(LDAPSearchControls controls) {
		SearchControls ctrls = new SearchControls();
		ctrls.setCountLimit(controls.getMaxResults());
		ctrls.setReturningAttributes(controls.getAttributesToReturn());
		ctrls.setReturningObjFlag(controls.isReturnObject());
		ctrls.setSearchScope(transformScope(controls.getScope()));
		ctrls.setTimeLimit(controls.getTimeout());
		return ctrls;
	}
	
	/**
	 * @param baseDn
	 * @param matchingAttributes
	 * @return
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#search(java.lang.String, leonards.common.ldap.LDAPEntryAttributes)
	 */
	public LDAPResultSet search(String baseDn, LDAPEntryAttributes matchingAttributes) throws LDAPException {
		try {
			
			NamingEnumeration entries = getConn().search(baseDn, buildAttributes(matchingAttributes));
			return buildSearchResultSet(baseDn, entries);
			
		} catch( javax.naming.NoPermissionException nex ) {
			
			logger.logWarning("search", nex);
			throw new NoPermissionException( nex );
			
		} catch( javax.naming.NameNotFoundException nex ) {
			
			logger.logWarning("search", nex);
			throw new NameNotFoundException( nex );
			
		} catch( NamingException nex ) {
			
			logger.logWarning("search", nex);
			throw new LDAPException( nex );
			
		}

	}

	/**
	 * @param baseDn
	 * @param filter
	 * @param controls
	 * @return
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#search(java.lang.String, java.lang.String, leonards.common.ldap.LDAPSearchControls)
	 */
	public LDAPResultSet search(String baseDn, String filter, LDAPSearchControls controls) throws LDAPException {
		try {
			
			NamingEnumeration entries = getConn().search(baseDn, filter, buildSearchControls(controls));
			return buildSearchResultSet(baseDn, entries);
			
		} catch( javax.naming.NoPermissionException nex ) {
			
			logger.logWarning("search", nex);
			throw new NoPermissionException( nex );
			
		} catch( javax.naming.NameNotFoundException nex ) {
			
			logger.logWarning("search", nex);
			throw new NameNotFoundException( nex );
			
		} catch( NamingException nex ) {
			
			logger.logWarning("search", nex);
			throw new LDAPException( nex );
			
		}
	}

	/**
	 * @param baseDn
	 * @param filter
	 * @param filterArgs
	 * @param controls
	 * @return
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#search(java.lang.String, java.lang.String, java.lang.Object[], leonards.common.ldap.LDAPSearchControls)
	 */
	public LDAPResultSet search(String baseDn, String filter, Object[] filterArgs, LDAPSearchControls controls) throws LDAPException {
		try {
			
			NamingEnumeration entries = getConn().search(baseDn, filter, filterArgs, buildSearchControls(controls));
			return buildSearchResultSet(baseDn, entries);
			
		} catch( javax.naming.NoPermissionException nex ) {
			
			logger.logWarning("search", nex);
			throw new NoPermissionException( nex );
			
		} catch( javax.naming.NameNotFoundException nex ) {
			
			logger.logWarning("search", nex);
			throw new NameNotFoundException( nex );
			
		} catch( NamingException nex ) {
			
			logger.logWarning("search", nex);
			throw new LDAPException( nex );
			
		}
	}

	/**
	 * 
	 * @param baseDn
	 * @param entries
	 * @return
	 * @throws LDAPException
	 */
	protected LDAPResultSet buildSearchResultSet(String baseDn, NamingEnumeration entries) throws LDAPException {
		SearchResult searchResult;
		LDAPEntry entry;
		String entryDn;
		LDAPResultSet result = new LDAPResultSet();
		while( entries.hasMoreElements() ) {
			searchResult=(SearchResult) entries.nextElement() ;
			if ( searchResult != null ) {
				entryDn = searchResult.getName();
				if (searchResult.isRelative()) {
					entryDn += "," + baseDn;
				}
				entry =  buildEntry(entryDn, searchResult.getAttributes());
				result.addEntry(entry);
			}
		}
		return result;
	}
	
	/**
	 * @param baseDn
	 * @param filter
	 * @return
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#search(java.lang.String, java.lang.String)
	 */
	public LDAPResultSet search(String baseDn, String filter) throws LDAPException {
		return search(baseDn, filter, new LDAPSearchControls());
	}

	/**
	 * @param dn
	 * @return
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#lookup(java.lang.String)
	 */
	public LDAPEntry lookup(String dn) throws LDAPException {
		try {
			
			return buildEntry(dn, getConn().getAttributes( dn ) );
			
		} catch(javax.naming.NoPermissionException nex ) {
			
			logger.logWarning("lookup", nex);
			throw new NoPermissionException(nex.getMessage(), nex);
			
		} catch( javax.naming.NameNotFoundException nex ) {
			
			logger.logWarning("lookup", nex);
			throw new NameNotFoundException(nex.getMessage(), nex);
			
		} catch(NamingException nex) {
			
			logger.logWarning("lookup", nex);
			throw new LDAPException(nex.getMessage(), nex);
			
		}
	}

	
	/**
	 * @param dn
	 * @param attributes
	 * @return
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#lookup(java.lang.String, java.lang.String[])
	 */
	public LDAPEntry lookup(String dn, String[] attributes) throws LDAPException {
		try {
			
			return buildEntry(dn, getConn().getAttributes(dn, attributes));
			
		} catch(javax.naming.NoPermissionException nex ) {
			
			logger.logWarning("lookup", nex);
			throw new NoPermissionException(nex.getMessage(), nex);
			
		} catch( javax.naming.NameNotFoundException nex ) {
			
			logger.logWarning("lookup", nex);
			throw new NameNotFoundException(nex.getMessage(), nex);
			
		} catch(NamingException nex) {
			
			logger.logWarning("lookup", nex);
			throw new LDAPException(nex.getMessage(), nex);
			
		}
	}

	/**
	 * @param entry
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#addEntry(leonards.common.ldap.LDAPEntry)
	 */
	public void addEntry(LDAPEntry entry) throws LDAPException {
		try {
			
			getConn().bind(entry.getDn(), null, buildAttributes(entry));
			
		} catch(javax.naming.NoPermissionException nex) {
			
			logger.logWarning("addEntry", nex);
			throw new NoPermissionException(nex.getMessage(), nex);
			
		} catch(javax.naming.InvalidNameException nex) {
			
			logger.logWarning("addEntry", nex);
			throw new InvalidAttributesException(nex.getMessage(), nex);
			
		} catch(javax.naming.NameAlreadyBoundException nex) {
			
			logger.logWarning("addEntry", nex);
			throw new NameAlreadyBoundException(nex.getMessage(), nex);
			
		} catch(NamingException nex) {
			
			logger.logWarning("addEntry", nex);
			throw new LDAPException(nex.getMessage(), nex);
			
		}			
	}

	/**
	 * @param entry
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#updateEntry(leonards.common.ldap.LDAPEntry)
	 */
	public void updateEntry(LDAPEntry entry) throws LDAPException {
		try {
			
			ModificationItem[] mods = new ModificationItem[ entry.getAttributeCount() ];
			Iterator it = entry.attributes(); 
			for(int i=0; it.hasNext() && i < mods.length; i++) {
				mods[i++] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, buildBasicAttribute(((LDAPEntryAttribute)it.next())));
			}	
			getConn().modifyAttributes(entry.getDn(), mods);
			
		} catch(javax.naming.NoPermissionException nex) {
			
			logger.logWarning("addAttribute", nex);
			throw new NoPermissionException(nex.getMessage(), nex);
			
		} catch(javax.naming.NameNotFoundException nex) {
			
			logger.logWarning("addAttribute", nex);
			throw new NoPermissionException(nex.getMessage(), nex);
			
		} catch(NamingException nex) {
			
			logger.logWarning("addAttribute", nex);
			throw new LDAPException(nex.getMessage(), nex);
			
		}			
	}

	/**
	 * @param entry
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#deleteEntry(leonards.common.ldap.LDAPEntry)
	 */
	public void deleteEntry(LDAPEntry entry) throws LDAPException {
		deleteEntry(entry.getDn());
	}

	/**
	 * @param dn
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#deleteEntry(java.lang.String)
	 */
	public void deleteEntry(String dn) throws LDAPException {
		try {
			
			getConn().unbind(dn);
			
		} catch(javax.naming.NoPermissionException nex) {
			
			logger.logWarning("deleteEntry", nex);
			throw new NoPermissionException(nex.getMessage(), nex);
			
		} catch(javax.naming.NameNotFoundException nex) {
			
			logger.logWarning("deleteEntry", nex);
			throw new NameNotFoundException(nex.getMessage(), nex);
			
		} catch(NamingException nex) {
			
			logger.logWarning("deleteEntry", nex);
			throw new LDAPException(nex.getMessage(), nex);
			
		}	
		
	}

	/**
	 * @param dn
	 * @param attribute
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#addAttribute(java.lang.String, leonards.common.ldap.LDAPEntryAttribute)
	 */
	public void addAttribute(String dn, LDAPEntryAttribute attribute)
			throws LDAPException {

		try {
			
			ModificationItem[] mods = new ModificationItem[ 1 ];
			mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, buildBasicAttribute(attribute));	
			getConn().modifyAttributes(dn, mods);
			
		} catch(javax.naming.NoPermissionException nex) {
			
			logger.logWarning("addAttribute", nex);
			throw new NoPermissionException(nex.getMessage(), nex);
			
		} catch(javax.naming.NameNotFoundException nex) {
			
			logger.logWarning("addAttribute", nex);
			throw new NoPermissionException(nex.getMessage(), nex);
			
		} catch(NamingException nex) {
			
			logger.logWarning("addAttribute", nex);
			throw new LDAPException(nex.getMessage(), nex);
			
		}			
	}

	/**
	 * @param dn
	 * @param attribute
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#updateAttribute(java.lang.String, leonards.common.ldap.LDAPEntryAttribute)
	 */
	public void updateAttribute(String dn, LDAPEntryAttribute attribute)
			throws LDAPException {
		
		try {
			
			ModificationItem[] mods = new ModificationItem[ 1 ];
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, buildBasicAttribute(attribute));	
			getConn().modifyAttributes(dn, mods);
			
		} catch(javax.naming.NoPermissionException nex) {
			
			logger.logWarning("addAttribute", nex);
			throw new NoPermissionException(nex.getMessage(), nex);
			
		} catch(javax.naming.NameNotFoundException nex) {
			
			logger.logWarning("addAttribute", nex);
			throw new NoPermissionException(nex.getMessage(), nex);
			
		} catch(NamingException nex) {
			
			logger.logWarning("addAttribute", nex);
			throw new LDAPException(nex.getMessage(), nex);
			
		}		
	}

	/**
	 * @param dn
	 * @param attribute
	 * @throws LDAPException
	 * @see leonards.common.ldap.LDAPConnection#deleteAttribute(java.lang.String, leonards.common.ldap.LDAPEntryAttribute)
	 */
	public void deleteAttribute(String dn, LDAPEntryAttribute attribute)
			throws LDAPException {
		try {
			
			ModificationItem[] mods = new ModificationItem[ 1 ];
			mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, buildBasicAttribute(attribute));	
			getConn().modifyAttributes(dn, mods);
			
		} catch(javax.naming.NoPermissionException nex) {
			
			logger.logWarning("addAttribute", nex);
			throw new NoPermissionException(nex.getMessage(), nex);
			
		} catch(javax.naming.NameNotFoundException nex) {
			
			logger.logWarning("addAttribute", nex);
			throw new NoPermissionException(nex.getMessage(), nex);
			
		} catch(NamingException nex) {
			
			logger.logWarning("addAttribute", nex);
			throw new LDAPException(nex.getMessage(), nex);
			
		}		
	}

	/**
	 * @return Returns the authentication.
	 */
	public String getAuthentication() {
		return authentication;
	}

	/**
	 * @param authentication The authentication to set.
	 */
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	/**
	 * @return Returns the initialPoolSizeConnections.
	 */
	public int getInitialPoolSizeConnections() {
		return initialPoolSizeConnections;
	}

	/**
	 * @param initialPoolSizeConnections The initialPoolSizeConnections to set.
	 */
	public void setInitialPoolSizeConnections(int initialPoolSizeConnections) {
		this.initialPoolSizeConnections = initialPoolSizeConnections;
	}

	/**
	 * @return Returns the maxPoolConnections.
	 */
	public int getMaxPoolConnections() {
		return maxPoolConnections;
	}

	/**
	 * @param maxPoolConnections The maxPoolConnections to set.
	 */
	public void setMaxPoolConnections(int maxPoolConnections) {
		this.maxPoolConnections = maxPoolConnections;
	}

	/**
	 * @return Returns the poolTimeout.
	 */
	public long getPoolTimeout() {
		return poolTimeout;
	}

	/**
	 * @param poolTimeout The poolTimeout to set.
	 */
	public void setPoolTimeout(long poolTimeout) {
		this.poolTimeout = poolTimeout;
	}

	/**
	 * @return Returns the providerUrl.
	 */
	public String getProviderUrl() {
		return providerUrl;
	}

	/**
	 * @param providerUrl The providerUrl to set.
	 */
	public void setProviderUrl(String provider) {
		this.providerUrl = provider;
	}

	public boolean isConnectionPoolEnabled() {
		return getInitialPoolSizeConnections() > 0;
	}

	/**
	 * @return Returns the initialContextFactory.
	 */
	public String getInitialContextFactory() {
		return initialContextFactory;
	}

	/**
	 * @param initialContextFactory The initialContextFactory to set.
	 */
	public void setInitialContextFactory(String initialContextFactory) {
		this.initialContextFactory = initialContextFactory;
	}

	/**
	 * @return Returns the bindDn.
	 */
	public String getBindDn() {
		return bindDn;
	}

	/**
	 * @param bindDn The bindDn to set.
	 */
	public void setBindDn(String bindDn) {
		this.bindDn = bindDn;
	}

	/**
	 * @return Returns the bindPassword.
	 */
	public String getBindPassword() {
		return bindPassword;
	}

	/**
	 * @param bindPassword The bindPassword to set.
	 */
	public void setBindPassword(String bindPassword) {
		this.bindPassword = bindPassword;
	}

	/**
	 * @return Returns the conn.
	 */
	protected DirContext getConn() {
		return conn;
	}

	/**
	 * @param conn The conn to set.
	 */
	protected void setConn(DirContext conn) {
		this.conn = conn;
	}

	/**
	 * 
	 * @param attribute
	 * @return
	 * @throws LDAPException
	 */
	protected LDAPEntryAttribute buildAttribute(Attribute attribute) throws LDAPException {
		if(attribute != null) {
			try {
			 	if( attribute.size() > 1 ) {
				 	LDAPMultiValueEntryAttribute newAttribute = new LDAPMultiValueEntryAttribute();
				 	newAttribute.setName(attribute.getID());
					NamingEnumeration values = attribute.getAll();
					while( values.hasMoreElements() ) {
						newAttribute.addValue(values.next());
					}
					return newAttribute;
			 	} else {
				 	LDAPSingleValueEntryAttribute newAttribute = new LDAPSingleValueEntryAttribute();
				 	newAttribute.setName(attribute.getID());
				 	newAttribute.setValue(attribute.get());
				 	return newAttribute;
			 	}
			} catch(NamingException nex) {
				logger.logError("buildAttribute", nex);
				throw new LDAPException("Could not build attribute.", nex);
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param entryDN
	 * @param attributes
	 * @return
	 * @throws LDAPException
	 */
	protected LDAPEntry buildEntry(String entryDN, Attributes attributes) throws LDAPException {
		LDAPEntry anEntry = new LDAPEntry(entryDN);
		if( attributes != null ) {
			try {
				for (NamingEnumeration attrs=attributes.getAll(); attrs.hasMore();) {
					anEntry.addAttribute(buildAttribute((Attribute)attrs.nextElement()));
				}
			} catch(NamingException nex) {
				logger.logError("buildEntry", nex);
				throw new LDAPException("Could not build entry.", nex);
			}
		}
		return anEntry;
		
	}

	/**
	 * 
	 * @param attrs
	 * @return
	 * @throws LDAPException
	 */
	protected Attributes buildAttributes(LDAPEntryAttributes attrs) throws LDAPException {
		Attributes attributes = new BasicAttributes(IGNORE_CASE);
		
		for(Iterator it = attrs.attributes(); it.hasNext(); ) {
			attributes.put(buildBasicAttribute((LDAPEntryAttribute)it.next()));
		}
		
		return attributes;
	}
	
	/**
	 * 
	 * @param entry
	 * @return
	 * @throws LDAPException
	 */
	protected Attributes buildAttributes(LDAPEntry entry) throws LDAPException {
		return buildAttributes(entry.getAttributes());
	}
	
	/**
	 * 
	 * @param attribute
	 * @return
	 * @throws LDAPException
	 */
	protected BasicAttribute buildBasicAttribute(LDAPEntryAttribute attribute) throws LDAPException {
		if(attribute != null) {
			if(attribute.isMultiValued()) {
				BasicAttribute basicAttribute = new BasicAttribute(attribute.getName());
				for(Iterator it = attribute.getValues().iterator(); it.hasNext(); ) {
					basicAttribute.add(it.next());
				}
				return basicAttribute;
			} else {
				return new BasicAttribute(attribute.getName(), attribute.getValue());
			}
		} else {
			return null;
		}
	}
}
