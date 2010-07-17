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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link MembershipRequest}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MembershipRequest
 * @generated
 */
public class MembershipRequestWrapper implements MembershipRequest {
	public MembershipRequestWrapper(MembershipRequest membershipRequest) {
		_membershipRequest = membershipRequest;
	}

	public long getPrimaryKey() {
		return _membershipRequest.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_membershipRequest.setPrimaryKey(pk);
	}

	public long getMembershipRequestId() {
		return _membershipRequest.getMembershipRequestId();
	}

	public void setMembershipRequestId(long membershipRequestId) {
		_membershipRequest.setMembershipRequestId(membershipRequestId);
	}

	public long getCompanyId() {
		return _membershipRequest.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_membershipRequest.setCompanyId(companyId);
	}

	public long getUserId() {
		return _membershipRequest.getUserId();
	}

	public void setUserId(long userId) {
		_membershipRequest.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequest.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_membershipRequest.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _membershipRequest.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_membershipRequest.setCreateDate(createDate);
	}

	public long getGroupId() {
		return _membershipRequest.getGroupId();
	}

	public void setGroupId(long groupId) {
		_membershipRequest.setGroupId(groupId);
	}

	public java.lang.String getComments() {
		return _membershipRequest.getComments();
	}

	public void setComments(java.lang.String comments) {
		_membershipRequest.setComments(comments);
	}

	public java.lang.String getReplyComments() {
		return _membershipRequest.getReplyComments();
	}

	public void setReplyComments(java.lang.String replyComments) {
		_membershipRequest.setReplyComments(replyComments);
	}

	public java.util.Date getReplyDate() {
		return _membershipRequest.getReplyDate();
	}

	public void setReplyDate(java.util.Date replyDate) {
		_membershipRequest.setReplyDate(replyDate);
	}

	public long getReplierUserId() {
		return _membershipRequest.getReplierUserId();
	}

	public void setReplierUserId(long replierUserId) {
		_membershipRequest.setReplierUserId(replierUserId);
	}

	public java.lang.String getReplierUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequest.getReplierUserUuid();
	}

	public void setReplierUserUuid(java.lang.String replierUserUuid) {
		_membershipRequest.setReplierUserUuid(replierUserUuid);
	}

	public int getStatusId() {
		return _membershipRequest.getStatusId();
	}

	public void setStatusId(int statusId) {
		_membershipRequest.setStatusId(statusId);
	}

	public com.liferay.portal.model.MembershipRequest toEscapedModel() {
		return _membershipRequest.toEscapedModel();
	}

	public boolean isNew() {
		return _membershipRequest.isNew();
	}

	public void setNew(boolean n) {
		_membershipRequest.setNew(n);
	}

	public boolean isCachedModel() {
		return _membershipRequest.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_membershipRequest.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _membershipRequest.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_membershipRequest.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _membershipRequest.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _membershipRequest.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_membershipRequest.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _membershipRequest.clone();
	}

	public int compareTo(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		return _membershipRequest.compareTo(membershipRequest);
	}

	public int hashCode() {
		return _membershipRequest.hashCode();
	}

	public java.lang.String toString() {
		return _membershipRequest.toString();
	}

	public java.lang.String toXmlString() {
		return _membershipRequest.toXmlString();
	}

	public MembershipRequest getWrappedMembershipRequest() {
		return _membershipRequest;
	}

	private MembershipRequest _membershipRequest;
}