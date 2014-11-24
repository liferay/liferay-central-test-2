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

package com.liferay.portlet.wiki.subscriptions;

import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.subscriptions.BaseSubscriptionContainerModelTestCase;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.test.WikiTestUtil;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class WikiSubscriptionContainerModelTest
	extends BaseSubscriptionContainerModelTestCase {

	@ClassRule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			MainServletTestRule.INSTANCE, SynchronousMailTestRule.INSTANCE);

	@Ignore
	@Override
	@Test
	public void testSubscriptionContainerModelWhenAddingBaseModelInRootContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionContainerModelWhenAddingBaseModelInSubcontainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionContainerModelWhenUpdatingBaseModelInRootContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionContainerModelWhenUpdatingBaseModelInSubcontainerModel() {
	}

	@Rule
	public final SynchronousMailTestRule synchronousMailTestRule =
		SynchronousMailTestRule.INSTANCE;

	@Override
	protected long addBaseModel(long containerModelId) throws Exception {
		WikiPage page = WikiTestUtil.addPage(
			group.getGroupId(), containerModelId, true);

		return page.getResourcePrimKey();
	}

	@Override
	protected long addContainerModel(long containerModelId) throws Exception {
		WikiNode node = WikiTestUtil.addNode(group.getGroupId());

		return node.getNodeId();
	}

	@Override
	protected void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		WikiNodeLocalServiceUtil.subscribeNode(
			user.getUserId(), containerModelId);
	}

	@Override
	protected void updateBaseModel(long baseModelId) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPage(baseModelId, true);

		WikiTestUtil.updatePage(page);
	}

}