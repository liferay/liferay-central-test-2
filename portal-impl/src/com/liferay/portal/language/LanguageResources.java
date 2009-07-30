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

package com.liferay.portal.language;

import com.liferay.portal.kernel.util.StringPool;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.struts.util.MessageResources;

/**
 * <a href="LanguageResources.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LanguageResources {

	public LanguageResources(MessageResources resources) {
		_resources = resources;
		_cache = new ConcurrentHashMap<String, ResourceValue>(10000);
	}

	public String getMessage(Locale locale, String key) {
		String cacheKey = _getCacheKey(locale, key);

		ResourceValue resourceValue = _cache.get(cacheKey);

		if (resourceValue == null) {
			String value = _resources.getMessage(locale, key);

			resourceValue = new ResourceValue(value);

			_cache.put(cacheKey, resourceValue);
		}

		return resourceValue.getValue();
	}

	private String _getCacheKey(Locale locale, String key) {
		return String.valueOf(locale) + StringPool.POUND + key;
	}

	private class ResourceValue {

		private ResourceValue(String value) {
			_value = value;
		}

		public String getValue() {
			return _value;
		}

		private String _value;

	}

	private Map<String, ResourceValue> _cache;
	private MessageResources _resources;

}