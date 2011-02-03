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
import com.liferay.portlet.forms.model.FormStructure;
import com.liferay.portlet.forms.model.FormStructureLink;
import com.liferay.portlet.forms.service.base.FormStructureLinkLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class FormStructureLinkLocalServiceImpl
	extends FormStructureLinkLocalServiceBaseImpl {

	public FormStructureLink addFormStructureLink(
			String formStructureId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Link

		long formStructureLinkId = counterLocalService.increment();

		FormStructureLink formStructureLink =
			formStructureLinkPersistence.create(formStructureLinkId);

		formStructureLink.setFormStructureId(formStructureId);
		formStructureLink.setClassName(className);
		formStructureLink.setClassPK(classPK);

		formStructureLinkPersistence.update(formStructureLink, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addFormStructureLinkResources(
				formStructureLink, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFormStructureLinkResources(
				formStructureLink, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		return formStructureLink;
	}

	public void addFormStructureLinkResources(
			FormStructureLink formStructureLink,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		FormStructure structure = formStructurePersistence.findByPrimaryKey(
			formStructureLink.getFormStructureId());

		resourceLocalService.addResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), FormStructureLink.class.getName(),
			formStructureLink.getFormStructureLinkId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFormStructureLinkResources(
			FormStructureLink formStructureLink, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		FormStructure structure = formStructurePersistence.findByPrimaryKey(
			formStructureLink.getFormStructureId());

		resourceLocalService.addModelResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), FormStructureLink.class.getName(),
			formStructureLink.getFormStructureLinkId(), communityPermissions,
			guestPermissions);
	}

	public void deleteFormStructureLink(FormStructureLink formStructureLink)
		throws PortalException, SystemException {

		deleteFormStructureLink(formStructureLink.getFormStructureLinkId());
	}

	public void deleteFormStructureLink(long formStructureLinkId)
		throws PortalException, SystemException {

		formStructureLinkPersistence.remove(formStructureLinkId);
	}

	public void deleteFormStructureLink(
			String formStructureId, String className, long classPK)
		throws PortalException, SystemException {

		formStructureLinkPersistence.remove(
			getFormStructureLink(formStructureId, className, classPK));
	}

	public FormStructureLink getFormStructureLink(long formStructureLinkId)
		throws PortalException, SystemException {

		return formStructureLinkPersistence.findByPrimaryKey(
			formStructureLinkId);
	}

	public FormStructureLink getFormStructureLink(
			String formStructureId, String className, long classPK)
		throws PortalException, SystemException {

		return formStructureLinkPersistence.findByF_C_C(
			formStructureId, className, classPK);
	}

	public List<FormStructureLink> getFormStructureLinks(
			String formStructureId, int start, int end)
		throws SystemException {

		return formStructureLinkPersistence.findByFormStructureId(
			formStructureId, start, end);
	}

	public FormStructureLink updateFormStructureLink(
			long formStructureLinkId, String formStructureId, long groupId,
			 String className, long classPK)
		throws PortalException, SystemException {

		FormStructureLink formStructureLink =
			formStructureLinkPersistence.findByPrimaryKey(formStructureLinkId);

		formStructureLink.setFormStructureId(formStructureId);
		formStructureLink.setClassName(className);
		formStructureLink.setClassPK(classPK);

		formStructureLinkPersistence.update(formStructureLink, false);

		return formStructureLink;
	}

}