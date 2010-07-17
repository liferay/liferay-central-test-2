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
 * This class is a wrapper for {@link Contact}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Contact
 * @generated
 */
public class ContactWrapper implements Contact {
	public ContactWrapper(Contact contact) {
		_contact = contact;
	}

	public long getPrimaryKey() {
		return _contact.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_contact.setPrimaryKey(pk);
	}

	public long getContactId() {
		return _contact.getContactId();
	}

	public void setContactId(long contactId) {
		_contact.setContactId(contactId);
	}

	public long getCompanyId() {
		return _contact.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_contact.setCompanyId(companyId);
	}

	public long getUserId() {
		return _contact.getUserId();
	}

	public void setUserId(long userId) {
		_contact.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _contact.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_contact.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _contact.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_contact.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _contact.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_contact.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _contact.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_contact.setModifiedDate(modifiedDate);
	}

	public long getAccountId() {
		return _contact.getAccountId();
	}

	public void setAccountId(long accountId) {
		_contact.setAccountId(accountId);
	}

	public long getParentContactId() {
		return _contact.getParentContactId();
	}

	public void setParentContactId(long parentContactId) {
		_contact.setParentContactId(parentContactId);
	}

	public java.lang.String getFirstName() {
		return _contact.getFirstName();
	}

	public void setFirstName(java.lang.String firstName) {
		_contact.setFirstName(firstName);
	}

	public java.lang.String getMiddleName() {
		return _contact.getMiddleName();
	}

	public void setMiddleName(java.lang.String middleName) {
		_contact.setMiddleName(middleName);
	}

	public java.lang.String getLastName() {
		return _contact.getLastName();
	}

	public void setLastName(java.lang.String lastName) {
		_contact.setLastName(lastName);
	}

	public int getPrefixId() {
		return _contact.getPrefixId();
	}

	public void setPrefixId(int prefixId) {
		_contact.setPrefixId(prefixId);
	}

	public int getSuffixId() {
		return _contact.getSuffixId();
	}

	public void setSuffixId(int suffixId) {
		_contact.setSuffixId(suffixId);
	}

	public boolean getMale() {
		return _contact.getMale();
	}

	public boolean isMale() {
		return _contact.isMale();
	}

	public void setMale(boolean male) {
		_contact.setMale(male);
	}

	public java.util.Date getBirthday() {
		return _contact.getBirthday();
	}

	public void setBirthday(java.util.Date birthday) {
		_contact.setBirthday(birthday);
	}

	public java.lang.String getSmsSn() {
		return _contact.getSmsSn();
	}

	public void setSmsSn(java.lang.String smsSn) {
		_contact.setSmsSn(smsSn);
	}

	public java.lang.String getAimSn() {
		return _contact.getAimSn();
	}

	public void setAimSn(java.lang.String aimSn) {
		_contact.setAimSn(aimSn);
	}

	public java.lang.String getFacebookSn() {
		return _contact.getFacebookSn();
	}

	public void setFacebookSn(java.lang.String facebookSn) {
		_contact.setFacebookSn(facebookSn);
	}

	public java.lang.String getIcqSn() {
		return _contact.getIcqSn();
	}

	public void setIcqSn(java.lang.String icqSn) {
		_contact.setIcqSn(icqSn);
	}

	public java.lang.String getJabberSn() {
		return _contact.getJabberSn();
	}

	public void setJabberSn(java.lang.String jabberSn) {
		_contact.setJabberSn(jabberSn);
	}

	public java.lang.String getMsnSn() {
		return _contact.getMsnSn();
	}

	public void setMsnSn(java.lang.String msnSn) {
		_contact.setMsnSn(msnSn);
	}

	public java.lang.String getMySpaceSn() {
		return _contact.getMySpaceSn();
	}

	public void setMySpaceSn(java.lang.String mySpaceSn) {
		_contact.setMySpaceSn(mySpaceSn);
	}

	public java.lang.String getSkypeSn() {
		return _contact.getSkypeSn();
	}

	public void setSkypeSn(java.lang.String skypeSn) {
		_contact.setSkypeSn(skypeSn);
	}

	public java.lang.String getTwitterSn() {
		return _contact.getTwitterSn();
	}

	public void setTwitterSn(java.lang.String twitterSn) {
		_contact.setTwitterSn(twitterSn);
	}

	public java.lang.String getYmSn() {
		return _contact.getYmSn();
	}

	public void setYmSn(java.lang.String ymSn) {
		_contact.setYmSn(ymSn);
	}

	public java.lang.String getEmployeeStatusId() {
		return _contact.getEmployeeStatusId();
	}

	public void setEmployeeStatusId(java.lang.String employeeStatusId) {
		_contact.setEmployeeStatusId(employeeStatusId);
	}

	public java.lang.String getEmployeeNumber() {
		return _contact.getEmployeeNumber();
	}

	public void setEmployeeNumber(java.lang.String employeeNumber) {
		_contact.setEmployeeNumber(employeeNumber);
	}

	public java.lang.String getJobTitle() {
		return _contact.getJobTitle();
	}

	public void setJobTitle(java.lang.String jobTitle) {
		_contact.setJobTitle(jobTitle);
	}

	public java.lang.String getJobClass() {
		return _contact.getJobClass();
	}

	public void setJobClass(java.lang.String jobClass) {
		_contact.setJobClass(jobClass);
	}

	public java.lang.String getHoursOfOperation() {
		return _contact.getHoursOfOperation();
	}

	public void setHoursOfOperation(java.lang.String hoursOfOperation) {
		_contact.setHoursOfOperation(hoursOfOperation);
	}

	public com.liferay.portal.model.Contact toEscapedModel() {
		return _contact.toEscapedModel();
	}

	public boolean isNew() {
		return _contact.isNew();
	}

	public void setNew(boolean n) {
		_contact.setNew(n);
	}

	public boolean isCachedModel() {
		return _contact.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_contact.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _contact.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_contact.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _contact.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _contact.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_contact.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _contact.clone();
	}

	public int compareTo(com.liferay.portal.model.Contact contact) {
		return _contact.compareTo(contact);
	}

	public int hashCode() {
		return _contact.hashCode();
	}

	public java.lang.String toString() {
		return _contact.toString();
	}

	public java.lang.String toXmlString() {
		return _contact.toXmlString();
	}

	public java.lang.String getFullName() {
		return _contact.getFullName();
	}

	public Contact getWrappedContact() {
		return _contact;
	}

	private Contact _contact;
}