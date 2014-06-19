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
		_bucketFactory = serviceTrackerMapBucketFactory;

		Registry registry = RegistryUtil.getRegistry();

		filterString =
			"(&(objectClass=" + clazz.getName() + ")" + filterString + ")";

		_serviceTracker = registry.trackServices(
			registry.getFilter(filterString), new MapServiceTrackerCustomizer());
	}

	@Override
	public void close() {
		_serviceTracker.close();
	}

	@Override
	public R getService(K key) {
		Bucket<S, R> bucket = _indexedServices.get(key);

		if (bucket == null) {
			return null;
		}

		return bucket.getContent();
	}

	@Override
	public void open() {
		_serviceTracker.open();
	}

	private ServiceTrackerMapBucketFactory<S, R> _bucketFactory;
	private ConcurrentHashMap<K, Bucket<S, R>> _indexedServices =
		new ConcurrentHashMap<K, Bucket<S, R>>();
	private final ServiceReferenceMapper<K> _serviceReferenceMapper;

	private final ServiceTracker<S, ServiceReference<S>> _serviceTracker;

	private class MapServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<S, ServiceReference<S>> {

		@Override
		public ServiceReference<S> addingService(
			final ServiceReference<S> serviceReference) {

			_serviceReferenceMapper.map(
				serviceReference, new ServiceReferenceMapper.Emitter<K>() {

				@Override
				public void emit(K key) {
					Bucket<S, R> bucket = _indexedServices.get(key);

					if (bucket == null) {
						Bucket<S, R> newBucket = _bucketFactory.create();

						bucket = _indexedServices.putIfAbsent(key, newBucket);

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
			final ServiceReference<S> serviceReference,
			final ServiceReference<S> service) {

			removedService(service, service);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			final ServiceReference<S> serviceReference,
			ServiceReference<S> service) {

			_serviceReferenceMapper.map(
				serviceReference, new ServiceReferenceMapper.Emitter<K>() {

				@Override
				public void emit(K key) {
					Bucket<S, R> bucket = _indexedServices.get(key);

					if (bucket == null) {
						return;
					}

					bucket.remove(serviceReference);

					if (bucket.isDisposable()) {
						_indexedServices.remove(key);
					}
				}

			} );
		}

	}

}