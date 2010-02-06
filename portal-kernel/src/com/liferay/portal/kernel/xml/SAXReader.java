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

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;
import java.util.Map;

/**
 * <a href="SAXReader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface SAXReader {

	public Attribute createAttribute(
		Element element, QName qName, String value);

	public Attribute createAttribute(
		Element element, String name, String value);

	public Document createDocument();

	public Document createDocument(Element rootElement);

	public Document createDocument(String encoding);

	public Element createElement(QName qName);

	public Element createElement(String name);

	public Entity createEntity(String name, String text);

	public ProcessingInstruction createProcessingInstruction(
		String target, Map<String, String> data);

	public ProcessingInstruction createProcessingInstruction(
		String target, String data);

	public Namespace createNamespace(String uri);

	public Namespace createNamespace(String prefix, String uri);

	public QName createQName(String localName);

	public QName createQName(String localName, Namespace namespace);

	public Text createText(String text);

	public XPath createXPath(String xpathExpression);

	public Document read(File file) throws DocumentException;

	public Document read(File file, boolean validate)
		throws DocumentException;

	public Document read(InputStream is) throws DocumentException;

	public Document read(InputStream is, boolean validate)
		throws DocumentException;

	public Document read(Reader reader) throws DocumentException;

	public Document read(Reader reader, boolean validate)
		throws DocumentException;

	public Document read(String xml) throws DocumentException;

	public Document read(String xml, boolean validate)
		throws DocumentException;

	public Document read(URL url) throws DocumentException;

	public Document read(URL url, boolean validate) throws DocumentException;

	public Document readURL(String url)
		throws DocumentException, MalformedURLException;

	public Document readURL(String url, boolean validate)
		throws DocumentException, MalformedURLException;

	public List<Node> selectNodes(
		String xpathFilterExpression, List<Node> nodes);

	public List<Node> selectNodes(String xpathFilterExpression, Node node);

	public void sort(List<Node> nodes, String xpathExpression);

	public void sort(
		List<Node> nodes, String xpathExpression, boolean distinct);

}