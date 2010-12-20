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
 * This class is a wrapper for {@link ResourceAction}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceAction
 * @generated
 */
public class ResourceActionWrapper implements ResourceAction {
	public ResourceActionWrapper(ResourceAction resourceAction) {
		_resourceAction = resourceAction;
	}

	/**
	* Gets the primary key of this resource action.
	*
	* @return the primary key of this resource action
	*/
	public long getPrimaryKey() {
		return _resourceAction.getPrimaryKey();
	}

	/**
	* Sets the primary key of this resource action
	*
	* @param pk the primary key of this resource action
	*/
	public void setPrimaryKey(long pk) {
		_resourceAction.setPrimaryKey(pk);
	}

	/**
	* Gets the resource action ID of this resource action.
	*
	* @return the resource action ID of this resource action
	*/
	public long getResourceActionId() {
		return _resourceAction.getResourceActionId();
	}

	/**
	* Sets the resource action ID of this resource action.
	*
	* @param resourceActionId the resource action ID of this resource action
	*/
	public void setResourceActionId(long resourceActionId) {
		_resourceAction.setResourceActionId(resourceActionId);
	}

	/**
	* Gets the name of this resource action.
	*
	* @return the name of this resource action
	*/
	public java.lang.String getName() {
		return _resourceAction.getName();
	}

	/**
	* Sets the name of this resource action.
	*
	* @param name the name of this resource action
	*/
	public void setName(java.lang.String name) {
		_resourceAction.setName(name);
	}

	/**
	* Gets the action ID of this resource action.
	*
	* @return the action ID of this resource action
	*/
	public java.lang.String getActionId() {
		return _resourceAction.getActionId();
	}

	/**
	* Sets the action ID of this resource action.
	*
	* @param actionId the action ID of this resource action
	*/
	public void setActionId(java.lang.String actionId) {
		_resourceAction.setActionId(actionId);
	}

	/**
	* Gets the bitwise value of this resource action.
	*
	* @return the bitwise value of this resource action
	*/
	public long getBitwiseValue() {
		return _resourceAction.getBitwiseValue();
	}

	/**
	* Sets the bitwise value of this resource action.
	*
	* @param bitwiseValue the bitwise value of this resource action
	*/
	public void setBitwiseValue(long bitwiseValue) {
		_resourceAction.setBitwiseValue(bitwiseValue);
	}

	public boolean isNew() {
		return _resourceAction.isNew();
	}

	public void setNew(boolean n) {
		_resourceAction.setNew(n);
	}

	public boolean isCachedModel() {
		return _resourceAction.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_resourceAction.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _resourceAction.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_resourceAction.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _resourceAction.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resourceAction.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resourceAction.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new ResourceActionWrapper((ResourceAction)_resourceAction.clone());
	}

	public int compareTo(com.liferay.portal.model.ResourceAction resourceAction) {
		return _resourceAction.compareTo(resourceAction);
	}

	public int hashCode() {
		return _resourceAction.hashCode();
	}

	public com.liferay.portal.model.ResourceAction toEscapedModel() {
		return new ResourceActionWrapper(_resourceAction.toEscapedModel());
	}

	public java.lang.String toString() {
		return _resourceAction.toString();
	}

	public java.lang.String toXmlString() {
		return _resourceAction.toXmlString();
	}

	public ResourceAction getWrappedResourceAction() {
		return _resourceAction;
	}

	private ResourceAction _resourceAction;
}