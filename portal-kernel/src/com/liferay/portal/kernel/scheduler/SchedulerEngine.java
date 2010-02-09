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

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;

import java.util.List;

/**
 * <a href="SchedulerEngine.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Shuyang Zhou
 */
public interface SchedulerEngine {

	public static final String DESCRIPTION = "description";

	public static final int DESCRIPTION_MAX_LENGTH = 120;

	public static final String DESTINATION = "destination";

	public static final String DISABLE = "disable";

	public static final int GROUP_NAME_MAX_LENGTH = 80;

	public static final int JOB_NAME_MAX_LENGTH = 80;

	public static final String MESSAGE = "message";

	public static final String RECEIVER_KEY = "receiver_key";

	public List<SchedulerRequest> getScheduledJobs(String groupName)
		throws SchedulerException;

	public void schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws SchedulerException;

	public void shutdown() throws SchedulerException;

	public void start() throws SchedulerException;

	public void unschedule(Trigger trigger) throws SchedulerException;

}