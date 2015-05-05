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

package com.liferay.registry.internal;

import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.ServiceTrackerMapFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapFactoryImpl implements ServiceTrackerMapFactory {

	public ServiceTrackerMapFactoryImpl(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public <S> ServiceTrackerMap<String, List<S>> multiValueMap(
		Class<S> clazz, String propertyKey) {

		try {
			com.liferay.osgi.service.tracker.map.ServiceTrackerMap
				<String, List<S>> serviceTrackerMap =
					com.liferay.osgi.service.tracker.map.
						ServiceTrackerMapFactory.multiValueMap(
							_bundleContext, clazz, propertyKey);

			return new ServiceTrackerMapWrapper<>(serviceTrackerMap);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public <K, S> ServiceTrackerMap<K, List<S>> multiValueMap(
		Class<S> clazz, String filterString,
		final ServiceReferenceMapper<K, ? super S> serviceReferenceMapper) {

		ServiceReferenceMapperWrapper<K, S> serviceReferenceMapperWrapper =
			new ServiceReferenceMapperWrapper<>(serviceReferenceMapper);

		try {
			com.liferay.osgi.service.tracker.map.ServiceTrackerMap<K, List<S>>
				serviceTrackerMap =
					com.liferay.osgi.service.tracker.map.
						ServiceTrackerMapFactory.multiValueMap(
							_bundleContext, clazz, filterString,
							serviceReferenceMapperWrapper);

			return new ServiceTrackerMapWrapper<>(serviceTrackerMap);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public <K, S> ServiceTrackerMap<K, List<S>> multiValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator) {

		ServiceReferenceMapperWrapper<K, S> serviceReferenceMapperWrapper =
			new ServiceReferenceMapperWrapper<>(serviceReferenceMapper);
		ServiceReferenceComparatorAdapter<S> serviceReferenceComparatorAdapter =
			new ServiceReferenceComparatorAdapter<>(comparator);

		try {
			com.liferay.osgi.service.tracker.map.ServiceTrackerMap<K, List<S>>
				serviceTrackerMap =
					com.liferay.osgi.service.tracker.map.
						ServiceTrackerMapFactory.multiValueMap(
							_bundleContext, clazz, filterString,
							serviceReferenceMapperWrapper,
							serviceReferenceComparatorAdapter);

			return new ServiceTrackerMapWrapper<>(serviceTrackerMap);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public <K, SR, S> ServiceTrackerMap<K, List<S>> multiValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

		ServiceReferenceMapperWrapper<K, SR> serviceReferenceMapperWrapper =
			new ServiceReferenceMapperWrapper<>(serviceReferenceMapper);
		ServiceTrackerCustomizerAdapter<SR, S> serviceTrackerCustomizerAdapter =
			new ServiceTrackerCustomizerAdapter<>(serviceTrackerCustomizer);

		try {
			com.liferay.osgi.service.tracker.map.ServiceTrackerMap<K, List<S>>
				serviceTrackerMap =
					com.liferay.osgi.service.tracker.map.
						ServiceTrackerMapFactory.multiValueMap(
							_bundleContext, clazz, filterString,
							serviceReferenceMapperWrapper,
							serviceTrackerCustomizerAdapter);

			return new ServiceTrackerMapWrapper<>(serviceTrackerMap);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public <K, SR, S> ServiceTrackerMap<K, List<S>> multiValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
		Comparator<ServiceReference<SR>> comparator) {

		ServiceReferenceMapperWrapper<K, SR> serviceReferenceMapperWrapper =
			new ServiceReferenceMapperWrapper<>(serviceReferenceMapper);
		ServiceTrackerCustomizerAdapter<SR, S> serviceTrackerCustomizerAdapter =
			new ServiceTrackerCustomizerAdapter<>(serviceTrackerCustomizer);
		ServiceReferenceComparatorAdapter<SR>
			serviceReferenceComparatorAdapter =
				new ServiceReferenceComparatorAdapter<>(comparator);

		try {
			com.liferay.osgi.service.tracker.map.ServiceTrackerMap<K, List<S>>
				serviceTrackerMap =
					com.liferay.osgi.service.tracker.map.
						ServiceTrackerMapFactory.multiValueMap(
							_bundleContext, clazz, filterString,
							serviceReferenceMapperWrapper,
							serviceTrackerCustomizerAdapter,
							serviceReferenceComparatorAdapter);

			return new ServiceTrackerMapWrapper<>(serviceTrackerMap);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public <SR, S> ServiceTrackerMap<String, List<S>> multiValueMap(
		Class<SR> clazz, String propertyKey,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

		ServiceTrackerCustomizerAdapter<SR, S> serviceTrackerCustomizerAdapter =
			new ServiceTrackerCustomizerAdapter<>(serviceTrackerCustomizer);

		try {
			com.liferay.osgi.service.tracker.map.ServiceTrackerMap
				<String, List<S>> serviceTrackerMap =
					com.liferay.osgi.service.tracker.map.
						ServiceTrackerMapFactory.multiValueMap(
							_bundleContext, clazz, propertyKey,
							serviceTrackerCustomizerAdapter);

			return new ServiceTrackerMapWrapper<>(serviceTrackerMap);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public <S> ServiceTrackerMap<String, S> singleValueMap(
		Class<S> clazz, String propertyKey) {

		try {
			com.liferay.osgi.service.tracker.map.ServiceTrackerMap<String, S>
				serviceTrackerMap =
					com.liferay.osgi.service.tracker.map.
						ServiceTrackerMapFactory.singleValueMap(
							_bundleContext, clazz, propertyKey);

			return new ServiceTrackerMapWrapper<>(serviceTrackerMap);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public <K, S> ServiceTrackerMap<K, S> singleValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper) {

		ServiceReferenceMapperWrapper<K, S> serviceReferenceMapperWrapper =
			new ServiceReferenceMapperWrapper<>(serviceReferenceMapper);

		try {
			com.liferay.osgi.service.tracker.map.ServiceTrackerMap<K, S>
				serviceTrackerMap =
					com.liferay.osgi.service.tracker.map.
						ServiceTrackerMapFactory.singleValueMap(
							_bundleContext, clazz, filterString,
							serviceReferenceMapperWrapper);

			return new ServiceTrackerMapWrapper<>(serviceTrackerMap);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public <K, S> ServiceTrackerMap<K, S> singleValueMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K, ? super S> serviceReferenceMapper,
		final Comparator<ServiceReference<S>> comparator) {

		ServiceReferenceMapperWrapper<K, S> serviceReferenceMapperWrapper =
			new ServiceReferenceMapperWrapper<>(serviceReferenceMapper);
		ServiceReferenceComparatorAdapter<S> serviceReferenceComparatorAdapter =
			new ServiceReferenceComparatorAdapter<>(comparator);

		try {
			com.liferay.osgi.service.tracker.map.ServiceTrackerMap<K, S>
				serviceTrackerMap =
					com.liferay.osgi.service.tracker.map.
						ServiceTrackerMapFactory.singleValueMap(
							_bundleContext, clazz, filterString,
							serviceReferenceMapperWrapper,
							serviceReferenceComparatorAdapter);

			return new ServiceTrackerMapWrapper<>(serviceTrackerMap);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public <K, SR, S> ServiceTrackerMap<K, S> singleValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

		ServiceReferenceMapperWrapper<K, SR> serviceReferenceMapperWrapper =
			new ServiceReferenceMapperWrapper<>(serviceReferenceMapper);
		ServiceTrackerCustomizerAdapter<SR, S> serviceTrackerCustomizerAdapter =
			new ServiceTrackerCustomizerAdapter<>(serviceTrackerCustomizer);

		try {
			com.liferay.osgi.service.tracker.map.ServiceTrackerMap<K, S>
				serviceTrackerMap =
					com.liferay.osgi.service.tracker.map.
						ServiceTrackerMapFactory.singleValueMap(
							_bundleContext, clazz, filterString,
							serviceReferenceMapperWrapper,
							serviceTrackerCustomizerAdapter);

			return new ServiceTrackerMapWrapper<>(serviceTrackerMap);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public <K, SR, S> ServiceTrackerMap<K, S> singleValueMap(
		Class<SR> clazz, String filterString,
		ServiceReferenceMapper<K, ? super SR> serviceReferenceMapper,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer,
		Comparator<ServiceReference<SR>> comparator) {

		ServiceReferenceMapperWrapper<K, SR> serviceReferenceMapperWrapper =
			new ServiceReferenceMapperWrapper<>(serviceReferenceMapper);
		ServiceTrackerCustomizerAdapter<SR, S> serviceTrackerCustomizerAdapter =
			new ServiceTrackerCustomizerAdapter<>(serviceTrackerCustomizer);

		try {
			ServiceReferenceComparatorAdapter<SR>
				serviceReferenceComparatorAdapter =
					new ServiceReferenceComparatorAdapter<>(comparator);

			com.liferay.osgi.service.tracker.map.ServiceTrackerMap<K, S>
				serviceTrackerMap =
					com.liferay.osgi.service.tracker.map.
						ServiceTrackerMapFactory.singleValueMap(
							_bundleContext, clazz, filterString,
							serviceReferenceMapperWrapper,
							serviceTrackerCustomizerAdapter,
							serviceReferenceComparatorAdapter);

			return new ServiceTrackerMapWrapper<>(serviceTrackerMap);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public <SR, S> ServiceTrackerMap<String, S> singleValueMap(
		Class<SR> clazz, String propertyKey,
		ServiceTrackerCustomizer<SR, S> serviceTrackerCustomizer) {

		ServiceTrackerCustomizerAdapter<SR, S> serviceTrackerCustomizerAdapter =
			new ServiceTrackerCustomizerAdapter<>(serviceTrackerCustomizer);

		try {
			com.liferay.osgi.service.tracker.map.ServiceTrackerMap<String, S>
				serviceTrackerMap =
					com.liferay.osgi.service.tracker.map.
						ServiceTrackerMapFactory.singleValueMap(
							_bundleContext, clazz, propertyKey,
							serviceTrackerCustomizerAdapter);

			return new ServiceTrackerMapWrapper<>(serviceTrackerMap);
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	private final BundleContext _bundleContext;

	private static class EmitterWrapper<K>
		implements ServiceReferenceMapper.Emitter<K> {

		public EmitterWrapper(
			com.liferay.osgi.service.tracker.map.ServiceReferenceMapper.
				Emitter<K> emitter) {

			_emitter = emitter;
		}

		@Override
		public void emit(K key) {
			_emitter.emit(key);
		}

		private final
			com.liferay.osgi.service.tracker.map.ServiceReferenceMapper.
				Emitter<K> _emitter;

	}

	private static class ServiceReferenceComparatorAdapter<S>
		implements Comparator<org.osgi.framework.ServiceReference<S>> {

		public ServiceReferenceComparatorAdapter(
			Comparator<ServiceReference<S>> comparator) {

			_comparator = comparator;
		}

		@Override
		public int compare(
			org.osgi.framework.ServiceReference<S> serviceReference1,
			org.osgi.framework.ServiceReference<S> serviceReference2) {

			return _comparator.compare(
				new ServiceReferenceWrapper<S>(serviceReference1),
				new ServiceReferenceWrapper<S>(serviceReference2));
		}

		private final Comparator<ServiceReference<S>> _comparator;

	}

	private static class ServiceReferenceMapperWrapper<K, S>
		implements
			com.liferay.osgi.service.tracker.map.ServiceReferenceMapper<K, S> {

		public ServiceReferenceMapperWrapper(
			ServiceReferenceMapper<K, ? super S> serviceReferenceMapper) {

			_serviceReferenceMapper = serviceReferenceMapper;
		}

		@Override
		@SuppressWarnings({"rawtypes", "unchecked"})
		public void map(
			org.osgi.framework.ServiceReference<S> serviceReference,
			final Emitter<K> emitter) {

			ServiceReferenceWrapper<S> serviceReferenceWrapper =
				new ServiceReferenceWrapper<>(serviceReference);

			_serviceReferenceMapper.map(
				(ServiceReferenceWrapper)serviceReferenceWrapper,
				new EmitterWrapper<>(emitter));
		}

		private final ServiceReferenceMapper<K, ? super S>
			_serviceReferenceMapper;

	}

	private class ServiceTrackerMapWrapper<K, S>
		implements ServiceTrackerMap<K, S> {

		public ServiceTrackerMapWrapper(
			com.liferay.osgi.service.tracker.map.ServiceTrackerMap<K, S>
				serviceTrackerMap) {

			_serviceTrackerMap = serviceTrackerMap;
		}

		@Override
		public void close() {
			_serviceTrackerMap.close();
		}

		@Override
		public boolean containsKey(K k) {
			return _serviceTrackerMap.containsKey(k);
		}

		@Override
		public S getService(K k) {
			return _serviceTrackerMap.getService(k);
		}

		@Override
		public Set<K> keySet() {
			return _serviceTrackerMap.keySet();
		}

		@Override
		public void open() {
			_serviceTrackerMap.open();
		}

		private final com.liferay.osgi.service.tracker.map.
			ServiceTrackerMap<K, S> _serviceTrackerMap;

	}

}