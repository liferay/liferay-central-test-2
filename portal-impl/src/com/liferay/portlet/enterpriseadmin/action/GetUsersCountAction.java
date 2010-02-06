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

package com.liferay.portlet.enterpriseadmin.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.AJAXAction;
import com.liferay.portal.util.PortalUtil;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="GetUsersCountAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Gavin Wan
 */
public class GetUsersCountAction extends AJAXAction {

	public String getText(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		String className = ParamUtil.getString(request, "className");
		long[] ids = StringUtil.split(ParamUtil.getString(request, "ids"), 0L);
		boolean active = ParamUtil.getBoolean(request, "active");

		int count = 0;

		if (className.equals(Organization.class.getName())) {
			count = getOrganizationUsersCount(companyId, ids, active);
		}
		else if (className.equals(UserGroup.class.getName())) {
			count = getUserGroupUsersCount(companyId, ids, active);
		}

		return String.valueOf(count);
	}

	protected int getOrganizationUsersCount(
			long companyId, long[] organizationIds, boolean active)
		throws Exception {

		int count = 0;

		for (long organizationId : organizationIds) {
			LinkedHashMap<String, Object> params =
				new LinkedHashMap<String, Object>();

			params.put("usersOrgs", organizationId);

			count+= UserLocalServiceUtil.searchCount(
				companyId, null, active, params);
		}

		return count;
	}

	protected int getUserGroupUsersCount(
			long companyId, long[] userGroupIds, boolean active)
		throws Exception {

		int count = 0;

		for (long userGroupId : userGroupIds) {
			LinkedHashMap<String, Object> params =
				new LinkedHashMap<String, Object>();

			params.put("usersUserGroups", userGroupId);

			count+= UserLocalServiceUtil.searchCount(
				companyId, null, active, params);
		}

		return count;
	}

}