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

<%@ include file="/html/portlet/library_admin/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");
%>

<%
String taglibUrl = null;
%>

<span class="add-button" id="<portlet:namespace />addButtonContainer">
	<liferay-util:include page="/html/portlet/library_admin/add_button.jsp" />
</span>

<liferay-ui:icon-menu icon="" align="left" direction="down" message="sort" showExpanded="<%= false %>" showWhenSingleIcon="<%= false %>">

	<%
	PortletURL sortTitle = renderResponse.createRenderURL();

	sortTitle.setParameter("orderByCol", "title");
	sortTitle.setParameter("orderByType", (orderByCol.equals("title") && orderByType.equals("asc")) ? "desc" : "asc");
	%>

	<liferay-ui:icon
		image="folder"
		url="<%= sortTitle.toString() %>"
	/>

	<%
	PortletURL sortCreationDate = renderResponse.createRenderURL();

	sortCreationDate.setParameter("orderByCol", "creationDate");
	sortCreationDate.setParameter("orderByType", (orderByCol.equals("creationDate") && orderByType.equals("asc")) ? "desc" : "asc");
	%>

	<liferay-ui:icon
		image="folder"
		url="<%= sortCreationDate.toString() %>"
	/>

	<%
	PortletURL sortModifiedDate = renderResponse.createRenderURL();

	sortModifiedDate.setParameter("orderByCol", "modifiedDate");
	sortModifiedDate.setParameter("orderByType", (orderByCol.equals("modifiedDate") && orderByType.equals("asc")) ? "desc" : "asc");
	%>

	<liferay-ui:icon
		image="folder"
		url="<%= sortModifiedDate.toString() %>"
	/>

	<%
	PortletURL sortReadCountDate = renderResponse.createRenderURL();

	sortReadCountDate.setParameter("orderByCol", "readCount");
	sortReadCountDate.setParameter("orderByType", (orderByCol.equals("readCount") && orderByType.equals("asc")) ? "desc" : "asc");
	%>

	<liferay-ui:icon
		image="folder"
		url="<%= sortReadCountDate.toString() %>"
	/>

	<%
	PortletURL sortSize = renderResponse.createRenderURL();

	sortSize.setParameter("orderByCol", "size");
	sortSize.setParameter("orderByType", (orderByCol.equals("size") && orderByType.equals("asc")) ? "desc" : "asc");
	%>

	<liferay-ui:icon
		image="folder"
		url="<%= sortSize.toString() %>"
	/>
</liferay-ui:icon-menu>


<liferay-ui:icon-menu icon="" align="left" direction="down" message="actions" showExpanded="<%= false %>" showWhenSingleIcon="<%= false %>">

	<%
	taglibUrl = "javascript:" + renderResponse.getNamespace() + "doFileEntryAction('" + Constants.LOCK + "')";
	%>

	<liferay-ui:icon
		image="lock"
		url="<%= taglibUrl %>"
	/>

	<%
	taglibUrl = "javascript:" + renderResponse.getNamespace() + "doFileEntryAction('" + Constants.UNLOCK + "')";
	%>

	<liferay-ui:icon
		image="unlock"
		url="<%= taglibUrl %>"
	/>

	<%
	taglibUrl = "javascript:" + renderResponse.getNamespace() + "doFileEntryAction('" + Constants.MOVE + "')";
	%>

	<liferay-ui:icon
		image="submit"
		message="move"
		url="<%= taglibUrl %>"
	/>

	<%
	taglibUrl = "javascript:" + renderResponse.getNamespace() + "doFileEntryAction('" + Constants.DELETE + "')";
	%>

	<liferay-ui:icon-delete
		url="<%= taglibUrl %>"
	/>
</liferay-ui:icon-menu>

<span class="select-documents lfr-component lfr-actions align-left direction-down null lfr-extended show-arrow"><input id="<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>" name="<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>" type="checkbox" /></span>

<aui:script use="aui-base">
	var allRowIds = A.one('#<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>');

	allRowIds.on(
		'click',
		function(event) {
			var documentViews = A.one('.document-container');
			var documentView = A.all('.document-display-style')

			Liferay.Util.checkAll(documentViews, '<portlet:namespace /><%= RowChecker.ROW_IDS %>', event.currentTarget);

			if (allRowIds.attr('checked')) {
				documentView.addClass("selected");
			}
			else {
				documentView.removeClass("selected");
			}
		}
	);
</aui:script>