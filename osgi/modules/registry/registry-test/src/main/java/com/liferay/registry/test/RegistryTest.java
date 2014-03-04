/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.registry.test;

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Raymond Augé
 */
public class RegistryTest {

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testGetBundleContext() {
		Assert.assertNotNull(_bundleContext);
	}

	@Test
	public void testGetRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		Assert.assertNotNull(registry);
	}

	@Test
	public void testGetRegistryService() {
		org.osgi.framework.ServiceReference<Registry> serviceReference =
			_bundleContext.getServiceReference(Registry.class);

		Registry registry = _bundleContext.getService(serviceReference);

		Assert.assertNotNull(registry);
	}

	@Test
	public void testGetRegistryServiceReference() {
		org.osgi.framework.ServiceReference<Registry> serviceReference =
			_bundleContext.getServiceReference(Registry.class);

		Assert.assertNotNull(serviceReference);
	}

	@Test
	public void testGetServiceByClass() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		InterfaceOne registeredOne = registry.getService(InterfaceOne.class);

		Assert.assertEquals(one, registeredOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceByClassName() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		InterfaceOne registeredOne = registry.getService(
			InterfaceOne.class.getName());

		Assert.assertEquals(one, registeredOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceByClassNameAndFilterString() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, oneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		InterfaceOne[] services = registry.getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(1, services.length);

		serviceRegistrationA.unregister();

		services = registry.getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(1, services.length);

		serviceRegistrationB.unregister();

		services = registry.getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertNull(services);
	}

	@Test
	public void testGetServiceReferenceByClass() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			registry.getServiceReference(InterfaceOne.class);

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredOne = registry.getService(serviceReference);

		Assert.assertEquals(one, registeredOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceReferenceByClassName() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			registry.getServiceReference(InterfaceOne.class.getName());

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredOne = registry.getService(serviceReference);

		Assert.assertEquals(one, registeredOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceReferencesByClass() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, oneB);

		Assert.assertNotNull(serviceRegistrationB);

		Collection<ServiceReference<InterfaceOne>> serviceReferences =
			registry.getServiceReferences(InterfaceOne.class, null);

		Assert.assertEquals(2, serviceReferences.size());

		serviceRegistrationA.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class, null);

		Assert.assertEquals(1, serviceReferences.size());

		serviceRegistrationB.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class, null);

		Assert.assertEquals(0, serviceReferences.size());
	}

	@Test
	public void testGetServiceReferencesByClassAndFilterString()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, oneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		Collection<ServiceReference<InterfaceOne>> serviceReferences =
			registry.getServiceReferences(InterfaceOne.class, filterString);

		Assert.assertEquals(1, serviceReferences.size());

		serviceRegistrationA.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class, filterString);

		Assert.assertEquals(1, serviceReferences.size());

		serviceRegistrationB.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class, filterString);

		Assert.assertEquals(0, serviceReferences.size());
	}

	@Test
	public void testGetServiceReferencesByClassName() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, oneB);

		Assert.assertNotNull(serviceRegistrationB);

		ServiceReference<InterfaceOne>[] serviceReferences =
			registry.getServiceReferences(InterfaceOne.class.getName(), null);

		Assert.assertNotNull(serviceReferences);
		Assert.assertEquals(2, serviceReferences.length);

		serviceRegistrationA.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class.getName(), null);

		Assert.assertEquals(1, serviceReferences.length);

		serviceRegistrationB.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class.getName(), null);

		Assert.assertNull(serviceReferences);
	}

	@Test
	public void testGetServiceReferencesByClassNameAndFilterString()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, oneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		ServiceReference<InterfaceOne>[] serviceReferences =
			registry.getServiceReferences(
				InterfaceOne.class.getName(), filterString);

		Assert.assertNotNull(serviceReferences);
		Assert.assertEquals(1, serviceReferences.length);

		serviceRegistrationA.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(1, serviceReferences.length);

		serviceRegistrationB.unregister();

		serviceReferences = registry.getServiceReferences(
			InterfaceOne.class.getName(), filterString);

		Assert.assertNull(serviceReferences);
	}

	@Test
	public void testGetServicesByClassAndFilterString() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, oneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		Collection<InterfaceOne> services = registry.getServices(
			InterfaceOne.class, filterString);

		Assert.assertEquals(1, services.size());

		serviceRegistrationA.unregister();

		services = registry.getServices(InterfaceOne.class, filterString);

		Assert.assertEquals(1, services.size());

		serviceRegistrationB.unregister();

		services = registry.getServices(InterfaceOne.class, filterString);

		Assert.assertEquals(0, services.size());
	}

	@Test
	public void testRegisterServiceByClass() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredOne = registry.getService(serviceReference);

		Assert.assertEquals(one, registeredOne);

		serviceRegistration.unregister();

		registeredOne = registry.getService(serviceReference);

		Assert.assertNull(registeredOne);
	}

	@Test
	public void testRegisterServiceByClassAndProperties() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "A");
		properties.put("b.property", "B");

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class, one, properties);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals(serviceReference.getProperty("a.property"), "A");
		Assert.assertEquals(serviceReference.getProperty("b.property"), "B");
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		InterfaceOne registeredOne = registry.getService(serviceReference);

		Assert.assertNull(registeredOne);
	}

	@Test
	public void testRegisterServiceByClassNames() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceBoth one = new InterfaceBoth() {};

		ServiceRegistration<?> serviceRegistration = registry.registerService(
			new String[] {
				InterfaceOne.class.getName(), InterfaceTwo.class.getName()
			},
			one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<?> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		Object registeredOne = registry.getService(serviceReference);

		Assert.assertEquals(one, registeredOne);
		Assert.assertTrue(registeredOne instanceof InterfaceOne);
		Assert.assertTrue(registeredOne instanceof InterfaceTwo);

		serviceRegistration.unregister();

		registeredOne = registry.getService(serviceReference);

		Assert.assertNull(registeredOne);
	}

	@Test
	public void testRegisterServiceByClassNamesAndProperties() {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "E");
		properties.put("b.property", "F");

		ServiceRegistration<?> serviceRegistration = registry.registerService(
			new String[] {
				InterfaceOne.class.getName(), InterfaceTwo.class.getName()
			},
			new InterfaceBoth() {}, properties);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<?> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals(serviceReference.getProperty("a.property"), "E");
		Assert.assertEquals(serviceReference.getProperty("b.property"), "F");
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		Object registeredOne = registry.getService(serviceReference);

		Assert.assertNull(registeredOne);
	}

	@Test
	public void testRegisterServiceByFilterString() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(InterfaceOne.class.getName(), one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredOne = registry.getService(serviceReference);

		Assert.assertEquals(one, registeredOne);

		serviceRegistration.unregister();

		registeredOne = registry.getService(serviceReference);

		Assert.assertNull(registeredOne);
	}

	@Test
	public void testRegisterServiceByFilterStringAndProperties() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne one = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "C");
		properties.put("b.property", "D");

		ServiceRegistration<InterfaceOne> serviceRegistration =
			registry.registerService(
				InterfaceOne.class.getName(), one, properties);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals(serviceReference.getProperty("a.property"), "C");
		Assert.assertEquals(serviceReference.getProperty("b.property"), "D");
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		InterfaceOne registeredOne = registry.getService(serviceReference);

		Assert.assertNull(registeredOne);
	}

	@Test
	public void testTrackServicesByClass() {
		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<InterfaceOne, InterfaceOne> serviceTracker =
			registry.trackServices(InterfaceOne.class);

		testTrackServices(serviceTracker, 2);
	}

	@Test
	public void testTrackServicesByClassAndServiceTrackerCustomizer() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne oneA = getInstance();
		InterfaceOne oneB = getInstance();
		AtomicReference<TrackedOne> referenceA =
			new AtomicReference<TrackedOne>();
		AtomicReference<TrackedOne> referenceB =
			new AtomicReference<TrackedOne>();

		ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
			serviceTrackerCustomizer =
				new MockServiceTrackerCustomizer(
					oneA, oneB, referenceA, referenceB);

		ServiceTracker<InterfaceOne, TrackedOne> serviceTracker =
			registry.trackServices(
				InterfaceOne.class, serviceTrackerCustomizer);

		serviceTracker.open();

		Assert.assertTrue(serviceTracker.isEmpty());
		Assert.assertEquals(0, serviceTracker.size());

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, oneB, properties);

		Assert.assertNotNull(serviceRegistrationB);
		Assert.assertFalse(serviceTracker.isEmpty());
		Assert.assertEquals(referenceA.get(), serviceTracker.getService());
		Assert.assertEquals(
			referenceB.get(),
			serviceTracker.getService(
				serviceRegistrationB.getServiceReference()));

		ServiceReference<InterfaceOne>[] serviceReferences =
			serviceTracker.getServiceReferences();

		Assert.assertEquals(2, serviceReferences.length);

		Object[] services = serviceTracker.getServices();

		Assert.assertEquals(2, services.length);

		SortedMap<ServiceReference<InterfaceOne>, TrackedOne>
			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(2, trackedServiceReferences.size());
		Assert.assertEquals(
			referenceA.get(),
			trackedServiceReferences.get(trackedServiceReferences.firstKey()));
		Assert.assertEquals(
			referenceB.get(),
			trackedServiceReferences.get(trackedServiceReferences.lastKey()));

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTracker.size());

		serviceRegistrationB.unregister();

		Assert.assertEquals(0, serviceTracker.size());

		trackedServiceReferences = serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(0, trackedServiceReferences.size());

		serviceTracker.close();
	}

	@Test
	public void testTrackServicesByClassName() {
		Registry registry = RegistryUtil.getRegistry();

		ServiceTracker<InterfaceOne, InterfaceOne> serviceTracker =
			registry.trackServices(InterfaceOne.class.getName());

		testTrackServices(serviceTracker, 2);
	}

	@Test
	public void testTrackServicesByClassNameAndServiceTrackerCustomizer() {
		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne oneA = getInstance();
		InterfaceOne oneB = getInstance();
		AtomicReference<TrackedOne> referenceA =
			new AtomicReference<TrackedOne>();
		AtomicReference<TrackedOne> referenceB =
			new AtomicReference<TrackedOne>();

		ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
			serviceTrackerCustomizer =
				new MockServiceTrackerCustomizer(
					oneA, oneB, referenceA, referenceB);

		ServiceTracker<InterfaceOne, TrackedOne> serviceTracker =
			registry.trackServices(
				InterfaceOne.class.getName(), serviceTrackerCustomizer);

		serviceTracker.open();

		Assert.assertTrue(serviceTracker.isEmpty());
		Assert.assertEquals(0, serviceTracker.size());

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, oneB, properties);

		Assert.assertNotNull(serviceRegistrationB);
		Assert.assertFalse(serviceTracker.isEmpty());
		Assert.assertEquals(referenceA.get(), serviceTracker.getService());
		Assert.assertEquals(
			referenceB.get(),
			serviceTracker.getService(
				serviceRegistrationB.getServiceReference()));

		ServiceReference<InterfaceOne>[] serviceReferences =
			serviceTracker.getServiceReferences();

		Assert.assertEquals(2, serviceReferences.length);

		Object[] services = serviceTracker.getServices();

		Assert.assertEquals(2, services.length);

		SortedMap<ServiceReference<InterfaceOne>, TrackedOne>
			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(2, trackedServiceReferences.size());
		Assert.assertEquals(
			referenceA.get(),
			trackedServiceReferences.get(trackedServiceReferences.firstKey()));
		Assert.assertEquals(
			referenceB.get(),
			trackedServiceReferences.get(trackedServiceReferences.lastKey()));

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTracker.size());

		serviceRegistrationB.unregister();

		Assert.assertEquals(0, serviceTracker.size());

		trackedServiceReferences = serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(0, trackedServiceReferences.size());

		serviceTracker.close();
	}

	@Test
	public void testTrackServicesByFilter() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter("(a.property=G)");

		ServiceTracker<InterfaceOne, InterfaceOne> serviceTracker =
			registry.trackServices(filter);

		testTrackServices(serviceTracker, 1);
	}

	@Test
	public void testTrackServicesByFilterAndServiceTrackerCustomizer()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter("(a.property=G)");

		InterfaceOne oneA = getInstance();
		InterfaceOne oneB = getInstance();
		AtomicReference<TrackedOne> referenceA =
			new AtomicReference<TrackedOne>();
		AtomicReference<TrackedOne> referenceB =
			new AtomicReference<TrackedOne>();

		ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
			serviceTrackerCustomizer =
				new MockServiceTrackerCustomizer(
					oneA, oneB, referenceA, referenceB);

		ServiceTracker<InterfaceOne, TrackedOne> serviceTracker =
			registry.trackServices(filter, serviceTrackerCustomizer);

		serviceTracker.open();

		Assert.assertTrue(serviceTracker.isEmpty());
		Assert.assertEquals(0, serviceTracker.size());

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, oneB, properties);

		Assert.assertNotNull(serviceRegistrationB);
		Assert.assertFalse(serviceTracker.isEmpty());
		Assert.assertEquals(referenceB.get(), serviceTracker.getService());
		Assert.assertEquals(
			referenceB.get(),
			serviceTracker.getService(
				serviceRegistrationB.getServiceReference()));

		ServiceReference<InterfaceOne>[] serviceReferences =
			serviceTracker.getServiceReferences();

		Assert.assertEquals(1, serviceReferences.length);

		Object[] services = serviceTracker.getServices();

		Assert.assertEquals(1, services.length);

		SortedMap<ServiceReference<InterfaceOne>, TrackedOne>
			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(1, trackedServiceReferences.size());
		Assert.assertEquals(
			referenceB.get(),
			trackedServiceReferences.get(trackedServiceReferences.firstKey()));
		Assert.assertEquals(
			referenceB.get(),
			trackedServiceReferences.get(trackedServiceReferences.lastKey()));

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTracker.size());

		serviceRegistrationB.unregister();

		Assert.assertEquals(0, serviceTracker.size());

		trackedServiceReferences = serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(0, trackedServiceReferences.size());

		serviceTracker.close();
	}

	protected InterfaceOne getInstance() {
		return new InterfaceOne() {};
	}

	protected void testTrackServices(
		ServiceTracker<InterfaceOne, InterfaceOne> serviceTracker,
		int expectedServicesCount) {

		serviceTracker.open();

		Assert.assertTrue(serviceTracker.isEmpty());
		Assert.assertEquals(0, serviceTracker.size());

		Registry registry = RegistryUtil.getRegistry();

		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			registry.registerService(InterfaceOne.class, oneB, properties);

		Assert.assertNotNull(serviceRegistrationB);
		Assert.assertFalse(serviceTracker.isEmpty());
		Assert.assertEquals(
			(expectedServicesCount == 2) ? oneA : oneB,
			serviceTracker.getService());
		Assert.assertEquals(
			oneB,
			serviceTracker.getService(
				serviceRegistrationB.getServiceReference()));

		ServiceReference<InterfaceOne>[] serviceReferences =
			serviceTracker.getServiceReferences();

		Assert.assertEquals(expectedServicesCount, serviceReferences.length);

		Object[] services = serviceTracker.getServices();

		Assert.assertEquals(expectedServicesCount, services.length);

		SortedMap<ServiceReference<InterfaceOne>, InterfaceOne>
			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(
			expectedServicesCount, trackedServiceReferences.size());
		Assert.assertEquals(
			(expectedServicesCount == 2) ? oneA : oneB,
			trackedServiceReferences.get(trackedServiceReferences.firstKey()));
		Assert.assertEquals(
			oneB,
			trackedServiceReferences.get(trackedServiceReferences.lastKey()));

		serviceRegistrationA.unregister();

		Assert.assertEquals(1, serviceTracker.size());

		serviceRegistrationB.unregister();

		Assert.assertEquals(0, serviceTracker.size());

		trackedServiceReferences = serviceTracker.getTrackedServiceReferences();

		Assert.assertNotNull(trackedServiceReferences);
		Assert.assertEquals(0, trackedServiceReferences.size());

		serviceTracker.close();
	}

	private BundleContext _bundleContext;

	private class MockServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<InterfaceOne, TrackedOne> {

		public MockServiceTrackerCustomizer(
			InterfaceOne oneA, InterfaceOne oneB,
			AtomicReference<TrackedOne> referenceA,
			AtomicReference<TrackedOne> referenceB) {

			_oneA = oneA;
			_oneB = oneB;
			_referenceA = referenceA;
			_referenceB = referenceB;
		}

		@Override
		public TrackedOne addingService(
			ServiceReference<InterfaceOne> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			InterfaceOne service = registry.getService(serviceReference);

			TrackedOne trackedOne = new TrackedOne();

			if (service == _oneA) {
				_referenceA.set(trackedOne);
			}
			else if (service == _oneB) {
				_referenceB.set(trackedOne);
			}

			return trackedOne;
		}

		@Override
		public void modifiedService(
			ServiceReference<InterfaceOne> serviceReference,
			TrackedOne service) {
		}

		@Override
		public void removedService(
			ServiceReference<InterfaceOne> serviceReference,
			TrackedOne service) {
		}

		private InterfaceOne _oneA;
		private InterfaceOne _oneB;
		private AtomicReference<TrackedOne> _referenceA;
		private AtomicReference<TrackedOne> _referenceB;

	}

}