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

import java.util.Date;

import javax.portlet.RenderRequest;

/**
 * <a href="InstanceDisplayTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class InstanceDisplayTerms extends DisplayTerms {

	public static final String WORKFLOW_NAME = "workflowName";

	public static final String WORKFLOW_VERSION = "workflowVersion";

	public static final String GT_START_DATE = "gtStartDate";

	public static final String LT_START_DATE = "ltStartDate";

	public static final String GT_END_DATE = "gtEndDate";

	public static final String LT_END_DATE = "ltEndDate";

	public InstanceDisplayTerms(RenderRequest req) {
		super(req);

		workflowName = ParamUtil.getString(req, WORKFLOW_NAME);
		workflowVersion = ParamUtil.getDouble(req, WORKFLOW_VERSION);
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public double getWorkflowVersion() {
		return workflowVersion;
	}

	protected String workflowName;
	protected double workflowVersion;
	protected Date gtStartDate;
	protected Date ltStartDate;
	protected Date gtEndDate;
	protected Date ltEndDate;

}