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

package com.liferay.portal.kernel.xml;

import java.util.List;

/**
 * <a href="Element.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface Element {

	public Element addAttribute(String name, String value);

	public Element addCDATA(String cdata);

	public Element addComment(String comment);

	public Element addEntity(String name, String text);

	public Element addElement(String name);

	public Element addText(String text);

	public Attribute attribute(String name);

	public String attributeValue(String name);

	public String attributeValue(String name, String defaultValue);

	public void clearContent();

	public Node detach();

	public String getName();

	public Namespace getNamespace();

	public Namespace getNamespaceForPrefix(String prefix);

	public Namespace getNamespaceForURI(String uri);

	public String getNamespacePrefix();

	public List<Namespace> getNamespacesForURI(String uri);

	public String getNamespaceURI();

	public String getText();

	public String getTextTrim();

	public Element element(String name);

	public List<Element> elements();

	public List<Element> elements(String name);

	public String elementText(String name);

	public boolean remove(Element el);

	public void setText(String text);

}