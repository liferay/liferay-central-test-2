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

package com.liferay.message.boards.comment.service;

import com.liferay.portal.kernel.comment.BaseDiscussionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public class MBDiscussionPermissionImpl extends BaseDiscussionPermission {

	public MBDiscussionPermissionImpl(PermissionChecker permissionChecker) {
		_permissionChecker = permissionChecker;
	}

	@Override
	public boolean hasAddPermission(
		long companyId, long groupId, String className, long classPK) {

		return MBDiscussionPermission.contains(
			_permissionChecker, companyId, groupId, className, classPK,
			ActionKeys.ADD_DISCUSSION);
	}

	@Override
	public boolean hasDeletePermission(long commentId) throws PortalException {
		return MBDiscussionPermission.contains(
			_permissionChecker, commentId, ActionKeys.DELETE_DISCUSSION);
	}

	@Override
	public boolean hasUpdatePermission(long commentId) throws PortalException {
		return MBDiscussionPermission.contains(
			_permissionChecker, commentId, ActionKeys.UPDATE_DISCUSSION);
	}

	@Override
	public boolean hasViewPermission(
		long companyId, long groupId, String className, long classPK) {

		return MBDiscussionPermission.contains(
			_permissionChecker, companyId, groupId, className, classPK,
			ActionKeys.VIEW);
	}

	private final PermissionChecker _permissionChecker;

}