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

package com.liferay.portal.job;

import com.liferay.portal.kernel.job.IntervalJob;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="JobClassUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class JobClassUtil {

	public static Class<IntervalJob> get(String jobName) {
		return _jobClasses.get(jobName);
	}

	public static void remove(String jobName) {
		_jobClasses.remove(jobName);
	}

	public static void put(
		String jobName, Class<IntervalJob> intervalJobClass) {

		_jobClasses.put(jobName, intervalJobClass);
	}

	private static Map<String, Class<IntervalJob>> _jobClasses =
		new HashMap<String, Class<IntervalJob>>();

}