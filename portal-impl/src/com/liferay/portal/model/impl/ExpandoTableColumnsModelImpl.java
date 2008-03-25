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
import com.liferay.portal.model.ExpandoTableColumns;
import com.liferay.portal.service.persistence.ExpandoTableColumnsPK;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

/**
 * <a href="ExpandoTableColumnsModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ExpandoTableColumns</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.ExpandoTableColumns
 * @see com.liferay.portal.service.model.ExpandoTableColumnsModel
 * @see com.liferay.portal.service.model.impl.ExpandoTableColumnsImpl
 *
 */
public class ExpandoTableColumnsModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "ExpandoTables_ExpandoColumns";
	public static final Object[][] TABLE_COLUMNS = {
			{ "tableId", new Integer(Types.BIGINT) },
			

			{ "columnId", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table ExpandoTables_ExpandoColumns (tableId LONG not null,columnId LONG not null,primary key (tableId, columnId))";
	public static final String TABLE_SQL_DROP = "drop table ExpandoTables_ExpandoColumns";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ExpandoTableColumns"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ExpandoTableColumns"));

	public ExpandoTableColumnsModelImpl() {
	}

	public ExpandoTableColumnsPK getPrimaryKey() {
		return new ExpandoTableColumnsPK(_tableId, _columnId);
	}

	public void setPrimaryKey(ExpandoTableColumnsPK pk) {
		setTableId(pk.tableId);
		setColumnId(pk.columnId);
	}

	public Serializable getPrimaryKeyObj() {
		return new ExpandoTableColumnsPK(_tableId, _columnId);
	}

	public long getTableId() {
		return _tableId;
	}

	public void setTableId(long tableId) {
		if (tableId != _tableId) {
			_tableId = tableId;
		}
	}

	public long getColumnId() {
		return _columnId;
	}

	public void setColumnId(long columnId) {
		if (columnId != _columnId) {
			_columnId = columnId;
		}
	}

	public ExpandoTableColumns toEscapedModel() {
		if (isEscapedModel()) {
			return (ExpandoTableColumns)this;
		}
		else {
			ExpandoTableColumns model = new ExpandoTableColumnsImpl();

			model.setEscapedModel(true);

			model.setTableId(getTableId());
			model.setColumnId(getColumnId());

			model = (ExpandoTableColumns)Proxy.newProxyInstance(ExpandoTableColumns.class.getClassLoader(),
					new Class[] { ExpandoTableColumns.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		ExpandoTableColumnsImpl clone = new ExpandoTableColumnsImpl();

		clone.setTableId(getTableId());
		clone.setColumnId(getColumnId());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ExpandoTableColumnsImpl expandoTableColumns = (ExpandoTableColumnsImpl)obj;

		ExpandoTableColumnsPK pk = expandoTableColumns.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ExpandoTableColumnsImpl expandoTableColumns = null;

		try {
			expandoTableColumns = (ExpandoTableColumnsImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		ExpandoTableColumnsPK pk = expandoTableColumns.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private long _tableId;
	private long _columnId;
}