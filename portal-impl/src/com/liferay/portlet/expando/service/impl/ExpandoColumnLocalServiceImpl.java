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

package com.liferay.portlet.expando.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.ColumnNameException;
import com.liferay.portlet.expando.ColumnTypeException;
import com.liferay.portlet.expando.DuplicateColumnNameException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.model.impl.ExpandoValueImpl;
import com.liferay.portlet.expando.service.base.ExpandoColumnLocalServiceBaseImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <a href="ExpandoColumnLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class ExpandoColumnLocalServiceImpl
	extends ExpandoColumnLocalServiceBaseImpl {

	public ExpandoColumn addColumn(long tableId, String name, int type)
		throws PortalException, SystemException {

		return addColumn(tableId, name, type, null);
	}

	public ExpandoColumn addColumn(
			long tableId, String name, int type, Object defaultData)
		throws PortalException, SystemException {

		// Column

		ExpandoTable table = expandoTablePersistence.findByPrimaryKey(tableId);

		ExpandoValue value = validate(0, tableId, name, type, defaultData);

		long columnId = counterLocalService.increment();

		ExpandoColumn column = expandoColumnPersistence.create(columnId);

		column.setCompanyId(table.getCompanyId());
		column.setTableId(tableId);
		column.setName(name);
		column.setType(type);
		column.setDefaultData(value.getData());

		expandoColumnPersistence.update(column, false);

		// Resources

		long companyId = CompanyThreadLocal.getCompanyId();

		resourceLocalService.addResources(
			companyId, 0, 0, ExpandoColumn.class.getName(),
			column.getColumnId(), false, false, false);

		return column;
	}

	public void deleteColumn(ExpandoColumn column) throws SystemException {

		// Column

		expandoColumnPersistence.remove(column);

		// Values

		expandoValueLocalService.deleteColumnValues(column.getColumnId());
	}

	public void deleteColumn(long columnId)
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnPersistence.findByPrimaryKey(
			columnId);

		deleteColumn(column);
	}

	public void deleteColumn(long tableId, String name)
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnPersistence.findByT_N(
			tableId, name);

		deleteColumn(column);
	}

	public void deleteColumn(long classNameId, String tableName, String name)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			classNameId, tableName);

		deleteColumn(table.getTableId(), name);
	}

	public void deleteColumn(String className, String tableName, String name)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteColumn(classNameId, tableName, name);
	}

	public void deleteColumns(long tableId) throws SystemException {
		List<ExpandoColumn> columns = expandoColumnPersistence.findByTableId(
			tableId);

		for (ExpandoColumn column : columns) {
			deleteColumn(column);
		}
	}

	public void deleteColumns(long classNameId, String tableName)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			classNameId, tableName);

		deleteColumns(table.getTableId());
	}

	public void deleteColumns(String className, String tableName)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteColumns(classNameId, tableName);
	}

	public ExpandoColumn getColumn(long columnId)
		throws PortalException, SystemException {

		return expandoColumnPersistence.findByPrimaryKey(columnId);
	}

	public ExpandoColumn getColumn(long tableId, String name)
		throws PortalException, SystemException {

		return expandoColumnPersistence.findByT_N(tableId, name);
	}

	public ExpandoColumn getColumn(
			long classNameId, String tableName, String name)
		throws SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return null;
		}

		return expandoColumnPersistence.fetchByT_N(table.getTableId(), name);
	}

	public ExpandoColumn getColumn(
			String className, String tableName, String name)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumn(classNameId, tableName, name);
	}

	public List<ExpandoColumn> getColumns(long tableId)
		throws SystemException {

		return expandoColumnPersistence.findByTableId(tableId);
	}

	public List<ExpandoColumn> getColumns(long classNameId, String tableName)
		throws SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return Collections.EMPTY_LIST;
		}

		return expandoColumnPersistence.findByTableId(table.getTableId());
	}

	public List<ExpandoColumn> getColumns(String className, String tableName)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumns(classNameId, tableName);
	}

	public int getColumnsCount(long tableId) throws SystemException {
		return expandoColumnPersistence.countByTableId(tableId);
	}

	public int getColumnsCount(long classNameId, String tableName)
		throws SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return 0;
		}

		return expandoColumnPersistence.countByTableId(table.getTableId());
	}

	public int getColumnsCount(String className, String tableName)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumnsCount(classNameId, tableName);
	}

	public ExpandoColumn getDefaultTableColumn(long classNameId, String name)
		throws SystemException {

		return getColumn(
			classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME, name);
	}

	public ExpandoColumn getDefaultTableColumn(String className, String name)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumn(
			classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME, name);
	}

	public List<ExpandoColumn> getDefaultTableColumns(long classNameId)
		throws SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);

		if (table == null) {
			return Collections.EMPTY_LIST;
		}

		return expandoColumnPersistence.findByTableId(table.getTableId());
	}

	public List<ExpandoColumn> getDefaultTableColumns(String className)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumns(
			classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	public int getDefaultTableColumnsCount(long classNameId)
		throws SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);

		if (table == null) {
			return 0;
		}

		return expandoColumnPersistence.countByTableId(table.getTableId());
	}

	public int getDefaultTableColumnsCount(String className)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumnsCount(
			classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	public ExpandoColumn updateColumn(long columnId, String name, int type)
		throws PortalException, SystemException {

		return updateColumn(columnId, name, type, null);
	}

	public ExpandoColumn updateColumn(
			long columnId, String name, int type, Object defaultData)
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnPersistence.findByPrimaryKey(
			columnId);

		ExpandoValue value = validate(
			columnId, column.getTableId(), name, type, defaultData);

		column.setName(name);
		column.setType(type);
		column.setDefaultData(value.getData());

		expandoColumnPersistence.update(column, false);

		return column;
	}

	public ExpandoColumn updateTypeSettings(long columnId, String typeSettings)
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnPersistence.findByPrimaryKey(
			columnId);

		column.setTypeSettings(typeSettings);

		expandoColumnPersistence.update(column, false);

		return column;
	}

	protected ExpandoValue validate(
			long columnId, long tableId, String name, int type,
			Object defaultData)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new ColumnNameException();
		}

		ExpandoColumn column = expandoColumnPersistence.fetchByT_N(
			tableId, name);

		if ((column != null) && (column.getColumnId() != columnId)) {
			throw new DuplicateColumnNameException();
		}

		if ((type != ExpandoColumnConstants.BOOLEAN) &&
			(type != ExpandoColumnConstants.BOOLEAN_ARRAY) &&
			(type != ExpandoColumnConstants.DATE) &&
			(type != ExpandoColumnConstants.DATE_ARRAY) &&
			(type != ExpandoColumnConstants.DOUBLE) &&
			(type != ExpandoColumnConstants.DOUBLE_ARRAY) &&
			(type != ExpandoColumnConstants.FLOAT) &&
			(type != ExpandoColumnConstants.FLOAT_ARRAY) &&
			(type != ExpandoColumnConstants.INTEGER) &&
			(type != ExpandoColumnConstants.INTEGER_ARRAY) &&
			(type != ExpandoColumnConstants.LONG) &&
			(type != ExpandoColumnConstants.LONG_ARRAY) &&
			(type != ExpandoColumnConstants.SHORT) &&
			(type != ExpandoColumnConstants.SHORT_ARRAY) &&
			(type != ExpandoColumnConstants.STRING) &&
			(type != ExpandoColumnConstants.STRING_ARRAY)) {

			throw new ColumnTypeException();
		}

		ExpandoValue value = new ExpandoValueImpl();

		if (Validator.isNotNull(defaultData)) {
			value.setColumnId(columnId);

			if (type == ExpandoColumnConstants.BOOLEAN) {
				value.setBoolean((Boolean)defaultData);
			}
			else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
				value.setBooleanArray((boolean[])defaultData);
			}
			else if (type == ExpandoColumnConstants.DATE) {
				value.setDate((Date)defaultData);
			}
			else if (type == ExpandoColumnConstants.DATE_ARRAY) {
				value.setDateArray((Date[])defaultData);
			}
			else if (type == ExpandoColumnConstants.DOUBLE) {
				value.setDouble((Double)defaultData);
			}
			else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
				value.setDoubleArray((double[])defaultData);
			}
			else if (type == ExpandoColumnConstants.FLOAT) {
				value.setFloat((Float)defaultData);
			}
			else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
				value.setFloatArray((float[])defaultData);
			}
			else if (type == ExpandoColumnConstants.INTEGER) {
				value.setInteger((Integer)defaultData);
			}
			else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
				value.setIntegerArray((int[])defaultData);
			}
			else if (type == ExpandoColumnConstants.LONG) {
				value.setLong((Long)defaultData);
			}
			else if (type == ExpandoColumnConstants.LONG_ARRAY) {
				value.setLongArray((long[])defaultData);
			}
			else if (type == ExpandoColumnConstants.SHORT) {
				value.setShort((Short)defaultData);
			}
			else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
				value.setShortArray((short[])defaultData);
			}
			else if (type == ExpandoColumnConstants.STRING) {
				value.setString((String)defaultData);
			}
			else if (type == ExpandoColumnConstants.STRING_ARRAY) {
				value.setStringArray((String[])defaultData);
			}
		}

		return value;
	}

}