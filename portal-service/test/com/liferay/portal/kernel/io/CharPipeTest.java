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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.test.TestCase;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.nio.CharBuffer;

/**
 * @author Shuyang Zhou
 */
public class CharPipeTest extends TestCase {

	public void testCloseForce() {
		CharPipe charPipe = new CharPipe();

		assertFalse(charPipe.finished);

		charPipe.close(true);

		assertNull(charPipe.buffer);
		assertFalse(charPipe.finished);

		try {
			charPipe.getReader().read();
			fail();
		}
		catch (IOException ioe) {
		}

		try {
			charPipe.getReader().read(new char[1]);
			fail();
		}
		catch (IOException ioe) {
		}

		try {
			charPipe.getReader().read(CharBuffer.allocate(1));
			fail();
		}
		catch (IOException ioe) {
		}

		try {
			charPipe.getReader().ready();
			fail();
		}
		catch (IOException ioe) {
		}

		try {
			charPipe.getReader().skip(1);
			fail();
		}
		catch (IOException ioe) {
		}

		try {
			charPipe.getWriter().append('a');
			fail();
		}
		catch (IOException ioe) {
		}

		try {
			charPipe.getWriter().append("a");
			fail();
		}
		catch (IOException ioe) {
		}

		try {
			charPipe.getWriter().write("abc".toCharArray());
			fail();
		}
		catch (IOException ioe) {
		}

		try {
			charPipe.getWriter().write((int)'a');
			fail();
		}
		catch (IOException ioe) {
		}

		try {
			charPipe.getWriter().write("a");
			fail();
		}
		catch (IOException ioe) {
		}
	}

	public void testClosePeacefullyBlocking() throws Exception {
		final CharPipe charPipe = new CharPipe();

		Reader reader = charPipe.getReader();

		Thread closeThread =  new Thread() {
			public void run() {
				try {
					Thread.sleep(100);
					charPipe.close();
				}
				catch (Exception e) {
					fail(e.getMessage());
				}
			}
		};

		long startTime = System.currentTimeMillis();

		closeThread.start();

		int result = reader.read();

		closeThread.join();

		long duration = System.currentTimeMillis() - startTime;

		assertEquals(-1, result);

		assertTrue(duration > 100);
	}

	public void testClosePeacefullyEmpty() throws IOException {
		CharPipe charPipe = new CharPipe();

		assertFalse(charPipe.finished);

		charPipe.close();

		assertNotNull(charPipe.buffer);
		assertTrue(charPipe.finished);

		assertEquals(-1, charPipe.getReader().read());
	}

	public void testClosePeacefullyNotEmpty() throws IOException {
		CharPipe charPipe = new CharPipe();

		charPipe.getWriter().write("abcd");

		assertFalse(charPipe.finished);

		charPipe.close();

		assertNotNull(charPipe.buffer);
		assertTrue(charPipe.finished);

		char[] readBuffer = new char[5];

		Reader reader = charPipe.getReader();

		int result = reader.read(readBuffer);

		assertEquals(4, result);
		assertEquals('a', readBuffer[0]);
		assertEquals('b', readBuffer[1]);
		assertEquals('c', readBuffer[2]);
		assertEquals('d', readBuffer[3]);
		assertEquals(0, readBuffer[4]);

		assertEquals(-1, reader.read());

	}

	public void testConstructor() {
		CharPipe charPipe = new CharPipe();

		assertNotNull(charPipe.buffer);
		assertEquals(8192, charPipe.buffer.length);
		assertEquals(0, charPipe.count);
		assertEquals(0, charPipe.readIndex);
		assertEquals(0, charPipe.writeIndex);
		assertNotNull(charPipe.bufferLock);
		assertNotNull(charPipe.notEmpty);
		assertNotNull(charPipe.notFull);

		charPipe = new CharPipe(1024);

		assertNotNull(charPipe.buffer);
		assertEquals(1024, charPipe.buffer.length);
		assertEquals(0, charPipe.count);
		assertEquals(0, charPipe.readIndex);
		assertEquals(0, charPipe.writeIndex);
		assertNotNull(charPipe.bufferLock);
		assertNotNull(charPipe.notEmpty);
		assertNotNull(charPipe.notFull);

		charPipe.close();
	}

