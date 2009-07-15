
package com.liferay.portal.kernel.workflow;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;

/**
 * <a href="UserCredential.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * The user credential is a container for a user's id and its roles and is used
 * as the credential towards the workflow engine. For convenience, it is just
 * added automatically through the request by creating it using the
 * {@link WorkflowUtil#createUserCredential(long)} as the API just takes the
 * user id, the role set is being added by the proxy in order to avoid the
 * implementation or adapter having to call back the portal for the set of roles
 * of a user's id.
 * </p>
 * 
 * @author Micha Kiener
 * 
 */
public class UserCredential implements Serializable {
	/** The serialization id. */
	private static final long serialVersionUID = -2223408262504608474L;
	
	/**
	 * Default constructor, just used for de-serialization, never for
	 * construction.
	 */
	public UserCredential() {

	}

	/**
	 * Creates a new user credential out of the given user object.
	 * 
	 * @param user the user to be represented by this credential
	 * 
	 * @throws SystemException is thrown if requesting attributes through the
	 *             user object failed
	 * @throws PortalException is thrown if requesting attributes through the
	 *             user object failed
	 */
	public UserCredential(User user)
		throws PortalException, SystemException {
		_companyId = user.getCompanyId();
		_emailAddress = user.getEmailAddress();
		_locale = user.getLocale();
		_login = user.getLogin();
		_screenName = user.getScreenName();
		_userId = user.getUserId();
		
		// create the role set
		long[] roleIds = user.getRoleIds();
		_roleSet = new HashSet<Long>(roleIds.length);
		for (long roleId : roleIds) {
			_roleSet.add(Long.valueOf(roleId));
		}
	}
	
	/**
	 * @return the id of the company the user represented by this credential
	 *         belongs to
	 */
	public long getCompanyId() {
		return _companyId;
	}
	
	/**
	 * @return the email address of the user represented by this credential
	 */
	public String getEmailAddress() {
		return _emailAddress;
	}

	/**
	 * @return the locale of the user represented by this credential
	 */
	public Locale getLocale() {
		return _locale;
	}
	
	/**
	 * @return the login name of the user represented by this credential
	 */
	public String getLogin() {
		return _login;
	}

	/**
	 * @return the set of the role ids the user reflected by this credential has
	 */
	public Set<Long> getRoleIds() {
		return _roleSet;
	}

	/**
	 * @return the screen name of the user represented by this credential
	 */
	public String getScreenName() {
		return _screenName;
	}

	/**
	 * @return the id of the user reflected by this credential
	 */
	public long getUserId() {
		return _userId;
	}

	private long _companyId;
	private String _emailAddress;
	private Locale _locale;
	private String _login;
	private Set<Long> _roleSet;
	private String _screenName;
	private long _userId;
}
