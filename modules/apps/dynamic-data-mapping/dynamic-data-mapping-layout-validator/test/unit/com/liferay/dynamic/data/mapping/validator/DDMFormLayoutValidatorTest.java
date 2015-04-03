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

package com.liferay.dynamic.data.mapping.validator;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.StructureLayoutException;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutColumn;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutPage;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutRow;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.validator.DDMFormLayoutValidator;

import java.util.Locale;

import org.junit.Test;

/**
 * @author Pablo Carvalho
 */
public class DDMFormLayoutValidatorTest {

	@Test(expected = StructureLayoutException.class)
	public void testInvalidRowSize() throws StructureLayoutException {
		DDMFormLayoutColumn ddmFormLayoutColumn1 = createDDMFormLayoutColumn(
			"field1", 6);

		DDMFormLayoutColumn ddmFormLayoutColumn2 = createDDMFormLayoutColumn(
			"field2", 7);

		DDMFormLayoutRow ddmFormLayoutRow = createDDMFormLayoutRow(
			ddmFormLayoutColumn1);

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn2);

		LocalizedValue pageTitle = createPageTitle(LocaleUtil.US, "Page1");

		DDMFormLayoutPage ddmFormLayoutPage = createDDMFormLayoutPage(
			ddmFormLayoutRow, pageTitle);

		DDMFormLayout ddmFormLayout = createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.US);

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	@Test(expected = StructureLayoutException.class)
	public void testRepeatedFieldName() throws StructureLayoutException {
		DDMFormLayoutColumn ddmFormLayoutColumn1 = createDDMFormLayoutColumn(
			"field", 6);

		DDMFormLayoutColumn ddmFormLayoutColumn2 = createDDMFormLayoutColumn(
			"field", 6);

		DDMFormLayoutRow ddmFormLayoutRow = createDDMFormLayoutRow(
			ddmFormLayoutColumn1);

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn2);

		LocalizedValue pageTitle = createPageTitle(LocaleUtil.US, "Page1");

		DDMFormLayoutPage ddmFormLayoutPage = createDDMFormLayoutPage(
			ddmFormLayoutRow, pageTitle);

		DDMFormLayout ddmFormLayout = createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.US);

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	@Test(expected = StructureLayoutException.class)
	public void testUnmatchedDefaultLocales() throws StructureLayoutException {
		DDMFormLayoutColumn ddmFormLayoutColumn = createDDMFormLayoutColumn(
			"field", 12);

		DDMFormLayoutRow ddmFormLayoutRow = createDDMFormLayoutRow(
			ddmFormLayoutColumn);

		LocalizedValue pageTitle = createPageTitle(LocaleUtil.US, "Page1");

		DDMFormLayoutPage ddmFormLayoutPage = createDDMFormLayoutPage(
			ddmFormLayoutRow, pageTitle);

		DDMFormLayout ddmFormLayout = createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.BRAZIL);

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	@Test
	public void testValidDDMFormLayout() throws StructureLayoutException {
		DDMFormLayoutColumn ddmFormLayoutColumn = createDDMFormLayoutColumn(
			"field", 12);

		DDMFormLayoutRow ddmFormLayoutRow = createDDMFormLayoutRow(
			ddmFormLayoutColumn);

		LocalizedValue pageTitle = createPageTitle(LocaleUtil.US, "Page1");

		DDMFormLayoutPage ddmFormLayoutPage = createDDMFormLayoutPage(
			ddmFormLayoutRow, pageTitle);

		DDMFormLayout ddmFormLayout = createDDMFormLayout(
			ddmFormLayoutPage, LocaleUtil.US);

		_ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	private DDMFormLayout createDDMFormLayout(
		DDMFormLayoutPage ddmFormLayoutPage, Locale defaultLocale) {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);

		ddmFormLayout.setDefaultLocale(defaultLocale);

		return ddmFormLayout;
	}

	private DDMFormLayoutColumn createDDMFormLayoutColumn(
		String fieldName, int size) {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn(
			fieldName, size);

		return ddmFormLayoutColumn;
	}

	private DDMFormLayoutPage createDDMFormLayoutPage(
		DDMFormLayoutRow ddmFormLayoutRow, LocalizedValue title) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);

		ddmFormLayoutPage.setTitle(title);

		return ddmFormLayoutPage;
	}

	private DDMFormLayoutRow createDDMFormLayoutRow(
		DDMFormLayoutColumn ddmFormLayoutColumn) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn);

		return ddmFormLayoutRow;
	}

	private LocalizedValue createPageTitle(Locale defaultLocale, String title) {
		LocalizedValue localizedValue = new LocalizedValue(defaultLocale);

		localizedValue.addString(defaultLocale, title);

		return localizedValue;
	}

	private final DDMFormLayoutValidator _ddmFormLayoutValidator =
		new DDMFormLayoutValidatorImpl();

}