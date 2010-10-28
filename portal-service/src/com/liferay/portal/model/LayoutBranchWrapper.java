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
 * This class is a wrapper for {@link LayoutBranch}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutBranch
 * @generated
 */
public class LayoutBranchWrapper implements LayoutBranch {
	public LayoutBranchWrapper(LayoutBranch layoutBranch) {
		_layoutBranch = layoutBranch;
	}

	/**
	* Gets the primary key of this layout branch.
	*
	* @return the primary key of this layout branch
	*/
	public long getPrimaryKey() {
		return _layoutBranch.getPrimaryKey();
	}

	/**
	* Sets the primary key of this layout branch
	*
	* @param pk the primary key of this layout branch
	*/
	public void setPrimaryKey(long pk) {
		_layoutBranch.setPrimaryKey(pk);
	}

	/**
	* Gets the layout branch id of this layout branch.
	*
	* @return the layout branch id of this layout branch
	*/
	public long getLayoutBranchId() {
		return _layoutBranch.getLayoutBranchId();
	}

	/**
	* Sets the layout branch id of this layout branch.
	*
	* @param layoutBranchId the layout branch id of this layout branch
	*/
	public void setLayoutBranchId(long layoutBranchId) {
		_layoutBranch.setLayoutBranchId(layoutBranchId);
	}

	/**
	* Gets the group id of this layout branch.
	*
	* @return the group id of this layout branch
	*/
	public long getGroupId() {
		return _layoutBranch.getGroupId();
	}

	/**
	* Sets the group id of this layout branch.
	*
	* @param groupId the group id of this layout branch
	*/
	public void setGroupId(long groupId) {
		_layoutBranch.setGroupId(groupId);
	}

	/**
	* Gets the company id of this layout branch.
	*
	* @return the company id of this layout branch
	*/
	public long getCompanyId() {
		return _layoutBranch.getCompanyId();
	}

	/**
	* Sets the company id of this layout branch.
	*
	* @param companyId the company id of this layout branch
	*/
	public void setCompanyId(long companyId) {
		_layoutBranch.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this layout branch.
	*
	* @return the user id of this layout branch
	*/
	public long getUserId() {
		return _layoutBranch.getUserId();
	}

	/**
	* Sets the user id of this layout branch.
	*
	* @param userId the user id of this layout branch
	*/
	public void setUserId(long userId) {
		_layoutBranch.setUserId(userId);
	}

	/**
	* Gets the user uuid of this layout branch.
	*
	* @return the user uuid of this layout branch
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutBranch.getUserUuid();
	}

	/**
	* Sets the user uuid of this layout branch.
	*
	* @param userUuid the user uuid of this layout branch
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_layoutBranch.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this layout branch.
	*
	* @return the user name of this layout branch
	*/
	public java.lang.String getUserName() {
		return _layoutBranch.getUserName();
	}

	/**
	* Sets the user name of this layout branch.
	*
	* @param userName the user name of this layout branch
	*/
	public void setUserName(java.lang.String userName) {
		_layoutBranch.setUserName(userName);
	}

	/**
	* Gets the create date of this layout branch.
	*
	* @return the create date of this layout branch
	*/
	public java.util.Date getCreateDate() {
		return _layoutBranch.getCreateDate();
	}

	/**
	* Sets the create date of this layout branch.
	*
	* @param createDate the create date of this layout branch
	*/
	public void setCreateDate(java.util.Date createDate) {
		_layoutBranch.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this layout branch.
	*
	* @return the modified date of this layout branch
	*/
	public java.util.Date getModifiedDate() {
		return _layoutBranch.getModifiedDate();
	}

	/**
	* Sets the modified date of this layout branch.
	*
	* @param modifiedDate the modified date of this layout branch
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_layoutBranch.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the name of this layout branch.
	*
	* @return the name of this layout branch
	*/
	public java.lang.String getName() {
		return _layoutBranch.getName();
	}

	/**
	* Sets the name of this layout branch.
	*
	* @param name the name of this layout branch
	*/
	public void setName(java.lang.String name) {
		_layoutBranch.setName(name);
	}

	/**
	* Gets the description of this layout branch.
	*
	* @return the description of this layout branch
	*/
	public java.lang.String getDescription() {
		return _layoutBranch.getDescription();
	}

	/**
	* Sets the description of this layout branch.
	*
	* @param description the description of this layout branch
	*/
	public void setDescription(java.lang.String description) {
		_layoutBranch.setDescription(description);
	}

	public boolean isNew() {
		return _layoutBranch.isNew();
	}

	public void setNew(boolean n) {
		_layoutBranch.setNew(n);
	}

	public boolean isCachedModel() {
		return _layoutBranch.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layoutBranch.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layoutBranch.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_layoutBranch.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutBranch.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutBranch.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutBranch.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _layoutBranch.clone();
	}

	public int compareTo(com.liferay.portal.model.LayoutBranch layoutBranch) {
		return _layoutBranch.compareTo(layoutBranch);
	}

	public int hashCode() {
		return _layoutBranch.hashCode();
	}

	public com.liferay.portal.model.LayoutBranch toEscapedModel() {
		return _layoutBranch.toEscapedModel();
	}

	public java.lang.String toString() {
		return _layoutBranch.toString();
	}

	public java.lang.String toXmlString() {
		return _layoutBranch.toXmlString();
	}

	public boolean isMaster() {
		return _layoutBranch.isMaster();
	}

	public LayoutBranch getWrappedLayoutBranch() {
		return _layoutBranch;
	}

	private LayoutBranch _layoutBranch;
}