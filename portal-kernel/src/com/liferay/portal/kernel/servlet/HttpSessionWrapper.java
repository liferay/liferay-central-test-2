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

package com.liferay.portal.kernel.servlet;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * <a href="HttpSessionWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class HttpSessionWrapper implements HttpSession {

	public HttpSessionWrapper(HttpSession session) {
		_session = session;
	}

	public Object getAttribute(String name) {
		return _session.getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		return _session.getAttributeNames();
	}

	public long getCreationTime() {
		return _session.getCreationTime();
	}

	public String getId() {
		return _session.getId();
	}

	public long getLastAccessedTime() {
		return _session.getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return _session.getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return _session.getServletContext();
	}

	/**
	 * @deprecated
	 */
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		return _session.getSessionContext();
	}

	/**
	 * @deprecated
	 */
	public Object getValue(String name) {
		return _session.getValue(name);
	}

	/**
	 * @deprecated
	 */
	public String[] getValueNames() {
		return _session.getValueNames();
	}

	public void invalidate() {
		_session.invalidate();
	}

	public boolean isNew() {
		return _session.isNew();
	}

	/**
	 * @deprecated
	 */
	public void putValue(String name, Object value) {
		_session.putValue(name, value);
	}

	public void removeAttribute(String name) {
		_session.removeAttribute(name);
	}

	/**
	 * @deprecated
	 */
	public void removeValue(String name) {
		_session.removeValue(name);
	}

	public void setAttribute(String name, Object value) {
		_session.setAttribute(name, value);
	}

	public void setMaxInactiveInterval(int interval) {
		_session.setMaxInactiveInterval(interval);
	}

	private HttpSession _session;

}