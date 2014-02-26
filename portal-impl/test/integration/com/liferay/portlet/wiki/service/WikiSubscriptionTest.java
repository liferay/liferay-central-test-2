/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.BaseSubscriptionTestCase;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.util.WikiTestUtil;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class WikiSubscriptionTest extends BaseSubscriptionTestCase {

	@Override
	public long addBaseModel(long containerModelId) throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), group.getGroupId(), containerModelId,
			ServiceTestUtil.randomString(), true);

		return page.getResourcePrimKey();
	}

	@Override
	public long addContainerModel(long containerModelId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		WikiNode node = WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
			StringPool.BLANK, serviceContext);

		return node.getNodeId();
	}

	@Override
	public void addSubscriptionBaseModel(long baseModelId) throws Exception {
		SubscriptionLocalServiceUtil.addSubscription(
			TestPropsValues.getUserId(), group.getGroupId(),
			WikiPage.class.getName(), baseModelId);
	}

	@Override
	public void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		SubscriptionLocalServiceUtil.addSubscription(
			TestPropsValues.getUserId(), group.getGroupId(),
			WikiNode.class.getName(), containerModelId);
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionContainerModelWhenInRootContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionContainerModelWhenInSubcontainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionRootContainerModelWhenInContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionRootContainerModelWhenInRootContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionRootContainerModelWhenInSubcontainerModel() {
	}

	@Override
	public long updateEntry(long baseModelId) throws Exception {
		WikiPage page = WikiTestUtil.updatePage(
			WikiPageLocalServiceUtil.getPage(baseModelId, true));

		return page.getResourcePrimKey();
	}

}