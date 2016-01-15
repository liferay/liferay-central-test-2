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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.trash.util.TrashUtil;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class DeleteThreadPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public DeleteThreadPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		if (isTrashEnabled(themeDisplay.getScopeGroupId())) {
			return "move-to-the-recycle-bin";
		}

		return "delete";
	}

	@Override
	public String getURL() {
		try {
			PortletURL deleteURL = PortalUtil.getControlPanelPortletURL(
				portletRequest, MBPortletKeys.MESSAGE_BOARDS_ADMIN,
				PortletRequest.ACTION_PHASE);

			deleteURL.setParameter(
				ActionRequest.ACTION_NAME, "/message_boards/delete_thread");

			String cmd = Constants.DELETE;

			if (isTrashEnabled(themeDisplay.getScopeGroupId())) {
				cmd = Constants.MOVE_TO_TRASH;
			}

			deleteURL.setParameter(Constants.CMD, cmd);

			MBMessageDisplay messageDisplay = ActionUtil.getMessageDisplay(
				portletRequest);

			MBCategory category = messageDisplay.getCategory();

			PortletURL parentCategoryURL = PortletURLFactoryUtil.create(
				portletRequest, MBPortletKeys.MESSAGE_BOARDS_ADMIN,
				themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

			long categoryId = getCategoryId(category);

			if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
				parentCategoryURL.setParameter(
					"mvcRenderCommandName", "/message_boards/view");
			}
			else {
				parentCategoryURL.setParameter(
					"mvcRenderCommandName", "/message_boards/view_category");
				parentCategoryURL.setParameter(
					"mbCategoryId", String.valueOf(categoryId));
			}

			deleteURL.setParameter("redirect", parentCategoryURL.toString());

			MBThread thread = messageDisplay.getThread();

			deleteURL.setParameter(
				"threadId", String.valueOf(thread.getThreadId()));

			return deleteURL.toString();
		}
		catch (PortalException pe) {
		}

		return StringPool.BLANK;
	}

	@Override
	public boolean isShow() {
		try {
			MBMessageDisplay messageDisplay = ActionUtil.getMessageDisplay(
				portletRequest);

			MBMessage message = messageDisplay.getMessage();
			MBThread thread = messageDisplay.getThread();

			if (MBMessagePermission.contains(
					themeDisplay.getPermissionChecker(), message,
					ActionKeys.DELETE) &&
				!thread.isLocked()) {

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

	protected boolean isTrashEnabled(long groupId) {
		try {
			if (TrashUtil.isTrashEnabled(groupId)) {
				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

}