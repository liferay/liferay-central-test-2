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

import com.liferay.portal.kernel.util.StringBundler;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaXMLSecurityCheck extends BaseFileCheck {

	public JavaXMLSecurityCheck(
		List<String> runOutsidePortalExcludes, List<String> secureXMLExcludes) {

		_runOutsidePortalExcludes = runOutsidePortalExcludes;
		_secureXMLExcludes = secureXMLExcludes;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (isExcludedPath(_secureXMLExcludes, absolutePath) ||
			fileName.contains("/test/") ||
			fileName.contains("/testIntegration/")) {

			return content;
		}

		_checkXMLSecurity(fileName, absolutePath, content);

		return content;
	}

	private void _checkXMLSecurity(
		String fileName, String absolutePath, String content) {

		String[] xmlVulnerabitilies = new String[] {
			"DocumentBuilderFactory.newInstance",
			"new javax.xml.parsers.SAXParser",
			"new org.apache.xerces.parsers.SAXParser",
			"new org.dom4j.io.SAXReader", "new SAXParser", "new SAXReader",
			"SAXParserFactory.newInstance", "saxParserFactory.newInstance",
			"SAXParserFactory.newSAXParser", "saxParserFactory.newSAXParser",
			"XMLInputFactory.newFactory", "xmlInputFactory.newFactory",
			"XMLInputFactory.newInstance", "xmlInputFactory.newInstance"
		};

		boolean runOutsidePortalExclusion = isExcludedPath(
			_runOutsidePortalExcludes, absolutePath);

		for (String xmlVulnerabitily : xmlVulnerabitilies) {
			if (!content.contains(xmlVulnerabitily)) {
				continue;
			}

			StringBundler sb = new StringBundler(3);

			if (runOutsidePortalExclusion) {
				sb.append("Possible XXE or Quadratic Blowup security ");
				sb.append("vulnerability using ");
			}
			else {
				sb.append("Use SecureXMLFactoryProviderUtil.");
				sb.append("newDocumentBuilderFactory instead of ");
			}

			sb.append(xmlVulnerabitily);

			addMessage(fileName, sb.toString());
		}
	}

	private final List<String> _runOutsidePortalExcludes;
	private final List<String> _secureXMLExcludes;

}