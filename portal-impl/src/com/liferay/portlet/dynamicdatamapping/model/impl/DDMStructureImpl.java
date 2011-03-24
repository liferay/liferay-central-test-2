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
import com.liferay.portal.kernel.xml.Document;
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
		Element dynamicElement = _getElement(fieldName);

		if (dynamicElement != null) {
			return dynamicElement.attributeValue("type");
		}

		return null;
	}

	public boolean hasField(String fieldName) {
		Element dynamicElement = _getElement(fieldName);

		if (dynamicElement != null) {
			return true;
		}

		return false;
	}

	public void setXsd(String xsd) {		
		super.setXsd(xsd);
		
		_document = null;
	}

	private Document _getDocument() throws Exception {
		if (_document == null) {
			_document = SAXReaderUtil.read(getXsd());
		}

		return _document;
	}

	private Element _getElement(String name) {
		try {
			Document document = _getDocument();
	
			XPath xPathSelector = SAXReaderUtil.createXPath(
				"//dynamic-element[@name='" + name + "']");
	
			List<Node> nodes = xPathSelector.selectNodes(document);
	
			if (nodes.size() == 1) {
				return (Element)nodes.get(0);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	private static Log _log = LogFactoryUtil.getLog(DDMStructureImpl.class);

	private Document _document;

}