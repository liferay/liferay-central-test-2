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

package com.liferay.wiki.item.selector.web;

import com.liferay.item.selector.ItemSelectorView;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Iván Zaera
 */
public class WikiAttachmentItemSelectorView
	implements ItemSelectorView<WikiAttachmentItemSelectorCriterion> {

	public static final String ITEM_SELECTED_CALLBACK =
		WikiAttachmentItemSelectorView.class.getName() +
			"#ITEM_SELECTED_CALLBACK";

	public static final String PORTLET_URL =
		WikiAttachmentItemSelectorView.class.getName() + "#PORTLET_URL";

	public static final String WIKI_ATTACHMENT_ITEM_SELECTOR_CRITERION =
		WikiAttachmentItemSelectorView.class.getName() +
			"#WIKI_ATTACHMENT_ITEM_SELECTOR_CRITERION";

	@Override
	public Class<WikiAttachmentItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return WikiAttachmentItemSelectorCriterion.class;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content/Language", locale);

		return resourceBundle.getString("attachments");
	}

	@Override
	public void renderHTML(
			ServletRequest request, ServletResponse response,
			WikiAttachmentItemSelectorCriterion
				wikiAttachmentItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedCallback)
		throws IOException, ServletException {

		request.setAttribute(ITEM_SELECTED_CALLBACK, itemSelectedCallback);
		request.setAttribute(PORTLET_URL, portletURL);
		request.setAttribute(
			WIKI_ATTACHMENT_ITEM_SELECTOR_CRITERION,
			wikiAttachmentItemSelectorCriterion);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
			"/o/wiki-item-selector-web/attachments.jsp");

		requestDispatcher.include(request, response);
	}

}