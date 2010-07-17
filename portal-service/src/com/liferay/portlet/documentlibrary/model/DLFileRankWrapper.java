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

package com.liferay.portlet.documentlibrary.model;

/**
 * <p>
 * This class is a wrapper for {@link DLFileRank}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileRank
 * @generated
 */
public class DLFileRankWrapper implements DLFileRank {
	public DLFileRankWrapper(DLFileRank dlFileRank) {
		_dlFileRank = dlFileRank;
	}

	public long getPrimaryKey() {
		return _dlFileRank.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_dlFileRank.setPrimaryKey(pk);
	}

	public long getFileRankId() {
		return _dlFileRank.getFileRankId();
	}

	public void setFileRankId(long fileRankId) {
		_dlFileRank.setFileRankId(fileRankId);
	}

	public long getGroupId() {
		return _dlFileRank.getGroupId();
	}

	public void setGroupId(long groupId) {
		_dlFileRank.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _dlFileRank.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_dlFileRank.setCompanyId(companyId);
	}

	public long getUserId() {
		return _dlFileRank.getUserId();
	}

	public void setUserId(long userId) {
		_dlFileRank.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRank.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_dlFileRank.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _dlFileRank.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_dlFileRank.setCreateDate(createDate);
	}

	public long getFolderId() {
		return _dlFileRank.getFolderId();
	}

	public void setFolderId(long folderId) {
		_dlFileRank.setFolderId(folderId);
	}

	public java.lang.String getName() {
		return _dlFileRank.getName();
	}

	public void setName(java.lang.String name) {
		_dlFileRank.setName(name);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank toEscapedModel() {
		return _dlFileRank.toEscapedModel();
	}

	public boolean isNew() {
		return _dlFileRank.isNew();
	}

	public void setNew(boolean n) {
		_dlFileRank.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFileRank.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFileRank.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFileRank.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_dlFileRank.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileRank.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileRank.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileRank.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _dlFileRank.clone();
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank) {
		return _dlFileRank.compareTo(dlFileRank);
	}

	public int hashCode() {
		return _dlFileRank.hashCode();
	}

	public java.lang.String toString() {
		return _dlFileRank.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileRank.toXmlString();
	}

	public DLFileRank getWrappedDLFileRank() {
		return _dlFileRank;
	}

	private DLFileRank _dlFileRank;
}