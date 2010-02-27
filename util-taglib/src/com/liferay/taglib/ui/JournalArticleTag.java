/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="JournalArticleTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class JournalArticleTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute(
			"liferay-ui:journal-article:articleResourcePrimKey",
			String.valueOf(_articleResourcePrimKey));
		request.setAttribute(
			"liferay-ui:journal-article:groupId", String.valueOf(_groupId));
		request.setAttribute(
			"liferay-ui:journal-article:articleId", _articleId);
		request.setAttribute(
			"liferay-ui:journal-template:templateId", _templateId);
		request.setAttribute(
			"liferay-ui:journal-article:languageId", _languageId);
		request.setAttribute(
			"liferay-ui:journal-article:articlePage",
			String.valueOf(_articlePage));
		request.setAttribute(
			"liferay-ui:journal-article:xmlRequest", _xmlRequest);
		request.setAttribute(
			"liferay-ui:journal-article:showTitle", String.valueOf(_showTitle));
		request.setAttribute(
			"liferay-ui:journal-article:showAvailableLocales",
			String.valueOf(_showAvailableLocales));

		return EVAL_BODY_BUFFERED;
	}

	public void setArticleResourcePrimKey(long articleResourcePrimKey) {
		_articleResourcePrimKey = articleResourcePrimKey;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setArticleId(String articleId) {
		_articleId = articleId;
	}

	public void setTemplateId(String templateId) {
		_templateId = templateId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setArticlePage(int articlePage) {
		_articlePage = articlePage;
	}

	public void setXmlRequest(String xmlRequest) {
		_xmlRequest = xmlRequest;
	}

	public void setShowTitle(boolean showTitle) {
		_showTitle = showTitle;
	}

	public void setShowAvailableLocales(boolean showAvailableLocales) {
		_showAvailableLocales = showAvailableLocales;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/ui/journal_article/page.jsp";

	private long _articleResourcePrimKey;
	private long _groupId;
	private String _articleId;
	private String _templateId;
	private String _languageId;
	private int _articlePage = 1;
	private String _xmlRequest;
	private boolean _showTitle;
	private boolean _showAvailableLocales;

}