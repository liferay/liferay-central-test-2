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

package com.liferay.portal.apache.bridges.struts;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStreamWrapper;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <a href="LiferayStrutsRequestImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 * @author Deepak Gothe
 */
public class LiferayStrutsRequestImpl extends HttpServletRequestWrapper {

	public LiferayStrutsRequestImpl(HttpServletRequest request) {
		super(request);

		_strutsAttributes = (Map<String, Object>)request.getAttribute(
			WebKeys.STRUTS_BRIDGES_ATTRIBUTES);

		if (_strutsAttributes == null) {
			_strutsAttributes = new HashMap<String, Object>();

			request.setAttribute(
				WebKeys.STRUTS_BRIDGES_ATTRIBUTES, _strutsAttributes);
		}
	}

	public Object getAttribute(String name) {
		Object value = null;

		if (name.startsWith(StrutsUtil.STRUTS_PACKAGE)) {
			value = _strutsAttributes.get(name);
		}
		else {
			value = super.getAttribute(name);
		}

		return value;
	}

	public void setAttribute(String name, Object value) {
		if (name.startsWith(StrutsUtil.STRUTS_PACKAGE)) {
			_strutsAttributes.put(name, value);
		}
		else {
			super.setAttribute(name, value);
		}
	}

	public Enumeration<String> getAttributeNames() {
		List<String> attributeNames = new Vector<String>();

		Enumeration<String> enu = super.getAttributeNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (!name.startsWith(StrutsUtil.STRUTS_PACKAGE)) {
				attributeNames.add(name);
			}
		}

		Iterator<String> itr = _strutsAttributes.keySet().iterator();

		while (itr.hasNext()) {
			String name = itr.next();

			attributeNames.add(name);
		}

		return Collections.enumeration(attributeNames);
	}

	public ServletInputStream getInputStream() throws IOException {
		if (_bytes == null) {
			InputStream is = super.getInputStream();

			_bytes = FileUtil.getBytes(is);

			is.close();
		}

		return new UnsyncByteArrayInputStreamWrapper(
			new UnsyncByteArrayInputStream(_bytes));
	}

	private Map<String, Object> _strutsAttributes;
	private byte[] _bytes;

}