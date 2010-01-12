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
import java.io.UnsupportedEncodingException;

/**
 * <a href="UnsyncByteArrayOutputStream.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncByteArrayOutputStream extends OutputStream {

	public UnsyncByteArrayOutputStream() {
		this(32);
	}

	public UnsyncByteArrayOutputStream(int size) {
		buffer = new byte[size];
	}

	public void reset() {
		index = 0;
	}

	public int size() {
		return index;
	}

	public byte[] toByteArray() {
		byte[] newBuffer = new byte[index];

		System.arraycopy(buffer, 0, newBuffer, 0, index);

		return newBuffer;
	}

	public String toString() {
		return new String(buffer, 0, index);
	}

	public String toString(String charsetName)
		throws UnsupportedEncodingException {

		return new String(buffer, 0, index, charsetName);
	}

	/**
	 * <p>
	 * This method is very dangerous, it returns the internal byte[] buffer
	 * directly without copy protection. Consider to use toByteArray() which is
	 * much more safer. Use this method only when you are 100% sure what you are
	 * doing!
	 * </p>
	 * <p>
	 * It must work with size() method to indicate where is the last valid byte.
	 * </p>
	 * <p>
	 * Since there is no copy protection, any modification to the return byte[]
	 * will propagate to the source output stream, on the other side, any
	 * modification to the source output stream will also propagate to the
	 * return byte[]. There is no way to detect or prevent these kinds of
	 * modification propagating, so again this is very dangerous!
	 * </p>
	 * <p>
	 * The only situation you will need this method is when using
	 * UnsyncByteArrayOutputStream to collect data, after the collection the
	 * data has to be transfer to other place for further processing. No other
	 * code will ever use the original UnsyncByteArrayOutputStream any more, so
	 * it is safe to deliver the internal buffer to other code. When you are
	 * collecting huge data, this metod can save you a lot of temporary byte[]
	 * buffers.
	 * </p>
	 * @return the internal byte[] buffer
	 */
	public byte[] unsafeGetByteArray() {
		return buffer;
	}

	public void write(byte[] byteArray) {
		write(byteArray, 0, byteArray.length);
	}

	public void write(byte[] byteArray, int offset, int length) {
		if (length <= 0) {
			return;
		}

		int newIndex = index + length;

		if (newIndex > buffer.length) {
			int newBufferSize = Math.max(buffer.length << 1, newIndex);

			byte[] newBuffer = new byte[newBufferSize];

			System.arraycopy(buffer, 0, newBuffer, 0, index);

			buffer = newBuffer;
		}

		System.arraycopy(byteArray, offset, buffer, index, length);

		index = newIndex;
	}

	public void write(int b) {
		int newIndex = index + 1;

		if (newIndex > buffer.length) {
			int newBufferSize = Math.max(buffer.length << 1, newIndex);

			byte[] newBuffer = new byte[newBufferSize];

			System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);

			buffer = newBuffer;
		}

		buffer[index] = (byte)b;

		index = newIndex;
	}

	public void writeTo(OutputStream outputStream) throws IOException {
		outputStream.write(buffer, 0, index);
	}

	protected byte[] buffer;
	protected int index;

}