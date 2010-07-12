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
 * <a href="Base64InputStreamTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public class Base64InputStreamTest extends TestCase {

	public void testAvailable() throws Exception {
		File testFile = new File(_filePath);

		FileOutputStream fos = new FileOutputStream(testFile);
		byte [] b = {'a', 'b', 'c', 'd'};
		fos.write(b);
		fos.close();

		Base64InputStream instance =
			new Base64InputStream(new FileInputStream(testFile));

		int result = instance.available();

		assertEquals(3, result);

		instance.close();

		testFile.delete();
	}

	public void testRead_0args() throws Exception {
		File testFile = new File(_filePath);

		FileOutputStream fos = new FileOutputStream(testFile);
		byte [] b = {'a', 'b', 'c', 'd'};
		fos.write(b);
		fos.close();

		Base64InputStream instance =
			new Base64InputStream(new FileInputStream(testFile));

		int result = instance.read();

		assertEquals(105, result);

		instance.read();
		instance.read();

		result = instance.read();
		assertEquals(-1, result);

		instance.close();

		testFile.delete();
	}

	public void testRead_3args() throws Exception {
		File testFile = new File(_filePath);

		byte[] buf = new byte[5];
		int off = 0;
		int len = 1;

		FileOutputStream fos = new FileOutputStream(testFile);
		byte [] b =
			{'a', 'b', 'c', 'd', 'a', 'b', 'c', 'd', 'e', 'f', 'g', '='};
		fos.write(b);
		fos.close();

		Base64InputStream instance =
			new Base64InputStream(new FileInputStream(testFile));

		int result = instance.read(buf, off, len);

		assertEquals(1, result);

		len = 2;
		result = instance.read(buf, off, len);
		assertEquals(2, result);

		len = 6;
		result = instance.read(buf, off, len);
		assertEquals(5, result);

		len = 3;
		result = instance.read(buf, off, len);
		assertEquals(-1, result);

		len = 1;
		result = instance.read(buf, off, len);
		assertEquals(-1, result);

		len = 0;
		result = instance.read(buf, off, len);
		assertEquals(-1, result);

		instance.close();

		testFile.delete();
	}

	public void testSkip() throws Exception {
		File testFile = new File(_filePath);

		long n = 4L;
		FileOutputStream fos = new FileOutputStream(testFile);
		byte [] b = {'a', 'b', 'c', 'd'};
		fos.write(b);
		fos.close();

		Base64InputStream instance =
			new Base64InputStream(new FileInputStream(testFile));

		long expResult = 3L;

		long result = instance.skip(n);

		assertEquals(expResult, result);

		instance.close();

		testFile.delete();
	}

	public void testDecode() {
		try {
			File testFile = new File(_filePath);

			byte[] bytes = {'a', 'b', 'c', 'd'};
			byte[] outbuf = new byte[3];
			FileOutputStream fos = new FileOutputStream(testFile);
			fos.write(bytes);
			fos.close();

			int pos = 0;
			int padNumber = 0;
			Base64InputStream instance =
				new Base64InputStream(new FileInputStream(testFile));

			int result = instance.decode(bytes, outbuf, pos, padNumber);
			int expResult = 3;
			assertEquals(expResult, result);
			padNumber = 1;
			result = instance.decode(bytes, outbuf, pos, padNumber);
			expResult = 2;
			assertEquals(expResult, result);
			padNumber = 2;
			result = instance.decode(bytes, outbuf, pos, padNumber);
			expResult = 1;
			assertEquals(expResult, result);
			padNumber = 3;
			result = instance.decode(bytes, outbuf, pos, padNumber);
			expResult = -1;
			assertEquals(expResult, result);

			instance.close();
			
			testFile.delete();
		}
		catch (Exception ex) {
			fail("Test decode failed.");
		}
	}

	public void testDecodeUnit() throws Exception {
		File testFile = new File(_filePath);

		byte[] outbuf = new byte[3];
		int pos = 0;

		FileOutputStream fos = new FileOutputStream(testFile);
		byte [] b = {'a', 'b', 'c', 'd', 'e', 'f', 'h',
					'=', 'e', 'f', '=', '=', 'e', '=', 'e',
					'f', '=', 'a'};
		fos.write(b);
		fos.close();

		Base64InputStream instance =
			new Base64InputStream(new FileInputStream(testFile));

		int expResult = 3;
		int result = instance.decodeUnit(outbuf, pos);
		assertEquals(expResult, result);

		expResult = 2;
		result = instance.decodeUnit(outbuf, pos);
		assertEquals(expResult, result);

		expResult = 1;
		result = instance.decodeUnit(outbuf, pos);
		assertEquals(expResult, result);

		expResult = -1;
		result = instance.decodeUnit(outbuf, pos);
		assertEquals(expResult, result);

		expResult = -1;
		result = instance.decodeUnit(outbuf, pos);
		assertEquals(expResult, result);

		expResult = -1;
		result = instance.decodeUnit(outbuf, pos);
		assertEquals(expResult, result);

		instance.close();
		testFile.delete();
	}

	public void testGetByte() {
		try {
			File testFile = new File(_filePath);
			
			FileOutputStream fos = new FileOutputStream(testFile);
			fos.write((byte)'a');
			fos.close();

			Base64InputStream instance =
				new Base64InputStream(new FileInputStream(testFile));
			char c = 'A';
			int expResult = 0;
			int result = instance.getByte(c);
			assertEquals(expResult, result);

			c = '=';
			expResult = 0;
			result = instance.getByte(c);
			assertEquals(expResult, result);

			c = '\n';
			expResult = -1;
			result = instance.getByte(c);
			assertEquals(expResult, result);

			c = '+';
			expResult = 62;
			result = instance.getByte(c);
			assertEquals(expResult, result);

			testFile.delete();
		}
		catch (Exception ex) {
			fail("Test getByte failed");
		}
	}

	public void testGetEncodedByte() throws Exception {
		File testFile = new File(_filePath);

		Base64InputStream instance;
		try {
			FileOutputStream fos = new FileOutputStream(testFile);
			fos.write('A');
			fos.write('=');
			fos.write('B');
			fos.write('\n');

			instance =
				new Base64InputStream(new FileInputStream(testFile));
			int expResult = 0;
			int result = instance.getEncodedByte();
			assertEquals(expResult, result);

			expResult = -2;
			result = instance.getEncodedByte();
			assertEquals(expResult, result);

			expResult = 1;
			result = instance.getEncodedByte();
			assertEquals(expResult, result);

			expResult = -1;
			result = instance.getEncodedByte();
			assertEquals(expResult, result);

			testFile.delete();
		}
		catch (Exception ex) {
			fail("Test getEncodedByte failed.");
		}
	}

	private static String _filePath =
		System.getProperty("user.dir") + File.separator + "test.txt";

}