<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
String twitterSn = contact.getTwitterSn();
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(twitterSn) %>">

		<%
		StringBuilder sb = new StringBuilder(5);

		sb.append("<a href=\"http://twitter.com/");
		sb.append(HtmlUtil.escapeAttribute(twitterSn));
		sb.append("\" target=\"_blank\">");
		sb.append(HtmlUtil.escape(twitterSn));
		sb.append("</a>");
		%>

		<liferay-ui:message arguments="<%= sb.toString() %>" key="your-twitter-screen-name-is-x" translateArguments="<%= false %>" />
	</c:when>
	<c:otherwise>

		<%
		String configureHREF = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "configureTwitter', title: '" + HtmlUtil.escapeJS(LanguageUtil.get(request, "my-account")) + "', uri: '" + HtmlUtil.escapeJS(themeDisplay.getURLMyAccount() + "#_" + PortletKeys.MY_ACCOUNT + "_tab=_" + PortletKeys.MY_ACCOUNT + "_socialNetwork") + "'});";
		%>

		<div class="alert alert-info">
			<a href="<%= configureHREF %>"><liferay-ui:message key="please-configure-your-twitter-screen-name" /></a>
		</div>
	</c:otherwise>
</c:choose>