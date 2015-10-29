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

package com.liferay.message.boards.web.portlet.action;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.struts.BaseFindActionHelper;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class MessageFindActionHelper extends BaseFindActionHelper {

	@Override
	public long getGroupId(long primaryKey) throws Exception {
		MBMessage message = MBMessageLocalServiceUtil.getMessage(primaryKey);

		return message.getGroupId();
	}

	@Override
	public String getPrimaryKeyParameterName() {
		return "messageId";
	}

	@Override
	public String[] initPortletIds() {

		// Order is important. See LPS-23770.

		return new String[] {
			MBPortletKeys.MESSAGE_BOARDS_ADMIN, MBPortletKeys.MESSAGE_BOARDS
		};
	}

	@Override
	public PortletURL processPortletURL(
			HttpServletRequest request, PortletURL portletURL)
		throws Exception {

		return portletURL;
	}

	@Override
	public void setPrimaryKeyParameter(PortletURL portletURL, long primaryKey)
		throws Exception {

		portletURL.setParameter(
			getPrimaryKeyParameterName(), String.valueOf(primaryKey));
	}

	@Override
	protected void addRequiredParameters(
		HttpServletRequest request, String portletId, PortletURL portletURL) {

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view_message");
	}

}