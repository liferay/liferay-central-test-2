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

package com.liferay.portal.service;

import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.util.test.DLTestUtil;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class RepositoryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testCreateLocalRepositoryFromExistingFileEntryId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(
			0, dlFileEntry.getFileEntryId(), 0);
	}

	@Test
	public void testCreateLocalRepositoryFromExistingFileVersionId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(
			0, 0, dlFileVersion.getFileVersionId());
	}

	@Test
	public void testCreateLocalRepositoryFromExistingFolderId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(
			dlFolder.getFolderId(), 0, 0);
	}

	@Test
	public void testCreateLocalRepositoryFromExistingRepositoryId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(
			dlFolder.getRepositoryId());
	}

	@Test(expected = InvalidRepositoryIdException.class)
	public void testCreateLocalRepositoryFromInvalidFolderId()
		throws Exception {

		long folderId = RandomTestUtil.randomLong();

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(folderId, 0, 0);
	}

	@Test(expected = InvalidRepositoryIdException.class)
	public void testCreateLocalRepositoryFromNonexistentFileEntryId()
		throws Exception {

		long fileEntryId = RandomTestUtil.randomLong();

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(0, fileEntryId, 0);
	}

	@Test(expected = InvalidRepositoryIdException.class)
	public void testCreateLocalRepositoryFromNonexistentFileVersionId()
		throws Exception {

		long fileVersionId = RandomTestUtil.randomLong();

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(0, 0, fileVersionId);
	}

	@Test(expected = RepositoryException.class)
	public void testCreateLocalRepositoryFromNonexistentRepositoryId()
		throws Exception {

		long repositoryId = RandomTestUtil.randomLong();

		RepositoryLocalServiceUtil.getLocalRepositoryImpl(repositoryId);
	}

	@Test
	public void testCreateRepositoryFromExistingFileEntryId() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		RepositoryLocalServiceUtil.getRepositoryImpl(
			0, dlFileEntry.getFileEntryId(), 0);
	}

	@Test
	public void testCreateRepositoryFromExistingFileVersionId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		RepositoryLocalServiceUtil.getRepositoryImpl(
			0, 0, dlFileVersion.getFileVersionId());
	}

	@Test
	public void testCreateRepositoryFromExistingFolderId() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		RepositoryLocalServiceUtil.getRepositoryImpl(
			dlFolder.getFolderId(), 0, 0);
	}

	@Test
	public void testCreateRepositoryFromExistingRepositoryId()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		RepositoryLocalServiceUtil.getRepositoryImpl(
			dlFolder.getRepositoryId());
	}

	@Test(expected = InvalidRepositoryIdException.class)
	public void testCreateRepositoryFromNonexistentFileEntryId()
		throws Exception {

		long fileEntryId = RandomTestUtil.randomLong();

		RepositoryLocalServiceUtil.getRepositoryImpl(0, fileEntryId, 0);
	}

	@Test(expected = InvalidRepositoryIdException.class)
	public void testCreateRepositoryFromNonexistentFileVersionId()
		throws Exception {

		long fileVersionId = RandomTestUtil.randomLong();

		RepositoryLocalServiceUtil.getRepositoryImpl(0, 0, fileVersionId);
	}

	@Test(expected = InvalidRepositoryIdException.class)
	public void testCreateRepositoryFromNonexistentFolderId() throws Exception {
		long folderId = RandomTestUtil.randomLong();

		RepositoryLocalServiceUtil.getRepositoryImpl(folderId, 0, 0);
	}

	@Test(expected = RepositoryException.class)
	public void testCreateRepositoryFromNonexistentRepositoryId()
		throws Exception {

		long repositoryId = RandomTestUtil.randomLong();

		RepositoryLocalServiceUtil.getRepositoryImpl(repositoryId);
	}

	@DeleteAfterTestRun
	private Group _group;

}