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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link Team}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Team
 * @generated
 */
public class TeamWrapper implements Team {
	public TeamWrapper(Team team) {
		_team = team;
	}

	public long getPrimaryKey() {
		return _team.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_team.setPrimaryKey(pk);
	}

	public long getTeamId() {
		return _team.getTeamId();
	}

	public void setTeamId(long teamId) {
		_team.setTeamId(teamId);
	}

	public long getCompanyId() {
		return _team.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_team.setCompanyId(companyId);
	}

	public long getUserId() {
		return _team.getUserId();
	}

	public void setUserId(long userId) {
		_team.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _team.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_team.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _team.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_team.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _team.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_team.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _team.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_team.setModifiedDate(modifiedDate);
	}

	public long getGroupId() {
		return _team.getGroupId();
	}

	public void setGroupId(long groupId) {
		_team.setGroupId(groupId);
	}

	public java.lang.String getName() {
		return _team.getName();
	}

	public void setName(java.lang.String name) {
		_team.setName(name);
	}

	public java.lang.String getDescription() {
		return _team.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_team.setDescription(description);
	}

	public com.liferay.portal.model.Team toEscapedModel() {
		return _team.toEscapedModel();
	}

	public boolean isNew() {
		return _team.isNew();
	}

	public void setNew(boolean n) {
		_team.setNew(n);
	}

	public boolean isCachedModel() {
		return _team.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_team.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _team.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_team.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _team.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _team.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_team.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _team.clone();
	}

	public int compareTo(com.liferay.portal.model.Team team) {
		return _team.compareTo(team);
	}

	public int hashCode() {
		return _team.hashCode();
	}

	public java.lang.String toString() {
		return _team.toString();
	}

	public java.lang.String toXmlString() {
		return _team.toXmlString();
	}

	public com.liferay.portal.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _team.getRole();
	}

	public Team getWrappedTeam() {
		return _team;
	}

	private Team _team;
}