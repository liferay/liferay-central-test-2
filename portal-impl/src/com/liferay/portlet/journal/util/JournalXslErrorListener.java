/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.util;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;

import org.apache.xml.res.XMLErrorResources;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xml.utils.WrappedRuntimeException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * <a href="JournalXslErrorListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class JournalXslErrorListener implements ErrorListener {

	public void error(TransformerException exception)
			throws TransformerException {

		setLocation(exception);

		throw exception;
	}

	public void fatalError(TransformerException exception)
			throws TransformerException {

		setLocation(exception);

		throw exception;
	}

	public void warning(TransformerException exception)
			throws TransformerException {

		setLocation(exception);

		throw exception;
	}

	public void setLocation(Throwable exception) {
		SourceLocator locator = null;
		Throwable cause = exception;
		Throwable realCause = null;

		do {
			if(cause instanceof SAXParseException) {
				locator = new SAXSourceLocator((SAXParseException)cause);
				realCause = cause;
			}
			else if (cause instanceof TransformerException) {
				SourceLocator causeLocator =
					((TransformerException)cause).getLocator();

				if(causeLocator != null) {
					locator = causeLocator;
					realCause = cause;
				}
			}

			if(cause instanceof TransformerException)
				cause = ((TransformerException)cause).getCause();
			else if(cause instanceof WrappedRuntimeException)
				cause = ((WrappedRuntimeException)cause).getException();
			else if(cause instanceof SAXException)
				cause = ((SAXException)cause).getException();
			else
				cause = null;
		}
		while(cause != null);

		_message = realCause.getMessage();

		if(null != locator) {
			_location = XMLMessages.createXMLMessage("line", null) +
				locator.getLineNumber() + "; " +
					XMLMessages.createXMLMessage("column", null) +
						locator.getColumnNumber() + "; ";
		}
		else {
			_location = "";
		}
	}

	public String getLocation() {
		return _location;
	}

	public String getMessage() {
		return _message;
	}

	public String getMessageAndLocation() {
		return _message + " " + _location;
	}

	private String _location = null;
	private String _message = null;

}
