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

package com.liferay.portal.kernel.scheduler.config;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.scheduler.Scheduler;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;

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
		for (Map.Entry<String, List<Scheduler>> schedulers :
				_schedulers.entrySet()) {
			for (Scheduler scheduler : schedulers.getValue()) {
				try {
					_schedulerEngine.unschedule(scheduler.getTrigger());
				}
				catch (Exception ex) {
					_log.error("Failed to unschedule:" + scheduler, ex);
				}
			}
		}
		_schedulers.clear();
	}

	public void init() {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		try {
			ClassLoader operatingClassLoader = getOperatingClassloader();

			Thread.currentThread().setContextClassLoader(operatingClassLoader);

			for (Map.Entry<String, List<Scheduler>> schedulers :
				_schedulers.entrySet()) {

				String destinationName = schedulers.getKey();

				for (Scheduler scheduler : schedulers.getValue()) {
					try {
						_messageBus.registerMessageListener(
							destinationName, scheduler.getListener());
						_schedulerEngine.schedule(
							scheduler.getTrigger(), scheduler.getDescription(),
							destinationName, null);
					}
					catch (Exception ex) {
						_log.error("Failed to schedule:" + scheduler, ex);
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

	public void setSchedulers(Map<String, List<Scheduler>> schedulers) {
		_schedulers = schedulers;
	}

	private static Log _log =
		LogFactoryUtil.getLog(AbstractSchedulingConfigurator.class);

	protected abstract ClassLoader getOperatingClassloader();

	private MessageBus _messageBus;

	private SchedulerEngine _schedulerEngine;

	private Map<String, List<Scheduler>> _schedulers =
		new HashMap<String, List<Scheduler>>();

}