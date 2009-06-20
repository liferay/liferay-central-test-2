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

package com.liferay.util.xml;

import com.liferay.portal.kernel.util.ByteArrayMaker;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.InitUtil;

import java.io.IOException;
import java.io.StringReader;

import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * <a href="XMLFormatter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alan Zimmerman
 *
 */
public class XMLFormatter {

	public static String fixProlog(String xml) {

		// LEP-1921

		if (xml != null) {
			char[] charArray = xml.toCharArray();

			for (int i = 0; i < charArray.length; i++) {
				if (charArray[i] == '<') {
					if (i != 0) {
						xml = xml.substring(i, xml.length());
					}

					break;
				}
			}
		}

		return xml;
	}

	public static String fromCompactSafe(String xml) {
		return StringUtil.replace(xml, "[$NEW_LINE$]", StringPool.NEW_LINE);
	}

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		String fileName = System.getProperty("xml.formatter.file");
		boolean stripComments = GetterUtil.getBoolean(
			System.getProperty("xml.formatter.strip.comments"));

		if (Validator.isNull(fileName)) {
			throw new IllegalArgumentException();
		}

		try {
			String xml = FileUtil.read(fileName);

			if (stripComments) {
				xml = HtmlUtil.stripComments(xml);
			}

			xml = toString(xml);

			FileUtil.write(fileName, xml);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String toCompactSafe(String xml) {
		return StringUtil.replace(
			xml,
			new String[] {
				StringPool.RETURN_NEW_LINE,
				StringPool.NEW_LINE,
				StringPool.RETURN
			},
			new String[] {
				"[$NEW_LINE$]",
				"[$NEW_LINE$]",
				"[$NEW_LINE$]"
			});
	}

	public static String toString(String xml)
		throws DocumentException, IOException {

		return toString(xml, StringPool.TAB);
	}

	public static String toString(String xml, String indent)
		throws DocumentException, IOException {

		SAXReader reader = new SAXReader();

		Document doc = reader.read(new StringReader(xml));

		return toString(doc, indent);
	}

	public static String toString(Branch branch) throws IOException {
		return toString(branch, StringPool.TAB);
	}

	public static String toString(Branch branch, String indent)
		throws IOException {

		return toString(branch, StringPool.TAB, false);
	}

	public static String toString(
			Branch branch, String indent, boolean expandEmptyElements)
		throws IOException {

		ByteArrayMaker bam = new ByteArrayMaker();

		OutputFormat format = OutputFormat.createPrettyPrint();

		format.setExpandEmptyElements(expandEmptyElements);
		format.setIndent(indent);
		format.setLineSeparator("\n");

		XMLWriter writer = new XMLWriter(bam, format);

		writer.write(branch);

		String content = bam.toString(StringPool.UTF8);

		// LEP-4257

		//content = StringUtil.replace(content, "\n\n\n", "\n\n");

		if (content.endsWith("\n\n")) {
			content = content.substring(0, content.length() - 2);
		}

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		while (content.indexOf(" \n") != -1) {
			content = StringUtil.replace(content, " \n", "\n");
		}

		return content;
	}

}