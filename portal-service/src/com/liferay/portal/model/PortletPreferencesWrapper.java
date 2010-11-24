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

	/**
	* Gets the primary key of this portlet preferences.
	*
	* @return the primary key of this portlet preferences
	*/
	public long getPrimaryKey() {
		return _portletPreferences.getPrimaryKey();
	}

	/**
	* Sets the primary key of this portlet preferences
	*
	* @param pk the primary key of this portlet preferences
	*/
	public void setPrimaryKey(long pk) {
		_portletPreferences.setPrimaryKey(pk);
	}

	/**
	* Gets the portlet preferences id of this portlet preferences.
	*
	* @return the portlet preferences id of this portlet preferences
	*/
	public long getPortletPreferencesId() {
		return _portletPreferences.getPortletPreferencesId();
	}

	/**
	* Sets the portlet preferences id of this portlet preferences.
	*
	* @param portletPreferencesId the portlet preferences id of this portlet preferences
	*/
	public void setPortletPreferencesId(long portletPreferencesId) {
		_portletPreferences.setPortletPreferencesId(portletPreferencesId);
	}

	/**
	* Gets the owner id of this portlet preferences.
	*
	* @return the owner id of this portlet preferences
	*/
	public long getOwnerId() {
		return _portletPreferences.getOwnerId();
	}

	/**
	* Sets the owner id of this portlet preferences.
	*
	* @param ownerId the owner id of this portlet preferences
	*/
	public void setOwnerId(long ownerId) {
		_portletPreferences.setOwnerId(ownerId);
	}

	/**
	* Gets the owner type of this portlet preferences.
	*
	* @return the owner type of this portlet preferences
	*/
	public int getOwnerType() {
		return _portletPreferences.getOwnerType();
	}

	/**
	* Sets the owner type of this portlet preferences.
	*
	* @param ownerType the owner type of this portlet preferences
	*/
	public void setOwnerType(int ownerType) {
		_portletPreferences.setOwnerType(ownerType);
	}

	/**
	* Gets the plid of this portlet preferences.
	*
	* @return the plid of this portlet preferences
	*/
	public long getPlid() {
		return _portletPreferences.getPlid();
	}

	/**
	* Sets the plid of this portlet preferences.
	*
	* @param plid the plid of this portlet preferences
	*/
	public void setPlid(long plid) {
		_portletPreferences.setPlid(plid);
	}

	/**
	* Gets the portlet id of this portlet preferences.
	*
	* @return the portlet id of this portlet preferences
	*/
	public java.lang.String getPortletId() {
		return _portletPreferences.getPortletId();
	}

	/**
	* Sets the portlet id of this portlet preferences.
	*
	* @param portletId the portlet id of this portlet preferences
	*/
	public void setPortletId(java.lang.String portletId) {
		_portletPreferences.setPortletId(portletId);
	}

	/**
	* Gets the preferences of this portlet preferences.
	*
	* @return the preferences of this portlet preferences
	*/
	public java.lang.String getPreferences() {
		return _portletPreferences.getPreferences();
	}

	/**
	* Sets the preferences of this portlet preferences.
	*
	* @param preferences the preferences of this portlet preferences
	*/
	public void setPreferences(java.lang.String preferences) {
		_portletPreferences.setPreferences(preferences);
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
		return new PortletPreferencesWrapper((PortletPreferences)_portletPreferences.clone());
	}

	public int compareTo(
		com.liferay.portal.model.PortletPreferences portletPreferences) {
		return _portletPreferences.compareTo(portletPreferences);
	}

	public int hashCode() {
		return _portletPreferences.hashCode();
	}

	public com.liferay.portal.model.PortletPreferences toEscapedModel() {
		return new PortletPreferencesWrapper(_portletPreferences.toEscapedModel());
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