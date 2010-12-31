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

package com.liferay.portlet.ratings.model;

/**
 * <p>
 * This class is a wrapper for {@link RatingsEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsEntry
 * @generated
 */
public class RatingsEntryWrapper implements RatingsEntry {
	public RatingsEntryWrapper(RatingsEntry ratingsEntry) {
		_ratingsEntry = ratingsEntry;
	}

	/**
	* Gets the primary key of this ratings entry.
	*
	* @return the primary key of this ratings entry
	*/
	public long getPrimaryKey() {
		return _ratingsEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this ratings entry
	*
	* @param pk the primary key of this ratings entry
	*/
	public void setPrimaryKey(long pk) {
		_ratingsEntry.setPrimaryKey(pk);
	}

	/**
	* Gets the entry ID of this ratings entry.
	*
	* @return the entry ID of this ratings entry
	*/
	public long getEntryId() {
		return _ratingsEntry.getEntryId();
	}

	/**
	* Sets the entry ID of this ratings entry.
	*
	* @param entryId the entry ID of this ratings entry
	*/
	public void setEntryId(long entryId) {
		_ratingsEntry.setEntryId(entryId);
	}

	/**
	* Gets the company ID of this ratings entry.
	*
	* @return the company ID of this ratings entry
	*/
	public long getCompanyId() {
		return _ratingsEntry.getCompanyId();
	}

	/**
	* Sets the company ID of this ratings entry.
	*
	* @param companyId the company ID of this ratings entry
	*/
	public void setCompanyId(long companyId) {
		_ratingsEntry.setCompanyId(companyId);
	}

	/**
	* Gets the user ID of this ratings entry.
	*
	* @return the user ID of this ratings entry
	*/
	public long getUserId() {
		return _ratingsEntry.getUserId();
	}

	/**
	* Sets the user ID of this ratings entry.
	*
	* @param userId the user ID of this ratings entry
	*/
	public void setUserId(long userId) {
		_ratingsEntry.setUserId(userId);
	}

	/**
	* Gets the user uuid of this ratings entry.
	*
	* @return the user uuid of this ratings entry
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntry.getUserUuid();
	}

	/**
	* Sets the user uuid of this ratings entry.
	*
	* @param userUuid the user uuid of this ratings entry
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ratingsEntry.setUserUuid(userUuid);
	}

	/**
	* Gets the user name of this ratings entry.
	*
	* @return the user name of this ratings entry
	*/
	public java.lang.String getUserName() {
		return _ratingsEntry.getUserName();
	}

	/**
	* Sets the user name of this ratings entry.
	*
	* @param userName the user name of this ratings entry
	*/
	public void setUserName(java.lang.String userName) {
		_ratingsEntry.setUserName(userName);
	}

	/**
	* Gets the create date of this ratings entry.
	*
	* @return the create date of this ratings entry
	*/
	public java.util.Date getCreateDate() {
		return _ratingsEntry.getCreateDate();
	}

	/**
	* Sets the create date of this ratings entry.
	*
	* @param createDate the create date of this ratings entry
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ratingsEntry.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this ratings entry.
	*
	* @return the modified date of this ratings entry
	*/
	public java.util.Date getModifiedDate() {
		return _ratingsEntry.getModifiedDate();
	}

	/**
	* Sets the modified date of this ratings entry.
	*
	* @param modifiedDate the modified date of this ratings entry
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_ratingsEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the class name of the model instance this ratings entry is polymorphically associated with.
	*
	* @return the class name of the model instance this ratings entry is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _ratingsEntry.getClassName();
	}

	/**
	* Gets the class name ID of this ratings entry.
	*
	* @return the class name ID of this ratings entry
	*/
	public long getClassNameId() {
		return _ratingsEntry.getClassNameId();
	}

	/**
	* Sets the class name ID of this ratings entry.
	*
	* @param classNameId the class name ID of this ratings entry
	*/
	public void setClassNameId(long classNameId) {
		_ratingsEntry.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this ratings entry.
	*
	* @return the class p k of this ratings entry
	*/
	public long getClassPK() {
		return _ratingsEntry.getClassPK();
	}

	/**
	* Sets the class p k of this ratings entry.
	*
	* @param classPK the class p k of this ratings entry
	*/
	public void setClassPK(long classPK) {
		_ratingsEntry.setClassPK(classPK);
	}

	/**
	* Gets the score of this ratings entry.
	*
	* @return the score of this ratings entry
	*/
	public double getScore() {
		return _ratingsEntry.getScore();
	}

	/**
	* Sets the score of this ratings entry.
	*
	* @param score the score of this ratings entry
	*/
	public void setScore(double score) {
		_ratingsEntry.setScore(score);
	}

	public boolean isNew() {
		return _ratingsEntry.isNew();
	}

	public void setNew(boolean n) {
		_ratingsEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _ratingsEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ratingsEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ratingsEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ratingsEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ratingsEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ratingsEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ratingsEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new RatingsEntryWrapper((RatingsEntry)_ratingsEntry.clone());
	}

	public int compareTo(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry) {
		return _ratingsEntry.compareTo(ratingsEntry);
	}

	public int hashCode() {
		return _ratingsEntry.hashCode();
	}

	public com.liferay.portlet.ratings.model.RatingsEntry toEscapedModel() {
		return new RatingsEntryWrapper(_ratingsEntry.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ratingsEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _ratingsEntry.toXmlString();
	}

	public RatingsEntry getWrappedRatingsEntry() {
		return _ratingsEntry;
	}

	private RatingsEntry _ratingsEntry;
}