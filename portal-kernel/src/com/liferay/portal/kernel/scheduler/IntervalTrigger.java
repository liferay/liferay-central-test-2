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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.util.StringBundler;

import java.util.Date;

/**
 * <a href="IntervalTrigger.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class IntervalTrigger extends BaseTrigger {

	public IntervalTrigger(
		String jobName, String groupName, Date startDate, Date endDate,
		long interval) {

		super(jobName, groupName, TriggerType.SIMPLE, startDate, endDate);

		_interval = interval;
	}

	public IntervalTrigger(
		String jobName, String groupName, Date startDate, long interval) {

		this(jobName, groupName, startDate, null, interval);
	}

	public IntervalTrigger(String jobName, String groupName, long interval) {
		this(jobName, groupName, new Date(), null, interval);
	}

	public Long getTriggerContent() {
		return _interval;
	}

	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{interval=");
		sb.append(_interval);
		sb.append(", ");
		sb.append(super.toString());
		sb.append("}");

		return sb.toString();
	}

	private Long _interval;

}