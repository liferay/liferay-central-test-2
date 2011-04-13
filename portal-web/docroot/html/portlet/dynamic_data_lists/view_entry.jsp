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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DDLEntry entry = (DDLEntry)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title="<%= entry.getName(locale) %>"
/>

<portlet:actionURL var="editEntryURL">
	<portlet:param name="struts_action" value="/dynamic_data_lists/edit_entry" />
</portlet:actionURL>

<aui:form action="<%= editEntryURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveEntry();" %>'>
	<aui:button onClick='<%= renderResponse.getNamespace() + "addEntryItem();" %>' value="add-list-item" />

	<div class="separator"><!-- --></div>

	<liferay-util:include page="/html/portlet/dynamic_data_lists/view_entry_items.jsp" />
</aui:form>

<aui:script>
	function <portlet:namespace />addEntryItem() {
		submitForm(document.<portlet:namespace />fm, '<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" portletName="<%= PortletKeys.DYNAMIC_DATA_LISTS %>"><portlet:param name="struts_action" value="/dynamic_data_lists/edit_entry_item" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="backURL" value="<%= currentURL %>" /><portlet:param name="entryId" value="<%= String.valueOf(entryId) %>" /></liferay-portlet:renderURL>');
	}
</aui:script>

<%
PortalUtil.setPageSubtitle(entry.getName(locale), request);
PortalUtil.setPageDescription(entry.getDescription(), request);

PortalUtil.addPortletBreadcrumbEntry(request, entry.getName(locale), currentURL);
%>