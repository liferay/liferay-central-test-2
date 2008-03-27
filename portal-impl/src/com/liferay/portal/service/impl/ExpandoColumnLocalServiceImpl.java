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

package com.liferay.portal.service.impl;

import com.liferay.portal.ExpandoColumnNameException;
import com.liferay.portal.ExpandoColumnTypeException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ExpandoColumn;
import com.liferay.portal.model.impl.ExpandoColumnImpl;
import com.liferay.portal.service.base.ExpandoColumnLocalServiceBaseImpl;

import java.util.List;
import java.util.Properties;

/**
 * <a href="ExpandoColumnLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoColumnLocalServiceImpl
	extends ExpandoColumnLocalServiceBaseImpl {

	public ExpandoColumn addColumn(long classNameId, String name, int type)
		throws PortalException, SystemException {

		return addColumn(classNameId, name, type, null);
	}

	public ExpandoColumn addColumn(
			long classNameId, String name, int type, Properties properties)
		throws PortalException, SystemException {

		validate(name, type);

		long expandoId = counterLocalService.increment();

		ExpandoColumn column = expandoColumnPersistence.create(expandoId);

		column.setClassNameId(classNameId);
		column.setName(name);
		column.setType(type);

		column.setSettingsProperties(properties);

		expandoColumnPersistence.update(column, false);

		return column;
	}

	public void addTableColumns(long tableId, long[] columnIds)
		throws PortalException, SystemException {

		expandoTablePersistence.addExpandoColumns(tableId, columnIds);
	}

	public void addTableColumns(long tableId, List<ExpandoColumn> columns)
		throws PortalException, SystemException {

		expandoTablePersistence.addExpandoColumns(tableId, columns);
	}

	public void deleteColumn(long columnId)
		throws PortalException, SystemException {

		// ExpandoValues

		expandoValueLocalService.deleteColumnValues(columnId);

		// ExpandoColumn

		expandoColumnPersistence.remove(columnId);
	}

	public void deleteTableColumns(long tableId)
		throws PortalException, SystemException {

		// ExpandoColumns

		List<ExpandoColumn> columns = getTableColumns(tableId);

		for (ExpandoColumn column : columns) {
			deleteColumn(column.getColumnId());
		}

		// ExpandoTable

		expandoTablePersistence.removeExpandoColumns(tableId, columns);
	}

	public void deleteTableColumns(long tableId, long[] columnIds)
		throws PortalException, SystemException {

		// ExpandoColumns

		for (int i = 0; i < columnIds.length; i++) {
			deleteColumn(columnIds[i]);
		}

		// ExpandoTable

		expandoTablePersistence.removeExpandoColumns(tableId, columnIds);
	}

	public void deleteTableColumns(long tableId, List<ExpandoColumn> columns)
		throws PortalException, SystemException {

		// ExpandoColumns

		for (ExpandoColumn column : columns) {
			deleteColumn(column.getColumnId());
		}

		// ExpandoTable

		expandoTablePersistence.removeExpandoColumns(tableId, columns);
	}

	public ExpandoColumn getColumn(long columnId)
		throws PortalException, SystemException {

		return expandoColumnPersistence.findByPrimaryKey(columnId);
	}

	public ExpandoColumn getColumn(long classNameId, String name)
		throws PortalException, SystemException {

		return expandoColumnPersistence.findByC_N(classNameId, name);
	}

	public List<ExpandoColumn> getColumns(long classNameId)
		throws PortalException, SystemException {

		return expandoColumnPersistence.findByClassNameId(classNameId);
	}

	public List<ExpandoColumn> getColumns(long classNameId, int begin, int end)
		throws PortalException, SystemException {

		return expandoColumnPersistence.findByClassNameId(
			classNameId, begin, end);
	}

	public int getColumnsCount(long classNameId)
		throws PortalException, SystemException {

		return expandoColumnPersistence.countByClassNameId(classNameId);
	}

	public List<ExpandoColumn> getTableColumns(long tableId)
		throws PortalException, SystemException {

		return expandoTablePersistence.getExpandoColumns(tableId);
	}

	public List<ExpandoColumn> getTableColumns(long tableId, int begin, int end)
		throws PortalException, SystemException {

		return expandoTablePersistence.getExpandoColumns(tableId, begin, end);
	}

	public int getTableColumnsCount(long tableId)
		throws PortalException, SystemException {

		return expandoTablePersistence.getExpandoColumnsSize(tableId);
	}

	public ExpandoColumn setColumn(long classNameId, String name, int type)
		throws PortalException, SystemException {

		return setColumn(classNameId, name, type, null);
	}

	public ExpandoColumn setColumn(
			long classNameId, String name, int type, Properties properties)
		throws PortalException, SystemException {

		ExpandoColumn column =
			expandoColumnPersistence.fetchByC_N(classNameId, name);

		if (column == null) {
			validate(name, type);

			long expandoId = counterLocalService.increment();

			column = expandoColumnPersistence.create(expandoId);

			column.setClassNameId(classNameId);
			column.setName(name);
			column.setType(type);

			column.setSettingsProperties(properties);

			expandoColumnPersistence.update(column, false);
		}

		return column;
	}

	public ExpandoColumn updateColumnType(long columnId, int type)
		throws PortalException, SystemException {

		validate(type);

		ExpandoColumn column =
			expandoColumnPersistence.findByPrimaryKey(columnId);

		if (column.getType() != type) {
			column.setType(type);

			expandoColumnPersistence.update(column, false);
		}

		return column;
	}

	public ExpandoColumn updateColumnName(long columnId, String name)
		throws PortalException, SystemException {

		validate(name);

		ExpandoColumn column =
			expandoColumnPersistence.findByPrimaryKey(columnId);

		column.setName(name);

		return expandoColumnPersistence.update(column, false);
	}

	public ExpandoColumn updateColumnProperties(
			long columnId, Properties properties)
		throws PortalException, SystemException {

		ExpandoColumn column =
			expandoColumnPersistence.findByPrimaryKey(columnId);

		column.setSettingsProperties(properties);

		return expandoColumnPersistence.update(column, false);
	}

	protected void validate(String name, int type)
		throws PortalException, SystemException {

		validate(name);
		validate(type);
	}

	protected void validate(String name)
		throws PortalException, SystemException {

		if (!Validator.isNull(name)) {
			throw new ExpandoColumnNameException();
		}
	}

	protected void validate(int type)
		throws PortalException, SystemException {

		if (type != ExpandoColumnImpl.BOOLEAN &&
			type != ExpandoColumnImpl.BOOLEAN_ARRAY &&
			type != ExpandoColumnImpl.DATE &&
			type != ExpandoColumnImpl.DOUBLE &&
			type != ExpandoColumnImpl.DOUBLE_ARRAY &&
			type != ExpandoColumnImpl.FLOAT &&
			type != ExpandoColumnImpl.FLOAT_ARRAY &&
			type != ExpandoColumnImpl.INTEGER &&
			type != ExpandoColumnImpl.INTEGER_ARRAY &&
			type != ExpandoColumnImpl.LONG &&
			type != ExpandoColumnImpl.LONG_ARRAY &&
			type != ExpandoColumnImpl.SHORT &&
			type != ExpandoColumnImpl.SHORT_ARRAY &&
			type != ExpandoColumnImpl.STRING) {

			throw new ExpandoColumnTypeException();
		}
	}

}