/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileShortcutSoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.StringBuilder;
import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="DLFileShortcutModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>DLFileShortcut</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.documentlibrary.model.DLFileShortcut
 * @see com.liferay.portlet.documentlibrary.model.DLFileShortcutModel
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl
 *
 */
public class DLFileShortcutModelImpl extends BaseModelImpl<DLFileShortcut> {
	public static final String TABLE_NAME = "DLFileShortcut";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			

			{ "fileShortcutId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "folderId", new Integer(Types.BIGINT) },
			

			{ "toFolderId", new Integer(Types.BIGINT) },
			

			{ "toName", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table DLFileShortcut (uuid_ VARCHAR(75) null,fileShortcutId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,folderId LONG,toFolderId LONG,toName VARCHAR(255) null)";
	public static final String TABLE_SQL_DROP = "drop table DLFileShortcut";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.documentlibrary.model.DLFileShortcut"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.documentlibrary.model.DLFileShortcut"),
			true);

	public static DLFileShortcut toModel(DLFileShortcutSoap soapModel) {
		DLFileShortcut model = new DLFileShortcutImpl();

		model.setUuid(soapModel.getUuid());
		model.setFileShortcutId(soapModel.getFileShortcutId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setFolderId(soapModel.getFolderId());
		model.setToFolderId(soapModel.getToFolderId());
		model.setToName(soapModel.getToName());

		return model;
	}

	public static List<DLFileShortcut> toModels(DLFileShortcutSoap[] soapModels) {
		List<DLFileShortcut> models = new ArrayList<DLFileShortcut>(soapModels.length);

		for (DLFileShortcutSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.documentlibrary.model.DLFileShortcut"));

	public DLFileShortcutModelImpl() {
	}

	public long getPrimaryKey() {
		return _fileShortcutId;
	}

	public void setPrimaryKey(long pk) {
		setFileShortcutId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_fileShortcutId);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		_uuid = uuid;

		if (_originalUuid == null) {
			_originalUuid = uuid;
		}
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	public long getFileShortcutId() {
		return _fileShortcutId;
	}

	public void setFileShortcutId(long fileShortcutId) {
		_fileShortcutId = fileShortcutId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = groupId;
		}
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	public long getToFolderId() {
		return _toFolderId;
	}

	public void setToFolderId(long toFolderId) {
		_toFolderId = toFolderId;
	}

	public String getToName() {
		return GetterUtil.getString(_toName);
	}

	public void setToName(String toName) {
		_toName = toName;
	}

	public DLFileShortcut toEscapedModel() {
		if (isEscapedModel()) {
			return (DLFileShortcut)this;
		}
		else {
			DLFileShortcut model = new DLFileShortcutImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setFileShortcutId(getFileShortcutId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setFolderId(getFolderId());
			model.setToFolderId(getToFolderId());
			model.setToName(HtmlUtil.escape(getToName()));

			model = (DLFileShortcut)Proxy.newProxyInstance(DLFileShortcut.class.getClassLoader(),
					new Class[] { DLFileShortcut.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(DLFileShortcut.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		DLFileShortcutImpl clone = new DLFileShortcutImpl();

		clone.setUuid(getUuid());
		clone.setFileShortcutId(getFileShortcutId());
		clone.setGroupId(getGroupId());
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

	public int compareTo(DLFileShortcut dlFileShortcut) {
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

		DLFileShortcut dlFileShortcut = null;

		try {
			dlFileShortcut = (DLFileShortcut)obj;
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

	public String toHtmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class=\"lfr-table\">\n");

		sb.append("<tr><td align=\"right\" valign=\"top\"><b>uuid</b></td><td>" +
			getUuid() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>fileShortcutId</b></td><td>" +
			getFileShortcutId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>groupId</b></td><td>" +
			getGroupId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>companyId</b></td><td>" +
			getCompanyId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>userId</b></td><td>" +
			getUserId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>userName</b></td><td>" +
			getUserName() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>createDate</b></td><td>" +
			getCreateDate() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>modifiedDate</b></td><td>" +
			getModifiedDate() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>folderId</b></td><td>" +
			getFolderId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>toFolderId</b></td><td>" +
			getToFolderId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>toName</b></td><td>" +
			getToName() + "</td></tr>\n");

		sb.append("</table>");

		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("com.liferay.portlet.documentlibrary.model.DLFileShortcut (");

		sb.append("uuid: " + getUuid() + ", ");
		sb.append("fileShortcutId: " + getFileShortcutId() + ", ");
		sb.append("groupId: " + getGroupId() + ", ");
		sb.append("companyId: " + getCompanyId() + ", ");
		sb.append("userId: " + getUserId() + ", ");
		sb.append("userName: " + getUserName() + ", ");
		sb.append("createDate: " + getCreateDate() + ", ");
		sb.append("modifiedDate: " + getModifiedDate() + ", ");
		sb.append("folderId: " + getFolderId() + ", ");
		sb.append("toFolderId: " + getToFolderId() + ", ");
		sb.append("toName: " + getToName() + ", ");

		sb.append(")");

		return sb.toString();
	}

	private String _uuid;
	private String _originalUuid;
	private long _fileShortcutId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _folderId;
	private long _toFolderId;
	private String _toName;
	private transient ExpandoBridge _expandoBridge;
}