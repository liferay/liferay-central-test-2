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

package com.liferay.wsrp.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoBridgeImpl;

import com.liferay.wsrp.model.WSRPConfiguredProducer;
import com.liferay.wsrp.model.WSRPConfiguredProducerSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="WSRPConfiguredProducerModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>WSRPConfiguredProducer</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.wsrp.model.WSRPConfiguredProducer
 * @see com.liferay.wsrp.model.WSRPConfiguredProducerModel
 * @see com.liferay.wsrp.model.impl.WSRPConfiguredProducerImpl
 *
 */
public class WSRPConfiguredProducerModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "WSRPConfiguredProducer";
	public static final Object[][] TABLE_COLUMNS = {
			{ "configuredProducerId", new Integer(Types.BIGINT) },
			

			{ "name", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table WSRPConfiguredProducer (configuredProducerId LONG not null primary key,name VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table WSRPConfiguredProducer";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.wsrp.model.WSRPConfiguredProducer"),
			true);

	public static WSRPConfiguredProducer toModel(
		WSRPConfiguredProducerSoap soapModel) {
		WSRPConfiguredProducer model = new WSRPConfiguredProducerImpl();

		model.setConfiguredProducerId(soapModel.getConfiguredProducerId());
		model.setName(soapModel.getName());

		return model;
	}

	public static List<WSRPConfiguredProducer> toModels(
		WSRPConfiguredProducerSoap[] soapModels) {
		List<WSRPConfiguredProducer> models = new ArrayList<WSRPConfiguredProducer>(soapModels.length);

		for (WSRPConfiguredProducerSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.wsrp.model.WSRPConfiguredProducer"));

	public WSRPConfiguredProducerModelImpl() {
	}

	public long getPrimaryKey() {
		return _configuredProducerId;
	}

	public void setPrimaryKey(long pk) {
		setConfiguredProducerId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_configuredProducerId);
	}

	public long getConfiguredProducerId() {
		return _configuredProducerId;
	}

	public void setConfiguredProducerId(long configuredProducerId) {
		if (configuredProducerId != _configuredProducerId) {
			_configuredProducerId = configuredProducerId;
		}
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if (((name == null) && (_name != null)) ||
				((name != null) && (_name == null)) ||
				((name != null) && (_name != null) && !name.equals(_name))) {
			_name = name;
		}
	}

	public WSRPConfiguredProducer toEscapedModel() {
		if (isEscapedModel()) {
			return (WSRPConfiguredProducer)this;
		}
		else {
			WSRPConfiguredProducer model = new WSRPConfiguredProducerImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setConfiguredProducerId(getConfiguredProducerId());
			model.setName(HtmlUtil.escape(getName()));

			model = (WSRPConfiguredProducer)Proxy.newProxyInstance(WSRPConfiguredProducer.class.getClassLoader(),
					new Class[] { WSRPConfiguredProducer.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(WSRPConfiguredProducer.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		WSRPConfiguredProducerImpl clone = new WSRPConfiguredProducerImpl();

		clone.setConfiguredProducerId(getConfiguredProducerId());
		clone.setName(getName());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		WSRPConfiguredProducerImpl wsrpConfiguredProducer = (WSRPConfiguredProducerImpl)obj;

		long pk = wsrpConfiguredProducer.getPrimaryKey();

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

		WSRPConfiguredProducerImpl wsrpConfiguredProducer = null;

		try {
			wsrpConfiguredProducer = (WSRPConfiguredProducerImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = wsrpConfiguredProducer.getPrimaryKey();

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

	private long _configuredProducerId;
	private String _name;
	private ExpandoBridge _expandoBridge;
}