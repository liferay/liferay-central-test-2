/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.DiffHtml;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.Reader;
import java.io.StringWriter;

import java.util.Locale;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.outerj.daisy.diff.HtmlCleaner;
import org.outerj.daisy.diff.XslFilter;
import org.outerj.daisy.diff.html.HTMLDiffer;
import org.outerj.daisy.diff.html.HtmlSaxDiffOutput;
import org.outerj.daisy.diff.html.TextNodeComparator;
import org.outerj.daisy.diff.html.dom.DomTreeBuilder;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.AttributesImpl;

/**
 * <a href="DiffHtmlImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This class can compare two different versions of html code. This class
 * detects changes to an entire html page such as removal or addition of
 * characters or images.
 * </p>
 *
 * @author Julio Camarero
 *
 */
public class DiffHtmlImpl implements DiffHtml {

	/**
	 * This is a diff method with default values.
	 *
	 * @param		source the <code>Reader</code> of the source text, this can
	 *				be for example, an instance of FileReader or StringReader
	 * @param		target the <code>Reader</code> of the target text
	 * @return		an string containing the html code of the source text
	 *				showing the differences regarding the target text
	 */
	public String diff(Reader source, Reader target) throws Exception {

		InputSource oldSource = new InputSource(source);
		InputSource newSource = new InputSource(target);

		String prefix = "diff";
		Locale locale = LocaleUtil.getDefault();

		StringWriter writer = new StringWriter();

		SAXTransformerFactory tf =
			(SAXTransformerFactory) TransformerFactory.newInstance();

		TransformerHandler handler = tf.newTransformerHandler();

		handler.setResult(new StreamResult(writer));

		XslFilter filter = new XslFilter();

		ContentHandler postProcess = filter.xsl(
			handler, "com/liferay/portal/util/dependencies/diff_html.xsl");

		HtmlCleaner cleaner = new HtmlCleaner();

		DomTreeBuilder oldHandler = new DomTreeBuilder();

		cleaner.cleanAndParse(oldSource, oldHandler);

		TextNodeComparator leftComparator = new TextNodeComparator(
			oldHandler, locale);

		DomTreeBuilder newHandler = new DomTreeBuilder();

		cleaner.cleanAndParse(newSource, newHandler);

		TextNodeComparator rightComparator = new TextNodeComparator(
			newHandler, locale);

		postProcess.startDocument();
		postProcess.startElement(
			"", "diffreport", "diffreport",new AttributesImpl());

		postProcess.startElement(
			"", "diff", "diff", new AttributesImpl());
		HtmlSaxDiffOutput output = new HtmlSaxDiffOutput(
			postProcess, prefix);

		HTMLDiffer differ = new HTMLDiffer(output);

		differ.diff(leftComparator, rightComparator);

		postProcess.endElement("", "diff", "diff");
		postProcess.endElement("", "diffreport", "diffreport");
		postProcess.endDocument();

		writer.flush();
		return writer.toString();
	}

}