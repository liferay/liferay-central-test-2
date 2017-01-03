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

package com.liferay.adaptive.media.demo.data.creator.internal;

import com.liferay.adaptive.media.demo.data.creator.DemoImageAdaptiveMediaConfiguration;
import com.liferay.adaptive.media.demo.data.creator.ImageAdaptiveMediaConfigurationDemoDataCreator;
import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;

import java.io.IOException;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(service = ImageAdaptiveMediaConfigurationDemoDataCreator.class)
public class ImageAdaptiveMediaConfigurationDemoDataCreatorImpl
	implements ImageAdaptiveMediaConfigurationDemoDataCreator {

	@Override
	public Collection<ImageAdaptiveMediaConfigurationEntry> create(
			long companyId)
		throws IOException {

	}

	@Override
	public ImageAdaptiveMediaConfigurationEntry create(
			long companyId, DemoImageAdaptiveMediaConfiguration configuration)
		throws IOException {

	}

	@Override
	public void delete() throws IOException {

	}

}