/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.BaseSubscriptionTestCase;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;

import org.junit.runner.RunWith;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BookmarksSubscriptionTest extends BaseSubscriptionTestCase {

	@Override
	protected long addBaseModel(long containerModelId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), group.getGroupId(), containerModelId,
			ServiceTestUtil.randomString(), "http://localhost",
			StringPool.BLANK, serviceContext);

		return entry.getEntryId();
	}

	@Override
	protected long addContainerModel(long containerModelId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BookmarksFolder folder = BookmarksFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), containerModelId,
			ServiceTestUtil.randomString(), StringPool.BLANK, serviceContext);

		return folder.getFolderId();
	}

	@Override
	protected void addSubscriptionBaseModel(long baseModelId) throws Exception {
		BookmarksEntryLocalServiceUtil.subscribeEntry(
			TestPropsValues.getUserId(), baseModelId);
	}

	@Override
	protected void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		BookmarksFolderLocalServiceUtil.subscribeFolder(
			TestPropsValues.getUserId(), group.getGroupId(), containerModelId);
	}

	@Override
	protected long updateEntry(long baseModelId) throws Exception {
		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(
			baseModelId);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.UPDATE);
		serviceContext.setLayoutFullURL("http://localhost");

		entry = BookmarksEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), baseModelId, entry.getGroupId(),
			entry.getFolderId(), ServiceTestUtil.randomString(), entry.getUrl(),
			entry.getDescription(), serviceContext);

		return entry.getEntryId();
	}

}