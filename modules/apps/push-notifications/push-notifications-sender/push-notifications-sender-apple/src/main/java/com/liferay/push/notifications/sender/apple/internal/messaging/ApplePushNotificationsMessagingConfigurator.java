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

package com.liferay.push.notifications.sender.apple.internal.messaging;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.push.notifications.constants.PushNotificationsDestinationNames;
import com.liferay.push.notifications.service.PushNotificationsDeviceLocalService;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 */
@Component(
	immediate = true,
	service = ApplePushNotificationsMessagingConfigurator.class
)
public class ApplePushNotificationsMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_applePushNotificationsResponseMessageListener =
			new ApplePushNotificationsResponseMessageListener(
				_pushNotificationsDeviceLocalService);

		_destination.register(_applePushNotificationsResponseMessageListener);
	}

	@Deactivate
	protected void deactivate() {
		_destination.unregister(_applePushNotificationsResponseMessageListener);
	}

	private MessageListener _applePushNotificationsResponseMessageListener;

	@Reference(
		target = "(destination.name= " + PushNotificationsDestinationNames.PUSH_NOTIFICATION_RESPONSE + ")"
	)
	private Destination _destination;

	@Reference
	private PushNotificationsDeviceLocalService
		_pushNotificationsDeviceLocalService;

}