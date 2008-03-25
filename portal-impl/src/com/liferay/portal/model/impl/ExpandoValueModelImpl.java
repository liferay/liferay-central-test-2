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
import com.liferay.portal.model.ExpandoValue;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

/**
 * <a href="ExpandoValueModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ExpandoValue</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.model.ExpandoValue
 * @see com.liferay.portal.service.model.ExpandoValueModel
 * @see com.liferay.portal.service.model.impl.ExpandoValueImpl
 *
 */
public class ExpandoValueModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "ExpandoValue";
	public static final Object[][] TABLE_COLUMNS = {
			{ "valueId", new Integer(Types.BIGINT) },
			

			{ "columnId", new Integer(Types.BIGINT) },
			

			{ "classPK", new Integer(Types.BIGINT) },
			

			{ "value", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table ExpandoValue (valueId LONG not null primary key,columnId LONG,classPK LONG,value STRING null)";
	public static final String TABLE_SQL_DROP = "drop table ExpandoValue";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ExpandoValue"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ExpandoValue"));

	public ExpandoValueModelImpl() {
	}

	public long getPrimaryKey() {
		return _valueId;
	}

	public void setPrimaryKey(long pk) {
		setValueId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_valueId);
	}

	public long getValueId() {
		return _valueId;
	}

	public void setValueId(long valueId) {
		if (valueId != _valueId) {
			_valueId = valueId;
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

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		if (classPK != _classPK) {
			_classPK = classPK;
		}
	}

	public String getValue() {
		return GetterUtil.getString(_value);
	}

	public void setValue(String value) {
		if (((value == null) && (_value != null)) ||
				((value != null) && (_value == null)) ||
				((value != null) && (_value != null) && !value.equals(_value))) {
			_value = value;
		}
	}

	public ExpandoValue toEscapedModel() {
		if (isEscapedModel()) {
			return (ExpandoValue)this;
		}
		else {
			ExpandoValue model = new ExpandoValueImpl();

			model.setEscapedModel(true);

			model.setValueId(getValueId());
			model.setColumnId(getColumnId());
			model.setClassPK(getClassPK());
			model.setValue(HtmlUtil.escape(getValue()));

			model = (ExpandoValue)Proxy.newProxyInstance(ExpandoValue.class.getClassLoader(),
					new Class[] { ExpandoValue.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		ExpandoValueImpl clone = new ExpandoValueImpl();

		clone.setValueId(getValueId());
		clone.setColumnId(getColumnId());
		clone.setClassPK(getClassPK());
		clone.setValue(getValue());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ExpandoValueImpl expandoValue = (ExpandoValueImpl)obj;

		long pk = expandoValue.getPrimaryKey();

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

		ExpandoValueImpl expandoValue = null;

		try {
			expandoValue = (ExpandoValueImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = expandoValue.getPrimaryKey();

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

	private long _valueId;
	private long _columnId;
	private long _classPK;
	private String _value;
}