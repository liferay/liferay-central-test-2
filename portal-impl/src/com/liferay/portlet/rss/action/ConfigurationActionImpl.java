/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.rss.action;

import com.liferay.portal.kernel.portlet.BaseConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurationActionImpl extends BaseConfigurationAction {

	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		if (cmd.equals("remove-footer-article")) {
			removeFooterArticle(actionRequest, preferences);
		}
		else if (cmd.equals("remove-header-article")) {
			removeHeaderArticle(actionRequest, preferences);
		}
		else if (cmd.equals("set-footer-article")) {
			setFooterArticle(actionRequest, preferences);
		}
		else if (cmd.equals("set-header-article")) {
			setHeaderArticle(actionRequest, preferences);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			updateConfiguration(actionRequest, preferences);
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			try {
				preferences.store();
			}
			catch (ValidatorException ve) {
				SessionErrors.add(
					actionRequest, ValidatorException.class.getName(), ve);

				return;
			}

			SessionMessages.add(
				actionRequest, portletConfig.getPortletName() + ".doConfigure");
		}
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/rss/configuration.jsp";
	}

	protected void removeFooterArticle(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		preferences.setValues(
			"footer-article-resource-values", new String[] {"0", ""});
	}

	protected void removeHeaderArticle(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		preferences.setValues(
			"header-article-resource-values", new String[] {"0", ""});
	}

	protected void setFooterArticle(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String footerArticleResourcePrimKey = ParamUtil.getString(
			actionRequest, "resourcePrimKey");
		String footerArticleResouceTitle = ParamUtil.getString(
			actionRequest, "resourceTitle");

		preferences.setValues(
			"footer-article-resource-values",
			new String[] {
				footerArticleResourcePrimKey, footerArticleResouceTitle
			});
	}

	protected void setHeaderArticle(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String headerArticleResourcePrimKey = ParamUtil.getString(
			actionRequest, "resourcePrimKey");
		String headerArticleResouceTitle = ParamUtil.getString(
			actionRequest, "resourceTitle");

		preferences.setValues(
			"header-article-resource-values",
		new String[] {headerArticleResourcePrimKey, headerArticleResouceTitle});
	}

	protected void updateConfiguration(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		int[] subscriptionIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "subscriptionIndexes"), 0);

		Map<String, String> subscriptions = new HashMap<String, String>();

		for (int i = 0; i < subscriptionIndexes.length; i++) {
			int subscriptionIndex = subscriptionIndexes[i];

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

		int entriesPerFeed = ParamUtil.getInteger(
			actionRequest, "entriesPerFeed", 4);
		int expandedEntriesPerFeed = ParamUtil.getInteger(
			actionRequest, "expandedEntriesPerFeed", 1);
		boolean showFeedTitle = ParamUtil.getBoolean(
			actionRequest, "showFeedTitle");
		boolean showFeedPublishedDate = ParamUtil.getBoolean(
			actionRequest, "showFeedPublishedDate");
		boolean showFeedDescription = ParamUtil.getBoolean(
			actionRequest, "showFeedDescription");
		boolean showFeedImage = ParamUtil.getBoolean(
			actionRequest, "showFeedImage");
		String feedImageAlignment = ParamUtil.getString(
			actionRequest, "feedImageAlignment");
		boolean showFeedItemAuthor = ParamUtil.getBoolean(
			actionRequest, "showFeedItemAuthor");

		preferences.setValues("urls", urls);
		preferences.setValues("titles", titles);
		preferences.setValue(
			"items-per-channel", String.valueOf(entriesPerFeed));
		preferences.setValue(
			"expanded-items-per-channel",
			String.valueOf(expandedEntriesPerFeed));
		preferences.setValue("show-feed-title", String.valueOf(showFeedTitle));
		preferences.setValue(
			"show-feed-published-date", String.valueOf(showFeedPublishedDate));
		preferences.setValue(
			"show-feed-description", String.valueOf(showFeedDescription));
		preferences.setValue("show-feed-image", String.valueOf(showFeedImage));
		preferences.setValue(
			"feed-image-alignment", String.valueOf(feedImageAlignment));
		preferences.setValue(
			"show-feed-item-author", String.valueOf(showFeedItemAuthor));
	}

}