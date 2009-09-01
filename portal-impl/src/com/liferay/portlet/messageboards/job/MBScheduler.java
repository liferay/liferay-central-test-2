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

package com.liferay.portlet.messageboards.job;

import com.liferay.portal.kernel.job.Scheduler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsValues;

/**
 * <a href="MBScheduler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 * @author Tina Tian
 * @author Brian Wing Shun Chan
 */
public class MBScheduler implements Scheduler {

	public MBScheduler() {
		try {
			long rawInterval = PrefsPropsUtil.getLong(
				PropsKeys.MESSAGE_BOARDS_EXPIRE_BAN_JOB_INTERVAL,
				PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_JOB_INTERVAL);

			if (_log.isDebugEnabled()) {
				_log.debug("Interval " + rawInterval + " minutes");
			}

			_interval = rawInterval * Time.MINUTE;
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void schedule() {
		if (PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL <= 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Auto expire of banned message board users is disabled");
			}

			return;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("schedulerId", MBScheduler.class.getName());

		try {
			SchedulerEngineUtil.schedule(
				MBScheduler.class.getName(), _interval, null, null, null,
				DestinationNames.SCHEDULER_JOB, jsonObject.toString());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void unschedule() {
		if (PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL <= 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Auto expire of banned message board users is disabled");
			}

			return;
		}

		try {
			SchedulerEngineUtil.unschedule(
				MBScheduler.class.getName(), MBScheduler.class.getName());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(Scheduler.class);

	private long _interval;

}