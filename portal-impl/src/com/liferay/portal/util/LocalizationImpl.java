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

package com.liferay.portal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
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
 * <a href="LocalizationImpl.java.html"><b><i>View Source</i></b></a>
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
 * @author Brian Wing Shun Chan
 */
public class LocalizationImpl implements Localization {

	public Object deserialize(JSONObject jsonObject) {
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

	public String[] getAvailableLocales(String xml) {
		String attributeValue = _getRootAttribute(
			xml, _AVAILABLE_LOCALES, StringPool.BLANK);

		return StringUtil.split(attributeValue);
	}

	public String getDefaultLocale(String xml) {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		return _getRootAttribute(xml, _DEFAULT_LOCALE, defaultLanguageId);
	}

	public String getLocalization(String xml, String requestedLanguageId) {
		return getLocalization(xml, requestedLanguageId, true);
	}

	public String getLocalization(
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

		XMLStreamReader xmlStreamReader = null;

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(portalClassLoader);
			}

			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			String defaultLanguageId = StringPool.BLANK;

			// Skip root node

			if (xmlStreamReader.hasNext()) {
				xmlStreamReader.nextTag();

				defaultLanguageId = xmlStreamReader.getAttributeValue(
					null, _DEFAULT_LOCALE);

				if (Validator.isNull(defaultLanguageId)) {
					defaultLanguageId = systemDefaultLanguageId;
				}
			}

			// Find specified language and/or default language

			while (xmlStreamReader.hasNext()) {
				int event = xmlStreamReader.next();

				if (event == XMLStreamConstants.START_ELEMENT) {
					String languageId = xmlStreamReader.getAttributeValue(
						null, _LANGUAGE_ID);

					if (Validator.isNull(languageId)) {
						languageId = defaultLanguageId;
					}

					if (languageId.equals(defaultLanguageId) ||
						languageId.equals(requestedLanguageId)) {

						while (xmlStreamReader.hasNext()) {
							event = xmlStreamReader.next();

							if (event == XMLStreamConstants.CHARACTERS ||
								event == XMLStreamConstants.CDATA) {

								String text = xmlStreamReader.getText();

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

			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception e) {
				}
			}
		}

		_setCachedValue(xml, requestedLanguageId, useDefault, value);

		return value;
	}

	public Map<Locale, String> getLocalizationMap(
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

	public Map<Locale, String> getLocalizationMap(String xml) {
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
	public Map<Locale, String> getLocalizedParameter(
		PortletRequest portletRequest, String parameter) {

		return getLocalizationMap(portletRequest, parameter);
	}

	public String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId) {

		return getPreferencesValue(preferences, key, languageId, true);
	}

	public String getPreferencesValue(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault) {

		String localizedKey = _getPreferencesKey(key, languageId);

		String value = preferences.getValue(localizedKey, StringPool.BLANK);

		if (useDefault && Validator.isNull(value)) {
			value = preferences.getValue(key, StringPool.BLANK);
		}

		return value;
	}

	public String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId) {

		return getPreferencesValues(preferences, key, languageId, true);
	}

	public String[] getPreferencesValues(
		PortletPreferences preferences, String key, String languageId,
		boolean useDefault) {

		String localizedKey = _getPreferencesKey(key, languageId);

		String[] values = preferences.getValues(localizedKey, new String[0]);

		if (useDefault && Validator.isNull(values)) {
			values = preferences.getValues(key, new String[0]);
		}

		return values;
	}

	public String removeLocalization(
		String xml, String key, String requestedLanguageId) {

		return removeLocalization(xml, key, requestedLanguageId, false);
	}

	public String removeLocalization(
		String xml, String key, String requestedLanguageId, boolean cdata) {

		if (Validator.isNull(xml)) {
			return StringPool.BLANK;
		}

		xml = _sanitizeXML(xml);

		String systemDefaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		XMLStreamReader xmlStreamReader = null;
		XMLStreamWriter xmlStreamWriter = null;

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(portalClassLoader);
			}

			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			String availableLocales = StringPool.BLANK;
			String defaultLanguageId = StringPool.BLANK;

			// Read root node

			if (xmlStreamReader.hasNext()) {
				xmlStreamReader.nextTag();

				availableLocales = xmlStreamReader.getAttributeValue(
					null, _AVAILABLE_LOCALES);
				defaultLanguageId = xmlStreamReader.getAttributeValue(
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

				XMLOutputFactory xmlOutputFactory =
					XMLOutputFactory.newInstance();

				xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(
					unsyncStringWriter);

				xmlStreamWriter.writeStartDocument();
				xmlStreamWriter.writeStartElement(_ROOT);
				xmlStreamWriter.writeAttribute(
					_AVAILABLE_LOCALES, availableLocales);
				xmlStreamWriter.writeAttribute(
					_DEFAULT_LOCALE, defaultLanguageId);

				_copyNonExempt(
					xmlStreamReader, xmlStreamWriter, requestedLanguageId,
					defaultLanguageId, cdata);

				xmlStreamWriter.writeEndElement();
				xmlStreamWriter.writeEndDocument();

				xmlStreamWriter.close();
				xmlStreamWriter = null;

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

			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception e) {
				}
			}

			if (xmlStreamWriter != null) {
				try {
					xmlStreamWriter.close();
				}
				catch (Exception e) {
				}
			}
		}

		return xml;
	}

