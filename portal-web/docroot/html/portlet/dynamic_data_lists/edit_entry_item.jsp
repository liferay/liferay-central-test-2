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
String backURL = ParamUtil.getString(request, "backURL");

DDLEntryItem entryItem = (DDLEntryItem)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_ENTRY_ITEM);

long entryItemId = BeanParamUtil.getLong(entryItem, request, "entryItemId");
long entryId = BeanParamUtil.getLong(entryItem, request, "entryId");
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	title='<%= (entryItem != null) ? "edit-list-item" : "new-list-item" %>'
/>

<portlet:actionURL var="editEntryItemURL">
	<portlet:param name="struts_action" value="/dynamic_data_lists/edit_entry_item" />
</portlet:actionURL>

<aui:form action="<%= editEntryItemURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveEntryItem();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="entryId" type="hidden" value="<%= entryId %>" />
	<aui:input name="entryItemId" type="hidden" value="<%= entryItemId %>" />

	<aui:fieldset>

		<%
		DDLEntry entry = DDLEntryLocalServiceUtil.getEntry(entryId);

		DDMStructure structure = entry.getDDMStructure();

		Fields fields = null;

		if (entryItem != null) {
			fields = StorageEngineUtil.getFields(entryItem.getClassPK());
		}

		out.print(DDMXSDUtil.getHTML(pageContext, structure.getXsd(), fields));
		%>

		<aui:button-row>
			<aui:button name="saveButton"  type="submit" value="save" />

			<aui:button name="cancelButton" onClick="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />saveEntryItem() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (entryItem == null) ? Constants.ADD : Constants.UPDATE %>";

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_lists/view_entry");
portletURL.setParameter("entryId", String.valueOf(entryId));

DDLEntry entry = DDLEntryLocalServiceUtil.getEntry(entryId);

PortalUtil.addPortletBreadcrumbEntry(request, entry.getName(locale), portletURL.toString());

if (entryItem != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit-list-item"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-list-item"), currentURL);
}
%>