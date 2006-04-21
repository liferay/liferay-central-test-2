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

package com.liferay.util.servlet;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <a href="ParamFilteringServletRequest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  James Schopp
 *
 */
public class ParamFilteringServletRequest extends HttpServletRequestWrapper {

	public ParamFilteringServletRequest(HttpServletRequest req) {
		this(req, null);
	}

	public ParamFilteringServletRequest(HttpServletRequest req,
										Collection filteredParams) {

		super(req);

		_filteredParams = filteredParams;
	}

	public String getParameter(String name) {
		if (isFilteredParam(name)) {
			return null;
		}
		else {
			return super.getParameter(name);
		}
	}

	public Map getParameterMap() {
		Map map = super.getParameterMap();

		if (_filteredParams == null) {
			return map;
		}

		Iterator itr = _filteredParams.iterator();

		while (itr.hasNext()) {
			map.remove(itr.next());
		}

		return map;
	}

	public Enumeration getParameterNames() {
		Enumeration enu = super.getParameterNames();

		Set set = new HashSet();

		while (enu.hasMoreElements()) {
			String name = (String)enu.nextElement();

			if (!isFilteredParam(name)) {
				set.add(name);
			}
		}

		return Collections.enumeration(set);
	}

	public String[] getParameterValues(String name) {
		if (isFilteredParam(name)) {
			return null;
		}
		else {
			return super.getParameterValues(name);
		}
	}

	public Collection getFilteredParams() {
		return _filteredParams;
	}

	public void setFilteredParams(Collection filteredParams) {
		_filteredParams = filteredParams;
	}

	public boolean isFilteredParam(String name) {
		if (_filteredParams == null) {
			return false;
		}
		else {
			return _filteredParams.contains(name);
		}
	}

	private Collection _filteredParams;

}