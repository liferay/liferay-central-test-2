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

package com.liferay.portlet.tasks.model;


/**
 * <a href="TasksReviewSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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
		throws com.liferay.portal.SystemException {
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
		throws com.liferay.portal.SystemException {
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

	public boolean setNew(boolean n) {
		return _tasksReview.setNew(n);
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