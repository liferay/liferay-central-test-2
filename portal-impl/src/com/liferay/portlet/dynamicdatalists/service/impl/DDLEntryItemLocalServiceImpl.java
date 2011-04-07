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

package com.liferay.portlet.dynamicdatalists.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLEntry;
import com.liferay.portlet.dynamicdatalists.model.DDLEntryItem;
import com.liferay.portlet.dynamicdatalists.service.base.DDLEntryItemLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class DDLEntryItemLocalServiceImpl
	extends DDLEntryItemLocalServiceBaseImpl {

	public DDLEntryItem addEntryItem(
			long entryId, Fields fields, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry item

		DDLEntry entry = ddlEntryPersistence.findByPrimaryKey(entryId);

		long entryItemId = counterLocalService.increment();

		DDLEntryItem entryItem = ddlEntryItemPersistence.create(entryItemId);

		DDMStructure ddmStructure = entry.getDDMStructure();

		entryItem.setClassNameId(ddmStructure.getClassNameId());

		long classPK = StorageEngineUtil.create(
			entry.getCompanyId(), entry.getDDMStructureId(), fields,
			serviceContext);

		entryItem.setClassPK(classPK);

		entryItem.setEntryId(entryId);

		ddlEntryItemPersistence.update(entryItem, false);

		// Dynamic data mapping structure link

		long classNameId = PortalUtil.getClassNameId(DDLEntryItem.class);

		ddmStructureLinkLocalService.addStructureLink(
			classNameId, entryItemId, entry.getDDMStructureId(),
			serviceContext);

		return entryItem;
	}

	public void deleteEntryItem(DDLEntryItem entryItem)
		throws PortalException, SystemException {

		// Entry item

		ddlEntryItemPersistence.remove(entryItem);

		// Dynamic data mapping storage

		StorageEngineUtil.deleteByClass(entryItem.getClassPK());

		// Dynamic data mapping structure link

		ddmStructureLinkLocalService.deleteClassStructureLink(
			entryItem.getEntryItemId());
	}

	public void deleteEntryItem(long entryItemId)
		throws PortalException, SystemException {

		DDLEntryItem entryItem = ddlEntryItemPersistence.findByPrimaryKey(
			entryItemId);

		deleteEntryItem(entryItem);
	}

	public void deleteEntryItems(long entryId)
		throws PortalException, SystemException {

		List<DDLEntryItem> entryItems = ddlEntryItemPersistence.findByEntryId(
			entryId);

		for (DDLEntryItem entryItem : entryItems) {
			deleteEntryItem(entryItem);
		}
	}

	public DDLEntryItem getEntryItem(long entryItemId)
		throws PortalException, SystemException {

		return ddlEntryItemPersistence.findByPrimaryKey(entryItemId);
	}

	public List<DDLEntryItem> getEntryItems(long entryId)
		throws SystemException {

		return ddlEntryItemPersistence.findByEntryId(entryId);
	}

	public DDLEntryItem updateEntryItem(
			long entryItemId, Fields fields, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry item

		DDLEntryItem entryItem = ddlEntryItemPersistence.findByPrimaryKey(
			entryItemId);

		// Dynamic data mapping storage

		StorageEngineUtil.update(
			entryItem.getClassPK(), fields, serviceContext);

		return entryItem;
	}

}