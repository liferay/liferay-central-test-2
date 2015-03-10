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

package com.liferay.portlet.blogs.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class BlogsEntryEditorDisplayContext {

	public BlogsEntryEditorDisplayContext(
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		_liferayPortletResponse = liferayPortletResponse;

		populateContentEditorData();

		populateCoverImageCaptionEditorData();

		populateTextEditorData();
	}

	public Map<String, Object> getContentEditorData() {
		return _contentEditorData;
	}

	public Map<String, Object> getCoverImageCaptionEditorData() {
		return _coverImageCaptionEditorData;
	}

	public Map<String, Object> getTextEditorData() {
		return _textEditorData;
	}

	protected void populateContentEditorData() {
		PortletURL uploadEditorImageURL =
			_liferayPortletResponse.createActionURL();

		uploadEditorImageURL.setParameter(
			"struts_action", "/blogs/upload_editor_image");

		_contentEditorData.put("uploadURL", uploadEditorImageURL);
	}

	protected void populateCoverImageCaptionEditorData() throws JSONException {
		JSONObject editorConfigJSONObject = JSONFactoryUtil.createJSONObject();

		editorConfigJSONObject.put("allowedContent", "a");
		editorConfigJSONObject.put("disallowedContent", "br");

		JSONObject toolbarJSONObject = JSONFactoryUtil.createJSONObject();

		JSONArray stylesJSONObject = JSONFactoryUtil.createJSONArray("['a']");

		toolbarJSONObject.put("styles", stylesJSONObject);

		editorConfigJSONObject.put("toolbars", toolbarJSONObject);

		_coverImageCaptionEditorData.put(
			"editorConfig", editorConfigJSONObject);

		JSONObject editorOptionsJSONObject = JSONFactoryUtil.createJSONObject();

		editorOptionsJSONObject.put("textMode", Boolean.TRUE);

		_coverImageCaptionEditorData.put(
			"editorOptions", editorOptionsJSONObject);
	}

	protected Map<String, Object> populateTextEditorData() {
		JSONObject editorConfigJSONObject = JSONFactoryUtil.createJSONObject();

		editorConfigJSONObject.put("allowedContent", "p");
		editorConfigJSONObject.put("disallowedContent", "br");
		editorConfigJSONObject.put(
			"toolbars", JSONFactoryUtil.createJSONObject());

		_textEditorData.put("editorConfig", editorConfigJSONObject);

		JSONObject editorOptionsJSONObject = JSONFactoryUtil.createJSONObject();

		editorOptionsJSONObject.put("textMode", Boolean.TRUE);

		_textEditorData.put("editorOptions", editorOptionsJSONObject);

		return _textEditorData;
	}

	private final Map<String, Object> _contentEditorData = new HashMap<>();
	private final Map<String, Object> _coverImageCaptionEditorData =
		new HashMap<>();
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Map<String, Object> _textEditorData = new HashMap<>();

}