/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntrySoap implements Serializable {
	public static DLFileEntrySoap toSoapModel(DLFileEntry model) {
		DLFileEntrySoap soapModel = new DLFileEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setVersionUserId(model.getVersionUserId());
		soapModel.setVersionUserName(model.getVersionUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setFolderId(model.getFolderId());
		soapModel.setName(model.getName());
		soapModel.setExtension(model.getExtension());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setExtraSettings(model.getExtraSettings());
		soapModel.setVersion(model.getVersion());
		soapModel.setSize(model.getSize());
		soapModel.setReadCount(model.getReadCount());

		return soapModel;
	}

	public static DLFileEntrySoap[] toSoapModels(DLFileEntry[] models) {
		DLFileEntrySoap[] soapModels = new DLFileEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DLFileEntrySoap[][] toSoapModels(DLFileEntry[][] models) {
		DLFileEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DLFileEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new DLFileEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DLFileEntrySoap[] toSoapModels(List<DLFileEntry> models) {
		List<DLFileEntrySoap> soapModels = new ArrayList<DLFileEntrySoap>(models.size());

		for (DLFileEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DLFileEntrySoap[soapModels.size()]);
	}

	public DLFileEntrySoap() {
	}

	public long getPrimaryKey() {
		return _fileEntryId;
	}

	public void setPrimaryKey(long pk) {
		setFileEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
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
		return _userName;
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

	public String getVersionUserName() {
		return _versionUserName;
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
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getExtension() {
		return _extension;
	}

	public void setExtension(String extension) {
		_extension = extension;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getExtraSettings() {
		return _extraSettings;
	}

	public void setExtraSettings(String extraSettings) {
		_extraSettings = extraSettings;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	public long getSize() {
		return _size;
	}

	public void setSize(long size) {
		_size = size;
	}

	public int getReadCount() {
		return _readCount;
	}

	public void setReadCount(int readCount) {
		_readCount = readCount;
	}

	private String _uuid;
	private long _fileEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private long _versionUserId;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _folderId;
	private String _name;
	private String _extension;
	private String _title;
	private String _description;
	private String _extraSettings;
	private String _version;
	private long _size;
	private int _readCount;
}