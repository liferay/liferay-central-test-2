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

package com.liferay.message.boards.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.web.lar.MBPortletDataHandler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.lar.test.BasePortletDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.exportimport.lar.ManifestSummary;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
public class MBPortletDataHandlerTest extends BasePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		super.setUp();

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testDeleteAllFolders() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		MBCategory parentCategory = MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		MBCategory childCategory = MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(), parentCategory.getCategoryId(),
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		MBCategoryLocalServiceUtil.moveCategoryToTrash(
			TestPropsValues.getUserId(), childCategory.getCategoryId());

		MBCategoryLocalServiceUtil.moveCategoryToTrash(
			TestPropsValues.getUserId(), parentCategory.getCategoryId());

		MBCategoryLocalServiceUtil.deleteCategory(parentCategory, false);

		GroupLocalServiceUtil.deleteGroup(group);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.getCategories(
			group.getGroupId());

		Assert.assertEquals(0, categories.size());
	}

	@Override
	protected void addParameters(Map<String, String[]> parameterMap) {
		addBooleanParameter(
			parameterMap, MBPortletDataHandler.NAMESPACE, "messages", true);
		addBooleanParameter(
			parameterMap, MBPortletDataHandler.NAMESPACE, "thread-flags", true);
		addBooleanParameter(
			parameterMap, MBPortletDataHandler.NAMESPACE, "user-bans", true);
	}

	@Override
	protected void addStagedModels() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		MBCategory category = MBCategoryServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			stagingGroup.getGroupId(), category.getCategoryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		MBThreadFlagLocalServiceUtil.addThreadFlag(
			TestPropsValues.getUserId(), message.getThread(), serviceContext);

		User user = UserTestUtil.addUser(TestPropsValues.getGroupId());

		MBBanLocalServiceUtil.addBan(
			TestPropsValues.getUserId(), user.getUserId(), serviceContext);
	}

	@Override
	protected void checkManifestSummary(
		Map<String, LongWrapper> expectedModelAdditionCounters) {

		String manifestSummaryKey = ManifestSummary.getManifestSummaryKey(
			MBThread.class.getName(), null);

		expectedModelAdditionCounters.remove(manifestSummaryKey);

		super.checkManifestSummary(expectedModelAdditionCounters);
	}

	@Override
	protected PortletDataHandler createPortletDataHandler() {
		try {
			Registry registry = RegistryUtil.getRegistry();

			Collection<PortletDataHandler> portletDataHandlers =
				registry.getServices(
					PortletDataHandler.class,
					"(javax.portlet.name=" + PortletKeys.MESSAGE_BOARDS + ")");

			Iterator<PortletDataHandler> iterator =
				portletDataHandlers.iterator();

			return iterator.next();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.MESSAGE_BOARDS;
	}

}