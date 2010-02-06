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

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void getCategory(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getCategory(request);
	}

	public static void getCategory(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getCategory(request);
	}

	public static void getCategory(HttpServletRequest request)
		throws Exception {

		// Add redundant check here because the JSP does not check permissions
		// on the initial search container

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		MBBanLocalServiceUtil.checkBan(
			themeDisplay.getScopeGroupId(), themeDisplay.getUserId());

		long categoryId = ParamUtil.getLong(request, "mbCategoryId");

		MBCategory category = null;

		if ((categoryId > 0) &&
			(categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID)) {

			category = MBCategoryServiceUtil.getCategory(categoryId);
		}

		request.setAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY, category);
	}

	public static void getMessage(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getMessage(request);
	}

	public static void getMessage(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getMessage(request);
	}

	public static void getMessage(HttpServletRequest request) throws Exception {
		long messageId = ParamUtil.getLong(request, "messageId");

		MBMessage message = null;

		if (messageId > 0) {
			message = MBMessageServiceUtil.getMessage(messageId);
		}

		request.setAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE, message);
	}

	public static void getThreadMessage(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getThreadMessage(request);
	}

	public static void getThreadMessage(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getThreadMessage(request);
	}

	public static void getThreadMessage(HttpServletRequest request)
		throws Exception {

		long threadId = ParamUtil.getLong(request, "threadId");

		MBMessage message = null;

		if (threadId > 0) {
			MBThread thread = MBThreadLocalServiceUtil.getThread(threadId);

			message = MBMessageServiceUtil.getMessage(
				thread.getRootMessageId());
		}

		request.setAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE, message);
	}

}