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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.model.UserTrackerPathSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="UserTrackerPathModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the UserTrackerPath table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerPathImpl
 * @see       com.liferay.portal.model.UserTrackerPath
 * @see       com.liferay.portal.model.UserTrackerPathModel
 * @generated
 */
public class UserTrackerPathModelImpl extends BaseModelImpl<UserTrackerPath> {
	public static final String TABLE_NAME = "UserTrackerPath";
	public static final Object[][] TABLE_COLUMNS = {
			{ "userTrackerPathId", new Integer(Types.BIGINT) },
			{ "userTrackerId", new Integer(Types.BIGINT) },
			{ "path_", new Integer(Types.VARCHAR) },
			{ "pathDate", new Integer(Types.TIMESTAMP) }
		};
	public static final String TABLE_SQL_CREATE = "create table UserTrackerPath (userTrackerPathId LONG not null primary key,userTrackerId LONG,path_ STRING null,pathDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table UserTrackerPath";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.UserTrackerPath"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.UserTrackerPath"),
			true);

	public static UserTrackerPath toModel(UserTrackerPathSoap soapModel) {
		UserTrackerPath model = new UserTrackerPathImpl();

		model.setUserTrackerPathId(soapModel.getUserTrackerPathId());
		model.setUserTrackerId(soapModel.getUserTrackerId());
		model.setPath(soapModel.getPath());
		model.setPathDate(soapModel.getPathDate());

		return model;
	}

	public static List<UserTrackerPath> toModels(
		UserTrackerPathSoap[] soapModels) {
		List<UserTrackerPath> models = new ArrayList<UserTrackerPath>(soapModels.length);

		for (UserTrackerPathSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserTrackerPath"));

	public UserTrackerPathModelImpl() {
	}

	public long getPrimaryKey() {
		return _userTrackerPathId;
	}

	public void setPrimaryKey(long pk) {
		setUserTrackerPathId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_userTrackerPathId);
	}

	public long getUserTrackerPathId() {
		return _userTrackerPathId;
	}

	public void setUserTrackerPathId(long userTrackerPathId) {
		_userTrackerPathId = userTrackerPathId;
	}

	public long getUserTrackerId() {
		return _userTrackerId;
	}

	public void setUserTrackerId(long userTrackerId) {
		_userTrackerId = userTrackerId;
	}

	public String getPath() {
		return GetterUtil.getString(_path);
	}

	public void setPath(String path) {
		_path = path;
	}

	public Date getPathDate() {
		return _pathDate;
	}

	public void setPathDate(Date pathDate) {
		_pathDate = pathDate;
	}

	public UserTrackerPath toEscapedModel() {
		if (isEscapedModel()) {
			return (UserTrackerPath)this;
		}
		else {
			UserTrackerPath model = new UserTrackerPathImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUserTrackerPathId(getUserTrackerPathId());
			model.setUserTrackerId(getUserTrackerId());
			model.setPath(HtmlUtil.escape(getPath()));
			model.setPathDate(getPathDate());

			model = (UserTrackerPath)Proxy.newProxyInstance(UserTrackerPath.class.getClassLoader(),
					new Class[] { UserTrackerPath.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					UserTrackerPath.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		UserTrackerPathImpl clone = new UserTrackerPathImpl();

		clone.setUserTrackerPathId(getUserTrackerPathId());
		clone.setUserTrackerId(getUserTrackerId());
		clone.setPath(getPath());
		clone.setPathDate(getPathDate());

		return clone;
	}

	public int compareTo(UserTrackerPath userTrackerPath) {
		long pk = userTrackerPath.getPrimaryKey();

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

		UserTrackerPath userTrackerPath = null;

		try {
			userTrackerPath = (UserTrackerPath)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = userTrackerPath.getPrimaryKey();

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
		StringBundler sb = new StringBundler(9);

		sb.append("{userTrackerPathId=");
		sb.append(getUserTrackerPathId());
		sb.append(", userTrackerId=");
		sb.append(getUserTrackerId());
		sb.append(", path=");
		sb.append(getPath());
		sb.append(", pathDate=");
		sb.append(getPathDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(16);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.UserTrackerPath");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>userTrackerPathId</column-name><column-value><![CDATA[");
		sb.append(getUserTrackerPathId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userTrackerId</column-name><column-value><![CDATA[");
		sb.append(getUserTrackerId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>path</column-name><column-value><![CDATA[");
		sb.append(getPath());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>pathDate</column-name><column-value><![CDATA[");
		sb.append(getPathDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _userTrackerPathId;
	private long _userTrackerId;
	private String _path;
	private Date _pathDate;
	private transient ExpandoBridge _expandoBridge;
}