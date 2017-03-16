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

package com.liferay.layout.item.selector.web.internal.display.context;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class LayoutItemSelectorViewDisplayContext {

	public LayoutItemSelectorViewDisplayContext(
		HttpServletRequest request,
		LayoutItemSelectorCriterion layoutItemSelectorCriterion,
		String itemSelectedEventName, boolean privateLayout) {

		_request = request;
		_layoutItemSelectorCriterion = layoutItemSelectorCriterion;
		_itemSelectedEventName = itemSelectedEventName;
		_privateLayout = privateLayout;
	}

	public String getCkEditorFuncNum() {
		String ckEditorFuncNum = ParamUtil.getString(
			_request, "CKEditorFuncNum");

		return ckEditorFuncNum;
	}

	public PortletURL getEditLayoutURL() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL editLayoutURL = PortletProviderUtil.getPortletURL(
			_request, Layout.class.getName(), PortletProvider.Action.EDIT);

		editLayoutURL.setParameter("redirect", themeDisplay.getURLCurrent());
		editLayoutURL.setParameter(
			"groupId", String.valueOf(themeDisplay.getScopeGroupId()));

		return editLayoutURL;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getItemSelectorReturnTypeName() {
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			_layoutItemSelectorCriterion.getDesiredItemSelectorReturnTypes();

		ItemSelectorReturnType desiredItemSelectorReturnType =
			desiredItemSelectorReturnTypes.get(0);

		String itemSelectorReturnTypeName = ClassUtil.getClassName(
			desiredItemSelectorReturnType);

		if (Validator.isNull(itemSelectorReturnTypeName)) {
			throw new IllegalArgumentException(
				"Invalid item selector return type " +
					itemSelectorReturnTypeName);
		}

		return itemSelectorReturnTypeName;
	}

	public String getLayoutBreadcrumb(Layout layout) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(_request, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(_request, "public-pages"));
		}

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(ancestors);

		for (Layout ancestor : ancestors) {
			sb.append(HtmlUtil.escape(ancestor.getName(locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(layout.getName(locale)));

		return sb.toString();
	}

	public LayoutItemSelectorCriterion getLayoutItemSelectorCriterion() {
		return _layoutItemSelectorCriterion;
	}

	public JSONObject getLayoutsJSONObject() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutUuid = ParamUtil.getString(_request, "layoutUuid");

		JSONArray jsonArray = _getLayoutsJSONArray(
			themeDisplay.getScopeGroupId(), isPrivateLayout(), 0, layoutUuid);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("children", jsonArray);
		jsonObject.put("disabled", true);
		jsonObject.put("expanded", true);
		jsonObject.put("icon", "home");
		jsonObject.put("name", themeDisplay.getScopeGroupName());

		return jsonObject;
	}

	public String getRootNodeName() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		return group.getLayoutRootNodeName(
			isPrivateLayout(), themeDisplay.getLocale());
	}

	public String getSelectedLayoutIds() {
		String selectedLayoutIds = ParamUtil.getString(
			_request, "selectedLayoutIds");

		return selectedLayoutIds;
	}

	public long getSelPlid() {
		long selPlid = ParamUtil.getLong(
			_request, "selPlid", LayoutConstants.DEFAULT_PLID);

		return selPlid;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	private JSONArray _getLayoutsJSONArray(
			long groupId, boolean privateLayout, long parentLayoutId,
			String selectedLayoutUuid)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId);

		for (Layout layout : layouts) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("icon", "page");
			jsonObject.put("id", layout.getUuid());
			jsonObject.put("name", layout.getName(themeDisplay.getLocale()));
			jsonObject.put("value", getLayoutBreadcrumb(layout));

			if (layout.getUuid().equals(selectedLayoutUuid)) {
				jsonObject.put("selected", true);
			}

			if (_layoutItemSelectorCriterion.isCheckDisplayPage() &&
				!layout.isContentDisplayPage()) {

				jsonObject.put("disabled", true);
			}

			JSONArray childrenJSONArray = _getLayoutsJSONArray(
				groupId, privateLayout, layout.getLayoutId(),
				selectedLayoutUuid);

			if (childrenJSONArray.length() > 0) {
				jsonObject.put("children", childrenJSONArray);
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private final String _itemSelectedEventName;
	private final LayoutItemSelectorCriterion _layoutItemSelectorCriterion;
	private final boolean _privateLayout;
	private final HttpServletRequest _request;

}