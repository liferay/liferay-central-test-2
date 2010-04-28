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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.model.TicketSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="TicketModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Ticket table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TicketImpl
 * @see       com.liferay.portal.model.Ticket
 * @see       com.liferay.portal.model.TicketModel
 * @generated
 */
public class TicketModelImpl extends BaseModelImpl<Ticket> {
	public static final String TABLE_NAME = "Ticket";
	public static final Object[][] TABLE_COLUMNS = {
			{ "ticketId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "expirationDate", new Integer(Types.BIGINT) },
			{ "classNameId", new Integer(Types.BIGINT) },
			{ "classPK", new Integer(Types.BIGINT) },
			{ "key_", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table Ticket (ticketId LONG not null primary key,companyId LONG,createDate DATE null,expirationDate LONG,classNameId LONG,classPK LONG,key_ VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table Ticket";
	public static final String ORDER_BY_JPQL = " ORDER BY ticket.createDate ASC";
	public static final String ORDER_BY_SQL = " ORDER BY Ticket.createDate ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Ticket"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Ticket"),
			true);

	public static Ticket toModel(TicketSoap soapModel) {
		Ticket model = new TicketImpl();

		model.setTicketId(soapModel.getTicketId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setExpirationDate(soapModel.getExpirationDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setKey(soapModel.getKey());

		return model;
	}

	public static List<Ticket> toModels(TicketSoap[] soapModels) {
		List<Ticket> models = new ArrayList<Ticket>(soapModels.length);

		for (TicketSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Ticket"));

	public TicketModelImpl() {
	}

	public long getPrimaryKey() {
		return _ticketId;
	}

	public void setPrimaryKey(long pk) {
		setTicketId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_ticketId);
	}

	public long getTicketId() {
		return _ticketId;
	}

	public void setTicketId(long ticketId) {
		_ticketId = ticketId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public long getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(long expirationDate) {
		_expirationDate = expirationDate;
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

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getKey() {
		if (_key == null) {
			return StringPool.BLANK;
		}
		else {
			return _key;
		}
	}

	public void setKey(String key) {
		_key = key;

		if (_originalKey == null) {
			_originalKey = key;
		}
	}

	public String getOriginalKey() {
		return GetterUtil.getString(_originalKey);
	}

	public Ticket toEscapedModel() {
		if (isEscapedModel()) {
			return (Ticket)this;
		}
		else {
			return (Ticket)Proxy.newProxyInstance(Ticket.class.getClassLoader(),
				new Class[] { Ticket.class }, new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					Ticket.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		TicketImpl clone = new TicketImpl();

		clone.setTicketId(getTicketId());
		clone.setCompanyId(getCompanyId());
		clone.setCreateDate(getCreateDate());
		clone.setExpirationDate(getExpirationDate());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setKey(getKey());

		return clone;
	}

	public int compareTo(Ticket ticket) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(), ticket.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Ticket ticket = null;

		try {
			ticket = (Ticket)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = ticket.getPrimaryKey();

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
		StringBundler sb = new StringBundler(15);

		sb.append("{ticketId=");
		sb.append(getTicketId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", expirationDate=");
		sb.append(getExpirationDate());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", key=");
		sb.append(getKey());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Ticket");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>ticketId</column-name><column-value><![CDATA[");
		sb.append(getTicketId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>expirationDate</column-name><column-value><![CDATA[");
		sb.append(getExpirationDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>key</column-name><column-value><![CDATA[");
		sb.append(getKey());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _ticketId;
	private long _companyId;
	private Date _createDate;
	private long _expirationDate;
	private long _classNameId;
	private long _classPK;
	private String _key;
	private String _originalKey;
	private transient ExpandoBridge _expandoBridge;
}