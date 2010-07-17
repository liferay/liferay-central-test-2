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
 * This class is a wrapper for {@link Company}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Company
 * @generated
 */
public class CompanyWrapper implements Company {
	public CompanyWrapper(Company company) {
		_company = company;
	}

	public long getPrimaryKey() {
		return _company.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_company.setPrimaryKey(pk);
	}

	public long getCompanyId() {
		return _company.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_company.setCompanyId(companyId);
	}

	public long getAccountId() {
		return _company.getAccountId();
	}

	public void setAccountId(long accountId) {
		_company.setAccountId(accountId);
	}

	public java.lang.String getWebId() {
		return _company.getWebId();
	}

	public void setWebId(java.lang.String webId) {
		_company.setWebId(webId);
	}

	public java.lang.String getKey() {
		return _company.getKey();
	}

	public void setKey(java.lang.String key) {
		_company.setKey(key);
	}

	public java.lang.String getVirtualHost() {
		return _company.getVirtualHost();
	}

	public void setVirtualHost(java.lang.String virtualHost) {
		_company.setVirtualHost(virtualHost);
	}

	public java.lang.String getMx() {
		return _company.getMx();
	}

	public void setMx(java.lang.String mx) {
		_company.setMx(mx);
	}

	public java.lang.String getHomeURL() {
		return _company.getHomeURL();
	}

	public void setHomeURL(java.lang.String homeURL) {
		_company.setHomeURL(homeURL);
	}

	public long getLogoId() {
		return _company.getLogoId();
	}

	public void setLogoId(long logoId) {
		_company.setLogoId(logoId);
	}

	public boolean getSystem() {
		return _company.getSystem();
	}

	public boolean isSystem() {
		return _company.isSystem();
	}

	public void setSystem(boolean system) {
		_company.setSystem(system);
	}

	public int getMaxUsers() {
		return _company.getMaxUsers();
	}

	public void setMaxUsers(int maxUsers) {
		_company.setMaxUsers(maxUsers);
	}

	public com.liferay.portal.model.Company toEscapedModel() {
		return _company.toEscapedModel();
	}

	public boolean isNew() {
		return _company.isNew();
	}

	public void setNew(boolean n) {
		_company.setNew(n);
	}

	public boolean isCachedModel() {
		return _company.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_company.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _company.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_company.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _company.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _company.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_company.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _company.clone();
	}

	public int compareTo(com.liferay.portal.model.Company company) {
		return _company.compareTo(company);
	}

	public int hashCode() {
		return _company.hashCode();
	}

	public java.lang.String toString() {
		return _company.toString();
	}

	public java.lang.String toXmlString() {
		return _company.toXmlString();
	}

	public com.liferay.portal.model.Account getAccount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getAccount();
	}

	public java.lang.String getAdminName() {
		return _company.getAdminName();
	}

	public java.lang.String getAuthType()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.getAuthType();
	}

	public com.liferay.portal.model.User getDefaultUser()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getDefaultUser();
	}

	public java.lang.String getDefaultWebId() {
		return _company.getDefaultWebId();
	}

	public java.lang.String getEmailAddress() {
		return _company.getEmailAddress();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getGroup();
	}

	public java.security.Key getKeyObj() {
		return _company.getKeyObj();
	}

	public java.util.Locale getLocale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getLocale();
	}

	public java.lang.String getName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getName();
	}

	public java.lang.String getShardName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getShardName();
	}

	public java.lang.String getShortName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getShortName();
	}

	public java.util.TimeZone getTimeZone()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _company.getTimeZone();
	}

	public boolean hasCompanyMx(java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.hasCompanyMx(emailAddress);
	}

	public boolean isAutoLogin()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isAutoLogin();
	}

	public boolean isCommunityLogo()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isCommunityLogo();
	}

	public boolean isSendPassword()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isSendPassword();
	}

	public boolean isSendPasswordResetLink()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isSendPasswordResetLink();
	}

	public boolean isStrangers()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isStrangers();
	}

	public boolean isStrangersVerify()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isStrangersVerify();
	}

	public boolean isStrangersWithMx()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _company.isStrangersWithMx();
	}

	public void setKeyObj(java.security.Key keyObj) {
		_company.setKeyObj(keyObj);
	}

	public Company getWrappedCompany() {
		return _company;
	}

	private Company _company;
}