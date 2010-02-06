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

package com.liferay.portlet.imagegallery.service;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.imagegallery.DuplicateImageNameException;
import com.liferay.portlet.imagegallery.NoSuchFolderException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;

import java.io.File;

/**
 * <a href="IGImageServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class IGImageServiceTest extends BaseServiceTestCase {

	public void setUp() throws Exception {
		super.setUp();

		String name = "Test Folder";
		String description = "This is a test folder.";

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		Layout layout = LayoutLocalServiceUtil.getLayout(
			TestPropsValues.LAYOUT_PLID);

		try {
			_folder = IGFolderServiceUtil.getFolder(
				layout.getGroupId(), IGFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				name);

			IGFolderServiceUtil.deleteFolder(_folder.getFolderId());

			_folder = null;
		}
		catch (NoSuchFolderException nsfe) {
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(addCommunityPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);
		serviceContext.setScopeGroupId(layout.getGroupId());
		serviceContext.setPlid(layout.getPlid());

		_folder = IGFolderServiceUtil.addFolder(
			IGFolderConstants.DEFAULT_PARENT_FOLDER_ID, name, description,
			serviceContext);
	}

	public void tearDown() throws Exception {
		if (_folder != null) {
			IGFolderServiceUtil.deleteFolder(_folder.getFolderId());
		}

		super.tearDown();
	}

	public void testAddImageWithDuplicateName() throws Exception {
		String name = "liferay.png";
		String description = StringPool.BLANK;
		File image = new File(getClassResource(
			"com/liferay/portlet/imagegallery/dependencies/" + name).getPath());
		String contentType = "png";

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		IGImageServiceUtil.addImage(
			_folder.getGroupId(), _folder.getFolderId(), name, description,
			image, contentType, serviceContext);

		try {
			IGImageServiceUtil.addImage(
				_folder.getGroupId(), _folder.getFolderId(), name, description,
				image, contentType, serviceContext);

			fail("Able to add two images of the name " + name);
		}
		catch (DuplicateImageNameException dine) {
		}
	}

	private IGFolder _folder;

}