/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBTopic;
import com.liferay.portlet.messageboards.service.spring.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.spring.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.spring.MBTopicLocalServiceUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.io.IOException;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.jsp.PageContext;

/**
 * <a href="MBUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBUtil {

	public static String getBreadcrumbs(
			String categoryId, String topicId, String messageId,
			PageContext pageContext, RenderResponse res)
		throws Exception {

		if (Validator.isNotNull(topicId) && Validator.isNotNull(messageId)) {
			MBTopic topic = MBTopicLocalServiceUtil.getTopic(topicId);
			MBMessage message = MBMessageLocalServiceUtil.getMessage(
				topicId, messageId);

			return getBreadcrumbs(null, topic, message, pageContext, res);
		}
		else if (Validator.isNotNull(topicId)) {
			MBTopic topic = MBTopicLocalServiceUtil.getTopic(topicId);

			return getBreadcrumbs(
				topic.getCategory(), topic, null, pageContext, res);
		}
		else {
			MBCategory category = null;

			try {
				category = MBCategoryLocalServiceUtil.getCategory(categoryId);
			}
			catch (Exception e) {
			}

			return getBreadcrumbs(category, null, null, pageContext, res);
		}
	}

	public static String getBreadcrumbs(
			MBCategory category, MBTopic topic, MBMessage message,
			PageContext pageContext, RenderResponse res)
		throws Exception {

		if ((message != null) && (topic == null)) {
			topic = MBTopicLocalServiceUtil.getTopic(message.getTopicId());
		}

		if ((topic != null) && (category == null)) {
			category = topic.getCategory();
		}

		PortletURL categoriesURL = res.createRenderURL();

		categoriesURL.setWindowState(WindowState.MAXIMIZED);

		categoriesURL.setParameter("struts_action", "/message_boards/view");
		categoriesURL.setParameter(
			"categoryId", MBCategory.DEFAULT_PARENT_CATEGORY_ID);

		String categoriesLink =
			"<a href=\"" + categoriesURL.toString() + "\">" +
				LanguageUtil.get(pageContext, "categories") + "</a>";

		if (category == null) {
			return categoriesLink;
		}

		String breadcrumbs = StringPool.BLANK;

		if (category != null) {
			for (int i = 0;; i++) {
				PortletURL portletURL = res.createRenderURL();

				portletURL.setWindowState(WindowState.MAXIMIZED);

				portletURL.setParameter("struts_action", "/message_boards/view");
				portletURL.setParameter("categoryId", category.getCategoryId());

				String categoryLink =
					"<a href=\"" + portletURL.toString() + "\">" +
						category.getName() + "</a>";

				if (i == 0) {
					breadcrumbs = categoryLink;
				}
				else {
					breadcrumbs = categoryLink + " &raquo; " + breadcrumbs;
				}

				if (category.isRoot()) {
					break;
				}

				category = MBCategoryLocalServiceUtil.getCategory(
					category.getParentCategoryId());
			}
		}

		breadcrumbs = categoriesLink + " &raquo; " + breadcrumbs;

		if (topic != null) {
			PortletURL topicURL = res.createRenderURL();

			topicURL.setWindowState(WindowState.MAXIMIZED);

			topicURL.setParameter("struts_action", "/message_boards/view_topic");
			topicURL.setParameter("topicId", topic.getTopicId());

			String topicLink =
				"<a href=\"" + topicURL.toString() + "\">" + topic.getName() +
					"</a>";

			breadcrumbs = breadcrumbs + " &raquo; " + topicLink;
		}

		if (message != null) {
			PortletURL messageURL = res.createRenderURL();

			messageURL.setWindowState(WindowState.MAXIMIZED);

			messageURL.setParameter(
				"struts_action", "/message_boards/view_message");
			messageURL.setParameter("messageId", message.getMessageId());
			messageURL.setParameter("topicId", topic.getTopicId());

			String messageLink =
				"<a href=\"" + messageURL.toString() + "\">" +
					message.getSubject() + "</a>";

			breadcrumbs = breadcrumbs + " &raquo; " + messageLink;
		}

		return breadcrumbs;
	}

	public static String getEmailFromAddress(PortletPreferences prefs) {
		String emailFromAddress = PropsUtil.get(
			PropsUtil.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS);

		return prefs.getValue("email-from-address", emailFromAddress);
	}

	public static String getEmailFromName(PortletPreferences prefs) {
		String emailFromName = PropsUtil.get(
			PropsUtil.MESSAGE_BOARDS_EMAIL_FROM_NAME);

		return prefs.getValue("email-from-name", emailFromName);
	}

	public static boolean getEmailMessageAddedEnabled(
		PortletPreferences prefs) {

		String emailMessageAddedEnabled = prefs.getValue(
			"email-message-added-enabled", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedEnabled)) {
			return GetterUtil.getBoolean(emailMessageAddedEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_ENABLED));
		}
	}

	public static String getEmailMessageAddedBody(PortletPreferences prefs)
		throws IOException {

		String emailMessageAddedBody = prefs.getValue(
			"email-message-added-body", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedBody)) {
			return emailMessageAddedBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY));
		}
	}

	public static String getEmailMessageAddedSubject(PortletPreferences prefs)
		throws IOException {

		String emailMessageAddedSubject = prefs.getValue(
			"email-message-added-subject", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedSubject)) {
			return emailMessageAddedSubject;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SUBJECT));
		}
	}

	public static boolean getEmailMessageUpdatedEnabled(
		PortletPreferences prefs) {

		String emailMessageUpdatedEnabled = prefs.getValue(
			"email-message-updated-enabled", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedEnabled)) {
			return GetterUtil.getBoolean(emailMessageUpdatedEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_ENABLED));
		}
	}

	public static String getEmailMessageUpdatedBody(PortletPreferences prefs)
		throws IOException {

		String emailMessageUpdatedBody = prefs.getValue(
			"email-message-updated-body", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedBody)) {
			return emailMessageUpdatedBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY));
		}
	}

	public static String getEmailMessageUpdatedSubject(PortletPreferences prefs)
		throws IOException {

		String emailMessageUpdatedSubject = prefs.getValue(
			"email-message-updated-subject", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedSubject)) {
			return emailMessageUpdatedSubject;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsUtil.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SUBJECT));
		}
	}

	public static String getRank(PortletPreferences prefs, int posts)
		throws Exception {

		String rank = StringPool.BLANK;

		String[] ranks = prefs.getValues("ranks", new String[0]);

		for (int i = 0; i < ranks.length; i++) {
			String[] kvp = StringUtil.split(ranks[i], StringPool.EQUAL);

			String kvpName = kvp[0];
			int kvpPosts = GetterUtil.getInteger(kvp[1]);

			if (posts >= kvpPosts) {
				rank = kvpName;
			}
			else {
				break;
			}
		}

		return rank;
	}

}