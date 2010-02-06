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

package com.liferay.portal.kernel.xml;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="Branch.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface Branch extends Node {

	public void add(Comment comment);

	public void add(Element element);

	public void add(Node node);

	public void add(ProcessingInstruction processingInstruction);

	public Element addElement(QName qName);

	public Element addElement(String name);

	public Element addElement(String qualifiedName, String namespaceURI);

	public void appendContent(Branch branch);

	public void clearContent();

	public List<Node> content();

	public Element elementByID(String elementID);

	public int indexOf(Node node);

	public Node node(int index);

	public int nodeCount();

	public Iterator<Node> nodeIterator();

	public void normalize();

	public ProcessingInstruction processingInstruction(String target);

	public List<ProcessingInstruction> processingInstructions();

	public List<ProcessingInstruction> processingInstructions(String target);

	public boolean remove(Comment comment);

	public boolean remove(Element element);

	public boolean remove(Node node);

	public boolean remove(ProcessingInstruction processingInstruction);

	public boolean removeProcessingInstruction(String target);

	public void setContent(List<Node> content);

	public void setProcessingInstructions(
		List<ProcessingInstruction> processingInstructions);

}