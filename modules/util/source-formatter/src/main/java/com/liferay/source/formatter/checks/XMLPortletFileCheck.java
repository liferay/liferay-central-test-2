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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checks.util.XMLSourceUtil;
import com.liferay.util.xml.Dom4jUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLPortletFileCheck extends BaseFileCheck {

	public XMLPortletFileCheck(
		List<String> excludes, boolean portalSource, boolean subrepository) {

		_excludes = excludes;
		_portalSource = portalSource;
		_subrepository = subrepository;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("/liferay-portlet.xml") ||
			((_portalSource || _subrepository) &&
			 fileName.endsWith("/portlet-custom.xml")) ||
			(!_portalSource && !_subrepository &&
			 fileName.endsWith("/portlet.xml"))) {

			content = _formatPortletXML(fileName, absolutePath, content);
		}

		return content;
	}

	private String _formatPortletXML(
			String fileName, String absolutePath, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		boolean checkNumericalPortletNameElement = !isExcludedPath(
			_NUMERICAL_PORTLET_NAME_ELEMENT_EXCLUDES, absolutePath);

		List<Element> portletElements = rootElement.elements("portlet");

		for (Element portletElement : portletElements) {
			if (checkNumericalPortletNameElement) {
				Element portletNameElement = portletElement.element(
					"portlet-name");

				String portletNameText = portletNameElement.getText();

				if (!Validator.isNumber(portletNameText)) {
					addMessage(
						fileName,
						"Nonstandard portlet-name element '" + portletNameText +
							"'");
				}
			}

			if (fileName.endsWith("/liferay-portlet.xml")) {
				continue;
			}

			XMLSourceUtil.sortElementsByChildElement(
				portletElement, "init-param", "name");

			Element portletPreferencesElement = portletElement.element(
				"portlet-preferences");

			if (portletPreferencesElement != null) {
				XMLSourceUtil.sortElementsByChildElement(
					portletPreferencesElement, "preference", "name");
			}
		}

		return StringUtil.replace(
			Dom4jUtil.toString(document), "\"/>\n", "\" />\n");
	}

	private static final String _NUMERICAL_PORTLET_NAME_ELEMENT_EXCLUDES =
		"numerical.portlet.name.element.excludes";

	private final List<String> _excludes;
	private final boolean _portalSource;
	private final boolean _subrepository;

}