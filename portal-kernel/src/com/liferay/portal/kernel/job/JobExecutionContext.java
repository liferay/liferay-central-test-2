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

import java.util.Date;

/**
 * <a href="JobExecutionContext.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface JobExecutionContext {

	public Object get(Object key);

	public long getJobRunTime();

	public Date getNextFireTime();

	public Date getPreviousFireTime();

	public Object getResult();

	public void put(Object key, Object value);

	public void setJobRunTime(long jobRunTime);

	public void setResult(Object result);

}