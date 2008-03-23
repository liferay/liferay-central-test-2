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
 *
 */
public class GetUsersCountAction extends AJAXAction {

	public String getText(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(req);

		String className = ParamUtil.getString(req, "className");
		long[] ids = StringUtil.split(ParamUtil.getString(req, "ids"), 0L);
		boolean active = ParamUtil.getBoolean(req, "active");

		int count = 0;

		if (className.equals(Organization.class.getName())) {
			count = getOrganizationUsersCount(companyId, ids, active);
		}
		else if (className.equals(UserGroup.class.getName())) {
		}

		return String.valueOf(count);
	}

	protected int getOrganizationUsersCount(
			long companyId, long[] organizationIds, boolean active)
		throws Exception {

		int count = 0;

		for (long organizationId : organizationIds) {
			LinkedHashMap<String, Object> userParams =
				new LinkedHashMap<String, Object>();

			userParams.put("usersOrgs", new Long(organizationId));

			count+= UserLocalServiceUtil.searchCount(
				companyId, null, active, userParams);
		}

		return count;
	}

}