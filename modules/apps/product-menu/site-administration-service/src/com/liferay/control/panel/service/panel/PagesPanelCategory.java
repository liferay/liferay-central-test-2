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

package com.liferay.control.panel.service.panel;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.productivity.center.panel.BasePanelCategory;
import com.liferay.productivity.center.panel.PanelCategory;
import com.liferay.productivity.center.panel.constants.PanelCategoryKeys;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION,
		"service.ranking:Integer=100"
	},
	service = PanelCategory.class
)
public class PagesPanelCategory extends BasePanelCategory {

	@Override
	public String getIconCssClass() {
		return "icon-sitemap";
	}

	@Override
	public String getKey() {
		return PanelCategoryKeys.PAGES;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "category.site_administration.pages");
	}

}