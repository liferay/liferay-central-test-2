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

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.util.RSSUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="RSSAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RSSAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			ServletResponseUtil.sendFile(
				response, null, getRSS(request), ContentTypes.TEXT_XML_UTF8);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected byte[] getRSS(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String plid = ParamUtil.getString(request, "p_l_id");
		long companyId = ParamUtil.getLong(request, "companyId");
		long groupId = ParamUtil.getLong(request, "groupId");
		long userId = ParamUtil.getLong(request, "userId");
		long categoryId = ParamUtil.getLong(request, "mbCategoryId");
		long threadId = ParamUtil.getLong(request, "threadId");
		int max = ParamUtil.getInteger(
			request, "max", SearchContainer.DEFAULT_DELTA);
		String type = ParamUtil.getString(
			request, "type", RSSUtil.DEFAULT_TYPE);
		double version = ParamUtil.getDouble(
			request, "version", RSSUtil.DEFAULT_VERSION);
		String displayStyle = ParamUtil.getString(
			request, "displayStyle", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);

		String entryURL =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/message_boards/find_message?p_l_id=" + plid;

		String rss = StringPool.BLANK;

		if (companyId > 0) {
			String feedURL = StringPool.BLANK;

			rss = MBMessageServiceUtil.getCompanyMessagesRSS(
				companyId, StatusConstants.APPROVED, max, type, version,
				displayStyle, feedURL, entryURL, themeDisplay);
		}
		else if (groupId > 0) {
			String feedURL =
				themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
					"/message_boards/find_recent_posts?p_l_id=" + plid;

			if (userId > 0) {
				rss = MBMessageServiceUtil.getGroupMessagesRSS(
					groupId, userId, StatusConstants.APPROVED, max, type,
					version, displayStyle, feedURL, entryURL, themeDisplay);
			}
			else {
				rss = MBMessageServiceUtil.getGroupMessagesRSS(
					groupId, StatusConstants.APPROVED, max, type, version,
					displayStyle, feedURL, entryURL, themeDisplay);
			}
		}
		else if (categoryId > 0) {
			String feedURL =
				themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
					"/message_boards/find_category?p_l_id=" + plid +
						"&mbCategoryId=" + categoryId;

			rss = MBMessageServiceUtil.getCategoryMessagesRSS(
				groupId, categoryId, StatusConstants.APPROVED, max, type,
				version, displayStyle, feedURL, entryURL, themeDisplay);
		}
		else if (threadId > 0) {
			String feedURL =
				themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
					"/message_boards/find_thread?p_l_id=" + plid +
						"&threadId=" + threadId;

			rss = MBMessageServiceUtil.getThreadMessagesRSS(
				threadId, StatusConstants.APPROVED, max, type, version,
				displayStyle, feedURL, entryURL, themeDisplay);
		}

		return rss.getBytes(StringPool.UTF8);
	}

}