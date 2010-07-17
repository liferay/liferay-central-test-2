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
 * This class is a wrapper for {@link PortletItem}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletItem
 * @generated
 */
public class PortletItemWrapper implements PortletItem {
	public PortletItemWrapper(PortletItem portletItem) {
		_portletItem = portletItem;
	}

	public long getPrimaryKey() {
		return _portletItem.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_portletItem.setPrimaryKey(pk);
	}

	public long getPortletItemId() {
		return _portletItem.getPortletItemId();
	}

	public void setPortletItemId(long portletItemId) {
		_portletItem.setPortletItemId(portletItemId);
	}

	public long getGroupId() {
		return _portletItem.getGroupId();
	}

	public void setGroupId(long groupId) {
		_portletItem.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _portletItem.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_portletItem.setCompanyId(companyId);
	}

	public long getUserId() {
		return _portletItem.getUserId();
	}

	public void setUserId(long userId) {
		_portletItem.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portletItem.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_portletItem.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _portletItem.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_portletItem.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _portletItem.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_portletItem.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _portletItem.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_portletItem.setModifiedDate(modifiedDate);
	}

	public java.lang.String getName() {
		return _portletItem.getName();
	}

	public void setName(java.lang.String name) {
		_portletItem.setName(name);
	}

	public java.lang.String getPortletId() {
		return _portletItem.getPortletId();
	}

	public void setPortletId(java.lang.String portletId) {
		_portletItem.setPortletId(portletId);
	}

	public java.lang.String getClassName() {
		return _portletItem.getClassName();
	}

	public long getClassNameId() {
		return _portletItem.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_portletItem.setClassNameId(classNameId);
	}

	public com.liferay.portal.model.PortletItem toEscapedModel() {
		return _portletItem.toEscapedModel();
	}

	public boolean isNew() {
		return _portletItem.isNew();
	}

	public void setNew(boolean n) {
		_portletItem.setNew(n);
	}

	public boolean isCachedModel() {
		return _portletItem.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_portletItem.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _portletItem.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_portletItem.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _portletItem.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portletItem.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_portletItem.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _portletItem.clone();
	}

	public int compareTo(com.liferay.portal.model.PortletItem portletItem) {
		return _portletItem.compareTo(portletItem);
	}

	public int hashCode() {
		return _portletItem.hashCode();
	}

	public java.lang.String toString() {
		return _portletItem.toString();
	}

	public java.lang.String toXmlString() {
		return _portletItem.toXmlString();
	}

	public PortletItem getWrappedPortletItem() {
		return _portletItem;
	}

	private PortletItem _portletItem;
}