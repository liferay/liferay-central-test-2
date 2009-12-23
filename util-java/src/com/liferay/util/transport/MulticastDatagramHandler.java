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

package com.liferay.util.transport;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import java.io.InputStream;

import java.net.DatagramPacket;

import java.util.zip.GZIPInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MulticastDatagramHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class MulticastDatagramHandler implements DatagramHandler {

	public MulticastDatagramHandler(boolean gzipData, boolean shortData) {
		_gzipData = gzipData;
		_shortData = shortData;
	}

	public void errorReceived(Throwable t) {
		_log.error(t, t);
	}

	public void process(DatagramPacket packet) {
		byte[] bytes = packet.getData();

		if (_gzipData) {
			try {
				bytes = getUnzippedBytes(bytes);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_shortData) {
			byte[] temp = new byte[96];

			System.arraycopy(bytes, 0, temp, 0, 96);

			bytes = temp;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");
		sb.append(packet.getSocketAddress());
		sb.append("] ");
		sb.append(new String(bytes));

		if (_log.isInfoEnabled()) {
			_log.info(sb);
		}
	}

	protected byte[] getUnzippedBytes(byte[] bytes) throws Exception {
		InputStream is =
			new GZIPInputStream(new UnsyncByteArrayInputStream(bytes));
		UnsyncByteArrayOutputStream baos =
			new UnsyncByteArrayOutputStream(bytes.length);

		byte[] buffer = new byte[1500];

		int c = 0;

		while (true) {
			if (c == -1) {
				break;
			}

			c = is.read(buffer, 0, 1500);

			if (c != -1) {
				baos.write(buffer, 0, c);
			}
		}

		is.close();

		baos.flush();
		baos.close();

		return baos.toByteArray();
	}

	private static Log _log = LogFactory.getLog(MulticastDatagramHandler.class);

	private boolean _gzipData;
	private boolean _shortData;

}