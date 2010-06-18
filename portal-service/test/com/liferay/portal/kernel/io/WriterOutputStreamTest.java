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
import com.liferay.portal.kernel.util.StringPool;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * <a href="WriterOutputStreamTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class WriterOutputStreamTest extends TestCase {

	public void testACSIIOutput() throws IOException {

		// autoFlush = true;
		StringWriter stringWriter = new StringWriter();
		WriterOutputStream writerOutputStream = new WriterOutputStream(
			stringWriter, StringPool.UTF8, true);

		String expectedResult = _testACSIIOutput[0];
		writerOutputStream.write(_testACSIIInput[0]);
		assertEquals(expectedResult, stringWriter.toString());

		expectedResult += _testACSIIOutput[1];
		writerOutputStream.write(_testACSIIInput[1]);
		assertEquals(expectedResult, stringWriter.toString());

		expectedResult += _testACSIIOutput[2];
		writerOutputStream.write(_testACSIIInput[2]);
		assertEquals(expectedResult, stringWriter.toString());

		expectedResult += _testACSIIOutput[3];
		writerOutputStream.write(_testACSIIInput[3]);
		assertEquals(expectedResult, stringWriter.toString());

		expectedResult += _testACSIIOutput[4];
		writerOutputStream.write(_testACSIIInput[4]);
		assertEquals(expectedResult, stringWriter.toString());

		// autoFlush = false;
		stringWriter = new StringWriter();
		writerOutputStream = new WriterOutputStream(stringWriter,
			StringPool.UTF8, false);

		writerOutputStream.write(_testACSIIInput[0]);
		assertEquals("", stringWriter.toString());

		writerOutputStream.write(_testACSIIInput[1]);
		assertEquals("", stringWriter.toString());

		writerOutputStream.write(_testACSIIInput[2]);
		assertEquals("", stringWriter.toString());

		writerOutputStream.write(_testACSIIInput[3]);
		assertEquals("", stringWriter.toString());

		writerOutputStream.write(_testACSIIInput[4]);
		assertEquals("", stringWriter.toString());

		writerOutputStream.flush();
		assertEquals(expectedResult, stringWriter.toString());
	}

	public void testChineseOutput() throws IOException {

		// autoFlush = true;
		StringWriter stringWriter = new StringWriter();
		WriterOutputStream writerOutputStream = new WriterOutputStream(
			stringWriter, StringPool.UTF8, true);

		String expectedResult = _testChineseOutput[0];
		writerOutputStream.write(_testChineseInput[0]);
		assertEquals(expectedResult, stringWriter.toString());

		expectedResult += _testChineseOutput[1];
		writerOutputStream.write(_testChineseInput[1]);
		assertEquals(expectedResult, stringWriter.toString());

		expectedResult += _testChineseOutput[2];
		writerOutputStream.write(_testChineseInput[2]);
		assertEquals(expectedResult, stringWriter.toString());

		expectedResult += _testChineseOutput[3];
		writerOutputStream.write(_testChineseInput[3]);
		assertEquals(expectedResult, stringWriter.toString());

		expectedResult += _testChineseOutput[4];
		writerOutputStream.write(_testChineseInput[4]);
		assertEquals(expectedResult, stringWriter.toString());

		// autoFlush = false;
		stringWriter = new StringWriter();
		writerOutputStream = new WriterOutputStream(stringWriter,
			StringPool.UTF8, false);

		writerOutputStream.write(_testChineseInput[0]);
		assertEquals("", stringWriter.toString());

		writerOutputStream.write(_testChineseInput[1]);
		assertEquals("", stringWriter.toString());

		writerOutputStream.write(_testChineseInput[2]);
		assertEquals("", stringWriter.toString());

		writerOutputStream.write(_testChineseInput[3]);
		assertEquals("", stringWriter.toString());

		writerOutputStream.write(_testChineseInput[4]);
		assertEquals("", stringWriter.toString());

		writerOutputStream.flush();
		assertEquals(expectedResult, stringWriter.toString());
	}

	public void testNoneAlignOutput() throws IOException {
		CharArrayWriter charArrayWriter = new CharArrayWriter();
		WriterOutputStream writerOutputStream = new WriterOutputStream(
			charArrayWriter, StringPool.UTF8, true);

		int charNumber = 0;
		for(byte b : _noneAlignInput) {
			writerOutputStream.write(b);
			int currentCharNumber = charArrayWriter.size();
			if (currentCharNumber > charNumber) {
				charNumber = currentCharNumber;
				assertEquals(_noneAlignOutput.charAt(charNumber - 1),
					charArrayWriter.toCharArray()[charNumber - 1]);
			}
		}
	}

	private static final String[] _testACSIIOutput =
		{"This ", "is ", "a ", "ACSII ", " test"};

	private static final byte[][] _testACSIIInput =
		{
			_testACSIIOutput[0].getBytes(),
			_testACSIIOutput[1].getBytes(),
			_testACSIIOutput[2].getBytes(),
			_testACSIIOutput[3].getBytes(),
			_testACSIIOutput[4].getBytes()
		};

	private static final String[] _testChineseOutput =
		{"这是", "一个", "中文", "解码 ", "测试"};

	private static final byte[][] _testChineseInput =
		{
			_testChineseOutput[0].getBytes(),
			_testChineseOutput[1].getBytes(),
			_testChineseOutput[2].getBytes(),
			_testChineseOutput[3].getBytes(),
			_testChineseOutput[4].getBytes()
		};

	private static final String _noneAlignOutput = "非对齐测试中文输出";

	private static final byte[] _noneAlignInput = _noneAlignOutput.getBytes();

}