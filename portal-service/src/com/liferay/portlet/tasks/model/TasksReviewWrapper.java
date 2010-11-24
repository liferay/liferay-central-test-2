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

package com.liferay.portlet.tasks.model;

/**
 * <p>
 * This class is a wrapper for {@link TasksReview}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksReview
 * @generated
 */
public class TasksReviewWrapper implements TasksReview {
	public TasksReviewWrapper(TasksReview tasksReview) {
		_tasksReview = tasksReview;
	}

	/**
	* Gets the primary key of this tasks review.
	*
	* @return the primary key of this tasks review
	*/
	public long getPrimaryKey() {
		return _tasksReview.getPrimaryKey();
	}

	/**
	* Sets the primary key of this tasks review
	*
	* @param pk the primary key of this tasks review
	*/
	public void setPrimaryKey(long pk) {
		_tasksReview.setPrimaryKey(pk);
	}

	/**
	* Gets the review id of this tasks review.
	*
	* @return the review id of this tasks review
	*/
	public long getReviewId() {
		return _tasksReview.getReviewId();
	}

	/**
	* Sets the review id of this tasks review.
	*
	* @param reviewId the review id of this tasks review
	*/
	public void setReviewId(long reviewId) {
		_tasksReview.setReviewId(reviewId);
	}

	/**
	* Gets the group id of this tasks review.
	*
	* @return the group id of this tasks review
	*/
	public long getGroupId() {
		return _tasksReview.getGroupId();
	}

	/**
	* Sets the group id of this tasks review.
	*
	* @param groupId the group id of this tasks review
	*/
	public void setGroupId(long groupId) {
		_tasksReview.setGroupId(groupId);
	}

	/**
	* Gets the company id of this tasks review.
	*
	* @return the company id of this tasks review
	*/
	public long getCompanyId() {
		return _tasksReview.getCompanyId();
	}

