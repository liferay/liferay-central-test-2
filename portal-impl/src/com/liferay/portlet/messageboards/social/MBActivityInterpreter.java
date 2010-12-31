/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.social;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public class MBActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!MBMessagePermission.contains(
				permissionChecker, activity.getClassPK(), ActionKeys.VIEW)) {

			return null;
		}

		String groupName = StringPool.BLANK;

		if (activity.getGroupId() != themeDisplay.getScopeGroupId()) {
			groupName = getGroupName(activity.getGroupId(), themeDisplay);
		}

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);
		String receiverUserName = getUserName(
			activity.getReceiverUserId(), themeDisplay);

		int activityType = activity.getType();

		// Link

		MBMessage message = MBMessageLocalServiceUtil.getMessage(
			activity.getClassPK());

		String link =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/message_boards/find_message?messageId=" +
					message.getMessageId();

		// Title

		String titlePattern = null;

		if (activityType == MBActivityKeys.ADD_MESSAGE) {
			titlePattern = "activity-message-boards-add-message";
		}
		else if (activityType == MBActivityKeys.REPLY_MESSAGE) {
			titlePattern = "activity-message-boards-reply-message";
		}

		if (Validator.isNotNull(groupName)) {
			titlePattern += "-in";
		}

		String messageSubject = wrapLink(
			link, HtmlUtil.escape(cleanContent(message.getSubject())));

		Object[] titleArguments = new Object[] {
			groupName, creatorUserName, receiverUserName, messageSubject
		};

		String title = themeDisplay.translate(titlePattern, titleArguments);

		// Body

		String categoryLink =
			themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
				"/message_boards/find_category?mbCategoryId=" +
					message.getCategoryId();

		String body = wrapLink(categoryLink, "go-to-category", themeDisplay);

		return new SocialActivityFeedEntry(link, title, body);
	}

	private static final String[] _CLASS_NAMES = new String[] {
		MBMessage.class.getName()
	};

}