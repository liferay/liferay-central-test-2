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

<%@ include file="/html/portlet/dynamic_data_list_display/init.jsp" %>

<%
DDLRecordSet recordSet = null;

try {
	if (Validator.isNotNull(recordSetId)) {
		recordSet = DDLRecordSetLocalServiceUtil.getRecordSet(recordSetId);
	}
%>

	<c:choose>
		<c:when test="<%= (recordSet != null) %>">

			<%
			renderRequest.setAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET, recordSet);
			%>

			<liferay-util:include page="/html/portlet/dynamic_data_lists/view_record_set.jsp">
				<liferay-util:param name="detailDDMTemplateId" value="<%= String.valueOf(detailDDMTemplateId) %>" />
				<liferay-util:param name="listDDMTemplateId" value="<%= String.valueOf(listDDMTemplateId) %>" />
				<liferay-util:param name="editable" value="<%= String.valueOf(editable) %>" />
			</liferay-util:include>

		</c:when>
		<c:otherwise>

			<%
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
			%>

			<br />

			<div class="portlet-msg-info">
				<liferay-ui:message key="select-an-existing-list-or-add-a-list-to-be-displayed-in-this-portlet" />
			</div>
		</c:otherwise>
	</c:choose>

<%
}
catch (NoSuchRecordSetException nsrse) {
%>

	<div class="portlet-msg-error">
		<%= LanguageUtil.get(pageContext, "the-selected-list-no-longer-exists") %>
	</div>

<%
}

boolean showSelectArticleIcon = PortletPermissionUtil.contains(permissionChecker, plid, portletDisplay.getId(), ActionKeys.CONFIGURATION);
boolean showAddListIcon = PortletPermissionUtil.contains(permissionChecker, plid, portletDisplay.getId(), ActionKeys.CONFIGURATION) && DDLPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_RECORD_SET);
boolean showIconsActions = themeDisplay.isSignedIn() && (showSelectArticleIcon || showAddListIcon);
%>

<c:if test="<%= showIconsActions %>">
	<div class="lfr-meta-actions icons-container">
		<div class="icon-actions">
			<c:if test="<%= showSelectArticleIcon %>">
				<liferay-ui:icon
					cssClass="portlet-configuration"
					image="configuration"
					message="select-list"
					method="get"
					url="<%= portletDisplay.getURLConfiguration() %>"
				/>
			</c:if>

			<c:if test="<%= showAddListIcon %>">
				<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addListURL" portletName="<%= PortletKeys.DYNAMIC_DATA_LISTS %>">
					<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record_set" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</liferay-portlet:renderURL>

				<liferay-ui:icon
					image="add_article"
					message="add-list"
					url="<%= addListURL %>"
				/>
			</c:if>
		</div>
	</div>
</c:if>