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

package com.liferay.counter.service;

import com.liferay.counter.model.Counter;
import com.liferay.portal.cache.key.SimpleCacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessConfig.Builder;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutorUtil;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.util.InitUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class CounterLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_COUNTER_NAME = StringUtil.randomString();

		CounterLocalServiceUtil.reset(_COUNTER_NAME);

		Counter counter = CounterLocalServiceUtil.createCounter(_COUNTER_NAME);

		CounterLocalServiceUtil.updateCounter(counter);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		CounterLocalServiceUtil.reset(_COUNTER_NAME);
	}

	@Test
	public void testConcurrentIncrement() throws Exception {
		String classPath = ClassPathUtil.getJVMClassPath(true);

		Builder builder = new Builder();

		builder.setArguments(
			Arrays.asList("-Xmx1024m", "-XX:MaxPermSize=200m"));
		builder.setBootstrapClassPath(classPath);
		builder.setReactClassLoader(PortalClassLoaderUtil.getClassLoader());
		builder.setRuntimeClassPath(classPath);

		ProcessConfig processConfig = builder.build();

		List<Future<Long[]>> futuresList = new ArrayList<>();

		for (int i = 0; i < _PROCESS_COUNT; i++) {
			ProcessCallable<Long[]> processCallable =
				new IncrementProcessCallable(
					"Increment Process-" + i, _COUNTER_NAME, _INCREMENT_COUNT);

			ProcessChannel<Long[]> processChannel = ProcessExecutorUtil.execute(
				processConfig, processCallable);

			Future<Long[]> futures =
				processChannel.getProcessNoticeableFuture();

			futuresList.add(futures);
		}

		int total = _PROCESS_COUNT * _INCREMENT_COUNT;

		List<Long> ids = new ArrayList<>(total);

		for (Future<Long[]> futures : futuresList) {
			ids.addAll(Arrays.asList(futures.get()));
		}

		Assert.assertEquals(total, ids.size());

		Collections.sort(ids);

		for (int i = 0; i < total; i++) {
			Long id = ids.get(i);

			Assert.assertEquals(i + 1, id.intValue());
		}
	}

	private static String _COUNTER_NAME;

	private static final int _INCREMENT_COUNT = 10000;

	private static final int _PROCESS_COUNT = 4;

	private static class IncrementProcessCallable
		implements ProcessCallable<Long[]> {

		public IncrementProcessCallable(
			String processName, String counterName, int incrementCount) {

			_processName = processName;
			_counterName = counterName;
			_incrementCount = incrementCount;
		}

		@Override
		public Long[] call() throws ProcessException {
			RegistryUtil.setRegistry(new BasicRegistryImpl());

			System.setProperty(
				PropsKeys.COUNTER_INCREMENT + "." + _counterName, "1");

			System.setProperty("catalina.base", ".");
			System.setProperty("external-properties", "portal-test.properties");

			CacheKeyGeneratorUtil cacheKeyGeneratorUtil =
				new CacheKeyGeneratorUtil();

			cacheKeyGeneratorUtil.setDefaultCacheKeyGenerator(
				new SimpleCacheKeyGenerator());

			InitUtil.initWithSpring(
				Arrays.asList(
					"META-INF/base-spring.xml", "META-INF/hibernate-spring.xml",
					"META-INF/infrastructure-spring.xml",
					"META-INF/management-spring.xml",
					"META-INF/counter-spring.xml"),
				false);

			List<Long> ids = new ArrayList<>();

			try {
				for (int i = 0; i < _incrementCount; i++) {
					ids.add(CounterLocalServiceUtil.increment(_counterName));
				}
			}
			catch (SystemException se) {
				throw new ProcessException(se);
			}

			return ids.toArray(new Long[ids.size()]);
		}

		@Override
		public String toString() {
			return _processName;
		}

		private static final long serialVersionUID = 1L;

		private String _counterName;
		private int _incrementCount;
		private String _processName;

	}

}