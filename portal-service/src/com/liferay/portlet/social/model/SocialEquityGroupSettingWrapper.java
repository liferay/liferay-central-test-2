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
 * This class is a wrapper for {@link SocialEquityGroupSetting}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityGroupSetting
 * @generated
 */
public class SocialEquityGroupSettingWrapper implements SocialEquityGroupSetting {
	public SocialEquityGroupSettingWrapper(
		SocialEquityGroupSetting socialEquityGroupSetting) {
		_socialEquityGroupSetting = socialEquityGroupSetting;
	}

	public long getPrimaryKey() {
		return _socialEquityGroupSetting.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_socialEquityGroupSetting.setPrimaryKey(pk);
	}

	public long getEquityGroupSettingId() {
		return _socialEquityGroupSetting.getEquityGroupSettingId();
	}

	public void setEquityGroupSettingId(long equityGroupSettingId) {
		_socialEquityGroupSetting.setEquityGroupSettingId(equityGroupSettingId);
	}

	public long getGroupId() {
		return _socialEquityGroupSetting.getGroupId();
	}

	public void setGroupId(long groupId) {
		_socialEquityGroupSetting.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _socialEquityGroupSetting.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_socialEquityGroupSetting.setCompanyId(companyId);
	}

	public java.lang.String getClassName() {
		return _socialEquityGroupSetting.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_socialEquityGroupSetting.setClassName(className);
	}

	public int getType() {
		return _socialEquityGroupSetting.getType();
	}

	public void setType(int type) {
		_socialEquityGroupSetting.setType(type);
	}

	public boolean getEnabled() {
		return _socialEquityGroupSetting.getEnabled();
	}

	public boolean isEnabled() {
		return _socialEquityGroupSetting.isEnabled();
	}

	public void setEnabled(boolean enabled) {
		_socialEquityGroupSetting.setEnabled(enabled);
	}

	public boolean isNew() {
		return _socialEquityGroupSetting.isNew();
	}

	public void setNew(boolean n) {
		_socialEquityGroupSetting.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialEquityGroupSetting.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialEquityGroupSetting.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialEquityGroupSetting.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_socialEquityGroupSetting.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialEquityGroupSetting.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialEquityGroupSetting.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialEquityGroupSetting.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _socialEquityGroupSetting.clone();
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting) {
		return _socialEquityGroupSetting.compareTo(socialEquityGroupSetting);
	}

	public int hashCode() {
		return _socialEquityGroupSetting.hashCode();
	}

	public com.liferay.portlet.social.model.SocialEquityGroupSetting toEscapedModel() {
		return _socialEquityGroupSetting.toEscapedModel();
	}

	public java.lang.String toString() {
		return _socialEquityGroupSetting.toString();
	}

	public java.lang.String toXmlString() {
		return _socialEquityGroupSetting.toXmlString();
	}

	public SocialEquityGroupSetting getWrappedSocialEquityGroupSetting() {
		return _socialEquityGroupSetting;
	}

	private SocialEquityGroupSetting _socialEquityGroupSetting;
}