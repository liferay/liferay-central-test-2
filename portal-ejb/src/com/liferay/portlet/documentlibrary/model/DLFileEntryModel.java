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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPK;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="DLFileEntryModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileEntryModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_FOLDERID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.folderId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_VERSIONUSERID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.versionUserId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_VERSIONUSERNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.versionUserName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TITLE = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.title"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.description"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.documentlibrary.model.DLFileEntryModel"));

	public DLFileEntryModel() {
	}

	public DLFileEntryPK getPrimaryKey() {
		return new DLFileEntryPK(_folderId, _name);
	}

	public void setPrimaryKey(DLFileEntryPK pk) {
		setFolderId(pk.folderId);
		setName(pk.name);
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

	public String getVersionUserId() {
		return GetterUtil.getString(_versionUserId);
	}

	public void setVersionUserId(String versionUserId) {
		if (((versionUserId == null) && (_versionUserId != null)) ||
				((versionUserId != null) && (_versionUserId == null)) ||
				((versionUserId != null) && (_versionUserId != null) &&
				!versionUserId.equals(_versionUserId))) {
			if (!XSS_ALLOW_VERSIONUSERID) {
				versionUserId = XSSUtil.strip(versionUserId);
			}

			_versionUserId = versionUserId;
			setModified(true);
		}
	}

	public String getVersionUserName() {
		return GetterUtil.getString(_versionUserName);
	}

	public void setVersionUserName(String versionUserName) {
		if (((versionUserName == null) && (_versionUserName != null)) ||
				((versionUserName != null) && (_versionUserName == null)) ||
				((versionUserName != null) && (_versionUserName != null) &&
				!versionUserName.equals(_versionUserName))) {
			if (!XSS_ALLOW_VERSIONUSERNAME) {
				versionUserName = XSSUtil.strip(versionUserName);
			}

			_versionUserName = versionUserName;
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

	public String getTitle() {
		return GetterUtil.getString(_title);
	}

	public void setTitle(String title) {
		if (((title == null) && (_title != null)) ||
				((title != null) && (_title == null)) ||
				((title != null) && (_title != null) && !title.equals(_title))) {
			if (!XSS_ALLOW_TITLE) {
				title = XSSUtil.strip(title);
			}

			_title = title;
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

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		if (version != _version) {
			_version = version;
			setModified(true);
		}
	}

	public int getSize() {
		return _size;
	}

	public void setSize(int size) {
		if (size != _size) {
			_size = size;
			setModified(true);
		}
	}

	public int getReadCount() {
		return _readCount;
	}

	public void setReadCount(int readCount) {
		if (readCount != _readCount) {
			_readCount = readCount;
			setModified(true);
		}
	}

	public Object clone() {
		DLFileEntry clone = new DLFileEntry();
		clone.setFolderId(getFolderId());
		clone.setName(getName());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setVersionUserId(getVersionUserId());
		clone.setVersionUserName(getVersionUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setTitle(getTitle());
		clone.setDescription(getDescription());
		clone.setVersion(getVersion());
		clone.setSize(getSize());
		clone.setReadCount(getReadCount());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		DLFileEntry dlFileEntry = (DLFileEntry)obj;
		DLFileEntryPK pk = dlFileEntry.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		DLFileEntry dlFileEntry = null;

		try {
			dlFileEntry = (DLFileEntry)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		DLFileEntryPK pk = dlFileEntry.getPrimaryKey();

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
	private String _name;
	private String _companyId;
	private String _userId;
	private String _userName;
	private String _versionUserId;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _description;
	private double _version;
	private int _size;
	private int _readCount;
}