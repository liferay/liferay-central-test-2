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

import java.io.IOException;
import java.io.Writer;

import java.util.List;

/**
 * <a href="Node.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface Node {

	public String asXML();

	public Node asXPathResult(Element parent);

	public Node detach();

	public Document getDocument();

	public String getName();

	public Element getParent();

	public String getPath();

	public String getPath(Element context);

	public String getStringValue();

	public String getText();

	public String getUniquePath();

	public String getUniquePath(Element context);

	public boolean hasContent();

	public boolean isReadOnly();

	public boolean matches(String xpathExpression);

	public Number numberValueOf(String xpathExpression);

	public List<Node> selectNodes(String xpathExpression);

	public List<Node> selectNodes(
		String xpathExpression, String comparisonXPathExpression);

	public List<Node> selectNodes(
		String xpathExpression, String comparisonXPathExpression,
		boolean removeDuplicates);

	public Object selectObject(String xpathExpression);

	public Node selectSingleNode(String xpathExpression);

	public void setName(String name);

	public void setText(String text);

	public boolean supportsParent();

	public String valueOf(String xpathExpression);

	public void write(Writer writer) throws IOException;

}