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

package com.liferay.message.boards.web.portlet.configuration.icon;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.messageboards.model.MBMessage;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class SubscribeThreadPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public SubscribeThreadPortletConfigurationIcon(
		PortletRequest portletRequest, MBMessage message) {

		super(portletRequest);

		_message = message;
	}

	@Override
	public String getMessage() {
		return "subscribe";
	}

	@Override
	public String getURL() {
		try {
			PortletURL portletURL = PortletURLFactoryUtil.create(
				portletRequest, MBPortletKeys.MESSAGE_BOARDS_ADMIN,
				themeDisplay.getPlid(), PortletRequest.ACTION_PHASE);

			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/message_boards/edit_message");
			portletURL.setParameter(Constants.CMD, Constants.SUBSCRIBE);
			portletURL.setParameter(
				"redirect", PortalUtil.getCurrentURL(portletRequest));
			portletURL.setParameter(
				"messageId", String.valueOf(_message.getMessageId()));

			return portletURL.toString();
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	@Override
	public boolean isShow() {
		return true;
	}

	private final MBMessage _message;

}