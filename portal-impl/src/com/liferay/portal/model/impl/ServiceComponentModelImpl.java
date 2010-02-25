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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.ServiceComponent;
import com.liferay.portal.model.ServiceComponentSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ServiceComponentModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ServiceComponent table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ServiceComponentImpl
 * @see       com.liferay.portal.model.ServiceComponent
 * @see       com.liferay.portal.model.ServiceComponentModel
 * @generated
 */
public class ServiceComponentModelImpl extends BaseModelImpl<ServiceComponent> {
	public static final String TABLE_NAME = "ServiceComponent";
	public static final Object[][] TABLE_COLUMNS = {
			{ "serviceComponentId", new Integer(Types.BIGINT) },
			{ "buildNamespace", new Integer(Types.VARCHAR) },
			{ "buildNumber", new Integer(Types.BIGINT) },
			{ "buildDate", new Integer(Types.BIGINT) },
			{ "data_", new Integer(Types.CLOB) }
		};
	public static final String TABLE_SQL_CREATE = "create table ServiceComponent (serviceComponentId LONG not null primary key,buildNamespace VARCHAR(75) null,buildNumber LONG,buildDate LONG,data_ TEXT null)";
	public static final String TABLE_SQL_DROP = "drop table ServiceComponent";
	public static final String ORDER_BY_JPQL = " ORDER BY serviceComponent.buildNamespace DESC, serviceComponent.buildNumber DESC";
	public static final String ORDER_BY_SQL = " ORDER BY ServiceComponent.buildNamespace DESC, ServiceComponent.buildNumber DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.ServiceComponent"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ServiceComponent"),
			true);

	public static ServiceComponent toModel(ServiceComponentSoap soapModel) {
		ServiceComponent model = new ServiceComponentImpl();

		model.setServiceComponentId(soapModel.getServiceComponentId());
		model.setBuildNamespace(soapModel.getBuildNamespace());
		model.setBuildNumber(soapModel.getBuildNumber());
		model.setBuildDate(soapModel.getBuildDate());
		model.setData(soapModel.getData());

		return model;
	}

	public static List<ServiceComponent> toModels(
		ServiceComponentSoap[] soapModels) {
		List<ServiceComponent> models = new ArrayList<ServiceComponent>(soapModels.length);

		for (ServiceComponentSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ServiceComponent"));

	public ServiceComponentModelImpl() {
	}

	public long getPrimaryKey() {
		return _serviceComponentId;
	}

	public void setPrimaryKey(long pk) {
		setServiceComponentId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_serviceComponentId);
	}

	public long getServiceComponentId() {
		return _serviceComponentId;
	}

	public void setServiceComponentId(long serviceComponentId) {
		_serviceComponentId = serviceComponentId;
	}

	public String getBuildNamespace() {
		return GetterUtil.getString(_buildNamespace);
	}

	public void setBuildNamespace(String buildNamespace) {
		_buildNamespace = buildNamespace;

		if (_originalBuildNamespace == null) {
			_originalBuildNamespace = buildNamespace;
		}
	}

	public String getOriginalBuildNamespace() {
		return GetterUtil.getString(_originalBuildNamespace);
	}

	public long getBuildNumber() {
		return _buildNumber;
	}

	public void setBuildNumber(long buildNumber) {
		_buildNumber = buildNumber;

		if (!_setOriginalBuildNumber) {
			_setOriginalBuildNumber = true;

			_originalBuildNumber = buildNumber;
		}
	}

	public long getOriginalBuildNumber() {
		return _originalBuildNumber;
	}

	public long getBuildDate() {
		return _buildDate;
	}

	public void setBuildDate(long buildDate) {
		_buildDate = buildDate;
	}

	public String getData() {
		return GetterUtil.getString(_data);
	}

	public void setData(String data) {
		_data = data;
	}

	public ServiceComponent toEscapedModel() {
		if (isEscapedModel()) {
			return (ServiceComponent)this;
		}
		else {
			ServiceComponent model = new ServiceComponentImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setServiceComponentId(getServiceComponentId());
			model.setBuildNamespace(HtmlUtil.escape(getBuildNamespace()));
			model.setBuildNumber(getBuildNumber());
			model.setBuildDate(getBuildDate());
			model.setData(HtmlUtil.escape(getData()));

			model = (ServiceComponent)Proxy.newProxyInstance(ServiceComponent.class.getClassLoader(),
					new Class[] { ServiceComponent.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					ServiceComponent.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		ServiceComponentImpl clone = new ServiceComponentImpl();

		clone.setServiceComponentId(getServiceComponentId());
		clone.setBuildNamespace(getBuildNamespace());
		clone.setBuildNumber(getBuildNumber());
		clone.setBuildDate(getBuildDate());
		clone.setData(getData());

		return clone;
	}

	public int compareTo(ServiceComponent serviceComponent) {
		int value = 0;

		value = getBuildNamespace()
					.compareTo(serviceComponent.getBuildNamespace());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		if (getBuildNumber() < serviceComponent.getBuildNumber()) {
			value = -1;
		}
		else if (getBuildNumber() > serviceComponent.getBuildNumber()) {
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

		ServiceComponent serviceComponent = null;

		try {
			serviceComponent = (ServiceComponent)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = serviceComponent.getPrimaryKey();

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

		sb.append("{serviceComponentId=");
		sb.append(getServiceComponentId());
		sb.append(", buildNamespace=");
		sb.append(getBuildNamespace());
		sb.append(", buildNumber=");
		sb.append(getBuildNumber());
		sb.append(", buildDate=");
		sb.append(getBuildDate());
		sb.append(", data=");
		sb.append(getData());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.ServiceComponent");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>serviceComponentId</column-name><column-value><![CDATA[");
		sb.append(getServiceComponentId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>buildNamespace</column-name><column-value><![CDATA[");
		sb.append(getBuildNamespace());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>buildNumber</column-name><column-value><![CDATA[");
		sb.append(getBuildNumber());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>buildDate</column-name><column-value><![CDATA[");
		sb.append(getBuildDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>data</column-name><column-value><![CDATA[");
		sb.append(getData());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _serviceComponentId;
	private String _buildNamespace;
	private String _originalBuildNamespace;
	private long _buildNumber;
	private long _originalBuildNumber;
	private boolean _setOriginalBuildNumber;
	private long _buildDate;
	private String _data;
	private transient ExpandoBridge _expandoBridge;
}