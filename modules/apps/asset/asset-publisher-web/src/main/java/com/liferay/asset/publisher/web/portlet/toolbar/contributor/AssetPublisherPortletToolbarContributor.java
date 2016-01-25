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

package com.liferay.asset.publisher.web.portlet.toolbar.contributor;

import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.display.context.AssetPublisherDisplayContext;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER,
		"mvc.path=-", "mvc.path=/view.jsp"
	},
	service = {
		AssetPublisherPortletToolbarContributor.class,
		PortletToolbarContributor.class
	}
)
public class AssetPublisherPortletToolbarContributor
	implements PortletToolbarContributor {

	@Override
	public List<Menu> getPortletTitleMenus(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Menu addEntryPortletTitleMenu = getAddEntryPortletTitleMenu(
			portletRequest, portletResponse);

		if (addEntryPortletTitleMenu == null) {
			return Collections.emptyList();
		}

		List<Menu> menus = new ArrayList<>();

		menus.add(addEntryPortletTitleMenu);

		return menus;
	}

	protected void addPortletTitleAddAssetEntryMenuItems(
			List<MenuItem> menuItems, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		AssetPublisherDisplayContext assetPublisherDisplayContext =
			new AssetPublisherDisplayContext(
				PortalUtil.getHttpServletRequest(portletRequest),
				portletRequest.getPreferences());

		if (!assetPublisherDisplayContext.isShowAddContentButton() ||
			(scopeGroup == null) || scopeGroup.isLayoutPrototype() ||
			(scopeGroup.hasStagingGroup() && !scopeGroup.isStagingGroup()) ||
			portletName.equals(
				AssetPublisherPortletKeys.HIGHEST_RATED_ASSETS) ||
			portletName.equals(AssetPublisherPortletKeys.MOST_VIEWED_ASSETS) ||
			portletName.equals(AssetPublisherPortletKeys.RELATED_ASSETS)) {

			return;
		}

		Map<Long, Map<String, PortletURL>> scopeAddPortletURLs =
			_getScopeAddPortletURLs(
				themeDisplay, assetPublisherDisplayContext, portletRequest,
				portletResponse, 1);

		if (MapUtil.isEmpty(scopeAddPortletURLs)) {
			return;
		}

		if (scopeAddPortletURLs.size() == 1) {
			Set<Map.Entry<Long, Map<String, PortletURL>>> entrySet =
				scopeAddPortletURLs.entrySet();

			Iterator<Map.Entry<Long, Map<String, PortletURL>>> iterator =
				entrySet.iterator();

			Map.Entry<Long, Map<String, PortletURL>> scopeAddPortletURL =
				iterator.next();

			long groupId = scopeAddPortletURL.getKey();
			Map<String, PortletURL> addPortletURLs =
				scopeAddPortletURL.getValue();

			for (Map.Entry<String, PortletURL> entry :
					addPortletURLs.entrySet()) {

				URLMenuItem urlMenuItem = _getPortletTitleAddAssetEntryMenuItem(
					themeDisplay, assetPublisherDisplayContext, groupId,
					entry.getKey(), entry.getValue());

				menuItems.add(urlMenuItem);
			}

			return;
		}

		URLMenuItem urlMenuItem = new URLMenuItem();

		Map<String, Object> data = new HashMap<>();

		data.put(
			"id", HtmlUtil.escape(portletDisplay.getNamespace()) + "editAsset");

		String title = LanguageUtil.get(
			themeDisplay.getLocale(), "add-content-select-scope-and-type");

		data.put("title", title);

		urlMenuItem.setData(data);
		urlMenuItem.setIcon("icon-plus-sign-2");

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(portletResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/add_asset_selector.jsp");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		urlMenuItem.setURL(portletURL.toString());
		urlMenuItem.setUseDialog(true);

		menuItems.add(urlMenuItem);
	}

	protected Menu getAddEntryPortletTitleMenu(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		List<MenuItem> portletTitleMenuItems = getPortletTitleMenuItems(
			portletRequest, portletResponse);

		if (ListUtil.isEmpty(portletTitleMenuItems)) {
			return null;
		}

		Menu menu = new Menu();

		menu.setDirection("down");
		menu.setExtended(false);
		menu.setIcon("../aui/plus-sign-2");
		menu.setMenuItems(portletTitleMenuItems);
		menu.setShowArrow(false);

		return menu;
	}

	protected List<MenuItem> getPortletTitleMenuItems(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		List<MenuItem> menuItems = new ArrayList<>();

		try {
			addPortletTitleAddAssetEntryMenuItems(
				menuItems, portletRequest, portletResponse);
		}
		catch (Exception e) {
			_log.error("Unable to add folder menu item", e);
		}

		return menuItems;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private String _getClassName(String className) {
		int pos = className.indexOf(AssetUtil.CLASSNAME_SEPARATOR);

		if (pos != -1) {
			className = className.substring(0, pos);
		}

		return className;
	}

	private String _getMessage(String className, Locale locale) {
		String message = null;

		int pos = className.indexOf(AssetUtil.CLASSNAME_SEPARATOR);

		if (pos != -1) {
			message = className.substring(
				pos + AssetUtil.CLASSNAME_SEPARATOR.length());

			className = className.substring(0, pos);
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (pos == -1) {
			message = assetRendererFactory.getTypeName(locale);
		}

		return message;
	}

	private URLMenuItem _getPortletTitleAddAssetEntryMenuItem(
		ThemeDisplay themeDisplay,
		AssetPublisherDisplayContext assetPublisherDisplayContext, long groupId,
		String className, PortletURL portletURL) {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_getClassName(className));

		String message = _getMessage(className, themeDisplay.getLocale());

		long curGroupId = groupId;

		Group group = _groupLocalService.fetchGroup(groupId);

		if (!group.isStagedPortlet(
				assetRendererFactory.getPortletId()) &&
			!group.isStagedRemotely()) {

			curGroupId = group.getLiveGroupId();
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		boolean addDisplayPageParameter = AssetUtil.isDefaultAssetPublisher(
			themeDisplay.getLayout(), portletDisplay.getId(),
			assetPublisherDisplayContext.getPortletResource());

		String url = _getURL(
			curGroupId, themeDisplay.getPlid(), portletURL,
			assetRendererFactory.getPortletId(), addDisplayPageParameter,
			themeDisplay.getLayout());

		URLMenuItem urlMenuItem = new URLMenuItem();

		urlMenuItem.setLabel(HtmlUtil.escape(message));
		urlMenuItem.setURL(url);
		urlMenuItem.setUseDialog(true);

		return urlMenuItem;
	}

	private Map<Long, Map<String, PortletURL>> _getScopeAddPortletURLs(
			ThemeDisplay themeDisplay,
			AssetPublisherDisplayContext assetPublisherDisplayContext,
			PortletRequest portletRequest, PortletResponse portletResponse,
			int max)
		throws Exception {

		long[] groupIds = assetPublisherDisplayContext.getGroupIds();

		if (groupIds.length == 0) {
			return Collections.emptyMap();
		}

		PortletURL redirectURL = PortletURLFactoryUtil.create(
			portletRequest, AssetPublisherPortletKeys.ASSET_PUBLISHER,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		redirectURL.setParameter(
			"hideDefaultSuccessMessage", Boolean.TRUE.toString());
		redirectURL.setParameter("mvcPath", "/add_asset_redirect.jsp");
		redirectURL.setParameter("redirect", themeDisplay.getURLCurrent());
		redirectURL.setWindowState(LiferayWindowState.POP_UP);

		String redirect = redirectURL.toString();

		Map<Long, Map<String, PortletURL>> scopeAddPortletURLs = new HashMap();

		for (long groupId : groupIds) {
			Map<String, PortletURL> addPortletURLs =
				AssetUtil.getAddPortletURLs(
					(LiferayPortletRequest)portletRequest,
					(LiferayPortletResponse)portletResponse, groupId,
					assetPublisherDisplayContext.getClassNameIds(),
					assetPublisherDisplayContext.getClassTypeIds(),
					assetPublisherDisplayContext.getAllAssetCategoryIds(),
					assetPublisherDisplayContext.getAllAssetTagNames(),
					redirect);

			if (MapUtil.isNotEmpty(addPortletURLs)) {
				scopeAddPortletURLs.put(groupId, addPortletURLs);
			}

			if (scopeAddPortletURLs.size() > max) {
				break;
			}
		}

		return scopeAddPortletURLs;
	}

	private String _getURL(
		long groupId, long plid, PortletURL addPortletURL, String portletId,
		boolean addDisplayPageParameter, Layout layout) {

		addPortletURL.setParameter(
			"hideDefaultSuccessMessage", Boolean.TRUE.toString());
		addPortletURL.setParameter("groupId", String.valueOf(groupId));
		addPortletURL.setParameter("showHeader", Boolean.FALSE.toString());

		String addPortletURLString = addPortletURL.toString();

		addPortletURLString = HttpUtil.addParameter(
			addPortletURLString, "refererPlid", plid);

		String namespace = PortalUtil.getPortletNamespace(portletId);

		if (addDisplayPageParameter) {
			addPortletURLString = HttpUtil.addParameter(
				addPortletURLString, namespace + "layoutUuid",
				layout.getUuid());
		}

		return addPortletURLString;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetPublisherPortletToolbarContributor.class);

	private GroupLocalService _groupLocalService;

}