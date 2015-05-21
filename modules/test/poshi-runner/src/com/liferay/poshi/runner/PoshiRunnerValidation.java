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

import com.liferay.poshi.runner.util.OSDetector;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.Validator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerValidation {

	public static void main(String[] args) throws Exception {
		PoshiRunnerContext.readFiles();

		validate();
	}

	public static void validate() throws Exception {
		String[] filePathsArray = PoshiRunnerContext.getFilePathsArray();

		for (String filePath : filePathsArray) {
			filePath = _BASE_DIR + "/" + filePath;

			if (OSDetector.isWindows()) {
				filePath = filePath.replace("/", "\\");
			}

			String className = PoshiRunnerGetterUtil.getClassNameFromFilePath(
				filePath);
			String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
				filePath);

			if (classType.equals("function")) {
				Element element = PoshiRunnerContext.getFunctionRootElement(
					className);

				_validateFunctionFile(element, filePath);
			}
			else if (classType.equals("macro")) {
				Element element = PoshiRunnerContext.getMacroRootElement(
					className);

				_validateMacroFile(element, filePath);
			}
			else if (classType.equals("path")) {
				Element element = PoshiRunnerContext.getPathRootElement(
					className);

				_validatePathFile(element, filePath);
			}
			else if (classType.equals("test-case")) {
				Element element = PoshiRunnerContext.getTestCaseRootElement(
					className);

				_validateTestCaseFile(element, filePath);
			}
		}

		if (!_exceptions.isEmpty()) {
			_throwExceptions();
		}
	}

	public static void validate(String testName) throws Exception {
		_validateTestName(testName);

		validate();
	}

	private static String _getPrimaryAttributeName(
		Element element, List<String> primaryAttributeNames, String filePath) {

		_validateHasPrimaryAttributeName(
			element, primaryAttributeNames, filePath);

		for (String primaryAttributeName : primaryAttributeNames) {
			if (Validator.isNotNull(
					element.attributeValue(primaryAttributeName))) {

				return primaryAttributeName;
			}
		}

		return null;
	}

	private static void _parseElements(Element element, String filePath) {
		List<Element> childElements = element.elements();

		List<String> possibleElementNames = Arrays.asList(
			"description", "echo", "execute", "fail", "for", "if", "property",
			"take-screenshot", "task", "var", "while");

		for (Element childElement : childElements) {
			String elementName = childElement.getName();

			if (!possibleElementNames.contains(elementName)) {
				_exceptions.add(
					new Exception(
						"Invalid " + elementName + " element\n" + filePath +
							":" + childElement.attributeValue("line-number")));
			}

			if (elementName.equals("description") ||
				elementName.equals("echo") || elementName.equals("fail")) {

				_validateMessageElement(childElement, filePath);
			}
			else if (elementName.equals("execute")) {
				_validateExecuteElement(childElement, filePath);
			}
			else if (elementName.equals("for")) {
				_validateForElement(childElement, filePath);
			}
			else if (elementName.equals("if")) {
				_validateIfElement(childElement, filePath);
			}
			else if (elementName.equals("take-screenshot")) {
				_validateTakeScreenshotElement(childElement, filePath);
			}
			else if (elementName.equals("task")) {
				_validateTaskElement(childElement, filePath);
			}
			else if (elementName.equals("var")) {
				_validateVarElement(childElement, filePath);
			}
			else if (elementName.equals("while")) {
				_validateWhileElement(childElement, filePath);
			}
		}
	}

	private static void _throwExceptions() throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append(Integer.toString(_exceptions.size()));
		sb.append(" errors in POSHI\n\n\n");

		for (Exception exception : _exceptions) {
			sb.append(exception.getMessage());
			sb.append("\n\n");
		}

		System.out.println(sb.toString());

		throw new Exception();
	}

	private static void _validateClassCommandName(
		Element element, String classCommandName, String classType,
		String filePath) {

		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				classCommandName);

		if (!PoshiRunnerContext.isRootElement(classType + "#" + className)) {
			_exceptions.add(
				new Exception(
					"Invalid " + classType + " class " + className + "\n" +
						filePath + ":" +
						element.attributeValue("line-number")));
		}

		String commandElementKey = classType + "#" + classCommandName;

		if (!PoshiRunnerContext.isCommandElement(commandElementKey)) {
			_exceptions.add(
				new Exception(
					"Invalid " + classType + " command " + classCommandName +
						"\n" + filePath + ":" +
						element.attributeValue("line-number")));
		}
	}

	private static void _validateCommandElement(
		Element element, String filePath) {

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "name", "summary", "summary-ignore");

		_validatePossibleAttributeNames(
			element, possibleAttributeNames, filePath);
		_validateRequiredAttributeNames(
			element, Arrays.asList("name"), filePath);
	}

	private static void _validateConditionElement(
		Element element, String filePath) {

		String elementName = element.getName();

		if (elementName.equals("and")) {
			_validateHasChildElements(element, filePath);
			_validateHasNoAttributes(element, filePath);

			List<Element> childElements = element.elements();

			for (Element childElement : childElements) {
				_validateConditionElement(childElement, filePath);
			}
		}
		else if (elementName.equals("condition")) {
			List<String> primaryAttributeNames = Arrays.asList(
				"function", "selenium");

			String primaryAttributeName = _getPrimaryAttributeName(
				element, primaryAttributeNames, filePath);

			if (primaryAttributeName.equals("function")) {
				_validateRequiredAttributeNames(
					element, Arrays.asList("locator1"), filePath);

				List<String> possibleAttributeNames = Arrays.asList(
					"function", "line-number", "locator1", "value1");

				_validatePossibleAttributeNames(
					element, possibleAttributeNames, filePath);
			}
			else if (primaryAttributeName.equals("selenium")) {
				List<String> possibleAttributeNames = Arrays.asList(
					"argument1", "argument2", "line-number", "selenium");

				_validatePossibleAttributeNames(
					element, possibleAttributeNames, filePath);
			}

			_validateHasNoChildElements(element, filePath);
		}
		else if (elementName.equals("contains")) {
			List<String> attributeNames = Arrays.asList(
				"line-number", "string", "substring");

			_validateHasNoChildElements(element, filePath);
			_validatePossibleAttributeNames(element, attributeNames, filePath);
			_validateRequiredAttributeNames(element, attributeNames, filePath);
		}
		else if (elementName.equals("equals")) {
			List<String> attributeNames = Arrays.asList(
				"arg1", "arg2", "line-number");

			_validateHasNoChildElements(element, filePath);
			_validatePossibleAttributeNames(element, attributeNames, filePath);
			_validateRequiredAttributeNames(element, attributeNames, filePath);
		}
		else if (elementName.equals("isset")) {
			List<String> attributeNames = Arrays.asList("line-number", "var");

			_validateHasNoChildElements(element, filePath);
			_validatePossibleAttributeNames(element, attributeNames, filePath);
			_validateRequiredAttributeNames(element, attributeNames, filePath);
		}
		else if (elementName.equals("not")) {
			_validateHasChildElements(element, filePath);
			_validateHasNoAttributes(element, filePath);

			List<Element> childElements = element.elements();

			for (Element childElement : childElements) {
				_validateConditionElement(childElement, filePath);
			}
		}
		else if (elementName.equals("or")) {
			_validateHasChildElements(element, filePath);
			_validateHasNoAttributes(element, filePath);

			List<Element> childElements = element.elements();

			for (Element childElement : childElements) {
				_validateConditionElement(childElement, filePath);
			}
		}
	}

	private static void _validateDefinitionElement(
		Element element, String filePath) {

		String elementName = element.getName();

		if (!StringUtils.equals(elementName, "definition")) {
			_exceptions.add(
				new Exception(
					"Invalid " + elementName + " element\n" + filePath + ":" +
						element.attributeValue("line-number")));
		}

		String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
			filePath);

		if (classType.equals("function")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"default", "line-number", "summary", "summary-ignore");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
			_validateRequiredAttributeNames(
				element, Arrays.asList("default"), filePath);
		}
		else if (classType.equals("macro")) {
			_validateHasNoAttributes(element, filePath);
		}
		else if (classType.equals("testcase")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"component-name", "extends", "ignore", "ignore-command-names",
				"line-number");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
			_validateRequiredAttributeNames(
				element, Arrays.asList("component-name"), filePath);
		}
	}

	private static void _validateElementName(
		Element element, List<String> possibleElementNames, String filePath) {

		if (!possibleElementNames.contains(element.getName())) {
			_exceptions.add(
				new Exception(
					"Missing " + possibleElementNames + " element\n" +
						filePath + ":" +
						element.attributeValue("line-number")));
		}
	}

	private static void _validateElseElement(Element element, String filePath) {
		List<Element> elseElements = element.elements("else");

		if (elseElements.size() > 1) {
			_exceptions.add(
				new Exception(
					"Too many else elements\n" + filePath + ":" +
						element.attributeValue("line-number")));
		}
	}

	private static void _validateElseIfElement(
		Element element, String filePath) {

		_validateHasChildElements(element, filePath);
		_validateHasNoAttributes(element, filePath);

		List<Element> childElements = element.elements();

		if (childElements.size() > 2) {
			_exceptions.add(
				new Exception(
					"Too many elseif child elements" + filePath + ":" +
						element.attributeValue("line-number")));
		}

		List<String> conditionTags = Arrays.asList(
			"and", "condition", "contains", "equals", "isset", "not", "or");

		Element conditionElement = childElements.get(0);

		String conditionElementName = conditionElement.getName();

		if (conditionTags.contains(conditionElementName)) {
			_validateConditionElement(conditionElement, filePath);
		}
		else {
			_exceptions.add(
				new Exception(
					"Invalid " + conditionElementName + " element" + filePath +
						":" + element.attributeValue("line-number")));
		}

		Element thenElement = childElements.get(1);

		if (StringUtils.equals("then", thenElement.getName())) {
			_validateHasChildElements(thenElement, filePath);
			_validateHasNoAttributes(thenElement, filePath);

			_parseElements(thenElement, filePath);
		}
		else {
			_exceptions.add(
				new Exception(
					"Missing then element" + filePath + ":" +
						element.attributeValue("line-number")));
		}
	}

	private static void _validateExecuteElement(
		Element element, String filePath) {

		List<String> primaryAttributeNames = Arrays.asList(
			"function", "macro", "macro-desktop", "macro-mobile", "selenium",
			"test-case");

		String primaryAttributeName = _getPrimaryAttributeName(
			element, primaryAttributeNames, filePath);

		if (primaryAttributeName.equals("function")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"function", "line-number", "locator1", "locator2", "value1",
				"value2");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);

			_validateFunctionContext(element, filePath);
		}
		else if (primaryAttributeName.equals("macro")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "macro");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);

			_validateMacroContext(element, "macro", filePath);
		}
		else if (primaryAttributeName.equals("macro-desktop")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "macro-desktop", "macro-mobile");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);

			_validateMacroContext(element, "macro-desktop", filePath);
		}
		else if (primaryAttributeName.equals("macro-mobile")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "macro-desktop", "macro-mobile");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);

			_validateMacroContext(element, "macro-mobile", filePath);
		}
		else if (primaryAttributeName.equals("selenium")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"argument1", "argument2", "line-number", "selenium");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
		}
		else if (primaryAttributeName.equals("test-case")) {
			List<String> possibleAttributeNames = Arrays.asList(
				"line-number", "test-case");

			_validatePossibleAttributeNames(
				element, possibleAttributeNames, filePath);
		}

		List<Element> childElements = element.elements();

		if (!childElements.isEmpty()) {
			primaryAttributeNames = Arrays.asList(
				"function", "macro", "macro-desktop", "macro-mobile");

			_validateHasPrimaryAttributeName(
				element, primaryAttributeNames, filePath);

			for (Element childElement : childElements) {
				if (StringUtils.equals(childElement.getName(), "var")) {
					_validateVarElement(childElement, filePath);

					continue;
				}
				else {
					_exceptions.add(
						new Exception(
							"Invalid child element\n" + filePath + ":" +
								element.attributeValue("line-number")));
				}
			}
		}
	}

	private static void _validateForElement(Element element, String filePath) {
		List<String> attributeNames = Arrays.asList(
			"line-number", "list", "param");

		_validateHasChildElements(element, filePath);
		_validatePossibleAttributeNames(element, attributeNames, filePath);
		_validateRequiredAttributeNames(element, attributeNames, filePath);

		_parseElements(element, filePath);
	}

	private static void _validateFunctionContext(
		Element element, String filePath) {

		String function = element.attributeValue("function");

		_validateClassCommandName(element, function, "function", filePath);

		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(function);

		int locatorCount = PoshiRunnerContext.getFunctionLocatorCount(
			className);

		for (int i = 0; i < locatorCount; i++) {
			String locator = element.attributeValue("locator" + (i + 1));

			if (locator != null) {
				Matcher matcher = _pattern.matcher(locator);

				if (!locator.contains("#") || matcher.find()) {
					continue;
				}

				String pathName =
					PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
						locator);

				if (!PoshiRunnerContext.isRootElement("path#" + pathName)) {
					_exceptions.add(
						new Exception(
							"Invalid path name " + pathName + "\n" + filePath +
								":" + element.attributeValue("line-number")));
				}

				if (!PoshiRunnerContext.isPathLocator(locator)) {
					_exceptions.add(
						new Exception(
							"Invalid path locator " + locator + "\n" +
								filePath + ":" +
								element.attributeValue("line-number")));
				}
			}
		}
	}

	private static void _validateFunctionFile(
		Element element, String filePath) {

		_validateDefinitionElement(element, filePath);
		_validateHasChildElements(element, filePath);
		_validateRequiredChildElementNames(
			element, Arrays.asList("command"), filePath);

		List<Element> childElements = element.elements();

		for (Element childElement : childElements) {
			_validateCommandElement(childElement, filePath);
			_validateHasChildElements(childElement, filePath);

			_parseElements(childElement, filePath);
		}
	}

	private static void _validateHasChildElements(
		Element element, String filePath) {

		List<Element> childElements = element.elements();

		if (childElements.isEmpty()) {
			_exceptions.add(
				new Exception(
					"Missing child elements\n " + filePath + ":" +
						element.attributeValue("line-number")));
		}
	}

	private static void _validateHasNoAttributes(
		Element element, String filePath) {

		List<Attribute> attributes = element.attributes();

		if (attributes.size() > 1) {
			Attribute attribute = attributes.get(1);

			_exceptions.add(
				new Exception(
					"Invalid " + attribute.getName() + " attribute\n" +
						filePath + ":" +
						element.attributeValue("line-number")));
		}
	}

	private static void _validateHasNoChildElements(
		Element element, String filePath) {

		List<Element> childElements = element.elements();

		if (!childElements.isEmpty()) {
			_exceptions.add(
				new Exception(
					"Invalid child elements\n" + filePath + ":" +
						element.attributeValue("line-number")));
		}
	}

	private static void _validateHasPrimaryAttributeName(
		Element element, List<String> primaryAttributeNames, String filePath) {

		boolean found = false;

		for (String primaryAttributeName : primaryAttributeNames) {
			if (Validator.isNotNull(
					element.attributeValue(primaryAttributeName))) {

				if (!found) {
					found = true;
				}
				else {
					_exceptions.add(
						new Exception(
							"Too many attributes: " + "\n" + filePath + ":" +
								element.attributeValue("line-number")));
				}
			}
		}

		if (!found) {
			_exceptions.add(
				new Exception(
					"Invalid or missing attribute\n" + filePath + ":" +
						element.attributeValue("line-number")));
		}
	}

	private static void _validateIfElement(Element element, String filePath) {
		_validateHasChildElements(element, filePath);
		_validateHasNoAttributes(element, filePath);

		String fileName = filePath.substring(filePath.lastIndexOf(".") + 1);

		List<Element> childElements = element.elements();

		if (fileName.equals("function")) {
			Element firstChildElement = childElements.get(0);

			_validateConditionElement(firstChildElement, filePath);

			List<String> possibleElementNames = Arrays.asList(
				"condition", "contains");

			_validateElementName(
				firstChildElement, possibleElementNames, filePath);
		}

		_validateElseElement(element, filePath);
		_validateThenElement(element, filePath);

		List<String> conditionTags = Arrays.asList(
			"and", "condition", "contains", "equals", "isset", "not", "or");

		for (int i = 0; i < childElements.size(); i++) {
			Element childElement = childElements.get(i);

			String childElementName = childElement.getName();

			if (conditionTags.contains(childElementName) && (i == 0)) {
				_validateConditionElement(childElement, filePath);
			}
			else if (childElementName.equals("else")) {
				_validateHasChildElements(childElement, filePath);
				_validateHasNoAttributes(childElement, filePath);

				_parseElements(childElement, filePath);
			}
			else if (childElementName.equals("elseif")) {
				_validateHasChildElements(childElement, filePath);
				_validateHasNoAttributes(childElement, filePath);

				_validateElseIfElement(childElement, filePath);
			}
			else if (childElementName.equals("then")) {
				_validateHasChildElements(childElement, filePath);
				_validateHasNoAttributes(childElement, filePath);

				_parseElements(childElement, filePath);
			}
			else {
				_exceptions.add(
					new Exception(
						"Invalid " + childElementName + " element\n" +
							filePath + ":" +
								childElement.attributeValue("line-number")));
			}
		}
	}

	private static void _validateMacroContext(
		Element element, String macroType, String filePath) {

		_validateClassCommandName(
			element, element.attributeValue(macroType), "macro", filePath);
	}

	private static void _validateMacroFile(Element element, String filePath) {
		_validateDefinitionElement(element, filePath);
		_validateHasChildElements(element, filePath);
		_validateRequiredChildElementNames(
			element, Arrays.asList("command"), filePath);

		List<Element> childElements = element.elements();

		List<String> possibleTagElementNames = Arrays.asList("command", "var");

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (!possibleTagElementNames.contains(childElementName)) {
				_exceptions.add(
					new Exception(
						"Invalid " + childElementName + " element\n" +
							filePath + ":" +
								childElement.attributeValue("line-number")));
			}

			if (childElementName.equals("command")) {
				_validateCommandElement(childElement, filePath);
				_validateHasChildElements(childElement, filePath);

				_parseElements(childElement, filePath);
			}
			else if (childElementName.equals("var")) {
				_validateVarElement(childElement, filePath);
			}
		}
	}

	private static void _validateMessageElement(
		Element element, String filePath) {

		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "message");

		_validatePossibleAttributeNames(
			element, possibleAttributeNames, filePath);

		if ((element.attributeValue("message") == null) &&
			Validator.isNull(element.getText())) {

			_exceptions.add(
				new Exception(
					"Missing message attribute\n" + filePath + ":" +
						element.attributeValue("line-number")));
		}
	}

	private static void _validatePathFile(Element element, String filePath) {
		String rootElementName = element.getName();

		if (!StringUtils.equals(rootElementName, "html")) {
			_exceptions.add(
				new Exception(
					"Invalid " + rootElementName + " element\n" + filePath +
						":" + element.attributeValue("line-number")));
		}

		_validateHasChildElements(element, filePath);
	}

	private static void _validatePossibleAttributeNames(
		Element element, List<String> possibleAttributeNames, String filePath) {

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (!possibleAttributeNames.contains(attributeName)) {
				_exceptions.add(
					new Exception(
						"Invalid " + attributeName + " attribute\n" + filePath +
							":" + element.attributeValue("line-number")));
			}
		}
	}

	private static void _validateRequiredAttributeNames(
		Element element, List<String> requiredAttributeNames, String filePath) {

		for (String requiredAttributeName : requiredAttributeNames) {
			if (element.attributeValue(requiredAttributeName) == null) {
				_exceptions.add(
					new Exception(
						"Missing " + requiredAttributeName + " attribute\n" +
							filePath + ":" +
							element.attributeValue("line-number")));
			}
		}
	}

	private static void _validateRequiredChildElementName(
		Element element, String requiredElementName, String filePath) {

		boolean found = false;

		List<Element> childElements = element.elements();

		for (Element childElement : childElements) {
			if (StringUtils.equals(
					childElement.getName(), requiredElementName)) {

				found = true;

				break;
			}
		}

		if (!found) {
			_exceptions.add(
				new Exception(
					"Missing required " + requiredElementName +
						" child element\n" + filePath + ":" +
						element.attributeValue("line-number")));
		}
	}

	private static void _validateRequiredChildElementNames(
		Element element, List<String> requiredElementNames, String filePath) {

		for (String requiredElementName : requiredElementNames) {
			_validateRequiredChildElementName(
				element, requiredElementName, filePath);
		}
	}

	private static void _validateTakeScreenshotElement(
		Element element, String filePath) {

		_validateHasNoAttributes(element, filePath);
		_validateHasNoChildElements(element, filePath);
	}

	private static void _validateTaskElement(Element element, String filePath) {
		List<String> possibleAttributeNames = Arrays.asList(
			"line-number", "macro-summary", "summary");

		_validateHasChildElements(element, filePath);
		_validatePossibleAttributeNames(
			element, possibleAttributeNames, filePath);

		List<String> primaryAttributeNames = Arrays.asList(
			"macro-summary", "summary");

		_validateHasPrimaryAttributeName(
			element, primaryAttributeNames, filePath);

		_parseElements(element, filePath);
	}

	private static void _validateTestCaseFile(
		Element element, String filePath) {

		_validateDefinitionElement(element, filePath);

		List<Element> childElements = element.elements();

		if (Validator.isNull(element.attributeValue("extends"))) {
			_validateHasChildElements(element, filePath);
			_validateRequiredChildElementNames(
				element, Arrays.asList("command"), filePath);
		}

		List<String> possibleTagElementNames = Arrays.asList(
			"command", "property", "set-up", "tear-down", "var");

		for (Element childElement : childElements) {
			String childElementName = childElement.getName();

			if (!possibleTagElementNames.contains(childElementName)) {
				_exceptions.add(
					new Exception(
						"Invalid " + childElementName + " element\n" +
							filePath + ":" +
							childElement.attributeValue("line-number")));
			}

			if (childElementName.equals("command")) {
				if (Validator.isNull(childElement.attributeValue("name"))) {
					_exceptions.add(
						new Exception(
							"Missing name attribute\n" + filePath + ":" +
								childElement.attributeValue("line-number")));
				}

				List<String> possibleAttributeNames = Arrays.asList(
					"description", "known-issues", "line-number", "name",
					"priority");

				_validateHasChildElements(childElement, filePath);
				_validatePossibleAttributeNames(
					childElement, possibleAttributeNames, filePath);
				_validateRequiredAttributeNames(
					childElement, Arrays.asList("name"), filePath);

				_parseElements(childElement, filePath);
			}
			else if (childElementName.equals("property")) {
				List<String> attributeNames = Arrays.asList(
					"line-number", "name", "value");

				_validatePossibleAttributeNames(
					childElement, attributeNames, filePath);
				_validateRequiredAttributeNames(
					childElement, attributeNames, filePath);
			}
			else if (childElementName.equals("set-up") ||
					 childElementName.equals("tear-down")) {

				_validateHasChildElements(childElement, filePath);
				_validateHasNoAttributes(childElement, filePath);

				_parseElements(childElement, filePath);
			}
			else if (childElementName.equals("var")) {
				_validateVarElement(childElement, filePath);
			}
		}
	}

	private static void _validateTestName(String testName) {
		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(testName);

		if (!PoshiRunnerContext.isRootElement("test-case#" + className)) {
			_exceptions.add(
				new Exception("Invalid test case class " + className));
		}

		if (testName.contains("#")) {
			String commandElementKey = "test-case#" + testName;

			if (!PoshiRunnerContext.isCommandElement(commandElementKey)) {
				String commandName =
					PoshiRunnerGetterUtil.getCommandNameFromClassCommandName(
						testName);

				_exceptions.add(
					new Exception("Invalid test case command " + commandName));
			}
		}
	}

	private static void _validateThenElement(Element element, String filePath) {
		List<Element> thenElements = element.elements("then");

		if (thenElements.isEmpty()) {
			_exceptions.add(
				new Exception(
					"Missing then elements\n" + filePath + ":" +
						element.attributeValue("line-number")));
		}
		else if (thenElements.size() > 1) {
			_exceptions.add(
				new Exception(
					"Too many then elements\n" + filePath + ":" +
						element.attributeValue("line-number")));
		}
	}

	private static void _validateVarElement(Element element, String filePath) {
		_validateRequiredAttributeNames(
			element, Arrays.asList("name"), filePath);

		List<Attribute> attributes = element.attributes();

		if (attributes.size() <= 2) {
			if (Validator.isNull(element.getText())) {
				_exceptions.add(
					new Exception(
						"Missing value attribute\n" + filePath + ":" +
							element.attributeValue("line-number")));
			}
		}

		List<String> possibleAttributeNames = Arrays.asList(
			"attribute", "group", "input", "line-number", "locator", "method",
			"name", "pattern", "property-value", "value");

		_validatePossibleAttributeNames(
			element, possibleAttributeNames, filePath);
	}

	private static void _validateWhileElement(
		Element element, String filePath) {

		_validatePossibleAttributeNames(
			element, Arrays.asList("line-number", "max-iterations"), filePath);
		_validateThenElement(element, filePath);

		List<String> conditionTags = Arrays.asList(
			"and", "condition", "contains", "equals", "isset", "not", "or");

		List<Element> childElements = element.elements();

		for (int i = 0; i < childElements.size(); i++) {
			Element childElement = childElements.get(i);

			String childElementName = childElement.getName();

			if (conditionTags.contains(childElementName) && (i == 0)) {
				_validateConditionElement(childElement, filePath);
			}
			else if (childElementName.equals("then")) {
				_validateHasChildElements(childElement, filePath);
				_validateHasNoAttributes(childElement, filePath);

				_parseElements(childElement, filePath);
			}
			else {
				_exceptions.add(
					new Exception(
						"Invalid " + childElementName + " element\n" +
							filePath + ":" +
							childElement.attributeValue("line-number")));
			}
		}
	}

	private static final String _BASE_DIR =
		PoshiRunnerGetterUtil.getCanonicalPath(PropsValues.TEST_BASE_DIR_NAME);

	private static final Set<Exception> _exceptions = new HashSet<>();
	private static final Pattern _pattern = Pattern.compile("\\$\\{([^}]*)\\}");

}