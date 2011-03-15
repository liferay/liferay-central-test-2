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
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureEntryLinkLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class DDMStructureEntryLinkLocalServiceImpl
	extends DDMStructureEntryLinkLocalServiceBaseImpl {

	public DDMStructureEntryLink addStructureEntryLink(
			String structureId, String className, long classPK,
			ServiceContext serviceContext)
		throws SystemException {

		long structureEntryLinkId = counterLocalService.increment();

		DDMStructureEntryLink structureEntryLink =
			ddmStructureEntryLinkPersistence.create(structureEntryLinkId);

		structureEntryLink.setStructureId(structureId);
		structureEntryLink.setClassName(className);
		structureEntryLink.setClassPK(classPK);

		return ddmStructureEntryLinkPersistence.update(
			structureEntryLink, false);
	}

	public void deleteStructureEntryLink(
			DDMStructureEntryLink structureEntryLink)
		throws SystemException {

		ddmStructureEntryLinkPersistence.remove(structureEntryLink);
	}

	public void deleteStructureEntryLink(long structureEntryLinkId)
		throws PortalException, SystemException {

		DDMStructureEntryLink structureEntryLink =
			ddmStructureEntryLinkPersistence.findByPrimaryKey(
				structureEntryLinkId);

		deleteStructureEntryLink(structureEntryLink);
	}

	public void deleteStructureEntryLink(
			String structureId, String className, long classPK)
		throws PortalException, SystemException {

		DDMStructureEntryLink structureEntryLink =
			ddmStructureEntryLinkPersistence.findByS_C_C(
				structureId, className, classPK);

		deleteStructureEntryLink(structureEntryLink);
	}

	public DDMStructureEntryLink getStructureEntryLink(
			long structureEntryLinkId)
		throws PortalException, SystemException {

		return ddmStructureEntryLinkPersistence.findByPrimaryKey(
			structureEntryLinkId);
	}

	public DDMStructureEntryLink getStructureEntryLink(
			String structureId, String className, long classPK)
		throws PortalException, SystemException {

		return ddmStructureEntryLinkPersistence.findByS_C_C(
			structureId, className, classPK);
	}

	public List<DDMStructureEntryLink> getStructureEntryLinks(
			String structureId, int start, int end)
		throws SystemException {

		return ddmStructureEntryLinkPersistence.findByStructureId(
			structureId, start, end);
	}

	public DDMStructureEntryLink updateStructureEntryLink(
			long structureEntryLinkId, String structureId, long groupId,
			String className, long classPK)
		throws PortalException, SystemException {

		DDMStructureEntryLink structureEntryLink =
			ddmStructureEntryLinkPersistence.findByPrimaryKey(
				structureEntryLinkId);

		structureEntryLink.setStructureId(structureId);
		structureEntryLink.setClassName(className);
		structureEntryLink.setClassPK(classPK);

		return ddmStructureEntryLinkPersistence.update(
			structureEntryLink, false);
	}

}