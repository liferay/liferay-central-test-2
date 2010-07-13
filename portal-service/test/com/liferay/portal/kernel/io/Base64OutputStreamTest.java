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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.CharPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author Tina Tian
 */
public class Base64OutputStreamTest extends TestCase {

	private static final String _TEST_FILE_NAME =
		"Base64OutputStreamTest.testFilename";

	public void testClose() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			new FileOutputStream(testFile));

		base64OutputStream.write('A');

		base64OutputStream.close();

		FileInputStream fileInputStream = new FileInputStream(testFile);

		byte[] bytes = new byte[4];

		fileInputStream.read(bytes);

		fileInputStream.close();

		if ((bytes[3] != CharPool.EQUAL) || (bytes[2] != CharPool.EQUAL)) {
			fail();
		}

		testFile.delete();
	}

	public void testEncodeUnit1Byte() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			new FileOutputStream(testFile));

		base64OutputStream.encodeUnit((byte)'A');

		base64OutputStream.close();

		FileInputStream fileInputStream = new FileInputStream(testFile);

		assertEquals(4, fileInputStream.read(new byte[4]));

		fileInputStream.close();

		testFile.delete();
	}

	public void testEncodeUnit2Bytes() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			new FileOutputStream(testFile));

		base64OutputStream.encodeUnit((byte)'A', (byte)'B');

		base64OutputStream.close();

		FileInputStream fileInputStream = new FileInputStream(testFile);

		assertEquals(4, fileInputStream.read(new byte[4]));

		fileInputStream.close();

		testFile.delete();
	}

	public void testEncodeUnit3Args() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			new FileOutputStream(testFile));

		base64OutputStream.encodeUnit((byte)'A', (byte)'B', (byte)'C');

		base64OutputStream.close();

		FileInputStream fileInputStream = new FileInputStream(testFile);

		assertEquals(4, fileInputStream.read(new byte[4]));

		fileInputStream.close();

		testFile.delete();
	}

	public void testFlush() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			new FileOutputStream(testFile));

		base64OutputStream.write('A');

		base64OutputStream.flush();

		FileInputStream fileInputStream = new FileInputStream(testFile);

		byte[] bytes = new byte[4];

		assertEquals(4, fileInputStream.read(bytes));

		fileInputStream.close();

		testFile.delete();

		testFile = new File(_TEST_FILE_NAME);

		base64OutputStream = new Base64OutputStream(
			new FileOutputStream(testFile));

		base64OutputStream.write('A');
		base64OutputStream.write('B');

		base64OutputStream.flush();

		fileInputStream = new FileInputStream(testFile);

		assertEquals(4, fileInputStream.read(bytes));

		fileInputStream.close();

		testFile.delete();
	}

	public void testGetChar() {
		try {
			File testFile = new File(_TEST_FILE_NAME);

			Base64OutputStream base64OutputStream = new Base64OutputStream(
				new FileOutputStream(testFile));

			assertEquals('A', base64OutputStream.getChar(0));
			assertEquals('?', base64OutputStream.getChar(64));

			testFile.delete();
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testWrite3Args() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			new FileOutputStream(testFile));

		byte[] bytes = {'A', 'B', 'C', 'A', 'B', 'C'};

		base64OutputStream.write(bytes, 0, 1);
		base64OutputStream.write(bytes, 1, 2);
		base64OutputStream.write(bytes, 3, 3);

		base64OutputStream.close();

		FileInputStream fileInputStream = new FileInputStream(testFile);

		assertEquals(8, fileInputStream.read(new byte[8]));

		fileInputStream.close();

		testFile.delete();
	}

	public void testWriteByteArray() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			new FileOutputStream(testFile));

		base64OutputStream.write(new byte [] {'A', 'B', 'C'});

		base64OutputStream.close();

		FileInputStream fileInputStream = new FileInputStream(testFile);

		assertEquals(4, fileInputStream.read(new byte[4]));

		fileInputStream.close();

		testFile.delete();
	}

	public void testWriteInt() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		Base64OutputStream base64OutputStream = new Base64OutputStream(
			new FileOutputStream(testFile));

		base64OutputStream.write('A');
		base64OutputStream.write('A');
		base64OutputStream.write('A');

		base64OutputStream.close();

		FileInputStream fileInputStream = new FileInputStream(testFile);

		assertEquals(4, fileInputStream.read(new byte[4]));

		fileInputStream.close();

		testFile.delete();
	}

}