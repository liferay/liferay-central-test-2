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

package com.liferay.adaptive.media.web.internal.servlet;

import com.liferay.adaptive.media.handler.AdaptiveMediaRequestHandler;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = AdaptiveMediaRequestHandlerLocator.class)
public class AdaptiveMediaRequestHandlerLocator {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AdaptiveMediaRequestHandler.class,
			"(adaptive.media.handler.pattern=*)",
			(serviceReference, emitter) -> emitter.emit(
				(String)serviceReference.getProperty(
					"adaptive.media.handler.pattern")));
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	public AdaptiveMediaRequestHandler locateForPattern(String pattern) {
		return _serviceTrackerMap.getService(pattern);
	}

	private ServiceTrackerMap<String, AdaptiveMediaRequestHandler>
		_serviceTrackerMap;

}