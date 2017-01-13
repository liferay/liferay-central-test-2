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

package com.liferay.mentions.internal.util;

import com.liferay.mentions.matcher.MentionsMatcher;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(service = MentionsMatcherRegistry.class)
public class MentionsMatcherRegistry {

	public MentionsMatcher getMentionsMatcher(String className) {
		MentionsMatcher mentionsMatcher = _serviceTrackerMap.getService(
			className);

		if (mentionsMatcher != null) {
			return mentionsMatcher;
		}

		MentionsMatcher defaultMentionsMatcher = _serviceTrackerMap.getService(
			"*");

		if (defaultMentionsMatcher == null) {
			throw new IllegalStateException(
				"Unable to get default mentions matcher");
		}

		return defaultMentionsMatcher;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, MentionsMatcher.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, MentionsMatcher> _serviceTrackerMap;

}