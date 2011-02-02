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

package com.liferay.portlet.forms.util;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;

import java.io.IOException;

/**
 * @author Bruno Basto
 * @author Brian Wing Shun Chan
 */
public class FormsXMLUtil {

	public static String formatXML(Document document)
		throws IOException {

		return getFormsXML().formatXML(document);
	}

	public static String formatXML(String xml)
		throws DocumentException, IOException {

		return getFormsXML().formatXML(xml);
	}

	public static FormsXML getFormsXML() {
		return _formsXML;
	}

	public void setFormsXML(FormsXML formsXML) {
		_formsXML = formsXML;
	}

	private static FormsXML _formsXML;

}