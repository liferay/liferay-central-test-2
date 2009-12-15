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
import java.io.Reader;

/**
 * <a href="LiferayTemplateSource.java.html"><i>View Source</i></a>
 * 
 * @author Mika Koivisto
 */
public class LiferayTemplateSource {

	public LiferayTemplateSource(
		Object source, FreeMarkerTemplateLoader loader) {

		_loader = loader;
		_source = source;
	}

	public void close() {
		_loader.closeTemplateSource(_source);
	}

	public boolean equals(Object o) {
		if (o instanceof LiferayTemplateSource) {
			LiferayTemplateSource lts = (LiferayTemplateSource) o;
			return lts._loader.equals(_loader) && lts._source.equals(_source);
		}
		return false;
	}

	public long getLastModified() {
		return _loader.getLastModified(_source);
	}

	public Reader getReader(String encoding) throws IOException {
		return _loader.getReader(_source, encoding);
	}

	public int hashCode() {
		return _loader.hashCode() + 31 * _source.hashCode();
	}

	public String toString() {
		return _source.toString();
	}

	private FreeMarkerTemplateLoader _loader;
	private Object _source;

}
