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
public class Base64InputStreamTest extends TestCase {

	public void testAvailable() throws Exception {
		File testFile = new File(_testFilePath);
		FileOutputStream fileOutputStream = new FileOutputStream(testFile);

		byte[] bytes = {CharPool.LOWER_CASE_A, CharPool.LOWER_CASE_B,
			CharPool.LOWER_CASE_C, CharPool.LOWER_CASE_D};

		fileOutputStream.write(bytes);

		fileOutputStream.close();

		Base64InputStream base64InputStream = new Base64InputStream(
			new FileInputStream(testFile));

		int returnValue = base64InputStream.available();

		assertEquals(3, returnValue);

		base64InputStream.close();

		testFile.delete();
	}

	public void testRead_0args() throws Exception {
		File testFile = new File(_testFilePath);
		FileOutputStream fileOutputStream = new FileOutputStream(testFile);

		byte[] bytes = {CharPool.LOWER_CASE_A, CharPool.LOWER_CASE_B,
			CharPool.LOWER_CASE_C, CharPool.LOWER_CASE_D};

		fileOutputStream.write(bytes);

		fileOutputStream.close();

		Base64InputStream base64InputStream = new Base64InputStream(
			new FileInputStream(testFile));

		int returnValue = base64InputStream.read();

		assertEquals(105, returnValue);

		base64InputStream.read();
		base64InputStream.read();

		returnValue = base64InputStream.read();

		assertEquals(-1, returnValue);

		base64InputStream.close();

		testFile.delete();
	}

	public void testRead_3args() throws Exception {
		File testFile = new File(_testFilePath);

		byte[] buffer = new byte[5];
		int offset = 0;
		int length = 1;

		FileOutputStream fileOutputStream = new FileOutputStream(testFile);

		byte[] bytes = {CharPool.LOWER_CASE_A, CharPool.LOWER_CASE_B,
			CharPool.LOWER_CASE_C, CharPool.LOWER_CASE_D, CharPool.LOWER_CASE_A,
			CharPool.LOWER_CASE_B, CharPool.LOWER_CASE_C, CharPool.LOWER_CASE_D,
			CharPool.LOWER_CASE_E, CharPool.LOWER_CASE_F, CharPool.LOWER_CASE_G,
			CharPool.EQUAL};

		fileOutputStream.write(bytes);

		fileOutputStream.close();

		Base64InputStream base64InputStream = new Base64InputStream(
			new FileInputStream(testFile));

		int returnValue = base64InputStream.read(buffer, offset, length);

		assertEquals(1, returnValue);

		length = 2;

		returnValue = base64InputStream.read(buffer, offset, length);

		assertEquals(2, returnValue);

		length = 6;

		returnValue = base64InputStream.read(buffer, offset, length);

		assertEquals(5, returnValue);

		length = 3;

		returnValue = base64InputStream.read(buffer, offset, length);

		assertEquals(-1, returnValue);

		length = 1;

		returnValue = base64InputStream.read(buffer, offset, length);

		assertEquals(-1, returnValue);

		length = 0;

		returnValue = base64InputStream.read(buffer, offset, length);

		assertEquals(-1, returnValue);

		base64InputStream.close();

		testFile.delete();
	}

	public void testSkip() throws Exception {
		File testFile = new File(_testFilePath);
		long skip = 4L;
		FileOutputStream fileOutputStream = new FileOutputStream(testFile);

		byte[] bytes = {CharPool.LOWER_CASE_A, CharPool.LOWER_CASE_B,
			CharPool.LOWER_CASE_C, CharPool.LOWER_CASE_D};

		fileOutputStream.write(bytes);

		fileOutputStream.close();

		Base64InputStream base64InputStream = new Base64InputStream(
			new FileInputStream(testFile));

		long expResult = 3L;

		long returnValue = base64InputStream.skip(skip);

		assertEquals(expResult, returnValue);

		base64InputStream.close();

		testFile.delete();
	}

	public void testDecode() {
		try {
			File testFile = new File(_testFilePath);

			byte[] bytes = {CharPool.LOWER_CASE_A, CharPool.LOWER_CASE_B,
			CharPool.LOWER_CASE_C, CharPool.LOWER_CASE_D};

			byte[] outputBuffer = new byte[3];

			FileOutputStream fileOutputStream = new FileOutputStream(testFile);

			fileOutputStream.write(bytes);

			fileOutputStream.close();

			int position = 0;
			int padNumber = 0;
			Base64InputStream base64InputStream =
				new Base64InputStream(new FileInputStream(testFile));

			int returnValue = base64InputStream.decode(
				bytes, outputBuffer, position, padNumber);

			int expResult = 3;

			assertEquals(expResult, returnValue);

			padNumber = 1;

			returnValue = base64InputStream.decode(
				bytes, outputBuffer, position, padNumber);

			expResult = 2;

			assertEquals(expResult, returnValue);

			padNumber = 2;

			returnValue = base64InputStream.decode(
				bytes, outputBuffer, position, padNumber);

			expResult = 1;

			assertEquals(expResult, returnValue);

			padNumber = 3;

			returnValue = base64InputStream.decode(
				bytes, outputBuffer, position, padNumber);

			expResult = -1;

			assertEquals(expResult, returnValue);

			base64InputStream.close();

			testFile.delete();
		}
		catch (Exception ex) {
			fail("Test decode failed.");
		}
	}

