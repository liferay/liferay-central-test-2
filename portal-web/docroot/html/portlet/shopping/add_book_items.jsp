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
String redirect = ParamUtil.getString(request, "redirect");

long categoryId = ParamUtil.getLong(request, "categoryId", ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/add_book_items" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="alert('<%= UnicodeLanguageUtil.get(pageContext, "please-be-patient") %>'); submitForm(this); return false;">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />categoryId" type="hidden" value="<%= categoryId %>" />

<liferay-util:include page="/html/portlet/shopping/tabs1.jsp">
	<liferay-util:param name="tabs1" value="categories" />
</liferay-util:include>

<div class="breadcrumbs">
	<%= ShoppingUtil.getBreadcrumbs(categoryId, pageContext, renderRequest, renderResponse) %>
</div>

<liferay-ui:message key="add-all-isbn-numbers-separated-by-spaces" />

<br /><br />

<textarea class="lfr-textarea" name="<portlet:namespace />isbns" wrap="soft"></textarea>

<br /><br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>';" />

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />isbns);
	</aui:script>
</c:if>