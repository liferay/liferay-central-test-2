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

package com.liferay.rss.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsProvider;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.rss.web.constants.RSSPortletKeys;
import com.liferay.rss.web.settings.RSSPortletInstanceSettings;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RSSPortletKeys.RSS
	},
	service = ConfigurationAction.class
)
public class RSSConfigurationAction extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		updateSubscriptions(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			RSSPortletInstanceSettings rssPortletInstanceSettings =
				_portletInstanceSettingsProvider.getPortletInstanceSettings(
					themeDisplay.getLayout(), RSSPortletKeys.RSS,
					renderRequest.getParameterMap());

			renderRequest.setAttribute(
				RSSPortletInstanceSettings.class.getName(),
				rssPortletInstanceSettings);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}

		return super.render(portletConfig, renderRequest, renderResponse);
	}

	@Reference(
		target = "(class.name=com.liferay.rss.web.settings.RSSPortletInstanceSettings)"
	)
	protected void setPortletInstanceSettingsProvider(
		PortletInstanceSettingsProvider<RSSPortletInstanceSettings>
			portletInstanceSettingsProvider) {

		_portletInstanceSettingsProvider = portletInstanceSettingsProvider;
	}

	protected void updateSubscriptions(ActionRequest actionRequest)
		throws Exception {

		int[] subscriptionIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "subscriptionIndexes"), 0);

		Map<String, String> subscriptions = new LinkedHashMap<>();

		for (int subscriptionIndex : subscriptionIndexes) {
			String url = ParamUtil.getString(
				actionRequest, "url" + subscriptionIndex);
			String title = ParamUtil.getString(
				actionRequest, "title" + subscriptionIndex);

			if (Validator.isNull(url)) {
				continue;
			}

			subscriptions.put(url, title);
		}

		String[] urls = new String[subscriptions.size()];
		String[] titles = new String[subscriptions.size()];

		int i = 0;

		for (Map.Entry<String, String> entry : subscriptions.entrySet()) {
			urls[i] = entry.getKey();
			titles[i] = entry.getValue();

			i++;
		}

		setPreference(actionRequest, "urls", urls);
		setPreference(actionRequest, "titles", titles);
	}

	private PortletInstanceSettingsProvider<RSSPortletInstanceSettings>
		_portletInstanceSettingsProvider;

}