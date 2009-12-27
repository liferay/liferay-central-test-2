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
 * <a href="UnsyncFilterOutputStream.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://support.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncFilterOutputStream extends OutputStream {

	public UnsyncFilterOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void close() throws IOException {
		try {
			flush();
		}
		catch (IOException ioe) {
		}

		outputStream.close();
	}

	public void flush() throws IOException {
		outputStream.flush();
	}

	public void write(byte[] byteArray) throws IOException {
		outputStream.write(byteArray, 0, byteArray.length);
	}

	public void write(byte[] byteArray, int offset, int length)
		throws IOException {

		outputStream.write(byteArray, offset, length);
	}

	public void write(int b) throws IOException {
		outputStream.write(b);
	}

	protected OutputStream outputStream;

}