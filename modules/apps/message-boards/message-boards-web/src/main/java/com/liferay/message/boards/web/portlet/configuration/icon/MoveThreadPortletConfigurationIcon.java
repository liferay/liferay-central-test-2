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
import com.liferay.message.boards.web.portlet.action.ActionUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class MoveThreadPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public MoveThreadPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "move";
	}

	@Override
	public String getURL() {
		try {
			MBMessageDisplay messageDisplay = ActionUtil.getMessageDisplay(
				portletRequest);

			MBCategory category = messageDisplay.getCategory();
			MBThread thread = messageDisplay.getThread();

			PortletURL portletURL = PortletURLFactoryUtil.create(
				portletRequest, MBPortletKeys.MESSAGE_BOARDS_ADMIN,
				themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"mvcRenderCommandName", "/message_boards/move_thread");
			portletURL.setParameter(
				"redirect", PortalUtil.getCurrentURL(portletRequest));
			portletURL.setParameter(
				"mbCategoryId", String.valueOf(getCategoryId(category)));
			portletURL.setParameter(
				"threadId", String.valueOf(thread.getThreadId()));

			return portletURL.toString();
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	@Override
	public boolean isShow() {
		try {
			MBMessageDisplay messageDisplay = ActionUtil.getMessageDisplay(
				portletRequest);

			MBCategory category = messageDisplay.getCategory();

			if (MBCategoryPermission.contains(
					themeDisplay.getPermissionChecker(),
					themeDisplay.getScopeGroupId(), getCategoryId(category),
					ActionKeys.MOVE_THREAD)) {

				return true;
			}
		}
		catch (Exception e) {
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

}