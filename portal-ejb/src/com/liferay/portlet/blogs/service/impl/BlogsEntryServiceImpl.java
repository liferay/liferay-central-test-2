/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsEntryService;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;

/**
 * <a href="BlogsEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BlogsEntryServiceImpl
	extends PrincipalBean implements BlogsEntryService {

	public BlogsEntry addEntry(
			String plid, String categoryId, String title, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.BLOGS,
			ActionKeys.ADD_ENTRY);

		return BlogsEntryLocalServiceUtil.addEntry(
			getUserId(), plid, categoryId, title, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			addCommunityPermissions, addGuestPermissions);
	}

	public BlogsEntry addEntry(
			String plid, String categoryId, String title, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.BLOGS,
			ActionKeys.ADD_ENTRY);

		return BlogsEntryLocalServiceUtil.addEntry(
			getUserId(), plid, categoryId, title, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			communityPermissions, guestPermissions);
	}

	public void deleteEntry(String entryId)
		throws PortalException, SystemException {

		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		BlogsEntryLocalServiceUtil.deleteEntry(entryId);
	}

	public BlogsEntry getEntry(String entryId)
		throws PortalException, SystemException {

		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return BlogsEntryLocalServiceUtil.getEntry(entryId);
	}

	public BlogsEntry updateEntry(
			String entryId, String categoryId, String title, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute)
		throws PortalException, SystemException {

		BlogsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return BlogsEntryLocalServiceUtil.updateEntry(
			getUserId(), entryId, categoryId, title, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute);
	}

}