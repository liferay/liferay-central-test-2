<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

double version = article.getVersion();

String cmd = ParamUtil.getString(request, Constants.CMD);
String toLanguageId = ParamUtil.getString(request, "toLanguageId");
String toLanguageDisplayName = StringPool.BLANK;

if (cmd.equals(Constants.TRANSLATE)) {
	toLanguageDisplayName = LocaleUtil.fromLanguageId(toLanguageId).getDisplayName(locale);
}
%>

<aui:script use="aui-base">
	var parent = window.parent;

	var parentIframe = window.parent.AUI().one('#<portlet:namespace /><%= toLanguageId %>controlPanelIframe');

	var originalParent = parentIframe.getData('originalParent');

	if (originalParent) {
		parent = originalParent;
	}

	parent.<portlet:namespace />postProcessTranslation('<%= HtmlUtil.escape(cmd) %>', '<%= version %>', '<%= HtmlUtil.escape(toLanguageId) %>', '<%= toLanguageDisplayName %>');

	window.parent.Liferay.fire(
		'<%= renderResponse.getNamespace() + toLanguageId %>close',
		{
			frame: window,
		}
	);
</aui:script>
