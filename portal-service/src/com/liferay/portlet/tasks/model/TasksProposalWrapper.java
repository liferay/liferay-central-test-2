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
 * This class is a wrapper for {@link TasksProposal}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksProposal
 * @generated
 */
public class TasksProposalWrapper implements TasksProposal {
	public TasksProposalWrapper(TasksProposal tasksProposal) {
		_tasksProposal = tasksProposal;
	}

	public long getPrimaryKey() {
		return _tasksProposal.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_tasksProposal.setPrimaryKey(pk);
	}

	public long getProposalId() {
		return _tasksProposal.getProposalId();
	}

	public void setProposalId(long proposalId) {
		_tasksProposal.setProposalId(proposalId);
	}

	public long getGroupId() {
		return _tasksProposal.getGroupId();
	}

	public void setGroupId(long groupId) {
		_tasksProposal.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _tasksProposal.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_tasksProposal.setCompanyId(companyId);
	}

	public long getUserId() {
		return _tasksProposal.getUserId();
	}

	public void setUserId(long userId) {
		_tasksProposal.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposal.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_tasksProposal.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _tasksProposal.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_tasksProposal.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _tasksProposal.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_tasksProposal.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _tasksProposal.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_tasksProposal.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _tasksProposal.getClassName();
	}

	public long getClassNameId() {
		return _tasksProposal.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_tasksProposal.setClassNameId(classNameId);
	}

	public java.lang.String getClassPK() {
		return _tasksProposal.getClassPK();
	}

	public void setClassPK(java.lang.String classPK) {
		_tasksProposal.setClassPK(classPK);
	}

	public java.lang.String getName() {
		return _tasksProposal.getName();
	}

	public void setName(java.lang.String name) {
		_tasksProposal.setName(name);
	}

	public java.lang.String getDescription() {
		return _tasksProposal.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_tasksProposal.setDescription(description);
	}

	public java.util.Date getPublishDate() {
		return _tasksProposal.getPublishDate();
	}

	public void setPublishDate(java.util.Date publishDate) {
		_tasksProposal.setPublishDate(publishDate);
	}

	public java.util.Date getDueDate() {
		return _tasksProposal.getDueDate();
	}

	public void setDueDate(java.util.Date dueDate) {
		_tasksProposal.setDueDate(dueDate);
	}

	public com.liferay.portlet.tasks.model.TasksProposal toEscapedModel() {
		return _tasksProposal.toEscapedModel();
	}

	public boolean isNew() {
		return _tasksProposal.isNew();
	}

	public void setNew(boolean n) {
		_tasksProposal.setNew(n);
	}

	public boolean isCachedModel() {
		return _tasksProposal.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_tasksProposal.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _tasksProposal.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_tasksProposal.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _tasksProposal.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _tasksProposal.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_tasksProposal.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _tasksProposal.clone();
	}

	public int compareTo(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal) {
		return _tasksProposal.compareTo(tasksProposal);
	}

	public int hashCode() {
		return _tasksProposal.hashCode();
	}

	public java.lang.String toString() {
		return _tasksProposal.toString();
	}

	public java.lang.String toXmlString() {
		return _tasksProposal.toXmlString();
	}

	public java.lang.String getStatus(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposal.getStatus(locale);
	}

	public TasksProposal getWrappedTasksProposal() {
		return _tasksProposal;
	}

	private TasksProposal _tasksProposal;
}