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

package com.liferay.portlet.assetpublisher.action;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.AssetEntryServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;

import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
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
			os.write(getRSS(resourceRequest, resourceResponse));
		}
		finally {
			os.close();
		}
	}

	protected String exportToRSS(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String name, String description, String type, double version,
			String displayStyle, String assetLinkBehavior,
			List<AssetEntry> assetEntries)
		throws Exception {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));
		syndFeed.setTitle(name);
		syndFeed.setLink(getFeedURL(portletRequest));
		syndFeed.setDescription(GetterUtil.getString(description, name));

		List<SyndEntry> entries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(entries);

		for (AssetEntry entry : assetEntries) {
			String link = getEntryURL(
				portletRequest, portletResponse, entry, assetLinkBehavior);

			String author = HtmlUtil.escape(
				PortalUtil.getUserName(entry.getUserId(), entry.getUserName()));

			String value = null;

			if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else {
				value = entry.getSummary();
			}

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setAuthor(author);
			syndEntry.setTitle(entry.getTitle());
			syndEntry.setLink(link);
			syndEntry.setUri(syndEntry.getLink());
			syndEntry.setPublishedDate(entry.getCreateDate());
			syndEntry.setUpdatedDate(entry.getModifiedDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.DEFAULT_ENTRY_TYPE);
			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			entries.add(syndEntry);
		}

		return RSSUtil.export(syndFeed);
	}

	protected String getAssetPublisherURL(
		PortletRequest portletRequest) throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		StringBundler sb = new StringBundler(7);

		String layoutFriendlyURL = GetterUtil.getString(
			PortalUtil.getLayoutFriendlyURL(layout, themeDisplay));

		if (!layoutFriendlyURL.startsWith(Http.HTTP_WITH_SLASH) &&
			!layoutFriendlyURL.startsWith(Http.HTTPS_WITH_SLASH)) {

			sb.append(themeDisplay.getPortalURL());
		}

		sb.append(layoutFriendlyURL);
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("asset_publisher/");
		sb.append(portletDisplay.getInstanceId());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected String getEntryURL(
			PortletRequest portletRequest, PortletResponse portletResponse,
			AssetEntry entry, String assetLinkBehavior)
		throws Exception {

		if ("viewInPortlet".equals(assetLinkBehavior)) {
			return getEntryURLViewInContext(
				portletRequest, portletResponse, entry);
		}
		else {
			return getEntryURLAssetPublisher(
				portletRequest, portletResponse, entry);
		}
	}

	protected String getEntryURLAssetPublisher(
			PortletRequest portletRequest, PortletResponse portletResponse,
			AssetEntry entry)
		throws Exception {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				entry.getClassName());

		StringBundler sb = new StringBundler(4);

		sb.append(getAssetPublisherURL(portletRequest));
		sb.append(assetRendererFactory.getType());
		sb.append("/id/");
		sb.append(entry.getEntryId());

		return sb.toString();
	}

	protected String getEntryURLViewInContext(
			PortletRequest portletRequest, PortletResponse portletResponse,
			AssetEntry entry)
		throws Exception {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				entry.getClassName());

		AssetRenderer assetRenderer =
			assetRendererFactory.getAssetRenderer(entry.getClassPK());

		String viewInContextURL = assetRenderer.getURLViewInContext(
			(LiferayPortletRequest)portletRequest,
			(LiferayPortletResponse)portletResponse, null);

		StringBundler sb = new StringBundler(2);

		if (!viewInContextURL.startsWith(Http.HTTP_WITH_SLASH) &&
			!viewInContextURL.startsWith(Http.HTTPS_WITH_SLASH)) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)portletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				sb.append(themeDisplay.getPortalURL());
		}

		sb.append(viewInContextURL);

		return sb.toString();
	}

	protected String getFeedURL(PortletRequest portletRequest)
		throws Exception {

		String feedURL = getAssetPublisherURL(portletRequest);

		return feedURL.concat("rss");
	}

	protected byte[] getRSS(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		PortletPreferences preferences = portletRequest.getPreferences();

		String selectionStyle = preferences.getValue(
			"selectionStyle", "dynamic");

		if (!selectionStyle.equals("dynamic")) {
			return new byte[0];
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] groupIds = AssetPublisherUtil.getGroupIds(
			preferences, themeDisplay.getScopeGroupId(),
			themeDisplay.getLayout());

		String assetLinkBehavior = preferences.getValue(
			"assetLinkBehaviour", "showFullContent");
		boolean excludeZeroViewCount = GetterUtil.getBoolean(
			preferences.getValue("excludeZeroViewCount", "0"));

		int rssDelta = GetterUtil.getInteger(
			preferences.getValue("rssDelta", "20"));
		String rssDisplayStyle = preferences.getValue(
			"rssDisplayStyle", RSSUtil.DISPLAY_STYLE_ABSTRACT);
		String rssFormat = preferences.getValue("rssFormat", "atom10");
		String rssName = preferences.getValue("rssName", null);

		String rssFormatType = RSSUtil.getFormatType(rssFormat);
		double rssFormatVersion = RSSUtil.getFormatVersion(rssFormat);

		AssetEntryQuery assetEntryQuery =
			AssetPublisherUtil.getAssetEntryQuery(
				preferences, new long[] {themeDisplay.getScopeGroupId()});

		assetEntryQuery.setEnd(rssDelta);
		assetEntryQuery.setExcludeZeroViewCount(excludeZeroViewCount);
		assetEntryQuery.setGroupIds(groupIds);
		assetEntryQuery.setStart(0);

		List<AssetEntry> assetEntries = AssetEntryServiceUtil.getEntries(
			assetEntryQuery);

		String rss = exportToRSS(
			portletRequest, portletResponse, rssName, null, rssFormatType,
			rssFormatVersion, rssDisplayStyle, assetLinkBehavior, assetEntries);

		return rss.getBytes(StringPool.UTF8);
	}

}