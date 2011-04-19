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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.util.List;

/**
 * @author Raymond Augé
 */
public class LocaleTransformerListener extends TransformerListener {

	public String onXml(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onXml");
		}

		s = replace(s);

		return s;
	}

	public String onScript(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onScript");
		}

		s = StringUtil.replace(s, "@language_id@", _requestedLocale);

		return s;
	}

	public String onOutput(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onOutput");
		}

		return s;
	}

	protected String replace(String xml) {
		if (xml == null) {
			return xml;
		}

		_requestedLocale = getLanguageId();

		try {
			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			String defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getDefault());

			String[] availableLocales = StringUtil.split(
				root.attributeValue("available-locales", defaultLanguageId));

			String defaultLocale = root.attributeValue(
				"default-locale", defaultLanguageId);

			boolean isSupportedLocale = false;

			for (int i = 0; i < availableLocales.length; i++) {
				if (availableLocales[i].equalsIgnoreCase(getLanguageId())) {
					isSupportedLocale = true;

					break;
				}
			}

			if (!isSupportedLocale) {
				setLanguageId(defaultLocale);
			}

			replace(root);

			xml = DDMXMLUtil.formatXML(doc);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return xml;
	}

	protected void replace(Element root) {
		List<Element> children = root.elements();

		int listIndex = children.size() - 1;

		while (listIndex >= 0) {
			Element child = children.get(listIndex);

			String languageId = child.attributeValue(
				"language-id", getLanguageId());

			if (!languageId.equalsIgnoreCase(getLanguageId())) {
				root.remove(child);
			}
			else{
				replace(child);
			}

			listIndex--;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LocaleTransformerListener.class);

	private String _requestedLocale = StringPool.BLANK;

}