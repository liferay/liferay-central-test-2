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

package com.liferay.portlet.bookmarks.model;

/**
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

	public void setNew(boolean n) {
		_bookmarksEntry.setNew(n);
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