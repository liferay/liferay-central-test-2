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

import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceServiceTuple;
import com.liferay.registry.collections.ServiceReferenceServiceTupleComparator;
import com.liferay.registry.collections.ServiceTrackerBucket;
import com.liferay.registry.collections.ServiceTrackerBucketFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Carlos Sierra Andrés
 */
public class SingleValueServiceTrackerBucketFactory<SR, TS>
	implements ServiceTrackerBucketFactory<SR, TS, TS> {

	public SingleValueServiceTrackerBucketFactory() {
		_comparator = Collections.reverseOrder();
	}

	public SingleValueServiceTrackerBucketFactory(
		Comparator<ServiceReference<SR>> comparator) {

		_comparator = comparator;
	}

	@Override
	public ServiceTrackerBucket<SR, TS, TS> create() {
		return new SingleBucket();
	}

	private final Comparator<ServiceReference<SR>> _comparator;

	private class SingleBucket implements ServiceTrackerBucket<SR, TS, TS> {

		public SingleBucket() {
			_service = null;

			ServiceReferenceServiceTupleComparator<SR>
				serviceReferenceServiceTupleComparator =
					new ServiceReferenceServiceTupleComparator<>(_comparator);

			_serviceReferences = new PriorityQueue<>(
				1, serviceReferenceServiceTupleComparator);
		}

		@Override
		public TS getContent() {
			return _service;
		}

		@Override
		public synchronized boolean isDisposable() {
			return _serviceReferences.isEmpty();
		}

		@Override
		public synchronized void remove(
			ServiceReferenceServiceTuple<SR, TS> serviceReferenceServiceTuple) {

			_serviceReferences.remove(serviceReferenceServiceTuple);

			ServiceReferenceServiceTuple<SR, TS>
				headServiceReferenceServiceTuple = _serviceReferences.peek();

			if (headServiceReferenceServiceTuple != null) {
				_service = headServiceReferenceServiceTuple.getService();
			}
			else {
				_service = null;
			}
		}

		@Override
		public synchronized void store(
			ServiceReferenceServiceTuple<SR, TS> serviceReferenceServiceTuple) {

			_serviceReferences.add(serviceReferenceServiceTuple);

			_service = _serviceReferences.peek().getService();
		}

		private TS _service;
		private final PriorityQueue<ServiceReferenceServiceTuple<SR, TS>>
			_serviceReferences;

	}

}