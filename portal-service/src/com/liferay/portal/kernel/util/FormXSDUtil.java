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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;

/**
 * @author Eduardo Lundgren
 */
public class FormXSDUtil {

	public static FormXSD getFormXSD() {
		return _formXSD;
	}

	public static JSONArray getJSONArray(Document document)
		throws JSONException {

		return getFormXSD().getJSONArray(document);
	}

	public static JSONArray getJSONArray(String xml)
		throws DocumentException, JSONException {

		return getFormXSD().getJSONArray(xml);
	}

	public void setFormXSD(FormXSD formXsd) {
		_formXSD = formXsd;
	}

	private static FormXSD _formXSD;

}