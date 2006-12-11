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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.ParamUtil;
import com.liferay.util.dao.search.DisplayTerms;

import javax.portlet.RenderRequest;

/**
 * <a href="InstanceDisplayTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class InstanceDisplayTerms extends DisplayTerms {

	public static final String DEFINITION_ID = "definitionId";

	public static final String INSTANCE_ID = "instanceId";

	public static final String DEFINITION_NAME = "definitionName";

	public static final String DEFINITION_VERSION = "definitionVersion";

	public static final String CREATE_START_GT = "startDateGT";

	public static final String CREATE_START_LT = "startDateLT";

	public static final String CREATE_END_GT = "endDateGT";

	public static final String CREATE_END_LT = "endDateLT";

	public static final String HIDE_ENDED_TASKS = "hideEndedTasks";

	public InstanceDisplayTerms(RenderRequest req) {
		super(req);

		definitionId = ParamUtil.getLong(req, DEFINITION_ID);
		instanceId = ParamUtil.getLong(req, INSTANCE_ID);
		definitionName = ParamUtil.getString(req, DEFINITION_NAME);
		definitionVersion = ParamUtil.getString(req, DEFINITION_VERSION);
		startDateGT = ParamUtil.getString(req, CREATE_START_GT);
		startDateLT = ParamUtil.getString(req, CREATE_START_LT);
		endDateGT = ParamUtil.getString(req, CREATE_END_GT);
		endDateLT = ParamUtil.getString(req, CREATE_END_LT);
		hideEndedTasks = ParamUtil.getBoolean(req, HIDE_ENDED_TASKS);
	}

	public long getDefinitionId() {
		return definitionId;
	}

	public String getDefinitionIdString() {
		if (definitionId != 0) {
			return String.valueOf(definitionId);
		}
		else {
			return StringPool.BLANK;
		}
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

	public String getDefinitionName() {
		return definitionName;
	}

	public String getDefinitionVersion() {
		return definitionVersion;
	}

	public String getStartDateGT() {
		return startDateGT;
	}

	public String getStartDateLT() {
		return startDateLT;
	}

	public String getEndDateGT() {
		return endDateGT;
	}

	public String getEndDateLT() {
		return endDateLT;
	}

	public boolean isHideEndedTasks() {
		return hideEndedTasks;
	}

	protected long definitionId;
	protected long instanceId;
	protected String definitionName;
	protected String definitionVersion;
	protected String startDateGT;
	protected String startDateLT;
	protected String endDateGT;
	protected String endDateLT;
	protected boolean hideEndedTasks;

}