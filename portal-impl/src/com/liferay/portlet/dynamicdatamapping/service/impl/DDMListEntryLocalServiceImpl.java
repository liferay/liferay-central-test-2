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
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMList;
import com.liferay.portlet.dynamicdatamapping.model.DDMListEntry;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMListEntryLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class DDMListEntryLocalServiceImpl
	extends DDMListEntryLocalServiceBaseImpl {

	public DDMListEntry addListEntry(
			long listId, Fields fields, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMList list = ddmListPersistence.findByPrimaryKey(listId);

		long listEntryId = counterLocalService.increment();

		DDMListEntry listEntry = ddmListEntryPersistence.create(listEntryId);

		listEntry.setListId(listId);

		long classPK = StorageEngineUtil.create(
			list.getCompanyId(), list.getStructureId(), fields, serviceContext);

		listEntry.setClassPK(classPK);

		ddmListEntryPersistence.update(listEntry, false);

		// Structure Link

		ddmStructureLinkLocalService.addStructureLink(
			PortalUtil.getClassNameId(DDMListEntry.class), listEntryId,
			list.getStructureId(), serviceContext);

		return listEntry;
	}

	public void deleteListEntry(DDMListEntry listEntry)
		throws PortalException, SystemException {

		ddmListEntryPersistence.remove(listEntry);

		// Structure Link

		ddmStructureLinkLocalService.deleteClassStructureLink(
			listEntry.getListEntryId());

		// Storage

		StorageEngineUtil.deleteByClass(listEntry.getClassPK());
	}

	public void deleteListEntry(long listEntryId)
		throws PortalException, SystemException {

		DDMListEntry listEntry = ddmListEntryPersistence.findByPrimaryKey(
			listEntryId);

		deleteListEntry(listEntry);
	}

	public void deleteListEntries(long listId)
		throws PortalException, SystemException {

		for (DDMListEntry listEntry :
				ddmListEntryPersistence.findByListId(listId)) {

			deleteListEntry(listEntry);
		}
	}

	public DDMListEntry getListEntry(long listEntryId)
		throws PortalException, SystemException {

		return ddmListEntryPersistence.findByPrimaryKey(listEntryId);
	}

	public List<DDMListEntry> getListEntries(long listId)
		throws SystemException {

		return ddmListEntryPersistence.findByListId(listId);
	}

	public DDMListEntry updateListEntry(
			long listEntryId, Fields fields, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMListEntry listEntry = ddmListEntryPersistence.findByPrimaryKey(
			listEntryId);

		StorageEngineUtil.update(
			listEntry.getClassPK(), fields, serviceContext);

		return listEntry;
	}

}