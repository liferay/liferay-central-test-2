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

package com.liferay.portal.freemarker;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

/**
 * <a href="URLTemplateSource.java.html"><i>View Source</i></a>
 *
 * @author Mika Koivisto
 */
public class URLTemplateSource {

	public URLTemplateSource(URL url) throws IOException {
		_url = url;
		_urlConnection = url.openConnection();
	}

	public boolean equals(Object obj) {
		if (obj instanceof URLTemplateSource) {
			URLTemplateSource urlTemplateSource = (URLTemplateSource)obj;

			if (_url.equals(urlTemplateSource._url)) {
				return true;
			}
		}

		return false;
	}

	public int hashCode() {
		return _url.hashCode();
	}

	public String toString() {
		return _url.toString();
	}

	protected void closeStream() throws IOException {
		try {
			if (_inputStream != null) {
				_inputStream.close();
			}
			else {
				_urlConnection.getInputStream().close();
			}
		}
		finally {
			_inputStream = null;
			_urlConnection = null;
		}
	}

	protected InputStream getInputStream() throws IOException {
		_inputStream = _urlConnection.getInputStream();

		return _inputStream;
	}

	protected long getLastModified() {
		return _urlConnection.getLastModified();
	}

	private InputStream _inputStream;
	private URL _url;
	private URLConnection _urlConnection;

}