/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.util;

import java.util.Map;

/**
 * @author     Alexander Chow
 * @author     Raymond Augé
 * @deprecated
 */
public class JournalXslUtil {

	public static String transform(
			Map<String, String> tokens, String viewMode, String languageId,
			String xml, String script)
		throws Exception {

		return _instance._templateParser.transform(
			null, tokens, viewMode, languageId, xml, script);
	}

	private JournalXslUtil() {
		_templateParser = new XSLTemplateParser();
	}

	private static JournalXslUtil _instance = new JournalXslUtil();

	private TemplateParser _templateParser;

}