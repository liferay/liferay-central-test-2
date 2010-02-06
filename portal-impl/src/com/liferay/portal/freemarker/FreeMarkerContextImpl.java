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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.freemarker.FreeMarkerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="FreeMarkerContextImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class FreeMarkerContextImpl implements FreeMarkerContext {

	public FreeMarkerContextImpl() {
		_context = new ConcurrentHashMap<String, Object>();
	}

	public FreeMarkerContextImpl(Map<String, Object> context) {
		_context = new ConcurrentHashMap<String, Object>();

		_context.putAll(context);
	}

	public Object get(String key) {
		return _context.get(key);
	}

	public Map<String, Object> getWrappedContext() {
		return _context;
	}

	public void put(String key, Object value) {
		_context.put(key, value);
	}

	private Map<String, Object> _context;

}