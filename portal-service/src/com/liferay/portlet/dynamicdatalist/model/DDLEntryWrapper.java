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

package com.liferay.portlet.dynamicdatalist.model;

/**
 * <p>
 * This class is a wrapper for {@link DDLEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLEntry
 * @generated
 */
public class DDLEntryWrapper implements DDLEntry {
	public DDLEntryWrapper(DDLEntry ddlEntry) {
		_ddlEntry = ddlEntry;
	}

	public Class<?> getModelClass() {
		return DDLEntry.class;
	}

	public String getModelClassName() {
		return DDLEntry.class.getName();
	}

	/**
	* Gets the primary key of this d d l entry.
	*
	* @return the primary key of this d d l entry
	*/
	public long getPrimaryKey() {
		return _ddlEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d l entry
	*
	* @param pk the primary key of this d d l entry
	*/
	public void setPrimaryKey(long pk) {
		_ddlEntry.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this d d l entry.
	*
	* @return the uuid of this d d l entry
	*/
	public java.lang.String getUuid() {
		return _ddlEntry.getUuid();
	}

	/**
	* Sets the uuid of this d d l entry.
	*
	* @param uuid the uuid of this d d l entry
	*/
	public void setUuid(java.lang.String uuid) {
		_ddlEntry.setUuid(uuid);
	}

	/**
	* Gets the entry ID of this d d l entry.
	*
	* @return the entry ID of this d d l entry
	*/
	public long getEntryId() {
		return _ddlEntry.getEntryId();
	}

	/**
	* Sets the entry ID of this d d l entry.
	*
	* @param entryId the entry ID of this d d l entry
	*/
	public void setEntryId(long entryId) {
		_ddlEntry.setEntryId(entryId);
	}

	/**
	* Gets the group ID of this d d l entry.
	*
	* @return the group ID of this d d l entry
	*/
	public long getGroupId() {
		return _ddlEntry.getGroupId();
	}

	/**
	* Sets the group ID of this d d l entry.
	*
	* @param groupId the group ID of this d d l entry
	*/
	public void setGroupId(long groupId) {
		_ddlEntry.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this d d l entry.
	*
	* @return the company ID of this d d l entry
	*/
	public long getCompanyId() {
		return _ddlEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this d d l entry.
	*
	* @param companyId the company ID of this d d l entry
	*/
	public void setCompanyId(long companyId) {
		_ddlEntry.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this d d l entry.
	*
	* @return the user ID of this d d l entry
	*/
	public long getUserId() {
		return _ddlEntry.getUserId();
	}

	/**
	* Sets the user ID of this d d l entry.
	*
	* @param userId the user ID of this d d l entry
	*/
	public void setUserId(long userId) {
		_ddlEntry.setUserId(userId);
	}

	/**
	* Gets the user uuid of this d d l entry.
	*
	* @return the user uuid of this d d l entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d l entry.
	*
	* @param userUuid the user uuid of this d d l entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ddlEntry.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this d d l entry.
	*
	* @return the user name of this d d l entry
	*/
	public java.lang.String getUserName() {
		return _ddlEntry.getUserName();
	}

	/**
	* Sets the user name of this d d l entry.
	*
	* @param userName the user name of this d d l entry
	*/
	public void setUserName(java.lang.String userName) {
		_ddlEntry.setUserName(userName);
	}

	/**
	* Gets the create date of this d d l entry.
	*
	* @return the create date of this d d l entry
	*/
	public java.util.Date getCreateDate() {
		return _ddlEntry.getCreateDate();
	}

	/**
	* Sets the create date of this d d l entry.
	*
	* @param createDate the create date of this d d l entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ddlEntry.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this d d l entry.
	*
	* @return the modified date of this d d l entry
	*/
	public java.util.Date getModifiedDate() {
		return _ddlEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this d d l entry.
	*
	* @param modifiedDate the modified date of this d d l entry
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ddlEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the name of this d d l entry.
	*
	* @return the name of this d d l entry
	*/
	public java.lang.String getName() {
		return _ddlEntry.getName();
	}

	/**
	* Gets the localized name of this d d l entry. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized name for
	* @return the localized name of this d d l entry
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _ddlEntry.getName(locale);
	}

	/**
	* Gets the localized name of this d d l entry, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d l entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddlEntry.getName(locale, useDefault);
	}

	/**
	* Gets the localized name of this d d l entry. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @return the localized name of this d d l entry
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _ddlEntry.getName(languageId);
	}

	/**
	* Gets the localized name of this d d l entry, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d l entry
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddlEntry.getName(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized name of this d d l entry.
	*
	* @return the locales and localized name
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddlEntry.getNameMap();
	}

	/**
	* Sets the name of this d d l entry.
	*
	* @param name the name of this d d l entry
	*/
	public void setName(java.lang.String name) {
		_ddlEntry.setName(name);
	}

	/**
	* Sets the localized name of this d d l entry.
	*
	* @param name the localized name of this d d l entry
	* @param locale the locale to set the localized name for
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddlEntry.setName(name, locale);
	}

	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddlEntry.setName(name, locale, defaultLocale);
	}

	/**
	* Sets the localized names of this d d l entry from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d l entry
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_ddlEntry.setNameMap(nameMap);
	}

	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddlEntry.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Gets the description of this d d l entry.
	*
	* @return the description of this d d l entry
	*/
	public java.lang.String getDescription() {
		return _ddlEntry.getDescription();
	}

	/**
	* Sets the description of this d d l entry.
	*
	* @param description the description of this d d l entry
	*/
	public void setDescription(java.lang.String description) {
		_ddlEntry.setDescription(description);
	}

	/**
	* Gets the structure ID of this d d l entry.
	*
	* @return the structure ID of this d d l entry
	*/
	public long getStructureId() {
		return _ddlEntry.getStructureId();
	}

	/**
	* Sets the structure ID of this d d l entry.
	*
	* @param structureId the structure ID of this d d l entry
	*/
	public void setStructureId(long structureId) {
		_ddlEntry.setStructureId(structureId);
	}

	public boolean isNew() {
		return _ddlEntry.isNew();
	}

	public void setNew(boolean n) {
		_ddlEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddlEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddlEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddlEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddlEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddlEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddlEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddlEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDLEntryWrapper((DDLEntry)_ddlEntry.clone());
	}

	public int compareTo(DDLEntry ddlEntry) {
		return _ddlEntry.compareTo(ddlEntry);
	}

	public int hashCode() {
		return _ddlEntry.hashCode();
	}

	public DDLEntry toEscapedModel() {
		return new DDLEntryWrapper(_ddlEntry.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddlEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _ddlEntry.toXmlString();
	}

	public DDLEntry getWrappedDDLEntry() {
		return _ddlEntry;
	}

	private DDLEntry _ddlEntry;
}