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

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDMStorageLinkLocalServiceImpl
	extends DDMStorageLinkLocalServiceBaseImpl {

	public DDMStorageLink addStorageLink(
			long classNameId, long classPK, long structureId, String type,
			ServiceContext serviceContext)
		throws SystemException {

		long storageLinkId = counterLocalService.increment();

		DDMStorageLink storageLink = ddmStorageLinkPersistence.create(
			storageLinkId);

		storageLink.setClassNameId(classNameId);
		storageLink.setClassPK(classPK);
		storageLink.setStructureId(structureId);
		storageLink.setType(type);

		ddmStorageLinkPersistence.update(storageLink, false);

		return storageLink;
	}

	public void deleteStorageLink(DDMStorageLink storageLink)
		throws SystemException {

		ddmStorageLinkPersistence.remove(storageLink);
	}

	public void deleteStorageLink(long storageLinkId)
		throws PortalException, SystemException {

		DDMStorageLink storageLink = ddmStorageLinkPersistence.findByPrimaryKey(
			storageLinkId);

		deleteStorageLink(storageLink);
	}

	public void deleteStorageLink(long classNameId, long classPK)
		throws PortalException, SystemException {

		DDMStorageLink storageLink = ddmStorageLinkPersistence.findByC_C(
			classNameId, classPK);

		deleteStorageLink(storageLink);
	}

	public void deleteStorageLinks(long structureId) throws SystemException {
		List<DDMStorageLink> storageLinks =
			ddmStorageLinkPersistence.findByStructureId(structureId);

		for (DDMStorageLink storageLink : storageLinks) {
			deleteStorageLink(storageLink);
		}
	}

	public DDMStorageLink getStorageLink(long storageLinkId)
		throws PortalException, SystemException {

		return ddmStorageLinkPersistence.findByPrimaryKey(storageLinkId);
	}

	public DDMStorageLink getStorageLink(long classNameId, long classPK)
		throws PortalException, SystemException {

		return ddmStorageLinkPersistence.findByC_C(classNameId, classPK);
	}

	public List<DDMStorageLink> getStorageLinks(long structureId)
		throws SystemException {

		return ddmStorageLinkPersistence.findByStructureId(structureId);
	}

	public DDMStorageLink updateStorageLink(
			long storageLinkId, long classNameId, long classPK, String type)
		throws PortalException, SystemException {

		DDMStorageLink storageLink = ddmStorageLinkPersistence.findByPrimaryKey(
			storageLinkId);

		storageLink.setClassNameId(classNameId);
		storageLink.setClassPK(classPK);
		storageLink.setType(type);

		ddmStorageLinkPersistence.update(storageLink, false);

		return storageLink;
	}

}