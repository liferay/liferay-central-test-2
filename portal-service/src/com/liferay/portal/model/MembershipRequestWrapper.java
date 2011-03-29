/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

	public Class<?> getModelClass() {
		return MembershipRequest.class;
	}

	public String getModelClassName() {
		return MembershipRequest.class.getName();
	}

	/**
	* Gets the primary key of this membership request.
	*
	* @return the primary key of this membership request
	*/
	public long getPrimaryKey() {
		return _membershipRequest.getPrimaryKey();
	}

	/**
	* Sets the primary key of this membership request
	*
	* @param pk the primary key of this membership request
	*/
	public void setPrimaryKey(long pk) {
		_membershipRequest.setPrimaryKey(pk);
	}

	/**
	* Gets the membership request ID of this membership request.
	*
	* @return the membership request ID of this membership request
	*/
	public long getMembershipRequestId() {
		return _membershipRequest.getMembershipRequestId();
	}

	/**
	* Sets the membership request ID of this membership request.
	*
	* @param membershipRequestId the membership request ID of this membership request
	*/
	public void setMembershipRequestId(long membershipRequestId) {
		_membershipRequest.setMembershipRequestId(membershipRequestId);
	}

	/**
	* Gets the group ID of this membership request.
	*
	* @return the group ID of this membership request
	*/
	public long getGroupId() {
		return _membershipRequest.getGroupId();
	}

	/**
	* Sets the group ID of this membership request.
	*
	* @param groupId the group ID of this membership request
	*/
	public void setGroupId(long groupId) {
		_membershipRequest.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this membership request.
	*
	* @return the company ID of this membership request
	*/
	public long getCompanyId() {
		return _membershipRequest.getCompanyId();
	}

	/**
	* Sets the company ID of this membership request.
	*
	* @param companyId the company ID of this membership request
	*/
	public void setCompanyId(long companyId) {
		_membershipRequest.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this membership request.
	*
	* @return the user ID of this membership request
	*/
	public long getUserId() {
		return _membershipRequest.getUserId();
	}

	/**
	* Sets the user ID of this membership request.
	*
	* @param userId the user ID of this membership request
	*/
	public void setUserId(long userId) {
		_membershipRequest.setUserId(userId);
	}

	/**
	* Gets the user uuid of this membership request.
	*
	* @return the user uuid of this membership request
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequest.getUserUuid();
	}

	/**
	* Sets the user uuid of this membership request.
	*
	* @param userUuid the user uuid of this membership request
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_membershipRequest.setUserUuid(userUuid);
	}

	/**
	* Gets the create date of this membership request.
	*
	* @return the create date of this membership request
	*/
	public java.util.Date getCreateDate() {
		return _membershipRequest.getCreateDate();
	}

	/**
	* Sets the create date of this membership request.
	*
	* @param createDate the create date of this membership request
	*/
	public void setCreateDate(java.util.Date createDate) {
		_membershipRequest.setCreateDate(createDate);
	}

	/**
	* Gets the comments of this membership request.
	*
	* @return the comments of this membership request
	*/
	public java.lang.String getComments() {
		return _membershipRequest.getComments();
	}

	/**
	* Sets the comments of this membership request.
	*
	* @param comments the comments of this membership request
	*/
	public void setComments(java.lang.String comments) {
		_membershipRequest.setComments(comments);
	}

	/**
	* Gets the reply comments of this membership request.
	*
	* @return the reply comments of this membership request
	*/
	public java.lang.String getReplyComments() {
		return _membershipRequest.getReplyComments();
	}

	/**
	* Sets the reply comments of this membership request.
	*
	* @param replyComments the reply comments of this membership request
	*/
	public void setReplyComments(java.lang.String replyComments) {
		_membershipRequest.setReplyComments(replyComments);
	}

	/**
	* Gets the reply date of this membership request.
	*
	* @return the reply date of this membership request
	*/
	public java.util.Date getReplyDate() {
		return _membershipRequest.getReplyDate();
	}

	/**
	* Sets the reply date of this membership request.
	*
	* @param replyDate the reply date of this membership request
	*/
	public void setReplyDate(java.util.Date replyDate) {
		_membershipRequest.setReplyDate(replyDate);
	}

	/**
	* Gets the replier user ID of this membership request.
	*
	* @return the replier user ID of this membership request
	*/
	public long getReplierUserId() {
		return _membershipRequest.getReplierUserId();
	}

	/**
	* Sets the replier user ID of this membership request.
	*
	* @param replierUserId the replier user ID of this membership request
	*/
	public void setReplierUserId(long replierUserId) {
		_membershipRequest.setReplierUserId(replierUserId);
	}

	/**
	* Gets the replier user uuid of this membership request.
	*
	* @return the replier user uuid of this membership request
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getReplierUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequest.getReplierUserUuid();
	}

	/**
	* Sets the replier user uuid of this membership request.
	*
	* @param replierUserUuid the replier user uuid of this membership request
	*/
	public void setReplierUserUuid(java.lang.String replierUserUuid) {
		_membershipRequest.setReplierUserUuid(replierUserUuid);
	}

	/**
	* Gets the status ID of this membership request.
	*
	* @return the status ID of this membership request
	*/
	public int getStatusId() {
		return _membershipRequest.getStatusId();
	}

	/**
	* Sets the status ID of this membership request.
	*
	* @param statusId the status ID of this membership request
	*/
	public void setStatusId(int statusId) {
		_membershipRequest.setStatusId(statusId);
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
		return new MembershipRequestWrapper((MembershipRequest)_membershipRequest.clone());
	}

	public int compareTo(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		return _membershipRequest.compareTo(membershipRequest);
	}

	public int hashCode() {
		return _membershipRequest.hashCode();
	}

	public com.liferay.portal.model.MembershipRequest toEscapedModel() {
		return new MembershipRequestWrapper(_membershipRequest.toEscapedModel());
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

	public void resetOriginalValues() {
		_membershipRequest.resetOriginalValues();
	}

	private MembershipRequest _membershipRequest;
}