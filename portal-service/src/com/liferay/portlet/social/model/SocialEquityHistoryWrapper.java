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
 * This class is a wrapper for {@link SocialEquityHistory}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityHistory
 * @generated
 */
public class SocialEquityHistoryWrapper implements SocialEquityHistory {
	public SocialEquityHistoryWrapper(SocialEquityHistory socialEquityHistory) {
		_socialEquityHistory = socialEquityHistory;
	}

	public long getPrimaryKey() {
		return _socialEquityHistory.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_socialEquityHistory.setPrimaryKey(pk);
	}

	public long getEquityHistoryId() {
		return _socialEquityHistory.getEquityHistoryId();
	}

	public void setEquityHistoryId(long equityHistoryId) {
		_socialEquityHistory.setEquityHistoryId(equityHistoryId);
	}

	public long getGroupId() {
		return _socialEquityHistory.getGroupId();
	}

	public void setGroupId(long groupId) {
		_socialEquityHistory.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _socialEquityHistory.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_socialEquityHistory.setCompanyId(companyId);
	}

	public long getUserId() {
		return _socialEquityHistory.getUserId();
	}

	public void setUserId(long userId) {
		_socialEquityHistory.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityHistory.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_socialEquityHistory.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _socialEquityHistory.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_socialEquityHistory.setCreateDate(createDate);
	}

	public int getPersonalEquity() {
		return _socialEquityHistory.getPersonalEquity();
	}

	public void setPersonalEquity(int personalEquity) {
		_socialEquityHistory.setPersonalEquity(personalEquity);
	}

	public com.liferay.portlet.social.model.SocialEquityHistory toEscapedModel() {
		return _socialEquityHistory.toEscapedModel();
	}

	public boolean isNew() {
		return _socialEquityHistory.isNew();
	}

	public void setNew(boolean n) {
		_socialEquityHistory.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialEquityHistory.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialEquityHistory.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialEquityHistory.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_socialEquityHistory.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialEquityHistory.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialEquityHistory.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialEquityHistory.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _socialEquityHistory.clone();
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory) {
		return _socialEquityHistory.compareTo(socialEquityHistory);
	}

	public int hashCode() {
		return _socialEquityHistory.hashCode();
	}

	public java.lang.String toString() {
		return _socialEquityHistory.toString();
	}

	public java.lang.String toXmlString() {
		return _socialEquityHistory.toXmlString();
	}

	public SocialEquityHistory getWrappedSocialEquityHistory() {
		return _socialEquityHistory;
	}

	private SocialEquityHistory _socialEquityHistory;
}