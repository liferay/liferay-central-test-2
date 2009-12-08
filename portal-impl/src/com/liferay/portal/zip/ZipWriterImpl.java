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

package com.liferay.portal.zip;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.util.SystemProperties;

import de.schlichtherle.io.ArchiveException;
import de.schlichtherle.io.DefaultArchiveDetector;
import de.schlichtherle.io.File;
import de.schlichtherle.io.FileInputStream;
import de.schlichtherle.io.FileOutputStream;
import de.schlichtherle.io.archive.zip.ZipDriver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <a href="ZipWriterImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class ZipWriterImpl implements ZipWriter {

	static {
		File.setDefaultArchiveDetector(
			new DefaultArchiveDetector(
			DefaultArchiveDetector.DEFAULT, "lar", new ZipDriver()));
	}

	public ZipWriterImpl() {
		_file = new File(
			SystemProperties.get(SystemProperties.TMP_DIR) + StringPool.SLASH +
				PortalUUIDUtil.generate() + ".zip");

		_file.mkdir();
		_file.deleteOnExit();
	}

	public ZipWriterImpl(java.io.File file) {
		_file = new File(file);

		_file.mkdir();
	}

	public void addEntry(String name, byte[] bytes) throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
			bytes);

		try {
			addEntry(name, byteArrayInputStream);
		}
		finally {
			byteArrayInputStream.close();
		}
	}

	public void addEntry(String name, InputStream inpuStream)
		throws IOException {

		if (name.startsWith(StringPool.SLASH)) {
			name = name.substring(1);
		}

		if (inpuStream == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Adding " + name);
		}

		FileUtil.mkdirs(getPath());

		OutputStream outputStream = new FileOutputStream(
			new File(getPath() + StringPool.SLASH + name));

		try {
			File.cat(inpuStream, outputStream);
		}
		finally {
			outputStream.close();
		}
	}

	public void addEntry(String name, String s) throws IOException {
		addEntry(name, s.getBytes(StringPool.UTF8));
	}

	public void addEntry(String name, StringBuilder sb) throws IOException {
		addEntry(name, sb.toString());
	}

	public byte[] finish() throws IOException {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		InputStream inputStream = new FileInputStream(_file);

		try {
			File.cat(inputStream, byteArrayOutputStream);
		}
		finally {
			byteArrayOutputStream.close();
			inputStream.close();
		}

		return byteArrayOutputStream.toByteArray();
	}

	public java.io.File getFile() {
		try {
			File.umount(_file);
		}
		catch (ArchiveException ae) {
			_log.error(ae, ae);
		}

		return _file;
	}

	public String getPath() {
		return _file.getPath();
	}

	private static final Log _log = LogFactoryUtil.getLog(ZipWriter.class);

	private final File _file;

}