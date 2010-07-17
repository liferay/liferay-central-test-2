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
 * This class is a wrapper for {@link Website}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Website
 * @generated
 */
public class WebsiteWrapper implements Website {
	public WebsiteWrapper(Website website) {
		_website = website;
	}

	public long getPrimaryKey() {
		return _website.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_website.setPrimaryKey(pk);
	}

	public long getWebsiteId() {
		return _website.getWebsiteId();
	}

	public void setWebsiteId(long websiteId) {
		_website.setWebsiteId(websiteId);
	}

	public long getCompanyId() {
		return _website.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_website.setCompanyId(companyId);
	}

	public long getUserId() {
		return _website.getUserId();
	}

	public void setUserId(long userId) {
		_website.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _website.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_website.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _website.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_website.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _website.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_website.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _website.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_website.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _website.getClassName();
	}

	public long getClassNameId() {
		return _website.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_website.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _website.getClassPK();
	}

	public void setClassPK(long classPK) {
		_website.setClassPK(classPK);
	}

	public java.lang.String getUrl() {
		return _website.getUrl();
	}

	public void setUrl(java.lang.String url) {
		_website.setUrl(url);
	}

	public int getTypeId() {
		return _website.getTypeId();
	}

	public void setTypeId(int typeId) {
		_website.setTypeId(typeId);
	}

	public boolean getPrimary() {
		return _website.getPrimary();
	}

	public boolean isPrimary() {
		return _website.isPrimary();
	}

	public void setPrimary(boolean primary) {
		_website.setPrimary(primary);
	}

	public com.liferay.portal.model.Website toEscapedModel() {
		return _website.toEscapedModel();
	}

	public boolean isNew() {
		return _website.isNew();
	}

	public void setNew(boolean n) {
		_website.setNew(n);
	}

	public boolean isCachedModel() {
		return _website.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_website.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _website.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_website.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _website.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _website.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_website.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _website.clone();
	}

	public int compareTo(com.liferay.portal.model.Website website) {
		return _website.compareTo(website);
	}

	public int hashCode() {
		return _website.hashCode();
	}

	public java.lang.String toString() {
		return _website.toString();
	}

	public java.lang.String toXmlString() {
		return _website.toXmlString();
	}

	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _website.getType();
	}

	public Website getWrappedWebsite() {
		return _website;
	}

	private Website _website;
}