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

import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPK;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="DLFileEntryModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>DLFileEntry</code> table in the
 * database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.documentlibrary.service.model.DLFileEntry
 * @see com.liferay.portlet.documentlibrary.service.model.DLFileEntryModel
 * @see com.liferay.portlet.documentlibrary.service.model.impl.DLFileEntryImpl
 *
 */
public class DLFileEntryModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "DLFileEntry";
	public static Object[][] TABLE_COLUMNS = {
			{ "folderId", new Integer(Types.VARCHAR) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "companyId", new Integer(Types.VARCHAR) },
			{ "userId", new Integer(Types.VARCHAR) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "versionUserId", new Integer(Types.VARCHAR) },
			{ "versionUserName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "title", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) },
			{ "version", new Integer(Types.DOUBLE) },
			{ "size_", new Integer(Types.INTEGER) },
			{ "readCount", new Integer(Types.INTEGER) },
			{ "extraSettings", new Integer(Types.CLOB) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_FOLDERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.folderId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_VERSIONUSERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.versionUserId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_VERSIONUSERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.versionUserName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TITLE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.title"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.description"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_EXTRASETTINGS = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileEntry.extraSettings"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.documentlibrary.model.DLFileEntryModel"));

	public DLFileEntryModelImpl() {
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

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		if (version != _version) {
			_version = version;
		}
	}

	public int getSize() {
		return _size;
	}

	public void setSize(int size) {
		if (size != _size) {
			_size = size;
		}
	}

	public int getReadCount() {
		return _readCount;
	}

	public void setReadCount(int readCount) {
		if (readCount != _readCount) {
			_readCount = readCount;
		}
	}

	public String getExtraSettings() {
		return GetterUtil.getString(_extraSettings);
	}

	public void setExtraSettings(String extraSettings) {
		if (((extraSettings == null) && (_extraSettings != null)) ||
				((extraSettings != null) && (_extraSettings == null)) ||
				((extraSettings != null) && (_extraSettings != null) &&
				!extraSettings.equals(_extraSettings))) {
			if (!XSS_ALLOW_EXTRASETTINGS) {
				extraSettings = XSSUtil.strip(extraSettings);
			}

			_extraSettings = extraSettings;
		}
	}

	public Object clone() {
		DLFileEntryImpl clone = new DLFileEntryImpl();
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
		clone.setExtraSettings(getExtraSettings());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		DLFileEntryImpl dlFileEntry = (DLFileEntryImpl)obj;
		int value = 0;
		value = getFolderId().compareTo(dlFileEntry.getFolderId());

		if (value != 0) {
			return value;
		}

		value = getName().compareTo(dlFileEntry.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		DLFileEntryImpl dlFileEntry = null;

		try {
			dlFileEntry = (DLFileEntryImpl)obj;
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
	private String _extraSettings;
}