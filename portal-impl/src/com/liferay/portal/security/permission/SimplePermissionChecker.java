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

package com.liferay.portal.security.permission;

/**
 * <a href="SimplePermissionChecker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SimplePermissionChecker extends BasePermissionChecker {

	public boolean hasOwnerPermission(
		long companyId, String name, String primKey, long ownerId,
		String actionId) {

		return hasPermission(actionId);
	}

	public boolean hasPermission(
		long groupId, String name, String primKey, String actionId) {

		return hasPermission(actionId);
	}

	public boolean hasUserPermission(
		long groupId, String name, String primKey, String actionId,
		boolean checkAdmin) {

		return hasPermission(actionId);
	}

	public boolean isCommunityAdmin(long groupId) {
		return signedIn;
	}

	public boolean isCommunityOwner(long groupId) {
		return signedIn;
	}

	public boolean isCompanyAdmin() {
		return signedIn;
	}

	public boolean isCompanyAdmin(long companyId) {
		return signedIn;
	}

	protected boolean hasPermission(String actionId) {
		if (signedIn) {
			return true;
		}

		if (actionId.equals(ActionKeys.VIEW)) {
			return true;
		}
		else {
			return false;
		}
	}

}