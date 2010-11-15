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

package com.liferay.portlet.assetpublisher.action;

import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.service.AssetEntryServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.util.RSSUtil;

import java.io.OutputStream;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class RSSAction extends PortletAction {

	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		resourceResponse.setContentType(ContentTypes.TEXT_XML_UTF8);

		OutputStream os = resourceResponse.getPortletOutputStream();

		try {
			os.write(getRSS(resourceRequest));
		}
		finally {
			os.close();
		}
	}

	protected String getEntryURL(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		StringBundler sb = new StringBundler(7);

		sb.append(themeDisplay.getPortalURL());
		sb.append(themeDisplay.getPathContext());
		sb.append(PortalUtil.getLayoutURL(layout, themeDisplay));
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("asset_publisher/");
		sb.append(portletDisplay.getInstanceId());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getFeedURL(PortletRequest portletRequest)
		throws Exception {

		String feedURL = getEntryURL(portletRequest);

		return feedURL.concat("rss");
	}

	protected byte[] getRSS(PortletRequest portletRequest) throws Exception {
		PortletPreferences preferences = portletRequest.getPreferences();

		String selectionStyle = preferences.getValue(
			"selection-style", "dynamic");

		if (!selectionStyle.equals("dynamic")) {
			return new byte[0];
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] groupIds = AssetPublisherUtil.getGroupIds(
			preferences, themeDisplay.getScopeGroupId(),
			themeDisplay.getLayout());

		boolean excludeZeroViewCount = GetterUtil.getBoolean(
			preferences.getValue("exclude-zero-view-count", "0"));

		int rssDelta = GetterUtil.getInteger(
			preferences.getValue("rss-delta", "20"));
		String rssDisplayStyle = preferences.getValue(
			"rss-display-style", RSSUtil.DISPLAY_STYLE_ABSTRACT);
		String rssFormat = preferences.getValue("rss-format", "atom10");
		String rssName = preferences.getValue("rss-name", null);

		String rssFormatType = RSSUtil.getFormatType(rssFormat);
		double rssFormatVersion = RSSUtil.getFormatVersion(rssFormat);

		AssetEntryQuery assetEntryQuery =
			AssetPublisherUtil.getAssetEntryQuery(
				preferences, new long[] {themeDisplay.getScopeGroupId()});

		assetEntryQuery.setEnd(rssDelta);
		assetEntryQuery.setExcludeZeroViewCount(excludeZeroViewCount);
		assetEntryQuery.setGroupIds(groupIds);
		assetEntryQuery.setStart(0);

		String rss = AssetEntryServiceUtil.getEntriesRSS(
			assetEntryQuery, rssName, rssFormatType, rssFormatVersion,
			rssDisplayStyle, getFeedURL(portletRequest),
			getEntryURL(portletRequest));

		return rss.getBytes(StringPool.UTF8);
	}

}