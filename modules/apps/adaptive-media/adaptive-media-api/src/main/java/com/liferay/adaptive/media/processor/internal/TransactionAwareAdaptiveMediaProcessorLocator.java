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

import com.liferay.adaptive.media.AdaptiveMediaProcessorException;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorLocator;
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
@Component(immediate = true, service = AdaptiveMediaProcessorLocator.class)
public class TransactionAwareAdaptiveMediaProcessorLocator
	implements AdaptiveMediaProcessorLocator {

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
	public <M> AdaptiveMediaProcessor<M, ?> locateForClass(Class<M> clazz) {
		return new AggregateAdaptiveMediaProcessor<>(clazz);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TransactionAwareAdaptiveMediaProcessorLocator.class);

	private ServiceTrackerMap<String, List<AdaptiveMediaProcessor>>
		_serviceTrackerMap;

	private interface AdaptiveMediaProcessorAction<M> {

		public void execute(AdaptiveMediaProcessor<M, ?> processor)
			throws Exception;

	}

	private final class AggregateAdaptiveMediaProcessor<M, T>
		implements AdaptiveMediaProcessor<M, T> {

		public AggregateAdaptiveMediaProcessor(Class<M> clazz) {
			_clazz = clazz;
		}

		@Override
		public void cleanUp(M model)
			throws AdaptiveMediaProcessorException, PortalException {

			TransactionCommitCallbackUtil.registerCallback(() ->
				_forEach(processor -> processor.cleanUp(model)));
		}

		@Override
		public void process(M model)
			throws AdaptiveMediaProcessorException, PortalException {

			TransactionCommitCallbackUtil.registerCallback(() ->
				_forEach(processor -> processor.process(model)));
		}

		private Void _forEach(AdaptiveMediaProcessorAction action) {
			List<AdaptiveMediaProcessor> processors =
				_serviceTrackerMap.getService(_clazz.getName());

			if (processors == null) {
				return null;
			}

			for (AdaptiveMediaProcessor<M, ?> processor : processors) {
				try {
					action.execute(processor);
				}
				catch (Exception e) {
					_log.error(e);
				}
			}

			return null;
		}

		private final Class<M> _clazz;

	}

}