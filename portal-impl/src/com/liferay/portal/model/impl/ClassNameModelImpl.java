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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.ClassNameSoap;
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
 * <a href="ClassNameModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ClassName_ table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClassNameImpl
 * @see       com.liferay.portal.model.ClassName
 * @see       com.liferay.portal.model.ClassNameModel
 * @generated
 */
public class ClassNameModelImpl extends BaseModelImpl<ClassName> {
	public static final String TABLE_NAME = "ClassName_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "classNameId", new Integer(Types.BIGINT) },
			{ "value", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table ClassName_ (classNameId LONG not null primary key,value VARCHAR(200) null)";
	public static final String TABLE_SQL_DROP = "drop table ClassName_";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.ClassName"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ClassName"),
			true);

	public static ClassName toModel(ClassNameSoap soapModel) {
		ClassName model = new ClassNameImpl();

		model.setClassNameId(soapModel.getClassNameId());
		model.setValue(soapModel.getValue());

		return model;
	}

	public static List<ClassName> toModels(ClassNameSoap[] soapModels) {
		List<ClassName> models = new ArrayList<ClassName>(soapModels.length);

		for (ClassNameSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ClassName"));

	public ClassNameModelImpl() {
	}

	public long getPrimaryKey() {
		return _classNameId;
	}

	public void setPrimaryKey(long pk) {
		setClassNameId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_classNameId);
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public String getValue() {
		return GetterUtil.getString(_value);
	}

	public void setValue(String value) {
		_value = value;

		if (_originalValue == null) {
			_originalValue = value;
		}
	}

	public String getOriginalValue() {
		return GetterUtil.getString(_originalValue);
	}

	public ClassName toEscapedModel() {
		if (isEscapedModel()) {
			return (ClassName)this;
		}
		else {
			ClassName model = new ClassNameImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setClassNameId(getClassNameId());
			model.setValue(HtmlUtil.escape(getValue()));

			model = (ClassName)Proxy.newProxyInstance(ClassName.class.getClassLoader(),
					new Class[] { ClassName.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(ClassName.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		ClassNameImpl clone = new ClassNameImpl();

		clone.setClassNameId(getClassNameId());
		clone.setValue(getValue());

		return clone;
	}

	public int compareTo(ClassName className) {
		long pk = className.getPrimaryKey();

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

		ClassName className = null;

		try {
			className = (ClassName)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = className.getPrimaryKey();

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
		StringBundler sb = new StringBundler(5);

		sb.append("{classNameId=");
		sb.append(getClassNameId());
		sb.append(", value=");
		sb.append(getValue());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(10);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.ClassName");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>value</column-name><column-value><![CDATA[");
		sb.append(getValue());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _classNameId;
	private String _value;
	private String _originalValue;
	private transient ExpandoBridge _expandoBridge;
}