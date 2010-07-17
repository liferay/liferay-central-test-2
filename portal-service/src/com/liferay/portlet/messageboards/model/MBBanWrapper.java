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

package com.liferay.portlet.messageboards.model;

/**
 * <p>
 * This class is a wrapper for {@link MBBan}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBBan
 * @generated
 */
public class MBBanWrapper implements MBBan {
	public MBBanWrapper(MBBan mbBan) {
		_mbBan = mbBan;
	}

	public long getPrimaryKey() {
		return _mbBan.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_mbBan.setPrimaryKey(pk);
	}

	public long getBanId() {
		return _mbBan.getBanId();
	}

	public void setBanId(long banId) {
		_mbBan.setBanId(banId);
	}

	public long getGroupId() {
		return _mbBan.getGroupId();
	}

	public void setGroupId(long groupId) {
		_mbBan.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _mbBan.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_mbBan.setCompanyId(companyId);
	}

	public long getUserId() {
		return _mbBan.getUserId();
	}

	public void setUserId(long userId) {
		_mbBan.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBan.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_mbBan.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _mbBan.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_mbBan.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _mbBan.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_mbBan.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _mbBan.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbBan.setModifiedDate(modifiedDate);
	}

	public long getBanUserId() {
		return _mbBan.getBanUserId();
	}

	public void setBanUserId(long banUserId) {
		_mbBan.setBanUserId(banUserId);
	}

	public java.lang.String getBanUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbBan.getBanUserUuid();
	}

	public void setBanUserUuid(java.lang.String banUserUuid) {
		_mbBan.setBanUserUuid(banUserUuid);
	}

	public com.liferay.portlet.messageboards.model.MBBan toEscapedModel() {
		return _mbBan.toEscapedModel();
	}

	public boolean isNew() {
		return _mbBan.isNew();
	}

	public void setNew(boolean n) {
		_mbBan.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbBan.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbBan.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbBan.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbBan.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbBan.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbBan.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbBan.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _mbBan.clone();
	}

	public int compareTo(com.liferay.portlet.messageboards.model.MBBan mbBan) {
		return _mbBan.compareTo(mbBan);
	}

	public int hashCode() {
		return _mbBan.hashCode();
	}

	public java.lang.String toString() {
		return _mbBan.toString();
	}

	public java.lang.String toXmlString() {
		return _mbBan.toXmlString();
	}

	public MBBan getWrappedMBBan() {
		return _mbBan;
	}

	private MBBan _mbBan;
}