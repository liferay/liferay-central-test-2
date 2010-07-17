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
 * This class is a wrapper for {@link Account}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Account
 * @generated
 */
public class AccountWrapper implements Account {
	public AccountWrapper(Account account) {
		_account = account;
	}

	public long getPrimaryKey() {
		return _account.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_account.setPrimaryKey(pk);
	}

	public long getAccountId() {
		return _account.getAccountId();
	}

	public void setAccountId(long accountId) {
		_account.setAccountId(accountId);
	}

	public long getCompanyId() {
		return _account.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_account.setCompanyId(companyId);
	}

	public long getUserId() {
		return _account.getUserId();
	}

	public void setUserId(long userId) {
		_account.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _account.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_account.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _account.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_account.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _account.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_account.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _account.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_account.setModifiedDate(modifiedDate);
	}

	public long getParentAccountId() {
		return _account.getParentAccountId();
	}

	public void setParentAccountId(long parentAccountId) {
		_account.setParentAccountId(parentAccountId);
	}

	public java.lang.String getName() {
		return _account.getName();
	}

	public void setName(java.lang.String name) {
		_account.setName(name);
	}

	public java.lang.String getLegalName() {
		return _account.getLegalName();
	}

	public void setLegalName(java.lang.String legalName) {
		_account.setLegalName(legalName);
	}

	public java.lang.String getLegalId() {
		return _account.getLegalId();
	}

	public void setLegalId(java.lang.String legalId) {
		_account.setLegalId(legalId);
	}

	public java.lang.String getLegalType() {
		return _account.getLegalType();
	}

	public void setLegalType(java.lang.String legalType) {
		_account.setLegalType(legalType);
	}

	public java.lang.String getSicCode() {
		return _account.getSicCode();
	}

	public void setSicCode(java.lang.String sicCode) {
		_account.setSicCode(sicCode);
	}

	public java.lang.String getTickerSymbol() {
		return _account.getTickerSymbol();
	}

	public void setTickerSymbol(java.lang.String tickerSymbol) {
		_account.setTickerSymbol(tickerSymbol);
	}

	public java.lang.String getIndustry() {
		return _account.getIndustry();
	}

	public void setIndustry(java.lang.String industry) {
		_account.setIndustry(industry);
	}

	public java.lang.String getType() {
		return _account.getType();
	}

	public void setType(java.lang.String type) {
		_account.setType(type);
	}

	public java.lang.String getSize() {
		return _account.getSize();
	}

	public void setSize(java.lang.String size) {
		_account.setSize(size);
	}

	public com.liferay.portal.model.Account toEscapedModel() {
		return _account.toEscapedModel();
	}

	public boolean isNew() {
		return _account.isNew();
	}

	public void setNew(boolean n) {
		_account.setNew(n);
	}

	public boolean isCachedModel() {
		return _account.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_account.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _account.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_account.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _account.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _account.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_account.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _account.clone();
	}

	public int compareTo(com.liferay.portal.model.Account account) {
		return _account.compareTo(account);
	}

	public int hashCode() {
		return _account.hashCode();
	}

	public java.lang.String toString() {
		return _account.toString();
	}

	public java.lang.String toXmlString() {
		return _account.toXmlString();
	}

	public Account getWrappedAccount() {
		return _account;
	}

	private Account _account;
}