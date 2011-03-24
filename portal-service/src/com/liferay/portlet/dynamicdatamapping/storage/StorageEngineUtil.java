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

import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 */
public class StorageEngineUtil {

	public static long create(
			long companyId, long structureId, Fields fields,
			ServiceContext serviceContext)
		throws StorageException {

		return getStorageEngine().create(
			companyId, structureId, fields, serviceContext);
	}

	public static void deleteByClass(long classPK) throws StorageException {
		getStorageEngine().deleteByClass(classPK);
	}

	public static void deleteByStructure(long structureId)
		throws StorageException {

		getStorageEngine().deleteByStructure(structureId);
	}

	public static Fields getFields(long classPK) throws StorageException {
		return getStorageEngine().getFields(classPK);
	}

	public static Fields getFields(long classPK, List<String> fieldNames)
		throws StorageException {

		return getStorageEngine().getFields(classPK, fieldNames);
	}

	public static List<Fields> getFieldsList(
			long structureId, List<String> fieldNames)
		throws StorageException {

		return getStorageEngine().getFieldsList(
			structureId, fieldNames);
	}

	public static List<Fields> getFieldsList(
			long structureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException {

		return getStorageEngine().getFieldsList(
			structureId, fieldNames, orderByComparator);
	}

	public static List<Fields> getFieldsList(
			long structureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException {

		return getStorageEngine().getFieldsList(
			structureId, classPKs, fieldNames, orderByComparator);
	}

	public static List<Fields> getFieldsList(
			long structureId, long[] classPKs,
			OrderByComparator orderByComparator)
		throws StorageException {

		return getStorageEngine().getFieldsList(
			structureId, classPKs, orderByComparator);
	}

	public static Map<Long, Fields> getFieldsMap(
			long structureId, long[] classPKs)
		throws StorageException {

		return getStorageEngine().getFieldsMap(
			structureId, classPKs);
	}

	public static Map<Long, Fields> getFieldsMap(
			long structureId, long[] classPKs, List<String> fieldNames)
		throws StorageException {

		return getStorageEngine().getFieldsMap(
			structureId, classPKs, fieldNames);
	}

	public static StorageEngine getStorageEngine() {
		return _storageEngine;
	}

	public static List<Fields> query(
			long structureId, List<String> fieldNames, String whereClause,
			OrderByComparator orderByComparator)
		throws StorageException {

		return getStorageEngine().query(
			structureId, fieldNames, whereClause, orderByComparator);
	}

	public static int queryCount(long structureId, String whereClause)
		throws StorageException {

		return getStorageEngine().queryCount(structureId, whereClause);
	}

	public static void update(
			long classPK, Fields fields, ServiceContext serviceContext)
		throws StorageException {

		getStorageEngine().update(classPK, fields, serviceContext);
	}

	public static void update(
			long classPK, Fields fields, ServiceContext serviceContext,
			boolean merge)
		throws StorageException {

		getStorageEngine().update(classPK, fields, serviceContext, merge);
	}

	public void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	private static StorageEngine _storageEngine;

}