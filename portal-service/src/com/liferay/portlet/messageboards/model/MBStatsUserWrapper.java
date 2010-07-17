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
 * This class is a wrapper for {@link MBStatsUser}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBStatsUser
 * @generated
 */
public class MBStatsUserWrapper implements MBStatsUser {
	public MBStatsUserWrapper(MBStatsUser mbStatsUser) {
		_mbStatsUser = mbStatsUser;
	}

	public long getPrimaryKey() {
		return _mbStatsUser.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_mbStatsUser.setPrimaryKey(pk);
	}

	public long getStatsUserId() {
		return _mbStatsUser.getStatsUserId();
	}

	public void setStatsUserId(long statsUserId) {
		_mbStatsUser.setStatsUserId(statsUserId);
	}

	public java.lang.String getStatsUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbStatsUser.getStatsUserUuid();
	}

	public void setStatsUserUuid(java.lang.String statsUserUuid) {
		_mbStatsUser.setStatsUserUuid(statsUserUuid);
	}

	public long getGroupId() {
		return _mbStatsUser.getGroupId();
	}

	public void setGroupId(long groupId) {
		_mbStatsUser.setGroupId(groupId);
	}

	public long getUserId() {
		return _mbStatsUser.getUserId();
	}

	public void setUserId(long userId) {
		_mbStatsUser.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbStatsUser.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_mbStatsUser.setUserUuid(userUuid);
	}

	public int getMessageCount() {
		return _mbStatsUser.getMessageCount();
	}

	public void setMessageCount(int messageCount) {
		_mbStatsUser.setMessageCount(messageCount);
	}

	public java.util.Date getLastPostDate() {
		return _mbStatsUser.getLastPostDate();
	}

	public void setLastPostDate(java.util.Date lastPostDate) {
		_mbStatsUser.setLastPostDate(lastPostDate);
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser toEscapedModel() {
		return _mbStatsUser.toEscapedModel();
	}

	public boolean isNew() {
		return _mbStatsUser.isNew();
	}

	public void setNew(boolean n) {
		_mbStatsUser.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbStatsUser.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbStatsUser.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbStatsUser.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbStatsUser.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbStatsUser.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbStatsUser.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbStatsUser.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _mbStatsUser.clone();
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser) {
		return _mbStatsUser.compareTo(mbStatsUser);
	}

	public int hashCode() {
		return _mbStatsUser.hashCode();
	}

	public java.lang.String toString() {
		return _mbStatsUser.toString();
	}

	public java.lang.String toXmlString() {
		return _mbStatsUser.toXmlString();
	}

	public MBStatsUser getWrappedMBStatsUser() {
		return _mbStatsUser;
	}

	private MBStatsUser _mbStatsUser;
}