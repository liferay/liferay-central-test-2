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
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailTestRule;
import com.liferay.portal.util.subscriptions.BaseSubscriptionBaseModelTestCase;
import com.liferay.portal.util.test.RoleTestUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.test.WikiTestUtil;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Sync
public class WikiSubscriptionBaseModelTest
	extends BaseSubscriptionBaseModelTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

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
		_node = WikiTestUtil.addNode(group.getGroupId());

		return _node.getNodeId();
	}

	@Override
	protected void addSubscriptionBaseModel(long baseModelId) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPage(baseModelId);

		WikiPageLocalServiceUtil.subscribePage(
			user.getUserId(), page.getNodeId(), page.getTitle());
	}

	@Override
	protected void removeContainerModelResourceViewPermission()
		throws Exception {

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, WikiNode.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_node.getNodeId()), ActionKeys.VIEW);

		RoleTestUtil.removeResourcePermission(
			RoleConstants.SITE_MEMBER, WikiNode.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_node.getNodeId()), ActionKeys.VIEW);
	}

	@Override
	protected void updateBaseModel(long baseModelId) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPage(baseModelId, true);

		WikiTestUtil.updatePage(page);
	}

	private WikiNode _node;

}