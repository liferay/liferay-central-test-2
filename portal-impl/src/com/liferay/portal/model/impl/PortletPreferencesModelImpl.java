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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.PortletPreferencesSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="PortletPreferencesModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the PortletPreferences table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPreferencesImpl
 * @see       com.liferay.portal.model.PortletPreferences
 * @see       com.liferay.portal.model.PortletPreferencesModel
 * @generated
 */
public class PortletPreferencesModelImpl extends BaseModelImpl<PortletPreferences> {
	public static final String TABLE_NAME = "PortletPreferences";
	public static final Object[][] TABLE_COLUMNS = {
			{ "portletPreferencesId", new Integer(Types.BIGINT) },
			{ "ownerId", new Integer(Types.BIGINT) },
			{ "ownerType", new Integer(Types.INTEGER) },
			{ "plid", new Integer(Types.BIGINT) },
			{ "portletId", new Integer(Types.VARCHAR) },
			{ "preferences", new Integer(Types.CLOB) }
		};
	public static final String TABLE_SQL_CREATE = "create table PortletPreferences (portletPreferencesId LONG not null primary key,ownerId LONG,ownerType INTEGER,plid LONG,portletId VARCHAR(200) null,preferences TEXT null)";
	public static final String TABLE_SQL_DROP = "drop table PortletPreferences";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.PortletPreferences"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.PortletPreferences"),
			true);

	public static PortletPreferences toModel(PortletPreferencesSoap soapModel) {
		PortletPreferences model = new PortletPreferencesImpl();

		model.setPortletPreferencesId(soapModel.getPortletPreferencesId());
		model.setOwnerId(soapModel.getOwnerId());
		model.setOwnerType(soapModel.getOwnerType());
		model.setPlid(soapModel.getPlid());
		model.setPortletId(soapModel.getPortletId());
		model.setPreferences(soapModel.getPreferences());

		return model;
	}

	public static List<PortletPreferences> toModels(
		PortletPreferencesSoap[] soapModels) {
		List<PortletPreferences> models = new ArrayList<PortletPreferences>(soapModels.length);

		for (PortletPreferencesSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PortletPreferences"));

	public PortletPreferencesModelImpl() {
	}

	public long getPrimaryKey() {
		return _portletPreferencesId;
	}

	public void setPrimaryKey(long pk) {
		setPortletPreferencesId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_portletPreferencesId);
	}

	public long getPortletPreferencesId() {
		return _portletPreferencesId;
	}

	public void setPortletPreferencesId(long portletPreferencesId) {
		_portletPreferencesId = portletPreferencesId;
	}

	public long getOwnerId() {
		return _ownerId;
	}

	public void setOwnerId(long ownerId) {
		_ownerId = ownerId;

		if (!_setOriginalOwnerId) {
			_setOriginalOwnerId = true;

			_originalOwnerId = ownerId;
		}
	}

	public long getOriginalOwnerId() {
		return _originalOwnerId;
	}

	public int getOwnerType() {
		return _ownerType;
	}

	public void setOwnerType(int ownerType) {
		_ownerType = ownerType;

		if (!_setOriginalOwnerType) {
			_setOriginalOwnerType = true;

			_originalOwnerType = ownerType;
		}
	}

	public int getOriginalOwnerType() {
		return _originalOwnerType;
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;

		if (!_setOriginalPlid) {
			_setOriginalPlid = true;

			_originalPlid = plid;
		}
	}

	public long getOriginalPlid() {
		return _originalPlid;
	}

	public String getPortletId() {
		if (_portletId == null) {
			return StringPool.BLANK;
		}
		else {
			return _portletId;
		}
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;

		if (_originalPortletId == null) {
			_originalPortletId = portletId;
		}
	}

	public String getOriginalPortletId() {
		return GetterUtil.getString(_originalPortletId);
	}

	public String getPreferences() {
		if (_preferences == null) {
			return StringPool.BLANK;
		}
		else {
			return _preferences;
		}
	}

	public void setPreferences(String preferences) {
		_preferences = preferences;
	}

	public PortletPreferences toEscapedModel() {
		if (isEscapedModel()) {
			return (PortletPreferences)this;
		}
		else {
			PortletPreferences model = new PortletPreferencesImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setPortletPreferencesId(getPortletPreferencesId());
			model.setOwnerId(getOwnerId());
			model.setOwnerType(getOwnerType());
			model.setPlid(getPlid());
			model.setPortletId(HtmlUtil.escape(getPortletId()));
			model.setPreferences(HtmlUtil.escape(getPreferences()));

			model = (PortletPreferences)Proxy.newProxyInstance(PortletPreferences.class.getClassLoader(),
					new Class[] { PortletPreferences.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					PortletPreferences.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		PortletPreferencesImpl clone = new PortletPreferencesImpl();

		clone.setPortletPreferencesId(getPortletPreferencesId());
		clone.setOwnerId(getOwnerId());
		clone.setOwnerType(getOwnerType());
		clone.setPlid(getPlid());
		clone.setPortletId(getPortletId());
		clone.setPreferences(getPreferences());

		return clone;
	}

	public int compareTo(PortletPreferences portletPreferences) {
		long pk = portletPreferences.getPrimaryKey();

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

		PortletPreferences portletPreferences = null;

		try {
			portletPreferences = (PortletPreferences)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = portletPreferences.getPrimaryKey();

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
		StringBundler sb = new StringBundler(13);

		sb.append("{portletPreferencesId=");
		sb.append(getPortletPreferencesId());
		sb.append(", ownerId=");
		sb.append(getOwnerId());
		sb.append(", ownerType=");
		sb.append(getOwnerType());
		sb.append(", plid=");
		sb.append(getPlid());
		sb.append(", portletId=");
		sb.append(getPortletId());
		sb.append(", preferences=");
		sb.append(getPreferences());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.PortletPreferences");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>portletPreferencesId</column-name><column-value><![CDATA[");
		sb.append(getPortletPreferencesId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>ownerId</column-name><column-value><![CDATA[");
		sb.append(getOwnerId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>ownerType</column-name><column-value><![CDATA[");
		sb.append(getOwnerType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>plid</column-name><column-value><![CDATA[");
		sb.append(getPlid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>portletId</column-name><column-value><![CDATA[");
		sb.append(getPortletId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>preferences</column-name><column-value><![CDATA[");
		sb.append(getPreferences());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _portletPreferencesId;
	private long _ownerId;
	private long _originalOwnerId;
	private boolean _setOriginalOwnerId;
	private int _ownerType;
	private int _originalOwnerType;
	private boolean _setOriginalOwnerType;
	private long _plid;
	private long _originalPlid;
	private boolean _setOriginalPlid;
	private String _portletId;
	private String _originalPortletId;
	private String _preferences;
	private transient ExpandoBridge _expandoBridge;
}