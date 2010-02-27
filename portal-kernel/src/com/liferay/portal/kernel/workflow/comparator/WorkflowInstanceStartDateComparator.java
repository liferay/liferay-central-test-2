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

package com.liferay.portal.kernel.workflow.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowInstance;

import java.util.Date;

/**
 * <a href="WorkflowInstanceStartDateComparator.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Shuyang Zhou
 */
public class WorkflowInstanceStartDateComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "startDate ASC, workflowInstanceId ASC";

	public static String ORDER_BY_DESC =
		"startDate DESC, workflowInstanceId DESC";

	public static String[] ORDER_BY_FIELDS = {
		"startDate", "workflowInstanceId"
	};

	public WorkflowInstanceStartDateComparator() {
		this(false);
	}

	public WorkflowInstanceStartDateComparator(boolean asc) {
		_asc = asc;
	}

	public int compare(Object obj1, Object obj2) {
		WorkflowInstance workflowInstance1 = (WorkflowInstance)obj1;
		WorkflowInstance workflowInstance2 = (WorkflowInstance)obj2;

		Date startDate1 = workflowInstance1.getStartDate();
		Date startDate2 = workflowInstance2.getStartDate();

		int value = startDate1.compareTo(startDate2);

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