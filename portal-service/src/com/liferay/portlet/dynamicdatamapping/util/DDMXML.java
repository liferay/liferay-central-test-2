/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.util.List;
import java.util.Locale;

/**
 * @author Bruno Basto
 * @author Brian Wing Shun Chan
 */
public interface DDMXML {

	public String formatXML(Document document);

	public String formatXML(String xml);

	public Fields getFields(DDMStructure structure, String xml)
		throws PortalException;

	public Fields getFields(
			DDMStructure structure, XPath xPath, String xml,
			List<String> fieldNames)
		throws PortalException;

	public String getXML(Document document, Fields fields);

	public String getXML(Fields fields);

	public String updateXMLDefaultLocale(
		String xml, Locale contentDefaultLocale,
		Locale contentNewDefaultLocale);

	public String validateXML(String xml) throws PortalException;

}