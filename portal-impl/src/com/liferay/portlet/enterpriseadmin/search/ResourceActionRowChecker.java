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

package com.liferay.portlet.enterpriseadmin.search;

import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import javax.portlet.RenderResponse;

/**
 * <a href="ResourceActionRowChecker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class ResourceActionRowChecker extends RowChecker {

	public ResourceActionRowChecker(RenderResponse renderResponse) {
		super(renderResponse);
	}

	public boolean isChecked(Object obj) {
		try {
			return doIsChecked(obj);
		}
		catch (Exception e) {
			return false;
		}
	}

	protected boolean doIsChecked(Object obj) throws Exception {
		Object[] objArray = (Object[])obj;

		Role role = (Role)objArray[0];
		String actionId = (String)objArray[1];
		String resourceName = (String)objArray[2];
		Integer scope = (Integer)objArray[4];

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			return
				ResourcePermissionLocalServiceUtil.hasScopeResourcePermission(
					role.getCompanyId(), resourceName, scope, role.getRoleId(),
					actionId);
		}
		else {
			return PermissionLocalServiceUtil.hasRolePermission(
				role.getRoleId(), role.getCompanyId(), resourceName, scope,
				actionId);
		}
	}

}