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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;

import javax.servlet.jsp.PageContext;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 */
public class DDMXSDUtil {

	public static DDMXSD getDDMXSD() {
		return _ddmXSD;
	}

	public static String getHTML(PageContext pageContext, Document document)
		throws Exception {

		return getDDMXSD().getHTML(pageContext, document);
	}

	public static String getHTML(PageContext pageContext, Element element)
		throws Exception {

		return getDDMXSD().getHTML(pageContext, element);
	}

	public static String getHTML(PageContext pageContext, String xml)
		throws Exception {

		return getDDMXSD().getHTML(pageContext, xml);
	}

	public static JSONArray getJSONArray(Document document)
		throws JSONException {

		return getDDMXSD().getJSONArray(document);
	}

	public static JSONArray getJSONArray(String xml)
		throws DocumentException, JSONException {

		return getDDMXSD().getJSONArray(xml);
	}

	public void setDDMXSD(DDMXSD ddmXSD) {
		_ddmXSD = ddmXSD;
	}

	private static DDMXSD _ddmXSD;

}