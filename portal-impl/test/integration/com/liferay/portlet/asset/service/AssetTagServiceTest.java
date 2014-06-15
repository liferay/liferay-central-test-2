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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class AssetTagServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteGroupTags() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		int initialTagsCount = AssetTagLocalServiceUtil.getGroupTagsCount(
			_group.getGroupId());

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			serviceContext);
		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			serviceContext);

		Assert.assertEquals(
			initialTagsCount + 2,
			AssetTagLocalServiceUtil.getGroupTagsCount(_group.getGroupId()));

		AssetTagLocalServiceUtil.deleteGroupTags(_group.getGroupId());

		Assert.assertEquals(
			initialTagsCount,
			AssetTagLocalServiceUtil.getGroupTagsCount(_group.getGroupId()));
	}

	@DeleteAfterTestRun
	private Group _group;

}