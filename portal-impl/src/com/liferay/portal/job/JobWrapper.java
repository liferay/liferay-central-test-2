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