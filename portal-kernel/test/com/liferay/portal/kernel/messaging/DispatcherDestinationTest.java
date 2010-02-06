/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.test.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <a href="DispatcherDestinationTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class DispatcherDestinationTest extends TestCase {

	public static int LISTENER_COUNT = 10000;

	public static int REGISTER_TASK_COUNT = 10;

	public static int TASK_ITERATION_COUNT = 100;

	public static int UNREGISTER_TASK_COUNT = 10;

	public void setUp() throws Exception {
		_executorService = Executors.newFixedThreadPool(
			REGISTER_TASK_COUNT + UNREGISTER_TASK_COUNT);

		_destination = new DummyDispatcherDestination();

		_destination.setName(DispatcherDestinationTest.class.getName());

		_listeners = new MessageListener[LISTENER_COUNT];

		for (int i = 0; i < _listeners.length; i++) {
			_listeners[i] = new DummyMessageListener();
		}

		_tasks = new ArrayList<Callable<Object>>();

		DestinationRegistrationTask registerTask =
			new DestinationRegistrationTask(
					_destination, _listeners, TASK_ITERATION_COUNT, true);

		for (int i = 0; i < REGISTER_TASK_COUNT; i++) {
			_tasks.add(registerTask);
		}

		DestinationRegistrationTask unregisterTask =
			new DestinationRegistrationTask(
					_destination, _listeners, TASK_ITERATION_COUNT, false);

		for (int i = 0; i < UNREGISTER_TASK_COUNT; i++) {
			_tasks.add(unregisterTask);
		}

		_startTime = System.currentTimeMillis();
	}

	public void tearDown() throws Exception {
		_executorService.shutdownNow();
		_executorService.awaitTermination(120, TimeUnit.SECONDS);

		long expectedTime = 100;
		long actualTime = System.currentTimeMillis() - _startTime;

		assertLessThan(expectedTime, actualTime);
	}

	public void testPerformance() throws Exception {
		_executorService.invokeAll(_tasks);
	}

	private BaseDestination _destination;
	private ExecutorService _executorService;
	private MessageListener[] _listeners;
	private long _startTime;
	private List<Callable<Object>> _tasks;

}