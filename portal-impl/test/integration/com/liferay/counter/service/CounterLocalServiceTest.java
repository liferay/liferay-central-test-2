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

package com.liferay.counter.service;

import com.liferay.counter.model.Counter;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.PwdGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class CounterLocalServiceTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		CounterLocalServiceUtil.reset(_counterName);

		Counter counter = CounterLocalServiceUtil.createCounter(_counterName);

		CounterLocalServiceUtil.updateCounter(counter);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		CounterLocalServiceUtil.reset(_counterName);
	}

	@Test
	public void testConcurrentIncrement() throws Exception {
		String classPath = ClassPathUtil.getJVMClassPath(true);

		List<String> jvmArguments = Arrays.asList(
			"-Xmx1024m", "-XX:MaxPermSize=200m");

		List<Future<Long[]>> futuresList = new ArrayList<Future<Long[]>>();

		for (int i = 0; i < _PROCESS_COUNT; i++) {
			ProcessCallable<Long[]> processCallable =
				new IncrementProcessCallable("Increment Process-" + i);

			Future<Long[]> futures = ProcessExecutor.execute(
				classPath, jvmArguments, processCallable);

			futuresList.add(futures);
		}

		int total = _PROCESS_COUNT * _INCREMENT_COUNT;

		List<Long> ids = new ArrayList<Long>(total);

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

	private static final int _INCREMENT_COUNT = 10000;

	private static final int _PROCESS_COUNT = 4;

	private static String _counterName = PwdGenerator.getPassword();

	private static class IncrementProcessCallable
		implements ProcessCallable<Long[]> {

		public IncrementProcessCallable(String processName) {
			_processName = processName;
		}

		@Override
		public Long[] call() throws ProcessException {
			System.setProperty("catalina.base", ".");

			PropsUtil.set(PropsValues.COUNTER_INCREMENT + _counterName, "1");

			InitUtil.initWithSpring();

			List<Long> ids = new ArrayList<Long>();

			try {
				for (int i = 0; i < _INCREMENT_COUNT; i++) {
					ids.add(CounterLocalServiceUtil.increment(_counterName));
				}
			}
			catch (SystemException se) {
				throw new ProcessException(se);
			}
			finally {
				try {
					SchedulerEngineHelperUtil.shutdown();
				}
				catch (SchedulerException se) {
					throw new ProcessException(se);
				}
			}

			return ids.toArray(new Long[ids.size()]);
		}

		@Override
		public String toString() {
			return _processName;
		}

		private static final long serialVersionUID = 1L;

		private String _processName;

	}

}