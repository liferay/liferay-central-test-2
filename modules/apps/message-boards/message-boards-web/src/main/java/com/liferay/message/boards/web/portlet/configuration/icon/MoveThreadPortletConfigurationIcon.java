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

import com.liferay.message.boards.kernel.model.MBCategory;
import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.model.MBMessageDisplay;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class MoveThreadPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public MoveThreadPortletConfigurationIcon(
		PortletRequest portletRequest, MBMessageDisplay messageDisplay) {

		super(portletRequest);

		_messageDisplay = messageDisplay;
	}

	@Override
	public String getMessage() {
		return "move";
	}

	@Override
	public String getURL() {
		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			portletRequest, MBPortletKeys.MESSAGE_BOARDS_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/move_thread");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(portletRequest));

		MBCategory category = _messageDisplay.getCategory();

		portletURL.setParameter(
			"mbCategoryId", String.valueOf(getCategoryId(category)));

		MBThread thread = _messageDisplay.getThread();

		portletURL.setParameter(
			"threadId", String.valueOf(thread.getThreadId()));

		return portletURL.toString();
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			MBCategory category = _messageDisplay.getCategory();

			if (MBCategoryPermission.contains(
					themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroupId(), getCategoryId(category),
					ActionKeys.MOVE_THREAD)) {

				return true;
			}
		}
		catch (PortalException pe) {
		}

		return false;
	}

	protected long getCategoryId(MBCategory category) {
		long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

		if (category != null) {
			categoryId = category.getCategoryId();
		}

		return categoryId;
	}

	private final MBMessageDisplay _messageDisplay;

}