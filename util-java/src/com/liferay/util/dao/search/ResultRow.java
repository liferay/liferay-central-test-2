/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.util.dao.search;

import com.liferay.util.CollectionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * <a href="ResultRow.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ResultRow {

	public ResultRow(Object obj, String primaryKey, int pos) {
		this(obj, primaryKey, pos, false);
	}

	public ResultRow(Object obj, String primaryKey, int pos, boolean bold) {
		_obj = obj;
		_primaryKey = primaryKey;
		_pos = pos;
		_bold = bold;
		_entries = new ArrayList();
	}

	public Object getObject() {
		return _obj;
	}

	public void setObject(Object obj) {
		_obj = obj;
	}

	public String getPrimaryKey() {
		return _primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		_primaryKey = primaryKey;
	}

	public int getPos() {
		return _pos;
	}

	public boolean isBold() {
		return _bold;
	}

	public List getEntries() {
		return _entries;
	}

	public Object getParameter(String param) {
		if (_params == null) {
			_params = CollectionFactory.getHashMap();
		}

		return _params.get(param);
	}

	public void setParameter(String param, Object value) {
		if (_params == null) {
			_params = CollectionFactory.getHashMap();
		}

		_params.put(param, value);
	}

	// Text with name

	public void addText(String name) {
		addText(_entries.size(), name);
	}

	public void addText(int index, String name) {
		addText(
			index,
			new TextSearchEntry(
				SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, name));
	}

	// Text with name and href

	public void addText(String name, String href) {
		addText(_entries.size(), name, href);
	}

	public void addText(int index, String name, String href) {
		addText(
			index,
			new TextSearchEntry(
				SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, name,
				href));
	}

	// Text with name and portlet URL

	public void addText(String name, PortletURL portletURL) {
		if (portletURL == null) {
			addText(name);
		}
		else {
			addText(name, portletURL.toString());
		}
	}

	public void addText(int index, String name, PortletURL portletURL) {
		if (portletURL == null) {
			addText(index, name);
		}
		else {
			addText(index, name, portletURL.toString());
		}
	}

	// Text with search entry

	public void addText(TextSearchEntry searchEntry) {
		_entries.add(_entries.size(), searchEntry);
	}

	public void addText(int index, TextSearchEntry searchEntry) {
		_entries.add(index, searchEntry);
	}

	// Button with name and href

	public void addButton(String name, String href) {
		addButton(_entries.size(), name, href);
	}

	public void addButton(int index, String name, String href) {
		addButton(
			index, SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, name,
			href);
	}

	public void addButton(
		String align, String valign, String name, String href) {

		addButton(_entries.size(), align, valign, name, href);
	}

	public void addButton(
		int index, String align, String valign, String name, String href) {

		_entries.add(
			index, new ButtonSearchEntry(align, valign, name, href));
	}

	// JSP

	public void addJSP(String path) {
		addJSP(_entries.size(), path);
	}

	public void addJSP(String path, String align, String valign) {
		addJSP(_entries.size(), path, align, valign);
	}

	public void addJSP(int index, String path) {
		addJSP(
			index, SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, path);
	}

	public void addJSP(int index, String align, String valign, String path) {
		_entries.add(index, new JSPSearchEntry(align, valign, path));
	}

	private Object _obj;
	private String _primaryKey;
	private int _pos;
	private boolean _bold;
	private List _entries;
	private Map _params;

}