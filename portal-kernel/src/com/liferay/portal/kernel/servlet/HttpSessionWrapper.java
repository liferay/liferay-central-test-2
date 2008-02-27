/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 *
 */
public class HttpSessionWrapper implements HttpSession {

	public HttpSessionWrapper(HttpSession ses) {
		_ses = ses;
	}

	public Object getAttribute(String name) {
		return _ses.getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		return _ses.getAttributeNames();
	}

	public long getCreationTime() {
		return _ses.getCreationTime();
	}

	public String getId() {
		return _ses.getId();
	}

	public long getLastAccessedTime() {
		return _ses.getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return _ses.getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return _ses.getServletContext();
	}

	/**
	 * @deprecated
	 */
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		return _ses.getSessionContext();
	}

	/**
	 * @deprecated
	 */
	public Object getValue(String name) {
		return _ses.getValue(name);
	}

	/**
	 * @deprecated
	 */
	public String[] getValueNames() {
		return _ses.getValueNames();
	}

	public void invalidate() {
		_ses.invalidate();
	}

	public boolean isNew() {
		return _ses.isNew();
	}

	/**
	 * @deprecated
	 */
	public void putValue(String name, Object value) {
		_ses.putValue(name, value);
	}

	public void removeAttribute(String name) {
		_ses.removeAttribute(name);
	}

	/**
	 * @deprecated
	 */
	public void removeValue(String name) {
		_ses.removeValue(name);
	}

	public void setAttribute(String name, Object value) {
		_ses.setAttribute(name, value);
	}

	public void setMaxInactiveInterval(int interval) {
		_ses.setMaxInactiveInterval(interval);
	}

	private HttpSession _ses;

}