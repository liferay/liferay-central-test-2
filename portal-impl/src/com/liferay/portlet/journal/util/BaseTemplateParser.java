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

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.TransformException;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseTemplateParser implements TemplateParser {

	public String transform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, String xml, String script)
		throws TransformException {

		try {
			return doTransform(
				themeDisplay, tokens, viewMode, languageId, xml, script);
		}
		catch (TransformException te) {
			throw te;
		}
		catch (Exception e) {
			throw new TransformException(e);
		}
	}

	protected String doTransform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, String xml, String script)
		throws Exception {

		return null;
	}

}