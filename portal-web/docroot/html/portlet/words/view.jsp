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

<%@ include file="/html/portlet/words/init.jsp" %>

<%
String word = ParamUtil.getString(request, "word");
boolean scramble = ParamUtil.getBoolean(request, "scramble", true);

String[] words = (String[])request.getAttribute(WebKeys.WORDS_LIST);
%>

<form action="<portlet:renderURL><portlet:param name="struts_action" value="/words/view" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.SEARCH %>" />

<liferay-ui:error exception="<%= ScramblerException.class %>" message="please-enter-a-word-that-is-at-least-3-characters-long" />

<input name="<portlet:namespace />word" type="text" value="<%= HtmlUtil.escape(word) %>" />

<select name="<portlet:namespace />scramble">
	<option <%= scramble ? "selected" : "" %> value="1"><liferay-ui:message key="scramble" /></option>
	<option <%= !scramble ? "selected" : "" %> value="0"><liferay-ui:message key="unscramble" /></option>
</select>

<input type="submit" value="<liferay-ui:message key="search" />" />

<c:if test="<%= (words != null) && (words.length > 0) %>">
	<br /><br />

	<%
	for (int i = 0; i < words.length; i++) {
	%>

		<%= HtmlUtil.escape(words[i]) %><br />

	<%
	}
	%>

</c:if>

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />word);
	</aui:script>
</c:if>