/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.PasswordTracker;
import com.liferay.portal.model.PasswordTrackerSoap;
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
 * <a href="PasswordTrackerModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the PasswordTracker table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordTrackerImpl
 * @see       com.liferay.portal.model.PasswordTracker
 * @see       com.liferay.portal.model.PasswordTrackerModel
 * @generated
 */
public class PasswordTrackerModelImpl extends BaseModelImpl<PasswordTracker> {
	public static final String TABLE_NAME = "PasswordTracker";
	public static final Object[][] TABLE_COLUMNS = {
			{ "passwordTrackerId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "password_", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table PasswordTracker (passwordTrackerId LONG not null primary key,userId LONG,createDate DATE null,password_ VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table PasswordTracker";
	public static final String ORDER_BY_JPQL = " ORDER BY passwordTracker.userId DESC, passwordTracker.createDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY PasswordTracker.userId DESC, PasswordTracker.createDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.PasswordTracker"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.PasswordTracker"),
			true);

	public static PasswordTracker toModel(PasswordTrackerSoap soapModel) {
		PasswordTracker model = new PasswordTrackerImpl();

		model.setPasswordTrackerId(soapModel.getPasswordTrackerId());
		model.setUserId(soapModel.getUserId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setPassword(soapModel.getPassword());

		return model;
	}

	public static List<PasswordTracker> toModels(
		PasswordTrackerSoap[] soapModels) {
		List<PasswordTracker> models = new ArrayList<PasswordTracker>(soapModels.length);

		for (PasswordTrackerSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PasswordTracker"));

	public PasswordTrackerModelImpl() {
	}

	public long getPrimaryKey() {
		return _passwordTrackerId;
	}

	public void setPrimaryKey(long pk) {
		setPasswordTrackerId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_passwordTrackerId);
	}

	public long getPasswordTrackerId() {
		return _passwordTrackerId;
	}

	public void setPasswordTrackerId(long passwordTrackerId) {
		_passwordTrackerId = passwordTrackerId;
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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public String getPassword() {
		return GetterUtil.getString(_password);
	}

	public void setPassword(String password) {
		_password = password;
	}

	public PasswordTracker toEscapedModel() {
		if (isEscapedModel()) {
			return (PasswordTracker)this;
		}
		else {
			PasswordTracker model = new PasswordTrackerImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setPasswordTrackerId(getPasswordTrackerId());
			model.setUserId(getUserId());
			model.setCreateDate(getCreateDate());
			model.setPassword(HtmlUtil.escape(getPassword()));

			model = (PasswordTracker)Proxy.newProxyInstance(PasswordTracker.class.getClassLoader(),
					new Class[] { PasswordTracker.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(PasswordTracker.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		PasswordTrackerImpl clone = new PasswordTrackerImpl();

		clone.setPasswordTrackerId(getPasswordTrackerId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setPassword(getPassword());

		return clone;
	}

	public int compareTo(PasswordTracker passwordTracker) {
		int value = 0;

		if (getUserId() < passwordTracker.getUserId()) {
			value = -1;
		}
		else if (getUserId() > passwordTracker.getUserId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

		value = DateUtil.compareTo(getCreateDate(),
				passwordTracker.getCreateDate());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		PasswordTracker passwordTracker = null;

		try {
			passwordTracker = (PasswordTracker)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = passwordTracker.getPrimaryKey();

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

		sb.append("{passwordTrackerId=");
		sb.append(getPasswordTrackerId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", password=");
		sb.append(getPassword());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(16);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.PasswordTracker");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>passwordTrackerId</column-name><column-value><![CDATA[");
		sb.append(getPasswordTrackerId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>password</column-name><column-value><![CDATA[");
		sb.append(getPassword());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _passwordTrackerId;
	private long _userId;
	private String _userUuid;
	private Date _createDate;
	private String _password;
	private transient ExpandoBridge _expandoBridge;
}