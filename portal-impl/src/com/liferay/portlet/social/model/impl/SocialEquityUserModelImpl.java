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
import com.liferay.portlet.social.model.SocialEquityUser;
import com.liferay.portlet.social.model.SocialEquityUserSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="SocialEquityUserModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the SocialEquityUser table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityUserImpl
 * @see       com.liferay.portlet.social.model.SocialEquityUser
 * @see       com.liferay.portlet.social.model.SocialEquityUserModel
 * @generated
 */
public class SocialEquityUserModelImpl extends BaseModelImpl<SocialEquityUser> {
	public static final String TABLE_NAME = "SocialEquityUser";
	public static final Object[][] TABLE_COLUMNS = {
			{ "equityUserId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "contributionEquity", new Integer(Types.DOUBLE) },
			{ "participationK", new Integer(Types.DOUBLE) },
			{ "participationB", new Integer(Types.DOUBLE) },
			{ "participationEquity", new Integer(Types.DOUBLE) },
			{ "personalEquity", new Integer(Types.DOUBLE) }
		};
	public static final String TABLE_SQL_CREATE = "create table SocialEquityUser (equityUserId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,contributionEquity DOUBLE,participationK DOUBLE,participationB DOUBLE,participationEquity DOUBLE,personalEquity DOUBLE)";
	public static final String TABLE_SQL_DROP = "drop table SocialEquityUser";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.social.model.SocialEquityUser"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.social.model.SocialEquityUser"),
			true);

	public static SocialEquityUser toModel(SocialEquityUserSoap soapModel) {
		SocialEquityUser model = new SocialEquityUserImpl();

		model.setEquityUserId(soapModel.getEquityUserId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setContributionEquity(soapModel.getContributionEquity());
		model.setParticipationK(soapModel.getParticipationK());
		model.setParticipationB(soapModel.getParticipationB());
		model.setParticipationEquity(soapModel.getParticipationEquity());
		model.setPersonalEquity(soapModel.getPersonalEquity());

		return model;
	}

	public static List<SocialEquityUser> toModels(
		SocialEquityUserSoap[] soapModels) {
		List<SocialEquityUser> models = new ArrayList<SocialEquityUser>(soapModels.length);

		for (SocialEquityUserSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.social.model.SocialEquityUser"));

	public SocialEquityUserModelImpl() {
	}

	public long getPrimaryKey() {
		return _equityUserId;
	}

	public void setPrimaryKey(long pk) {
		setEquityUserId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_equityUserId);
	}

	public long getEquityUserId() {
		return _equityUserId;
	}

	public void setEquityUserId(long equityUserId) {
		_equityUserId = equityUserId;
	}

	public String getEquityUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getEquityUserId(), "uuid",
			_equityUserUuid);
	}

	public void setEquityUserUuid(String equityUserUuid) {
		_equityUserUuid = equityUserUuid;
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

	public double getContributionEquity() {
		return _contributionEquity;
	}

	public void setContributionEquity(double contributionEquity) {
		_contributionEquity = contributionEquity;
	}

	public double getParticipationK() {
		return _participationK;
	}

	public void setParticipationK(double participationK) {
		_participationK = participationK;
	}

	public double getParticipationB() {
		return _participationB;
	}

	public void setParticipationB(double participationB) {
		_participationB = participationB;
	}

	public double getParticipationEquity() {
		return _participationEquity;
	}

	public void setParticipationEquity(double participationEquity) {
		_participationEquity = participationEquity;
	}

	public double getPersonalEquity() {
		return _personalEquity;
	}

	public void setPersonalEquity(double personalEquity) {
		_personalEquity = personalEquity;
	}

	public SocialEquityUser toEscapedModel() {
		if (isEscapedModel()) {
			return (SocialEquityUser)this;
		}
		else {
			return (SocialEquityUser)Proxy.newProxyInstance(SocialEquityUser.class.getClassLoader(),
				new Class[] { SocialEquityUser.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					SocialEquityUser.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		SocialEquityUserImpl clone = new SocialEquityUserImpl();

		clone.setEquityUserId(getEquityUserId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setContributionEquity(getContributionEquity());
		clone.setParticipationK(getParticipationK());
		clone.setParticipationB(getParticipationB());
		clone.setParticipationEquity(getParticipationEquity());
		clone.setPersonalEquity(getPersonalEquity());

		return clone;
	}

	public int compareTo(SocialEquityUser socialEquityUser) {
		long pk = socialEquityUser.getPrimaryKey();

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

		SocialEquityUser socialEquityUser = null;

		try {
			socialEquityUser = (SocialEquityUser)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = socialEquityUser.getPrimaryKey();

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

		sb.append("{equityUserId=");
		sb.append(getEquityUserId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", contributionEquity=");
		sb.append(getContributionEquity());
		sb.append(", participationK=");
		sb.append(getParticipationK());
		sb.append(", participationB=");
		sb.append(getParticipationB());
		sb.append(", participationEquity=");
		sb.append(getParticipationEquity());
		sb.append(", personalEquity=");
		sb.append(getPersonalEquity());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.social.model.SocialEquityUser");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>equityUserId</column-name><column-value><![CDATA[");
		sb.append(getEquityUserId());
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
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>contributionEquity</column-name><column-value><![CDATA[");
		sb.append(getContributionEquity());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>participationK</column-name><column-value><![CDATA[");
		sb.append(getParticipationK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>participationB</column-name><column-value><![CDATA[");
		sb.append(getParticipationB());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>participationEquity</column-name><column-value><![CDATA[");
		sb.append(getParticipationEquity());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>personalEquity</column-name><column-value><![CDATA[");
		sb.append(getPersonalEquity());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _equityUserId;
	private String _equityUserUuid;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private double _contributionEquity;
	private double _participationK;
	private double _participationB;
	private double _participationEquity;
	private double _personalEquity;
	private transient ExpandoBridge _expandoBridge;
}