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

package com.liferay.portlet.messageboards.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.messageboards.NoSuchMailingException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMailing;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMailingLocalServiceUtil;

/**
 * <a href="MBMailingPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 */
public class MBMailingPermission {

	public static void check(
		PermissionChecker permissionChecker, long mailingId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, mailingId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long mailingId, String actionId)
		throws PortalException, SystemException {

		MBMailing mailing = MBMailingLocalServiceUtil.getMBMailing(mailingId);

		return contains(permissionChecker, mailing, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, MBMailing mailing, String actionId)
		throws PortalException, SystemException {

		long categoryId = mailing.getCategoryId();

		while (categoryId != MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID) {

			try {
				mailing =
					MBMailingLocalServiceUtil.getMailingByCategory(categoryId);

				if (permissionChecker.hasPermission(
					mailing.getGroupId(), MBMailing.class.getName(),
					mailing.getMailingId(), actionId)) {

					return true;
				}
			}
			catch (NoSuchMailingException e) {
			}

			MBCategory category =
				MBCategoryLocalServiceUtil.getCategory(categoryId);

			categoryId = category.getParentCategoryId();

			if (actionId.equals(ActionKeys.VIEW)) {
				break;
			}
		}

		return false;
	}

}