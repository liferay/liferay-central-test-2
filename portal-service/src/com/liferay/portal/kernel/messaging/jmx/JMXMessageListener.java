/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.messaging.jmx;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseDestinationEventListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;

import java.util.Collection;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * <a href="JMXMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class JMXMessageListener extends BaseDestinationEventListener {

	public void afterPropertiesSet() throws Exception {
		if ((_mBeanServer == null) || (_messageBus == null)) {
			throw new IllegalStateException(
				"MBean server and message bus are not configured");
		}

		try {
			_replaceMBeanRegistration(
				new MessageBusManager(_messageBus),
				MessageBusManager.createObjectName());
		}
		catch (Exception e) {
			if (log.isWarnEnabled()) {
				log.warn("Unable to register message bus manager", e);
			}
		}

		Collection<Destination> destinations = _messageBus.getDestinations();

		for (Destination destination : destinations) {
			try {
				registerDestination(destination);
			}
			catch (Exception e) {
				if (log.isWarnEnabled()) {
					log.warn(
						"Unable to register destination " +
							destination.getName(),
					e);
				}
			}
		}
	}

	public void destinationAdded(Destination destination) {
		try {
			registerDestination(destination);
		}
		catch (Exception e) {
			log.error(
				"Unable to register destination " + destination.getName(), e);
		}
	}

	public void destinationRemoved(Destination destination) {
		try {
			unregisterDestination(destination);
		}
		catch (Exception e) {
			log.error(
				"Unable to unregister destination " + destination.getName(), e);
		}
	}

	public void destroy() throws Exception {
		Collection<Destination> destinations = _messageBus.getDestinations();

		for (Destination destination : destinations) {
			try {
				unregisterDestination(destination);
			}
			catch (Exception e) {
				if (log.isWarnEnabled()) {
					log.warn(
						"Unable to unregister destination " +
							destination.getName(),
						e);
				}
			}
		}

		try {
			_mBeanServer.unregisterMBean(
				MessageBusManager.createObjectName());
		}
		catch (Exception e) {
			if (log.isWarnEnabled()) {
				log.warn("Unable to unregister message bus manager", e);
			}
		}
	}

	/**
	 * @deprecated {@link #afterPropertiesSet}
	 */
	public void init() throws Exception {
		afterPropertiesSet();
	}

	public void setMBeanServer(MBeanServer mBeanServer) {
		_mBeanServer = mBeanServer;
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	protected void registerDestination(Destination destination)
		throws Exception {

		String destinationName = destination.getName();

		_replaceMBeanRegistration(
			new DestinationManager(destination),
			DestinationManager.createObjectName(destinationName));

		_replaceMBeanRegistration(
			new DestinationStatisticsManager(destination),
			DestinationStatisticsManager.createObjectName(destinationName));
	}

	protected void unregisterDestination(Destination destination)
		throws Exception {

		String destinationName = destination.getName();

		_mBeanServer.unregisterMBean(
			DestinationManager.createObjectName(destinationName));

		_mBeanServer.unregisterMBean(
			DestinationStatisticsManager.createObjectName(destinationName));
	}

	private void _replaceMBeanRegistration(Object object, ObjectName objectName)
		throws Exception {

		try {
			_mBeanServer.registerMBean(object, objectName);
		}
		catch (InstanceAlreadyExistsException iaee) {
			_mBeanServer.unregisterMBean(objectName);

			_mBeanServer.registerMBean(object, objectName);
		}
	}

	private static Log log = LogFactoryUtil.getLog(JMXMessageListener.class);

	private MBeanServer _mBeanServer;
	private MessageBus _messageBus;

}