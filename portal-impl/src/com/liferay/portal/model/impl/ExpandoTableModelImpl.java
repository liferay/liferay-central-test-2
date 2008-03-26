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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.ExpandoTable;
import com.liferay.portal.model.ExpandoTableSoap;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ExpandoTableModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ExpandoTable</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.ExpandoTable
 * @see com.liferay.portal.service.model.ExpandoTableModel
 * @see com.liferay.portal.service.model.impl.ExpandoTableImpl
 *
 */
public class ExpandoTableModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "ExpandoTable";
	public static final Object[][] TABLE_COLUMNS = {
			{ "tableId", new Integer(Types.BIGINT) },
			

			{ "classNameId", new Integer(Types.BIGINT) },
			

			{ "name", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table ExpandoTable (tableId LONG not null primary key,classNameId LONG,name VARCHAR(200) null)";
	public static final String TABLE_SQL_DROP = "drop table ExpandoTable";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ExpandoTable"),
			true);

	public static ExpandoTable toModel(ExpandoTableSoap soapModel) {
		ExpandoTable model = new ExpandoTableImpl();

		model.setTableId(soapModel.getTableId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setName(soapModel.getName());

		return model;
	}

	public static List<ExpandoTable> toModels(ExpandoTableSoap[] soapModels) {
		List<ExpandoTable> models = new ArrayList<ExpandoTable>(soapModels.length);

		for (ExpandoTableSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final boolean CACHE_ENABLED_EXPANDOTABLES_EXPANDOCOLUMNS = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.ExpandoTables_ExpandoColumns"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ExpandoTable"));

	public ExpandoTableModelImpl() {
	}

	public long getPrimaryKey() {
		return _tableId;
	}

	public void setPrimaryKey(long pk) {
		setTableId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_tableId);
	}

	public long getTableId() {
		return _tableId;
	}

	public void setTableId(long tableId) {
		if (tableId != _tableId) {
			_tableId = tableId;
		}
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		if (classNameId != _classNameId) {
			_classNameId = classNameId;
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

	public ExpandoTable toEscapedModel() {
		if (isEscapedModel()) {
			return (ExpandoTable)this;
		}
		else {
			ExpandoTable model = new ExpandoTableImpl();

			model.setEscapedModel(true);

			model.setTableId(getTableId());
			model.setClassNameId(getClassNameId());
			model.setName(HtmlUtil.escape(getName()));

			model = (ExpandoTable)Proxy.newProxyInstance(ExpandoTable.class.getClassLoader(),
					new Class[] { ExpandoTable.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		ExpandoTableImpl clone = new ExpandoTableImpl();

		clone.setTableId(getTableId());
		clone.setClassNameId(getClassNameId());
		clone.setName(getName());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ExpandoTableImpl expandoTable = (ExpandoTableImpl)obj;

		long pk = expandoTable.getPrimaryKey();

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

		ExpandoTableImpl expandoTable = null;

		try {
			expandoTable = (ExpandoTableImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = expandoTable.getPrimaryKey();

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

	private long _tableId;
	private long _classNameId;
	private String _name;
}