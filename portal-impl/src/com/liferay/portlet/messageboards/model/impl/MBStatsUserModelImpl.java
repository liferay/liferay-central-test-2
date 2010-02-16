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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.MBStatsUserSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBStatsUserModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the MBStatsUser table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBStatsUserImpl
 * @see       com.liferay.portlet.messageboards.model.MBStatsUser
 * @see       com.liferay.portlet.messageboards.model.MBStatsUserModel
 * @generated
 */
public class MBStatsUserModelImpl extends BaseModelImpl<MBStatsUser> {
	public static final String TABLE_NAME = "MBStatsUser";
	public static final Object[][] TABLE_COLUMNS = {
			{ "statsUserId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "messageCount", new Integer(Types.INTEGER) },
			{ "lastPostDate", new Integer(Types.TIMESTAMP) }
		};
	public static final String TABLE_SQL_CREATE = "create table MBStatsUser (statsUserId LONG not null primary key,groupId LONG,userId LONG,messageCount INTEGER,lastPostDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table MBStatsUser";
	public static final String ORDER_BY_JPQL = " ORDER BY mbStatsUser.messageCount DESC";
	public static final String ORDER_BY_SQL = " ORDER BY MBStatsUser.messageCount DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.messageboards.model.MBStatsUser"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.messageboards.model.MBStatsUser"),
			true);

	public static MBStatsUser toModel(MBStatsUserSoap soapModel) {
		MBStatsUser model = new MBStatsUserImpl();

		model.setStatsUserId(soapModel.getStatsUserId());
		model.setGroupId(soapModel.getGroupId());
		model.setUserId(soapModel.getUserId());
		model.setMessageCount(soapModel.getMessageCount());
		model.setLastPostDate(soapModel.getLastPostDate());

		return model;
	}

	public static List<MBStatsUser> toModels(MBStatsUserSoap[] soapModels) {
		List<MBStatsUser> models = new ArrayList<MBStatsUser>(soapModels.length);

		for (MBStatsUserSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBStatsUser"));

	public MBStatsUserModelImpl() {
	}

	public long getPrimaryKey() {
		return _statsUserId;
	}

	public void setPrimaryKey(long pk) {
		setStatsUserId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_statsUserId);
	}

	public long getStatsUserId() {
		return _statsUserId;
	}

	public void setStatsUserId(long statsUserId) {
		_statsUserId = statsUserId;
	}

	public String getStatsUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getStatsUserId(), "uuid", _statsUserUuid);
	}

	public void setStatsUserUuid(String statsUserUuid) {
		_statsUserUuid = statsUserUuid;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = groupId;
		}
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
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

	public int getMessageCount() {
		return _messageCount;
	}

	public void setMessageCount(int messageCount) {
		_messageCount = messageCount;
	}

	public Date getLastPostDate() {
		return _lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		_lastPostDate = lastPostDate;
	}

	public MBStatsUser toEscapedModel() {
		if (isEscapedModel()) {
			return (MBStatsUser)this;
		}
		else {
			MBStatsUser model = new MBStatsUserImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setStatsUserId(getStatsUserId());
			model.setGroupId(getGroupId());
			model.setUserId(getUserId());
			model.setMessageCount(getMessageCount());
			model.setLastPostDate(getLastPostDate());

			model = (MBStatsUser)Proxy.newProxyInstance(MBStatsUser.class.getClassLoader(),
					new Class[] { MBStatsUser.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(MBStatsUser.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		MBStatsUserImpl clone = new MBStatsUserImpl();

		clone.setStatsUserId(getStatsUserId());
		clone.setGroupId(getGroupId());
		clone.setUserId(getUserId());
		clone.setMessageCount(getMessageCount());
		clone.setLastPostDate(getLastPostDate());

		return clone;
	}

	public int compareTo(MBStatsUser mbStatsUser) {
		int value = 0;

		if (getMessageCount() < mbStatsUser.getMessageCount()) {
			value = -1;
		}
		else if (getMessageCount() > mbStatsUser.getMessageCount()) {
			value = 1;
		}
		else {
			value = 0;
		}

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

		MBStatsUser mbStatsUser = null;

		try {
			mbStatsUser = (MBStatsUser)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = mbStatsUser.getPrimaryKey();

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

		sb.append("{statsUserId=");
		sb.append(getStatsUserId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", messageCount=");
		sb.append(getMessageCount());
		sb.append(", lastPostDate=");
		sb.append(getLastPostDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.messageboards.model.MBStatsUser");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>statsUserId</column-name><column-value><![CDATA[");
		sb.append(getStatsUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>messageCount</column-name><column-value><![CDATA[");
		sb.append(getMessageCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPostDate</column-name><column-value><![CDATA[");
		sb.append(getLastPostDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _statsUserId;
	private String _statsUserUuid;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private int _messageCount;
	private Date _lastPostDate;
	private transient ExpandoBridge _expandoBridge;
}