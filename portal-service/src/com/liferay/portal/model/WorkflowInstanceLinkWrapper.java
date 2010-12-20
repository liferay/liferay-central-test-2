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
 * This class is a wrapper for {@link WorkflowInstanceLink}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowInstanceLink
 * @generated
 */
public class WorkflowInstanceLinkWrapper implements WorkflowInstanceLink {
	public WorkflowInstanceLinkWrapper(
		WorkflowInstanceLink workflowInstanceLink) {
		_workflowInstanceLink = workflowInstanceLink;
	}

	/**
	* Gets the primary key of this workflow instance link.
	*
	* @return the primary key of this workflow instance link
	*/
	public long getPrimaryKey() {
		return _workflowInstanceLink.getPrimaryKey();
	}

	/**
	* Sets the primary key of this workflow instance link
	*
	* @param pk the primary key of this workflow instance link
	*/
	public void setPrimaryKey(long pk) {
		_workflowInstanceLink.setPrimaryKey(pk);
	}

	/**
	* Gets the workflow instance link ID of this workflow instance link.
	*
	* @return the workflow instance link ID of this workflow instance link
	*/
	public long getWorkflowInstanceLinkId() {
		return _workflowInstanceLink.getWorkflowInstanceLinkId();
	}

	/**
	* Sets the workflow instance link ID of this workflow instance link.
	*
	* @param workflowInstanceLinkId the workflow instance link ID of this workflow instance link
	*/
	public void setWorkflowInstanceLinkId(long workflowInstanceLinkId) {
		_workflowInstanceLink.setWorkflowInstanceLinkId(workflowInstanceLinkId);
	}

	/**
	* Gets the group ID of this workflow instance link.
	*
	* @return the group ID of this workflow instance link
	*/
	public long getGroupId() {
		return _workflowInstanceLink.getGroupId();
	}

	/**
	* Sets the group ID of this workflow instance link.
	*
	* @param groupId the group ID of this workflow instance link
	*/
	public void setGroupId(long groupId) {
		_workflowInstanceLink.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this workflow instance link.
	*
	* @return the company ID of this workflow instance link
	*/
	public long getCompanyId() {
		return _workflowInstanceLink.getCompanyId();
	}

	/**
	* Sets the company ID of this workflow instance link.
	*
	* @param companyId the company ID of this workflow instance link
	*/
	public void setCompanyId(long companyId) {
		_workflowInstanceLink.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this workflow instance link.
	*
	* @return the user ID of this workflow instance link
	*/
	public long getUserId() {
		return _workflowInstanceLink.getUserId();
	}

	/**
	* Sets the user ID of this workflow instance link.
	*
	* @param userId the user ID of this workflow instance link
	*/
	public void setUserId(long userId) {
		_workflowInstanceLink.setUserId(userId);
	}

	/**
	* Gets the user uuid of this workflow instance link.
	*
	* @return the user uuid of this workflow instance link
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLink.getUserUuid();
	}

	/**
	* Sets the user uuid of this workflow instance link.
	*
	* @param userUuid the user uuid of this workflow instance link
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_workflowInstanceLink.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this workflow instance link.
	*
	* @return the user name of this workflow instance link
	*/
	public java.lang.String getUserName() {
		return _workflowInstanceLink.getUserName();
	}

	/**
	* Sets the user name of this workflow instance link.
	*
	* @param userName the user name of this workflow instance link
	*/
	public void setUserName(java.lang.String userName) {
		_workflowInstanceLink.setUserName(userName);
	}

	/**
	* Gets the create date of this workflow instance link.
	*
	* @return the create date of this workflow instance link
	*/
	public java.util.Date getCreateDate() {
		return _workflowInstanceLink.getCreateDate();
	}

	/**
	* Sets the create date of this workflow instance link.
	*
	* @param createDate the create date of this workflow instance link
	*/
	public void setCreateDate(java.util.Date createDate) {
		_workflowInstanceLink.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this workflow instance link.
	*
	* @return the modified date of this workflow instance link
	*/
	public java.util.Date getModifiedDate() {
		return _workflowInstanceLink.getModifiedDate();
	}

	/**
	* Sets the modified date of this workflow instance link.
	*
	* @param modifiedDate the modified date of this workflow instance link
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_workflowInstanceLink.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the class name of the model instance this workflow instance link is polymorphically associated with.
	*
	* @return the class name of the model instance this workflow instance link is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _workflowInstanceLink.getClassName();
	}

	/**
	* Gets the class name ID of this workflow instance link.
	*
	* @return the class name ID of this workflow instance link
	*/
	public long getClassNameId() {
		return _workflowInstanceLink.getClassNameId();
	}

	/**
	* Sets the class name ID of this workflow instance link.
	*
	* @param classNameId the class name ID of this workflow instance link
	*/
	public void setClassNameId(long classNameId) {
		_workflowInstanceLink.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this workflow instance link.
	*
	* @return the class p k of this workflow instance link
	*/
	public long getClassPK() {
		return _workflowInstanceLink.getClassPK();
	}

	/**
	* Sets the class p k of this workflow instance link.
	*
	* @param classPK the class p k of this workflow instance link
	*/
	public void setClassPK(long classPK) {
		_workflowInstanceLink.setClassPK(classPK);
	}

	/**
	* Gets the workflow instance ID of this workflow instance link.
	*
	* @return the workflow instance ID of this workflow instance link
	*/
	public long getWorkflowInstanceId() {
		return _workflowInstanceLink.getWorkflowInstanceId();
	}

	/**
	* Sets the workflow instance ID of this workflow instance link.
	*
	* @param workflowInstanceId the workflow instance ID of this workflow instance link
	*/
	public void setWorkflowInstanceId(long workflowInstanceId) {
		_workflowInstanceLink.setWorkflowInstanceId(workflowInstanceId);
	}

	public boolean isNew() {
		return _workflowInstanceLink.isNew();
	}

	public void setNew(boolean n) {
		_workflowInstanceLink.setNew(n);
	}

	public boolean isCachedModel() {
		return _workflowInstanceLink.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_workflowInstanceLink.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _workflowInstanceLink.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_workflowInstanceLink.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _workflowInstanceLink.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _workflowInstanceLink.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_workflowInstanceLink.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new WorkflowInstanceLinkWrapper((WorkflowInstanceLink)_workflowInstanceLink.clone());
	}

	public int compareTo(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink) {
		return _workflowInstanceLink.compareTo(workflowInstanceLink);
	}

	public int hashCode() {
		return _workflowInstanceLink.hashCode();
	}

	public com.liferay.portal.model.WorkflowInstanceLink toEscapedModel() {
		return new WorkflowInstanceLinkWrapper(_workflowInstanceLink.toEscapedModel());
	}

	public java.lang.String toString() {
		return _workflowInstanceLink.toString();
	}

	public java.lang.String toXmlString() {
		return _workflowInstanceLink.toXmlString();
	}

	public WorkflowInstanceLink getWrappedWorkflowInstanceLink() {
		return _workflowInstanceLink;
	}

	private WorkflowInstanceLink _workflowInstanceLink;
}