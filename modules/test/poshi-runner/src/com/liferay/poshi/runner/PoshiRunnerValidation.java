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

package com.liferay.poshi.runner;

import com.liferay.poshi.runner.util.Validator;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerValidation {

	public static void validate(Element element, String filePath)
		throws PoshiRunnerException {

		String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
			filePath);

		if (classType.equals("function")) {
			_validateFunctionFile(element, filePath);
		}
		else if (classType.equals("macro")) {
			_validateMacroFile(element, filePath);
		}
		else if (classType.equals("path")) {
			_validatePathFile(element, filePath);
		}
		else if (classType.equals("testcase")) {
			_validateTestcaseFile(element, filePath);
		}
	}

	private static void _parseElements(Element element, String filePath)
		throws PoshiRunnerException {

		List<Element> childElements = element.elements();

		List<String> possibleElementNames = Arrays.asList(
			"description", "echo", "execute", "fail", "for", "if",
			"take-screenshot", "task", "var", "while");

		for (Element childElement : childElements) {
			String elementName = childElement.getName();

			if (!possibleElementNames.contains(elementName)) {
				throw new PoshiRunnerException(
					"Invalid " + elementName + " element\n" + filePath + ":" +
						childElement.attributeValue("line-number"));
			}

			if (elementName.equals("description") ||
				elementName.equals("echo") || elementName.equals("fail")) {

				_validateMessageElement(childElement, filePath);
			}
			else if (elementName.equals("execute")) {
				_validateExecuteElement(childElement, filePath);
			}
		}
	}

	private static void _validateCommandElement(
			Element element, String filePath)
		throws PoshiRunnerException {

		if (Validator.isNull(element.attributeValue("name"))) {
			throw new PoshiRunnerException(
				"Missing name attribute\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "name", "summary", "summary-ignore");

		_validatePossibleAttributeNames(
			element, possibleAttributeNames, filePath);
	}

	private static void _validateDefinitionElement(
			Element element, String filePath)
		throws PoshiRunnerException {

		String elementName = element.getName();

		if (!StringUtils.equals(elementName, "definition")) {
			throw new PoshiRunnerException(
				"Invalid " + elementName + " element\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
			filePath);

		if (classType.equals("function")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"default", "line-number", "summary", "summary-ignore");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
		}

		if (classType.equals("macro")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"extends", "line-number");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
		}

		if (classType.equals("testcase")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"component-name", "extends", "ignore", "ignore-command-names",
				"line-number");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
		}
	}

	private static void _validateExecuteElement(
			Element element, String filePath)
		throws PoshiRunnerException {

		if (Validator.isNotNull(element.attributeValue("function"))) {
			List<String> possibleAttributeNames = Arrays.asList(
				"function", "line-number", "locator1", "locator2", "value1",
				"value2");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
		}
		else if (Validator.isNotNull(element.attributeValue("macro"))) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "macro");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
		}
		else if (Validator.isNotNull(element.attributeValue("selenium"))) {
			List<String> possibleAttributeNames = Arrays.asList(
				"argument1", "argument2", "line-number", "selenium");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
		}
		else if (Validator.isNotNull(element.attributeValue("test-case"))) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "test-case");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
		}
		else {
			throw new PoshiRunnerException(
				"Invalid attribute\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<Element> childElements = element.elements();

		if (!childElements.isEmpty()) {
			if (Validator.isNotNull(element.attributeValue("function")) ||
				Validator.isNotNull(element.attributeValue("macro"))) {

				for (Element childElement : childElements) {
					if (StringUtils.equals(childElement.getName(), "var")) {
						_validateVarElement(childElement, filePath);

						continue;
					}
					else {
						throw new PoshiRunnerException(
							"Invalid child element\n" +
								filePath + ":" +
									element.attributeValue("line-number"));
					}
				}
			}
			else {
				throw new PoshiRunnerException(
					"Invalid child element\n" + filePath + ":" +
						element.attributeValue("line-number"));
			}
		}
	}

	private static void _validateFunctionFile(Element element, String filePath)
		throws PoshiRunnerException {

		_validateDefinitionElement(element, filePath);

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			throw new PoshiRunnerException(
				"Missing child elements\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (!childElementName.equals("command")) {
				throw new PoshiRunnerException(
					"Invalid " + childElementName + " element\n" + filePath +
						":" + childElement.attributeValue("line-number"));
			}

			_validateCommandElement(childElement, filePath);

			_parseElements(childElement, filePath);
		}
	}

	private static void _validateMacroFile(Element element, String filePath)
		throws PoshiRunnerException {

		_validateDefinitionElement(element, filePath);

		List<Element> childElements = element.elements();

		if (childElements.isEmpty() &&
			(element.attributeValue("extends") == null)) {

			throw new PoshiRunnerException(
				"Missing child elements\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<String> possibleTagElementNames = Arrays.asList("command", "var");

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (!possibleTagElementNames.contains(childElementName)) {
				throw new PoshiRunnerException(
					"Invalid " + childElementName + " element\n" + filePath +
						":" + childElement.attributeValue("line-number"));
			}

			if (childElementName.equals("command")) {
				_validateCommandElement(childElement, filePath);

				_parseElements(childElement, filePath);
			}
			else if (childElementName.equals("var")) {
				_validateVarElement(childElement, filePath);
			}
		}
	}

	private static void _validateMessageElement(
			Element element, String filePath)
		throws PoshiRunnerException {

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "message");

		_validatePossibleAttributeNames(
			element, possibleAttributeNames, filePath);

		if (Validator.isNull(element.attributeValue("message")) &&
			Validator.isNull(element.getText())) {

			throw new PoshiRunnerException(
				"Missing message attribute\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}
	}

	private static void _validatePathFile(Element element, String filePath)
		throws PoshiRunnerException {

		String rootElementName = element.getName();

		if (!StringUtils.equals(rootElementName, "html")) {
			throw new PoshiRunnerException(
				"Invalid " + rootElementName + " element\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			throw new PoshiRunnerException(
				"Missing child elements\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}
	}

	private static void _validatePossibleAttributeNames(
			Element element, List<String> possibleAttributeNames,
			String filePath)
		throws PoshiRunnerException {

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (!possibleAttributeNames.contains(attributeName)) {
				throw new PoshiRunnerException(
					"Invalid " + attributeName + " attribute\n" + filePath +
						":" + element.attributeValue("line-number"));
			}
		}
	}

	private static void _validateTestcaseFile(Element element, String filePath)
		throws PoshiRunnerException {

		_validateDefinitionElement(element, filePath);
	}

	private static void _validateVarElement(Element element, String filePath)
		throws PoshiRunnerException {

		if (Validator.isNull(element.attributeValue("name"))) {
			throw new PoshiRunnerException(
				"Missing name attribute\n" + filePath + ":" +
					element.attributeValue("line-number"));
		}

		List<Attribute> attributes = element.attributes();

		if (attributes.size() <= 2) {
			if (Validator.isNull(element.getText())) {
				throw new PoshiRunnerException(
					"Missing value attribute\n" + filePath + ":" +
						element.attributeValue("line-number"));
			}
		}

		List<String> possibleAttributeNames = Arrays.asList(
			"attribute", "group", "line-number", "locator", "method", "name",
			"pattern", "value");

		_validatePossibleAttributeNames(
			element, possibleAttributeNames, filePath);
	}

}