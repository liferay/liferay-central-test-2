/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.documentlibrary.util;

import com.liferay.documentlibrary.model.Content;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.BaseTestCase;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Blob;

import java.util.Arrays;
import java.util.Date;

/**
 * @author Michael Chen
 */
public abstract class HookTest extends BaseTestCase {

	public void testAddDirectory() {
		try {
			Content content = createContent();

			hook.addDirectory(
				content.getCompanyId(), content.getRepositoryId(),
				content.getPath());
		}
		catch (Exception e) {
			fail("testAddDirectory failed!");
		}
	}

	public void testAddFileWithByteArray() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getContentId(), randomString(), new Date(),
			new ServiceContext(), _TEST_BYTE_ARRAY);

		boolean exists = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(exists);

		byte[] bytes = hook.getFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		assertTrue(Arrays.equals(bytes, _TEST_BYTE_ARRAY));
	}

	public void testAddFileWithFile() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getContentId(), randomString(), new Date(),
			new ServiceContext(), _TEST_FILE);

		byte[] bytes = hook.getFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		assertTrue(Arrays.equals(bytes, _TEST_BYTE_ARRAY));
	}

	public void testAddFileWithInputStream() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getContentId(), randomString(), new Date(),
			new ServiceContext(), new ByteArrayInputStream(_TEST_BYTE_ARRAY));

		boolean exists = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(exists);

		byte[] bytes = hook.getFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		assertTrue(Arrays.equals(bytes, _TEST_BYTE_ARRAY));
	}

	public void testCheckRoot() {
		try {
			hook.checkRoot(nextLong());
		}
		catch(Exception e) {
			fail("testCheckRoot failed!");
		}
	}

	public void testDeleteDirectory() throws Exception {
		Content content = createContent();

		content.setPath(content.getPath().concat(StringPool.SLASH));

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(),
			content.getPath(), content.getContentId(), randomString(),
			new Date(), new ServiceContext(), _TEST_BYTE_ARRAY);

		boolean exists = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(exists);

		hook.deleteDirectory(
			content.getCompanyId(), content.getPortletId(),
			content.getRepositoryId(), content.getPath());

		boolean exists2 = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertFalse(exists2);
	}

	public void testDeleteFile() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getContentId(), randomString(), new Date(),
			new ServiceContext(), _TEST_BYTE_ARRAY);

		boolean exists = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(exists);

		hook.deleteFile(
			content.getCompanyId(), content.getPortletId(),
			content.getRepositoryId(), content.getPath());

		boolean exists2 = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertFalse(exists2);
	}

	public void testDeleteFileWithVersion() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getContentId(), randomString(), new Date(),
			new ServiceContext(), _TEST_BYTE_ARRAY);

		boolean exists = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(exists);

		hook.deleteFile(content.getCompanyId(), content.getPortletId(),
			content.getRepositoryId(), content.getPath(),
			content.getVersion());

		boolean exists2 = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertFalse(exists2);
	}

	public void testGetFile() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(),
			content.getPath(), content.getContentId(), randomString(),
			new Date(), new ServiceContext(), _TEST_BYTE_ARRAY);

		byte[] bytes = hook.getFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		assertTrue(Arrays.equals(bytes, _TEST_BYTE_ARRAY));
	}

	public void testGetFileAsStream() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(),
			content.getPath(), content.getContentId(), randomString(),
			new Date(), new ServiceContext(), _TEST_BYTE_ARRAY);

		InputStream inputStream = hook.getFileAsStream(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		assertEquals(
			_TEST_BYTE_ARRAY.length, inputStream.available());
	}

	public void testGetFileAsStreamWithVersion() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(),
			content.getPath(), content.getContentId(), randomString(),
			new Date(), new ServiceContext(), _TEST_BYTE_ARRAY);

		InputStream inputStream = hook.getFileAsStream(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertEquals(
			_TEST_BYTE_ARRAY.length, inputStream.available());
	}

	public void testGetFileNames() throws Exception {
		Content content = createContent();

		content.setPath(content.getPath().concat(StringPool.SLASH));

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(),
			content.getPath() + "dir", content.getContentId(), randomString(),
			new Date(), new ServiceContext(), _TEST_BYTE_ARRAY);

		String[] names = hook.getFileNames(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		assertEquals(1, names.length);
	}

	public void testGetFileSize() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getContentId(), randomString(), new Date(),
			new ServiceContext(), _TEST_BYTE_ARRAY);

		long size = hook.getFileSize(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		assertEquals(_TEST_BYTE_ARRAY.length, size);
	}

	public void testGetFileWithVersion() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(),
			content.getPath(), content.getContentId(), randomString(),
			new Date(), new ServiceContext(), _TEST_BYTE_ARRAY);

		byte[] bytes = hook.getFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(Arrays.equals(bytes, _TEST_BYTE_ARRAY));
	}

	public void testHasFile() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getContentId(), randomString(), new Date(),
			new ServiceContext(), _TEST_BYTE_ARRAY);

		boolean has = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(has);
	}

	public void testMove() {
		try {
			hook.move(randomString(), randomString());
		}
		catch(Exception e) {
			fail("testMove failed!");
		}
	}

	public void testReindex() throws Exception {
		try {
			Content content = createContent();

			hook.reindex(
				new String[] {
					String.valueOf(content.getCompanyId()),
					content.getPortletId(), content.getPath(),
					String.valueOf(content.getRepositoryId())});
		}
		catch (Exception e) {
			fail("testReindex failed!");
		}
	}

	public void testUpdateFileWithByteArray() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getContentId(), randomString(), new Date(),
			new ServiceContext(), _TEST_BYTE_ARRAY);

		String newVersion = String.valueOf(
			Double.valueOf(content.getVersion()) + 0.1);

		hook.updateFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			newVersion, content.getPath(), content.getContentId(),
			randomString(), new Date(), new ServiceContext(),
			new byte[]{1, 2, 3});

		boolean exists = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(exists);

		boolean exists2 = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), newVersion);

		assertTrue(exists2);

		byte[] bytes = hook.getFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), newVersion);

		assertFalse(Arrays.equals(bytes, _TEST_BYTE_ARRAY));
	}

	public void testUpdateFileWithFile() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getContentId(), randomString(), new Date(),
			new ServiceContext(), new byte[]{1, 2, 3});

		String newVersion = String.valueOf(
			Double.valueOf(content.getVersion()) + 0.1);

		hook.updateFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			newVersion, content.getPath(), content.getContentId(),
			randomString(), new Date(), new ServiceContext(), _TEST_FILE);

		boolean exists = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(exists);

		boolean exists2 = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), newVersion);

		assertTrue(exists2);

		byte[] bytes = hook.getFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), newVersion);

		assertFalse(Arrays.equals(bytes, new byte[]{1, 2, 3}));
	}

	public void testUpdateFileWithInputStream() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(),
			content.getPath(), content.getContentId(), randomString(),
			new Date(), new ServiceContext(), new byte[1024]);

		String newVersion = String.valueOf(
			Double.valueOf(content.getVersion()) + 0.1);

		hook.updateFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			newVersion, content.getPath(), content.getContentId(),
			randomString(), new Date(), new ServiceContext(),
			new ByteArrayInputStream(new byte[1024]));

		boolean exists = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(exists);

		boolean exists2 = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), newVersion);

		assertTrue(exists2);

		byte[] bytes = hook.getFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), newVersion);

		assertFalse(Arrays.equals(bytes, _TEST_BYTE_ARRAY));
	}

	public void testUpdateFileWithNewFileName() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getContentId(), randomString(), new Date(),
			new ServiceContext(), new byte[1024]);

		String newFileName = randomString();

		hook.updateFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			newFileName, false);

		boolean exists = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertFalse(exists);

		boolean exists2 = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(), newFileName,
			content.getVersion());

		assertTrue(exists2);
	}

	public void testUpdateFileWithNewRepositoryId() throws Exception {
		Content content = createContent();

		hook.addFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getContentId(), randomString(), new Date(),
			new ServiceContext(), new byte[1024]);

		long newRepositoryId = nextLong();

		hook.updateFile(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), newRepositoryId,
			content.getPath(), content.getContentId());

		boolean exists = hook.hasFile(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertFalse(exists);

		boolean exists2 = hook.hasFile(
			content.getCompanyId(), newRepositoryId, content.getPath(),
			content.getVersion());

		assertTrue(exists2);
	}

	protected Content createContent() throws Exception {
		long contentId = nextLong();

		byte[] bytes = new byte[1024];

		Blob data = new OutputBlob(
			new ByteArrayInputStream(bytes), bytes.length);

		Content content = new Content(
			contentId, nextLong(), randomString(), nextLong(), nextLong(),
			randomString(), Hook.DEFAULT_VERSION, data, data.length());

		return content;
	}

	protected void setUpTestFile() throws Exception {
		//set up test byte array

		for(int i = 0; i < _TEST_BYTE_ARRAY.length; i ++) {
			_TEST_BYTE_ARRAY[i] = (byte)(i % 128);
		}

		//set up test file

		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(_TEST_FILE);
			fileWriter.write(new String(_TEST_BYTE_ARRAY));
			fileWriter.flush();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		finally {
			try {
				fileWriter.close();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	protected void tearDownTestFile() throws Exception {
		_TEST_FILE.delete();
	}

	protected Hook hook;

	private static final File _TEST_FILE = new File("HookTest.testFilename");

	private static final byte[] _TEST_BYTE_ARRAY = new byte[1024 * 65];

}