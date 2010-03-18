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

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.UserIdMapper;
import com.liferay.portal.model.UserIdMapperSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="UserIdMapperModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the UserIdMapper table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserIdMapperImpl
 * @see       com.liferay.portal.model.UserIdMapper
 * @see       com.liferay.portal.model.UserIdMapperModel
 * @generated
 */
public class UserIdMapperModelImpl extends BaseModelImpl<UserIdMapper> {
	public static final String TABLE_NAME = "UserIdMapper";
	public static final Object[][] TABLE_COLUMNS = {
			{ "userIdMapperId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "type_", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) },
			{ "externalUserId", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table UserIdMapper (userIdMapperId LONG not null primary key,userId LONG,type_ VARCHAR(75) null,description VARCHAR(75) null,externalUserId VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table UserIdMapper";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.UserIdMapper"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.UserIdMapper"),
			true);

	public static UserIdMapper toModel(UserIdMapperSoap soapModel) {
		UserIdMapper model = new UserIdMapperImpl();

		model.setUserIdMapperId(soapModel.getUserIdMapperId());
		model.setUserId(soapModel.getUserId());
		model.setType(soapModel.getType());
		model.setDescription(soapModel.getDescription());
		model.setExternalUserId(soapModel.getExternalUserId());

		return model;
	}

	public static List<UserIdMapper> toModels(UserIdMapperSoap[] soapModels) {
		List<UserIdMapper> models = new ArrayList<UserIdMapper>(soapModels.length);

		for (UserIdMapperSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserIdMapper"));

	public UserIdMapperModelImpl() {
	}

	public long getPrimaryKey() {
		return _userIdMapperId;
	}

	public void setPrimaryKey(long pk) {
		setUserIdMapperId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_userIdMapperId);
	}

	public long getUserIdMapperId() {
		return _userIdMapperId;
	}

	public void setUserIdMapperId(long userIdMapperId) {
		_userIdMapperId = userIdMapperId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = userId;
		}
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public String getType() {
		if (_type == null) {
			return StringPool.BLANK;
		}
		else {
			return _type;
		}
	}

	public void setType(String type) {
		_type = type;

		if (_originalType == null) {
			_originalType = type;
		}
	}

	public String getOriginalType() {
		return GetterUtil.getString(_originalType);
	}

	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getExternalUserId() {
		if (_externalUserId == null) {
			return StringPool.BLANK;
		}
		else {
			return _externalUserId;
		}
	}

	public void setExternalUserId(String externalUserId) {
		_externalUserId = externalUserId;

		if (_originalExternalUserId == null) {
			_originalExternalUserId = externalUserId;
		}
	}

	public String getOriginalExternalUserId() {
		return GetterUtil.getString(_originalExternalUserId);
	}

	public UserIdMapper toEscapedModel() {
		if (isEscapedModel()) {
			return (UserIdMapper)this;
		}
		else {
			UserIdMapper model = new UserIdMapperImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUserIdMapperId(getUserIdMapperId());
			model.setUserId(getUserId());
			model.setType(HtmlUtil.escape(getType()));
			model.setDescription(HtmlUtil.escape(getDescription()));
			model.setExternalUserId(HtmlUtil.escape(getExternalUserId()));

			model = (UserIdMapper)Proxy.newProxyInstance(UserIdMapper.class.getClassLoader(),
					new Class[] { UserIdMapper.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					UserIdMapper.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		UserIdMapperImpl clone = new UserIdMapperImpl();

		clone.setUserIdMapperId(getUserIdMapperId());
		clone.setUserId(getUserId());
		clone.setType(getType());
		clone.setDescription(getDescription());
		clone.setExternalUserId(getExternalUserId());

		return clone;
	}

	public int compareTo(UserIdMapper userIdMapper) {
		long pk = userIdMapper.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		UserIdMapper userIdMapper = null;

		try {
			userIdMapper = (UserIdMapper)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = userIdMapper.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{userIdMapperId=");
		sb.append(getUserIdMapperId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", externalUserId=");
		sb.append(getExternalUserId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.UserIdMapper");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>userIdMapperId</column-name><column-value><![CDATA[");
		sb.append(getUserIdMapperId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>externalUserId</column-name><column-value><![CDATA[");
		sb.append(getExternalUserId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _userIdMapperId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _type;
	private String _originalType;
	private String _description;
	private String _externalUserId;
	private String _originalExternalUserId;
	private transient ExpandoBridge _expandoBridge;
}