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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;

/**
 * <a href="URLTemplateLoader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public abstract class URLTemplateLoader extends FreeMarkerTemplateLoader {

	public void closeTemplateSource(Object templateSource) {
		if (templateSource instanceof URLTemplateSource) {
			URLTemplateSource urlTemplateSource =
				(URLTemplateSource)templateSource;

			try {
				urlTemplateSource.closeStream();
			}
			catch (IOException ioe) {
			}
		}
	}

	public Object findTemplateSource(String name) throws IOException {
		URL url = getURL(name);

		if (url != null) {
			return new URLTemplateSource(url);
		}

		return null;
	}

	public long getLastModified(Object templateSource) {
		if (templateSource instanceof URLTemplateSource) {
			URLTemplateSource urlTemplateSource =
				(URLTemplateSource)templateSource;

			return urlTemplateSource.getLastModified();
		}

		return super.getLastModified(templateSource);
	}

	public Reader getReader(Object templateSource, String encoding)
		throws IOException {

		if (templateSource instanceof URLTemplateSource) {
			URLTemplateSource urlTemplateSource =
				(URLTemplateSource)templateSource;

			return new UnsyncBufferedReader(
				new InputStreamReader(
					urlTemplateSource.getInputStream(), encoding));
		}

		return null;
	}

	public abstract URL getURL(String name) throws IOException;

}