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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;

/**
 * <a href="LiferayTemplateLoader.java.html"><i>View Source</i></a>
 *
 * @author Mika Koivisto
 */
public class LiferayTemplateLoader implements TemplateLoader {

	public void setTemplateLoaders(String[] loaders) {

		_templateLoaders = new FreeMarkerTemplateLoader[loaders.length];

		for (int i = 0; i < loaders.length; i++) {
			try {
				_templateLoaders[i] =
					(FreeMarkerTemplateLoader) Class.forName(
						loaders[i]).newInstance();
			}
			catch (Exception ex) {
				_log.error(ex);

				_templateLoaders[i] = null;
			}
		}
	}

	public void closeTemplateSource(Object templateSource)
		throws IOException {

		for (int i = 0; i < _templateLoaders.length; i++) {

			if (_templateLoaders[i] != null) {
				_templateLoaders[i].closeTemplateSource(templateSource);
			}
		}
	}

	public Object findTemplateSource(String name)
		throws IOException {

		Object source = null;

		for (int i = 0; (source == null) &&
			(i < _templateLoaders.length); i++) {

			if (_templateLoaders[i] != null) {
				source = _templateLoaders[i].findTemplateSource(name);
			}
		}
		return source;
	}

	public long getLastModified(Object templateSource) {

		long lastModified = -1;

		for (int i = 0; (lastModified == -1) &&
			(i < _templateLoaders.length); i++) {

			if (_templateLoaders[i] != null) {
				lastModified =
					_templateLoaders[i].getLastModified(templateSource);
			}
		}

		if (lastModified == -1) {
			return 0;
		}
		else {
			return lastModified;
		}
	}

	public Reader getReader(Object templateSource, String encoding)
		throws IOException {

		Reader reader = null;

		for (int i = 0; (reader == null) &&
			(i < _templateLoaders.length); i++) {

			if (_templateLoaders[i] != null) {
				reader =
					_templateLoaders[i].getReader(templateSource, encoding);
			}
		}
		return reader;
	}

	private static Log _log =
		LogFactoryUtil.getLog(LiferayTemplateLoader.class);

	private FreeMarkerTemplateLoader[] _templateLoaders;

}