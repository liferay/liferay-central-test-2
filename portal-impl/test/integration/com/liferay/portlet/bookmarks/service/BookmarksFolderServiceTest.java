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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class BookmarksFolderServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddFolder() throws Exception {
		BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());
	}

	@Test
	public void testAddSubfolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		BookmarksTestUtil.addFolder(
			_group.getGroupId(), folder.getFolderId(),
			ServiceTestUtil.randomString());
	}

	@Test
	public void testDeleteFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		BookmarksFolderServiceUtil.deleteFolder(folder.getFolderId());
	}

	@Test
	public void testGetFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		BookmarksFolderServiceUtil.getFolder(folder.getFolderId());
	}

	@Test
	public void testMoveFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		long initialParentFolderId = folder.getParentFolderId();

		BookmarksFolder destinationFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		folder = BookmarksFolderLocalServiceUtil.moveFolder(
			folder.getFolderId(), destinationFolder.getFolderId());

		Assert.assertNotEquals(
			initialParentFolderId, folder.getParentFolderId());
		Assert.assertEquals(
			destinationFolder.getFolderId(), folder.getParentFolderId());
	}

	@Test
	public void testMoveFolderFromTrash() throws Exception {
		BookmarksFolder initialParentFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), initialParentFolder.getFolderId(),
			ServiceTestUtil.randomString());

		Assert.assertTrue(folder.isApproved());

		BookmarksFolderLocalServiceUtil.moveFolderToTrash(
			initialParentFolder.getUserId(), initialParentFolder.getFolderId());

		folder = BookmarksFolderLocalServiceUtil.getFolder(
			folder.getFolderId());

		Assert.assertTrue(folder.isInTrash());
		Assert.assertFalse(folder.isInTrashExplicitly());

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		folder = BookmarksFolderLocalServiceUtil.moveFolderFromTrash(
			parentFolder.getUserId(), folder.getFolderId(),
			parentFolder.getFolderId());

		Assert.assertTrue(folder.isApproved());
		Assert.assertEquals(
			parentFolder.getFolderId(), folder.getParentFolderId());
	}

	@Test
	public void testMoveFolderFromTrashWhenIsInTrashExplicitly()
		throws Exception {

		BookmarksFolder initialParentFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), initialParentFolder.getFolderId(),
			ServiceTestUtil.randomString());

		Assert.assertTrue(folder.isApproved());

		folder = BookmarksFolderLocalServiceUtil.moveFolderToTrash(
			folder.getUserId(), folder.getFolderId());

		Assert.assertTrue(folder.isInTrash());
		Assert.assertTrue(folder.isInTrashExplicitly());

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		folder = BookmarksFolderLocalServiceUtil.moveFolderFromTrash(
			parentFolder.getUserId(), folder.getFolderId(),
			parentFolder.getFolderId());

		Assert.assertTrue(folder.isApproved());
		Assert.assertEquals(
			parentFolder.getFolderId(), folder.getParentFolderId());
	}

	@Test
	public void testRestoreFolderFromTrash() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		Assert.assertTrue(folder.isApproved());

		BookmarksFolderLocalServiceUtil.moveFolderToTrash(
			folder.getUserId(), folder.getFolderId());

		folder = BookmarksFolderLocalServiceUtil.getFolder(
			folder.getFolderId());

		Assert.assertTrue(folder.isInTrash());

		BookmarksFolderLocalServiceUtil.restoreFolderFromTrash(
			folder.getUserId(), folder.getFolderId());

		folder = BookmarksFolderLocalServiceUtil.getFolder(
			folder.getFolderId());

		Assert.assertTrue(folder.isApproved());
	}

	@Test
	public void testSearch() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			folder.getFolderId(), true, serviceContext);

		SearchContext searchContext = BookmarksTestUtil.getSearchContext(
			entry.getCompanyId(), entry.getGroupId(), entry.getFolderId(),
			"test");

		Indexer indexer = IndexerRegistryUtil.getIndexer(BookmarksEntry.class);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(1, hits.getLength());
	}

	@Test
	public void testSearchAndDeleteFolderAndSearch() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			folder.getFolderId(), true, serviceContext);

		long companyId = entry.getCompanyId();
		long groupId = entry.getFolder().getGroupId();
		long folderId = entry.getFolderId();
		String keywords = "test";

		SearchContext searchContext = BookmarksTestUtil.getSearchContext(
			companyId, groupId, folderId, keywords);

		Indexer indexer = IndexerRegistryUtil.getIndexer(BookmarksEntry.class);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(1, hits.getLength());

		BookmarksFolderLocalServiceUtil.deleteFolder(folderId);

		hits = indexer.search(searchContext);

		Query query = hits.getQuery();

		Assert.assertEquals(query.toString(), 0, hits.getLength());
	}

	@Test
	public void testSearchAndVerifyDocs() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			folder.getFolderId(), true, serviceContext);

		SearchContext searchContext = BookmarksTestUtil.getSearchContext(
			entry.getCompanyId(), entry.getGroupId(), entry.getFolderId(),
			"test");

		Indexer indexer = IndexerRegistryUtil.getIndexer(BookmarksEntry.class);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(1, hits.getLength());

		List<Document> results = hits.toList();

		for (Document doc : results) {
			Assert.assertEquals(
				entry.getCompanyId(),
				GetterUtil.getLong(doc.get(Field.COMPANY_ID)));
			AssertUtils.assertEqualsIgnoreCase(
				entry.getDescription(), doc.get(Field.DESCRIPTION));
			Assert.assertEquals(
				entry.getEntryId(),
				GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK)));
			Assert.assertEquals(
				entry.getGroupId(),
				GetterUtil.getLong(doc.get(Field.GROUP_ID)));
			AssertUtils.assertEqualsIgnoreCase(
				entry.getName(), doc.get(Field.TITLE));
			Assert.assertEquals(entry.getUrl(), doc.get(Field.URL));
			Assert.assertEquals(
				entry.getFolderId(), GetterUtil.getLong(doc.get("folderId")));
		}
	}

	@Test
	public void testSearchRange() throws Exception {
		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			_group.getGroupId(), true);

		BookmarksTestUtil.addEntry(_group.getGroupId(), true);
		BookmarksTestUtil.addEntry(_group.getGroupId(), true);
		BookmarksTestUtil.addEntry(_group.getGroupId(), true);

		SearchContext searchContext = BookmarksTestUtil.getSearchContext(
			_group.getCompanyId(), _group.getGroupId(), entry.getFolderId(),
			"test");

		Indexer indexer = IndexerRegistryUtil.getIndexer(BookmarksEntry.class);

		searchContext.setEnd(3);
		searchContext.setFolderIds((long[])null);
		searchContext.setStart(1);

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(4, hits.getLength());

		Document[] documents = hits.getDocs();

		Assert.assertEquals(2, documents.length);
	}

	@Test
	public void testSubscribeFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		testSubscribeFolder(folder.getFolderId());
	}

	@Test
	public void testSubscribeFolderWithDefaultFolder() throws Exception {
		testSubscribeFolder(BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Test
	public void testUnsubscribeFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		testUnsubscribeFolder(folder.getFolderId());
	}

	@Test
	public void testUnsubscribeFolderWithDefaultFolder() throws Exception {
		testUnsubscribeFolder(
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Test
	public void testUpdateFolderWithChildFolderAsParentFolder()
		throws Exception {

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		String initialName = folder.getName();
		long initialParentFolderId = folder.getParentFolderId();

		BookmarksFolder childfolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), folder.getFolderId(),
			ServiceTestUtil.randomString());

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		folder = BookmarksFolderLocalServiceUtil.updateFolder(
			folder.getUserId(), folder.getFolderId(), childfolder.getFolderId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(50),
			false, serviceContext);

		Assert.assertNotEquals(initialName, folder.getName());
		Assert.assertEquals(initialParentFolderId, folder.getParentFolderId());
	}

	@Test
	public void testUpdateFolderWithCustomParentFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		String initialName = folder.getName();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		folder = BookmarksFolderLocalServiceUtil.updateFolder(
			folder.getUserId(), folder.getFolderId(),
			parentFolder.getFolderId(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(50), false, serviceContext);

		Assert.assertNotEquals(initialName, folder.getName());
		Assert.assertEquals(
			parentFolder.getFolderId(), folder.getParentFolderId());
	}

	@Test
	public void testUpdateFolderWithDefaultParentFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		String initialName = folder.getName();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		folder = BookmarksFolderLocalServiceUtil.updateFolder(
			folder.getUserId(), folder.getFolderId(),
			folder.getParentFolderId(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(50), false, serviceContext);

		Assert.assertNotEquals(initialName, folder.getName());
		Assert.assertEquals(
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			folder.getParentFolderId());
	}

	@Test
	public void testUpdateFolderWithItselfAsParentFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		String initialName = folder.getName();
		long initialParentFolderId = folder.getParentFolderId();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		folder = BookmarksFolderLocalServiceUtil.updateFolder(
			folder.getUserId(), folder.getFolderId(), folder.getFolderId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(50),
			false, serviceContext);

		Assert.assertNotEquals(initialName, folder.getName());
		Assert.assertEquals(initialParentFolderId, folder.getParentFolderId());
	}

	@Test
	public void testUpdateFolderWithParentFolderFromDifferentGroup()
		throws Exception {

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		String initialName = folder.getName();
		long initialParentFolderId = folder.getParentFolderId();

		Group group = GroupTestUtil.addGroup();

		BookmarksFolder parentFolder = BookmarksTestUtil.addFolder(
			group.getGroupId(), ServiceTestUtil.randomString());

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		folder = BookmarksFolderLocalServiceUtil.updateFolder(
			folder.getUserId(), folder.getFolderId(),
			parentFolder.getFolderId(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(50), false, serviceContext);

		Assert.assertNotEquals(initialName, folder.getName());
		Assert.assertEquals(initialParentFolderId, folder.getParentFolderId());
	}

	@Test
	public void testUpdateFolderWithWrongParentFolder() throws Exception {
		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), ServiceTestUtil.randomString());

		String initialName = folder.getName();
		long initialParentFolderId = folder.getParentFolderId();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		long randomParentFolder = 0;
		BookmarksFolder parentFolder = null;

		do {
			randomParentFolder = ServiceTestUtil.randomLong();

			parentFolder = BookmarksFolderLocalServiceUtil.fetchBookmarksFolder(
				randomParentFolder);
		}
		while (parentFolder != null);

		folder = BookmarksFolderLocalServiceUtil.updateFolder(
			folder.getUserId(), folder.getFolderId(), randomParentFolder,
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(50),
			false, serviceContext);

		Assert.assertNotEquals(initialName, folder.getName());
		Assert.assertEquals(initialParentFolderId, folder.getParentFolderId());
	}

	protected void testSubscribeFolder(long folderId) throws Exception {
		int initialUserSubscriptionsCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId());

		BookmarksFolderLocalServiceUtil.subscribeFolder(
			TestPropsValues.getUserId(), _group.getGroupId(), folderId);

		Assert.assertEquals(
			initialUserSubscriptionsCount + 1,
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId()));

		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getUserSubscriptions(
				TestPropsValues.getUserId(), BookmarksFolder.class.getName());

		long subscriptionClassPK = folderId;

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			subscriptionClassPK = _group.getGroupId();
		}

		boolean fail = true;

		for (Subscription subscription : subscriptions) {
			if (subscription.getClassName().equals(
					BookmarksFolder.class.getName()) &&
				(subscription.getClassPK() == subscriptionClassPK)) {

				fail = false;
			}
		}

		if (fail) {
			Assert.fail("No subscription exist for folder");
		}
	}

	protected void testUnsubscribeFolder(long folderId) throws Exception {
		int initialUserSubscriptionsCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId());

		testSubscribeFolder(folderId);

		BookmarksFolderLocalServiceUtil.unsubscribeFolder(
			TestPropsValues.getUserId(), _group.getGroupId(), folderId);

		Assert.assertEquals(
			initialUserSubscriptionsCount,
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId()));

		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getUserSubscriptions(
				TestPropsValues.getUserId(), BookmarksFolder.class.getName());

		long subscriptionClassPK = folderId;

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			subscriptionClassPK = _group.getGroupId();
		}

		for (Subscription subscription : subscriptions) {
			if (subscription.getClassName().equals(
					BookmarksFolder.class.getName()) &&
				(subscription.getClassPK() == subscriptionClassPK)) {

				Assert.fail("A susbcription exist for folder");
			}
		}
	}

	private Group _group;

}