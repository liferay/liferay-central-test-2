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

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.zip.ZipWriter;

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
 * <a href="ZipReaderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class ZipWriterImpl implements ZipWriter {

	static {
		File.setDefaultArchiveDetector(
			new DefaultArchiveDetector(
				DefaultArchiveDetector.DEFAULT, "lar|zip", new ZipDriver()));
	}

	private ZipWriterImpl() throws IOException {
		_zipFile = new File(
			System.getProperty("java.io.tmpdir") + File.separator +
				PortalUUIDUtil.generate() + ".zip");

		_zipFile.mkdir();
		_zipFile.deleteOnExit();
	}

	private ZipWriterImpl(java.io.File file) throws IOException {
		_zipFile = new File(file);

		_zipFile.mkdir();
	}

	public static ZipWriter create() throws IOException {
		return new ZipWriterImpl();
	}

	public static ZipWriter create(java.io.File file) throws IOException {
		return new ZipWriterImpl(file);
	}

	public void addEntry(String name, byte[] bytes) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

		try {
			addEntry(name, bais);
		}
		finally {
			bais.close();
		}
	}

	public void addEntry(String name, InputStream inpuStream) throws IOException {
		if (name.startsWith(StringPool.SLASH)) {
			name = name.substring(1);
		}

		if (inpuStream == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Adding " + name);
		}

		File entry = new File(getPath() + File.separator + name);

		new File(entry.getParent()).mkdirs();

		OutputStream outputStream = new FileOutputStream(entry);

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
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		InputStream inputStream = new FileInputStream(_zipFile);

		try {
			File.cat(inputStream, baos);
		}
		finally {
			inputStream.close();
			baos.close();
		}

		return baos.toByteArray();
	}

	public String getPath() {
		return _zipFile.getPath();
	}

	public java.io.File getZipFile() {
		try {
			File.umount(_zipFile);
		}
		catch (ArchiveException e) {
			_log.error(e);
		}

		return _zipFile;
	}

	private static final Log _log = LogFactoryUtil.getLog(ZipWriter.class);

	private final File _zipFile;

}
