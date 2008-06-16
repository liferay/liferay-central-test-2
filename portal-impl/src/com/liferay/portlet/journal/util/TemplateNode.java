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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="TemplateNode.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Raymond Augé
 *
 */
public class TemplateNode extends LinkedHashMap<String, Object> {

	public TemplateNode(String name, String data, String type) {
		super();

		put("name", name);
		put("data", data);
		put("type", type);
		put("options", new ArrayList<String>());
	}

	public void appendChild(TemplateNode child) {
		_children.put(child.getName(), child);
		put(child.getName(), child);
	}

	public void appendChildren(List<TemplateNode> children) {
		for (TemplateNode child : children) {
			appendChild(child);
		}
	}

	public void appendOption(String option) {
		getOptions().add(option);
	}

	public void appendOptions(List<String> options) {
		getOptions().addAll(options);
	}

	public TemplateNode getChild(String name) {
		return _children.get(name);
	}

	public List<TemplateNode> getChildren() {
		return new ArrayList<TemplateNode>(_children.values());
	}

	public String getData() {
		return (String)get("data");
	}

	public String getName() {
		return (String)get("name");
	}

	public List<String> getOptions() {
		return (List<String>)get("options");
	}

	public String getType() {
		return (String)get("type");
	}

	public String getUrl() {
		if (getType().equals("link_to_layout")) {
			StringMaker sm = new StringMaker();

			sm.append(
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING);
			sm.append(StringPool.SLASH);
			sm.append("@group_id@");
			sm.append(StringPool.SLASH);
			sm.append(getData());

			return sm.toString();
		}
		else {
			return StringPool.BLANK;
		}
	}

	private Map<String, TemplateNode> _children =
		new LinkedHashMap<String, TemplateNode>();

}