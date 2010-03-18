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

<%@ include file="/html/portlet/sms/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">

		<%
		String to = ParamUtil.getString(request, "to");
		String subject = ParamUtil.getString(request, "subject");
		String message = ParamUtil.getString(request, "message");
		%>

		<form action="<portlet:actionURL />" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
		<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.SEND %>" />

		<liferay-ui:success key='<%= portletName + ".send" %>' message="you-have-successfully-sent-a-sms-message" />

		<liferay-ui:error key="to" message="please-enter-a-valid-email-address" />
		<liferay-ui:error key="message" message="please-limit-your-message-to-500-characters" />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="to" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />to" type="text" value="<%= to %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="subject" />
			</td>
			<td>
				<input class="lfr-input-text" name="<portlet:namespace />subject" type="text" onChange="document.<portlet:namespace />fm.<portlet:namespace />length.value = document.<portlet:namespace />fm.<portlet:namespace />subject.value.length + document.<portlet:namespace />fm.<portlet:namespace />message.value.length;" onKeyUp="document.<portlet:namespace />fm.<portlet:namespace />length.value = document.<portlet:namespace />fm.<portlet:namespace />subject.value.length + document.<portlet:namespace />fm.<portlet:namespace />message.value.length;" value="<%= HtmlUtil.escape(subject) %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="message" />
			</td>
			<td>
				<textarea class="lfr-textarea" name="<portlet:namespace />message" wrap="soft" onChange="document.<portlet:namespace />fm.<portlet:namespace />length.value = document.<portlet:namespace />fm.<portlet:namespace />subject.value.length + document.<portlet:namespace />fm.<portlet:namespace />message.value.length;" onKeyUp="document.<portlet:namespace />fm.<portlet:namespace />length.value = document.<portlet:namespace />fm.<portlet:namespace />subject.value.length + document.<portlet:namespace />fm.<portlet:namespace />message.value.length;"><%= HtmlUtil.escape(message) %></textarea><br />
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input disabled="disabled" maxlength="3" name="<portlet:namespace />length" size="3" type="text" value="<%= subject.length() + message.length() %>" />

				<span style="font-size: xx-small;">(500 <liferay-ui:message key="characters-maximum" />)</span>
			</td>
		</tr>
		</table>

		<br />

		<input type="submit" value="<liferay-ui:message key="send-text-message" />" />

		</form>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<aui:script>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />to);
			</aui:script>
		</c:if>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="you-must-be-authenticated-to-use-this-portlet" />
	</c:otherwise>
</c:choose>