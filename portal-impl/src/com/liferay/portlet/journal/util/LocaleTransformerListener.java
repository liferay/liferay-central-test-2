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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.BaseTransformerListener;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class LocaleTransformerListener extends BaseTransformerListener {

	@Override
	public String onScript(
		String script, Document document, String languageId,
		Map<String, String> tokens) {

		if (_log.isDebugEnabled()) {
			_log.debug("onScript");
		}

		return StringUtil.replace(script, "@language_id@", languageId);
	}

	@Override
	public Document onXml(
		Document document, String languageId, Map<String, String> tokens) {

		if (_log.isDebugEnabled()) {
			_log.debug("onXml");
		}

		filterByLanguage(document, languageId);

		return document;
	}

	protected void filterByLanguage(Document document, String languageId) {
		Element rootElement = document.getRootElement();

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		String[] availableLocales = StringUtil.split(
			rootElement.attributeValue("available-locales", defaultLanguageId));

		String defaultLocale = rootElement.attributeValue(
			"default-locale", defaultLanguageId);

		boolean supportedLocale = false;

		for (String availableLocale : availableLocales) {
			if (StringUtil.equalsIgnoreCase(availableLocale, languageId)) {
				supportedLocale = true;

				break;
			}
		}

		if (!supportedLocale) {
			filterByLanguage(rootElement, defaultLocale, defaultLanguageId);
		}
		else {
			filterByLanguage(rootElement, languageId, defaultLanguageId);
		}
	}

	protected void filterByLanguage(
		Element root, String languageId, String defaultLanguageId) {

		Element defaultLanguageElement = null;

		boolean hasLanguageIdElement = false;

		List<Element> elements = root.elements();

		int listIndex = elements.size() - 1;

		while (listIndex >= 0) {
			Element element = elements.get(listIndex);

			String tempLanguageId = element.attributeValue(
				"language-id", languageId);

			if (StringUtil.equalsIgnoreCase(tempLanguageId, languageId)) {
				hasLanguageIdElement = true;

				filterByLanguage(element, languageId, defaultLanguageId);
			}
			else {
				if (StringUtil.equalsIgnoreCase(
						tempLanguageId, defaultLanguageId)) {

					defaultLanguageElement = element;
				}

				root.remove(element);
			}

			listIndex--;
		}

		if (!hasLanguageIdElement && (defaultLanguageElement != null)) {
			root.add(defaultLanguageElement);

			filterByLanguage(
				defaultLanguageElement, languageId, defaultLanguageId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LocaleTransformerListener.class);

}