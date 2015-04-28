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

package com.liferay.frontend.alloy.editor.config;

import com.liferay.portal.kernel.editor.config.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;

import java.util.Locale;
import java.util.Map;

import javax.portlet.WindowStateException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(property = {"editor.name=alloyeditor"})
public class AlloyEditorConfigContributor implements EditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		String contentsLanguageId = (String)inputEditorTaglibAttributes.get(
			"liferay-ui:input-editor:contentsLanguageId");

		Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

		String contentsLanguageDir = LanguageUtil.get(
			contentsLocale, "lang.dir");

		contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

		jsonObject.put(
			"contentsLangDirection", HtmlUtil.escapeJS(contentsLanguageDir));
		jsonObject.put(
			"contentsLanguage", contentsLanguageId.replace("iw_", "he_"));
		jsonObject.put(
			"extraPlugins",
			"autolink,dragresize,dropimages,placeholder,selectionregion," +
				"tableresize,tabletools,uicore");

		String languageId = LocaleUtil.toLanguageId(themeDisplay.getLocale());

		jsonObject.put("language", languageId.replace("iw_", "he_"));
		jsonObject.put(
			"removePlugins", "elementspath,link,liststyle,resize,toolbar");

		if (liferayPortletResponse != null) {
			LiferayPortletURL itemSelectorURL =
				liferayPortletResponse.createRenderURL(
					PortletKeys.ITEM_SELECTOR);

			itemSelectorURL.setParameter("mvcPath", "/view.jsp");
			itemSelectorURL.setParameter(
				"groupId", String.valueOf(themeDisplay.getScopeGroupId()));

			String name =
				liferayPortletResponse.getNamespace() +
					GetterUtil.getString(
						(String)inputEditorTaglibAttributes.get(
							"liferay-ui:input-editor:name"));

			itemSelectorURL.setParameter("eventName", name + "selectDocument");
			itemSelectorURL.setParameter(
				"showGroupsSelector", Boolean.TRUE.toString());

			Map<String, String> fileBrowserParamsMap =
				(Map<String, String>)inputEditorTaglibAttributes.get(
					"liferay-ui:input-editor:fileBrowserParams");

			if (fileBrowserParamsMap != null) {
				for (Map.Entry<String, String> entry :
						fileBrowserParamsMap.entrySet()) {

					itemSelectorURL.setParameter(
						entry.getKey(), entry.getValue());
				}
			}

			try {
				itemSelectorURL.setWindowState(LiferayWindowState.POP_UP);
			}
			catch (WindowStateException wse) {
			}

			jsonObject.put("filebrowserBrowseUrl", itemSelectorURL.toString());
			jsonObject.put(
				"filebrowserFlashBrowseUrl",
				itemSelectorURL.toString() + "&Type=flash");
			jsonObject.put(
				"filebrowserImageBrowseLinkUrl",
				itemSelectorURL.toString() + "&Type=image");
			jsonObject.put(
				"filebrowserImageBrowseUrl",
				itemSelectorURL.toString() + "&Type=image");

			jsonObject.put("srcNode", name);
		}

		jsonObject.put("toolbars", getToolbarsJSONObject());
	}

	@Override
	public void populateOptionsJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {
	}

	protected JSONObject getToolbarsJSONObject() {
		JSONObject toolbarsJSONObject = JSONFactoryUtil.createJSONObject();

		try {
			JSONObject toolbarsAddJSONObject =
				JSONFactoryUtil.createJSONObject();

			JSONArray toolbarsAddButtonsJSONArray =
				JSONFactoryUtil.createJSONArray(
					"['imageselector', 'table', 'hline']");

			toolbarsAddJSONObject.put("buttons", toolbarsAddButtonsJSONArray);
			toolbarsAddJSONObject.put("tabIndex", 2);

			toolbarsJSONObject.put("add", toolbarsAddJSONObject);

			JSONObject toolbarsStylesJSONObject =
				JSONFactoryUtil.createJSONObject();

			JSONArray toolbarsStylesSelectionJSONArray =
				JSONFactoryUtil.createJSONArray();

			JSONObject toolbarsStylesSelectionLinkJSONObject =
				JSONFactoryUtil.createJSONObject();

			toolbarsStylesSelectionLinkJSONObject.put(
				"buttons", JSONFactoryUtil.createJSONArray("['linkEdit']"));
			toolbarsStylesSelectionLinkJSONObject.put("name", "link");
			toolbarsStylesSelectionLinkJSONObject.put("test", "link");

			toolbarsStylesSelectionJSONArray.put(
				toolbarsStylesSelectionLinkJSONObject);

			JSONObject toolbarsStylesSelectionImageJSONObject =
				JSONFactoryUtil.createJSONObject();

			toolbarsStylesSelectionImageJSONObject.put(
				"buttons",
				JSONFactoryUtil.createJSONArray("['imageLeft', 'imageRight']"));
			toolbarsStylesSelectionImageJSONObject.put("name", "image");
			toolbarsStylesSelectionImageJSONObject.put("test", "image");

			toolbarsStylesSelectionJSONArray.put(
				toolbarsStylesSelectionImageJSONObject);

			JSONObject toolbarsStylesSelectionTextJSONObject =
				JSONFactoryUtil.createJSONObject();

			toolbarsStylesSelectionTextJSONObject.put(
				"buttons",
				JSONFactoryUtil.createJSONArray(
					"['styles', 'bold', 'italic', 'underline', 'link', " +
						"'twitter']"));
			toolbarsStylesSelectionTextJSONObject.put("name", "text");
			toolbarsStylesSelectionTextJSONObject.put("test", "text");

			toolbarsStylesSelectionJSONArray.put(
				toolbarsStylesSelectionTextJSONObject);

			JSONObject toolbarsStylesSelectionTableJSONObject =
				JSONFactoryUtil.createJSONObject();

			toolbarsStylesSelectionTableJSONObject.put(
				"buttons",
				JSONFactoryUtil.createJSONArray(
					"['tableRow', 'tableColumn', 'tableCell', 'tableRemove']"));
			toolbarsStylesSelectionTableJSONObject.put(
				"getArrowBoxClasses", "table");
			toolbarsStylesSelectionTableJSONObject.put("name", "table");
			toolbarsStylesSelectionTableJSONObject.put("setPosition", "table");
			toolbarsStylesSelectionTableJSONObject.put("test", "table");

			toolbarsStylesSelectionJSONArray.put(
				toolbarsStylesSelectionTableJSONObject);

			toolbarsStylesJSONObject.put(
				"selections", toolbarsStylesSelectionJSONArray);
			toolbarsStylesJSONObject.put("tabIndex", 1);

			toolbarsJSONObject.put("styles", toolbarsStylesJSONObject);
		}
		catch (JSONException jsone) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to create a JSON array from string");
			}
		}

		return toolbarsJSONObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AlloyEditorConfigContributor.class);

}