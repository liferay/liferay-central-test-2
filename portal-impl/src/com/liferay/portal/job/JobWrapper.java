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

import com.liferay.portal.dao.shard.ShardUtil;
import com.liferay.portal.kernel.job.IntervalJob;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PropsValues;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;

/**
 * <a href="JobWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class JobWrapper implements StatefulJob {

	public void execute(JobExecutionContext context) {
		try {
			if (_job == null) {
				JobDetail jobDetail = context.getJobDetail();

				Class<IntervalJob> intervalJobClass = JobClassUtil.get(
					jobDetail.getName());

				_classLoader = intervalJobClass.getClassLoader();

				_job = (IntervalJob)_classLoader.loadClass(
					intervalJobClass.getName()).newInstance();
			}

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			try {
				currentThread.setContextClassLoader(_classLoader);

				for (String shardName : PropsValues.SHARD_AVAILABLE_NAMES) {
					ShardUtil.pushCompanyService(shardName);

					try {
						_job.execute(new JobExecutionContextImpl(context));
					}
					finally {
						ShardUtil.popCompanyService();
					}
				}
			}
			finally {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JobWrapper.class);

	private ClassLoader _classLoader;
	private IntervalJob _job;

}