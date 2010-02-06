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

package com.liferay.portal.zip;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipReader;

import de.schlichtherle.io.DefaultArchiveDetector;
import de.schlichtherle.io.File;
import de.schlichtherle.io.FileInputStream;
import de.schlichtherle.io.FileOutputStream;
import de.schlichtherle.io.archive.zip.ZipDriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ZipReaderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class ZipReaderImpl implements ZipReader {

	static {
		File.setDefaultArchiveDetector(
			new DefaultArchiveDetector(
				DefaultArchiveDetector.DEFAULT, "lar|war|zip",
				new ZipDriver()));
	}

	public ZipReaderImpl(InputStream inputStream) throws IOException {
		_zipFile = new File(FileUtil.createTempFile("zip"));

		OutputStream outputStream = new FileOutputStream(_zipFile);

		try {
			File.cat(inputStream, outputStream);
		}
		finally {
			outputStream.close();
			inputStream.close();
		}
	}

	public ZipReaderImpl(java.io.File file) {
		_zipFile = new File(file);
	}

	public void close() {
		_zipFile.delete();
	}

	public List<String> getEntries() {
		List<String> folderEntries = new ArrayList<String>();

		File[] files = (File[])_zipFile.listFiles();

		for (File file : files) {
			if (!file.isDirectory()) {
				folderEntries.add(file.getEnclEntryName());
			}
			else {
				processDirectory(file, folderEntries);
			}
		}

		return folderEntries;
	}

	public byte[] getEntryAsByteArray(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		byte[] bytes = null;

		try {
			InputStream is = getEntryAsInputStream(name);

			if (is != null) {
				bytes = FileUtil.getBytes(is);
			}
		}
		catch (IOException e) {
			_log.error(e, e);
		}

		return bytes;
	}

	public InputStream getEntryAsInputStream(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		if (name.startsWith(StringPool.SLASH)) {
			name = name.substring(1);
		}

		File file = new File(_zipFile, name, DefaultArchiveDetector.NULL);

		if (file.exists() && !file.isDirectory()) {
			try {
				if (_log.isDebugEnabled()) {
					_log.debug("Extracting " + name);
				}

				return new FileInputStream(file);
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return null;
	}

	public String getEntryAsString(String name) {
		if (Validator.isNull(name)) {
			return null;
		}

		byte[] bytes = getEntryAsByteArray(name);

		if (bytes != null) {
			return new String(bytes);
		}

		return null;
	}

	public List<String> getFolderEntries(String path) {
		if (Validator.isNull(path)) {
			return null;
		}

		List<String> folderEntries = new ArrayList<String>();

		File directory = new File(_zipFile.getPath() + StringPool.SLASH + path);

		File[] files = (File[])directory.listFiles();

		for (File file : files) {
			if (!file.isDirectory()) {
				folderEntries.add(file.getEnclEntryName());
			}
		}

		return folderEntries;
	}

	protected void processDirectory(
		File directory, List<String> folderEntries) {

		File[] files = (File[])directory.listFiles();

		for (File file : files) {
			if (!file.isDirectory()) {
				folderEntries.add(file.getEnclEntryName());
			}
			else {
				processDirectory(file, folderEntries);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ZipReaderImpl.class);

	private File _zipFile;

}