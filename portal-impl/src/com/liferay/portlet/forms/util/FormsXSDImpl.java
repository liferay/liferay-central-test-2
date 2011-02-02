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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.List;

/**
 * @author Bruno Basto
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 */
public class FormsXSDImpl implements FormsXSD {

	public JSONArray getJSONArray(Document document) throws JSONException {
		return getJSONArray(document.getRootElement());
	}

	public JSONArray getJSONArray(Element element) throws JSONException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Element> dynamicElements = element.elements("dynamic-element");

		for (Element dynamicElement : dynamicElements) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			for (Attribute attribute : dynamicElement.attributes()) {
				jsonObject.put(attribute.getName(), attribute.getValue());
			}

			Element metadataElement = dynamicElement.element("meta-data");

			if (metadataElement != null) {
				for (Element metadataEntry : metadataElement.elements()) {
					jsonObject.put(
						metadataEntry.attributeValue("name"),
						metadataEntry.getText());
				}
			}

			String type = jsonObject.getString("type");

			String key = "fields";

			if (type.equals(_TYPE_RADIO) || type.equals(_TYPE_SELECT)) {
				key = "options";
			}

			jsonObject.put(key, getJSONArray(dynamicElement));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public JSONArray getJSONArray(String xml)
		throws DocumentException, JSONException {

		return getJSONArray(SAXReaderUtil.read(xml));
	}

	private static final String _TYPE_RADIO = "radio";

	private static final String _TYPE_SELECT = "select";

}