/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * <a href="StreamUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class StreamUtil {

	public static final int BUFFER_SIZE = GetterUtil.getInteger(
		System.getProperty(
			"com.liferay.portal.kernel.util.StreamUtil.buffer.size"),
		8192);

	public static final boolean USE_NIO = GetterUtil.getBoolean(
		System.getProperty(
			"com.liferay.portal.kernel.util.StreamUtil.use.nio"),
		false);

	public static void cleanUp(Channel channel) {
		try {
			if (channel != null) {
				channel.close();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	public static void cleanUp(Channel inputChannel, Channel outputChannel) {
		cleanUp(inputChannel);
		cleanUp(outputChannel);
	}

	public static void cleanUp(InputStream inputStream) {
		try {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	public static void cleanUp(
		InputStream inputStream, OutputStream outputStream) {

		cleanUp(outputStream);
		cleanUp(inputStream);
	}

	public static void cleanUp(OutputStream outputStream) {
		try {
			if (outputStream != null) {
				outputStream.flush();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		try {
			if (outputStream != null) {
				outputStream.close();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	public static void transfer(
			InputStream inputStream, OutputStream outputStream)
		throws IOException {

		transfer(inputStream, outputStream, BUFFER_SIZE, true);
	}

	public static void transfer(
			InputStream inputStream, OutputStream outputStream, boolean cleanUp)
		throws IOException {

		transfer(inputStream, outputStream, BUFFER_SIZE, cleanUp);
	}

	public static void transfer(
			InputStream inputStream, OutputStream outputStream, int bufferSize)
		throws IOException {

		transfer(inputStream, outputStream, bufferSize, true);
	}

	public static void transfer(
			InputStream inputStream, OutputStream outputStream, int bufferSize,
			boolean cleanUp)
		throws IOException {

		if (inputStream == null) {
			throw new IllegalArgumentException("Input stream cannot be null");
		}

		if (outputStream == null) {
			throw new IllegalArgumentException("Output stream cannot be null");
		}

		if (bufferSize <= 0) {
			bufferSize = BUFFER_SIZE;
		}

		if (USE_NIO) {
			ReadableByteChannel readableByteChannel = Channels.newChannel(
				inputStream);
			WritableByteChannel writableByteChannel = Channels.newChannel(
				outputStream);

			transfer(
				readableByteChannel, writableByteChannel, bufferSize, cleanUp);
		}
		else {
			try {
				byte[] bytes = new byte[bufferSize];

				int value = -1;

				while ((value = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0 , value);
				}
			}
			finally {
				if (cleanUp) {
					cleanUp(inputStream, outputStream);
				}
			}
		}
	}

	public static void transfer(
			ReadableByteChannel readableByteChannel,
			WritableByteChannel writableByteChannel)
		throws IOException {

		transfer(readableByteChannel, writableByteChannel, BUFFER_SIZE);
	}

	public static void transfer(
			ReadableByteChannel readableByteChannel,
			WritableByteChannel writableByteChannel, boolean cleanUp)
		throws IOException {

		transfer(
			readableByteChannel, writableByteChannel, BUFFER_SIZE, cleanUp);
	}

	public static void transfer(
			ReadableByteChannel readableByteChannel,
			WritableByteChannel writableByteChannel, int bufferSize)
		throws IOException {

		transfer(readableByteChannel, writableByteChannel, bufferSize, true);
	}

	public static void transfer(
			ReadableByteChannel readableByteChannel,
			WritableByteChannel writableByteChannel, int bufferSize,
			boolean cleanUp)
		throws IOException {

		if (readableByteChannel == null) {
			throw new IllegalArgumentException(
				"Readable byte channel cannot be null");
		}

		if (writableByteChannel == null) {
			throw new IllegalArgumentException(
				"Writable byte channel cannot be null");
		}

		try {
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bufferSize);

			while (readableByteChannel.read(byteBuffer) != -1) {
				byteBuffer.flip();

				writableByteChannel.write(byteBuffer);

				byteBuffer.compact();
			}

			byteBuffer.flip();

			while (byteBuffer.hasRemaining()) {
				writableByteChannel.write(byteBuffer);
			}
		}
		finally {
			if (cleanUp) {
				cleanUp(readableByteChannel, writableByteChannel);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(StreamUtil.class);

}