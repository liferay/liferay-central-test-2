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

package com.liferay.portlet.documentlibrary.model;


/**
 * <a href="DLFolderSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link DLFolder}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFolder
 * @generated
 */
public class DLFolderWrapper implements DLFolder {
	public DLFolderWrapper(DLFolder dlFolder) {
		_dlFolder = dlFolder;
	}

	public long getPrimaryKey() {
		return _dlFolder.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_dlFolder.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _dlFolder.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_dlFolder.setUuid(uuid);
	}

	public long getFolderId() {
		return _dlFolder.getFolderId();
	}

	public void setFolderId(long folderId) {
		_dlFolder.setFolderId(folderId);
	}

	public long getGroupId() {
		return _dlFolder.getGroupId();
	}

	public void setGroupId(long groupId) {
		_dlFolder.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _dlFolder.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_dlFolder.setCompanyId(companyId);
	}

	public long getUserId() {
		return _dlFolder.getUserId();
	}

	public void setUserId(long userId) {
		_dlFolder.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _dlFolder.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_dlFolder.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _dlFolder.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_dlFolder.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _dlFolder.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_dlFolder.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _dlFolder.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_dlFolder.setModifiedDate(modifiedDate);
	}

	public long getParentFolderId() {
		return _dlFolder.getParentFolderId();
	}

	public void setParentFolderId(long parentFolderId) {
		_dlFolder.setParentFolderId(parentFolderId);
	}

	public java.lang.String getName() {
		return _dlFolder.getName();
	}

	public void setName(java.lang.String name) {
		_dlFolder.setName(name);
	}

	public java.lang.String getDescription() {
		return _dlFolder.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_dlFolder.setDescription(description);
	}

	public java.util.Date getLastPostDate() {
		return _dlFolder.getLastPostDate();
	}

	public void setLastPostDate(java.util.Date lastPostDate) {
		_dlFolder.setLastPostDate(lastPostDate);
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder toEscapedModel() {
		return _dlFolder.toEscapedModel();
	}

	public boolean isNew() {
		return _dlFolder.isNew();
	}

	public boolean setNew(boolean n) {
		return _dlFolder.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFolder.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFolder.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFolder.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_dlFolder.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFolder.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFolder.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFolder.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _dlFolder.clone();
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFolder dlFolder) {
		return _dlFolder.compareTo(dlFolder);
	}

	public int hashCode() {
		return _dlFolder.hashCode();
	}

	public java.lang.String toString() {
		return _dlFolder.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFolder.toXmlString();
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getAncestors()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFolder.getAncestors();
	}

	public com.liferay.portlet.documentlibrary.model.DLFolder getParentFolder()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFolder.getParentFolder();
	}

	public java.lang.String getPath()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFolder.getPath();
	}

	public java.lang.String[] getPathArray()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _dlFolder.getPathArray();
	}

	public boolean isRoot() {
		return _dlFolder.isRoot();
	}

	public DLFolder getWrappedDLFolder() {
		return _dlFolder;
	}

	private DLFolder _dlFolder;
}