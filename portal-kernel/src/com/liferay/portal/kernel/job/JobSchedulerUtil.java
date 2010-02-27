/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.job;

/**
 * <a href="JobSchedulerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class JobSchedulerUtil {

	public static JobScheduler getJobScheduler() {
		return _jobScheduler;
	}

	public static void schedule(IntervalJob intervalJob) {
		getJobScheduler().schedule(intervalJob);
	}

	public static void shutdown() {
		getJobScheduler().shutdown();
	}

	public static void unschedule(IntervalJob intervalJob) {
		getJobScheduler().unschedule(intervalJob);
	}

	public void setJobScheduler(JobScheduler jobScheduler) {
		_jobScheduler = jobScheduler;
	}

	private static JobScheduler _jobScheduler;

}