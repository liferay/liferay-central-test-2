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

package com.liferay.adaptive.media.image.jaxrs;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationHelper;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = ImageAdaptiveMediaRootResource.class)
@Path("/images")
public class ImageAdaptiveMediaRootResource {

	@Path("/configuration/{companyId}")
	public ImageAdaptiveMediaConfigResource getConfiguration(
		@PathParam("companyId") long companyId) {

		return new ImageAdaptiveMediaConfigResource(
			companyId, imageAdaptiveMediaConfigurationHelper);
	}

	@Reference
	protected ImageAdaptiveMediaConfigurationHelper
		imageAdaptiveMediaConfigurationHelper;

}