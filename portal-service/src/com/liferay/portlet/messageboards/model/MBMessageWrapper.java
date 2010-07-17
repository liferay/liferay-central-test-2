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
 * This class is a wrapper for {@link MBMessage}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessage
 * @generated
 */
public class MBMessageWrapper implements MBMessage {
	public MBMessageWrapper(MBMessage mbMessage) {
		_mbMessage = mbMessage;
	}

	public long getPrimaryKey() {
		return _mbMessage.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_mbMessage.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _mbMessage.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_mbMessage.setUuid(uuid);
	}

	public long getMessageId() {
		return _mbMessage.getMessageId();
	}

	public void setMessageId(long messageId) {
		_mbMessage.setMessageId(messageId);
	}

	public long getGroupId() {
		return _mbMessage.getGroupId();
	}

	public void setGroupId(long groupId) {
		_mbMessage.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _mbMessage.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_mbMessage.setCompanyId(companyId);
	}

	public long getUserId() {
		return _mbMessage.getUserId();
	}

	public void setUserId(long userId) {
		_mbMessage.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_mbMessage.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _mbMessage.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_mbMessage.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _mbMessage.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_mbMessage.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _mbMessage.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbMessage.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _mbMessage.getClassName();
	}

	public long getClassNameId() {
		return _mbMessage.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_mbMessage.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _mbMessage.getClassPK();
	}

	public void setClassPK(long classPK) {
		_mbMessage.setClassPK(classPK);
	}

	public long getCategoryId() {
		return _mbMessage.getCategoryId();
	}

	public void setCategoryId(long categoryId) {
		_mbMessage.setCategoryId(categoryId);
	}

	public long getThreadId() {
		return _mbMessage.getThreadId();
	}

	public void setThreadId(long threadId) {
		_mbMessage.setThreadId(threadId);
	}

	public long getRootMessageId() {
		return _mbMessage.getRootMessageId();
	}

	public void setRootMessageId(long rootMessageId) {
		_mbMessage.setRootMessageId(rootMessageId);
	}

	public long getParentMessageId() {
		return _mbMessage.getParentMessageId();
	}

	public void setParentMessageId(long parentMessageId) {
		_mbMessage.setParentMessageId(parentMessageId);
	}

	public java.lang.String getSubject() {
		return _mbMessage.getSubject();
	}

	public void setSubject(java.lang.String subject) {
		_mbMessage.setSubject(subject);
	}

	public java.lang.String getBody() {
		return _mbMessage.getBody();
	}

	public void setBody(java.lang.String body) {
		_mbMessage.setBody(body);
	}

	public boolean getAttachments() {
		return _mbMessage.getAttachments();
	}

	public boolean isAttachments() {
		return _mbMessage.isAttachments();
	}

	public void setAttachments(boolean attachments) {
		_mbMessage.setAttachments(attachments);
	}

	public boolean getAnonymous() {
		return _mbMessage.getAnonymous();
	}

	public boolean isAnonymous() {
		return _mbMessage.isAnonymous();
	}

	public void setAnonymous(boolean anonymous) {
		_mbMessage.setAnonymous(anonymous);
	}

	public double getPriority() {
		return _mbMessage.getPriority();
	}

	public void setPriority(double priority) {
		_mbMessage.setPriority(priority);
	}

	public boolean getAllowPingbacks() {
		return _mbMessage.getAllowPingbacks();
	}

	public boolean isAllowPingbacks() {
		return _mbMessage.isAllowPingbacks();
	}

	public void setAllowPingbacks(boolean allowPingbacks) {
		_mbMessage.setAllowPingbacks(allowPingbacks);
	}

	public int getStatus() {
		return _mbMessage.getStatus();
	}

	public void setStatus(int status) {
		_mbMessage.setStatus(status);
	}

	public long getStatusByUserId() {
		return _mbMessage.getStatusByUserId();
	}

	public void setStatusByUserId(long statusByUserId) {
		_mbMessage.setStatusByUserId(statusByUserId);
	}

	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.getStatusByUserUuid();
	}

	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_mbMessage.setStatusByUserUuid(statusByUserUuid);
	}

	public java.lang.String getStatusByUserName() {
		return _mbMessage.getStatusByUserName();
	}

	public void setStatusByUserName(java.lang.String statusByUserName) {
		_mbMessage.setStatusByUserName(statusByUserName);
	}

	public java.util.Date getStatusDate() {
		return _mbMessage.getStatusDate();
	}

	public void setStatusDate(java.util.Date statusDate) {
		_mbMessage.setStatusDate(statusDate);
	}

	public boolean isApproved() {
		return _mbMessage.isApproved();
	}

	public boolean isDraft() {
		return _mbMessage.isDraft();
	}

	public boolean isExpired() {
		return _mbMessage.isExpired();
	}

	public boolean isPending() {
		return _mbMessage.isPending();
	}

	public com.liferay.portlet.messageboards.model.MBMessage toEscapedModel() {
		return _mbMessage.toEscapedModel();
	}

	public boolean isNew() {
		return _mbMessage.isNew();
	}

	public void setNew(boolean n) {
		_mbMessage.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbMessage.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbMessage.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbMessage.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbMessage.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbMessage.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbMessage.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbMessage.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _mbMessage.clone();
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage) {
		return _mbMessage.compareTo(mbMessage);
	}

	public int hashCode() {
		return _mbMessage.hashCode();
	}

	public java.lang.String toString() {
		return _mbMessage.toString();
	}

	public java.lang.String toXmlString() {
		return _mbMessage.toXmlString();
	}

	public java.lang.String[] getAssetTagNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.getAssetTagNames();
	}

	public java.lang.String getAttachmentsDir() {
		return _mbMessage.getAttachmentsDir();
	}

	public java.lang.String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.getAttachmentsFiles();
	}

	public java.lang.String getBody(boolean translate) {
		return _mbMessage.getBody(translate);
	}

	public com.liferay.portlet.messageboards.model.MBCategory getCategory() {
		return _mbMessage.getCategory();
	}

	public com.liferay.portlet.messageboards.model.MBThread getThread()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.getThread();
	}

	public java.lang.String getThreadAttachmentsDir() {
		return _mbMessage.getThreadAttachmentsDir();
	}

	public java.lang.String getWorkflowClassName() {
		return _mbMessage.getWorkflowClassName();
	}

	public boolean isDiscussion() {
		return _mbMessage.isDiscussion();
	}

	public boolean isReply() {
		return _mbMessage.isReply();
	}

	public boolean isRoot() {
		return _mbMessage.isRoot();
	}

	public void setAttachmentsDir(java.lang.String attachmentsDir) {
		_mbMessage.setAttachmentsDir(attachmentsDir);
	}

	public MBMessage getWrappedMBMessage() {
		return _mbMessage;
	}

	private MBMessage _mbMessage;
}