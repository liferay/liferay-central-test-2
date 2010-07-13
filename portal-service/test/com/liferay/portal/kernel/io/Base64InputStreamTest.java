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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author Tina Tian
 */
public class Base64InputStreamTest extends TestCase {

	public void testAvailable() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		FileOutputStream fileOutputStream = new FileOutputStream(testFile);

		byte[] bytes = {'a', 'b', 'c', 'd'};

		fileOutputStream.write(bytes);

		fileOutputStream.close();

		Base64InputStream base64InputStream = new Base64InputStream(
			new FileInputStream(testFile));

		int returnValue = base64InputStream.available();

		assertEquals(3, returnValue);

		base64InputStream.close();

		testFile.delete();
	}

	public void testDecode() {
		try {
			File testFile = new File(_TEST_FILE_NAME);

			FileOutputStream fileOutputStream = new FileOutputStream(testFile);

			byte[] bytes = {'a', 'b', 'c', 'd'};

			fileOutputStream.write(bytes);

			fileOutputStream.close();

			Base64InputStream base64InputStream =
				new Base64InputStream(new FileInputStream(testFile));

			byte[] outputBuffer = new byte[3];
			int position = 0;

			assertEquals(
				3, base64InputStream.decode(bytes, outputBuffer, position, 0));
			assertEquals(
				2, base64InputStream.decode(bytes, outputBuffer, position, 1));
			assertEquals(
				1, base64InputStream.decode(bytes, outputBuffer, position, 2));
			assertEquals(
				-1, base64InputStream.decode(bytes, outputBuffer, position, 3));

			base64InputStream.close();

			testFile.delete();
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testDecodeUnit() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		FileOutputStream fileOutputStream = new FileOutputStream(testFile);

		byte[] bytes = {
			'a', 'b', 'c', 'd', 'e', 'f', 'h', '=', 'e', 'f', '=', '=', 'e',
			'=', 'e', 'f', '=', 'a'};

		fileOutputStream.write(bytes);

		fileOutputStream.close();

		Base64InputStream base64InputStream = new Base64InputStream(
			new FileInputStream(testFile));

		byte[] outputBuffer = new byte[3];
		int position = 0;

		assertEquals(3, base64InputStream.decodeUnit(outputBuffer, position));
		assertEquals(2, base64InputStream.decodeUnit(outputBuffer, position));
		assertEquals(1, base64InputStream.decodeUnit(outputBuffer, position));
		assertEquals(-1, base64InputStream.decodeUnit(outputBuffer, position));
		assertEquals(-1, base64InputStream.decodeUnit(outputBuffer, position));
		assertEquals(-1, base64InputStream.decodeUnit(outputBuffer, position));

		base64InputStream.close();

		testFile.delete();
	}

	public void testGetByte() {
		try {
			File testFile = new File(_TEST_FILE_NAME);

			FileOutputStream fileOutputStream = new FileOutputStream(testFile);

			fileOutputStream.write('a');

			fileOutputStream.close();

			Base64InputStream base64InputStream = new Base64InputStream(
				new FileInputStream(testFile));

			assertEquals(0, base64InputStream.getByte('A'));
			assertEquals(0, base64InputStream.getByte('='));
			assertEquals(-1, base64InputStream.getByte('\n'));
			assertEquals(62, base64InputStream.getByte('+'));

			testFile.delete();
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testGetEncodedByte() throws Exception {
		try {
			File testFile = new File(_TEST_FILE_NAME);

			FileOutputStream fileOutputStream = new FileOutputStream(testFile);

			fileOutputStream.write('A');
			fileOutputStream.write('=');
			fileOutputStream.write('B');
			fileOutputStream.write('\n');

			Base64InputStream base64InputStream = new Base64InputStream(
				new FileInputStream(testFile));

			assertEquals(0, base64InputStream.getEncodedByte());
			assertEquals(-2, base64InputStream.getEncodedByte());
			assertEquals(1, base64InputStream.getEncodedByte());
			assertEquals(-1, base64InputStream.getEncodedByte());

			testFile.delete();
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testRead_0args() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		FileOutputStream fileOutputStream = new FileOutputStream(testFile);

		byte[] bytes = {'a', 'b', 'c', 'd'};

		fileOutputStream.write(bytes);

		fileOutputStream.close();

		Base64InputStream base64InputStream = new Base64InputStream(
			new FileInputStream(testFile));

		assertEquals(105, base64InputStream.read());

		base64InputStream.read();
		base64InputStream.read();

		assertEquals(-1, base64InputStream.read());

		base64InputStream.close();

		testFile.delete();
	}

	public void testRead_3args() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		FileOutputStream fileOutputStream = new FileOutputStream(testFile);

		byte[] bytes = {
			'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'e', 'f', 'g', '='};

		fileOutputStream.write(bytes);

		fileOutputStream.close();

		Base64InputStream base64InputStream = new Base64InputStream(
			new FileInputStream(testFile));

		byte[] buffer = new byte[5];
		int offset = 0;

		assertEquals(1, base64InputStream.read(buffer, offset, 1));
		assertEquals(2, base64InputStream.read(buffer, offset, 2));
		assertEquals(5, base64InputStream.read(buffer, offset, 6));
		assertEquals(-1, base64InputStream.read(buffer, offset, 3));
		assertEquals(-1, base64InputStream.read(buffer, offset, 1));
		assertEquals(-1, base64InputStream.read(buffer, offset, 0));

		base64InputStream.close();

		testFile.delete();
	}

	public void testSkip() throws Exception {
		File testFile = new File(_TEST_FILE_NAME);

		FileOutputStream fileOutputStream = new FileOutputStream(testFile);

		byte[] bytes = {'a', 'b', 'c', 'd'};

		fileOutputStream.write(bytes);

		fileOutputStream.close();

		Base64InputStream base64InputStream = new Base64InputStream(
			new FileInputStream(testFile));

		assertEquals(3L, base64InputStream.skip(4L));

		base64InputStream.close();

		testFile.delete();
	}

	private static final String _TEST_FILE_NAME =
		"Base64InputStreamTest.testFilename";

}