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
import com.liferay.portal.model.ExpandoRowValues;
import com.liferay.portal.service.persistence.ExpandoRowValuesPK;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

/**
 * <a href="ExpandoRowValuesModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ExpandoRowValues</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.ExpandoRowValues
 * @see com.liferay.portal.service.model.ExpandoRowValuesModel
 * @see com.liferay.portal.service.model.impl.ExpandoRowValuesImpl
 *
 */
public class ExpandoRowValuesModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "ExpandoTableRows_ExpandoValues";
	public static final Object[][] TABLE_COLUMNS = {
			{ "rowId", new Integer(Types.BIGINT) },
			

			{ "valueId", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table ExpandoTableRows_ExpandoValues (rowId LONG not null,valueId LONG not null,primary key (rowId, valueId))";
	public static final String TABLE_SQL_DROP = "drop table ExpandoTableRows_ExpandoValues";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ExpandoRowValues"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ExpandoRowValues"));

	public ExpandoRowValuesModelImpl() {
	}

	public ExpandoRowValuesPK getPrimaryKey() {
		return new ExpandoRowValuesPK(_rowId, _valueId);
	}

	public void setPrimaryKey(ExpandoRowValuesPK pk) {
		setRowId(pk.rowId);
		setValueId(pk.valueId);
	}

	public Serializable getPrimaryKeyObj() {
		return new ExpandoRowValuesPK(_rowId, _valueId);
	}

	public long getRowId() {
		return _rowId;
	}

	public void setRowId(long rowId) {
		if (rowId != _rowId) {
			_rowId = rowId;
		}
	}

	public long getValueId() {
		return _valueId;
	}

	public void setValueId(long valueId) {
		if (valueId != _valueId) {
			_valueId = valueId;
		}
	}

	public ExpandoRowValues toEscapedModel() {
		if (isEscapedModel()) {
			return (ExpandoRowValues)this;
		}
		else {
			ExpandoRowValues model = new ExpandoRowValuesImpl();

			model.setEscapedModel(true);

			model.setRowId(getRowId());
			model.setValueId(getValueId());

			model = (ExpandoRowValues)Proxy.newProxyInstance(ExpandoRowValues.class.getClassLoader(),
					new Class[] { ExpandoRowValues.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		ExpandoRowValuesImpl clone = new ExpandoRowValuesImpl();

		clone.setRowId(getRowId());
		clone.setValueId(getValueId());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ExpandoRowValuesImpl expandoRowValues = (ExpandoRowValuesImpl)obj;

		ExpandoRowValuesPK pk = expandoRowValues.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ExpandoRowValuesImpl expandoRowValues = null;

		try {
			expandoRowValues = (ExpandoRowValuesImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		ExpandoRowValuesPK pk = expandoRowValues.getPrimaryKey();

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

	private long _rowId;
	private long _valueId;
}