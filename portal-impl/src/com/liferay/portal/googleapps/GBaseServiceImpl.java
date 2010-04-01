/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.googleapps;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

/**
 * <a href="GBaseServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GBaseServiceImpl {

	public GBaseServiceImpl(GAuthenticator gAuthenticator) {
		_gAuthenticator = gAuthenticator;
	}

	protected static final String APPS_URL =
		"https://apps-apis.google.com/a/feeds";

	protected Element addAppsProperty(
		Element parentElement, String name, String value) {

		Element element = parentElement.addElement("apps:property");

		element.addAttribute("name", name);
		element.addAttribute("value", value);

		return element;
	}

	protected Element addAtomCategory(Element parentElement, String type) {
		Element element = parentElement.addElement("atom:category");

		element.addAttribute("scheme", "http://schemas.google.com/g/2005#kind");
		element.addAttribute(
			"term", "http://schemas.google.com/apps/2006#" + type);

		return element;
	}

	protected Element addAtomEntry(Document document) {
		Element element = document.addElement("atom:entry");

		element.add(getAppsNamespace());
		element.add(getAtomNamespace());

		return element;
	}

	protected Namespace getAppsNamespace() {
		return SAXReaderUtil.createNamespace(_APPS_PREFIX, _APPS_URI);
	}

	protected QName getAppsQName(String localName) {
		return SAXReaderUtil.createQName(localName, getAppsNamespace());
	}

	protected Namespace getAtomNamespace() {
		return SAXReaderUtil.createNamespace(_ATOM_PREFIX, _ATOM_URI);
	}

	protected QName getAtomQName(String localName) {
		return SAXReaderUtil.createQName(localName, getAtomNamespace());
	}

	protected Http.Options getOptions() {
		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.AUTHORIZATION,
			"GoogleLogin auth=" + _gAuthenticator.getAuthenticationToken());

		return options;
	}

	private static final String _APPS_PREFIX = "apps";

	private static final String _APPS_URI =
		"http://schemas.google.com/apps/2006";

	private static final String _ATOM_PREFIX = "atom";

	private static final String _ATOM_URI = "http://www.w3.org/2005/Atom";

	private GAuthenticator _gAuthenticator;

}