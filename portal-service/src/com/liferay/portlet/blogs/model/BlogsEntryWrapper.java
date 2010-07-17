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

package com.liferay.portlet.blogs.model;

/**
 * <p>
 * This class is a wrapper for {@link BlogsEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsEntry
 * @generated
 */
public class BlogsEntryWrapper implements BlogsEntry {
	public BlogsEntryWrapper(BlogsEntry blogsEntry) {
		_blogsEntry = blogsEntry;
	}

	public long getPrimaryKey() {
		return _blogsEntry.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_blogsEntry.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _blogsEntry.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_blogsEntry.setUuid(uuid);
	}

	public long getEntryId() {
		return _blogsEntry.getEntryId();
	}

	public void setEntryId(long entryId) {
		_blogsEntry.setEntryId(entryId);
	}

	public long getGroupId() {
		return _blogsEntry.getGroupId();
	}

	public void setGroupId(long groupId) {
		_blogsEntry.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _blogsEntry.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_blogsEntry.setCompanyId(companyId);
	}

	public long getUserId() {
		return _blogsEntry.getUserId();
	}

	public void setUserId(long userId) {
		_blogsEntry.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntry.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_blogsEntry.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _blogsEntry.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_blogsEntry.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _blogsEntry.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_blogsEntry.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _blogsEntry.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_blogsEntry.setModifiedDate(modifiedDate);
	}

	public java.lang.String getTitle() {
		return _blogsEntry.getTitle();
	}

	public void setTitle(java.lang.String title) {
		_blogsEntry.setTitle(title);
	}

	public java.lang.String getUrlTitle() {
		return _blogsEntry.getUrlTitle();
	}

	public void setUrlTitle(java.lang.String urlTitle) {
		_blogsEntry.setUrlTitle(urlTitle);
	}

	public java.lang.String getContent() {
		return _blogsEntry.getContent();
	}

	public void setContent(java.lang.String content) {
		_blogsEntry.setContent(content);
	}

	public java.util.Date getDisplayDate() {
		return _blogsEntry.getDisplayDate();
	}

	public void setDisplayDate(java.util.Date displayDate) {
		_blogsEntry.setDisplayDate(displayDate);
	}

	public boolean getAllowPingbacks() {
		return _blogsEntry.getAllowPingbacks();
	}

	public boolean isAllowPingbacks() {
		return _blogsEntry.isAllowPingbacks();
	}

	public void setAllowPingbacks(boolean allowPingbacks) {
		_blogsEntry.setAllowPingbacks(allowPingbacks);
	}

	public boolean getAllowTrackbacks() {
		return _blogsEntry.getAllowTrackbacks();
	}

	public boolean isAllowTrackbacks() {
		return _blogsEntry.isAllowTrackbacks();
	}

	public void setAllowTrackbacks(boolean allowTrackbacks) {
		_blogsEntry.setAllowTrackbacks(allowTrackbacks);
	}

	public java.lang.String getTrackbacks() {
		return _blogsEntry.getTrackbacks();
	}

	public void setTrackbacks(java.lang.String trackbacks) {
		_blogsEntry.setTrackbacks(trackbacks);
	}

	public int getStatus() {
		return _blogsEntry.getStatus();
	}

	public void setStatus(int status) {
		_blogsEntry.setStatus(status);
	}

	public long getStatusByUserId() {
		return _blogsEntry.getStatusByUserId();
	}

	public void setStatusByUserId(long statusByUserId) {
		_blogsEntry.setStatusByUserId(statusByUserId);
	}

	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsEntry.getStatusByUserUuid();
	}

	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_blogsEntry.setStatusByUserUuid(statusByUserUuid);
	}

	public java.lang.String getStatusByUserName() {
		return _blogsEntry.getStatusByUserName();
	}

	public void setStatusByUserName(java.lang.String statusByUserName) {
		_blogsEntry.setStatusByUserName(statusByUserName);
	}

	public java.util.Date getStatusDate() {
		return _blogsEntry.getStatusDate();
	}

	public void setStatusDate(java.util.Date statusDate) {
		_blogsEntry.setStatusDate(statusDate);
	}

	public boolean isApproved() {
		return _blogsEntry.isApproved();
	}

	public boolean isDraft() {
		return _blogsEntry.isDraft();
	}

	public boolean isExpired() {
		return _blogsEntry.isExpired();
	}

	public boolean isPending() {
		return _blogsEntry.isPending();
	}

	public com.liferay.portlet.blogs.model.BlogsEntry toEscapedModel() {
		return _blogsEntry.toEscapedModel();
	}

	public boolean isNew() {
		return _blogsEntry.isNew();
	}

	public void setNew(boolean n) {
		_blogsEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _blogsEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_blogsEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _blogsEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_blogsEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _blogsEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _blogsEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_blogsEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _blogsEntry.clone();
	}

	public int compareTo(com.liferay.portlet.blogs.model.BlogsEntry blogsEntry) {
		return _blogsEntry.compareTo(blogsEntry);
	}

	public int hashCode() {
		return _blogsEntry.hashCode();
	}

	public java.lang.String toString() {
		return _blogsEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _blogsEntry.toXmlString();
	}

	public BlogsEntry getWrappedBlogsEntry() {
		return _blogsEntry;
	}

	private BlogsEntry _blogsEntry;
}