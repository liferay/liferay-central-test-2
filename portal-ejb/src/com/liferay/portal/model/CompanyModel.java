/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

/**
 * <a href="CompanyModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CompanyModel extends BaseModel {
	public static boolean CACHEABLE = GetterUtil.get(PropsUtil.get(
				"value.object.cacheable.com.liferay.portal.model.Company"),
			VALUE_OBJECT_CACHEABLE);
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Company"), XSS_ALLOW);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Company.companyId"),
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

	public CompanyModel() {
	}

	public String getPrimaryKey() {
		return _companyId;
	}

	public void setPrimaryKey(String pk) {
		setCompanyId(pk);
	}

	public String getCompanyId() {
		return GetterUtil.getString(_companyId);
	}

	public void setCompanyId(String companyId) {
		if (((companyId == null) && (_companyId != null)) ||
				((companyId != null) && (_companyId == null)) ||
				((companyId != null) && (_companyId != null) &&
				!companyId.equals(_companyId))) {
			if (!XSS_ALLOW_COMPANYID) {
				companyId = XSSUtil.strip(companyId);
			}

			_companyId = companyId;
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

	public Object clone() {
		Company clone = new Company();
		clone.setCompanyId(getCompanyId());
		clone.setKey(getKey());
		clone.setPortalURL(getPortalURL());
		clone.setHomeURL(getHomeURL());
		clone.setMx(getMx());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		Company company = (Company)obj;
		String pk = company.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Company company = null;

		try {
			company = (Company)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = company.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _companyId;
	private String _key;
	private String _portalURL;
	private String _homeURL;
	private String _mx;
}