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

import com.liferay.registry.ServiceReference;

import java.util.Comparator;
import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapFactory {

	public static <K, S> ServiceTrackerMap<K, List<S>>
		createListServiceTrackerMap(
			Class<S> clazz, String filterString,
			ServiceReferenceMapper<K> serviceReferenceMapper) {

		return new ServiceTrackerMapImpl<K, S, List<S>>(
			clazz, filterString, serviceReferenceMapper,
			new ListServiceTrackerBucketFactory<S>(
				new ServiceReferenceComparator<S>("service.ranking")));
	}

	public static <K, S> ServiceTrackerMap<K, List<S>>
		createListServiceTrackerMap(
			Class<S> clazz, String filterString,
			ServiceReferenceMapper<K> serviceReferenceMapper,
			Comparator<ServiceReference<S>> comparator) {

		return new ServiceTrackerMapImpl<K, S, List<S>>(
			clazz, filterString, serviceReferenceMapper,
				new ListServiceTrackerBucketFactory<S>(comparator));
	}

	public static <S> ServiceTrackerMap<String, List<S>>
		createServiceTrackerListMapFromProperty(
			Class<S> clazz, String propertyKey) {

		return new ServiceTrackerMapImpl<String, S, List<S>>(
			clazz,"(" + propertyKey + "=*)",
			Mappers.<String>fromProperty(propertyKey),
			new ListServiceTrackerBucketFactory<S>(
				new ServiceReferenceComparator<S>("service.ranking")));
	}

	public static <K, S> ServiceTrackerMap<K, S> createServiceTrackerObjectMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K> serviceReferenceMapper) {

		return new ServiceTrackerMapImpl<K, S, S>(
			clazz, filterString, serviceReferenceMapper,
			new ObjectServiceTrackerBucketFactory<S>(
				new ServiceReferenceComparator<S>("service.ranking")));
	}

	public static <K, S> ServiceTrackerMap<K, S> createServiceTrackerObjectMap(
		Class<S> clazz, String filterString,
		ServiceReferenceMapper<K> serviceReferenceMapper,
		Comparator<ServiceReference<S>> comparator) {

		return new ServiceTrackerMapImpl<K, S, S>(
			clazz, filterString, serviceReferenceMapper,
			new ObjectServiceTrackerBucketFactory<S>(comparator));
	}

	public static <S> ServiceTrackerMap<String, S>
		createServiceTrackerObjectMapFromProperty(
			Class<S> clazz, String propertyKey) {

		return new ServiceTrackerMapImpl<String, S, S>(
			clazz, "(" + propertyKey + "=*)",
			Mappers.<String>fromProperty(propertyKey),
			new ObjectServiceTrackerBucketFactory<S>(
				new ServiceReferenceComparator<S>("service.ranking")));
	}
	
	private static class ServiceReferenceComparator <T>
		implements Comparator<ServiceReference<T>> {
	
		public ServiceReferenceComparator(String propertyKey) {
			_propertyKey = propertyKey;
		}

		@Override
		public int compare(
			ServiceReference<T> sr1, ServiceReference<T> sr2) {

			if (sr1 == null) {
				if (sr2 == null) {
					return 0;
				}
				else {
					return -1;
				}
			}
			else if (sr2 == null) {
				return 1;
			}

			try {
				Comparable property1 = (Comparable)sr1.getProperty(
					_propertyKey);

				Object property2 = sr2.getProperty(_propertyKey);

				if (property1 == null) {
					if (property2 != null) {
						return -1;
					}

					return -(sr1.compareTo(sr2));
				}

				return -(property1.compareTo(property2));
			}
			catch (ClassCastException cce) {
				return 0;
			}
		}

		private String _propertyKey;
		
	}

	public static class Mappers {

		public static <T> ServiceReferenceMapper<T> fromProperty(
			final String property) {

			return new ServiceReferenceMapper<T>() {

				@Override
				public void map(
					ServiceReference<?> serviceReference, Emitter<T> emitter) {

					T value = (T)serviceReference.getProperty(property);

					if (value != null) {
						emitter.emit(value);
					}
				}
			};
		}

	}

}