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

package com.liferay.portlet.wiki.notifications;

import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailTestRule;
import com.liferay.portal.util.BaseUserNotificationTestCase;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.util.test.WikiTestUtil;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@Sync
public class WikiUserNotificationTest extends BaseUserNotificationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Override
	protected BaseModel<?> addBaseModel() throws Exception {
		return WikiTestUtil.addPage(
			group.getGroupId(), _node.getNodeId(), true);
	}

	@Override
	protected void addContainerModel() throws Exception {
		_node = WikiTestUtil.addNode(group.getGroupId());
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.WIKI;
	}

	@Override
	protected void subscribeToContainer() throws Exception {
		WikiNodeLocalServiceUtil.subscribeNode(
			user.getUserId(), _node.getNodeId());
	}

	@Override
	protected BaseModel<?> updateBaseModel(BaseModel<?> baseModel)
		throws Exception {

		return WikiTestUtil.updatePage((WikiPage)baseModel);
	}

	private WikiNode _node;

}