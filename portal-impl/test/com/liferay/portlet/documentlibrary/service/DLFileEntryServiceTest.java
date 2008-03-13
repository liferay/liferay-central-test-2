/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;

/**
 * <a href="DLFileEntryServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class DLFileEntryServiceTest extends BaseServiceTestCase {

	public void testAddFileEntryWithDuplicateName() throws Exception {
		String fileName = "helloworld.txt";
		String description = StringPool.BLANK;
		String[] tagsEntries = null;
		String extraSettings = StringPool.BLANK;

		String content = "Hello World!";

		byte[] byteArray = content.getBytes();

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		DLFileEntryServiceUtil.addFileEntry(
			_folder.getFolderId(), fileName, fileName, description,
			tagsEntries, extraSettings, byteArray, addCommunityPermissions,
			addGuestPermissions);

		try {
			DLFileEntryServiceUtil.addFileEntry(
				_folder.getFolderId(), fileName, fileName, description,
				tagsEntries, extraSettings, byteArray, addCommunityPermissions,
				addGuestPermissions);

			fail("Able to add two files of the name " + fileName);
		}
		catch (DuplicateFileException dfe) {
		}
	}

	protected void setUp() throws Exception {
		super.setUp();

		long groupId = PortalUtil.getPortletGroupId(
			TestPropsValues.LAYOUT_PLID);

		String name = "Test Folder";
		String description = "This is a test folder.";

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		try {
			DLFolderServiceUtil.deleteFolder(
				groupId, DLFolderImpl.DEFAULT_PARENT_FOLDER_ID, name);
		}
		catch (NoSuchFolderException nsfe) {
		}

		_folder = DLFolderServiceUtil.addFolder(
			TestPropsValues.LAYOUT_PLID, DLFolderImpl.DEFAULT_PARENT_FOLDER_ID,
			name, description, addCommunityPermissions, addGuestPermissions);
	}

	protected void tearDown() throws Exception {
		if (_folder != null) {
			DLFolderServiceUtil.deleteFolder(_folder.getFolderId());
		}

		super.tearDown();
	}

	private DLFolder _folder;

}