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

package com.liferay.project.templates.util;

import java.io.StringWriter;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Assert;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Andrea Di Giorgi
 */
public class XMLTestUtil {

	public static Element getChildElement(Element parentElement, String name) {
		Node node = parentElement.getFirstChild();

		do {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)node;

				if (name.equals(element.getTagName())) {
					return element;
				}
			}
		}
		while ((node = node.getNextSibling()) != null);

		return null;
	}

	public static List<Element> getChildElements(Element element) {
		NodeList nodeList = element.getChildNodes();

		List<Element> elements = new ArrayList<>(nodeList.getLength());

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				elements.add((Element)node);
			}
		}

		return elements;
	}

	public static void testXmlElement(
			Path path, String parentElementString, List<Element> elements,
			int index, String expectedTagName, String expectedTextContent)
		throws TransformerException {

		if (elements.size() <= index) {
			StringBuilder sb = new StringBuilder();

			sb.append("Missing child element <");
			sb.append(expectedTagName);
			sb.append('>');
			sb.append(expectedTextContent);
			sb.append("</");
			sb.append(expectedTagName);
			sb.append("> of ");
			sb.append(parentElementString);
			sb.append(" in ");
			sb.append(path);

			Assert.fail(sb.toString());
		}

		Element element = elements.get(index);

		String elementString = toString(element);

		Assert.assertEquals(
			"Incorrect tag name of " + elementString + " in " + path,
			expectedTagName, element.getTagName());
		Assert.assertEquals(
			"Incorrect text content of " + elementString + " in " + path,
			expectedTextContent, element.getTextContent());
	}

	public static String toString(Element element) throws TransformerException {
		StringWriter stringWriter = new StringWriter();

		_transformer.transform(
			new DOMSource(element), new StreamResult(stringWriter));

		return stringWriter.toString();
	}

	private static final Transformer _transformer;

	static {
		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		try {
			_transformer = transformerFactory.newTransformer();
		}
		catch (TransformerConfigurationException tce) {
			throw new ExceptionInInitializerError(tce);
		}

		_transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	}

}