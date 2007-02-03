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

package com.liferay.util.zip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;

import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * <a href="ZipReader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class ZipReader implements Serializable {

	public ZipReader(File file) throws Exception {
		_zis = new ZipInputStream(new FileInputStream(file));
	}

	public ZipReader(InputStream stream) {
		_zis = new ZipInputStream(stream);
	}

	public String getEntryAsString(String name) throws Exception {
		byte[] byteArray = getEntryAsByteArray(name);

		if (byteArray != null) {
			return new String(byteArray);
		}

		return null;
	}

	public byte[] getEntryAsByteArray(String name) throws Exception {
		if (_entries.containsKey(name)) {
			return (byte[])_entries.get(name);
		}
		else {
			ZipEntry entry = null;

			while ((entry = _zis.getNextEntry()) != null) {
				String currentName = entry.getName();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				int count;

				while ((count = _zis.read(_data, 0, _BUFFER)) != -1) {
					baos.write(_data, 0, count);
				}

				byte[] byteArray = baos.toByteArray();

				_entries.put(currentName, byteArray);

				if (currentName.equals(name)) {
					return byteArray;
				}
			}
		}

		return null;
	}

	private static final int _BUFFER = 2048;

	private ZipInputStream _zis;
	private HashMap _entries = new HashMap();
	private byte[] _data = new byte[_BUFFER];

}