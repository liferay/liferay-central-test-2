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

package com.liferay.portlet.journal.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.journal.model.JournalSyndicatedFeed;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalSyndicatedFeedLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;

/**
 * <a href="JournalSyndicatedFeedPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class JournalSyndicatedFeedPermission {

	public static void check(
			PermissionChecker permissionChecker, long synFeedId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, synFeedId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String feedId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, feedId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, JournalSyndicatedFeed synFeed,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, synFeed, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long synFeedId,
			String actionId)
		throws PortalException, SystemException {

		JournalSyndicatedFeed synFeed =
			JournalSyndicatedFeedLocalServiceUtil.getSyndicatedFeed(synFeedId);

		return contains(permissionChecker, synFeed, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String feedId,
			String actionId)
		throws PortalException, SystemException {

		JournalSyndicatedFeed synFeed =
			JournalSyndicatedFeedLocalServiceUtil.getSyndicatedFeed(
				groupId, feedId);

		return contains(permissionChecker, synFeed, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, JournalSyndicatedFeed synFeed,
			String actionId)
		throws PortalException, SystemException {

		return permissionChecker.hasPermission(
			synFeed.getGroupId(), JournalSyndicatedFeed.class.getName(),
			synFeed.getId(), actionId);
	}

}
