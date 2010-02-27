/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntrySoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="DLFileEntryModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the DLFileEntry table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryImpl
 * @see       com.liferay.portlet.documentlibrary.model.DLFileEntry
 * @see       com.liferay.portlet.documentlibrary.model.DLFileEntryModel
 * @generated
 */
public class DLFileEntryModelImpl extends BaseModelImpl<DLFileEntry> {
	public static final String TABLE_NAME = "DLFileEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			{ "fileEntryId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "versionUserId", new Integer(Types.BIGINT) },
			{ "versionUserName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "folderId", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "title", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) },
			{ "version", new Integer(Types.VARCHAR) },
			{ "pendingVersion", new Integer(Types.VARCHAR) },
			{ "size_", new Integer(Types.INTEGER) },
			{ "readCount", new Integer(Types.INTEGER) },
			{ "extraSettings", new Integer(Types.CLOB) }
		};
	public static final String TABLE_SQL_CREATE = "create table DLFileEntry (uuid_ VARCHAR(75) null,fileEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,folderId LONG,name VARCHAR(255) null,title VARCHAR(255) null,description STRING null,version VARCHAR(75) null,pendingVersion VARCHAR(75) null,size_ INTEGER,readCount INTEGER,extraSettings TEXT null)";
	public static final String TABLE_SQL_DROP = "drop table DLFileEntry";
	public static final String ORDER_BY_JPQL = " ORDER BY dlFileEntry.folderId ASC, dlFileEntry.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY DLFileEntry.folderId ASC, DLFileEntry.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.documentlibrary.model.DLFileEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.documentlibrary.model.DLFileEntry"),
			true);

	public static DLFileEntry toModel(DLFileEntrySoap soapModel) {
		DLFileEntry model = new DLFileEntryImpl();

		model.setUuid(soapModel.getUuid());
		model.setFileEntryId(soapModel.getFileEntryId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setVersionUserId(soapModel.getVersionUserId());
		model.setVersionUserName(soapModel.getVersionUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setFolderId(soapModel.getFolderId());
		model.setName(soapModel.getName());
		model.setTitle(soapModel.getTitle());
		model.setDescription(soapModel.getDescription());
		model.setVersion(soapModel.getVersion());
		model.setPendingVersion(soapModel.getPendingVersion());
		model.setSize(soapModel.getSize());
		model.setReadCount(soapModel.getReadCount());
		model.setExtraSettings(soapModel.getExtraSettings());

		return model;
	}

	public static List<DLFileEntry> toModels(DLFileEntrySoap[] soapModels) {
		List<DLFileEntry> models = new ArrayList<DLFileEntry>(soapModels.length);

		for (DLFileEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.documentlibrary.model.DLFileEntry"));

	public DLFileEntryModelImpl() {
	}

	public long getPrimaryKey() {
		return _fileEntryId;
	}

	public void setPrimaryKey(long pk) {
		setFileEntryId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_fileEntryId);
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

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
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

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public long getVersionUserId() {
		return _versionUserId;
	}

	public void setVersionUserId(long versionUserId) {
		_versionUserId = versionUserId;
	}

	public String getVersionUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getVersionUserId(), "uuid",
			_versionUserUuid);
	}

	public void setVersionUserUuid(String versionUserUuid) {
		_versionUserUuid = versionUserUuid;
	}

	public String getVersionUserName() {
		return GetterUtil.getString(_versionUserName);
	}

	public void setVersionUserName(String versionUserName) {
		_versionUserName = versionUserName;
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

		if (!_setOriginalFolderId) {
			_setOriginalFolderId = true;

			_originalFolderId = folderId;
		}
	}

	public long getOriginalFolderId() {
		return _originalFolderId;
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		_name = name;

		if (_originalName == null) {
			_originalName = name;
		}
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	public String getTitle() {
		return GetterUtil.getString(_title);
	}

	public void setTitle(String title) {
		_title = title;

		if (_originalTitle == null) {
			_originalTitle = title;
		}
	}

	public String getOriginalTitle() {
		return GetterUtil.getString(_originalTitle);
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getVersion() {
		return GetterUtil.getString(_version);
	}

	public void setVersion(String version) {
		_version = version;
	}

	public String getPendingVersion() {
		return GetterUtil.getString(_pendingVersion);
	}

	public void setPendingVersion(String pendingVersion) {
		_pendingVersion = pendingVersion;
	}

	public int getSize() {
		return _size;
	}

	public void setSize(int size) {
		_size = size;
	}

	public int getReadCount() {
		return _readCount;
	}

	public void setReadCount(int readCount) {
		_readCount = readCount;
	}

	public String getExtraSettings() {
		return GetterUtil.getString(_extraSettings);
	}

	public void setExtraSettings(String extraSettings) {
		_extraSettings = extraSettings;
	}

	public DLFileEntry toEscapedModel() {
		if (isEscapedModel()) {
			return (DLFileEntry)this;
		}
		else {
			DLFileEntry model = new DLFileEntryImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setFileEntryId(getFileEntryId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setVersionUserId(getVersionUserId());
			model.setVersionUserName(HtmlUtil.escape(getVersionUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setFolderId(getFolderId());
			model.setName(HtmlUtil.escape(getName()));
			model.setTitle(HtmlUtil.escape(getTitle()));
			model.setDescription(HtmlUtil.escape(getDescription()));
			model.setVersion(HtmlUtil.escape(getVersion()));
			model.setPendingVersion(HtmlUtil.escape(getPendingVersion()));
			model.setSize(getSize());
			model.setReadCount(getReadCount());
			model.setExtraSettings(HtmlUtil.escape(getExtraSettings()));

			model = (DLFileEntry)Proxy.newProxyInstance(DLFileEntry.class.getClassLoader(),
					new Class[] { DLFileEntry.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					DLFileEntry.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		DLFileEntryImpl clone = new DLFileEntryImpl();

		clone.setUuid(getUuid());
		clone.setFileEntryId(getFileEntryId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setVersionUserId(getVersionUserId());
		clone.setVersionUserName(getVersionUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setFolderId(getFolderId());
		clone.setName(getName());
		clone.setTitle(getTitle());
		clone.setDescription(getDescription());
		clone.setVersion(getVersion());
		clone.setPendingVersion(getPendingVersion());
		clone.setSize(getSize());
		clone.setReadCount(getReadCount());
		clone.setExtraSettings(getExtraSettings());

		return clone;
	}

	public int compareTo(DLFileEntry dlFileEntry) {
		int value = 0;

		if (getFolderId() < dlFileEntry.getFolderId()) {
			value = -1;
		}
		else if (getFolderId() > dlFileEntry.getFolderId()) {
			value = 1;
		}
		else {
			value = 0;
		}

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

		DLFileEntry dlFileEntry = null;

		try {
			dlFileEntry = (DLFileEntry)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = dlFileEntry.getPrimaryKey();

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

	public String toString() {
		StringBundler sb = new StringBundler(39);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", fileEntryId=");
		sb.append(getFileEntryId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", versionUserId=");
		sb.append(getVersionUserId());
		sb.append(", versionUserName=");
		sb.append(getVersionUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", folderId=");
		sb.append(getFolderId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", version=");
		sb.append(getVersion());
		sb.append(", pendingVersion=");
		sb.append(getPendingVersion());
		sb.append(", size=");
		sb.append(getSize());
		sb.append(", readCount=");
		sb.append(getReadCount());
		sb.append(", extraSettings=");
		sb.append(getExtraSettings());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(61);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.documentlibrary.model.DLFileEntry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fileEntryId</column-name><column-value><![CDATA[");
		sb.append(getFileEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>versionUserId</column-name><column-value><![CDATA[");
		sb.append(getVersionUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>versionUserName</column-name><column-value><![CDATA[");
		sb.append(getVersionUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>folderId</column-name><column-value><![CDATA[");
		sb.append(getFolderId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>version</column-name><column-value><![CDATA[");
		sb.append(getVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>pendingVersion</column-name><column-value><![CDATA[");
		sb.append(getPendingVersion());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>size</column-name><column-value><![CDATA[");
		sb.append(getSize());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>readCount</column-name><column-value><![CDATA[");
		sb.append(getReadCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>extraSettings</column-name><column-value><![CDATA[");
		sb.append(getExtraSettings());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private String _originalUuid;
	private long _fileEntryId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private long _versionUserId;
	private String _versionUserUuid;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _folderId;
	private long _originalFolderId;
	private boolean _setOriginalFolderId;
	private String _name;
	private String _originalName;
	private String _title;
	private String _originalTitle;
	private String _description;
	private String _version;
	private String _pendingVersion;
	private int _size;
	private int _readCount;
	private String _extraSettings;
	private transient ExpandoBridge _expandoBridge;
}