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

<%@ include file="/html/taglib/init.jsp" %>

<%
String key = (String)request.getAttribute("liferay-ui:error:key");
String message = (String)request.getAttribute("liferay-ui:error:message");
boolean translateMessage = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:error:translateMessage"));
String rowBreak = (String)request.getAttribute("liferay-ui:error:rowBreak");

String bodyContentString = StringPool.BLANK;

Object bodyContent = request.getAttribute("liferay-ui:error:bodyContent");

if (bodyContent != null) {
	bodyContentString = bodyContent.toString();
}
%>

<c:choose>
	<c:when test="<%= (key != null) && Validator.isNull(message) %>">
		<c:if test="<%= SessionErrors.contains(portletRequest, key) %>">
			<c:if test="<%= Validator.isNotNull(bodyContentString) %>">
				<liferay-ui:alert
					message="<%= HtmlUtil.escapeJS(bodyContentString) %>"
					timeout="0"
					title='<%= LanguageUtil.get(request, "danger") %>'
					type="danger"
				/>

				<%= rowBreak %>
			</c:if>
		</c:if>
	</c:when>
	<c:when test='<%= SessionErrors.contains(portletRequest, "warning") %>'>
		<liferay-util:buffer var="alertMessage">
			<c:when test="<%= message != null %>">
				<liferay-ui:message key="<%= message %>" localizeKey="<%= translateMessage %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key='<%= (String)SessionErrors.get(portletRequest, "warning") %>' localizeKey="<%= translateMessage %>" />
			</c:otherwise>
		</liferay-util:buffer>

		<liferay-ui:alert
			message="<%= alertMessage %>"
			timeout="0"
			title='<%= LanguageUtil.get(request, "warning") %>'
			type="warning"
		/>

		<%= rowBreak %>
	</c:when>
	<c:when test="<%= key == null %>">
		<c:if test="<%= !SessionErrors.isEmpty(portletRequest) %>">
			<liferay-ui:alert
				message='<%= LanguageUtil.get(request, "your-request-failed-to-complete") %>'
				timeout="5000"
				title='<%= LanguageUtil.get(request, "danger") %>'
				type="danger"
			/>

			<%= rowBreak %>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:if test="<%= SessionErrors.contains(portletRequest, key) %>">
			<liferay-ui:alert
				message="<%= translateMessage ? LanguageUtil.get(request, message) : message %>"
				timeout="0"
				title='<%= LanguageUtil.get(request, "danger") %>'
				type="danger"
			/>

			<%= rowBreak %>
		</c:if>
	</c:otherwise>
</c:choose>