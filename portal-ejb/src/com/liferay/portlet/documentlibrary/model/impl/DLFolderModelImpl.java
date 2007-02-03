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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="DLFolderModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFolderModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "DLFolder";
	public static Object[][] TABLE_COLUMNS = {
			{ "folderId", new Integer(Types.VARCHAR) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.VARCHAR) },
			{ "userId", new Integer(Types.VARCHAR) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.VARCHAR) },
			{ "modifiedDate", new Integer(Types.VARCHAR) },
			{ "parentFolderId", new Integer(Types.VARCHAR) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) },
			{ "lastPostDate", new Integer(Types.VARCHAR) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFolder"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_FOLDERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFolder.folderId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFolder.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFolder.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFolder.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PARENTFOLDERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFolder.parentFolderId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFolder.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFolder.description"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.documentlibrary.model.DLFolderModel"));

	public DLFolderModelImpl() {
	}

	public String getPrimaryKey() {
		return _folderId;
	}

	public void setPrimaryKey(String pk) {
		setFolderId(pk);
	}

	public String getFolderId() {
		return GetterUtil.getString(_folderId);
	}

	public void setFolderId(String folderId) {
		if (((folderId == null) && (_folderId != null)) ||
				((folderId != null) && (_folderId == null)) ||
				((folderId != null) && (_folderId != null) &&
				!folderId.equals(_folderId))) {
			if (!XSS_ALLOW_FOLDERID) {
				folderId = XSSUtil.strip(folderId);
			}

			_folderId = folderId;
		}
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;
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

	public String getParentFolderId() {
		return GetterUtil.getString(_parentFolderId);
	}

	public void setParentFolderId(String parentFolderId) {
		if (((parentFolderId == null) && (_parentFolderId != null)) ||
				((parentFolderId != null) && (_parentFolderId == null)) ||
				((parentFolderId != null) && (_parentFolderId != null) &&
				!parentFolderId.equals(_parentFolderId))) {
			if (!XSS_ALLOW_PARENTFOLDERID) {
				parentFolderId = XSSUtil.strip(parentFolderId);
			}

			_parentFolderId = parentFolderId;
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
		}
	}

	public Date getLastPostDate() {
		return _lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		if (((lastPostDate == null) && (_lastPostDate != null)) ||
				((lastPostDate != null) && (_lastPostDate == null)) ||
				((lastPostDate != null) && (_lastPostDate != null) &&
				!lastPostDate.equals(_lastPostDate))) {
			_lastPostDate = lastPostDate;
		}
	}

	public Object clone() {
		DLFolderImpl clone = new DLFolderImpl();
		clone.setFolderId(getFolderId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setParentFolderId(getParentFolderId());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setLastPostDate(getLastPostDate());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		DLFolderImpl dlFolder = (DLFolderImpl)obj;
		int value = 0;
		value = getParentFolderId().compareTo(dlFolder.getParentFolderId());

		if (value != 0) {
			return value;
		}

		value = getName().toLowerCase().compareTo(dlFolder.getName()
														  .toLowerCase());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		DLFolderImpl dlFolder = null;

		try {
			dlFolder = (DLFolderImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = dlFolder.getPrimaryKey();

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

	private String _folderId;
	private long _groupId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _parentFolderId;
	private String _name;
	private String _description;
	private Date _lastPostDate;
}