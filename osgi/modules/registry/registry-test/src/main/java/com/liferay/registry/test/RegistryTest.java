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
		Assert.assertNotNull(_registry);
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
		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		InterfaceOne registeredOne = _registry.getService(InterfaceOne.class);

		Assert.assertEquals(one, registeredOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceByClassName() {
		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		InterfaceOne registeredOne = _registry.getService(
			InterfaceOne.class.getName());

		Assert.assertEquals(one, registeredOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceByClassNameAndFilterString() throws Exception {
		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(InterfaceOne.class, oneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		InterfaceOne[] services = _registry.getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(1, services.length);

		serviceRegistrationA.unregister();

		services = _registry.getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(1, services.length);

		serviceRegistrationB.unregister();

		services = _registry.getServices(
			InterfaceOne.class.getName(), filterString);

		Assert.assertNull(services);
	}

	@Test
	public void testGetServiceReferenceByClass() {
		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			_registry.getServiceReference(InterfaceOne.class);

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredOne = _registry.getService(serviceReference);

		Assert.assertEquals(one, registeredOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceReferenceByClassName() {
		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			_registry.getServiceReference(InterfaceOne.class.getName());

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredOne = _registry.getService(serviceReference);

		Assert.assertEquals(one, registeredOne);

		serviceRegistration.unregister();
	}

	@Test
	public void testGetServiceReferencesByClass() throws Exception {
		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(InterfaceOne.class, oneB);

		Assert.assertNotNull(serviceRegistrationB);

		Collection<ServiceReference<InterfaceOne>> serviceReferences =
			_registry.getServiceReferences(InterfaceOne.class, null);

		Assert.assertEquals(2, serviceReferences.size());

		serviceRegistrationA.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class, null);

		Assert.assertEquals(1, serviceReferences.size());

		serviceRegistrationB.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class, null);

		Assert.assertEquals(0, serviceReferences.size());
	}

	@Test
	public void testGetServiceReferencesByClassAndFilterString()
		throws Exception {

		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(InterfaceOne.class, oneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		Collection<ServiceReference<InterfaceOne>> serviceReferences =
			_registry.getServiceReferences(InterfaceOne.class, filterString);

		Assert.assertEquals(1, serviceReferences.size());

		serviceRegistrationA.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class, filterString);

		Assert.assertEquals(1, serviceReferences.size());

		serviceRegistrationB.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class, filterString);

		Assert.assertEquals(0, serviceReferences.size());
	}

	@Test
	public void testGetServiceReferencesByClassName() throws Exception {
		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(InterfaceOne.class, oneB);

		Assert.assertNotNull(serviceRegistrationB);

		ServiceReference<InterfaceOne>[] serviceReferences =
			_registry.getServiceReferences(InterfaceOne.class.getName(), null);

		Assert.assertNotNull(serviceReferences);
		Assert.assertEquals(2, serviceReferences.length);

		serviceRegistrationA.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class.getName(), null);

		Assert.assertEquals(1, serviceReferences.length);

		serviceRegistrationB.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class.getName(), null);

		Assert.assertNull(serviceReferences);
	}

	@Test
	public void testGetServiceReferencesByClassNameAndFilterString()
		throws Exception {

		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(InterfaceOne.class, oneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		ServiceReference<InterfaceOne>[] serviceReferences =
			_registry.getServiceReferences(
				InterfaceOne.class.getName(), filterString);

		Assert.assertNotNull(serviceReferences);
		Assert.assertEquals(1, serviceReferences.length);

		serviceRegistrationA.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class.getName(), filterString);

		Assert.assertEquals(1, serviceReferences.length);

		serviceRegistrationB.unregister();

		serviceReferences = _registry.getServiceReferences(
			InterfaceOne.class.getName(), filterString);

		Assert.assertNull(serviceReferences);
	}

	@Test
	public void testGetServicesByClassAndFilterString() throws Exception {
		InterfaceOne oneA = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistrationA =
			_registry.registerService(InterfaceOne.class, oneA);

		Assert.assertNotNull(serviceRegistrationA);

		InterfaceOne oneB = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "G");

		ServiceRegistration<InterfaceOne> serviceRegistrationB =
			_registry.registerService(InterfaceOne.class, oneB, properties);

		Assert.assertNotNull(serviceRegistrationB);

		String filterString = "(a.property=G)";

		Collection<InterfaceOne> services = _registry.getServices(
			InterfaceOne.class, filterString);

		Assert.assertEquals(1, services.size());

		serviceRegistrationA.unregister();

		services = _registry.getServices(InterfaceOne.class, filterString);

		Assert.assertEquals(1, services.size());

		serviceRegistrationB.unregister();

		services = _registry.getServices(InterfaceOne.class, filterString);

		Assert.assertEquals(0, services.size());
	}

	@Test
	public void testRegisterServiceByClass() {
		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredOne = _registry.getService(serviceReference);

		Assert.assertEquals(one, registeredOne);

		serviceRegistration.unregister();

		registeredOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredOne);
	}

	@Test
	public void testRegisterServiceByClassAndProperties() {
		InterfaceOne one = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "A");
		properties.put("b.property", "B");

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class, one, properties);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals(serviceReference.getProperty("a.property"), "A");
		Assert.assertEquals(serviceReference.getProperty("b.property"), "B");
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		InterfaceOne registeredOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredOne);
	}

	@Test
	public void testRegisterServiceByClassNames() {
		InterfaceBoth one = new InterfaceBoth() {};

		ServiceRegistration<?> serviceRegistration = _registry.registerService(
			new String[] {
				InterfaceOne.class.getName(), InterfaceTwo.class.getName()
			},
			one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<?> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		Object registeredOne = _registry.getService(serviceReference);

		Assert.assertEquals(one, registeredOne);
		Assert.assertTrue(registeredOne instanceof InterfaceOne);
		Assert.assertTrue(registeredOne instanceof InterfaceTwo);

		serviceRegistration.unregister();

		registeredOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredOne);
	}

	@Test
	public void testRegisterServiceByClassNamesAndProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "E");
		properties.put("b.property", "F");

		ServiceRegistration<?> serviceRegistration = _registry.registerService(
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

		Object registeredOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredOne);
	}

	@Test
	public void testRegisterServiceByFilterString() {
		InterfaceOne one = getInstance();

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(InterfaceOne.class.getName(), one);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);

		InterfaceOne registeredOne = _registry.getService(serviceReference);

		Assert.assertEquals(one, registeredOne);

		serviceRegistration.unregister();

		registeredOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredOne);
	}

	@Test
	public void testRegisterServiceByFilterStringAndProperties() {
		InterfaceOne one = getInstance();

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("a.property", "C");
		properties.put("b.property", "D");

		ServiceRegistration<InterfaceOne> serviceRegistration =
			_registry.registerService(
				InterfaceOne.class.getName(), one, properties);

		Assert.assertNotNull(serviceRegistration);

		ServiceReference<InterfaceOne> serviceReference =
			serviceRegistration.getServiceReference();

		Assert.assertNotNull(serviceReference);
		Assert.assertEquals(serviceReference.getProperty("a.property"), "C");
		Assert.assertEquals(serviceReference.getProperty("b.property"), "D");
		Assert.assertNull(serviceReference.getProperty("c.property"));

		serviceRegistration.unregister();

		InterfaceOne registeredOne = _registry.getService(serviceReference);

		Assert.assertNull(registeredOne);
	}

	@Test
	public void testTrackServicesByClass() {
		TestTrackServices testTrackServices = new TestTrackServices() {

			@Override
			public ServiceTracker<InterfaceOne, InterfaceOne>
				getServiceTracker() {

				return _registry.trackServices(InterfaceOne.class);
			}

		};

		testTrackServices.test(2);
	}

	@Test
	public void testTrackServicesByClassAndServiceTrackerCustomizer() {
		TestTrackServicesByServiceTrackerCustomizer
			testTrackServicesByServiceTrackerCustomizer =
				new TestTrackServicesByServiceTrackerCustomizer() {

					@Override
					public ServiceTracker<InterfaceOne, TrackedOne>
						getServiceTracker(
							ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
								serviceTrackerCustomizer) {

						return _registry.trackServices(
							InterfaceOne.class, serviceTrackerCustomizer);
					}

				};

		testTrackServicesByServiceTrackerCustomizer.test(2);
	}

	@Test
	public void testTrackServicesByClassName() {
		TestTrackServices test = new TestTrackServices() {

			@Override
			public ServiceTracker<InterfaceOne, InterfaceOne>
				getServiceTracker() {

				return _registry.trackServices(InterfaceOne.class.getName());
			}

		};

		test.test(2);
	}

	@Test
	public void testTrackServicesByClassNameAndServiceTrackerCustomizer() {
		TestTrackServicesByServiceTrackerCustomizer
			testTrackServicesByServiceTrackerCustomizer =
				new TestTrackServicesByServiceTrackerCustomizer() {

					@Override
					public ServiceTracker<InterfaceOne, TrackedOne>
						getServiceTracker(
							ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
								serviceTrackerCustomizer) {

						return _registry.trackServices(
							InterfaceOne.class.getName(),
							serviceTrackerCustomizer);
					}

				};

		testTrackServicesByServiceTrackerCustomizer.test(2);
	}

	@Test
	public void testTrackServicesByFilter() throws Exception {
		TestTrackServices testTrackServices = new TestTrackServices() {

			@Override
			public ServiceTracker<InterfaceOne, InterfaceOne>
				getServiceTracker() {

				Filter filter = _registry.getFilter("(a.property=G)");

				return _registry.trackServices(filter);
			}

		};

		testTrackServices.test(1);
	}

	@Test
	public void testTrackServicesByFilterAndServiceTrackerCustomizer()
		throws Exception {

		TestTrackServicesByServiceTrackerCustomizer
			testTrackServicesByServiceTrackerCustomizer =
				new TestTrackServicesByServiceTrackerCustomizer() {

					@Override
					public ServiceTracker<InterfaceOne, TrackedOne>
						getServiceTracker(
							ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
								serviceTrackerCustomizer) {

						Filter filter = _registry.getFilter("(a.property=G)");

						return _registry.trackServices(
							filter, serviceTrackerCustomizer);
					}

				};

		testTrackServicesByServiceTrackerCustomizer.test(1);
	}

	protected InterfaceOne getInstance() {
		return new InterfaceOne() {};
	}

	private BundleContext _bundleContext;
	private Registry _registry = RegistryUtil.getRegistry();

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

			InterfaceOne service = _registry.getService(serviceReference);

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
			TrackedOne trackedOne) {
		}

		@Override
		public void removedService(
			ServiceReference<InterfaceOne> serviceReference,
			TrackedOne trackedOne) {
		}

		private InterfaceOne _oneA;
		private InterfaceOne _oneB;
		private AtomicReference<TrackedOne> _referenceA;
		private AtomicReference<TrackedOne> _referenceB;

	}

	private abstract class TestTrackServices {

		public abstract ServiceTracker<InterfaceOne, InterfaceOne>
			getServiceTracker();

		public void test(int expectedServicesCount) {
			ServiceTracker<InterfaceOne, InterfaceOne> serviceTracker =
				getServiceTracker();

			serviceTracker.open();

			Assert.assertTrue(serviceTracker.isEmpty());
			Assert.assertEquals(0, serviceTracker.size());

			InterfaceOne oneA = getInstance();

			ServiceRegistration<InterfaceOne> serviceRegistrationA =
				_registry.registerService(InterfaceOne.class, oneA);

			Assert.assertNotNull(serviceRegistrationA);

			InterfaceOne oneB = getInstance();

			Map<String, Object> properties = new HashMap<String, Object>();

			properties.put("a.property", "G");

			ServiceRegistration<InterfaceOne> serviceRegistrationB =
				_registry.registerService(InterfaceOne.class, oneB, properties);

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

			Assert.assertEquals(
				expectedServicesCount, serviceReferences.length);

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
				trackedServiceReferences.get(
					trackedServiceReferences.firstKey()));
			Assert.assertEquals(
				oneB,
				trackedServiceReferences.get(
					trackedServiceReferences.lastKey()));

			serviceRegistrationA.unregister();

			Assert.assertEquals(1, serviceTracker.size());

			serviceRegistrationB.unregister();

			Assert.assertEquals(0, serviceTracker.size());

			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

			Assert.assertNotNull(trackedServiceReferences);
			Assert.assertEquals(0, trackedServiceReferences.size());

			serviceTracker.close();
		}
	}

	private abstract class TestTrackServicesByServiceTrackerCustomizer {

		public abstract ServiceTracker<InterfaceOne, TrackedOne>
			getServiceTracker(
				ServiceTrackerCustomizer<InterfaceOne, TrackedOne>
					serviceTrackerCustomizer);

		public void test(int expectedServicesCount) {
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
				getServiceTracker(serviceTrackerCustomizer);

			serviceTracker.open();

			Assert.assertTrue(serviceTracker.isEmpty());
			Assert.assertEquals(0, serviceTracker.size());

			ServiceRegistration<InterfaceOne> serviceRegistrationA =
				_registry.registerService(InterfaceOne.class, oneA);

			Assert.assertNotNull(serviceRegistrationA);

			Map<String, Object> properties = new HashMap<String, Object>();

			properties.put("a.property", "G");

			ServiceRegistration<InterfaceOne> serviceRegistrationB =
				_registry.registerService(InterfaceOne.class, oneB, properties);

			Assert.assertNotNull(serviceRegistrationB);
			Assert.assertFalse(serviceTracker.isEmpty());
			Assert.assertEquals(
				(expectedServicesCount == 2) ? referenceA.get() :
					referenceB.get(), serviceTracker.getService());
			Assert.assertEquals(
				referenceB.get(),
				serviceTracker.getService(
					serviceRegistrationB.getServiceReference()));

			ServiceReference<InterfaceOne>[] serviceReferences =
				serviceTracker.getServiceReferences();

			Assert.assertEquals(
				expectedServicesCount, serviceReferences.length);

			Object[] services = serviceTracker.getServices();

			Assert.assertEquals(expectedServicesCount, services.length);

			SortedMap<ServiceReference<InterfaceOne>, TrackedOne>
				trackedServiceReferences =
					serviceTracker.getTrackedServiceReferences();

			Assert.assertNotNull(trackedServiceReferences);
			Assert.assertEquals(
				expectedServicesCount, trackedServiceReferences.size());
			Assert.assertEquals(
				(expectedServicesCount == 2) ?
					referenceA.get() : referenceB.get(),
				trackedServiceReferences.get(
					trackedServiceReferences.firstKey()));
			Assert.assertEquals(
				referenceB.get(),
				trackedServiceReferences.get(
					trackedServiceReferences.lastKey()));

			serviceRegistrationA.unregister();

			Assert.assertEquals(1, serviceTracker.size());

			serviceRegistrationB.unregister();

			Assert.assertEquals(0, serviceTracker.size());

			trackedServiceReferences =
				serviceTracker.getTrackedServiceReferences();

			Assert.assertNotNull(trackedServiceReferences);
			Assert.assertEquals(0, trackedServiceReferences.size());

			serviceTracker.close();
		}

	}

}