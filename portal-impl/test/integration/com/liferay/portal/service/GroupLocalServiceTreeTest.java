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

package com.liferay.portal.service;

import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.TreeModel;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.TestPropsValues;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Shinn Lok
 */
public class GroupLocalServiceTreeTest extends BaseLocalServiceTreeTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Override
	protected TreeModel addTreeModel(TreeModel parentTreeModel)
		throws Exception {

		long parentGroupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;

		if (parentTreeModel != null) {
			Group group = (Group)parentTreeModel;

			parentGroupId = group.getGroupId();
		}

		Group group = GroupTestUtil.addGroup(parentGroupId);

		group.setTreePath(null);

		return GroupLocalServiceUtil.updateGroup(group);
	}

	@Override
	protected void deleteTreeModel(TreeModel treeModel) throws Exception {
		Group group = (Group)treeModel;

		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Override
	protected TreeModel getTreeModel(long primaryKey) throws Exception {
		return GroupLocalServiceUtil.getGroup(primaryKey);
	}

	@Override
	protected void rebuildTree() throws Exception {
		GroupLocalServiceUtil.rebuildTree(TestPropsValues.getCompanyId());
	}

}