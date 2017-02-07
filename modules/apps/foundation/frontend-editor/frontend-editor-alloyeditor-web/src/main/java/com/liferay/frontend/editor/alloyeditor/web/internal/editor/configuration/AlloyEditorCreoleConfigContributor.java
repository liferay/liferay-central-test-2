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

package com.liferay.frontend.editor.alloyeditor.web.internal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = {"editor.name=alloyeditor_creole"},
	service = EditorConfigContributor.class
)
public class AlloyEditorCreoleConfigContributor
	extends BaseAlloyEditorConfigContributor {

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

		if (fileBrowserParams != null) {
			String attachmentURLPrefix = fileBrowserParams.get(
				"attachmentURLPrefix");

			if (Validator.isNotNull(attachmentURLPrefix)) {
				jsonObject.put("attachmentURLPrefix", attachmentURLPrefix);
			}
		}

		JSONObject buttonCfgJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject linkEditJSONObject = JSONFactoryUtil.createJSONObject();

		linkEditJSONObject.put("showTargetSelector", false);

		buttonCfgJSONObject.put("linkEdit", linkEditJSONObject);

		jsonObject.put("buttonCfg", buttonCfgJSONObject);

		jsonObject.put("decodeLinks", Boolean.TRUE);
		jsonObject.put("disableObjectResizing", Boolean.TRUE);

		String extraPlugins = jsonObject.getString("extraPlugins");

		extraPlugins = extraPlugins.replace(
			",ae_tableresize", StringPool.BLANK);
		extraPlugins = extraPlugins.concat(",creole,itemselector,media");

		jsonObject.put("extraPlugins", extraPlugins);

		jsonObject.put("format_tags", "p;h1;h2;h3;h4;h5;h6;pre");

		String removePlugins = jsonObject.getString("removePlugins");

		StringBundler sb = new StringBundler();

		sb.append("bidi,colorbutton,colordialog,div,flash,font,forms,");
		sb.append("indentblock,justify,keystrokes,maximize,newpage,pagebreak,");
		sb.append("preview,print,save,showblocks,smiley,stylescombo,");
		sb.append("templates,video");

		jsonObject.put(
			"removePlugins", removePlugins.concat(",").concat(sb.toString()));

		jsonObject.put(
			"toolbars", getToolbarsJSONObject(themeDisplay.getLocale()));
	}

	protected JSONObject getStyleFormatJSONObject(
		String styleFormatName, String element, int type) {

		JSONObject styleJSONObject = JSONFactoryUtil.createJSONObject();

		styleJSONObject.put("element", element);
		styleJSONObject.put("type", type);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("name", styleFormatName);
		jsonObject.put("style", styleJSONObject);

		return jsonObject;
	}

	protected JSONArray getStyleFormatsJSONArray(Locale locale) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ResourceBundle resourceBundle = null;

		try {
			resourceBundle = _resourceBundleLoader.loadResourceBundle(
				LocaleUtil.toLanguageId(locale));
		}
		catch (MissingResourceException mre) {
			resourceBundle = ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE;
		}

		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "normal"), "p",
				_CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "1"), "h1",
				_CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "2"), "h2",
				_CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "3"), "h3",
				_CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "4"), "h4",
				_CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "5"), "h5",
				_CKEDITOR_STYLE_BLOCK));
		jsonArray.put(
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "6"), "h6",
				_CKEDITOR_STYLE_BLOCK));

		return jsonArray;
	}

	protected JSONObject getStyleFormatsJSONObject(Locale locale) {
		JSONObject stylesJSONObject = JSONFactoryUtil.createJSONObject();

		stylesJSONObject.put("styles", getStyleFormatsJSONArray(locale));

		JSONObject styleFormatsJSONObject = JSONFactoryUtil.createJSONObject();

		styleFormatsJSONObject.put("cfg", stylesJSONObject);
		styleFormatsJSONObject.put("name", "styles");

		return styleFormatsJSONObject;
	}

	protected JSONObject getToolbarsAddJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONArray buttonsJSONArray = JSONFactoryUtil.createJSONArray();

		buttonsJSONArray.put("image");

		JSONObject buttonJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject cfgJSONObject = JSONFactoryUtil.createJSONObject();

		cfgJSONObject.put(
			"tableAttributes", JSONFactoryUtil.createJSONObject());

		buttonJSONObject.put("cfg", cfgJSONObject);

		buttonJSONObject.put("name", "table");

		buttonsJSONArray.put(buttonJSONObject);

		buttonsJSONArray.put("hline");

		jsonObject.put("buttons", buttonsJSONArray);

		jsonObject.put("tabIndex", 2);

		return jsonObject;
	}

	protected JSONObject getToolbarsJSONObject(Locale locale) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("add", getToolbarsAddJSONObject());
		jsonObject.put("styles", getToolbarsStylesJSONObject(locale));

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesJSONObject(Locale locale) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"selections", getToolbarsStylesSelectionsJSONArray(locale));
		jsonObject.put("tabIndex", 1);

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsHeadingTextJSONObject(
		Locale locale) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(getStyleFormatsJSONObject(locale));

		jsonObject.put("buttons", jsonArray);

		jsonObject.put("name", "headertext");
		jsonObject.put("test", "AlloyEditor.SelectionTest.headingtext");

		return jsonObject;
	}

	protected JSONArray getToolbarsStylesSelectionsJSONArray(Locale locale) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(getToolbarsStylesSelectionsHeadingTextJSONObject(locale));
		jsonArray.put(getToolbarsStylesSelectionsLinkJSONObject());
		jsonArray.put(getToolbarsStylesSelectionsTextJSONObject(locale));
		jsonArray.put(getToolbarsStylesSelectionsTableJSONObject());

		return jsonArray;
	}

	protected JSONObject getToolbarsStylesSelectionsLinkJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONArray linkButtonsJSONArray = JSONFactoryUtil.createJSONArray();

		JSONObject linkEditJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject cfgJSONObject = JSONFactoryUtil.createJSONObject();

		cfgJSONObject.put("showTargetSelector", false);

		linkEditJSONObject.put("cfg", cfgJSONObject);

		linkEditJSONObject.put("name", "linkEdit");

		linkButtonsJSONArray.put(linkEditJSONObject);

		jsonObject.put("buttons", linkButtonsJSONArray);
		jsonObject.put("name", "link");
		jsonObject.put("test", "AlloyEditor.SelectionTest.link");

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsTableJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"buttons",
			toJSONArray(
				"['tableHeading', 'tableRow', 'tableColumn', 'tableCell', " +
					"'tableRemove']"));
		jsonObject.put(
			"getArrowBoxClasses",
			"AlloyEditor.SelectionGetArrowBoxClasses.table");
		jsonObject.put("name", "table");
		jsonObject.put("setPosition", "AlloyEditor.SelectionSetPosition.table");
		jsonObject.put("test", "AlloyEditor.SelectionTest.table");

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsTextJSONObject(
		Locale locale) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(getStyleFormatsJSONObject(locale));
		jsonArray.put("bold");
		jsonArray.put("italic");
		jsonArray.put("ul");
		jsonArray.put("ol");
		jsonArray.put("link");
		jsonArray.put("removeFormat");

		jsonObject.put("buttons", jsonArray);

		jsonObject.put("name", "text");
		jsonObject.put("test", "AlloyEditor.SelectionTest.text");

		return jsonObject;
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.frontend.editor.lang)",
		unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		ClassLoader classLoader =
			AlloyEditorConfigContributor.class.getClassLoader();

		_resourceBundleLoader = new AggregateResourceBundleLoader(
			ResourceBundleUtil.getResourceBundleLoader(
				"content.Language", classLoader),
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	private static final int _CKEDITOR_STYLE_BLOCK = 1;

	private static final int _CKEDITOR_STYLE_INLINE = 2;

	private volatile ResourceBundleLoader _resourceBundleLoader;

}