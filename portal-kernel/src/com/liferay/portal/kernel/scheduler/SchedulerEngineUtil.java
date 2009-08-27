/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;

import java.util.Date;
import java.util.List;

/**
 * <a href="SchedulerEngineUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class SchedulerEngineUtil {

	public static List<SchedulerRequest> getScheduledJobs(String groupName)
		throws SchedulerException {

		return _instance._getScheduledJobs(groupName);
	}

	public static void init(SchedulerEngine defaultScheduler) {
		_instance._init(defaultScheduler);
	}

	public static void schedule(
			String groupName, long interval, Date startDate, Date endDate,
			String description, String destinationName, String messageBody)
		throws SchedulerException {

		_instance._schedule(
			groupName, interval, startDate, endDate, description,
			destinationName, messageBody);
	}

	public static void schedule(
			String groupName, String cronText, Date startDate, Date endDate,
			String description, String destinationName, String messageBody)
		throws SchedulerException {

		_instance._schedule(
			groupName, cronText, startDate, endDate, description,
			destinationName, messageBody);
	}

	public static void shutdown() throws SchedulerException {
		_instance._shutdown();
	}

	public static void start() throws SchedulerException {
		_instance._start();
	}

	public static void unschedule(String jobName, String groupName)
		throws SchedulerException {

		_instance._unschedule(jobName, groupName);
	}

	private List<SchedulerRequest> _getScheduledJobs(String groupName)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs(groupName);
	}

	private void _init(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	private void _schedule(
			String groupName, long interval, Date startDate, Date endDate,
			String description, String destinationName, String messageBody)
		throws SchedulerException {

		_schedulerEngine.schedule(
			groupName, interval, startDate, endDate, description,
			destinationName, messageBody);
	}

	private void _schedule(
			String groupName, String cronText, Date startDate, Date endDate,
			String description, String destinationName, String messageBody)
		throws SchedulerException {

		_schedulerEngine.schedule(
			groupName, cronText, startDate, endDate, description,
			destinationName, messageBody);
	}

	private void _shutdown() throws SchedulerException {
		_schedulerEngine.shutdown();
	}

	private void _start() throws SchedulerException {
		_schedulerEngine.start();
	}

	private void _unschedule(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.unschedule(jobName, groupName);
	}

	private static SchedulerEngineUtil _instance = new SchedulerEngineUtil();

	private SchedulerEngine _schedulerEngine;

}