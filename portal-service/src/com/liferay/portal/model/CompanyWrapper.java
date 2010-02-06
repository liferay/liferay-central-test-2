/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.model;


/**
 * <a href="CompanySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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

	public com.liferay.portal.model.Company toEscapedModel() {
		return _company.toEscapedModel();
	}

	public boolean isNew() {
		return _company.isNew();
	}

	public boolean setNew(boolean n) {
		return _company.setNew(n);
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

	public com.liferay.portal.model.Account getAccount() {
		return _company.getAccount();
	}

	public java.lang.String getAdminName() {
		return _company.getAdminName();
	}

	public java.lang.String getAuthType()
		throws com.liferay.portal.SystemException {
		return _company.getAuthType();
	}

	public com.liferay.portal.model.User getDefaultUser() {
		return _company.getDefaultUser();
	}

	public java.lang.String getDefaultWebId() {
		return _company.getDefaultWebId();
	}

	public java.lang.String getEmailAddress() {
		return _company.getEmailAddress();
	}

	public com.liferay.portal.model.Group getGroup() {
		return _company.getGroup();
	}

	public java.security.Key getKeyObj() {
		return _company.getKeyObj();
	}

	public java.util.Locale getLocale() {
		return _company.getLocale();
	}

	public java.lang.String getName() {
		return _company.getName();
	}

	public java.lang.String getShardName() {
		return _company.getShardName();
	}

	public java.lang.String getShortName() {
		return _company.getShortName();
	}

	public java.util.TimeZone getTimeZone() {
		return _company.getTimeZone();
	}

	public boolean hasCompanyMx(java.lang.String emailAddress) {
		return _company.hasCompanyMx(emailAddress);
	}

	public boolean isAutoLogin() throws com.liferay.portal.SystemException {
		return _company.isAutoLogin();
	}

	public boolean isCommunityLogo() throws com.liferay.portal.SystemException {
		return _company.isCommunityLogo();
	}

	public boolean isSendPassword() throws com.liferay.portal.SystemException {
		return _company.isSendPassword();
	}

	public boolean isStrangers() throws com.liferay.portal.SystemException {
		return _company.isStrangers();
	}

	public boolean isStrangersVerify()
		throws com.liferay.portal.SystemException {
		return _company.isStrangersVerify();
	}

	public boolean isStrangersWithMx()
		throws com.liferay.portal.SystemException {
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