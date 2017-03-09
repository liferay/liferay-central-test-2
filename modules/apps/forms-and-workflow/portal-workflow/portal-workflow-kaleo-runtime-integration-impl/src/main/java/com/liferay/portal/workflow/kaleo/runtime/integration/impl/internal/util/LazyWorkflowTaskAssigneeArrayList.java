/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.workflow.kaleo.runtime.integration.impl.internal.util;

import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Inácio Nery
 */
public class LazyWorkflowTaskAssigneeArrayList
	extends ArrayList<WorkflowTaskAssignee> {

	public LazyWorkflowTaskAssigneeArrayList(
		KaleoTaskInstanceToken kaleoTaskInstanceToken,
		KaleoWorkflowModelConverter kaleoWorkflowModelConverter) {

		_kaleoTaskInstanceToken = kaleoTaskInstanceToken;
		_kaleoWorkflowModelConverter = kaleoWorkflowModelConverter;
	}

	@Override
	public WorkflowTaskAssignee get(int index) {
		if (index == 0) {
			return _kaleoWorkflowModelConverter.getFirstWorkflowTaskAssignee(
				_kaleoTaskInstanceToken);
		}

		if (_workflowTaskAssignee == null) {
			_workflowTaskAssignee =
				_kaleoWorkflowModelConverter.getWorkflowTaskAssignees(
					_kaleoTaskInstanceToken);
		}

		return _workflowTaskAssignee.get(index);
	}

	@Override
	public int size() {
		if (_workflowTaskAssignee == null) {
			return KaleoTaskAssignmentInstanceLocalServiceUtil.
				getKaleoTaskAssignmentInstancesCount(
					_kaleoTaskInstanceToken.getKaleoInstanceTokenId());
		}

		return _workflowTaskAssignee.size();
	}

	private final KaleoTaskInstanceToken _kaleoTaskInstanceToken;
	private final KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;
	private List<WorkflowTaskAssignee> _workflowTaskAssignee;

}