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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

/**
 * <a href="OrganizationModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrganizationModelImpl extends BaseModelImpl {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Organization"), XSS_ALLOW);
	public static boolean XSS_ALLOW_ORGANIZATIONID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Organization.organizationId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Organization.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PARENTORGANIZATIONID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Organization.parentOrganizationId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Organization.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_REGIONID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Organization.regionId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COUNTRYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Organization.countryId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMMENTS = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portal.model.Organization.comments"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.OrganizationModel"));

	public OrganizationModelImpl() {
	}

	public String getPrimaryKey() {
		return _organizationId;
	}

	public void setPrimaryKey(String pk) {
		setOrganizationId(pk);
	}

	public String getOrganizationId() {
		return GetterUtil.getString(_organizationId);
	}

	public void setOrganizationId(String organizationId) {
		if (((organizationId == null) && (_organizationId != null)) ||
				((organizationId != null) && (_organizationId == null)) ||
				((organizationId != null) && (_organizationId != null) &&
				!organizationId.equals(_organizationId))) {
			if (!XSS_ALLOW_ORGANIZATIONID) {
				organizationId = XSSUtil.strip(organizationId);
			}

			_organizationId = organizationId;
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

	public String getParentOrganizationId() {
		return GetterUtil.getString(_parentOrganizationId);
	}

	public void setParentOrganizationId(String parentOrganizationId) {
		if (((parentOrganizationId == null) && (_parentOrganizationId != null)) ||
				((parentOrganizationId != null) &&
				(_parentOrganizationId == null)) ||
				((parentOrganizationId != null) &&
				(_parentOrganizationId != null) &&
				!parentOrganizationId.equals(_parentOrganizationId))) {
			if (!XSS_ALLOW_PARENTORGANIZATIONID) {
				parentOrganizationId = XSSUtil.strip(parentOrganizationId);
			}

			_parentOrganizationId = parentOrganizationId;
		}
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if (((name == null) && (_name != null)) ||
				((name != null) && (_name == null)) ||
				((name != null) && (_name != null) && !name.equals(_name))) {
			if (!XSS_ALLOW_NAME) {
				name = XSSUtil.strip(name);
			}

			_name = name;
		}
	}

	public boolean getRecursable() {
		return _recursable;
	}

	public boolean isRecursable() {
		return _recursable;
	}

	public void setRecursable(boolean recursable) {
		if (recursable != _recursable) {
			_recursable = recursable;
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

	public int getStatusId() {
		return _statusId;
	}

	public void setStatusId(int statusId) {
		if (statusId != _statusId) {
			_statusId = statusId;
		}
	}

	public String getComments() {
		return GetterUtil.getString(_comments);
	}

	public void setComments(String comments) {
		if (((comments == null) && (_comments != null)) ||
				((comments != null) && (_comments == null)) ||
				((comments != null) && (_comments != null) &&
				!comments.equals(_comments))) {
			if (!XSS_ALLOW_COMMENTS) {
				comments = XSSUtil.strip(comments);
			}

			_comments = comments;
		}
	}

	public Object clone() {
		OrganizationImpl clone = new OrganizationImpl();
		clone.setOrganizationId(getOrganizationId());
		clone.setCompanyId(getCompanyId());
		clone.setParentOrganizationId(getParentOrganizationId());
		clone.setName(getName());
		clone.setRecursable(getRecursable());
		clone.setRegionId(getRegionId());
		clone.setCountryId(getCountryId());
		clone.setStatusId(getStatusId());
		clone.setComments(getComments());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		OrganizationImpl organization = (OrganizationImpl)obj;
		int value = 0;
		value = getName().compareTo(organization.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		OrganizationImpl organization = null;

		try {
			organization = (OrganizationImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = organization.getPrimaryKey();

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

	private String _organizationId;
	private String _companyId;
	private String _parentOrganizationId;
	private String _name;
	private boolean _recursable;
	private String _regionId;
	private String _countryId;
	private int _statusId;
	private String _comments;
}