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
 * <a href="AddressModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AddressModel extends BaseModel {
	public static boolean CACHEABLE = GetterUtil.get(PropsUtil.get(
				"value.object.cacheable.com.liferay.portal.model.Address"),
			VALUE_OBJECT_CACHEABLE);
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address"), XSS_ALLOW);
	public static boolean XSS_ALLOW_ADDRESSID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.addressId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.className"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSPK = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.classPK"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_STREET1 = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.street1"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_STREET2 = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.street2"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_STREET3 = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.street3"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CITY = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.city"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ZIP = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.zip"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_REGIONID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.regionId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COUNTRYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.countryId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TYPEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Address.typeId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.AddressModel"));

	public AddressModel() {
	}

	public String getPrimaryKey() {
		return _addressId;
	}

	public void setPrimaryKey(String pk) {
		setAddressId(pk);
	}

	public String getAddressId() {
		return GetterUtil.getString(_addressId);
	}

	public void setAddressId(String addressId) {
		if (((addressId == null) && (_addressId != null)) ||
				((addressId != null) && (_addressId == null)) ||
				((addressId != null) && (_addressId != null) &&
				!addressId.equals(_addressId))) {
			if (!XSS_ALLOW_ADDRESSID) {
				addressId = XSSUtil.strip(addressId);
			}

			_addressId = addressId;
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

	public String getStreet1() {
		return GetterUtil.getString(_street1);
	}

	public void setStreet1(String street1) {
		if (((street1 == null) && (_street1 != null)) ||
				((street1 != null) && (_street1 == null)) ||
				((street1 != null) && (_street1 != null) &&
				!street1.equals(_street1))) {
			if (!XSS_ALLOW_STREET1) {
				street1 = XSSUtil.strip(street1);
			}

			_street1 = street1;
		}
	}

	public String getStreet2() {
		return GetterUtil.getString(_street2);
	}

	public void setStreet2(String street2) {
		if (((street2 == null) && (_street2 != null)) ||
				((street2 != null) && (_street2 == null)) ||
				((street2 != null) && (_street2 != null) &&
				!street2.equals(_street2))) {
			if (!XSS_ALLOW_STREET2) {
				street2 = XSSUtil.strip(street2);
			}

			_street2 = street2;
		}
	}

	public String getStreet3() {
		return GetterUtil.getString(_street3);
	}

	public void setStreet3(String street3) {
		if (((street3 == null) && (_street3 != null)) ||
				((street3 != null) && (_street3 == null)) ||
				((street3 != null) && (_street3 != null) &&
				!street3.equals(_street3))) {
			if (!XSS_ALLOW_STREET3) {
				street3 = XSSUtil.strip(street3);
			}

			_street3 = street3;
		}
	}

	public String getCity() {
		return GetterUtil.getString(_city);
	}

	public void setCity(String city) {
		if (((city == null) && (_city != null)) ||
				((city != null) && (_city == null)) ||
				((city != null) && (_city != null) && !city.equals(_city))) {
			if (!XSS_ALLOW_CITY) {
				city = XSSUtil.strip(city);
			}

			_city = city;
		}
	}

	public String getZip() {
		return GetterUtil.getString(_zip);
	}

	public void setZip(String zip) {
		if (((zip == null) && (_zip != null)) ||
				((zip != null) && (_zip == null)) ||
				((zip != null) && (_zip != null) && !zip.equals(_zip))) {
			if (!XSS_ALLOW_ZIP) {
				zip = XSSUtil.strip(zip);
			}

			_zip = zip;
		}
	}

	public String getRegionId() {
		return GetterUtil.getString(_regionId);
	}

	public void setRegionId(String regionId) {
		if (((regionId == null) && (_regionId != null)) ||
				((regionId != null) && (_regionId == null)) ||
				((regionId != null) && (_regionId != null) &&
				!regionId.equals(_regionId))) {
			if (!XSS_ALLOW_REGIONID) {
				regionId = XSSUtil.strip(regionId);
			}

			_regionId = regionId;
		}
	}

	public String getCountryId() {
		return GetterUtil.getString(_countryId);
	}

	public void setCountryId(String countryId) {
		if (((countryId == null) && (_countryId != null)) ||
				((countryId != null) && (_countryId == null)) ||
				((countryId != null) && (_countryId != null) &&
				!countryId.equals(_countryId))) {
			if (!XSS_ALLOW_COUNTRYID) {
				countryId = XSSUtil.strip(countryId);
			}

			_countryId = countryId;
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

	public boolean getMailing() {
		return _mailing;
	}

	public boolean isMailing() {
		return _mailing;
	}

	public void setMailing(boolean mailing) {
		if (mailing != _mailing) {
			_mailing = mailing;
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
		Address clone = new Address();
		clone.setAddressId(getAddressId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setClassName(getClassName());
		clone.setClassPK(getClassPK());
		clone.setStreet1(getStreet1());
		clone.setStreet2(getStreet2());
		clone.setStreet3(getStreet3());
		clone.setCity(getCity());
		clone.setZip(getZip());
		clone.setRegionId(getRegionId());
		clone.setCountryId(getCountryId());
		clone.setTypeId(getTypeId());
		clone.setMailing(getMailing());
		clone.setPrimary(getPrimary());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		Address address = (Address)obj;
		int value = 0;
		value = DateUtil.compareTo(getCreateDate(), address.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Address address = null;

		try {
			address = (Address)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = address.getPrimaryKey();

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

	private String _addressId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _className;
	private String _classPK;
	private String _street1;
	private String _street2;
	private String _street3;
	private String _city;
	private String _zip;
	private String _regionId;
	private String _countryId;
	private String _typeId;
	private boolean _mailing;
	private boolean _primary;
}