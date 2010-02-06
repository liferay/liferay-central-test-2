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

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;

/**
 * <a href="DocumentImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DocumentImpl extends BranchImpl implements Document {

	public DocumentImpl(org.dom4j.Document document) {
		super(document);

		_document = document;
	}

	public Document addComment(String comment) {
		_document.addComment(comment);

		return this;
	}

	public Document addDocType(String name, String publicId, String systemId) {
		_document.addDocType(name, publicId, systemId);

		return this;
	}

	public boolean equals(Object obj) {
		org.dom4j.Document document = ((DocumentImpl)obj).getWrappedDocument();

		return _document.equals(document);
	}

	public String formattedString() throws IOException {
		return XMLFormatter.toString(_document);
	}

	public String formattedString(String indent) throws IOException {
		return XMLFormatter.toString(_document, indent);
	}

	public String formattedString(String indent, boolean expandEmptyElements)
		throws IOException {

		return XMLFormatter.toString(_document, indent, expandEmptyElements);
	}

	public Element getRootElement() {
		return new ElementImpl(_document.getRootElement());
	}

	public org.dom4j.Document getWrappedDocument() {
		return _document;
	}

	public String getXMLEncoding() {
		return _document.getXMLEncoding();
	}

	public int hashCode() {
		return _document.hashCode();
	}

	public void setRootElement(Element rootElement) {
		ElementImpl rootElementImpl = (ElementImpl)rootElement;

		_document.setRootElement(rootElementImpl.getWrappedElement());
	}

	public void setXMLEncoding(String encoding) {
		_document.setXMLEncoding(encoding);
	}

	private org.dom4j.Document _document;

}