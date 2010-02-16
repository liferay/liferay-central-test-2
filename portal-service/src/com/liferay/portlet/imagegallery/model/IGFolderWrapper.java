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

package com.liferay.portlet.imagegallery.model;


/**
 * <a href="IGFolderSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link IGFolder}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       IGFolder
 * @generated
 */
public class IGFolderWrapper implements IGFolder {
	public IGFolderWrapper(IGFolder igFolder) {
		_igFolder = igFolder;
	}

	public long getPrimaryKey() {
		return _igFolder.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_igFolder.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _igFolder.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_igFolder.setUuid(uuid);
	}

	public long getFolderId() {
		return _igFolder.getFolderId();
	}

	public void setFolderId(long folderId) {
		_igFolder.setFolderId(folderId);
	}

	public long getGroupId() {
		return _igFolder.getGroupId();
	}

	public void setGroupId(long groupId) {
		_igFolder.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _igFolder.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_igFolder.setCompanyId(companyId);
	}

	public long getUserId() {
		return _igFolder.getUserId();
	}

	public void setUserId(long userId) {
		_igFolder.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _igFolder.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_igFolder.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _igFolder.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_igFolder.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _igFolder.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_igFolder.setModifiedDate(modifiedDate);
	}

	public long getParentFolderId() {
		return _igFolder.getParentFolderId();
	}

	public void setParentFolderId(long parentFolderId) {
		_igFolder.setParentFolderId(parentFolderId);
	}

	public java.lang.String getName() {
		return _igFolder.getName();
	}

	public void setName(java.lang.String name) {
		_igFolder.setName(name);
	}

	public java.lang.String getDescription() {
		return _igFolder.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_igFolder.setDescription(description);
	}

	public com.liferay.portlet.imagegallery.model.IGFolder toEscapedModel() {
		return _igFolder.toEscapedModel();
	}

	public boolean isNew() {
		return _igFolder.isNew();
	}

	public boolean setNew(boolean n) {
		return _igFolder.setNew(n);
	}

	public boolean isCachedModel() {
		return _igFolder.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_igFolder.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _igFolder.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_igFolder.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _igFolder.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _igFolder.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_igFolder.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _igFolder.clone();
	}

	public int compareTo(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder) {
		return _igFolder.compareTo(igFolder);
	}

	public int hashCode() {
		return _igFolder.hashCode();
	}

	public java.lang.String toString() {
		return _igFolder.toString();
	}

	public java.lang.String toXmlString() {
		return _igFolder.toXmlString();
	}

	public java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolder.getAncestors();
	}

	public com.liferay.portlet.imagegallery.model.IGFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _igFolder.getParentFolder();
	}

	public boolean isRoot() {
		return _igFolder.isRoot();
	}

	public IGFolder getWrappedIGFolder() {
		return _igFolder;
	}

	private IGFolder _igFolder;
}