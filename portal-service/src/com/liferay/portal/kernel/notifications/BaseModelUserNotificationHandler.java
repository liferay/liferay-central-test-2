/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.notifications;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 */
public abstract class BaseModelUserNotificationHandler
	extends BaseUserNotificationHandler {

	protected String getTitle(int notificationType) {
		if (notificationType ==
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY) {

			return "x-added-a-new-blog-entry";
		}
		else if (notificationType ==
					UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY) {

			return "x-updated-a-blog-entry";
		}

		return StringPool.BLANK;
	}

	protected String getTitle(BaseModel<?> baseModel) {
		BlogsEntry entry = (BlogsEntry)baseModel;
	
		return entry.getTitle();
	}

	protected BaseModel<?> fetchBaseModel(long classPK) throws SystemException {
		return BlogsEntryLocalServiceUtil.fetchBlogsEntry(classPK);
	}

	protected String getUserName(BaseModel<?> baseModel) {
		AuditedModel auditedModel = (AuditedModel)baseModel;

		return PortalUtil.getUserName(
			auditedModel.getUserId(), StringPool.BLANK);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		BaseModel<?> baseModel = fetchBaseModel(classPK);

		if (baseModel == null) {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		int notificationType = jsonObject.getInt("notificationType");

		String title = getTitle(notificationType);

		StringBundler sb = new StringBundler(5);

		sb.append("<div class=\"title\">");
		sb.append(
			serviceContext.translate(
				title, HtmlUtil.escape(getUserName(baseModel))));
		sb.append("</div><div class=\"body\">");
		sb.append(
			HtmlUtil.escape(StringUtil.shorten(getTitle(baseModel)), 50));
		sb.append("</div>");

		return sb.toString();
	}

	protected void setLinkParameters(
		PortletURL portletURL, BaseModel<?> baseModel) {

		BlogsEntry entry = (BlogsEntry)baseModel;

		portletURL.setParameter("struts_action", "/blogs/view_entry");
		portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		BaseModel<?> baseModel = fetchBaseModel(classPK);

		if (baseModel == null) {
			return null;
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		User user = themeDisplay.getUser();

		Group group = user.getGroup();

		long portletPlid = PortalUtil.getPlidFromPortletId(
			group.getGroupId(), true, getPortletId());

		PortletURL portletURL = null;

		if (portletPlid != 0) {
			portletURL = PortletURLFactoryUtil.create(
				serviceContext.getLiferayPortletRequest(), getPortletId(),
				portletPlid, PortletRequest.RENDER_PHASE);

			setLinkParameters(portletURL, baseModel);
		}
		else {
			LiferayPortletResponse liferayPortletResponse =
				serviceContext.getLiferayPortletResponse();

			portletURL = liferayPortletResponse.createRenderURL(
				getPortletId());

			setLinkParameters(portletURL, baseModel);

			portletURL.setWindowState(WindowState.MAXIMIZED);
		}

		return portletURL.toString();
	}

}