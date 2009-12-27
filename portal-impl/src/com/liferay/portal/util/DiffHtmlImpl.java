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

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.DiffHtml;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Reader;

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
 * This class can compare two different versions of HTML code. It detects
 * changes to an entire HTML page such as removal or addition of characters or
 * images.
 * </p>
 *
 * @author Julio Camarero
 */
public class DiffHtmlImpl implements DiffHtml {

	/**
	 * This is a diff method with default values.
	 *
	 * @return a string containing the HTML code of the source text showing the
	 *		   differences with the target text
	 */
	public String diff(Reader source, Reader target) throws Exception {
		InputSource oldSource = new InputSource(source);
		InputSource newSource = new InputSource(target);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(true);

		SAXTransformerFactory saxTransformerFactory =
			(SAXTransformerFactory)TransformerFactory.newInstance();

		TransformerHandler tranformHandler =
			saxTransformerFactory.newTransformerHandler();

		tranformHandler.setResult(new StreamResult(unsyncStringWriter));

		XslFilter xslFilter = new XslFilter();

		ContentHandler contentHandler = xslFilter.xsl(
			tranformHandler,
			"com/liferay/portal/util/dependencies/diff_html.xsl");

		HtmlCleaner htmlCleaner = new HtmlCleaner();

		DomTreeBuilder oldDomTreeBuilder = new DomTreeBuilder();

		htmlCleaner.cleanAndParse(oldSource, oldDomTreeBuilder);

		Locale locale = LocaleUtil.getDefault();

		TextNodeComparator leftTextNodeComparator = new TextNodeComparator(
			oldDomTreeBuilder, locale);

		DomTreeBuilder newDomTreeBuilder = new DomTreeBuilder();

		htmlCleaner.cleanAndParse(newSource, newDomTreeBuilder);

		TextNodeComparator rightTextNodeComparator = new TextNodeComparator(
			newDomTreeBuilder, locale);

		contentHandler.startDocument();
		contentHandler.startElement(
			StringPool.BLANK, _DIFF_REPORT, _DIFF_REPORT, new AttributesImpl());
		contentHandler.startElement(
			StringPool.BLANK, _DIFF, _DIFF, new AttributesImpl());

		HtmlSaxDiffOutput htmlSaxDiffOutput = new HtmlSaxDiffOutput(
			contentHandler, _DIFF);

		HTMLDiffer htmlDiffer = new HTMLDiffer(htmlSaxDiffOutput);

		htmlDiffer.diff(leftTextNodeComparator, rightTextNodeComparator);

		contentHandler.endElement(StringPool.BLANK, _DIFF, _DIFF);
		contentHandler.endElement(StringPool.BLANK, _DIFF_REPORT, _DIFF_REPORT);
		contentHandler.endDocument();

		unsyncStringWriter.flush();

		return unsyncStringWriter.toString();
	}

	private static final String _DIFF = "diff";

	private static final String _DIFF_REPORT = "diffreport";

}