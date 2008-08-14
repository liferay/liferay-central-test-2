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

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;

import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="NodeImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class NodeImpl implements Node {

	public NodeImpl(org.dom4j.Node node) {
		_node = node;
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
		else {
			return new NodeImpl(node);
		}
	}

	public Node detach() {
		org.dom4j.Node node = _node.detach();

		if (node == null) {
			return null;
		}
		else {
			return new NodeImpl(node);
		}
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
		return toNewNodes(_node.selectNodes(xpathExpression));
	}

	public List<Node> selectNodes(
		String xpathExpression, String comparisonXPathExpression) {

		return toNewNodes(
			_node.selectNodes(xpathExpression, comparisonXPathExpression));
	}

	public List<Node> selectNodes(
		String xpathExpression, String comparisonXPathExpression,
		boolean removeDuplicates) {

		return toNewNodes(
			_node.selectNodes(
				xpathExpression, comparisonXPathExpression, removeDuplicates));
	}

	public Object selectObject(String xpathExpression) {
		Object obj = _node.selectObject(xpathExpression);

		if (obj == null) {
			return null;
		}
		else if (obj instanceof List) {
			return toNewNodes((List<org.dom4j.Node>)obj);
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

	public String valueOf(String xpathExpression) {
		return _node.valueOf(xpathExpression);
	}

	public void write(Writer writer) throws IOException {
		_node.write(writer);
	}

	protected List<Node> toNewNodes(List<org.dom4j.Node> oldNodes) {
		List<Node> newNodes = new ArrayList<Node>(oldNodes.size());

		for (org.dom4j.Node oldNode : oldNodes) {
			newNodes.add(new NodeImpl(oldNode));
		}

		return newNodes;
	}

	protected List<org.dom4j.Node> toOldNodes(List<Node> newNodes) {
		List<org.dom4j.Node> oldNodes = new ArrayList<org.dom4j.Node>(
			newNodes.size());

		for (Node newNode : newNodes) {
			NodeImpl newNodeImpl = (NodeImpl)newNode;

			oldNodes.add(newNodeImpl.getWrappedNode());
		}

		return oldNodes;
	}

	private org.dom4j.Node _node;

}