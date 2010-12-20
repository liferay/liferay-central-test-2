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

	/**
	* Gets the primary key of this tasks proposal.
	*
	* @return the primary key of this tasks proposal
	*/
	public long getPrimaryKey() {
		return _tasksProposal.getPrimaryKey();
	}

	/**
	* Sets the primary key of this tasks proposal
	*
	* @param pk the primary key of this tasks proposal
	*/
	public void setPrimaryKey(long pk) {
		_tasksProposal.setPrimaryKey(pk);
	}

	/**
	* Gets the proposal ID of this tasks proposal.
	*
	* @return the proposal ID of this tasks proposal
	*/
	public long getProposalId() {
		return _tasksProposal.getProposalId();
	}

	/**
	* Sets the proposal ID of this tasks proposal.
	*
	* @param proposalId the proposal ID of this tasks proposal
	*/
	public void setProposalId(long proposalId) {
		_tasksProposal.setProposalId(proposalId);
	}

	/**
	* Gets the group ID of this tasks proposal.
	*
	* @return the group ID of this tasks proposal
	*/
	public long getGroupId() {
		return _tasksProposal.getGroupId();
	}

	/**
	* Sets the group ID of this tasks proposal.
	*
	* @param groupId the group ID of this tasks proposal
	*/
	public void setGroupId(long groupId) {
		_tasksProposal.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this tasks proposal.
	*
	* @return the company ID of this tasks proposal
	*/
	public long getCompanyId() {
		return _tasksProposal.getCompanyId();
	}

	/**
	* Sets the company ID of this tasks proposal.
	*
	* @param companyId the company ID of this tasks proposal
	*/
	public void setCompanyId(long companyId) {
		_tasksProposal.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this tasks proposal.
	*
	* @return the user ID of this tasks proposal
	*/
	public long getUserId() {
		return _tasksProposal.getUserId();
	}

	/**
	* Sets the user ID of this tasks proposal.
	*
	* @param userId the user ID of this tasks proposal
	*/
	public void setUserId(long userId) {
		_tasksProposal.setUserId(userId);
	}

	/**
	* Gets the user uuid of this tasks proposal.
	*
	* @return the user uuid of this tasks proposal
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposal.getUserUuid();
	}

	/**
	* Sets the user uuid of this tasks proposal.
	*
	* @param userUuid the user uuid of this tasks proposal
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_tasksProposal.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this tasks proposal.
	*
	* @return the user name of this tasks proposal
	*/
	public java.lang.String getUserName() {
		return _tasksProposal.getUserName();
	}

	/**
	* Sets the user name of this tasks proposal.
	*
	* @param userName the user name of this tasks proposal
	*/
	public void setUserName(java.lang.String userName) {
		_tasksProposal.setUserName(userName);
	}

	/**
	* Gets the create date of this tasks proposal.
	*
	* @return the create date of this tasks proposal
	*/
	public java.util.Date getCreateDate() {
		return _tasksProposal.getCreateDate();
	}

	/**
	* Sets the create date of this tasks proposal.
	*
	* @param createDate the create date of this tasks proposal
	*/
	public void setCreateDate(java.util.Date createDate) {
		_tasksProposal.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this tasks proposal.
	*
	* @return the modified date of this tasks proposal
	*/
	public java.util.Date getModifiedDate() {
		return _tasksProposal.getModifiedDate();
	}

	/**
	* Sets the modified date of this tasks proposal.
	*
	* @param modifiedDate the modified date of this tasks proposal
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_tasksProposal.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the class name of the model instance this tasks proposal is polymorphically associated with.
	*
	* @return the class name of the model instance this tasks proposal is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _tasksProposal.getClassName();
	}

	/**
	* Gets the class name ID of this tasks proposal.
	*
	* @return the class name ID of this tasks proposal
	*/
	public long getClassNameId() {
		return _tasksProposal.getClassNameId();
	}

	/**
	* Sets the class name ID of this tasks proposal.
	*
	* @param classNameId the class name ID of this tasks proposal
	*/
	public void setClassNameId(long classNameId) {
		_tasksProposal.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this tasks proposal.
	*
	* @return the class p k of this tasks proposal
	*/
	public java.lang.String getClassPK() {
		return _tasksProposal.getClassPK();
	}

	/**
	* Sets the class p k of this tasks proposal.
	*
	* @param classPK the class p k of this tasks proposal
	*/
	public void setClassPK(java.lang.String classPK) {
		_tasksProposal.setClassPK(classPK);
	}

	/**
	* Gets the name of this tasks proposal.
	*
	* @return the name of this tasks proposal
	*/
	public java.lang.String getName() {
		return _tasksProposal.getName();
	}

	/**
	* Sets the name of this tasks proposal.
	*
	* @param name the name of this tasks proposal
	*/
	public void setName(java.lang.String name) {
		_tasksProposal.setName(name);
	}

	/**
	* Gets the description of this tasks proposal.
	*
	* @return the description of this tasks proposal
	*/
	public java.lang.String getDescription() {
		return _tasksProposal.getDescription();
	}

	/**
	* Sets the description of this tasks proposal.
	*
	* @param description the description of this tasks proposal
	*/
	public void setDescription(java.lang.String description) {
		_tasksProposal.setDescription(description);
	}

	/**
	* Gets the publish date of this tasks proposal.
	*
	* @return the publish date of this tasks proposal
	*/
	public java.util.Date getPublishDate() {
		return _tasksProposal.getPublishDate();
	}

	/**
	* Sets the publish date of this tasks proposal.
	*
	* @param publishDate the publish date of this tasks proposal
	*/
	public void setPublishDate(java.util.Date publishDate) {
		_tasksProposal.setPublishDate(publishDate);
	}

	/**
	* Gets the due date of this tasks proposal.
	*
	* @return the due date of this tasks proposal
	*/
	public java.util.Date getDueDate() {
		return _tasksProposal.getDueDate();
	}

	/**
	* Sets the due date of this tasks proposal.
	*
	* @param dueDate the due date of this tasks proposal
	*/
	public void setDueDate(java.util.Date dueDate) {
		_tasksProposal.setDueDate(dueDate);
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
		return new TasksProposalWrapper((TasksProposal)_tasksProposal.clone());
	}

	public int compareTo(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal) {
		return _tasksProposal.compareTo(tasksProposal);
	}

	public int hashCode() {
		return _tasksProposal.hashCode();
	}

	public com.liferay.portlet.tasks.model.TasksProposal toEscapedModel() {
		return new TasksProposalWrapper(_tasksProposal.toEscapedModel());
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