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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="ExportUsersAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ExportUsersAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			String csv = getUsersCSV(request);

			String fileName = "users.csv";
			byte[] bytes = csv.getBytes();

			ServletResponseUtil.sendFile(
				response, fileName, bytes, ContentTypes.TEXT_CSV_UTF8);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected String getUsersCSV(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!RoleLocalServiceUtil.hasUserRole(
				themeDisplay.getUserId(), themeDisplay.getCompanyId(),
				RoleConstants.ADMINISTRATOR, true)) {

			return StringPool.BLANK;
		}

		String exportProgressId = ParamUtil.getString(
			request, "exportProgressId");

		ProgressTracker progressTracker = new ProgressTracker(
			request, exportProgressId);

		progressTracker.start();

		List<User> users = UserLocalServiceUtil.search(
			themeDisplay.getCompanyId(), null, Boolean.TRUE, null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator)null);

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

			sb.append(user.getFullName());
			sb.append(StringPool.COMMA);
			sb.append(user.getEmailAddress());
			sb.append(StringPool.NEW_LINE);

			percentage = Math.min(10 + (i * 90) / total, 99);

			progressTracker.updateProgress(percentage);
		}

		progressTracker.finish();

		return sb.toString();
	}

}