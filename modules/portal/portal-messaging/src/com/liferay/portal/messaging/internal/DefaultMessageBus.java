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

package com.liferay.portal.messaging.internal;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusEventListener;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.nio.intraband.messaging.IntrabandBridgeDestination;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = MessageBus.class)
public class DefaultMessageBus implements MessageBus {

	@Override
	public synchronized void addDestination(Destination destination) {
		Class<?> clazz = destination.getClass();

		if (SPIUtil.isSPI() &&
			!clazz.equals(IntrabandBridgeDestination.class)) {

			destination = new IntrabandBridgeDestination(destination);
		}

		_destinations.put(destination.getName(), destination);

		for (MessageBusEventListener messageBusEventListener :
				_messageBusEventListeners) {

			messageBusEventListener.destinationAdded(destination);
		}
	}

	@Override
	public boolean addMessageBusEventListener(
		MessageBusEventListener messageBusEventListener) {

		return _messageBusEventListeners.add(messageBusEventListener);
	}

	@Override
	public Destination getDestination(String destinationName) {
		return _destinations.get(destinationName);
	}

	@Override
	public int getDestinationCount() {
		return _destinations.size();
	}

	@Override
	public Collection<String> getDestinationNames() {
		return _destinations.keySet();
	}

	@Override
	public Collection<Destination> getDestinations() {
		return _destinations.values();
	}

	@Override
	public boolean hasDestination(String destinationName) {
		return _destinations.containsKey(destinationName);
	}

	@Override
	public boolean hasMessageListener(String destinationName) {
		Destination destination = _destinations.get(destinationName);

		if ((destination != null) && destination.isRegistered()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public synchronized boolean registerMessageListener(
		String destinationName, MessageListener messageListener) {

		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			throw new IllegalStateException(
				"Destination " + destinationName + " is not configured");
		}

		return destination.register(messageListener);
	}

	@Override
	public Destination removeDestination(String destinationName) {
		return removeDestination(destinationName, true);
	}

	@Override
	public synchronized Destination removeDestination(
		String destinationName, boolean closeOnRemove) {

		Destination destination = _destinations.remove(destinationName);

		if (destination != null) {
			if (closeOnRemove) {
				destination.close(true);
			}

			destination.removeDestinationEventListeners();

			destination.unregisterMessageListeners();

			for (MessageBusEventListener messageBusEventListener :
					_messageBusEventListeners) {

				messageBusEventListener.destinationRemoved(destination);
			}
		}

		return destination;
	}

	@Override
	public boolean removeMessageBusEventListener(
		MessageBusEventListener messageBusEventListener) {

		return _messageBusEventListeners.remove(messageBusEventListener);
	}

	@Override
	public void replace(Destination destination) {
		replace(destination, true);
	}

	@Override
	public void replace(Destination destination, boolean closeOnRemove) {
		Destination oldDestination = _destinations.get(destination.getName());

		oldDestination.copyDestinationEventListeners(destination);
		oldDestination.copyMessageListeners(destination);

		removeDestination(oldDestination.getName(), closeOnRemove);

		addDestination(destination);

		if (closeOnRemove) {
			destination.open();
		}
	}

	@Override
	public void sendMessage(String destinationName, Message message) {
		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Destination " + destinationName + " is not configured");
			}

			return;
		}

		message.setDestinationName(destinationName);

		destination.send(message);
	}

	@Override
	public void shutdown() {
		shutdown(false);
	}

	@Override
	public synchronized void shutdown(boolean force) {
		for (Destination destination : _destinations.values()) {
			destination.close(force);
		}
	}

	@Override
	public synchronized boolean unregisterMessageListener(
		String destinationName, MessageListener messageListener) {

		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			return false;
		}

		return destination.unregister(messageListener);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, Destination.class,
			new DestinationServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(destination.name=*)"
	)
	protected synchronized void addDestination(
		Destination destination, Map<String, Object> properties) {

		Class<?> clazz = destination.getClass();

		if (SPIUtil.isSPI() &&
			!clazz.equals(IntrabandBridgeDestination.class)) {

			destination = new IntrabandBridgeDestination(destination);
		}

		if (_destinations.containsKey(destination.getName())) {
			replace(destination);
		}
		else {
			_destinations.put(destination.getName(), destination);
		}

		for (MessageBusEventListener messageBusEventListener :
				_messageBusEventListeners) {

			messageBusEventListener.destinationAdded(destination);
		}
	}

	@Deactivate
	protected void deactivate() {
		shutdown(true);

		for (Destination destination : _destinations.values()) {
			destination.destroy();
		}

		_messageBusEventListeners.clear();

		_serviceTracker.close();

		_serviceTracker = null;

		_bundleContext = null;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void registerMessageBusEventListener(
		MessageBusEventListener messageBusEventListener) {

		addMessageBusEventListener(messageBusEventListener);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(destination.name=*)"
	)
	protected synchronized void registerMessageListener(
		MessageListener messageListener, Map<String, Object> properties) {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			ClassLoader operatingClassLoader = (ClassLoader)properties.get(
				"operatingClassLoader");

			currentThread.setContextClassLoader(operatingClassLoader);

			String destinationName = MapUtil.getString(
				properties, "destination.name");

			registerMessageListener(destinationName, messageListener);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected synchronized void removeDestination(
		Destination destination, Map<String, Object> properties) {

		removeDestination(destination.getName());
	}

	protected void unregisterMessageBusEventListener(
		MessageBusEventListener messageBusEventListener) {

		removeMessageBusEventListener(messageBusEventListener);
	}

	protected void unregisterMessageListener(
		MessageListener messageListener, Map<String, Object> properties) {

		String destinationName = MapUtil.getString(
			properties, "destination.name");

		unregisterMessageListener(destinationName, messageListener);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultMessageBus.class);

	private BundleContext _bundleContext;
	private final Map<String, Destination> _destinations = new HashMap<>();
	private final Set<MessageBusEventListener> _messageBusEventListeners =
		new ConcurrentHashSet<>();
	private ServiceTracker<Destination, Destination> _serviceTracker;

	private class DestinationServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Destination, Destination> {

		@Override
		public Destination addingService(
			ServiceReference<Destination> serviceReference) {

			return _bundleContext.getService(serviceReference);
		}

		@Override
		public void modifiedService(
			ServiceReference<Destination> serviceReference,
			Destination destination) {
		}

		@Override
		public void removedService(
			ServiceReference<Destination> serviceReference,
			Destination destination) {

			destination.destroy();
		}

	}

}