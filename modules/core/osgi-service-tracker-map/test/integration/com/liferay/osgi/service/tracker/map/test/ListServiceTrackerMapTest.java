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

package com.liferay.osgi.service.tracker.map.test;

import com.liferay.osgi.service.tracker.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.map.internal.BundleContextWrapper;
import com.liferay.osgi.service.tracker.map.internal.TrackedOne;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.arquillian.liferay.deploymentscenario.annotations.BndFile;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
@BndFile("test/integration/bnd.bnd")
@RunWith(Arquillian.class)
public class ListServiceTrackerMapTest {

	@Before
	public void setUp() throws BundleException {
		_bundle.start();

		_bundleContext = _bundle.getBundleContext();
	}

	@After
	public void tearDown() throws BundleException {
		_bundle.stop();

		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();

			_serviceTrackerMap = null;
		}
	}

	@Test
	public void testGestServiceWithDifferentRanking() {
		ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
			createServiceTrackerMap(_bundleContext);

		TrackedOne trackedOne1 = new TrackedOne();

		registerService(trackedOne1, 1);

		TrackedOne trackedOne3 = new TrackedOne();

		registerService(trackedOne3, 3);

		TrackedOne trackedOne2 = new TrackedOne();

		registerService(trackedOne2, 2);

		List<TrackedOne> services = serviceTrackerMap.getService("aTarget");

		Assert.assertEquals(3, services.size());

		Iterator<? extends TrackedOne> iterator = services.iterator();

		Assert.assertEquals(trackedOne3, iterator.next());
		Assert.assertEquals(trackedOne2, iterator.next());
		Assert.assertEquals(trackedOne1, iterator.next());
	}

	@Test
	public void testGestServiceWithUnregistering() {
		ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
			createServiceTrackerMap(_bundleContext);

		TrackedOne trackedOne1 = new TrackedOne();

		registerService(trackedOne1, 1);

		TrackedOne trackedOne3 = new TrackedOne();

		registerService(trackedOne3, 3);

		TrackedOne trackedOne2 = new TrackedOne();

		ServiceRegistration<TrackedOne> serviceRegistration2 = registerService(
			trackedOne2, 2);

		List<TrackedOne> services = serviceTrackerMap.getService("aTarget");

		Assert.assertEquals(3, services.size());

		Iterator<? extends TrackedOne> iterator = services.iterator();

		serviceRegistration2.unregister();

		// Deregistering a service should not affect an already existing
		// collection or iterator

		Assert.assertEquals(trackedOne3, iterator.next());
		Assert.assertEquals(trackedOne2, iterator.next());
		Assert.assertEquals(trackedOne1, iterator.next());

		services = serviceTrackerMap.getService("aTarget");

		// Getting the list of services should return a list with the affected
		// changes

		Assert.assertEquals(2, services.size());

		iterator = services.iterator();

		Assert.assertEquals(trackedOne3, iterator.next());
		Assert.assertEquals(trackedOne1, iterator.next());
	}

	@Test
	public void testGetServicesIsNullAfterDeregistration() {
		ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
			createServiceTrackerMap(_bundleContext);

		ServiceRegistration<TrackedOne> serviceRegistration1 = registerService(
			new TrackedOne());
		ServiceRegistration<TrackedOne> serviceRegistration2 = registerService(
			new TrackedOne());

		Assert.assertNotNull(serviceTrackerMap.getService("aTarget"));

		serviceRegistration1.unregister();
		serviceRegistration2.unregister();

		Assert.assertNull(serviceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServicesWithDifferentKeys() {
		ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
			createServiceTrackerMap(_bundleContext);

		registerService(new TrackedOne(), "aTarget");
		registerService(new TrackedOne(), "anotherTarget");
		registerService(new TrackedOne(), "aTarget");
		registerService(new TrackedOne(), "anotherTarget");
		registerService(new TrackedOne(), "anotherTarget");

		List<TrackedOne> aTargetList = serviceTrackerMap.getService("aTarget");

		Assert.assertNotNull(aTargetList);
		Assert.assertEquals(2, aTargetList.size());

		List<TrackedOne> anotherTargetList = serviceTrackerMap.getService(
			"anotherTarget");

		Assert.assertNotNull(anotherTargetList);
		Assert.assertEquals(3, anotherTargetList.size());
	}

	@Test
	public void testGetServiceWithSimpleRegistration() {
		ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
			createServiceTrackerMap(_bundleContext);

		registerService(new TrackedOne());

		List<TrackedOne> services = serviceTrackerMap.getService("aTarget");

		Assert.assertEquals(1, services.size());

		serviceTrackerMap.close();
	}

	@Test
	public void testGetServiceWithSimpleRegistrationTwice() {
		ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
			createServiceTrackerMap(_bundleContext);

		registerService(new TrackedOne());
		registerService(new TrackedOne());

		List<TrackedOne> services = serviceTrackerMap.getService("aTarget");

		Assert.assertEquals(2, services.size());

		serviceTrackerMap.close();
	}

	@Test
	public void testOperationBalancesOutGetServiceAndUngetService() {
		BundleContextWrapper bundleContextWrapper = wrapContext();

		ServiceTrackerMap<String, List<TrackedOne>> serviceTrackerMap =
			createServiceTrackerMap(bundleContextWrapper);

		ServiceRegistration<TrackedOne> serviceRegistration1 = registerService(
			new TrackedOne());

		ServiceRegistration<TrackedOne> serviceRegistration2 = registerService(
			new TrackedOne());

		serviceRegistration2.unregister();

		serviceRegistration2 = registerService(new TrackedOne());

		serviceRegistration2.unregister();

		serviceRegistration1.unregister();

		Map<ServiceReference<?>, AtomicInteger> serviceReferenceCountsMap =
			bundleContextWrapper.getServiceReferenceCountsMap();

		Collection<AtomicInteger> serviceReferenceCounts =
			serviceReferenceCountsMap.values();

		Assert.assertEquals(3, serviceReferenceCounts.size());

		for (AtomicInteger serviceReferenceCount : serviceReferenceCounts) {
			Assert.assertEquals(0, serviceReferenceCount.get());
		}

		serviceTrackerMap.close();
	}

	@Test
	public void testUnkeyedServiceReferencesBalanceReferenceCount()
		throws InvalidSyntaxException {

		BundleContextWrapper bundleContextWrapper = wrapContext();

		ServiceTrackerMap<TrackedOne, TrackedOne> serviceTrackerMap =
			ServiceTrackerMapFactory.singleValueMap(
				bundleContextWrapper, TrackedOne.class, null,
				new ServiceReferenceMapper<TrackedOne, TrackedOne>() {

				@Override
				public void map(
					ServiceReference<TrackedOne> serviceReference,
					Emitter<TrackedOne> emitter) {
				}

			});

		serviceTrackerMap.open();

		ServiceRegistration<TrackedOne> serviceRegistration1 = registerService(
			new TrackedOne());
		ServiceRegistration<TrackedOne> serviceRegistration2 = registerService(
			new TrackedOne());

		Map<ServiceReference<?>, AtomicInteger> serviceReferenceCountsMap =
			bundleContextWrapper.getServiceReferenceCountsMap();

		Collection<AtomicInteger> serviceReferenceCounts =
			serviceReferenceCountsMap.values();

		Assert.assertEquals(0, serviceReferenceCounts.size());

		serviceRegistration1.unregister();
		serviceRegistration2.unregister();

		Assert.assertEquals(0, serviceReferenceCounts.size());

		serviceTrackerMap.close();
	}

	protected ServiceTrackerMap<String, List<TrackedOne>>
		createServiceTrackerMap(BundleContext bundleContext) {

		try {
			_serviceTrackerMap = ServiceTrackerMapFactory.multiValueMap(
				bundleContext, TrackedOne.class, "target");
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}

		_serviceTrackerMap.open();

		return _serviceTrackerMap;
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne) {

		return registerService(trackedOne, "aTarget");
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne, int ranking) {

		return registerService(trackedOne, ranking, "aTarget");
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne, int ranking, String target) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("service.ranking", ranking);
		properties.put("target", target);

		return _bundleContext.registerService(
			TrackedOne.class, trackedOne, properties);
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne, String target) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("target", target);

		return _bundleContext.registerService(
			TrackedOne.class, trackedOne, properties);
	}

	protected BundleContextWrapper wrapContext() {
		return new BundleContextWrapper(_bundleContext);
	}

	@ArquillianResource
	private Bundle _bundle;

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, List<TrackedOne>> _serviceTrackerMap;

}