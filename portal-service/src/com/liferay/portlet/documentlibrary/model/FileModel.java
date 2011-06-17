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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class FileModel implements Serializable {

	public long[] getAssetCategoryIds() {
		return _assetCategoryIds;
	}

	public String[] getAssetCategoryNames() {
		return _assetCategoryNames;
	}

	public String[] getAssetTagNames() {
		return _assetTagNames;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public String getFileName() {
		return _fileName;
	}

	public long getGroupId() {
		return _groupId;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public String getPortletId() {
		return _portletId;
	}

	public String getProperties() {
		return _properties;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUserName() {
		return _userName;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setAssetCategoryIds(long[] assetCategoryIds) {
		_assetCategoryIds = assetCategoryIds;
	}

	public void setAssetCategoryNames(String[] assetCategoryNames) {
		_assetCategoryNames = assetCategoryNames;
	}

	public void setAssetTagNames(String[] assetTagNames) {
		_assetTagNames = assetTagNames;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public void setFileName(String fileName) {
		_fileName = fileName;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setProperties(String properties) {
		_properties = properties;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{assetCategoryIds=");
		sb.append(_assetCategoryIds);
		sb.append(", assetCategoryNames=");
		sb.append(_assetCategoryNames);
		sb.append(", assetTagNames=");
		sb.append(_assetTagNames);
		sb.append(", companyId=");
		sb.append(_companyId);
		sb.append(", createDate=");
		sb.append(_createDate);
		sb.append(", fileEntryId=");
		sb.append(_fileEntryId);
		sb.append(", fileName=");
		sb.append(_fileName);
		sb.append(", groupId=");
		sb.append(_groupId);
		sb.append(", modifiedDate=");
		sb.append(_modifiedDate);
		sb.append(", portletId=");
		sb.append(_portletId);
		sb.append(", properties=");
		sb.append(_properties);
		sb.append(", repositoryId=");
		sb.append(_repositoryId);
		sb.append(", userId=");
		sb.append(_userId);
		sb.append(", userName=");
		sb.append(_userName);
		sb.append(", userUuid=");
		sb.append(_userUuid);
		sb.append("}");

		return sb.toString();
	}

	private long[] _assetCategoryIds;
	private String[] _assetCategoryNames;
	private String[] _assetTagNames;
	private long _companyId;
	private Date _createDate;
	private long _fileEntryId;
	private String _fileName;
	private long _groupId;
	private Date _modifiedDate;
	private String _portletId;
	private String _properties;
	private long _repositoryId;
	private long _userId;
	private String _userName;
	private String _userUuid;

}