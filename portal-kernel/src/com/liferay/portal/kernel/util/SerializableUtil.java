/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <a href="SerializableUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class SerializableUtil {

	public static Object deserialize(byte[] bytes)
		throws ClassNotFoundException, IOException {

		ObjectInputStream ois = null;

		try {
			ois = new ObjectInputStream(new UnsyncByteArrayInputStream(bytes));

			Object obj = ois.readObject();

			ois.close();

			ois = null;

			return obj;
		}
		finally {
			if (ois != null) {
				ois.close();
			}
		}
	}

	public static byte[] serialize(Object obj) throws IOException {
		ObjectOutputStream oos = null;

		try {
			UnsyncByteArrayOutputStream ubaos =
				new UnsyncByteArrayOutputStream();

			oos = new ObjectOutputStream(ubaos);

			oos.writeObject(obj);

			oos.close();

			oos = null;

			return ubaos.toByteArray();
		}
		finally {
			if (oos != null) {
				oos.close();
			}
		}
	}

}