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
 * This class is a wrapper for {@link EmailAddress}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       EmailAddress
 * @generated
 */
public class EmailAddressWrapper implements EmailAddress {
	public EmailAddressWrapper(EmailAddress emailAddress) {
		_emailAddress = emailAddress;
	}

	public long getPrimaryKey() {
		return _emailAddress.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_emailAddress.setPrimaryKey(pk);
	}

	public long getEmailAddressId() {
		return _emailAddress.getEmailAddressId();
	}

	public void setEmailAddressId(long emailAddressId) {
		_emailAddress.setEmailAddressId(emailAddressId);
	}

	public long getCompanyId() {
		return _emailAddress.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_emailAddress.setCompanyId(companyId);
	}

	public long getUserId() {
		return _emailAddress.getUserId();
	}

	public void setUserId(long userId) {
		_emailAddress.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _emailAddress.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_emailAddress.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _emailAddress.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_emailAddress.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _emailAddress.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_emailAddress.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _emailAddress.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_emailAddress.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _emailAddress.getClassName();
	}

	public long getClassNameId() {
		return _emailAddress.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_emailAddress.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _emailAddress.getClassPK();
	}

	public void setClassPK(long classPK) {
		_emailAddress.setClassPK(classPK);
	}

	public java.lang.String getAddress() {
		return _emailAddress.getAddress();
	}

	public void setAddress(java.lang.String address) {
		_emailAddress.setAddress(address);
	}

	public int getTypeId() {
		return _emailAddress.getTypeId();
	}

	public void setTypeId(int typeId) {
		_emailAddress.setTypeId(typeId);
	}

	public boolean getPrimary() {
		return _emailAddress.getPrimary();
	}

	public boolean isPrimary() {
		return _emailAddress.isPrimary();
	}

	public void setPrimary(boolean primary) {
		_emailAddress.setPrimary(primary);
	}

	public com.liferay.portal.model.EmailAddress toEscapedModel() {
		return _emailAddress.toEscapedModel();
	}

	public boolean isNew() {
		return _emailAddress.isNew();
	}

	public void setNew(boolean n) {
		_emailAddress.setNew(n);
	}

	public boolean isCachedModel() {
		return _emailAddress.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_emailAddress.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _emailAddress.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_emailAddress.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _emailAddress.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _emailAddress.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_emailAddress.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _emailAddress.clone();
	}

	public int compareTo(com.liferay.portal.model.EmailAddress emailAddress) {
		return _emailAddress.compareTo(emailAddress);
	}

	public int hashCode() {
		return _emailAddress.hashCode();
	}

	public java.lang.String toString() {
		return _emailAddress.toString();
	}

	public java.lang.String toXmlString() {
		return _emailAddress.toXmlString();
	}

	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _emailAddress.getType();
	}

	public EmailAddress getWrappedEmailAddress() {
		return _emailAddress;
	}

	private EmailAddress _emailAddress;
}