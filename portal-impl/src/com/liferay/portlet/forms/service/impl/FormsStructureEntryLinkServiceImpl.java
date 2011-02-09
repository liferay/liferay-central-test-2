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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.forms.model.FormsStructureEntryLink;
import com.liferay.portlet.forms.service.base.FormsStructureEntryLinkServiceBaseImpl;
import com.liferay.portlet.forms.service.permission.FormsPermission;
import com.liferay.portlet.forms.service.permission.FormsStructureEntryPermission;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class FormsStructureEntryLinkServiceImpl
	extends FormsStructureEntryLinkServiceBaseImpl {

	public FormsStructureEntryLink addStructureEntryLink(
			String structureId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		FormsPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_STRUCTURE_ENTRY);

		return formsStructureEntryLinkLocalService.addStructureEntryLink(
			structureId, className, classPK, serviceContext);
	}

	public void deleteStructureEntryLink(
			long groupId, String structureId, long structureEntryLinkId)
		throws PortalException, SystemException {

		FormsStructureEntryPermission.check(
			getPermissionChecker(), groupId, structureId, ActionKeys.DELETE);

		formsStructureEntryLinkLocalService.deleteStructureEntryLink(
			structureEntryLinkId);
	}

	public FormsStructureEntryLink getStructureEntryLink(
			long groupId, String structureId, String className, long classPK)
		throws PortalException, SystemException {

		FormsStructureEntryPermission.check(
			getPermissionChecker(), groupId, structureId, ActionKeys.VIEW);

		return formsStructureEntryLinkLocalService.getStructureEntryLink(
			structureId, className, classPK);
	}

	public FormsStructureEntryLink updateStructureEntryLink(
			long structureEntryLinkId, String structureId, long groupId,
			String className, long classPK)
		throws PortalException, SystemException {

		FormsStructureEntryPermission.check(
			getPermissionChecker(), groupId, structureId, ActionKeys.UPDATE);

		return formsStructureEntryLinkLocalService.updateStructureEntryLink(
			structureEntryLinkId, structureId, groupId, className, classPK);
	}

}