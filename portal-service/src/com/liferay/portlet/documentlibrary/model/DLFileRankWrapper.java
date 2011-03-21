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

	public Class<?> getModelClass() {
		return DLFileRank.class;
	}

	public String getModelClassName() {
		return DLFileRank.class.getName();
	}

	/**
	* Gets the primary key of this d l file rank.
	*
	* @return the primary key of this d l file rank
	*/
	public long getPrimaryKey() {
		return _dlFileRank.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d l file rank
	*
	* @param pk the primary key of this d l file rank
	*/
	public void setPrimaryKey(long pk) {
		_dlFileRank.setPrimaryKey(pk);
	}

	/**
	* Gets the file rank ID of this d l file rank.
	*
	* @return the file rank ID of this d l file rank
	*/
	public long getFileRankId() {
		return _dlFileRank.getFileRankId();
	}

	/**
	* Sets the file rank ID of this d l file rank.
	*
	* @param fileRankId the file rank ID of this d l file rank
	*/
	public void setFileRankId(long fileRankId) {
		_dlFileRank.setFileRankId(fileRankId);
	}

	/**
	* Gets the group ID of this d l file rank.
	*
	* @return the group ID of this d l file rank
	*/
	public long getGroupId() {
		return _dlFileRank.getGroupId();
	}

	/**
	* Sets the group ID of this d l file rank.
	*
	* @param groupId the group ID of this d l file rank
	*/
	public void setGroupId(long groupId) {
		_dlFileRank.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this d l file rank.
	*
	* @return the company ID of this d l file rank
	*/
	public long getCompanyId() {
		return _dlFileRank.getCompanyId();
	}

	/**
	* Sets the company ID of this d l file rank.
	*
	* @param companyId the company ID of this d l file rank
	*/
	public void setCompanyId(long companyId) {
		_dlFileRank.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this d l file rank.
	*
	* @return the user ID of this d l file rank
	*/
	public long getUserId() {
		return _dlFileRank.getUserId();
	}

	/**
	* Sets the user ID of this d l file rank.
	*
	* @param userId the user ID of this d l file rank
	*/
	public void setUserId(long userId) {
		_dlFileRank.setUserId(userId);
	}

	/**
	* Gets the user uuid of this d l file rank.
	*
	* @return the user uuid of this d l file rank
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRank.getUserUuid();
	}

	/**
	* Sets the user uuid of this d l file rank.
	*
	* @param userUuid the user uuid of this d l file rank
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_dlFileRank.setUserUuid(userUuid);
	}

	/**
	* Gets the create date of this d l file rank.
	*
	* @return the create date of this d l file rank
	*/
	public java.util.Date getCreateDate() {
		return _dlFileRank.getCreateDate();
	}

	/**
	* Sets the create date of this d l file rank.
	*
	* @param createDate the create date of this d l file rank
	*/
	public void setCreateDate(java.util.Date createDate) {
		_dlFileRank.setCreateDate(createDate);
	}

	/**
	* Gets the file entry ID of this d l file rank.
	*
	* @return the file entry ID of this d l file rank
	*/
	public long getFileEntryId() {
		return _dlFileRank.getFileEntryId();
	}

	/**
	* Sets the file entry ID of this d l file rank.
	*
	* @param fileEntryId the file entry ID of this d l file rank
	*/
	public void setFileEntryId(long fileEntryId) {
		_dlFileRank.setFileEntryId(fileEntryId);
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
		return new DLFileRankWrapper((DLFileRank)_dlFileRank.clone());
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank) {
		return _dlFileRank.compareTo(dlFileRank);
	}

	public int hashCode() {
		return _dlFileRank.hashCode();
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank toEscapedModel() {
		return new DLFileRankWrapper(_dlFileRank.toEscapedModel());
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