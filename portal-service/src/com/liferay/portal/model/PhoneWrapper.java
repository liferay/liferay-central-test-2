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
 * This class is a wrapper for {@link Phone}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Phone
 * @generated
 */
public class PhoneWrapper implements Phone {
	public PhoneWrapper(Phone phone) {
		_phone = phone;
	}

	public long getPrimaryKey() {
		return _phone.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_phone.setPrimaryKey(pk);
	}

	public long getPhoneId() {
		return _phone.getPhoneId();
	}

	public void setPhoneId(long phoneId) {
		_phone.setPhoneId(phoneId);
	}

	public long getCompanyId() {
		return _phone.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_phone.setCompanyId(companyId);
	}

	public long getUserId() {
		return _phone.getUserId();
	}

	public void setUserId(long userId) {
		_phone.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _phone.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_phone.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _phone.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_phone.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _phone.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_phone.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _phone.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_phone.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _phone.getClassName();
	}

	public long getClassNameId() {
		return _phone.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_phone.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _phone.getClassPK();
	}

	public void setClassPK(long classPK) {
		_phone.setClassPK(classPK);
	}

	public java.lang.String getNumber() {
		return _phone.getNumber();
	}

	public void setNumber(java.lang.String number) {
		_phone.setNumber(number);
	}

	public java.lang.String getExtension() {
		return _phone.getExtension();
	}

	public void setExtension(java.lang.String extension) {
		_phone.setExtension(extension);
	}

	public int getTypeId() {
		return _phone.getTypeId();
	}

	public void setTypeId(int typeId) {
		_phone.setTypeId(typeId);
	}

	public boolean getPrimary() {
		return _phone.getPrimary();
	}

	public boolean isPrimary() {
		return _phone.isPrimary();
	}

	public void setPrimary(boolean primary) {
		_phone.setPrimary(primary);
	}

	public com.liferay.portal.model.Phone toEscapedModel() {
		return _phone.toEscapedModel();
	}

	public boolean isNew() {
		return _phone.isNew();
	}

	public void setNew(boolean n) {
		_phone.setNew(n);
	}

	public boolean isCachedModel() {
		return _phone.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_phone.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _phone.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_phone.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _phone.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _phone.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_phone.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _phone.clone();
	}

	public int compareTo(com.liferay.portal.model.Phone phone) {
		return _phone.compareTo(phone);
	}

	public int hashCode() {
		return _phone.hashCode();
	}

	public java.lang.String toString() {
		return _phone.toString();
	}

	public java.lang.String toXmlString() {
		return _phone.toXmlString();
	}

	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _phone.getType();
	}

	public Phone getWrappedPhone() {
		return _phone;
	}

	private Phone _phone;
}