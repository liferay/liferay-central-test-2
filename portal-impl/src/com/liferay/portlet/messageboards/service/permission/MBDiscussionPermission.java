/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

/**
 * <a href="MBDiscussionPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 */
public class MBDiscussionPermission {

	public static void check(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK, long ownerId, String actionId)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, companyId, groupId, className, classPK,
				ownerId, actionId)) {

			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK, long messageId, long ownerId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, companyId, groupId, className, classPK,
				messageId, ownerId, actionId)) {

			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK, long ownerId, String actionId)
		throws SystemException {

		if (MBBanLocalServiceUtil.hasBan(
				groupId, permissionChecker.getUserId())) {

			return false;
		}

		if (permissionChecker.hasOwnerPermission(
				companyId, className, classPK, ownerId, actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			groupId, className, classPK, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK, long messageId, long ownerId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, companyId, groupId, className, classPK,
				ownerId, actionId)) {

			return false;
		}

		MBMessage message = MBMessageLocalServiceUtil.getMessage(
			messageId);

		MBDiscussion discussion =
			MBDiscussionLocalServiceUtil.getThreadDiscussion(
				message.getThreadId());

		long classNameId = PortalUtil.getClassNameId(className);

		if ((discussion.getClassNameId() == classNameId) &&
			(discussion.getClassPK() == classPK)) {

			return true;
		}

		return false;
	}

}