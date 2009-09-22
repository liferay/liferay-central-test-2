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

package com.liferay.portal.kernel.workflow.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowInstanceInfo;

import java.util.Date;

/**
 * <a href="WorkflowInstanceEndDateComparator.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Shuyang Zhou
 */
/**
 * <a href="WorkflowInstanceEndDateComparator.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Shuyang Zhou
 */
public class WorkflowInstanceEndDateComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "endDate ASC, id ASC";

	public static String ORDER_BY_DESC = "endDate DESC, id DESC";

	public static String[] ORDER_BY_FIELDS = {"endDate", "id"};

	public WorkflowInstanceEndDateComparator() {
		this(false);
	}

	public WorkflowInstanceEndDateComparator(boolean asc) {
		_asc = asc;
	}

	public int compare(Object obj1, Object obj2) {
		WorkflowInstanceInfo workflowInstanceInfo1 = (WorkflowInstanceInfo)obj1;
		WorkflowInstanceInfo workflowInstanceInfo2 = (WorkflowInstanceInfo)obj2;

		Date endDate1 = workflowInstanceInfo1.getEndDate();
		Date endDate2 = workflowInstanceInfo2.getEndDate();

		int value = endDate1.compareTo(endDate2);

		if (value == 0) {
			Long workflowInstanceId1 =
				workflowInstanceInfo1.getWorkflowInstanceId();
			Long workflowInstanceId2 =
				workflowInstanceInfo2.getWorkflowInstanceId();

			value = workflowInstanceId1.compareTo(workflowInstanceId2);
		}

		if (_asc) {
			return value;
		}
		else {
			return -value;
		}
	}

	public String getOrderBy() {
		if (_asc) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	public boolean isAscending() {
		return _asc;
	}

	private boolean _asc;

}