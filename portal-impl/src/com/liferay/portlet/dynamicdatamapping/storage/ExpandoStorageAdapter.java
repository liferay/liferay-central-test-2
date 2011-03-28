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

		return null;
	}

	protected List<Fields> doGetFieldsListByDDMStructure(
			long ddmStructureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception {

		return null;
	}

	protected Map<Long, Fields> doGetFieldsMapByClasses(
			long ddmStructureId, long[] classPKs, List<String> fieldNames)
		throws Exception {

		return null;
	}

	protected List<Fields> doQuery(
			long ddmStructureId, List<String> fieldNames, Condition condition,
			OrderByComparator orderByComparator)
		throws Exception {

		return null;
	}

	protected int doQueryCount(long ddmStructureId, Condition condition)
		throws Exception {

		return 0;
	}

	protected void doUpdate(
			long classPK, Fields fields, ServiceContext serviceContext,
			boolean merge)
		throws Exception {

		ExpandoRow expandoRow = ExpandoRowLocalServiceUtil.getRow(classPK);

		DDMStorageLink ddmStorageLink =
			DDMStorageLinkLocalServiceUtil.getClassStorageLink(
				expandoRow.getRowId());

		ExpandoTable expandoTable = _getExpandoTable(
			expandoRow.getCompanyId(), ddmStorageLink.getStructureId(), fields);

		List<ExpandoColumn> expandoColumns =
			ExpandoColumnLocalServiceUtil.getColumns(expandoTable.getTableId());

		if (!merge) {
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