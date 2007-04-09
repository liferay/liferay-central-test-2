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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.AccountLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.GetterUtil;

/**
 * <a href="CommonPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 *
 */
public class CommonPermission {

	public static void checkPermission(
			PermissionChecker permissionChecker, String className,
			String classPK, String actionId)
		throws PortalException, SystemException {

		if (className.equals(Account.class.getName())) {
			Account account = AccountLocalServiceUtil.getAccount(classPK);

			if (account.isDefaultAccount()) {
				AccountPermission.check(permissionChecker, classPK, actionId);
			}
			else {
				throw new PrincipalException();
			}
		}
		else if (className.equals(Contact.class.getName())) {
			User user = UserLocalServiceUtil.getUserByContactId(
				GetterUtil.getLong(classPK));

			UserPermission.check(
				permissionChecker, user.getUserId(),
				user.getOrganization().getOrganizationId(),
				user.getLocation().getOrganizationId(), actionId);
		}
		else if (className.equals(Organization.class.getName())) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(classPK);

			if (organization.isRoot()) {
				OrganizationPermission.check(
					permissionChecker, classPK, actionId);
			}
			else {
				LocationPermission.check(permissionChecker, classPK, actionId);
			}
		}
		else {
			throw new PrincipalException();
		}
	}

}