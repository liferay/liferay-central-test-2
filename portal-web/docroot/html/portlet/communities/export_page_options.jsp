<%
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
%>

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Layout curLayout = (Layout)row.getObject();
%>

<div class="type" style="float: right; padding-right: 32px; text-align: center; width: 100px;">
	<%= LanguageUtil.get(pageContext, StringUtil.replace(curLayout.getType(), "_", "-")) %>
</div>

<div class="layout">

	<%
	String taglibHref = "javascript:Liferay.LayoutExporter.details({toggle: '#_detail_" + curLayout.getPlid() + "_toggle', detail: '#_detail_" + curLayout.getPlid() + "'});";
	%>

	<aui:a href="<%= taglibHref %>" style="text-decoration: none;" target="_self"><img align="absmiddle" border="0" id="_detail_<%= curLayout.getPlid() %>_toggle" src="<%= themeDisplay.getPathThemeImages() %>/arrows/01_plus.png" onmouseover="Liferay.Portal.ToolTip.show(this, '<%= UnicodeLanguageUtil.get(pageContext, "details") %>')" /> <%= curLayout.getName(locale) %></aui:a>
</div>

<div class="aui-helper-hidden export-layout-detail" id="_detail_<%= curLayout.getPlid() %>" style="border-top: 1px solid #CCC; margin-top: 4px; padding-top: 4px; width: 95%;">
	<aui:input checked="<%= true %>" disabled="<%= true%>" inlineLabel="left" label="include-ancestor-pages-if-necessary" name='<%= "includeAncestors_" + curLayout.getPlid() %>' type="checkbox" value="1" />

	<aui:input inlineLabel="left" label="delete-live-page" name='<%= "delete_" + curLayout.getPlid() %>' type="checkbox" />

	<c:if test="<%= !curLayout.getChildren().isEmpty() %>">
		<aui:input label="include-all-descendent-pages" name='<%= "includeChildren_" + curLayout.getPlid() %>' type="checkbox" value="1" />
	</c:if>
</div>