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
 * This class is a wrapper for {@link DDMListEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMListEntry
 * @generated
 */
public class DDMListEntryWrapper implements DDMListEntry {
	public DDMListEntryWrapper(DDMListEntry ddmListEntry) {
		_ddmListEntry = ddmListEntry;
	}

	public Class<?> getModelClass() {
		return DDMListEntry.class;
	}

	public String getModelClassName() {
		return DDMListEntry.class.getName();
	}

	/**
	* Gets the primary key of this d d m list entry.
	*
	* @return the primary key of this d d m list entry
	*/
	public long getPrimaryKey() {
		return _ddmListEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this d d m list entry
	*
	* @param pk the primary key of this d d m list entry
	*/
	public void setPrimaryKey(long pk) {
		_ddmListEntry.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this d d m list entry.
	*
	* @return the uuid of this d d m list entry
	*/
	public java.lang.String getUuid() {
		return _ddmListEntry.getUuid();
	}

	/**
	* Sets the uuid of this d d m list entry.
	*
	* @param uuid the uuid of this d d m list entry
	*/
	public void setUuid(java.lang.String uuid) {
		_ddmListEntry.setUuid(uuid);
	}

	/**
	* Gets the list entry ID of this d d m list entry.
	*
	* @return the list entry ID of this d d m list entry
	*/
	public long getListEntryId() {
		return _ddmListEntry.getListEntryId();
	}

	/**
	* Sets the list entry ID of this d d m list entry.
	*
	* @param listEntryId the list entry ID of this d d m list entry
	*/
	public void setListEntryId(long listEntryId) {
		_ddmListEntry.setListEntryId(listEntryId);
	}

	/**
	* Gets the group ID of this d d m list entry.
	*
	* @return the group ID of this d d m list entry
	*/
	public long getGroupId() {
		return _ddmListEntry.getGroupId();
	}

	/**
	* Sets the group ID of this d d m list entry.
	*
	* @param groupId the group ID of this d d m list entry
	*/
	public void setGroupId(long groupId) {
		_ddmListEntry.setGroupId(groupId);
	}

	/**
	* Gets the company ID of this d d m list entry.
	*
	* @return the company ID of this d d m list entry
	*/
	public long getCompanyId() {
		return _ddmListEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this d d m list entry.
	*
	* @param companyId the company ID of this d d m list entry
	*/
	public void setCompanyId(long companyId) {
		_ddmListEntry.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this d d m list entry.
	*
	* @return the user ID of this d d m list entry
	*/
	public long getUserId() {
		return _ddmListEntry.getUserId();
	}

	/**
	* Sets the user ID of this d d m list entry.
	*
	* @param userId the user ID of this d d m list entry
	*/
	public void setUserId(long userId) {
		_ddmListEntry.setUserId(userId);
	}

	/**
	* Gets the user uuid of this d d m list entry.
	*
	* @return the user uuid of this d d m list entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmListEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this d d m list entry.
	*
	* @param userUuid the user uuid of this d d m list entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ddmListEntry.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this d d m list entry.
	*
	* @return the user name of this d d m list entry
	*/
	public java.lang.String getUserName() {
		return _ddmListEntry.getUserName();
	}

	/**
	* Sets the user name of this d d m list entry.
	*
	* @param userName the user name of this d d m list entry
	*/
	public void setUserName(java.lang.String userName) {
		_ddmListEntry.setUserName(userName);
	}

	/**
	* Gets the create date of this d d m list entry.
	*
	* @return the create date of this d d m list entry
	*/
	public java.util.Date getCreateDate() {
		return _ddmListEntry.getCreateDate();
	}

	/**
	* Sets the create date of this d d m list entry.
	*
	* @param createDate the create date of this d d m list entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ddmListEntry.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this d d m list entry.
	*
	* @return the modified date of this d d m list entry
	*/
	public java.util.Date getModifiedDate() {
		return _ddmListEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this d d m list entry.
	*
	* @param modifiedDate the modified date of this d d m list entry
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ddmListEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the name of this d d m list entry.
	*
	* @return the name of this d d m list entry
	*/
	public java.lang.String getName() {
		return _ddmListEntry.getName();
	}

	/**
	* Gets the localized name of this d d m list entry. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale to get the localized name for
	* @return the localized name of this d d m list entry
	*/
	public java.lang.String getName(java.util.Locale locale) {
		return _ddmListEntry.getName(locale);
	}

	/**
	* Gets the localized name of this d d m list entry, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m list entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	public java.lang.String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmListEntry.getName(locale, useDefault);
	}

	/**
	* Gets the localized name of this d d m list entry. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @return the localized name of this d d m list entry
	*/
	public java.lang.String getName(java.lang.String languageId) {
		return _ddmListEntry.getName(languageId);
	}

	/**
	* Gets the localized name of this d d m list entry, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the id of the language to get the localized name for
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this d d m list entry
	*/
	public java.lang.String getName(java.lang.String languageId,
		boolean useDefault) {
		return _ddmListEntry.getName(languageId, useDefault);
	}

	/**
	* Gets a map of the locales and localized name of this d d m list entry.
	*
	* @return the locales and localized name
	*/
	public java.util.Map<java.util.Locale, java.lang.String> getNameMap() {
		return _ddmListEntry.getNameMap();
	}

	/**
	* Sets the name of this d d m list entry.
	*
	* @param name the name of this d d m list entry
	*/
	public void setName(java.lang.String name) {
		_ddmListEntry.setName(name);
	}

	/**
	* Sets the localized name of this d d m list entry.
	*
	* @param name the localized name of this d d m list entry
	* @param locale the locale to set the localized name for
	*/
	public void setName(java.lang.String name, java.util.Locale locale) {
		_ddmListEntry.setName(name, locale);
	}

	public void setName(java.lang.String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmListEntry.setName(name, locale, defaultLocale);
	}

	/**
	* Sets the localized names of this d d m list entry from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this d d m list entry
	*/
	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap) {
		_ddmListEntry.setNameMap(nameMap);
	}

	public void setNameMap(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmListEntry.setNameMap(nameMap, defaultLocale);
	}

	/**
	* Gets the description of this d d m list entry.
	*
	* @return the description of this d d m list entry
	*/
	public java.lang.String getDescription() {
		return _ddmListEntry.getDescription();
	}

	/**
	* Sets the description of this d d m list entry.
	*
	* @param description the description of this d d m list entry
	*/
	public void setDescription(java.lang.String description) {
		_ddmListEntry.setDescription(description);
	}

	/**
	* Gets the structure ID of this d d m list entry.
	*
	* @return the structure ID of this d d m list entry
	*/
	public long getStructureId() {
		return _ddmListEntry.getStructureId();
	}

	/**
	* Sets the structure ID of this d d m list entry.
	*
	* @param structureId the structure ID of this d d m list entry
	*/
	public void setStructureId(long structureId) {
		_ddmListEntry.setStructureId(structureId);
	}

	public boolean isNew() {
		return _ddmListEntry.isNew();
	}

	public void setNew(boolean n) {
		_ddmListEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _ddmListEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ddmListEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ddmListEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ddmListEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmListEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmListEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmListEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new DDMListEntryWrapper((DDMListEntry)_ddmListEntry.clone());
	}

	public int compareTo(DDMListEntry ddmListEntry) {
		return _ddmListEntry.compareTo(ddmListEntry);
	}

	public int hashCode() {
		return _ddmListEntry.hashCode();
	}

	public DDMListEntry toEscapedModel() {
		return new DDMListEntryWrapper(_ddmListEntry.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ddmListEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _ddmListEntry.toXmlString();
	}

	public DDMListEntry getWrappedDDMListEntry() {
		return _ddmListEntry;
	}

	public void resetOriginalValues() {
		_ddmListEntry.resetOriginalValues();
	}

	private DDMListEntry _ddmListEntry;
}