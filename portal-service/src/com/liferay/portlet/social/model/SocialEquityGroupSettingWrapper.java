/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

	/**
	* Gets the primary key of this social equity group setting.
	*
	* @return the primary key of this social equity group setting
	*/
	public long getPrimaryKey() {
		return _socialEquityGroupSetting.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social equity group setting
	*
	* @param pk the primary key of this social equity group setting
	*/
	public void setPrimaryKey(long pk) {
		_socialEquityGroupSetting.setPrimaryKey(pk);
	}

	/**
	* Gets the equity group setting ID of this social equity group setting.
	*
	* @return the equity group setting ID of this social equity group setting
	*/
	public long getEquityGroupSettingId() {
		return _socialEquityGroupSetting.getEquityGroupSettingId();
	}

	/**
	* Sets the equity group setting ID of this social equity group setting.
	*
	* @param equityGroupSettingId the equity group setting ID of this social equity group setting
	*/
	public void setEquityGroupSettingId(long equityGroupSettingId) {
		_socialEquityGroupSetting.setEquityGroupSettingId(equityGroupSettingId);
	}

	/**
	* Gets the group ID of this social equity group setting.
	*
	* @return the group ID of this social equity group setting
	*/
	public long getGroupId() {
		return _socialEquityGroupSetting.getGroupId();
	}

	/**
	* Sets the group ID of this social equity group setting.
	*
	* @param groupId the group ID of this social equity group setting
	*/
	public void setGroupId(long groupId) {
		_socialEquityGroupSetting.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this social equity group setting.
	*
	* @return the company ID of this social equity group setting
	*/
	public long getCompanyId() {
		return _socialEquityGroupSetting.getCompanyId();
	}

	/**
	* Sets the company ID of this social equity group setting.
	*
	* @param companyId the company ID of this social equity group setting
	*/
	public void setCompanyId(long companyId) {
		_socialEquityGroupSetting.setCompanyId(companyId);
	}

	/**
	* Gets the class name of the model instance this social equity group setting is polymorphically associated with.
	*
	* @return the class name of the model instance this social equity group setting is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _socialEquityGroupSetting.getClassName();
	}

	/**
	* Gets the class name ID of this social equity group setting.
	*
	* @return the class name ID of this social equity group setting
	*/
	public long getClassNameId() {
		return _socialEquityGroupSetting.getClassNameId();
	}

	/**
	* Sets the class name ID of this social equity group setting.
	*
	* @param classNameId the class name ID of this social equity group setting
	*/
	public void setClassNameId(long classNameId) {
		_socialEquityGroupSetting.setClassNameId(classNameId);
	}

	/**
	* Gets the type of this social equity group setting.
	*
	* @return the type of this social equity group setting
	*/
	public int getType() {
		return _socialEquityGroupSetting.getType();
	}

	/**
	* Sets the type of this social equity group setting.
	*
	* @param type the type of this social equity group setting
	*/
	public void setType(int type) {
		_socialEquityGroupSetting.setType(type);
	}

	/**
	* Gets the enabled of this social equity group setting.
	*
	* @return the enabled of this social equity group setting
	*/
	public boolean getEnabled() {
		return _socialEquityGroupSetting.getEnabled();
	}

	/**
	* Determines if this social equity group setting is enabled.
	*
	* @return <code>true</code> if this social equity group setting is enabled; <code>false</code> otherwise
	*/
	public boolean isEnabled() {
		return _socialEquityGroupSetting.isEnabled();
	}

	/**
	* Sets whether this social equity group setting is enabled.
	*
	* @param enabled the enabled of this social equity group setting
	*/
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
		return new SocialEquityGroupSettingWrapper((SocialEquityGroupSetting)_socialEquityGroupSetting.clone());
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting) {
		return _socialEquityGroupSetting.compareTo(socialEquityGroupSetting);
	}

	public int hashCode() {
		return _socialEquityGroupSetting.hashCode();
	}

	public com.liferay.portlet.social.model.SocialEquityGroupSetting toEscapedModel() {
		return new SocialEquityGroupSettingWrapper(_socialEquityGroupSetting.toEscapedModel());
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