/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.model.UserTrackerSoap;
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
 * <a href="UserTrackerModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the UserTracker table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserTrackerImpl
 * @see       com.liferay.portal.model.UserTracker
 * @see       com.liferay.portal.model.UserTrackerModel
 * @generated
 */
public class UserTrackerModelImpl extends BaseModelImpl<UserTracker> {
	public static final String TABLE_NAME = "UserTracker";
	public static final Object[][] TABLE_COLUMNS = {
			{ "userTrackerId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "sessionId", new Integer(Types.VARCHAR) },
			{ "remoteAddr", new Integer(Types.VARCHAR) },
			{ "remoteHost", new Integer(Types.VARCHAR) },
			{ "userAgent", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table UserTracker (userTrackerId LONG not null primary key,companyId LONG,userId LONG,modifiedDate DATE null,sessionId VARCHAR(200) null,remoteAddr VARCHAR(75) null,remoteHost VARCHAR(75) null,userAgent VARCHAR(200) null)";
	public static final String TABLE_SQL_DROP = "drop table UserTracker";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.UserTracker"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.UserTracker"),
			true);

	public static UserTracker toModel(UserTrackerSoap soapModel) {
		UserTracker model = new UserTrackerImpl();

		model.setUserTrackerId(soapModel.getUserTrackerId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setSessionId(soapModel.getSessionId());
		model.setRemoteAddr(soapModel.getRemoteAddr());
		model.setRemoteHost(soapModel.getRemoteHost());
		model.setUserAgent(soapModel.getUserAgent());

		return model;
	}

	public static List<UserTracker> toModels(UserTrackerSoap[] soapModels) {
		List<UserTracker> models = new ArrayList<UserTracker>(soapModels.length);

		for (UserTrackerSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.UserTracker"));

	public UserTrackerModelImpl() {
	}

	public long getPrimaryKey() {
		return _userTrackerId;
	}

	public void setPrimaryKey(long pk) {
		setUserTrackerId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_userTrackerId);
	}

	public long getUserTrackerId() {
		return _userTrackerId;
	}

	public void setUserTrackerId(long userTrackerId) {
		_userTrackerId = userTrackerId;
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

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getSessionId() {
		return GetterUtil.getString(_sessionId);
	}

	public void setSessionId(String sessionId) {
		_sessionId = sessionId;
	}

	public String getRemoteAddr() {
		return GetterUtil.getString(_remoteAddr);
	}

	public void setRemoteAddr(String remoteAddr) {
		_remoteAddr = remoteAddr;
	}

	public String getRemoteHost() {
		return GetterUtil.getString(_remoteHost);
	}

	public void setRemoteHost(String remoteHost) {
		_remoteHost = remoteHost;
	}

	public String getUserAgent() {
		return GetterUtil.getString(_userAgent);
	}

	public void setUserAgent(String userAgent) {
		_userAgent = userAgent;
	}

	public UserTracker toEscapedModel() {
		if (isEscapedModel()) {
			return (UserTracker)this;
		}
		else {
			UserTracker model = new UserTrackerImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUserTrackerId(getUserTrackerId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setModifiedDate(getModifiedDate());
			model.setSessionId(HtmlUtil.escape(getSessionId()));
			model.setRemoteAddr(HtmlUtil.escape(getRemoteAddr()));
			model.setRemoteHost(HtmlUtil.escape(getRemoteHost()));
			model.setUserAgent(HtmlUtil.escape(getUserAgent()));

			model = (UserTracker)Proxy.newProxyInstance(UserTracker.class.getClassLoader(),
					new Class[] { UserTracker.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(UserTracker.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		UserTrackerImpl clone = new UserTrackerImpl();

		clone.setUserTrackerId(getUserTrackerId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setModifiedDate(getModifiedDate());
		clone.setSessionId(getSessionId());
		clone.setRemoteAddr(getRemoteAddr());
		clone.setRemoteHost(getRemoteHost());
		clone.setUserAgent(getUserAgent());

		return clone;
	}

	public int compareTo(UserTracker userTracker) {
		long pk = userTracker.getPrimaryKey();

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

		UserTracker userTracker = null;

		try {
			userTracker = (UserTracker)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = userTracker.getPrimaryKey();

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
		StringBundler sb = new StringBundler(17);

		sb.append("{userTrackerId=");
		sb.append(getUserTrackerId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", sessionId=");
		sb.append(getSessionId());
		sb.append(", remoteAddr=");
		sb.append(getRemoteAddr());
		sb.append(", remoteHost=");
		sb.append(getRemoteHost());
		sb.append(", userAgent=");
		sb.append(getUserAgent());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(28);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.UserTracker");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>userTrackerId</column-name><column-value><![CDATA[");
		sb.append(getUserTrackerId());
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
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sessionId</column-name><column-value><![CDATA[");
		sb.append(getSessionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>remoteAddr</column-name><column-value><![CDATA[");
		sb.append(getRemoteAddr());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>remoteHost</column-name><column-value><![CDATA[");
		sb.append(getRemoteHost());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userAgent</column-name><column-value><![CDATA[");
		sb.append(getUserAgent());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _userTrackerId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private Date _modifiedDate;
	private String _sessionId;
	private String _remoteAddr;
	private String _remoteHost;
	private String _userAgent;
	private transient ExpandoBridge _expandoBridge;
}