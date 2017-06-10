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
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THESE_PROPERTIES;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THESE_VARIABLES;
import static com.liferay.poshi.runner.util.StringPool.COLON;

import com.liferay.poshi.runner.util.StringUtil;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class DefinitionElement extends PoshiElement {

	public DefinitionElement(Element element) {
		super("definition", element);
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		sb.append(FEATURE);
		sb.append(COLON);
		sb.append("\n\n");
		sb.append(BACKGROUND);
		sb.append(": This executes once per feature file");
		sb.append("\n\t");
		sb.append(GIVEN);
		sb.append(" ");
		sb.append(THESE_PROPERTIES);

		for (Iterator<PoshiElement> i =
			elementIterator("property"); i.hasNext();) {

			PoshiElement poshiElement = i.next();

			sb.append(poshiElement.toReadableSyntax());
		}

		if (elements("var").size() != 0) {
			sb.append("\n\t");
			sb.append(AND);
			sb.append(" ");
			sb.append(THESE_VARIABLES);

			for (Iterator<PoshiElement> i = elementIterator("var");
				 i.hasNext();) {

				PoshiElement poshiElement = i.next();

				sb.append(poshiElement.toReadableSyntax());
			}
		}

		sb.append("\n");

		for (Iterator<PoshiElement> i = elementIterator("set-up");
			 i.hasNext();) {

			PoshiElement poshiElement = i.next();

			sb.append(poshiElement.toReadableSyntax());
		}

		sb.append("\n");

		for (Iterator<PoshiElement> i = elementIterator("tear-down");
			 i.hasNext();) {

			PoshiElement poshiElement = i.next();

			sb.append(poshiElement.toReadableSyntax());
		}

		for (Iterator<PoshiElement> i = elementIterator("command");
			 i.hasNext();) {

			sb.append("\n");

			PoshiElement poshiElement = i.next();

			sb.append(poshiElement.toReadableSyntax());
		}

		return sb.toString();
	}

}