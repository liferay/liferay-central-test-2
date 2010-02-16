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
 * <a href="BookmarksEntrySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link BookmarksEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BookmarksEntry
 * @generated
 */
public class BookmarksEntryWrapper implements BookmarksEntry {
	public BookmarksEntryWrapper(BookmarksEntry bookmarksEntry) {
		_bookmarksEntry = bookmarksEntry;
	}

	public long getPrimaryKey() {
		return _bookmarksEntry.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_bookmarksEntry.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _bookmarksEntry.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_bookmarksEntry.setUuid(uuid);
	}

	public long getEntryId() {
		return _bookmarksEntry.getEntryId();
	}

	public void setEntryId(long entryId) {
		_bookmarksEntry.setEntryId(entryId);
	}

	public long getGroupId() {
		return _bookmarksEntry.getGroupId();
	}

	public void setGroupId(long groupId) {
		_bookmarksEntry.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _bookmarksEntry.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_bookmarksEntry.setCompanyId(companyId);
	}

	public long getUserId() {
		return _bookmarksEntry.getUserId();
	}

	public void setUserId(long userId) {
		_bookmarksEntry.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksEntry.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_bookmarksEntry.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _bookmarksEntry.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_bookmarksEntry.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _bookmarksEntry.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_bookmarksEntry.setModifiedDate(modifiedDate);
	}

	public long getFolderId() {
		return _bookmarksEntry.getFolderId();
	}

	public void setFolderId(long folderId) {
		_bookmarksEntry.setFolderId(folderId);
	}

	public java.lang.String getName() {
		return _bookmarksEntry.getName();
	}

	public void setName(java.lang.String name) {
		_bookmarksEntry.setName(name);
	}

	public java.lang.String getUrl() {
		return _bookmarksEntry.getUrl();
	}

	public void setUrl(java.lang.String url) {
		_bookmarksEntry.setUrl(url);
	}

	public java.lang.String getComments() {
		return _bookmarksEntry.getComments();
	}

	public void setComments(java.lang.String comments) {
		_bookmarksEntry.setComments(comments);
	}

	public int getVisits() {
		return _bookmarksEntry.getVisits();
	}

	public void setVisits(int visits) {
		_bookmarksEntry.setVisits(visits);
	}

	public int getPriority() {
		return _bookmarksEntry.getPriority();
	}

	public void setPriority(int priority) {
		_bookmarksEntry.setPriority(priority);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksEntry toEscapedModel() {
		return _bookmarksEntry.toEscapedModel();
	}

	public boolean isNew() {
		return _bookmarksEntry.isNew();
	}

	public boolean setNew(boolean n) {
		return _bookmarksEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _bookmarksEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_bookmarksEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _bookmarksEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_bookmarksEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _bookmarksEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _bookmarksEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_bookmarksEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _bookmarksEntry.clone();
	}

	public int compareTo(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry) {
		return _bookmarksEntry.compareTo(bookmarksEntry);
	}

	public int hashCode() {
		return _bookmarksEntry.hashCode();
	}

	public java.lang.String toString() {
		return _bookmarksEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _bookmarksEntry.toXmlString();
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder getFolder() {
		return _bookmarksEntry.getFolder();
	}

	public BookmarksEntry getWrappedBookmarksEntry() {
		return _bookmarksEntry;
	}

	private BookmarksEntry _bookmarksEntry;
}