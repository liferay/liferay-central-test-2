/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.xml.simple;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.LinkedList;

/**
 * <a href="Element.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class Element {

	public Element(Element parentElement, String name) {
		this(parentElement, name, null);
	}

	public Element(Element parentElement, String name, String text) {
		if (parentElement._elementClosed) {
			throw new IllegalArgumentException("Parent element is closed");
		}

		_parentElement = parentElement;
		_name = name;
		_text = _formatText(text);
		_elementStack = parentElement._elementStack;
		_stringBundler = parentElement._stringBundler;

		_appendChildElement();
	}

	public Element(String name) {
		this(name, null);
	}

	public Element(String name, String text) {
		_name = name;
		_text = _formatText(text);
		_elementStack = new LinkedList<Element>();
		_stringBundler = new StringBundler();

		_stringBundler.append(_XML_HEADER);

		_openElement(this);
	}

	public void addAttribute(String name, String value) {
		if (_openTagClosed) {
			throw new IllegalStateException("Element is closed");
		}

		_stringBundler.append(StringPool.SPACE);
		_stringBundler.append(name);
		_stringBundler.append(_EQUAL_QUOTE);
		_stringBundler.append(value);
		_stringBundler.append(StringPool.QUOTE);
	}

	public Element addElement(String name) {
		return addElement(name, null);
	}

	public Element addElement(String name, boolean text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, double text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, float text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, int text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, long text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, Object text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, short text) {
		return addElement(name, String.valueOf(text));
	}

	public Element addElement(String name, String text) {
		return new Element(this, name, text);
	}

	public String getName() {
		return _name;
	}

	public Element getParentElement() {
		return _parentElement;
	}

	public String getText() {
		return _text;
	}

	public boolean isRootElement() {
		if (_parentElement == null) {
			return true;
		}
		else {
			return false;
		}
	}

	public String toXMLString() {
		if (_parentElement != null) {
			throw new IllegalStateException(
				"XML string can only generated from a root element");
		}

		if (_xmlString == null) {
			_flushPendingOpenElements();

			_xmlString = _stringBundler.toString();
		}

		return _xmlString;
	}

	private void _appendChildElement() {
		Element topElement = _elementStack.getLast();

		while ((topElement != _parentElement) && (topElement != null)) {

			// Close previous sibling elements

			_closeElement(topElement);

			_elementStack.removeLast();

			topElement = _elementStack.getLast();
		}

		if (topElement == _parentElement) {

			// Append current element to its parent

			_closeOpenTag(topElement);

			_openElement(this);
		}
		else {
			throw new IllegalArgumentException(
				"The parent element does not exist");
		}
	}

	private void _closeElement(Element element) {
		_closeOpenTag(element);

		_stringBundler.append(_CLOSE_PRE);
		_stringBundler.append(element._name);
		_stringBundler.append(_CLOSE_POST);

		element._elementClosed = true;
	}

	private void _closeOpenTag(Element element) {
		if (element._openTagClosed == false) {
			_stringBundler.append(_OPEN_POST);

			if (element._text != null) {
				_stringBundler.append(element._text);
			}

			element._openTagClosed = true;
		}
	}

	private void _flushPendingOpenElements() {
		while (_elementStack.size() > 0) {
			_closeElement(_elementStack.removeLast());
		}
	}

	private String _formatText(String text) {
		return HtmlUtil.escape(text);
	}

	private void _openElement(Element element) {
		_stringBundler.append(_OPEN_PRE).append(element._name);

		_elementStack.addLast(element);
	}

	private static final String _CLOSE_POST = ">";

	private static final String _CLOSE_PRE = "</";

	private static final String _EQUAL_QUOTE = "=\"";

	private static final String _OPEN_POST = ">";

	private static final String _OPEN_PRE = "<";

	private static final String _XML_HEADER =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

	private boolean _elementClosed;
	private LinkedList<Element> _elementStack;
	private String _name;
	private boolean _openTagClosed;
	private Element _parentElement;
	private StringBundler _stringBundler;
	private String _text;
	private String _xmlString;

}