/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.permission.RolePermissionUtil;
import com.liferay.portal.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.announcements.model.AnnouncementEntry;
import com.liferay.portlet.announcements.service.base.AnnouncementEntryServiceBaseImpl;
import com.liferay.portlet.announcements.service.permission.AnnouncementEntryPermission;

/**
 * <a href="AnnouncementEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementEntryServiceImpl
	extends AnnouncementEntryServiceBaseImpl {

	public AnnouncementEntry addEntry(
			long userId, long plid, long classNameId, long classPK, String title,
			String content, String url, String type, int displayMonth,
			int displayDay, int displayYear, int expirationMonth,
			int expirationDay, int expirationYear, int priority, boolean alert)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), plid, PortletKeys.ANNOUNCEMENTS,
			ActionKeys.ADD_ENTRY);

		if (classNameId == 0) {
			if (!getPermissionChecker().isOmniadmin()) {
				throw new PrincipalException();
			}
		}
		else {
			String className = PortalUtil.getClassName(classNameId);

			if (className.equals(Role.class.getName()) &&
				!RolePermissionUtil.contains(
					getPermissionChecker(), classPK, ActionKeys.ASSIGN_MEMBERS)) {
				throw new PrincipalException();
			}
			else if (className.equals(UserGroup.class.getName()) &&
				!UserGroupPermissionUtil.contains(
					getPermissionChecker(), classPK, ActionKeys.ASSIGN_MEMBERS)) {
				throw new PrincipalException();
			}
			else if (className.equals(Group.class.getName()) &&
				!GroupPermissionUtil.contains(
					getPermissionChecker(), classPK, ActionKeys.ASSIGN_MEMBERS)) {
				throw new PrincipalException();
			}
			else if (className.equals(Organization.class.getName()) &&
				!OrganizationPermissionUtil.contains(
					getPermissionChecker(), classPK, ActionKeys.ASSIGN_MEMBERS)) {
				throw new PrincipalException();
			}
		}

		return announcementEntryLocalService.addEntry(
			userId, classNameId, classPK, title, content, url, type,
			displayMonth, displayDay, displayYear, expirationMonth,
			expirationDay, expirationYear, priority, alert);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		AnnouncementEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		announcementEntryLocalService.deleteEntry(entryId);
	}

	public AnnouncementEntry updateEntry(
			long entryId, String title, String content, String url,
			String type, int displayMonth, int displayDay, int displayYear,
			int expirationMonth, int expirationDay, int expirationYear,
			int priority)
		throws PortalException, SystemException {

		AnnouncementEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return announcementEntryLocalService.updateEntry(
			entryId, title, content, url, type, displayMonth, displayDay,
			displayYear, expirationMonth, expirationDay, expirationYear,
			priority);
	}

}