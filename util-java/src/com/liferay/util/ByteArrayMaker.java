/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.util;

import com.liferay.portal.kernel.util.StringPool;

import java.io.ByteArrayOutputStream;

/**
 * <a href="ByteArrayMaker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Harry Mark
 *
 */
public class ByteArrayMaker extends ByteArrayOutputStream {

	static boolean collect = GetterUtil.getBoolean(
		SystemProperties.get(MakerStats.class.getName()), true);

	static MakerStats stats = null;

	static {
		if (collect) {
			stats = new MakerStats(ByteArrayMaker.class.toString());
		}
	}

	public static int DEFAULT_INIT_SIZE = GetterUtil.getInteger(
		SystemProperties.get(ByteArrayMaker.class.getName() + ".initial.size"),
		8000);

	public static MakerStats getStatistics() {
		return stats;
	}

	public ByteArrayMaker() {
		super(DEFAULT_INIT_SIZE);

		if (collect) {
			_getInfo(new Throwable());
		}
	}

	public ByteArrayMaker(int size) {
		super(size);

		if (collect) {
			_getInfo(new Throwable());
		}
	}

	public byte[] toByteArray() {
		if (collect) {
			stats.add(_caller, _initSize, count);
		}

		return super.toByteArray();
	}

	public String toString() {
		return super.toString();
	}

	private void _getInfo(Throwable t) {
		_initSize = buf.length;

		StackTraceElement[] elements = t.getStackTrace();

		if (elements.length > 1) {
			StackTraceElement el = elements[1];

			_caller =
				el.getClassName() + StringPool.PERIOD + el.getMethodName() +
					StringPool.COLON + el.getLineNumber();
		}
	}

	private int _initSize;
	private String _caller;

}