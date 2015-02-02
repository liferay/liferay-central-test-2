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

package com.liferay.portal.search;

import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalInstances;

import java.util.HashMap;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
@Sync
public class BackupAndRestoreIndexesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testBackupAndRestore() throws Exception {
		Map<Long, String> backupNames = new HashMap<>();

		for (long companyId : PortalInstances.getCompanyIds()) {
			String backupName = StringUtil.lowerCase(
				BackupAndRestoreIndexesTest.class.getName());

			backupName = backupName + "-" + System.currentTimeMillis();

			SearchEngineUtil.backup(
				companyId, SearchEngineUtil.SYSTEM_ENGINE_ID, backupName);

			backupNames.put(companyId, backupName);
		}

		_group = GroupTestUtil.addGroup();

		for (Map.Entry<Long, String> entry : backupNames.entrySet()) {
			String backupName = entry.getValue();

			SearchEngineUtil.restore(entry.getKey(), backupName);

			SearchEngineUtil.removeBackup(entry.getKey(), backupName);
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}