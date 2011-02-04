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
import com.liferay.portlet.forms.model.FormStructure;
import com.liferay.portlet.forms.service.base.FormStructureServiceBaseImpl;
import com.liferay.portlet.forms.service.permission.FormPermission;
import com.liferay.portlet.forms.service.permission.FormStructurePermission;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Eduardo Lundgren
 */
public class FormStructureServiceImpl extends FormStructureServiceBaseImpl {

	public FormStructure addFormStructure(
			long groupId, String formStructureId, boolean autoFormStructureId,
			String name, String description, String xsd,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		FormPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_STRUCTURE);

		return formStructureLocalService.addFormStructure(
			getUserId(), groupId, formStructureId, autoFormStructureId, name,
			description, xsd, serviceContext);
	}

	public void deleteFormStructure(long groupId, String formStructureId)
		throws PortalException, SystemException {

		FormStructurePermission.check(
			getPermissionChecker(), groupId, formStructureId,
			ActionKeys.DELETE);

		formStructureLocalService.deleteFormStructure(
			groupId, formStructureId);
	}

	public FormStructure fetchByG_F(long groupId, String formStructureId)
		throws PortalException, SystemException {

		return formStructureLocalService.fetchByG_F(groupId, formStructureId);
	}

	public FormStructure getFormStructure(long groupId, String formStructureId)
		throws PortalException, SystemException {

		FormStructurePermission.check(
			getPermissionChecker(), groupId, formStructureId, ActionKeys.VIEW);

		return formStructurePersistence.findByG_F(groupId, formStructureId);
	}

	public FormStructure updateFormStructure(
			long groupId, String formStructureId, String name,
			String description, String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		FormStructurePermission.check(
			getPermissionChecker(), groupId, formStructureId,
			ActionKeys.UPDATE);

		return formStructureLocalService.updateFormStructure(
			groupId, formStructureId, name, description, xsd, serviceContext);
	}

}