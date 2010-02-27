/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

/**
 * <a href="WikiPagePermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WikiPagePermission {

	public static void check(
			PermissionChecker permissionChecker, long resourcePrimKey,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, resourcePrimKey, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long nodeId, String title,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, nodeId, title, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, WikiPage page, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, page, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long resourcePrimKey,
			String actionId)
		throws PortalException, SystemException {

		WikiPage page = WikiPageLocalServiceUtil.getPage(resourcePrimKey);

		return contains(permissionChecker, page, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long nodeId, String title,
			String actionId)
		throws PortalException, SystemException {

		try {
			WikiPage page = WikiPageLocalServiceUtil.getPage(nodeId, title);

			return contains(permissionChecker, page, actionId);
		}
		catch (NoSuchPageException nspe) {
			return WikiNodePermission.contains(
				permissionChecker, nodeId, ActionKeys.ADD_PAGE);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, WikiPage page, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				page.getCompanyId(), WikiPage.class.getName(), page.getPageId(),
				page.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			page.getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey(), actionId);
	}

}