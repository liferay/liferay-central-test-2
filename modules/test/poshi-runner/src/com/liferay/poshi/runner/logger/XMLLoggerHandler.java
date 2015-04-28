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

package com.liferay.poshi.runner.logger;

import com.liferay.poshi.runner.util.Validator;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public final class XMLLoggerHandler {

	public static void generateXMLLog(String classCommandName) {
		LoggerElement xmlLoggerElement = new LoggerElement();

		xmlLoggerElement.setClassName("header");
		xmlLoggerElement.setName("li");

		LoggerElement btnContainerLoggerElement = new LoggerElement();

		btnContainerLoggerElement.setClassName("btn-container");
		btnContainerLoggerElement.setName("div");

		LoggerElement btnLoggerElement = new LoggerElement();

		btnLoggerElement.setClassName("btn btn-collapse");
		btnLoggerElement.setName("button");

		btnContainerLoggerElement.addChildLoggerElement(btnLoggerElement);

		xmlLoggerElement.addChildLoggerElement(btnContainerLoggerElement);

		LoggerElement lineContainerLoggerElement = new LoggerElement();

		lineContainerLoggerElement.setClassName("line-container");
		lineContainerLoggerElement.setName("div");

		LoggerElement lineLoggerElement = new LoggerElement();

		lineLoggerElement.setClassName("test-case-command");
		lineLoggerElement.setName("h3");
		lineLoggerElement.setText(classCommandName);

		lineContainerLoggerElement.addChildLoggerElement(lineLoggerElement);

		xmlLoggerElement.addChildLoggerElement(lineContainerLoggerElement);
	}

	private static LoggerElement _getBtnContainerLoggerElement(
		Element element) {

		LoggerElement btnContainerLoggerElement = new LoggerElement();

		btnContainerLoggerElement.setClassName("btn-container");
		btnContainerLoggerElement.setName("div");

		StringBuilder sb = new StringBuilder();

		sb.append(
			_getLineNumberItemText(element.attributeValue("line-number")));

		List<Element> childElements = element.elements();

		boolean executingMacro = _isExecutingMacro(element);

		if (!childElements.isEmpty() || executingMacro) {
			sb.append(_getBtnItemText("btn-collapse"));
		}

		if (!childElements.isEmpty() && executingMacro) {
			sb.append(_getBtnItemText("btn-var"));
		}

		btnContainerLoggerElement.setText(sb.toString());

		return btnContainerLoggerElement;
	}

	private static String _getBtnItemText(String className) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("btn " + className);
		loggerElement.setID(null);
		loggerElement.setName("button");

		return loggerElement.toString();
	}

	private static LoggerElement _getChildContainerLoggerElement() {
		LoggerElement childContainerLoggerElement = new LoggerElement();

		childContainerLoggerElement.setClassName(
			"child-container collapse collapsible");
		childContainerLoggerElement.setName("ul");

		return childContainerLoggerElement;
	}

	private static LoggerElement _getClosingLineContainerLoggerElement(
		Element element) {

		LoggerElement closingLineContainerLoggerElement = new LoggerElement();

		closingLineContainerLoggerElement.setClassName("line-container");
		closingLineContainerLoggerElement.setName("div");

		StringBuilder sb = new StringBuilder();

		sb.append(_getLineItemText("misc", "&lt;/"));
		sb.append(_getLineItemText("action-type", element.getName()));
		sb.append(_getLineItemText("misc", "&gt;"));

		closingLineContainerLoggerElement.setText(sb.toString());

		return closingLineContainerLoggerElement;
	}

	private static LoggerElement _getEchoLoggerElement(Element element) {
		return _getLineGroupLoggerElement("echo", element);
	}

	private static LoggerElement _getFailLoggerElement(Element element) {
		return _getLineGroupLoggerElement(element);
	}

	private static LoggerElement _getLineContainerLoggerElement(
		Element element) {

		LoggerElement lineContainerLoggerElement = new LoggerElement();

		lineContainerLoggerElement.setClassName("line-container");
		lineContainerLoggerElement.setName("div");

		StringBuilder sb = new StringBuilder();

		sb.append(_getLineItemText("misc", "&lt;"));
		sb.append(_getLineItemText("action-type", element.getName()));

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (attributeName.equals("line-number")) {
				continue;
			}

			sb.append(_getLineItemText("tag-type", attributeName));
			sb.append(_getLineItemText("misc", "="));
			sb.append(_getLineItemText("misc quote", "\""));
			sb.append(_getLineItemText("name", attribute.getValue()));
			sb.append(_getLineItemText("misc quote", "\""));
		}

		List<Element> elements = element.elements();

		if (elements.isEmpty()) {
			sb.append(_getLineItemText("misc", "/&gt;"));
		}
		else {
			sb.append(_getLineItemText("misc", "&gt;"));
		}

		lineContainerLoggerElement.setText(sb.toString());

		return lineContainerLoggerElement;
	}

	private static LoggerElement _getLineGroupLoggerElement(Element element) {
		return _getLineGroupLoggerElement(null, element);
	}

	private static LoggerElement _getLineGroupLoggerElement(
		String className, Element element) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("line-group");
		loggerElement.setName("li");

		if (Validator.isNotNull(className)) {
			loggerElement.addClassName(className);
		}

		loggerElement.addChildLoggerElement(
			_getBtnContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getLineContainerLoggerElement(element));

		return loggerElement;
	}

	private static String _getLineItemText(String className, String text) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName(className);
		loggerElement.setID(null);
		loggerElement.setName("span");
		loggerElement.setText(text);

		return loggerElement.toString();
	}

	private static String _getLineNumberItemText(String lineNumber) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("line-number");
		loggerElement.setID(null);
		loggerElement.setName("div");
		loggerElement.setText(lineNumber);

		return loggerElement.toString();
	}

	private static LoggerElement _getLoggerElementFromElement(Element element) {
		String elementName = element.getName();

		LoggerElement loggerElement = new LoggerElement();

		if (elementName.equals("description") || elementName.equals("echo")) {
			loggerElement = _getEchoLoggerElement(element);
		}
		else if (elementName.equals("fail")) {
			loggerElement = _getFailLoggerElement(element);
		}

		return loggerElement;
	}

	private static boolean _isExecutingMacro(Element element) {
		if ((element.attributeValue("macro") != null) ||
			(element.attributeValue("macro-desktop") != null) ||
			(element.attributeValue("macro-mobile") != null)) {

			return true;
		}

		return false;
	}

}