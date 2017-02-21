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

package com.liferay.adaptive.media.asset.publisher.web.internal.filter;

import com.liferay.adaptive.media.web.filter.BaseAdaptiveMediaPortletFilter;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;

import javax.portlet.RenderRequest;
import javax.portlet.filter.PortletFilter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.ASSET_PUBLISHER
	},
	service = PortletFilter.class
)
public class AssetPublisherPortletFilter
	extends BaseAdaptiveMediaPortletFilter {

	@Override
	protected boolean mustProcessContent(RenderRequest renderRequest) {
		return true;
	}

}