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
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceCodeSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ResourceCodeModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ResourceCode table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceCodeImpl
 * @see       com.liferay.portal.model.ResourceCode
 * @see       com.liferay.portal.model.ResourceCodeModel
 * @generated
 */
public class ResourceCodeModelImpl extends BaseModelImpl<ResourceCode> {
	public static final String TABLE_NAME = "ResourceCode";
	public static final Object[][] TABLE_COLUMNS = {
			{ "codeId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "scope", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table ResourceCode (codeId LONG not null primary key,companyId LONG,name VARCHAR(255) null,scope INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table ResourceCode";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.ResourceCode"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ResourceCode"),
			true);

	public static ResourceCode toModel(ResourceCodeSoap soapModel) {
		ResourceCode model = new ResourceCodeImpl();

		model.setCodeId(soapModel.getCodeId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setName(soapModel.getName());
		model.setScope(soapModel.getScope());

		return model;
	}

	public static List<ResourceCode> toModels(ResourceCodeSoap[] soapModels) {
		List<ResourceCode> models = new ArrayList<ResourceCode>(soapModels.length);

		for (ResourceCodeSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ResourceCode"));

	public ResourceCodeModelImpl() {
	}

	public long getPrimaryKey() {
		return _codeId;
	}

	public void setPrimaryKey(long pk) {
		setCodeId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_codeId);
	}

	public long getCodeId() {
		return _codeId;
	}

	public void setCodeId(long codeId) {
		_codeId = codeId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = companyId;
		}
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
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

	public int getScope() {
		return _scope;
	}

	public void setScope(int scope) {
		_scope = scope;

		if (!_setOriginalScope) {
			_setOriginalScope = true;

			_originalScope = scope;
		}
	}

	public int getOriginalScope() {
		return _originalScope;
	}

	public ResourceCode toEscapedModel() {
		if (isEscapedModel()) {
			return (ResourceCode)this;
		}
		else {
			ResourceCode model = new ResourceCodeImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setCodeId(getCodeId());
			model.setCompanyId(getCompanyId());
			model.setName(HtmlUtil.escape(getName()));
			model.setScope(getScope());

			model = (ResourceCode)Proxy.newProxyInstance(ResourceCode.class.getClassLoader(),
					new Class[] { ResourceCode.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					ResourceCode.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		ResourceCodeImpl clone = new ResourceCodeImpl();

		clone.setCodeId(getCodeId());
		clone.setCompanyId(getCompanyId());
		clone.setName(getName());
		clone.setScope(getScope());

		return clone;
	}

	public int compareTo(ResourceCode resourceCode) {
		long pk = resourceCode.getPrimaryKey();

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

		ResourceCode resourceCode = null;

		try {
			resourceCode = (ResourceCode)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = resourceCode.getPrimaryKey();

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

		sb.append("{codeId=");
		sb.append(getCodeId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", scope=");
		sb.append(getScope());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(16);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.ResourceCode");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>codeId</column-name><column-value><![CDATA[");
		sb.append(getCodeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>scope</column-name><column-value><![CDATA[");
		sb.append(getScope());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _codeId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private String _name;
	private String _originalName;
	private int _scope;
	private int _originalScope;
	private boolean _setOriginalScope;
	private transient ExpandoBridge _expandoBridge;
}