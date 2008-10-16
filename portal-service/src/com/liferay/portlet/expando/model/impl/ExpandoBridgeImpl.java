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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoColumnServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="ExpandoBridgeImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class ExpandoBridgeImpl implements ExpandoBridge {

	public ExpandoBridgeImpl(String className) {
		this(className, 0);
	}

	public ExpandoBridgeImpl(String className, long classPK) {
		_className = className;
		_classPK = classPK;
	}

	public void addAttribute(String name) {
		addAttribute(name, ExpandoColumnConstants.STRING, null);
	}

	public void addAttribute(String name, int type) {
		addAttribute(name, type, null);
	}

	public void addAttribute(String name, int type, Object defaultValue) {
		try {
			ExpandoTable table = null;

			try {
				table = ExpandoTableLocalServiceUtil.getDefaultTable(
					_className);
			}
			catch (NoSuchTableException nste) {
				table = ExpandoTableLocalServiceUtil.addDefaultTable(
					_className);
			}

			ExpandoColumnServiceUtil.addColumn(
				table.getTableId(), name, type, defaultValue);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public Object getAttribute(String name) {
		Object data = null;

		try {
			data = ExpandoValueServiceUtil.getData(
				_className, ExpandoTableConstants.DEFAULT_TABLE_NAME, name,
				_classPK);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return data;
	}

	public Object getAttributeDefault(String name) {
		try {
			ExpandoColumn column =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
					_className, name);

			return column.getDefaultValue();
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	public Enumeration<String> getAttributeNames() {
		List<ExpandoColumn> columns = new ArrayList<ExpandoColumn>();

		try {
			columns = ExpandoColumnLocalServiceUtil.getDefaultTableColumns(
				_className);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		List<String> columnNames = new ArrayList<String>();

		for (ExpandoColumn column : columns) {
			columnNames.add(column.getName());
		}

		return Collections.enumeration(columnNames);
	}

	public UnicodeProperties getAttributeProperties(String name) {
		try {
			ExpandoColumn column =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
					_className, name);

			return column.getTypeSettingsProperties();
		}
		catch (Exception e) {
			_log.error(e, e);

			return new UnicodeProperties(true);
		}
	}

	public Map<String, Object> getAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		List<ExpandoColumn> columns = new ArrayList<ExpandoColumn>();

		try {
			columns = ExpandoColumnLocalServiceUtil.getDefaultTableColumns(
				_className);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		for (ExpandoColumn column : columns) {
			attributes.put(column.getName(), getAttribute(column.getName()));
		}

		return attributes;
	}

	public int getAttributeType(String name) {
		try {
			ExpandoColumn column =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
					_className, name);

			return column.getType();
		}
		catch (Exception e) {
			_log.error(e, e);

			return 0;
		}
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setAttribute(String name, Object value) {
		if (_classPK <= 0) {
			throw new UnsupportedOperationException();
		}

		try {
			ExpandoValueServiceUtil.addValue(
				_className, ExpandoTableConstants.DEFAULT_TABLE_NAME, name,
				_classPK, value);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setAttributeDefault(String name, Object defaultValue) {
		try {
			ExpandoColumn column =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
					_className, name);

			ExpandoColumnServiceUtil.updateColumn(
				column.getColumnId(), column.getName(), column.getType(),
				defaultValue);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setAttributeProperties(
		String name, UnicodeProperties properties) {

		try {
			ExpandoColumn column =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
					_className, name);

			ExpandoColumnServiceUtil.updateTypeSettings(
				column.getColumnId(), properties.toString());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoBridgeImpl.class);

	private String _className;
	private long _classPK;

}