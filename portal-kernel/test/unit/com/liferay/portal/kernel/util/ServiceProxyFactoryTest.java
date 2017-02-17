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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.memory.FinalizeAction;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.TimeoutTestRule;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * @author Tina Tian
 */
public class ServiceProxyFactoryTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Test
	public void testBlockingProxy() throws Exception {
		_testBlockingProxy(false);
	}

	@Test
	public void testBlockingProxyWithProxyService() throws Exception {
		_testBlockingProxy(true);
	}

	@Test
	public void testCloseServiceTrackerFinalizeAction() throws Exception {
		TestServiceUtil testServiceUtil = new TestServiceUtil();

		ServiceProxyFactory.newServiceTrackedInstance(
			TestService.class, TestServiceUtil.class, testServiceUtil,
			"nonStaticField", null, false);

		FinalizeAction finalizeAction = null;

		Map<Reference<?>, FinalizeAction> finalizeActions =
			ReflectionTestUtil.getFieldValue(
				FinalizeManager.class, "_finalizeActions");

		for (Map.Entry<Reference<?>, FinalizeAction> entry :
				finalizeActions.entrySet()) {

			Reference<?> reference = entry.getKey();

			if (!(reference instanceof PhantomReference<?>)) {
				continue;
			}

			Object referent = ReflectionTestUtil.getFieldValue(
				reference, "referent");

			if (referent != testServiceUtil) {
				continue;
			}

			finalizeAction = entry.getValue();

			Class<?> clazz = finalizeAction.getClass();

			Assert.assertEquals(
				"com.liferay.portal.kernel.util.ServiceProxyFactory$" +
					"CloseServiceTrackerFinalizeAction",
				clazz.getName());

			break;
		}

		Assert.assertNotNull(finalizeAction);

		final AtomicBoolean atomicBoolean = new AtomicBoolean();

		final ServiceTracker<TestService, TestService> serviceTracker =
			ReflectionTestUtil.getFieldValue(finalizeAction, "_serviceTracker");

		ReflectionTestUtil.setFieldValue(
			finalizeAction, "_serviceTracker",
			ProxyUtil.newProxyInstance(
				FinalizeManager.class.getClassLoader(),
				new Class<?>[] {ServiceTracker.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws Throwable {

						if (method.equals(
								ServiceTracker.class.getMethod("close"))) {

							atomicBoolean.set(true);
						}

						return method.invoke(serviceTracker, args);
					}

				}));

		testServiceUtil = null;

		GCUtil.gc(true);

		ReflectionTestUtil.invoke(
			FinalizeManager.class, "_pollingCleanup", new Class<?>[0]);

		Assert.assertTrue(atomicBoolean.get());
	}

	@Test
	public void testMisc() throws Exception {

		// Test 1, wrong field

		try {
			ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, null,
				"wrongFieldName", null, false);

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(NoSuchFieldException.class, throwable.getClass());
			Assert.assertEquals("wrongFieldName", throwable.getMessage());
		}

		try {
			TestServiceUtil testServiceUtil = new TestServiceUtil();

			ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, testServiceUtil,
				"wrongFieldName", null, false);

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(NoSuchFieldException.class, throwable.getClass());
			Assert.assertEquals("wrongFieldName", throwable.getMessage());
		}

		// Test 2, field is static

		try {
			TestServiceUtil testServiceUtil = new TestServiceUtil();

			ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, testServiceUtil,
				"testService", null, false);

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(
				IllegalArgumentException.class, throwable.getClass());

			Field testServiceField = ReflectionUtil.getDeclaredField(
				TestServiceUtil.class, "testService");

			Assert.assertEquals(
				testServiceField + " is static", throwable.getMessage());
		}

		// Test 3, field is not static

		try {
			ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, "nonStaticField",
				false);

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(
				IllegalArgumentException.class, throwable.getClass());

			Field testServiceField = ReflectionUtil.getDeclaredField(
				TestServiceUtil.class, "nonStaticField");

			Assert.assertEquals(
				testServiceField + " is not static", throwable.getMessage());
		}

		// Test 4, field already set

		TestServiceUtil testServiceUtil = new TestServiceUtil();

		TestService testService = new TestServiceImpl();

		testServiceUtil.nonStaticField = testService;

		ServiceProxyFactory.newServiceTrackedInstance(
			TestService.class, TestServiceUtil.class, testServiceUtil,
			"nonStaticField", null, false);

		Assert.assertSame(testService, testServiceUtil.nonStaticField);

		// Test 5, test constructor

		new ServiceProxyFactory();
	}

	@Test
	public void testNonblockingProxy() throws Exception {
		_testNonBlockingProxy(false);
	}

	@Test
	public void testNonblockingProxyWithFilter() throws Exception {
		_testNonBlockingProxy(true);
	}

	@Test
	public void testNonblockingProxyWithInstanceField() throws Exception {
		TestServiceUtil testServiceUtil = new TestServiceUtil();

		TestService testService = ServiceProxyFactory.newServiceTrackedInstance(
			TestService.class, TestServiceUtil.class, testServiceUtil,
			"nonStaticField", null, false);

		_testNonBlockingProxy(false, testService, testServiceUtil);
	}

	@Test
	public void testNullDummyService() throws Exception {
		TestService testService = ServiceProxyFactory.newServiceTrackedInstance(
			TestService.class, TestServiceUtil.class, "testService", false,
			true);

		Assert.assertNull(testService);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<TestService> serviceRegistration =
			registry.registerService(TestService.class, new TestServiceImpl());

		TestService newTestService = TestServiceUtil.testService;

		Assert.assertEquals(
			_TEST_SERVICE_NAME, newTestService.getTestServiceName());
		Assert.assertEquals(
			_TEST_SERVICE_ID, newTestService.getTestServiceId());

		Assert.assertFalse(ProxyUtil.isProxyClass(newTestService.getClass()));
		Assert.assertSame(TestServiceImpl.class, newTestService.getClass());

		serviceRegistration.unregister();
	}

	@Rule
	public final TestRule testRule = TimeoutTestRule.INSTANCE;

	public static class TestServiceImpl implements TestService {

		@Override
		public long getTestServiceId() {
			return _TEST_SERVICE_ID;
		}

		@Override
		public String getTestServiceName() {
			return _TEST_SERVICE_NAME;
		}

	}

	public interface TestService {

		public long getTestServiceId();

		public String getTestServiceName();

	}

	private void _testBlockingProxy(boolean proxyService) throws Exception {
		final TestService testService =
			ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, "testService", true);

		Assert.assertTrue(ProxyUtil.isProxyClass(testService.getClass()));
		Assert.assertNotSame(TestServiceImpl.class, testService.getClass());

		FutureTask<Void> futureTask = new FutureTask<>(
			new Callable<Void>() {

				@Override
				public Void call() {
					Assert.assertEquals(
						_TEST_SERVICE_NAME, testService.getTestServiceName());
					Assert.assertEquals(
						_TEST_SERVICE_ID, testService.getTestServiceId());

					TestService newTestService = TestServiceUtil.testService;

					if (proxyService) {
						Assert.assertTrue(
							ProxyUtil.isProxyClass(newTestService.getClass()));
						Assert.assertNotSame(
							TestServiceImpl.class, newTestService.getClass());
					}
					else {
						Assert.assertFalse(
							ProxyUtil.isProxyClass(newTestService.getClass()));
						Assert.assertSame(
							TestServiceImpl.class, newTestService.getClass());
					}

					return null;
				}

			});

		Thread thread = new Thread(futureTask);

		thread.start();

		_waitForBlocked(testService, thread);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<TestService> serviceRegistration = null;

		if (proxyService) {
			serviceRegistration = registry.registerService(
				TestService.class,
				(TestService)ProxyFactory.newInstance(
					TestService.class.getClassLoader(),
					new Class<?>[] {TestService.class},
					TestServiceImpl.class.getName()));
		}
		else {
			serviceRegistration = registry.registerService(
				TestService.class, new TestServiceImpl());
		}

		futureTask.get();

		serviceRegistration.unregister();
	}

	private void _testNonBlockingProxy(boolean filterEnabled) throws Exception {
		TestService testService = null;

		if (filterEnabled) {
			testService = ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, "testService",
				"(test.filter=true)", false);
		}
		else {
			testService = ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, "testService", false);
		}

		_testNonBlockingProxy(filterEnabled, testService, null);
	}

	private void _testNonBlockingProxy(
			boolean filterEnabled, TestService testService,
			TestServiceUtil testServiceUtil)
		throws Exception {

		Assert.assertTrue(ProxyUtil.isProxyClass(testService.getClass()));
		Assert.assertNotSame(TestServiceImpl.class, testService.getClass());

		Assert.assertEquals(0, testService.getTestServiceId());
		Assert.assertEquals(null, testService.getTestServiceName());

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<TestService> serviceRegistration = null;

		if (filterEnabled) {
			serviceRegistration = registry.registerService(
				TestService.class, new TestServiceImpl(),
				Collections.singletonMap("test.filter", "true"));
		}
		else {
			serviceRegistration = registry.registerService(
				TestService.class, new TestServiceImpl());
		}

		TestService newTestService = null;

		if (testServiceUtil == null) {
			newTestService = TestServiceUtil.testService;
		}
		else {
			newTestService = testServiceUtil.nonStaticField;
		}

		Assert.assertEquals(
			_TEST_SERVICE_NAME, newTestService.getTestServiceName());
		Assert.assertEquals(
			_TEST_SERVICE_ID, newTestService.getTestServiceId());

		Assert.assertFalse(ProxyUtil.isProxyClass(newTestService.getClass()));
		Assert.assertSame(TestServiceImpl.class, newTestService.getClass());

		serviceRegistration.unregister();
	}

	private void _waitForBlocked(TestService testService, Thread targetThread) {
		InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(
			testService);

		Lock lock = ReflectionTestUtil.getFieldValue(
			invocationHandler, "_lock");

		Object sync = ReflectionTestUtil.getFieldValue(lock, "sync");

		Condition condition = ReflectionTestUtil.getFieldValue(
			invocationHandler, "_realServiceSet");

		while (true) {
			Collection<Thread> waitingThreads = null;

			lock.lock();

			try {
				waitingThreads = ReflectionTestUtil.invoke(
					sync, "getWaitingThreads",
					new Class<?>[] {
						AbstractQueuedSynchronizer.ConditionObject.class
					},
					condition);
			}
			finally {
				lock.unlock();
			}

			if (waitingThreads.contains(targetThread)) {
				return;
			}
		}
	}

	private static final long _TEST_SERVICE_ID = 1234L;

	private static final String _TEST_SERVICE_NAME = "TestServiceName";

	private static class TestServiceUtil {

		public static volatile TestService testService;

		public volatile TestService nonStaticField;

	}

}