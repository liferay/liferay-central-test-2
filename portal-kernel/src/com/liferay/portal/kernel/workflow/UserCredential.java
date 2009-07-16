
package com.liferay.portal.kernel.workflow;

import java.io.Serializable;
import java.util.Locale;
import java.util.Set;

/**
 * <a href="UserCredential.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * The user credential is a container for a user's id and its roles and is used
 * as the credential towards the workflow engine. For convenience, it is just
 * added automatically through the request by creating it using the
 * {@link WorkflowUtil#createUserCredential(long)} as the API just takes the
 * user id, the role set and additional information is being added by the
 * {@link UserCredentialFactory} invoked by the proxy (most likely the request
 * builder) in order to avoid the implementation or adapter having to call back
 * the portal for the set of roles of a user's id.
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

	/**
	 * @param companyId the id of the company the user reflected by this
	 *            credential should be assigned to
	 */
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	/**
	 * @param emailAddress the email address of the user reflected by this
	 *            credential
	 */
	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	/**
	 * @param locale the locale of the user reflected by this credential
	 */
	public void setLocale(Locale locale) {
		this._locale = locale;
	}

	/**
	 * @param login the login name of the user reflected by this credential
	 */
	public void setLogin(String login) {
		this._login = login;
	}

	/**
	 * @param roleSet the set of roles the user of this credential is assigned
	 *            to
	 */
	public void setRoleSet(Set<Long> roleSet) {
		_roleSet = roleSet;
	}

	/**
	 * @param screenName the screen name of the user reflected by this
	 *            credential
	 */
	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	/**
	 * @param userId the id of the user reflected by this credential
	 */
	public void setUserId(long userId) {
		_userId = userId;
	}

	private long _companyId;
	private String _emailAddress;
	private Locale _locale;
	private String _login;
	private Set<Long> _roleSet;
	private String _screenName;
	private long _userId;
}
