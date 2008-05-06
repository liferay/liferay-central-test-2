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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ElementImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ElementImpl implements Element {

	public ElementImpl(org.dom4j.Element el) {
		_el = el;
	}

	public String attributeValue(String name) {
		return _el.attributeValue(name);
	}

	public String getName() {
		return _el.getName();
	}

	public String getText() {
		return _el.getText();
	}

	public Element element(String name) {
		org.dom4j.Element el = _el.element(name);

		if (el == null) {
			return null;
		}
		else {
			return new ElementImpl(el);
		}
	}

	public List<Element> elements() {
		return toNewElements(_el.elements());
	}

	public List<Element> elements(String name) {
		return toNewElements(_el.elements(name));
	}

	public String elementText(String name) {
		return _el.elementText(name);
	}

	private List<Element> toNewElements(List<org.dom4j.Element> oldElements) {
		List<Element> newElements = new ArrayList<Element>(oldElements.size());

		for (org.dom4j.Element oldEl : oldElements) {
			newElements.add(new ElementImpl(oldEl));
		}

		return newElements;
	}

	private org.dom4j.Element _el;

}