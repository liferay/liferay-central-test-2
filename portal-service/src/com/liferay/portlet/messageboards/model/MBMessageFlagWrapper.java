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
 * This class is a wrapper for {@link MBMessageFlag}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageFlag
 * @generated
 */
public class MBMessageFlagWrapper implements MBMessageFlag {
	public MBMessageFlagWrapper(MBMessageFlag mbMessageFlag) {
		_mbMessageFlag = mbMessageFlag;
	}

	public long getPrimaryKey() {
		return _mbMessageFlag.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_mbMessageFlag.setPrimaryKey(pk);
	}

	public long getMessageFlagId() {
		return _mbMessageFlag.getMessageFlagId();
	}

	public void setMessageFlagId(long messageFlagId) {
		_mbMessageFlag.setMessageFlagId(messageFlagId);
	}

	public long getUserId() {
		return _mbMessageFlag.getUserId();
	}

	public void setUserId(long userId) {
		_mbMessageFlag.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlag.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_mbMessageFlag.setUserUuid(userUuid);
	}

	public java.util.Date getModifiedDate() {
		return _mbMessageFlag.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbMessageFlag.setModifiedDate(modifiedDate);
	}

	public long getThreadId() {
		return _mbMessageFlag.getThreadId();
	}

	public void setThreadId(long threadId) {
		_mbMessageFlag.setThreadId(threadId);
	}

	public long getMessageId() {
		return _mbMessageFlag.getMessageId();
	}

	public void setMessageId(long messageId) {
		_mbMessageFlag.setMessageId(messageId);
	}

	public int getFlag() {
		return _mbMessageFlag.getFlag();
	}

	public void setFlag(int flag) {
		_mbMessageFlag.setFlag(flag);
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag toEscapedModel() {
		return _mbMessageFlag.toEscapedModel();
	}

	public boolean isNew() {
		return _mbMessageFlag.isNew();
	}

	public void setNew(boolean n) {
		_mbMessageFlag.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbMessageFlag.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbMessageFlag.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbMessageFlag.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbMessageFlag.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbMessageFlag.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbMessageFlag.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbMessageFlag.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _mbMessageFlag.clone();
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag) {
		return _mbMessageFlag.compareTo(mbMessageFlag);
	}

	public int hashCode() {
		return _mbMessageFlag.hashCode();
	}

	public java.lang.String toString() {
		return _mbMessageFlag.toString();
	}

	public java.lang.String toXmlString() {
		return _mbMessageFlag.toXmlString();
	}

	public MBMessageFlag getWrappedMBMessageFlag() {
		return _mbMessageFlag;
	}

	private MBMessageFlag _mbMessageFlag;
}