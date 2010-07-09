/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.journal.TransformException;

import java.util.List;
import java.util.Map;

/**
 * @author	   Alexander Chow
 * @author	   Brian Wing Shun Chan
 * @author	   Raymond Augé
 * @deprecated
 */
public class JournalVmUtil {

	public static List<TemplateNode> extractDynamicContents(Element parent)
		throws TransformException {

		return _instance._velocityTemplateParser.extractDynamicContents(
			null, parent);
	}

	public static String transform(
			Map<String, String> tokens, String viewMode, String languageId,
			String xml, String script)
		throws TransformException {

		return _instance._velocityTemplateParser.transform(
			null, tokens, viewMode, languageId, xml, script);
	}

	private JournalVmUtil() {
		_velocityTemplateParser = new VelocityTemplateParser();
	}

	private static JournalVmUtil _instance = new JournalVmUtil();

	private VelocityTemplateParser _velocityTemplateParser;

}