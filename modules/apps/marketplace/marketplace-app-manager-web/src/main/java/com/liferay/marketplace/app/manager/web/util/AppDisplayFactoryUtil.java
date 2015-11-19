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

package com.liferay.marketplace.app.manager.web.util;

import com.liferay.marketplace.app.manager.web.constants.BundleConstants;
import com.liferay.marketplace.model.App;
import com.liferay.marketplace.model.Module;
import com.liferay.marketplace.service.AppLocalService;
import com.liferay.marketplace.service.ModuleLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ryan Park
 */
@Component
public class AppDisplayFactoryUtil {

	public static AppDisplay getAppDisplay(List<Bundle> bundles, long appId) {
		try {
			BundlesMap bundlesMap = new BundlesMap(bundles.size());

			bundlesMap.load(bundles);

			App app = _appLocalService.getApp(appId);

			return createMarketplaceAppDisplay(bundlesMap, app);
		}
		catch (PortalException pe) {
			return null;
		}
	}

	public static AppDisplay getAppDisplay(
		List<Bundle> bundles, String appTitle) {

		AppDisplay appDisplay = new AppDisplay(appTitle);

		if (appTitle.equals(AppDisplay.APP_NAME_UNCATEGORIZED)) {
			appTitle = null;
		}

		for (Bundle bundle : bundles) {
			Dictionary<String, String> headers = bundle.getHeaders();

			String curAppTitle = headers.get(
				BundleConstants.LIFERAY_RELENG_APP_TITLE);

			if (Validator.isNotNull(appTitle) &&
				!appTitle.equals(curAppTitle)) {

				continue;
			}
			else if (curAppTitle != null) {
				continue;
			}

			appDisplay.addBundle(bundle);
		}

		return appDisplay;
	}

	public static List<AppDisplay> getAppDisplays(
		List<Bundle> bundles, String category, int state) {

		List<AppDisplay> appDisplays = new ArrayList<>();

		BundlesMap bundlesMap = new BundlesMap(bundles.size());

		bundlesMap.load(bundles);

		appDisplays.addAll(createMarketplaceAppDisplays(bundlesMap, category));
		appDisplays.addAll(createPortalAppDisplays(bundlesMap, category));

		filterAppDisplays(appDisplays, state);

		return ListUtil.sort(appDisplays);
	}

	protected static AppDisplay createMarketplaceAppDisplay(
		BundlesMap bundlesMap, App app) {

		AppDisplay appDisplay = new AppDisplay(app.getTitle());

		List<Module> modules = _moduleLocalService.getModules(app.getAppId());

		for (Module module : modules) {
			Bundle bundle = bundlesMap.get(module);

			if (bundle != null) {
				appDisplay.addBundle(bundle);
			}
		}

		return appDisplay;
	}

	protected static List<AppDisplay> createMarketplaceAppDisplays(
		BundlesMap bundlesMap, String category) {

		List<AppDisplay> appDisplays = new ArrayList<>();

		Set<Bundle> removeBundles = new HashSet<>();

		List<App> apps = null;

		if (Validator.isNotNull(category)) {
			apps = _appLocalService.getApps(category);
		}
		else {
			apps = _appLocalService.getApps(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}

		for (App app : apps) {
			AppDisplay appDisplay = createMarketplaceAppDisplay(
				bundlesMap, app);

			appDisplays.add(appDisplay);

			removeBundles.addAll(appDisplay.getBundles());
		}

		for (Bundle bundle : removeBundles) {
			bundlesMap.removeBundle(bundle);
		}

		return appDisplays;
	}

	protected static List<AppDisplay> createPortalAppDisplays(
		BundlesMap bundlesMap, String category) {

		Map<String, AppDisplay> appDisplaysMap = new HashMap<>();

		Collection<Bundle> bundles = bundlesMap.values();

		for (Bundle bundle : bundles) {
			Dictionary<String, String> headers = bundle.getHeaders();

			String[] categories = StringUtil.split(
				headers.get(BundleConstants.LIFERAY_RELENG_CATEGORY));

			if (!ArrayUtil.contains(categories, category)) {
				continue;
			}

			String appTitle = headers.get(
				BundleConstants.LIFERAY_RELENG_APP_TITLE);

			if (appTitle == null) {
				appTitle = AppDisplay.APP_NAME_UNCATEGORIZED;
			}

			AppDisplay appDisplay = appDisplaysMap.get(appTitle);

			if (appDisplay == null) {
				appDisplay = new AppDisplay(appTitle);

				appDisplaysMap.put(appTitle, appDisplay);
			}

			appDisplay.addBundle(bundle);
		}

		return ListUtil.fromMapValues(appDisplaysMap);
	}

	protected static void filterAppDisplays(
		List<AppDisplay> appDisplays, int state) {

		Iterator<AppDisplay> iterator = appDisplays.iterator();

		while (iterator.hasNext()) {
			AppDisplay appDisplay = iterator.next();

			if (appDisplay.getState() != state) {
				iterator.remove();
			}
		}
	}

	@Reference(unbind = "-")
	protected void setAppLocalService(AppLocalService appLocalService) {
		_appLocalService = appLocalService;
	}

	@Reference(unbind = "-")
	protected void setModuleLocalService(
		ModuleLocalService moduleLocalService) {

		_moduleLocalService = moduleLocalService;
	}

	private static AppLocalService _appLocalService;
	private static ModuleLocalService _moduleLocalService;

}