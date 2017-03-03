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

package com.liferay.calendar.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.lar.test.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
@Sync
public class CalendarBookingStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		Calendar calendar = CalendarTestUtil.addCalendar(group, serviceContext);

		addDependentStagedModel(
			dependentStagedModelsMap, CalendarResource.class,
			calendar.getCalendarResource());

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			CalendarResource.class.getSimpleName());

		CalendarResource calendarResource =
			(CalendarResource)dependentStagedModels.get(0);

		return CalendarBookingTestUtil.addRegularCalendarBooking(
			TestPropsValues.getUser(), calendarResource.getDefaultCalendar(),
			serviceContext);
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		List<StagedModel> stagedModels = new ArrayList<>();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		Calendar calendar = CalendarTestUtil.addCalendar(group, serviceContext);

		CalendarBooking approvedCalendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(
				TestPropsValues.getUser(), calendar, serviceContext);

		stagedModels.add(approvedCalendarBooking);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			group, TestPropsValues.getUserId());

		calendar = CalendarTestUtil.addCalendar(group, serviceContext);

		CalendarBooking pendingCalendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBookingWithWorkflow(
				TestPropsValues.getUser(), calendar, serviceContext);

		stagedModels.add(pendingCalendarBooking);

		return stagedModels;
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return CalendarBookingLocalServiceUtil.
				getCalendarBookingByUuidAndGroupId(uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CalendarBooking.class;
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return true;
	}

}