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
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStructureImpl
	extends DDMStructureModelImpl implements DDMStructure {

	public DDMStructureImpl() {
	}

	public String getFieldType(String fieldName) {
		Map<String, String> fieldsTypeMap = _getFieldsTypeMap();

		return fieldsTypeMap.get(fieldName);
	}

	public boolean hasField(String fieldName) {
		Map<String, String> fieldsTypeMap = _getFieldsTypeMap();

		return fieldsTypeMap.containsKey(fieldName);
	}

	public void setXsd(String xsd) {
		super.setXsd(xsd);

		_fieldsType = null;
	}

	private Map<String, String> _getFieldsTypeMap() {
		if (_fieldsType == null) {
			_fieldsType = new HashMap<String, String>();

			try {
				XPath xPathSelector = SAXReaderUtil.createXPath(
					"//dynamic-element");

				List<Node> nodes = xPathSelector.selectNodes(
					SAXReaderUtil.read(getXsd()));

				if (nodes != null) {
					Iterator<Node> itr = nodes.iterator();

					while (itr.hasNext()) {
						Element element = (Element)itr.next();

						String name = element.attributeValue("name");
						String type = element.attributeValue("type");

						_fieldsType.put(name, type);
					}
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _fieldsType;
	}

	private static Log _log = LogFactoryUtil.getLog(DDMStructureImpl.class);

	private Map<String, String> _fieldsType;

}