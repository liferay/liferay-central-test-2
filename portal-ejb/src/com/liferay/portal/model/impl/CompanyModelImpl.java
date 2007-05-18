/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

/**
 * <a href="CompanyModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>Company</code> table in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.Company
 * @see com.liferay.portal.service.model.CompanyModel
 * @see com.liferay.portal.service.model.impl.CompanyImpl
 *
 */
public class CompanyModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "Company";
	public static Object[][] TABLE_COLUMNS = {
			{ "companyId", new Integer(Types.BIGINT) },
			{ "accountId", new Integer(Types.BIGINT) },
			{ "webId", new Integer(Types.VARCHAR) },
			{ "key_", new Integer(Types.CLOB) },
			{ "portalURL", new Integer(Types.VARCHAR) },
			{ "homeURL", new Integer(Types.VARCHAR) },
			{ "mx", new Integer(Types.VARCHAR) },
			{ "logoId", new Integer(Types.BIGINT) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Company"), XSS_ALLOW);
	public static boolean XSS_ALLOW_WEBID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Company.webId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_KEY = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Company.key"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PORTALURL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Company.portalURL"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_HOMEURL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Company.homeURL"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_MX = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Company.mx"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.CompanyModel"));

	public CompanyModelImpl() {
	}

	public long getPrimaryKey() {
		return _companyId;
	}

	public void setPrimaryKey(long pk) {
		setCompanyId(pk);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public long getAccountId() {
		return _accountId;
	}

	public void setAccountId(long accountId) {
		if (accountId != _accountId) {
			_accountId = accountId;
		}
	}

	public String getWebId() {
		return GetterUtil.getString(_webId);
	}

	public void setWebId(String webId) {
		if (((webId == null) && (_webId != null)) ||
				((webId != null) && (_webId == null)) ||
				((webId != null) && (_webId != null) && !webId.equals(_webId))) {
			if (!XSS_ALLOW_WEBID) {
				webId = XSSUtil.strip(webId);
			}

			_webId = webId;
		}
	}

	public String getKey() {
		return GetterUtil.getString(_key);
	}

	public void setKey(String key) {
		if (((key == null) && (_key != null)) ||
				((key != null) && (_key == null)) ||
				((key != null) && (_key != null) && !key.equals(_key))) {
			if (!XSS_ALLOW_KEY) {
				key = XSSUtil.strip(key);
			}

			_key = key;
		}
	}

	public String getPortalURL() {
		return GetterUtil.getString(_portalURL);
	}

	public void setPortalURL(String portalURL) {
		if (((portalURL == null) && (_portalURL != null)) ||
				((portalURL != null) && (_portalURL == null)) ||
				((portalURL != null) && (_portalURL != null) &&
				!portalURL.equals(_portalURL))) {
			if (!XSS_ALLOW_PORTALURL) {
				portalURL = XSSUtil.strip(portalURL);
			}

			_portalURL = portalURL;
		}
	}

	public String getHomeURL() {
		return GetterUtil.getString(_homeURL);
	}

	public void setHomeURL(String homeURL) {
		if (((homeURL == null) && (_homeURL != null)) ||
				((homeURL != null) && (_homeURL == null)) ||
				((homeURL != null) && (_homeURL != null) &&
				!homeURL.equals(_homeURL))) {
			if (!XSS_ALLOW_HOMEURL) {
				homeURL = XSSUtil.strip(homeURL);
			}

			_homeURL = homeURL;
		}
	}

	public String getMx() {
		return GetterUtil.getString(_mx);
	}

	public void setMx(String mx) {
		if (((mx == null) && (_mx != null)) || ((mx != null) && (_mx == null)) ||
				((mx != null) && (_mx != null) && !mx.equals(_mx))) {
			if (!XSS_ALLOW_MX) {
				mx = XSSUtil.strip(mx);
			}

			_mx = mx;
		}
	}

	public long getLogoId() {
		return _logoId;
	}

	public void setLogoId(long logoId) {
		if (logoId != _logoId) {
			_logoId = logoId;
		}
	}

	public Object clone() {
		CompanyImpl clone = new CompanyImpl();
		clone.setCompanyId(getCompanyId());
		clone.setAccountId(getAccountId());
		clone.setWebId(getWebId());
		clone.setKey(getKey());
		clone.setPortalURL(getPortalURL());
		clone.setHomeURL(getHomeURL());
		clone.setMx(getMx());
		clone.setLogoId(getLogoId());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		CompanyImpl company = (CompanyImpl)obj;
		long pk = company.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		CompanyImpl company = null;

		try {
			company = (CompanyImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = company.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	private long _companyId;
	private long _accountId;
	private String _webId;
	private String _key;
	private String _portalURL;
	private String _homeURL;
	private String _mx;
	private long _logoId;
}