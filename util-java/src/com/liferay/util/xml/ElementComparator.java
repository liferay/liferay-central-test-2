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

package com.liferay.util.xml;

import java.util.Comparator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * <a href="ElementComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ElementComparator implements Comparator<Element> {

	public int compare(Element el1, Element el2) {
		String el1Name = el1.getName();
		String el2Name = el2.getName();

		if (!el1Name.equals(el2Name)) {
			return el1Name.compareTo(el2Name);
		}

		String el1Text = el1.getTextTrim();
		String el2Text = el2.getTextTrim();

		if (!el1Text.equals(el2Text)) {
			return el1Text.compareTo(el2Text);
		}

		List<Attribute> el1Attributes = el1.attributes();
		List<Attribute> el2Attributes = el2.attributes();

		if (el1Attributes.size() < el2Attributes.size()) {
			return -1;
		}
		else if (el1Attributes.size() > el2Attributes.size()) {
			return 1;
		}

		for (Attribute attr : el1Attributes) {
			int value = _compare(
				el2Attributes, attr, new AttributeComparator());

			if (value != 0) {
				return value;
			}
		}

		List<Element> el1Elements = el1.elements();
		List<Element> el2Elements = el2.elements();

		if (el1Elements.size() < el2Elements.size()) {
			return -1;
		}
		else if (el1Elements.size() > el2Elements.size()) {
			return 1;
		}

		for (Element el : el1Elements) {
			int value = _compare(el2Elements, el, new ElementComparator());

			if (value != 0) {
				return value;
			}
		}

		return 0;
	}

	private int _compare(
		List<Attribute> list, Attribute obj, Comparator<Attribute> comparator) {

		int firstValue = -1;

		for (int i = 0; i < list.size(); i++) {
			Attribute o = list.get(i);

			int value = comparator.compare(obj, o);

			if (i == 0) {
				firstValue = value;
			}

			if (value == 0) {
				return 0;
			}
		}

		return firstValue;
	}

	private int _compare(
		List<Element> list, Element obj, Comparator<Element> comparator) {

		int firstValue = -1;

		for (int i = 0; i < list.size(); i++) {
			Element o = list.get(i);

			int value = comparator.compare(obj, o);

			if (i == 0) {
				firstValue = value;
			}

			if (value == 0) {
				return 0;
			}
		}

		return firstValue;
	}

}