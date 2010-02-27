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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<liferay-ui:tabs names="error" backURL="javascript:history.go(-1);" />

<liferay-ui:error exception="<%= NoSuchNodeException.class %>" message="the-wiki-could-not-be-found" />

<c:if test="<%= SessionErrors.contains(renderRequest, NoSuchPageException.class.getName()) %>">

	<%
	String nodeId = ParamUtil.getString(request, "nodeId");
	String title = ParamUtil.getString(request, "title");

	if (Validator.isNull(nodeId) || nodeId.equals("0")) {
		WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);

		if (node != null) {
			nodeId = String.valueOf(node.getNodeId());
		}
	}

	PortletURL searchURL = renderResponse.createRenderURL();

	searchURL.setParameter("struts_action", "/wiki/search");
	searchURL.setParameter("redirect", currentURL);
	searchURL.setParameter("nodeId", nodeId);
	searchURL.setParameter("keywords", title);

	PortletURL editPageURL = renderResponse.createRenderURL();

	editPageURL.setParameter("struts_action", "/wiki/edit_page");
	editPageURL.setParameter("redirect", currentURL);
	editPageURL.setParameter("nodeId", nodeId);
	editPageURL.setParameter("title", title);
	%>

	<div class="portlet-msg-info">
		<liferay-ui:message key="this-page-is-empty.-use-the-buttons-below-to-create-it-or-to-search-for-the-words-in-the-title" />
	</div>

	<div>
		<input onclick="location.href = '<%= searchURL.toString() %>'" type="button" value="<%= LanguageUtil.format(pageContext, "search-for-x", title) %>" />

		<input onclick="location.href = '<%= editPageURL.toString() %>'" type="button" value="<%= LanguageUtil.format(pageContext, "create-page-x", title) %>" />
	</div>
</c:if>

<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-page-title" />
<liferay-ui:error exception="<%= PrincipalException.class %>" message="you-do-not-have-the-required-permissions" />