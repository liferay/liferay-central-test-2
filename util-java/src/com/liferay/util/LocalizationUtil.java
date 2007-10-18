/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.xml.XMLFormatter;

import java.io.StringReader;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="LocalizationUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class LocalizationUtil {

	public static String[] getAvailableLocales(String xml) {
		String[] availableLocales = new String[0];

		try {
			SAXReader reader = new SAXReader();

			Document doc = reader.read(new StringReader(xml));

			Element root = doc.getRootElement();

			availableLocales = StringUtil.split(
				root.attributeValue("available-locales"));
		}
		catch (Exception e) {
			_log.warn(e);
		}

		return availableLocales;
	}

	public static String getDefaultLocale(String xml) {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		try {
			SAXReader reader = new SAXReader();

			Document doc = reader.read(new StringReader(xml));

			Element root = doc.getRootElement();

			return root.attributeValue("default-locale", defaultLanguageId);
		}
		catch (Exception e) {
			_log.warn(e);
		}

		return defaultLanguageId;
	}

	public static String getLocalization(
			String xml, String requestedLanguageId) {

		String defaultLanguageId =
			LocaleUtil.toLanguageId(LocaleUtil.getDefault());

		return getLocalization(xml, requestedLanguageId, defaultLanguageId);
	}

	public static String getLocalization(
			String xml, String requestedLanguageId, String defaultLanguageId) {

		String value = StringPool.BLANK;

		try {
			SAXReader reader = new SAXReader();

			Document doc = reader.read(new StringReader(xml));

			Element root = doc.getRootElement();

			Iterator itr = root.elements().iterator();

			while (itr.hasNext()) {
				Element el = (Element)itr.next();

				String languageId =
					el.attributeValue("language-id", defaultLanguageId);

				if (languageId.equals(defaultLanguageId)) {
					value = el.getText();
				}

				if (languageId.equals(requestedLanguageId)) {
					value = el.getText();

					break;
				}
			}
		}
		catch (Exception e) {
			_log.warn(e);
		}

		return value;
	}

	public static String removeLocalization(
			String xml, String key, String requestedLanguageId) {

		if (Validator.isNull(xml) || (xml.indexOf("<root") == -1)) {
			xml = "<root />";
		}

		String defaultLanguageId =
			LocaleUtil.toLanguageId(LocaleUtil.getDefault());

		try {
			SAXReader reader = new SAXReader();

			Document doc = reader.read(new StringReader(xml));

			Element root = doc.getRootElement();

			String availableLocales = root.attributeValue("available-locales");

			defaultLanguageId =
				root.attributeValue("default-locale", defaultLanguageId);

			if (availableLocales.indexOf(requestedLanguageId) != -1) {
				Iterator itr = root.elements().iterator();

				while (itr.hasNext()) {
					Element el = (Element) itr.next();

					String languageId =
						el.attributeValue("language-id", defaultLanguageId);

					if (languageId.equals(requestedLanguageId)) {
						root.remove(el);

						break;
					}
				}

				availableLocales = StringUtil.remove(
					availableLocales, requestedLanguageId, StringPool.COMMA);

				root.addAttribute("available-locales", availableLocales);
			}

			xml = XMLFormatter.toString(doc, "  ");
		}
		catch (Exception e) {
			_log.warn(e);
		}

		return xml;
	}

	public static String updateLocalization(
			String xml, String key, String value) {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		return updateLocalization(
			xml, key, value, defaultLanguageId, defaultLanguageId);
	}

	public static String updateLocalization(
			String xml, String key, String value, String requestedLanguageId) {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		return updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId);
	}

	public static String updateLocalization(
			String xml, String key, String value, String requestedLanguageId,
			String defaultLanguageId) {

		if (Validator.isNull(xml) || (xml.indexOf("<root") == -1)) {
			xml = "<root />";
		}

		String updatedXml = xml;

		try {
			SAXReader reader = new SAXReader();

			Document doc = reader.read(new StringReader(xml));

			Element root = doc.getRootElement();

			String availableLocales = root.attributeValue("available-locales");

			Element localeEl = null;

			Iterator itr = root.elements().iterator();

			while (itr.hasNext()) {
				Element el = (Element) itr.next();

				String languageId =
					el.attributeValue("language-id", defaultLanguageId);

				if (languageId.equals(requestedLanguageId)) {
					localeEl = el;

					break;
				}
			}

			if (localeEl != null) {
				localeEl.addAttribute("language-id", requestedLanguageId);
				localeEl.setText(value);
			}
			else {
				localeEl = root.addElement(key);

				localeEl.addAttribute("language-id", requestedLanguageId);
				localeEl.setText(value);

				if (availableLocales == null) {
					availableLocales = defaultLanguageId;
				}

				if (!requestedLanguageId.equals(defaultLanguageId)) {
					availableLocales += StringPool.COMMA + requestedLanguageId;
				}

				root.addAttribute("available-locales", availableLocales);
			}

			root.addAttribute("default-locale", defaultLanguageId);

			updatedXml = XMLFormatter.toString(doc, "  ");
		}
		catch (Exception e) {
			_log.warn(e);
		}

		return updatedXml;
	}

	private static Log _log = LogFactoryUtil.getLog(LocalizationUtil.class);

}