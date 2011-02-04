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
 * @author Bruno Farache
 * @author Eduardo Lundgren
 */
public class FormsStructureEntryServiceImpl
	extends FormsStructureEntryServiceBaseImpl {

	public FormsStructureEntry addEntry(
			long groupId, String formStrucureId, boolean autoFormStrucureId,
			String name, String description, String xsd,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		FormsPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_STRUCTURE);

		return formsStructureEntryLocalService.addEntry(
			getUserId(), groupId, formStrucureId, autoFormStrucureId, name,
			description, xsd, serviceContext);
	}

	public void deleteEntry(long groupId, String formStructureId)
		throws PortalException, SystemException {

		FormsStructureEntryPermission.check(
			getPermissionChecker(), groupId, formStructureId,
			ActionKeys.DELETE);

		formsStructureEntryLocalService.deleteEntry(
			groupId, formStructureId);
	}

	public FormsStructureEntry fetchByG_F(long groupId, String formStructureId)
		throws PortalException, SystemException {

		return formsStructureEntryLocalService.fetchByG_F(
			groupId, formStructureId);
	}

	public FormsStructureEntry getEntry(long groupId, String formStructureId)
		throws PortalException, SystemException {

		FormsStructureEntryPermission.check(
			getPermissionChecker(), groupId, formStructureId, ActionKeys.VIEW);

		return formsStructureEntryPersistence.findByG_F(
			groupId, formStructureId);
	}

	public FormsStructureEntry updateEntry(
			long groupId, String formStructureId, String name,
			String description, String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		FormsStructureEntryPermission.check(
			getPermissionChecker(), groupId, formStructureId,
			ActionKeys.UPDATE);

		return formsStructureEntryLocalService.updateEntry(
			groupId, formStructureId, name, description, xsd, serviceContext);
	}

}