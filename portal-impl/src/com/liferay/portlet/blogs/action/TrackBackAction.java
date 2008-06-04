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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.util.Locale;

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
 * <a href="TrackBackAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class TrackBackAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		long groupId = PortalUtil.getPortletGroupId(req);
		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);
		Locale locale = themeDisplay.getLocale();
		String title = ParamUtil.getString(req, "title");
		String excerpt = ParamUtil.getString(req, "excerpt");
		String url = ParamUtil.getString(req, "url");
		String blogName = ParamUtil.getString(req, "blog_name");

		try {
			if (!commentsEnabled(req)) {
				sendError(res, _COMMENTS_DISABLED);
			}
			else if (Validator.isNull(url)) {
				sendError(res, _MISSING_URL);
			}
			else {
				ActionUtil.getEntry(req);

				BlogsEntry entry =
					(BlogsEntry)req.getAttribute(WebKeys.BLOGS_ENTRY);

				if (!entry.isAllowTrackbacks()) {
					sendError(res, _UNAUTHORIZED);
				}
				else {
					long userId =
						PortalUtil.getCompany(req).getDefaultUser().getUserId();
					String className = BlogsEntry.class.getName();
					long classPK = entry.getEntryId();

					MBThread thread =
						MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
							userId, className, classPK).getThread();

					long threadId = thread.getThreadId();
					long parentMessageId = thread.getRootMessageId();
					String body =
						"[...] " + excerpt + " [...] [url=" + url + "]" +
							LanguageUtil.get(locale, "read-more") + "[/url]";

					MBMessageLocalServiceUtil.addDiscussionMessage(
						userId, blogName, groupId, className, classPK, threadId,
						parentMessageId, title, body, themeDisplay);

					sendSuccess(res);
				}
			}
		}
		catch (Exception e) {
			sendError(res, _UNKNOWN_ERROR);

			_log.error(e);
		}

		setForward(req, ActionConstants.COMMON_NULL);
	}

	protected boolean commentsEnabled(ActionRequest req) throws Exception {
		PortletPreferences prefs = req.getPreferences();

		String portletResource = ParamUtil.getString(req, "portletResource");

		if (Validator.isNotNull(portletResource)) {
			prefs = PortletPreferencesFactoryUtil.getPortletSetup(
				req, portletResource);
		}

		return GetterUtil.getBoolean(
			prefs.getValue("enable-comments", null), true);
	}

	protected void sendError(ActionResponse res, String msg)
		throws Exception {

		sendResponse(res, msg, false);
	}

	protected void sendSuccess(ActionResponse res) throws Exception {
		sendResponse(res, null, true);
	}

	protected void sendResponse(ActionResponse res, String msg, boolean success)
		throws Exception{

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

		HttpServletResponse httpRes = PortalUtil.getHttpServletResponse(res);

		ServletResponseUtil.sendFile(
			httpRes, null, sb.toString().getBytes(),
			ContentTypes.TEXT_XML_UTF8);
	}

	private static final String _MISSING_URL =
		"TrackBack ping requires a valid permalink URL.";

	private static final String _COMMENTS_DISABLED =
		"Comments have been disabled for this blog entry.";

	private static final String _UNKNOWN_ERROR =
		"An unknown error has occurred.";

	private static final String _UNAUTHORIZED =
		"Trackbacks are not enabled on this blog entry.";

	private static Log _log = LogFactory.getLog(TrackBackAction.class);

}