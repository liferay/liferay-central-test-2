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

package com.liferay.portal.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Eudaldo Alonso
 */
public class DefaultLayoutPrototypesUtil {

	public static Layout addLayout(
			LayoutSet layoutSet, String nameKey, String friendlyURL,
			String layouteTemplateId)
		throws Exception {

		Group group = layoutSet.getGroup();

		Map<Locale, String> nameMap = new HashMap<>();

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			nameMap.put(locale, LanguageUtil.get(locale, nameKey));
		}

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.getDefault(), friendlyURL);

		ServiceContext serviceContext = new ServiceContext();

		Layout layout = LayoutLocalServiceUtil.addLayout(
			group.getCreatorUserId(), group.getGroupId(),
			layoutSet.isPrivateLayout(),
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, nameMap, null, null, null,
			null, LayoutConstants.TYPE_PORTLET, StringPool.BLANK, false,
			friendlyURLMap, serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(0, layouteTemplateId, false);

		return layout;
	}

	public static Layout addLayoutPrototype(
			long companyId, long defaultUserId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String layouteTemplateId,
			List<LayoutPrototype> layoutPrototypes)
		throws Exception {

		String name = nameMap.get(LocaleUtil.getDefault());
		String description = descriptionMap.get(LocaleUtil.getDefault());

		for (LayoutPrototype layoutPrototype : layoutPrototypes) {
			String curName = layoutPrototype.getName(LocaleUtil.getDefault());
			String curDescription = layoutPrototype.getDescription(
				LocaleUtil.getDefault());

			if (name.equals(curName) && description.equals(curDescription)) {
				return null;
			}
		}

		LayoutPrototype layoutPrototype =
			LayoutPrototypeLocalServiceUtil.addLayoutPrototype(
				defaultUserId, companyId, nameMap, descriptionMap, true,
				new ServiceContext());

		Layout layout = layoutPrototype.getLayout();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(0, layouteTemplateId, false);

		return layout;
	}

	public static String addPortletId(
			Layout layout, String portletId, String columnId)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		portletId = layoutTypePortlet.addPortletId(
			0, portletId, columnId, -1, false);

		updateLayout(layout);

		addResourcePermissions(layout, portletId);

		return portletId;
	}

	public static PortletPreferences updatePortletSetup(
			Layout layout, String portletId, Map<String, String> preferences)
		throws Exception {

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletId);

		for (Map.Entry<String, String> entry : preferences.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			portletSetup.setValue(key, value);
		}

		portletSetup.store();

		return portletSetup;
	}

	protected static void addResourcePermissions(
			Layout layout, String portletId)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			layout.getCompanyId(), portletId);

		PortalUtil.addPortletDefaultResource(
			layout.getCompanyId(), layout, portlet);
	}

	protected static void updateLayout(Layout layout) throws Exception {
		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

}