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

package com.liferay.portlet.messageboards.model;

/**
 * <p>
 * This class is a wrapper for {@link MBThread}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBThread
 * @generated
 */
public class MBThreadWrapper implements MBThread {
	public MBThreadWrapper(MBThread mbThread) {
		_mbThread = mbThread;
	}

	public long getPrimaryKey() {
		return _mbThread.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_mbThread.setPrimaryKey(pk);
	}

	public long getThreadId() {
		return _mbThread.getThreadId();
	}

	public void setThreadId(long threadId) {
		_mbThread.setThreadId(threadId);
	}

	public long getGroupId() {
		return _mbThread.getGroupId();
	}

	public void setGroupId(long groupId) {
		_mbThread.setGroupId(groupId);
	}

	public long getCategoryId() {
		return _mbThread.getCategoryId();
	}

	public void setCategoryId(long categoryId) {
		_mbThread.setCategoryId(categoryId);
	}

	public long getRootMessageId() {
		return _mbThread.getRootMessageId();
	}

	public void setRootMessageId(long rootMessageId) {
		_mbThread.setRootMessageId(rootMessageId);
	}

	public int getMessageCount() {
		return _mbThread.getMessageCount();
	}

	public void setMessageCount(int messageCount) {
		_mbThread.setMessageCount(messageCount);
	}

	public int getViewCount() {
		return _mbThread.getViewCount();
	}

	public void setViewCount(int viewCount) {
		_mbThread.setViewCount(viewCount);
	}

	public long getLastPostByUserId() {
		return _mbThread.getLastPostByUserId();
	}

	public void setLastPostByUserId(long lastPostByUserId) {
		_mbThread.setLastPostByUserId(lastPostByUserId);
	}

	public java.lang.String getLastPostByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.getLastPostByUserUuid();
	}

	public void setLastPostByUserUuid(java.lang.String lastPostByUserUuid) {
		_mbThread.setLastPostByUserUuid(lastPostByUserUuid);
	}

	public java.util.Date getLastPostDate() {
		return _mbThread.getLastPostDate();
	}

	public void setLastPostDate(java.util.Date lastPostDate) {
		_mbThread.setLastPostDate(lastPostDate);
	}

	public double getPriority() {
		return _mbThread.getPriority();
	}

	public void setPriority(double priority) {
		_mbThread.setPriority(priority);
	}

	public int getStatus() {
		return _mbThread.getStatus();
	}

	public void setStatus(int status) {
		_mbThread.setStatus(status);
	}

	public long getStatusByUserId() {
		return _mbThread.getStatusByUserId();
	}

	public void setStatusByUserId(long statusByUserId) {
		_mbThread.setStatusByUserId(statusByUserId);
	}

	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.getStatusByUserUuid();
	}

	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_mbThread.setStatusByUserUuid(statusByUserUuid);
	}

	public java.lang.String getStatusByUserName() {
		return _mbThread.getStatusByUserName();
	}

	public void setStatusByUserName(java.lang.String statusByUserName) {
		_mbThread.setStatusByUserName(statusByUserName);
	}

	public java.util.Date getStatusDate() {
		return _mbThread.getStatusDate();
	}

	public void setStatusDate(java.util.Date statusDate) {
		_mbThread.setStatusDate(statusDate);
	}

	public boolean isApproved() {
		return _mbThread.isApproved();
	}

	public boolean isDraft() {
		return _mbThread.isDraft();
	}

	public boolean isExpired() {
		return _mbThread.isExpired();
	}

	public boolean isPending() {
		return _mbThread.isPending();
	}

	public com.liferay.portlet.messageboards.model.MBThread toEscapedModel() {
		return _mbThread.toEscapedModel();
	}

	public boolean isNew() {
		return _mbThread.isNew();
	}

	public void setNew(boolean n) {
		_mbThread.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbThread.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbThread.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbThread.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbThread.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbThread.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbThread.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbThread.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _mbThread.clone();
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBThread mbThread) {
		return _mbThread.compareTo(mbThread);
	}

	public int hashCode() {
		return _mbThread.hashCode();
	}

	public java.lang.String toString() {
		return _mbThread.toString();
	}

	public java.lang.String toXmlString() {
		return _mbThread.toXmlString();
	}

	public java.lang.String getAttachmentsDir() {
		return _mbThread.getAttachmentsDir();
	}

	public com.liferay.portal.model.Lock getLock() {
		return _mbThread.getLock();
	}

	public boolean hasLock(long userId) {
		return _mbThread.hasLock(userId);
	}

	public boolean isLocked() {
		return _mbThread.isLocked();
	}

	public MBThread getWrappedMBThread() {
		return _mbThread;
	}

	private MBThread _mbThread;
}