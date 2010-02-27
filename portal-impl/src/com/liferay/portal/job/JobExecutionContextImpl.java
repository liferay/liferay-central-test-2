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

import java.util.Date;

import org.quartz.JobExecutionContext;

/**
 * <a href="JobExecutionContextImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class JobExecutionContextImpl
	implements com.liferay.portal.kernel.job.JobExecutionContext {

	public JobExecutionContextImpl(JobExecutionContext context) {
		_context = context;
	}

	public Object get(Object key) {
		return _context.get(key);
	}

	public long getJobRunTime() {
		return _context.getJobRunTime();
	}

	public Date getNextFireTime() {
		return _context.getNextFireTime();
	}

	public Date getPreviousFireTime() {
		return _context.getPreviousFireTime();
	}

	public Object getResult() {
		return _context.getResult();
	}

	public void put(Object key, Object value) {
		_context.put(key, value);
	}

	public void setJobRunTime(long jobRunTime) {
		_context.setJobRunTime(jobRunTime);
	}

	public void setResult(Object result) {
		_context.setResult(result);
	}

	private JobExecutionContext _context;

}