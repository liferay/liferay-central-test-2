/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.ruon.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.ruon.model.PresenceStatuses;
import com.liferay.portal.ruon.model.PresenceStatusesSoap;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="PresenceStatusesModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>PresenceStatuses</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.ruon.service.model.PresenceStatuses
 * @see com.liferay.portal.ruon.service.model.PresenceStatusesModel
 * @see com.liferay.portal.ruon.service.model.impl.PresenceStatusesImpl
 *
 */
public class PresenceStatusesModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "PresenceStatuses";
	public static final Object[][] TABLE_COLUMNS = {
			{ "presenceStatusId", new Integer(Types.BIGINT) },
			

			{ "presenceStatusMessage", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table PresenceStatuses (presenceStatusId LONG not null primary key,presenceStatusMessage VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table PresenceStatuses";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.ruon.model.PresenceStatuses"),
			true);

	public static PresenceStatuses toModel(PresenceStatusesSoap soapModel) {
		PresenceStatuses model = new PresenceStatusesImpl();

		model.setPresenceStatusId(soapModel.getPresenceStatusId());
		model.setPresenceStatusMessage(soapModel.getPresenceStatusMessage());

		return model;
	}

	public static List<PresenceStatuses> toModels(
		PresenceStatusesSoap[] soapModels) {
		List<PresenceStatuses> models = new ArrayList<PresenceStatuses>(soapModels.length);

		for (PresenceStatusesSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.ruon.model.PresenceStatuses"));

	public PresenceStatusesModelImpl() {
	}

	public long getPrimaryKey() {
		return _presenceStatusId;
	}

	public void setPrimaryKey(long pk) {
		setPresenceStatusId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_presenceStatusId);
	}

	public long getPresenceStatusId() {
		return _presenceStatusId;
	}

	public void setPresenceStatusId(long presenceStatusId) {
		if (presenceStatusId != _presenceStatusId) {
			_presenceStatusId = presenceStatusId;
		}
	}

	public String getPresenceStatusMessage() {
		return GetterUtil.getString(_presenceStatusMessage);
	}

	public void setPresenceStatusMessage(String presenceStatusMessage) {
		if (((presenceStatusMessage == null) &&
				(_presenceStatusMessage != null)) ||
				((presenceStatusMessage != null) &&
				(_presenceStatusMessage == null)) ||
				((presenceStatusMessage != null) &&
				(_presenceStatusMessage != null) &&
				!presenceStatusMessage.equals(_presenceStatusMessage))) {
			_presenceStatusMessage = presenceStatusMessage;
		}
	}

	public PresenceStatuses toEscapedModel() {
		if (isEscapedModel()) {
			return (PresenceStatuses)this;
		}
		else {
			PresenceStatuses model = new PresenceStatusesImpl();

			model.setEscapedModel(true);

			model.setPresenceStatusId(getPresenceStatusId());
			model.setPresenceStatusMessage(HtmlUtil.escape(
					getPresenceStatusMessage()));

			model = (PresenceStatuses)Proxy.newProxyInstance(PresenceStatuses.class.getClassLoader(),
					new Class[] { PresenceStatuses.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		PresenceStatusesImpl clone = new PresenceStatusesImpl();

		clone.setPresenceStatusId(getPresenceStatusId());
		clone.setPresenceStatusMessage(getPresenceStatusMessage());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		PresenceStatusesImpl presenceStatuses = (PresenceStatusesImpl)obj;

		long pk = presenceStatuses.getPrimaryKey();

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

		PresenceStatusesImpl presenceStatuses = null;

		try {
			presenceStatuses = (PresenceStatusesImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = presenceStatuses.getPrimaryKey();

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

	private long _presenceStatusId;
	private String _presenceStatusMessage;
}