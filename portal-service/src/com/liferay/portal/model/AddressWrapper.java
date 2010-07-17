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
 * This class is a wrapper for {@link Address}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Address
 * @generated
 */
public class AddressWrapper implements Address {
	public AddressWrapper(Address address) {
		_address = address;
	}

	public long getPrimaryKey() {
		return _address.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_address.setPrimaryKey(pk);
	}

	public long getAddressId() {
		return _address.getAddressId();
	}

	public void setAddressId(long addressId) {
		_address.setAddressId(addressId);
	}

	public long getCompanyId() {
		return _address.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_address.setCompanyId(companyId);
	}

	public long getUserId() {
		return _address.getUserId();
	}

	public void setUserId(long userId) {
		_address.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _address.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_address.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _address.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_address.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _address.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_address.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _address.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_address.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _address.getClassName();
	}

	public long getClassNameId() {
		return _address.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_address.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _address.getClassPK();
	}

	public void setClassPK(long classPK) {
		_address.setClassPK(classPK);
	}

	public java.lang.String getStreet1() {
		return _address.getStreet1();
	}

	public void setStreet1(java.lang.String street1) {
		_address.setStreet1(street1);
	}

	public java.lang.String getStreet2() {
		return _address.getStreet2();
	}

	public void setStreet2(java.lang.String street2) {
		_address.setStreet2(street2);
	}

	public java.lang.String getStreet3() {
		return _address.getStreet3();
	}

	public void setStreet3(java.lang.String street3) {
		_address.setStreet3(street3);
	}

	public java.lang.String getCity() {
		return _address.getCity();
	}

	public void setCity(java.lang.String city) {
		_address.setCity(city);
	}

	public java.lang.String getZip() {
		return _address.getZip();
	}

	public void setZip(java.lang.String zip) {
		_address.setZip(zip);
	}

	public long getRegionId() {
		return _address.getRegionId();
	}

	public void setRegionId(long regionId) {
		_address.setRegionId(regionId);
	}

	public long getCountryId() {
		return _address.getCountryId();
	}

	public void setCountryId(long countryId) {
		_address.setCountryId(countryId);
	}

	public int getTypeId() {
		return _address.getTypeId();
	}

	public void setTypeId(int typeId) {
		_address.setTypeId(typeId);
	}

	public boolean getMailing() {
		return _address.getMailing();
	}

	public boolean isMailing() {
		return _address.isMailing();
	}

	public void setMailing(boolean mailing) {
		_address.setMailing(mailing);
	}

	public boolean getPrimary() {
		return _address.getPrimary();
	}

	public boolean isPrimary() {
		return _address.isPrimary();
	}

	public void setPrimary(boolean primary) {
		_address.setPrimary(primary);
	}

	public com.liferay.portal.model.Address toEscapedModel() {
		return _address.toEscapedModel();
	}

	public boolean isNew() {
		return _address.isNew();
	}

	public void setNew(boolean n) {
		_address.setNew(n);
	}

	public boolean isCachedModel() {
		return _address.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_address.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _address.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_address.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _address.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _address.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_address.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _address.clone();
	}

	public int compareTo(com.liferay.portal.model.Address address) {
		return _address.compareTo(address);
	}

	public int hashCode() {
		return _address.hashCode();
	}

	public java.lang.String toString() {
		return _address.toString();
	}

	public java.lang.String toXmlString() {
		return _address.toXmlString();
	}

	public com.liferay.portal.model.Region getRegion() {
		return _address.getRegion();
	}

	public com.liferay.portal.model.Country getCountry() {
		return _address.getCountry();
	}

	public com.liferay.portal.model.ListType getType() {
		return _address.getType();
	}

	public Address getWrappedAddress() {
		return _address;
	}

	private Address _address;
}