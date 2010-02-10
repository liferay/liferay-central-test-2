/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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