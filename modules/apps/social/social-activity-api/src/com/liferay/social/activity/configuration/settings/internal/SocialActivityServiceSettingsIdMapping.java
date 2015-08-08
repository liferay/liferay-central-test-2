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

package com.liferay.social.activity.configuration.settings.internal;

import com.liferay.portal.kernel.settings.definition.SettingsIdMapping;
import com.liferay.portlet.social.constants.SocialConstants;
import com.liferay.social.activity.configuration.SocialActivityGroupServiceConfiguration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component
public class SocialActivityServiceSettingsIdMapping
	implements SettingsIdMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return SocialActivityGroupServiceConfiguration.class;
	}

	@Override
	public String getSettingsId() {
		return SocialConstants.SERVICE_NAME;
	}

}