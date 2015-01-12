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

package com.liferay.portlet.dynamicdatamapping.io;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Pablo Carvalho
 */
public class DDMFormXSDDeserializerImpl implements DDMFormXSDDeserializer {

	@Override
	public DDMForm deserialize(String serializedDDMForm)
		throws PortalException {

		try {
			Document document = SAXReaderUtil.read(serializedDDMForm);

			DDMForm ddmForm = new DDMForm();

			setDDMFormAvailableLocales(document.getRootElement(), ddmForm);
			setDDMFormDefaultLocale(document.getRootElement(), ddmForm);
			setDDMFormFields(document.getRootElement(), ddmForm);
			setDDMFormLocalizedValuesDefaultLocale(ddmForm);

			return ddmForm;
		}
		catch (DocumentException de) {
			throw new PortalException(de);
		}
	}

	protected void addOptionValueLabels(
		Element dynamicElementElement, DDMFormFieldOptions ddmFormFieldOptions,
		String optionValue) {

		List<Element> metadataElements = dynamicElementElement.elements(
			"meta-data");

		for (Element metadataElement : metadataElements) {
			String languageId = metadataElement.attributeValue("locale");

			Locale locale = LocaleUtil.fromLanguageId(languageId);

			Element labelElement = fetchMetadataEntry(metadataElement, "label");

			ddmFormFieldOptions.addOptionLabel(
				optionValue, locale, labelElement.getText());
		}
	}

	protected Element fetchMetadataEntry(
		Element parentElement, String entryName) {

		XPath xPathSelector = SAXReaderUtil.createXPath(
			"entry[@name=" + HtmlUtil.escapeXPathAttribute(entryName) +
				StringPool.CLOSE_BRACKET);

		return (Element)xPathSelector.selectSingleNode(parentElement);
	}

	protected Set<Locale> getAvailableLocales(Element rootElement) {
		Set<Locale> availableLocales = new HashSet<>();

		String availableLanguageIds = rootElement.attributeValue(
			"available-locales");

		for (String availableLanguageId :
				StringUtil.split(availableLanguageIds)) {

			Locale availableLocale = LocaleUtil.fromLanguageId(
				availableLanguageId);

			availableLocales.add(availableLocale);
		}

		return availableLocales;
	}

	protected DDMFormField getDDMFormField(Element dynamicElementElement) {
		String name = dynamicElementElement.attributeValue("name");
		String type = dynamicElementElement.attributeValue("type");

		DDMFormField ddmFormField = new DDMFormField(name, type);

		setDDMFormFieldDataType(dynamicElementElement, ddmFormField);
		setDDMFormFieldIndexType(dynamicElementElement, ddmFormField);
		setDDMFormFieldLocalizable(dynamicElementElement, ddmFormField);
		setDDMFormFieldMultiple(dynamicElementElement, ddmFormField);
		setDDMFormFieldNamespace(dynamicElementElement, ddmFormField);
		setDDMFormFieldReadOnly(dynamicElementElement, ddmFormField);
		setDDMFormFieldRepeatable(dynamicElementElement, ddmFormField);
		setDDMFormFieldRequired(dynamicElementElement, ddmFormField);
		setDDMFormFieldShowLabel(dynamicElementElement, ddmFormField);

		List<Element> metadataElements = dynamicElementElement.elements(
			"meta-data");

		for (Element metadataElement : metadataElements) {
			setDDMFormFieldMetadata(metadataElement, ddmFormField);
		}

		if (type.equals("radio") || type.equals("select")) {
			setDDMFormFieldOptions(dynamicElementElement, ddmFormField);
		}
		else {
			setNestedDDMFormField(dynamicElementElement, ddmFormField);
		}

		return ddmFormField;
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		List<Element> dynamicElementElements) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (Element dynamicElementElement : dynamicElementElements) {
			String value = dynamicElementElement.attributeValue("value");

			ddmFormFieldOptions.addOption(value);

			addOptionValueLabels(
				dynamicElementElement, ddmFormFieldOptions, value);
		}

