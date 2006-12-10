/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.workflow.search;

import com.liferay.util.ParamUtil;
import com.liferay.util.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.StringPool;
import java.util.Date;

import javax.portlet.RenderRequest;

/**
 * <a href="TaskDisplayTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class TaskDisplayTerms extends DisplayTerms {

	public static final String INSTANCE_ID = "instanceId";

	public static final String TASK_NAME = "taskName";

	public static final String DEFINITION_NAME = "definitionName";

	public static final String ASSIGNED_TO = "assignedTo";

	public static final String GT_CREATE_DATE = "gtCreateDate";

	public static final String LT_CREATE_DATE = "ltCreateDate";

	public static final String GT_START_DATE = "gtStartDate";

	public static final String LT_START_DATE = "ltStartDate";

	public static final String GT_END_DATE = "gtEndDate";

	public static final String LT_END_DATE = "ltEndDate";

	public static final String HIDE_ENDED_TASKS = "hideEndedTasks";

	public TaskDisplayTerms(RenderRequest req) {
		super(req);

		instanceId = ParamUtil.getLong(req, INSTANCE_ID);
		taskName = ParamUtil.getString(req, TASK_NAME);
		definitionName = ParamUtil.getString(req, DEFINITION_NAME);
		assignedTo = ParamUtil.getString(req, ASSIGNED_TO);
		gtCreateDate = ParamUtil.getString(req, GT_CREATE_DATE);
		ltCreateDate = ParamUtil.getString(req, LT_CREATE_DATE);
		gtStartDate = ParamUtil.getString(req, GT_START_DATE);
		ltStartDate = ParamUtil.getString(req, LT_START_DATE);
		gtEndDate = ParamUtil.getString(req, GT_END_DATE);
		ltEndDate = ParamUtil.getString(req, LT_END_DATE);
		hideEndedTasks = ParamUtil.getBoolean(req, HIDE_ENDED_TASKS);
	}

	public long getInstanceId() {
		return instanceId;
	}

	public String getInstanceIdString() {
		if (instanceId != 0) {
			return String.valueOf(instanceId);
		}
		else {
			return StringPool.BLANK;
		}
	}

	public String getTaskName() {
		return taskName;
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public String getGtCreateDate() {
		return gtCreateDate;
	}

	public String getLtCreateDate() {
		return ltCreateDate;
	}

	public String getGtStartDate() {
		return gtStartDate;
	}

	public String getLtStartDate() {
		return ltStartDate;
	}

	public String getGtEndDate() {
		return gtEndDate;
	}

	public String getLtEndDate() {
		return ltEndDate;
	}

	public boolean isHideEndedTasks() {
		return hideEndedTasks;
	}

	protected long instanceId;
	protected String taskName;
	protected String definitionName;
	protected String assignedTo;
	protected String gtCreateDate;
	protected String ltCreateDate;
	protected String gtStartDate;
	protected String ltStartDate;
	protected String gtEndDate;
	protected String ltEndDate;
	protected boolean hideEndedTasks;

}