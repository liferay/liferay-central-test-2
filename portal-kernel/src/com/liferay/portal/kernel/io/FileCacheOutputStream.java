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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <a href="FileCacheOutputStream.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class FileCacheOutputStream extends OutputStream {

	public FileCacheOutputStream() throws IOException {
		_tempFile = File.createTempFile(
			PortalUUIDUtil.generate() + StringPool.DASH, _EXTENSION);

		_ubos = new UnsyncBufferedOutputStream(
			new FileOutputStream(_tempFile), _BUFFER);
	}

	public void cleanUp() {
		try {
			flush();
			close();

			if (_fis != null) {
				_fis.close();
			}

			FileUtil.delete(_tempFile);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe.getMessage());
			}
		}
	}

	public void close() throws IOException {
		_ubos.close();
	}

	public void flush() throws IOException {
		_ubos.flush();
	}

	public byte[] getBytes() throws IOException {
		flush();
		close();

		return FileUtil.getBytes(_tempFile);
	}

	public File getFile() throws IOException {
		flush();
		close();

		return _tempFile;
	}

	public FileInputStream getFileInputStream() throws IOException {
		if (_fis == null) {
			flush();
			close();

			_fis = new FileInputStream(_tempFile);
		}

		return _fis;
	}

	public long getSize() {
		return _tempFile.length();
	}

	public void write(byte[] b) throws IOException {
		_ubos.write(b);
	}

	public void write(byte[] b, int off, int len) throws IOException {
		_ubos.write(b, off, len);
	}

	public void write(int b) throws IOException {
		_ubos.write(b);
	}

	private static final int _BUFFER = 2048;

	private static final String _EXTENSION = ".fcos";

	protected FileInputStream _fis;
	protected File _tempFile;
	protected UnsyncBufferedOutputStream _ubos;

	private static Log _log =
		LogFactoryUtil.getLog(FileCacheOutputStream.class);

}