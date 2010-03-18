/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.ListTypeSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ListTypeModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ListType table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ListTypeImpl
 * @see       com.liferay.portal.model.ListType
 * @see       com.liferay.portal.model.ListTypeModel
 * @generated
 */
public class ListTypeModelImpl extends BaseModelImpl<ListType> {
	public static final String TABLE_NAME = "ListType";
	public static final Object[][] TABLE_COLUMNS = {
			{ "listTypeId", new Integer(Types.INTEGER) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "type_", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table ListType (listTypeId INTEGER not null primary key,name VARCHAR(75) null,type_ VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table ListType";
	public static final String ORDER_BY_JPQL = " ORDER BY listType.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY ListType.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.ListType"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ListType"),
			true);

	public static ListType toModel(ListTypeSoap soapModel) {
		ListType model = new ListTypeImpl();

		model.setListTypeId(soapModel.getListTypeId());
		model.setName(soapModel.getName());
		model.setType(soapModel.getType());

		return model;
	}

	public static List<ListType> toModels(ListTypeSoap[] soapModels) {
		List<ListType> models = new ArrayList<ListType>(soapModels.length);

		for (ListTypeSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ListType"));

	public ListTypeModelImpl() {
	}

	public int getPrimaryKey() {
		return _listTypeId;
	}

	public void setPrimaryKey(int pk) {
		setListTypeId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Integer(_listTypeId);
	}

	public int getListTypeId() {
		return _listTypeId;
	}

	public void setListTypeId(int listTypeId) {
		_listTypeId = listTypeId;
	}

	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	public void setName(String name) {
		_name = name;
	}

	public String getType() {
		if (_type == null) {
			return StringPool.BLANK;
		}
		else {
			return _type;
		}
	}

	public void setType(String type) {
		_type = type;
	}

	public ListType toEscapedModel() {
		if (isEscapedModel()) {
			return (ListType)this;
		}
		else {
			ListType model = new ListTypeImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setListTypeId(getListTypeId());
			model.setName(HtmlUtil.escape(getName()));
			model.setType(HtmlUtil.escape(getType()));

			model = (ListType)Proxy.newProxyInstance(ListType.class.getClassLoader(),
					new Class[] { ListType.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		ListTypeImpl clone = new ListTypeImpl();

		clone.setListTypeId(getListTypeId());
		clone.setName(getName());
		clone.setType(getType());

		return clone;
	}

	public int compareTo(ListType listType) {
		int value = 0;

		value = getName().toLowerCase()
					.compareTo(listType.getName().toLowerCase());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ListType listType = null;

		try {
			listType = (ListType)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		int pk = listType.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey();
	}

	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{listTypeId=");
		sb.append(getListTypeId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", type=");
		sb.append(getType());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.ListType");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>listTypeId</column-name><column-value><![CDATA[");
		sb.append(getListTypeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private int _listTypeId;
	private String _name;
	private String _type;
}