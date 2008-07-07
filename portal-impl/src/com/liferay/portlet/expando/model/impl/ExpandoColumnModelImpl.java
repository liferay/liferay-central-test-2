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

package com.liferay.portlet.expando.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ExpandoColumnModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ExpandoColumn</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.model.ExpandoColumn
 * @see com.liferay.portlet.expando.service.model.ExpandoColumnModel
 * @see com.liferay.portlet.expando.service.model.impl.ExpandoColumnImpl
 *
 */
public class ExpandoColumnModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "ExpandoColumn";
	public static final Object[][] TABLE_COLUMNS = {
			{ "columnId", new Integer(Types.BIGINT) },
			

			{ "tableId", new Integer(Types.BIGINT) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "type_", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table ExpandoColumn (columnId LONG not null primary key,tableId LONG,name VARCHAR(75) null,type_ INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table ExpandoColumn";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.expando.model.ExpandoColumn"),
			true);

	public static ExpandoColumn toModel(ExpandoColumnSoap soapModel) {
		ExpandoColumn model = new ExpandoColumnImpl();

		model.setColumnId(soapModel.getColumnId());
		model.setTableId(soapModel.getTableId());
		model.setName(soapModel.getName());
		model.setType(soapModel.getType());

		return model;
	}

	public static List<ExpandoColumn> toModels(ExpandoColumnSoap[] soapModels) {
		List<ExpandoColumn> models = new ArrayList<ExpandoColumn>(soapModels.length);

		for (ExpandoColumnSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.expando.model.ExpandoColumn"));

	public ExpandoColumnModelImpl() {
	}

	public long getPrimaryKey() {
		return _columnId;
	}

	public void setPrimaryKey(long pk) {
		setColumnId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_columnId);
	}

	public long getColumnId() {
		return _columnId;
	}

	public void setColumnId(long columnId) {
		if (columnId != _columnId) {
			_columnId = columnId;
		}
	}

	public long getTableId() {
		return _tableId;
	}

	public void setTableId(long tableId) {
		if (tableId != _tableId) {
			_tableId = tableId;
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

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		if (type != _type) {
			_type = type;
		}
	}

	public ExpandoColumn toEscapedModel() {
		if (isEscapedModel()) {
			return (ExpandoColumn)this;
		}
		else {
			ExpandoColumn model = new ExpandoColumnImpl();

			model.setEscapedModel(true);

			model.setColumnId(getColumnId());
			model.setTableId(getTableId());
			model.setName(HtmlUtil.escape(getName()));
			model.setType(getType());

			model = (ExpandoColumn)Proxy.newProxyInstance(ExpandoColumn.class.getClassLoader(),
					new Class[] { ExpandoColumn.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		ExpandoColumnImpl clone = new ExpandoColumnImpl();

		clone.setColumnId(getColumnId());
		clone.setTableId(getTableId());
		clone.setName(getName());
		clone.setType(getType());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ExpandoColumnImpl expandoColumn = (ExpandoColumnImpl)obj;

		long pk = expandoColumn.getPrimaryKey();

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

		ExpandoColumnImpl expandoColumn = null;

		try {
			expandoColumn = (ExpandoColumnImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = expandoColumn.getPrimaryKey();

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

	private long _columnId;
	private long _tableId;
	private String _name;
	private int _type;
}