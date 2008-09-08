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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.TrackbackVerifierUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="TrackbackAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class TrackbackAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			addTrackback(actionRequest, actionResponse);
		}
		catch (Exception e) {
			sendError(actionResponse, "An unknown error has occurred.");

			_log.error(e);
		}

		setForward(actionRequest, ActionConstants.COMMON_NULL);
	}

	protected void addTrackback(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String title = ParamUtil.getString(actionRequest, "title");
		String excerpt = ParamUtil.getString(actionRequest, "excerpt");
		String url = ParamUtil.getString(actionRequest, "url");
		String blogName = ParamUtil.getString(actionRequest, "blog_name");

		if (!isCommentsEnabled(actionRequest)) {
			sendError(
				actionResponse,
				"Comments have been disabled for this blog entry.");

			return;
		}

		if (Validator.isNull(url)) {
			sendError(
				actionResponse, "Trackback requires a valid permanent URL.");

			return;
		}

		ActionUtil.getEntry(actionRequest);

		BlogsEntry entry = (BlogsEntry)actionRequest.getAttribute(
			WebKeys.BLOGS_ENTRY);

		if (!entry.isAllowTrackbacks()) {
			sendError(
				actionResponse,
				"Trackbacks are not enabled on this blog entry.");

			return;
		}

		long userId = UserLocalServiceUtil.getDefaultUserId(
			themeDisplay.getCompanyId());
		long groupId = themeDisplay.getPortletGroupId();
		String className = BlogsEntry.class.getName();
		long classPK = entry.getEntryId();

		MBMessageDisplay messageDisplay =
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				userId, className, classPK);

		MBThread thread = messageDisplay.getThread();

		long threadId = thread.getThreadId();
		long parentMessageId = thread.getRootMessageId();
		String body =
			"[...] " + excerpt + " [...] [url=" + url + "]" +
				themeDisplay.translate("read-more") + "[/url]";

		MBMessage message = MBMessageLocalServiceUtil.addDiscussionMessage(
			userId, blogName, groupId, className, classPK, threadId,
			parentMessageId, title, body, themeDisplay);

		String trackbackUrl =
			themeDisplay.getPortalURL() +
				PortalUtil.getLayoutURL(themeDisplay) + "/-/blogs/trackback/" +
					entry.getUrlTitle();

		TrackbackVerifierUtil.addNewPost(
			message.getMessageId(), url, trackbackUrl);

		sendSuccess(actionResponse);
	}

	protected boolean isCommentsEnabled(ActionRequest actionRequest)
		throws Exception {

		PortletPreferences prefs = actionRequest.getPreferences();

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		if (Validator.isNotNull(portletResource)) {
			prefs = PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);
		}

		return GetterUtil.getBoolean(
			prefs.getValue("enable-comments", null), true);
	}

	protected void sendError(ActionResponse actionResponse, String msg)
		throws Exception {

		sendResponse(actionResponse, msg, false);
	}

	protected void sendResponse(
			ActionResponse actionResponse, String msg, boolean success)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<response>");

		if (success) {
			sb.append("<error>0</error>");
		}
		else {
			sb.append("<error>1</error>");
			sb.append("<message>" + msg + "</message>");
		}

		sb.append("</response>");

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		ServletResponseUtil.sendFile(
			response, null, sb.toString().getBytes(StringPool.UTF8),
			ContentTypes.TEXT_XML_UTF8);
	}

	protected void sendSuccess(ActionResponse actionResponse) throws Exception {
		sendResponse(actionResponse, null, true);
	}

	private static Log _log = LogFactory.getLog(TrackbackAction.class);

}