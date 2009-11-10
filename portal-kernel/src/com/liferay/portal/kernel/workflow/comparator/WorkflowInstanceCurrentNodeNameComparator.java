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
import com.liferay.portal.kernel.workflow.WorkflowInstance;

/**
 * <a href="WorkflowInstanceCurrentNodeNameComparator.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WorkflowInstanceCurrentNodeNameComparator
	extends OrderByComparator {

	public static String ORDER_BY_ASC =
		"currentNodeName ASC, workflowInstanceId ASC";

	public static String ORDER_BY_DESC =
		"currentNodeName DESC, workflowInstanceId DESC";

	public static String[] ORDER_BY_FIELDS = {
		"currentNodeName", "workflowInstanceId"
	};

	public WorkflowInstanceCurrentNodeNameComparator() {
		this(false);
	}

	public WorkflowInstanceCurrentNodeNameComparator(boolean asc) {
		_asc = asc;
	}

	public int compare(Object obj1, Object obj2) {
		WorkflowInstance workflowInstance1 = (WorkflowInstance)obj1;
		WorkflowInstance workflowInstance2 = (WorkflowInstance)obj2;

		String currentNodeName1 = workflowInstance1.getCurrentNodeName();
		String currentNodeName2 = workflowInstance2.getCurrentNodeName();

		int value = currentNodeName1.compareTo(currentNodeName2);

		if (value == 0) {
			Long workflowInstanceId1 =
				workflowInstance1.getWorkflowInstanceId();
			Long workflowInstanceId2 =
				workflowInstance2.getWorkflowInstanceId();

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