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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

/**
 * <a href="PortalSessionContext.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalSessionContext {

	public static int count() {
		return _instance._count();
	}

	public static HttpSession get(String sessionId) {
		return _instance._get(sessionId);
	}

	public static void put(String sessionId, HttpSession session) {
		_instance._put(sessionId, session);
	}

	public static HttpSession remove(String sessionId) {
		return _instance._remove(sessionId);
	}

	public static Collection<HttpSession> values() {
		return _instance._values();
	}

	private PortalSessionContext() {
		_sessionPool = new ConcurrentHashMap<String, HttpSession>();
	}

	private int _count() {
		return _sessionPool.size();
	}

	private HttpSession _get(String sessionId) {
		if (Validator.isNull(sessionId)) {
			return null;
		}

		return _sessionPool.get(sessionId);
	}

	private void _put(String sessionId, HttpSession session) {
		_sessionPool.put(sessionId, session);
	}

	private HttpSession _remove(String sessionId) {
		return _sessionPool.remove(sessionId);
	}

	public Collection<HttpSession> _values() {
		return _sessionPool.values();
	}

	private static PortalSessionContext _instance = new PortalSessionContext();

	private Map<String, HttpSession> _sessionPool;

}