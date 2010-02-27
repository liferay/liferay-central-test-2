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

package com.liferay.portlet.announcements.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.AnnouncementsDeliverySoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="AnnouncementsDeliveryModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the AnnouncementsDelivery table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDeliveryImpl
 * @see       com.liferay.portlet.announcements.model.AnnouncementsDelivery
 * @see       com.liferay.portlet.announcements.model.AnnouncementsDeliveryModel
 * @generated
 */
public class AnnouncementsDeliveryModelImpl extends BaseModelImpl<AnnouncementsDelivery> {
	public static final String TABLE_NAME = "AnnouncementsDelivery";
	public static final Object[][] TABLE_COLUMNS = {
			{ "deliveryId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "type_", new Integer(Types.VARCHAR) },
			{ "email", new Integer(Types.BOOLEAN) },
			{ "sms", new Integer(Types.BOOLEAN) },
			{ "website", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table AnnouncementsDelivery (deliveryId LONG not null primary key,companyId LONG,userId LONG,type_ VARCHAR(75) null,email BOOLEAN,sms BOOLEAN,website BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table AnnouncementsDelivery";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.announcements.model.AnnouncementsDelivery"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.announcements.model.AnnouncementsDelivery"),
			true);

	public static AnnouncementsDelivery toModel(
		AnnouncementsDeliverySoap soapModel) {
		AnnouncementsDelivery model = new AnnouncementsDeliveryImpl();

		model.setDeliveryId(soapModel.getDeliveryId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setType(soapModel.getType());
		model.setEmail(soapModel.getEmail());
		model.setSms(soapModel.getSms());
		model.setWebsite(soapModel.getWebsite());

		return model;
	}

	public static List<AnnouncementsDelivery> toModels(
		AnnouncementsDeliverySoap[] soapModels) {
		List<AnnouncementsDelivery> models = new ArrayList<AnnouncementsDelivery>(soapModels.length);

		for (AnnouncementsDeliverySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.announcements.model.AnnouncementsDelivery"));

	public AnnouncementsDeliveryModelImpl() {
	}

	public long getPrimaryKey() {
		return _deliveryId;
	}

	public void setPrimaryKey(long pk) {
		setDeliveryId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_deliveryId);
	}

	public long getDeliveryId() {
		return _deliveryId;
	}

	public void setDeliveryId(long deliveryId) {
		_deliveryId = deliveryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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
		return GetterUtil.getString(_type);
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

	public boolean getEmail() {
		return _email;
	}

	public boolean isEmail() {
		return _email;
	}

	public void setEmail(boolean email) {
		_email = email;
	}

	public boolean getSms() {
		return _sms;
	}

	public boolean isSms() {
		return _sms;
	}

	public void setSms(boolean sms) {
		_sms = sms;
	}

	public boolean getWebsite() {
		return _website;
	}

	public boolean isWebsite() {
		return _website;
	}

	public void setWebsite(boolean website) {
		_website = website;
	}

	public AnnouncementsDelivery toEscapedModel() {
		if (isEscapedModel()) {
			return (AnnouncementsDelivery)this;
		}
		else {
			AnnouncementsDelivery model = new AnnouncementsDeliveryImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setDeliveryId(getDeliveryId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setType(HtmlUtil.escape(getType()));
			model.setEmail(getEmail());
			model.setSms(getSms());
			model.setWebsite(getWebsite());

			model = (AnnouncementsDelivery)Proxy.newProxyInstance(AnnouncementsDelivery.class.getClassLoader(),
					new Class[] { AnnouncementsDelivery.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					AnnouncementsDelivery.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		AnnouncementsDeliveryImpl clone = new AnnouncementsDeliveryImpl();

		clone.setDeliveryId(getDeliveryId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setType(getType());
		clone.setEmail(getEmail());
		clone.setSms(getSms());
		clone.setWebsite(getWebsite());

		return clone;
	}

	public int compareTo(AnnouncementsDelivery announcementsDelivery) {
		long pk = announcementsDelivery.getPrimaryKey();

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

		AnnouncementsDelivery announcementsDelivery = null;

		try {
			announcementsDelivery = (AnnouncementsDelivery)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = announcementsDelivery.getPrimaryKey();

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

		sb.append("{deliveryId=");
		sb.append(getDeliveryId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", email=");
		sb.append(getEmail());
		sb.append(", sms=");
		sb.append(getSms());
		sb.append(", website=");
		sb.append(getWebsite());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append(
			"com.liferay.portlet.announcements.model.AnnouncementsDelivery");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>deliveryId</column-name><column-value><![CDATA[");
		sb.append(getDeliveryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
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
			"<column><column-name>email</column-name><column-value><![CDATA[");
		sb.append(getEmail());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sms</column-name><column-value><![CDATA[");
		sb.append(getSms());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>website</column-name><column-value><![CDATA[");
		sb.append(getWebsite());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _deliveryId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _type;
	private String _originalType;
	private boolean _email;
	private boolean _sms;
	private boolean _website;
	private transient ExpandoBridge _expandoBridge;
}