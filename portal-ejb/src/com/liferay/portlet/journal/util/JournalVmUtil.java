/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.util.Html;
import com.liferay.util.StringPool;
import com.liferay.util.velocity.VelocityResourceListener;
import com.liferay.util.xml.CDATAUtil;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.VelocityException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="JournalVmUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class JournalVmUtil {

	public static String transform(
			Map tokens, String languageId, String xml, String vmScript)
		throws TransformException {

		StringWriter output = new StringWriter();

		boolean load = false;

		try {
			VelocityContext context = new VelocityContext();

			SAXReader reader = new SAXReader();

			Document doc = reader.read(new StringReader(xml));

			List nodes = _extractDynamicContents(doc.getRootElement());

			Iterator itr = nodes.iterator();

			while (itr.hasNext()) {
				TemplateNode node = (TemplateNode)itr.next();

				context.put(node.getName(), node);
			}

			String companyId = (String)tokens.get("company_id");

			context.put(
				"journalTemplatesPath",
				VelocityResourceListener.JOURNAL_SEPARATOR + StringPool.SLASH +
					companyId);

			load = Velocity.evaluate(
				context, output, JournalVmUtil.class.getName(), vmScript);
		}
		catch (Exception e) {
			if (e instanceof DocumentException) {
				throw new TransformException("Unable to read XML document", e);
			}
			else if (e instanceof VelocityException) {
				VelocityException pex = (VelocityException)e;

				throw new TransformException(
					"Unable to parse velocity template." +
						Html.escape(pex.getMessage(), false),
					e);
			}
			else if (e instanceof IOException) {
				throw new TransformException(
					"Error reading velocity template", e);
			}
			else if (e instanceof TransformException) {
				throw (TransformException)e;
			}
			else {
				throw new TransformException("Unhandled exception", e);
			}
		}

		if (!load) {
			throw new TransformException(
				"Unable to dynamically load velocity transform script");
		}

		return output.toString();
	}

	private static List _extractDynamicContents(Element parent)
		throws TransformException {

		List nodes = new ArrayList();

		Iterator itr1 = parent.elementIterator("dynamic-element");

		while (itr1.hasNext()) {
			Element element = (Element)itr1.next();

			Element content = element.element("dynamic-content");

			if (content == null) {
				throw new TransformException(
					"Element missing \"dynamic-content\"");
			}

			String name = element.attributeValue("name", "");

			if (name.length() == 0) {
				throw new TransformException(
					"Element missing \"name\" attribute");
			}

			String type = element.attributeValue("type", "");

			TemplateNode node = new TemplateNode(
				name, CDATAUtil.strip(content.getText()), type);

			if (element.element("dynamic-element") != null) {
				node.appendChildren(_extractDynamicContents(element));
			}
			else if (content.element("option") != null) {
				Iterator itr2 = content.elementIterator("option");

				while (itr2.hasNext()) {
					Element option = (Element)itr2.next();

					node.appendOption(CDATAUtil.strip(option.getText()));
				}
			}

			nodes.add(node);
		}

		return nodes;
	}

}