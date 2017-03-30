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

import static com.liferay.portal.workflow.kaleo.runtime.integration.impl.internal.util.KaleoRuntimeTestUtil.assertWorkflowTaskAssignee;
import static com.liferay.portal.workflow.kaleo.runtime.integration.impl.internal.util.KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance;
import static com.liferay.portal.workflow.kaleo.runtime.integration.impl.internal.util.KaleoRuntimeTestUtil.mockKaleoTaskInstanceToken;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class WorkflowTaskAssigneesSupplierTest {

	@Test
	public void testGetWhenKaleoTaskAssignmentsIsEmpty() {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			mockKaleoTaskInstanceToken();

		WorkflowTaskAssigneesSupplier workflowTaskAssigneesSupplier =
			new WorkflowTaskAssigneesSupplier(kaleoTaskInstanceToken);

		List<WorkflowTaskAssignee> workflowTaskAssignees =
			workflowTaskAssigneesSupplier.get();

		Assert.assertTrue(workflowTaskAssignees.isEmpty());
	}

	@Test
	public void testGetWhenKaleoTaskAssignmentsIsNotEmpty() {
		KaleoTaskAssignmentInstance[] kaleoTaskAssignmentInstances =
			new KaleoTaskAssignmentInstance[] {
				mockKaleoTaskAssignmentInstance(Role.class.getName(), 1),
				mockKaleoTaskAssignmentInstance(User.class.getName(), 2)
			};

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			mockKaleoTaskInstanceToken(kaleoTaskAssignmentInstances);

		WorkflowTaskAssigneesSupplier workflowTaskAssigneesSupplier =
			new WorkflowTaskAssigneesSupplier(kaleoTaskInstanceToken);

		List<WorkflowTaskAssignee> workflowTaskAssignees =
			workflowTaskAssigneesSupplier.get();

		int actualSize = workflowTaskAssignees.size();

		Assert.assertEquals(2, actualSize);

		assertWorkflowTaskAssignee(
			Role.class.getName(), 1, workflowTaskAssignees.get(0));
		assertWorkflowTaskAssignee(
			User.class.getName(), 2, workflowTaskAssignees.get(1));
	}

}