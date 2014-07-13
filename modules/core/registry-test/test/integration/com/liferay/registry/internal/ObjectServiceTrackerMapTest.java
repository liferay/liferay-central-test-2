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
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.ServiceTrackerMapFactory;

import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;

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
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class ObjectServiceTrackerMapTest {

	@Before
	public void setUp() throws BundleException {
		_bundle.start();

		_bundleContext = _bundle.getBundleContext();
	}

	@After
	public void tearDown() throws BundleException {
		_bundle.stop();
	}

	@Test
	public void testGetServiceAfterRemoval() {
		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap = createServiceTrackerMap();

		ServiceRegistration<TrackedOne> sr = registerService(
			new TrackedOne());

		Assert.assertNotNull(ServiceTrackerMap.getService("aTarget"));

		sr.unregister();

		Assert.assertNull(ServiceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceGetsReplacedAfterRemoval() {
		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap = createServiceTrackerMap();

		TrackedOne TrackedOne1 = new TrackedOne();
		ServiceRegistration<TrackedOne> sr1 = registerService(
			TrackedOne1, 2);

		TrackedOne TrackedOne2 = new TrackedOne();

		registerService(TrackedOne2, 1);

		Assert.assertEquals(
			TrackedOne1, ServiceTrackerMap.getService("aTarget"));

		sr1.unregister();

		Assert.assertEquals(
			TrackedOne2, ServiceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceGetsReplacedAfterRemovalInverseOrder() {
		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap = createServiceTrackerMap();

		TrackedOne TrackedOne2 = new TrackedOne();

		registerService(TrackedOne2, 1);

		TrackedOne TrackedOne1 = new TrackedOne();

		ServiceRegistration<TrackedOne> sr1 = registerService(
			TrackedOne1, 2);

		Assert.assertEquals(
			TrackedOne1, ServiceTrackerMap.getService("aTarget"));

		sr1.unregister();

		Assert.assertEquals(
			TrackedOne2, ServiceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceIsNullAfterDeregistration() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			createServiceTrackerMap();

		ServiceRegistration<TrackedOne> sr1 = registerService(
			new TrackedOne());
		ServiceRegistration<TrackedOne> sr2 = registerService(
			new TrackedOne());
		ServiceRegistration<TrackedOne> sr3 = registerService(
			new TrackedOne());

		Assert.assertNotNull(serviceTrackerMap.getService("aTarget"));

		sr1.unregister();
		sr2.unregister();
		sr3.unregister();

		Assert.assertNull(serviceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceWithCustomComparator() {
		ServiceTrackerMap<String, TrackedOne> serviceTrackerMap =
			ServiceTrackerMapFactory.createObjectServiceTrackerMap(
				TrackedOne.class, "(target=*)",
				new ServiceTrackerMapFactory.PropertyServiceReferenceMapper<String>(
					"target"),
				new Comparator<ServiceReference<TrackedOne>>() {
					@Override
					public int compare(
						ServiceReference<TrackedOne> o1,
						ServiceReference<TrackedOne> o2) {

						return -1;
					}
				}
		);

		serviceTrackerMap.open();

		TrackedOne TrackedOne1 = new TrackedOne();

		ServiceRegistration<TrackedOne> sr1 = registerService(
			TrackedOne1);

		TrackedOne TrackedOne2 = new TrackedOne();

		ServiceRegistration<TrackedOne> sr2 = registerService(
			TrackedOne2);

		Assert.assertEquals(
			TrackedOne2, serviceTrackerMap.getService("aTarget"));

		sr1.unregister();
		sr2.unregister();

		registerService(TrackedOne2);

		registerService(TrackedOne1);

		Assert.assertEquals(
			TrackedOne1, serviceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceWithCustomResolver() {
		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap =

			ServiceTrackerMapFactory.createObjectServiceTrackerMap(
				TrackedOne.class, "(&(target=*)(other=*))",
				new ServiceReferenceMapper<String>() {

					@Override
					public void map(
						ServiceReference<?> serviceReference,
						Emitter<String> keys) {

						keys.emit(
							serviceReference.getProperty("target") + " - " +
								serviceReference.getProperty("other")
						);
					}
				}
			);

		ServiceTrackerMap.open();

		Dictionary<String, String> properties = new Hashtable<String, String>();

		properties.put("target", "aTarget");
		properties.put("other", "aProperty");

		_bundleContext.registerService(
			TrackedOne.class, new TrackedOne(), properties);

		Assert.assertNotNull(
			ServiceTrackerMap.getService("aTarget - aProperty"));
	}

	@Test
	public void testGetServiceWithIncorrectKey() {
		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap = createServiceTrackerMap();

		registerService(new TrackedOne(), "anotherTarget");

		Assert.assertNull(ServiceTrackerMap.getService("aTarget"));
	}

	@Test
	public void testGetServiceWithSimpleRegistration() {
		ServiceTrackerMap<String, TrackedOne>
			ServiceTrackerMap = createServiceTrackerMap();

		registerService(new TrackedOne());

		Assert.assertNotNull(ServiceTrackerMap.getService("aTarget"));
	}

	@ArquillianResource
	public Bundle _bundle;

	protected ServiceTrackerMap<String, TrackedOne> createServiceTrackerMap() {
		ServiceTrackerMap<String, TrackedOne>
			serviceTrackerMap =
				ServiceTrackerMapFactory.createObjectServiceTrackerMap(
					TrackedOne.class, "target");

		serviceTrackerMap.open();

		return serviceTrackerMap;
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne) {

		return registerService(trackedOne, "aTarget");
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne, int ranking) {

		return registerService(trackedOne, "aTarget", ranking);
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne, String target) {

		Dictionary<String, Object> properties = new Hashtable<String, Object>();

		properties.put("target", target);

		return _bundleContext.registerService(
			TrackedOne.class, trackedOne, properties);
	}

	protected ServiceRegistration<TrackedOne> registerService(
		TrackedOne trackedOne, String target, int ranking) {

		Dictionary<String, Object> properties = new Hashtable<String, Object>();

		properties.put("service.ranking", ranking);
		properties.put("target", target);

		return _bundleContext.registerService(
			TrackedOne.class, trackedOne, properties);
	}

	private BundleContext _bundleContext;

}