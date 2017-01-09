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

package com.liferay.adaptive.media.demo.data.creator;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;

import java.io.IOException;

import java.util.Collection;

/**
 * Provides a class for creating adaptive media configurations.
 *
 * @author Alejandro Hernández
 *
 * @review
 */
public interface ImageAdaptiveMediaConfigurationDemoDataCreator {

	/**
	 * Creates a {@link ImageAdaptiveMediaConfigurationEntry} for a certain
	 * company
	 *
	 * @param companyId id of the company where the configuration will be created
	 * @param configurationVariant configuration to be created
	 * @return created configuration
	 *
	 * @review
	 */
	public ImageAdaptiveMediaConfigurationEntry create(
			long companyId,
			DemoImageAdaptiveMediaConfigurationVariant configurationVariant)
		throws IOException;

	/**
	  * Creates all {@link ImageAdaptiveMediaConfigurationEntry} based on the
	  * {@link DemoImageAdaptiveMediaConfigurationVariant} enum
	  *
	  * @param companyId id of the company where the configurations will be
	 *                      created
	  * @return list of created configurations
	 *
	 * @review
	  */
	public Collection<ImageAdaptiveMediaConfigurationEntry> create(
			long companyId)
		throws IOException;

	/**
	 * Deletes the configurations created by this demo data creator
	 *
	 * @review
	 */
	public void delete() throws IOException;

}