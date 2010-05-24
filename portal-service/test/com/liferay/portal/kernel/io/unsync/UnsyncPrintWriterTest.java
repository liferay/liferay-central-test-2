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

package com.liferay.portal.kernel.io.unsync;

import com.liferay.portal.kernel.test.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import java.lang.reflect.Field;

/**
 * <a href="UnsyncPrintWriterTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class UnsyncPrintWriterTest extends TestCase {

	protected void tearDown() throws Exception {
		super.tearDown();
		new File(_testFileName).delete();
	}

	public void testConstructor() throws IOException {

		// public UnsyncPrintWriter(Writer out)
		StringWriter sw = new StringWriter();
		UnsyncPrintWriter writer = new UnsyncPrintWriter(sw);
		assertEquals(sw, _getOut(writer));
		assertFalse(_isAutoFlush(writer));

		// public UnsyncPrintWriter(Writer out, boolean autoFlush)
		writer = new UnsyncPrintWriter(sw, false);
		assertEquals(sw, _getOut(writer));
		assertFalse(_isAutoFlush(writer));
		writer = new UnsyncPrintWriter(sw, true);
		assertEquals(sw, _getOut(writer));
		assertTrue(_isAutoFlush(writer));

		// public UnsyncPrintWriter(OutputStream out)
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		writer = new UnsyncPrintWriter(baos);
		assertTrue(_getOut(writer) instanceof OutputStreamWriter);
		assertFalse(_isAutoFlush(writer));

		// public UnsyncPrintWriter(OutputStream out, boolean autoFlush)
		writer = new UnsyncPrintWriter(baos, false);
		assertTrue(_getOut(writer) instanceof OutputStreamWriter);
		assertFalse(_isAutoFlush(writer));
		writer = new UnsyncPrintWriter(baos, true);
		assertTrue(_getOut(writer) instanceof OutputStreamWriter);
		assertTrue(_isAutoFlush(writer));

		// public UnsyncPrintWriter(String fileName)
		writer = new UnsyncPrintWriter(_testFileName);
		assertTrue(_getOut(writer) instanceof FileWriter);
		assertFalse(_isAutoFlush(writer));

		// public UnsyncPrintWriter(String fileName, String csn)
		writer = new UnsyncPrintWriter(_testFileName, "UTF8");
		assertTrue(_getOut(writer) instanceof OutputStreamWriter);
		assertFalse(_isAutoFlush(writer));

		OutputStreamWriter osw = (OutputStreamWriter) _getOut(writer);
		assertEquals("UTF8", osw.getEncoding());

		// public UnsyncPrintWriter(File file)
		writer = new UnsyncPrintWriter(new File(_testFileName));
		assertTrue(_getOut(writer) instanceof FileWriter);
		assertFalse(_isAutoFlush(writer));

		// public UnsyncPrintWriter(File file, String csn)
		writer = new UnsyncPrintWriter(new File(_testFileName), "UTF8");
		assertTrue(_getOut(writer) instanceof OutputStreamWriter);
		assertFalse(_isAutoFlush(writer));

		osw = (OutputStreamWriter) _getOut(writer);
		assertEquals("UTF8", osw.getEncoding());
	}

	public void testFormat() {
		StringWriter sw = new StringWriter();
		UnsyncPrintWriter writer = new UnsyncPrintWriter(sw);
		writer.format("%2$2d %1$2s", "a", 1);
		assertEquals(" 1  a", sw.toString());
	}

	public void testPrintln() {
		StringWriter sw = new StringWriter();
		UnsyncPrintWriter writer = new UnsyncPrintWriter(sw);

		writer.println();

		String lineSeparator = System.getProperty("line.separator");

		assertEquals(lineSeparator, sw.toString());
	}

	public void testWrite() {
		StringWriter sw = new StringWriter();
		UnsyncPrintWriter writer = new UnsyncPrintWriter(sw);

		// public void write(int c)
		writer.write('a');
		assertEquals("a", sw.toString());
		writer.write('b');
		assertEquals("ab", sw.toString());

		// public void write(char[] buf)
		writer.write(new char[]{'c', 'd'});
		assertEquals("abcd", sw.toString());
		writer.write(new char[]{'e', 'f'});
		assertEquals("abcdef", sw.toString());

		// public void write(char[] buf, int off, int len)
		writer.write(new char[]{'e', 'f', 'g', 'h', 'i', 'j'}, 2, 2);
		assertEquals("abcdefgh", sw.toString());
		writer.write(new char[]{'g', 'h', 'i', 'j', 'k', 'l'}, 2, 2);
		assertEquals("abcdefghij", sw.toString());

		// public void write(String s)
		writer.write("kl");
		assertEquals("abcdefghijkl", sw.toString());
		writer.write("mn");
		assertEquals("abcdefghijklmn", sw.toString());

		// public void write(String s, int off, int len)
		writer.write("mnopqr", 2, 2);
		assertEquals("abcdefghijklmnop", sw.toString());
		writer.write("opqrst", 2, 2);
		assertEquals("abcdefghijklmnopqr", sw.toString());
	}

	private static Writer _getOut(UnsyncPrintWriter writer) {
		try {
			return (Writer) _OUT.get(writer);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static boolean _isAutoFlush(UnsyncPrintWriter writer) {
		try {
			return _AUTO_FLUSH.getBoolean(writer);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static Field _AUTO_FLUSH;

	private static Field _OUT;

	static {
		try {
			_AUTO_FLUSH = UnsyncPrintWriter.class.getDeclaredField(
				"_autoFlush");
			_AUTO_FLUSH.setAccessible(true);
			_OUT = UnsyncPrintWriter.class.getDeclaredField("_out");
			_OUT.setAccessible(true);
		} catch (Throwable t) {
			throw new ExceptionInInitializerError(t);
		}
	}

	private String _testFileName = "testFileName";

}