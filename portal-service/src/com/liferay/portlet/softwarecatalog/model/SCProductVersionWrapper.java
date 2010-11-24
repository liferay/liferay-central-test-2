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
 * This class is a wrapper for {@link SCProductVersion}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductVersion
 * @generated
 */
public class SCProductVersionWrapper implements SCProductVersion {
	public SCProductVersionWrapper(SCProductVersion scProductVersion) {
		_scProductVersion = scProductVersion;
	}

	/**
	* Gets the primary key of this s c product version.
	*
	* @return the primary key of this s c product version
	*/
	public long getPrimaryKey() {
		return _scProductVersion.getPrimaryKey();
	}

	/**
	* Sets the primary key of this s c product version
	*
	* @param pk the primary key of this s c product version
	*/
	public void setPrimaryKey(long pk) {
		_scProductVersion.setPrimaryKey(pk);
	}

	/**
	* Gets the product version id of this s c product version.
	*
	* @return the product version id of this s c product version
	*/
	public long getProductVersionId() {
		return _scProductVersion.getProductVersionId();
	}

	/**
	* Sets the product version id of this s c product version.
	*
	* @param productVersionId the product version id of this s c product version
	*/
	public void setProductVersionId(long productVersionId) {
		_scProductVersion.setProductVersionId(productVersionId);
	}

	/**
	* Gets the company id of this s c product version.
	*
	* @return the company id of this s c product version
	*/
	public long getCompanyId() {
		return _scProductVersion.getCompanyId();
	}

	/**
	* Sets the company id of this s c product version.
	*
	* @param companyId the company id of this s c product version
	*/
	public void setCompanyId(long companyId) {
		_scProductVersion.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this s c product version.
	*
	* @return the user id of this s c product version
	*/
	public long getUserId() {
		return _scProductVersion.getUserId();
	}

	/**
	* Sets the user id of this s c product version.
	*
	* @param userId the user id of this s c product version
	*/
	public void setUserId(long userId) {
		_scProductVersion.setUserId(userId);
	}

	/**
	* Gets the user uuid of this s c product version.
	*
	* @return the user uuid of this s c product version
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersion.getUserUuid();
	}

	/**
	* Sets the user uuid of this s c product version.
	*
	* @param userUuid the user uuid of this s c product version
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_scProductVersion.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this s c product version.
	*
	* @return the user name of this s c product version
	*/
	public java.lang.String getUserName() {
		return _scProductVersion.getUserName();
	}

	/**
	* Sets the user name of this s c product version.
	*
	* @param userName the user name of this s c product version
	*/
	public void setUserName(java.lang.String userName) {
		_scProductVersion.setUserName(userName);
	}

	/**
	* Gets the create date of this s c product version.
	*
	* @return the create date of this s c product version
	*/
	public java.util.Date getCreateDate() {
		return _scProductVersion.getCreateDate();
	}

	/**
	* Sets the create date of this s c product version.
	*
	* @param createDate the create date of this s c product version
	*/
	public void setCreateDate(java.util.Date createDate) {
		_scProductVersion.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this s c product version.
	*
	* @return the modified date of this s c product version
	*/
	public java.util.Date getModifiedDate() {
		return _scProductVersion.getModifiedDate();
	}

	/**
	* Sets the modified date of this s c product version.
	*
	* @param modifiedDate the modified date of this s c product version
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_scProductVersion.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the product entry id of this s c product version.
	*
	* @return the product entry id of this s c product version
	*/
	public long getProductEntryId() {
		return _scProductVersion.getProductEntryId();
	}

	/**
	* Sets the product entry id of this s c product version.
	*
	* @param productEntryId the product entry id of this s c product version
	*/
	public void setProductEntryId(long productEntryId) {
		_scProductVersion.setProductEntryId(productEntryId);
	}

	/**
	* Gets the version of this s c product version.
	*
	* @return the version of this s c product version
	*/
	public java.lang.String getVersion() {
		return _scProductVersion.getVersion();
	}

	/**
	* Sets the version of this s c product version.
	*
	* @param version the version of this s c product version
	*/
	public void setVersion(java.lang.String version) {
		_scProductVersion.setVersion(version);
	}

	/**
	* Gets the change log of this s c product version.
	*
	* @return the change log of this s c product version
	*/
	public java.lang.String getChangeLog() {
		return _scProductVersion.getChangeLog();
	}

	/**
	* Sets the change log of this s c product version.
	*
	* @param changeLog the change log of this s c product version
	*/
	public void setChangeLog(java.lang.String changeLog) {
		_scProductVersion.setChangeLog(changeLog);
	}

	/**
	* Gets the download page u r l of this s c product version.
	*
	* @return the download page u r l of this s c product version
	*/
	public java.lang.String getDownloadPageURL() {
		return _scProductVersion.getDownloadPageURL();
	}

	/**
	* Sets the download page u r l of this s c product version.
	*
	* @param downloadPageURL the download page u r l of this s c product version
	*/
	public void setDownloadPageURL(java.lang.String downloadPageURL) {
		_scProductVersion.setDownloadPageURL(downloadPageURL);
	}

	/**
	* Gets the direct download u r l of this s c product version.
	*
	* @return the direct download u r l of this s c product version
	*/
	public java.lang.String getDirectDownloadURL() {
		return _scProductVersion.getDirectDownloadURL();
	}

	/**
	* Sets the direct download u r l of this s c product version.
	*
	* @param directDownloadURL the direct download u r l of this s c product version
	*/
	public void setDirectDownloadURL(java.lang.String directDownloadURL) {
		_scProductVersion.setDirectDownloadURL(directDownloadURL);
	}

	/**
	* Gets the repo store artifact of this s c product version.
	*
	* @return the repo store artifact of this s c product version
	*/
	public boolean getRepoStoreArtifact() {
		return _scProductVersion.getRepoStoreArtifact();
	}

	/**
	* Determines if this s c product version is repo store artifact.
	*
	* @return <code>true</code> if this s c product version is repo store artifact; <code>false</code> otherwise
	*/
	public boolean isRepoStoreArtifact() {
		return _scProductVersion.isRepoStoreArtifact();
	}

	/**
	* Sets whether this s c product version is repo store artifact.
	*
	* @param repoStoreArtifact the repo store artifact of this s c product version
	*/
	public void setRepoStoreArtifact(boolean repoStoreArtifact) {
		_scProductVersion.setRepoStoreArtifact(repoStoreArtifact);
	}

	public boolean isNew() {
		return _scProductVersion.isNew();
	}

	public void setNew(boolean n) {
		_scProductVersion.setNew(n);
	}

	public boolean isCachedModel() {
		return _scProductVersion.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_scProductVersion.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _scProductVersion.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_scProductVersion.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _scProductVersion.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scProductVersion.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scProductVersion.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new SCProductVersionWrapper((SCProductVersion)_scProductVersion.clone());
	}

	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion) {
		return _scProductVersion.compareTo(scProductVersion);
	}

	public int hashCode() {
		return _scProductVersion.hashCode();
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion toEscapedModel() {
		return new SCProductVersionWrapper(_scProductVersion.toEscapedModel());
	}

	public java.lang.String toString() {
		return _scProductVersion.toString();
	}

	public java.lang.String toXmlString() {
		return _scProductVersion.toXmlString();
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scProductVersion.getFrameworkVersions();
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry() {
		return _scProductVersion.getProductEntry();
	}

	public SCProductVersion getWrappedSCProductVersion() {
		return _scProductVersion;
	}

	private SCProductVersion _scProductVersion;
}