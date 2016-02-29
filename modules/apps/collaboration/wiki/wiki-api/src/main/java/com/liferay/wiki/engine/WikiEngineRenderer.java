/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.wiki.engine;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.wiki.exception.PageContentException;
import com.liferay.wiki.exception.WikiFormatException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import java.io.IOException;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

/**
 * @author Sergio González
 */
public interface WikiEngineRenderer {

	public String convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException, WikiFormatException;

	public String diffHtml(
			WikiPage sourcePage, WikiPage targetPage, PortletURL viewPageURL,
			PortletURL editPageURL, String attachmentURLPrefix)
		throws Exception;

	public WikiEngine fetchWikiEngine(String format);

	public List<WikiPage> filterOrphans(List<WikiPage> pages)
		throws PortalException;

	public String getFormatLabel(String format, Locale locale);

	public Collection<String> getFormats();

	public String getFormattedContent(
			RenderRequest renderRequest, RenderResponse renderResponse,
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String title, boolean preview)
		throws Exception;

	public void renderEditPageHTML(
			String format, PageContext pageContext, WikiNode node,
			WikiPage page)
		throws IOException, ServletException;

}