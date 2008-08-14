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

import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.Node;

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

	public Element addAttribute(String name, String value) {
		return new ElementImpl(_el.addAttribute(name, value));
	}

	public Element addCDATA(String cdata) {
		return new ElementImpl(_el.addCDATA(cdata));
	}

	public Element addComment(String comment) {
		return new ElementImpl(_el.addComment(comment));
	}

	public Element addEntity(String name, String text) {
		return new ElementImpl(_el.addEntity(name, text));
	}

	public Element addElement(String name) {
		return new ElementImpl(_el.addElement(name));
	}

	public Element addText(String text) {
		return new ElementImpl(_el.addText(text));
	}

	public Attribute attribute(String name) {
		org.dom4j.Attribute attribute = _el.attribute(name);

		if (attribute == null) {
			return null;
		}
		else {
			return new AttributeImpl(attribute);
		}
	}

	public String attributeValue(String name) {
		return _el.attributeValue(name);
	}

	public String attributeValue(String name, String defaultValue) {
		return _el.attributeValue(name, defaultValue);
	}

	public void clearContent() {
		_el.clearContent();
	}

	public Node detach() {
		return new NodeImpl(_el.detach());
	}

	public org.dom4j.Element getElement() {
		return _el;
	}

	public String getName() {
		return _el.getName();
	}

	public Namespace getNamespace() {
		org.dom4j.Namespace namespace = _el.getNamespace();

		if (namespace == null) {
			return null;
		}
		else {
			return new NamespaceImpl(namespace);
		}
	}

	public Namespace getNamespaceForPrefix(String prefix) {
		org.dom4j.Namespace namespace = _el.getNamespaceForPrefix(prefix);

		if (namespace == null) {
			return null;
		}
		else {
			return new NamespaceImpl(namespace);
		}
	}

	public Namespace getNamespaceForURI(String uri) {
		org.dom4j.Namespace namespace = _el.getNamespaceForURI(uri);

		if (namespace == null) {
			return null;
		}
		else {
			return new NamespaceImpl(namespace);
		}
	}

	public String getNamespacePrefix() {
		return _el.getNamespacePrefix();
	}

	public List<Namespace> getNamespacesForURI(String uri) {
		return toNewNamespaces(_el.getNamespacesForURI(uri));
	}

	public String getNamespaceURI() {
		return _el.getNamespaceURI();
	}

	public String getText() {
		return _el.getText();
	}

	public String getTextTrim() {
		return _el.getTextTrim();
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

	public boolean remove(Element el) {
		ElementImpl elImpl = (ElementImpl)el;

		return _el.remove(elImpl.getElement());
	}

	public void setText(String text) {
		_el.setText(text);
	}

	protected List<Element> toNewElements(List<org.dom4j.Element> oldElements) {
		List<Element> newElements = new ArrayList<Element>(oldElements.size());

		for (org.dom4j.Element oldEl : oldElements) {
			newElements.add(new ElementImpl(oldEl));
		}

		return newElements;
	}

	protected List<Namespace> toNewNamespaces(
		List<org.dom4j.Namespace> oldNamespaces) {

		List<Namespace> newNamespaces = new ArrayList<Namespace>(
			oldNamespaces.size());

		for (org.dom4j.Namespace oldNamespace : oldNamespaces) {
			newNamespaces.add(new NamespaceImpl(oldNamespace));
		}

		return newNamespaces;
	}

	private org.dom4j.Element _el;

}