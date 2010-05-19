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
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
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
 * <a href="RSSAction.java.html"><b><i>View Source</i></b></a>
 *
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

	protected byte[] getRSS(PortletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = themeDisplay.getScopeGroupId();

		PortletPreferences preferences = request.getPreferences();

		String selectionStyle = preferences.getValue(
			"selection-style", "dynamic");

		int rssDelta = GetterUtil.getInteger(
			preferences.getValue("rss-delta", "20"));
		String rssDisplayStyle = preferences.getValue(
			"rss-display-style", RSSUtil.DISPLAY_STYLE_ABSTRACT);
		String rssFormat = preferences.getValue("rss-format", "atom10");
		String rssName = preferences.getValue("rss-name", null);

		String rssFormatType = RSSUtil.getFormatType(rssFormat);
		double rssFormatVersion = RSSUtil.getFormatVersion(rssFormat);

		Layout layout = themeDisplay.getLayout();

		String instanceId = themeDisplay.getPortletDisplay().getInstanceId();

		StringBundler sb = new StringBundler(5);

		sb.append(PortalUtil.getLayoutURL(layout, themeDisplay));
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("asset_publisher/");
		sb.append(instanceId);
		sb.append("/rss");

		String feedURL =sb.toString();

		sb = new StringBundler(5);

		sb.append(PortalUtil.getLayoutURL(layout, themeDisplay));
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("asset_publisher/");
		sb.append(instanceId);
		sb.append(StringPool.SLASH);

		String entryURL = sb.toString();

		String rss = StringPool.BLANK;

		long[] groupIds = AssetPublisherUtil.getGroupIdsFromPreferences(
			preferences, scopeGroupId, layout);

		long[] availableClassNameIds =
			AssetRendererFactoryRegistryUtil.getClassNameIds();
		long[] classNameIds = AssetPublisherUtil.getClassNameIds(
			preferences, availableClassNameIds);

		if (selectionStyle.equals("dynamic")) {
			boolean excludeZeroViewCount = GetterUtil.getBoolean(
				preferences.getValue("exclude-zero-view-count", "0"));

			AssetEntryQuery assetEntryQuery =
				AssetPublisherUtil.getAssetEntryQuery(
					preferences, scopeGroupId);

			assetEntryQuery.setGroupIds(groupIds);
			assetEntryQuery.setEnd(rssDelta);
			assetEntryQuery.setExcludeZeroViewCount(excludeZeroViewCount);
			assetEntryQuery.setStart(0);

			rss = AssetEntryServiceUtil.getEntriesRSS(
				rssName, assetEntryQuery, rssFormatType, rssFormatVersion,
				rssDisplayStyle, feedURL, entryURL);

		}

		return rss.getBytes(StringPool.UTF8);
	}

}