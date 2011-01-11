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

package com.liferay.portlet.messageboards.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 */
public class MBMessageAssetRenderer extends BaseAssetRenderer {

	public MBMessageAssetRenderer(MBMessage message) {
		_message = message;
	}

	public long getClassPK() {
		return _message.getMessageId();
	}

	public long getGroupId() {
		return _message.getGroupId();
	}

	public String getSummary(Locale locale) {
		return HtmlUtil.stripHtml(_message.getBody());
	}

	public String getTitle(Locale locale) {
		return _message.getSubject();
	}

	public PortletURL getURLEdit(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createRenderURL(
			PortletKeys.MESSAGE_BOARDS);

		portletURL.setParameter(
			"struts_action", "/message_boards/edit_message");
		portletURL.setParameter(
			"messageId", String.valueOf(_message.getMessageId()));

		return portletURL;
	}
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
			"/message_boards/find_message?messageId=" + _message.getMessageId();
	}

	public long getUserId() {
		return _message.getUserId();
	}

	public String getUuid() {
		return _message.getUuid();
	}

	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		if (_message.isDiscussion()) {
			return MBDiscussionPermission.contains(
				permissionChecker, _message.getCompanyId(),
				_message.getGroupId(), _message.getClassName(),
				_message.getClassPK(), _message.getMessageId(),
				_message.getUserId(), ActionKeys.UPDATE);
		}
		else {
			return MBMessagePermission.contains(
				permissionChecker, _message, ActionKeys.UPDATE);
		}
	}

	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		if (_message.isDiscussion()) {
			return MBDiscussionPermission.contains(
				permissionChecker, _message.getCompanyId(),
				_message.getGroupId(), _message.getClassName(),
				_message.getClassPK(), _message.getMessageId(),
				_message.getUserId(), ActionKeys.VIEW);
		}
		else {
			return MBMessagePermission.contains(
				permissionChecker, _message, ActionKeys.VIEW);
		}
	}

	public boolean isPrintable() {
		return true;
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(
				WebKeys.MESSAGE_BOARDS_MESSAGE, _message);

			return "/html/portlet/message_boards/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/conversation.png";
	}

	private MBMessage _message;

}