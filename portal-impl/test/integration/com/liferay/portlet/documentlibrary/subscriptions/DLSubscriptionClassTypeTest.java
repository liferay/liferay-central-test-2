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

package com.liferay.portlet.documentlibrary.subscriptions;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailTestRule;
import com.liferay.portal.util.subscriptions.BaseSubscriptionClassTypeTestCase;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Sync
public class DLSubscriptionClassTypeTest
	extends BaseSubscriptionClassTypeTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Override
	protected long addBaseModelWithClassType(
			long containerModelId, long classTypeId)
		throws Exception {

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), containerModelId, RandomTestUtil.randomString(),
			classTypeId);

		return fileEntry.getFileEntryId();
	}

	@Override
	protected long addClassType() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			DLFileEntry.class.getName());

		DLFileEntryType fileEntryType = DLAppTestUtil.addDLFileEntryType(
			group.getGroupId(), ddmStructure.getStructureId());

		return fileEntryType.getFileEntryTypeId();
	}

	@Override
	protected void addSubscriptionClassType(long classTypeId) throws Exception {
		DLAppLocalServiceUtil.subscribeFileEntryType(
			user.getUserId(), group.getGroupId(), classTypeId);
	}

	@Override
	protected void deleteSubscriptionClassType(long classTypeId)
		throws Exception {

		DLAppLocalServiceUtil.unsubscribeFileEntryType(
			user.getUserId(), group.getGroupId(), classTypeId);
	}

	@Override
	protected Long getDefaultClassTypeId() throws Exception {
		DLFileEntryType basicEntryType =
			DLFileEntryTypeLocalServiceUtil.getDLFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		Assert.assertNotNull(basicEntryType);

		return basicEntryType.getPrimaryKey();
	}

	@Override
	protected void updateBaseModel(long baseModelId) throws Exception {
		DLAppTestUtil.updateFileEntryWithWorkflow(
			group.getGroupId(), baseModelId, false, true);
	}

}