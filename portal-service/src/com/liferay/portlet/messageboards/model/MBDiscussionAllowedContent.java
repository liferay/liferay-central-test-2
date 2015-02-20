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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sergio González
 */
public class MBDiscussionAllowedContent {

	public MBDiscussionAllowedContent(String allowedContent) {
		Pattern paddingPattern = Pattern.compile("\\s+");

		Matcher matcher = paddingPattern.matcher(allowedContent);

		allowedContent = matcher.replaceAll(StringPool.BLANK);

		String[] allowedContentElementAttributesArray = StringUtil.split(
			allowedContent, StringPool.SEMICOLON);

		for (String allowedContentElementAttributes :
				allowedContentElementAttributesArray) {

			int x = allowedContentElementAttributes.indexOf(
				StringPool.OPEN_BRACKET);
			int y = allowedContentElementAttributes.indexOf(
				StringPool.CLOSE_BRACKET);

			String allowedContentElement = allowedContentElementAttributes;
			String[] allowedContentAttributes = new String[0];

			if ((x != -1) && (y != -1)) {
				allowedContentElement =
					allowedContentElementAttributes.substring(0, x);
				allowedContentAttributes = StringUtil.split(
					allowedContentElementAttributes.substring(x + 1, y));
			}

			_allowedContentElementAttributes.put(
				allowedContentElement, allowedContentAttributes);
		}
	}

	public Map<String, String[]> getAllowedContentElementAttributes() {
		return _allowedContentElementAttributes;
	}

	private final Map<String, String[]> _allowedContentElementAttributes =
		new HashMap<>();

}