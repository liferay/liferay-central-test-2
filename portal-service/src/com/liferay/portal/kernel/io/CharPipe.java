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

import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.nio.CharBuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shuyang Zhou
 */
public class CharPipe {

	public CharPipe() {
		this(DEFAULT_BUFFER_SIZE);
	}

	public CharPipe(int bufferSize) {
		buffer = new char[bufferSize];
		count = 0;
		readIndex = 0;
		writeIndex = 0;
	}

	public void close() {
		close(false);
	}

	public void close(boolean force) {
		_pipeWriter.close();

		if (force) {
			_pipeReader.close();
			buffer = null;
		}
		else {
			bufferLock.lock();

			finished= true;

			try {
				notEmpty.signalAll();
			}
			finally {
				bufferLock.unlock();
			}
		}
	}

	public Reader getReader() {
		return _pipeReader;
	}

	public Writer getWriter() {
		return _pipeWriter;
	}

	public static final int DEFAULT_BUFFER_SIZE = 1024 * 8;

	protected char[] buffer;
	protected final Lock bufferLock = new ReentrantLock();
	protected int count;
	protected boolean finished;
	protected final Condition notEmpty = bufferLock.newCondition();
	protected final Condition notFull = bufferLock.newCondition();
	protected int readIndex;
	protected int writeIndex;

	private final PipeReader _pipeReader = new PipeReader();
	private final PipeWriter _pipeWriter = new PipeWriter();

	protected class PipeReader extends Reader {

		public void close() {
			bufferLock.lock();
			try {
				closed = true;
				notEmpty.signalAll();
			}
			finally {
				bufferLock.unlock();
			}
		}

		public void mark(int readAheadLimit) throws IOException {
			throw new IOException("mark() not supported");
		}

		public boolean markSupported() {
			return false;
		}

		public int read() throws IOException {
			if (closed) {
				throw new IOException("Stream closed");
			}

			bufferLock.lock();
			try {
				if (waitUntilNotEmpty()) {
					return -1;
				}

				char result = buffer[readIndex];

				increaseReadIndex(1);

				return result;
			}
			finally {
				bufferLock.unlock();
			}
		}

		public int read(char[] chars) throws IOException {
			return read(chars, 0, chars.length);
		}

		public int read(char[] chars, int offset, int length)
			throws IOException {
			if (closed) {
				throw new IOException("Stream closed");
			}
			if (length <= 0) {
				return 0;
			}

			bufferLock.lock();
			try {
				if (waitUntilNotEmpty()) {
					return -1;
				}

				int read = length;

				if (length > count) {
					read = count;
				}

				if (buffer.length - readIndex >= read) {
					// One step read
					System.arraycopy(buffer, readIndex, chars, offset, read);
				}
				else {
					// Two steps read
					int tailLength = buffer.length - readIndex;
					int headLength = read - tailLength;

					System.arraycopy(buffer, readIndex, chars, offset,
						tailLength);
					System.arraycopy(buffer, 0, chars, offset + tailLength,
						headLength);
				}

				increaseReadIndex(read);

				return read;
			}
			finally {
				bufferLock.unlock();
			}
		}

		public int read(CharBuffer charBuffer) throws IOException {
			if (closed) {
				throw new IOException("Stream closed");
			}

			int length = charBuffer.remaining();

			if (length <= 0) {
				return 0;
			}

			char[] tempBuffer = new char[length];

			int n = read(tempBuffer, 0, length);

			if (n > 0) {
				charBuffer.put(tempBuffer, 0, n);
			}

			return n;
		}

		public boolean ready() throws IOException {
			if (closed) {
				throw new IOException("Stream closed");
			}

			bufferLock.lock();
			try {
				return count > 0;
			}
			finally {
				bufferLock.unlock();
			}
		}

		public void reset() throws IOException {
			throw new IOException("reset() not supported");
		}

		public long skip(long skip) throws IOException {
			if (skip < 0) {
				throw new IllegalArgumentException("skip value is negative");
			}

			if (closed) {
				throw new IOException("Stream closed");
			}

			int skipBufferSize = (int)Math.min(skip, _MAX_SKIP_BUFFER_SIZE);

			bufferLock.lock();
			try {
				if ((_skipBuffer == null)
					|| (_skipBuffer.length < skipBufferSize)) {
					_skipBuffer = new char[skipBufferSize];
				}

				long remaining = skip;
				while (remaining > 0) {
					int skipped = read(_skipBuffer, 0,
						(int)Math.min(remaining, skipBufferSize));

					if (skipped == -1) {
						break;
					}

					remaining -= skipped;
				}

				return skip - remaining;
			}
			finally {
				bufferLock.unlock();
			}
		}

