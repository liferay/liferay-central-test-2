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
import com.liferay.portlet.dynamicdatamapping.model.DDMListItem;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMListItemLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class DDMListItemLocalServiceImpl
	extends DDMListItemLocalServiceBaseImpl {

	public DDMListItem addListItem(
			long listId, Fields fields, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// List item

		DDMList list = ddmListPersistence.findByPrimaryKey(listId);

		long listItemId = counterLocalService.increment();

		DDMListItem listItem = ddmListItemPersistence.create(listItemId);

		DDMStructure structure = list.getStructure();

		listItem.setClassNameId(structure.getClassNameId());

		long classPK = StorageEngineUtil.create(
			list.getCompanyId(), list.getStructureId(), fields, serviceContext);

		listItem.setClassPK(classPK);

		listItem.setListId(listId);

		ddmListItemPersistence.update(listItem, false);

		// Structure link

		ddmStructureLinkLocalService.addStructureLink(
			PortalUtil.getClassNameId(DDMListItem.class), listItemId,
			list.getStructureId(), serviceContext);

		return listItem;
	}

	public void deleteListItem(DDMListItem listItem)
		throws PortalException, SystemException {

		// List item

		ddmListItemPersistence.remove(listItem);

		// Structure link

		ddmStructureLinkLocalService.deleteClassStructureLink(
			listItem.getListItemId());

		// Storage

		StorageEngineUtil.deleteByClass(listItem.getClassPK());
	}

	public void deleteListItem(long listItemId)
		throws PortalException, SystemException {

		DDMListItem listItem = ddmListItemPersistence.findByPrimaryKey(
			listItemId);

		deleteListItem(listItem);
	}

	public void deleteListItems(long listId)
		throws PortalException, SystemException {

		List<DDMListItem> listItems = ddmListItemPersistence.findByListId(
			listId);

		for (DDMListItem listItem : listItems) {
			deleteListItem(listItem);
		}
	}

	public DDMListItem getListItem(long listItemId)
		throws PortalException, SystemException {

		return ddmListItemPersistence.findByPrimaryKey(listItemId);
	}

	public List<DDMListItem> getListItems(long listId)
		throws SystemException {

		return ddmListItemPersistence.findByListId(listId);
	}

	public DDMListItem updateListItem(
			long listItemId, Fields fields, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// List item

		DDMListItem listItem = ddmListItemPersistence.findByPrimaryKey(
			listItemId);

		// Storage

		StorageEngineUtil.update(listItem.getClassPK(), fields, serviceContext);

		return listItem;
	}

}