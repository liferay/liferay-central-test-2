/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.message.boards.service.permission;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.permission.PermissionUpdateHandler;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.MBCategoryLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {
		"model.class.name=com.liferay.portlet.messageboards.model.MBCategory"
	},
	service = PermissionUpdateHandler.class
)
public class MBCategoryPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		MBCategory mbCategory = _mbCategoryLocalService.fetchMBCategory(
			GetterUtil.getLong(primKey));

		if (mbCategory == null) {
			return;
		}

		mbCategory.setModifiedDate(new Date());

		_mbCategoryLocalService.updateMBCategory(mbCategory);
	}

	@Reference
	protected void setMBCategoryLocalService(
		MBCategoryLocalService mbCategoryLocalService) {

		_mbCategoryLocalService = mbCategoryLocalService;
	}

	private MBCategoryLocalService _mbCategoryLocalService;

}