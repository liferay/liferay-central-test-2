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

import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.UserGroupGroupRolePK;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * <a href="UserGroupGroupRoleModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the UserGroupGroupRole table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupGroupRole
 * @see       com.liferay.portal.model.impl.UserGroupGroupRoleImpl
 * @see       com.liferay.portal.model.impl.UserGroupGroupRoleModelImpl
 * @generated
 */
public interface UserGroupGroupRoleModel extends BaseModel<UserGroupGroupRole> {
	public UserGroupGroupRolePK getPrimaryKey();

	public void setPrimaryKey(UserGroupGroupRolePK pk);

	public long getUserGroupId();

	public void setUserGroupId(long userGroupId);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getRoleId();

	public void setRoleId(long roleId);

	public UserGroupGroupRole toEscapedModel();

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(UserGroupGroupRole userGroupGroupRole);

	public int hashCode();

	public String toString();

	public String toXmlString();
}