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

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class UnsupportedElement extends PoshiElement {

	public UnsupportedElement(Element element) {
		super(element.getName(), element);
	}

	public UnsupportedElement(String readableSyntax) {
		super("unsupported", readableSyntax);
	}

	@Override
	public void addAttributes(String readableSyntax) {
	}

	@Override
	public void addElements(String readableSyntax) {
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");

		for (int i = 0; i < 80; i++) {
			sb.append("#");
		}

		sb.append("\n");
		sb.append("The Poshi \"");
		sb.append(getName());
		sb.append("\" element is not supported in the readable syntax. ");
		sb.append("Please update this test.");
		sb.append("\n");

		for (int i = 0; i < 80; i++) {
			sb.append("#");
		}

		return sb.toString();
	}

}