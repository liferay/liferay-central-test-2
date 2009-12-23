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
 * Note: This class has the same function as
 * {@link java.io.FilterOutputStream}, but without synchronized protection.
 * We make this for performance, see http://issues.liferay.com/browse/LPS-6648.
 *
 * Warning: This class is not thread safe, make sure using it only under single
 * thread context or adding external synchronized protection.
 *
 * @author Shuyang Zhou
 */
public class UnsyncFilterOutputStream extends OutputStream {

	public UnsyncFilterOutputStream(OutputStream out) {
		this.out = out;
	}

	public void close() throws IOException {
		try {
			flush();
		}
		catch (IOException ignored) {
		}
		out.close();
	}

	public void flush() throws IOException {
		out.flush();
	}

	public void write(int b) throws IOException {
		out.write(b);
	}

	public void write(byte[] b) throws IOException {
		out.write(b, 0, b.length);
	}

	public void write(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
	}

	protected OutputStream out;

}