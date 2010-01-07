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

package com.liferay.portal.kernel.io.unsync;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <a href="UnsyncBufferedOutputStream.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedOutputStream extends UnsyncFilterOutputStream {

	public UnsyncBufferedOutputStream(OutputStream outputStream) {
		this(outputStream, _DEFAULT_BUFFER_SIZE);
	}

	public UnsyncBufferedOutputStream(OutputStream outputStream, int size) {
		super(outputStream);

		buffer = new byte[size];
	}

	public void flush() throws IOException {
		if (count > 0) {
			outputStream.write(buffer, 0, count);

			count = 0;
		}

		outputStream.flush();
	}

	public void write(byte[] byteArray, int offset, int length)
		throws IOException {

		if (length >= buffer.length) {
			if (count > 0) {
				outputStream.write(buffer, 0, count);

				count = 0;
			}

			outputStream.write(byteArray, offset, length);

			return;
		}

		if (count > 0 && length > buffer.length - count) {
			outputStream.write(buffer, 0, count);

			count = 0;
		}

		System.arraycopy(byteArray, offset, buffer, count, length);

		count += length;
	}

	public void write(byte[] byteArray) throws IOException {
		write(byteArray, 0, byteArray.length);
	}

	public void write(int b) throws IOException {
		if (count >= buffer.length) {
			outputStream.write(buffer, 0, count);

			count = 0;
		}

		buffer[count++] = (byte)b;
	}

	protected byte[] buffer;
	protected int count;

	private static int _DEFAULT_BUFFER_SIZE = 8192;

}