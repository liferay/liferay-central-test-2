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
 * This class is a wrapper for {@link PasswordTracker}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordTracker
 * @generated
 */
public class PasswordTrackerWrapper implements PasswordTracker {
	public PasswordTrackerWrapper(PasswordTracker passwordTracker) {
		_passwordTracker = passwordTracker;
	}

	public long getPrimaryKey() {
		return _passwordTracker.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_passwordTracker.setPrimaryKey(pk);
	}

	public long getPasswordTrackerId() {
		return _passwordTracker.getPasswordTrackerId();
	}

	public void setPasswordTrackerId(long passwordTrackerId) {
		_passwordTracker.setPasswordTrackerId(passwordTrackerId);
	}

	public long getUserId() {
		return _passwordTracker.getUserId();
	}

	public void setUserId(long userId) {
		_passwordTracker.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _passwordTracker.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_passwordTracker.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _passwordTracker.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_passwordTracker.setCreateDate(createDate);
	}

	public java.lang.String getPassword() {
		return _passwordTracker.getPassword();
	}

	public void setPassword(java.lang.String password) {
		_passwordTracker.setPassword(password);
	}

	public com.liferay.portal.model.PasswordTracker toEscapedModel() {
		return _passwordTracker.toEscapedModel();
	}

	public boolean isNew() {
		return _passwordTracker.isNew();
	}

	public void setNew(boolean n) {
		_passwordTracker.setNew(n);
	}

	public boolean isCachedModel() {
		return _passwordTracker.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_passwordTracker.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _passwordTracker.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_passwordTracker.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _passwordTracker.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _passwordTracker.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_passwordTracker.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _passwordTracker.clone();
	}

	public int compareTo(
		com.liferay.portal.model.PasswordTracker passwordTracker) {
		return _passwordTracker.compareTo(passwordTracker);
	}

	public int hashCode() {
		return _passwordTracker.hashCode();
	}

	public java.lang.String toString() {
		return _passwordTracker.toString();
	}

	public java.lang.String toXmlString() {
		return _passwordTracker.toXmlString();
	}

	public PasswordTracker getWrappedPasswordTracker() {
		return _passwordTracker;
	}

	private PasswordTracker _passwordTracker;
}