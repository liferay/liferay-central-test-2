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

package com.liferay.portlet.expando.model.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.service.ServiceContext;
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

import java.io.Serializable;

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
 */
public class ExpandoBridgeImpl implements ExpandoBridge {

	public ExpandoBridgeImpl(String className) {
		this(className, 0);
	}

	public ExpandoBridgeImpl(String className, long classPK) {
		_className = className;
		_classPK = classPK;

		if (IndexerRegistryUtil.getIndexer(className) == null) {
			setIndexEnabled(true);
		}
	}

	public void addAttribute(String name) throws PortalException {
		addAttribute(name, ExpandoColumnConstants.STRING, null);
	}

	public void addAttribute(String name, int type) throws PortalException {
		addAttribute(name, type, null);
	}

	public void addAttribute(String name, int type, Serializable defaultValue)
		throws PortalException {

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
			if (e instanceof PortalException) {
				throw (PortalException)e;
			}
			else {
				_log.error(e, e);
			}
		}
	}

	public Serializable getAttribute(String name) {
		Serializable data = null;

		try {
			data = ExpandoValueServiceUtil.getData(
				_className, ExpandoTableConstants.DEFAULT_TABLE_NAME, name,
				_classPK);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return data;
	}

	public Serializable getAttributeDefault(String name) {
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
			if (_log.isDebugEnabled()) {
				_log.debug("Properties for " + name, e);
			}

			return new UnicodeProperties(true);
		}
	}

	public Map<String, Serializable> getAttributes() {
		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

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

	public boolean hasAttribute(String name) {
		ExpandoColumn column = null;

		try {
			column = ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
				_className, name);
		}
		catch (Exception e) {
		}

		if (column != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isIndexEnabled() {
		if (_indexEnabled && (_classPK > 0)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void reindex() {
		if (!isIndexEnabled()) {
			return;
		}

		Indexer indexer = IndexerRegistryUtil.getIndexer(_className);

		if (indexer != null) {
			try {
				indexer.reindex(_className, _classPK);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public void setAttribute(String name, Serializable value) {
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

	public void setAttributeDefault(String name, Serializable defaultValue) {
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

	public void setAttributes(Map<String, Serializable> attributes) {
		if (attributes == null) {
			return;
		}

		for (Map.Entry<String, Serializable> entry : attributes.entrySet()) {
			setAttribute(entry.getKey(), entry.getValue());
		}
	}

	public void setAttributes(ServiceContext serviceContext) {
		if (serviceContext == null) {
			return;
		}

		setAttributes(serviceContext.getExpandoBridgeAttributes());
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setIndexEnabled(boolean indexEnabled) {
		_indexEnabled = indexEnabled;
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoBridgeImpl.class);

	private String _className;
	private long _classPK;
	private boolean _indexEnabled;

}