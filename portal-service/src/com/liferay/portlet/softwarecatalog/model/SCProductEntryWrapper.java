/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * <a href="SCProductEntrySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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
		throws com.liferay.portal.SystemException {
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

	public boolean setNew(boolean n) {
		return _scProductEntry.setNew(n);
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
		throws com.liferay.portal.SystemException {
		return _scProductEntry.getLatestVersion();
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses()
		throws com.liferay.portal.SystemException {
		return _scProductEntry.getLicenses();
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> getScreenshots()
		throws com.liferay.portal.SystemException {
		return _scProductEntry.getScreenshots();
	}

	public SCProductEntry getWrappedSCProductEntry() {
		return _scProductEntry;
	}

	private SCProductEntry _scProductEntry;
}