		return ddmFormFieldOptions;
	}

	protected List<DDMFormField> getDDMFormFields(Element rootElement) {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (Element dynamicElement : rootElement.elements("dynamic-element")) {
			DDMFormField ddmFormField = getDDMFormField(dynamicElement);

			ddmFormFields.add(ddmFormField);
		}

		return ddmFormFields;
	}

	protected Locale getDefaultLocale(Element rootElement) {
		String defaultLanguageId = rootElement.attributeValue("default-locale");

		return LocaleUtil.fromLanguageId(defaultLanguageId);
	}

	protected void setDDMFormAvailableLocales(
		Element rootElement, DDMForm ddmForm) {

		Set<Locale> availableLocales = getAvailableLocales(rootElement);

		ddmForm.setAvailableLocales(availableLocales);
	}

	protected void setDDMFormDefaultLocale(
		Element rootElement, DDMForm ddmForm) {

		Locale defaultLocale = getDefaultLocale(rootElement);

		ddmForm.setDefaultLocale(defaultLocale);
	}

	protected void setDDMFormFieldDataType(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		String dataType = dynamicElementElement.attributeValue("dataType");

		ddmFormField.setDataType(dataType);
	}

	protected void setDDMFormFieldIndexType(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		String indexType = dynamicElementElement.attributeValue("indexType");

		ddmFormField.setIndexType(indexType);
	}

	protected void setDDMFormFieldLocalizable(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		boolean localizable = GetterUtil.getBoolean(
			dynamicElementElement.attributeValue("localizable"), true);

		ddmFormField.setLocalizable(localizable);
	}

	protected void setDDMFormFieldLocalizedValuesDefaultLocale(
		DDMFormField ddmFormField, Locale defaultLocale) {

		LocalizedValue label = ddmFormField.getLabel();

		label.setDefaultLocale(defaultLocale);

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		predefinedValue.setDefaultLocale(defaultLocale);

		LocalizedValue style = ddmFormField.getStyle();

		style.setDefaultLocale(defaultLocale);

		LocalizedValue tip = ddmFormField.getTip();

		tip.setDefaultLocale(defaultLocale);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormFieldOptions.setDefaultLocale(defaultLocale);

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			setDDMFormFieldLocalizedValuesDefaultLocale(
				nestedDDMFormField, defaultLocale);
		}
	}

	protected void setDDMFormFieldMetadata(
		Element metadataElement, DDMFormField ddmFormField) {

		String languageId = metadataElement.attributeValue("locale");

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		Element labelElement = fetchMetadataEntry(metadataElement, "label");

		if (labelElement != null) {
			LocalizedValue label = ddmFormField.getLabel();

			label.addString(locale, labelElement.getText());
		}

		Element predefinedValueElement = fetchMetadataEntry(
			metadataElement, "predefinedValue");

		if (predefinedValueElement != null) {
			LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

			predefinedValue.addString(locale, predefinedValueElement.getText());
		}

		Element styleElement = fetchMetadataEntry(metadataElement, "style");

		if (styleElement != null) {
			LocalizedValue style = ddmFormField.getStyle();

			style.addString(locale, styleElement.getText());
		}

		Element tipElement = fetchMetadataEntry(metadataElement, "tip");

		if (tipElement != null) {
			LocalizedValue tip = ddmFormField.getTip();

			tip.addString(locale, tipElement.getText());
		}
	}

	protected void setDDMFormFieldMultiple(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		boolean multiple = GetterUtil.getBoolean(
			dynamicElementElement.attributeValue("multiple"));

		ddmFormField.setMultiple(multiple);
	}

	protected void setDDMFormFieldNamespace(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		String fieldNamespace = dynamicElementElement.attributeValue(
			"fieldNamespace");

		ddmFormField.setNamespace(fieldNamespace);
	}

	protected void setDDMFormFieldOptions(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		DDMFormFieldOptions ddmFormFieldOptions = getDDMFormFieldOptions(
			dynamicElementElement.elements("dynamic-element"));

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
	}

	protected void setDDMFormFieldReadOnly(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		boolean readOnly = GetterUtil.getBoolean(
			dynamicElementElement.attributeValue("readOnly"));

		ddmFormField.setReadOnly(readOnly);
	}

	protected void setDDMFormFieldRepeatable(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		boolean repeatable = GetterUtil.getBoolean(
			dynamicElementElement.attributeValue("repeatable"));

		ddmFormField.setRepeatable(repeatable);
	}

	protected void setDDMFormFieldRequired(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		boolean required = GetterUtil.getBoolean(
			dynamicElementElement.attributeValue("required"));

		ddmFormField.setRequired(required);
	}

	protected void setDDMFormFields(Element rootElement, DDMForm ddmForm) {
		List<DDMFormField> ddmFormFields = getDDMFormFields(rootElement);

		ddmForm.setDDMFormFields(ddmFormFields);
	}

	protected void setDDMFormFieldShowLabel(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		boolean showLabel = GetterUtil.getBoolean(
			dynamicElementElement.attributeValue("showLabel"), true);

		ddmFormField.setShowLabel(showLabel);
	}

	protected void setDDMFormLocalizedValuesDefaultLocale(DDMForm ddmForm) {
		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			setDDMFormFieldLocalizedValuesDefaultLocale(
				ddmFormField, ddmForm.getDefaultLocale());
		}
	}

	protected void setNestedDDMFormField(
		Element dynamicElementElement, DDMFormField ddmFormField) {

		List<DDMFormField> nestedDDMFormFields = getDDMFormFields(
			dynamicElementElement);

		ddmFormField.setNestedDDMFormFields(nestedDDMFormFields);
	}

}