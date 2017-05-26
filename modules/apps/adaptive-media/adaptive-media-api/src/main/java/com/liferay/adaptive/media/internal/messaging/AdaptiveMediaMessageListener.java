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

package com.liferay.adaptive.media.internal.messaging;

import com.liferay.adaptive.media.processor.AdaptiveMediaProcessor;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"destination.name=" + AdaptiveMediaDestinationNames.ADAPTIVE_MEDIA_PROCESSOR
	},
	service = MessageListener.class
)
public class AdaptiveMediaMessageListener extends BaseMessageListener {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, AdaptiveMediaProcessor.class, "(model.class.name=*)",
			(serviceReference, emitter) ->
				emitter.emit(
					(String)serviceReference.getProperty("model.class.name")));
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String className = message.getString("className");

		List<AdaptiveMediaProcessor> processors = _serviceTrackerMap.getService(
			className);

		if (processors == null) {
			return;
		}

		AdaptiveMediaProcessorCommand command =
			(AdaptiveMediaProcessorCommand)message.get("command");

		Object model = message.get("model");

		for (AdaptiveMediaProcessor processor : processors) {
			try {
				command.execute(processor, model);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdaptiveMediaMessageListener.class);

	private ServiceTrackerMap<String, List<AdaptiveMediaProcessor>>
		_serviceTrackerMap;

}