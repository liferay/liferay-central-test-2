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

package com.liferay.portal.kernel.dynamicdatamapping.storage;

import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

/**
 * @author Rafael Praxedes
 */
public class DummyStorageEngineManagerImpl implements StorageEngineManager {

	@Override
	public long create(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {

		return 0;
	}

	@Override
	public void deleteByClass(long classPK) throws StorageException {
	}

	@Override
	public void deleteByDDMStructure(long ddmStructureId)
		throws StorageException {
	}

	@Override
	public DDMFormValues getDDMFormValues(long classPK)
		throws StorageException {

		return null;
	}

	@Override
	public String getStorageType() {
		return null;
	}

	@Override
	public void update(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws StorageException {
	}

}