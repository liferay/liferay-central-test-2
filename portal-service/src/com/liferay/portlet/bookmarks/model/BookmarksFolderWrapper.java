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

package com.liferay.portlet.bookmarks.model;


/**
 * <a href="BookmarksFolderSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link BookmarksFolder}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BookmarksFolder
 * @generated
 */
public class BookmarksFolderWrapper implements BookmarksFolder {
	public BookmarksFolderWrapper(BookmarksFolder bookmarksFolder) {
		_bookmarksFolder = bookmarksFolder;
	}

	public long getPrimaryKey() {
		return _bookmarksFolder.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_bookmarksFolder.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _bookmarksFolder.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_bookmarksFolder.setUuid(uuid);
	}

	public long getFolderId() {
		return _bookmarksFolder.getFolderId();
	}

	public void setFolderId(long folderId) {
		_bookmarksFolder.setFolderId(folderId);
	}

	public long getGroupId() {
		return _bookmarksFolder.getGroupId();
	}

	public void setGroupId(long groupId) {
		_bookmarksFolder.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _bookmarksFolder.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_bookmarksFolder.setCompanyId(companyId);
	}

	public long getUserId() {
		return _bookmarksFolder.getUserId();
	}

	public void setUserId(long userId) {
		_bookmarksFolder.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolder.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_bookmarksFolder.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _bookmarksFolder.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_bookmarksFolder.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _bookmarksFolder.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_bookmarksFolder.setModifiedDate(modifiedDate);
	}

	public long getParentFolderId() {
		return _bookmarksFolder.getParentFolderId();
	}

	public void setParentFolderId(long parentFolderId) {
		_bookmarksFolder.setParentFolderId(parentFolderId);
	}

	public java.lang.String getName() {
		return _bookmarksFolder.getName();
	}

	public void setName(java.lang.String name) {
		_bookmarksFolder.setName(name);
	}

	public java.lang.String getDescription() {
		return _bookmarksFolder.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_bookmarksFolder.setDescription(description);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder toEscapedModel() {
		return _bookmarksFolder.toEscapedModel();
	}

	public boolean isNew() {
		return _bookmarksFolder.isNew();
	}

	public boolean setNew(boolean n) {
		return _bookmarksFolder.setNew(n);
	}

	public boolean isCachedModel() {
		return _bookmarksFolder.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_bookmarksFolder.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _bookmarksFolder.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_bookmarksFolder.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _bookmarksFolder.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _bookmarksFolder.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_bookmarksFolder.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _bookmarksFolder.clone();
	}

	public int compareTo(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder) {
		return _bookmarksFolder.compareTo(bookmarksFolder);
	}

	public int hashCode() {
		return _bookmarksFolder.hashCode();
	}

	public java.lang.String toString() {
		return _bookmarksFolder.toString();
	}

	public java.lang.String toXmlString() {
		return _bookmarksFolder.toXmlString();
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolder.getAncestors();
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolder.getParentFolder();
	}

	public boolean isRoot() {
		return _bookmarksFolder.isRoot();
	}

	public BookmarksFolder getWrappedBookmarksFolder() {
		return _bookmarksFolder;
	}

	private BookmarksFolder _bookmarksFolder;
}