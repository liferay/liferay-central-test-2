/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portlet.journal.TransformException;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * <a href="JournalXslUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class JournalXslUtil {

	public static String transform(
			Map tokens, String languageId, String xml, String script)
		throws TransformException {

		ByteArrayOutputStream outputBaos = new ByteArrayOutputStream();

		try {
			StreamSource xmlSource = new StreamSource(new StringReader(xml));
			StreamSource scriptSource = new StreamSource(
				new StringReader(script));

			TransformerFactory transformerFactory =
				TransformerFactory.newInstance();

			transformerFactory.setURIResolver(
				new URIResolver(tokens, languageId));

			Transformer transformer =
				transformerFactory.newTransformer(scriptSource);

			transformer.transform(xmlSource, new StreamResult(outputBaos));
		}
		catch (TransformerException te) {
			throw new TransformException(te.getMessage());
		}

		return outputBaos.toString();
	}

}