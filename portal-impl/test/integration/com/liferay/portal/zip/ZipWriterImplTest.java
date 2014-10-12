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

package com.liferay.portal.zip;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.DependenciesTestUtil;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ZipWriterImplTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_expectedEntryContent = StringUtil.read(
			DependenciesTestUtil.getDependencyAsInputStream(
				ZipWriterImplTest.class, _ENTRY_FILE_PATH));
	}

	@Before
	public void setUp() {
		_tempZipFilePath = SystemProperties.get(SystemProperties.TMP_DIR) +
			File.separatorChar + new Date().getTime() + "-file.zip";
	}

	@Test
	public void testAddEntryFromBytes() throws Exception {
		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		File entry = DependenciesTestUtil.getDependencyAsFile(
			getClass(), _ENTRY_FILE_PATH);

		zipWriter.addEntry(_ENTRY_FILE_PATH, FileUtil.getBytes(entry));

		File file = zipWriter.getFile();

		Assert.assertTrue(file.exists());

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals(
			_expectedEntryContent,
			zipReader.getEntryAsString(_ENTRY_FILE_PATH));

		zipReader.close();

		zipWriter.getFile().delete();
	}

	@Test
	public void testAddEntryFromBytesThatAreEmpty() throws Exception {
		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		byte[] emptyBytes = new byte[0];

		zipWriter.addEntry("empty.txt", emptyBytes);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals("", zipReader.getEntryAsString("empty.txt"));

		zipReader.close();

		zipWriter.getFile().delete();
	}

	@Test
	public void testAddEntryFromInputStream() throws Exception {
		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		zipWriter.addEntry(
			_ENTRY_FILE_PATH,
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ENTRY_FILE_PATH));

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals(
			_expectedEntryContent,
			zipReader.getEntryAsString(_ENTRY_FILE_PATH));

		zipReader.close();

		zipWriter.getFile().delete();
	}

	@Test
	public void testAddEntryFromInputStreamThatIsNull() throws Exception {
		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		InputStream nullableInputStream = null;

		zipWriter.addEntry("null.txt", nullableInputStream);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertNull(zipReader.getEntryAsString("null.txt"));

		zipReader.close();

		zipWriter.getFile().delete();
	}

	@Test
	public void testAddEntryFromInputStreamThatStartsWithSlash()
		throws Exception {

		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		zipWriter.addEntry(
			"/" + _ENTRY_FILE_PATH,
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ENTRY_FILE_PATH));

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals(
			_expectedEntryContent,
			zipReader.getEntryAsString(_ENTRY_FILE_PATH));

		zipReader.close();

		zipWriter.getFile().delete();
	}

	@Test
	public void testAddEntryFromString() throws Exception {
		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		zipWriter.addEntry("string.txt", "This is a String");

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals(
			"This is a String", zipReader.getEntryAsString("string.txt"));

		zipReader.close();

		zipWriter.getFile().delete();
	}

	@Test
	public void testAddEntryFromStringBuilder() throws Exception {
		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		StringBuilder sb = new StringBuilder();

		sb.append("This is a String");

		zipWriter.addEntry("string.txt", sb);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals(
			"This is a String", zipReader.getEntryAsString("string.txt"));

		zipReader.close();

		zipWriter.getFile().delete();
	}

	@Test
	public void testAddEntryFromStringBuilderThatIsEmpty() throws Exception {
		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		StringBuilder emptyStringBuilder = new StringBuilder();

		zipWriter.addEntry("empty.txt", emptyStringBuilder);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals("", zipReader.getEntryAsString("empty.txt"));

		zipReader.close();

		zipWriter.getFile().delete();
	}

	@Test
	public void testAddEntryFromStringBuilderThatIsNull() throws Exception {
		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		StringBuilder nullStringBuilder = null;

		zipWriter.addEntry("null.txt", nullStringBuilder);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertNull(zipReader.getEntryAsString("null.txt"));

		zipReader.close();

		zipWriter.getFile().delete();
	}

	@Test
	public void testAddEntryFromStringThatIsEmpty() throws Exception {
		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		String emptyString = "";

		zipWriter.addEntry("empty.txt", emptyString);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals("", zipReader.getEntryAsString("empty.txt"));

		zipReader.close();

		zipWriter.getFile().delete();
	}

	@Test
	public void testAddEntryFromStringThatIsNull() throws Exception {
		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		String nullString = null;

		zipWriter.addEntry("null.txt", nullString);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertNull(zipReader.getEntryAsString("null.txt"));

		zipReader.close();

		zipWriter.getFile().delete();
	}

	@Test
	public void testConstructor() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl();

		Assert.assertNotNull(zipWriter);
		Assert.assertNotNull(zipWriter.getFile());

		zipWriter.getFile().delete();

		File zipFile = new File(_tempZipFilePath);

		zipWriter = new ZipWriterImpl(zipFile);

		Assert.assertNotNull(zipWriter);

		File file = zipWriter.getFile();

		Assert.assertNotNull(file);
		Assert.assertTrue(file.exists());
		Assert.assertEquals(zipFile.getPath(), file.getPath());

		zipWriter.getFile().delete();
	}

	@Test
	public void testFinish() throws Exception {
		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		StringBuilder sb = new StringBuilder();

		sb.append("This is a String");

		zipWriter.addEntry("string.txt", sb);

		byte[] bytes = zipWriter.finish();

		Assert.assertArrayEquals(FileUtil.getBytes(tempZipFile), bytes);

		zipWriter.getFile().delete();
	}

	@Test
	public void testFinishIfZipFileIsNotSet() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl();

		zipWriter.finish();

		zipWriter.getFile().delete();
	}

	/**
	 * Throws an ArchiveFileNotFoundException if and only if this file is a
	 * true archive file, not just a false positive, including RAES
	 * encrypted ZIP files for which key prompting has been cancelled or
	 * disabled.
	 */
	@Test
	public void testFinishIfZipFileIsSet() throws Exception {
		File tempZipFile = new File(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		zipWriter.finish();

		zipWriter.getFile().delete();
	}

	private static final String _ENTRY_FILE_PATH = "entry.txt";

	private static String _expectedEntryContent;

	private String _tempZipFilePath;

}