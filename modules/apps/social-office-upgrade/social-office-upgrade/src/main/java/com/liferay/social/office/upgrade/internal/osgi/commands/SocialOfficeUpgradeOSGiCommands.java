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

package com.liferay.social.office.upgrade.internal.osgi.commands;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=executeAll",
		"osgi.command.function=hideTasksLayout",
		"osgi.command.function=removeTasksPortlet",
		"osgi.command.function=updateTheme", "osgi.command.scope=socialOffice"
	},
	service = SocialOfficeUpgradeOSGiCommands.class
)
public class SocialOfficeUpgradeOSGiCommands {

	public void executeAll() throws PortalException {
		hideTasksLayout();
		removeTasksPortlet();
		updateTheme();
	}

	public void hideTasksLayout() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_layoutLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				public void addCriteria(DynamicQuery dynamicQuery) {
					dynamicQuery.add(
						RestrictionsFactoryUtil.eq("friendlyURL", "/so/tasks"));
					dynamicQuery.add(
						RestrictionsFactoryUtil.eq("hidden", false));
				}

			});

		final AtomicInteger atomicInteger = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Layout>() {

				public void performAction(Layout layout)
					throws PortalException {

					layout.setHidden(true);

					_layoutLocalService.updateLayout(layout);

					atomicInteger.incrementAndGet();
				}

			});

		actionableDynamicQuery.performActions();

		System.out.printf(
			"[socialOffice:hideTasksLayout] %d layouts updated.%n",
			atomicInteger.get());
	}

	public void removeTasksPortlet() throws PortalException {
		int portletPreferencesCount = _removeTasksPortletPreferences();

		System.out.printf(
			"[socialOffice:removeTasksPortlet] %d Tasks portlet preferences " +
				"deleted.%n",
			portletPreferencesCount);

		int layoutsCount = _removeTasksPortletLayoutTypeSettings();

		System.out.printf(
			"[socialOffice:removeTasksPortlet] Removed tasks portlet from %d " +
				"layouts.%n",
			layoutsCount);
	}

	public void updateTheme() throws PortalException {
		int layoutsCount = _updateLayoutTheme();

		System.out.printf(
			"[socialOffice:updateTheme] %d layouts updated.%n", layoutsCount);

		int layoutSetsCount = _updateLayoutSetTheme();

		System.out.printf(
			"[socialOffice:updateTheme] %d layout sets updated.%n",
			layoutSetsCount);
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		_layoutSetLocalService = layoutSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	private int _removeTasksPortletLayoutTypeSettings() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_layoutLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					dynamicQuery.add(
						RestrictionsFactoryUtil.like(
							"typeSettings", "%tasksportlet%"));
				}

			});

		final AtomicInteger atomicInteger = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Layout>() {

				@Override
				public void performAction(Layout layout)
					throws PortalException {

					UnicodeProperties typeSettingsProperties =
						layout.getTypeSettingsProperties();

					Map<String, String> modifiedProperties = new HashMap<>();

					for (Map.Entry<String, String> entry :
							typeSettingsProperties.entrySet()) {

						String value = entry.getValue();

						if (value.contains("tasksportlet")) {
							String[] portletIds = StringUtil.split(value);

							List<String> newPortletIds = new ArrayList<>();

							for (String portletId : portletIds) {
								if (!portletId.contains("tasksportlet")) {
									newPortletIds.add(portletId);
								}
							}

							modifiedProperties.put(
								entry.getKey(),
								StringUtil.merge(newPortletIds));
						}
					}

					typeSettingsProperties.putAll(modifiedProperties);

					layout.setTypeSettingsProperties(typeSettingsProperties);

					_layoutLocalService.updateLayout(layout);

					atomicInteger.incrementAndGet();
				}

			});

		actionableDynamicQuery.performActions();

		return atomicInteger.get();
	}

	private int _removeTasksPortletPreferences() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_portletPreferencesLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				public void addCriteria(DynamicQuery dynamicQuery) {
					dynamicQuery.add(
						RestrictionsFactoryUtil.like(
							"portletId", "%tasksportlet%"));
				}

			});

		final AtomicInteger atomicInteger = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<PortletPreferences>() {

				public void performAction(PortletPreferences portletPreferences)
					throws PortalException {

					_portletPreferencesLocalService.deletePersistedModel(
						portletPreferences);

					atomicInteger.incrementAndGet();
				}

			});

		actionableDynamicQuery.performActions();

		return atomicInteger.get();
	}

	private int _updateLayoutSetTheme() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_layoutSetLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				public void addCriteria(DynamicQuery dynamicQuery) {
					dynamicQuery.add(
						RestrictionsFactoryUtil.eq(
							"themeId", "so_WAR_sotheme"));
				}

			});

		final AtomicInteger atomicInteger = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<LayoutSet>() {

				public void performAction(LayoutSet layoutSet)
					throws PortalException {

					layoutSet.setThemeId("classic_WAR_classictheme");

					_layoutSetLocalService.updateLayoutSet(layoutSet);

					atomicInteger.incrementAndGet();
				}

			});

		actionableDynamicQuery.performActions();

		return atomicInteger.get();
	}

	private int _updateLayoutTheme() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_layoutLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				public void addCriteria(DynamicQuery dynamicQuery) {
					dynamicQuery.add(
						RestrictionsFactoryUtil.eq(
							"themeId", "so_WAR_sotheme"));
				}

			});

		final AtomicInteger atomicInteger = new AtomicInteger(0);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Layout>() {

				public void performAction(Layout layout)
					throws PortalException {

					layout.setThemeId("classic_WAR_classictheme");

					_layoutLocalService.updateLayout(layout);

					atomicInteger.incrementAndGet();
				}

			});

		actionableDynamicQuery.performActions();

		return atomicInteger.get();
	}

	private LayoutLocalService _layoutLocalService;
	private LayoutSetLocalService _layoutSetLocalService;
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}