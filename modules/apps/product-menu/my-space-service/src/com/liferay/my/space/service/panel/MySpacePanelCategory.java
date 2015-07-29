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

package com.liferay.my.space.service.panel;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.productivity.center.panel.BasePanelCategory;
import com.liferay.productivity.center.panel.PanelCategory;
import com.liferay.productivity.center.panel.constants.PanelCategoryKeys;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {"panel.category.key=" + PanelCategoryKeys.ROOT},
	service = PanelCategory.class
)
public class MySpacePanelCategory extends BasePanelCategory {

	@Override
	public String getIconCssClass() {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		return PanelCategoryKeys.MY_SPACE;
	}

	@Override
	public String getLabel(Locale locale) {
		return StringPool.BLANK;
	}

}