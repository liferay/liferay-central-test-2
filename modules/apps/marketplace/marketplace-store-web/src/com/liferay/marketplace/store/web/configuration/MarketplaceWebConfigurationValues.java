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

package com.liferay.marketplace.store.web.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Ryan Park
 * @author Joan Kim
 */
public class MarketplaceWebConfigurationValues {

	public static final boolean MARKETPLACE_STORE_ENABLED =
		GetterUtil.getBoolean(
			MarketplaceWebConfigurationUtil.get("marketplace.store.enabled"),
			true);

	public static final String MARKETPLACE_URL = GetterUtil.getString(
		MarketplaceWebConfigurationUtil.get("marketplace.url"));

}