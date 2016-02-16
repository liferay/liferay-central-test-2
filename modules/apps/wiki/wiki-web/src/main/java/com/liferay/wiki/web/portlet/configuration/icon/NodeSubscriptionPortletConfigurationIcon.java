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

package com.liferay.wiki.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.SubscriptionLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.wiki.configuration.WikiGroupServiceOverriddenConfiguration;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.permission.WikiNodePermissionChecker;
import com.liferay.wiki.web.display.context.util.WikiRequestHelper;
import com.liferay.wiki.web.portlet.action.ActionUtil;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"path=/wiki/view_pages"
	},
	service = PortletConfigurationIcon.class
)
public class NodeSubscriptionPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		if (isShow(portletRequest)) {
			return "unsubscribe";
		}

		return "subscribe";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			WikiNode node = ActionUtil.getNode(portletRequest);

			PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
				portletRequest, WikiPortletKeys.WIKI_ADMIN,
				PortletRequest.ACTION_PHASE);

			portletURL.setParameter(
				ActionRequest.ACTION_NAME, "/wiki/edit_node");

			if (isSubscribed(portletRequest)) {
				portletURL.setParameter(Constants.CMD, Constants.UNSUBSCRIBE);
			}
			else {
				portletURL.setParameter(Constants.CMD, Constants.SUBSCRIBE);
			}

			portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
			portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

			return portletURL.toString();
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	@Override
	public double getWeight() {
		return 102;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		WikiRequestHelper wikiRequestHelper = new WikiRequestHelper(request);

		WikiGroupServiceOverriddenConfiguration
			wikiGroupServiceOverriddenConfiguration =
				wikiRequestHelper.getWikiGroupServiceOverriddenConfiguration();

		try {
			WikiNode node = ActionUtil.getNode(portletRequest);

			if (WikiNodePermissionChecker.contains(
					themeDisplay.getPermissionChecker(), node,
					ActionKeys.SUBSCRIBE) &&
				(wikiGroupServiceOverriddenConfiguration.emailPageAddedEnabled(
					) ||
				 wikiGroupServiceOverriddenConfiguration.
					 emailPageUpdatedEnabled())) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	protected boolean isSubscribed(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean subscribed = false;

		try {
			WikiNode node = ActionUtil.getNode(portletRequest);

			subscribed = _subscriptionLocalService.isSubscribed(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				WikiNode.class.getName(), node.getNodeId());
		}
		catch (Exception e) {
		}

		return subscribed;
	}

	@Reference(unbind = "-")
	protected void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {

		_subscriptionLocalService = subscriptionLocalService;
	}

	private SubscriptionLocalService _subscriptionLocalService;

}