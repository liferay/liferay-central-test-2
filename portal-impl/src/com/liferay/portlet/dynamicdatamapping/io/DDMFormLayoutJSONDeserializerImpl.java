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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutColumn;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutPage;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayoutRow;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutJSONDeserializerImpl
	implements DDMFormLayoutJSONDeserializer {

	@Override
	public DDMFormLayout deserialize(String serializedDDMFormLayout)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			serializedDDMFormLayout);

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		setDDMFormLayoutDefaultLocale(
			jsonObject.getString("defaultLanguageId"), ddmFormLayout);
		setDDMFormLayoutPages(jsonObject.getJSONArray("pages"), ddmFormLayout);
		setDDMFormLayoutPageTitlesDefaultLocale(ddmFormLayout);

		return ddmFormLayout;
	}

	protected DDMFormLayoutColumn getDDMFormLayoutColumn(
		JSONObject jsonObject) {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn(
			jsonObject.getString("fieldName"), jsonObject.getInt("size"));

		return ddmFormLayoutColumn;
	}

	protected List<DDMFormLayoutColumn> getDDMFormLayoutColumns(
		JSONArray jsonArray) {

		List<DDMFormLayoutColumn> ddmFormLayoutColumns = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormLayoutColumn ddmFormLayoutColumn = getDDMFormLayoutColumn(
				jsonArray.getJSONObject(i));

			ddmFormLayoutColumns.add(ddmFormLayoutColumn);
		}

		return ddmFormLayoutColumns;
	}

	protected DDMFormLayoutPage getDDMFormLayoutPage(JSONObject jsonObject) {
		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		setDDMFormLayoutPageRows(
			jsonObject.getJSONArray("rows"), ddmFormLayoutPage);
		setDDMFormLayoutPageTitle(
			jsonObject.getJSONObject("title"), ddmFormLayoutPage);

		return ddmFormLayoutPage;
	}

	protected List<DDMFormLayoutPage> getDDMFormLayoutPages(
		JSONArray jsonArray) {

		List<DDMFormLayoutPage> ddmFormLayoutPages = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormLayoutPage ddmFormLayoutPage = getDDMFormLayoutPage(
				jsonArray.getJSONObject(i));

			ddmFormLayoutPages.add(ddmFormLayoutPage);
		}

		return ddmFormLayoutPages;
	}

	protected DDMFormLayoutRow getDDMFormLayoutRow(JSONObject jsonObject) {
		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		setDDMFormLayoutRowColumns(
			jsonObject.getJSONArray("columns"), ddmFormLayoutRow);

		return ddmFormLayoutRow;
	}

	protected List<DDMFormLayoutRow> getDDMFormLayoutRows(JSONArray jsonArray) {
		List<DDMFormLayoutRow> ddmFormLayoutRows = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormLayoutRow ddmFormLayoutRow = getDDMFormLayoutRow(
				jsonArray.getJSONObject(i));

			ddmFormLayoutRows.add(ddmFormLayoutRow);
		}

		return ddmFormLayoutRows;
	}

	protected LocalizedValue getTitle(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		LocalizedValue title = new LocalizedValue();

		Iterator<String> itr = jsonObject.keys();

		while (itr.hasNext()) {
			String languageId = itr.next();

			title.addString(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return title;
	}

	protected void setDDMFormLayoutDefaultLocale(
		String defaultLanguageId, DDMFormLayout ddmFormLayout) {

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		ddmFormLayout.setDefaultLocale(defaultLocale);
	}

	protected void setDDMFormLayoutPageRows(
		JSONArray jsonArray, DDMFormLayoutPage ddmFormLayoutPage) {

		List<DDMFormLayoutRow> ddmFormLayoutRows = getDDMFormLayoutRows(
			jsonArray);

		ddmFormLayoutPage.setDDMFormLayoutRows(ddmFormLayoutRows);
	}

	protected void setDDMFormLayoutPages(
		JSONArray jsonArray, DDMFormLayout ddmFormLayout) {

		List<DDMFormLayoutPage> ddmFormLayoutPages = getDDMFormLayoutPages(
			jsonArray);

		ddmFormLayout.setDDMFormLayoutPages(ddmFormLayoutPages);
	}

	protected void setDDMFormLayoutPageTitle(
		JSONObject jsonObject, DDMFormLayoutPage ddmFormLayoutPage) {

		LocalizedValue title = getTitle(jsonObject);

		if (title == null) {
			return;
		}

		ddmFormLayoutPage.setTitle(title);
	}

	protected void setDDMFormLayoutPageTitlesDefaultLocale(
		DDMFormLayout ddmFormLayout) {

		for (DDMFormLayoutPage ddmFormLayoutPage :
				ddmFormLayout.getDDMFormLayoutPages()) {

			LocalizedValue title = ddmFormLayoutPage.getTitle();

			title.setDefaultLocale(ddmFormLayout.getDefaultLocale());
		}
	}

	protected void setDDMFormLayoutRowColumns(
		JSONArray jsonArray, DDMFormLayoutRow ddmFormLayoutRow) {

		List<DDMFormLayoutColumn> ddmFormLayoutColumns =
			getDDMFormLayoutColumns(jsonArray);

		ddmFormLayoutRow.setDDMFormLayoutColumns(ddmFormLayoutColumns);
	}

}