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
 * @author Eduardo Lundgren
 */
public class FormsStructureEntryLinkLocalServiceImpl
	extends FormsStructureEntryLinkLocalServiceBaseImpl {

	public FormsStructureEntryLink addEntry(
			String formStructureEntryId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Link

		long entryId = counterLocalService.increment();

		FormsStructureEntryLink entry =
			formsStructureEntryLinkPersistence.create(entryId);

		entry.setFormStructureId(formStructureEntryId);
		entry.setClassName(className);
		entry.setClassPK(classPK);

		formsStructureEntryLinkPersistence.update(entry, false);

		return entry;
	}

	public void deleteEntry(FormsStructureEntryLink entry)
		throws PortalException, SystemException {

		deleteEntry(entry.getFormStructureLinkId());
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		formsStructureEntryLinkPersistence.remove(entryId);
	}

	public void deleteEntry(
			String formStructureEntryId, String className, long classPK)
		throws PortalException, SystemException {

		formsStructureEntryLinkPersistence.remove(
			getEntry(formStructureEntryId, className, classPK));
	}

	public FormsStructureEntryLink getEntry(long entryId)
		throws PortalException, SystemException {

		return formsStructureEntryLinkPersistence.findByPrimaryKey(
			entryId);
	}

	public FormsStructureEntryLink getEntry(
			String formStructureEntryId, String className, long classPK)
		throws PortalException, SystemException {

		return formsStructureEntryLinkPersistence.findByF_C_C(
			formStructureEntryId, className, classPK);
	}

	public List<FormsStructureEntryLink> getEntries(
			String formStructureEntryId, int start, int end)
		throws SystemException {

		return formsStructureEntryLinkPersistence.findByFormStructureId(
			formStructureEntryId, start, end);
	}

	public FormsStructureEntryLink updateEntry(
			long entryId, String formStructureEntryId, long groupId,
			String className, long classPK)
		throws PortalException, SystemException {

		FormsStructureEntryLink entry =
			formsStructureEntryLinkPersistence.findByPrimaryKey(entryId);

		entry.setFormStructureId(formStructureEntryId);
		entry.setClassName(className);
		entry.setClassPK(classPK);

		formsStructureEntryLinkPersistence.update(entry, false);

		return entry;
	}

}