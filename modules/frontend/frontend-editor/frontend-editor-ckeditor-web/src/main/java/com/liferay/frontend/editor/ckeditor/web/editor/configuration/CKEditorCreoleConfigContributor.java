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

package com.liferay.frontend.editor.ckeditor.web.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.RequestBackedPortletURLFactory;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ambrin Chaudhary
 */
@Component(
	property = {"editor.name=ckeditor_creole"},
	service = EditorConfigContributor.class
)
public class CKEditorCreoleConfigContributor
	extends BaseCKEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		jsonObject.put(
			"allowedContent",
			"b strong i hr h1 h2 h3 h4 h5 h6 em ul ol li pre table tr th; " +
				"img a[*]");

		Map<String, String> fileBrowserParams =
			(Map<String, String>)inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:fileBrowserParams");

		String attachmentURLPrefix = fileBrowserParams.get(
			"attachmentURLPrefix");

		jsonObject.put("attachmentURLPrefix", attachmentURLPrefix);

		jsonObject.put("decodeLinks", Boolean.TRUE);
		jsonObject.put("disableObjectResizing", Boolean.TRUE);
		jsonObject.put(
			"extraPlugins",
			"a11yhelpbtn,creole,imageselector,lfrpopup,wikilink");
		jsonObject.put(
			"filebrowserWindowFeatures",
			"title=" + LanguageUtil.get(themeDisplay.getLocale(), "browse"));
		jsonObject.put("format_tags", "p;h1;h2;h3;h4;h5;h6;pre");

		StringBundler sb = new StringBundler();

		sb.append("bidi,colorbutton,colordialog,div,elementspath,flash,font,");
		sb.append("forms,indentblock,justify,keystrokes,link,maximize,");
		sb.append("newpage,pagebreak,preview,print,save,showblocks,smiley,");
		sb.append("stylescombo,templates,video");

		jsonObject.put("removePlugins", sb.toString());

		jsonObject.put(
			"toolbar_creole",
			getToolbarsCreoleJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"toolbar_phone",
			getToolbarsPhoneJSONArray(inputEditorTaglibAttributes));
		jsonObject.put(
			"toolbar_tablet",
			getToolbarsTabletJSONArray(inputEditorTaglibAttributes));
	}

	protected JSONArray getToolbarsCreoleJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(toJSONArray("['Bold', 'Italic', '-' ,'RemoveFormat']"));
		jsonArray.put(
			toJSONArray(
				"['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent']"));
		jsonArray.put(toJSONArray("['Format']"));
		jsonArray.put(toJSONArray("['Link', 'Unlink']"));

		String buttons = "['Table', '-',";

		if (isIncludeButtonImage(inputEditorTaglibAttributes)) {
			buttons = buttons.concat("'ImageSelector', '-', ");
		}

		buttons = buttons.concat("'HorizontalRule', '-', 'SpecialChar']");

		jsonArray.put(toJSONArray(buttons));

		jsonArray.put("/");
		jsonArray.put(
			toJSONArray(
				"['Cut', 'Copy', 'Paste', '-', 'PasteText', 'PasteFromWord', " +
					"'-', 'SelectAll', '-', 'Undo', 'Redo']"));
		jsonArray.put(toJSONArray("['Find','Replace']"));

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		jsonArray.put(toJSONArray("['A11YBtn']"));

		return jsonArray;
	}

	protected JSONArray getToolbarsPhoneJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(toJSONArray("['Bold', 'Italic']"));
		jsonArray.put(toJSONArray("['NumberedList', 'BulletedList']"));
		jsonArray.put(toJSONArray("['Link', 'Unlink']"));

		if (isIncludeButtonImage(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['ImageSelector']"));
		}

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		return jsonArray;
	}

	protected JSONArray getToolbarsTabletJSONArray(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(toJSONArray("['Bold', 'Italic']"));
		jsonArray.put(
			toJSONArray(
				"['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent']"));
		jsonArray.put(toJSONArray("['Format']"));
		jsonArray.put(toJSONArray("['Link', 'Unlink']"));

		if (isIncludeButtonImage(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['ImageSelector']"));
		}

		if (isShowSource(inputEditorTaglibAttributes)) {
			jsonArray.put(toJSONArray("['Source']"));
		}

		return jsonArray;
	}

	protected boolean isIncludeButtonImage(
		Map<String, Object> inputEditorTaglibAttributes) {

		Map<String, String> fileBrowserParams =
			(Map<String, String>)inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:fileBrowserParams");

		if (GetterUtil.getLong(
				fileBrowserParams.get("wikiPageResourcePrimKey")) > 0) {

			return true;
		}

		return false;
	}

}