	public void setLocalizedPreferencesValues (
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

	public void setPreferencesValue(
			PortletPreferences preferences, String key, String languageId,
			String value)
		throws Exception {

		preferences.setValue(_getPreferencesKey(key, languageId), value);
	}

	public void setPreferencesValues(
			PortletPreferences preferences, String key, String languageId,
			String[] values)
		throws Exception {

		preferences.setValues(_getPreferencesKey(key, languageId), values);
	}

	public String updateLocalization(String xml, String key, String value) {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		return updateLocalization(
			xml, key, value, defaultLanguageId, defaultLanguageId);
	}

	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId) {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		return updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId);
	}

	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId) {

		return updateLocalization(
			xml, key, value, requestedLanguageId, defaultLanguageId, false);
	}

	public String updateLocalization(
		String xml, String key, String value, String requestedLanguageId,
		String defaultLanguageId, boolean cdata) {

		xml = _sanitizeXML(xml);

		XMLStreamReader xmlStreamReader = null;
		XMLStreamWriter xmlStreamWriter = null;

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(portalClassLoader);
			}

			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			String availableLocales = StringPool.BLANK;

			// Read root node

			if (xmlStreamReader.hasNext()) {
				xmlStreamReader.nextTag();

				availableLocales = xmlStreamReader.getAttributeValue(
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

			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

			xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(
				unsyncStringWriter);

			xmlStreamWriter.writeStartDocument();
			xmlStreamWriter.writeStartElement(_ROOT);
			xmlStreamWriter.writeAttribute(
				_AVAILABLE_LOCALES, availableLocales);
			xmlStreamWriter.writeAttribute(_DEFAULT_LOCALE, defaultLanguageId);

			_copyNonExempt(
				xmlStreamReader, xmlStreamWriter, requestedLanguageId,
				defaultLanguageId, cdata);

			if (cdata) {
				xmlStreamWriter.writeStartElement(key);
				xmlStreamWriter.writeAttribute(
					_LANGUAGE_ID, requestedLanguageId);
				xmlStreamWriter.writeCData(value);
				xmlStreamWriter.writeEndElement();
			}
			else {
				xmlStreamWriter.writeStartElement(key);
				xmlStreamWriter.writeAttribute(
					_LANGUAGE_ID, requestedLanguageId);
				xmlStreamWriter.writeCharacters(value);
				xmlStreamWriter.writeEndElement();
			}

			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndDocument();

			xmlStreamWriter.close();
			xmlStreamWriter = null;

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

			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception e) {
				}
			}

			if (xmlStreamWriter != null) {
				try {
					xmlStreamWriter.close();
				}
				catch (Exception e) {
				}
			}
		}

		return xml;
	}

	private void _copyNonExempt(
			XMLStreamReader xmlStreamReader, XMLStreamWriter xmlStreamWriter,
			String exemptLanguageId, String defaultLanguageId, boolean cdata)
		throws XMLStreamException {

		while (xmlStreamReader.hasNext()) {
			int event = xmlStreamReader.next();

			if (event == XMLStreamConstants.START_ELEMENT) {
				String languageId = xmlStreamReader.getAttributeValue(
					null, _LANGUAGE_ID);

				if (Validator.isNull(languageId)) {
					languageId = defaultLanguageId;
				}

				if (!languageId.equals(exemptLanguageId)) {
					xmlStreamWriter.writeStartElement(
						xmlStreamReader.getLocalName());
					xmlStreamWriter.writeAttribute(_LANGUAGE_ID, languageId);

					while (xmlStreamReader.hasNext()) {
						event = xmlStreamReader.next();

						if (event == XMLStreamConstants.CHARACTERS ||
							event == XMLStreamConstants.CDATA) {

							String text = xmlStreamReader.getText();

							if (cdata) {
								xmlStreamWriter.writeCData(text);
							}
							else {
								xmlStreamWriter.writeCharacters(
									xmlStreamReader.getText());
							}

							break;
						}
						else if (event == XMLStreamConstants.END_ELEMENT) {
							break;
						}
					}

					xmlStreamWriter.writeEndElement();
				}
			}
			else if (event == XMLStreamConstants.END_DOCUMENT) {
				break;
			}
		}
	}

	private String _getCachedValue(
		String xml, String requestedLanguageId, boolean useDefault) {

		String value = null;

		Map<Tuple, String> valueMap = _cache.get(xml);

		if (valueMap != null) {
			Tuple subkey = new Tuple(useDefault, requestedLanguageId);

			value = valueMap.get(subkey);
		}

		return value;
	}

	private String _getPreferencesKey(String key, String languageId) {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		if (!languageId.equals(defaultLanguageId)) {
			key += StringPool.UNDERLINE + languageId;
		}

		return key;
	}

	private String _getRootAttribute(
		String xml, String name, String defaultValue) {

		String value = null;

		XMLStreamReader xmlStreamReader = null;

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if (contextClassLoader != portalClassLoader) {
				currentThread.setContextClassLoader(portalClassLoader);
			}

			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			if (xmlStreamReader.hasNext()) {
				xmlStreamReader.nextTag();

				value = xmlStreamReader.getAttributeValue(null, name);
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

			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
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

	private String _sanitizeXML(String xml) {
		if (Validator.isNull(xml) || (xml.indexOf("<root") == -1)) {
			xml = _EMPTY_ROOT_NODE;
		}

		return xml;
	}

	private void _setCachedValue(
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

	private static Log _log = LogFactoryUtil.getLog(LocalizationImpl.class);

	private Map<String, Map<Tuple, String>> _cache = new ReferenceMap(
		ReferenceMap.SOFT, ReferenceMap.HARD);

}