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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.messaging.DestinationPrototype;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
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
@Component(immediate = true, service = DestinationFactory.class)
public class DefaultDestinationFactory implements DestinationFactory {

	@Override
	public Destination createDestination(DestinationConfiguration destinationConfig) {
		String type = destinationConfig.getDestinationType();

		DestinationPrototype destinationPrototype = _destinationPrototypes.get(
			type);

		if (destinationPrototype == null) {
			throw new IllegalArgumentException(
				"No prototype configured for " + type);
		}

		return destinationPrototype.createDestination(destinationConfig);
	}

	@Override
	public Collection<String> getTypes() {
		return Collections.unmodifiableCollection(
			_destinationPrototypes.keySet());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, DestinationConfiguration.class,
			new DestinationConfigServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDestinationPrototype(
		DestinationPrototype destinationPrototype,
		Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "type");

		_destinationPrototypes.put(type, destinationPrototype);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		synchronized (_serviceRegistrations) {
			for (ServiceRegistration<Destination>
					destinationServiceRegistration :
						_serviceRegistrations.values()) {

				destinationServiceRegistration.unregister();
			}
		}

		_serviceRegistrations.clear();

		_bundleContext = null;
	}

	protected void removeDestinationPrototype(
		DestinationPrototype destinationPrototype,
		Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "type");

		_destinationPrototypes.remove(type);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultDestinationFactory.class);

	private BundleContext _bundleContext;
	private final Map<String, DestinationPrototype> _destinationPrototypes =
		new ConcurrentHashMap<>();
	private final Map<String, ServiceRegistration<Destination>>
		_serviceRegistrations = new HashMap<>();
	private ServiceTracker<DestinationConfiguration, DestinationConfiguration>
		_serviceTracker;

	private class DestinationConfigServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<DestinationConfiguration, DestinationConfiguration> {

		@Override
		public DestinationConfiguration addingService(
			ServiceReference<DestinationConfiguration> serviceReference) {

			DestinationConfiguration destinationConfig = _bundleContext.getService(
				serviceReference);

			try {
				synchronized (_serviceRegistrations) {
					unregister(destinationConfig);

					Destination destination = createDestination(
						destinationConfig);

					Dictionary<String, Object> dictionary =
						new HashMapDictionary<>();

					dictionary.put("destination.name", destination.getName());

					ServiceRegistration<Destination> serviceRegistration =
						_bundleContext.registerService(
							Destination.class, destination, dictionary);

					_serviceRegistrations.put(
						destination.getName(), serviceRegistration);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to instantiate destination " +
							destinationConfig,
						e);
				}
			}

			return destinationConfig;
		}

		@Override
		public void modifiedService(
			ServiceReference<DestinationConfiguration> serviceReference,
			DestinationConfiguration destinationConfig) {

			unregister(destinationConfig);

			Destination destination = createDestination(destinationConfig);

			Dictionary<String, Object> dictionary = new HashMapDictionary<>();

			dictionary.put("destination.name", destination.getName());

			_bundleContext.registerService(
				Destination.class, destination, dictionary);
		}

		@Override
		public void removedService(
			ServiceReference<DestinationConfiguration> serviceReference,
			DestinationConfiguration destinationConfig) {

			unregister(destinationConfig);
		}

		protected void unregister(DestinationConfiguration destinationConfig) {
			synchronized (_serviceRegistrations) {
				if (_serviceRegistrations.containsKey(
						destinationConfig.getDestinationName())) {

					ServiceRegistration<Destination> serviceRegistration =
						_serviceRegistrations.get(
							destinationConfig.getDestinationName());

					serviceRegistration.unregister();
				}
			}
		}

	}

}