/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.permission;

/**
 * <a href="PortletPermission_IW.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletPermission_IW {
	public static PortletPermission_IW getInstance() {
		return _instance;
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		java.lang.String portletId, java.lang.String actionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		PortletPermission.check(permissionChecker, portletId, actionId);
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long plid, java.lang.String portletId, java.lang.String actionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		PortletPermission.check(permissionChecker, plid, portletId, actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		java.lang.String portletId, java.lang.String actionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PortletPermission.contains(permissionChecker, portletId, actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long plid, java.lang.String portletId, java.lang.String actionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PortletPermission.contains(permissionChecker, plid, portletId,
			actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long plid, com.liferay.portal.model.Portlet portlet,
		java.lang.String actionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PortletPermission.contains(permissionChecker, plid, portlet,
			actionId);
	}

	public java.lang.String getPrimaryKey(long plid, java.lang.String portletId) {
		return PortletPermission.getPrimaryKey(plid, portletId);
	}

	private PortletPermission_IW() {
	}

	private static PortletPermission_IW _instance = new PortletPermission_IW();
}