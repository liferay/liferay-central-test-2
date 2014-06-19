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

package com.liferay.registry.collections;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapImpl<K, S, R> implements ServiceTrackerMap<K, R> {

	public ServiceTrackerMapImpl(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K> serviceReferenceMapper,
		ServiceTrackerMapBucketFactory<S, R> serviceTrackerMapBucketFactory) {

		_serviceReferenceMapper = serviceReferenceMapper;
		_serviceTrackerMapBucketFactory = serviceTrackerMapBucketFactory;

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			registry.getFilter(
				"(&(objectClass=" + clazz.getName() + ")" + filterString + ")"),
			new MapServiceTrackerCustomizer());
	}

	@Override
	public void close() {
		_serviceTracker.close();
	}

	@Override
	public R getService(K key) {
		ServiceTrackerMapBucket<S, R> bucket = _indexedServices.get(key);

		if (bucket == null) {
			return null;
		}

		return bucket.getContent();
	}

	@Override
	public void open() {
		_serviceTracker.open();
	}

	private ConcurrentHashMap<K, ServiceTrackerMapBucket<S, R>> _indexedServices =
		new ConcurrentHashMap<K, ServiceTrackerMapBucket<S, R>>();
	private ServiceReferenceMapper<K> _serviceReferenceMapper;
	private ServiceTracker<S, ServiceReference<S>> _serviceTracker;
	private ServiceTrackerMapBucketFactory<S, R>
		_serviceTrackerMapBucketFactory;

	private class MapServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<S, ServiceReference<S>> {

		@Override
		public ServiceReference<S> addingService(
			final ServiceReference<S> serviceReference) {

			_serviceReferenceMapper.map(
				serviceReference,
				new ServiceReferenceMapper.Emitter<K>() {

					@Override
					public void emit(K key) {
						ServiceTrackerMapBucket<S, R> bucket = _indexedServices.get(key);

						if (bucket == null) {
							ServiceTrackerMapBucket<S, R> newBucket =
								_serviceTrackerMapBucketFactory.create();

							bucket = _indexedServices.putIfAbsent(
								key, newBucket);

							if (bucket == null) {
								bucket = newBucket;
							}
						}

						bucket.store(serviceReference);
					}

				});

			return serviceReference;
		}

		@Override
		public void modifiedService(
			ServiceReference<S> serviceReference, ServiceReference<S> service) {

			removedService(service, service);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			final ServiceReference<S> serviceReference,
			ServiceReference<S> service) {

			_serviceReferenceMapper.map(
				serviceReference,
				new ServiceReferenceMapper.Emitter<K>() {

					@Override
					public void emit(K key) {
						ServiceTrackerMapBucket<S, R> bucket = _indexedServices.get(key);

						if (bucket == null) {
							return;
						}

						bucket.remove(serviceReference);

						if (bucket.isDisposable()) {
							_indexedServices.remove(key);
						}
					}

				});
		}
	}

}