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

import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorLocator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import org.osgi.service.component.annotations.Component;

/**
 * This is an {@link AdaptiveMediaProcessorLocator} that will decorate any
 * returned {@link AdaptiveMediaProcessor} so that it executes its logic
 * asynchronously once the current transaction has finished.
 *
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = AdaptiveMediaProcessorLocator.class)
public class AdaptiveMediaProcessorLocatorImpl
	implements AdaptiveMediaProcessorLocator {

	@Override
	public <M> AdaptiveMediaProcessor<M, ?> locateForClass(Class<M> clazz) {
		return new AggregateAdaptiveMediaProcessor<>(clazz);
	}

	private final class AggregateAdaptiveMediaProcessor<M, T>
		implements AdaptiveMediaProcessor<M, T> {

		public AggregateAdaptiveMediaProcessor(Class<M> clazz) {
			_clazz = clazz;
		}

		@Override
		public void cleanUp(M model)
			throws AdaptiveMediaException, PortalException {

			Message message = new Message();

			message.put("className", _clazz.getName());
			message.put("model", model);
			message.put("command", AdaptiveMediaProcessorCommand.CLEAN_UP);

			TransactionCommitCallbackUtil.registerCallback(() -> {
				MessageBusUtil.sendMessage(
					AdaptiveMediaDestinationNames.ADAPTIVE_MEDIA_PROCESSOR,
					message);

				return null;
			});
		}

		@Override
		public void process(M model)
			throws AdaptiveMediaException, PortalException {

			Message message = new Message();

			message.put("className", _clazz.getName());
			message.put("model", model);
			message.put("command", AdaptiveMediaProcessorCommand.PROCESS);

			TransactionCommitCallbackUtil.registerCallback(() -> {
				MessageBusUtil.sendMessage(
					AdaptiveMediaDestinationNames.ADAPTIVE_MEDIA_PROCESSOR,
					message);

				return null;
			});
		}

		private final Class<M> _clazz;

	}

}