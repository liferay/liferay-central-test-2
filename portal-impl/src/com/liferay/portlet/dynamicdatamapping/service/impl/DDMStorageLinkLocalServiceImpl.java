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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStorageLinkLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDMStorageLinkLocalServiceImpl
	extends DDMStorageLinkLocalServiceBaseImpl {

	public DDMStorageLink addStorageLink(
			String type, String className, long classPK,
			ServiceContext serviceContext)
		throws SystemException {

		long storageLinkId = counterLocalService.increment();

		DDMStorageLink storageLink =
			ddmStorageLinkPersistence.create(storageLinkId);

		storageLink.setType(type);
		storageLink.setClassName(className);
		storageLink.setClassPK(classPK);

		ddmStorageLinkPersistence.update(storageLink, false);

		return storageLink;
	}

	public void deleteStorageLink(DDMStorageLink storageLink)
		throws SystemException {

		ddmStorageLinkPersistence.remove(storageLink);
	}

	public void deleteStorageLink(long storageLinkId)
		throws PortalException, SystemException {

		DDMStorageLink storageLink =
			ddmStorageLinkPersistence.findByPrimaryKey(storageLinkId);

		deleteStorageLink(storageLink);
	}

	public void deleteStorageLink(String className, long classPK)
		throws PortalException, SystemException {

		DDMStorageLink storageLink =
			ddmStorageLinkPersistence.findByC_C(className, classPK);

		deleteStorageLink(storageLink);
	}

	public DDMStorageLink getStorageLink(long storageLinkId)
		throws PortalException, SystemException {

		return ddmStorageLinkPersistence.findByPrimaryKey(storageLinkId);
	}

	public DDMStorageLink getStorageLink(String className, long classPK)
		throws PortalException, SystemException {

		return ddmStorageLinkPersistence.findByC_C(className, classPK);
	}

	public DDMStorageLink updateStorageLink(
			long storageLinkId, String type, long groupId, String className,
			long classPK)
		throws PortalException, SystemException {

		DDMStorageLink storageLink =
			ddmStorageLinkPersistence.findByPrimaryKey(storageLinkId);

		storageLink.setType(type);
		storageLink.setClassName(className);
		storageLink.setClassPK(classPK);

		ddmStorageLinkPersistence.update(storageLink, false);

		return storageLink;
	}


}