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
 * This class is a wrapper for {@link SocialEquitySetting}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquitySetting
 * @generated
 */
public class SocialEquitySettingWrapper implements SocialEquitySetting {
	public SocialEquitySettingWrapper(SocialEquitySetting socialEquitySetting) {
		_socialEquitySetting = socialEquitySetting;
	}

	public long getPrimaryKey() {
		return _socialEquitySetting.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_socialEquitySetting.setPrimaryKey(pk);
	}

	public long getEquitySettingId() {
		return _socialEquitySetting.getEquitySettingId();
	}

	public void setEquitySettingId(long equitySettingId) {
		_socialEquitySetting.setEquitySettingId(equitySettingId);
	}

	public long getGroupId() {
		return _socialEquitySetting.getGroupId();
	}

	public void setGroupId(long groupId) {
		_socialEquitySetting.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _socialEquitySetting.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_socialEquitySetting.setCompanyId(companyId);
	}

	public java.lang.String getClassName() {
		return _socialEquitySetting.getClassName();
	}

	public long getClassNameId() {
		return _socialEquitySetting.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_socialEquitySetting.setClassNameId(classNameId);
	}

	public java.lang.String getActionId() {
		return _socialEquitySetting.getActionId();
	}

	public void setActionId(java.lang.String actionId) {
		_socialEquitySetting.setActionId(actionId);
	}

	public int getDailyLimit() {
		return _socialEquitySetting.getDailyLimit();
	}

	public void setDailyLimit(int dailyLimit) {
		_socialEquitySetting.setDailyLimit(dailyLimit);
	}

	public int getLifespan() {
		return _socialEquitySetting.getLifespan();
	}

	public void setLifespan(int lifespan) {
		_socialEquitySetting.setLifespan(lifespan);
	}

	public int getType() {
		return _socialEquitySetting.getType();
	}

	public void setType(int type) {
		_socialEquitySetting.setType(type);
	}

	public boolean getUniqueEntry() {
		return _socialEquitySetting.getUniqueEntry();
	}

	public boolean isUniqueEntry() {
		return _socialEquitySetting.isUniqueEntry();
	}

	public void setUniqueEntry(boolean uniqueEntry) {
		_socialEquitySetting.setUniqueEntry(uniqueEntry);
	}

	public int getValue() {
		return _socialEquitySetting.getValue();
	}

	public void setValue(int value) {
		_socialEquitySetting.setValue(value);
	}

	public com.liferay.portlet.social.model.SocialEquitySetting toEscapedModel() {
		return _socialEquitySetting.toEscapedModel();
	}

	public boolean isNew() {
		return _socialEquitySetting.isNew();
	}

	public void setNew(boolean n) {
		_socialEquitySetting.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialEquitySetting.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialEquitySetting.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialEquitySetting.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_socialEquitySetting.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialEquitySetting.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialEquitySetting.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialEquitySetting.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _socialEquitySetting.clone();
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting) {
		return _socialEquitySetting.compareTo(socialEquitySetting);
	}

	public int hashCode() {
		return _socialEquitySetting.hashCode();
	}

	public java.lang.String toString() {
		return _socialEquitySetting.toString();
	}

	public java.lang.String toXmlString() {
		return _socialEquitySetting.toXmlString();
	}

	public void update(
		com.liferay.portlet.social.model.SocialEquityActionMapping equityActionMapping) {
		_socialEquitySetting.update(equityActionMapping);
	}

	public SocialEquitySetting getWrappedSocialEquitySetting() {
		return _socialEquitySetting;
	}

	private SocialEquitySetting _socialEquitySetting;
}