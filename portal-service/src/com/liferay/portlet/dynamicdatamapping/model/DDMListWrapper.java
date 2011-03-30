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

package com.liferay.portlet.dynamicdatamapping.model;

/**
 * <p>
 * This class is a wrapper for {@link DDMList}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMList
 * @generated
 */
public class DDMListWrapper implements DDMList {
	public DDMListWrapper(DDMList ddmList) {
		_ddmList = ddmList;
	}

	public Class<?> getModelClass() {
		return DDMList.class;
	}

	public String getModelClassName() {
		return DDMList.class.getName();
	}

	/**
	* Gets the primary key of this d d m list.
	*
	* @return the primary key of this d d m list
	*/
	public long getPrimaryKey() {
		return _ddmList.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m list
	*
	* @param pk the primary key of this d d m list
	*/
	public void setPrimaryKey(long pk) {
		_ddmList.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this d d m list.
	*
	* @return the uuid of this d d m list
	*/
	public java.lang.String getUuid() {
		return _ddmList.getUuid();
	}

	/**
	* Sets the uuid of this d d m list.
	*
	* @param uuid the uuid of this d d m list
	*/
	public void setUuid(java.lang.String uuid) {
		_ddmList.setUuid(uuid);
	}

	/**
	* Gets the list ID of this d d m list.
	*
	* @return the list ID of this d d m list
	*/
	public long getListId() {
		return _ddmList.getListId();
	}

	/**
	* Sets the list ID of this d d m list.
	*
	* @param listId the list ID of this d d m list
	*/
	public void setListId(long listId) {
		_ddmList.setListId(listId);
	}

	/**
	* Gets the group ID of this d d m list.
	*
	* @return the group ID of this d d m list
	*/
	public long getGroupId() {
		return _ddmList.getGroupId();
	}

	/**
	* Sets the group ID of this d d m list.
	*
	* @param groupId the group ID of this d d m list
	*/
	public void setGroupId(long groupId) {
		_ddmList.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this d d m list.
	*
	* @return the company ID of this d d m list
	*/
	public long getCompanyId() {
		return _ddmList.getCompanyId();
	}

	/**
	* Sets the company ID of this d d m list.
	*
	* @param companyId the company ID of this d d m list
	*/
	public void setCompanyId(long companyId) {
		_ddmList.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this d d m list.
	*
	* @return the user ID of this d d m list
	*/
	public long getUserId() {
		return _ddmList.getUserId();
	}

	/**
	* Sets the user ID of this d d m list.
	*
	* @param userId the user ID of this d d m list
	*/
	public void setUserId(long userId) {
		_ddmList.setUserId(userId);
	}

	/**
	* Gets the user uuid of this d d m list.
	*
	* @return the user uuid of this d d m list
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmList.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d m list.
	*
	* @param userUuid the user uuid of this d d m list
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ddmList.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this d d m list.
	*
	* @return the user name of this d d m list
	*/
	public java.lang.String getUserName() {
		return _ddmList.getUserName();
	}

	/**
	* Sets the user name of this d d m list.
	*
	* @param userName the user name of this d d m list
	*/
	public void setUserName(java.lang.String userName) {
		_ddmList.setUserName(userName);
	}

	/**
	* Gets the create date of this d d m list.
	*
	* @return the create date of this d d m list
	*/
	public java.util.Date getCreateDate() {
		return _ddmList.getCreateDate();
	}

	/**
	* Sets the create date of this d d m list.
	*
	* @param createDate the create date of this d d m list
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ddmList.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this d d m list.
	*
	* @return the modified date of this d d m list
	*/
	public java.util.Date getModifiedDate() {
		return _ddmList.getModifiedDate();
	}

	/**
	* Sets the modified date of this d d m list.
	*
	* @param modifiedDate the modified date of this d d m list
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ddmList.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the name of this d d m list.
	*
	* @return the name of this d d m list
	*/
	public java.lang.String getName() {
		return _ddmList.getName();
	}

	/**
	* Gets the localized name of this d d m list. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized name for
	* @return the localized name of this d d m list
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _ddmList.getName(locale);
	}

	/**
	* Gets the localized name of this d d m list, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m list. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmList.getName(locale, useDefault);
	}

	/**
	* Gets the localized name of this d d m list. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @return the localized name of this d d m list
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _ddmList.getName(languageId);
	}

	/**
	* Gets the localized name of this d d m list, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m list
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddmList.getName(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized name of this d d m list.
	*
	* @return the locales and localized name
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmList.getNameMap();
	}

	/**
	* Sets the name of this d d m list.
	*
	* @param name the name of this d d m list
	*/
	public void setName(java.lang.String name) {
		_ddmList.setName(name);
	}

	/**
	* Sets the localized name of this d d m list.
	*
	* @param name the localized name of this d d m list
	* @param locale the locale to set the localized name for
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddmList.setName(name, locale);
	}

	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmList.setName(name, locale, defaultLocale);
	}

	/**
	* Sets the localized names of this d d m list from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d m list
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmList.setNameMap(nameMap);
	}

	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmList.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Gets the description of this d d m list.
	*
	* @return the description of this d d m list
	*/
	public java.lang.String getDescription() {
		return _ddmList.getDescription();
	}

	/**
	* Sets the description of this d d m list.
	*
	* @param description the description of this d d m list
	*/
	public void setDescription(java.lang.String description) {
		_ddmList.setDescription(description);
	}

	/**
	* Gets the structure ID of this d d m list.
	*
	* @return the structure ID of this d d m list
	*/
	public long getStructureId() {
		return _ddmList.getStructureId();
	}

	/**
	* Sets the structure ID of this d d m list.
	*
	* @param structureId the structure ID of this d d m list
	*/
	public void setStructureId(long structureId) {
		_ddmList.setStructureId(structureId);
	}

	public boolean isNew() {
		return _ddmList.isNew();
	}

	public void setNew(boolean n) {
		_ddmList.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddmList.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddmList.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddmList.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddmList.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmList.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmList.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmList.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDMListWrapper((DDMList)_ddmList.clone());
	}

	public int compareTo(
		com.liferay.portlet.dynamicdatamapping.model.DDMList ddmList) {
		return _ddmList.compareTo(ddmList);
	}

	public int hashCode() {
		return _ddmList.hashCode();
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMList toEscapedModel() {
		return new DDMListWrapper(_ddmList.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddmList.toString();
	}

	public java.lang.String toXmlString() {
		return _ddmList.toXmlString();
	}

	public DDMList getWrappedDDMList() {
		return _ddmList;
	}

	public void resetOriginalValues() {
		_ddmList.resetOriginalValues();
	}

	private DDMList _ddmList;
}