		private void increaseReadIndex(int consumed) {
			readIndex += consumed;

			if (readIndex >= buffer.length) {
				readIndex -= buffer.length;
			}

			if (count == buffer.length) {
				notFull.signalAll();
			}

			count -= consumed;
		}

		protected boolean waitUntilNotEmpty() throws IOException {
			while ((count == 0) && !finished) {
				notEmpty.awaitUninterruptibly();

				if (closed) {
					throw new IOException("Stream closed");
				}
			}

			if ((count == 0) && finished) {
				return true;
			}
			else {
				return false;
			}
		}

		private static final int _MAX_SKIP_BUFFER_SIZE = 8192;
		private char[] _skipBuffer;
		protected volatile boolean closed;
	}

	protected class PipeWriter extends Writer {

		public Writer append(char c) throws IOException {
			write(c);

			return this;
		}

		public Writer append(CharSequence charSequence) throws IOException {
			String string = null;

			if (charSequence == null) {
				string = StringPool.NULL;
			}
			else {
				string = charSequence.toString();
			}

			write(string, 0, string.length());

			return this;
		}

		public Writer append(CharSequence charSequence, int start, int end)
			throws IOException {
			String string = null;

			if (charSequence == null) {
				string = StringPool.NULL;
			}
			else {
				string = charSequence.subSequence(start, end).toString();
			}

			write(string, 0, string.length());

			return this;
		}

		public void close() {
			bufferLock.lock();
			try {
				closed = true;
				notFull.signalAll();
			}
			finally {
				bufferLock.unlock();
			}
		}

		public void flush() {
		}

		public void write(char[] chars) throws IOException {
			write(chars, 0, chars.length);
		}

		public void write(char[] chars, int offset, int length)
			throws IOException {
			if (closed) {
				throw new IOException("Stream closed");
			}

			if (length <= 0) {
				return;
			}

			bufferLock.lock();
			try {
				int remaining = length;

				while (remaining > 0) {
					waitUntilNotFull();

					int write = remaining;

					if (remaining > buffer.length - count) {
						write = buffer.length - count;
					}

					int sourceBegin = offset + length - remaining;

					if (buffer.length - writeIndex >= write) {
						// One step write
						System.arraycopy(chars, sourceBegin, buffer,
							writeIndex, write);
					}
					else {
						// Two steps write
						int tailLength = buffer.length - writeIndex;
						int headLength = write - tailLength;

						System.arraycopy(chars, sourceBegin, buffer,
							writeIndex, tailLength);
						System.arraycopy(chars, sourceBegin + tailLength,
							buffer, 0, headLength);
					}

					increaseWriteIndex(write);

					remaining -= write;
				}
			}
			finally {
				bufferLock.unlock();
			}
		}

		public void write(int c) throws IOException {
			if (closed) {
				throw new IOException("Stream closed");
			}

			bufferLock.lock();
			try {
				waitUntilNotFull();

				buffer[writeIndex] = (char)c;

				increaseWriteIndex(1);
			}
			finally {
				bufferLock.unlock();
			}
		}

		public void write(String string) throws IOException {
			write(string, 0, string.length());
		}

		public void write(String string, int offset, int length)
			throws IOException {
			if (closed) {
				throw new IOException("Stream closed");
			}

			if (length <= 0) {
				return;
			}

			bufferLock.lock();
			try {
				int remaining = length;

				while (remaining > 0) {
					waitUntilNotFull();

					int write = remaining;

					if (remaining > buffer.length - count) {
						write = buffer.length - count;
					}

					int sourceBegin = offset + length - remaining;

					if (buffer.length - writeIndex >= write) {
						// One step write
						string.getChars(sourceBegin, sourceBegin + write,
							buffer, writeIndex);
					}
					else {
						// Two steps write
						int tailLength = buffer.length - writeIndex;
						int headLength = write - tailLength;

						string.getChars(sourceBegin, sourceBegin + tailLength,
							buffer, writeIndex);
						string.getChars(sourceBegin + tailLength,
							sourceBegin + tailLength + headLength, buffer, 0);
					}

					increaseWriteIndex(write);

					remaining -= write;
				}
			}
			finally {
				bufferLock.unlock();
			}
		}

		private void increaseWriteIndex(int produced) {
			writeIndex += produced;

			if (writeIndex >= buffer.length) {
				writeIndex -= buffer.length;
			}

			if (count == 0) {
				notEmpty.signalAll();
			}

			count += produced;
		}

		protected void waitUntilNotFull() throws IOException {
			while (count == buffer.length) {
				notFull.awaitUninterruptibly();

				if (closed) {
					throw new IOException("Stream closed");
				}
			}
		}

		protected volatile boolean closed;

	}

}