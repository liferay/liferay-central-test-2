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
 * This class is a wrapper for {@link AnnouncementsEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsEntry
 * @generated
 */
public class AnnouncementsEntryWrapper implements AnnouncementsEntry {
	public AnnouncementsEntryWrapper(AnnouncementsEntry announcementsEntry) {
		_announcementsEntry = announcementsEntry;
	}

	/**
	* Gets the primary key of this announcements entry.
	*
	* @return the primary key of this announcements entry
	*/
	public long getPrimaryKey() {
		return _announcementsEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this announcements entry
	*
	* @param pk the primary key of this announcements entry
	*/
	public void setPrimaryKey(long pk) {
		_announcementsEntry.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this announcements entry.
	*
	* @return the uuid of this announcements entry
	*/
	public java.lang.String getUuid() {
		return _announcementsEntry.getUuid();
	}

	/**
	* Sets the uuid of this announcements entry.
	*
	* @param uuid the uuid of this announcements entry
	*/
	public void setUuid(java.lang.String uuid) {
		_announcementsEntry.setUuid(uuid);
	}

	/**
	* Gets the entry ID of this announcements entry.
	*
	* @return the entry ID of this announcements entry
	*/
	public long getEntryId() {
		return _announcementsEntry.getEntryId();
	}

	/**
	* Sets the entry ID of this announcements entry.
	*
	* @param entryId the entry ID of this announcements entry
	*/
	public void setEntryId(long entryId) {
		_announcementsEntry.setEntryId(entryId);
	}

	/**
	* Gets the company ID of this announcements entry.
	*
	* @return the company ID of this announcements entry
	*/
	public long getCompanyId() {
		return _announcementsEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this announcements entry.
	*
	* @param companyId the company ID of this announcements entry
	*/
	public void setCompanyId(long companyId) {
		_announcementsEntry.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this announcements entry.
	*
	* @return the user ID of this announcements entry
	*/
	public long getUserId() {
		return _announcementsEntry.getUserId();
	}

	/**
	* Sets the user ID of this announcements entry.
	*
	* @param userId the user ID of this announcements entry
	*/
	public void setUserId(long userId) {
		_announcementsEntry.setUserId(userId);
	}

	/**
	* Gets the user uuid of this announcements entry.
	*
	* @return the user uuid of this announcements entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this announcements entry.
	*
	* @param userUuid the user uuid of this announcements entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_announcementsEntry.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this announcements entry.
	*
	* @return the user name of this announcements entry
	*/
	public java.lang.String getUserName() {
		return _announcementsEntry.getUserName();
	}

	/**
	* Sets the user name of this announcements entry.
	*
	* @param userName the user name of this announcements entry
	*/
	public void setUserName(java.lang.String userName) {
		_announcementsEntry.setUserName(userName);
	}

	/**
	* Gets the create date of this announcements entry.
	*
	* @return the create date of this announcements entry
	*/
	public java.util.Date getCreateDate() {
		return _announcementsEntry.getCreateDate();
	}

	/**
	* Sets the create date of this announcements entry.
	*
	* @param createDate the create date of this announcements entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_announcementsEntry.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this announcements entry.
	*
	* @return the modified date of this announcements entry
	*/
	public java.util.Date getModifiedDate() {
		return _announcementsEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this announcements entry.
	*
	* @param modifiedDate the modified date of this announcements entry
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_announcementsEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the class name of the model instance this announcements entry is polymorphically associated with.
	*
	* @return the class name of the model instance this announcements entry is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _announcementsEntry.getClassName();
	}

	/**
	* Gets the class name ID of this announcements entry.
	*
	* @return the class name ID of this announcements entry
	*/
	public long getClassNameId() {
		return _announcementsEntry.getClassNameId();
	}

	/**
	* Sets the class name ID of this announcements entry.
	*
	* @param classNameId the class name ID of this announcements entry
	*/
	public void setClassNameId(long classNameId) {
		_announcementsEntry.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this announcements entry.
	*
	* @return the class p k of this announcements entry
	*/
	public long getClassPK() {
		return _announcementsEntry.getClassPK();
	}

	/**
	* Sets the class p k of this announcements entry.
	*
	* @param classPK the class p k of this announcements entry
	*/
	public void setClassPK(long classPK) {
		_announcementsEntry.setClassPK(classPK);
	}

	/**
	* Gets the title of this announcements entry.
	*
	* @return the title of this announcements entry
	*/
	public java.lang.String getTitle() {
		return _announcementsEntry.getTitle();
	}

	/**
	* Sets the title of this announcements entry.
	*
	* @param title the title of this announcements entry
	*/
	public void setTitle(java.lang.String title) {
		_announcementsEntry.setTitle(title);
	}

	/**
	* Gets the content of this announcements entry.
	*
	* @return the content of this announcements entry
	*/
	public java.lang.String getContent() {
		return _announcementsEntry.getContent();
	}

	/**
	* Sets the content of this announcements entry.
	*
	* @param content the content of this announcements entry
	*/
	public void setContent(java.lang.String content) {
		_announcementsEntry.setContent(content);
	}

	/**
	* Gets the url of this announcements entry.
	*
	* @return the url of this announcements entry
	*/
	public java.lang.String getUrl() {
		return _announcementsEntry.getUrl();
	}

	/**
	* Sets the url of this announcements entry.
	*
	* @param url the url of this announcements entry
	*/
	public void setUrl(java.lang.String url) {
		_announcementsEntry.setUrl(url);
	}

	/**
	* Gets the type of this announcements entry.
	*
	* @return the type of this announcements entry
	*/
	public java.lang.String getType() {
		return _announcementsEntry.getType();
	}

	/**
	* Sets the type of this announcements entry.
	*
	* @param type the type of this announcements entry
	*/
	public void setType(java.lang.String type) {
		_announcementsEntry.setType(type);
	}

	/**
	* Gets the display date of this announcements entry.
	*
	* @return the display date of this announcements entry
	*/
	public java.util.Date getDisplayDate() {
		return _announcementsEntry.getDisplayDate();
	}

	/**
	* Sets the display date of this announcements entry.
	*
	* @param displayDate the display date of this announcements entry
	*/
	public void setDisplayDate(java.util.Date displayDate) {
		_announcementsEntry.setDisplayDate(displayDate);
	}

	/**
	* Gets the expiration date of this announcements entry.
	*
	* @return the expiration date of this announcements entry
	*/
	public java.util.Date getExpirationDate() {
		return _announcementsEntry.getExpirationDate();
	}

	/**
	* Sets the expiration date of this announcements entry.
	*
	* @param expirationDate the expiration date of this announcements entry
	*/
	public void setExpirationDate(java.util.Date expirationDate) {
		_announcementsEntry.setExpirationDate(expirationDate);
	}

	/**
	* Gets the priority of this announcements entry.
	*
	* @return the priority of this announcements entry
	*/
	public int getPriority() {
		return _announcementsEntry.getPriority();
	}

	/**
	* Sets the priority of this announcements entry.
	*
	* @param priority the priority of this announcements entry
	*/
	public void setPriority(int priority) {
		_announcementsEntry.setPriority(priority);
	}

	/**
	* Gets the alert of this announcements entry.
	*
	* @return the alert of this announcements entry
	*/
	public boolean getAlert() {
		return _announcementsEntry.getAlert();
	}

	/**
	* Determines if this announcements entry is alert.
	*
	* @return <code>true</code> if this announcements entry is alert; <code>false</code> otherwise
	*/
	public boolean isAlert() {
		return _announcementsEntry.isAlert();
	}

	/**
	* Sets whether this announcements entry is alert.
	*
	* @param alert the alert of this announcements entry
	*/
	public void setAlert(boolean alert) {
		_announcementsEntry.setAlert(alert);
	}

	public boolean isNew() {
		return _announcementsEntry.isNew();
	}

	public void setNew(boolean n) {
		_announcementsEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _announcementsEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_announcementsEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _announcementsEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_announcementsEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _announcementsEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _announcementsEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_announcementsEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new AnnouncementsEntryWrapper((AnnouncementsEntry)_announcementsEntry.clone());
	}

	public int compareTo(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry) {
		return _announcementsEntry.compareTo(announcementsEntry);
	}

	public int hashCode() {
		return _announcementsEntry.hashCode();
	}

	public com.liferay.portlet.announcements.model.AnnouncementsEntry toEscapedModel() {
		return new AnnouncementsEntryWrapper(_announcementsEntry.toEscapedModel());
	}

	public java.lang.String toString() {
		return _announcementsEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _announcementsEntry.toXmlString();
	}

	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _announcementsEntry.getGroupId();
	}

	public AnnouncementsEntry getWrappedAnnouncementsEntry() {
		return _announcementsEntry;
	}

	private AnnouncementsEntry _announcementsEntry;
}