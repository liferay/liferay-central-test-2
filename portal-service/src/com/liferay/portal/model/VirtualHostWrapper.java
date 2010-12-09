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
 * This class is a wrapper for {@link VirtualHost}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       VirtualHost
 * @generated
 */
public class VirtualHostWrapper implements VirtualHost {
	public VirtualHostWrapper(VirtualHost virtualHost) {
		_virtualHost = virtualHost;
	}

	/**
	* Gets the primary key of this virtual host.
	*
	* @return the primary key of this virtual host
	*/
	public long getPrimaryKey() {
		return _virtualHost.getPrimaryKey();
	}

	/**
	* Sets the primary key of this virtual host
	*
	* @param pk the primary key of this virtual host
	*/
	public void setPrimaryKey(long pk) {
		_virtualHost.setPrimaryKey(pk);
	}

	/**
	* Gets the virtual host id of this virtual host.
	*
	* @return the virtual host id of this virtual host
	*/
	public long getVirtualHostId() {
		return _virtualHost.getVirtualHostId();
	}

	/**
	* Sets the virtual host id of this virtual host.
	*
	* @param virtualHostId the virtual host id of this virtual host
	*/
	public void setVirtualHostId(long virtualHostId) {
		_virtualHost.setVirtualHostId(virtualHostId);
	}

	/**
	* Gets the company id of this virtual host.
	*
	* @return the company id of this virtual host
	*/
	public long getCompanyId() {
		return _virtualHost.getCompanyId();
	}

	/**
	* Sets the company id of this virtual host.
	*
	* @param companyId the company id of this virtual host
	*/
	public void setCompanyId(long companyId) {
		_virtualHost.setCompanyId(companyId);
	}

	/**
	* Gets the layout set id of this virtual host.
	*
	* @return the layout set id of this virtual host
	*/
	public long getLayoutSetId() {
		return _virtualHost.getLayoutSetId();
	}

	/**
	* Sets the layout set id of this virtual host.
	*
	* @param layoutSetId the layout set id of this virtual host
	*/
	public void setLayoutSetId(long layoutSetId) {
		_virtualHost.setLayoutSetId(layoutSetId);
	}

	/**
	* Gets the virtual host name of this virtual host.
	*
	* @return the virtual host name of this virtual host
	*/
	public java.lang.String getVirtualHostName() {
		return _virtualHost.getVirtualHostName();
	}

	/**
	* Sets the virtual host name of this virtual host.
	*
	* @param virtualHostName the virtual host name of this virtual host
	*/
	public void setVirtualHostName(java.lang.String virtualHostName) {
		_virtualHost.setVirtualHostName(virtualHostName);
	}

	public boolean isNew() {
		return _virtualHost.isNew();
	}

	public void setNew(boolean n) {
		_virtualHost.setNew(n);
	}

	public boolean isCachedModel() {
		return _virtualHost.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_virtualHost.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _virtualHost.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_virtualHost.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _virtualHost.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _virtualHost.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_virtualHost.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new VirtualHostWrapper((VirtualHost)_virtualHost.clone());
	}

	public int compareTo(com.liferay.portal.model.VirtualHost virtualHost) {
		return _virtualHost.compareTo(virtualHost);
	}

	public int hashCode() {
		return _virtualHost.hashCode();
	}

	public com.liferay.portal.model.VirtualHost toEscapedModel() {
		return new VirtualHostWrapper(_virtualHost.toEscapedModel());
	}

	public java.lang.String toString() {
		return _virtualHost.toString();
	}

	public java.lang.String toXmlString() {
		return _virtualHost.toXmlString();
	}

	public VirtualHost getWrappedVirtualHost() {
		return _virtualHost;
	}

	private VirtualHost _virtualHost;
}