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
import com.liferay.portlet.forms.model.FormsStructureEntry;
import com.liferay.portlet.forms.service.base.FormsStructureEntryServiceBaseImpl;
import com.liferay.portlet.forms.service.permission.FormsPermission;
import com.liferay.portlet.forms.service.permission.FormsStructureEntryPermission;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class FormsStructureEntryServiceImpl
	extends FormsStructureEntryServiceBaseImpl {

	public FormsStructureEntry addStructureEntry(
			long groupId, String structureId, boolean autoStrucureId,
			String name, String description, String xsd,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		FormsPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_STRUCTURE_ENTRY);

		return formsStructureEntryLocalService.addStructureEntry(
			getUserId(), groupId, structureId, autoStrucureId, name,
			description, xsd, serviceContext);
	}

	public void deleteStructureEntry(long groupId, String structureId)
		throws PortalException, SystemException {

		FormsStructureEntryPermission.check(
			getPermissionChecker(), groupId, structureId, ActionKeys.DELETE);

		formsStructureEntryLocalService.deleteStructureEntry(
			groupId, structureId);
	}

	public FormsStructureEntry getStructureEntry(
			long groupId, String structureId)
		throws PortalException, SystemException {

		FormsStructureEntryPermission.check(
			getPermissionChecker(), groupId, structureId, ActionKeys.VIEW);

		return formsStructureEntryPersistence.findByG_S(groupId, structureId);
	}

	public FormsStructureEntry updateStructureEntry(
			long groupId, String structureId, String name, String description,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		FormsStructureEntryPermission.check(
			getPermissionChecker(), groupId, structureId, ActionKeys.UPDATE);

		return formsStructureEntryLocalService.updateStructureEntry(
			groupId, structureId, name, description, xsd, serviceContext);
	}

}