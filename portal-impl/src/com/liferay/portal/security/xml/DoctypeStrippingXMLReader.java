/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.xml;

import java.io.FilterInputStream;
import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/**
 * @author Tomas Polesovsky
 */
public class DoctypeStrippingXMLReader implements XMLReader {

	public DoctypeStrippingXMLReader(XMLReader _xmlReader) {
		this._xmlReader = _xmlReader;
	}

	@Override
	public boolean getFeature(String name)
		throws SAXNotRecognizedException, SAXNotSupportedException {

		return _xmlReader.getFeature(name);
	}

	@Override
	public void setFeature(String name, boolean value)
		throws SAXNotRecognizedException, SAXNotSupportedException {

		if (_FEATURES_DISALLOW_DTD.equals(name)) {
			_disallowDTD = value;
		}

		_xmlReader.setFeature(name, value);
	}

	@Override
	public Object getProperty(String name)
		throws SAXNotRecognizedException, SAXNotSupportedException {

		return _xmlReader.getProperty(name);
	}

	@Override
	public void setProperty(String name, Object value)
		throws SAXNotRecognizedException, SAXNotSupportedException {

		_xmlReader.setProperty(name, value);
	}

	@Override
	public void setEntityResolver(EntityResolver resolver) {
		_xmlReader.setEntityResolver(resolver);
	}

	@Override
	public EntityResolver getEntityResolver() {
		return _xmlReader.getEntityResolver();
	}

	@Override
	public void setDTDHandler(DTDHandler handler) {
		_xmlReader.setDTDHandler(handler);
	}

	@Override
	public DTDHandler getDTDHandler() {
		return _xmlReader.getDTDHandler();
	}

	@Override
	public void setContentHandler(ContentHandler handler) {
		_xmlReader.setContentHandler(handler);
	}

	@Override
	public ContentHandler getContentHandler() {
		return _xmlReader.getContentHandler();
	}

	@Override
	public void setErrorHandler(ErrorHandler handler) {
		_xmlReader.setErrorHandler(handler);
	}

	@Override
	public ErrorHandler getErrorHandler() {
		return _xmlReader.getErrorHandler();
	}

	@Override
	public void parse(InputSource input) throws IOException, SAXException {
		if (_disallowDTD) {
			final InputStream byteStream = input.getByteStream();

			if (byteStream != null) {
				final SimpleDoctypeStrippingFilter filter =
					new SimpleDoctypeStrippingFilter(byteStream);

				input.setByteStream(new FilterInputStream(byteStream) {
					@Override
					public int read() throws IOException {
						return filter.read();
					}

					@Override
					public int read(byte[] b, int off, int len)
						throws IOException {

						return filter.read(b, off, len);
					}
				});
			}

			Reader characterStream = input.getCharacterStream();

			if (characterStream != null) {
				final SimpleDoctypeStrippingFilter filter =
					new SimpleDoctypeStrippingFilter(characterStream);

				input.setCharacterStream(
					new FilterReader(characterStream) {
						@Override
						public int read() throws IOException {
							return filter.read();
						}

						@Override
						public int read(char[] cbuf, int off, int len)
							throws IOException {

							return filter.read(cbuf, off, len);
						}
					});
			}
		}

		_xmlReader.parse(input);
	}

	@Override
	public void parse(String systemId) throws IOException, SAXException {
		_xmlReader.parse(systemId);
	}

	private static final String _FEATURES_DISALLOW_DTD =
		"http://apache.org/xml/features/disallow-doctype-decl";

	private boolean _disallowDTD;
	private XMLReader _xmlReader;

}