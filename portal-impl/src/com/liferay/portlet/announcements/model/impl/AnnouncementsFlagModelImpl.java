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

package com.liferay.portlet.announcements.model.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.announcements.model.AnnouncementsFlag;
import com.liferay.portlet.announcements.model.AnnouncementsFlagSoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="AnnouncementsFlagModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the AnnouncementsFlag table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsFlagImpl
 * @see       com.liferay.portlet.announcements.model.AnnouncementsFlag
 * @see       com.liferay.portlet.announcements.model.AnnouncementsFlagModel
 * @generated
 */
public class AnnouncementsFlagModelImpl extends BaseModelImpl<AnnouncementsFlag> {
	public static final String TABLE_NAME = "AnnouncementsFlag";
	public static final Object[][] TABLE_COLUMNS = {
			{ "flagId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "entryId", new Integer(Types.BIGINT) },
			{ "value", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table AnnouncementsFlag (flagId LONG not null primary key,userId LONG,createDate DATE null,entryId LONG,value INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table AnnouncementsFlag";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.announcements.model.AnnouncementsFlag"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.announcements.model.AnnouncementsFlag"),
			true);

	public static AnnouncementsFlag toModel(AnnouncementsFlagSoap soapModel) {
		AnnouncementsFlag model = new AnnouncementsFlagImpl();

		model.setFlagId(soapModel.getFlagId());
		model.setUserId(soapModel.getUserId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setEntryId(soapModel.getEntryId());
		model.setValue(soapModel.getValue());

		return model;
	}

	public static List<AnnouncementsFlag> toModels(
		AnnouncementsFlagSoap[] soapModels) {
		List<AnnouncementsFlag> models = new ArrayList<AnnouncementsFlag>(soapModels.length);

		for (AnnouncementsFlagSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.announcements.model.AnnouncementsFlag"));

	public AnnouncementsFlagModelImpl() {
	}

	public long getPrimaryKey() {
		return _flagId;
	}

	public void setPrimaryKey(long pk) {
		setFlagId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_flagId);
	}

	public long getFlagId() {
		return _flagId;
	}

	public void setFlagId(long flagId) {
		_flagId = flagId;
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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;

		if (!_setOriginalEntryId) {
			_setOriginalEntryId = true;

			_originalEntryId = entryId;
		}
	}

	public long getOriginalEntryId() {
		return _originalEntryId;
	}

	public int getValue() {
		return _value;
	}

	public void setValue(int value) {
		_value = value;

		if (!_setOriginalValue) {
			_setOriginalValue = true;

			_originalValue = value;
		}
	}

	public int getOriginalValue() {
		return _originalValue;
	}

	public AnnouncementsFlag toEscapedModel() {
		if (isEscapedModel()) {
			return (AnnouncementsFlag)this;
		}
		else {
			AnnouncementsFlag model = new AnnouncementsFlagImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setFlagId(getFlagId());
			model.setUserId(getUserId());
			model.setCreateDate(getCreateDate());
			model.setEntryId(getEntryId());
			model.setValue(getValue());

			model = (AnnouncementsFlag)Proxy.newProxyInstance(AnnouncementsFlag.class.getClassLoader(),
					new Class[] { AnnouncementsFlag.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(AnnouncementsFlag.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		AnnouncementsFlagImpl clone = new AnnouncementsFlagImpl();

		clone.setFlagId(getFlagId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setEntryId(getEntryId());
		clone.setValue(getValue());

		return clone;
	}

	public int compareTo(AnnouncementsFlag announcementsFlag) {
		int value = 0;

		if (getUserId() < announcementsFlag.getUserId()) {
			value = -1;
		}
		else if (getUserId() > announcementsFlag.getUserId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = DateUtil.compareTo(getCreateDate(),
				announcementsFlag.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		AnnouncementsFlag announcementsFlag = null;

		try {
			announcementsFlag = (AnnouncementsFlag)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = announcementsFlag.getPrimaryKey();

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

		sb.append("{flagId=");
		sb.append(getFlagId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", entryId=");
		sb.append(getEntryId());
		sb.append(", value=");
		sb.append(getValue());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.announcements.model.AnnouncementsFlag");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>flagId</column-name><column-value><![CDATA[");
		sb.append(getFlagId());
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
			"<column><column-name>entryId</column-name><column-value><![CDATA[");
		sb.append(getEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>value</column-name><column-value><![CDATA[");
		sb.append(getValue());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _flagId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private Date _createDate;
	private long _entryId;
	private long _originalEntryId;
	private boolean _setOriginalEntryId;
	private int _value;
	private int _originalValue;
	private boolean _setOriginalValue;
	private transient ExpandoBridge _expandoBridge;
}