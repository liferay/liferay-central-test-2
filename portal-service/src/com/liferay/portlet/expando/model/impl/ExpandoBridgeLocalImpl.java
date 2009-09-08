/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;
import com.liferay.portlet.expando.NoSuchTableException;

import java.io.Serializable;

/**
 * <a href="ExpandoBridgeLocalImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Edward Han
 */
public class ExpandoBridgeLocalImpl extends ExpandoBridgeImpl {
	public ExpandoBridgeLocalImpl(String className) {
		super(className);
	}

	public ExpandoBridgeLocalImpl(String className, long classPK) {
		super(className, classPK);
	}

	public ExpandoBridgeLocalImpl(ExpandoBridge base) {
		super(base.getClassName(), base.getClassPK());
	}

	public void addAttribute(String name, int type, Serializable defaultValue)
		throws PortalException {

		try {
			ExpandoTable table = null;

			try {
				table = ExpandoTableLocalServiceUtil.getDefaultTable(
					super.getClassName());
			}
			catch (NoSuchTableException nste) {
				table = ExpandoTableLocalServiceUtil.addDefaultTable(
					super.getClassName());
			}

			ExpandoColumnLocalServiceUtil.addColumn(
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
			data = ExpandoValueLocalServiceUtil.getData(
				super.getClassName(), ExpandoTableConstants.DEFAULT_TABLE_NAME, name,
				super.getClassPK());
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return data;
	}

	public void setAttribute(String name, Serializable value) {
		if (super.getClassPK() <= 0) {
			throw new UnsupportedOperationException();
		}

		try {
			ExpandoValueLocalServiceUtil.addValue(
				super.getClassName(), ExpandoTableConstants.DEFAULT_TABLE_NAME, name,
				super.getClassPK(), value);

			checkIndex(name);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setAttributeDefault(String name, Serializable defaultValue) {
		try {
			ExpandoColumn column =
				ExpandoColumnLocalServiceUtil.getDefaultTableColumn(
					super.getClassName(), name);

			ExpandoColumnLocalServiceUtil.updateColumn(
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
					super.getClassName(), name);

			ExpandoColumnLocalServiceUtil.updateTypeSettings(
				column.getColumnId(), properties.toString());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoBridgeLocalImpl.class);
}
