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

package com.liferay.portal.kernel.scheduler.config;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="AbstractSchedulingConfigurator.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public abstract class AbstractSchedulingConfigurator
	implements SchedulingConfigurator {

	public void destroy() {
		for (Map.Entry<String, List<SchedulerEntry>> schedulerEntries :
				_schedulerEntries.entrySet()) {

			for (SchedulerEntry schedulerEntry : schedulerEntries.getValue()) {
				try {
					destroySchedulerEntry(schedulerEntry);
				}
				catch (Exception e) {
					_log.error("Unable to unschedule " + schedulerEntry, e);
				}
			}
		}

		_schedulerEntries.clear();
	}

	public void init() {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		try {
			ClassLoader operatingClassLoader = getOperatingClassloader();

			Thread.currentThread().setContextClassLoader(operatingClassLoader);

			for (Map.Entry<String, List<SchedulerEntry>> schedulerEntries :
					_schedulerEntries.entrySet()) {

				String destinationName = schedulerEntries.getKey();

				for (SchedulerEntry schedulerEntry :
						schedulerEntries.getValue()) {

					try {
						initSchedulerEntry(destinationName, schedulerEntry);
					}
					catch (Exception e) {
						_log.error("Unable to schedule " + schedulerEntry, e);
					}
				}
			}
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	public void setSchedulerEngine(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	public void setSchedulerEntries(
		Map<String, List<SchedulerEntry>> schedulerEntries) {

		_schedulerEntries = schedulerEntries;
	}

	protected abstract ClassLoader getOperatingClassloader();

	protected void destroySchedulerEntry(SchedulerEntry schedulerEntry)
		throws Exception {

		_schedulerEngine.unschedule(schedulerEntry.getTrigger());
	}

	protected void initSchedulerEntry(
			String destinationName, SchedulerEntry schedulerEntry)
		throws Exception {

		_messageBus.registerMessageListener(
			destinationName, schedulerEntry.getEventListener());

		_schedulerEngine.schedule(
			schedulerEntry.getTrigger(), schedulerEntry.getDescription(),
			destinationName, null);
	}

	private static Log _log = LogFactoryUtil.getLog(
		AbstractSchedulingConfigurator.class);

	private MessageBus _messageBus;
	private SchedulerEngine _schedulerEngine;
	private Map<String, List<SchedulerEntry>> _schedulerEntries =
		new HashMap<String, List<SchedulerEntry>>();

}