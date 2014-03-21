/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.LocalizationImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockPageContext;

/**
 * @author Pablo Carvalho
 */
@PrepareForTest({LocalizationUtil.class, SAXReaderUtil.class})
@RunWith(PowerMockRunner.class)
public class DDMXSDImplTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpMocks();

		_document = createSampleDocument();

		_fieldsContextKey = _PORTLET_NAMESPACE.concat(
			_NAMESPACE).concat("fieldsContext");
	}

	@Test
	public void testGetFieldsContext() throws Exception {
		_ddmXSD.getFieldsContext(
			_mockPageContext, _PORTLET_NAMESPACE, _NAMESPACE);

		Assert.assertNotNull(_mockPageContext.getAttribute(_fieldsContextKey));
	}

	@Test
	public void testGetLocalizableFieldContext() throws Exception {
		Element rootElement = _document.getRootElement();

		Element fieldElement = addTextElement(
			rootElement, "Localizable", "Localizable", true);

		Map<String, Object> fieldContext = _ddmXSD.getFieldContext(
			_mockPageContext, _PORTLET_NAMESPACE, _NAMESPACE, fieldElement,
			LocaleUtil.US);

		Assert.assertFalse(fieldContext.containsKey("disabled"));
	}

	@Test
	public void testGetTranslatingLocalizableFieldContext() throws Exception {
		Element rootElement = _document.getRootElement();

		Element fieldElement = addTextElement(
			rootElement, "Localizable", "Localizable", true);

		Map<String, Object> fieldContext = _ddmXSD.getFieldContext(
			_mockPageContext, _PORTLET_NAMESPACE, _NAMESPACE, fieldElement,
			LocaleUtil.BRAZIL);

		Assert.assertFalse(fieldContext.containsKey("disabled"));
	}

	@Test
	public void testGetTranslatingUnlocalizableFieldContext() throws Exception {
		Element rootElement = _document.getRootElement();

		Element fieldElement = addTextElement(
			rootElement, "Unlocalizable", "Unlocalizable", false);

		Map<String, Object> fieldContext = _ddmXSD.getFieldContext(
			_mockPageContext, _PORTLET_NAMESPACE, _NAMESPACE, fieldElement,
			LocaleUtil.BRAZIL);

		Assert.assertEquals(
			Boolean.TRUE.toString(), fieldContext.get("disabled"));
	}

	@Test
	public void testGetUnlocalizableFieldContext() throws Exception {
		Element rootElement = _document.getRootElement();

		Element fieldElement = addTextElement(
			rootElement, "Unlocalizable", "Unlocalizable", false);

		Map<String, Object> fieldContext = _ddmXSD.getFieldContext(
			_mockPageContext, _PORTLET_NAMESPACE, _NAMESPACE, fieldElement,
			LocaleUtil.US);

		Assert.assertFalse(fieldContext.containsKey("disabled"));
	}

	protected Element addTextElement(
		Element element, String name, String label, boolean localizable) {

		Element dynamicElement = element.addElement("dynamic-element");

		dynamicElement.addAttribute("dataType", "string");
		dynamicElement.addAttribute("localizable", String.valueOf(localizable));
		dynamicElement.addAttribute("name", name);
		dynamicElement.addAttribute("type", "text");

		Element metadataElement = dynamicElement.addElement("meta-data");

		metadataElement.addAttribute(
			"locale", LocaleUtil.toLanguageId(LocaleUtil.US));

		Element entryElement = metadataElement.addElement("entry");

		entryElement.addAttribute("name", "label");
		entryElement.setText(label);

		return dynamicElement;
	}

	protected Document createSampleDocument() {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", "en_US");
		rootElement.addAttribute("default-locale", "en_US");

		addTextElement(rootElement, "Unlocalizable", "Text 2", false);

		return document;
	}

	protected void setUpMocks() {
		spy(LocalizationUtil.class);

		when(
			LocalizationUtil.getLocalization()
		).thenReturn(
			_localization
		);

		spy(SAXReaderUtil.class);

		when(
			SAXReaderUtil.getSAXReader()
		).thenReturn(
			_saxReader
		);
	}

	private static final String _NAMESPACE = "_namespace_";

	private static final String _PORTLET_NAMESPACE = "_portletNamespace_";

	private DDMXSDImpl _ddmXSD = new DDMXSDImpl();
	private Document _document;
	private String _fieldsContextKey;
	private Localization _localization = new LocalizationImpl();
	private MockPageContext _mockPageContext = new MockPageContext();
	private SAXReader _saxReader = new SAXReaderImpl();

}