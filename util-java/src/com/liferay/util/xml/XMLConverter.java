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

package com.liferay.util.xml;

/**
 * <a href="XMLConverter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class XMLConverter {

	public static org.w3c.dom.Document toW3CDocument(
			org.dom4j.Document dom4jDoc)
		throws org.dom4j.DocumentException {

		org.dom4j.io.DOMWriter dom4jWriter = new org.dom4j.io.DOMWriter();

		org.w3c.dom.Document w3cDoc = dom4jWriter.write(dom4jDoc);

		return w3cDoc;
	}

	public static org.w3c.dom.Element toW3CElement(org.dom4j.Element dom4jEl)
		throws org.dom4j.DocumentException {

		org.dom4j.Document dom4jDoc =
			org.dom4j.DocumentFactory.getInstance().createDocument();

		dom4jDoc.setRootElement(dom4jEl.createCopy());

		org.w3c.dom.Document w3cDoc = toW3CDocument(dom4jDoc);

		return w3cDoc.getDocumentElement();
	}

	public static javax.xml.namespace.QName toJavaxQName(
		org.dom4j.QName dom4jQName) {

		javax.xml.namespace.QName javaxQName = new javax.xml.namespace.QName(
			dom4jQName.getNamespaceURI(), dom4jQName.getName(),
			dom4jQName.getNamespacePrefix());

		return javaxQName;
	}

}