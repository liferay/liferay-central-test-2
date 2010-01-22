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

package com.liferay.portal.kernel.xml;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.LinkedList;

/**
 * <a href="BuilderElement.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class BuilderElement {

	public BuilderElement(String name) {
		this(name, null);
	}

	public BuilderElement(String name, String text) {
		this.name = name;
		this.text = formatText(text);
		elementStack = new LinkedList<BuilderElement>();
		stringBundler = new StringBundler();

		stringBundler.append(XML_HEADER);
		openElement(this);
	}

	public BuilderElement(BuilderElement parentElement, String name) {
		this(parentElement, name, null);
	}

	public BuilderElement(
		BuilderElement parentElement, String name, String text) {

		if (parentElement.elementClosed) {
			throw new IllegalArgumentException(
				"Append child to a already closed parent.");
		}

		this.parentElement = parentElement;
		this.name = name;
		this.text = formatText(text);
		elementStack = parentElement.elementStack;
		stringBundler = parentElement.stringBundler;
		appendChildElement();
	}

	public void addAttribute(String name, String value) {
		if (openTagClosed) {
			throw new IllegalStateException(
				"Adding attribute to a closed element");
		}
		stringBundler.append(StringPool.SPACE).
			append(name).
			append(StringPool.EQUAL).
			append(StringPool.QUOTE).
			append(value).
			append(StringPool.QUOTE);
	}

	public String getName() {
		return name;
	}

	public BuilderElement getParent() {
		return parentElement;
	}

	public String getText() {
		return text;
	}

	public boolean isRootElement() {
		return parentElement == null;
	}

	public String toXMLString() {
		if (parentElement != null) {
			throw new IllegalStateException(
				"Generate xml string from non-root element.");
		}

		if (xmlString == null) {
			flushPendingOpenElements();
			xmlString = stringBundler.toString();
		}

		return xmlString;
	}

	protected void appendChildElement() {
		BuilderElement top = elementStack.getLast();
		while (top != parentElement && top != null) {
			// close previous sibling elements
			closeElement(top);
			elementStack.removeLast();
			top = elementStack.getLast();
		}

		if (top == parentElement) {
			// append current element to its parent
			closeOpenTag(top);
			openElement(this);
		}
		else {
			throw new IllegalArgumentException(
				"The parent element does not exist!");
		}
	}

	protected void closeElement(BuilderElement element) {
		closeOpenTag(element);
		stringBundler.append(CLOSE_PRE).
			append(element.name).
			append(CLOSE_POST);
		element.elementClosed = true;
	}

	protected void closeOpenTag(BuilderElement element) {
		if (element.openTagClosed == false) {
			stringBundler.append(OPEN_POST);
			if (element.text != null) {
				stringBundler.append(element.text);
			}
			element.openTagClosed = true;
		}
	}

	protected void flushPendingOpenElements() {

		while (elementStack.size() > 0) {
			closeElement(elementStack.removeLast());
		}
	}

	protected String formatText(String text) {
		return HtmlUtil.escape(text);
	}

	protected void openElement(BuilderElement element) {
		stringBundler.append(OPEN_PRE).append(element.name);

		elementStack.addLast(element);
	}

	protected boolean elementClosed;
	// The max length of this link equals to xml file's depth
	protected LinkedList<BuilderElement> elementStack;
	protected String name;
	protected boolean openTagClosed;
	protected BuilderElement parentElement;
	protected StringBundler stringBundler;
	protected String text;
	protected String xmlString;

	public final static String CLOSE_POST = ">";
	public final static String CLOSE_PRE = "</";
	public final static String OPEN_POST = ">";
	public final static String OPEN_PRE = "<";
	public final static String XML_HEADER =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

}