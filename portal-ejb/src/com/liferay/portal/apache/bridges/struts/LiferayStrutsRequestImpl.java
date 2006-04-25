/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.util.CollectionFactory;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <a href="LiferayStrutsRequestImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class LiferayStrutsRequestImpl extends HttpServletRequestWrapper {

	public LiferayStrutsRequestImpl(HttpServletRequest req) {
		super(req);
		
		_strutsAttributes = (Map)req.getAttribute("STRUTS_BRIDGES_ATTRIBUTES");
		
		if (_strutsAttributes == null) {
			_strutsAttributes = CollectionFactory.getHashMap();
			req.setAttribute("STRUTS_BRIDGES_ATTRIBUTES", _strutsAttributes);
		}
	}

	public Object getAttribute(String name) {
		Object value = null;
		
		if (name.startsWith(_STRUTS_PACKAGE)) {
			value = _strutsAttributes.get(name);
		} 
		else {
			value = super.getAttribute(name);
		}
		
		return value;
	}

	public void setAttribute(String name, Object value) {
		if (name.startsWith(_STRUTS_PACKAGE)) {
			_strutsAttributes.put(name, value);
		} 
		else {
			super.setAttribute(name, value);
		}
	}
	
	public Enumeration getAttributeNames() {
		Enumeration parentAttributeNames = super.getAttributeNames();
		
		Vector attributeNames = new Vector();
		
		for (String name = null; parentAttributeNames.hasMoreElements(); 
			name = (String) parentAttributeNames.nextElement()) {
			
			if (!name.startsWith(_STRUTS_PACKAGE)) {
				attributeNames.add(name);
			}
		}
		
		Iterator it = _strutsAttributes.keySet().iterator();
		
		for (Object name = null; it.hasNext(); name = it.next()) {
			attributeNames.add(name);
		}
		
		return attributeNames.elements();
	}

	private static String _STRUTS_PACKAGE = "org.apache.struts.";

	private Map _strutsAttributes;
}
