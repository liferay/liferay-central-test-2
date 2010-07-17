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

	public long getPrimaryKey() {
		return _ratingsEntry.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_ratingsEntry.setPrimaryKey(pk);
	}

	public long getEntryId() {
		return _ratingsEntry.getEntryId();
	}

	public void setEntryId(long entryId) {
		_ratingsEntry.setEntryId(entryId);
	}

	public long getCompanyId() {
		return _ratingsEntry.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_ratingsEntry.setCompanyId(companyId);
	}

	public long getUserId() {
		return _ratingsEntry.getUserId();
	}

	public void setUserId(long userId) {
		_ratingsEntry.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntry.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_ratingsEntry.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _ratingsEntry.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_ratingsEntry.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _ratingsEntry.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_ratingsEntry.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _ratingsEntry.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_ratingsEntry.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _ratingsEntry.getClassName();
	}

	public long getClassNameId() {
		return _ratingsEntry.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_ratingsEntry.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _ratingsEntry.getClassPK();
	}

	public void setClassPK(long classPK) {
		_ratingsEntry.setClassPK(classPK);
	}

	public double getScore() {
		return _ratingsEntry.getScore();
	}

	public void setScore(double score) {
		_ratingsEntry.setScore(score);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry toEscapedModel() {
		return _ratingsEntry.toEscapedModel();
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
		return _ratingsEntry.clone();
	}

	public int compareTo(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry) {
		return _ratingsEntry.compareTo(ratingsEntry);
	}

	public int hashCode() {
		return _ratingsEntry.hashCode();
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