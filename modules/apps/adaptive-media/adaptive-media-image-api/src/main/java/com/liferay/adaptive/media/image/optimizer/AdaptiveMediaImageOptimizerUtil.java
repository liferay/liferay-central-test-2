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

package com.liferay.adaptive.media.image.optimizer;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class AdaptiveMediaImageOptimizerUtil {

	public static void optimize(long companyId) {
		Set<String> modelClassNames = _serviceTrackerMap.keySet();

		for (String modelClassName : modelClassNames) {
			AdaptiveMediaImageOptimizer optimizer =
				_serviceTrackerMap.getService(modelClassName);

			optimizer.optimize(companyId);
		}
	}

	public static void optimize(long companyId, String configurationEntryUuid) {
		Set<String> modelClassNames = _serviceTrackerMap.keySet();

		for (String modelClassName : modelClassNames) {
			AdaptiveMediaImageOptimizer optimizer =
				_serviceTrackerMap.getService(modelClassName);

			optimizer.optimize(companyId, configurationEntryUuid);
		}
	}

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AdaptiveMediaImageOptimizer.class,
			"adaptive.media.key");
	}

	private static ServiceTrackerMap<String, AdaptiveMediaImageOptimizer>
		_serviceTrackerMap;

}