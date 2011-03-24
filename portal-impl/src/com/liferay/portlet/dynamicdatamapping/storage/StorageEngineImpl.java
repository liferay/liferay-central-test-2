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

		StorageAdapter storageAdapter = getStructureStorageAdapter(structureId);

		return storageAdapter.create(
			companyId, structureId, fields, serviceContext);
	}

	public void deleteByClass(long classPK) throws StorageException {
		StorageAdapter storageAdapter = getClassStorageAdapter(classPK);

		storageAdapter.deleteByClass(classPK);
	}

	public void deleteByStructure(long structureId)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(structureId);

		storageAdapter.deleteByStructure(structureId);
	}

	public Fields getFields(long classPK) throws StorageException {
		return getFields(classPK, null);
	}

	public Fields getFields(long classPK, List<String> fieldNames)
		throws StorageException {

		StorageAdapter storageAdapter = getClassStorageAdapter(classPK);

		return storageAdapter.getFields(classPK, fieldNames);
	}

	public List<Fields> getFieldsList(
			long structureId, List<String> fieldNames)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(structureId);

		return storageAdapter.getFieldsList(structureId, fieldNames);
	}

	public List<Fields> getFieldsList(
			long structureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(structureId);

		return storageAdapter.getFieldsList(
			structureId, fieldNames, orderByComparator);
	}

	public List<Fields> getFieldsList(
			long structureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(structureId);

		return storageAdapter.getFieldsList(
			structureId, classPKs, fieldNames, orderByComparator);
	}

	public List<Fields> getFieldsList(
			long structureId, long[] classPKs,
			OrderByComparator orderByComparator)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(structureId);

		return storageAdapter.getFieldsList(
			structureId, classPKs, orderByComparator);
	}

	public Map<Long, Fields> getFieldsMap(long structureId, long[] classPKs)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(structureId);

		return storageAdapter.getFieldsMap(structureId, classPKs);
	}

	public Map<Long, Fields> getFieldsMap(
			long structureId, long[] classPKs, List<String> fieldNames)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(structureId);

		return storageAdapter.getFieldsMap(structureId, classPKs, fieldNames);
	}

	public List<Fields> query(
			long structureId, List<String> fieldNames, String whereClause,
			OrderByComparator orderByComparator)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(structureId);

		return storageAdapter.query(
			structureId, fieldNames, whereClause, orderByComparator);
	}

	public int queryCount(long structureId, String whereClause)
		throws StorageException {

		StorageAdapter storageAdapter = getStructureStorageAdapter(structureId);

		return storageAdapter.queryCount(structureId, whereClause);
	}

	public void setDefaultStorageAdapter(
		StorageAdapter defaultStorageAdapter) {

		_defaultStorageAdapter = defaultStorageAdapter;
	}

	public void setStorageAdapters(
		Map<String, StorageAdapter> storageAdapters) {

		_storageAdapters = storageAdapters;
	}

	public void update(
			long classPK, Fields fields, ServiceContext serviceContext)
		throws StorageException {

		StorageAdapter storageAdapter = getClassStorageAdapter(classPK);

		storageAdapter.update(classPK, fields, serviceContext);
	}

	public void update(
			long classPK, Fields fields, ServiceContext serviceContext,
			boolean merge)
		throws StorageException {

		StorageAdapter storageAdapter = getClassStorageAdapter(classPK);

		storageAdapter.update(classPK, fields, serviceContext, merge);
	}

	protected StorageAdapter getClassStorageAdapter(long classPK)
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

	protected StorageAdapter getStorageAdapter(String storageType) {
		StorageAdapter storageAdapter = _storageAdapters.get(storageType);

		if (storageAdapter == null) {
			storageAdapter = _defaultStorageAdapter;
		}

		return storageAdapter;
	}

	protected StorageAdapter getStructureStorageAdapter(long structureId)
		throws StorageException {

		try {
			DDMStructure structure =
				DDMStructureLocalServiceUtil.getDDMStructure(structureId);

			return getStorageAdapter(structure.getStorageType());
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	private StorageAdapter _defaultStorageAdapter;
	private Map<String, StorageAdapter> _storageAdapters =
		new HashMap<String, StorageAdapter>();

}