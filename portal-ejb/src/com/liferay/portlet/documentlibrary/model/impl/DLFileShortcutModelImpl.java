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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="DLFileShortcutModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileShortcutModelImpl extends BaseModelImpl {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileShortcut"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileShortcut.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileShortcut.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileShortcut.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_FOLDERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileShortcut.folderId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TOFOLDERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileShortcut.toFolderId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TONAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.documentlibrary.model.DLFileShortcut.toName"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.documentlibrary.model.DLFileShortcutModel"));

	public DLFileShortcutModelImpl() {
	}

	public long getPrimaryKey() {
		return _fileShortcutId;
	}

	public void setPrimaryKey(long pk) {
		setFileShortcutId(pk);
	}

	public long getFileShortcutId() {
		return _fileShortcutId;
	}

	public void setFileShortcutId(long fileShortcutId) {
		if (fileShortcutId != _fileShortcutId) {
			_fileShortcutId = fileShortcutId;
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

	public String getToFolderId() {
		return GetterUtil.getString(_toFolderId);
	}

	public void setToFolderId(String toFolderId) {
		if (((toFolderId == null) && (_toFolderId != null)) ||
				((toFolderId != null) && (_toFolderId == null)) ||
				((toFolderId != null) && (_toFolderId != null) &&
				!toFolderId.equals(_toFolderId))) {
			if (!XSS_ALLOW_TOFOLDERID) {
				toFolderId = XSSUtil.strip(toFolderId);
			}

			_toFolderId = toFolderId;
		}
	}

	public String getToName() {
		return GetterUtil.getString(_toName);
	}

	public void setToName(String toName) {
		if (((toName == null) && (_toName != null)) ||
				((toName != null) && (_toName == null)) ||
				((toName != null) && (_toName != null) &&
				!toName.equals(_toName))) {
			if (!XSS_ALLOW_TONAME) {
				toName = XSSUtil.strip(toName);
			}

			_toName = toName;
		}
	}

	public Object clone() {
		DLFileShortcutImpl clone = new DLFileShortcutImpl();
		clone.setFileShortcutId(getFileShortcutId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setFolderId(getFolderId());
		clone.setToFolderId(getToFolderId());
		clone.setToName(getToName());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		DLFileShortcutImpl dlFileShortcut = (DLFileShortcutImpl)obj;
		long pk = dlFileShortcut.getPrimaryKey();

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

		DLFileShortcutImpl dlFileShortcut = null;

		try {
			dlFileShortcut = (DLFileShortcutImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = dlFileShortcut.getPrimaryKey();

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

	private long _fileShortcutId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _folderId;
	private String _toFolderId;
	private String _toName;
}