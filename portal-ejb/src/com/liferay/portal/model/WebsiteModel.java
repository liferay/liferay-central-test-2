/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.util.DateUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="WebsiteModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WebsiteModel extends BaseModel {
	public static boolean CACHEABLE = GetterUtil.get(PropsUtil.get(
				"value.object.cacheable.com.liferay.portal.model.Website"),
			VALUE_OBJECT_CACHEABLE);
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Website"), XSS_ALLOW);
	public static boolean XSS_ALLOW_WEBSITEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Website.websiteId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Website.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Website.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Website.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Website.className"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSPK = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Website.classPK"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_URL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Website.url"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TYPEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Website.typeId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.WebsiteModel"));

	public WebsiteModel() {
	}

	public String getPrimaryKey() {
		return _websiteId;
	}

	public void setPrimaryKey(String pk) {
		setWebsiteId(pk);
	}

	public String getWebsiteId() {
		return GetterUtil.getString(_websiteId);
	}

	public void setWebsiteId(String websiteId) {
		if (((websiteId == null) && (_websiteId != null)) ||
				((websiteId != null) && (_websiteId == null)) ||
				((websiteId != null) && (_websiteId != null) &&
				!websiteId.equals(_websiteId))) {
			if (!XSS_ALLOW_WEBSITEID) {
				websiteId = XSSUtil.strip(websiteId);
			}

			_websiteId = websiteId;
		}
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

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
		}
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		if (((userName == null) && (_userName != null)) ||
				((userName != null) && (_userName == null)) ||
				((userName != null) && (_userName != null) &&
				!userName.equals(_userName))) {
			if (!XSS_ALLOW_USERNAME) {
				userName = XSSUtil.strip(userName);
			}

			_userName = userName;
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
		}
	}

	public String getClassName() {
		return GetterUtil.getString(_className);
	}

	public void setClassName(String className) {
		if (((className == null) && (_className != null)) ||
				((className != null) && (_className == null)) ||
				((className != null) && (_className != null) &&
				!className.equals(_className))) {
			if (!XSS_ALLOW_CLASSNAME) {
				className = XSSUtil.strip(className);
			}

			_className = className;
		}
	}

	public String getClassPK() {
		return GetterUtil.getString(_classPK);
	}

	public void setClassPK(String classPK) {
		if (((classPK == null) && (_classPK != null)) ||
				((classPK != null) && (_classPK == null)) ||
				((classPK != null) && (_classPK != null) &&
				!classPK.equals(_classPK))) {
			if (!XSS_ALLOW_CLASSPK) {
				classPK = XSSUtil.strip(classPK);
			}

			_classPK = classPK;
		}
	}

	public String getUrl() {
		return GetterUtil.getString(_url);
	}

	public void setUrl(String url) {
		if (((url == null) && (_url != null)) ||
				((url != null) && (_url == null)) ||
				((url != null) && (_url != null) && !url.equals(_url))) {
			if (!XSS_ALLOW_URL) {
				url = XSSUtil.strip(url);
			}

			_url = url;
		}
	}

	public String getTypeId() {
		return GetterUtil.getString(_typeId);
	}

	public void setTypeId(String typeId) {
		if (((typeId == null) && (_typeId != null)) ||
				((typeId != null) && (_typeId == null)) ||
				((typeId != null) && (_typeId != null) &&
				!typeId.equals(_typeId))) {
			if (!XSS_ALLOW_TYPEID) {
				typeId = XSSUtil.strip(typeId);
			}

			_typeId = typeId;
		}
	}

	public boolean getPrimary() {
		return _primary;
	}

	public boolean isPrimary() {
		return _primary;
	}

	public void setPrimary(boolean primary) {
		if (primary != _primary) {
			_primary = primary;
		}
	}

	public Object clone() {
		Website clone = new Website();
		clone.setWebsiteId(getWebsiteId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setClassName(getClassName());
		clone.setClassPK(getClassPK());
		clone.setUrl(getUrl());
		clone.setTypeId(getTypeId());
		clone.setPrimary(getPrimary());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		Website website = (Website)obj;
		int value = 0;
		value = DateUtil.compareTo(getCreateDate(), website.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Website website = null;

		try {
			website = (Website)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = website.getPrimaryKey();

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

	private String _websiteId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _className;
	private String _classPK;
	private String _url;
	private String _typeId;
	private boolean _primary;
}