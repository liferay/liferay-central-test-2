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
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.util.EntityResolver;
import com.liferay.util.xml.XMLSafeReader;

import java.io.File;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.DocumentHelper;

/**
 * <a href="SAXReaderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SAXReaderImpl implements SAXReader {

	public Document createDocument() {
		return new DocumentImpl(DocumentHelper.createDocument());
	}

	public Element createElement(String name) {
		return new ElementImpl(DocumentHelper.createElement(name));
	}

	public Document read(File file) throws DocumentException {
		return read(file, false);
	}

	public Document read(File file, boolean validate)
		throws DocumentException {

		try {
			org.dom4j.io.SAXReader saxReader = getSAXReader(validate);

			return new DocumentImpl(saxReader.read(file));
		}
		catch (org.dom4j.DocumentException de) {
			throw new DocumentException(de.getMessage());
		}
	}

	public Document read(Reader reader) throws DocumentException {
		return read(reader, false);
	}

	public Document read(Reader reader, boolean validate)
		throws DocumentException {

		try {
			org.dom4j.io.SAXReader saxReader = getSAXReader(validate);

			return new DocumentImpl(saxReader.read(reader));
		}
		catch (org.dom4j.DocumentException de) {
			throw new DocumentException(de.getMessage());
		}
	}

	public Document read(String xml) throws DocumentException {
		return read(new XMLSafeReader(xml));
	}

	public Document read(String xml, boolean validate)
		throws DocumentException {

		return read(new XMLSafeReader(xml), validate);
	}

	protected org.dom4j.io.SAXReader getSAXReader(boolean validate) {

		// Crimson cannot do XSD validation. See the following links:
		//
		// http://www.geocities.com/herong_yang/jdk/xsd_validation.html
		// http://www.burnthacker.com/archives/000086.html
		// http://www.theserverside.com/news/thread.tss?thread_id=22525

		org.dom4j.io.SAXReader reader = null;

		try {
			reader = new org.dom4j.io.SAXReader(_SAX_PARSER_IMPL, validate);

			reader.setEntityResolver(new EntityResolver());

			reader.setFeature(_FEATURES_VALIDATION, validate);
			reader.setFeature(_FEATURES_VALIDATION_SCHEMA, validate);
			reader.setFeature(
				_FEATURES_VALIDATION_SCHEMA_FULL_CHECKING, validate);
			reader.setFeature(_FEATURES_DYNAMIC, validate);
		}
		catch (Exception e) {
			_log.warn("XSD validation is diasabled because " + e.getMessage());

			reader = new org.dom4j.io.SAXReader(validate);

			reader.setEntityResolver(new EntityResolver());
		}

		return reader;
	}

	private static final String _SAX_PARSER_IMPL =
		"org.apache.xerces.parsers.SAXParser";

	private static final String _FEATURES_VALIDATION =
		"http://xml.org/sax/features/validation";

	private static final String _FEATURES_VALIDATION_SCHEMA =
		"http://apache.org/xml/features/validation/schema";

	private static final String _FEATURES_VALIDATION_SCHEMA_FULL_CHECKING =
		"http://apache.org/xml/features/validation/schema-full-checking";

	private static final String _FEATURES_DYNAMIC =
		"http://apache.org/xml/features/validation/dynamic";

	private static Log _log = LogFactory.getLog(SAXReaderImpl.class);

}