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

package com.liferay.adaptive.media.processor.internal;

import com.liferay.adaptive.media.processor.MediaProcessor;
import com.liferay.adaptive.media.processor.MediaProcessorException;
import com.liferay.adaptive.media.processor.MediaProcessorLocator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = MediaProcessorLocator.class)
public class TransactionAwareMediaProcessorLocator
	implements MediaProcessorLocator {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, MediaProcessor.class, "(model.class.name=*)",
			(serviceReference, emitter) ->
				emitter.emit(
					(String)serviceReference.getProperty("model.class.name")));
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	@Override
	public <M> MediaProcessor<M, ?> locateForClass(Class<M> clazz) {
		return new AggregateMediaProcessor<>(clazz);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TransactionAwareMediaProcessorLocator.class);

	private ServiceTrackerMap<String, List<MediaProcessor>> _serviceTrackerMap;

	private final class AggregateMediaProcessor<M, T>
		implements MediaProcessor<M, T> {

		public AggregateMediaProcessor(Class<M> clazz) {
			_clazz = clazz;
		}

		@Override
		public void cleanUp(M model)
			throws MediaProcessorException, PortalException {

			TransactionCommitCallbackUtil.registerCallback(() ->
				_forEach(mediaProcessor -> mediaProcessor.cleanUp(model)));
		}

		@Override
		public void process(M model)
			throws MediaProcessorException, PortalException {

			TransactionCommitCallbackUtil.registerCallback(() ->
				_forEach(mediaProcessor -> mediaProcessor.process(model)));
		}

		private Void _forEach(MediaProcessorAction action) {
			List<MediaProcessor> mediaProcessors =
				_serviceTrackerMap.getService(_clazz.getName());

			if (mediaProcessors == null) {
				return null;
			}

			for (MediaProcessor<M, ?> mediaProcessor : mediaProcessors) {
				try {
					action.execute(mediaProcessor);
				}
				catch (Exception e) {
					_log.error(e);
				}
			}

			return null;
		}

		private final Class<M> _clazz;

	}

	private interface MediaProcessorAction<M> {

		public void execute(MediaProcessor<M, ?> mediaProcessor)
			throws Exception;

	}

}