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

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStructureImpl
	extends DDMStructureModelImpl implements DDMStructure {

	public DDMStructureImpl() {
	}

	public String getFieldType(String fieldName) {
		Element dynamicElement = _getElementByName(getXsdDocument(), fieldName);

		if (dynamicElement != null) {
			return dynamicElement.attributeValue("type");
		}

		return null;
	}

	public Document getXsdDocument() {
		if (_xsdDocument == null) {
			try {
				_xsdDocument = SAXReaderUtil.read(getXsd());
			}
			catch (DocumentException de) {
			}
		}

		return _xsdDocument;
	}

	public boolean hasField(String fieldName) {
		Element dynamicElement = _getElementByName(getXsdDocument(), fieldName);

		if (dynamicElement != null) {
			return true;
		}

		return false;
	}

	public void setXsdDocument(Document xsdDocument) {
		_xsdDocument = xsdDocument;
	}

	private Element _getElementByName(Document document, String fieldName) {
		XPath xPathSelector = SAXReaderUtil.createXPath(
			"//dynamic-element[@name='" + fieldName + "']");

		List<Node> nodes = xPathSelector.selectNodes(document);

		if (nodes.size() == 1) {
			return (Element)nodes.get(0);
		}
		else {
			return null;
		}
	}

	private Document _xsdDocument = null;

}