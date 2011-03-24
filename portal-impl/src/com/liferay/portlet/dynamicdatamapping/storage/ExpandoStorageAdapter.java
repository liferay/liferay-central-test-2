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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.query.Condition;
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
			long companyId, long structureId, Fields fields,
			ServiceContext serviceContext)
		throws Exception {

		ExpandoTable table = _getExpandoTable(companyId, structureId, fields);

		ExpandoRow row = ExpandoRowLocalServiceUtil.addRow(
			table.getTableId(), CounterLocalServiceUtil.increment());

		_updateFields(table, row.getClassPK(), fields);

		DDMStorageLinkLocalServiceUtil.addStorageLink(
			table.getClassNameId(), row.getRowId(), structureId,
			serviceContext);

		return row.getRowId();
	}

	protected void doDeleteByClass(long classPK) throws Exception {
		_deleteRows(new long[] {classPK});
	}

	protected void doDeleteByStructure(long structureId) throws Exception {
		long[] rowIds = _getRowIdsByStructure(structureId);

		_deleteRows(rowIds);
	}

	protected List<Fields> doGetFieldsListByClasses(
			long structureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception {

		return null;
	}

	protected List<Fields> doGetFieldsListByStructure(
			long structureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception {

		return null;
	}

	protected Map<Long, Fields> doGetFieldsMapByClasses(
			long structureId, long[] classPKs, List<String> fieldNames)
		throws Exception {

		return null;
	}

	protected List<Fields> doQuery(
			long structureId, List<String> fieldNames, Condition whereCondition,
			OrderByComparator orderByComparator)
		throws Exception {

		return null;
	}

	protected int doQueryCount(long structureId, Condition whereCondition)
		throws Exception {

		return 0;
	}

	protected void doUpdate(
			long classPK, Fields fields, ServiceContext serviceContext,
			boolean merge)
		throws Exception {

		ExpandoRow row = ExpandoRowLocalServiceUtil.getRow(classPK);

		DDMStorageLink ddmStorageLink =
			DDMStorageLinkLocalServiceUtil.getClassStorageLink(row.getRowId());

		ExpandoTable table = _getExpandoTable(
			row.getCompanyId(), ddmStorageLink.getStructureId(), fields);

		List<ExpandoColumn> columns = ExpandoColumnLocalServiceUtil.getColumns(
			table.getTableId());

		if (!merge) {
			for (ExpandoColumn column : columns) {
				if (!fields.contains(column.getName())) {
					ExpandoValueLocalServiceUtil.deleteValue(
						column.getColumnId(), row.getRowId());
				}
			}
		}

		_updateFields(table, row.getClassPK(), fields);
	}

	private void _checkSchema(ExpandoTable table, Fields fields)
		throws PortalException, SystemException {

		for (String name : fields.getNames()) {
			try {
				ExpandoColumnLocalServiceUtil.getColumn(
					table.getTableId(), name);
			}
			catch (NoSuchColumnException nsce) {
				ExpandoColumnLocalServiceUtil.addColumn(
					table.getTableId(), name,
					ExpandoColumnConstants.STRING);
			}
		}
	}

	private void _deleteRows(long[] rowIds)
		throws PortalException, SystemException {

		for (long rowId : rowIds) {
			ExpandoRowLocalServiceUtil.deleteExpandoRow(rowId);
		}
	}

	private ExpandoTable _getExpandoTable(
			long companyId, long structureId, Fields fields)
		throws PortalException, SystemException {

		ExpandoTable table = null;

		long classNameId = PortalUtil.getClassNameId(
			ExpandoStorageAdapter.class.getName());

		try {
			table = ExpandoTableLocalServiceUtil.getTable(
				companyId, classNameId, String.valueOf(structureId));
		}
		catch (NoSuchTableException nste) {
			table = ExpandoTableLocalServiceUtil.addTable(
				companyId, classNameId, String.valueOf(structureId));
		}

		_checkSchema(table, fields);

		return table;
	}

	private long[] _getRowIdsByStructure(long structureId)
		throws SystemException {

		List<DDMStorageLink> ddmStorageLinks =
			DDMStorageLinkLocalServiceUtil.getStructureStorageLinks(
				structureId);

		long[] rowIds = new long[ddmStorageLinks.size()];

		for (int i = 0; i < ddmStorageLinks.size(); i++) {
			rowIds[i] = ddmStorageLinks.get(i).getClassPK();
		}

		return rowIds;
	}

	private void _updateFields(
			ExpandoTable table, long classPK, Fields fields)
		throws PortalException, SystemException {

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

			ExpandoValueServiceUtil.addValue(
				table.getCompanyId(), ExpandoStorageAdapter.class.getName(),
				table.getName(), field.getName(), classPK, field.getValue());
		}
	}

}