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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.UserGroupGroupRole;
import com.liferay.portal.model.UserGroupGroupRoleSoap;
import com.liferay.portal.service.persistence.UserGroupGroupRolePK;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="UserGroupGroupRoleModelImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see       UserGroupGroupRoleImpl
 * @see       com.liferay.portal.model.UserGroupGroupRole
 * @see       com.liferay.portal.model.UserGroupGroupRoleModel
 * @generated
 */
public class UserGroupGroupRoleModelImpl extends BaseModelImpl<UserGroupGroupRole> {
	public static final String TABLE_NAME = "UserGroupGroupRole";
	public static final Object[][] TABLE_COLUMNS = {
			{ "userGroupId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "roleId", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table UserGroupGroupRole (userGroupId LONG not null,groupId LONG not null,roleId LONG not null,primary key (userGroupId, groupId, roleId))";
	public static final String TABLE_SQL_DROP = "drop table UserGroupGroupRole";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.UserGroupGroupRole"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.UserGroupGroupRole"),
			true);

	public static UserGroupGroupRole toModel(UserGroupGroupRoleSoap soapModel) {
		UserGroupGroupRole model = new UserGroupGroupRoleImpl();

		model.setUserGroupId(soapModel.getUserGroupId());
		model.setGroupId(soapModel.getGroupId());
		model.setRoleId(soapModel.getRoleId());

		return model;
	}

	public static List<UserGroupGroupRole> toModels(
		UserGroupGroupRoleSoap[] soapModels) {
		List<UserGroupGroupRole> models = new ArrayList<UserGroupGroupRole>(soapModels.length);

		for (UserGroupGroupRoleSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserGroupGroupRole"));

	public UserGroupGroupRoleModelImpl() {
	}

	public UserGroupGroupRolePK getPrimaryKey() {
		return new UserGroupGroupRolePK(_userGroupId, _groupId, _roleId);
	}

	public void setPrimaryKey(UserGroupGroupRolePK pk) {
		setUserGroupId(pk.userGroupId);
		setGroupId(pk.groupId);
		setRoleId(pk.roleId);
	}

	public Serializable getPrimaryKeyObj() {
		return new UserGroupGroupRolePK(_userGroupId, _groupId, _roleId);
	}

	public long getUserGroupId() {
		return _userGroupId;
	}

	public void setUserGroupId(long userGroupId) {
		_userGroupId = userGroupId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getRoleId() {
		return _roleId;
	}

	public void setRoleId(long roleId) {
		_roleId = roleId;
	}

	public UserGroupGroupRole toEscapedModel() {
		if (isEscapedModel()) {
			return (UserGroupGroupRole)this;
		}
		else {
			return (UserGroupGroupRole)Proxy.newProxyInstance(UserGroupGroupRole.class.getClassLoader(),
				new Class[] { UserGroupGroupRole.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public Object clone() {
		UserGroupGroupRoleImpl clone = new UserGroupGroupRoleImpl();

		clone.setUserGroupId(getUserGroupId());
		clone.setGroupId(getGroupId());
		clone.setRoleId(getRoleId());

		return clone;
	}

	public int compareTo(UserGroupGroupRole userGroupGroupRole) {
		UserGroupGroupRolePK pk = userGroupGroupRole.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		UserGroupGroupRole userGroupGroupRole = null;

		try {
			userGroupGroupRole = (UserGroupGroupRole)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		UserGroupGroupRolePK pk = userGroupGroupRole.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{userGroupId=");
		sb.append(getUserGroupId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", roleId=");
		sb.append(getRoleId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.UserGroupGroupRole");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>userGroupId</column-name><column-value><![CDATA[");
		sb.append(getUserGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>roleId</column-name><column-value><![CDATA[");
		sb.append(getRoleId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _userGroupId;
	private long _groupId;
	private long _roleId;
}