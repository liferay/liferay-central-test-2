/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This JUnit test case takes into consideration all possible permutations of
 * file names and extensions that can be added into the document library. It
 * verifies that the correct validations occur and the correct title with
 * extension can be generated.
 *
 * <table>
 *
 * 	<tr>
 * 		<th>
 * 			Source
 * 		</th>
 * 		<th>
 * 			Title
 * 		</th>
 * 		<th>
 * 			Extension
 * 		</th>
 * 		<th>
 * 			Download Title
 * 		</th>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			Text.txt
 * 		</td>
 * 		<td>
 * 			Text.pdf
 * 		</td>
 * 		<td>
 * 			txt
 * 		</td>
 * 		<td>
 * 			Text.pdf.txt
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			Test.txt
 * 		</td>
 * 		<td>
 * 			Test.txt
 * 		</td>
 * 		<td>
 * 			txt
 * 		</td>
 * 		<td>
 * 			Test.txt
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			Test.txt
 * 		</td>
 * 		<td>
 * 			Test
 * 		</td>
 * 		<td>
 * 			txt
 * 		</td>
 * 		<td>
 * 			Test.txt
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			Test.txt
 * 		</td>
 * 		<td>
 * 		</td>
 * 		<td>
 * 			txt
 * 		</td>
 * 		<td>
 * 			Test.txt
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			Test
 * 		</td>
 * 		<td>
 * 			Test.txt
 * 		</td>
 * 		<td>
 * 			txt
 * 		</td>
 * 		<td>
 * 			Test.txt
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			Test
 * 		</td>
 * 		<td>
 * 			Test
 * 		</td>
 * 		<td>
 * 		</td>
 * 		<td>
 * 			Test
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 			Test
 * 		</td>
 * 		<td>
 * 		</td>
 * 		<td>
 * 		</td>
 * 		<td>
 * 			Test
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td>
 * 		</td>
 * 		<td>
 * 			Test.txt
 * 		</td>
 * 		<td>
 * 			txt
 * 		</td>
 * 		<td>
 * 			Test.txt
 * 		</td>
 * 	</tr>
 *
 * 	<tr>
 * 		<td>
 * 		</td>
 * 		<td>
 * 			Test
 * 		</td>
 * 		<td>
 * 		</td>
 * 		<td>
 * 			Test
 * 		</td>
 * 	</tr>
 *
 * </table>
 *
 * @author Alexander Chow
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileEntryExtensionTest extends BaseDLAppTestCase {

	@Test
	public void testAddFileEntryBasic() throws Exception {
		testAddFileEntryBasic(_FILE_NAME, "Test.pdf", "txt", "Test.pdf.txt");
		testAddFileEntryBasic(_FILE_NAME, _FILE_NAME, "txt", _FILE_NAME);
		testAddFileEntryBasic(
			_FILE_NAME, _STRIPPED_FILE_NAME, "txt", _FILE_NAME);
		testAddFileEntryBasic(_FILE_NAME, "", "txt", _FILE_NAME);
		testAddFileEntryBasic(
			_STRIPPED_FILE_NAME, _FILE_NAME, "txt", _FILE_NAME);
		testAddFileEntryBasic(
			_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME, "", _STRIPPED_FILE_NAME);
		testAddFileEntryBasic(_STRIPPED_FILE_NAME, "", "", _STRIPPED_FILE_NAME);
		testAddFileEntryBasic("", _FILE_NAME, "txt", _FILE_NAME);
		testAddFileEntryBasic("", _STRIPPED_FILE_NAME, "", _STRIPPED_FILE_NAME);

		try {
			addFileEntry(false, "", "");

			Assert.fail(
				"Created document with blank source file name and blank title");
		}
		catch (FileNameException fne) {
		}
	}

	@Test
	public void testAddFileEntryFalsePositives() throws Exception {

		// "Test.txt" / "Test.txt" followed by "Test" / "Test"

		FileEntry fileEntry = addFileEntry(false, _FILE_NAME, _FILE_NAME);

		try {
			FileEntry tempFileEntry = addFileEntry(
				false, _STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}
		catch (DuplicateFileException dfe) {
			Assert.fail("Unable to create" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());

		// "Test" / "Test" followed by "Test.txt" / "Test.txt"

		fileEntry = addFileEntry(
			false, _STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

		try {
			FileEntry tempFileEntry = addFileEntry(
				false, _FILE_NAME, _FILE_NAME);

			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}
		catch (DuplicateFileException dfe) {
			Assert.fail("Unable to create" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Test
	public void testAddFileEntryWithExtension() throws Exception {
		FileEntry fileEntry = addFileEntry(false, _FILE_NAME, _FILE_NAME);

		// "Test.txt" / "Test"

		try {
			addFileEntry(false, _FILE_NAME, _STRIPPED_FILE_NAME);

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}

		FileEntry tempFileEntry = addFileEntry(false, "Temp.txt", "Temp");

		try {
			updateFileEntry(
				tempFileEntry.getFileEntryId(), _FILE_NAME,
				_STRIPPED_FILE_NAME);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe2) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "Test.txt" / ""

		try {
			addFileEntry(false, _FILE_NAME, "");

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}

		tempFileEntry = addFileEntry(false, "Temp.txt", "");

		try {
			updateFileEntry(tempFileEntry.getFileEntryId(), _FILE_NAME, "");

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "Test" / "Test.txt"

		try {
			addFileEntry(false, _STRIPPED_FILE_NAME, _FILE_NAME);

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}

		tempFileEntry = addFileEntry(false, "Temp", "Temp.txt");

		try {
			updateFileEntry(
				tempFileEntry.getFileEntryId(), _STRIPPED_FILE_NAME,
				_FILE_NAME);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe2) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "" / "Test.txt"

		try {
			addFileEntry(false, "", _FILE_NAME);

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}

		tempFileEntry = addFileEntry(false, "", "Temp.txt");

		try {
			updateFileEntry(tempFileEntry.getFileEntryId(), "", _FILE_NAME);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe2) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Test
	public void testAddFileEntryWithoutExtension() throws Exception {
		FileEntry fileEntry = addFileEntry(
			false, _STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

		// "Test" / ""

		try {
			addFileEntry(false, _STRIPPED_FILE_NAME, "");

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}

		FileEntry tempFileEntry = addFileEntry(false, "Temp", "");

		try {
			updateFileEntry(
				tempFileEntry.getFileEntryId(), _STRIPPED_FILE_NAME, "");

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe2) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "" / "Test"

		try {
			addFileEntry(false, "", _STRIPPED_FILE_NAME);

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}

		tempFileEntry = addFileEntry(false, "", "Temp");

		try {
			updateFileEntry(
				tempFileEntry.getFileEntryId(), "", _STRIPPED_FILE_NAME);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	protected void testAddFileEntryBasic(
			String sourceFileName, String title, String extension,
			String titleWithExtension)
		throws Exception {

		FileEntry fileEntry = addFileEntry(false, sourceFileName, title);

		Assert.assertEquals(
			"Invalid file extension", extension, fileEntry.getExtension());

		Assert.assertEquals(
			titleWithExtension, DLUtil.getTitleWithExtension(fileEntry));

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	private static final String _FAIL_DUPLICATE_MESSAGE_SUFFIX =
		" a file on top of one with the same title and extension";

	private static final String _FILE_NAME = "Test.txt";

	private static final String _STRIPPED_FILE_NAME = "Test";

}