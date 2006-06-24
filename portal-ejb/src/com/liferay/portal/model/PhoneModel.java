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

import com.liferay.util.DateUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="PhoneModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PhoneModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Phone"), XSS_ALLOW);
	public static boolean XSS_ALLOW_PHONEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Phone.phoneId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Phone.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Phone.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Phone.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Phone.className"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSPK = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Phone.classPK"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NUMBER = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Phone.number"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_EXTENSION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Phone.extension"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TYPEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Phone.typeId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PhoneModel"));

	public PhoneModel() {
	}

	public String getPrimaryKey() {
		return _phoneId;
	}

	public void setPrimaryKey(String pk) {
		setPhoneId(pk);
	}

	public String getPhoneId() {
		return GetterUtil.getString(_phoneId);
	}

	public void setPhoneId(String phoneId) {
		if (((phoneId == null) && (_phoneId != null)) ||
				((phoneId != null) && (_phoneId == null)) ||
				((phoneId != null) && (_phoneId != null) &&
				!phoneId.equals(_phoneId))) {
			if (!XSS_ALLOW_PHONEID) {
				phoneId = XSSUtil.strip(phoneId);
			}

			_phoneId = phoneId;
			setModified(true);
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
			setModified(true);
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
			setModified(true);
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
			setModified(true);
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
			setModified(true);
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
			setModified(true);
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
			setModified(true);
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
			setModified(true);
		}
	}

	public String getNumber() {
		return GetterUtil.getString(_number);
	}

	public void setNumber(String number) {
		if (((number == null) && (_number != null)) ||
				((number != null) && (_number == null)) ||
				((number != null) && (_number != null) &&
				!number.equals(_number))) {
			if (!XSS_ALLOW_NUMBER) {
				number = XSSUtil.strip(number);
			}

			_number = number;
			setModified(true);
		}
	}

	public String getExtension() {
		return GetterUtil.getString(_extension);
	}

	public void setExtension(String extension) {
		if (((extension == null) && (_extension != null)) ||
				((extension != null) && (_extension == null)) ||
				((extension != null) && (_extension != null) &&
				!extension.equals(_extension))) {
			if (!XSS_ALLOW_EXTENSION) {
				extension = XSSUtil.strip(extension);
			}

			_extension = extension;
			setModified(true);
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
			setModified(true);
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
			setModified(true);
		}
	}

	public Object clone() {
		Phone clone = new Phone();
		clone.setPhoneId(getPhoneId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setClassName(getClassName());
		clone.setClassPK(getClassPK());
		clone.setNumber(getNumber());
		clone.setExtension(getExtension());
		clone.setTypeId(getTypeId());
		clone.setPrimary(getPrimary());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		Phone phone = (Phone)obj;
		int value = 0;
		value = DateUtil.compareTo(getCreateDate(), phone.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Phone phone = null;

		try {
			phone = (Phone)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = phone.getPrimaryKey();

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

	private String _phoneId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _className;
	private String _classPK;
	private String _number;
	private String _extension;
	private String _typeId;
	private boolean _primary;
}