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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
OrderSearch searchContainer = (OrderSearch)request.getAttribute("liferay-ui:search:searchContainer");

OrderDisplayTerms displayTerms = (OrderDisplayTerms)searchContainer.getDisplayTerms();
%>

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="number" />
	</td>
	<td>
		<liferay-ui:message key="status" />
	</td>
	<td>
		<liferay-ui:message key="first-name" />
	</td>
	<td>
		<liferay-ui:message key="last-name" />
	</td>
	<td>
		<liferay-ui:message key="email-address" />
	</td>
</tr>
<tr>
	<td>
		<input name="<portlet:namespace /><%= displayTerms.NUMBER %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getNumber()) %>" />
	</td>
	<td>
		<select name="<%= displayTerms.STATUS %>">
			<option value=""></option>

			<%
			for (int i = 0; i < ShoppingOrderConstants.STATUSES.length; i++) {
			%>

				<option <%= displayTerms.getStatus().equals(ShoppingOrderConstants.STATUSES[i]) ? "selected" : "" %> value="<%= ShoppingOrderConstants.STATUSES[i] %>"><%= LanguageUtil.get(pageContext, ShoppingOrderConstants.STATUSES[i]) %></option>

			<%
			}
			%>

		</select>
	</td>
	<td>
		<input name="<portlet:namespace /><%= displayTerms.FIRST_NAME %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getFirstName()) %>" />
	</td>
	<td>
		<input name="<portlet:namespace /><%= displayTerms.LAST_NAME %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getLastName()) %>" />
	</td>
	<td>
		<input name="<portlet:namespace /><%= displayTerms.EMAIL_ADDRESS %>" size="20" type="text" value="<%= HtmlUtil.escape(displayTerms.getEmailAddress()) %>" />
	</td>
</tr>
</table>

<br />

<div>
	<select name="<portlet:namespace /><%= displayTerms.AND_OPERATOR %>">
		<option <%= displayTerms.isAndOperator() ? "selected" : "" %> value="1"><liferay-ui:message key="and" /></option>
		<option <%= !displayTerms.isAndOperator() ? "selected" : "" %> value="0"><liferay-ui:message key="or" /></option>
	</select>

	<input type="submit" value="<liferay-ui:message key="search" />" />
</div>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.NUMBER %>);
	</aui:script>
</c:if>