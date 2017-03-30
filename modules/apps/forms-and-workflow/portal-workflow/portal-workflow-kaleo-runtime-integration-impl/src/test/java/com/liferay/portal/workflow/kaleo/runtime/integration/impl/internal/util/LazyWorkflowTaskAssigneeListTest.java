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

import static com.liferay.portal.kernel.test.util.RandomTestUtil.randomLong;
import static com.liferay.portal.kernel.util.StringUtil.randomString;
import static com.liferay.portal.workflow.kaleo.runtime.integration.impl.internal.util.KaleoRuntimeTestUtil.assertWorkflowTaskAssignee;
import static com.liferay.portal.workflow.kaleo.runtime.integration.impl.internal.util.KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance;
import static com.liferay.portal.workflow.kaleo.runtime.integration.impl.internal.util.KaleoRuntimeTestUtil.mockKaleoTaskInstanceToken;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

/**
 * @author Marcellus Tavares
 */
public class LazyWorkflowTaskAssigneeListTest {

	@Test
	public void testGetSizeWhenWorkflowTaskAssigneesIsLoaded() {
		KaleoTaskAssignmentInstance[] kaleoTaskAssignmentInstances =
			new KaleoTaskAssignmentInstance[] {
				mockKaleoTaskAssignmentInstance(Role.class.getName(), 1),
				mockKaleoTaskAssignmentInstance(User.class.getName(), 2)
			};

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			mockKaleoTaskInstanceToken(kaleoTaskAssignmentInstances);

		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService = Mockito.mock(
				KaleoTaskAssignmentInstanceLocalService.class);

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(
				kaleoTaskInstanceToken,
				kaleoTaskAssignmentInstanceLocalService);

		lazyWorkflowTaskAssigneeList.initWorkflowTaskAssignees();

		int actualSize = lazyWorkflowTaskAssigneeList.size();

		verifyGetKaleoTaskAssignmentInstancesCountCall(
			kaleoTaskAssignmentInstanceLocalService, Mockito.never());

		Assert.assertEquals(2, actualSize);
	}

	@Test
	public void testGetSizeWhenWorkflowTaskAssigneesIsNotLoaded() {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			mockKaleoTaskInstanceToken();

		long kaleoTaskInstanceTokenId = RandomTestUtil.randomLong();

		Mockito.when(
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId()
		).thenReturn(
			kaleoTaskInstanceTokenId
		);

		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService = Mockito.mock(
				KaleoTaskAssignmentInstanceLocalService.class);

		int expectedCount = RandomTestUtil.randomInt();

		Mockito.when(
			kaleoTaskAssignmentInstanceLocalService.
				getKaleoTaskAssignmentInstancesCount(
					Matchers.eq(kaleoTaskInstanceTokenId))
		).thenReturn(
			expectedCount
		);

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(
				kaleoTaskInstanceToken,
				kaleoTaskAssignmentInstanceLocalService);

		int actualCount = lazyWorkflowTaskAssigneeList.size();

		verifyGetKaleoTaskAssignmentInstancesCountCall(
			kaleoTaskAssignmentInstanceLocalService, Mockito.atLeastOnce());

		Assert.assertEquals(expectedCount, actualCount);
	}

	@Test
	public void testGetWhenIndexIsGreaterThanZero() {
		KaleoTaskAssignmentInstance[] kaleoTaskAssignmentInstances = {
			mockKaleoTaskAssignmentInstance(Role.class.getName(), 1),
			mockKaleoTaskAssignmentInstance(User.class.getName(), 2)
		};

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			mockKaleoTaskInstanceToken(kaleoTaskAssignmentInstances);

		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService = Mockito.mock(
				KaleoTaskAssignmentInstanceLocalService.class);

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(
				kaleoTaskInstanceToken,
				kaleoTaskAssignmentInstanceLocalService);

		WorkflowTaskAssignee workflowTaskAssignee =
			lazyWorkflowTaskAssigneeList.get(1);

		verifyGetKaleoTaskAssignmentInstancesCall(
			kaleoTaskInstanceToken, Mockito.atLeastOnce());

		verifyGetFirstKaleoTaskAssignmentInstanceCall(
			kaleoTaskInstanceToken, Mockito.never());

		assertWorkflowTaskAssignee(
			User.class.getName(), 2, workflowTaskAssignee);
	}

	@Test
	public void testGetWhenIndexIsZeroAndAssignmentIsNotNull() {
		String expectedAssigneeClassName = randomString();

		long expectedAssigneeClassPK = randomLong();

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			mockKaleoTaskAssignmentInstance(
				expectedAssigneeClassName, expectedAssigneeClassPK);

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			mockKaleoTaskInstanceToken(kaleoTaskAssignmentInstance);

		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService = Mockito.mock(
				KaleoTaskAssignmentInstanceLocalService.class);

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(
				kaleoTaskInstanceToken,
				kaleoTaskAssignmentInstanceLocalService);

		WorkflowTaskAssignee workflowTaskAssignee =
			lazyWorkflowTaskAssigneeList.get(0);

		verifyGetKaleoTaskAssignmentInstancesCall(
			kaleoTaskInstanceToken, Mockito.never());

		verifyGetFirstKaleoTaskAssignmentInstanceCall(
			kaleoTaskInstanceToken, Mockito.atLeastOnce());

		assertWorkflowTaskAssignee(
			expectedAssigneeClassName, expectedAssigneeClassPK,
			workflowTaskAssignee);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetWhenIndexIsZeroAndAssignmentIsNull() {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			mockKaleoTaskInstanceToken();

		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService = Mockito.mock(
				KaleoTaskAssignmentInstanceLocalService.class);

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(
				kaleoTaskInstanceToken,
				kaleoTaskAssignmentInstanceLocalService);

		lazyWorkflowTaskAssigneeList.get(0);
	}

	protected void verifyGetFirstKaleoTaskAssignmentInstanceCall(
		KaleoTaskInstanceToken kaleoTaskInstanceToken,
		VerificationMode verificationMode) {

		Mockito.verify(
			kaleoTaskInstanceToken, verificationMode
		).getFirstKaleoTaskAssignmentInstance();
	}

	protected void verifyGetKaleoTaskAssignmentInstancesCall(
		KaleoTaskInstanceToken kaleoTaskInstanceToken,
		VerificationMode verificationMode) {

		Mockito.verify(
			kaleoTaskInstanceToken, verificationMode
		).getKaleoTaskAssignmentInstances();
	}

	protected void verifyGetKaleoTaskAssignmentInstancesCountCall(
		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService,
		VerificationMode verificationMode) {

		Mockito.verify(
			kaleoTaskAssignmentInstanceLocalService, verificationMode
		).getKaleoTaskAssignmentInstancesCount(
			Matchers.anyLong()
		);
	}

}