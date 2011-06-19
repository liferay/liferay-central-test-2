/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.mobile.device.messaging;

import com.liferay.portal.kernel.messaging.BaseDestinationEventListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;

/**
 * @author Milen Dyankov
 * @author Michael C. Han
 */
public class DeviceRecognitionProviderDestinationEventListener
	extends BaseDestinationEventListener {

	@Override
	public void messageListenerRegistered(
		String destinationName, MessageListener messageListener) {

		if (!isProceed(destinationName, messageListener)) {
			return;
		}

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.DEVICE_RECOGNITION_PROVIDER,
			_proxyMessageListener);
	}

	@Override
	public void messageListenerUnregistered(
		String destinationName, MessageListener messageListener) {

		if (!isProceed(destinationName, messageListener)) {
			return;
		}

		MessageBusUtil.registerMessageListener(
			DestinationNames.DEVICE_RECOGNITION_PROVIDER,
			_proxyMessageListener);
	}

	public void setProxyMessageListener(
		ProxyMessageListener proxyMessageListener) {

		_proxyMessageListener = proxyMessageListener;
	}

	protected boolean isProceed(
		String destinationName, MessageListener messageListener) {

		if ((!destinationName.equals(
				DestinationNames.DEVICE_RECOGNITION_PROVIDER)) ||
			(messageListener == _proxyMessageListener) ||
			!(messageListener instanceof ProxyMessageListener)) {

			return false;
		}
		else {
			return true;
		}
	}

	private ProxyMessageListener _proxyMessageListener;

}