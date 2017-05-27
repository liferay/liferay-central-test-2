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

package com.liferay.jenkins.results.parser;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

/**
 * @author Peter Yoo
 */
public class Dom4JUtil {

	public static void addToElement(Element element, Object... items) {
		for (int i = 0; i < items.length; i++) {
			Object item = items[i];

			if (item == null) {
				continue;
			}

			if (item instanceof Element) {
				element.add((Element)item);

				continue;
			}

			if (item instanceof String) {
				element.addText((String)item);

				continue;
			}

			throw new IllegalArgumentException(
				"Only elements and strings may be added");
		}
	}

	public static String format(Element element) throws IOException {
		return format(element, true);
	}

	public static String format(Element element, boolean pretty)
		throws IOException {

		Writer writer = new CharArrayWriter();

		OutputFormat outputFormat = OutputFormat.createPrettyPrint();

		outputFormat.setTrimText(false);

		XMLWriter xmlWriter = null;

		if (pretty) {
			xmlWriter = new XMLWriter(writer, outputFormat);
		}
		else {
			xmlWriter = new XMLWriter(writer);
		}

		xmlWriter.write(element);

		return writer.toString();
	}

	public static Element getNewAnchorElement(
		String href, Element parentElement, Object... items) {

		if ((items == null) || (items.length == 0)) {
			return null;
		}

		Element anchorElement = null;

		anchorElement = getNewElement("a", parentElement);

		anchorElement.addAttribute("href", href);

		addToElement(anchorElement, items);

		return anchorElement;
	}

	public static Element getNewAnchorElement(String href, Object... items) {
		return getNewAnchorElement(href, null, items);
	}

	public static Element getNewElement(String childElementTag) {
		return getNewElement(childElementTag, null);
	}

	public static Element getNewElement(
		String childElementTag, Element parentElement, Object... items) {

		Element childElement = new DefaultElement(childElementTag);

		if (parentElement != null) {
			parentElement.add(childElement);
		}

		if ((items != null) && (items.length > 0)) {
			addToElement(childElement, items);
		}

		return childElement;
	}

	public static Element getOrderedListElement(
		List<Element> itemElements, Element parentElement, int maxItems) {

		Element orderedListElement = getNewElement("ol", parentElement);

		int i = 0;

		for (Element itemElement : itemElements) {
			if (i < maxItems) {
				String itemElementName = itemElement.getName();

				if (itemElementName.equals("li")) {
					orderedListElement.add(itemElement);
				}
				else {
					getNewElement("li", orderedListElement, itemElement);
				}

				i++;

				continue;
			}

			getNewElement("li", orderedListElement, "...");

			break;
		}

		return orderedListElement;
	}

	public static Element getOrderedListElement(
		List<Element> itemElements, int maxItems) {

		return getOrderedListElement(itemElements, null, maxItems);
	}

	public static Document parse(String xml) throws DocumentException {
		SAXReader saxReader = new SAXReader();

		return saxReader.read(new StringReader(xml));
	}

	public static void replace(
		Element element, boolean cascade, String replacementText,
		String targetText) {

		Iterator<?> attributeIterator = element.attributeIterator();

		while (attributeIterator.hasNext()) {
			Attribute attribute = (Attribute)attributeIterator.next();

			String text = attribute.getValue();

			attribute.setValue(text.replace(targetText, replacementText));
		}

		Iterator<?> nodeIterator = element.nodeIterator();

		while (nodeIterator.hasNext()) {
			Node node = (Node)nodeIterator.next();

			if (node instanceof Text) {
				Text textNode = (Text)node;

				String text = textNode.getText();

				if (text.contains(targetText)) {
					text = text.replace(targetText, replacementText);

					textNode.setText(text);
				}

				continue;
			}

			if (node instanceof Element && cascade) {
				replace((Element)node, cascade, replacementText, targetText);

				continue;
			}
		}
	}

	public static Element toCodeSnippetElement(String content) {
		return getNewElement(
			"pre", null,
			getNewElement(
				"code", null, JenkinsResultsParserUtil.redact(content)));
	}

}