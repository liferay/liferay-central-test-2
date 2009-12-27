/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.collections.map.ReferenceMap;

/**
 * <a href="LocalizationUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This class is used to localize values stored in XML and is often used to add
 * localization behavior to value objects.
 * </p>
 *
 * <p>
 * Caching of the localized values is done in this class rather than in the
 * value object since value objects get flushed from cache fairly quickly.
 * Though lookups performed on a key based on an XML file is slower than lookups
 * done at the value object level in general, the value object will get flushed
 * at a rate which works against the performance gain. The cache is a soft hash
 * map which prevents memory leaks within the system while enabling the cache to
 * live longer than in a weak hash map.
 * </p>
 *
 * @author Alexander Chow
 * @author Jorge Ferrer
 * @author Mauro Mariuzzo
 * @author Julio Camarero
 */
public class LocalizationUtil {

	public static Object deserialize(JSONObject jsonObject) {
		Locale[] locales = LanguageUtil.getAvailableLocales();

		Map<Locale, String> map = new HashMap<Locale, String>();

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String value = jsonObject.getString(languageId);

			if (Validator.isNotNull(value)) {
				map.put(locale, value);
			}
		}

		return map;
	}

	public static String[] getAvailableLocales(String xml) {
		String attributeValue = _getRootAttribute(
			xml, _AVAILABLE_LOCALES, StringPool.BLANK);

		return StringUtil.split(attributeValue);
	}

	public static String getDefaultLocale(String xml) {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		return _getRootAttribute(xml, _DEFAULT_LOCALE, defaultLanguageId);
	}

	public static String getLocalization(
		String xml, String requestedLanguageId) {

		return getLocalization(xml, requestedLanguageId, true);
	}

	public static String getLocalization(
		String xml, String requestedLanguageId, boolean useDefault) {

		String value = _getCachedValue(xml, requestedLanguageId, useDefault);

		if (value != null) {
			return value;
		}
		else {
			value = StringPool.BLANK;
		}

		String systemDefaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		String defaultValue = StringPool.BLANK;

		if (!Validator.isXml(xml)) {
			if (requestedLanguageId.equals(systemDefaultLanguageId)) {
				value = xml;
			}
			else {
				value = defaultValue;
			}

			_setCachedValue(xml, requestedLanguageId, useDefault, value);

			return value;
		}

		XMLStreamReader reader = null;

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(portalClassLoader);
			}

			XMLInputFactory factory = XMLInputFactory.newInstance();

			reader = factory.createXMLStreamReader(new UnsyncStringReader(xml));

			String defaultLanguageId = StringPool.BLANK;

			// Skip root node

			if (reader.hasNext()) {
				reader.nextTag();

				defaultLanguageId = reader.getAttributeValue(
					null, _DEFAULT_LOCALE);

				if (Validator.isNull(defaultLanguageId)) {
					defaultLanguageId = systemDefaultLanguageId;
				}
			}

			// Find specified language and/or default language

			while (reader.hasNext()) {
				int event = reader.next();

				if (event == XMLStreamConstants.START_ELEMENT) {
					String languageId = reader.getAttributeValue(
						null, _LANGUAGE_ID);

					if (Validator.isNull(languageId)) {
						languageId = defaultLanguageId;
					}

					if (languageId.equals(defaultLanguageId) ||
						languageId.equals(requestedLanguageId)) {

						while (reader.hasNext()) {
							event = reader.next();

							if (event == XMLStreamConstants.CHARACTERS ||
								event == XMLStreamConstants.CDATA) {

								String text = reader.getText();

								if (languageId.equals(defaultLanguageId)) {
									defaultValue = text;
								}

								if (languageId.equals(requestedLanguageId)) {
									value = text;
								}

								break;
							}
							else if (event == XMLStreamConstants.END_ELEMENT) {
								break;
							}
						}

						if (Validator.isNotNull(value)) {
							break;
						}
					}
				}
				else if (event == XMLStreamConstants.END_DOCUMENT) {
					break;
				}
			}

			if (useDefault && Validator.isNull(value)) {
				value = defaultValue;
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
		finally {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}

			if (reader != null) {
				try {
					reader.close();
				}
				catch (Exception e) {
				}
			}
		}

		_setCachedValue(xml, requestedLanguageId, useDefault, value);

		return value;
	}

	public static Map<Locale, String> getLocalizationMap(
		PortletRequest portletRequest, String parameter) {

		Locale[] locales = LanguageUtil.getAvailableLocales();

		Map<Locale, String> map = new HashMap<Locale, String>();

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String localeParameter =
				parameter + StringPool.UNDERLINE + languageId;

			map.put(
				locale, ParamUtil.getString(portletRequest, localeParameter));
		}

		return map;
	}

	public static Map<Locale, String> getLocalizationMap(String xml) {
		Locale[] locales = LanguageUtil.getAvailableLocales();

		Map<Locale, String> map = new HashMap<Locale, String>();

		for (Locale locale : locales) {
			String languageId = LocaleUtil.toLanguageId(locale);

			map.put(locale, getLocalization(xml, languageId));
		}

		return map;
	}

	/**
	 * @deprecated Use <code>getLocalizationMap</code>.
	 */
	public static Map<Locale, String> getLocalizedParameter(
		PortletRequest portletRequest, String parameter) {

		return getLocalizationMap(portletRequest, parameter);
	}

	public static String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId) {

		return getPreferencesValue(preferences, key, languageId, true);
	}

	public static String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault) {

		String localizedKey = _getPreferencesKey(key, languageId);

		String value = preferences.getValue(localizedKey, StringPool.BLANK);

		if (useDefault && Validator.isNull(value)) {
			value = preferences.getValue(key, StringPool.BLANK);
		}

		return value;
	}

	public static String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId) {

		return getPreferencesValues(preferences, key, languageId, true);
	}

	public static String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault) {

		String localizedKey = _getPreferencesKey(key, languageId);

		String[] values = preferences.getValues(localizedKey, new String[0]);

		if (useDefault && Validator.isNull(values)) {
			values = preferences.getValues(key, new String[0]);
		}

		return values;
	}

	public static String removeLocalization(
		String xml, String key, String requestedLanguageId) {

		return removeLocalization(xml, key, requestedLanguageId, false);
	}

	public static String removeLocalization(
		String xml, String key, String requestedLanguageId, boolean cdata) {

		if (Validator.isNull(xml)) {
			return StringPool.BLANK;
		}

		xml = _sanitizeXML(xml);

		String systemDefaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		XMLStreamReader reader = null;
		XMLStreamWriter writer = null;

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(portalClassLoader);
			}

			XMLInputFactory inputFactory = XMLInputFactory.newInstance();

			reader = inputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			String availableLocales = StringPool.BLANK;
			String defaultLanguageId = StringPool.BLANK;

			// Read root node

			if (reader.hasNext()) {
				reader.nextTag();

				availableLocales = reader.getAttributeValue(
					null, _AVAILABLE_LOCALES);
				defaultLanguageId = reader.getAttributeValue(
					null, _DEFAULT_LOCALE);

				if (Validator.isNull(defaultLanguageId)) {
					defaultLanguageId = systemDefaultLanguageId;
				}
			}

			if ((availableLocales != null) &&
				(availableLocales.indexOf(requestedLanguageId) != -1)) {

				availableLocales = StringUtil.remove(
					availableLocales, requestedLanguageId, StringPool.COMMA);

				UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(
					true);

				XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

				writer = outputFactory.createXMLStreamWriter(
					unsyncStringWriter);

				writer.writeStartDocument();
				writer.writeStartElement(_ROOT);
				writer.writeAttribute(_AVAILABLE_LOCALES, availableLocales);
				writer.writeAttribute(_DEFAULT_LOCALE, defaultLanguageId);

				_copyNonExempt(
					reader, writer, requestedLanguageId, defaultLanguageId,
					cdata);

				writer.writeEndElement();
				writer.writeEndDocument();

				writer.close();
				writer = null;

				xml = unsyncStringWriter.toString();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
		finally {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}

			if (reader != null) {
				try {
					reader.close();
				}
				catch (Exception e) {
				}
			}

			if (writer != null) {
				try {
					writer.close();
				}
				catch (Exception e) {
				}
			}
		}

		return xml;
	}

	public static void setLocalizedPreferencesValues (
			ActionRequest actionRequest, PortletPreferences preferences,
			String parameter)
		throws Exception {

		Map<Locale, String> map = getLocalizedParameter(
			actionRequest, parameter);

		for (Locale locale : map.keySet()) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String key = parameter + StringPool.UNDERLINE + languageId;
			String value = map.get(locale);

			preferences.setValue(key, value);
		}
	}

	public static void setPreferencesValue(
			PortletPreferences preferences, String key, String languageId,
			String value)
		throws Exception {

		preferences.setValue(_getPreferencesKey(key, languageId), value);
	}

	public static void setPreferencesValues(
			PortletPreferences preferences, String key, String languageId,
			String[] values)
		throws Exception {

		preferences.setValues(_getPreferencesKey(key, languageId), values);
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

		return updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId, false);
	}

	public static String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId, boolean cdata) {

		xml = _sanitizeXML(xml);

		XMLStreamReader reader = null;
		XMLStreamWriter writer = null;

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(portalClassLoader);
			}

			XMLInputFactory inputFactory = XMLInputFactory.newInstance();

			reader = inputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			String availableLocales = StringPool.BLANK;

			// Read root node

			if (reader.hasNext()) {
				reader.nextTag();

				availableLocales = reader.getAttributeValue(
					null, _AVAILABLE_LOCALES);

				if (Validator.isNull(availableLocales)) {
					availableLocales = defaultLanguageId;
				}

				if (availableLocales.indexOf(requestedLanguageId) == -1) {
					availableLocales = StringUtil.add(
						availableLocales, requestedLanguageId,
						StringPool.COMMA);
				}
			}

			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(
				true);

			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

			writer = outputFactory.createXMLStreamWriter(unsyncStringWriter);

			writer.writeStartDocument();
			writer.writeStartElement(_ROOT);
			writer.writeAttribute(_AVAILABLE_LOCALES, availableLocales);
			writer.writeAttribute(_DEFAULT_LOCALE, defaultLanguageId);

			_copyNonExempt(
				reader, writer, requestedLanguageId, defaultLanguageId,
				cdata);

			if (cdata) {
				writer.writeStartElement(key);
				writer.writeAttribute(_LANGUAGE_ID, requestedLanguageId);
				writer.writeCData(value);
				writer.writeEndElement();
			}
			else {
				writer.writeStartElement(key);
				writer.writeAttribute(_LANGUAGE_ID, requestedLanguageId);
				writer.writeCharacters(value);
				writer.writeEndElement();
			}

			writer.writeEndElement();
			writer.writeEndDocument();

			writer.close();
			writer = null;

			xml = unsyncStringWriter.toString();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
		finally {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}

			if (reader != null) {
				try {
					reader.close();
				}
				catch (Exception e) {
				}
			}

			if (writer != null) {
				try {
					writer.close();
				}
				catch (Exception e) {
				}
			}
		}

		return xml;
	}

	private static void _copyNonExempt(
			XMLStreamReader reader, XMLStreamWriter writer,
			String exemptLanguageId, String defaultLanguageId, boolean cdata)
		throws XMLStreamException {

		while (reader.hasNext()) {
			int event = reader.next();

			if (event == XMLStreamConstants.START_ELEMENT) {
				String languageId = reader.getAttributeValue(
					null, _LANGUAGE_ID);

				if (Validator.isNull(languageId)) {
					languageId = defaultLanguageId;
				}

				if (!languageId.equals(exemptLanguageId)) {
					writer.writeStartElement(reader.getLocalName());
					writer.writeAttribute(_LANGUAGE_ID, languageId);

					while (reader.hasNext()) {
						event = reader.next();

						if (event == XMLStreamConstants.CHARACTERS ||
							event == XMLStreamConstants.CDATA) {

							String text = reader.getText();

							if (cdata) {
								writer.writeCData(text);
							}
							else {
								writer.writeCharacters(reader.getText());
							}

							break;
						}
						else if (event == XMLStreamConstants.END_ELEMENT) {
							break;
						}
					}

					writer.writeEndElement();
				}
			}
			else if (event == XMLStreamConstants.END_DOCUMENT) {
				break;
			}
		}
	}

	private static String _getCachedValue(
		String xml, String requestedLanguageId, boolean useDefault) {

		String value = null;

		Map<Tuple, String> valueMap = _cache.get(xml);

		if (valueMap != null) {
			Tuple subkey = new Tuple(useDefault, requestedLanguageId);

			value = valueMap.get(subkey);
		}

		return value;
	}

	private static String _getPreferencesKey(String key, String languageId) {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		if (!languageId.equals(defaultLanguageId)) {
			key += StringPool.UNDERLINE + languageId;
		}

		return key;
	}

	private static String _getRootAttribute(
		String xml, String name, String defaultValue) {

		String value = null;

		XMLStreamReader reader = null;

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(portalClassLoader);
			}

			XMLInputFactory factory = XMLInputFactory.newInstance();

			reader = factory.createXMLStreamReader(new UnsyncStringReader(xml));

			if (reader.hasNext()) {
				reader.nextTag();

				value = reader.getAttributeValue(null, name);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
		finally {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}

			if (reader != null) {
				try {
					reader.close();
				}
				catch (Exception e) {
				}
			}
		}

		if (Validator.isNull(value)) {
			value = defaultValue;
		}

		return value;
	}

	private static String _sanitizeXML(String xml) {
		if (Validator.isNull(xml) || (xml.indexOf("<root") == -1)) {
			xml = _EMPTY_ROOT_NODE;
		}

		return xml;
	}

	private static void _setCachedValue(
		String xml, String requestedLanguageId, boolean useDefault,
		String value) {

		if (Validator.isNotNull(xml) && !xml.equals(_EMPTY_ROOT_NODE)) {
			synchronized (_cache) {
				Map<Tuple, String> map = _cache.get(xml);

				if (map == null) {
					map = new HashMap<Tuple, String>();
				}

				Tuple subkey = new Tuple(useDefault, requestedLanguageId);

				map.put(subkey, value);

				_cache.put(xml, map);
			}
		}
	}

	private static final String _AVAILABLE_LOCALES = "available-locales";

	private static final String _DEFAULT_LOCALE = "default-locale";

	private static final String _EMPTY_ROOT_NODE = "<root />";

	private static final String _LANGUAGE_ID = "language-id";

	private static final String _ROOT = "root";

	private static Log _log = LogFactoryUtil.getLog(LocalizationUtil.class);

	private static Map<String, Map<Tuple, String>> _cache = new ReferenceMap(
		ReferenceMap.SOFT, ReferenceMap.HARD);

}