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

package com.liferay.site.navigation.breadcrumb.web.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Eudaldo Alonso
 */
public class BreadcrumbConfigurationValues {

	public static final String DDM_TEMPLATE_KEY_DEFAULT = GetterUtil.getString(
		BreadcrumbWebConfigurationUtil.get("ddm.template.key.default"));

	public static final String DISPLAY_TEMPLATES_CONFIG = GetterUtil.getString(
		BreadcrumbWebConfigurationUtil.get("display.templates.config"));

	public static final boolean SHOW_GUEST_GROUP = GetterUtil.getBoolean(
		BreadcrumbWebConfigurationUtil.get("show.guest.group"));

	public static final boolean SHOW_PARENT_GROUPS = GetterUtil.getBoolean(
		BreadcrumbWebConfigurationUtil.get("show.parent.groups"));

}