/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link PasswordPolicy}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicy
 * @generated
 */
public class PasswordPolicyWrapper implements PasswordPolicy {
	public PasswordPolicyWrapper(PasswordPolicy passwordPolicy) {
		_passwordPolicy = passwordPolicy;
	}

	/**
	* Gets the primary key of this password policy.
	*
	* @return the primary key of this password policy
	*/
	public long getPrimaryKey() {
		return _passwordPolicy.getPrimaryKey();
	}

	/**
	* Sets the primary key of this password policy
	*
	* @param pk the primary key of this password policy
	*/
	public void setPrimaryKey(long pk) {
		_passwordPolicy.setPrimaryKey(pk);
	}

	/**
	* Gets the password policy id of this password policy.
	*
	* @return the password policy id of this password policy
	*/
	public long getPasswordPolicyId() {
		return _passwordPolicy.getPasswordPolicyId();
	}

	/**
	* Sets the password policy id of this password policy.
	*
	* @param passwordPolicyId the password policy id of this password policy
	*/
	public void setPasswordPolicyId(long passwordPolicyId) {
		_passwordPolicy.setPasswordPolicyId(passwordPolicyId);
	}

	/**
	* Gets the company id of this password policy.
	*
	* @return the company id of this password policy
	*/
	public long getCompanyId() {
		return _passwordPolicy.getCompanyId();
	}

