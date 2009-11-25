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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BrowserTracker;
import com.liferay.portal.model.BrowserTrackerSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="BrowserTrackerModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the BrowserTracker table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BrowserTrackerImpl
 * @see       com.liferay.portal.model.BrowserTracker
 * @see       com.liferay.portal.model.BrowserTrackerModel
 * @generated
 */
public class BrowserTrackerModelImpl extends BaseModelImpl<BrowserTracker> {
	public static final String TABLE_NAME = "BrowserTracker";
	public static final Object[][] TABLE_COLUMNS = {
			{ "browserTrackerId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "browserKey", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table BrowserTracker (browserTrackerId LONG not null primary key,userId LONG,browserKey LONG)";
	public static final String TABLE_SQL_DROP = "drop table BrowserTracker";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.BrowserTracker"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.BrowserTracker"),
			true);

	public static BrowserTracker toModel(BrowserTrackerSoap soapModel) {
		BrowserTracker model = new BrowserTrackerImpl();

		model.setBrowserTrackerId(soapModel.getBrowserTrackerId());
		model.setUserId(soapModel.getUserId());
		model.setBrowserKey(soapModel.getBrowserKey());

		return model;
	}

	public static List<BrowserTracker> toModels(BrowserTrackerSoap[] soapModels) {
		List<BrowserTracker> models = new ArrayList<BrowserTracker>(soapModels.length);

		for (BrowserTrackerSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.BrowserTracker"));

	public BrowserTrackerModelImpl() {
	}

	public long getPrimaryKey() {
		return _browserTrackerId;
	}

	public void setPrimaryKey(long pk) {
		setBrowserTrackerId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_browserTrackerId);
	}

	public long getBrowserTrackerId() {
		return _browserTrackerId;
	}

	public void setBrowserTrackerId(long browserTrackerId) {
		_browserTrackerId = browserTrackerId;
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

	public long getBrowserKey() {
		return _browserKey;
	}

	public void setBrowserKey(long browserKey) {
		_browserKey = browserKey;
	}

	public BrowserTracker toEscapedModel() {
		if (isEscapedModel()) {
			return (BrowserTracker)this;
		}
		else {
			BrowserTracker model = new BrowserTrackerImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setBrowserTrackerId(getBrowserTrackerId());
			model.setUserId(getUserId());
			model.setBrowserKey(getBrowserKey());

			model = (BrowserTracker)Proxy.newProxyInstance(BrowserTracker.class.getClassLoader(),
					new Class[] { BrowserTracker.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(BrowserTracker.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		BrowserTrackerImpl clone = new BrowserTrackerImpl();

		clone.setBrowserTrackerId(getBrowserTrackerId());
		clone.setUserId(getUserId());
		clone.setBrowserKey(getBrowserKey());

		return clone;
	}

	public int compareTo(BrowserTracker browserTracker) {
		long pk = browserTracker.getPrimaryKey();

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

		BrowserTracker browserTracker = null;

		try {
			browserTracker = (BrowserTracker)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = browserTracker.getPrimaryKey();

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
		StringBundler sb = new StringBundler(7);

		sb.append("{browserTrackerId=");
		sb.append(getBrowserTrackerId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", browserKey=");
		sb.append(getBrowserKey());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.BrowserTracker");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>browserTrackerId</column-name><column-value><![CDATA[");
		sb.append(getBrowserTrackerId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>browserKey</column-name><column-value><![CDATA[");
		sb.append(getBrowserKey());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _browserTrackerId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private long _browserKey;
	private transient ExpandoBridge _expandoBridge;
}