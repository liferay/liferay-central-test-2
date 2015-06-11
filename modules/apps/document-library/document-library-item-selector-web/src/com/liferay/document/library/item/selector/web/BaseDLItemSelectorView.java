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

package com.liferay.document.library.item.selector.web;

import com.liferay.document.library.item.selector.web.display.context.DLItemSelectorViewDisplayContext;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;

import java.io.IOException;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Roberto Díaz
 */
public abstract class BaseDLItemSelectorView
	<T extends ItemSelectorCriterion, S extends ItemSelectorReturnType>
		implements DLItemSelectorView<T, S> {

	@Override
	public String[] getMimeTypes() {
		return new String[0];
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response, T t,
			PortletURL portletURL, String itemSelectedEventName)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
			"/o/document-library-item-selector-web/documents.jsp");

		DLItemSelectorViewDisplayContext dlItemSelectorViewDisplayContext =
			new DLItemSelectorViewDisplayContext(
				t, this, itemSelectedEventName, portletURL);

		request.setAttribute(
			DL_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			dlItemSelectorViewDisplayContext);

		requestDispatcher.include(request, response);
	}

}