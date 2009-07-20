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

package com.liferay.portal.kernel.zip;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ByteArrayMaker;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipReader implements Serializable {

	public ZipReader(File file) throws Exception {
		this(new FileInputStream(file));
	}

	public ZipReader(InputStream stream) {
		try {
			_zis = new ZipInputStream(stream);

			while (true) {
				ZipEntry entry = _zis.getNextEntry();

				if (entry == null) {
					break;
				}

				String currentName = entry.getName();

				ByteArrayMaker bam = new ByteArrayMaker();

				while (true) {
					int count = _zis.read(_data, 0, _BUFFER);

					if (count == -1) {
						break;
					}

					bam.write(_data, 0, count);
				}

				byte[] bytes = bam.toByteArray();

				_entries.put(currentName, bytes);

				int pos = currentName.lastIndexOf(StringPool.SLASH);

				String folderPath = StringPool.BLANK;
				String fileName = currentName;

				if (pos > 0) {
					folderPath = currentName.substring(0, pos + 1);
					fileName = currentName.substring(pos + 1);
				}

				List<ObjectValuePair<String, byte[]>> files =
					_folderEntries.get(folderPath);

				if (files == null) {
					files = new ArrayList<ObjectValuePair<String, byte[]>>();

					_folderEntries.put(folderPath, files);
				}

				ObjectValuePair<String, byte[]> ovp =
					new ObjectValuePair<String, byte[]>(fileName, bytes);

				files.add(ovp);
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
		finally {
			try {
				_zis.close();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}
		}
	}

	public Map<String, byte[]> getEntries() {
		return _entries;
	}

	public byte[] getEntryAsByteArray(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		byte[] bytes = _entries.get(name);

		if ((bytes == null) && name.startsWith(StringPool.SLASH)) {
			bytes = _entries.get(name.substring(1));
		}

		return bytes;
	}

	public String getEntryAsString(String name) {
		byte[] bytes = getEntryAsByteArray(name);

		if (bytes != null) {
			return new String(bytes);
		}

		return null;
	}

	public Map<String, List<ObjectValuePair<String, byte[]>>>
		getFolderEntries() {

		return _folderEntries;
	}

	public List<ObjectValuePair<String, byte[]>> getFolderEntries(String path) {
		if (Validator.isNull(path)) {
			return null;
		}

		return _folderEntries.get(path);
	}

	private static final int _BUFFER = 2048;

	private static Log _log = LogFactoryUtil.getLog(ZipReader.class);

	private ZipInputStream _zis;
	private Map<String, byte[]> _entries = new LinkedHashMap<String, byte[]>();
	private Map<String, List<ObjectValuePair<String, byte[]>>> _folderEntries =
		new LinkedHashMap<String, List<ObjectValuePair<String, byte[]>>>();
	private byte[] _data = new byte[_BUFFER];

}