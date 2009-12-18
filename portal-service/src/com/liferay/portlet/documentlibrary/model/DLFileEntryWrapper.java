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

package com.liferay.portlet.documentlibrary.model;


/**
 * <a href="DLFileEntrySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link DLFileEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntry
 * @generated
 */
public class DLFileEntryWrapper implements DLFileEntry {
	public DLFileEntryWrapper(DLFileEntry dlFileEntry) {
		_dlFileEntry = dlFileEntry;
	}

	public long getPrimaryKey() {
		return _dlFileEntry.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_dlFileEntry.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _dlFileEntry.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_dlFileEntry.setUuid(uuid);
	}

	public long getFileEntryId() {
		return _dlFileEntry.getFileEntryId();
	}

	public void setFileEntryId(long fileEntryId) {
		_dlFileEntry.setFileEntryId(fileEntryId);
	}

	public long getGroupId() {
		return _dlFileEntry.getGroupId();
	}

	public void setGroupId(long groupId) {
		_dlFileEntry.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _dlFileEntry.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_dlFileEntry.setCompanyId(companyId);
	}

	public long getUserId() {
		return _dlFileEntry.getUserId();
	}

	public void setUserId(long userId) {
		_dlFileEntry.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _dlFileEntry.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_dlFileEntry.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _dlFileEntry.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_dlFileEntry.setUserName(userName);
	}

	public long getVersionUserId() {
		return _dlFileEntry.getVersionUserId();
	}

	public void setVersionUserId(long versionUserId) {
		_dlFileEntry.setVersionUserId(versionUserId);
	}

	public java.lang.String getVersionUserUuid()
		throws com.liferay.portal.SystemException {
		return _dlFileEntry.getVersionUserUuid();
	}

	public void setVersionUserUuid(java.lang.String versionUserUuid) {
		_dlFileEntry.setVersionUserUuid(versionUserUuid);
	}

	public java.lang.String getVersionUserName() {
		return _dlFileEntry.getVersionUserName();
	}

	public void setVersionUserName(java.lang.String versionUserName) {
		_dlFileEntry.setVersionUserName(versionUserName);
	}

	public java.util.Date getCreateDate() {
		return _dlFileEntry.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_dlFileEntry.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _dlFileEntry.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_dlFileEntry.setModifiedDate(modifiedDate);
	}

	public long getFolderId() {
		return _dlFileEntry.getFolderId();
	}

	public void setFolderId(long folderId) {
		_dlFileEntry.setFolderId(folderId);
	}

	public java.lang.String getName() {
		return _dlFileEntry.getName();
	}

	public void setName(java.lang.String name) {
		_dlFileEntry.setName(name);
	}

	public java.lang.String getTitle() {
		return _dlFileEntry.getTitle();
	}

	public void setTitle(java.lang.String title) {
		_dlFileEntry.setTitle(title);
	}

	public java.lang.String getDescription() {
		return _dlFileEntry.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_dlFileEntry.setDescription(description);
	}

	public double getVersion() {
		return _dlFileEntry.getVersion();
	}

	public void setVersion(double version) {
		_dlFileEntry.setVersion(version);
	}

	public double getPendingVersion() {
		return _dlFileEntry.getPendingVersion();
	}

	public void setPendingVersion(double pendingVersion) {
		_dlFileEntry.setPendingVersion(pendingVersion);
	}

	public int getSize() {
		return _dlFileEntry.getSize();
	}

	public void setSize(int size) {
		_dlFileEntry.setSize(size);
	}

	public int getReadCount() {
		return _dlFileEntry.getReadCount();
	}

	public void setReadCount(int readCount) {
		_dlFileEntry.setReadCount(readCount);
	}

	public java.lang.String getExtraSettings() {
		return _dlFileEntry.getExtraSettings();
	}

	public void setExtraSettings(java.lang.String extraSettings) {
		_dlFileEntry.setExtraSettings(extraSettings);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntry toEscapedModel() {
		return _dlFileEntry.toEscapedModel();
	}

	public boolean isNew() {
		return _dlFileEntry.isNew();
	}

	public boolean setNew(boolean n) {
		return _dlFileEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFileEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFileEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFileEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_dlFileEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _dlFileEntry.clone();
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileEntry dlFileEntry) {
		return _dlFileEntry.compareTo(dlFileEntry);
	}

	public int hashCode() {
		return _dlFileEntry.hashCode();
	}

	public java.lang.String toString() {
		return _dlFileEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileEntry.toXmlString();
	}

	public java.util.Properties getExtraSettingsProperties() {
		return _dlFileEntry.getExtraSettingsProperties();
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder() {
		return _dlFileEntry.getFolder();
	}

	public java.lang.String getLuceneProperties() {
		return _dlFileEntry.getLuceneProperties();
	}

	public long getRepositoryId() {
		return _dlFileEntry.getRepositoryId();
	}

	public void setExtraSettingsProperties(
		java.util.Properties extraSettingsProperties) {
		_dlFileEntry.setExtraSettingsProperties(extraSettingsProperties);
	}

	public DLFileEntry getWrappedDLFileEntry() {
		return _dlFileEntry;
	}

	private DLFileEntry _dlFileEntry;
}