/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.UserNotificationDelivery;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserNotificationDeliveryLocalServiceUtil;

/**
 * @author Jonathan Lee
 */
public abstract class BaseUserNotificationHandler
	implements UserNotificationHandler {

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public String getSelector() {
		return _selector;
	}

	@Override
	@SuppressWarnings("unused")
	public UserNotificationFeedEntry interpret(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			UserNotificationFeedEntry userNotificationFeedEntry = doInterpret(
				userNotificationEvent, serviceContext);

			if (userNotificationFeedEntry != null) {
				userNotificationFeedEntry.setOpenDialog(isOpenDialog());
				userNotificationFeedEntry.setPortletId(getPortletId());
			}

			return userNotificationFeedEntry;
		}
		catch (Exception e) {
			_log.error("Unable to interpret notification", e);
		}

		return null;
	}

	@Override
	public boolean isDeliver(
			long userId, long classNameId, int notificationType,
			int deliveryType, ServiceContext serviceContext)
		throws PortalException, SystemException {

		UserNotificationDefinition userNotificationDefinition =
			UserNotificationManagerUtil.fetchUserNotificationDefinition(
				_portletId, classNameId, notificationType);

		if (userNotificationDefinition == null) {
			if (deliveryType == UserNotificationDeliveryConstants.TYPE_EMAIL) {
				return true;
			}

			return false;
		}

		UserNotificationDeliveryType userNotificationDeliveryType =
			userNotificationDefinition.getUserNotificationDeliveryType(
				deliveryType);

		if (userNotificationDeliveryType == null) {
			return false;
		}

		UserNotificationDelivery userNotificationDelivery =
			UserNotificationDeliveryLocalServiceUtil.
				getUserNotificationDelivery(
					userId, _portletId, classNameId, notificationType,
					deliveryType, userNotificationDeliveryType.isDefault());

		return userNotificationDelivery.isDeliver();
	}

	@Override
	public boolean isOpenDialog() {
		return _openDialog;
	}

	protected UserNotificationFeedEntry doInterpret(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		String body = getBody(userNotificationEvent, serviceContext);

		if (Validator.isNull(body)) {
			return null;
		}

		String link = getLink(userNotificationEvent, serviceContext);

		return new UserNotificationFeedEntry(body, link);
	}

	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		return StringPool.BLANK;
	}

	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		return StringPool.BLANK;
	}

	protected String getNotificationTemplate() throws Exception {
		if (isActionable()) {
			StringBundler sb = new StringBundler(15);

			sb.append("<div class=\"title\">");
			sb.append("[$TITLE$]");
			sb.append("</div>");

			sb.append("<div class=\"body\">");

			sb.append("<a class=\"btn btn-action btn-success\" href=\"");
			sb.append("[$CONFIRM_URL$]");
			sb.append("\">");
			sb.append("[$CONFIRM$]");
			sb.append("</a>");

			sb.append("<a class=\"btn btn-action btn-warning\" href=\"");
			sb.append("[$IGNORE_URL$]");
			sb.append("\">");
			sb.append("[$IGNORE$]");
			sb.append("</a>");

			sb.append("</div>");

			return sb.toString();
		}
		else {
			StringBundler sb = new StringBundler();

			sb.append("<div class=\"title\">");
			sb.append("[$TITLE$]");
			sb.append("</div>");

			sb.append("<div class=\"body\">");
			sb.append("[$BODY$]");
			sb.append("</div>");

			return sb.toString();
		}
	}

	protected boolean isActionable() {
		return _actionable;
	}

	protected void setActionable(boolean actionable) {
		_actionable = actionable;
	}

	protected void setOpenDialog(boolean openDialog) {
		_openDialog = openDialog;
	}

	protected void setPortletId(String portletId) {
		_portletId = portletId;
	}

	protected void setSelector(String selector) {
		_selector = selector;
	}

	private static Log _log = LogFactoryUtil.getLog(
		BaseUserNotificationHandler.class);

	private boolean _actionable = false;
	private boolean _openDialog;
	private String _portletId;
	private String _selector = StringPool.BLANK;

}