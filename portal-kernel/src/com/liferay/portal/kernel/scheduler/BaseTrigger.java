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

import java.util.Date;

/**
 * <a href="BaseTrigger.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public abstract class BaseTrigger implements Trigger {

	public BaseTrigger(
		String jobName, String groupName, TriggerType triggerType,
		Date startDate, Date endDate) {

		_jobName = jobName;
		_groupName = groupName;
		_triggerType = triggerType;
		_startDate = startDate;
		_endDate = endDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public String getGroupName() {
		return _groupName;
	}

	public String getJobName() {
		return _jobName;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public TriggerType getTriggerType() {
		return _triggerType;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public void setTriggerType(TriggerType triggerType) {
		_triggerType = triggerType;
	}

	private Date _endDate;
	private String _groupName;
	private String _jobName;
	private Date _startDate;
	private TriggerType _triggerType;

}