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

package com.liferay.portlet.social.model;

/**
 * <p>
 * This class is a wrapper for {@link SocialEquityLog}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityLog
 * @generated
 */
public class SocialEquityLogWrapper implements SocialEquityLog {
	public SocialEquityLogWrapper(SocialEquityLog socialEquityLog) {
		_socialEquityLog = socialEquityLog;
	}

	public long getPrimaryKey() {
		return _socialEquityLog.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_socialEquityLog.setPrimaryKey(pk);
	}

	public long getEquityLogId() {
		return _socialEquityLog.getEquityLogId();
	}

	public void setEquityLogId(long equityLogId) {
		_socialEquityLog.setEquityLogId(equityLogId);
	}

	public long getGroupId() {
		return _socialEquityLog.getGroupId();
	}

	public void setGroupId(long groupId) {
		_socialEquityLog.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _socialEquityLog.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_socialEquityLog.setCompanyId(companyId);
	}

	public long getUserId() {
		return _socialEquityLog.getUserId();
	}

	public void setUserId(long userId) {
		_socialEquityLog.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityLog.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_socialEquityLog.setUserUuid(userUuid);
	}

	public long getAssetEntryId() {
		return _socialEquityLog.getAssetEntryId();
	}

	public void setAssetEntryId(long assetEntryId) {
		_socialEquityLog.setAssetEntryId(assetEntryId);
	}

	public java.lang.String getActionId() {
		return _socialEquityLog.getActionId();
	}

	public void setActionId(java.lang.String actionId) {
		_socialEquityLog.setActionId(actionId);
	}

	public int getActionDate() {
		return _socialEquityLog.getActionDate();
	}

	public void setActionDate(int actionDate) {
		_socialEquityLog.setActionDate(actionDate);
	}

	public boolean getActive() {
		return _socialEquityLog.getActive();
	}

	public boolean isActive() {
		return _socialEquityLog.isActive();
	}

	public void setActive(boolean active) {
		_socialEquityLog.setActive(active);
	}

	public int getExpiration() {
		return _socialEquityLog.getExpiration();
	}

	public void setExpiration(int expiration) {
		_socialEquityLog.setExpiration(expiration);
	}

	public int getType() {
		return _socialEquityLog.getType();
	}

	public void setType(int type) {
		_socialEquityLog.setType(type);
	}

	public int getValue() {
		return _socialEquityLog.getValue();
	}

	public void setValue(int value) {
		_socialEquityLog.setValue(value);
	}

	public com.liferay.portlet.social.model.SocialEquityLog toEscapedModel() {
		return _socialEquityLog.toEscapedModel();
	}

	public boolean isNew() {
		return _socialEquityLog.isNew();
	}

	public void setNew(boolean n) {
		_socialEquityLog.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialEquityLog.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialEquityLog.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialEquityLog.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_socialEquityLog.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialEquityLog.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialEquityLog.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialEquityLog.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _socialEquityLog.clone();
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog) {
		return _socialEquityLog.compareTo(socialEquityLog);
	}

	public int hashCode() {
		return _socialEquityLog.hashCode();
	}

	public java.lang.String toString() {
		return _socialEquityLog.toString();
	}

	public java.lang.String toXmlString() {
		return _socialEquityLog.toXmlString();
	}

	public int getLifespan() {
		return _socialEquityLog.getLifespan();
	}

	public SocialEquityLog getWrappedSocialEquityLog() {
		return _socialEquityLog;
	}

	private SocialEquityLog _socialEquityLog;
}