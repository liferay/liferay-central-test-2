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
 * This class is a wrapper for {@link SocialEquityUser}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityUser
 * @generated
 */
public class SocialEquityUserWrapper implements SocialEquityUser {
	public SocialEquityUserWrapper(SocialEquityUser socialEquityUser) {
		_socialEquityUser = socialEquityUser;
	}

	public long getPrimaryKey() {
		return _socialEquityUser.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_socialEquityUser.setPrimaryKey(pk);
	}

	public long getEquityUserId() {
		return _socialEquityUser.getEquityUserId();
	}

	public void setEquityUserId(long equityUserId) {
		_socialEquityUser.setEquityUserId(equityUserId);
	}

	public java.lang.String getEquityUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUser.getEquityUserUuid();
	}

	public void setEquityUserUuid(java.lang.String equityUserUuid) {
		_socialEquityUser.setEquityUserUuid(equityUserUuid);
	}

	public long getGroupId() {
		return _socialEquityUser.getGroupId();
	}

	public void setGroupId(long groupId) {
		_socialEquityUser.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _socialEquityUser.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_socialEquityUser.setCompanyId(companyId);
	}

	public long getUserId() {
		return _socialEquityUser.getUserId();
	}

	public void setUserId(long userId) {
		_socialEquityUser.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUser.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_socialEquityUser.setUserUuid(userUuid);
	}

	public double getContributionK() {
		return _socialEquityUser.getContributionK();
	}

	public void setContributionK(double contributionK) {
		_socialEquityUser.setContributionK(contributionK);
	}

	public double getContributionB() {
		return _socialEquityUser.getContributionB();
	}

	public void setContributionB(double contributionB) {
		_socialEquityUser.setContributionB(contributionB);
	}

	public double getParticipationK() {
		return _socialEquityUser.getParticipationK();
	}

	public void setParticipationK(double participationK) {
		_socialEquityUser.setParticipationK(participationK);
	}

	public double getParticipationB() {
		return _socialEquityUser.getParticipationB();
	}

	public void setParticipationB(double participationB) {
		_socialEquityUser.setParticipationB(participationB);
	}

	public int getRank() {
		return _socialEquityUser.getRank();
	}

	public void setRank(int rank) {
		_socialEquityUser.setRank(rank);
	}

	public com.liferay.portlet.social.model.SocialEquityUser toEscapedModel() {
		return _socialEquityUser.toEscapedModel();
	}

	public boolean isNew() {
		return _socialEquityUser.isNew();
	}

	public void setNew(boolean n) {
		_socialEquityUser.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialEquityUser.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialEquityUser.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialEquityUser.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_socialEquityUser.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialEquityUser.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialEquityUser.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialEquityUser.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _socialEquityUser.clone();
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser) {
		return _socialEquityUser.compareTo(socialEquityUser);
	}

	public int hashCode() {
		return _socialEquityUser.hashCode();
	}

	public java.lang.String toString() {
		return _socialEquityUser.toString();
	}

	public java.lang.String toXmlString() {
		return _socialEquityUser.toXmlString();
	}

	public double getContributionEquity() {
		return _socialEquityUser.getContributionEquity();
	}

	public double getParticipationEquity() {
		return _socialEquityUser.getParticipationEquity();
	}

	public SocialEquityUser getWrappedSocialEquityUser() {
		return _socialEquityUser;
	}

	private SocialEquityUser _socialEquityUser;
}