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

import com.liferay.portal.kernel.io.FileCacheOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <a href="ZipWriter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ZipWriter implements Serializable {

	public ZipWriter() throws IOException {
		_fcos = new FileCacheOutputStream();
		_zos = new ZipOutputStream(_fcos);
	}

	public void addEntry(String name, byte[] bytes) throws IOException {
		addEntry(name, new ByteArrayInputStream(bytes));
	}

	public void addEntry(String name, InputStream is) throws IOException {
		if (name.startsWith(StringPool.SLASH)) {
			name = name.substring(1);
		}

		if (is == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Adding " + name);
		}

		ZipEntry entry = new ZipEntry(name);

		_zos.putNextEntry(entry);

		// LPS-6009

		if (StreamUtil.USE_NIO) {
			int count;

			while ((count = is.read(_data, 0, _BUFFER)) != -1) {
				_zos.write(_data, 0, count);
			}

			StreamUtil.cleanUp(is);
		}
		else {
			ReadableByteChannel inputChannel = Channels.newChannel(is);
			WritableByteChannel outputChannel = Channels.newChannel(_zos);

			StreamUtil.transfer(inputChannel, outputChannel);

			StreamUtil.cleanUp(inputChannel);
		}

		_zos.closeEntry();
	}

	public void addEntry(String name, String s) throws IOException {
		addEntry(name, s.getBytes());
	}

	public void addEntry(String name, StringBuilder sb) throws IOException {
		addEntry(name, sb.toString());
	}

	public byte[] finish() throws IOException {
		_zos.close();

		return _fcos.getBytes();
	}

	public FileCacheOutputStream finishWithStream() throws IOException {
		_zos.close();

		return _fcos;
	}

	private static final Log _log = LogFactoryUtil.getLog(ZipWriter.class);

	private static final int _BUFFER = 2048;

	private FileCacheOutputStream _fcos;
	private ZipOutputStream _zos;
	private byte[] _data = new byte[_BUFFER];

}