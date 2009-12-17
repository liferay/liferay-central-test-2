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

package com.liferay.portlet.softwarecatalog.model;


/**
 * <a href="SCProductVersionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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

	public long getPrimaryKey() {
		return _scProductVersion.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_scProductVersion.setPrimaryKey(pk);
	}

	public long getProductVersionId() {
		return _scProductVersion.getProductVersionId();
	}

	public void setProductVersionId(long productVersionId) {
		_scProductVersion.setProductVersionId(productVersionId);
	}

	public long getCompanyId() {
		return _scProductVersion.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_scProductVersion.setCompanyId(companyId);
	}

	public long getUserId() {
		return _scProductVersion.getUserId();
	}

	public void setUserId(long userId) {
		_scProductVersion.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _scProductVersion.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_scProductVersion.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _scProductVersion.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_scProductVersion.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _scProductVersion.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_scProductVersion.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _scProductVersion.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_scProductVersion.setModifiedDate(modifiedDate);
	}

	public long getProductEntryId() {
		return _scProductVersion.getProductEntryId();
	}

	public void setProductEntryId(long productEntryId) {
		_scProductVersion.setProductEntryId(productEntryId);
	}

	public java.lang.String getVersion() {
		return _scProductVersion.getVersion();
	}

	public void setVersion(java.lang.String version) {
		_scProductVersion.setVersion(version);
	}

	public java.lang.String getChangeLog() {
		return _scProductVersion.getChangeLog();
	}

	public void setChangeLog(java.lang.String changeLog) {
		_scProductVersion.setChangeLog(changeLog);
	}

	public java.lang.String getDownloadPageURL() {
		return _scProductVersion.getDownloadPageURL();
	}

	public void setDownloadPageURL(java.lang.String downloadPageURL) {
		_scProductVersion.setDownloadPageURL(downloadPageURL);
	}

	public java.lang.String getDirectDownloadURL() {
		return _scProductVersion.getDirectDownloadURL();
	}

	public void setDirectDownloadURL(java.lang.String directDownloadURL) {
		_scProductVersion.setDirectDownloadURL(directDownloadURL);
	}

	public boolean getRepoStoreArtifact() {
		return _scProductVersion.getRepoStoreArtifact();
	}

	public boolean isRepoStoreArtifact() {
		return _scProductVersion.isRepoStoreArtifact();
	}

	public void setRepoStoreArtifact(boolean repoStoreArtifact) {
		_scProductVersion.setRepoStoreArtifact(repoStoreArtifact);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductVersion toEscapedModel() {
		return _scProductVersion.toEscapedModel();
	}

	public boolean isNew() {
		return _scProductVersion.isNew();
	}

	public boolean setNew(boolean n) {
		return _scProductVersion.setNew(n);
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
		return _scProductVersion.clone();
	}

	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion) {
		return _scProductVersion.compareTo(scProductVersion);
	}

	public int hashCode() {
		return _scProductVersion.hashCode();
	}

	public java.lang.String toString() {
		return _scProductVersion.toString();
	}

	public java.lang.String toXmlString() {
		return _scProductVersion.toXmlString();
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions()
		throws com.liferay.portal.SystemException {
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