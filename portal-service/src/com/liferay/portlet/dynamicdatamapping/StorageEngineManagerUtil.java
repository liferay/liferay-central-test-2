/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

/**
 * @author Rafael Praxedes
 */
public class StorageEngineManagerUtil {

	public static long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		return _storageEngineManager.create(
			companyId, ddmStructureId, ddmFormValues, serviceContext);
	}

	public static void deleteByClass(long classPK) throws StorageException {
		_storageEngineManager.deleteByClass(classPK);
	}

	public static void deleteByDDMStructure(long ddmStructureId)
		throws StorageException {

		_storageEngineManager.deleteByDDMStructure(ddmStructureId);
	}

	public static DDMFormValues getDDMFormValues(long classPK)
		throws StorageException {

		return _storageEngineManager.getDDMFormValues(classPK);
	}

	public static DDMFormValues getDDMFormValues(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException {

		return _storageEngineManager.getDDMFormValues(
			ddmStructureId, fieldNamespace, serviceContext);
	}

	public static void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		_storageEngineManager.update(classPK, ddmFormValues, serviceContext);
	}

	private static final StorageEngineManager _storageEngineManager =
		ProxyFactory.newServiceTrackedInstance(StorageEngineManager.class);

}