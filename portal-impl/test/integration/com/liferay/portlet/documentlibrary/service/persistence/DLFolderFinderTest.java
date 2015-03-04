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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zsolt Berentey
 */
public class DLFolderFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder A",
			StringPool.BLANK, serviceContext);

		DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "Folder B", StringPool.BLANK,
			serviceContext);

		Folder folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "Folder C", StringPool.BLANK,
			serviceContext);

		DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

		FileEntry fileEntry = addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), "FE1.txt",
			ContentTypes.TEXT_PLAIN);

		_dlFileShortcut = DLAppLocalServiceUtil.addFileShortcut(
			TestPropsValues.getUserId(), _group.getGroupId(),
			fileEntry.getFolderId(), fileEntry.getFileEntryId(),
			serviceContext);

		addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), "FE2.pdf",
			ContentTypes.APPLICATION_PDF);

		fileEntry = addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), "FE3.txt",
			ContentTypes.TEXT_PLAIN);

		DLAppServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());
	}

	@Test
	public void testCountF_FE_FS_ByG_F_M_M() throws Exception {
		QueryDefinition<?> queryDefinition = new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			6,
			DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
				_group.getGroupId(), _folder.getFolderId(), null, false,
				queryDefinition));
		Assert.assertEquals(
			5,
			DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
				_group.getGroupId(), _folder.getFolderId(),
				new String[] {ContentTypes.TEXT_PLAIN}, false,
				queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			4,
			DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
				_group.getGroupId(), _folder.getFolderId(), null, false,
				queryDefinition));
		Assert.assertEquals(
			2,
			DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
				_group.getGroupId(), _folder.getFolderId(),
				new String[] {ContentTypes.APPLICATION_PDF}, false,
				queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			2,
			DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
				_group.getGroupId(), _folder.getFolderId(), null, false,
				queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			4,
			DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
				_group.getGroupId(), _folder.getFolderId(), null, false,
				queryDefinition));
		Assert.assertEquals(
			3,
			DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
				_group.getGroupId(), _folder.getFolderId(),
				new String[] {ContentTypes.TEXT_PLAIN}, false,
				queryDefinition));
	}

	@Test
	public void testCountFE_ByG_F() throws Exception {
		QueryDefinition<?> queryDefinition = new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			3,
			DLFolderFinderUtil.countFE_ByG_F(
				_group.getGroupId(), _folder.getFolderId(), queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			1,
			DLFolderFinderUtil.countFE_ByG_F(
				_group.getGroupId(), _folder.getFolderId(), queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			2,
			DLFolderFinderUtil.countFE_ByG_F(
				_group.getGroupId(), _folder.getFolderId(), queryDefinition));
	}

	@Test
	public void testCountFE_FS_ByG_F() throws Exception {
		QueryDefinition<?> queryDefinition = new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			4,
			DLFolderFinderUtil.filterCountFE_FS_ByG_F(
				_group.getGroupId(), _folder.getFolderId(), queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			3,
			DLFolderFinderUtil.filterCountFE_FS_ByG_F(
				_group.getGroupId(), _folder.getFolderId(), queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			1,
			DLFolderFinderUtil.filterCountFE_FS_ByG_F(
				_group.getGroupId(), _folder.getFolderId(), queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			3,
			DLFolderFinderUtil.filterCountFE_FS_ByG_F(
				_group.getGroupId(), _folder.getFolderId(), queryDefinition));
	}

	@Test
	public void testCountFE_FS_ByG_F_M() throws Exception {
		QueryDefinition<?> queryDefinition = new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			4,
			DLFolderFinderUtil.filterCountFE_FS_ByG_F_M(
				_group.getGroupId(), _folder.getFolderId(), null,
				queryDefinition));
		Assert.assertEquals(
			3,
			DLFolderFinderUtil.filterCountFE_FS_ByG_F_M(
				_group.getGroupId(), _folder.getFolderId(),
				new String[] {ContentTypes.TEXT_PLAIN}, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			3,
			DLFolderFinderUtil.filterCountFE_FS_ByG_F_M(
				_group.getGroupId(), _folder.getFolderId(), null,
				queryDefinition));
		Assert.assertEquals(
			1,
			DLFolderFinderUtil.filterCountFE_FS_ByG_F_M(
				_group.getGroupId(), _folder.getFolderId(),
				new String[] {ContentTypes.APPLICATION_PDF}, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			1,
			DLFolderFinderUtil.filterCountFE_FS_ByG_F_M(
				_group.getGroupId(), _folder.getFolderId(), null,
				queryDefinition));
		Assert.assertEquals(
			1,
			DLFolderFinderUtil.filterCountFE_FS_ByG_F_M(
				_group.getGroupId(), _folder.getFolderId(),
				new String[] {ContentTypes.TEXT_PLAIN}, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			3,
			DLFolderFinderUtil.filterCountFE_FS_ByG_F_M(
				_group.getGroupId(), _folder.getFolderId(), null,
				queryDefinition));
		Assert.assertEquals(
			2,
			DLFolderFinderUtil.filterCountFE_FS_ByG_F_M(
				_group.getGroupId(), _folder.getFolderId(),
				new String[] {ContentTypes.TEXT_PLAIN}, queryDefinition));
	}

	@Test
	public void testFindF_FE_FS_ByG_F_M_M() throws Exception {
		QueryDefinition<?> queryDefinition = new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		List<Object> results = DLFolderFinderUtil.filterFindF_FE_FS_ByG_F_M_M(
			_group.getGroupId(), _folder.getFolderId(),
			new String[] {ContentTypes.TEXT_PLAIN}, false, queryDefinition);

		Assert.assertEquals(3, results.size());

		for (Object result : results) {
			if (result instanceof DLFileEntry) {
				DLFileEntry dlFileEntry = (DLFileEntry)result;

				Assert.assertEquals("FE1.txt", dlFileEntry.getTitle());
			}
			else if (result instanceof DLFileShortcut) {
				DLFileShortcut fileShortcut = (DLFileShortcut)result;

				Assert.assertEquals(
					_dlFileShortcut.getFileShortcutId(),
					fileShortcut.getFileShortcutId());
			}
			else if (result instanceof DLFolder) {
				DLFolder dlFolder = (DLFolder)result;

				Assert.assertEquals("Folder B", dlFolder.getName());
			}
			else {
				Assert.fail(String.valueOf(result.getClass()));
			}
		}
	}

	protected FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String mimeType)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		return DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), groupId, folderId, sourceFileName,
			mimeType, RandomTestUtil.randomBytes(), serviceContext);
	}

	private DLFileShortcut _dlFileShortcut;
	private Folder _folder;
	private Group _group;

}