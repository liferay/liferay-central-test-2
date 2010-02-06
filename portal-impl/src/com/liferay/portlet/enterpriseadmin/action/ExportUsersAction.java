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