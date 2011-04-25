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

String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.JOURNAL_IMAGE_EXTENSIONS, StringPool.COMMA);

boolean smallImage = BeanParamUtil.getBoolean(article, request, "smallImage");
String smallImageURL = BeanParamUtil.getString(article, request, "smallImageURL");
%>

<liferay-ui:error-marker key="errorSection" value="image" />

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<h3><liferay-ui:message key="image" /></h3>

<liferay-ui:error exception="<%= ArticleSmallImageNameException.class %>">

<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, ", ") %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= ArticleSmallImageSizeException.class %>" message="please-enter-a-small-image-with-a-valid-file-size" />

<aui:fieldset>
	<aui:input inlineLabel="left" label="use-small-image" name="smallImage" />

	<aui:input label="small-image-url" name="smallImageURL" />

	<span style="font-size: xx-small;">-- <%= LanguageUtil.get(pageContext, "or").toUpperCase() %> --</span>

	<aui:input cssClass="lfr-input-text-container" label="small-image" name="smallFile" type="file" />
</aui:fieldset>