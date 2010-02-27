/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

/**
 * <a href="DLFileEntryServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class DLFileEntryServiceTest extends BaseServiceTestCase {

	public void setUp() throws Exception {
		super.setUp();

		long groupId = PortalUtil.getScopeGroupId(TestPropsValues.LAYOUT_PLID);

		String name = "Test Folder";
		String description = "This is a test folder.";

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		try {
			DLFolderServiceUtil.deleteFolder(
				groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name);
		}
		catch (NoSuchFolderException nsfe) {
		}

		_folder = DLFolderServiceUtil.addFolder(
			groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			name, description, serviceContext);
	}

	public void tearDown() throws Exception {
		if (_folder != null) {
			DLFolderServiceUtil.deleteFolder(_folder.getFolderId());
		}

		super.tearDown();
	}

	public void testAddFileEntryWithDuplicateName() throws Exception {
		String fileName = "helloworld.txt";
		String description = StringPool.BLANK;
		String versionDescription = StringPool.BLANK;
		String extraSettings = StringPool.BLANK;

		String content = "Hello World!";

		byte[] bytes = content.getBytes();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		DLFileEntryServiceUtil.addFileEntry(
			_folder.getGroupId(), _folder.getFolderId(), fileName, fileName,
			description, versionDescription, extraSettings, bytes,
			serviceContext);

		try {
			DLFileEntryServiceUtil.addFileEntry(
				_folder.getGroupId(), _folder.getFolderId(), fileName, fileName,
				description, versionDescription, extraSettings, bytes,
				serviceContext);

			fail("Able to add two files of the name " + fileName);
		}
		catch (DuplicateFileException dfe) {
		}
	}

	private DLFolder _folder;

}