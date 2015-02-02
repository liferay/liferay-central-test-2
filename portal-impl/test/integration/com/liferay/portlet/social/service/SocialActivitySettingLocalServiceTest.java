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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.util.SocialConfigurationUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zsolt Berentey
 */
public class SocialActivitySettingLocalServiceTest
	extends BaseSocialActivityTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testGetActivityDefinition() throws Exception {
		SocialActivitySettingLocalServiceUtil.updateActivitySetting(
			_group.getGroupId(), TEST_MODEL, true);

		SocialActivityDefinition defaultActivityDefinition =
			SocialConfigurationUtil.getActivityDefinition(TEST_MODEL, 1);

		SocialActivityDefinition activityDefinition =
			SocialActivitySettingLocalServiceUtil.getActivityDefinition(
				_group.getGroupId(), TEST_MODEL, 1);

		Assert.assertEquals(defaultActivityDefinition, activityDefinition);

		List<SocialActivityDefinition> defaultActivityDefinitions =
			SocialConfigurationUtil.getActivityDefinitions(TEST_MODEL);

		Assert.assertNotNull(defaultActivityDefinitions);
		Assert.assertFalse(defaultActivityDefinitions.isEmpty());

		List<SocialActivityDefinition> activityDefinitions =
			SocialActivitySettingLocalServiceUtil.getActivityDefinitions(
				_group.getGroupId(), TEST_MODEL);

		Assert.assertNotNull(activityDefinitions);
		Assert.assertFalse(activityDefinitions.isEmpty());
		Assert.assertEquals(
			defaultActivityDefinitions.size(), activityDefinitions.size());
		Assert.assertTrue(
			activityDefinitions.contains(defaultActivityDefinition));
	}

	@Test
	public void testUpdateActivitySettings() throws Exception {
		SocialActivitySettingLocalServiceUtil.updateActivitySetting(
			_group.getGroupId(), TEST_MODEL, true);

		long classNameId = PortalUtil.getClassNameId(TEST_MODEL);

		Assert.assertTrue(
			SocialActivitySettingLocalServiceUtil.isEnabled(
				_group.getGroupId(), classNameId));

		SocialActivitySettingLocalServiceUtil.updateActivitySetting(
			_group.getGroupId(), TEST_MODEL, false);

		Assert.assertFalse(
			SocialActivitySettingLocalServiceUtil.isEnabled(
				_group.getGroupId(), classNameId));
		Assert.assertTrue(
			SocialActivitySettingLocalServiceUtil.isEnabled(
				_group.getGroupId(), classNameId, 1));

		SocialActivitySettingLocalServiceUtil.updateActivitySetting(
			_group.getGroupId(), TEST_MODEL, 1, false);

		Assert.assertFalse(
			SocialActivitySettingLocalServiceUtil.isEnabled(
				_group.getGroupId(), classNameId, 1));
	}

}