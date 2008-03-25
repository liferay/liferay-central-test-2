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
import com.liferay.portal.model.ExpandoColumn;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

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
 * @see com.liferay.portal.service.model.ExpandoColumn
 * @see com.liferay.portal.service.model.ExpandoColumnModel
 * @see com.liferay.portal.service.model.impl.ExpandoColumnImpl
 *
 */
public class ExpandoColumnModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "ExpandoColumn";
	public static final Object[][] TABLE_COLUMNS = {
			{ "columnId", new Integer(Types.BIGINT) },
			

			{ "classNameId", new Integer(Types.BIGINT) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "type_", new Integer(Types.INTEGER) },
			

			{ "settings_", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table ExpandoColumn (columnId LONG not null primary key,classNameId LONG,name VARCHAR(200) null,type_ INTEGER,settings_ STRING null)";
	public static final String TABLE_SQL_DROP = "drop table ExpandoColumn";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ExpandoColumn"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ExpandoColumn"));

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

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		if (type != _type) {
			_type = type;
		}
	}

	public String getSettings() {
		return GetterUtil.getString(_settings);
	}

	public void setSettings(String settings) {
		if (((settings == null) && (_settings != null)) ||
				((settings != null) && (_settings == null)) ||
				((settings != null) && (_settings != null) &&
				!settings.equals(_settings))) {
			_settings = settings;
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
			model.setClassNameId(getClassNameId());
			model.setName(HtmlUtil.escape(getName()));
			model.setType(getType());
			model.setSettings(HtmlUtil.escape(getSettings()));

			model = (ExpandoColumn)Proxy.newProxyInstance(ExpandoColumn.class.getClassLoader(),
					new Class[] { ExpandoColumn.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		ExpandoColumnImpl clone = new ExpandoColumnImpl();

		clone.setColumnId(getColumnId());
		clone.setClassNameId(getClassNameId());
		clone.setName(getName());
		clone.setType(getType());
		clone.setSettings(getSettings());

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
	private long _classNameId;
	private String _name;
	private int _type;
	private String _settings;
}