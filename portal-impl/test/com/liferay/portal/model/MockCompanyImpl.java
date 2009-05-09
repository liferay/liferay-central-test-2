/*
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.SystemException;

import java.security.Key;
import java.util.Locale;
import java.util.TimeZone;

/**
 * <a href="MockCompanyImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class MockCompanyImpl
	extends MockBaseModelImpl<Company> implements Company {

	public MockCompanyImpl(long companyId) {
		_companyId = companyId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getAccountId() {
		return _accountId;
	}

	public void setAccountId(long accountId) {
		_accountId = accountId;
	}

	public String getWebId() {
		return _webId;
	}

	public void setWebId(String webId) {
		_webId = webId;
	}

	public String getOriginalWebId() {
		return _originalWebId;
	}

	public void setOriginalWebId(String originalWebId) {
		_originalWebId = originalWebId;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public String getVirtualHost() {
		return _virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		_virtualHost = virtualHost;
	}

	public String getOriginalVirtualHost() {
		return _originalVirtualHost;
	}

	public void setOriginalVirtualHost(String originalVirtualHost) {
		_originalVirtualHost = originalVirtualHost;
	}

	public String getMx() {
		return _mx;
	}

	public void setMx(String mx) {
		_mx = mx;
	}

	public String getOriginalMx() {
		return _originalMx;
	}

	public void setOriginalMx(String originalMx) {
		_originalMx = originalMx;
	}

	public String getHomeURL() {
		return _homeURL;
	}

	public void setHomeURL(String homeURL) {
		_homeURL = homeURL;
	}

	public long getLogoId() {
		return _logoId;
	}

	public void setLogoId(long logoId) {
		_logoId = logoId;
	}

	public long getOriginalLogoId() {
		return _originalLogoId;
	}

	public void setOriginalLogoId(long originalLogoId) {
		_originalLogoId = originalLogoId;
	}

	public boolean isSetOriginalLogoId() {
		return _setOriginalLogoId;
	}

	public void setSetOriginalLogoId(boolean setOriginalLogoId) {
		_setOriginalLogoId = setOriginalLogoId;
	}

	public boolean isSystem() {
		return _system;
	}

	public void setSystem(boolean system) {
		_system = system;
	}

	public Account getAccount() {
		return null;
	}

	public String getAdminName() {
		return null;
	}

	public String getAuthType() throws SystemException {
		return null;
	}

	public User getDefaultUser() {
		return null;
	}

	public String getDefaultWebId() {
		return null;
	}

	public String getEmailAddress() {
		return null;
	}

	public Key getKeyObj() {
		return null;
	}

	public Locale getLocale() {
		return null;
	}

	public String getName() {
		return null;
	}

	public String getShardName() {
		return null;
	}

	public String getShortName() {
		return null;
	}

	public TimeZone getTimeZone() {
		return null;
	}

	public boolean hasCompanyMx(String emailAddress) {
		return false;
	}

	public boolean isAutoLogin() throws SystemException {
		return false;
	}

	public boolean isCommunityLogo() throws SystemException {
		return false;
	}

	public boolean isSendPassword() throws SystemException {
		return false;
	}

	public boolean isStrangers() throws SystemException {
		return false;
	}

	public boolean isStrangersVerify() throws SystemException {
		return false;
	}

	public boolean isStrangersWithMx() throws SystemException {
		return false;
	}

	public void setKeyObj(Key keyObj) {

	}

	public long getPrimaryKey() {
		return 0;
	}

	public void setPrimaryKey(long pk) {

	}

	public boolean getSystem() {
		return isSystem();
	}

	public Company toEscapedModel() {
		return null;  
	}

	private long _companyId;
	private long _accountId;
	private String _webId;
	private String _originalWebId;
	private String _key;
	private String _virtualHost;
	private String _originalVirtualHost;
	private String _mx;
	private String _originalMx;
	private String _homeURL;
	private long _logoId;
	private long _originalLogoId;
	private boolean _setOriginalLogoId;
	private boolean _system;
}