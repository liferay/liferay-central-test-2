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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailExecutionTestListener;
import com.liferay.portal.util.subscriptions.BaseSubscriptionBaseModelTestCase;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.test.WikiTestUtil;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousMailExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class WikiSubscriptionBaseModelTest
	extends BaseSubscriptionBaseModelTestCase {

	@Ignore
	@Override
	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel() {
	}

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
	protected void addSubscriptionBaseModel(long baseModelId) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPage(baseModelId);

		WikiPageLocalServiceUtil.subscribePage(
			TestPropsValues.getUserId(), page.getNodeId(), page.getTitle());
	}

	@Override
	protected long updateEntry(long baseModelId) throws Exception {
		WikiPage page = WikiTestUtil.updatePage(
			WikiPageLocalServiceUtil.getPage(baseModelId, true));

		return page.getResourcePrimKey();
	}

}