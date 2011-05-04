/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.query.ComparisonOperator;
import com.liferay.portlet.dynamicdatamapping.storage.query.Condition;
import com.liferay.portlet.dynamicdatamapping.storage.query.FieldCondition;
import com.liferay.portlet.dynamicdatamapping.storage.query.Junction;
import com.liferay.portlet.dynamicdatamapping.storage.query.LogicalOperator;
import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoRowLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueServiceUtil;
import com.liferay.portlet.expando.util.ExpandoConverterUtil;

import edu.emory.mathcs.backport.java.util.Collections;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class ExpandoStorageAdapter extends BaseStorageAdapter {

	protected long doCreate(
			long companyId, long ddmStructureId, Fields fields,
			ServiceContext serviceContext)
		throws Exception {

		ExpandoTable expandoTable = _getExpandoTable(
			companyId, ddmStructureId, fields);

		ExpandoRow expandoRow = ExpandoRowLocalServiceUtil.addRow(
			expandoTable.getTableId(), CounterLocalServiceUtil.increment());

		_updateFields(expandoTable, expandoRow.getClassPK(), fields);

		DDMStorageLinkLocalServiceUtil.addStorageLink(
			expandoTable.getClassNameId(), expandoRow.getRowId(),
			ddmStructureId, serviceContext);

		return expandoRow.getRowId();
	}

	protected void doDeleteByClass(long classPK) throws Exception {
		_deleteExpandoRows(new long[] {classPK});
	}

	protected void doDeleteByDDMStructure(long ddmStructureId)
		throws Exception {

		long[] expandoRowIds = _getExpandoRowIds(ddmStructureId);

		_deleteExpandoRows(expandoRowIds);
	}

	protected List<Fields> doGetFieldsListByClasses(
			long ddmStructureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception {

		Map<Long, Fields> fieldsMap = _doQuery(classPKs, fieldNames, null);

		return _toList(fieldsMap, orderByComparator);
	}

	protected List<Fields> doGetFieldsListByDDMStructure(
			long ddmStructureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception {

		long[] expandoRowIds = _getExpandoRowIds(ddmStructureId);

		Map<Long, Fields> fieldsMap = _doQuery(expandoRowIds, fieldNames, null);

		return _toList(fieldsMap, orderByComparator);
	}

	protected Map<Long, Fields> doGetFieldsMapByClasses(
			long ddmStructureId, long[] classPKs, List<String> fieldNames)
		throws Exception {

		return _doQuery(classPKs, fieldNames, null);
	}

	protected List<Fields> doQuery(
			long ddmStructureId, List<String> fieldNames, Condition condition,
			OrderByComparator orderByComparator)
		throws Exception {

		long[] expandoRowIds = _getExpandoRowIds(ddmStructureId);

		Map<Long, Fields> fieldsMap = _doQuery(
			expandoRowIds, fieldNames, condition);

		return _toList(fieldsMap, orderByComparator);
	}

	protected int doQueryCount(long ddmStructureId, Condition condition)
		throws Exception {

		long[] expandoRowIds = _getExpandoRowIds(ddmStructureId);

		return _doQueryCount(expandoRowIds, condition);
	}

	protected void doUpdate(
			long classPK, Fields fields, boolean mergeFields,
			ServiceContext serviceContext)
		throws Exception {

		ExpandoRow expandoRow = ExpandoRowLocalServiceUtil.getRow(classPK);

		DDMStorageLink ddmStorageLink =
			DDMStorageLinkLocalServiceUtil.getClassStorageLink(
				expandoRow.getRowId());

		ExpandoTable expandoTable = _getExpandoTable(
			expandoRow.getCompanyId(), ddmStorageLink.getStructureId(), fields);

		List<ExpandoColumn> expandoColumns =
			ExpandoColumnLocalServiceUtil.getColumns(expandoTable.getTableId());

		if (!mergeFields) {
			for (ExpandoColumn expandoColumn : expandoColumns) {
				if (!fields.contains(expandoColumn.getName())) {
					ExpandoValueLocalServiceUtil.deleteValue(
						expandoColumn.getColumnId(), expandoRow.getRowId());
				}
			}
		}

		_updateFields(expandoTable, expandoRow.getClassPK(), fields);
	}

	private void _checkExpandoColumns(ExpandoTable expandoTable, Fields fields)
		throws PortalException, SystemException {

		for (String name : fields.getNames()) {
			try {
				ExpandoColumnLocalServiceUtil.getColumn(
					expandoTable.getTableId(), name);
			}
			catch (NoSuchColumnException nsce) {
				ExpandoColumnLocalServiceUtil.addColumn(
					expandoTable.getTableId(), name,
					ExpandoColumnConstants.STRING);
			}
		}
	}

	private void _deleteExpandoRows(long[] expandoRowIds)
		throws PortalException, SystemException {

		for (long rowId : expandoRowIds) {
			ExpandoRowLocalServiceUtil.deleteExpandoRow(rowId);
		}
	}

	private Map<Long, Fields> _doQuery(
			long[] expandoRowIds, List<String> fieldNames, Condition condition)
		throws Exception {

		Map<Long, Fields> fieldsMap = new HashMap<Long, Fields>();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DataAccess.getConnection();

			statement = connection.createStatement();

			String sql = _toSQL(expandoRowIds, fieldNames, condition, false);

			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				long rowId = resultSet.getLong("rowId_");
				String name = resultSet.getString("name");
				int type = resultSet.getInt("type_");
				String data = resultSet.getString("data_");

				Fields fields = fieldsMap.get(rowId);

				if (fields == null) {
					fields = new Fields();

					fieldsMap.put(rowId, fields);
				}

				Serializable value =
					ExpandoConverterUtil.getAttributeFromString(type, data);

				Field field = new Field(name, value);

				fields.put(field);
			}
		}
		finally {
			DataAccess.cleanUp(connection, statement, resultSet);
		}

		return fieldsMap;
	}

	private int _doQueryCount(long[] expandoRowIds, Condition condition)
		throws Exception {

		int count = 0;

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DataAccess.getConnection();

			statement = connection.createStatement();

			String sql = _toSQL(expandoRowIds, null, condition, true);

			resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				count = resultSet.getInt("COUNT_VALUE");
			}
		}
		finally {
			DataAccess.cleanUp(connection, statement, resultSet);
		}

		return count;
	}

	private long[] _getExpandoRowIds(long ddmStructureId)
		throws SystemException {

		List<DDMStorageLink> ddmStorageLinks =
			DDMStorageLinkLocalServiceUtil.getStructureStorageLinks(
				ddmStructureId);

		long[] expandoRowIds = new long[ddmStorageLinks.size()];

		for (int i = 0; i < ddmStorageLinks.size(); i++) {
			DDMStorageLink ddmStorageLink = ddmStorageLinks.get(i);

			expandoRowIds[i] = ddmStorageLink.getClassPK();
		}

		return expandoRowIds;
	}

	private ExpandoTable _getExpandoTable(
			long companyId, long ddmStructureId, Fields fields)
		throws PortalException, SystemException {

		ExpandoTable expandoTable = null;

		long classNameId = PortalUtil.getClassNameId(
			ExpandoStorageAdapter.class.getName());

		try {
			expandoTable = ExpandoTableLocalServiceUtil.getTable(
				companyId, classNameId, String.valueOf(ddmStructureId));
		}
		catch (NoSuchTableException nste) {
			expandoTable = ExpandoTableLocalServiceUtil.addTable(
				companyId, classNameId, String.valueOf(ddmStructureId));
		}

		_checkExpandoColumns(expandoTable, fields);

		return expandoTable;
	}

	private List<Fields> _toList(
		Map<Long, Fields> fieldsMap, OrderByComparator orderByComparator) {

		List<Fields> fieldsList = ListUtil.fromCollection(fieldsMap.values());

		if (orderByComparator != null) {
			Collections.sort(fieldsList, orderByComparator);
		}

		return fieldsList;
	}

	private String _toSQL(Condition condition) {
		if (condition.isJunction()) {
			Junction junction = (Junction)condition;

			return StringPool.OPEN_PARENTHESIS.concat(
				_toSQL(junction)).concat(StringPool.CLOSE_PARENTHESIS);
		}
		else {
			FieldCondition fieldCondition = (FieldCondition)condition;

			return _toSQL(fieldCondition);
		}
	}

	private String _toSQL(FieldCondition fieldCondition) {
		StringBundler sb = new StringBundler(10);

		sb.append("(ExpandoColumn.name = '");
		sb.append(fieldCondition.getName());
		sb.append("' AND ");

		Object value = fieldCondition.getValue();

		ComparisonOperator comparisonOperator =
			fieldCondition.getComparisonOperator();

		sb.append("ExpandoValue.data_ ");
		sb.append(comparisonOperator);
		sb.append(StringPool.SPACE);

		if ((value.getClass() == String.class) &&
			(!comparisonOperator.equals(ComparisonOperator.IN))) {

			sb.append(StringPool.APOSTROPHE);
		}
		else if (comparisonOperator.equals(ComparisonOperator.IN)) {
			sb.append(StringPool.OPEN_PARENTHESIS);
		}

		sb.append(value);

		if ((value.getClass() == String.class) &&
			(!comparisonOperator.equals(ComparisonOperator.IN))) {

			sb.append(StringPool.APOSTROPHE);
		}
		else if (comparisonOperator.equals(ComparisonOperator.IN)) {
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private String _toSQL(Junction junction) {
		StringBundler sb = new StringBundler();

		LogicalOperator logicalOperator = junction.getLogicalOperator();

		Iterator<Condition> itr = junction.iterator();

		while (itr.hasNext()) {
			Condition condition = itr.next();

			sb.append(_toSQL(condition));

			if (itr.hasNext()) {
				sb.append(StringPool.SPACE);
				sb.append(logicalOperator.toString());
				sb.append(StringPool.SPACE);
			}
		}

		return sb.toString();
	}

	private String _toSQL(
		long[] expandoRowIds, List<String> fieldNames, Condition condition,
		boolean count) {

		StringBundler sb = new StringBundler();

		if (count) {
			sb.append("SELECT COUNT(DISTINCT(ExpandoValue.rowId_)) AS ");
			sb.append("COUNT_VALUE ");
		}
		else {
			sb.append("SELECT ExpandoColumn.name, ExpandoColumn.type_, ");
			sb.append("ExpandoValue.rowId_, ExpandoValue.data_ ");
		}

		sb.append("FROM ExpandoValue INNER JOIN ExpandoColumn ON (");
		sb.append("ExpandoColumn.columnId = ExpandoValue.columnId) WHERE ");
		sb.append("ExpandoValue.rowId_ IN (");

		if (condition == null) {
			sb.append(StringUtil.merge(expandoRowIds));
		}
		else {
			sb.append("SELECT DISTINCT (ExpandoValue.rowId_) FROM ");
			sb.append("ExpandoValue INNER JOIN ExpandoColumn ON (");
			sb.append("ExpandoColumn.columnId = ExpandoValue.columnId) WHERE ");
			sb.append("ExpandoValue.rowId_ IN (");
			sb.append(StringUtil.merge(expandoRowIds));
			sb.append(") AND ");
			sb.append(_toSQL(condition));
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		if (fieldNames != null && !fieldNames.isEmpty()) {
			sb.append(" AND ExpandoColumn.name IN (");

			for (int i = 0; i < fieldNames.size(); i++) {
				String fieldName = fieldNames.get(i);

				sb.append(StringUtil.quote(fieldName));

				if ((i + 1) < fieldNames.size()) {
					sb.append(StringPool.COMMA);
				}
				else {
					sb.append(StringPool.CLOSE_PARENTHESIS);
				}
			}
		}

		sb.append(StringPool.SPACE);

		return sb.toString();
	}

	private void _updateFields(
			ExpandoTable expandoTable, long classPK, Fields fields)
		throws PortalException, SystemException {

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

			ExpandoValueServiceUtil.addValue(
				expandoTable.getCompanyId(),
				ExpandoStorageAdapter.class.getName(), expandoTable.getName(),
				field.getName(), classPK, field.getValue());
		}
	}

}