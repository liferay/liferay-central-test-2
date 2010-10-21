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
 * This class is a wrapper for {@link Branch}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Branch
 * @generated
 */
public class BranchWrapper implements Branch {
	public BranchWrapper(Branch branch) {
		_branch = branch;
	}

	/**
	* Gets the primary key of this branch.
	*
	* @return the primary key of this branch
	*/
	public long getPrimaryKey() {
		return _branch.getPrimaryKey();
	}

	/**
	* Sets the primary key of this branch
	*
	* @param pk the primary key of this branch
	*/
	public void setPrimaryKey(long pk) {
		_branch.setPrimaryKey(pk);
	}

	/**
	* Gets the branch id of this branch.
	*
	* @return the branch id of this branch
	*/
	public long getBranchId() {
		return _branch.getBranchId();
	}

	/**
	* Sets the branch id of this branch.
	*
	* @param branchId the branch id of this branch
	*/
	public void setBranchId(long branchId) {
		_branch.setBranchId(branchId);
	}

	/**
	* Gets the group id of this branch.
	*
	* @return the group id of this branch
	*/
	public long getGroupId() {
		return _branch.getGroupId();
	}

	/**
	* Sets the group id of this branch.
	*
	* @param groupId the group id of this branch
	*/
	public void setGroupId(long groupId) {
		_branch.setGroupId(groupId);
	}

	/**
	* Gets the company id of this branch.
	*
	* @return the company id of this branch
	*/
	public long getCompanyId() {
		return _branch.getCompanyId();
	}

	/**
	* Sets the company id of this branch.
	*
	* @param companyId the company id of this branch
	*/
	public void setCompanyId(long companyId) {
		_branch.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this branch.
	*
	* @return the user id of this branch
	*/
	public long getUserId() {
		return _branch.getUserId();
	}

	/**
	* Sets the user id of this branch.
	*
	* @param userId the user id of this branch
	*/
	public void setUserId(long userId) {
		_branch.setUserId(userId);
	}

	/**
	* Gets the user uuid of this branch.
	*
	* @return the user uuid of this branch
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _branch.getUserUuid();
	}

	/**
	* Sets the user uuid of this branch.
	*
	* @param userUuid the user uuid of this branch
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_branch.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this branch.
	*
	* @return the user name of this branch
	*/
	public java.lang.String getUserName() {
		return _branch.getUserName();
	}

	/**
	* Sets the user name of this branch.
	*
	* @param userName the user name of this branch
	*/
	public void setUserName(java.lang.String userName) {
		_branch.setUserName(userName);
	}

	/**
	* Gets the create date of this branch.
	*
	* @return the create date of this branch
	*/
	public java.util.Date getCreateDate() {
		return _branch.getCreateDate();
	}

	/**
	* Sets the create date of this branch.
	*
	* @param createDate the create date of this branch
	*/
	public void setCreateDate(java.util.Date createDate) {
		_branch.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this branch.
	*
	* @return the modified date of this branch
	*/
	public java.util.Date getModifiedDate() {
		return _branch.getModifiedDate();
	}

	/**
	* Sets the modified date of this branch.
	*
	* @param modifiedDate the modified date of this branch
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_branch.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the name of this branch.
	*
	* @return the name of this branch
	*/
	public java.lang.String getName() {
		return _branch.getName();
	}

	/**
	* Sets the name of this branch.
	*
	* @param name the name of this branch
	*/
	public void setName(java.lang.String name) {
		_branch.setName(name);
	}

	/**
	* Gets the description of this branch.
	*
	* @return the description of this branch
	*/
	public java.lang.String getDescription() {
		return _branch.getDescription();
	}

	/**
	* Sets the description of this branch.
	*
	* @param description the description of this branch
	*/
	public void setDescription(java.lang.String description) {
		_branch.setDescription(description);
	}

	public boolean isNew() {
		return _branch.isNew();
	}

	public void setNew(boolean n) {
		_branch.setNew(n);
	}

	public boolean isCachedModel() {
		return _branch.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_branch.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _branch.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_branch.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _branch.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _branch.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_branch.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _branch.clone();
	}

	public int compareTo(com.liferay.portal.model.Branch branch) {
		return _branch.compareTo(branch);
	}

	public int hashCode() {
		return _branch.hashCode();
	}

	public com.liferay.portal.model.Branch toEscapedModel() {
		return _branch.toEscapedModel();
	}

	public java.lang.String toString() {
		return _branch.toString();
	}

	public java.lang.String toXmlString() {
		return _branch.toXmlString();
	}

	public boolean isMaster() {
		return _branch.isMaster();
	}

	public Branch getWrappedBranch() {
		return _branch;
	}

	private Branch _branch;
}