	/**
	* Sets the company id of this tasks review.
	*
	* @param companyId the company id of this tasks review
	*/
	public void setCompanyId(long companyId) {
		_tasksReview.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this tasks review.
	*
	* @return the user id of this tasks review
	*/
	public long getUserId() {
		return _tasksReview.getUserId();
	}

	/**
	* Sets the user id of this tasks review.
	*
	* @param userId the user id of this tasks review
	*/
	public void setUserId(long userId) {
		_tasksReview.setUserId(userId);
	}

	/**
	* Gets the user uuid of this tasks review.
	*
	* @return the user uuid of this tasks review
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReview.getUserUuid();
	}

	/**
	* Sets the user uuid of this tasks review.
	*
	* @param userUuid the user uuid of this tasks review
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_tasksReview.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this tasks review.
	*
	* @return the user name of this tasks review
	*/
	public java.lang.String getUserName() {
		return _tasksReview.getUserName();
	}

	/**
	* Sets the user name of this tasks review.
	*
	* @param userName the user name of this tasks review
	*/
	public void setUserName(java.lang.String userName) {
		_tasksReview.setUserName(userName);
	}

	/**
	* Gets the create date of this tasks review.
	*
	* @return the create date of this tasks review
	*/
	public java.util.Date getCreateDate() {
		return _tasksReview.getCreateDate();
	}

	/**
	* Sets the create date of this tasks review.
	*
	* @param createDate the create date of this tasks review
	*/
	public void setCreateDate(java.util.Date createDate) {
		_tasksReview.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this tasks review.
	*
	* @return the modified date of this tasks review
	*/
	public java.util.Date getModifiedDate() {
		return _tasksReview.getModifiedDate();
	}

	/**
	* Sets the modified date of this tasks review.
	*
	* @param modifiedDate the modified date of this tasks review
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_tasksReview.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the proposal id of this tasks review.
	*
	* @return the proposal id of this tasks review
	*/
	public long getProposalId() {
		return _tasksReview.getProposalId();
	}

	/**
	* Sets the proposal id of this tasks review.
	*
	* @param proposalId the proposal id of this tasks review
	*/
	public void setProposalId(long proposalId) {
		_tasksReview.setProposalId(proposalId);
	}

	/**
	* Gets the assigned by user id of this tasks review.
	*
	* @return the assigned by user id of this tasks review
	*/
	public long getAssignedByUserId() {
		return _tasksReview.getAssignedByUserId();
	}

	/**
	* Sets the assigned by user id of this tasks review.
	*
	* @param assignedByUserId the assigned by user id of this tasks review
	*/
	public void setAssignedByUserId(long assignedByUserId) {
		_tasksReview.setAssignedByUserId(assignedByUserId);
	}

	/**
	* Gets the assigned by user uuid of this tasks review.
	*
	* @return the assigned by user uuid of this tasks review
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getAssignedByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReview.getAssignedByUserUuid();
	}

	/**
	* Sets the assigned by user uuid of this tasks review.
	*
	* @param assignedByUserUuid the assigned by user uuid of this tasks review
	*/
	public void setAssignedByUserUuid(java.lang.String assignedByUserUuid) {
		_tasksReview.setAssignedByUserUuid(assignedByUserUuid);
	}

	/**
	* Gets the assigned by user name of this tasks review.
	*
	* @return the assigned by user name of this tasks review
	*/
	public java.lang.String getAssignedByUserName() {
		return _tasksReview.getAssignedByUserName();
	}

	/**
	* Sets the assigned by user name of this tasks review.
	*
	* @param assignedByUserName the assigned by user name of this tasks review
	*/
	public void setAssignedByUserName(java.lang.String assignedByUserName) {
		_tasksReview.setAssignedByUserName(assignedByUserName);
	}

	/**
	* Gets the stage of this tasks review.
	*
	* @return the stage of this tasks review
	*/
	public int getStage() {
		return _tasksReview.getStage();
	}

	/**
	* Sets the stage of this tasks review.
	*
	* @param stage the stage of this tasks review
	*/
	public void setStage(int stage) {
		_tasksReview.setStage(stage);
	}

	/**
	* Gets the completed of this tasks review.
	*
	* @return the completed of this tasks review
	*/
	public boolean getCompleted() {
		return _tasksReview.getCompleted();
	}

	/**
	* Determines if this tasks review is completed.
	*
	* @return <code>true</code> if this tasks review is completed; <code>false</code> otherwise
	*/
	public boolean isCompleted() {
		return _tasksReview.isCompleted();
	}

	/**
	* Sets whether this tasks review is completed.
	*
	* @param completed the completed of this tasks review
	*/
	public void setCompleted(boolean completed) {
		_tasksReview.setCompleted(completed);
	}

	/**
	* Gets the rejected of this tasks review.
	*
	* @return the rejected of this tasks review
	*/
	public boolean getRejected() {
		return _tasksReview.getRejected();
	}

	/**
	* Determines if this tasks review is rejected.
	*
	* @return <code>true</code> if this tasks review is rejected; <code>false</code> otherwise
	*/
	public boolean isRejected() {
		return _tasksReview.isRejected();
	}

	/**
	* Sets whether this tasks review is rejected.
	*
	* @param rejected the rejected of this tasks review
	*/
	public void setRejected(boolean rejected) {
		_tasksReview.setRejected(rejected);
	}

	public boolean isNew() {
		return _tasksReview.isNew();
	}

	public void setNew(boolean n) {
		_tasksReview.setNew(n);
	}

	public boolean isCachedModel() {
		return _tasksReview.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_tasksReview.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _tasksReview.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_tasksReview.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _tasksReview.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _tasksReview.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_tasksReview.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new TasksReviewWrapper((TasksReview)_tasksReview.clone());
	}

	public int compareTo(
		com.liferay.portlet.tasks.model.TasksReview tasksReview) {
		return _tasksReview.compareTo(tasksReview);
	}

	public int hashCode() {
		return _tasksReview.hashCode();
	}

	public com.liferay.portlet.tasks.model.TasksReview toEscapedModel() {
		return new TasksReviewWrapper(_tasksReview.toEscapedModel());
	}

	public java.lang.String toString() {
		return _tasksReview.toString();
	}

	public java.lang.String toXmlString() {
		return _tasksReview.toXmlString();
	}

	public TasksReview getWrappedTasksReview() {
		return _tasksReview;
	}

	private TasksReview _tasksReview;
}