	public void testGetReader() {
		CharPipe charPipe = new CharPipe();

		Reader reader1 = charPipe.getReader();
		Reader reader2 = charPipe.getReader();

		assertSame(reader1, reader2);

		assertFalse(reader1.markSupported());

		try {
			reader1.mark(1);
			fail();
		}
		catch (IOException ioe) {
		}

		try {
			reader1.reset();
			fail();
		}
		catch (IOException ioe) {
		}

		charPipe.close();
	}

	public void testGetWriter() throws IOException {
		CharPipe charPipe = new CharPipe();

		Writer writer1 = charPipe.getWriter();
		Writer writer2 = charPipe.getWriter();

		assertSame(writer1, writer2);

		writer1.flush();

		charPipe.close();
	}

	public void testPipingChar() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		assertFalse(reader.ready());

		writer.write('a');

		assertTrue(reader.ready());

		assertEquals('a', reader.read());

		assertFalse(reader.ready());

		writer.append('b');

		assertTrue(reader.ready());

		assertEquals('b', reader.read());

		assertFalse(reader.ready());

		charPipe.close();
	}

	public void testPipingCharArray() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		char[] data = "abcd".toCharArray();
		char[] readBuffer = new char[4];

		writer.write(data);
		int result = reader.read(readBuffer);
		assertEquals(4, result);
		assertTrue(Arrays.equals(data, readBuffer));

		writer.append(new String(data));
		result = reader.read(readBuffer);
		assertEquals(4, result);
		assertTrue(Arrays.equals(data, readBuffer));

		charPipe.close();
	}

	public void testPipingCharArrayWithOffset() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		char[] data = "abcd".toCharArray();
		char[] readBuffer = new char[4];

		writer.write(data, 0, 0);
		assertFalse(reader.ready());

		writer.write(data, 1, 2);
		int result = reader.read(readBuffer, 1, 0);
		assertEquals(0, result);
		result = reader.read(readBuffer, 1, 3);
		assertEquals(2, result);
		assertEquals('b', readBuffer[1]);
		assertEquals('c', readBuffer[2]);

		writer.append(new String(data), 1, 3);
		result = reader.read(readBuffer, 1, 0);
		assertEquals(0, result);
		result = reader.read(readBuffer, 1, 3);
		assertEquals(2, result);
		assertEquals('b', readBuffer[1]);
		assertEquals('c', readBuffer[2]);

		charPipe.close();
	}

	public void testPipingCharArrayWithOffsetTwoSteps() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		char[] data = "abcd".toCharArray();
		char[] readBuffer = new char[4];

		writer.write(data);
		int result = reader.read(readBuffer, 0, 3);
		assertEquals(3, result);
		assertEquals('a', readBuffer[0]);
		assertEquals('b', readBuffer[1]);
		assertEquals('c', readBuffer[2]);

		writer.write(data, 0, 3);
		result = reader.read(readBuffer);
		assertEquals(4, result);
		assertEquals('d', readBuffer[0]);
		assertEquals('a', readBuffer[1]);
		assertEquals('b', readBuffer[2]);
		assertEquals('c', readBuffer[3]);

		writer.write(data);
		result = reader.read(readBuffer);
		assertEquals(4, result);
		assertEquals('a', readBuffer[0]);
		assertEquals('b', readBuffer[1]);
		assertEquals('c', readBuffer[2]);
		assertEquals('d', readBuffer[3]);

		charPipe.close();
	}

	public void testPipingCharBuffer() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		String string = "abcd";

		writer.write(string);

		CharBuffer charBuffer = CharBuffer.allocate(0);

		int result = reader.read(charBuffer);
		assertEquals(0, result);

		charBuffer = CharBuffer.allocate(2);

		result = reader.read(charBuffer);
		assertEquals(2, result);

		charBuffer.flip();

		assertEquals('a', charBuffer.get());
		assertEquals('b', charBuffer.get());

		charPipe.close();
	}

	public void testPipingString() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		String string = "abcd";
		char[] readBuffer = new char[4];

		writer.write(string);
		int result = reader.read(readBuffer);
		assertEquals(4, result);
		assertEquals(string, new String(readBuffer));

		charPipe.close();
	}

	public void testPipingStringWithOffset() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		String string = "abcd";
		char[] readBuffer = new char[4];

		writer.write(string, 0, 0);
		assertFalse(reader.ready());

		writer.write(string, 1, 3);
		int result = reader.read(readBuffer, 1, 0);
		assertEquals(0, result);
		result = reader.read(readBuffer, 1, 3);
		assertEquals(3, result);
		assertEquals('b', readBuffer[1]);
		assertEquals('c', readBuffer[2]);
		assertEquals('d', readBuffer[3]);

		charPipe.close();
	}

	public void testPipingStringWithOffsetTwoSteps() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		String string = "abcd";
		char[] readBuffer = new char[4];

		writer.write(string);
		int result = reader.read(readBuffer, 0, 3);
		assertEquals(3, result);
		assertEquals('a', readBuffer[0]);
		assertEquals('b', readBuffer[1]);
		assertEquals('c', readBuffer[2]);

		writer.write(string, 0, 3);
		result = reader.read(readBuffer);
		assertEquals(4, result);
		assertEquals('d', readBuffer[0]);
		assertEquals('a', readBuffer[1]);
		assertEquals('b', readBuffer[2]);
		assertEquals('c', readBuffer[3]);

		writer.write(string);
		result = reader.read(readBuffer);
		assertEquals(4, result);
		assertEquals('a', readBuffer[0]);
		assertEquals('b', readBuffer[1]);
		assertEquals('c', readBuffer[2]);
		assertEquals('d', readBuffer[3]);

		charPipe.close();
	}

	public void testSkip() throws Exception {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		try {
			reader.skip(-1);
			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		SlowWriterJob slowWriterJob = new SlowWriterJob(writer, 4, false);

		Thread writerThread = new Thread(slowWriterJob);

		writerThread.start();

		for (int i = 0; i < 10; i++) {
			assertTrue(timedSkip(reader, 2) > 50);
			assertTrue(timedSkip(reader, 2) < 50);
		}

		charPipe.close();
		writerThread.join();

		assertFalse(slowWriterJob.isFailed());
	}

	public void testSlowReader() throws Exception {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		SlowReaderJob slowReaderJob = new SlowReaderJob(reader, 4, false,
			false);

		Thread readerThread = new Thread(slowReaderJob);
		assertTrue(timedWrite(writer, "abcd") < 100);

		readerThread.start();

		for (int i = 0; i < 5; i++) {
			if ((i % 2) == 0) {
				assertTrue(timedWrite(writer, "abcdefgh") > 100);
			}
			else {
				assertTrue(timedWrite(writer, "abcdefgh".toCharArray()) > 100);
			}
		}

		charPipe.close();
		readerThread.join();

		assertFalse(slowReaderJob.isFailed());
	}

	public void testSlowReaderOnCloseForce() throws Exception {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		SlowReaderJob slowReaderJob = new SlowReaderJob(reader, 4, true, true);

		Thread readerThread = new Thread(slowReaderJob);
		assertTrue(timedWrite(writer, "abcd") < 100);

		readerThread.start();

		for (int i = 0; i < 2; i++) {
			assertTrue(timedWrite(writer, "abcdefgh") > 100);
		}

		charPipe.close(true);
		readerThread.join();

		assertFalse(slowReaderJob.isFailed());
	}

	public void testSlowReaderOnClosePeacefully() throws Exception {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		SlowReaderJob slowReaderJob = new SlowReaderJob(reader, 4, true, false);

		Thread readerThread = new Thread(slowReaderJob);
		assertTrue(timedWrite(writer, "abcd") < 100);

		readerThread.start();

		for (int i = 0; i < 2; i++) {
			assertTrue(timedWrite(writer, "abcdefgh") > 100);
		}

		charPipe.close();
		readerThread.join();

		assertFalse(slowReaderJob.isFailed());
	}

	public void testSlowWriter() throws Exception {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		char[] readBuffer = new char[8];

		SlowWriterJob slowWriterJob = new SlowWriterJob(writer, 4, false);

		Thread writerThread = new Thread(slowWriterJob);

		writerThread.start();

		for (int i = 0; i < 10; i++) {
			assertTrue(timedRead(reader, readBuffer) > 50);
		}

		charPipe.close();
		writerThread.join();

		assertFalse(slowWriterJob.isFailed());
	}

	public void testSlowWriterOnClose() throws Exception {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		char[] readBuffer = new char[8];

		SlowWriterJob slowWriterJob = new SlowWriterJob(writer, 4, true);

		Thread writerThread = new Thread(slowWriterJob);

		writerThread.start();

		for (int i = 0; i < 5; i++) {
			assertTrue(timedRead(reader, readBuffer) > 50);
		}

		charPipe.close();
		writerThread.join();

		assertFalse(slowWriterJob.isFailed());
	}

	private long timedRead(Reader reader, char[] readBuffer)
		throws IOException {
		long startTime = System.currentTimeMillis();
		reader.read(readBuffer);
		return System.currentTimeMillis() - startTime;
	}

	private long timedSkip(Reader reader, int skipSize)
		throws IOException {
		long startTime = System.currentTimeMillis();
		reader.skip(skipSize);
		return System.currentTimeMillis() - startTime;
	}

	private long timedWrite(Writer writer, char[] data) throws IOException {
		long startTime = System.currentTimeMillis();
		writer.write(data);
		return System.currentTimeMillis() - startTime;
	}

	private long timedWrite(Writer writer, String data) throws IOException {
		long startTime = System.currentTimeMillis();
		writer.write(data);
		return System.currentTimeMillis() - startTime;
	}

	private class SlowReaderJob implements Runnable {

		public SlowReaderJob(
			Reader reader, int bufferSize, boolean close, boolean force) {
			_close = close;
			_force = force;
			_readBuffer = new char[bufferSize];
			_reader = reader;
		}

		public boolean isFailed() {
			return _failed;
		}

		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					Thread.sleep(100);

					int result = _reader.read(_readBuffer);

					if (result == _readBuffer.length) {
						continue;
					}
					else if (_close && !_force && (result == -1)) {
						return;
					}
					else {
						_failed = true;
						break;
					}
				}

				if (_close && _force) {
					_failed = true;
				}
			}
			catch (Exception e) {
				if (!_close) {
					_failed = true;
				}
			}
		}

		private final boolean _close;
		private final boolean _force;
		private boolean _failed;
		private final char[] _readBuffer;
		private final Reader _reader;

	}

	private class SlowWriterJob implements Runnable {

		public SlowWriterJob(
			Writer writer, int dataSize, boolean expectException) {
			_dataSize = dataSize;
			_expectException = expectException;
			_writer = writer;
		}

		public boolean isFailed() {
			return _failed;
		}

		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					Thread.sleep(100);

					_writer.write(new char[_dataSize]);
				}

				if (_expectException) {
					_failed = true;
				}
			}
			catch (Exception e) {
				if (!_expectException) {
					_failed = true;
				}
			}
		}

		private final int _dataSize;
		private final boolean _expectException;
		private boolean _failed;
		private final Writer _writer;

	}

}