	/**
	* Sets the company id of this password policy.
	*
	* @param companyId the company id of this password policy
	*/
	public void setCompanyId(long companyId) {
		_passwordPolicy.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this password policy.
	*
	* @return the user id of this password policy
	*/
	public long getUserId() {
		return _passwordPolicy.getUserId();
	}

	/**
	* Sets the user id of this password policy.
	*
	* @param userId the user id of this password policy
	*/
	public void setUserId(long userId) {
		_passwordPolicy.setUserId(userId);
	}

	/**
	* Gets the user uuid of this password policy.
	*
	* @return the user uuid of this password policy
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordPolicy.getUserUuid();
	}

	/**
	* Sets the user uuid of this password policy.
	*
	* @param userUuid the user uuid of this password policy
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_passwordPolicy.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this password policy.
	*
	* @return the user name of this password policy
	*/
	public java.lang.String getUserName() {
		return _passwordPolicy.getUserName();
	}

	/**
	* Sets the user name of this password policy.
	*
	* @param userName the user name of this password policy
	*/
	public void setUserName(java.lang.String userName) {
		_passwordPolicy.setUserName(userName);
	}

	/**
	* Gets the create date of this password policy.
	*
	* @return the create date of this password policy
	*/
	public java.util.Date getCreateDate() {
		return _passwordPolicy.getCreateDate();
	}

	/**
	* Sets the create date of this password policy.
	*
	* @param createDate the create date of this password policy
	*/
	public void setCreateDate(java.util.Date createDate) {
		_passwordPolicy.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this password policy.
	*
	* @return the modified date of this password policy
	*/
	public java.util.Date getModifiedDate() {
		return _passwordPolicy.getModifiedDate();
	}

	/**
	* Sets the modified date of this password policy.
	*
	* @param modifiedDate the modified date of this password policy
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_passwordPolicy.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the default policy of this password policy.
	*
	* @return the default policy of this password policy
	*/
	public boolean getDefaultPolicy() {
		return _passwordPolicy.getDefaultPolicy();
	}

	/**
	* Determines if this password policy is default policy.
	*
	* @return <code>true</code> if this password policy is default policy; <code>false</code> otherwise
	*/
	public boolean isDefaultPolicy() {
		return _passwordPolicy.isDefaultPolicy();
	}

	/**
	* Sets whether this password policy is default policy.
	*
	* @param defaultPolicy the default policy of this password policy
	*/
	public void setDefaultPolicy(boolean defaultPolicy) {
		_passwordPolicy.setDefaultPolicy(defaultPolicy);
	}

	/**
	* Gets the name of this password policy.
	*
	* @return the name of this password policy
	*/
	public java.lang.String getName() {
		return _passwordPolicy.getName();
	}

	/**
	* Sets the name of this password policy.
	*
	* @param name the name of this password policy
	*/
	public void setName(java.lang.String name) {
		_passwordPolicy.setName(name);
	}

	/**
	* Gets the description of this password policy.
	*
	* @return the description of this password policy
	*/
	public java.lang.String getDescription() {
		return _passwordPolicy.getDescription();
	}

	/**
	* Sets the description of this password policy.
	*
	* @param description the description of this password policy
	*/
	public void setDescription(java.lang.String description) {
		_passwordPolicy.setDescription(description);
	}

	/**
	* Gets the changeable of this password policy.
	*
	* @return the changeable of this password policy
	*/
	public boolean getChangeable() {
		return _passwordPolicy.getChangeable();
	}

	/**
	* Determines if this password policy is changeable.
	*
	* @return <code>true</code> if this password policy is changeable; <code>false</code> otherwise
	*/
	public boolean isChangeable() {
		return _passwordPolicy.isChangeable();
	}

	/**
	* Sets whether this password policy is changeable.
	*
	* @param changeable the changeable of this password policy
	*/
	public void setChangeable(boolean changeable) {
		_passwordPolicy.setChangeable(changeable);
	}

	/**
	* Gets the change required of this password policy.
	*
	* @return the change required of this password policy
	*/
	public boolean getChangeRequired() {
		return _passwordPolicy.getChangeRequired();
	}

	/**
	* Determines if this password policy is change required.
	*
	* @return <code>true</code> if this password policy is change required; <code>false</code> otherwise
	*/
	public boolean isChangeRequired() {
		return _passwordPolicy.isChangeRequired();
	}

	/**
	* Sets whether this password policy is change required.
	*
	* @param changeRequired the change required of this password policy
	*/
	public void setChangeRequired(boolean changeRequired) {
		_passwordPolicy.setChangeRequired(changeRequired);
	}

	/**
	* Gets the min age of this password policy.
	*
	* @return the min age of this password policy
	*/
	public long getMinAge() {
		return _passwordPolicy.getMinAge();
	}

	/**
	* Sets the min age of this password policy.
	*
	* @param minAge the min age of this password policy
	*/
	public void setMinAge(long minAge) {
		_passwordPolicy.setMinAge(minAge);
	}

	/**
	* Gets the check syntax of this password policy.
	*
	* @return the check syntax of this password policy
	*/
	public boolean getCheckSyntax() {
		return _passwordPolicy.getCheckSyntax();
	}

	/**
	* Determines if this password policy is check syntax.
	*
	* @return <code>true</code> if this password policy is check syntax; <code>false</code> otherwise
	*/
	public boolean isCheckSyntax() {
		return _passwordPolicy.isCheckSyntax();
	}

	/**
	* Sets whether this password policy is check syntax.
	*
	* @param checkSyntax the check syntax of this password policy
	*/
	public void setCheckSyntax(boolean checkSyntax) {
		_passwordPolicy.setCheckSyntax(checkSyntax);
	}

	/**
	* Gets the allow dictionary words of this password policy.
	*
	* @return the allow dictionary words of this password policy
	*/
	public boolean getAllowDictionaryWords() {
		return _passwordPolicy.getAllowDictionaryWords();
	}

	/**
	* Determines if this password policy is allow dictionary words.
	*
	* @return <code>true</code> if this password policy is allow dictionary words; <code>false</code> otherwise
	*/
	public boolean isAllowDictionaryWords() {
		return _passwordPolicy.isAllowDictionaryWords();
	}

	/**
	* Sets whether this password policy is allow dictionary words.
	*
	* @param allowDictionaryWords the allow dictionary words of this password policy
	*/
	public void setAllowDictionaryWords(boolean allowDictionaryWords) {
		_passwordPolicy.setAllowDictionaryWords(allowDictionaryWords);
	}

	/**
	* Gets the min alphanumeric of this password policy.
	*
	* @return the min alphanumeric of this password policy
	*/
	public int getMinAlphanumeric() {
		return _passwordPolicy.getMinAlphanumeric();
	}

	/**
	* Sets the min alphanumeric of this password policy.
	*
	* @param minAlphanumeric the min alphanumeric of this password policy
	*/
	public void setMinAlphanumeric(int minAlphanumeric) {
		_passwordPolicy.setMinAlphanumeric(minAlphanumeric);
	}

	/**
	* Gets the min length of this password policy.
	*
	* @return the min length of this password policy
	*/
	public int getMinLength() {
		return _passwordPolicy.getMinLength();
	}

	/**
	* Sets the min length of this password policy.
	*
	* @param minLength the min length of this password policy
	*/
	public void setMinLength(int minLength) {
		_passwordPolicy.setMinLength(minLength);
	}

	/**
	* Gets the min lower case of this password policy.
	*
	* @return the min lower case of this password policy
	*/
	public int getMinLowerCase() {
		return _passwordPolicy.getMinLowerCase();
	}

	/**
	* Sets the min lower case of this password policy.
	*
	* @param minLowerCase the min lower case of this password policy
	*/
	public void setMinLowerCase(int minLowerCase) {
		_passwordPolicy.setMinLowerCase(minLowerCase);
	}

	/**
	* Gets the min numbers of this password policy.
	*
	* @return the min numbers of this password policy
	*/
	public int getMinNumbers() {
		return _passwordPolicy.getMinNumbers();
	}

	/**
	* Sets the min numbers of this password policy.
	*
	* @param minNumbers the min numbers of this password policy
	*/
	public void setMinNumbers(int minNumbers) {
		_passwordPolicy.setMinNumbers(minNumbers);
	}

	/**
	* Gets the min symbols of this password policy.
	*
	* @return the min symbols of this password policy
	*/
	public int getMinSymbols() {
		return _passwordPolicy.getMinSymbols();
	}

	/**
	* Sets the min symbols of this password policy.
	*
	* @param minSymbols the min symbols of this password policy
	*/
	public void setMinSymbols(int minSymbols) {
		_passwordPolicy.setMinSymbols(minSymbols);
	}

	/**
	* Gets the min upper case of this password policy.
	*
	* @return the min upper case of this password policy
	*/
	public int getMinUpperCase() {
		return _passwordPolicy.getMinUpperCase();
	}

	/**
	* Sets the min upper case of this password policy.
	*
	* @param minUpperCase the min upper case of this password policy
	*/
	public void setMinUpperCase(int minUpperCase) {
		_passwordPolicy.setMinUpperCase(minUpperCase);
	}

	/**
	* Gets the history of this password policy.
	*
	* @return the history of this password policy
	*/
	public boolean getHistory() {
		return _passwordPolicy.getHistory();
	}

	/**
	* Determines if this password policy is history.
	*
	* @return <code>true</code> if this password policy is history; <code>false</code> otherwise
	*/
	public boolean isHistory() {
		return _passwordPolicy.isHistory();
	}

	/**
	* Sets whether this password policy is history.
	*
	* @param history the history of this password policy
	*/
	public void setHistory(boolean history) {
		_passwordPolicy.setHistory(history);
	}

	/**
	* Gets the history count of this password policy.
	*
	* @return the history count of this password policy
	*/
	public int getHistoryCount() {
		return _passwordPolicy.getHistoryCount();
	}

	/**
	* Sets the history count of this password policy.
	*
	* @param historyCount the history count of this password policy
	*/
	public void setHistoryCount(int historyCount) {
		_passwordPolicy.setHistoryCount(historyCount);
	}

	/**
	* Gets the expireable of this password policy.
	*
	* @return the expireable of this password policy
	*/
	public boolean getExpireable() {
		return _passwordPolicy.getExpireable();
	}

	/**
	* Determines if this password policy is expireable.
	*
	* @return <code>true</code> if this password policy is expireable; <code>false</code> otherwise
	*/
	public boolean isExpireable() {
		return _passwordPolicy.isExpireable();
	}

	/**
	* Sets whether this password policy is expireable.
	*
	* @param expireable the expireable of this password policy
	*/
	public void setExpireable(boolean expireable) {
		_passwordPolicy.setExpireable(expireable);
	}

	/**
	* Gets the max age of this password policy.
	*
	* @return the max age of this password policy
	*/
	public long getMaxAge() {
		return _passwordPolicy.getMaxAge();
	}

	/**
	* Sets the max age of this password policy.
	*
	* @param maxAge the max age of this password policy
	*/
	public void setMaxAge(long maxAge) {
		_passwordPolicy.setMaxAge(maxAge);
	}

	/**
	* Gets the warning time of this password policy.
	*
	* @return the warning time of this password policy
	*/
	public long getWarningTime() {
		return _passwordPolicy.getWarningTime();
	}

	/**
	* Sets the warning time of this password policy.
	*
	* @param warningTime the warning time of this password policy
	*/
	public void setWarningTime(long warningTime) {
		_passwordPolicy.setWarningTime(warningTime);
	}

	/**
	* Gets the grace limit of this password policy.
	*
	* @return the grace limit of this password policy
	*/
	public int getGraceLimit() {
		return _passwordPolicy.getGraceLimit();
	}

	/**
	* Sets the grace limit of this password policy.
	*
	* @param graceLimit the grace limit of this password policy
	*/
	public void setGraceLimit(int graceLimit) {
		_passwordPolicy.setGraceLimit(graceLimit);
	}

	/**
	* Gets the lockout of this password policy.
	*
	* @return the lockout of this password policy
	*/
	public boolean getLockout() {
		return _passwordPolicy.getLockout();
	}

	/**
	* Determines if this password policy is lockout.
	*
	* @return <code>true</code> if this password policy is lockout; <code>false</code> otherwise
	*/
	public boolean isLockout() {
		return _passwordPolicy.isLockout();
	}

	/**
	* Sets whether this password policy is lockout.
	*
	* @param lockout the lockout of this password policy
	*/
	public void setLockout(boolean lockout) {
		_passwordPolicy.setLockout(lockout);
	}

	/**
	* Gets the max failure of this password policy.
	*
	* @return the max failure of this password policy
	*/
	public int getMaxFailure() {
		return _passwordPolicy.getMaxFailure();
	}

	/**
	* Sets the max failure of this password policy.
	*
	* @param maxFailure the max failure of this password policy
	*/
	public void setMaxFailure(int maxFailure) {
		_passwordPolicy.setMaxFailure(maxFailure);
	}

	/**
	* Gets the lockout duration of this password policy.
	*
	* @return the lockout duration of this password policy
	*/
	public long getLockoutDuration() {
		return _passwordPolicy.getLockoutDuration();
	}

	/**
	* Sets the lockout duration of this password policy.
	*
	* @param lockoutDuration the lockout duration of this password policy
	*/
	public void setLockoutDuration(long lockoutDuration) {
		_passwordPolicy.setLockoutDuration(lockoutDuration);
	}

	/**
	* Gets the require unlock of this password policy.
	*
	* @return the require unlock of this password policy
	*/
	public boolean getRequireUnlock() {
		return _passwordPolicy.getRequireUnlock();
	}

	/**
	* Determines if this password policy is require unlock.
	*
	* @return <code>true</code> if this password policy is require unlock; <code>false</code> otherwise
	*/
	public boolean isRequireUnlock() {
		return _passwordPolicy.isRequireUnlock();
	}

	/**
	* Sets whether this password policy is require unlock.
	*
	* @param requireUnlock the require unlock of this password policy
	*/
	public void setRequireUnlock(boolean requireUnlock) {
		_passwordPolicy.setRequireUnlock(requireUnlock);
	}

	/**
	* Gets the reset failure count of this password policy.
	*
	* @return the reset failure count of this password policy
	*/
	public long getResetFailureCount() {
		return _passwordPolicy.getResetFailureCount();
	}

	/**
	* Sets the reset failure count of this password policy.
	*
	* @param resetFailureCount the reset failure count of this password policy
	*/
	public void setResetFailureCount(long resetFailureCount) {
		_passwordPolicy.setResetFailureCount(resetFailureCount);
	}

	/**
	* Gets the reset ticket max age of this password policy.
	*
	* @return the reset ticket max age of this password policy
	*/
	public long getResetTicketMaxAge() {
		return _passwordPolicy.getResetTicketMaxAge();
	}

	/**
	* Sets the reset ticket max age of this password policy.
	*
	* @param resetTicketMaxAge the reset ticket max age of this password policy
	*/
	public void setResetTicketMaxAge(long resetTicketMaxAge) {
		_passwordPolicy.setResetTicketMaxAge(resetTicketMaxAge);
	}

	public boolean isNew() {
		return _passwordPolicy.isNew();
	}

	public void setNew(boolean n) {
		_passwordPolicy.setNew(n);
	}

	public boolean isCachedModel() {
		return _passwordPolicy.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_passwordPolicy.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _passwordPolicy.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_passwordPolicy.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _passwordPolicy.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _passwordPolicy.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_passwordPolicy.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new PasswordPolicyWrapper((PasswordPolicy)_passwordPolicy.clone());
	}

	public int compareTo(com.liferay.portal.model.PasswordPolicy passwordPolicy) {
		return _passwordPolicy.compareTo(passwordPolicy);
	}

	public int hashCode() {
		return _passwordPolicy.hashCode();
	}

	public com.liferay.portal.model.PasswordPolicy toEscapedModel() {
		return new PasswordPolicyWrapper(_passwordPolicy.toEscapedModel());
	}

	public java.lang.String toString() {
		return _passwordPolicy.toString();
	}

	public java.lang.String toXmlString() {
		return _passwordPolicy.toXmlString();
	}

	public PasswordPolicy getWrappedPasswordPolicy() {
		return _passwordPolicy;
	}

	private PasswordPolicy _passwordPolicy;
}