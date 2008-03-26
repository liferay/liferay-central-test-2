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
import com.liferay.portal.model.ExpandoTableRow;
import com.liferay.portal.model.ExpandoTableRowSoap;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ExpandoTableRowModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ExpandoTableRow</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.ExpandoTableRow
 * @see com.liferay.portal.service.model.ExpandoTableRowModel
 * @see com.liferay.portal.service.model.impl.ExpandoTableRowImpl
 *
 */
public class ExpandoTableRowModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "ExpandoTableRow";
	public static final Object[][] TABLE_COLUMNS = {
			{ "rowId", new Integer(Types.BIGINT) },
			

			{ "tableId", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table ExpandoTableRow (rowId LONG not null primary key,tableId LONG)";
	public static final String TABLE_SQL_DROP = "drop table ExpandoTableRow";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ExpandoTableRow"),
			true);

	public static ExpandoTableRow toModel(ExpandoTableRowSoap soapModel) {
		ExpandoTableRow model = new ExpandoTableRowImpl();

		model.setRowId(soapModel.getRowId());
		model.setTableId(soapModel.getTableId());

		return model;
	}

	public static List<ExpandoTableRow> toModels(
		ExpandoTableRowSoap[] soapModels) {
		List<ExpandoTableRow> models = new ArrayList<ExpandoTableRow>(soapModels.length);

		for (ExpandoTableRowSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final boolean CACHE_ENABLED_EXPANDOTABLEROWS_EXPANDOVALUES = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.ExpandoTableRows_ExpandoValues"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ExpandoTableRow"));

	public ExpandoTableRowModelImpl() {
	}

	public long getPrimaryKey() {
		return _rowId;
	}

	public void setPrimaryKey(long pk) {
		setRowId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_rowId);
	}

	public long getRowId() {
		return _rowId;
	}

	public void setRowId(long rowId) {
		if (rowId != _rowId) {
			_rowId = rowId;
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

	public ExpandoTableRow toEscapedModel() {
		if (isEscapedModel()) {
			return (ExpandoTableRow)this;
		}
		else {
			ExpandoTableRow model = new ExpandoTableRowImpl();

			model.setEscapedModel(true);

			model.setRowId(getRowId());
			model.setTableId(getTableId());

			model = (ExpandoTableRow)Proxy.newProxyInstance(ExpandoTableRow.class.getClassLoader(),
					new Class[] { ExpandoTableRow.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		ExpandoTableRowImpl clone = new ExpandoTableRowImpl();

		clone.setRowId(getRowId());
		clone.setTableId(getTableId());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ExpandoTableRowImpl expandoTableRow = (ExpandoTableRowImpl)obj;

		long pk = expandoTableRow.getPrimaryKey();

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

		ExpandoTableRowImpl expandoTableRow = null;

		try {
			expandoTableRow = (ExpandoTableRowImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = expandoTableRow.getPrimaryKey();

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

	private long _rowId;
	private long _tableId;
}