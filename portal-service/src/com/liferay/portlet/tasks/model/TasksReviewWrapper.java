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

	public long getPrimaryKey() {
		return _tasksReview.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_tasksReview.setPrimaryKey(pk);
	}

	public long getReviewId() {
		return _tasksReview.getReviewId();
	}

	public void setReviewId(long reviewId) {
		_tasksReview.setReviewId(reviewId);
	}

	public long getGroupId() {
		return _tasksReview.getGroupId();
	}

	public void setGroupId(long groupId) {
		_tasksReview.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _tasksReview.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_tasksReview.setCompanyId(companyId);
	}

	public long getUserId() {
		return _tasksReview.getUserId();
	}

	public void setUserId(long userId) {
		_tasksReview.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReview.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_tasksReview.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _tasksReview.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_tasksReview.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _tasksReview.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_tasksReview.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _tasksReview.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_tasksReview.setModifiedDate(modifiedDate);
	}

	public long getProposalId() {
		return _tasksReview.getProposalId();
	}

	public void setProposalId(long proposalId) {
		_tasksReview.setProposalId(proposalId);
	}

	public long getAssignedByUserId() {
		return _tasksReview.getAssignedByUserId();
	}

	public void setAssignedByUserId(long assignedByUserId) {
		_tasksReview.setAssignedByUserId(assignedByUserId);
	}

	public java.lang.String getAssignedByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksReview.getAssignedByUserUuid();
	}

	public void setAssignedByUserUuid(java.lang.String assignedByUserUuid) {
		_tasksReview.setAssignedByUserUuid(assignedByUserUuid);
	}

	public java.lang.String getAssignedByUserName() {
		return _tasksReview.getAssignedByUserName();
	}

	public void setAssignedByUserName(java.lang.String assignedByUserName) {
		_tasksReview.setAssignedByUserName(assignedByUserName);
	}

	public int getStage() {
		return _tasksReview.getStage();
	}

	public void setStage(int stage) {
		_tasksReview.setStage(stage);
	}

	public boolean getCompleted() {
		return _tasksReview.getCompleted();
	}

	public boolean isCompleted() {
		return _tasksReview.isCompleted();
	}

	public void setCompleted(boolean completed) {
		_tasksReview.setCompleted(completed);
	}

	public boolean getRejected() {
		return _tasksReview.getRejected();
	}

	public boolean isRejected() {
		return _tasksReview.isRejected();
	}

	public void setRejected(boolean rejected) {
		_tasksReview.setRejected(rejected);
	}

	public com.liferay.portlet.tasks.model.TasksReview toEscapedModel() {
		return _tasksReview.toEscapedModel();
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
		return _tasksReview.clone();
	}

	public int compareTo(
		com.liferay.portlet.tasks.model.TasksReview tasksReview) {
		return _tasksReview.compareTo(tasksReview);
	}

	public int hashCode() {
		return _tasksReview.hashCode();
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