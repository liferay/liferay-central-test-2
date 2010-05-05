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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.social.model.SocialEquityAssetEntry;
import com.liferay.portlet.social.model.SocialEquityAssetEntrySoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="SocialEquityAssetEntryModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the SocialEquityAssetEntry table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityAssetEntryImpl
 * @see       com.liferay.portlet.social.model.SocialEquityAssetEntry
 * @see       com.liferay.portlet.social.model.SocialEquityAssetEntryModel
 * @generated
 */
public class SocialEquityAssetEntryModelImpl extends BaseModelImpl<SocialEquityAssetEntry> {
	public static final String TABLE_NAME = "SocialEquityAssetEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "equityAssetEntryId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "assetEntryId", new Integer(Types.BIGINT) },
			{ "informationK", new Integer(Types.DOUBLE) },
			{ "informationB", new Integer(Types.DOUBLE) },
			{ "informationEquity", new Integer(Types.DOUBLE) }
		};
	public static final String TABLE_SQL_CREATE = "create table SocialEquityAssetEntry (equityAssetEntryId LONG not null primary key,companyId LONG,userId LONG,assetEntryId LONG,informationK DOUBLE,informationB DOUBLE,informationEquity DOUBLE)";
	public static final String TABLE_SQL_DROP = "drop table SocialEquityAssetEntry";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.social.model.SocialEquityAssetEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.social.model.SocialEquityAssetEntry"),
			true);

	public static SocialEquityAssetEntry toModel(
		SocialEquityAssetEntrySoap soapModel) {
		SocialEquityAssetEntry model = new SocialEquityAssetEntryImpl();

		model.setEquityAssetEntryId(soapModel.getEquityAssetEntryId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setAssetEntryId(soapModel.getAssetEntryId());
		model.setInformationK(soapModel.getInformationK());
		model.setInformationB(soapModel.getInformationB());
		model.setInformationEquity(soapModel.getInformationEquity());

		return model;
	}

	public static List<SocialEquityAssetEntry> toModels(
		SocialEquityAssetEntrySoap[] soapModels) {
		List<SocialEquityAssetEntry> models = new ArrayList<SocialEquityAssetEntry>(soapModels.length);

		for (SocialEquityAssetEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.social.model.SocialEquityAssetEntry"));

	public SocialEquityAssetEntryModelImpl() {
	}

	public long getPrimaryKey() {
		return _equityAssetEntryId;
	}

	public void setPrimaryKey(long pk) {
		setEquityAssetEntryId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_equityAssetEntryId);
	}

	public long getEquityAssetEntryId() {
		return _equityAssetEntryId;
	}

	public void setEquityAssetEntryId(long equityAssetEntryId) {
		_equityAssetEntryId = equityAssetEntryId;
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
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;

		if (!_setOriginalAssetEntryId) {
			_setOriginalAssetEntryId = true;

			_originalAssetEntryId = assetEntryId;
		}
	}

	public long getOriginalAssetEntryId() {
		return _originalAssetEntryId;
	}

	public double getInformationK() {
		return _informationK;
	}

	public void setInformationK(double informationK) {
		_informationK = informationK;
	}

	public double getInformationB() {
		return _informationB;
	}

	public void setInformationB(double informationB) {
		_informationB = informationB;
	}

	public double getInformationEquity() {
		return _informationEquity;
	}

	public void setInformationEquity(double informationEquity) {
		_informationEquity = informationEquity;
	}

	public SocialEquityAssetEntry toEscapedModel() {
		if (isEscapedModel()) {
			return (SocialEquityAssetEntry)this;
		}
		else {
			return (SocialEquityAssetEntry)Proxy.newProxyInstance(SocialEquityAssetEntry.class.getClassLoader(),
				new Class[] { SocialEquityAssetEntry.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					SocialEquityAssetEntry.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		SocialEquityAssetEntryImpl clone = new SocialEquityAssetEntryImpl();

		clone.setEquityAssetEntryId(getEquityAssetEntryId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setAssetEntryId(getAssetEntryId());
		clone.setInformationK(getInformationK());
		clone.setInformationB(getInformationB());
		clone.setInformationEquity(getInformationEquity());

		return clone;
	}

	public int compareTo(SocialEquityAssetEntry socialEquityAssetEntry) {
		long pk = socialEquityAssetEntry.getPrimaryKey();

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

		SocialEquityAssetEntry socialEquityAssetEntry = null;

		try {
			socialEquityAssetEntry = (SocialEquityAssetEntry)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = socialEquityAssetEntry.getPrimaryKey();

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

		sb.append("{equityAssetEntryId=");
		sb.append(getEquityAssetEntryId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", assetEntryId=");
		sb.append(getAssetEntryId());
		sb.append(", informationK=");
		sb.append(getInformationK());
		sb.append(", informationB=");
		sb.append(getInformationB());
		sb.append(", informationEquity=");
		sb.append(getInformationEquity());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.social.model.SocialEquityAssetEntry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>equityAssetEntryId</column-name><column-value><![CDATA[");
		sb.append(getEquityAssetEntryId());
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
			"<column><column-name>assetEntryId</column-name><column-value><![CDATA[");
		sb.append(getAssetEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>informationK</column-name><column-value><![CDATA[");
		sb.append(getInformationK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>informationB</column-name><column-value><![CDATA[");
		sb.append(getInformationB());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>informationEquity</column-name><column-value><![CDATA[");
		sb.append(getInformationEquity());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _equityAssetEntryId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private long _assetEntryId;
	private long _originalAssetEntryId;
	private boolean _setOriginalAssetEntryId;
	private double _informationK;
	private double _informationB;
	private double _informationEquity;
	private transient ExpandoBridge _expandoBridge;
}