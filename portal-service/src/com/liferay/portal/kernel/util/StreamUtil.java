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
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class StreamUtil {

	public static final int BUFFER_SIZE = GetterUtil.getInteger(
		System.getProperty(StreamUtil.class.getName() + ".buffer.size"),
		8192);

	public static final boolean USE_NIO = GetterUtil.getBoolean(
		System.getProperty(StreamUtil.class.getName() + ".use.nio"),
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

		try {
			if (readableByteChannel == null) {
				throw new IllegalArgumentException(
					"Readable byte channel cannot be null");
			}

			if (writableByteChannel == null) {
				throw new IllegalArgumentException(
					"Writable byte channel cannot be null");
			}

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