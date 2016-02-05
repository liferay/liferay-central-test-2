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

package com.liferay.item.selector;

import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Iván Zaera
 */
public interface ItemSelectorView<T extends ItemSelectorCriterion> {

	public Class<T> getItemSelectorCriterionClass();

	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes();

	public String getTitle(Locale locale);

	public boolean isShowSearch();

	public boolean isVisible(ThemeDisplay themeDisplay);

	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			T itemSelectorCriterion, PortletURL portletURL,
			String itemSelectedEventName, boolean search)
		throws IOException, ServletException;

}