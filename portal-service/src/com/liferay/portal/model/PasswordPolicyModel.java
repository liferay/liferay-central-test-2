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

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="PasswordPolicyModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the PasswordPolicy table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicy
 * @see       com.liferay.portal.model.impl.PasswordPolicyImpl
 * @see       com.liferay.portal.model.impl.PasswordPolicyModelImpl
 * @generated
 */
public interface PasswordPolicyModel extends BaseModel<PasswordPolicy> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getPasswordPolicyId();

	public void setPasswordPolicyId(long passwordPolicyId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	@AutoEscape
	public String getUserName();

	public void setUserName(String userName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public boolean getDefaultPolicy();

	public boolean isDefaultPolicy();

	public void setDefaultPolicy(boolean defaultPolicy);

	@AutoEscape
	public String getName();

	public void setName(String name);

	@AutoEscape
	public String getDescription();

	public void setDescription(String description);

	public boolean getChangeable();

	public boolean isChangeable();

	public void setChangeable(boolean changeable);

	public boolean getChangeRequired();

	public boolean isChangeRequired();

	public void setChangeRequired(boolean changeRequired);

	public long getMinAge();

	public void setMinAge(long minAge);

	public boolean getCheckSyntax();

	public boolean isCheckSyntax();

	public void setCheckSyntax(boolean checkSyntax);

	public boolean getAllowDictionaryWords();

	public boolean isAllowDictionaryWords();

	public void setAllowDictionaryWords(boolean allowDictionaryWords);

	public int getMinAlphanumeric();

	public void setMinAlphanumeric(int minAlphanumeric);

	public int getMinLength();

	public void setMinLength(int minLength);

	public int getMinLowerCase();

	public void setMinLowerCase(int minLowerCase);

	public int getMinNumbers();

	public void setMinNumbers(int minNumbers);

	public int getMinSymbols();

	public void setMinSymbols(int minSymbols);

	public int getMinUpperCase();

	public void setMinUpperCase(int minUpperCase);

	public boolean getHistory();

	public boolean isHistory();

	public void setHistory(boolean history);

	public int getHistoryCount();

	public void setHistoryCount(int historyCount);

	public boolean getExpireable();

	public boolean isExpireable();

	public void setExpireable(boolean expireable);

	public long getMaxAge();

	public void setMaxAge(long maxAge);

	public long getWarningTime();

	public void setWarningTime(long warningTime);

	public int getGraceLimit();

	public void setGraceLimit(int graceLimit);

	public boolean getLockout();

	public boolean isLockout();

	public void setLockout(boolean lockout);

	public int getMaxFailure();

	public void setMaxFailure(int maxFailure);

	public long getLockoutDuration();

	public void setLockoutDuration(long lockoutDuration);

	public boolean getRequireUnlock();

	public boolean isRequireUnlock();

	public void setRequireUnlock(boolean requireUnlock);

	public long getResetFailureCount();

	public void setResetFailureCount(long resetFailureCount);

	public long getResetTicketMaxAge();

	public void setResetTicketMaxAge(long resetTicketMaxAge);

	public PasswordPolicy toEscapedModel();

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(PasswordPolicy passwordPolicy);

	public int hashCode();

	public String toString();

	public String toXmlString();
}