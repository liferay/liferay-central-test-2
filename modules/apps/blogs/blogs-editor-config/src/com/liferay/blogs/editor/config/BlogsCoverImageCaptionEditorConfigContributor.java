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

package com.liferay.blogs.editor.config;

import com.liferay.portal.kernel.editor.config.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"editor.config.key=coverImageCaptionEditor", "javax.portlet.name=33",
		"javax.portlet.name=161"
	}
)
public class BlogsCoverImageCaptionEditorConfigContributor
	implements EditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		jsonObject.put("allowedContent", "a");
		jsonObject.put("disallowedContent", "br");
		jsonObject.put("extraPlugins", "placeholder,selectionregion,uicore");
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

		JSONObject toolbarStylesJSONObject = JSONFactoryUtil.createJSONObject();

		toolbarStylesJSONObject.put(
			"selections", getToolbarStylesSelectionsJSONArray());
		toolbarStylesJSONObject.put("tabIndex", 1);
		toolbarsJSONObject.put("styles", toolbarStylesJSONObject);

		return toolbarsJSONObject;
	}

	protected JSONArray getToolbarStylesSelectionsJSONArray() {
		JSONArray toolbarStylesSelectionsJSONArray =
			JSONFactoryUtil.createJSONArray();

		toolbarStylesSelectionsJSONArray.put(
			getToolbarStylesSelectionsLinkJSONObject());
		toolbarStylesSelectionsJSONArray.put(
			getToolbarStylesSelectionsTextJSONObject());

		return toolbarStylesSelectionsJSONArray;
	}

	protected JSONObject getToolbarStylesSelectionsLinkJSONObject() {
		JSONObject toolbarStylesSelectionsLinkJSONObject =
			JSONFactoryUtil.createJSONObject();

		try {
			toolbarStylesSelectionsLinkJSONObject.put(
				"buttons", JSONFactoryUtil.createJSONArray("['linkEdit']"));
		}
		catch (JSONException jsone) {
		}

		toolbarStylesSelectionsLinkJSONObject.put("name", "link");
		toolbarStylesSelectionsLinkJSONObject.put("test", "link");

		return toolbarStylesSelectionsLinkJSONObject;
	}

	protected JSONObject getToolbarStylesSelectionsTextJSONObject() {
		JSONObject toolbarStylesSelectionsTextJSONObject =
			JSONFactoryUtil.createJSONObject();

		try {
			toolbarStylesSelectionsTextJSONObject.put(
				"buttons", JSONFactoryUtil.createJSONArray("['link']"));
		}
		catch (JSONException jsone) {
		}

		toolbarStylesSelectionsTextJSONObject.put("name", "text");
		toolbarStylesSelectionsTextJSONObject.put("test", "text");

		return toolbarStylesSelectionsTextJSONObject;
	}

}