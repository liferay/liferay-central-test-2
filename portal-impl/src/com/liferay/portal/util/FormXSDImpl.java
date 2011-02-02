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

package com.liferay.portal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.FormXSD;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.List;

/**
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class FormXSDImpl implements FormXSD {

	public JSONArray getJSONArray(Document document) throws JSONException {
		return getJSONArray(document.getRootElement());
	}

	public JSONArray getJSONArray(Element element) throws JSONException {
		List<Element> dynamicElements = element.elements("dynamic-element");

		JSONArray children = JSONFactoryUtil.createJSONArray();

		for (Element dynamicElement : dynamicElements) {
			JSONObject field = JSONFactoryUtil.createJSONObject();

			for (Attribute attribute : dynamicElement.attributes()) {
				field.put(attribute.getName(), attribute.getValue());
			}

			Element metadata = dynamicElement.element("meta-data");

			if (metadata != null) {
				for (Element metadataEntry : metadata.elements()) {
					field.put(
						metadataEntry.attributeValue("name"),
						metadataEntry.getText());
				}
			}

			String type = field.getString("type");
			String key = "fields";

			if (type.equals(_TYPE_RADIO) ||
				type.equals(_TYPE_SELECT)) {

				key = "options";
			}

			field.put(key, getJSONArray(dynamicElement));

			children.put(field);
		}

		return children;
	}

	public JSONArray getJSONArray(String xml)
		throws DocumentException, JSONException {

		return getJSONArray(SAXReaderUtil.read(xml));
	}

	private static final String _TYPE_RADIO = "radio";

	private static final String _TYPE_SELECT = "select";

}