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

package com.liferay.portlet.dynamicdatamapping.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStructureImpl
	extends DDMStructureModelImpl implements DDMStructure {

	public DDMStructureImpl() {
	}

	public String getFieldDataType(String fieldName) {
		Map<String, Map<String, String>> fieldsMap = _getFieldsMap();

		Map<String, String> field = fieldsMap.get(fieldName);

		return field.get("dataType");
	}

	public String getFieldLabel(String fieldName) {
		Map<String, Map<String, String>> fieldsMap = _getFieldsMap();

		Map<String, String> field = fieldsMap.get(fieldName);

		return GetterUtil.getString(field.get("label"), fieldName);
	}

	public Set<String> getFieldNames() {
		Map<String, Map<String, String>> fieldsMap = _getFieldsMap();

		return fieldsMap.keySet();
	}

	public Map<String, Map<String, String>> getFieldsMap() {
		return _getFieldsMap();
	}

	public boolean getFieldRequired(String fieldName) {
		Map<String, Map<String, String>> fieldsMap = _getFieldsMap();

		Map<String, String> field = fieldsMap.get(fieldName);

		return GetterUtil.getBoolean(field.get("required"));
	}

	public String getFieldType(String fieldName) {
		Map<String, Map<String, String>> fieldsMap = _getFieldsMap();

		Map<String, String> field = fieldsMap.get(fieldName);

		return field.get("type");
	}

	public boolean hasField(String fieldName) {
		Map<String, Map<String, String>> fieldsMap = _getFieldsMap();

		return fieldsMap.containsKey(fieldName);
	}

	public void setXsd(String xsd) {
		super.setXsd(xsd);

		_fieldsMap = null;
	}

	private Map<String, String> _getField(Element element) {
		Map<String, String> field = new HashMap<String, String>();

		Element metaDataElement = element.element("meta-data");

		if (metaDataElement != null) {
			List<Element> childMetaDataElements = metaDataElement.elements();

			for (Element childMetaDataElement : childMetaDataElements) {
				String name = childMetaDataElement.attributeValue("name");
				String value = childMetaDataElement.getText();

				field.put(name, value);
			}
		}

		for (Attribute attribute : element.attributes()) {
			field.put(attribute.getName(), attribute.getValue());
		}

		return field;
	}

	private Map<String, Map<String, String>> _getFieldsMap() {
		if (_fieldsMap == null) {
			synchronized (this) {
				if (_fieldsMap == null) {
					_fieldsMap = new HashMap<String, Map<String, String>>();

					try {
						XPath xPathSelector = SAXReaderUtil.createXPath(
							"//dynamic-element[@dataType]");

						List<Node> nodes = xPathSelector.selectNodes(
							SAXReaderUtil.read(getXsd()));

						Iterator<Node> itr = nodes.iterator();

						while (itr.hasNext()) {
							Element element = (Element)itr.next();

							String name = element.attributeValue("name");

							_fieldsMap.put(name, _getField(element));
						}
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				}
			}
		}

		return _fieldsMap;
	}

	private static Log _log = LogFactoryUtil.getLog(DDMStructureImpl.class);

	private Map<String, Map<String, String>> _fieldsMap;

}