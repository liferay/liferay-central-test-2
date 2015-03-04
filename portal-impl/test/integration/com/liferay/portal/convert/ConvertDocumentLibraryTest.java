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

package com.liferay.portal.convert;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchContentException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLContentLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.store.DBStore;
import com.liferay.portlet.documentlibrary.store.FileSystemStore;
import com.liferay.portlet.documentlibrary.store.Store;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
import com.liferay.portlet.documentlibrary.util.DLPreviewableProcessor;
import com.liferay.portlet.documentlibrary.util.ImageProcessorUtil;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public class ConvertDocumentLibraryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		PropsValues.DL_STORE_IMPL = FileSystemStore.class.getName();

		_sourceStore = (Store)InstanceFactory.newInstance(
			ClassLoaderUtil.getPortalClassLoader(),
			FileSystemStore.class.getName());

		StoreFactory.setInstance(_sourceStore);

		_group = GroupTestUtil.addGroup();

		_convertProcess = (ConvertProcess)InstancePool.get(
			ConvertDocumentLibrary.class.getName());

		_convertProcess.setParameterValues(
			new String[] {DBStore.class.getName(), Boolean.TRUE.toString()});
	}

	@After
	public void tearDown() throws Exception {
		PropsValues.DL_STORE_IMPL = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

		Store store = (Store)InstanceFactory.newInstance(
			ClassLoaderUtil.getPortalClassLoader(), PropsValues.DL_STORE_IMPL);

		StoreFactory.setInstance(store);
	}

	@Test
	public void testMigrateDLAndDeleteFilesInSourceStore() throws Exception {
		testMigrateAndCheckOldRepositoryFiles(Boolean.TRUE);
	}

	@Test
	public void testMigrateDLAndKeepFilesInSourceStore() throws Exception {
		testMigrateAndCheckOldRepositoryFiles(Boolean.FALSE);
	}

	@Test
	public void testMigrateDLWhenFileEntryInFolder() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		testMigrateDL(folder.getFolderId());
	}

	@Test
	public void testMigrateDLWhenFileEntryInRootFolder() throws Exception {
		testMigrateDL(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Test
	public void testMigrateImages() throws Exception {
		Image image = addImage();

		try {
			_convertProcess.convert();

			try {
				DLContentLocalServiceUtil.getContent(
					0, 0, image.getImageId() + ".jpg");
			}
			catch (NoSuchContentException nsce) {
				Assert.fail();
			}
		}
		finally {
			ImageLocalServiceUtil.deleteImage(image);
		}
	}

	@Test
	public void testMigrateMB() throws Exception {
		MBMessage mbMessage = addMBMessageAttachment();

		_convertProcess.convert();

		DLFileEntry dlFileEntry = getDLFileEntry(mbMessage);

		String title = dlFileEntry.getTitle();

		Assert.assertTrue(title.endsWith(".docx"));

		try {
			DLContentLocalServiceUtil.getContent(
				dlFileEntry.getCompanyId(),
				DLFolderConstants.getDataRepositoryId(
					dlFileEntry.getRepositoryId(), dlFileEntry.getFolderId()),
				dlFileEntry.getName());
		}
		catch (NoSuchContentException nsce) {
			Assert.fail();
		}
	}

	@Test
	public void testStoreUpdatedAfterConversion() throws Exception {
		_convertProcess.convert();

		Store store = StoreFactory.getInstance();

		Assert.assertEquals(
			DBStore.class.getName(), store.getClass().getName());
	}

	protected FileEntry addFileEntry(
			long folderId, String fileName, String mimeType, byte[] bytes)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), folderId,
			fileName, mimeType, bytes, serviceContext);
	}

	protected Image addImage() throws Exception {
		return ImageLocalServiceUtil.updateImage(
			CounterLocalServiceUtil.increment(),
			FileUtil.getBytes(getClass(), "dependencies/liferay.jpg"));
	}

	protected MBMessage addMBMessageAttachment() throws Exception {
		List<ObjectValuePair<String, InputStream>> objectValuePairs =
			MBTestUtil.getInputStreamOVPs(
				"OSX_Test.docx", getClass(), StringPool.BLANK);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		User user = TestPropsValues.getUser();

		return MBMessageLocalServiceUtil.addMessage(
			user.getUserId(), user.getFullName(), _group.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, "Subject", "Body",
			MBMessageConstants.DEFAULT_FORMAT, objectValuePairs, false, 0,
			false, serviceContext);
	}

	protected DLFileEntry getDLFileEntry(Object object) throws Exception {
		List<FileEntry> fileEntries = new ArrayList<>();

		if (object instanceof MBMessage) {
			fileEntries = ((MBMessage)object).getAttachmentsFileEntries(0, 1);
		}

		if (fileEntries.isEmpty()) {
			Assert.fail();
		}

		FileEntry fileEntry = fileEntries.get(0);

		return DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileEntry.getFileEntryId());
	}

	protected void testMigrateAndCheckOldRepositoryFiles(Boolean delete)
		throws Exception {

		_convertProcess.setParameterValues(
			new String[] {DBStore.class.getName(), delete.toString()});

		FileEntry rootFileEntry = addFileEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		FileEntry folderFileEntry = addFileEntry(
			folder.getFolderId(), "liferay.jpg", ContentTypes.IMAGE_JPEG,
			FileUtil.getBytes(getClass(), "dependencies/liferay.jpg"));

		ImageProcessorUtil.generateImages(
			null, folderFileEntry.getFileVersion());

		_convertProcess.convert();

		DLFileEntry rootDLFileEntry = (DLFileEntry)rootFileEntry.getModel();

		Assert.assertNotEquals(
			delete,
			_sourceStore.hasFile(
				rootDLFileEntry.getCompanyId(),
				rootDLFileEntry.getDataRepositoryId(),
				rootDLFileEntry.getName()));

		DLFileEntry folderDLFileEntry = (DLFileEntry)folderFileEntry.getModel();

		Assert.assertNotEquals(
			delete,
			_sourceStore.hasDirectory(
				folderDLFileEntry.getCompanyId(),
				DLPreviewableProcessor.REPOSITORY_ID,
				DLPreviewableProcessor.THUMBNAIL_PATH));
		Assert.assertNotEquals(
			delete,
			_sourceStore.hasFile(
				folderDLFileEntry.getCompanyId(),
				folderDLFileEntry.getDataRepositoryId(),
				folderDLFileEntry.getName()));
	}

	protected void testMigrateDL(long folderId) throws Exception {
		FileEntry fileEntry = addFileEntry(
			folderId, RandomTestUtil.randomString() + ".txt",
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomBytes());

		_convertProcess.convert();

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		try {
			DLContentLocalServiceUtil.getContent(
				dlFileEntry.getCompanyId(),
				DLFolderConstants.getDataRepositoryId(
					dlFileEntry.getRepositoryId(), dlFileEntry.getFolderId()),
				dlFileEntry.getName());
		}
		catch (NoSuchContentException nsce) {
			Assert.fail();
		}
	}

	private ConvertProcess _convertProcess;

	@DeleteAfterTestRun
	private Group _group;

	private Store _sourceStore;

}