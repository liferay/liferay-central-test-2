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
 * This class is a wrapper for {@link BrowserTracker}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BrowserTracker
 * @generated
 */
public class BrowserTrackerWrapper implements BrowserTracker {
	public BrowserTrackerWrapper(BrowserTracker browserTracker) {
		_browserTracker = browserTracker;
	}

	public long getPrimaryKey() {
		return _browserTracker.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_browserTracker.setPrimaryKey(pk);
	}

	public long getBrowserTrackerId() {
		return _browserTracker.getBrowserTrackerId();
	}

	public void setBrowserTrackerId(long browserTrackerId) {
		_browserTracker.setBrowserTrackerId(browserTrackerId);
	}

	public long getUserId() {
		return _browserTracker.getUserId();
	}

	public void setUserId(long userId) {
		_browserTracker.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _browserTracker.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_browserTracker.setUserUuid(userUuid);
	}

	public long getBrowserKey() {
		return _browserTracker.getBrowserKey();
	}

	public void setBrowserKey(long browserKey) {
		_browserTracker.setBrowserKey(browserKey);
	}

	public com.liferay.portal.model.BrowserTracker toEscapedModel() {
		return _browserTracker.toEscapedModel();
	}

	public boolean isNew() {
		return _browserTracker.isNew();
	}

	public void setNew(boolean n) {
		_browserTracker.setNew(n);
	}

	public boolean isCachedModel() {
		return _browserTracker.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_browserTracker.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _browserTracker.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_browserTracker.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _browserTracker.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _browserTracker.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_browserTracker.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _browserTracker.clone();
	}

	public int compareTo(com.liferay.portal.model.BrowserTracker browserTracker) {
		return _browserTracker.compareTo(browserTracker);
	}

	public int hashCode() {
		return _browserTracker.hashCode();
	}

	public java.lang.String toString() {
		return _browserTracker.toString();
	}

	public java.lang.String toXmlString() {
		return _browserTracker.toXmlString();
	}

	public BrowserTracker getWrappedBrowserTracker() {
		return _browserTracker;
	}

	private BrowserTracker _browserTracker;
}