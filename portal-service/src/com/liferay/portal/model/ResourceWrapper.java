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
 * This class is a wrapper for {@link Resource}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Resource
 * @generated
 */
public class ResourceWrapper implements Resource {
	public ResourceWrapper(Resource resource) {
		_resource = resource;
	}

	/**
	* Gets the primary key of this resource.
	*
	* @return the primary key of this resource
	*/
	public long getPrimaryKey() {
		return _resource.getPrimaryKey();
	}

	/**
	* Sets the primary key of this resource
	*
	* @param pk the primary key of this resource
	*/
	public void setPrimaryKey(long pk) {
		_resource.setPrimaryKey(pk);
	}

	/**
	* Gets the resource id of this resource.
	*
	* @return the resource id of this resource
	*/
	public long getResourceId() {
		return _resource.getResourceId();
	}

	/**
	* Sets the resource id of this resource.
	*
	* @param resourceId the resource id of this resource
	*/
	public void setResourceId(long resourceId) {
		_resource.setResourceId(resourceId);
	}

	/**
	* Gets the code id of this resource.
	*
	* @return the code id of this resource
	*/
	public long getCodeId() {
		return _resource.getCodeId();
	}

	/**
	* Sets the code id of this resource.
	*
	* @param codeId the code id of this resource
	*/
	public void setCodeId(long codeId) {
		_resource.setCodeId(codeId);
	}

	/**
	* Gets the prim key of this resource.
	*
	* @return the prim key of this resource
	*/
	public java.lang.String getPrimKey() {
		return _resource.getPrimKey();
	}

	/**
	* Sets the prim key of this resource.
	*
	* @param primKey the prim key of this resource
	*/
	public void setPrimKey(java.lang.String primKey) {
		_resource.setPrimKey(primKey);
	}

	public boolean isNew() {
		return _resource.isNew();
	}

	public void setNew(boolean n) {
		_resource.setNew(n);
	}

	public boolean isCachedModel() {
		return _resource.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_resource.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _resource.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_resource.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _resource.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resource.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resource.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new ResourceWrapper((Resource)_resource.clone());
	}

	public int compareTo(com.liferay.portal.model.Resource resource) {
		return _resource.compareTo(resource);
	}

	public int hashCode() {
		return _resource.hashCode();
	}

	public com.liferay.portal.model.Resource toEscapedModel() {
		return new ResourceWrapper(_resource.toEscapedModel());
	}

	public java.lang.String toString() {
		return _resource.toString();
	}

	public java.lang.String toXmlString() {
		return _resource.toXmlString();
	}

	public long getCompanyId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resource.getCompanyId();
	}

	public java.lang.String getName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resource.getName();
	}

	public int getScope()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resource.getScope();
	}

	public void setCompanyId(long companyId) {
		_resource.setCompanyId(companyId);
	}

	public void setName(java.lang.String name) {
		_resource.setName(name);
	}

	public void setScope(int scope) {
		_resource.setScope(scope);
	}

	public Resource getWrappedResource() {
		return _resource;
	}

	private Resource _resource;
}