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

package com.liferay.portlet.softwarecatalog.model;

/**
 * <p>
 * This class is a wrapper for {@link SCProductEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductEntry
 * @generated
 */
public class SCProductEntryWrapper implements SCProductEntry {
	public SCProductEntryWrapper(SCProductEntry scProductEntry) {
		_scProductEntry = scProductEntry;
	}

	public long getPrimaryKey() {
		return _scProductEntry.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_scProductEntry.setPrimaryKey(pk);
	}

	public long getProductEntryId() {
		return _scProductEntry.getProductEntryId();
	}

	public void setProductEntryId(long productEntryId) {
		_scProductEntry.setProductEntryId(productEntryId);
	}

	public long getGroupId() {
		return _scProductEntry.getGroupId();
	}

	public void setGroupId(long groupId) {
		_scProductEntry.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _scProductEntry.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_scProductEntry.setCompanyId(companyId);
	}

	public long getUserId() {
		return _scProductEntry.getUserId();
	}

	public void setUserId(long userId) {
		_scProductEntry.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntry.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_scProductEntry.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _scProductEntry.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_scProductEntry.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _scProductEntry.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_scProductEntry.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _scProductEntry.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_scProductEntry.setModifiedDate(modifiedDate);
	}

	public java.lang.String getName() {
		return _scProductEntry.getName();
	}

	public void setName(java.lang.String name) {
		_scProductEntry.setName(name);
	}

	public java.lang.String getType() {
		return _scProductEntry.getType();
	}

	public void setType(java.lang.String type) {
		_scProductEntry.setType(type);
	}

	public java.lang.String getTags() {
		return _scProductEntry.getTags();
	}

	public void setTags(java.lang.String tags) {
		_scProductEntry.setTags(tags);
	}

	public java.lang.String getShortDescription() {
		return _scProductEntry.getShortDescription();
	}

	public void setShortDescription(java.lang.String shortDescription) {
		_scProductEntry.setShortDescription(shortDescription);
	}

	public java.lang.String getLongDescription() {
		return _scProductEntry.getLongDescription();
	}

	public void setLongDescription(java.lang.String longDescription) {
		_scProductEntry.setLongDescription(longDescription);
	}

	public java.lang.String getPageURL() {
		return _scProductEntry.getPageURL();
	}

	public void setPageURL(java.lang.String pageURL) {
		_scProductEntry.setPageURL(pageURL);
	}

	public java.lang.String getAuthor() {
		return _scProductEntry.getAuthor();
	}

	public void setAuthor(java.lang.String author) {
		_scProductEntry.setAuthor(author);
	}

	public java.lang.String getRepoGroupId() {
		return _scProductEntry.getRepoGroupId();
	}

	public void setRepoGroupId(java.lang.String repoGroupId) {
		_scProductEntry.setRepoGroupId(repoGroupId);
	}

	public java.lang.String getRepoArtifactId() {
		return _scProductEntry.getRepoArtifactId();
	}

	public void setRepoArtifactId(java.lang.String repoArtifactId) {
		_scProductEntry.setRepoArtifactId(repoArtifactId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry toEscapedModel() {
		return _scProductEntry.toEscapedModel();
	}

	public boolean isNew() {
		return _scProductEntry.isNew();
	}

	public void setNew(boolean n) {
		_scProductEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _scProductEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_scProductEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _scProductEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_scProductEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _scProductEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scProductEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scProductEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _scProductEntry.clone();
	}

	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry) {
		return _scProductEntry.compareTo(scProductEntry);
	}

	public int hashCode() {
		return _scProductEntry.hashCode();
	}

	public java.lang.String toString() {
		return _scProductEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _scProductEntry.toXmlString();
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion getLatestVersion()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntry.getLatestVersion();
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntry.getLicenses();
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> getScreenshots()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductEntry.getScreenshots();
	}

	public SCProductEntry getWrappedSCProductEntry() {
		return _scProductEntry;
	}

	private SCProductEntry _scProductEntry;
}