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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.Branch;
import com.liferay.portal.kernel.xml.Comment;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.ProcessingInstruction;
import com.liferay.portal.kernel.xml.QName;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="BranchImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BranchImpl extends NodeImpl implements Branch {

	public BranchImpl(org.dom4j.Branch branch) {
		super(branch);

		_branch = branch;
	}

	public void add(Comment comment) {
		CommentImpl commentImpl = (CommentImpl)comment;

		_branch.add(commentImpl.getWrappedComment());
	}

	public void add(Element element) {
		ElementImpl elementImpl = (ElementImpl)element;

		_branch.add(elementImpl.getWrappedElement());
	}

	public void add(Node node) {
		NodeImpl nodeImpl = (NodeImpl)node;

		_branch.add(nodeImpl.getWrappedNode());
	}

	public void add(ProcessingInstruction processingInstruction) {
		ProcessingInstructionImpl processingInstructionImpl =
			(ProcessingInstructionImpl)processingInstruction;

		_branch.add(
			processingInstructionImpl.getWrappedProcessingInstruction());
	}

	public Element addElement(QName qName) {
		QNameImpl qNameImpl = (QNameImpl)qName;

		return new ElementImpl(_branch.addElement(qNameImpl.getWrappedQName()));
	}

	public Element addElement(String name) {
		return new ElementImpl(_branch.addElement(name));
	}

	public Element addElement(String qualifiedName, String namespaceURI) {
		return new ElementImpl(_branch.addElement(qualifiedName, namespaceURI));
	}

	public void appendContent(Branch branch) {
		BranchImpl branchImpl = (BranchImpl)branch;

		_branch.appendContent(branchImpl.getWrappedBranch());
	}

	public void clearContent() {
		_branch.clearContent();
	}

	public List<Node> content() {
		return SAXReaderImpl.toNewNodes(_branch.content());
	}

	public Element elementByID(String elementID) {
		return new ElementImpl(_branch.elementByID(elementID));
	}

	public boolean equals(Object obj) {
		org.dom4j.Branch branch = ((BranchImpl)obj).getWrappedBranch();

		return _branch.equals(branch);
	}

	public org.dom4j.Branch getWrappedBranch() {
		return _branch;
	}

	public int hashCode() {
		return _branch.hashCode();
	}

	public int indexOf(Node node) {
		NodeImpl nodeImpl = (NodeImpl)node;

		return _branch.indexOf(nodeImpl.getWrappedNode());
	}

	public Node node(int index) {
		org.dom4j.Node node = _branch.node(index);

		if (node == null) {
			return null;
		}
		else {
			if (node instanceof org.dom4j.Element) {
				return new ElementImpl((org.dom4j.Element)node);
			}
			else {
				return new NodeImpl(node);
			}
		}
	}

	public int nodeCount() {
		return _branch.nodeCount();
	}

	public Iterator<Node> nodeIterator() {
		return content().iterator();
	}

	public void normalize() {
		_branch.normalize();
	}

	public ProcessingInstruction processingInstruction(String target) {
		org.dom4j.ProcessingInstruction processingInstruction =
			_branch.processingInstruction(target);

		if (processingInstruction == null) {
			return null;
		}
		else {
			return new ProcessingInstructionImpl(processingInstruction);
		}
	}

	public List<ProcessingInstruction> processingInstructions() {
		return SAXReaderImpl.toNewProcessingInstructions(
			_branch.processingInstructions());
	}

	public List<ProcessingInstruction> processingInstructions(String target) {
		return SAXReaderImpl.toNewProcessingInstructions(
			_branch.processingInstructions(target));
	}

	public boolean remove(Comment comment) {
		CommentImpl commentImpl = (CommentImpl)comment;

		return _branch.remove(commentImpl.getWrappedComment());
	}

	public boolean remove(Element element) {
		ElementImpl elementImpl = (ElementImpl)element;

		return _branch.remove(elementImpl.getWrappedElement());
	}

	public boolean remove(Node node) {
		NodeImpl nodeImpl = (NodeImpl)node;

		return _branch.remove(nodeImpl.getWrappedNode());
	}

	public boolean remove(ProcessingInstruction processingInstruction) {
		ProcessingInstructionImpl processingInstructionImpl =
			(ProcessingInstructionImpl)processingInstruction;

		return _branch.remove(
			processingInstructionImpl.getWrappedProcessingInstruction());
	}

	public boolean removeProcessingInstruction(String target) {
		return _branch.removeProcessingInstruction(target);
	}

	public void setContent(List<Node> content) {
		_branch.setContent(SAXReaderImpl.toOldNodes(content));
	}

	public void setProcessingInstructions(
		List<ProcessingInstruction> processingInstructions) {

		_branch.setProcessingInstructions(
			SAXReaderImpl.toOldProcessingInstructions(processingInstructions));
	}

	private org.dom4j.Branch _branch;

}