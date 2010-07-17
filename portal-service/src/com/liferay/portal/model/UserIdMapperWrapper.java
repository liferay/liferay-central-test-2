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
 * This class is a wrapper for {@link UserIdMapper}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserIdMapper
 * @generated
 */
public class UserIdMapperWrapper implements UserIdMapper {
	public UserIdMapperWrapper(UserIdMapper userIdMapper) {
		_userIdMapper = userIdMapper;
	}

	public long getPrimaryKey() {
		return _userIdMapper.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_userIdMapper.setPrimaryKey(pk);
	}

	public long getUserIdMapperId() {
		return _userIdMapper.getUserIdMapperId();
	}

	public void setUserIdMapperId(long userIdMapperId) {
		_userIdMapper.setUserIdMapperId(userIdMapperId);
	}

	public long getUserId() {
		return _userIdMapper.getUserId();
	}

	public void setUserId(long userId) {
		_userIdMapper.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapper.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_userIdMapper.setUserUuid(userUuid);
	}

	public java.lang.String getType() {
		return _userIdMapper.getType();
	}

	public void setType(java.lang.String type) {
		_userIdMapper.setType(type);
	}

	public java.lang.String getDescription() {
		return _userIdMapper.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_userIdMapper.setDescription(description);
	}

	public java.lang.String getExternalUserId() {
		return _userIdMapper.getExternalUserId();
	}

	public void setExternalUserId(java.lang.String externalUserId) {
		_userIdMapper.setExternalUserId(externalUserId);
	}

	public com.liferay.portal.model.UserIdMapper toEscapedModel() {
		return _userIdMapper.toEscapedModel();
	}

	public boolean isNew() {
		return _userIdMapper.isNew();
	}

	public void setNew(boolean n) {
		_userIdMapper.setNew(n);
	}

	public boolean isCachedModel() {
		return _userIdMapper.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_userIdMapper.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _userIdMapper.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_userIdMapper.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _userIdMapper.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userIdMapper.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userIdMapper.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _userIdMapper.clone();
	}

	public int compareTo(com.liferay.portal.model.UserIdMapper userIdMapper) {
		return _userIdMapper.compareTo(userIdMapper);
	}

	public int hashCode() {
		return _userIdMapper.hashCode();
	}

	public java.lang.String toString() {
		return _userIdMapper.toString();
	}

	public java.lang.String toXmlString() {
		return _userIdMapper.toXmlString();
	}

	public UserIdMapper getWrappedUserIdMapper() {
		return _userIdMapper;
	}

	private UserIdMapper _userIdMapper;
}