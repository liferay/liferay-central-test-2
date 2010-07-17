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

	public long getPrimaryKey() {
		return _resourceAction.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_resourceAction.setPrimaryKey(pk);
	}

	public long getResourceActionId() {
		return _resourceAction.getResourceActionId();
	}

	public void setResourceActionId(long resourceActionId) {
		_resourceAction.setResourceActionId(resourceActionId);
	}

	public java.lang.String getName() {
		return _resourceAction.getName();
	}

	public void setName(java.lang.String name) {
		_resourceAction.setName(name);
	}

	public java.lang.String getActionId() {
		return _resourceAction.getActionId();
	}

	public void setActionId(java.lang.String actionId) {
		_resourceAction.setActionId(actionId);
	}

	public long getBitwiseValue() {
		return _resourceAction.getBitwiseValue();
	}

	public void setBitwiseValue(long bitwiseValue) {
		_resourceAction.setBitwiseValue(bitwiseValue);
	}

	public com.liferay.portal.model.ResourceAction toEscapedModel() {
		return _resourceAction.toEscapedModel();
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
		return _resourceAction.clone();
	}

	public int compareTo(com.liferay.portal.model.ResourceAction resourceAction) {
		return _resourceAction.compareTo(resourceAction);
	}

	public int hashCode() {
		return _resourceAction.hashCode();
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