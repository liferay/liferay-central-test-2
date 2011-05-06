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

DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

long recordSetId = BeanParamUtil.getLong(recordSet, request, "recordSetId");

boolean editable = ParamUtil.getBoolean(request, "editable", true);

long ddmDetailTemplateId = ParamUtil.getLong(request, "ddmDetailTemplateId");

long ddmListTemplateId = ParamUtil.getLong(request, "ddmListTemplateId");
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title="<%= recordSet.getName(locale) %>"
/>

<portlet:actionURL var="editRecordSetURL">
	<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record_set" />
</portlet:actionURL>

<aui:form action="<%= editRecordSetURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveRecordSet();" %>'>
	<c:if test="<%= _isRecordSetEditable(portletName, editable) %>">
		<aui:button onClick='<%= renderResponse.getNamespace() + "addRecord();" %>' value="add-record" />

		<div class="separator"><!-- --></div>
	</c:if>

	<c:choose>
		<c:when test="<%= (ddmListTemplateId > 0) %>">

			<%= DDLUtil.getTemplateContent(ddmListTemplateId, recordSet, themeDisplay, renderRequest, renderResponse) %>

		</c:when>
		<c:otherwise>
			<liferay-util:include page="/html/portlet/dynamic_data_lists/view_records.jsp" />
		</c:otherwise>
	</c:choose>

</aui:form>

<aui:script>
	function <portlet:namespace />addRecord() {
		submitForm(document.<portlet:namespace />fm, '<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" portletName="<%= PortletKeys.DYNAMIC_DATA_LISTS %>"><portlet:param name="struts_action" value="/dynamic_data_lists/edit_record" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="backURL" value="<%= currentURL %>" /><portlet:param name="recordSetId" value="<%= String.valueOf(recordSetId) %>" /><portlet:param name="ddmDetailTemplateId" value="<%= String.valueOf(ddmDetailTemplateId) %>" /></liferay-portlet:renderURL>');
	}
</aui:script>

<%
PortalUtil.setPageSubtitle(recordSet.getName(locale), request);
PortalUtil.setPageDescription(recordSet.getDescription(), request);

PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), currentURL);
%>