/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.Visitor;

import java.io.IOException;
import java.io.Writer;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class NodeImpl implements Node {

	public NodeImpl(org.dom4j.Node node) {
		_node = node;
	}

	public <T, V extends Visitor<T>> T accept(V v) {
		return v.visitNode(this);
	}

	public String asXML() {
		return _node.asXML();
	}

	public Node asXPathResult(Element parent) {
		ElementImpl parentImpl = (ElementImpl)parent;

		org.dom4j.Node node = _node.asXPathResult(
			parentImpl.getWrappedElement());

		if (node == null) {
			return null;
		}
		if (node instanceof org.dom4j.Element) {
			return new ElementImpl((org.dom4j.Element)node);
		}
		else {
			return new NodeImpl(node);
		}
	}

	public Node detach() {
		org.dom4j.Node node = _node.detach();

		if (node == null) {
			return null;
		}
		if (node instanceof org.dom4j.Element) {
			return new ElementImpl((org.dom4j.Element)node);
		}
		else {
			return new NodeImpl(node);
		}
	}

	public boolean equals(Object obj) {
		org.dom4j.Node node = ((NodeImpl)obj).getWrappedNode();

		return _node.equals(node);
	}

	public Document getDocument() {
		org.dom4j.Document document = _node.getDocument();

		if (document == null) {
			return null;
		}
		else {
			return new DocumentImpl(document);
		}
	}

	public String getName() {
		return _node.getName();
	}

	public Element getParent() {
		org.dom4j.Element element = _node.getParent();

		if (element == null) {
			return null;
		}
		else {
			return new ElementImpl(element);
		}
	}

	public String getPath() {
		return _node.getPath();
	}

	public String getPath(Element context) {
		ElementImpl contextImpl = (ElementImpl)context;

		return _node.getPath(contextImpl.getWrappedElement());
	}

	public String getStringValue() {
		return _node.getStringValue();
	}

	public String getText() {
		return _node.getText();
	}

	public String getUniquePath() {
		return _node.getUniquePath();
	}

	public String getUniquePath(Element context) {
		ElementImpl contextImpl = (ElementImpl)context;

		return _node.getUniquePath(contextImpl.getWrappedElement());
	}

	public org.dom4j.Node getWrappedNode() {
		return _node;
	}

	public boolean hasContent() {
		return _node.hasContent();
	}

	public int hashCode() {
		return _node.hashCode();
	}

	public boolean isReadOnly() {
		return _node.isReadOnly();
	}

	public boolean matches(String xpathExpression) {
		return _node.matches(xpathExpression);
	}

	public Number numberValueOf(String xpathExpression) {
		return _node.numberValueOf(xpathExpression);
	}

	public List<Node> selectNodes(String xpathExpression) {
		return SAXReaderImpl.toNewNodes(_node.selectNodes(xpathExpression));
	}

	public List<Node> selectNodes(
		String xpathExpression, String comparisonXPathExpression) {

		return SAXReaderImpl.toNewNodes(
			_node.selectNodes(xpathExpression, comparisonXPathExpression));
	}

	public List<Node> selectNodes(
		String xpathExpression, String comparisonXPathExpression,
		boolean removeDuplicates) {

		return SAXReaderImpl.toNewNodes(
			_node.selectNodes(
				xpathExpression, comparisonXPathExpression, removeDuplicates));
	}

	public Object selectObject(String xpathExpression) {
		Object obj = _node.selectObject(xpathExpression);

		if (obj == null) {
			return null;
		}
		else if (obj instanceof List<?>) {
			return SAXReaderImpl.toNewNodes((List<org.dom4j.Node>)obj);
		}
		else {
			return obj;
		}
	}

	public Node selectSingleNode(String xpathExpression) {
		org.dom4j.Node node = _node.selectSingleNode(xpathExpression);

		if (node == null) {
			return null;
		}
		if (node instanceof org.dom4j.Element) {
			return new ElementImpl((org.dom4j.Element)node);
		}
		else {
			return new NodeImpl(node);
		}
	}

	public void setName(String name) {
		_node.setName(name);
	}

	public void setText(String text) {
		_node.setText(text);
	}

	public boolean supportsParent() {
		return _node.supportsParent();
	}

	public String toString() {
		return _node.toString();
	}

	public String valueOf(String xpathExpression) {
		return _node.valueOf(xpathExpression);
	}

	public void write(Writer writer) throws IOException {
		_node.write(writer);
	}

	private org.dom4j.Node _node;

}