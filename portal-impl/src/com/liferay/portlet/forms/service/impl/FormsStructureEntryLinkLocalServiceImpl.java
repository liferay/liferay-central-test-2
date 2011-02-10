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

package com.liferay.portlet.forms.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.forms.model.FormsStructureEntryLink;
import com.liferay.portlet.forms.service.base.FormsStructureEntryLinkLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class FormsStructureEntryLinkLocalServiceImpl
	extends FormsStructureEntryLinkLocalServiceBaseImpl {

	public FormsStructureEntryLink addStructureEntryLink(
			String structureId, String className, long classPK,
			ServiceContext serviceContext)
		throws SystemException {

		long structureEntryLinkId = counterLocalService.increment();

		FormsStructureEntryLink structureEntryLink =
			formsStructureEntryLinkPersistence.create(structureEntryLinkId);

		structureEntryLink.setStructureId(structureId);
		structureEntryLink.setClassName(className);
		structureEntryLink.setClassPK(classPK);

		return formsStructureEntryLinkPersistence.update(
			structureEntryLink, false);
	}

	public void deleteStructureEntryLink(
			FormsStructureEntryLink structureEntryLink)
		throws SystemException {

		formsStructureEntryLinkPersistence.remove(structureEntryLink);
	}

	public void deleteStructureEntryLink(long structureEntryLinkId)
		throws PortalException, SystemException {

		FormsStructureEntryLink structureEntryLink =
			formsStructureEntryLinkPersistence.findByPrimaryKey(
				structureEntryLinkId);

		deleteStructureEntryLink(structureEntryLink);
	}

	public void deleteStructureEntryLink(
			String structureId, String className, long classPK)
		throws PortalException, SystemException {

		FormsStructureEntryLink structureEntryLink =
			formsStructureEntryLinkPersistence.findByS_C_C(
				structureId, className, classPK);

		deleteStructureEntryLink(structureEntryLink);
	}

	public FormsStructureEntryLink getStructureEntryLink(
			long structureEntryLinkId)
		throws PortalException, SystemException {

		return formsStructureEntryLinkPersistence.findByPrimaryKey(
			structureEntryLinkId);
	}

	public FormsStructureEntryLink getStructureEntryLink(
			String structureId, String className, long classPK)
		throws PortalException, SystemException {

		return formsStructureEntryLinkPersistence.findByS_C_C(
			structureId, className, classPK);
	}

	public List<FormsStructureEntryLink> getStructureEntryLinks(
			String structureId, int start, int end)
		throws SystemException {

		return formsStructureEntryLinkPersistence.findByStructureId(
			structureId, start, end);
	}

	public FormsStructureEntryLink updateStructureEntryLink(
			long structureEntryLinkId, String structureId, long groupId,
			String className, long classPK)
		throws PortalException, SystemException {

		FormsStructureEntryLink structureEntryLink =
			formsStructureEntryLinkPersistence.findByPrimaryKey(
				structureEntryLinkId);

		structureEntryLink.setStructureId(structureId);
		structureEntryLink.setClassName(className);
		structureEntryLink.setClassPK(classPK);

		return formsStructureEntryLinkPersistence.update(
			structureEntryLink, false);
	}

}