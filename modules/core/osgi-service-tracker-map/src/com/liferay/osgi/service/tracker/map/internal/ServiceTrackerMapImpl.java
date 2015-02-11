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

package com.liferay.osgi.service.tracker.map.internal;

import com.liferay.osgi.service.tracker.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.map.ServiceReferenceServiceTuple;
import com.liferay.osgi.service.tracker.map.ServiceTrackerBucket;
import com.liferay.osgi.service.tracker.map.ServiceTrackerBucketFactory;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapImpl<K, SR, TS, R>
	implements ServiceTrackerMap<K, R> {

	public ServiceTrackerMapImpl(
			BundleContext bundleContext, Class<SR> clazz, String filterString,
			ServiceReferenceMapper<K, SR> serviceReferenceMapper,
			ServiceTrackerCustomizer<SR, TS> serviceTrackerCustomizer,
			ServiceTrackerBucketFactory<SR, TS, R>
				serviceTrackerMapBucketFactory)
		throws InvalidSyntaxException {

		_serviceReferenceMapper = serviceReferenceMapper;
		_serviceTrackerCustomizer = serviceTrackerCustomizer;
		_serviceTrackerMapBucketFactory = serviceTrackerMapBucketFactory;

		if (filterString != null) {
			Filter filter = bundleContext.createFilter(
				"(&(objectClass=" + clazz.getName() + ")" + filterString + ")");

			_serviceTracker = new ServiceTracker<>(
				bundleContext, filter,
				new ServiceReferenceServiceTrackerCustomizer());
		}
		else {
			_serviceTracker = new ServiceTracker<>(
				bundleContext, clazz,
				new ServiceReferenceServiceTrackerCustomizer());
		}
	}

	@Override
	public void close() {
		_serviceTracker.close();
	}

	@Override
	public boolean containsKey(K key) {
		return _serviceTrackerBuckets.containsKey(key);
	}

	@Override
	public R getService(K key) {
		ServiceTrackerBucket<SR, TS, R> serviceTrackerBucket =
			_serviceTrackerBuckets.get(key);

		if (serviceTrackerBucket == null) {
			return null;
		}

		return serviceTrackerBucket.getContent();
	}

	@Override
	public Set<K> keySet() {
		return Collections.unmodifiableSet(_serviceTrackerBuckets.keySet());
	}

	@Override
	public void open() {
		_serviceTracker.open();
	}

	private void removeKeys(
		ServiceReferenceServiceTuple<SR, TS, K> serviceReferenceServiceTuple) {

		List<K> emittedKeys = serviceReferenceServiceTuple.getEmittedKeys();

		for (K emittedKey : emittedKeys) {
			ServiceTrackerBucket<SR, TS, R> serviceTrackerBucket =
				_serviceTrackerBuckets.get(emittedKey);

			if (serviceTrackerBucket == null) {
				continue;
			}

			serviceTrackerBucket.remove(serviceReferenceServiceTuple);

			if (serviceTrackerBucket.isDisposable()) {
				_serviceTrackerBuckets.remove(emittedKey);
			}
		}

		emittedKeys.clear();
	}

	private void storeKey(
		K key,
		ServiceReferenceServiceTuple<SR, TS, K> serviceReferenceServiceTuple) {

		ServiceTrackerBucket<SR, TS, R> serviceTrackerBucket =
			_serviceTrackerBuckets.get(key);

		if (serviceTrackerBucket == null) {
			ServiceTrackerBucket<SR, TS, R> newServiceTrackerBucket =
				_serviceTrackerMapBucketFactory.create();

			serviceTrackerBucket = _serviceTrackerBuckets.putIfAbsent(
				key, newServiceTrackerBucket);

			if (serviceTrackerBucket == null) {
				serviceTrackerBucket = newServiceTrackerBucket;
			}
		}

		serviceTrackerBucket.store(serviceReferenceServiceTuple);

		serviceReferenceServiceTuple.addEmittedKey(key);
	}

	private final ServiceReferenceMapper<K, SR> _serviceReferenceMapper;
	private final ServiceTracker<SR, ServiceReferenceServiceTuple<SR, TS, K>>
		_serviceTracker;
	private final ConcurrentHashMap<K, ServiceTrackerBucket<SR, TS, R>>
		_serviceTrackerBuckets = new ConcurrentHashMap<>();
	private final ServiceTrackerCustomizer<SR, TS> _serviceTrackerCustomizer;
	private final ServiceTrackerBucketFactory<SR, TS, R>
		_serviceTrackerMapBucketFactory;

	private class DefaultEmitter implements ServiceReferenceMapper.Emitter<K> {

		public DefaultEmitter(ServiceReference<SR> serviceReference) {
			_serviceReference = serviceReference;
		}

		@Override
		public void emit(K key) {
			if ((_serviceReferenceServiceTuple == null) &&
				!_invokedServiceTrackerCustomizer) {

				TS service = _serviceTrackerCustomizer.addingService(
					_serviceReference);

				_invokedServiceTrackerCustomizer = true;

				if (service == null) {
					return;
				}

				_serviceReferenceServiceTuple =
					new ServiceReferenceServiceTuple<>(
						_serviceReference, service);
			}

			storeKey(key, _serviceReferenceServiceTuple);
		}

		public ServiceReferenceServiceTuple<SR, TS, K>
			getServiceReferenceServiceTuple() {

			return _serviceReferenceServiceTuple;
		}

		private boolean _invokedServiceTrackerCustomizer;
		private final ServiceReference<SR> _serviceReference;
		private ServiceReferenceServiceTuple<SR, TS, K>
			_serviceReferenceServiceTuple;

	}

	private class ServiceReferenceServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer
				<SR, ServiceReferenceServiceTuple<SR, TS, K>> {

		@Override
		public ServiceReferenceServiceTuple<SR, TS, K> addingService(
			final ServiceReference<SR> serviceReference) {

			DefaultEmitter defaultEmitter = new DefaultEmitter(
				serviceReference);

			_serviceReferenceMapper.map(serviceReference, defaultEmitter);

			return defaultEmitter.getServiceReferenceServiceTuple();
		}

		@Override
		public void modifiedService(
			ServiceReference<SR> serviceReference,
			final ServiceReferenceServiceTuple<SR, TS, K>
				serviceReferenceServiceTuple) {

			removeKeys(serviceReferenceServiceTuple);

			_serviceTrackerCustomizer.modifiedService(
				serviceReference, serviceReferenceServiceTuple.getService());

			_serviceReferenceMapper.map(
				serviceReference, new ServiceReferenceMapper.Emitter<K>() {

				@Override
				public void emit(K key) {
					storeKey(key, serviceReferenceServiceTuple);
				}

			});
		}

		@Override
		public void removedService(
			final ServiceReference<SR> serviceReference,
			final ServiceReferenceServiceTuple<SR, TS, K>
				serviceReferenceServiceTuple) {

			removeKeys(serviceReferenceServiceTuple);

			_serviceTrackerCustomizer.removedService(
				serviceReference, serviceReferenceServiceTuple.getService());
		}

	}

}