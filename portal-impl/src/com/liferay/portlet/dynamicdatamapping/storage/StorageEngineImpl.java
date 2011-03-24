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

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 */
public class StorageEngineImpl implements StorageEngine {

	public long create(
			long companyId, long structureId, Fields fields,
			ServiceContext serviceContext)
		throws StorageException {

		return getStorageAdapterByStructure(structureId).create(
			companyId, structureId, fields, serviceContext);
	}

	public void deleteByClass(long classPK) throws StorageException {
		getStorageAdapterByClass(classPK).deleteByClass(classPK);
	}

	public void deleteByStructure(long structureId)
		throws StorageException {

		getStorageAdapterByStructure(structureId).deleteByStructure(
			structureId);
	}

	public Fields getFields(long classPK) throws StorageException {
		return getFields(classPK, null);
	}

	public Fields getFields(long classPK, List<String> fieldNames)
		throws StorageException {

		return getStorageAdapterByClass(classPK).getFields(classPK, fieldNames);
	}

	public List<Fields> getFieldsListByClasses(
			long structureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException {

		return getStorageAdapterByStructure(structureId).getFieldsListByClasses(
			structureId, classPKs, fieldNames, orderByComparator);
	}

	public List<Fields> getFieldsListByClasses(
			long structureId, long[] classPKs,
			OrderByComparator orderByComparator)
		throws StorageException {

		return getFieldsListByClasses(
			structureId, classPKs, null, orderByComparator);
	}

	public List<Fields> getFieldsListByStructure(
			long structureId, List<String> fieldNames)
		throws StorageException {

		return getFieldsListByStructure(structureId, fieldNames, null);
	}

	public List<Fields> getFieldsListByStructure(
			long structureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException {

		return getStorageAdapterByStructure(structureId)
				.getFieldsListByStructure(
					structureId, fieldNames, orderByComparator);
	}

	public Map<Long, Fields> getFieldsMapByClasses(
			long structureId, long[] classPKs)
		throws StorageException {

		return getFieldsMapByClasses(structureId, classPKs, null);
	}

	public Map<Long, Fields> getFieldsMapByClasses(
			long structureId, long[] classPKs, List<String> fieldNames)
		throws StorageException {

		return getStorageAdapterByStructure(structureId).getFieldsMapByClasses(
			structureId, classPKs, fieldNames);
	}

	public List<Fields> query(
			long structureId, List<String> fieldNames, String whereClause,
			OrderByComparator orderByComparator)
		throws StorageException {

		return getStorageAdapterByStructure(structureId).query(
			structureId, fieldNames, whereClause, orderByComparator);
	}

	public int queryCount(long structureId, String whereClause)
		throws StorageException {

		return getStorageAdapterByStructure(structureId).queryCount(
			structureId, whereClause);
	}

	public void setDefaultStorageAdapter(
		StorageAdapter defaultStorageEngine) {

		_defaultStorageAdapter = defaultStorageEngine;
	}

	public void setStorageAdapters(
		Map<String, StorageAdapter> storageAdapters) {

		_storageAdapters = storageAdapters;
	}

	public void update(
			long classPK, Fields fields, ServiceContext serviceContext)
		throws StorageException {

		update(classPK, fields, serviceContext, false);
	}

	public void update(
			long classPK, Fields fields, ServiceContext serviceContext,
			boolean merge)
		throws StorageException {

		getStorageAdapterByClass(classPK).update(
			classPK, fields, serviceContext, merge);
	}

	protected StorageAdapter getStorageAdapter(String storageType) {
		StorageAdapter storageAdapter = _storageAdapters.get(storageType);

		if (storageAdapter == null) {
			storageAdapter = _defaultStorageAdapter;
		}

		return storageAdapter;
	}

	protected StorageAdapter getStorageAdapterByClass(long classPK)
		throws StorageException {

		try {
			DDMStorageLink storageLink =
				DDMStorageLinkLocalServiceUtil.getClassStorageLink(classPK);

			return getStorageAdapter(storageLink.getStorageType());
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	protected StorageAdapter getStorageAdapterByStructure(long structureId)
		throws StorageException {

		try {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getDDMStructure(structureId);

			return getStorageAdapter(ddmStructure.getStorageType());
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	private Map<String, StorageAdapter> _storageAdapters =
		new HashMap<String, StorageAdapter>();

	private StorageAdapter _defaultStorageAdapter;

}