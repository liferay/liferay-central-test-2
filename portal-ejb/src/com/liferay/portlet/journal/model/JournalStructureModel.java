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

package com.liferay.portlet.journal.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.journal.service.persistence.JournalStructurePK;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="JournalStructureModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalStructureModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalStructure"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalStructure.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_STRUCTUREID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalStructure.structureId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_GROUPID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalStructure.groupId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalStructure.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalStructure.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalStructure.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalStructure.description"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_XSD = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalStructure.xsd"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.journal.model.JournalStructureModel"));

	public JournalStructureModel() {
	}

	public JournalStructurePK getPrimaryKey() {
		return new JournalStructurePK(_companyId, _structureId);
	}

	public void setPrimaryKey(JournalStructurePK pk) {
		setCompanyId(pk.companyId);
		setStructureId(pk.structureId);
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

	public String getStructureId() {
		return GetterUtil.getString(_structureId);
	}

	public void setStructureId(String structureId) {
		if (((structureId == null) && (_structureId != null)) ||
				((structureId != null) && (_structureId == null)) ||
				((structureId != null) && (_structureId != null) &&
				!structureId.equals(_structureId))) {
			if (!XSS_ALLOW_STRUCTUREID) {
				structureId = XSSUtil.strip(structureId);
			}

			_structureId = structureId;
			setModified(true);
		}
	}

	public String getGroupId() {
		return GetterUtil.getString(_groupId);
	}

	public void setGroupId(String groupId) {
		if (((groupId == null) && (_groupId != null)) ||
				((groupId != null) && (_groupId == null)) ||
				((groupId != null) && (_groupId != null) &&
				!groupId.equals(_groupId))) {
			if (!XSS_ALLOW_GROUPID) {
				groupId = XSSUtil.strip(groupId);
			}

			_groupId = groupId;
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
			setModified(true);
		}
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		if (((description == null) && (_description != null)) ||
				((description != null) && (_description == null)) ||
				((description != null) && (_description != null) &&
				!description.equals(_description))) {
			if (!XSS_ALLOW_DESCRIPTION) {
				description = XSSUtil.strip(description);
			}

			_description = description;
			setModified(true);
		}
	}

	public String getXsd() {
		return GetterUtil.getString(_xsd);
	}

	public void setXsd(String xsd) {
		if (((xsd == null) && (_xsd != null)) ||
				((xsd != null) && (_xsd == null)) ||
				((xsd != null) && (_xsd != null) && !xsd.equals(_xsd))) {
			if (!XSS_ALLOW_XSD) {
				xsd = XSSUtil.strip(xsd);
			}

			_xsd = xsd;
			setModified(true);
		}
	}

	public Object clone() {
		JournalStructure clone = new JournalStructure();
		clone.setCompanyId(getCompanyId());
		clone.setStructureId(getStructureId());
		clone.setGroupId(getGroupId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setXsd(getXsd());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		JournalStructure journalStructure = (JournalStructure)obj;
		int value = 0;
		value = getStructureId().compareTo(journalStructure.getStructureId());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		JournalStructure journalStructure = null;

		try {
			journalStructure = (JournalStructure)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		JournalStructurePK pk = journalStructure.getPrimaryKey();

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
	private String _structureId;
	private String _groupId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private String _xsd;
}