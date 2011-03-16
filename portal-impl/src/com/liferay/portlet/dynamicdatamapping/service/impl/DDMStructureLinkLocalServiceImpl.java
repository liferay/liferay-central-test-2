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
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureLinkLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class DDMStructureLinkLocalServiceImpl
	extends DDMStructureLinkLocalServiceBaseImpl {

	public DDMStructureLink addStructureLink(
			String structureKey, String className, long classPK,
			ServiceContext serviceContext)
		throws SystemException {

		long structureLinkId = counterLocalService.increment();

		DDMStructureLink structureLink =
			ddmStructureLinkPersistence.create(structureLinkId);

		structureLink.setStructureKey(structureKey);
		structureLink.setClassName(className);
		structureLink.setClassPK(classPK);

		ddmStructureLinkPersistence.update(structureLink, false);

		return structureLink;
	}

	public void deleteStructureLink(DDMStructureLink structureLink)
		throws SystemException {

		ddmStructureLinkPersistence.remove(structureLink);
	}

	public void deleteStructureLink(long structureLinkId)
		throws PortalException, SystemException {

		DDMStructureLink structureLink =
			ddmStructureLinkPersistence.findByPrimaryKey(structureLinkId);

		deleteStructureLink(structureLink);
	}

	public void deleteStructureLink(
			String structureKey, String className, long classPK)
		throws PortalException, SystemException {

		DDMStructureLink structureLink =
			ddmStructureLinkPersistence.findByS_C_C(
				structureKey, className, classPK);

		deleteStructureLink(structureLink);
	}

	public DDMStructureLink getStructureLink(
			long structureLinkId)
		throws PortalException, SystemException {

		return ddmStructureLinkPersistence.findByPrimaryKey(structureLinkId);
	}

	public DDMStructureLink getStructureLink(
			String structureKey, String className, long classPK)
		throws PortalException, SystemException {

		return ddmStructureLinkPersistence.findByS_C_C(
			structureKey, className, classPK);
	}

	public List<DDMStructureLink> getStructureLinks(
			String structureKey, int start, int end)
		throws SystemException {

		return ddmStructureLinkPersistence.findByStructureKey(
			structureKey, start, end);
	}

	public DDMStructureLink updateStructureLink(
			long structureLinkId, String structureKey, long groupId,
			String className, long classPK)
		throws PortalException, SystemException {

		DDMStructureLink structureLink =
			ddmStructureLinkPersistence.findByPrimaryKey(structureLinkId);

		structureLink.setStructureKey(structureKey);
		structureLink.setClassName(className);
		structureLink.setClassPK(classPK);

		ddmStructureLinkPersistence.update(structureLink, false);

		return structureLink;
	}

}