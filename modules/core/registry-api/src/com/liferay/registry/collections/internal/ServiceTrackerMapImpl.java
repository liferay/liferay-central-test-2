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

package com.liferay.registry.collections.internal;

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceReferenceServiceTuple;
import com.liferay.registry.collections.ServiceTrackerBucket;
import com.liferay.registry.collections.ServiceTrackerBucketFactory;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapImpl<K, S, R> implements ServiceTrackerMap<K, R> {

	public ServiceTrackerMapImpl(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K> serviceReferenceMapper,
		ServiceTrackerBucketFactory<S, R> serviceTrackerMapBucketFactory) {

		_serviceReferenceMapper = serviceReferenceMapper;
		_serviceTrackerMapBucketFactory = serviceTrackerMapBucketFactory;

		Registry registry = RegistryUtil.getRegistry();

		if (filterString != null) {
			Filter filter = registry.getFilter(
				"(&(objectClass=" + clazz.getName() + ")" + filterString + ")");

			_serviceTracker = registry.trackServices(
				filter, new ServiceReferenceServiceTrackerCustomizer());
		}
		else {
			_serviceTracker = registry.trackServices(
				clazz, new ServiceReferenceServiceTrackerCustomizer());
		}
	}

	@Override
	public void close() {
		_serviceTracker.close();
	}

	@Override
	public R getService(K key) {
		ServiceTrackerBucket<S, R> serviceTrackerBucket =
			_serviceTrackerBuckets.get(key);

		if (serviceTrackerBucket == null) {
			return null;
		}

		return serviceTrackerBucket.getContent();
	}

	@Override
	public void open() {
		_serviceTracker.open();
	}

	private ServiceReferenceMapper<K> _serviceReferenceMapper;
	private ServiceTracker<S, ServiceReferenceServiceTuple<S>> _serviceTracker;
	private ConcurrentHashMap<K, ServiceTrackerBucket<S, R>>
		_serviceTrackerBuckets =
			new ConcurrentHashMap<K, ServiceTrackerBucket<S, R>>();
	private ServiceTrackerBucketFactory<S, R> _serviceTrackerMapBucketFactory;

	private class Holder<T> {

		public T get() {
			return _value;
		}

		public void set(T value) {
			_value = value;
		}

		private T _value;

	}

	private class ServiceReferenceServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer<S, ServiceReferenceServiceTuple<S>> {

		@Override
		public ServiceReferenceServiceTuple<S> addingService(
			final ServiceReference<S> serviceReference) {

			final Registry registry = RegistryUtil.getRegistry();

			final Holder<ServiceReferenceServiceTuple<S>> holder =
				new Holder<ServiceReferenceServiceTuple<S>>();

			_serviceReferenceMapper.map(
				serviceReference,
				new ServiceReferenceMapper.Emitter<K>() {

					@Override
					public void emit(K key) {
						ServiceTrackerBucket<S, R> serviceTrackerBucket =
							_serviceTrackerBuckets.get(key);

						if (serviceTrackerBucket == null) {
							ServiceTrackerBucket<S, R> newServiceTrackerBucket =
								_serviceTrackerMapBucketFactory.create();

							serviceTrackerBucket =
								_serviceTrackerBuckets.putIfAbsent(
									key, newServiceTrackerBucket);

							if (serviceTrackerBucket == null) {
								serviceTrackerBucket = newServiceTrackerBucket;
							}
						}

						ServiceReferenceServiceTuple<S>
							serviceReferenceServiceTuple = holder.get();

						if (serviceReferenceServiceTuple == null) {
							S service = registry.getService(serviceReference);

							serviceReferenceServiceTuple =
								new ServiceReferenceServiceTuple<S>(
									serviceReference, service);

							holder.set(serviceReferenceServiceTuple);
						}

						serviceTrackerBucket.store(
							serviceReferenceServiceTuple);
					}

				});

			return holder.get();
		}

		@Override
		public void modifiedService(
			ServiceReference<S> service,
			ServiceReferenceServiceTuple<S> serviceReferenceServiceTuple) {

			removedService(
				serviceReferenceServiceTuple.getServiceReference(),
				serviceReferenceServiceTuple);

			addingService(serviceReferenceServiceTuple.getServiceReference());
		}

		@Override
		public void removedService(
			final ServiceReference<S> serviceReference,
			final ServiceReferenceServiceTuple<S>
				serviceReferenceServiceTuple) {

			_serviceReferenceMapper.map(
				serviceReference,
				new ServiceReferenceMapper.Emitter<K>() {

					@Override
					public void emit(K key) {
						ServiceTrackerBucket<S, R> serviceTrackerBucket =
							_serviceTrackerBuckets.get(key);

						if (serviceTrackerBucket == null) {
							return;
						}

						serviceTrackerBucket.remove(
							serviceReferenceServiceTuple);

						if (serviceTrackerBucket.isDisposable()) {
							_serviceTrackerBuckets.remove(key);
						}
					}
				});

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);
		}

	}

}