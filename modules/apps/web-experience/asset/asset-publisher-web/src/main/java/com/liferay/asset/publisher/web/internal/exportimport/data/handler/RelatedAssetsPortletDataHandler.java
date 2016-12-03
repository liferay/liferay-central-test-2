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

package com.liferay.asset.publisher.web.internal.exportimport.data.handler;

import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;

import org.osgi.service.component.annotations.Component;

/**
 * Provides implementation of {@link PortletDataHandler} for Related Assets
 * portlet which defines specific behaviour of exporting and importing of
 * Liferay data to LAR files when layouts with Related Assets portlet are
 * exported or imported.
 *
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetPublisherPortletKeys.RELATED_ASSETS
	},
	service = PortletDataHandler.class
)
public class RelatedAssetsPortletDataHandler
	extends AssetPublisherPortletDataHandler {
}