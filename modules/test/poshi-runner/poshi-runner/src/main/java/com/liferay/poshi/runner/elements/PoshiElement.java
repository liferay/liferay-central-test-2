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

package com.liferay.poshi.runner.elements;

import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.AND;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.BACKGROUND;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.FEATURE;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.GIVEN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.SCENARIO;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.SET_UP;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.TEAR_DOWN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THEN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THESE_PROPERTIES;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.WHEN;
import static com.liferay.poshi.runner.util.StringPool.COLON;
import static com.liferay.poshi.runner.util.StringPool.PIPE;
import static com.liferay.poshi.runner.util.StringPool.TAB;

import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * @author Kenji Heigel
 */
public abstract class PoshiElement extends DefaultElement {

	public PoshiElement(String name, Element element) {
		super(name);

		_addAttributes(element);
		_addElements(element);
	}

	public PoshiElement(String name, String readableSyntax) {
		super(name);

		addAttributes(readableSyntax);
		addElements(readableSyntax);
	}

	public abstract void addAttributes(String readableSyntax);

	public abstract void addElements(String readableSyntax);

	public void addVariableElements(String readableSyntax) {
		List<String> readableVariableBlocks = StringUtil.partition(
			readableSyntax, READABLE_VARIABLE_BLOCK_KEYS);

		for (String readableVariableBlock : readableVariableBlocks) {
			if (!readableVariableBlock.contains(PIPE)) {
				continue;
			}

			if (readableSyntax.contains(THESE_PROPERTIES)) {
				Element element = new PropertyElement(readableVariableBlock);

				add(element);

				continue;
			}

			PoshiElement poshiElement = PoshiElementFactory.newPoshiElement(
				readableVariableBlock);

			add(poshiElement);
		}
	}

	public String toReadableSyntax() {
		prepareVarElementsForReadableSyntax();

		StringBuilder sb = new StringBuilder();

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			sb.append(poshiElement.toReadableSyntax());
		}

		return sb.toString();
	}

	protected static String toPhrase(String s) {
		String phrase = s.replaceAll(_PHRASE_REGEX, " $0");

		if (phrase.startsWith(" ")) {
			return phrase.substring(1);
		}

		return phrase;
	}

	protected String getAttributeValue(String startKey, String readableSyntax) {
		return getAttributeValue(startKey, "\n", readableSyntax);
	}

	protected String getAttributeValue(
		String startKey, String endKey, String readableSyntax) {

		int start = readableSyntax.indexOf(startKey);

		int end = readableSyntax.indexOf(endKey, start + startKey.length());

		String substring = readableSyntax.substring(
			start + startKey.length(), end);

		return substring.trim();
	}

	protected Element getPreviousSiblingElement() {
		Element parentElement = getParent();

		if (parentElement != null) {
			int index = parentElement.indexOf(this);

			if (index > 0) {
				return (Element)parentElement.node(index - 1);
			}
		}

		return null;
	}

	protected String getReadableExecuteKey() {
		List<Element> siblingElements = getSiblings();

		int index = siblingElements.indexOf(this);

		if (index == 0) {
			return GIVEN;
		}

		if (index == (siblingElements.size() - 1)) {
			return THEN;
		}

		if (index == 1) {
			return WHEN;
		}

		return AND;
	}

	protected List<Element> getSiblings() {
		Element parentElement = getParent();

		if (parentElement == null) {
			return Collections.emptyList();
		}

		return Dom4JUtil.toElementList(parentElement.elements());
	}

	protected void prepareVarElementsForReadableSyntax() {
		List<PoshiElement> poshiElements = toPoshiElements(elements());

		List<VarElement> varElements = new ArrayList<>(poshiElements.size());

		int maxNameLength = 0;
		int maxValueLength = 0;

		for (PoshiElement poshiElement : poshiElements) {
			if (!(poshiElement instanceof VarElement)) {
				continue;
			}

			VarElement varElement = (VarElement)poshiElement;

			varElements.add(varElement);

			String name = varElement.getVarName();

			maxNameLength = Math.max(maxNameLength, name.length());

			String value = varElement.getVarValue();

			maxValueLength = Math.max(maxValueLength, value.length());
		}

		for (VarElement varElement : varElements) {
			varElement.setNamePadLength(maxNameLength);
			varElement.setValuePadLength(maxValueLength);
		}
	}

	protected List<PoshiElement> toPoshiElements(List<?> list) {
		if (list == null) {
			return null;
		}

		List<PoshiElement> poshiElements = new ArrayList<>(list.size());

		for (Object object : list) {
			poshiElements.add((PoshiElement)object);
		}

		return poshiElements;
	}

	protected static final String[] READABLE_COMMAND_BLOCK_KEYS = {
		BACKGROUND + COLON, FEATURE + COLON, SCENARIO + COLON, SET_UP + COLON,
		TEAR_DOWN + COLON
	};

	protected static final String[] READABLE_EXECUTE_BLOCK_KEYS =
		{TAB + AND, TAB + GIVEN, TAB + THEN, TAB + WHEN};

	protected static final String[] READABLE_VARIABLE_BLOCK_KEYS =
		{TAB + TAB + PIPE};

	private void _addAttributes(Element element) {
		for (Attribute attribute :
				Dom4JUtil.toAttributeList(element.attributes())) {

			add((Attribute)attribute.clone());
		}
	}

	private void _addElements(Element element) {
		for (Element childElement :
				Dom4JUtil.toElementList(element.elements())) {

			add(PoshiElementFactory.newPoshiElement(childElement));
		}
	}

	private static final String _PHRASE_REGEX =
		"([\\d]+|[A-Z][a-z]+|[A-Z]+(?![a-z]))";

}