	public void testDecodeUnit() throws Exception {
		File testFile = new File(_testFilePath);
		byte[] outputBuffer = new byte[3];
		int position = 0;

		FileOutputStream fileOutputStream = new FileOutputStream(testFile);

		byte[] bytes = {CharPool.LOWER_CASE_A, CharPool.LOWER_CASE_B,
			CharPool.LOWER_CASE_C, CharPool.LOWER_CASE_D, CharPool.LOWER_CASE_E,
			CharPool.LOWER_CASE_F, CharPool.LOWER_CASE_H, CharPool.EQUAL,
			CharPool.LOWER_CASE_E, CharPool.LOWER_CASE_F, CharPool.EQUAL,
			CharPool.EQUAL, CharPool.LOWER_CASE_E, CharPool.EQUAL,
			CharPool.LOWER_CASE_E, CharPool.LOWER_CASE_F, CharPool.EQUAL,
			CharPool.LOWER_CASE_A};

		fileOutputStream.write(bytes);

		fileOutputStream.close();

		Base64InputStream base64InputStream = new Base64InputStream(
			new FileInputStream(testFile));

		int expResult = 3;

		int returnValue = base64InputStream.decodeUnit(outputBuffer, position);

		assertEquals(expResult, returnValue);

		expResult = 2;

		returnValue = base64InputStream.decodeUnit(outputBuffer, position);

		assertEquals(expResult, returnValue);

		expResult = 1;

		returnValue = base64InputStream.decodeUnit(outputBuffer, position);

		assertEquals(expResult, returnValue);

		expResult = -1;

		returnValue = base64InputStream.decodeUnit(outputBuffer, position);

		assertEquals(expResult, returnValue);

		expResult = -1;

		returnValue = base64InputStream.decodeUnit(outputBuffer, position);

		assertEquals(expResult, returnValue);

		expResult = -1;

		returnValue = base64InputStream.decodeUnit(outputBuffer, position);

		assertEquals(expResult, returnValue);

		base64InputStream.close();

		testFile.delete();
	}

	public void testGetByte() {
		try {
			File testFile = new File(_testFilePath);
			FileOutputStream fileOutputStream = new FileOutputStream(testFile);

			fileOutputStream.write(CharPool.LOWER_CASE_A);

			fileOutputStream.close();

			Base64InputStream base64InputStream = new Base64InputStream(
				new FileInputStream(testFile));

			char character = CharPool.UPPER_CASE_A;
			int expResult = 0;

			int returnValue = base64InputStream.getByte(character);

			assertEquals(expResult, returnValue);

			character = CharPool.EQUAL;
			expResult = 0;

			returnValue = base64InputStream.getByte(character);

			assertEquals(expResult, returnValue);

			character = CharPool.NEW_LINE;
			expResult = -1;

			returnValue = base64InputStream.getByte(character);

			assertEquals(expResult, returnValue);

			character = CharPool.PLUS;
			expResult = 62;

			returnValue = base64InputStream.getByte(character);

			assertEquals(expResult, returnValue);

			testFile.delete();
		}
		catch (Exception ex) {
			fail("Test getByte failed");
		}
	}

	public void testGetEncodedByte() throws Exception {
		File testFile = new File(_testFilePath);

		Base64InputStream base64InputStream;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(testFile);

			fileOutputStream.write(CharPool.UPPER_CASE_A);
			fileOutputStream.write(CharPool.EQUAL);
			fileOutputStream.write(CharPool.UPPER_CASE_B);
			fileOutputStream.write(CharPool.NEW_LINE);

			base64InputStream = new Base64InputStream(
				new FileInputStream(testFile));

			int expResult = 0;

			int returnValue = base64InputStream.getEncodedByte();

			assertEquals(expResult, returnValue);

			expResult = -2;

			returnValue = base64InputStream.getEncodedByte();

			assertEquals(expResult, returnValue);

			expResult = 1;

			returnValue = base64InputStream.getEncodedByte();

			assertEquals(expResult, returnValue);

			expResult = -1;

			returnValue = base64InputStream.getEncodedByte();

			assertEquals(expResult, returnValue);

			testFile.delete();
		}
		catch (Exception ex) {
			fail("Test getEncodedByte failed.");
		}
	}

	private static String _testFilePath =
		System.getProperty("user.dir") + File.separator + "test.txt";

}