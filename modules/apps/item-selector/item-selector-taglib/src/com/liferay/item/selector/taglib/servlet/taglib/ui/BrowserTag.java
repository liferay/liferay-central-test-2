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

package com.liferay.item.selector.taglib.servlet.taglib.ui;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UploadableFileReturnType;
import com.liferay.item.selector.taglib.ItemSelectorBrowserReturnTypeUtil;
import com.liferay.item.selector.taglib.util.ServletContextUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Roberto Díaz
 */
public class BrowserTag extends IncludeTag {

	public void setDesiredItemSelectorReturnTypes(
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes) {

		_desiredItemSelectorReturnTypes = desiredItemSelectorReturnTypes;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisplayStyleURL(PortletURL displayStyleURL) {
		_displayStyleURL = displayStyleURL;
	}

	public void setItemSelectedEventName(String itemSelectedEventName) {
		_itemSelectedEventName = itemSelectedEventName;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setSearchContainer(SearchContainer<?> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setSearchURL(PortletURL searchURL) {
		_searchURL = searchURL;
	}

	public void setShowBreadcrumb(boolean showBreadcrumb) {
		_showBreadcrumb = showBreadcrumb;
	}

	public void setTabName(String tabName) {
		_tabName = tabName;
	}

	public void setUploadMessage(String uploadMessage) {
		_uploadMessage = uploadMessage;
	}

	public void setUploadURL(PortletURL uploadURL) {
		_uploadURL = uploadURL;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_desiredItemSelectorReturnTypes = null;
		_displayStyle = null;
		_displayStyleURL = null;
		_itemSelectedEventName = null;
		_searchContainer = null;
		_searchURL = null;
		_showBreadcrumb = false;
		_tabName = null;
		_uploadMessage = null;
		_uploadURL = null;
	}

	protected String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		return _DEFAULT_DISPLAY_STYLE;
	}

	protected ItemSelectorReturnType getDraggableFileReturnType() {
		ItemSelectorReturnType firstDraggableFileReturnType =
			ItemSelectorBrowserReturnTypeUtil.
				getFirstAvailableDraggableFileReturnType(
					_desiredItemSelectorReturnTypes);

		if (Validator.equals(
				ClassUtil.getClassName(firstDraggableFileReturnType),
				UploadableFileReturnType.class.getName()) &&
			(_uploadURL == null)) {

			return null;
		}

		return firstDraggableFileReturnType;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected String getUploadMessage() {
		if (Validator.isNotNull(_uploadMessage)) {
			return _uploadMessage;
		}

		return LanguageUtil.get(
			request,
			"upload-a-document-by-dropping-it-right-here-or-by-pressing-plus-" +
				"icon");
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:item-selector-browser:displayStyle", getDisplayStyle());
		request.setAttribute(
			"liferay-ui:item-selector-browser:displayStyleURL",
			_displayStyleURL);
		request.setAttribute(
			"liferay-ui:item-selector-browser:draggableFileReturnType",
			getDraggableFileReturnType());
		request.setAttribute(
			"liferay-ui:item-selector-browser:existingFileEntryReturnType",
			ItemSelectorBrowserReturnTypeUtil.
				getFirstAvailableExistingFileEntryReturnType(
					_desiredItemSelectorReturnTypes));
		request.setAttribute(
			"liferay-ui:item-selector-browser:itemSelectedEventName",
			_itemSelectedEventName);
		request.setAttribute(
			"liferay-ui:item-selector-browser:searchContainer",
			_searchContainer);
		request.setAttribute(
			"liferay-ui:item-selector-browser:searchURL", _searchURL);
		request.setAttribute(
			"liferay-ui:item-selector-browser:showBreadcrumb", _showBreadcrumb);
		request.setAttribute(
			"liferay-ui:item-selector-browser:tabName", _tabName);
		request.setAttribute(
			"liferay-ui:item-selector-browser:uploadMessage",
			getUploadMessage());
		request.setAttribute(
			"liferay-ui:item-selector-browser:uploadURL", _uploadURL);
	}

	private static final String _DEFAULT_DISPLAY_STYLE = "icon";

	private static final String _PAGE = "/taglib/ui/browser/page.jsp";

	private List<ItemSelectorReturnType> _desiredItemSelectorReturnTypes;
	private String _displayStyle;
	private PortletURL _displayStyleURL;
	private String _itemSelectedEventName;
	private SearchContainer<?> _searchContainer;
	private PortletURL _searchURL;
	private boolean _showBreadcrumb;
	private String _tabName;
	private String _uploadMessage;
	private PortletURL _uploadURL;

}