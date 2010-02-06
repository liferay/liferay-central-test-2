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
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceActionSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ResourceActionModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ResourceAction table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceActionImpl
 * @see       com.liferay.portal.model.ResourceAction
 * @see       com.liferay.portal.model.ResourceActionModel
 * @generated
 */
public class ResourceActionModelImpl extends BaseModelImpl<ResourceAction> {
	public static final String TABLE_NAME = "ResourceAction";
	public static final Object[][] TABLE_COLUMNS = {
			{ "resourceActionId", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "actionId", new Integer(Types.VARCHAR) },
			{ "bitwiseValue", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table ResourceAction (resourceActionId LONG not null primary key,name VARCHAR(255) null,actionId VARCHAR(75) null,bitwiseValue LONG)";
	public static final String TABLE_SQL_DROP = "drop table ResourceAction";
	public static final String ORDER_BY_JPQL = " ORDER BY resourceAction.name ASC, resourceAction.bitwiseValue ASC";
	public static final String ORDER_BY_SQL = " ORDER BY ResourceAction.name ASC, ResourceAction.bitwiseValue ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.ResourceAction"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ResourceAction"),
			true);

	public static ResourceAction toModel(ResourceActionSoap soapModel) {
		ResourceAction model = new ResourceActionImpl();

		model.setResourceActionId(soapModel.getResourceActionId());
		model.setName(soapModel.getName());
		model.setActionId(soapModel.getActionId());
		model.setBitwiseValue(soapModel.getBitwiseValue());

		return model;
	}

	public static List<ResourceAction> toModels(ResourceActionSoap[] soapModels) {
		List<ResourceAction> models = new ArrayList<ResourceAction>(soapModels.length);

		for (ResourceActionSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ResourceAction"));

	public ResourceActionModelImpl() {
	}

	public long getPrimaryKey() {
		return _resourceActionId;
	}

	public void setPrimaryKey(long pk) {
		setResourceActionId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_resourceActionId);
	}

	public long getResourceActionId() {
		return _resourceActionId;
	}

	public void setResourceActionId(long resourceActionId) {
		_resourceActionId = resourceActionId;
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		_name = name;

		if (_originalName == null) {
			_originalName = name;
		}
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	public String getActionId() {
		return GetterUtil.getString(_actionId);
	}

	public void setActionId(String actionId) {
		_actionId = actionId;

		if (_originalActionId == null) {
			_originalActionId = actionId;
		}
	}

	public String getOriginalActionId() {
		return GetterUtil.getString(_originalActionId);
	}

	public long getBitwiseValue() {
		return _bitwiseValue;
	}

	public void setBitwiseValue(long bitwiseValue) {
		_bitwiseValue = bitwiseValue;
	}

	public ResourceAction toEscapedModel() {
		if (isEscapedModel()) {
			return (ResourceAction)this;
		}
		else {
			ResourceAction model = new ResourceActionImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setResourceActionId(getResourceActionId());
			model.setName(HtmlUtil.escape(getName()));
			model.setActionId(HtmlUtil.escape(getActionId()));
			model.setBitwiseValue(getBitwiseValue());

			model = (ResourceAction)Proxy.newProxyInstance(ResourceAction.class.getClassLoader(),
					new Class[] { ResourceAction.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(ResourceAction.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		ResourceActionImpl clone = new ResourceActionImpl();

		clone.setResourceActionId(getResourceActionId());
		clone.setName(getName());
		clone.setActionId(getActionId());
		clone.setBitwiseValue(getBitwiseValue());

		return clone;
	}

	public int compareTo(ResourceAction resourceAction) {
		int value = 0;

		value = getName().compareTo(resourceAction.getName());

		if (value != 0) {
			return value;
		}

		if (getBitwiseValue() < resourceAction.getBitwiseValue()) {
			value = -1;
		}
		else if (getBitwiseValue() > resourceAction.getBitwiseValue()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ResourceAction resourceAction = null;

		try {
			resourceAction = (ResourceAction)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = resourceAction.getPrimaryKey();

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

		sb.append("{resourceActionId=");
		sb.append(getResourceActionId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", actionId=");
		sb.append(getActionId());
		sb.append(", bitwiseValue=");
		sb.append(getBitwiseValue());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(16);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.ResourceAction");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>resourceActionId</column-name><column-value><![CDATA[");
		sb.append(getResourceActionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actionId</column-name><column-value><![CDATA[");
		sb.append(getActionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>bitwiseValue</column-name><column-value><![CDATA[");
		sb.append(getBitwiseValue());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _resourceActionId;
	private String _name;
	private String _originalName;
	private String _actionId;
	private String _originalActionId;
	private long _bitwiseValue;
	private transient ExpandoBridge _expandoBridge;
}