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

package com.liferay.portal.test;

import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.test.BaseTestRule;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.jdbc.ResetDatabaseUtilDataSource;
import com.liferay.portal.test.log.LogAssertionTestRule;
import com.liferay.portal.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.test.rule.DeleteAfterTestRunTestRule;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsUtil;

import java.util.List;

import org.junit.rules.TestRule;
import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class LiferayIntegrationTestRule extends AggregateTestRule {

	public LiferayIntegrationTestRule() {
		super(
			LogAssertionTestRule.INSTANCE, _clearThreadLocalTestRule,
			_uniqueStringRandomizerBumperTestRule,
			new DeleteAfterTestRunTestRule());

		System.setProperty("catalina.base", ".");

		ResetDatabaseUtilDataSource.initialize();

		List<String> configLocations = ListUtil.fromArray(
			PropsUtil.getArray(PropsKeys.SPRING_CONFIGS));

		InitUtil.initWithSpring(configLocations, true);

		if (System.getProperty("external-properties") == null) {
			System.setProperty("external-properties", "portal-test.properties");
		}
	}

	private static final TestRule _clearThreadLocalTestRule =
		new BaseTestRule<Object, Object>() {

			@Override
			protected void afterClass(Description description, Object object) {
				CentralizedThreadLocal.clearShortLivedThreadLocals();
			}

		};

	private static final TestRule _uniqueStringRandomizerBumperTestRule =
		new BaseTestRule<Object, Object>() {

			@Override
			protected Object beforeClass(Description description) {
				UniqueStringRandomizerBumper.reset();

				return null;
			}

		};

}