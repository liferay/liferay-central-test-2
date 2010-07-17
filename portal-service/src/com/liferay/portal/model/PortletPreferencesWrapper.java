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
 * This class is a wrapper for {@link PortletPreferences}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPreferences
 * @generated
 */
public class PortletPreferencesWrapper implements PortletPreferences {
	public PortletPreferencesWrapper(PortletPreferences portletPreferences) {
		_portletPreferences = portletPreferences;
	}

	public long getPrimaryKey() {
		return _portletPreferences.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_portletPreferences.setPrimaryKey(pk);
	}

	public long getPortletPreferencesId() {
		return _portletPreferences.getPortletPreferencesId();
	}

	public void setPortletPreferencesId(long portletPreferencesId) {
		_portletPreferences.setPortletPreferencesId(portletPreferencesId);
	}

	public long getOwnerId() {
		return _portletPreferences.getOwnerId();
	}

	public void setOwnerId(long ownerId) {
		_portletPreferences.setOwnerId(ownerId);
	}

	public int getOwnerType() {
		return _portletPreferences.getOwnerType();
	}

	public void setOwnerType(int ownerType) {
		_portletPreferences.setOwnerType(ownerType);
	}

	public long getPlid() {
		return _portletPreferences.getPlid();
	}

	public void setPlid(long plid) {
		_portletPreferences.setPlid(plid);
	}

	public java.lang.String getPortletId() {
		return _portletPreferences.getPortletId();
	}

	public void setPortletId(java.lang.String portletId) {
		_portletPreferences.setPortletId(portletId);
	}

	public java.lang.String getPreferences() {
		return _portletPreferences.getPreferences();
	}

	public void setPreferences(java.lang.String preferences) {
		_portletPreferences.setPreferences(preferences);
	}

	public com.liferay.portal.model.PortletPreferences toEscapedModel() {
		return _portletPreferences.toEscapedModel();
	}

	public boolean isNew() {
		return _portletPreferences.isNew();
	}

	public void setNew(boolean n) {
		_portletPreferences.setNew(n);
	}

	public boolean isCachedModel() {
		return _portletPreferences.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_portletPreferences.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _portletPreferences.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_portletPreferences.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _portletPreferences.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portletPreferences.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_portletPreferences.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _portletPreferences.clone();
	}

	public int compareTo(
		com.liferay.portal.model.PortletPreferences portletPreferences) {
		return _portletPreferences.compareTo(portletPreferences);
	}

	public int hashCode() {
		return _portletPreferences.hashCode();
	}

	public java.lang.String toString() {
		return _portletPreferences.toString();
	}

	public java.lang.String toXmlString() {
		return _portletPreferences.toXmlString();
	}

	public PortletPreferences getWrappedPortletPreferences() {
		return _portletPreferences;
	}

	private PortletPreferences _portletPreferences;
}