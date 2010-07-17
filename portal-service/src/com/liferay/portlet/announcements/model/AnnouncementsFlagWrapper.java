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

package com.liferay.portlet.announcements.model;

/**
 * <p>
 * This class is a wrapper for {@link AnnouncementsFlag}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsFlag
 * @generated
 */
public class AnnouncementsFlagWrapper implements AnnouncementsFlag {
	public AnnouncementsFlagWrapper(AnnouncementsFlag announcementsFlag) {
		_announcementsFlag = announcementsFlag;
	}

	public long getPrimaryKey() {
		return _announcementsFlag.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_announcementsFlag.setPrimaryKey(pk);
	}

	public long getFlagId() {
		return _announcementsFlag.getFlagId();
	}

	public void setFlagId(long flagId) {
		_announcementsFlag.setFlagId(flagId);
	}

	public long getUserId() {
		return _announcementsFlag.getUserId();
	}

	public void setUserId(long userId) {
		_announcementsFlag.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsFlag.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_announcementsFlag.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _announcementsFlag.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_announcementsFlag.setCreateDate(createDate);
	}

	public long getEntryId() {
		return _announcementsFlag.getEntryId();
	}

	public void setEntryId(long entryId) {
		_announcementsFlag.setEntryId(entryId);
	}

	public int getValue() {
		return _announcementsFlag.getValue();
	}

	public void setValue(int value) {
		_announcementsFlag.setValue(value);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag toEscapedModel() {
		return _announcementsFlag.toEscapedModel();
	}

	public boolean isNew() {
		return _announcementsFlag.isNew();
	}

	public void setNew(boolean n) {
		_announcementsFlag.setNew(n);
	}

	public boolean isCachedModel() {
		return _announcementsFlag.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_announcementsFlag.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _announcementsFlag.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_announcementsFlag.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _announcementsFlag.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _announcementsFlag.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_announcementsFlag.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _announcementsFlag.clone();
	}

	public int compareTo(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag) {
		return _announcementsFlag.compareTo(announcementsFlag);
	}

	public int hashCode() {
		return _announcementsFlag.hashCode();
	}

	public java.lang.String toString() {
		return _announcementsFlag.toString();
	}

	public java.lang.String toXmlString() {
		return _announcementsFlag.toXmlString();
	}

	public AnnouncementsFlag getWrappedAnnouncementsFlag() {
		return _announcementsFlag;
	}

	private AnnouncementsFlag _announcementsFlag;
}