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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alexander Chow
 */
public class DLFileVersionHistoryTest extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testDeleteOneVersion() throws Exception {
		deleteVersion(false, false);
	}

	@Test
	public void testDeleteOneVersionOnePWC() throws Exception {
		deleteVersion(false, true);
	}

	@Test
	public void testDeleteTwoVersions() throws Exception {
		deleteVersion(true, false);
	}

	@Test
	public void testDeleteTwoVersionsOnePWC() throws Exception {
		deleteVersion(true, true);
	}

	@Test
	public void testRevertOneVersion() throws Exception {
		revertVersion(false, false);
	}

	@Test
	public void testRevertOneVersionOnePWC() throws Exception {
		revertVersion(false, true);
	}

	@Test
	public void testRevertTwoVersions() throws Exception {
		revertVersion(true, false);
	}

	@Test
	public void testRevertTwoVersionsOnePWC() throws Exception {
		revertVersion(true, true);
	}

	protected void assertFileEntryTitle(String fileName)
		throws PortalException {

		FileEntry fileEntry = DLAppServiceUtil.getFileEntry(
			_fileEntry.getFileEntryId());

		Assert.assertEquals(fileName, fileEntry.getTitle());
	}

	protected void assertLatestFileVersionTitle(String fileName)
		throws PortalException {

		DLFileVersion latestDLFileVersion =
			DLFileVersionLocalServiceUtil.getLatestFileVersion(
				_fileEntry.getFileEntryId(), false);

		Assert.assertEquals(fileName, latestDLFileVersion.getTitle());
	}

	protected void deleteFileVersion(
			String version, String fileName, boolean pwc)
		throws PortalException {

		DLAppServiceUtil.deleteFileVersion(
			_fileEntry.getFileEntryId(), version);

		if (fileName != null) {
			if (pwc) {
				assertLatestFileVersionTitle(fileName);
			}
			else {
				assertFileEntryTitle(fileName);
			}
		}
	}

	protected void deleteVersion(boolean versioned, boolean leaveCheckedOut)
		throws Exception {

		_fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), _VERSION_1_0);

		long fileEntryId = _fileEntry.getFileEntryId();

		if (versioned) {
			DLAppTestUtil.updateFileEntry(
				group.getGroupId(), fileEntryId, null, _VERSION_1_1);
		}

		if (leaveCheckedOut) {
			DLAppServiceUtil.checkOutFileEntry(
				fileEntryId, new ServiceContext());

			DLAppTestUtil.updateFileEntry(
				group.getGroupId(), fileEntryId, null, _VERSION_PWC);
		}

		if (versioned && leaveCheckedOut) {
			Assert.assertEquals(3, getFileVersionsCount());

			failDeleteFileVersion("PWC");
			deleteFileVersion("1.1", _VERSION_PWC, true);
			failDeleteFileVersion("1.0");

			Assert.assertEquals(2, getFileVersionsCount());
		}
		else if (versioned) {
			Assert.assertEquals(2, getFileVersionsCount());

			deleteFileVersion("1.1", _VERSION_1_0, false);
			failDeleteFileVersion("1.0");

			Assert.assertEquals(1, getFileVersionsCount());
		}
		else if (leaveCheckedOut) {
			Assert.assertEquals(2, getFileVersionsCount());

			failDeleteFileVersion("PWC");
			failDeleteFileVersion("1.0");

			Assert.assertEquals(2, getFileVersionsCount());
		}
		else {
			Assert.assertEquals(1, getFileVersionsCount());

			failDeleteFileVersion("1.0");

			Assert.assertEquals(1, getFileVersionsCount());
		}
	}

	protected void failDeleteFileVersion(String version)
		throws PortalException {

		try {
			deleteFileVersion(version, null, true);

			Assert.fail();
		}
		catch (InvalidFileVersionException ifve) {
		}
	}

	protected void failRevertFileVersion(String version)
		throws PortalException {

		try {
			revertFileVersion(version, null);

			Assert.fail();
		}
		catch (InvalidFileVersionException ifve) {
		}
	}

	protected int getFileVersionsCount() {
		List<FileVersion> fileVersions = _fileEntry.getFileVersions(
			WorkflowConstants.STATUS_ANY);

		return fileVersions.size();
	}

	protected void revertFileVersion(String version, String fileName)
		throws PortalException {

		DLAppServiceUtil.revertFileEntry(
			_fileEntry.getFileEntryId(), version, new ServiceContext());

		if (fileName != null) {
			assertLatestFileVersionTitle(fileName);
		}
	}

	protected void revertVersion(boolean versioned, boolean leaveCheckedOut)
		throws Exception {

		_fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), _VERSION_1_0);

		long fileEntryId = _fileEntry.getFileEntryId();

		if (versioned) {
			DLAppTestUtil.updateFileEntry(
				group.getGroupId(), fileEntryId, null, _VERSION_1_1);
		}

		if (leaveCheckedOut) {
			DLAppServiceUtil.checkOutFileEntry(
				fileEntryId, new ServiceContext());

			DLAppTestUtil.updateFileEntry(
				group.getGroupId(), fileEntryId, null, _VERSION_PWC);
		}

		if (versioned && leaveCheckedOut) {
			Assert.assertEquals(3, getFileVersionsCount());

			failRevertFileVersion("PWC");
			revertFileVersion("1.1", _VERSION_1_1);
			revertFileVersion("1.0", _VERSION_1_0);

			Assert.assertEquals(3, getFileVersionsCount());
		}
		else if (versioned) {
			Assert.assertEquals(2, getFileVersionsCount());

			failRevertFileVersion("1.1");
			revertFileVersion("1.0", _VERSION_1_0);

			Assert.assertEquals(3, getFileVersionsCount());
		}
		else if (leaveCheckedOut) {
			Assert.assertEquals(2, getFileVersionsCount());

			failRevertFileVersion("PWC");
			revertFileVersion("1.0", _VERSION_1_0);

			Assert.assertEquals(2, getFileVersionsCount());
		}
		else {
			Assert.assertEquals(1, getFileVersionsCount());

			failRevertFileVersion("1.0");

			Assert.assertEquals(1, getFileVersionsCount());
		}
	}

	private static final String _VERSION_1_0 = "Test Version 1.0.txt";

	private static final String _VERSION_1_1 = "Test Version 1.1.txt";

	private static final String _VERSION_PWC = "Test Version PWC.txt";

	private FileEntry _fileEntry;

}