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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="LiferayTemplateLoader.java.html"><i>View Source</i></a>
 *
 * @author Mika Koivisto
 */
public class LiferayTemplateLoader implements TemplateLoader {

	public void closeTemplateSource(Object templateSource) {
		LiferayTemplateSource source = (LiferayTemplateSource)templateSource;

		source.close();
	}

	public Object findTemplateSource(String name) throws IOException {
		// Give the last template loader that found this resource a change to
		// find it again.

		FreeMarkerTemplateLoader templateLoader = _lastLoaderForName.get(name);
		if (templateLoader != null) {
			Object templateSource = templateLoader.findTemplateSource(name);

			if (templateSource != null) {
				return new LiferayTemplateSource(
					templateSource, templateLoader);
			}
		}

		for (FreeMarkerTemplateLoader freeMarkerTemplateLoader :
				_freeMarkerTemplateLoaders) {

			Object templateSource = 
				freeMarkerTemplateLoader.findTemplateSource(name);

			if (templateSource != null) {
				_lastLoaderForName.put(name, freeMarkerTemplateLoader);

				return new LiferayTemplateSource(
					templateSource, freeMarkerTemplateLoader);
			}
		}

		return null;
	}

	public long getLastModified(Object templateSource) {
		LiferayTemplateSource source = (LiferayTemplateSource)templateSource;

		return source.getLastModified();
	}

	public Reader getReader(Object templateSource, String encoding)
		throws IOException {

		LiferayTemplateSource source = (LiferayTemplateSource)templateSource;

		return source.getReader(encoding);
	}

	public void setTemplateLoaders(
		String[] freeMarkerTemplateLoaderClassNames) {

		List<FreeMarkerTemplateLoader> freeMarkerTemplateLoaders =
			new ArrayList<FreeMarkerTemplateLoader>(
				freeMarkerTemplateLoaderClassNames.length);

		for (String freeMarkerTemplateLoaderClassName :
				freeMarkerTemplateLoaderClassNames) {

			try {
				FreeMarkerTemplateLoader freeMarkerTemplateLoader =
					(FreeMarkerTemplateLoader)Class.forName(
						freeMarkerTemplateLoaderClassName).newInstance();

				freeMarkerTemplateLoaders.add(freeMarkerTemplateLoader);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		_freeMarkerTemplateLoaders = freeMarkerTemplateLoaders.toArray(
			new FreeMarkerTemplateLoader[freeMarkerTemplateLoaders.size()]);
	}

	private static Log _log =
		LogFactoryUtil.getLog(LiferayTemplateLoader.class);

	private FreeMarkerTemplateLoader[] _freeMarkerTemplateLoaders;
	private Map<String, FreeMarkerTemplateLoader> _lastLoaderForName = 
		new ConcurrentHashMap<String, FreeMarkerTemplateLoader>();

}