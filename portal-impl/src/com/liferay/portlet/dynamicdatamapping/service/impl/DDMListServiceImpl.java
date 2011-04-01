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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMList;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMListServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMListPermission;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission;

import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class DDMListServiceImpl extends DDMListServiceBaseImpl {

	public DDMList addList(
			long groupId, String listKey, boolean autoListKey,
			Map<Locale, String> nameMap, String description, long structureId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_LIST);

		return ddmListLocalService.addList(
			getUserId(), groupId, listKey, autoListKey, nameMap, description,
			structureId, serviceContext);
	}

	public void deleteList(long listId)
		throws PortalException, SystemException {

		DDMListPermission.check(
			getPermissionChecker(), listId, ActionKeys.DELETE);

		ddmListLocalService.deleteList(listId);
	}

	public void deleteList(long groupId, String listKey)
		throws PortalException, SystemException {

		DDMListPermission.check(
			getPermissionChecker(), groupId, listKey, ActionKeys.DELETE);

		ddmListLocalService.deleteList(groupId, listKey);
	}

	public DDMList getList(long listId)
		throws PortalException, SystemException {

		DDMListPermission.check(
			getPermissionChecker(), listId, ActionKeys.VIEW);

		return ddmListLocalService.getList(listId);
	}

	public DDMList getList(long groupId, String listKey)
		throws PortalException, SystemException {

		DDMListPermission.check(
			getPermissionChecker(), groupId, listKey, ActionKeys.VIEW);

		return ddmListLocalService.getList(groupId, listKey);
	}

	public DDMList updateList(
			long groupId, String listKey, Map<Locale, String> nameMap,
			String description, long structureId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMListPermission.check(
			getPermissionChecker(), groupId, listKey, ActionKeys.UPDATE);

		return ddmListLocalService.updateList(
			groupId, listKey, nameMap, description, structureId,
			serviceContext);
	}

}