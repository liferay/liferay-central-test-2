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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.social.model.SocialEquitySetting;
import com.liferay.portlet.social.model.SocialEquitySettingSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="SocialEquitySettingModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the SocialEquitySetting table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquitySettingImpl
 * @see       com.liferay.portlet.social.model.SocialEquitySetting
 * @see       com.liferay.portlet.social.model.SocialEquitySettingModel
 * @generated
 */
public class SocialEquitySettingModelImpl extends BaseModelImpl<SocialEquitySetting> {
	public static final String TABLE_NAME = "SocialEquitySetting";
	public static final Object[][] TABLE_COLUMNS = {
			{ "equitySettingId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "classNameId", new Integer(Types.BIGINT) },
			{ "actionId", new Integer(Types.VARCHAR) },
			{ "type_", new Integer(Types.INTEGER) },
			{ "value", new Integer(Types.INTEGER) },
			{ "validity", new Integer(Types.INTEGER) },
			{ "active_", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table SocialEquitySetting (equitySettingId LONG not null primary key,groupId LONG,companyId LONG,classNameId LONG,actionId VARCHAR(75) null,type_ INTEGER,value INTEGER,validity INTEGER,active_ BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table SocialEquitySetting";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.social.model.SocialEquitySetting"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.social.model.SocialEquitySetting"),
			true);

	public static SocialEquitySetting toModel(SocialEquitySettingSoap soapModel) {
		SocialEquitySetting model = new SocialEquitySettingImpl();

		model.setEquitySettingId(soapModel.getEquitySettingId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setActionId(soapModel.getActionId());
		model.setType(soapModel.getType());
		model.setValue(soapModel.getValue());
		model.setValidity(soapModel.getValidity());
		model.setActive(soapModel.getActive());

		return model;
	}

	public static List<SocialEquitySetting> toModels(
		SocialEquitySettingSoap[] soapModels) {
		List<SocialEquitySetting> models = new ArrayList<SocialEquitySetting>(soapModels.length);

		for (SocialEquitySettingSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.social.model.SocialEquitySetting"));

	public SocialEquitySettingModelImpl() {
	}

	public long getPrimaryKey() {
		return _equitySettingId;
	}

	public void setPrimaryKey(long pk) {
		setEquitySettingId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_equitySettingId);
	}

	public long getEquitySettingId() {
		return _equitySettingId;
	}

	public void setEquitySettingId(long equitySettingId) {
		_equitySettingId = equitySettingId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public String getActionId() {
		if (_actionId == null) {
			return StringPool.BLANK;
		}
		else {
			return _actionId;
		}
	}

	public void setActionId(String actionId) {
		_actionId = actionId;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public int getValue() {
		return _value;
	}

	public void setValue(int value) {
		_value = value;
	}

	public int getValidity() {
		return _validity;
	}

	public void setValidity(int validity) {
		_validity = validity;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public SocialEquitySetting toEscapedModel() {
		if (isEscapedModel()) {
			return (SocialEquitySetting)this;
		}
		else {
			return (SocialEquitySetting)Proxy.newProxyInstance(SocialEquitySetting.class.getClassLoader(),
				new Class[] { SocialEquitySetting.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					SocialEquitySetting.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		SocialEquitySettingImpl clone = new SocialEquitySettingImpl();

		clone.setEquitySettingId(getEquitySettingId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setClassNameId(getClassNameId());
		clone.setActionId(getActionId());
		clone.setType(getType());
		clone.setValue(getValue());
		clone.setValidity(getValidity());
		clone.setActive(getActive());

		return clone;
	}

	public int compareTo(SocialEquitySetting socialEquitySetting) {
		long pk = socialEquitySetting.getPrimaryKey();

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

		SocialEquitySetting socialEquitySetting = null;

		try {
			socialEquitySetting = (SocialEquitySetting)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = socialEquitySetting.getPrimaryKey();

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
		StringBundler sb = new StringBundler(19);

		sb.append("{equitySettingId=");
		sb.append(getEquitySettingId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", actionId=");
		sb.append(getActionId());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", value=");
		sb.append(getValue());
		sb.append(", validity=");
		sb.append(getValidity());
		sb.append(", active=");
		sb.append(getActive());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.social.model.SocialEquitySetting");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>equitySettingId</column-name><column-value><![CDATA[");
		sb.append(getEquitySettingId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actionId</column-name><column-value><![CDATA[");
		sb.append(getActionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>value</column-name><column-value><![CDATA[");
		sb.append(getValue());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>validity</column-name><column-value><![CDATA[");
		sb.append(getValidity());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>active</column-name><column-value><![CDATA[");
		sb.append(getActive());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _equitySettingId;
	private long _groupId;
	private long _companyId;
	private long _classNameId;
	private String _actionId;
	private int _type;
	private int _value;
	private int _validity;
	private boolean _active;
	private transient ExpandoBridge _expandoBridge;
}