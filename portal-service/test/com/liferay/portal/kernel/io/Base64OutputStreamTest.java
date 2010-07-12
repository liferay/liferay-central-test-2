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
 * <a href="Base64OutputStreamTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Tina Tian
 */
public class Base64OutputStreamTest extends TestCase {

	public void testClose() throws Exception {
		File testFile = new File(_filePath);

		Base64OutputStream instance =
			new Base64OutputStream(new FileOutputStream(testFile));
		instance.write('A');
		instance.close();

		FileInputStream fin = new FileInputStream(testFile);
		byte[] b = new byte[4];
		fin.read(b);
		fin.close();

		if ((b[3] != '=') || (b[2] != '=')) {
			fail("test close() failed");
		}

		testFile.delete();
	}

	public void testFlush() throws Exception {
		File testFile = new File(_filePath);

		Base64OutputStream instance =
			new Base64OutputStream(new FileOutputStream(testFile));
		instance.write('A');
		instance.flush();

		FileInputStream fin = new FileInputStream(testFile);
		byte[] b = new byte[4];
		int result = fin.read(b);
		fin.close();

		assertEquals(4, result);

		testFile.delete();

		testFile = new File(_filePath);

		instance = new Base64OutputStream(new FileOutputStream(testFile));
		instance.write('A');
		instance.write('B');
		instance.flush();

		fin = new FileInputStream(testFile);
		result = fin.read(b);
		fin.close();
		assertEquals(4, result);

		testFile.delete();
	}

	public void testWrite_byteArr() throws Exception {
		File testFile = new File(_filePath);

		byte[] b = {'a', 'b', 'c'};
		Base64OutputStream instance =
			new Base64OutputStream(new FileOutputStream(testFile));
		instance.write(b);
		instance.close();

		FileInputStream fin = new FileInputStream(testFile);
		byte[] bytes = new byte[4];
		int result = fin.read(bytes);
		fin.close();

		assertEquals(4, result);

		testFile.delete();
	}

	public void testWrite_int() throws Exception {
		File testFile = new File(_filePath);

		Base64OutputStream instance =
			new Base64OutputStream(new FileOutputStream(testFile));
		instance.write('A');
		instance.write('A');
		instance.write('A');
		instance.close();

		FileInputStream fin = new FileInputStream(testFile);
		byte[] bytes = new byte[4];
		int result = fin.read(bytes);
		fin.close();

		assertEquals(4, result);

		testFile.delete();
	}

	public void testWrite_3args() throws Exception {
		File testFile = new File(_filePath);

		byte[] b = {'a', 'b', 'c', 'a', 'b', 'c'};
		int off = 0;
		int len = 1;
		Base64OutputStream instance =
			new Base64OutputStream(new FileOutputStream(testFile));
		instance.write(b, off, len);

		len = 2;
		off = 1;
		instance.write(b, off, len);

		len = 3;
		off = 3;
		instance.write(b, off, len);

		instance.close();

		FileInputStream fin = new FileInputStream(testFile);
		byte [] bytes = new byte[8];
		int result = fin.read(bytes);
		fin.close();

		assertEquals(8, result);

		testFile.delete();
	}

	public void testEncodeUnit_byte() throws Exception {
		File testFile = new File(_filePath);

		Base64OutputStream instance =
			new Base64OutputStream(new FileOutputStream(testFile));
		instance.encodeUnit((byte)'a');
		instance.close();

		FileInputStream fin = new FileInputStream(testFile);
		byte [] b = new byte[4];
		int result = fin.read(b);
		fin.close();

		assertEquals(4, result);

		testFile.delete();

	}

	public void testEncodeUnit_byte_byte() throws Exception {
		File testFile = new File(_filePath);

		byte b1 = 'a';
		byte b2 = 'b';
		Base64OutputStream instance =
			new Base64OutputStream(new FileOutputStream(testFile));
		instance.encodeUnit(b1, b2);
		instance.close();

		FileInputStream fin = new FileInputStream(testFile);
		byte [] b = new byte[4];
		int result = fin.read(b);
		fin.close();

		assertEquals(4, result);

		testFile.delete();
	}

	public void testEncodeUnit_3args() throws Exception {
		File testFile = new File(_filePath);

		byte b1 = 'a';
		byte b2 = 'b';
		byte b3 = 'c';
		Base64OutputStream instance =
			new Base64OutputStream(new FileOutputStream(testFile));
		instance.encodeUnit(b1, b2, b3);
		instance.close();

		FileInputStream fin = new FileInputStream(testFile);
		byte [] b = new byte[4];
		int result = fin.read(b);
		fin.close();

		assertEquals(4, result);

		testFile.delete();
	}

	public void testGetChar() {
		try {
			File testFile = new File(_filePath);

			int sixbit = 0;
			Base64OutputStream instance =
				new Base64OutputStream(new FileOutputStream(testFile));

			char expResult = 'A';
			char result = instance.getChar(sixbit);
			assertEquals(expResult, result);

			sixbit = 64;
			expResult = '?';
			result = instance.getChar(sixbit);
			assertEquals(expResult, result);

			testFile.delete();
		}
		catch (Exception e) {
			fail("Test getChar failed");
		}

	}

	private static String _filePath =
		System.getProperty("user.dir") + File.separator + "test.txt";

}