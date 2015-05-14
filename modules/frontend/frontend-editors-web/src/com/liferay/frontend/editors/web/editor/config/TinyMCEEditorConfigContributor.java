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

package com.liferay.frontend.editors.web.editor.config;

import com.liferay.portal.kernel.editor.config.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrin Chaudhary
 */
@Component(
	property = {"editor.name=tinymce"}, service = EditorConfigContributor.class
)
public class TinyMCEEditorConfigContributor
	extends BaseTinyMCEEditorConfigConfigurator {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			liferayPortletResponse);

		jsonObject.put("mode", "exact");
		jsonObject.put(
			"plugins", getPluginsJSONArray(inputEditorTaglibAttributes));
		jsonObject.put("style_formats", getStyleFormatsJSONArray());
		jsonObject.put(
			"toolbar",
			getToolbarJSONArray(inputEditorTaglibAttributes, themeDisplay));
	}

	protected JSONArray getPluginsJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			"advlist autolink autosave link image lists charmap print " +
				"preview hr anchor");
		jsonArray.put("searchreplace wordcount fullscreen media");

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put("code");
		}

		jsonArray.put(
			"table contextmenu emoticons textcolor paste fullpage textcolor " +
				"colorpicker textpattern");

		return jsonArray;
	}

	protected JSONArray getStyleFormatsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String[] styleFormats = {
			"{inline: 'p', title: 'Normal'}",
			"{block: 'h1', title: 'Heading 1'}",
			"{block: 'h2', title: 'Heading 2'}",
			"{block: 'h3', title: 'Heading 3'}",
			"{block: 'h4', title: 'Heading 4'}",
			"{block: 'pre', title: 'Preformatted Text'}",
			"{inline: 'cite', title: 'Cited Work'}",
			"{inline: 'code', title: 'Computer Code'}",
			"{block: 'div', classes: 'portlet-msg-info', title: 'Info " +
				"Message'}",
			"{block: 'div', classes: 'portlet-msg-alert', title: 'Alert " +
				"Message'}",
			"{block: 'div', classes: 'portlet-msg-error', title: 'Error " +
				"Message'}"
		};

		for (String styleFormat : styleFormats) {
			jsonArray.put(toJSONObject(styleFormat));
		}

		return jsonArray;
	}

	protected JSONArray getToolbarJSONArray(
		Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay) {

		JSONObject toolbarsJSONObject = getToolbarsJSONObject(
			inputEditorTaglibAttributes);

		String toolbarSet = (String)inputEditorTaglibAttributes.get(
			"liferay-ui:input-editor:toolbarSet");

		String currentToolbarSet = TextFormatter.format(
			HtmlUtil.escapeJS(toolbarSet), TextFormatter.M);

		if (BrowserSnifferUtil.isMobile(themeDisplay.getRequest())) {
			currentToolbarSet = "phone";
		}

		JSONArray toolbarJSONArray = toolbarsJSONObject.getJSONArray(
			currentToolbarSet);

		if (toolbarJSONArray == null) {
			toolbarJSONArray = toolbarsJSONObject.getJSONArray("liferay");
		}

		return toolbarJSONArray;
	}

	protected JSONArray getToolbarsEmailJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			"fontselect fontsizeselect | forecolor backcolor | bold italic " +
				"underline strikethrough | alignleft aligncenter alignright " +
					"alignjustify");

		String buttons =
			"cut copy paste bullist numlist | blockquote | undo redo | link " +
				"unlink image ";

		if (isShowSource(inputEditorTaglibAttributes)) {
			buttons += "code ";
		}

		buttons += "| hr removeformat | preview print fullscreen";

		jsonArray.put(buttons);

		return jsonArray;
	}

	protected JSONObject getToolbarsJSONObject(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"email", getToolbarsEmailJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"liferay",
			getToolbarsLiferayJSONArray(inputEditorTaglibAttributes));
		jsonObject.put("phone", getToolbarsPhoneJSONArray());
		jsonObject.put(
			"simple", getToolbarsSimpleJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"tablet", getToolbarsTabletJSONArray(inputEditorTaglibAttributes));

		return jsonObject;
	}

	protected JSONArray getToolbarsLiferayJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			"styleselect fontselect fontsizeselect | forecolor backcolor | " +
				"bold italic underline strikethrough | alignleft aligncenter " +
					"alignright alignjustify");

		String buttons =
			"cut copy paste searchreplace bullist numlist | outdent indent " +
				"blockquote | undo redo | link unlink anchor image media ";

		if (isShowSource(inputEditorTaglibAttributes)) {
			buttons += "code";
		}

		jsonArray.put(buttons);

		jsonArray.put(
			"table | hr removeformat | subscript superscript | charmap " +
				"emoticons | preview print fullscreen");

		return jsonArray;
	}

	protected JSONArray getToolbarsPhoneJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put("bold italic underline | bullist numlist");
		jsonArray.put("link unlink image");

		return jsonArray;
	}

	protected JSONArray getToolbarsSimpleJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String buttons =
			"bold italic underline strikethrough | bullist numlist | table | " +
				"link unlink image";

		if (isShowSource(inputEditorTaglibAttributes)) {
			buttons += " code";
		}

		jsonArray.put(buttons);

		return jsonArray;
	}

	protected JSONArray getToolbarsTabletJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(
			"styleselect fontselect fontsizeselect | bold italic underline " +
				"strikethrough | alignleft aligncenter alignright " +
					"alignjustify");

		String buttons = "bullist numlist | link unlink image";

		if (isShowSource(inputEditorTaglibAttributes)) {
			buttons += " code";
		}

		jsonArray.put(buttons);

		return jsonArray;
	}

}