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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link Role}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Role
 * @generated
 */
public class RoleWrapper implements Role {
	public RoleWrapper(Role role) {
		_role = role;
	}

	public Class<?> getModelClass() {
		return Role.class;
	}

	public String getModelClassName() {
		return Role.class.getName();
	}

	/**
	* Gets the primary key of this role.
	*
	* @return the primary key of this role
	*/
	public long getPrimaryKey() {
		return _role.getPrimaryKey();
	}

	/**
	* Sets the primary key of this role
	*
	* @param pk the primary key of this role
	*/
	public void setPrimaryKey(long pk) {
		_role.setPrimaryKey(pk);
	}

	/**
	* Gets the role ID of this role.
	*
	* @return the role ID of this role
	*/
	public long getRoleId() {
		return _role.getRoleId();
	}

	/**
	* Sets the role ID of this role.
	*
	* @param roleId the role ID of this role
	*/
	public void setRoleId(long roleId) {
		_role.setRoleId(roleId);
	}

	/**
	* Gets the company ID of this role.
	*
	* @return the company ID of this role
	*/
	public long getCompanyId() {
		return _role.getCompanyId();
	}

	/**
	* Sets the company ID of this role.
	*
	* @param companyId the company ID of this role
	*/
	public void setCompanyId(long companyId) {
		_role.setCompanyId(companyId);
	}

	/**
	* Gets the class name of the model instance this role is polymorphically associated with.
	*
	* @return the class name of the model instance this role is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _role.getClassName();
	}

	/**
	* Gets the class name ID of this role.
	*
	* @return the class name ID of this role
	*/
	public long getClassNameId() {
		return _role.getClassNameId();
	}

	/**
	* Sets the class name ID of this role.
	*
	* @param classNameId the class name ID of this role
	*/
	public void setClassNameId(long classNameId) {
		_role.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this role.
	*
	* @return the class p k of this role
	*/
	public long getClassPK() {
		return _role.getClassPK();
	}

	/**
	* Sets the class p k of this role.
	*
	* @param classPK the class p k of this role
	*/
	public void setClassPK(long classPK) {
		_role.setClassPK(classPK);
	}

	/**
	* Gets the name of this role.
	*
	* @return the name of this role
	*/
	public java.lang.String getName() {
		return _role.getName();
	}

	/**
	* Sets the name of this role.
	*
	* @param name the name of this role
	*/
	public void setName(java.lang.String name) {
		_role.setName(name);
	}

	/**
	* Gets the title of this role.
	*
	* @return the title of this role
	*/
	public java.lang.String getTitle() {
		return _role.getTitle();
	}

	/**
	* Gets the localized title of this role. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized title for
	* @return the localized title of this role
	*/
	public java.lang.String getTitle(java.util.Locale locale) {
		return _role.getTitle(locale);
	}

	/**
	* Gets the localized title of this role, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized title for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this role. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _role.getTitle(locale, useDefault);
	}

	/**
	* Gets the localized title of this role. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized title for
	* @return the localized title of this role
	*/
	public java.lang.String getTitle(java.lang.String languageId) {
		return _role.getTitle(languageId);
	}

	/**
	* Gets the localized title of this role, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized title for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this role
	*/
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _role.getTitle(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized title of this role.
	*
	* @return the locales and localized title
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _role.getTitleMap();
	}

	/**
	* Sets the title of this role.
	*
	* @param title the title of this role
	*/
	public void setTitle(java.lang.String title) {
		_role.setTitle(title);
	}

	/**
	* Sets the localized title of this role.
	*
	* @param title the localized title of this role
	* @param locale the locale to set the localized title for
	*/
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_role.setTitle(title, locale);
	}

	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_role.setTitle(title, locale, defaultLocale);
	}

	/**
	* Sets the localized titles of this role from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this role
	*/
	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap) {
		_role.setTitleMap(titleMap);
	}

	public void setTitleMap(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_role.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Gets the description of this role.
	*
	* @return the description of this role
	*/
	public java.lang.String getDescription() {
		return _role.getDescription();
	}

	/**
	* Sets the description of this role.
	*
	* @param description the description of this role
	*/
	public void setDescription(java.lang.String description) {
		_role.setDescription(description);
	}

	/**
	* Gets the type of this role.
	*
	* @return the type of this role
	*/
	public int getType() {
		return _role.getType();
	}

	/**
	* Sets the type of this role.
	*
	* @param type the type of this role
	*/
	public void setType(int type) {
		_role.setType(type);
	}

	/**
	* Gets the subtype of this role.
	*
	* @return the subtype of this role
	*/
	public java.lang.String getSubtype() {
		return _role.getSubtype();
	}

	/**
	* Sets the subtype of this role.
	*
	* @param subtype the subtype of this role
	*/
	public void setSubtype(java.lang.String subtype) {
		_role.setSubtype(subtype);
	}

	public boolean isNew() {
		return _role.isNew();
	}

	public void setNew(boolean n) {
		_role.setNew(n);
	}

	public boolean isCachedModel() {
		return _role.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_role.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _role.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_role.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _role.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _role.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_role.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new RoleWrapper((Role)_role.clone());
	}

	public int compareTo(com.liferay.portal.model.Role role) {
		return _role.compareTo(role);
	}

	public int hashCode() {
		return _role.hashCode();
	}

	public com.liferay.portal.model.Role toEscapedModel() {
		return new RoleWrapper(_role.toEscapedModel());
	}

	public java.lang.String toString() {
		return _role.toString();
	}

	public java.lang.String toXmlString() {
		return _role.toXmlString();
	}

	public java.lang.String getDescriptiveName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _role.getDescriptiveName();
	}

	public java.lang.String getTypeLabel() {
		return _role.getTypeLabel();
	}

	public boolean isTeam() {
		return _role.isTeam();
	}

	public Role getWrappedRole() {
		return _role;
	}

	public void resetOriginalValues() {
		_role.resetOriginalValues();
	}

	private Role _role;
}