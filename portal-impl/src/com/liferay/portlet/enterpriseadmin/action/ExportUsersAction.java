/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.enterpriseadmin.action;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.http.UserJSONSerializer;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactory;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.enterpriseadmin.search.UserSearch;
import com.liferay.portlet.enterpriseadmin.search.UserSearchTerms;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ExportUsersAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Mika Koivisto
 */
public class ExportUsersAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String csv = getUsersCSV(actionRequest);

			String fileName = "users.csv";
			byte[] bytes = csv.getBytes();

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			ServletResponseUtil.sendFile(
				request, response, fileName, bytes, ContentTypes.TEXT_CSV_UTF8);

			response.flushBuffer();
			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass().getName());

			setForward(actionRequest, "portlet.enterprise_admin.error");
		}
	}

	public ActionForward render(
		ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
		RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		return null;
	}

	protected String getUsersCSV(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!PortalPermissionUtil.contains(
			permissionChecker, ActionKeys.EXPORT_USER)) {

			return StringPool.BLANK;
		}

		long companyId = themeDisplay.getCompanyId();

		String exportProgressId = ParamUtil.getString(
			actionRequest, "exportProgressId");

		ProgressTracker progressTracker = new ProgressTracker(
			actionRequest, exportProgressId);

		progressTracker.start();

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		PortletURLFactory portletURLFactory = 
			PortletURLFactoryUtil.getPortletURLFactory();
		PortletURL portletURL = portletURLFactory.create(
			request, PortletKeys.ENTERPRISE_ADMIN_USERS, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		UserSearch userSearch = new UserSearch(actionRequest, portletURL);
		UserSearchTerms searchTerms =
			(UserSearchTerms)userSearch.getSearchTerms();

		if (!searchTerms.isAdvancedSearch() && !searchTerms.hasActive()) {
			searchTerms.setActive(Boolean.TRUE);
		}

		long organizationId = searchTerms.getOrganizationId();
		long roleId = searchTerms.getRoleId();
		long userGroupId = searchTerms.getUserGroupId();

		LinkedHashMap<String, Object> userParams =
			new LinkedHashMap<String, Object>();

		if (organizationId > 0) {
			userParams.put("usersOrgs", new Long(organizationId));
		}

		if (roleId > 0) {
			userParams.put("usersRoles", new Long(roleId));
		}

		if (userGroupId > 0) {
			userParams.put("usersUserGroups", new Long(userGroupId));
		}

		List<User> users = null;

		if (searchTerms.isAdvancedSearch()) {
			users = UserLocalServiceUtil.search(
				companyId, searchTerms.getFirstName(),
				searchTerms.getMiddleName(), searchTerms.getLastName(),
				searchTerms.getScreenName(), searchTerms.getEmailAddress(),
				searchTerms.getActive(), userParams,
				searchTerms.isAndOperator(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, (OrderByComparator)null);
		}
		else {
			users = UserLocalServiceUtil.search(
				companyId, searchTerms.getKeywords(), searchTerms.getActive(),
				userParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				(OrderByComparator)null);
		}

		int percentage = 10;
		int total = users.size();

		progressTracker.updateProgress(percentage);

		if (total == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(users.size() * 4);

		Iterator<User> itr = users.iterator();

		for (int i = 0; itr.hasNext(); i++) {
			User user = itr.next();

			exportUser(user, sb);

			percentage = Math.min(10 + (i * 90) / total, 99);

			progressTracker.updateProgress(percentage);
		}

		progressTracker.finish();

		return sb.toString();
	}

	protected void exportUser(User user, StringBundler sb) {
		JSONObject jsonObj = UserJSONSerializer.toJSONObject(user);

		for (int i = 0; i < PropsValues.USERS_EXPORT_CVS_FIELDS.length; i++) {
			String field = PropsValues.USERS_EXPORT_CVS_FIELDS[i];

			if (field.equals("fullName")) {
				sb.append(user.getFullName());
			}
			else if (field.startsWith("expando:")) {
				String attributeName = field.substring(7);
				sb.append(user.getExpandoBridge().getAttribute(attributeName));
			}
			else {
				sb.append(jsonObj.getString(field));
			}

			if (i+1 < PropsValues.USERS_EXPORT_CVS_FIELDS.length) {
				sb.append(StringPool.COMMA);
			}
		}
		sb.append(StringPool.NEW_LINE);
	}

}